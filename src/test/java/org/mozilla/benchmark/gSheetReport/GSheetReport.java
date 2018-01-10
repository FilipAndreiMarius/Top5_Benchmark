package org.mozilla.benchmark.gSheetReport;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.openqa.selenium.net.UrlChecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import static org.mozilla.benchmark.gSheetReport.GSheetConstants.*;
import static org.mozilla.benchmark.gSheetReport.GSheetUtils.convertReportObject;

/**
 * Created by andrei.filip on 12/8/2017.
 */

public class GSheetReport extends GSheetService {

    public Spreadsheet getSheet() {
        return sheet;
    }

    public void setSheet(Spreadsheet sheet) {
        this.sheet = sheet;
    }

    Spreadsheet sheet;



    public GSheetReport() throws Exception {
      this.sheet=createNewSpreadSheet();


    }


    private void generateReportObject(Spreadsheet spreadsheet, Sheets sheets, List<Object> Headers, String testResults, String scenarioName) throws IOException {

        String id = spreadsheet.getSpreadsheetId();
        String HeaderRange = scenarioName + "!B1:G10";
        String RunNr = scenarioName + "!A1:A11";
        List<List<Object>> runColumnNr = new ArrayList<>();

        runColumnNr.add(RunListObject);
        List<List<Object>> writeData = new ArrayList<>();
        List<List<Object>> resultValues = convertReportObject(testResults);
        //adding headers
        writeData.add(Headers);
        //results values
        for (List<Object> resultLine : resultValues) {
            writeData.add(resultLine);
        }
        /*headersValues Eq: Navigation_Start","First_Non_Blank","Hero_Element","Image_Start","First_Non_Blank","Hero_Element.
        -The headers will be created from RANGE B1 to G10
        -if an extra header is needed the Range need to be increased
        */
        ValueRange headersValues = new ValueRange().setValues(writeData).setMajorDimension("ROWS");

       /*RunColumn

        */
        ValueRange RunColumn = new ValueRange().setValues(runColumnNr).setMajorDimension("COLUMNS");
        //update operation for
        sheets.spreadsheets().values().update(id, HeaderRange, headersValues).setValueInputOption("RAW").execute();

        sheets.spreadsheets().values().update(id, RunNr, RunColumn).setValueInputOption("RAW").execute();


    }


    public  void writeData(String testResults, Spreadsheet spreadsheet, String testName) throws Exception {

        //Create an Authentication Service
        Sheets service = getSheetsService();


        try {

            switch (testName) {
                case "GoogleSearch": {
                    generateReportObject(spreadsheet, service, GSearchHeaderObject, testResults, testName);
                    break;

                }
                case "Gmail": {
                    generateReportObject(spreadsheet, service, GmailHeaderObjects, testResults, testName);
                    break;

                }
                case "Facebook": {
                    generateReportObject(spreadsheet, service, FacebookHeaderObjects, testResults, testName);
                    break;

                }
                case "Youtube": {
                    generateReportObject(spreadsheet, service, YoutubeHeaderObjects, testResults, testName);
                    break;

                }
                case "Amazon": {
                    generateReportObject(spreadsheet, service, AmazonHeaderObjects, testResults, testName);
                    break;

                }

            }

        } catch (Exception e) {
            System.out.print(e);
        }
    }

    private  Spreadsheet createNewSpreadSheet() throws Exception {

        List<Sheet> tabpages = generateSheetList(Scenarios);
        Spreadsheet spreadsheet = new Spreadsheet();
        spreadsheet.setProperties(new SpreadsheetProperties().setTitle("Automation Benchmarking"));
        spreadsheet.setSheets(tabpages);
        Spreadsheet response = null;
        try {
            //login with requested credentials
            Sheets sheetsService = getSheetsService();
            Sheets.Spreadsheets.Create requestObj = sheetsService.spreadsheets().create(spreadsheet);
            response = requestObj.execute();

        } catch (UrlChecker.TimeoutException timeout) {
            System.out.print(timeout);
        }
        System.out.print(response.toPrettyString());

        return response;


    }

    private static List<Sheet> generateSheetList(List<String> Scenarios) {
        List<Sheet> sheetScenarios = new ArrayList<Sheet>();
        for (String scenario : Scenarios) {
            Sheet tabScenario = new Sheet();
            tabScenario.setProperties(new SheetProperties().setTitle(scenario));
            sheetScenarios.add(tabScenario);
        }
        return sheetScenarios;

    }


    private static String getSpreadsheetId(Spreadsheet spreadsheet) {
        String id = "";
        if (spreadsheet != null) {
            JSONObject responseObject = JSONObject.fromObject(spreadsheet);
            Iterator<?> keys = responseObject.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (key == "spreadsheetId") {
                    id = responseObject.getString(key);
                }
            }

        }
        return id;
    }

    private static String getSheetTitle(Spreadsheet spreadsheet) {

        String title = "";
        if (spreadsheet != null) {
            JSONObject responseObject = JSONObject.fromObject(spreadsheet);
            Iterator<?> keys = responseObject.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (key == "sheets") {
                    JSONArray sheets = (JSONArray) responseObject.get(key);
                    JSONObject sheet = sheets.getJSONObject(0).getJSONObject("properties");
                    title = sheet.getString("title");

                }
            }
        }
        return title;

    }


    public static void main(String[] args) throws Exception {

        //this approach should be used if we report the results sequentially and the write data should be modified to take the SpreadSheet id as an argument
        // String id = getSpreadsheetId(sheet);

        GSheetReport report=new GSheetReport();
        report.writeData(resultList,report.getSheet(),"GoogleSearch");
        report.writeData(resultList,report.getSheet(),"Gmail");
        report.writeData(resultList,report.getSheet(),"Facebook");
        report.writeData(resultList,report.getSheet(),"Youtube");
        report.writeData(resultList,report.getSheet(),"Amazon");








    }
}