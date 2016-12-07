package web.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.TicketDao;
import web.dao.opearion.TicketOperation;
import web.dao.util.MybatisUtils;
import web.model.po.Ticket;
import web.tools.MyMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 06/12/2016.
 * All rights reserved.
 */
@Transactional
@Repository("ticketDao")
public class TicketDaoImpl implements TicketDao {

    SqlSession session;
    TicketOperation operation;

    @Override
    public List<Ticket> getAllTicket() {
        List<Ticket> list = new ArrayList<>();
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(TicketOperation.class);
            list = this.operation.getAllTicket();
            this.session.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            this.session.rollback();
        }finally {
            this.session.close();
        }
        return list;
    }

    @Override
    public Ticket getTicket(int id) {
        Ticket ticket = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(TicketOperation.class);
            ticket = this.operation.getTicket(id);
            this.session.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            this.session.rollback();
        }finally {
            this.session.close();
        }
        return ticket;
    }

    @Override
    public MyMessage addTicket(Ticket newTicket) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(TicketOperation.class);
            this.operation.insertTicket(newTicket);
            this.session.commit();
            myMessage =  new MyMessage(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            myMessage = new MyMessage(false, 0, ex.getMessage());
        } finally {
            this.session.close();
        }
        return myMessage;
    }

    @Override
    public MyMessage updateTicket(Ticket ticket) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(TicketOperation.class);
            this.operation.update(ticket);
            this.session.commit();
            myMessage =  new MyMessage(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            myMessage = new MyMessage(false, 0, ex.getMessage());
        } finally {
            this.session.close();
        }
        return myMessage;
    }

    @Override
    public MyMessage deleteTicket(int id) {
        MyMessage myMessage = null;
        try {
            this.session = MybatisUtils.getSession();
            this.operation = this.session.getMapper(TicketOperation.class);
            this.operation.delete(id);
            this.session.commit();
            myMessage =  new MyMessage(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.session.rollback();
            myMessage = new MyMessage(false, 0, ex.getMessage());
        } finally {
            this.session.close();
        }
        return myMessage;
    }
}
