package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.Proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ZapUtil {
    static Dotenv dotenv = Dotenv.load();
    private static final RequestSpecification requestSpecification;
    public static Proxy proxy;
    private static Response response;
    private static final String zapAddress;
    private static final String apiKey ;
    private static final int zapPort;

    static {

    zapAddress = dotenv.get("proxy_address_zap");
    zapPort = Integer.parseInt(Objects.requireNonNull(dotenv.get("proxy_port_zap")));
    apiKey = dotenv.get("proxy_apiKey_zap");
            requestSpecification = RestAssured.given();
            requestSpecification.baseUri("http://" + zapAddress + ":" + zapPort + "/JSON");
            requestSpecification.queryParam("apikey", apiKey);

            proxy = new Proxy().setSslProxy(zapAddress + ":" + zapPort).setHttpProxy(zapAddress + ":" + zapPort);
        }


        public static void waitForPassiveScanCompletion () {
            response = requestSpecification.get("/pscan/view/recordsToScan/");
            String records = response.jsonPath().get("recordsToScan");

            while (!records.equals("0")) {
                System.out.println("under passive scanning");
                response = requestSpecification.get("/pscan/view/recordsToScan/");
                records = response.jsonPath().get("recordsToScan");
            }
            System.out.println("passive scan is completed");
        }

        public static void addURLToScanTree (String site_to_test){
            requestSpecification.queryParam("url", site_to_test);

            response = requestSpecification.get("/core/action/accessUrl/");
            if (response.getStatusCode() == 200)
                System.out.println("URL has been added to Scan tree");
        }

        public static void startActiveScan (String site_to_test){
            requestSpecification.queryParam("url", site_to_test);

            response = requestSpecification.get("/ascan/action/scan/");
            if (response.getStatusCode() == 200)
                System.out.println("active scan has started");
        }

        public static void waitForActiveScanCompletion () {
            response = requestSpecification.get("/ascan/view/status/");
            String status = response.jsonPath().get("status");

            while (!status.equals("100")) {
                System.out.println("active scan is in progress");
                response = requestSpecification.get("/ascan/view/status/");
                status = response.jsonPath().get("status");
            }
            System.out.println("active scan has completed");
        }


        public static void createReport (String site_to_test){
            String title = dotenv.get("reportTitle");
            String template = "traditional-html";
            String description = dotenv.get("reportDescription");
            String sites = site_to_test;
            String reportfilename = dotenv.get("reportFileName");
            String reportdir = System.getProperty("user.dir");

            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("title", title);
            queryParams.put("template", template);
            queryParams.put("description", description);
            queryParams.put("sites", sites);
            queryParams.put("reportFileName", reportfilename);
            queryParams.put("reportDir", reportdir);

            response = requestSpecification.queryParams(queryParams).get("/reports/action/generate/");
            assert reportfilename != null;
            if (response.body().asString().contains(reportfilename)) {
                System.out.println("Zap report has been created");
            } else {
                throw new RuntimeException("zap report not created");
            }

        }
    }

