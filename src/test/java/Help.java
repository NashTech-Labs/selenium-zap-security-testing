import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class Help {
        public static void helpPage(WebDriver driver){
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            // Click on Help tab
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/help']")));
            WebElement helpTab = driver.findElement(By.cssSelector("a[href='/help']"));
            helpTab.click();
            System.out.println("click on My Help button");
        }
        public static String getHelpPage(WebDriver driver) throws IOException {
            String appUrl = Utility.readProperties("appUrl");;
            driver.get(appUrl);
            SignIn.signInWithSavedDetails(driver);
            Help.helpPage(driver);
            return driver.getCurrentUrl();
        }
    }


