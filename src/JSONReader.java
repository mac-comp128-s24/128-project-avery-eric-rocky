import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Rocky Slaymaker on Apr 16, 2024
 */
public class JSONReader {

    public static void read() {
        try {
            Scanner scn = new Scanner(
                new File(JSONReader.class.getResource("/data/twentyquestions-all.jsonl").getPath()));
            scn.useDelimiter("\n").tokens().map((String s) -> new JSONObject(s))
                .forEach((JSONObject o) -> System.out
                    .println(
                        o.getString("subject") + " : " + trim(o.getString("question")) + " : "
                            + o.getBoolean("majority") + " : " + avg(o.getJSONArray("labels"))));
            scn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String trim(String s) {
        s = s.toLowerCase().trim();
        if (s.charAt(s.length() - 1) != '?') {
            s = s + '?';
        }
        return s;
    }

    public static double avg(JSONArray a) {
        return a.toList().stream().mapToDouble((Object o) -> {
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

    public static void main(String[] args) {
        read();
    }
}
