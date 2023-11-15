import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

public class SignIn {

    public static void signInWithSavedDetails(WebDriver driver) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        ArrayList<String> userData= Utility.getTestData("Account","Account");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement loginId = driver.findElement(By.id("username"));
        loginId.sendKeys(userData.get(1));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        WebElement passwordLogin = driver.findElement(By.id("password"));
        passwordLogin.sendKeys(userData.get(2));
        WebElement signIn = driver.findElement(By.id("kc-login"));
        signIn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Welcome, ']")));
    }

    public static String loginToWebApp(WebDriver driver) throws IOException {
        String appUrl = Utility.readProperties("appUrl");;
        driver.get(appUrl);
        SignIn.signInWithSavedDetails(driver);
        return driver.getCurrentUrl();
    }
}
