import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
public class UserProfile {
    public static void userProfilePage(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='navbar-nav justify-content-end']//*[@class='material-icons user-icon'][normalize-space()='settings']")));
        // user profile Page
        WebElement setting = driver.findElement(By.xpath("//ul[@class='navbar-nav justify-content-end']//*[@class='material-icons user-icon'][normalize-space()='settings']"));
        setting.click();
        WebElement profile = driver.findElement(By.xpath("//h6/span[text()='Profile']"));
        profile.click();
    }

    public static String getUserProfile(WebDriver driver) throws IOException {
        String appUrl = Utility.readProperties("appUrl");;
        driver.get(appUrl);
        SignIn.signInWithSavedDetails(driver);
        UserProfile.userProfilePage(driver);
        return driver.getCurrentUrl();
    }
}
