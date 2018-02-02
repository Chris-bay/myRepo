package MiningOverview;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Controller
public class RestAPI {

    static String completeMiningHistoryPath = "MiningHistory.json";

    @RequestMapping("/api/history")
    @ResponseBody
    public String dummyJSON(){

        JSONParser parser = new JSONParser();

        Object oldFileContent = null;

        try {
            BufferedReader in = new BufferedReader(new FileReader(completeMiningHistoryPath));//load File
            // buffer for storing file contents in memory
            StringBuffer stringBuffer = new StringBuffer("");
            // for reading one line
            String oldLine = null;
            // keep reading till readLine returns null
            while ((oldLine = in.readLine()) != null) {
                // keep appending last line read to buffer
                stringBuffer.append(oldLine);
            }

            //System.out.println("Length: " + stringBuffer.length());
            oldFileContent = parser.parse(stringBuffer.toString());//parse to object

        } catch (Exception e) {
            e.printStackTrace();
        }
        //JSONArray jsonArray = (JSONArray) oldFileContent;//convert object to jsonArray
        JSONObject jsonObject = (JSONObject) oldFileContent;

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.defaultPrettyPrintingWriter().writeValueAsString(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
