# Copyright (c) 2004 Python Software Foundation.
# All rights reserved.

# Written by Eric Price <eprice at tjhsst.edu>
#    and Facundo Batista <facundo at taniquetil.com.ar>
#    and Raymond Hettinger <python at rcn.com>
#    and Aahz <aahz at pobox.com>
#    and Tim Peters

# This module is currently Py2.3 compatible and should be kept that way
# unless a major compelling advantage arises.  IOW, 2.3 compatibility is
# strongly preferred, but not guaranteed.

# Also, this module should be kept in sync with the latest updates of
# the IBM specification as it evolves.  Those updates will be treated
# as bug fixes (deviation from the spec is a compatibility, usability
# bug) and will be backported.  At this point the spec is stabilizing
# and the updates are becoming fewer, smaller, and less significant.

"""
This is a Py2.3 implementation of decimal floating point arithmetic based on
the General Decimal Arithmetic Specification:

    http://speleotrove.com/decimal/decarith.html

and IEEE standard 854-1987:

    http://en.wikipedia.org/wiki/IEEE_854-1987

Decimal floating point has finite precision with arbitrarily large bounds.

The purpose of this module is to support arithmetic using familiar
"schoolhouse" rules and to avoid some of the tricky representation
issues associated with binary floating point.  The package is especially
useful for financial applications or for contexts where users have
expectations that are at odds with binary floating point (for instance,
in binary floating point, 1.00 % 0.1 gives 0.09999999999999995 instead
of the expected Decimal('0.00') returned by decimal floating point).

Here are some examples of using the decimal module:

>>> from decimal import *
>>> setcontext(ExtendedContext)
>>> Decimal(0)
Decimal('0')
>>> Decimal('1')
Decimal('1')
>>> Decimal('-.0123')
Decimal('-0.0123')
>>> Decimal(123456)
Decimal('123456')
>>> Decimal('123.45e12345678901234567890')
Decimal('1.2345E+12345678901234567892')
>>> Decimal('1.33') + Decimal('1.27')
Decimal('2.60')
>>> Decimal('12.34') + Decimal('3.87') - Decimal('18.41')
Decimal('-2.20')
>>> dig = Decimal(1)
>>> print dig / Decimal(3)
0.333333333
>>> getcontext().prec = 18
>>> print dig / Decimal(3)
0.333333333333333333
>>> print dig.sqrt()
1
>>> print Decimal(3).sqrt()
1.73205080756887729
>>> print Decimal(3) ** 123
4.85192780976896427E+58
>>> inf = Decimal(1) / Decimal(0)
>>> print inf
Infinity
>>> neginf = Decimal(-1) / Decimal(0)
>>> print neginf
-Infinity
>>> print neginf + inf
NaN
>>> print neginf * inf
-Infinity
>>> print dig / 0
Infinity
>>> getcontext().traps[DivisionByZero] = 1
>>> print dig / 0
Traceback (most recent call last):
  ...
  ...
  ...
DivisionByZero: x / 0
>>> c = Context()
>>> c.traps[InvalidOperation] = 0
>>> print c.flags[InvalidOperation]
0
>>> c.divide(Decimal(0), Decimal(0))
Decimal('NaN')
>>> c.traps[InvalidOperation] = 1
>>> print c.flags[InvalidOperation]
1
>>> c.flags[InvalidOperation] = 0
>>> print c.flags[InvalidOperation]
0
>>> print c.divide(Decimal(0), Decimal(0))
Traceback (most recent call last):
  ...
  ...
  ...
InvalidOperation: 0 / 0
>>> print c.flags[InvalidOperation]
1
>>> c.flags[InvalidOperation] = 0
>>> c.traps[InvalidOperation] = 0
>>> print c.divide(Decimal(0), Decimal(0))
NaN
>>> print c.flags[InvalidOperation]
1
>>>
"""

__all__ = [
    # Two major classes
    'Decimal', 'Context',

    # Contexts
    'DefaultContext', 'BasicContext', 'ExtendedContext',

    # Exceptions
    'DecimalException', 'Clamped', 'InvalidOperation', 'DivisionByZero',
    'Inexact', 'Rounded', 'Subnormal', 'Overflow', 'Underflow',

    # Constants for use in setting up contexts
    'ROUND_DOWN', 'ROUND_HALF_UP', 'ROUND_HALF_EVEN', 'ROUND_CEILING',
    'ROUND_FLOOR', 'ROUND_UP', 'ROUND_HALF_DOWN', 'ROUND_05UP',

    # Functions for manipulating contexts
    'setcontext', 'getcontext', 'localcontext'
]

__version__ = '1.70'    # Highest version of the spec this complies with

import math as _math
import numbers as _numbers

try:
    from collections import namedtuple as _namedtuple
    DecimalTuple = _namedtuple('DecimalTuple', 'sign digits exponent')
except ImportError:
    DecimalTuple = lambda *args: args

# Rounding
ROUND_DOWN = 'ROUND_DOWN'
ROUND_HALF_UP = 'ROUND_HALF_UP'
ROUND_HALF_EVEN = 'ROUND_HALF_EVEN'
ROUND_CEILING = 'ROUND_CEILING'
ROUND_FLOOR = 'ROUND_FLOOR'
ROUND_UP = 'ROUND_UP'
ROUND_HALF_DOWN = 'ROUND_HALF_DOWN'
ROUND_05UP = 'ROUND_05UP'

# Errors

class DecimalException(ArithmeticError):
    """Base exception class.

    Used exceptions derive from this.
    If an exception derives from another exception besides this (such as
    Underflow (Inexact, Rounded, Subnormal) that indicates that it is only
    called if the others are present.  This isn't actually used for
    anything, though.

    handle  -- Called when context._raise_error is called and the
               trap_enabler is not set.  First argument is self, second is the
               context.  More arguments can be given, those being after
               the explanation in _raise_error (For example,
               context._raise_error(NewError, '(-x)!', self._sign) would
               call NewError().handle(context, self._sign).)

    To define a new exception, it should be sufficient to have it derive
    from DecimalException.
    """
    def handle(self, context, *args):
        pass


class Clamped(DecimalException):
    """Exponent of a 0 changed to fit bounds.

    This occurs and signals clamped if the exponent of a result has been
    altered in order to fit the constraints of a specific concrete
    representation.  This may occur when the exponent of a zero result would
    be outside the bounds of a representation, or when a large normal
    number would have an encoded exponent that cannot be represented.  In
    this latter case, the exponent is reduced to fit and the corresponding
    number of zero digits are appended to the coefficient ("fold-down").
    """

class InvalidOperation(DecimalException):
    """An invalid operation was performed.

    Various bad things cause this:

    Something creates a signaling NaN
    -INF + INF
    0 * (+-)INF
    (+-)INF / (+-)INF
    x % 0
    (+-)INF % x
    x._rescale( non-integer )
    sqrt(-x) , x > 0
    0 ** 0
    x ** (non-integer)
    x ** (+-)INF
    An operand is invalid

    The result of the operation after these is a quiet positive NaN,
    except when the cause is a signaling NaN, in which case the result is
    also a quiet NaN, but with the original sign, and an optional
    diagnostic information.
    """
    def handle(self, context, *args):
        if args:
            ans = _dec_from_triple(args[0]._sign, args[0]._int, 'n', True)
            return ans._fix_nan(context)
        return _NaN

class ConversionSyntax(InvalidOperation):
    """Trying to convert badly formed string.

    This occurs and signals invalid-operation if a string is being
    converted to a number and it does not conform to the numeric string
    syntax.  The result is [0,qNaN].
    """
    def handle(self, context, *args):
        return _NaN

class DivisionByZero(DecimalException, ZeroDivisionError):
    """Division by 0.

    This occurs and signals division-by-zero if division of a finite number
    by zero was attempted (during a divide-integer or divide operation, or a
    power operation with negative right-hand operand), and the dividend was
    not zero.

    The result of the operation is [sign,inf], where sign is the exclusive
    or of the signs of the operands for divide, or is 1 for an odd power of
    -0, for power.
    """

    def handle(self, context, sign, *args):
        return _SignedInfinity[sign]

class DivisionImpossible(InvalidOperation):
    """Cannot perform the division adequately.

    This occurs and signals invalid-operation if the integer result of a
    divide-integer or remainder operation had too many digits (would be
    longer than precision).  The result is [0,qNaN].
    """

    def handle(self, context, *args):
        return _NaN

class DivisionUndefined(InvalidOperation, ZeroDivisionError):
    """Undefined result of division.

    This occurs and signals invalid-operation if division by zero was
    attempted (during a divide-integer, divide, or remainder operation), and
    the dividend is also zero.  The result is [0,qNaN].
    """

    def handle(self, context, *args):
        return _NaN

class Inexact(DecimalException):
    """Had to round, losing information.

    This occurs and signals inexact whenever the result of an operation is
    not exact (that is, it needed to be rounded and any discarded digits
    were non-zero), or if an overflow or underflow condition occurs.  The
    result in all cases is unchanged.

    The inexact signal may be tested (or trapped) to determine if a given
    operation (or sequence of operations) was inexact.
    """

class InvalidContext(InvalidOperation):
    """Invalid context.  Unknown rounding, for example.

    This occurs and signals invalid-operation if an invalid context was
    detected during an operation.  This can occur if contexts are not checked
    on creation and either the precision exceeds the capability of the
    underlying concrete representation or an unknown or unsupported rounding
    was specified.  These aspects of the context need only be checked when
    the values are required to be used.  The result is [0,qNaN].
    """

    def handle(self, context, *args):
        return _NaN

class Rounded(DecimalException):
    """Number got rounded (not  necessarily changed during rounding).

    This occurs and signals rounded whenever the result of an operation is
    rounded (that is, some zero or non-zero digits were discarded from the
    coefficient), or if an overflow or underflow condition occurs.  The
    result in all cases is unchanged.

    The rounded signal may be tested (or trapped) to determine if a given
    operation (or sequence of operations) caused a loss of precision.
    """

class Subnormal(DecimalException):
    """Exponent < Emin before rounding.

    This occurs and signals subnormal whenever the result of a conversion or
    operation is subnormal (that is, its adjusted exponent is less than
    Emin, before any rounding).  The result in all cases is unchanged.

    The subnormal signal may be tested (or trapped) to determine if a given
    or operation (or sequence of operations) yielded a subnormal result.
    """

class Overflow(Inexact, Rounded):
    """Numerical overflow.

    This occurs and signals overflow if the adjusted exponent of a result
    (from a conversion or from an operation that is not an attempt to divide
    by zero), after rounding, would be greater than the largest value that
    can be handled by the implementation (the value Emax).

    The result depends on the rounding mode:

    For round-half-up and round-half-even (and for round-half-down and
    round-up, if implemented), the result of the operation is [sign,inf],
    where sign is the sign of the intermediate result.  For round-down, the
    result is the largest finite number that can be represented in the
    current precision, with the sign of the intermediate result.  For
    round-ceiling, the result is the same as for round-down if the sign of
    the intermediate result is 1, or is [0,inf] otherwise.  For round-floor,
    the result is the same as for round-down if the sign of the intermediate
    result is 0, or is [1,inf] otherwise.  In all cases, Inexact and Rounded
    will also be raised.
    """

    def handle(self, context, sign, *args):
        if context.rounding in (ROUND_HALF_UP, ROUND_HALF_EVEN,
                                ROUND_HALF_DOWN, ROUND_UP):
            return _SignedInfinity[sign]
        if sign == 0:
            if context.rounding == ROUND_CEILING:
                return _SignedInfinity[sign]
            return _dec_from_triple(sign, '9'*context.prec,
                            context.Emax-context.prec+1)
        if sign == 1:
            if context.rounding == ROUND_FLOOR:
                return _SignedInfinity[sign]
            return _dec_from_triple(sign, '9'*context.prec,
                             context.Emax-context.prec+1)


class Underflow(Inexact, Rounded, Subnormal):
    """Numerical underflow with result rounded to 0.

    This occurs and signals underflow if a result is inexact and the
    adjusted exponent of the result would be smaller (more negative) than
    the smallest value that can be handled by the implementation (the value
    Emin).  That is, the result is both inexact and subnormal.

    The result after an underflow will be a subnormal number rounded, if
    necessary, so that its exponent is not less than Etiny.  This may result
    in 0 with the sign of the intermediate result and an exponent of Etiny.

    In all cases, Inexact, Rounded, and Subnormal will also be raised.
    """

# List of public traps and flags
_signals = [Clamped, DivisionByZero, Inexact, Overflow, Rounded,
           Underflow, InvalidOperation, Subnormal]

# Map conditions (per the spec) to signals
_condition_map = {ConversionSyntax:InvalidOperation,
                  DivisionImpossible:InvalidOperation,
                  DivisionUndefined:InvalidOperation,
                  InvalidContext:InvalidOperation}

##### Context Functions ##################################################

# The getcontext() and setcontext() function manage access to a thread-local
# current context.  Py2.4 offers direct support for thread locals.  If that
# is not available, use threading.currentThread() which is slower but will
# work for older Pythons.  If threads are not part of the build, create a
# mock threading object with threading.local() returning the module namespace.

try:
    import threading
except ImportError:
    # Python was compiled without threads; create a mock object instead
    import sys
    class MockThreading(object):
        def local(self, sys=sys):
            return sys.modules[__name__]
    threading = MockThreading()
    del sys, MockThreading

try:
    threading.local

except AttributeError:

    # To fix reloading, force it to create a new context
    # Old contexts have different exceptions in their dicts, making problems.
    if hasattr(threading.currentThread(), '__decimal_context__'):
        del threading.currentThread().__decimal_context__

    def setcontext(context):
        """Set this thread's context to context."""
        if context in (DefaultContext, BasicContext, ExtendedContext):
            context = context.copy()
            context.clear_flags()
        threading.currentThread().__decimal_context__ = context

    def getcontext():
        """Returns this thread's context.

        If this thread does not yet have a context, returns
        a new context and sets this thread's context.
        New contexts are copies of DefaultContext.
        """
        try:
            return threading.currentThread().__decimal_context__
        except AttributeError:
            context = Context()
            threading.currentThread().__decimal_context__ = context
            return context

else:

    local = threading.local()
    if hasattr(local, '__decimal_context__'):
        del local.__decimal_context__

    def getcontext(_local=local):
        """Returns this thread's context.

        If this thread does not yet have a context, returns
        a new context and sets this thread's context.
        New contexts are copies of DefaultContext.
        """
        try:
            return _local.__decimal_context__
        except AttributeError:
            context = Context()
            _local.__decimal_context__ = context
            return context

    def setcontext(context, _local=local):
        """Set this thread's context to context."""
        if context in (DefaultContext, BasicContext, ExtendedContext):
            context = context.copy()
            context.clear_flags()
        _local.__decimal_context__ = context

    del threading, local        # Don't contaminate the namespace

def localcontext(ctx=None):
    """Return a context manager for a copy of the supplied context

    Uses a copy of the current context if no context is specified
    The returned context manager creates a local decimal context
    in a with statement:
        def sin(x):
             with localcontext() as ctx:
                 ctx.prec += 2
                 # Rest of sin calculation algorithm
                 # uses a precision 2 greater than normal
             return +s  # Convert result to normal precision

         def sin(x):
             with localcontext(ExtendedContext):
                 # Rest of sin calculation algorithm
                 # uses the Extended Context from the
                 # General Decimal Arithmetic Specification
             return +s  # Convert result to normal context

    >>> setcontext(DefaultContext)
    >>> print getcontext().prec
    28
    >>> with localcontext():
    ...     ctx = getcontext()
    ...     ctx.prec += 2
    ...     print ctx.prec
    ...
    30
    >>> with localcontext(ExtendedContext):
    ...     print getcontext().prec
    ...
    9
    >>> print getcontext().prec
    28
    """
    if ctx is None: ctx = getcontext()
    return _ContextManager(ctx)


##### Decimal class #######################################################

