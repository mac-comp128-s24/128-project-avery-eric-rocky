import java.util.List;

/**
 * @author Rocky Slaymaker on Apr 15, 2024
 */
public class DeccisionNode {
    private boolean isQuestion;
    private List<String> answers;
    private DeccisionNode yes;
    private DeccisionNode no;

    private boolean isQuestion() {
        return isQuestion;
    }
}
