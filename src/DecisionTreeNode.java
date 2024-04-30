import java.io.Serializable;
import java.util.List;

/**
 * A node of the decision tree could be either an AnswerNode or a QuestionNode
 * 
 * @author Avery, Eric, Rocky on Apr 30, 2024
 */
public sealed interface DecisionTreeNode extends Serializable {
    /**
     * @return true if the DecisionNode is a leaf node else false.
     */
    public boolean isLeaf();

    public final class AnswerNode implements DecisionTreeNode {
        private List<String> answers;

        @Override
        public boolean isLeaf() {
            return true;
        }

        /**
         * @param answers the list of posable answers
         */
        public AnswerNode(List<String> answers) {
            this.answers = answers;
        }

        /**
         * @return the list of posable answers
         */
        public List<String> getAnswers() {
            return answers;
        }

        /**
         * @param answers the answers to set
         */
        public void setAnswers(List<String> answers) {
            this.answers = answers;
        }
    };

    public final class QuestionNode implements DecisionTreeNode {
        private DecisionTreeNode yes;
        private DecisionTreeNode no;
        private String question;

        @Override
        public boolean isLeaf() {
            return false;
        }

        /**
         * @param question the node's question
         * @param yes      child node
         * @param no       child node
         */
        public QuestionNode(String question, DecisionTreeNode yes, DecisionTreeNode no) {
            this.question = question;
            this.yes = yes;
            this.no = no;
        }


        /**
         * @return the yes child node
         */
        public DecisionTreeNode getYes() {
            return yes;
        }

        /**
         * @param yes the yes child node to set
         */
        public void setYes(DecisionTreeNode yes) {
            this.yes = yes;
        }

        /**
         * @return the no child node
         */
        public DecisionTreeNode getNo() {
            return no;
        }

        /**
         * @param no the no child node to set
         */
        public void setNo(DecisionTreeNode no) {
            this.no = no;
        }

        /**
         * @return the string question
         */
        public String getQuestion() {
            return question;
        }


    };
}
