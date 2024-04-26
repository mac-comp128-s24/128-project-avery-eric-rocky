import java.util.Comparator;

import edu.macalester.graphics.CanvasWindow;

/**
 * @author Rocky Slaymaker on Apr 15, 2024
 */
public class DecisionTree {
    private DecisionNode root;
    private SplittingCriterion splittingFunction;

    public DecisionTree(Database database/* , SplittingCriterion splittingFunction */) {
        DatabaseView dataView = new DatabaseView(database);
        this.splittingFunction = splittingFunction;
        root = internalRecursive(dataView, 0);
    }

    private DecisionNode internalRecursive(DatabaseView dataView, int depth) {
        DecisionNode node;
        if (depth >= 8 || shouldStop(dataView)) {
            node = new DecisionNode.AnswerNode(dataView.getObjectStrings());
        } else {
            node = split(dataView, depth);
        }
        return node;
    }

    private DecisionNode split(DatabaseView dataView, int depth) {
        // int splittingQuestionID = splittingFunction.choose(dataView);
        int splittingQuestionID = dataView.testSplit2();
        if (!dataView.getQuestionIDs().contains(splittingQuestionID)) {
            throw new Error();
        }
        dataView.removeQuestion(splittingQuestionID);
        depth += 1;
        DecisionNode yes = internalRecursive(dataView.filterAnswers(splittingQuestionID, true), depth);
        DecisionNode no = internalRecursive(dataView.filterAnswers(splittingQuestionID, false), depth);
        DecisionNode idk = internalRecursive(dataView, depth);
        return new DecisionNode.QuestionNode(dataView.getQuestionByID(splittingQuestionID), yes, no, idk);
    }

    private boolean shouldStop(DatabaseView dataView) {
        return dataView.getQuestionIDs().isEmpty() || dataView.getObjectIDs().size() <= 1
            || dataView.indistinguishable();
    }

    public interface SplittingCriterion {
        public int choose(DatabaseView dataView);
    }

    /**
     * @return the root
     */
    public DecisionNode getRoot() {
        return root;
    }

    private int testSplittingFunction(DatabaseView d) {
        return d.getQuestionIDs().parallelStream().map((Integer questionID) -> {
            int numTrue = (int) d.getAnswers(questionID).filter((e) -> e.getValue()).count();
            int numFalse = (int) d.getAnswers(questionID).filter((e) -> !e.getValue()).count();
            int num = numFalse + numTrue;
            return new Tuple<>(questionID, num);
        }).sorted(Tuple.sortByB(Comparator.reverseOrder()))
            .limit(1)
            .map((t) -> t.a).findFirst().get();
    }

    public static void main(String[] args) {
        Database database = new Database();
        JSONReader.readToDatabase(database);
        // database.writeToFile("yourfile.txt");
        // Database database = readFromFile("yourfile.txt");
        DecisionTree tree = new DecisionTree(database);
        System.out.println("Done");
        CanvasWindow canvas = new CanvasWindow("null", 800, 800);
        DecisionTreeViewer viewer = new DecisionTreeViewer(tree, canvas, 400, 10);
    }
}
