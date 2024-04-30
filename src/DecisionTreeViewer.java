import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.TextAlignment;

/**
 * Interactive view of the decision tree
 * 
 * @author Avery, Eric, Rocky on Apr 24, 2024
 */
public class DecisionTreeViewer extends GraphicsGroup {
    private static final double NODE_MAX_WIDTH = 70;
    private static final double PADDING = 3;
    private DecisionTree tree;
    private List<Node> nodes;

    public DecisionTreeViewer(DecisionTree tree, CanvasWindow canvas, double x, double y) {
        // super(x, y);
        this.tree = tree;
        nodes = new ArrayList<>();
        canvas.add(this);
        Node root = new Node(tree.getRoot(), x, y);
        System.out.println("done");
        canvas.onClick((e) -> {
            for (Node node : new ArrayList<>(nodes)) {
                if (node.isInBounds(e.getPosition().subtract(getPosition()))) {
                    node.onClick(e.getPosition().subtract(getPosition()));
                }
            }
        });
        canvas.onDrag((e) -> this.moveBy(e.getDelta()));
    }

    private class Node extends GraphicsGroup {
        private DecisionTreeNode node;
        private double x, y;
        Node child;
        GraphicsText gText;
        Rectangle rect, yesBox, noBox;

        public Node(DecisionTreeNode node, double x, double y) {
            super(x, y);
            this.node = node;
            this.x = x;
            this.y = y;
            String text;
            if (node.isLeaf()) {
                DecisionTreeNode.AnswerNode answerNodeNode = (DecisionTreeNode.AnswerNode) node;
                text = answerNodeNode.getAnswers().subList(0, Math.min(answerNodeNode.getAnswers().size(), 10))
                    .toString();
            } else {
                DecisionTreeNode.QuestionNode questionNode = (DecisionTreeNode.QuestionNode) node;
                text = questionNode.getQuestion();
            }

            gText = new GraphicsText(text);
            gText.setAlignment(TextAlignment.CENTER);
            gText.setFontStyle(FontStyle.BOLD);
            gText.setFontSize(11);
            gText.setWrappingWidth(NODE_MAX_WIDTH - 2 * PADDING);
            gText.moveBy(0, gText.getLineHeight());
            gText.moveBy(PADDING, -PADDING);

            rect = new Rectangle(-gText.getWidth() / 2, 0, gText.getWidth() + PADDING * 2,
                gText.getHeight() + PADDING * 2);
            rect.setFillColor(Color.YELLOW);

            yesBox = new Rectangle(rect.getX(), rect.getY() + rect.getHeight(), rect.getWidth() / 2, 10);
            yesBox.setFillColor(Color.GREEN);
            noBox = new Rectangle(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight(),
                rect.getWidth() / 2, 10);
            noBox.setFillColor(Color.RED);

            add(rect);
            if (!node.isLeaf()) {
                add(yesBox);
                add(noBox);
            }
            add(gText);

            DecisionTreeViewer.this.add(this);
            nodes.add(this);
        }

        public void onClick(Point point) {
            Point p = point.subtract(getPosition());
            if (rect.isInBounds(p)) {
                removeChild();
            } else if (yesBox.isInBounds(p)) {
                drawYes();
            } else if (noBox.isInBounds(p)) {
                drawNo();
            }
        }

        private void removeChild() {
            if (child != null) {
                child.removeChild();
                child.delete();
                child = null;
            }
        }

        private void delete() {
            DecisionTreeViewer.this.remove(this);
            nodes.remove(this);
        }

        private void drawYes() {
            removeChild();
            if (!node.isLeaf()) {
                DecisionTreeNode.QuestionNode questionNode = (DecisionTreeNode.QuestionNode) node;
                child = new Node(questionNode.getYes(), x - (NODE_MAX_WIDTH / 2 + PADDING / 2),
                    y + (getHeight() + PADDING));
            }
        }

        private void drawNo() {
            if (!node.isLeaf()) {
                removeChild();
                DecisionTreeNode.QuestionNode questionNode = (DecisionTreeNode.QuestionNode) node;
                child = new Node(questionNode.getNo(), x + (NODE_MAX_WIDTH / 2 + PADDING / 2),
                    y + (getHeight() + PADDING));
            }
        }
    }

    public static void main(String[] args) {
        DecisionTree tree = utils.readFromFile("res/caches/tree.txt");
        CanvasWindow canvas = new CanvasWindow("null", 800, 800);
        DecisionTreeViewer viewer = new DecisionTreeViewer(tree, canvas, 400, 10);
    }
}
