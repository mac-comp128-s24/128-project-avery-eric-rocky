import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.json.JSONObject;

/**
 * @author Rocky Slaymaker on Apr 16, 2024
 */
public class JSONReader {

    public static void testPrint() {
        try {
            Scanner scn = new Scanner(
                new File(JSONReader.class.getResource("/data/twentyquestions-all.jsonl").getPath()));
            scn.useDelimiter("\n").tokens().map((String s) -> new JSONObject(s))
                .forEach((JSONObject o) -> System.out
                    .println(
                        o.getString("subject") + " : " + formatQuestion(o.getString("question")) + " : "
                            + o.getBoolean("majority") + " : " + avg(o.getJSONArray("labels").toList())));
            scn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        repeats();
    }

    public static void repeats() {
        HashMap<String, Integer> map = new HashMap<>();
        try {
            Scanner scn = new Scanner(
                new File(JSONReader.class.getResource("/data/twentyquestions-all.jsonl").getPath()));
            scn.useDelimiter("\n").tokens().map((String s) -> new JSONObject(s))
                .forEach((JSONObject o) -> {
                    String q = formatQuestion(o.getString("question"));
                    map.put(q, map.getOrDefault(q, 0) + 1);
                });
            scn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        var list = map.entrySet().stream().sorted(Entry.comparingByValue(Comparator.reverseOrder()))
            .filter((e) -> e.getValue() > 1)
            .collect(Collectors.toList());
        for (Entry<String, Integer> entry : list) {
            System.out.println(entry);
        }
        System.out.println("Number: " + list.size() + " of: " + map.size());
    }

    public static void readToDatabase(Database database) {
        try {
            Scanner scn = new Scanner(
                new File(JSONReader.class.getResource("/data/twentyquestions-all.jsonl").getPath()));
            scn.useDelimiter("\n").tokens()
                // .limit(200)
                .map((String s) -> new JSONObject(s))
                .forEach((JSONObject o) -> {
                    database.addData(o.getString("subject"), formatQuestion(o.getString("question")),
                        o.getBoolean("majority"));
                });
            scn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String formatQuestion(String s) {
        s = s.toLowerCase().trim();
        if (s.charAt(s.length() - 1) != '?') {
            s = s + '?';
        }
        return s;
    }

    public static double avg(List<Object> values) {
        return values.stream().mapToDouble((Object o) -> {
            return switch ((String) o) {
                case "always" -> 1.0;
                case "usually" -> 0.75;
                case "sometimes" -> 0.5;
                case "rarely" -> 0.25;
                case "never" -> 0;
                // case"bad"
                default -> 0.5;
            };
        }).average().getAsDouble();
    }
}
