import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Rocky Slaymaker on Apr 15, 2024
 */
public class DatabaseView {
    private Database database;
    private Set<Integer> questions;
    private Set<Integer> objects;

    public DatabaseView(Database database) {
        this.database = database;
        // questions = new HashSet<>();
        // questions.addAll(IntStream.range(0,
        // database.getQuestions().size()).boxed().collect(Collectors.toSet()));
        objects = database.getNBestObjects(100);
        questions = database.getNBestQuestions(500);
    }

    public DatabaseView(DatabaseView view) {
        this.database = view.database;
        // this.objects = new HashSet<>(view.objects);
        this.questions = new HashSet<>(view.questions);

    }

    public void removeQuestion(int questionID) {
        questions.remove(questionID);
    }

    public Set<Integer> getAskedQuestions() {
        return IntStream.range(0, database.getQuestionStrings().size()).filter((i) -> !questions.contains(i)).boxed()
            .collect(Collectors.toSet());
    }

    public DatabaseView filterAnswers(int questionID, boolean truthValue) {
        DatabaseView newView = new DatabaseView(this);
        newView.objects = objects.stream()
            .filter((objectID) -> database.getTruthValue(objectID, questionID) == null
                || database.getTruthValue(objectID, questionID) == truthValue)
            .collect(Collectors.toSet());
        return newView;
    }

    public Set<Integer> getObjectIDs() {
        return objects;
    }

    public Set<Integer> getQuestionsIDs() {
        return questions;
    }


    public boolean indistinguishable() {
        for (Integer questionID : questions) {
            Boolean truthValue = null;
            for (Integer objectID : objects) {
                if (truthValue == null) {
                    truthValue = database.getTruthValue(objectID, questionID);
                } else if (database.getTruthValue(objectID, questionID) != null
                    && !truthValue.equals(database.getTruthValue(objectID, questionID))) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getQuestionByID(int questionID) {
        return database.getQuestionByID(questionID);
    }

    public List<String> getObjectStrings() {
        return objects.stream().map((Integer objectID) -> database.getObjectByID(objectID))
            .collect(Collectors.toList());
    }

    public List<String> getObjectStringsByRelevance() {
        HashMap<Integer, Integer> map = new HashMap<>();
        var a = getAskedQuestions();
        for (Integer o : objects) {
            int num = 0;
            for (Integer q : a) {
                if (database.getTable().get(q).containsKey(o)) {
                    num++;
                }
            }
            map.put(o, num);
        }
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .map((e) -> database.getObjectByID(e.getKey()))
            .collect(Collectors.toList());
    }

    public Stream<Map.Entry<Integer, Boolean>> getAnswers(int questionID) {
        return database.getTable().get(questionID).entrySet().stream()
            .filter((e) -> getObjectIDs().contains(e.getKey()));
    }

}
