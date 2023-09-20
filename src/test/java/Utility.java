import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public class Utility {
    private static ClientApi api;

    public static void activeScan(String webAppUrl, String zapAddress, int zapPort) throws ClientApiException {
        String scanId;
        String proxy_apiKey_zap = Utility.readProperties("proxy_apiKey_zap");
        api = new ClientApi(zapAddress, zapPort, proxy_apiKey_zap);
        api.pscan.setEnabled("true");
        ApiResponse apiResponseEnable = api.ascan.enableAllScanners(null);
        System.out.println("apiResponseEnable : " + apiResponseEnable.getName());
        ApiResponse apiResponse = api.spider.scan(webAppUrl, null, null, null, null);
        System.out.println("apiResponse : " + apiResponse.getName());
        ApiResponse apiResponseActive = api.ascan.scan(webAppUrl, "True", "False", null, null, null);
        System.out.println("apiResponseActive : " + apiResponseActive.getName());

        scanId = ((ApiResponseElement)apiResponseActive).getValue();
        ApiResponse activeScanStatus = api.ascan.status(scanId);
        String statusAs = ((ApiResponseElement)activeScanStatus).getValue();
        System.out.println("statusAs : " + statusAs);
        while(!statusAs.equals("100")){
            activeScanStatus = api.ascan.status(scanId);
            statusAs = ((ApiResponseElement)activeScanStatus).getValue();
            System.out.println("statusAs : " + statusAs);
        }
    }

    public static void getReports() throws ClientApiException {
        if(api!=null){
            String title = Utility.readProperties("reportTitle");
            String description = Utility.readProperties("reportDescription");
            String reportFileName = Utility.readProperties("reportFileName");
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
}
