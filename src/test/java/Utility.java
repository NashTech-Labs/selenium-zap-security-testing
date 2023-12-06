import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zaproxy.clientapi.core.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Utility {
    static ApiResponse apiResponse;

    public static void AllScan(String webAppUrl,String zapAddress, int zapPort, ClientApi api, String activeScanBool) throws ClientApiException {
        System.out.println("Starting Spider Scan");
        apiResponse = api.spider.scan(webAppUrl, null, null, null, null);
        String spiderScanId = ((ApiResponseElement)apiResponse).getValue();
        ApiResponse spiderScanStatus = api.spider.status(spiderScanId);
        String statusAs = ((ApiResponseElement)spiderScanStatus).getValue();
        while(!statusAs.equals("100")){
            spiderScanStatus = api.spider.status(spiderScanId);
            statusAs = ((ApiResponseElement)spiderScanStatus).getValue();

        }
        System.out.println("Spider scan completed");

        waitTillPassiveScanCompleted(api);

        addUrlScanTree(webAppUrl,api);

        if(activeScanBool!=null&&activeScanBool.equalsIgnoreCase("true")){
            System.out.println("Starting Active Scan");
            api.ascan.enableAllScanners(null);
            activeScan(webAppUrl, zapAddress, zapPort, api);
            System.out.println("Active scan completed");
        }
    }

    public static void waitTillPassiveScanCompleted(ClientApi api) throws ClientApiException {
        System.out.println("Starting Passive Scan");
        ApiResponse apiResponsePs = api.pscan.recordsToScan();
        String scanRecords = ((ApiResponseElement)apiResponsePs).getValue();
        while(!scanRecords.equals("0")){
            apiResponsePs = api.pscan.recordsToScan();
            scanRecords = ((ApiResponseElement)apiResponsePs).getValue();
        }
        System.out.println("Passive scan completed");
    }

    public static void addUrlScanTree(String webAppUrl, ClientApi api) throws ClientApiException {
        api.core.accessUrl(webAppUrl, "false");
        if(getUrlsFromScanTree(api).contains(webAppUrl)){
            System.out.println("Sites to test : " + getUrlsFromScanTree(api));
        }
    }

    public static List<String>  getUrlsFromScanTree(ClientApi api) throws ClientApiException {
        apiResponse = api.core.urls();
        List<ApiResponse> urlList= ((ApiResponseList)apiResponse).getItems();
        return urlList.stream().map(r -> ((ApiResponseElement)r).getValue()).collect(Collectors.toList());
    }


    public static void activeScan(String webAppUrl, String zapAddress, int zapPort, ClientApi api) throws ClientApiException {
        String scanId;
        apiResponse = api.ascan.scan(webAppUrl, "True", "False", null, null, null);
        System.out.println("apiResponseActive : " + apiResponse.getName());

        scanId = ((ApiResponseElement)apiResponse).getValue();
        ApiResponse activeScanStatus = api.ascan.status(scanId);
        String statusAs = ((ApiResponseElement)activeScanStatus).getValue();
        while(!statusAs.equals("100")){
            activeScanStatus = api.ascan.status(scanId);
            statusAs = ((ApiResponseElement)activeScanStatus).getValue();
        }
    }

    public static void getReports(ClientApi api, String fileName) throws ClientApiException {
        if(api!=null){
            String title = Utility.readProperties("reportTitle");
            String description = Utility.readProperties("reportDescription");
            String reportFileName = fileName;
//                    Utility.readProperties("reportFileName");
            String targetFolder = System.getProperty("user.dir");
            String template = "traditional-html";
            ApiResponse response = api.reports.generate(title, template, null,
                    description, null,
                    null, null, null,
                    null, reportFileName, null,
                    targetFolder, null );
            System.out.println("ZAP report location: " + response.toString());
        }
    }

    public static ArrayList<String> getTestData(String sheetName, String testCase) throws IOException {
        ArrayList<String> arrayList= new ArrayList<>();
        FileInputStream file = new FileInputStream("src/main/resources/searchKeys.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        int sheetNumber=workbook.getNumberOfSheets();
        for(int i=0;i<sheetNumber;i++){
            if(workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rows=sheet.iterator();
                Row first= rows.next();
                Iterator<Cell> cell=first.cellIterator();
                int r=0;
                int c=0;
                while (cell.hasNext()){
                    Cell value=cell.next();
                    if(value.getStringCellValue().equalsIgnoreCase("Test Cases")){
                        c=r;
                    }
                    r++;
                }
                while (rows.hasNext()){
                    Row r2=rows.next();
                    if(r2.getCell(c).getStringCellValue().equalsIgnoreCase(testCase)){
                        Iterator<Cell> cv=r2.cellIterator();
                        while (cv.hasNext()){
                            Cell cell2= cv.next();
                            if(cell2.getCellType()== CellType.STRING){
                                arrayList.add(cell2.getStringCellValue());
                            }
                            else {
                                arrayList.add(NumberToTextConverter.toText(cell2.getNumericCellValue()));
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }


    public static String readProperties(String propertyName)  {
        try{
            Properties properties = new Properties();
            FileInputStream input;
            input = new FileInputStream("application.properties");
            properties.load(input);
            return properties.getProperty(propertyName).trim();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public static void cleanTheScanTree(ClientApi api) throws ClientApiException {
        List<String> urls=getUrlsFromScanTree(api);
        for (String url:urls){
            if(getUrlsFromScanTree(api).stream().anyMatch(s->s.contains(url))){
                api.core.deleteSiteNode(url,"","");
            }
        }
        if(getUrlsFromScanTree(api).isEmpty())
            System.out.println("scan tree has been cleared successfully");
        else
            throw new RuntimeException("scan tree was not cleared");

    }
}
