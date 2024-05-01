package navigationTestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class Radar {

    public static String getRadarPage (WebDriver driver) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".navbar-nav .nav-item:nth-child(2) h6")));
        WebElement radarBtn = driver.findElement(By.cssSelector(".navbar-nav .nav-item:nth-child(2) h6"));
        radarBtn.click();
        System.out.println("click on navigationTestCases.Radar button");
        return driver.getCurrentUrl();
    }

    public static String getRadarTechPage (WebDriver driver) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".navbar-nav .nav-item:nth-child(2) h6")));
        WebElement radarBtn = driver.findElement(By.cssSelector(".navbar-nav .nav-item:nth-child(2) h6"));
        radarBtn.click();
        WebElement techBtn = driver.findElement(By.cssSelector("a[href='/radar/list']"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/radar/list']")));
        techBtn.click();
        System.out.println("click on Technologies button");
        return driver.getCurrentUrl();
    }


}
