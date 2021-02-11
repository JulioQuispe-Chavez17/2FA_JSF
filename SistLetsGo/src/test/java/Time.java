import java.util.Random;

public class Time {
    public static void main(String[] args) {
        String twoFaCode = String.valueOf(new Random().nextInt(9999) + 1000);
        System.out.println((System.currentTimeMillis()/100)+120);
        System.out.println(twoFaCode);
        /* Tabla Usuario
        * IDUSU
        * EMLUSU
        * PSSUSU
        * 2FA_CODE
        * 2FA_EXPIRE_TIME
        * */
    }
}
