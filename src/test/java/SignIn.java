import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SignIn {
    static Dotenv dotenv = Dotenv.load();
    public static void signInWithSavedDetails(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement loginId = driver.findElement(By.id("username"));
        loginId.sendKeys(dotenv.get("userId"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        WebElement passwordLogin = driver.findElement(By.id("password"));
        passwordLogin.sendKeys(dotenv.get("password"));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("kc-login")));
        WebElement signIn = wait.until(ExpectedConditions.elementToBeClickable(By.id("kc-login")));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", signIn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Welcome, ']")));
    }

    public static String loginToWebApp(WebDriver driver) {
        String appUrl = dotenv.get("appUrlFrontend");
        driver.get(appUrl);
        SignIn.signInWithSavedDetails(driver);
        return driver.getCurrentUrl();
    }
}
