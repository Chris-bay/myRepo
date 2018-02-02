package QuiZ;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;

public class Quiz {

    ArrayList<Question> questions = new ArrayList<>();

    String datapath = "Quiz.json";

    public Question createNewQuestion(String question, String[] answers){
        String id = question + questions.size();

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(id.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        Question q = new Question(sb.toString(), question, answers);

        questions.add(q);

        return q;
    }

    public Optional<Question> getQuestion(String question){
        Optional<Question> retq = null;
        for(Question q : questions){
            if(q.getQuestion().equals(question)){
                retq = Optional.of(q);
            }
        }
        return retq;
    }

    public void save(){
        JSONArray array = new JSONArray();

        array.addAll(questions);

        try (FileWriter file = new FileWriter(datapath)) {

            file.write(array.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(){
    }
}