class Decimal(object):
    """Floating point class for decimal arithmetic."""

    __slots__ = ('_exp','_int','_sign', '_is_special')
    # Generally, the value of the Decimal instance is given by
    #  (-1)**_sign * _int * 10**_exp
    # Special values are signified by _is_special == True

    # We're immutable, so use __new__ not __init__
    def __new__(cls, value="0", context=None):
        """Create a decimal point instance.

        >>> Decimal('3.14')              # string input
        Decimal('3.14')
        >>> Decimal((0, (3, 1, 4), -2))  # tuple (sign, digit_tuple, exponent)
        Decimal('3.14')
        >>> Decimal(314)                 # int or long
        Decimal('314')
        >>> Decimal(Decimal(314))        # another decimal instance
        Decimal('314')
        >>> Decimal('  3.14  \\n')        # leading and trailing whitespace okay
        Decimal('3.14')
        """

        # Note that the coefficient, self._int, is actually stored as
        # a string rather than as a tuple of digits.  This speeds up
        # the "digits to integer" and "integer to digits" conversions
        # that are used in almost every arithmetic operation on
        # Decimals.  This is an internal detail: the as_tuple function
        # and the Decimal constructor still deal with tuples of
        # digits.

        self = object.__new__(cls)

        # From a string
        # REs insist on real strings, so we can too.
        if isinstance(value, basestring):
            m = _parser(value.strip())
            if m is None:
                if context is None:
                    context = getcontext()
                return context._raise_error(ConversionSyntax,
                                "Invalid literal for Decimal: %r" % value)

            if m.group('sign') == "-":
                self._sign = 1
            else:
                self._sign = 0
            intpart = m.group('int')
            if intpart is not None:
                # finite number
                fracpart = m.group('frac') or ''
                exp = int(m.group('exp') or '0')
                self._int = str(int(intpart+fracpart))
                self._exp = exp - len(fracpart)
                self._is_special = False
            else:
                diag = m.group('diag')
                if diag is not None:
                    # NaN
                    self._int = str(int(diag or '0')).lstrip('0')
                    if m.group('signal'):
                        self._exp = 'N'
                    else:
                        self._exp = 'n'
                else:
                    # infinity
                    self._int = '0'
                    self._exp = 'F'
                self._is_special = True
            return self

        # From an integer
        if isinstance(value, (int,long)):
            if value >= 0:
                self._sign = 0
            else:
                self._sign = 1
            self._exp = 0
            self._int = str(abs(value))
            self._is_special = False
            return self

        # From another decimal
        if isinstance(value, Decimal):
            self._exp  = value._exp
            self._sign = value._sign
            self._int  = value._int
            self._is_special  = value._is_special
            return self

        # From an internal working value
        if isinstance(value, _WorkRep):
            self._sign = value.sign
            self._int = str(value.int)
            self._exp = int(value.exp)
            self._is_special = False
            return self

        # tuple/list conversion (possibly from as_tuple())
        if isinstance(value, (list,tuple)):
            if len(value) != 3:
                raise ValueError('Invalid tuple size in creation of Decimal '
                                 'from list or tuple.  The list or tuple '
                                 'should have exactly three elements.')
            # process sign.  The isinstance test rejects floats
            if not (isinstance(value[0], (int, long)) and value[0] in (0,1)):
                raise ValueError("Invalid sign.  The first value in the tuple "
                                 "should be an integer; either 0 for a "
                                 "positive number or 1 for a negative number.")
            self._sign = value[0]
            if value[2] == 'F':
                # infinity: value[1] is ignored
                self._int = '0'
                self._exp = value[2]
                self._is_special = True
            else:
                # process and validate the digits in value[1]
                digits = []
                for digit in value[1]:
                    if isinstance(digit, (int, long)) and 0 <= digit <= 9:
                        # skip leading zeros
                        if digits or digit != 0:
                            digits.append(digit)
                    else:
                        raise ValueError("The second value in the tuple must "
                                         "be composed of integers in the range "
                                         "0 through 9.")
                if value[2] in ('n', 'N'):
                    # NaN: digits form the diagnostic
                    self._int = ''.join(map(str, digits))
                    self._exp = value[2]
                    self._is_special = True
                elif isinstance(value[2], (int, long)):
                    # finite number: digits give the coefficient
                    self._int = ''.join(map(str, digits or [0]))
                    self._exp = value[2]
                    self._is_special = False
                else:
                    raise ValueError("The third value in the tuple must "
                                     "be an integer, or one of the "
                                     "strings 'F', 'n', 'N'.")
            return self

        if isinstance(value, float):
            value = Decimal.from_float(value)
            self._exp  = value._exp
            self._sign = value._sign
            self._int  = value._int
            self._is_special  = value._is_special
            return self

        raise TypeError("Cannot convert %r to Decimal" % value)

    # @classmethod, but @decorator is not valid Python 2.3 syntax, so
    # don't use it (see notes on Py2.3 compatibility at top of file)
    def from_float(cls, f):
        """Converts a float to a decimal number, exactly.

        Note that Decimal.from_float(0.1) is not the same as Decimal('0.1').
        Since 0.1 is not exactly representable in binary floating point, the
        value is stored as the nearest representable value which is
        0x1.999999999999ap-4.  The exact equivalent of the value in decimal
        is 0.1000000000000000055511151231257827021181583404541015625.

        >>> Decimal.from_float(0.1)
        Decimal('0.1000000000000000055511151231257827021181583404541015625')
        >>> Decimal.from_float(float('nan'))
        Decimal('NaN')
        >>> Decimal.from_float(float('inf'))
        Decimal('Infinity')
        >>> Decimal.from_float(-float('inf'))
        Decimal('-Infinity')
        >>> Decimal.from_float(-0.0)
        Decimal('-0')

        """
        if isinstance(f, (int, long)):        # handle integer inputs
            return cls(f)
        if _math.isinf(f) or _math.isnan(f):  # raises TypeError if not a float
            return cls(repr(f))
        if _math.copysign(1.0, f) == 1.0:
            sign = 0
        else:
            sign = 1
        n, d = abs(f).as_integer_ratio()
        k = d.bit_length() - 1
        result = _dec_from_triple(sign, str(n*5**k), -k)
        if cls is Decimal:
            return result
        else:
            return cls(result)
    from_float = classmethod(from_float)

    def _isnan(self):
        """Returns whether the number is not actually one.

        0 if a number
        1 if NaN
        2 if sNaN
        """
        if self._is_special:
            exp = self._exp
            if exp == 'n':
                return 1
            elif exp == 'N':
                return 2
        return 0

    def _isinfinity(self):
        """Returns whether the number is infinite

        0 if finite or not a number
        1 if +INF
        -1 if -INF
        """
        if self._exp == 'F':
            if self._sign:
                return -1
            return 1
        return 0

    def _check_nans(self, other=None, context=None):
        """Returns whether the number is not actually one.

        if self, other are sNaN, signal
        if self, other are NaN return nan
        return 0

        Done before operations.
        """

        self_is_nan = self._isnan()
        if other is None:
            other_is_nan = False
        else:
            other_is_nan = other._isnan()

        if self_is_nan or other_is_nan:
            if context is None:
                context = getcontext()

            if self_is_nan == 2:
                return context._raise_error(InvalidOperation, 'sNaN',
                                        self)
            if other_is_nan == 2:
                return context._raise_error(InvalidOperation, 'sNaN',
                                        other)
            if self_is_nan:
                return self._fix_nan(context)

            return other._fix_nan(context)
        return 0

    def _compare_check_nans(self, other, context):
        """Version of _check_nans used for the signaling comparisons
        compare_signal, __le__, __lt__, __ge__, __gt__.

        Signal InvalidOperation if either self or other is a (quiet
        or signaling) NaN.  Signaling NaNs take precedence over quiet
        NaNs.

        Return 0 if neither operand is a NaN.

        """
        if context is None:
            context = getcontext()

        if self._is_special or other._is_special:
            if self.is_snan():
                return context._raise_error(InvalidOperation,
                                            'comparison involving sNaN',
                                            self)
            elif other.is_snan():
                return context._raise_error(InvalidOperation,
                                            'comparison involving sNaN',
                                            other)
            elif self.is_qnan():
                return context._raise_error(InvalidOperation,
                                            'comparison involving NaN',
                                            self)
            elif other.is_qnan():
                return context._raise_error(InvalidOperation,
                                            'comparison involving NaN',
                                            other)
        return 0

    def __nonzero__(self):
        """Return True if self is nonzero; otherwise return False.

        NaNs and infinities are considered nonzero.
        """
        return self._is_special or self._int != '0'

    def _cmp(self, other):
        """Compare the two non-NaN decimal instances self and other.

        Returns -1 if self < other, 0 if self == other and 1
        if self > other.  This routine is for internal use only."""

        if self._is_special or other._is_special:
            self_inf = self._isinfinity()
            other_inf = other._isinfinity()
            if self_inf == other_inf:
                return 0
            elif self_inf < other_inf:
                return -1
            else:
                return 1

        # check for zeros;  Decimal('0') == Decimal('-0')
        if not self:
            if not other:
                return 0
            else:
                return -((-1)**other._sign)
        if not other:
            return (-1)**self._sign

        # If different signs, neg one is less
        if other._sign < self._sign:
            return -1
        if self._sign < other._sign:
            return 1

        self_adjusted = self.adjusted()
        other_adjusted = other.adjusted()
        if self_adjusted == other_adjusted:
            self_padded = self._int + '0'*(self._exp - other._exp)
            other_padded = other._int + '0'*(other._exp - self._exp)
            if self_padded == other_padded:
                return 0
            elif self_padded < other_padded:
                return -(-1)**self._sign
            else:
                return (-1)**self._sign
        elif self_adjusted > other_adjusted:
            return (-1)**self._sign
        else: # self_adjusted < other_adjusted
            return -((-1)**self._sign)

    # Note: The Decimal standard doesn't cover rich comparisons for
    # Decimals.  In particular, the specification is silent on the
    # subject of what should happen for a comparison involving a NaN.
    # We take the following approach:
    #
    #   == comparisons involving a quiet NaN always return False
    #   != comparisons involving a quiet NaN always return True
    #   == or != comparisons involving a signaling NaN signal
    #      InvalidOperation, and return False or True as above if the
    #      InvalidOperation is not trapped.
    #   <, >, <= and >= comparisons involving a (quiet or signaling)
    #      NaN signal InvalidOperation, and return False if the
    #      InvalidOperation is not trapped.
    #
    # This behavior is designed to conform as closely as possible to
    # that specified by IEEE 754.

    def __eq__(self, other, context=None):
        other = _convert_other(other, allow_float=True)
        if other is NotImplemented:
            return other
        if self._check_nans(other, context):
            return False
        return self._cmp(other) == 0

    def __ne__(self, other, context=None):
        other = _convert_other(other, allow_float=True)
        if other is NotImplemented:
            return other
        if self._check_nans(other, context):
            return True
        return self._cmp(other) != 0

    def __lt__(self, other, context=None):
        other = _convert_other(other, allow_float=True)
        if other is NotImplemented:
            return other
        ans = self._compare_check_nans(other, context)
        if ans:
            return False
        return self._cmp(other) < 0

    def __le__(self, other, context=None):
        other = _convert_other(other, allow_float=True)
        if other is NotImplemented:
            return other
        ans = self._compare_check_nans(other, context)
        if ans:
            return False
        return self._cmp(other) <= 0

    def __gt__(self, other, context=None):
        other = _convert_other(other, allow_float=True)
        if other is NotImplemented:
            return other
        ans = self._compare_check_nans(other, context)
        if ans:
            return False
        return self._cmp(other) > 0

    def __ge__(self, other, context=None):
        other = _convert_other(other, allow_float=True)
        if other is NotImplemented:
            return other
        ans = self._compare_check_nans(other, context)
        if ans:
            return False
        return self._cmp(other) >= 0

    def compare(self, other, context=None):
        """Compares one to another.

        -1 => a < b
        0  => a = b
        1  => a > b
        NaN => one is NaN
        Like __cmp__, but returns Decimal instances.
        """
        other = _convert_other(other, raiseit=True)

        # Compare(NaN, NaN) = NaN
        if (self._is_special or other and other._is_special):
            ans = self._check_nans(other, context)
            if ans:
                return ans

        return Decimal(self._cmp(other))

    def __hash__(self):
        """x.__hash__() <==> hash(x)"""
        # Decimal integers must hash the same as the ints
        #
        # The hash of a nonspecial noninteger Decimal must depend only
        # on the value of that Decimal, and not on its representation.
        # For example: hash(Decimal('100E-1')) == hash(Decimal('10')).

        # Equality comparisons involving signaling nans can raise an
        # exception; since equality checks are implicitly and
        # unpredictably used when checking set and dict membership, we
        # prevent signaling nans from being used as set elements or
        # dict keys by making __hash__ raise an exception.
        if self._is_special:
            if self.is_snan():
                raise TypeError('Cannot hash a signaling NaN value.')
            elif self.is_nan():
                # 0 to match hash(float('nan'))
                return 0
            else:
                # values chosen to match hash(float('inf')) and
                # hash(float('-inf')).
                if self._sign:
                    return -271828
                else:
                    return 314159

        # In Python 2.7, we're allowing comparisons (but not
        # arithmetic operations) between floats and Decimals;  so if
        # a Decimal instance is exactly representable as a float then
        # its hash should match that of the float.
        self_as_float = float(self)
        if Decimal.from_float(self_as_float) == self:
            return hash(self_as_float)

        if self._isinteger():
            op = _WorkRep(self.to_integral_value())
            # to make computation feasible for Decimals with large
            # exponent, we use the fact that hash(n) == hash(m) for
            # any two nonzero integers n and m such that (i) n and m
            # have the same sign, and (ii) n is congruent to m modulo
            # 2**64-1.  So we can replace hash((-1)**s*c*10**e) with
            # hash((-1)**s*c*pow(10, e, 2**64-1).
            return hash((-1)**op.sign*op.int*pow(10, op.exp, 2**64-1))
        # The value of a nonzero nonspecial Decimal instance is
        # faithfully represented by the triple consisting of its sign,
        # its adjusted exponent, and its coefficient with trailing
        # zeros removed.
        return hash((self._sign,
                     self._exp+len(self._int),
                     self._int.rstrip('0')))

    def as_tuple(self):
        """Represents the number as a triple tuple.

        To show the internals exactly as they are.
        """
        return DecimalTuple(self._sign, tuple(map(int, self._int)), self._exp)

    def __repr__(self):
        """Represents the number as an instance of Decimal."""
        # Invariant:  eval(repr(d)) == d
        return "Decimal('%s')" % str(self)

    def __str__(self, eng=False, context=None):
        """Return string representation of the number in scientific notation.

        Captures all of the information in the underlying representation.
        """

        sign = ['', '-'][self._sign]
        if self._is_special:
            if self._exp == 'F':
                return sign + 'Infinity'
            elif self._exp == 'n':
                return sign + 'NaN' + self._int
            else: # self._exp == 'N'
                return sign + 'sNaN' + self._int

        # number of digits of self._int to left of decimal point
        leftdigits = self._exp + len(self._int)

        # dotplace is number of digits of self._int to the left of the
        # decimal point in the mantissa of the output string (that is,
        # after adjusting the exponent)
        if self._exp <= 0 and leftdigits > -6:
            # no exponent required
            dotplace = leftdigits
        elif not eng:
            # usual scientific notation: 1 digit on left of the point
            dotplace = 1
        elif self._int == '0':
            # engineering notation, zero
            dotplace = (leftdigits + 1) % 3 - 1
        else:
            # engineering notation, nonzero
            dotplace = (leftdigits - 1) % 3 + 1

        if dotplace <= 0:
            intpart = '0'
            fracpart = '.' + '0'*(-dotplace) + self._int
        elif dotplace >= len(self._int):
            intpart = self._int+'0'*(dotplace-len(self._int))
            fracpart = ''
        else:
            intpart = self._int[:dotplace]
            fracpart = '.' + self._int[dotplace:]
        if leftdigits == dotplace:
            exp = ''
        else:
            if context is None:
                context = getcontext()
            exp = ['e', 'E'][context.capitals] + "%+d" % (leftdigits-dotplace)

        return sign + intpart + fracpart + exp

    def to_eng_string(self, context=None):
        """Convert to engineering-type string.

        Engineering notation has an exponent which is a multiple of 3, so there
        are up to 3 digits left of the decimal place.

        Same rules for when in exponential and when as a value as in __str__.
        """
        return self.__str__(eng=True, context=context)

    def __neg__(self, context=None):
        """Returns a copy with the sign switched.

        Rounds, if it has reason.
        """
        if self._is_special:
            ans = self._check_nans(context=context)
            if ans:
                return ans

        if context is None:
            context = getcontext()

        if not self and context.rounding != ROUND_FLOOR:
            # -Decimal('0') is Decimal('0'), not Decimal('-0'), except
            # in ROUND_FLOOR rounding mode.
            ans = self.copy_abs()
        else:
            ans = self.copy_negate()

        return ans._fix(context)

    def __pos__(self, context=None):
        """Returns a copy, unless it is a sNaN.

        Rounds the number (if more than precision digits)
        """
        if self._is_special:
            ans = self._check_nans(context=context)
            if ans:
                return ans

        if context is None:
            context = getcontext()

        if not self and context.rounding != ROUND_FLOOR:
            # + (-0) = 0, except in ROUND_FLOOR rounding mode.
            ans = self.copy_abs()
        else:
            ans = Decimal(self)

        return ans._fix(context)

    def __abs__(self, round=True, context=None):
        """Returns the absolute value of self.

        If the keyword argument 'round' is false, do not round.  The
        expression self.__abs__(round=False) is equivalent to
        self.copy_abs().
        """
        if not round:
            return self.copy_abs()

        if self._is_special:
            ans = self._check_nans(context=context)
            if ans:
                return ans

        if self._sign:
            ans = self.__neg__(context=context)
        else:
            ans = self.__pos__(context=context)

        return ans

    def __add__(self, other, context=None):
        """Returns self + other.

        -INF + INF (or the reverse) cause InvalidOperation errors.
        """
        other = _convert_other(other)
        if other is NotImplemented:
            return other

        if context is None:
            context = getcontext()

        if self._is_special or other._is_special:
            ans = self._check_nans(other, context)
            if ans:
                return ans

            if self._isinfinity():
                # If both INF, same sign => same as both, opposite => error.
                if self._sign != other._sign and other._isinfinity():
                    return context._raise_error(InvalidOperation, '-INF + INF')
                return Decimal(self)
            if other._isinfinity():
                return Decimal(other)  # Can't both be infinity here

        exp = min(self._exp, other._exp)
        negativezero = 0
        if context.rounding == ROUND_FLOOR and self._sign != other._sign:
            # If the answer is 0, the sign should be negative, in this case.
            negativezero = 1

        if not self and not other:
            sign = min(self._sign, other._sign)
            if negativezero:
                sign = 1
            ans = _dec_from_triple(sign, '0', exp)
            ans = ans._fix(context)
            return ans
        if not self:
            exp = max(exp, other._exp - context.prec-1)
            ans = other._rescale(exp, context.rounding)
            ans = ans._fix(context)
            return ans
        if not other:
            exp = max(exp, self._exp - context.prec-1)
            ans = self._rescale(exp, context.rounding)
            ans = ans._fix(context)
            return ans

        op1 = _WorkRep(self)
        op2 = _WorkRep(other)
        op1, op2 = _normalize(op1, op2, context.prec)

        result = _WorkRep()
        if op1.sign != op2.sign:
            # Equal and opposite
            if op1.int == op2.int:
                ans = _dec_from_triple(negativezero, '0', exp)
                ans = ans._fix(context)
                return ans
            if op1.int < op2.int:
                op1, op2 = op2, op1
                # OK, now abs(op1) > abs(op2)
            if op1.sign == 1:
                result.sign = 1
                op1.sign, op2.sign = op2.sign, op1.sign
            else:
                result.sign = 0
                # So we know the sign, and op1 > 0.
        elif op1.sign == 1:
            result.sign = 1
            op1.sign, op2.sign = (0, 0)
        else:
            result.sign = 0
        # Now, op1 > abs(op2) > 0

        if op2.sign == 0:
            result.int = op1.int + op2.int
        else:
            result.int = op1.int - op2.int

        result.exp = op1.exp
        ans = Decimal(result)
        ans = ans._fix(context)
        return ans

    __radd__ = __add__

    def __sub__(self, other, context=None):
        """Return self - other"""
        other = _convert_other(other)
        if other is NotImplemented:
            return other

        if self._is_special or other._is_special:
            ans = self._check_nans(other, context=context)
            if ans:
                return ans

        # self - other is computed as self + other.copy_negate()
        return self.__add__(other.copy_negate(), context=context)

    def __rsub__(self, other, context=None):
        """Return other - self"""
        other = _convert_other(other)
        if other is NotImplemented:
            return other

        return other.__sub__(self, context=context)

    def __mul__(self, other, context=None):
        """Return self * other.

        (+-) INF * 0 (or its reverse) raise InvalidOperation.
        """
        other = _convert_other(other)
        if other is NotImplemented:
            return other

        if context is None:
            context = getcontext()

        resultsign = self._sign ^ other._sign

        if self._is_special or other._is_special:
            ans = self._check_nans(other, context)
            if ans:
                return ans

            if self._isinfinity():
                if not other:
                    return context._raise_error(InvalidOperation, '(+-)INF * 0')
                return _SignedInfinity[resultsign]

            if other._isinfinity():
                if not self:
                    return context._raise_error(InvalidOperation, '0 * (+-)INF')
                return _SignedInfinity[resultsign]

        resultexp = self._exp + other._exp

        # Special case for multiplying by zero
        if not self or not other:
            ans = _dec_from_triple(resultsign, '0', resultexp)
            # Fixing in case the exponent is out of bounds
            ans = ans._fix(context)
            return ans

        # Special case for multiplying by power of 10
        if self._int == '1':
            ans = _dec_from_triple(resultsign, other._int, resultexp)
            ans = ans._fix(context)
            return ans
        if other._int == '1':
            ans = _dec_from_triple(resultsign, self._int, resultexp)
            ans = ans._fix(context)
            return ans

        op1 = _WorkRep(self)
        op2 = _WorkRep(other)

        ans = _dec_from_triple(resultsign, str(op1.int * op2.int), resultexp)
        ans = ans._fix(context)

        return ans
    __rmul__ = __mul__

    def __truediv__(self, other, context=None):
        """Return self / other."""
        other = _convert_other(other)
        if other is NotImplemented:
            return NotImplemented

        if context is None:
            context = getcontext()

        sign = self._sign ^ other._sign

        if self._is_special or other._is_special:
            ans = self._check_nans(other, context)
            if ans:
                return ans

            if self._isinfinity() and other._isinfinity():
                return context._raise_error(InvalidOperation, '(+-)INF/(+-)INF')

            if self._isinfinity():
                return _SignedInfinity[sign]

            if other._isinfinity():
                context._raise_error(Clamped, 'Division by infinity')
                return _dec_from_triple(sign, '0', context.Etiny())

        # Special cases for zeroes
        if not other:
            if not self:
                return context._raise_error(DivisionUndefined, '0 / 0')
            return context._raise_error(DivisionByZero, 'x / 0', sign)

        if not self:
            exp = self._exp - other._exp
            coeff = 0
        else:
            # OK, so neither = 0, INF or NaN
            shift = len(other._int) - len(self._int) + context.prec + 1
            exp = self._exp - other._exp - shift
            op1 = _WorkRep(self)
            op2 = _WorkRep(other)
            if shift >= 0:
                coeff, remainder = divmod(op1.int * 10**shift, op2.int)
            else:
                coeff, remainder = divmod(op1.int, op2.int * 10**-shift)
            if remainder:
                # result is not exact; adjust to ensure correct rounding
                if coeff % 5 == 0:
                    coeff += 1
            else:
                # result is exact; get as close to ideal exponent as possible
                ideal_exp = self._exp - other._exp
                while exp < ideal_exp and coeff % 10 == 0:
                    coeff //= 10
                    exp += 1

        ans = _dec_from_triple(sign, str(coeff), exp)
        return ans._fix(context)

    def _divide(self, other, context):
        """Return (self // other, self % other), to context.prec precision.

        Assumes that neither self nor other is a NaN, that self is not
        infinite and that other is nonzero.
        """
        sign = self._sign ^ other._sign
        if other._isinfinity():
            ideal_exp = self._exp
        else:
            ideal_exp = min(self._exp, other._exp)

        expdiff = self.adjusted() - other.adjusted()
        if not self or other._isinfinity() or expdiff <= -2:
            return (_dec_from_triple(sign, '0', 0),
                    self._rescale(ideal_exp, context.rounding))
        if expdiff <= context.prec:
            op1 = _WorkRep(self)
            op2 = _WorkRep(other)
            if op1.exp >= op2.exp:
                op1.int *= 10**(op1.exp - op2.exp)
            else:
                op2.int *= 10**(op2.exp - op1.exp)
            q, r = divmod(op1.int, op2.int)
            if q < 10**context.prec:
                return (_dec_from_triple(sign, str(q), 0),
                        _dec_from_triple(self._sign, str(r), ideal_exp))

        # Here the quotient is too large to be representable
        ans = context._raise_error(DivisionImpossible,
                                   'quotient too large in //, % or divmod')
        return ans, ans

    def __rtruediv__(self, other, context=None):
        """Swaps self/other and returns __truediv__."""
        other = _convert_other(other)
        if other is NotImplemented:
            return other
        return other.__truediv__(self, context=context)

    __div__ = __truediv__
    __rdiv__ = __rtruediv__

    def __divmod__(self, other, context=None):
        """
        Return (self // other, self % other)
        """
        other = _convert_other(other)
        if other is NotImplemented:
            return other

        if context is None:
            context = getcontext()

        ans = self._check_nans(other, context)
        if ans:
            return (ans, ans)

        sign = self._sign ^ other._sign
        if self._isinfinity():
            if other._isinfinity():
                ans = context._raise_error(InvalidOperation, 'divmod(INF, INF)')
                return ans, ans
            else:
                return (_SignedInfinity[sign],
                        context._raise_error(InvalidOperation, 'INF % x'))

        if not other:
            if not self:
                ans = context._raise_error(DivisionUndefined, 'divmod(0, 0)')
                return ans, ans
            else:
                return (context._raise_error(DivisionByZero, 'x // 0', sign),
                        context._raise_error(InvalidOperation, 'x % 0'))

        quotient, remainder = self._divide(other, context)
        remainder = remainder._fix(context)
        return quotient, remainder

    def __rdivmod__(self, other, context=None):
        """Swaps self/other and returns __divmod__."""
        other = _convert_other(other)
        if other is NotImplemented:
            return other
        return other.__divmod__(self, context=context)

    def __mod__(self, other, context=None):
        """
        self % other
        """
        other = _convert_other(other)
        if other is NotImplemented:
            return other

        if context is None:
            context = getcontext()

        ans = self._check_nans(other, context)
        if ans:
            return ans

        if self._isinfinity():
            return context._raise_error(InvalidOperation, 'INF % x')
        elif not other:
            if self:
                return context._raise_error(InvalidOperation, 'x % 0')
            else:
                return context._raise_error(DivisionUndefined, '0 % 0')

        remainder = self._divide(other, context)[1]
        remainder = remainder._fix(context)
        return remainder

    def __rmod__(self, other, context=None):
        """Swaps self/other and returns __mod__."""
        other = _convert_other(other)
        if other is NotImplemented:
            return other
        return other.__mod__(self, context=context)

    def remainder_near(self, other, context=None):
        """
        Remainder nearest to 0-  abs(remainder-near) <= other/2
        """
        if context is None:
            context = getcontext()

        other = _convert_other(other, raiseit=True)

        ans = self._check_nans(other, context)
        if ans:
            return ans

        # self == +/-infinity -> InvalidOperation
        if self._isinfinity():
            return context._raise_error(InvalidOperation,
                                        'remainder_near(infinity, x)')

        # other == 0 -> either InvalidOperation or DivisionUndefined
        if not other:
            if self:
                return context._raise_error(InvalidOperation,
                                            'remainder_near(x, 0)')
            else:
                return context._raise_error(DivisionUndefined,
                                            'remainder_near(0, 0)')

        # other = +/-infinity -> remainder = self
        if other._isinfinity():
            ans = Decimal(self)
            return ans._fix(context)

        # self = 0 -> remainder = self, with ideal exponent
        ideal_exponent = min(self._exp, other._exp)
        if not self:
            ans = _dec_from_triple(self._sign, '0', ideal_exponent)
            return ans._fix(context)

        # catch most cases of large or small quotient
        expdiff = self.adjusted() - other.adjusted()
        if expdiff >= context.prec + 1:
            # expdiff >= prec+1 => abs(self/other) > 10**prec
            return context._raise_error(DivisionImpossible)
        if expdiff <= -2:
            # expdiff <= -2 => abs(self/other) < 0.1
            ans = self._rescale(ideal_exponent, context.rounding)
            return ans._fix(context)

        # adjust both arguments to have the same exponent, then divide
        op1 = _WorkRep(self)
        op2 = _WorkRep(other)
        if op1.exp >= op2.exp:
            op1.int *= 10**(op1.exp - op2.exp)
        else:
            op2.int *= 10**(op2.exp - op1.exp)
        q, r = divmod(op1.int, op2.int)
        # remainder is r*10**ideal_exponent; other is +/-op2.int *
        # 10**ideal_exponent.   Apply correction to ensure that
        # abs(remainder) <= abs(other)/2
        if 2*r + (q&1) > op2.int:
            r -= op2.int
            q += 1

        if q >= 10**context.prec:
            return context._raise_error(DivisionImpossible)

        # result has same sign as self unless r is negative
        sign = self._sign
        if r < 0:
            sign = 1-sign
            r = -r

        ans = _dec_from_triple(sign, str(r), ideal_exponent)
        return ans._fix(context)

    def __floordiv__(self, other, context=None):
        """self // other"""
        other = _convert_other(other)
        if other is NotImplemented:
            return other

        if context is None:
            context = getcontext()

        ans = self._check_nans(other, context)
        if ans:
            return ans

        if self._isinfinity():
            if other._isinfinity():
                return context._raise_error(InvalidOperation, 'INF // INF')
            else:
                return _SignedInfinity[self._sign ^ other._sign]

        if not other:
            if self:
                return context._raise_error(DivisionByZero, 'x // 0',
                                            self._sign ^ other._sign)
            else:
                return context._raise_error(DivisionUndefined, '0 // 0')

        return self._divide(other, context)[0]

    def __rfloordiv__(self, other, context=None):
        """Swaps self/other and returns __floordiv__."""
        other = _convert_other(other)
        if other is NotImplemented:
            return other
        return other.__floordiv__(self, context=context)

    def __float__(self):
        """Float representation."""
        if self._isnan():
            if self.is_snan():
                raise ValueError("Cannot convert signaling NaN to float")
            s = "-nan" if self._sign else "nan"
        else:
            s = str(self)
        return float(s)

    def __int__(self):
        """Converts self to an int, truncating if necessary."""
        if self._is_special:
            if self._isnan():
                raise ValueError("Cannot convert NaN to integer")
            elif self._isinfinity():
                raise OverflowError("Cannot convert infinity to integer")
        s = (-1)**self._sign
        if self._exp >= 0:
            return s*int(self._int)*10**self._exp
        else:
            return s*int(self._int[:self._exp] or '0')

    __trunc__ = __int__

    def real(self):
        return self
    real = property(real)

    def imag(self):
        return Decimal(0)
    imag = property(imag)

    def conjugate(self):
        return self

    def __complex__(self):
        return complex(float(self))

    def __long__(self):
        """Converts to a long.

        Equivalent to long(int(self))
        """
        return long(self.__int__())

    def _fix_nan(self, context):
        """Decapitate the payload of a NaN to fit the context"""
        payload = self._int

        # maximum length of payload is precision if _clamp=0,
        # precision-1 if _clamp=1.
        max_payload_len = context.prec - context._clamp
        if len(payload) > max_payload_len:
            payload = payload[len(payload)-max_payload_len:].lstrip('0')
            return _dec_from_triple(self._sign, payload, self._exp, True)
        return Decimal(self)

    def _fix(self, context):
        """Round if it is necessary to keep self within prec precision.

        Rounds and fixes the exponent.  Does not raise on a sNaN.

        Arguments:
        self - Decimal instance
        context - context used.
        """

        if self._is_special:
            if self._isnan():
                # decapitate payload if necessary
                return self._fix_nan(context)
            else:
                # self is +/-Infinity; return unaltered
                return Decimal(self)

        # if self is zero then exponent should be between Etiny and
        # Emax if _clamp==0, and between Etiny and Etop if _clamp==1.
        Etiny = context.Etiny()
        Etop = context.Etop()
        if not self:
            exp_max = [context.Emax, Etop][context._clamp]
            new_exp = min(max(self._exp, Etiny), exp_max)
            if new_exp != self._exp:
                context._raise_error(Clamped)
                return _dec_from_triple(self._sign, '0', new_exp)
            else:
                return Decimal(self)

        # exp_min is the smallest allowable exponent of the result,
        # equal to max(self.adjusted()-context.prec+1, Etiny)
        exp_min = len(self._int) + self._exp - context.prec
        if exp_min > Etop:
            # overflow: exp_min > Etop iff self.adjusted() > Emax
            ans = context._raise_error(Overflow, 'above Emax', self._sign)
            context._raise_error(Inexact)
            context._raise_error(Rounded)
            return ans

        self_is_subnormal = exp_min < Etiny
        if self_is_subnormal:
            exp_min = Etiny

        # round if self has too many digits
        if self._exp < exp_min:
            digits = len(self._int) + self._exp - exp_min
            if digits < 0:
                self = _dec_from_triple(self._sign, '1', exp_min-1)
                digits = 0
            rounding_method = self._pick_rounding_function[context.rounding]
            changed = rounding_method(self, digits)
            coeff = self._int[:digits] or '0'
            if changed > 0:
                coeff = str(int(coeff)+1)
                if len(coeff) > context.prec:
                    coeff = coeff[:-1]
                    exp_min += 1

            # check whether the rounding pushed the exponent out of range
            if exp_min > Etop:
                ans = context._raise_error(Overflow, 'above Emax', self._sign)
            else:
                ans = _dec_from_triple(self._sign, coeff, exp_min)

            # raise the appropriate signals, taking care to respect
            # the precedence described in the specification
            if changed and self_is_subnormal:
                context._raise_error(Underflow)
            if self_is_subnormal:
                context._raise_error(Subnormal)
            if changed:
                context._raise_error(Inexact)
            context._raise_error(Rounded)
            if not ans:
                # raise Clamped on underflow to 0
                context._raise_error(Clamped)
            return ans

        if self_is_subnormal:
            context._raise_error(Subnormal)

        # fold down if _clamp == 1 and self has too few digits
        if context._clamp == 1 and self._exp > Etop:
            context._raise_error(Clamped)
            self_padded = self._int + '0'*(self._exp - Etop)
            return _dec_from_triple(self._sign, self_padded, Etop)

        # here self was representable to begin with; return unchanged
        return Decimal(self)

    # for each of the rounding functions below:
    #   self is a finite, nonzero Decimal
    #   prec is an integer satisfying 0 <= prec < len(self._int)
    #
    # each function returns either -1, 0, or 1, as follows:
    #   1 indicates that self should be rounded up (away from zero)
    #   0 indicates that self should be truncated, and that all the
    #     digits to be truncated are zeros (so the value is unchanged)
    #  -1 indicates that there are nonzero digits to be truncated

    def _round_down(self, prec):
        """Also known as round-towards-0, truncate."""
        if _all_zeros(self._int, prec):
            return 0
        else:
            return -1

    def _round_up(self, prec):
        """Rounds away from 0."""
        return -self._round_down(prec)

    def _round_half_up(self, prec):
        """Rounds 5 up (away from 0)"""
        if self._int[prec] in '56789':
            return 1
        elif _all_zeros(self._int, prec):
            return 0
        else:
            return -1

    def _round_half_down(self, prec):
        """Round 5 down"""
        if _exact_half(self._int, prec):
            return -1
        else:
            return self._round_half_up(prec)

    def _round_half_even(self, prec):
        """Round 5 to even, rest to nearest."""
        if _exact_half(self._int, prec) and \
                (prec == 0 or self._int[prec-1] in '02468'):
            return -1
        else:
            return self._round_half_up(prec)

    def _round_ceiling(self, prec):
        """Rounds up (not away from 0 if negative.)"""
        if self._sign:
            return self._round_down(prec)
        else:
            return -self._round_down(prec)

    def _round_floor(self, prec):
        """Rounds down (not towards 0 if negative)"""
        if not self._sign:
            return self._round_down(prec)
        else:
            return -self._round_down(prec)

    def _round_05up(self, prec):
        """Round down unless digit prec-1 is 0 or 5."""
        if prec and self._int[prec-1] not in '05':
            return self._round_down(prec)
        else:
            return -self._round_down(prec)

    _pick_rounding_function = dict(
        ROUND_DOWN = _round_down,
        ROUND_UP = _round_up,
        ROUND_HALF_UP = _round_half_up,
        ROUND_HALF_DOWN = _round_half_down,
        ROUND_HALF_EVEN = _round_half_even,
        ROUND_CEILING = _round_ceiling,
        ROUND_FLOOR = _round_floor,
        ROUND_05UP = _round_05up,
    )

    def fma(self, other, third, context=None):
        """Fused multiply-add.

        Returns self*other+third with no rounding of the intermediate
        product self*other.

        self and other are multiplied together, with no rounding of
        the result.  The third operand is then added to the result,
        and a single final rounding is performed.
        """

        other = _convert_other(other, raiseit=True)

        # compute product; raise InvalidOperation if either operand is
        # a signaling NaN or if the product is zero times infinity.
        if self._is_special or other._is_special:
            if context is None:
                context = getcontext()
            if self._exp == 'N':
                return context._raise_error(InvalidOperation, 'sNaN', self)
            if other._exp == 'N':
                return context._raise_error(InvalidOperation, 'sNaN', other)
            if self._exp == 'n':
                product = self
            elif other._exp == 'n':
                product = other
            elif self._exp == 'F':
                if not other:
                    return context._raise_error(InvalidOperation,
                                                'INF * 0 in fma')
                product = _SignedInfinity[self._sign ^ other._sign]
            elif other._exp == 'F':
                if not self:
                    return context._raise_error(InvalidOperation,
                                                '0 * INF in fma')
                product = _SignedInfinity[self._sign ^ other._sign]
        else:
            product = _dec_from_triple(self._sign ^ other._sign,
                                       str(int(self._int) * int(other._int)),
                                       self._exp + other._exp)

        third = _convert_other(third, raiseit=True)
        return product.__add__(third, context)

    def _power_modulo(self, other, modulo, context=None):
        """Three argument version of __pow__"""

        # if can't convert other and modulo to Decimal, raise
        # TypeError; there's no point returning NotImplemented (no
        # equivalent of __rpow__ for three argument pow)
        other = _convert_other(other, raiseit=True)
        modulo = _convert_other(modulo, raiseit=True)

        if context is None:
            context = getcontext()

        # deal with NaNs: if there are any sNaNs then first one wins,
        # (i.e. behaviour for NaNs is identical to that of fma)
        self_is_nan = self._isnan()
        other_is_nan = other._isnan()
        modulo_is_nan = modulo._isnan()
        if self_is_nan or other_is_nan or modulo_is_nan:
            if self_is_nan == 2:
                return context._raise_error(InvalidOperation, 'sNaN',
                                        self)
            if other_is_nan == 2:
                return context._raise_error(InvalidOperation, 'sNaN',
                                        other)
            if modulo_is_nan == 2:
                return context._raise_error(InvalidOperation, 'sNaN',
                                        modulo)
            if self_is_nan:
                return self._fix_nan(context)
            if other_is_nan:
                return other._fix_nan(context)
            return modulo._fix_nan(context)

        # check inputs: we apply same restrictions as Python's pow()
        if not (self._isinteger() and
                other._isinteger() and
                modulo._isinteger()):
            return context._raise_error(InvalidOperation,
                                        'pow() 3rd argument not allowed '
                                        'unless all arguments are integers')
        if other < 0:
            return context._raise_error(InvalidOperation,
                                        'pow() 2nd argument cannot be '
                                        'negative when 3rd argument specified')
        if not modulo:
            return context._raise_error(InvalidOperation,
                                        'pow() 3rd argument cannot be 0')

        # additional restriction for decimal: the modulus must be less
        # than 10**prec in absolute value
        if modulo.adjusted() >= context.prec:
            return context._raise_error(InvalidOperation,
                                        'insufficient precision: pow() 3rd '
                                        'argument must not have more than '
                                        'precision digits')

        # define 0**0 == NaN, for consistency with two-argument pow
        # (even though it hurts!)
        if not other and not self:
            return context._raise_error(InvalidOperation,
                                        'at least one of pow() 1st argument '
                                        'and 2nd argument must be nonzero ;'
                                        '0**0 is not defined')

        # compute sign of result
        if other._iseven():
            sign = 0
        else:
            sign = self._sign

        # convert modulo to a Python integer, and self and other to
        # Decimal integers (i.e. force their exponents to be >= 0)
        modulo = abs(int(modulo))
        base = _WorkRep(self.to_integral_value())
        exponent = _WorkRep(other.to_integral_value())

        # compute result using integer pow()
        base = (base.int % modulo * pow(10, base.exp, modulo)) % modulo
        for i in xrange(exponent.exp):
            base = pow(base, 10, modulo)
        base = pow(base, exponent.int, modulo)

        return _dec_from_triple(sign, str(base), 0)

    def _power_exact(self, other, p):
        """Attempt to compute self**other exactly.

        Given Decimals self and other and an integer p, attempt to
        compute an exact result for the power self**other, with p
        digits of precision.  Return None if self**other is not
        exactly representable in p digits.

        Assumes that elimination of special cases has already been
        performed: self and other must both be nonspecial; self must
        be positive and not numerically equal to 1; other must be
        nonzero.  For efficiency, other._exp should not be too large,
        so that 10**abs(other._exp) is a feasible calculation."""

        # In the comments below, we write x for the value of self and y for the
        # value of other.  Write x = xc*10**xe and abs(y) = yc*10**ye, with xc
        # and yc positive integers not divisible by 10.

        # The main purpose of this method is to identify the *failure*
        # of x**y to be exactly representable with as little effort as
        # possible.  So we look for cheap and easy tests that
        # eliminate the possibility of x**y being exact.  Only if all
        # these tests are passed do we go on to actually compute x**y.

        # Here's the main idea.  Express y as a rational number m/n, with m and
        # n relatively prime and n>0.  Then for x**y to be exactly
        # representable (at *any* precision), xc must be the nth power of a
        # positive integer and xe must be divisible by n.  If y is negative
        # then additionally xc must be a power of either 2 or 5, hence a power
        # of 2**n or 5**n.
        #
        # There's a limit to how small |y| can be: if y=m/n as above
        # then:
        #
        #  (1) if xc != 1 then for the result to be representable we
        #      need xc**(1/n) >= 2, and hence also xc**|y| >= 2.  So
        #      if |y| <= 1/nbits(xc) then xc < 2**nbits(xc) <=
        #      2**(1/|y|), hence xc**|y| < 2 and the result is not
        #      representable.
        #
        #  (2) if xe != 0, |xe|*(1/n) >= 1, so |xe|*|y| >= 1.  Hence if
        #      |y| < 1/|xe| then the result is not representable.
        #
        # Note that since x is not equal to 1, at least one of (1) and
        # (2) must apply.  Now |y| < 1/nbits(xc) iff |yc|*nbits(xc) <
        # 10**-ye iff len(str(|yc|*nbits(xc)) <= -ye.
        #
        # There's also a limit to how large y can be, at least if it's
        # positive: the normalized result will have coefficient xc**y,
        # so if it's representable then xc**y < 10**p, and y <
        # p/log10(xc).  Hence if y*log10(xc) >= p then the result is
        # not exactly representable.

        # if len(str(abs(yc*xe)) <= -ye then abs(yc*xe) < 10**-ye,
        # so |y| < 1/xe and the result is not representable.
        # Similarly, len(str(abs(yc)*xc_bits)) <= -ye implies |y|
        # < 1/nbits(xc).

        x = _WorkRep(self)
        xc, xe = x.int, x.exp
        while xc % 10 == 0:
            xc //= 10
            xe += 1

        y = _WorkRep(other)
        yc, ye = y.int, y.exp
        while yc % 10 == 0:
            yc //= 10
            ye += 1

        # case where xc == 1: result is 10**(xe*y), with xe*y
        # required to be an integer
        if xc == 1:
            xe *= yc
            # result is now 10**(xe * 10**ye);  xe * 10**ye must be integral
            while xe % 10 == 0:
                xe //= 10
                ye += 1
            if ye < 0:
                return None
            exponent = xe * 10**ye
            if y.sign == 1:
                exponent = -exponent
            # if other is a nonnegative integer, use ideal exponent
            if other._isinteger() and other._sign == 0:
                ideal_exponent = self._exp*int(other)
                zeros = min(exponent-ideal_exponent, p-1)
            else:
                zeros = 0
            return _dec_from_triple(0, '1' + '0'*zeros, exponent-zeros)

        # case where y is negative: xc must be either a power
        # of 2 or a power of 5.
        if y.sign == 1:
            last_digit = xc % 10
            if last_digit in (2,4,6,8):
                # quick test for power of 2
                if xc & -xc != xc:
                    return None
                # now xc is a power of 2; e is its exponent
                e = _nbits(xc)-1

                # We now have:
                #
                #   x = 2**e * 10**xe, e > 0, and y < 0.
                #
                # The exact result is:
                #
                #   x**y = 5**(-e*y) * 10**(e*y + xe*y)
                #
                # provided that both e*y and xe*y are integers.  Note that if
                # 5**(-e*y) >= 10**p, then the result can't be expressed
                # exactly with p digits of precision.
                #
                # Using the above, we can guard against large values of ye.
                # 93/65 is an upper bound for log(10)/log(5), so if
                #
                #   ye >= len(str(93*p//65))
                #
                # then
                #
                #   -e*y >= -y >= 10**ye > 93*p/65 > p*log(10)/log(5),
                #
                # so 5**(-e*y) >= 10**p, and the coefficient of the result
                # can't be expressed in p digits.

                # emax >= largest e such that 5**e < 10**p.
                emax = p*93//65
                if ye >= len(str(emax)):
                    return None

                # Find -e*y and -xe*y; both must be integers
                e = _decimal_lshift_exact(e * yc, ye)
                xe = _decimal_lshift_exact(xe * yc, ye)
                if e is None or xe is None:
                    return None

                if e > emax:
                    return None
                xc = 5**e

            elif last_digit == 5:
                # e >= log_5(xc) if xc is a power of 5; we have
                # equality all the way up to xc=5**2658
                e = _nbits(xc)*28//65
                xc, remainder = divmod(5**e, xc)
                if remainder:
                    return None
                while xc % 5 == 0:
                    xc //= 5
                    e -= 1

                # Guard against large values of ye, using the same logic as in
                # the 'xc is a power of 2' branch.  10/3 is an upper bound for
                # log(10)/log(2).
                emax = p*10//3
                if ye >= len(str(emax)):
                    return None

                e = _decimal_lshift_exact(e * yc, ye)
                xe = _decimal_lshift_exact(xe * yc, ye)
                if e is None or xe is None:
                    return None

                if e > emax:
                    return None
                xc = 2**e
            else:
                return None

            if xc >= 10**p:
                return None
            xe = -e-xe
            return _dec_from_triple(0, str(xc), xe)

        # now y is positive; find m and n such that y = m/n
        if ye >= 0:
            m, n = yc*10**ye, 1
        else:
            if xe != 0 and len(str(abs(yc*xe))) <= -ye:
                return None
            xc_bits = _nbits(xc)
            if xc != 1 and len(str(abs(yc)*xc_bits)) <= -ye:
                return None
            m, n = yc, 10**(-ye)
            while m % 2 == n % 2 == 0:
                m //= 2
                n //= 2
            while m % 5 == n % 5 == 0:
                m //= 5
                n //= 5

        # compute nth root of xc*10**xe
        if n > 1:
            # if 1 < xc < 2**n then xc isn't an nth power
            if xc != 1 and xc_bits <= n:
                return None

            xe, rem = divmod(xe, n)
            if rem != 0:
                return None

            # compute nth root of xc using Newton's method
            a = 1L << -(-_nbits(xc)//n) # initial estimate
            while True:
                q, r = divmod(xc, a**(n-1))
                if a <= q:
                    break
                else:
                    a = (a*(n-1) + q)//n
            if not (a == q and r == 0):
                return None
            xc = a

        # now xc*10**xe is the nth root of the original xc*10**xe
        # compute mth power of xc*10**xe

        # if m > p*100//_log10_lb(xc) then m > p/log10(xc), hence xc**m >
        # 10**p and the result is not representable.
        if xc > 1 and m > p*100//_log10_lb(xc):
            return None
        xc = xc**m
        xe *= m
        if xc > 10**p:
            return None

        # by this point the result *is* exactly representable
        # adjust the exponent to get as close as possible to the ideal
        # exponent, if necessary
        str_xc = str(xc)
        if other._isinteger() and other._sign == 0:
            ideal_exponent = self._exp*int(other)
            zeros = min(xe-ideal_exponent, p-len(str_xc))
        else:
            zeros = 0
        return _dec_from_triple(0, str_xc+'0'*zeros, xe-zeros)

    def __pow__(self, other, modulo=None, context=None):
        """Return self ** other [ % modulo].

        With two arguments, compute self**other.

        With three arguments, compute (self**other) % modulo.  For the
        three argument form, the following restrictions on the
        arguments hold:

         - all three arguments must be integral
         - other must be nonnegative
         - either self or other (or both) must be nonzero
         - modulo must be nonzero and must have at most p digits,
           where p is the context precision.

        If any of these restrictions is violated the InvalidOperation
        flag is raised.

        The result of pow(self, other, modulo) is identical to the
        result that would be obtained by computing (self**other) %
        modulo with unbounded precision, but is computed more
        efficiently.  It is always exact.
        """

        if modulo is not None:
            return self._power_modulo(other, modulo, context)

        other = _convert_other(other)
        if other is NotImplemented:
            return other

        if context is None:
            context = getcontext()

        # either argument is a NaN => result is NaN
        ans = self._check_nans(other, context)
        if ans:
            return ans

        # 0**0 = NaN (!), x**0 = 1 for nonzero x (including +/-Infinity)
        if not other:
            if not self:
                return context._raise_error(InvalidOperation, '0 ** 0')
            else:
                return _One

        # result has sign 1 iff self._sign is 1 and other is an odd integer
        result_sign = 0
        if self._sign == 1:
            if other._isinteger():
                if not other._iseven():
                    result_sign = 1
            else:
                # -ve**noninteger = NaN
                # (-0)**noninteger = 0**noninteger
                if self:
                    return context._raise_error(InvalidOperation,
                        'x ** y with x negative and y not an integer')
            # negate self, without doing any unwanted rounding
            self = self.copy_negate()

        # 0**(+ve or Inf)= 0; 0**(-ve or -Inf) = Infinity
        if not self:
            if other._sign == 0:
                return _dec_from_triple(result_sign, '0', 0)
            else:
                return _SignedInfinity[result_sign]

        # Inf**(+ve or Inf) = Inf; Inf**(-ve or -Inf) = 0
        if self._isinfinity():
            if other._sign == 0:
                return _SignedInfinity[result_sign]
            else:
                return _dec_from_triple(result_sign, '0', 0)

        # 1**other = 1, but the choice of exponent and the flags
        # depend on the exponent of self, and on whether other is a
        # positive integer, a negative integer, or neither
        if self == _One:
            if other._isinteger():
                # exp = max(self._exp*max(int(other), 0),
                # 1-context.prec) but evaluating int(other) directly
                # is dangerous until we know other is small (other
                # could be 1e999999999)
                if other._sign == 1:
                    multiplier = 0
                elif other > context.prec:
                    multiplier = context.prec
                else:
                    multiplier = int(other)

                exp = self._exp * multiplier
                if exp < 1-context.prec:
                    exp = 1-context.prec
                    context._raise_error(Rounded)
            else:
                context._raise_error(Inexact)
                context._raise_error(Rounded)
                exp = 1-context.prec

            return _dec_from_triple(result_sign, '1'+'0'*-exp, exp)

        # compute adjusted exponent of self
        self_adj = self.adjusted()

        # self ** infinity is infinity if self > 1, 0 if self < 1
        # self ** -infinity is infinity if self < 1, 0 if self > 1
        if other._isinfinity():
            if (other._sign == 0) == (self_adj < 0):
                return _dec_from_triple(result_sign, '0', 0)
            else:
                return _SignedInfinity[result_sign]

        # from here on, the result always goes through the call
        # to _fix at the end of this function.
        ans = None
        exact = False

        # crude test to catch cases of extreme overflow/underflow.  If
        # log10(self)*other >= 10**bound and bound >= len(str(Emax))
        # then 10**bound >= 10**len(str(Emax)) >= Emax+1 and hence
        # self**other >= 10**(Emax+1), so overflow occurs.  The test
        # for underflow is similar.
        bound = self._log10_exp_bound() + other.adjusted()
        if (self_adj >= 0) == (other._sign == 0):
            # self > 1 and other +ve, or self < 1 and other -ve
            # possibility of overflow
            if bound >= len(str(context.Emax)):
                ans = _dec_from_triple(result_sign, '1', context.Emax+1)
        else:
            # self > 1 and other -ve, or self < 1 and other +ve
            # possibility of underflow to 0
            Etiny = context.Etiny()
            if bound >= len(str(-Etiny)):
                ans = _dec_from_triple(result_sign, '1', Etiny-1)

        # try for an exact result with precision +1
        if ans is None:
            ans = self._power_exact(other, context.prec + 1)
            if ans is not None:
                if result_sign == 1:
                    ans = _dec_from_triple(1, ans._int, ans._exp)
                exact = True

        # usual case: inexact result, x**y computed directly as exp(y*log(x))
        if ans is None:
            p = context.prec
            x = _WorkRep(self)
            xc, xe = x.int, x.exp
            y = _WorkRep(other)
            yc, ye = y.int, y.exp
            if y.sign == 1:
                yc = -yc

            # compute correctly rounded result:  start with precision +3,
            # then increase precision until result is unambiguously roundable
            extra = 3
            while True:
                coeff, exp = _dpower(xc, xe, yc, ye, p+extra)
                if coeff % (5*10**(len(str(coeff))-p-1)):
                    break
                extra += 3

            ans = _dec_from_triple(result_sign, str(coeff), exp)

        # unlike exp, ln and log10, the power function respects the
        # rounding mode; no need to switch to ROUND_HALF_EVEN here

        # There's a difficulty here when 'other' is not an integer and
        # the result is exact.  In this case, the specification
        # requires that the Inexact flag be raised (in spite of
        # exactness), but since the result is exact _fix won't do this
        # for us.  (Correspondingly, the Underflow signal should also
        # be raised for subnormal results.)  We can't directly raise
        # these signals either before or after calling _fix, since
        # that would violate the precedence for signals.  So we wrap
        # the ._fix call in a temporary context, and reraise
        # afterwards.
        if exact and not other._isinteger():
            # pad with zeros up to length context.prec+1 if necessary; this
            # ensures that the Rounded signal will be raised.
            if len(ans._int) <= context.prec:
                expdiff = context.prec + 1 - len(ans._int)
                ans = _dec_from_triple(ans._sign, ans._int+'0'*expdiff,
                                       ans._exp-expdiff)

            # create a copy of the current context, with cleared flags/traps
            newcontext = context.copy()
            newcontext.clear_flags()
            for exception in _signals:
                newcontext.traps[exception] = 0

            # round in the new context
            ans = ans._fix(newcontext)

            # raise Inexact, and if necessary, Underflow
            newcontext._raise_error(Inexact)
            if newcontext.flags[Subnormal]:
                newcontext._raise_error(Underflow)

            # propagate signals to the original context; _fix could
            # have raised any of Overflow, Underflow, Subnormal,
            # Inexact, Rounded, Clamped.  Overflow needs the correct
            # arguments.  Note that the order of the exceptions is
            # important here.
            if newcontext.flags[Overflow]:
                context._raise_error(Overflow, 'above Emax', ans._sign)
            for exception in Underflow, Subnormal, Inexact, Rounded, Clamped:
                if newcontext.flags[exception]:
                    context._raise_error(exception)

        else:
            ans = ans._fix(context)

        return ans

    def __rpow__(self, other, context=None):
        """Swaps self/other and returns __pow__."""
        other = _convert_other(other)
        if other is NotImplemented:
            return other
        return other.__pow__(self, context=context)

    def normalize(self, context=None):
        """Normalize- strip trailing 0s, change anything equal to 0 to 0e0"""

        if context is None:
            context = getcontext()

        if self._is_special:
            ans = self._check_nans(context=context)
            if ans:
                return ans

        dup = self._fix(context)
        if dup._isinfinity():
            return dup

        if not dup:
            return _dec_from_triple(dup._sign, '0', 0)
        exp_max = [context.Emax, context.Etop()][context._clamp]
        end = len(dup._int)
        exp = dup._exp
        while dup._int[end-1] == '0' and exp < exp_max:
            exp += 1
            end -= 1
        return _dec_from_triple(dup._sign, dup._int[:end], exp)

    def quantize(self, exp, rounding=None, context=None, watchexp=True):
        """Quantize self so its exponent is the same as that of exp.

        Similar to self._rescale(exp._exp) but with error checking.
        """
        exp = _convert_other(exp, raiseit=True)

        if context is None:
            context = getcontext()
        if rounding is None:
            rounding = context.rounding

        if self._is_special or exp._is_special:
            ans = self._check_nans(exp, context)
            if ans:
                return ans

            if exp._isinfinity() or self._isinfinity():
                if exp._isinfinity() and self._isinfinity():
                    return Decimal(self)  # if both are inf, it is OK
                return context._raise_error(InvalidOperation,
                                        'quantize with one INF')

        # if we're not watching exponents, do a simple rescale
        if not watchexp:
            ans = self._rescale(exp._exp, rounding)
            # raise Inexact and Rounded where appropriate
            if ans._exp > self._exp:
                context._raise_error(Rounded)
                if ans != self:
                    context._raise_error(Inexact)
            return ans

        # exp._exp should be between Etiny and Emax
        if not (context.Etiny() <= exp._exp <= context.Emax):
            return context._raise_error(InvalidOperation,
                   'target exponent out of bounds in quantize')

        if not self:
            ans = _dec_from_triple(self._sign, '0', exp._exp)
            return ans._fix(context)

        self_adjusted = self.adjusted()
        if self_adjusted > context.Emax:
            return context._raise_error(InvalidOperation,
                                        'exponent of quantize result too large for current context')
        if self_adjusted - exp._exp + 1 > context.prec:
            return context._raise_error(InvalidOperation,
                                        'quantize result has too many digits for current context')

        ans = self._rescale(exp._exp, rounding)
        if ans.adjusted() > context.Emax:
            return context._raise_error(InvalidOperation,
                                        'exponent of quantize result too large for current context')
        if len(ans._int) > context.prec:
            return context._raise_error(InvalidOperation,
                                        'quantize result has too many digits for current context')

        # raise appropriate flags
        if ans and ans.adjusted() < context.Emin:
            context._raise_error(Subnormal)
        if ans._exp > self._exp:
            if ans != self:
                context._raise_error(Inexact)
            context._raise_error(Rounded)

        # call to fix takes care of any necessary folddown, and
        # signals Clamped if necessary
        ans = ans._fix(context)
        return ans

    def same_quantum(self, other):
        """Return True if self and other have the same exponent; otherwise
        return False.

        If either operand is a special value, the following rules are used:
           * return True if both operands are infinities
           * return True if both operands are NaNs
           * otherwise, return False.
        """
        other = _convert_other(other, raiseit=True)
        if self._is_special or other._is_special:
            return (self.is_nan() and other.is_nan() or
                    self.is_infinite() and other.is_infinite())
        return self._exp == other._exp

    def _rescale(self, exp, rounding):
        """Rescale self so that the exponent is exp, either by padding with zeros
        or by truncating digits, using the given rounding mode.

        Specials are returned without change.  This operation is
        quiet: it raises no flags, and uses no information from the
        context.

        exp = exp to scale to (an integer)
        rounding = rounding mode
        """
        if self._is_special:
            return Decimal(self)
        if not self:
            return _dec_from_triple(self._sign, '0', exp)

        if self._exp >= exp:
            # pad answer with zeros if necessary
            return _dec_from_triple(self._sign,
                                        self._int + '0'*(self._exp - exp), exp)

        # too many digits; round and lose data.  If self.adjusted() <
        # exp-1, replace self by 10**(exp-1) before rounding
        digits = len(self._int) + self._exp - exp
        if digits < 0:
            self = _dec_from_triple(self._sign, '1', exp-1)
            digits = 0
        this_function = self._pick_rounding_function[rounding]
        changed = this_function(self, digits)
        coeff = self._int[:digits] or '0'
        if changed == 1:
            coeff = str(int(coeff)+1)
        return _dec_from_triple(self._sign, coeff, exp)

    def _round(self, places, rounding):
        """Round a nonzero, nonspecial Decimal to a fixed number of
        significant figures, using the given rounding mode.

        Infinities, NaNs and zeros are returned unaltered.

        This operation is quiet: it raises no flags, and uses no
        information from the context.

        """
        if places <= 0:
            raise ValueError("argument should be at least 1 in _round")
        if self._is_special or not self:
            return Decimal(self)
        ans = self._rescale(self.adjusted()+1-places, rounding)
        # it can happen that the rescale alters the adjusted exponent;
        # for example when rounding 99.97 to 3 significant figures.
        # When this happens we end up with an extra 0 at the end of
        # the number; a second rescale fixes this.
        if ans.adjusted() != self.adjusted():
            ans = ans._rescale(ans.adjusted()+1-places, rounding)
        return ans

    def to_integral_exact(self, rounding=None, context=None):
        """Rounds to a nearby integer.

        If no rounding mode is specified, take the rounding mode from
        the context.  This method raises the Rounded and Inexact flags
        when appropriate.

        See also: to_integral_value, which does exactly the same as
        this method except that it doesn't raise Inexact or Rounded.
        """
        if self._is_special:
            ans = self._check_nans(context=context)
            if ans:
                return ans
            return Decimal(self)
        if self._exp >= 0:
            return Decimal(self)
        if not self:
            return _dec_from_triple(self._sign, '0', 0)
        if context is None:
            context = getcontext()
        if rounding is None:
            rounding = context.rounding
        ans = self._rescale(0, rounding)
        if ans != self:
            context._raise_error(Inexact)
        context._raise_error(Rounded)
        return ans

    def to_integral_value(self, rounding=None, context=None):
        """Rounds to the nearest integer, without raising inexact, rounded."""
        if context is None:
            context = getcontext()
        if rounding is None:
            rounding = context.rounding
        if self._is_special:
            ans = self._check_nans(context=context)
            if ans:
                return ans
            return Decimal(self)
        if self._exp >= 0:
            return Decimal(self)
        else:
            return self._rescale(0, rounding)

    # the method name changed, but we provide also the old one, for compatibility
    to_integral = to_integral_value

    def sqrt(self, context=None):
        """Return the square root of self."""
        if context is None:
            context = getcontext()

        if self._is_special:
            ans = self._check_nans(context=context)
            if ans:
                return ans

            if self._isinfinity() and self._sign == 0:
                return Decimal(self)

        if not self:
            # exponent = self._exp // 2.  sqrt(-0) = -0
            ans = _dec_from_triple(self._sign, '0', self._exp // 2)
            return ans._fix(context)

        if self._sign == 1:
            return context._raise_error(InvalidOperation, 'sqrt(-x), x > 0')

        # At this point self represents a positive number.  Let p be
        # the desired precision and express self in the form c*100**e
        # with c a positive real number and e an integer, c and e
        # being chosen so that 100**(p-1) <= c < 100**p.  Then the
        # (exact) square root of self is sqrt(c)*10**e, and 10**(p-1)
        # <= sqrt(c) < 10**p, so the closest representable Decimal at
        # precision p is n*10**e where n = round_half_even(sqrt(c)),
        # the closest integer to sqrt(c) with the even integer chosen
        # in the case of a tie.
        #
        # To ensure correct rounding in all cases, we use the
        # following trick: we compute the square root to an extra
        # place (precision p+1 instead of precision p), rounding down.
        # Then, if the result is inexact and its last digit is 0 or 5,
        # we increase the last digit to 1 or 6 respectively; if it's
        # exact we leave the last digit alone.  Now the final round to
        # p places (or fewer in the case of underflow) will round
        # correctly and raise the appropriate flags.

        # use an extra digit of precision
        prec = context.prec+1

        # write argument in the form c*100**e where e = self._exp//2
        # is the 'ideal' exponent, to be used if the square root is
        # exactly representable.  l is the number of 'digits' of c in
        # base 100, so that 100**(l-1) <= c < 100**l.
        op = _WorkRep(self)
        e = op.exp >> 1
        if op.exp & 1:
            c = op.int * 10
            l = (len(self._int) >> 1) + 1
        else:
            c = op.int
            l = len(self._int)+1 >> 1

        # rescale so that c has exactly prec base 100 'digits'
        shift = prec-l
        if shift >= 0:
            c *= 100**shift
            exact = True
        else:
            c, remainder = divmod(c, 100**-shift)
            exact = not remainder
        e -= shift

        # find n = floor(sqrt(c)) using Newton's method
        n = 10**prec
        while True:
            q = c//n
            if n <= q:
                break
            else:
                n = n + q >> 1
        exact = exact and n*n == c

        if exact:
            # result is exact; rescale to use ideal exponent e
            if shift >= 0:
                # assert n % 10**shift == 0
                n //= 10**shift
            else:
                n *= 10**-shift
            e += shift
        else:
            # result is not exact; fix last digit as described above
            if n % 5 == 0:
                n += 1

        ans = _dec_from_triple(0, str(n), e)

        # round, and fit to current context
        context = context._shallow_copy()
        rounding = context._set_rounding(ROUND_HALF_EVEN)
        ans = ans._fix(context)
        context.rounding = rounding

        return ans

    def max(self, other, context=None):
        """Returns the larger value.

        Like max(self, other) except if one is not a number, returns
        NaN (and signals if one is sNaN).  Also rounds.
        """
        other = _convert_other(other, raiseit=True)

        if context is None:
            context = getcontext()

        if self._is_special or other._is_special:
            # If one operand is a quiet NaN and the other is number, then the
            # number is always returned
            sn = self._isnan()
            on = other._isnan()
            if sn or on:
                if on == 1 and sn == 0:
                    return self._fix(context)
                if sn == 1 and on == 0:
                    return other._fix(context)
                return self._check_nans(other, context)

        c = self._cmp(other)
        if c == 0:
            # If both operands are finite and equal in numerical value
            # then an ordering is applied:
            #
            # If the signs differ then max returns the operand with the
            # positive sign and min returns the operand with the negative sign
            #
            # If the signs are the same then the exponent is used to select
            # the result.  This is exactly the ordering used in compare_total.
            c = self.compare_total(other)

        if c == -1:
            ans = other
        else:
            ans = self

        return ans._fix(context)

    def min(self, other, context=None):
        """Returns the smaller value.

        Like min(self, other) except if one is not a number, returns
        NaN (and signals if one is sNaN).  Also rounds.
        """
        other = _convert_other(other, raiseit=True)

        if context is None:
            context = getcontext()

        if self._is_special or other._is_special:
            # If one operand is a quiet NaN and the other is number, then the
            # number is always returned
            sn = self._isnan()
            on = other._isnan()
            if sn or on:
                if on == 1 and sn == 0:
                    return self._fix(context)
                if sn == 1 and on == 0:
                    return other._fix(context)
                return self._check_nans(other, context)

        c = self._cmp(other)
        if c == 0:
            c = self.compare_total(other)

        if c == -1:
            ans = self
        else:
            ans = other

        return ans._fix(context)

    def _isinteger(self):
        """Returns whether self is an integer"""
        if self._is_special:
            return False
        if self._exp >= 0:
            return True
        rest = self._int[self._exp:]
        return rest == '0'*len(rest)

    def _iseven(self):
        """Returns True if self is even.  Assumes self is an integer."""
        if not self or self._exp > 0:
            return True
        return self._int[-1+self._exp] in '02468'

    def adjusted(self):
        """Return the adjusted exponent of self"""
        try:
            return self._exp + len(self._int) - 1
        # If NaN or Infinity, self._exp is string
        except TypeError:
            return 0

    def canonical(self, context=None):
        """Returns the same Decimal object.

        As we do not have different encodings for the same number, the
        received object already is in its canonical form.
        """
        return self

    def compare_signal(self, other, context=None):
        """Compares self to the other operand numerically.

        It's pretty much like compare(), but all NaNs signal, with signaling
        NaNs taking precedence over quiet NaNs.
        """
        other = _convert_other(other, raiseit = True)
        ans = self._compare_check_nans(other, context)
        if ans:
            return ans
        return self.compare(other, context=context)

    def compare_total(self, other):
        """Compares self to other using the abstract representations.

        This is not like the standard compare, which use their numerical
        value. Note that a total ordering is defined for all possible abstract
        representations.
        """
        other = _convert_other(other, raiseit=True)

        # if one is negative and the other is positive, it's easy
        if self._sign and not other._sign:
            return _NegativeOne
        if not self._sign and other._sign:
            return _One
        sign = self._sign

        # let's handle both NaN types
        self_nan = self._isnan()
        other_nan = other._isnan()
        if self_nan or other_nan:
            if self_nan == other_nan:
                # compare payloads as though they're integers
                self_key = len(self._int), self._int
                other_key = len(other._int), other._int
                if self_key < other_key:
                    if sign:
                        return _One
                    else:
                        return _NegativeOne
                if self_key > other_key:
                    if sign:
                        return _NegativeOne
                    else:
                        return _One
                return _Zero

            if sign:
                if self_nan == 1:
                    return _NegativeOne
                if other_nan == 1:
                    return _One
                if self_nan == 2:
                    return _NegativeOne
                if other_nan == 2:
                    return _One
            else:
                if self_nan == 1:
                    return _One
                if other_nan == 1:
                    return _NegativeOne
                if self_nan == 2:
                    return _One
                if other_nan == 2:
                    return _NegativeOne

        if self < other:
            return _NegativeOne
        if self > other:
            return _One

        if self._exp < other._exp:
            if sign:
                return _One
            else:
                return _NegativeOne
        if self._exp > other._exp:
            if sign:
                return _NegativeOne
            else:
                return _One
        return _Zero


    def compare_total_mag(self, other):
        """Compares self to other using abstract repr., ignoring sign.

        Like compare_total, but with operand's sign ignored and assumed to be 0.
        """
        other = _convert_other(other, raiseit=True)

        s = self.copy_abs()
        o = other.copy_abs()
        return s.compare_total(o)

    def copy_abs(self):
        """Returns a copy with the sign set to 0. """
        return _dec_from_triple(0, self._int, self._exp, self._is_special)

    def copy_negate(self):
        """Returns a copy with the sign inverted."""
        if self._sign:
            return _dec_from_triple(0, self._int, self._exp, self._is_special)
        else:
            return _dec_from_triple(1, self._int, self._exp, self._is_special)

    def copy_sign(self, other):
        """Returns self with the sign of other."""
        other = _convert_other(other, raiseit=True)
        return _dec_from_triple(other._sign, self._int,
                                self._exp, self._is_special)

    def exp(self, context=None):
        """Returns e ** self."""

        if context is None:
            context = getcontext()

        # exp(NaN) = NaN
        ans = self._check_nans(context=context)
        if ans:
            return ans

        # exp(-Infinity) = 0
        if self._isinfinity() == -1:
            return _Zero

        # exp(0) = 1
        if not self:
            return _One

        # exp(Infinity) = Infinity
        if self._isinfinity() == 1:
            return Decimal(self)

        # the result is now guaranteed to be inexact (the true
        # mathematical result is transcendental). There's no need to
        # raise Rounded and Inexact here---they'll always be raised as
        # a result of the call to _fix.
        p = context.prec
        adj = self.adjusted()

        # we only need to do any computation for quite a small range
        # of adjusted exponents---for example, -29 <= adj <= 10 for
        # the default context.  For smaller exponent the result is
        # indistinguishable from 1 at the given precision, while for
        # larger exponent the result either overflows or underflows.
        if self._sign == 0 and adj > len(str((context.Emax+1)*3)):
            # overflow
            ans = _dec_from_triple(0, '1', context.Emax+1)
        elif self._sign == 1 and adj > len(str((-context.Etiny()+1)*3)):
            # underflow to 0
            ans = _dec_from_triple(0, '1', context.Etiny()-1)
        elif self._sign == 0 and adj < -p:
            # p+1 digits; final round will raise correct flags
            ans = _dec_from_triple(0, '1' + '0'*(p-1) + '1', -p)
        elif self._sign == 1 and adj < -p-1:
            # p+1 digits; final round will raise correct flags
            ans = _dec_from_triple(0, '9'*(p+1), -p-1)
        # general case
        else:
            op = _WorkRep(self)
            c, e = op.int, op.exp
            if op.sign == 1:
                c = -c

            # compute correctly rounded result: increase precision by
            # 3 digits at a time until we get an unambiguously
            # roundable result
            extra = 3
            while True:
                coeff, exp = _dexp(c, e, p+extra)
                if coeff % (5*10**(len(str(coeff))-p-1)):
                    break
                extra += 3

            ans = _dec_from_triple(0, str(coeff), exp)

        # at this stage, ans should round correctly with *any*
        # rounding mode, not just with ROUND_HALF_EVEN
        context = context._shallow_copy()
        rounding = context._set_rounding(ROUND_HALF_EVEN)
        ans = ans._fix(context)
        context.rounding = rounding

        return ans

    def is_canonical(self):
        """Return True if self is canonical; otherwise return False.

        Currently, the encoding of a Decimal instance is always
        canonical, so this method returns True for any Decimal.
        """
        return True

    def is_finite(self):
        """Return True if self is finite; otherwise return False.

        A Decimal instance is considered finite if it is neither
        infinite nor a NaN.
        """
        return not self._is_special

    def is_infinite(self):
        """Return True if self is infinite; otherwise return False."""
        return self._exp == 'F'

    def is_nan(self):
        """Return True if self is a qNaN or sNaN; otherwise return False."""
        return self._exp in ('n', 'N')

    def is_normal(self, context=None):
        """Return True if self is a normal number; otherwise return False."""
        if self._is_special or not self:
            return False
        if context is None:
            context = getcontext()
        return context.Emin <= self.adjusted()

    def is_qnan(self):
        """Return True if self is a quiet NaN; otherwise return False."""
        return self._exp == 'n'

    def is_signed(self):
        """Return True if self is negative; otherwise return False."""
        return self._sign == 1

    def is_snan(self):
        """Return True if self is a signaling NaN; otherwise return False."""
        return self._exp == 'N'

    def is_subnormal(self, context=None):
        """Return True if self is subnormal; otherwise return False."""
        if self._is_special or not self:
            return False
        if context is None:
            context = getcontext()
        return self.adjusted() < context.Emin

    def is_zero(self):
        """Return True if self is a zero; otherwise return False."""
        return not self._is_special and self._int == '0'

    def _ln_exp_bound(self):
        """Compute a lower bound for the adjusted exponent of self.ln().
        In other words, compute r such that self.ln() >= 10**r.  Assumes
        that self is finite and positive and that self != 1.
        """

        # for 0.1 <= x <= 10 we use the inequalities 1-1/x <= ln(x) <= x-1
        adj = self._exp + len(self._int) - 1
        if adj >= 1:
            # argument >= 10; we use 23/10 = 2.3 as a lower bound for ln(10)
            return len(str(adj*23//10)) - 1
        if adj <= -2:
            # argument <= 0.1
            return len(str((-1-adj)*23//10)) - 1
        op = _WorkRep(self)
        c, e = op.int, op.exp
        if adj == 0:
            # 1 < self < 10
            num = str(c-10**-e)
            den = str(c)
            return len(num) - len(den) - (num < den)
        # adj == -1, 0.1 <= self < 1
        return e + len(str(10**-e - c)) - 1


    def ln(self, context=None):
        """Returns the natural (base e) logarithm of self."""

        if context is None:
            context = getcontext()

        # ln(NaN) = NaN
        ans = self._check_nans(context=context)
        if ans:
            return ans

        # ln(0.0) == -Infinity
        if not self:
            return _NegativeInfinity

        # ln(Infinity) = Infinity
        if self._isinfinity() == 1:
            return _Infinity

        # ln(1.0) == 0.0
        if self == _One:
            return _Zero

        # ln(negative) raises InvalidOperation
        if self._sign == 1:
            return context._raise_error(InvalidOperation,
                                        'ln of a negative value')

        # result is irrational, so necessarily inexact
        op = _WorkRep(self)
        c, e = op.int, op.exp
        p = context.prec

        # correctly rounded result: repeatedly increase precision by 3
        # until we get an unambiguously roundable result
        places = p - self._ln_exp_bound() + 2 # at least p+3 places
        while True:
            coeff = _dlog(c, e, places)
            # assert len(str(abs(coeff)))-p >= 1
            if coeff % (5*10**(len(str(abs(coeff)))-p-1)):
                break
            places += 3
        ans = _dec_from_triple(int(coeff<0), str(abs(coeff)), -places)

        context = context._shallow_copy()
        rounding = context._set_rounding(ROUND_HALF_EVEN)
        ans = ans._fix(context)
        context.rounding = rounding
        return ans

    def _log10_exp_bound(self):
        """Compute a lower bound for the adjusted exponent of self.log10().
        In other words, find r such that self.log10() >= 10**r.
        Assumes that self is finite and positive and that self != 1.
        """

        # For x >= 10 or x < 0.1 we only need a bound on the integer
        # part of log10(self), and this comes directly from the
        # exponent of x.  For 0.1 <= x <= 10 we use the inequalities
        # 1-1/x <= log(x) <= x-1. If x > 1 we have |log10(x)| >
        # (1-1/x)/2.31 > 0.  If x < 1 then |log10(x)| > (1-x)/2.31 > 0

        adj = self._exp + len(self._int) - 1
        if adj >= 1:
            # self >= 10
            return len(str(adj))-1
        if adj <= -2:
            # self < 0.1
            return len(str(-1-adj))-1
        op = _WorkRep(self)
        c, e = op.int, op.exp
        if adj == 0:
            # 1 < self < 10
            num = str(c-10**-e)
            den = str(231*c)
            return len(num) - len(den) - (num < den) + 2
        # adj == -1, 0.1 <= self < 1
        num = str(10**-e-c)
        return len(num) + e - (num < "231") - 1

    def log10(self, context=None):
        """Returns the base 10 logarithm of self."""

        if context is None:
            context = getcontext()

        # log10(NaN) = NaN
        ans = self._check_nans(context=context)
        if ans:
            return ans

        # log10(0.0) == -Infinity
        if not self:
            return _NegativeInfinity

        # log10(Infinity) = Infinity
        if self._isinfinity() == 1:
            return _Infinity

        # log10(negative or -Infinity) raises InvalidOperation
        if self._sign == 1:
            return context._raise_error(InvalidOperation,
                                        'log10 of a negative value')

        # log10(10**n) = n
        if self._int[0] == '1' and self._int[1:] == '0'*(len(self._int) - 1):
            # answer may need rounding
            ans = Decimal(self._exp + len(self._int) - 1)
        else:
            # result is irrational, so necessarily inexact
            op = _WorkRep(self)
            c, e = op.int, op.exp
            p = context.prec

            # correctly rounded result: repeatedly increase precision
            # until result is unambiguously roundable
            places = p-self._log10_exp_bound()+2
            while True:
                coeff = _dlog10(c, e, places)
                # assert len(str(abs(coeff)))-p >= 1
                if coeff % (5*10**(len(str(abs(coeff)))-p-1)):
                    break
                places += 3
            ans = _dec_from_triple(int(coeff<0), str(abs(coeff)), -places)

        context = context._shallow_copy()
        rounding = context._set_rounding(ROUND_HALF_EVEN)
        ans = ans._fix(context)
        context.rounding = rounding
        return ans

    def logb(self, context=None):
        """ Returns the exponent of the magnitude of self's MSD.

        The result is the integer which is the exponent of the magnitude
        of the most significant digit of self (as though it were truncated
        to a single digit while maintaining the value of that digit and
        without limiting the resulting exponent).
        """
        # logb(NaN) = NaN
        ans = self._check_nans(context=context)
        if ans:
            return ans

        if context is None:
            context = getcontext()

        # logb(+/-Inf) = +Inf
        if self._isinfinity():
            return _Infinity

        # logb(0) = -Inf, DivisionByZero
        if not self:
            return context._raise_error(DivisionByZero, 'logb(0)', 1)

        # otherwise, simply return the adjusted exponent of self, as a
        # Decimal.  Note that no attempt is made to fit the result
        # into the current context.
        ans = Decimal(self.adjusted())
        return ans._fix(context)

    def _islogical(self):
        """Return True if self is a logical operand.

        For being logical, it must be a finite number with a sign of 0,
        an exponent of 0, and a coefficient whose digits must all be
        either 0 or 1.
        """
        if self._sign != 0 or self._exp != 0:
            return False
        for dig in self._int:
            if dig not in '01':
                return False
        return True

    def _fill_logical(self, context, opa, opb):
        dif = context.prec - len(opa)
        if dif > 0:
            opa = '0'*dif + opa
        elif dif < 0:
            opa = opa[-context.prec:]
        dif = context.prec - len(opb)
        if dif > 0:
            opb = '0'*dif + opb
        elif dif < 0:
            opb = opb[-context.prec:]
        return opa, opb

    def logical_and(self, other, context=None):
        """Applies an 'and' operation between self and other's digits."""
        if context is None:
            context = getcontext()

        other = _convert_other(other, raiseit=True)

        if not self._islogical() or not other._islogical():
            return context._raise_error(InvalidOperation)

        # fill to context.prec
        (opa, opb) = self._fill_logical(context, self._int, other._int)

        # make the operation, and clean starting zeroes
        result = "".join([str(int(a)&int(b)) for a,b in zip(opa,opb)])
        return _dec_from_triple(0, result.lstrip('0') or '0', 0)

    def logical_invert(self, context=None):
        """Invert all its digits."""
        if context is None:
            context = getcontext()
        return self.logical_xor(_dec_from_triple(0,'1'*context.prec,0),
                                context)

    def logical_or(self, other, context=None):
        """Applies an 'or' operation between self and other's digits."""
        if context is None:
            context = getcontext()

        other = _convert_other(other, raiseit=True)

        if not self._islogical() or not other._islogical():
            return context._raise_error(InvalidOperation)

        # fill to context.prec
        (opa, opb) = self._fill_logical(context, self._int, other._int)

        # make the operation, and clean starting zeroes
        result = "".join([str(int(a)|int(b)) for a,b in zip(opa,opb)])
        return _dec_from_triple(0, result.lstrip('0') or '0', 0)

    def logical_xor(self, other, context=None):
        """Applies an 'xor' operation between self and other's digits."""
        if context is None:
            context = getcontext()

        other = _convert_other(other, raiseit=True)

        if not self._islogical() or not other._islogical():
            return context._raise_error(InvalidOperation)

        # fill to context.prec
        (opa, opb) = self._fill_logical(context, self._int, other._int)

        # make the operation, and clean starting zeroes
        result = "".join([str(int(a)^int(b)) for a,b in zip(opa,opb)])
        return _dec_from_triple(0, result.lstrip('0') or '0', 0)

    def max_mag(self, other, context=None):
        """Compares the values numerically with their sign ignored."""
        other = _convert_other(other, raiseit=True)

        if context is None:
            context = getcontext()

        if self._is_special or other._is_special:
            # If one operand is a quiet NaN and the other is number, then the
            # number is always returned
            sn = self._isnan()
            on = other._isnan()
            if sn or on:
                if on == 1 and sn == 0:
                    return self._fix(context)
                if sn == 1 and on == 0:
                    return other._fix(context)
                return self._check_nans(other, context)

        c = self.copy_abs()._cmp(other.copy_abs())
        if c == 0:
            c = self.compare_total(other)

        if c == -1:
            ans = other
        else:
            ans = self

        return ans._fix(context)

    def min_mag(self, other, context=None):
        """Compares the values numerically with their sign ignored."""
        other = _convert_other(other, raiseit=True)

        if context is None:
            context = getcontext()

        if self._is_special or other._is_special:
            # If one operand is a quiet NaN and the other is number, then the
            # number is always returned
            sn = self._isnan()
            on = other._isnan()
            if sn or on:
                if on == 1 and sn == 0:
                    return self._fix(context)
                if sn == 1 and on == 0:
                    return other._fix(context)
                return self._check_nans(other, context)

        c = self.copy_abs()._cmp(other.copy_abs())
        if c == 0:
            c = self.compare_total(other)

        if c == -1:
            ans = self
        else:
            ans = other

        return ans._fix(context)

    def next_minus(self, context=None):
        """Returns the largest representable number smaller than itself."""
        if context is None:
            context = getcontext()

        ans = self._check_nans(context=context)
        if ans:
            return ans

        if self._isinfinity() == -1:
            return _NegativeInfinity
        if self._isinfinity() == 1:
            return _dec_from_triple(0, '9'*context.prec, context.Etop())

        context = context.copy()
        context._set_rounding(ROUND_FLOOR)
        context._ignore_all_flags()
        new_self = self._fix(context)
        if new_self != self:
            return new_self
        return self.__sub__(_dec_from_triple(0, '1', context.Etiny()-1),
                            context)

    def next_plus(self, context=None):
        """Returns the smallest representable number larger than itself."""
        if context is None:
            context = getcontext()

        ans = self._check_nans(context=context)
        if ans:
            return ans

        if self._isinfinity() == 1:
            return _Infinity
        if self._isinfinity() == -1:
            return _dec_from_triple(1, '9'*context.prec, context.Etop())

        context = context.copy()
        context._set_rounding(ROUND_CEILING)
        context._ignore_all_flags()
        new_self = self._fix(context)
        if new_self != self:
            return new_self
        return self.__add__(_dec_from_triple(0, '1', context.Etiny()-1),
                            context)

    def next_toward(self, other, context=None):
        """Returns the number closest to self, in the direction towards other.

        The result is the closest representable number to self
        (excluding self) that is in the direction towards other,
        unless both have the same value.  If the two operands are
        numerically equal, then the result is a copy of self with the
        sign set to be the same as the sign of other.
        """
        other = _convert_other(other, raiseit=True)

        if context is None:
            context = getcontext()

        ans = self._check_nans(other, context)
        if ans:
            return ans

        comparison = self._cmp(other)
        if comparison == 0:
            return self.copy_sign(other)

        if comparison == -1:
            ans = self.next_plus(context)
        else: # comparison == 1
            ans = self.next_minus(context)

        # decide which flags to raise using value of ans
        if ans._isinfinity():
            context._raise_error(Overflow,
                                 'Infinite result from next_toward',
                                 ans._sign)
            context._raise_error(Inexact)
            context._raise_error(Rounded)
        elif ans.adjusted() < context.Emin:
            context._raise_error(Underflow)
            context._raise_error(Subnormal)
            context._raise_error(Inexact)
            context._raise_error(Rounded)
            # if precision == 1 then we don't raise Clamped for a
            # result 0E-Etiny.
            if not ans:
                context._raise_error(Clamped)

        return ans

    def number_class(self, context=None):
        """Returns an indication of the class of self.

        The class is one of the following strings:
          sNaN
          NaN
          -Infinity
          -Normal
          -Subnormal
          -Zero
          +Zero
          +Subnormal
          +Normal
          +Infinity
        """
        if self.is_snan():
            return "sNaN"
        if self.is_qnan():
            return "NaN"
        inf = self._isinfinity()
        if inf == 1:
            return "+Infinity"
        if inf == -1:
            return "-Infinity"
        if self.is_zero():
            if self._sign:
                return "-Zero"
            else:
                return "+Zero"
        if context is None:
            context = getcontext()
        if self.is_subnormal(context=context):
            if self._sign:
                return "-Subnormal"
            else:
                return "+Subnormal"
        # just a normal, regular, boring number, :)
        if self._sign:
            return "-Normal"
        else:
            return "+Normal"

    def radix(self):
        """Just returns 10, as this is Decimal, :)"""
        return Decimal(10)

    def rotate(self, other, context=None):
        """Returns a rotated copy of self, value-of-other times."""
        if context is None:
            context = getcontext()

        other = _convert_other(other, raiseit=True)

        ans = self._check_nans(other, context)
        if ans:
            return ans

        if other._exp != 0:
            return context._raise_error(InvalidOperation)
        if not (-context.prec <= int(other) <= context.prec):
            return context._raise_error(InvalidOperation)

        if self._isinfinity():
            return Decimal(self)

        # get values, pad if necessary
        torot = int(other)
        rotdig = self._int
        topad = context.prec - len(rotdig)
        if topad > 0:
            rotdig = '0'*topad + rotdig
        elif topad < 0:
            rotdig = rotdig[-topad:]

        # let's rotate!
        rotated = rotdig[torot:] + rotdig[:torot]
        return _dec_from_triple(self._sign,
                                rotated.lstrip('0') or '0', self._exp)

    def scaleb(self, other, context=None):
        """Returns self operand after adding the second value to its exp."""
        if context is None:
            context = getcontext()

        other = _convert_other(other, raiseit=True)

        ans = self._check_nans(other, context)
        if ans:
            return ans

        if other._exp != 0:
            return context._raise_error(InvalidOperation)
        liminf = -2 * (context.Emax + context.prec)
        limsup =  2 * (context.Emax + context.prec)
        if not (liminf <= int(other) <= limsup):
            return context._raise_error(InvalidOperation)

        if self._isinfinity():
            return Decimal(self)

        d = _dec_from_triple(self._sign, self._int, self._exp + int(other))
        d = d._fix(context)
        return d

    def shift(self, other, context=None):
        """Returns a shifted copy of self, value-of-other times."""
        if context is None:
            context = getcontext()

        other = _convert_other(other, raiseit=True)

        ans = self._check_nans(other, context)
        if ans:
            return ans

        if other._exp != 0:
            return context._raise_error(InvalidOperation)
        if not (-context.prec <= int(other) <= context.prec):
            return context._raise_error(InvalidOperation)

        if self._isinfinity():
            return Decimal(self)

        # get values, pad if necessary
        torot = int(other)
        rotdig = self._int
        topad = context.prec - len(rotdig)
        if topad > 0:
            rotdig = '0'*topad + rotdig
        elif topad < 0:
            rotdig = rotdig[-topad:]

        # let's shift!
        if torot < 0:
            shifted = rotdig[:torot]
        else:
            shifted = rotdig + '0'*torot
            shifted = shifted[-context.prec:]

        return _dec_from_triple(self._sign,
                                    shifted.lstrip('0') or '0', self._exp)

    # Support for pickling, copy, and deepcopy
    def __reduce__(self):
        return (self.__class__, (str(self),))

    def __copy__(self):
        if type(self) is Decimal:
            return self     # I'm immutable; therefore I am my own clone
        return self.__class__(str(self))

    def __deepcopy__(self, memo):
        if type(self) is Decimal:
            return self     # My components are also immutable
        return self.__class__(str(self))

    # PEP 3101 support.  the _localeconv keyword argument should be
    # considered private: it's provided for ease of testing only.
    def __format__(self, specifier, context=None, _localeconv=None):
        """Format a Decimal instance according to the given specifier.

        The specifier should be a standard format specifier, with the
        form described in PEP 3101.  Formatting types 'e', 'E', 'f',
        'F', 'g', 'G', 'n' and '%' are supported.  If the formatting
        type is omitted it defaults to 'g' or 'G', depending on the
        value of context.capitals.
        """

        # Note: PEP 3101 says that if the type is not present then
        # there should be at least one digit after the decimal point.
        # We take the liberty of ignoring this requirement for
        # Decimal---it's presumably there to make sure that
        # format(float, '') behaves similarly to str(float).
        if context is None:
            context = getcontext()

        spec = _parse_format_specifier(specifier, _localeconv=_localeconv)

        # special values don't care about the type or precision
        if self._is_special:
            sign = _format_sign(self._sign, spec)
            body = str(self.copy_abs())
            if spec['type'] == '%':
                body += '%'
            return _format_align(sign, body, spec)

        # a type of None defaults to 'g' or 'G', depending on context
        if spec['type'] is None:
            spec['type'] = ['g', 'G'][context.capitals]

        # if type is '%', adjust exponent of self accordingly
        if spec['type'] == '%':
            self = _dec_from_triple(self._sign, self._int, self._exp+2)

        # round if necessary, taking rounding mode from the context
        rounding = context.rounding
        precision = spec['precision']
        if precision is not None:
            if spec['type'] in 'eE':
                self = self._round(precision+1, rounding)
            elif spec['type'] in 'fF%':
                self = self._rescale(-precision, rounding)
            elif spec['type'] in 'gG' and len(self._int) > precision:
                self = self._round(precision, rounding)
        # special case: zeros with a positive exponent can't be
        # represented in fixed point; rescale them to 0e0.
        if not self and self._exp > 0 and spec['type'] in 'fF%':
            self = self._rescale(0, rounding)

        # figure out placement of the decimal point
        leftdigits = self._exp + len(self._int)
        if spec['type'] in 'eE':
            if not self and precision is not None:
                dotplace = 1 - precision
            else:
                dotplace = 1
        elif spec['type'] in 'fF%':
            dotplace = leftdigits
        elif spec['type'] in 'gG':
            if self._exp <= 0 and leftdigits > -6:
                dotplace = leftdigits
            else:
                dotplace = 1

        # find digits before and after decimal point, and get exponent
        if dotplace < 0:
            intpart = '0'
            fracpart = '0'*(-dotplace) + self._int
        elif dotplace > len(self._int):
            intpart = self._int + '0'*(dotplace-len(self._int))
            fracpart = ''
        else:
            intpart = self._int[:dotplace] or '0'
            fracpart = self._int[dotplace:]
        exp = leftdigits-dotplace

        # done with the decimal-specific stuff;  hand over the rest
        # of the formatting to the _format_number function
        return _format_number(self._sign, intpart, fracpart, exp, spec)

def _dec_from_triple(sign, coefficient, exponent, special=False):
    """Create a decimal instance directly, without any validation,
    normalization (e.g. removal of leading zeros) or argument
    conversion.

    This function is for *internal use only*.
    """

    self = object.__new__(Decimal)
    self._sign = sign
    self._int = coefficient
    self._exp = exponent
    self._is_special = special

    return self

# Register Decimal as a kind of Number (an abstract base class).
# However, do not register it as Real (because Decimals are not
# interoperable with floats).
_numbers.Number.register(Decimal)


##### Context class #######################################################

class _ContextManager(object):
    """Context manager class to support localcontext().

      Sets a copy of the supplied context in __enter__() and restores
      the previous decimal context in __exit__()
    """
    def __init__(self, new_context):
        self.new_context = new_context.copy()
    def __enter__(self):
        self.saved_context = getcontext()
        setcontext(self.new_context)
        return self.new_context
    def __exit__(self, t, v, tb):
        setcontext(self.saved_context)

class Context(object):
    """Contains the context for a Decimal instance.

    Contains:
    prec - precision (for use in rounding, division, square roots..)
    rounding - rounding type (how you round)
    traps - If traps[exception] = 1, then the exception is
                    raised when it is caused.  Otherwise, a value is
                    substituted in.
    flags  - When an exception is caused, flags[exception] is set.
             (Whether or not the trap_enabler is set)
             Should be reset by user of Decimal instance.
    Emin -   Minimum exponent
    Emax -   Maximum exponent
    capitals -      If 1, 1*10^1 is printed as 1E+1.
                    If 0, printed as 1e1
    _clamp - If 1, change exponents if too high (Default 0)
    """

    def __init__(self, prec=None, rounding=None,
                 traps=None, flags=None,
                 Emin=None, Emax=None,
                 capitals=None, _clamp=0,
                 _ignored_flags=None):
        # Set defaults; for everything except flags and _ignored_flags,
        # inherit from DefaultContext.
        try:
            dc = DefaultContext
        except NameError:
            pass

        self.prec = prec if prec is not None else dc.prec
        self.rounding = rounding if rounding is not None else dc.rounding
        self.Emin = Emin if Emin is not None else dc.Emin
        self.Emax = Emax if Emax is not None else dc.Emax
        self.capitals = capitals if capitals is not None else dc.capitals
        self._clamp = _clamp if _clamp is not None else dc._clamp

        if _ignored_flags is None:
            self._ignored_flags = []
        else:
            self._ignored_flags = _ignored_flags

        if traps is None:
            self.traps = dc.traps.copy()
        elif not isinstance(traps, dict):
            self.traps = dict((s, int(s in traps)) for s in _signals)
        else:
            self.traps = traps

        if flags is None:
            self.flags = dict.fromkeys(_signals, 0)
        elif not isinstance(flags, dict):
            self.flags = dict((s, int(s in flags)) for s in _signals)
        else:
            self.flags = flags

    def __repr__(self):
        """Show the current context."""
        s = []
        s.append('Context(prec=%(prec)d, rounding=%(rounding)s, '
                 'Emin=%(Emin)d, Emax=%(Emax)d, capitals=%(capitals)d'
                 % vars(self))
        names = [f.__name__ for f, v in self.flags.items() if v]
        s.append('flags=[' + ', '.join(names) + ']')
        names = [t.__name__ for t, v in self.traps.items() if v]
        s.append('traps=[' + ', '.join(names) + ']')
        return ', '.join(s) + ')'

    def clear_flags(self):
        """Reset all flags to zero"""
        for flag in self.flags:
            self.flags[flag] = 0

    def _shallow_copy(self):
        """Returns a shallow copy from self."""
        nc = Context(self.prec, self.rounding, self.traps,
                     self.flags, self.Emin, self.Emax,
                     self.capitals, self._clamp, self._ignored_flags)
        return nc

    def copy(self):
        """Returns a deep copy from self."""
        nc = Context(self.prec, self.rounding, self.traps.copy(),
                     self.flags.copy(), self.Emin, self.Emax,
                     self.capitals, self._clamp, self._ignored_flags)
        return nc
    __copy__ = copy

    def _raise_error(self, condition, explanation = None, *args):
        """Handles an error

        If the flag is in _ignored_flags, returns the default response.
        Otherwise, it sets the flag, then, if the corresponding
        trap_enabler is set, it reraises the exception.  Otherwise, it returns
        the default value after setting the flag.
        """
        error = _condition_map.get(condition, condition)
        if error in self._ignored_flags:
            # Don't touch the flag
            return error().handle(self, *args)

        self.flags[error] = 1
        if not self.traps[error]:
            # The errors define how to handle themselves.
            return condition().handle(self, *args)

        # Errors should only be risked on copies of the context
        # self._ignored_flags = []
        raise error(explanation)

    def _ignore_all_flags(self):
        """Ignore all flags, if they are raised"""
        return self._ignore_flags(*_signals)

    def _ignore_flags(self, *flags):
        """Ignore the flags, if they are raised"""
        # Do not mutate-- This way, copies of a context leave the original
        # alone.
        self._ignored_flags = (self._ignored_flags + list(flags))
        return list(flags)

    def _regard_flags(self, *flags):
        """Stop ignoring the flags, if they are raised"""
        if flags and isinstance(flags[0], (tuple,list)):
            flags = flags[0]
        for flag in flags:
            self._ignored_flags.remove(flag)

    # We inherit object.__hash__, so we must deny this explicitly
    __hash__ = None

    def Etiny(self):
        """Returns Etiny (= Emin - prec + 1)"""
        return int(self.Emin - self.prec + 1)

    def Etop(self):
        """Returns maximum exponent (= Emax - prec + 1)"""
        return int(self.Emax - self.prec + 1)

    def _set_rounding(self, type):
        """Sets the rounding type.

        Sets the rounding type, and returns the current (previous)
        rounding type.  Often used like:

        context = context.copy()
        # so you don't change the calling context
        # if an error occurs in the middle.
        rounding = context._set_rounding(ROUND_UP)
        val = self.__sub__(other, context=context)
        context._set_rounding(rounding)

        This will make it round up for that operation.
        """
        rounding = self.rounding
        self.rounding= type
        return rounding

    def create_decimal(self, num='0'):
        """Creates a new Decimal instance but using self as context.

        This method implements the to-number operation of the
        IBM Decimal specification."""

        if isinstance(num, basestring) and num != num.strip():
            return self._raise_error(ConversionSyntax,
                                     "no trailing or leading whitespace is "
                                     "permitted.")

        d = Decimal(num, context=self)
        if d._isnan() and len(d._int) > self.prec - self._clamp:
            return self._raise_error(ConversionSyntax,
                                     "diagnostic info too long in NaN")
        return d._fix(self)

    def create_decimal_from_float(self, f):
        """Creates a new Decimal instance from a float but rounding using self
        as the context.

        >>> context = Context(prec=5, rounding=ROUND_DOWN)
        >>> context.create_decimal_from_float(3.1415926535897932)
        Decimal('3.1415')
        >>> context = Context(prec=5, traps=[Inexact])
        >>> context.create_decimal_from_float(3.1415926535897932)
        Traceback (most recent call last):
            ...
        Inexact: None

        """
        d = Decimal.from_float(f)       # An exact conversion
        return d._fix(self)             # Apply the context rounding

    # Methods
    def abs(self, a):
        """Returns the absolute value of the operand.

        If the operand is negative, the result is the same as using the minus
        operation on the operand.  Otherwise, the result is the same as using
        the plus operation on the operand.

        >>> ExtendedContext.abs(Decimal('2.1'))
        Decimal('2.1')
        >>> ExtendedContext.abs(Decimal('-100'))
        Decimal('100')
        >>> ExtendedContext.abs(Decimal('101.5'))
        Decimal('101.5')
        >>> ExtendedContext.abs(Decimal('-101.5'))
        Decimal('101.5')
        >>> ExtendedContext.abs(-1)
        Decimal('1')
        """
        a = _convert_other(a, raiseit=True)
        return a.__abs__(context=self)

    def add(self, a, b):
        """Return the sum of the two operands.

        >>> ExtendedContext.add(Decimal('12'), Decimal('7.00'))
        Decimal('19.00')
        >>> ExtendedContext.add(Decimal('1E+2'), Decimal('1.01E+4'))
        Decimal('1.02E+4')
        >>> ExtendedContext.add(1, Decimal(2))
        Decimal('3')
        >>> ExtendedContext.add(Decimal(8), 5)
        Decimal('13')
        >>> ExtendedContext.add(5, 5)
        Decimal('10')
        """
        a = _convert_other(a, raiseit=True)
        r = a.__add__(b, context=self)
        if r is NotImplemented:
            raise TypeError("Unable to convert %s to Decimal" % b)
        else:
            return r

    def _apply(self, a):
        return str(a._fix(self))

    def canonical(self, a):
        """Returns the same Decimal object.

        As we do not have different encodings for the same number, the
        received object already is in its canonical form.

        >>> ExtendedContext.canonical(Decimal('2.50'))
        Decimal('2.50')
        """
        return a.canonical(context=self)

    def compare(self, a, b):
        """Compares values numerically.

        If the signs of the operands differ, a value representing each operand
        ('-1' if the operand is less than zero, '0' if the operand is zero or
        negative zero, or '1' if the operand is greater than zero) is used in
        place of that operand for the comparison instead of the actual
        operand.

        The comparison is then effected by subtracting the second operand from
        the first and then returning a value according to the result of the
        subtraction: '-1' if the result is less than zero, '0' if the result is
        zero or negative zero, or '1' if the result is greater than zero.

        >>> ExtendedContext.compare(Decimal('2.1'), Decimal('3'))
        Decimal('-1')
        >>> ExtendedContext.compare(Decimal('2.1'), Decimal('2.1'))
        Decimal('0')
        >>> ExtendedContext.compare(Decimal('2.1'), Decimal('2.10'))
        Decimal('0')
        >>> ExtendedContext.compare(Decimal('3'), Decimal('2.1'))
        Decimal('1')
        >>> ExtendedContext.compare(Decimal('2.1'), Decimal('-3'))
        Decimal('1')
        >>> ExtendedContext.compare(Decimal('-3'), Decimal('2.1'))
        Decimal('-1')
        >>> ExtendedContext.compare(1, 2)
        Decimal('-1')
        >>> ExtendedContext.compare(Decimal(1), 2)
        Decimal('-1')
        >>> ExtendedContext.compare(1, Decimal(2))
        Decimal('-1')
        """
        a = _convert_other(a, raiseit=True)
        return a.compare(b, context=self)

    def compare_signal(self, a, b):
        """Compares the values of the two operands numerically.

        It's pretty much like compare(), but all NaNs signal, with signaling
        NaNs taking precedence over quiet NaNs.

        >>> c = ExtendedContext
        >>> c.compare_signal(Decimal('2.1'), Decimal('3'))
        Decimal('-1')
        >>> c.compare_signal(Decimal('2.1'), Decimal('2.1'))
        Decimal('0')
        >>> c.flags[InvalidOperation] = 0
        >>> print c.flags[InvalidOperation]
        0
        >>> c.compare_signal(Decimal('NaN'), Decimal('2.1'))
        Decimal('NaN')
        >>> print c.flags[InvalidOperation]
        1
        >>> c.flags[InvalidOperation] = 0
        >>> print c.flags[InvalidOperation]
        0
        >>> c.compare_signal(Decimal('sNaN'), Decimal('2.1'))
        Decimal('NaN')
        >>> print c.flags[InvalidOperation]
        1
        >>> c.compare_signal(-1, 2)
        Decimal('-1')
        >>> c.compare_signal(Decimal(-1), 2)
        Decimal('-1')
        >>> c.compare_signal(-1, Decimal(2))
        Decimal('-1')
        """
        a = _convert_other(a, raiseit=True)
        return a.compare_signal(b, context=self)

    def compare_total(self, a, b):
        """Compares two operands using their abstract representation.

        This is not like the standard compare, which use their numerical
        value. Note that a total ordering is defined for all possible abstract
        representations.

        >>> ExtendedContext.compare_total(Decimal('12.73'), Decimal('127.9'))
        Decimal('-1')
        >>> ExtendedContext.compare_total(Decimal('-127'),  Decimal('12'))
        Decimal('-1')
        >>> ExtendedContext.compare_total(Decimal('12.30'), Decimal('12.3'))
        Decimal('-1')
        >>> ExtendedContext.compare_total(Decimal('12.30'), Decimal('12.30'))
        Decimal('0')
        >>> ExtendedContext.compare_total(Decimal('12.3'),  Decimal('12.300'))
        Decimal('1')
        >>> ExtendedContext.compare_total(Decimal('12.3'),  Decimal('NaN'))
        Decimal('-1')
        >>> ExtendedContext.compare_total(1, 2)
        Decimal('-1')
        >>> ExtendedContext.compare_total(Decimal(1), 2)
        Decimal('-1')
        >>> ExtendedContext.compare_total(1, Decimal(2))
        Decimal('-1')
        """
        a = _convert_other(a, raiseit=True)
        return a.compare_total(b)

    def compare_total_mag(self, a, b):
        """Compares two operands using their abstract representation ignoring sign.

        Like compare_total, but with operand's sign ignored and assumed to be 0.
        """
        a = _convert_other(a, raiseit=True)
        return a.compare_total_mag(b)

    def copy_abs(self, a):
        """Returns a copy of the operand with the sign set to 0.

        >>> ExtendedContext.copy_abs(Decimal('2.1'))
        Decimal('2.1')
        >>> ExtendedContext.copy_abs(Decimal('-100'))
        Decimal('100')
        >>> ExtendedContext.copy_abs(-1)
        Decimal('1')
        """
        a = _convert_other(a, raiseit=True)
        return a.copy_abs()

    def copy_decimal(self, a):
        """Returns a copy of the decimal object.

        >>> ExtendedContext.copy_decimal(Decimal('2.1'))
        Decimal('2.1')
        >>> ExtendedContext.copy_decimal(Decimal('-1.00'))
        Decimal('-1.00')
        >>> ExtendedContext.copy_decimal(1)
        Decimal('1')
        """
        a = _convert_other(a, raiseit=True)
        return Decimal(a)

    def copy_negate(self, a):
        """Returns a copy of the operand with the sign inverted.

        >>> ExtendedContext.copy_negate(Decimal('101.5'))
        Decimal('-101.5')
        >>> ExtendedContext.copy_negate(Decimal('-101.5'))
        Decimal('101.5')
        >>> ExtendedContext.copy_negate(1)
        Decimal('-1')
        """
        a = _convert_other(a, raiseit=True)
        return a.copy_negate()

    def copy_sign(self, a, b):
        """Copies the second operand's sign to the first one.

        In detail, it returns a copy of the first operand with the sign
        equal to the sign of the second operand.

        >>> ExtendedContext.copy_sign(Decimal( '1.50'), Decimal('7.33'))
        Decimal('1.50')
        >>> ExtendedContext.copy_sign(Decimal('-1.50'), Decimal('7.33'))
        Decimal('1.50')
        >>> ExtendedContext.copy_sign(Decimal( '1.50'), Decimal('-7.33'))
        Decimal('-1.50')
        >>> ExtendedContext.copy_sign(Decimal('-1.50'), Decimal('-7.33'))
        Decimal('-1.50')
        >>> ExtendedContext.copy_sign(1, -2)
        Decimal('-1')
        >>> ExtendedContext.copy_sign(Decimal(1), -2)
        Decimal('-1')
        >>> ExtendedContext.copy_sign(1, Decimal(-2))
        Decimal('-1')
        """
        a = _convert_other(a, raiseit=True)
        return a.copy_sign(b)

    def divide(self, a, b):
        """Decimal division in a specified context.

        >>> ExtendedContext.divide(Decimal('1'), Decimal('3'))
        Decimal('0.333333333')
        >>> ExtendedContext.divide(Decimal('2'), Decimal('3'))
        Decimal('0.666666667')
        >>> ExtendedContext.divide(Decimal('5'), Decimal('2'))
        Decimal('2.5')
        >>> ExtendedContext.divide(Decimal('1'), Decimal('10'))
        Decimal('0.1')
        >>> ExtendedContext.divide(Decimal('12'), Decimal('12'))
        Decimal('1')
        >>> ExtendedContext.divide(Decimal('8.00'), Decimal('2'))
        Decimal('4.00')
        >>> ExtendedContext.divide(Decimal('2.400'), Decimal('2.0'))
        Decimal('1.20')
        >>> ExtendedContext.divide(Decimal('1000'), Decimal('100'))
        Decimal('10')
        >>> ExtendedContext.divide(Decimal('1000'), Decimal('1'))
        Decimal('1000')
        >>> ExtendedContext.divide(Decimal('2.40E+6'), Decimal('2'))
        Decimal('1.20E+6')
        >>> ExtendedContext.divide(5, 5)
        Decimal('1')
        >>> ExtendedContext.divide(Decimal(5), 5)
        Decimal('1')
        >>> ExtendedContext.divide(5, Decimal(5))
        Decimal('1')
        """
        a = _convert_other(a, raiseit=True)
        r = a.__div__(b, context=self)
        if r is NotImplemented:
            raise TypeError("Unable to convert %s to Decimal" % b)
        else:
            return r

    def divide_int(self, a, b):
        """Divides two numbers and returns the integer part of the result.

        >>> ExtendedContext.divide_int(Decimal('2'), Decimal('3'))
        Decimal('0')
        >>> ExtendedContext.divide_int(Decimal('10'), Decimal('3'))
        Decimal('3')
        >>> ExtendedContext.divide_int(Decimal('1'), Decimal('0.3'))
        Decimal('3')
        >>> ExtendedContext.divide_int(10, 3)
        Decimal('3')
        >>> ExtendedContext.divide_int(Decimal(10), 3)
        Decimal('3')
        >>> ExtendedContext.divide_int(10, Decimal(3))
        Decimal('3')
        """
        a = _convert_other(a, raiseit=True)
        r = a.__floordiv__(b, context=self)
        if r is NotImplemented:
            raise TypeError("Unable to convert %s to Decimal" % b)
        else:
            return r

    def divmod(self, a, b):
        """Return (a // b, a % b).

        >>> ExtendedContext.divmod(Decimal(8), Decimal(3))
        (Decimal('2'), Decimal('2'))
        >>> ExtendedContext.divmod(Decimal(8), Decimal(4))
        (Decimal('2'), Decimal('0'))
        >>> ExtendedContext.divmod(8, 4)
        (Decimal('2'), Decimal('0'))
        >>> ExtendedContext.divmod(Decimal(8), 4)
        (Decimal('2'), Decimal('0'))
        >>> ExtendedContext.divmod(8, Decimal(4))
        (Decimal('2'), Decimal('0'))
        """
        a = _convert_other(a, raiseit=True)
        r = a.__divmod__(b, context=self)
        if r is NotImplemented:
            raise TypeError("Unable to convert %s to Decimal" % b)
        else:
            return r

    def exp(self, a):
        """Returns e ** a.

        >>> c = ExtendedContext.copy()
        >>> c.Emin = -999
        >>> c.Emax = 999
        >>> c.exp(Decimal('-Infinity'))
        Decimal('0')
        >>> c.exp(Decimal('-1'))
        Decimal('0.367879441')
        >>> c.exp(Decimal('0'))
        Decimal('1')
        >>> c.exp(Decimal('1'))
        Decimal('2.71828183')
        >>> c.exp(Decimal('0.693147181'))
        Decimal('2.00000000')
        >>> c.exp(Decimal('+Infinity'))
        Decimal('Infinity')
        >>> c.exp(10)
        Decimal('22026.4658')
        """
        a =_convert_other(a, raiseit=True)
        r