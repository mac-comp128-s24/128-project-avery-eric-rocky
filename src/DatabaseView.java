import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A view in to a data base that allows for the filtering of questions and objects without having to
 * duplicate the whole data base.
 * 
 * @author Avery, Eric, Rocky on Apr 15, 2024
 */
public class DatabaseView {
    // Reference to a Database
    private Database database;
    // The set of questionIDs left
    private Set<Integer> questions;
    // The set of objectIDs left
    private Set<Integer> objects;

    public DatabaseView(Database database, int maxObjects, int maxQuestions) {
        this.database = database;
        objects = database.getNBestObjects(maxObjects);
        questions = database.getNBestQuestions(maxQuestions, objects);
    }

    public DatabaseView(DatabaseView view) {
        this.database = view.database;
        // removed for efficiency because is is always set immediately after copying.
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

    public Set<Integer> getQuestionIDs() {
        return questions;
    }

    public String getQuestionByID(int questionID) {
        return database.getQuestionByID(questionID);
    }

    public List<String> getObjectStrings() {
        return objects.stream().map((Integer objectID) -> database.getObjectByID(objectID))
            .collect(Collectors.toList());
    }

    public List<String> getObjectStringsByRelevance() {
        Set<Integer> qs = getAskedQuestions();
        return objects.parallelStream().map((Integer objectID) -> {
            int num = (int) qs.parallelStream()
                .filter((Integer questionID) -> database.getTruthValue(objectID, questionID) != null).count();
            return new Tuple<>(objectID, num);
        })
            .sorted(Tuple.sortByB(Comparator.reverseOrder())).map((t) -> database.getObjectByID(t.a))
            .collect(Collectors.toList());
    }

    public Stream<Map.Entry<Integer, Boolean>> getAnswers(int questionID) {
        return database.getTable().get(questionID).entrySet().parallelStream().unordered()
            .filter((e) -> objects.contains(e.getKey()));
    }

}
