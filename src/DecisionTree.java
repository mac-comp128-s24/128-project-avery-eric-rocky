/**
 * @author Rocky Slaymaker on Apr 15, 2024
 */
public class DecisionTree {
    private DecisionNode root;

    public DecisionTree(DataBase data) {
        if (root.isLeaf()) {
            DecisionNode.AnswerNode answerNode = (DecisionNode.AnswerNode) root;
        } else {
            DecisionNode.QuestionNode questionNode = (DecisionNode.QuestionNode) root;
        }
    }
}
