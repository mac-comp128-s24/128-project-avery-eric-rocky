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
    private static final double NODE_HIGHT = 20;
    private static final double NODE_WIDTH = 80;
    private static final double PADDING = 0;

    public DecisionTreeViewer(DecisionTree tree, CanvasWindow canvas, double x, double y) {
        // super(x, y);
        canvas.add(this);
        Node root = drawNode(tree.getRoot(), x, y);
        root.setHidden(false);
        System.out.println("done");
        canvas.onClick((e) -> {
            Node n = (Node) getElementAt(e.getPosition());
            if (n == null) {
                return;
            } else if (n.areChildrenHidden()) {
                n.showChildren();
            } else {
                n.hideAllChildren();
            }

        });
        canvas.onDrag((e) -> this.moveBy(e.getDelta()));
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
        Node n = new Node(x, y, NODE_WIDTH, NODE_HIGHT, node.getAnswers().stream().limit(30).toList().toString(),
            new ArrayList<>(0));
        return n;
    }

    private class Node extends GraphicsText {
        private boolean hidden = true;
        private String text;
        private double x, y;
        private List<Node> children;

        public Node(double x, double y, double width, double height, String text, List<Node> children) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.children = children;
        }

        private void draw() {
            setText(text);
            setFontStyle(FontStyle.BOLD);
            setFontSize(10);
            setWrappingWidth(NODE_WIDTH);
            setCenter(x, y);
            setY(y);
        }

        public boolean areChildrenHidden() {
            return children.stream().anyMatch((c) -> c.isHidden());
        }

        private void setHidden(boolean b) {
            if (!b && hidden) {
                add(this);
                draw();
            } else if (b && !hidden) {
                remove(this);
            }
            hidden = b;
        }


        private boolean isHidden() {
            return hidden;
        }

        public void hideAllChildren() {
            for (Node node : children) {
                node.setHidden(true);
                node.hideAllChildren();
            }
        }

        public void showChildren() {
            for (Node node : children) {
                node.setHidden(false);
            }
        }
    }
}
