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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("social-oidc")));
        driver.findElement(By.id("social-oidc")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='loginfmt']")));
        WebElement loginId = driver.findElement(By.xpath("//input[@name='loginfmt']"));
        loginId.sendKeys(userData.get(1));
        WebElement nextBtn = driver.findElement(By.id("idSIButton9"));
        nextBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0118")));
        WebElement passwordLogin = driver.findElement(By.id("i0118"));
        passwordLogin.sendKeys(userData.get(2));
        WebElement signIn = driver.findElement(By.id("idSIButton9"));
        signIn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("displayName")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idSIButton9")));
        WebElement yesBtn = driver.findElement(By.id("idSIButton9"));
        yesBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Deepansh Gupta']")));
    }

    public static String loginToWebApp(WebDriver driver) throws IOException {
        String appUrl = Utility.readProperties("appUrl");;
        driver.get(appUrl);
        SignIn.signInWithSavedDetails(driver);
        return driver.getCurrentUrl();
    }
}
