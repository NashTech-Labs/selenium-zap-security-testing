package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.example.ZapUtil.*;
public class ZapTest {

    static String urlToTest;

    @Test
    public void demoTest() {
        Dotenv dotenv = Dotenv.load();
        urlToTest = dotenv.get("appUrl");
        addURLToScanTree(urlToTest);
        startActiveScan(urlToTest);
        //passive scan
//        waitForPassiveScanCompletion ();
        // active scan
        waitForActiveScanCompletion();
    }

    @AfterMethod
    public void tearDown(){
    ZapUtil.createReport(urlToTest);
    }
}
