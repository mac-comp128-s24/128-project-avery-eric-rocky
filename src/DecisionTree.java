/**
 * @author Rocky Slaymaker on Apr 15, 2024
 */
public class DecisionTree {
    private DecisionNode root;
    private SplittingFunction splittingFunction;

    public DecisionTree(Database data, SplittingFunction splittingFunction) {
        this.splittingFunction = splittingFunction;
        root = internalRecursive(data);
        // if (root.isLeaf()) {
        // DecisionNode.AnswerNode answerNode = (DecisionNode.AnswerNode) root;
        // } else {
        // DecisionNode.QuestionNode questionNode = (DecisionNode.QuestionNode) root;
        // }

    }

    private DecisionNode internalRecursive(Database data) {
        DecisionNode node;
        if (shouldStop(data)) {
            node = new DecisionNode.AnswerNode(null);// TODO
        } else {
            node = split(data);
        }
        return node;
    }

    private DecisionNode split(Database data) {
        String splittingQuestion = splittingFunction.choose(data);
        data = data.removeQuestion(splittingQuestion);
        DecisionNode yes = internalRecursive(data.filterAnswers(splittingQuestion, true));
        DecisionNode no = internalRecursive(data.filterAnswers(splittingQuestion, false));
        DecisionNode idk = internalRecursive(data);
        DecisionNode node = new DecisionNode.QuestionNode(splittingQuestion, yes, no, idk);
        return node;
    }

    private boolean shouldStop(Database data) {
        return false; // TODO!!
    }

    public interface SplittingFunction {
        public String choose(Database data);
    }


}
