import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public  static void scroll(WebDriver driver, int height){
        JavascriptExecutor js= (JavascriptExecutor)  driver;
        js.executeScript("window.scrollBy(0,"+height+")");

    }

    public static void main(String[] args) {
       Date today = new Date();
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss:aa");
        String formattedDate = simpleDateFormat.format(today);
        System.out.println(formattedDate);
        double timestamp = today.getTime();
        System.out.println(timestamp);
        String f2 = simpleDateFormat.format(timestamp);
        System.out.println(f2);

    }
}
