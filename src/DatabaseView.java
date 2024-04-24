import java.util.Set;

/**
 * @author Rocky Slaymaker on Apr 15, 2024
 */
public class DatabaseView {
    private Databased database;
    private Set<Integer> questions;
    private Set<Integer> objects;

    public DatabaseView(Databased database) {
        this.database = database;

    }

    public DatabaseView removeQuestion(String splittingQuestion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeQuestion'");
    }

    public DatabaseView filterAnswers(String splittingQuestion, boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filterAnswers'");
    }

}
