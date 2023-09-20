import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.zaproxy.clientapi.core.ClientApiException;
import java.io.IOException;
import java.util.Objects;

public class SeleniumZap {
    static final String proxy_address_zap = Utility.readProperties("proxy_address_zap");
    static final int proxy_port_zap = Integer.parseInt(Objects.requireNonNull(Utility.readProperties("proxy_port_zap")));;
    static WebDriver driver;

    @BeforeMethod
    public void startUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        String proxyUrl = proxy_address_zap + ":" + proxy_port_zap;
        System.out.println("proxyUrl : " + proxyUrl);
        Proxy proxyServer = new Proxy();
        proxyServer.setHttpProxy(proxyUrl)
                .setSslProxy(proxyUrl);
        options.setCapability(CapabilityType.PROXY, proxyServer);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testLoginSecurity() throws ClientApiException, IOException {
        String currentUrl = SignIn.loginToWebApp(driver);
        System.out.println("currentUrl : " + currentUrl);
        Utility.activeScan(currentUrl, proxy_address_zap, proxy_port_zap);
    }

    @AfterMethod
    public void tearDown() throws ClientApiException {
        Utility.getReports();
        driver.close();
    }
}
