import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class Leaderboard {

    public static String getLeaderboardSummaryPage (WebDriver driver) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        SignIn.loginToWebApp(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".navbar-nav .nav-item:nth-child(3) h6")));
        WebElement LeaderboardBtn = driver.findElement(By.cssSelector(".navbar-nav .nav-item:nth-child(3) h6"));
        LeaderboardBtn.click();
        WebElement SummaryBtn = driver.findElement(By.cssSelector("a[href='/dashboard']"));
        SummaryBtn.click();
        System.out.println("click on Leaderboard Summary tab");
        return driver.getCurrentUrl();
    }

    public static String getLeaderboardWallOfFamePage (WebDriver driver) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        SignIn.loginToWebApp(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".navbar-nav .nav-item:nth-child(3) h6")));
        WebElement LeaderboardBtn = driver.findElement(By.cssSelector(".navbar-nav .nav-item:nth-child(3) h6"));
        LeaderboardBtn.click();
        WebElement WallOfFameBtn = driver.findElement(By.cssSelector("a[href='/hall-of-fame']"));
        WallOfFameBtn.click();
        System.out.println("click on Leaderboard Summary tab");
        return driver.getCurrentUrl();
    }

    public static String getLeaderboardRewardsPage (WebDriver driver) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        SignIn.loginToWebApp(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".navbar-nav .nav-item:nth-child(3) h6")));
        WebElement LeaderboardBtn = driver.findElement(By.cssSelector(".navbar-nav .nav-item:nth-child(3) h6"));
        LeaderboardBtn.click();
        WebElement RewardsBtn = driver.findElement(By.cssSelector("a[href='/rewards/list']"));
        RewardsBtn.click();
        System.out.println("click on Leaderboard Summary tab");
        return driver.getCurrentUrl();
    }


}
