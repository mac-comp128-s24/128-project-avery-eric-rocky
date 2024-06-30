import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Database containing a table that is essentially just a sparse matrix were the rows are
 * questionIDs and the columns are objectIDs and the value is Boolean representing the answer to the
 * question for that specific object
 * 
 * @author Avery, Eric, Rocky on Apr 24, 2024
 */
public class Database implements Serializable {
    // Essentially just a sparse matrix were the rows are questionIDs and the columns are objectIDs and
    // the value is Boolean representing the answer to the question for that specific object. Most
    // values are null because of incomplete data so we use a map indexed by objectID to avoid storing
    // many null values.
    private List<Map<Integer, Boolean>> table;
    // A list of unique questions where the index corresponds it that questions unique ID
    private List<String> questions;
    // A list of unique objects where the index corresponds it that questions unique ID
    private List<String> objects;

    public Database() {
        table = new ArrayList<>();
        questions = new ArrayList<>();
        objects = new ArrayList<>();
    }

    public void addData(String object, String question, boolean truthValue) {
        int questionID = questions.indexOf(question);
        if (questionID == -1) {
            questionID = questions.size();
            questions.add(question);
            table.add(new HashMap<>());
        }
        int objectID = objects.indexOf(object);
        if (objectID == -1) {
            objectID = objects.size();
            objects.add(object);
        }
        table.get(questionID).put(objectID, truthValue);
    }

    public String getQuestionByID(int questionID) {
        return questions.get(questionID);
    }

    public String getObjectByID(int objectID) {
        return objects.get(objectID);
    }

    public Boolean getTruthValue(int objectID, int questionID) {
        return table.get(questionID).get(objectID);
    }


    /**
     * @return the questions
     */
    public List<String> getQuestionStrings() {
        return questions;
    }

    /**
     * @return the objects
     */
    public List<String> getObjectStrings() {
        return objects;
    }

    public Set<Integer> getNBestQuestions(int limit, Set<Integer> bestObjects) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int i = 0;
        for (Map<Integer, Boolean> qs : table) {
            int numTrue = (int) qs.entrySet().parallelStream().filter((e) -> bestObjects.contains(e.getKey()))
                .filter((e) -> e.getValue()).count();
            int numFalse = qs.size() - numTrue;
            map.put(i, Math.min(numFalse, numTrue));
            i++;
        }
        Comparator.naturalOrder();
        return map.entrySet().parallelStream()
            .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(limit)
            .map((e) -> e.getKey())
            .collect(Collectors.toSet());
    }

    /**
     * @return the table
     */
    public List<Map<Integer, Boolean>> getTable() {
        return table;
    }

    public Set<Integer> getNBestObjects(int limit) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < objects.size(); i++) {
            int num = 0;
            for (Map<Integer, Boolean> qs : table) {
                if (qs.containsKey(i)) {
                    num++;
                }
            }
            map.put(i, num);
        }
        Comparator.naturalOrder();
        return map.entrySet().parallelStream().sorted(Entry.comparingByValue(Comparator.reverseOrder())).limit(limit)
            .map((e) -> e.getKey())
            .collect(Collectors.toSet());
    }

}
