import java.io.Serializable;

import edu.macalester.graphics.CanvasWindow;

/**
 * @author Rocky Slaymaker on Apr 15, 2024
 */
public class DecisionTree implements Serializable {
    @SuppressWarnings("unused")
    private static final long serialversionUID = 1;
    private DecisionNode root;

    public DecisionTree(Database database/* , SplittingCriterion splittingFunction */) {
        DatabaseView dataView = new DatabaseView(database);
        root = internalRecursive(dataView, 0);
    }

    private DecisionNode internalRecursive(DatabaseView dataView, int depth) {
        DecisionNode node;
        if (depth >= 15 || shouldStop(dataView)) {
            node = new DecisionNode.AnswerNode(dataView.getObjectStringsByRelevance());
        } else {
            node = split(dataView, depth);
        }
        return node;
    }

    private DecisionNode split(DatabaseView dataView, int depth) {
        int splittingQuestionID = dataView.testSplit2();
        if (!dataView.getQuestionIDs().contains(splittingQuestionID)) {
            throw new Error();
        }
        dataView.removeQuestion(splittingQuestionID);
        depth += 1;
        DecisionNode yes = internalRecursive(dataView.filterAnswers(splittingQuestionID, true), depth);
        DecisionNode no = internalRecursive(dataView.filterAnswers(splittingQuestionID, false), depth);
        // DecisionNode idk = internalRecursive(dataView, depth);
        return new DecisionNode.QuestionNode(dataView.getQuestionByID(splittingQuestionID), yes, no, null);
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

    public static void main(String[] args) {
        Database database = new Database();
        JSONReader.readToDatabase(database);
        // // database.writeToFile("yourfile.txt");
        // // Database database = readFromFile("yourfile.txt");
        DecisionTree tree = new DecisionTree(database);
        utils.writeToFile(tree, "res/caches/tree.txt");
        CanvasWindow canvas = new CanvasWindow("null", 800, 800);
        DecisionTreeViewer viewer = new DecisionTreeViewer(tree, canvas, 400, 10);
    }
}
