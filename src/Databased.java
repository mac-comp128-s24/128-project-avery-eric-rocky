import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Rocky Slaymaker on Apr 24, 2024
 */
public class Databased {
    private List<Map<Integer, Boolean>> database;
    private List<String> questions;
    private List<String> objects;


    public void addData(String object, String question, boolean truthValue) {
        int questionID = questions.indexOf(question);
        if (questionID == -1) {
            questionID = questions.size();
            questions.add(question);
            database.add(new HashMap<>());
        }
        int objectID = objects.indexOf(object);
        if (objectID == -1) {
            objectID = objects.size();
            objects.add(object);
        }
        database.get(questionID).put(objectID, truthValue);
    }

    public List<String> getQuestionsObjects(String question) {
        int questionID = questions.indexOf(question);
        return database.get(questionID).keySet().stream().map((Integer i) -> objects.get(i))
            .collect(Collectors.toList());
    }
}
