import java.util.List;

public sealed interface DecisionNode {
    public boolean isLeaf();

    public final class AnswerNode implements DecisionNode {

        /**
         * @return true if the DecisionNode is a leaf node else false.
         */
        private List<String> answers;

        @Override
        public boolean isLeaf() {
            return true;
        }

        /**
         * @param answers
         */
        public AnswerNode(List<String> answers) {
            this.answers = answers;
        }

        /**
         * @return the answers
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

    public final class QuestionNode implements DecisionNode {
        private DecisionNode yes;
        private DecisionNode no;
        private DecisionNode idk;
        private String question;

        @Override
        public boolean isLeaf() {
            return false;
        }

        /**
         * @param yes
         * @param no
         */
        public QuestionNode(String question, DecisionNode yes, DecisionNode no, DecisionNode idk) {
            this.question = question;
            this.yes = yes;
            this.no = no;
            this.idk = idk;
        }


        /**
         * @return the yes
         */
        public DecisionNode getYes() {
            return yes;
        }

        /**
         * @param yes the yes to set
         */
        public void setYes(DecisionNode yes) {
            this.yes = yes;
        }

        /**
         * @return the no
         */
        public DecisionNode getNo() {
            return no;
        }

        /**
         * @param no the no to set
         */
        public void setNo(DecisionNode no) {
            this.no = no;
        }

        /**
         * @return the idk
         */
        public DecisionNode getIdk() {
            return idk;
        }

        /**
         * @param idk the idk to set
         */
        public void setIdk(DecisionNode idk) {
            this.idk = idk;
        }

        /**
         * @return the question
         */
        public String getQuestion() {
            return question;
        }


    };
}
