import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Collectors;

import edu.macalester.graphics.CanvasWindow;

/**
 * Binary decision tree and ID3 generation algorithm
 * 
 * @author Avery, Eric, Rocky on Apr 15, 2024
 */
public class DecisionTree implements Serializable {
    @SuppressWarnings("unused")
    private static final long serialversionUID = 1;
    private transient static final int MAX_DEPTH = 15;
    private transient static final int MAX_OBJECTS = 500;
    private transient static final int MAX_QUESTIONS = 500;

    private DecisionTreeNode root;

    public DecisionTree(Database database) {
        DatabaseView dataView = new DatabaseView(database, MAX_OBJECTS, MAX_QUESTIONS);
        root = internalRecursive(dataView, 0);
    }

    private DecisionTreeNode internalRecursive(DatabaseView dataView, int depth) {
        if (depth < MAX_DEPTH && !dataView.getQuestionIDs().isEmpty() && dataView.getObjectIDs().size() > 1) {
            Optional<Integer> splittingQuestionID = testSplit2(dataView);
            if (splittingQuestionID.isPresent()) {
                return split(dataView, depth, splittingQuestionID.get());
            }
        }
        return new DecisionTreeNode.AnswerNode(dataView.getObjectStringsByRelevance());
    }

    private DecisionTreeNode split(DatabaseView dataView, int depth, int splittingQuestionID) {
        dataView.removeQuestion(splittingQuestionID);
        depth += 1;
        DecisionTreeNode yes = internalRecursive(dataView.filterAnswers(splittingQuestionID, true), depth);
        DecisionTreeNode no = internalRecursive(dataView.filterAnswers(splittingQuestionID, false), depth);
        // DecisionNode idk = internalRecursive(dataView, depth);
        return new DecisionTreeNode.QuestionNode(dataView.getQuestionByID(splittingQuestionID), yes, no);
    }

    /**
     * @return whether or not there exists a question that can split the dataset
     */
    public boolean indistinguishable(DatabaseView dataView) {
        return dataView.getQuestionIDs().parallelStream().unordered()
            .map((questionID) -> dataView.getAnswers(questionID)
                .map((e) -> e.getValue())
                .distinct().count() <= 1)
            .allMatch((b) -> b);
    }


    public Optional<Integer> testSplit2(DatabaseView dataView) {
        return dataView.getQuestionIDs().parallelStream()
            .map((questionID) -> new Tuple<>(questionID, dataView.getAnswers(questionID).collect(Collectors.toSet())))
            .filter(t -> !t.b.isEmpty())
            .map((t) -> {
                int all = t.b.size();
                t.b.removeIf((e) -> e.getValue());
                double y = t.b.size();
                double n = all - t.b.size();
                if (y + n == 0) {
                    return null;
                }
                return new Tuple<>(t.a, Math.log(y * n));
            })
            .filter((t) -> t != null)
            .max(Tuple.sortByB()).map((t) -> t.a);

    }

    /**
     * @return the root node
     */
    public DecisionTreeNode getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return "DecisionTree:" + MAX_DEPTH + ":" + MAX_OBJECTS + ":" + MAX_QUESTIONS;
    }

    /**
     * This main function generates a new tree an serializes it to file
     */
    public static void main(String[] args) {
        Database database = new Database();
        JSONReader.readToDatabase(database);
        DecisionTree tree = new DecisionTree(database);
        utils.writeToFile(tree, "res/caches/" + tree.toString());
        CanvasWindow canvas = new CanvasWindow("null", 800, 800);
        DecisionTreeViewer viewer = new DecisionTreeViewer(tree, canvas, 400, 10);
    }
}
