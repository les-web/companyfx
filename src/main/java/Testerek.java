import model.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Locale;

public class Testerek {
    String pathFile = Paths.get("").toAbsolutePath().toString() +
            "\\src\\main\\java\\utility\\testerek.csv";
    public void saveProductsInFile() {
        try {
            FileWriter pw = new FileWriter(new File(pathFile));
            //        pw.append("id;nazwa;kategoria;cena;lość" + "\n");
            pw.write("id;nazwa;kategoria;cena;lość");
double y = 3212.23;
            String format = "";
   //         for (Product product : products) {
                format = new StringBuilder().append("\n").append(String.format(
                        Locale.US,
                        "%s;%s;%s;%s;%s",
                        1,
                        "sdfsdfsdsfd ssdf",
                        "SILNIK",
                        y,
                        100
                )).toString();
                pw.write(format);
//                pw.append(format + "\n");
 //           }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        double x = 1.1234567890123;
        String b ="sdsddsfsfsdfsd";
        String wyj = String.format(Locale.US,"%.10f;%.2f;%f;%s", x, x, x,b);
        System.out.println(wyj);
        //        System.out.format(Locale.US,"[%.10f] [%.2f] [%f]", x, x, x);
Testerek testus = new Testerek();
        testus.saveProductsInFile();

    }
}
