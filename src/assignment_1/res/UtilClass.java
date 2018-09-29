package res;
import java.util.Random;

public class UtilClass {
    private static Random random = new Random();

public static int getRandomNumber(int range){
        return random.nextInt(range) + 1;

    }

public static boolean isPrime(int number){
    if(number == 1)
        return false;

    for(int i=2; i<=((int)Math.sqrt(number)); i++){
        if(number%i == 0){
            return false;
        }
    }
    return true;
}
}
