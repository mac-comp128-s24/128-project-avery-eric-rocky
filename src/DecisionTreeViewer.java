import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;

/**
 * @author Rocky Slaymaker on Apr 24, 2024
 */
public class DecisionTreeViewer extends GraphicsGroup {
    private static final double NODE_HIGHT = 30;
    private static final double NODE_WIDTH = 60;
    private static final double PADDING = 5;

    public DecisionTreeViewer(DecisionTree tree, CanvasWindow canvas, double x, double y) {
        // super(x, y);
        canvas.add(this);
        Node root = drawNode(tree.getRoot(), x, y);
        add(root);
        System.out.println("done");
        canvas.onClick((e) -> {
            Node n = (Node) getElementAt(e.getPosition());
            if (n == null) {
                return;
            }
            n.children.forEach(this::add);
        });
    }

    private Node drawNode(DecisionNode node, double x, double y) {
        Node n;
        if (node.isLeaf()) {
            n = drawLeaf((DecisionNode.AnswerNode) node, x, y);
        } else {
            DecisionNode.QuestionNode questionNode = (DecisionNode.QuestionNode) node;
            n = drawInter(questionNode, x, y, List.of(
                drawNode(questionNode.getYes(), x - (NODE_WIDTH + PADDING), y + (NODE_HIGHT + PADDING)),
                // drawNode(questionNode.getIdk(), x, y + (NODE_HIGHT + PADDING)),
                drawNode(questionNode.getNo(), x + (NODE_WIDTH + PADDING), y + (NODE_HIGHT + PADDING))));
        }
        return n;
    }

    private Node drawInter(DecisionNode.QuestionNode node, double x, double y, List<Node> children) {
        Node n = new Node(x, y, NODE_WIDTH, NODE_HIGHT, node.getQuestion(), children);
        return n;
    }

    private Node drawLeaf(DecisionNode.AnswerNode node, double x, double y) {
        Node n = new Node(x, y, NODE_WIDTH, NODE_HIGHT, node.getAnswers().stream().limit(10).toList().toString(),
            new ArrayList<>(0));
        return n;
    }

    private class Node extends GraphicsText {
        public Node(double x, double y, double width, double height, String text, List<Node> children) {
            super(text, x, y);
            // GraphicsText graphicsText = new GraphicsText(text, x, y);
            setFontStyle(FontStyle.BOLD);
            setFontSize(10);
            setWrappingWidth(NODE_WIDTH);
            setCenter(x, y);
            setY(y);
            this.children = children;
        }

        private List<Node> children;
    }
}
