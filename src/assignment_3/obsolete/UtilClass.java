package assignment_3.obsolete;

public class UtilClass {

    // a little bit modified version of Integer.valueOf()
    public static int valueOf(String string){
        try{
            return Integer.valueOf(string);
        }
        catch(NumberFormatException nfe){
            return -1;
        }
    }
}
