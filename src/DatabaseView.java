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
        questions = database.getNBestQuestions(200);
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

    public Set<Integer> getQuestionIDs() {
        return questions;
    }



    public boolean indistinguishable() {
        return getQuestionIDs().parallelStream().unordered()
            .map((questionID) -> database.getTable().get(questionID).entrySet().parallelStream().unordered()
                .filter((e) -> objects.contains(e.getKey())).map((e) -> e.getValue()).distinct().count() <= 1)
            .allMatch((b) -> b);
    }

    public int testSplit2() {
        return getQuestionIDs().parallelStream()
            .map((questionID) -> new Tuple<>(questionID,
                database.getTable().get(questionID).entrySet().parallelStream().unordered()
                    .filter((e) -> objects.contains(e.getKey())).collect(Collectors.toSet())))
            .map((t) -> {
                int all = t.b.size();
                t.b.removeIf((e) -> e.getValue());
                double y = Math.log(t.b.size());
                double n = Math.log(all - t.b.size());
                return new Tuple<>(t.a, y + n);
            }).sorted(Tuple.sortByB(Comparator.reverseOrder())).findFirst().get().a;

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
        getAskedQuestions().parallelStream().forEach((questionID) -> {
            database.getTable().get(questionID)
                .forEach((k, v) -> { map.put(k, map.getOrDefault(k, 0) + 1); });
        });

        return map.entrySet().parallelStream().filter((e) -> objects.contains(e.getKey()))
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .map((e) -> database.getObjectByID(e.getKey()))
            .collect(Collectors.toList());
    }

    public Stream<Map.Entry<Integer, Boolean>> getAnswers(int questionID) {
        return database.getTable().get(questionID).entrySet().parallelStream()
            .filter((e) -> getObjectIDs().contains(e.getKey()));
    }

}
