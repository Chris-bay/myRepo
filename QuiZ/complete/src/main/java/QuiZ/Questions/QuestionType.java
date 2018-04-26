package QuiZ.Questions;

import java.util.HashMap;
import java.util.Map;

public enum QuestionType{
    MULTIPLECHOICE(0),
    GUESS(1),
    STRING(2),
    MMULTIPLECHOICE(3),
    MGUESS(4),
    MSTRING(5);

    private int number;
    private static Map map = new HashMap<>();

    QuestionType(int number){
        this.number = number;
    }

    static {
        for (QuestionType qtype : QuestionType.values()){
            map.put(qtype.number,qtype);
        }
    }

    public static QuestionType getValue(int number){
        return (QuestionType) map.get(number);
    }

    public int getNumber() {
        return number;
    }
}
