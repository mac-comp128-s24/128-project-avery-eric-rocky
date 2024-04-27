import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
 * @author Rocky Slaymaker on Apr 24, 2024
 */
public class Database implements Serializable {
    private List<Map<Integer, Boolean>> table;
    private List<String> questions;
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


    public void writeToFile(String file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Database readFromFile(String file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Database database = (Database) objectInputStream.readObject();
            objectInputStream.close();
            return database;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public List<Entry<String, Integer>> getNBestQuestions2(int limit) {
        HashMap<String, Integer> map = new HashMap<>();
        int i = 0;
        for (Map<Integer, Boolean> qs : table) {
            int numTrue = (int) qs.values().stream().filter((b) -> b).count();
            int numFalse = qs.size() - numTrue;
            map.put(getQuestionByID(i), Math.min(numFalse, numTrue));
            i++;
        }
        Comparator.naturalOrder();
        return map.entrySet().stream().sorted(Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(limit).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Database database = new Database();
        JSONReader.readToDatabase(database);
        var list = database.getNBestQuestions2(500);
        for (Entry<String, Integer> entry : list) {
            System.out.println(entry);
        }
        System.out.println("Number: " + list.size() + " of: " + database.table.size());
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
