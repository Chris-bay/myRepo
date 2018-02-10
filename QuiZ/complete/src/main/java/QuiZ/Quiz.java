package QuiZ;

import QuiZ.Questions.*;
import org.json.simple.JSONArray;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Quiz {
    ArrayList<Question> questions = new ArrayList<>();
    String datapath = "data/";

    String id;
    String title;

    public Question createNewQuestion(QuestionType type , String question, String[] answers, Integer rightAnswer){

        Question q;
        switch (type){
            case GUESS:
                q = new GuessQuestion(questions.size(), question, answers);
                break;
            case STRING:
                q = new StringQuestion(questions.size(), question, answers);
                break;
            case MULTIPLECHOICE:
                q = new MultipleChoiceQuestion(questions.size(), question, answers, rightAnswer);
                break;
            case MEDIAGUESS:
                q = new MediaGuessQuestion(questions.size(), question, answers);
                break;
            case MEDIAMULKTIPLECHOICE:
                q = new MediaMultipleChoiceQuestion(questions.size(), question, answers, rightAnswer);
                break;
            case MEDIASTRING:
                q = new MediaStringQuestion(questions.size(), question, answers);
                break;
            default: {
                System.out.println("Faulty Question Type: " + type.toString());
                System.out.println("Initializing multiple choice question");
                q = new MultipleChoiceQuestion(questions.size(), question, answers);
            }
        }
        //Question q = new Question(questions.size(), question, answers);

        questions.add(q);

        return q;
    }

    public Optional<Question> findQuestion(Integer id){
        Optional<Question> retQuestion = Optional.empty();
        for (Question q:questions){
            if (q.getId()==id){
                retQuestion = Optional.of(q);
            }
        }
        return retQuestion;
    }

    public Optional<Question> getQuestion(String question){
        Optional<Question> retq = Optional.empty();
        for(Question q : questions){
            if(q.getQuestionText().equals(question)){
                retq = Optional.of(q);
            }
        }
        return retq;
    }

    public void changeQuestion(Integer id, Question question){
        Optional<Question> foundQuestion = findQuestion(id);
        if (foundQuestion.isPresent()){
            if (!foundQuestion.getClass().equals(question.getClass())){
                System.out.println("not matching types of found Question and new Question ("
                        + foundQuestion.getClass().toString() +
                        " and " + question.getClass().toString());
            }
        }
    }

    public void save() {
        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();

        array.addAll(questions);
        obj.put("id", id);
        obj.put("data", array);

        try (FileWriter file = new FileWriter(datapath + "Quiz_" + id + ".json")) {

            file.write(mapper.defaultPrettyPrintingWriter().writeValueAsString(obj));
            file.flush();

        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(){
    }

    public Quiz(){
        File f = new File("./data");
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
        String seed = Integer.toString(names.size());

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(seed.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        this.id = sb.toString();
    }
}
