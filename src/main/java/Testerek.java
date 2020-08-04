import java.util.Locale;

public class Testerek {

    public static void main(String[] args) {
        double x = 1.1234567890123;
        String b ="sdsddsfsfsdfsd";
        String wyj = String.format(Locale.US,"%.10f;%.2f;%f;%s", x, x, x,b);
        System.out.println(wyj);
        //        System.out.format(Locale.US,"[%.10f] [%.2f] [%f]", x, x, x);

    }
}
