package assignment_7.src.utils;

public class SafeConverter {
    public static int ToSafeInt(Object o){
        int id = 0;
        try{
            id = Integer.parseInt(o == null ? "0" : o.toString());
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
        return id;
    }

    public static String toSafeString(Object o){
        return o == null ? "" : o.toString();
    }
}
