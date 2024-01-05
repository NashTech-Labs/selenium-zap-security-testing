import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class Knolx {
    public static void knolxPage(WebDriver driver) throws IOException{
        String appUrl = Utility.readProperties("appUrl");;
        driver.get(appUrl);
        SignIn.signInWithSavedDetails(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        // Click on knolx tab
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@role='button']/h6[contains(text(),'Knolx')]")));
        WebElement knolxBtn = driver.findElement(By.xpath("//*[@role='button']/h6[contains(text(),'Knolx')]"));
        knolxBtn.click();
        System.out.println("click on knolx button");
    }
    public static String getKnolxReportsPage(WebDriver driver) throws IOException {
        Knolx.knolxPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        // Click on reports tab
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/knolx/reports']")));
        WebElement reportBtn = driver.findElement(By.cssSelector("a[href='/knolx/reports']"));
        reportBtn.click();
        System.out.println("click on report button");
        return driver.getCurrentUrl();
    }
    public static String getKnolxSessionsPage(WebDriver driver) throws IOException {
        Knolx.knolxPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        // Click on sessions tab
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/knolx/upcoming-sessions']")));
        WebElement sessionsBtn = driver.findElement(By.cssSelector("a[href='/knolx/upcoming-sessions']"));
        sessionsBtn.click();
        System.out.println("click on sessions button");
        return driver.getCurrentUrl();
    }
    public static String getKnolxBookASessionPage(WebDriver driver) throws IOException {
        Knolx.knolxPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        // Click on book a sessions tab
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/knolx/book-a-session']")));
        WebElement bookAsessionBTn = driver.findElement(By.cssSelector("a[href='/knolx/book-a-session']"));
        bookAsessionBTn.click();
        System.out.println("click on book a session button");
        return driver.getCurrentUrl();
    }
    public static String getKnolxMySessionsPage(WebDriver driver) throws IOException {
        Knolx.knolxPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        // Click on My sessions tab
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/knolx/my-sessions/my-upcoming-sessions']")));
        WebElement mySessionsTab = driver.findElement(By.cssSelector("a[href='/knolx/my-sessions/my-upcoming-sessions']"));
        mySessionsTab.click();
        System.out.println("click on My Session button");
        return driver.getCurrentUrl();
    }
}
