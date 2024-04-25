import edu.macalester.graphics.CanvasWindow;

/**
 * @author Rocky Slaymaker on Apr 15, 2024
 */
public class DecisionTree {
    private DecisionNode root;
    private SplittingCriterion splittingFunction;

    public DecisionTree(Database database, SplittingCriterion splittingFunction) {
        DatabaseView dataView = new DatabaseView(database);
        this.splittingFunction = splittingFunction;
        root = internalRecursive(dataView, 0);
        // if (root.isLeaf()) {
        // DecisionNode.AnswerNode answerNode = (DecisionNode.AnswerNode) root;
        // } else {
        // DecisionNode.QuestionNode questionNode = (DecisionNode.QuestionNode) root;
        // }

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
        int splittingQuestionID = splittingFunction.choose(dataView);
        if (!dataView.getQuestionsIDs().contains(splittingQuestionID)) {
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
        return dataView.getQuestionsIDs().isEmpty() || dataView.indistinguishable();
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
        // database.writeToFile("yourfile.txt");
        // Database database = readFromFile("yourfile.txt");
        DecisionTree tree = new DecisionTree(database, (DatabaseView d) -> {
            int maxID = -1;
            int maxNum = -1;
            for (int questionID : d.getQuestionsIDs()) {
                int numTrue = (int) d.getAnswers(questionID).filter((e) -> e.getValue()).count();
                int numFalse = (int) d.getAnswers(questionID).filter((e) -> !e.getValue()).count();
                int num = Math.min(numFalse, numTrue);
                if (num > maxNum) {
                    maxNum = num;
                    maxID = questionID;
                }
            }
            return maxID;
        });
        System.out.println("Done");
        CanvasWindow canvas = new CanvasWindow("null", 800, 800);
        DecisionTreeViewer viewer = new DecisionTreeViewer(tree, canvas, 400, 10);
    }
}
