package org.mozilla.benchmark.gSheetReport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by andrei.filip on 12/21/2017.
 */
public class GSheetUtils {

    public static List<List<Object>> convertReportObject(String resultObject){
        JSONArray results=JSONArray.fromString(resultObject);
        List<String> runValues=new ArrayList<>();
        List<List<Object>> matrix=new ArrayList<>();
        Iterator<?> keys=null;

        if(results!=null){
            for(int i=0;i<results.length();i++){
                JSONObject object=JSONObject.fromObject(results.getJSONObject(i));
                keys=object.keys();
                while(keys.hasNext()){
                    String key= (String) keys.next();
                    runValues.add(object.getString(key));
                }
                List<Object> convertToObjects= new ArrayList<Object>(runValues);
                matrix.add(convertToObjects);
                runValues=new ArrayList<>();


            }
        }


        return matrix;
    }
}
