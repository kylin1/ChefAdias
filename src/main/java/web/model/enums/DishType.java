//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package web.model.enums;

public enum DishType {
    NODDLE("noddle"),
    PIZZA("pizza"),
    SALAD("salad"),
    CHICKEN("chicken");

    private String strType;

    private DishType(String type1) {
        this.strType = type1;
    }

    public static DishType getDishType(String stringType) {
        DishType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            DishType dishType = var1[var3];
            if(dishType.strType.equals(stringType)) {
                return dishType;
            }
        }

        return null;
    }
}
