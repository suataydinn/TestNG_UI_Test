package utilities;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.OutputType;
import java.io.File;
import java.io.IOException;
import base.DriverManager;


public class TestUtils {

    public static void takeScreenshot(String testName) {
        File screenshot = ((TakesScreenshot) DriverManager.get()).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String directoryPath = "screenshots";
        String filePath = directoryPath + "/" + testName + "_" + timestamp + ".png";

        try {
            // "screenshots" dizinini oluşturma
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Ekran görüntüsünü kaydetme
            FileHandler.copy(screenshot, new File(filePath));
            System.out.println("Screenshot saved at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
