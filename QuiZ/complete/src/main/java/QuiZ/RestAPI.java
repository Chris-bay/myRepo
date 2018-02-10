package QuiZ;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class RestAPI {
    @RequestMapping("/api/getAllQuiz")
    @ResponseBody
    public String Quiz(){
        JSONArray retArray = new JSONArray();
        File f = new File("./data");
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
        for (String n:names
             ) {
            if (!n.substring(0,4).equals("Quiz_")){
                names.remove(n);
            }
        }
        retArray.addAll(names);
        return retArray.toJSONString();
    }

    @RequestMapping(value="/api/getquiz/{quizId}", method= RequestMethod.GET)
    @ResponseBody
    public String findOwner(@PathVariable String quizId) {
        JSONParser parser = new JSONParser();

        Object oldFileContent = null;

        try {
            BufferedReader in = new BufferedReader(new FileReader("data/Quiz_" + quizId + ".json"));//load File
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

        return jsonObject.toJSONString();
    }

}
