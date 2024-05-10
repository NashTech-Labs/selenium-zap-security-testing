import io.github.cdimascio.dotenv.Dotenv;
import org.frontend.SignIn;
import org.frontend.Utility;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.*;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;
import java.lang.reflect.Method;
import java.util.Objects;

public class SeleniumZap {
    static Dotenv dotenv = Dotenv.load();
    static final String proxy_address_zap = dotenv.get("proxy_address_zap");
    static final int proxy_port_zap = Integer.parseInt(Objects.requireNonNull(dotenv.get("proxy_port_zap")));
    static WebDriver driver;
    private static ClientApi api;
    static String proxy_apiKey_zap = dotenv.get("proxy_apiKey_zap");

    @BeforeMethod
    public void startUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        assert proxy_address_zap != null;
        api = new ClientApi(proxy_address_zap, proxy_port_zap, proxy_apiKey_zap);
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
    public void testLoginSecurity() throws ClientApiException {
        String currentUrl = SignIn.loginToWebApp(driver);
        System.out.println("currentUrl : " + currentUrl);
        Utility.AllScan(currentUrl, proxy_address_zap, proxy_port_zap, api, dotenv.get("ActiveScan"));
    }

    @AfterMethod
    public void tearDown(Method method) throws ClientApiException {
        Utility.getReports(api, method.getName().replace("test",""));
        Utility.cleanTheScanTree(api);
        driver.quit();
        System.out.println("Close browser");
    }
}
