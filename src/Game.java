import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;

public class Game {

    private CanvasWindow canvas;
    private Color black = Color.BLACK;
    HomeScreen HomeScreen = new HomeScreen();

    public Game() {
        canvas = new CanvasWindow("Title", 600, 800);
        canvas.setBackground(black);
        HomeScreen.addHomescreen(canvas);
        HomeScreen.editRulesScreen(HomeScreen.rulesScreen);

    }

    // private String simpleEntropy(DatabaseView data) {
    // int greatestEntropy = 0;
    // String bestQuestion;
    // for (String question : data.getQuestions()) {
    // int currentEntropy = 0;
    // currentEntropy = question.answers.length() * 10;
    // int trueNum = question.trues.length();
    // int falseNum = question.falses.length();
    // int lowerVal = Math.min(trueNum, falseNum);
    // currentEntropy = currentEntropy + ((lowerVal / (trueNum + falseNum)) * 100);
    // if (currentEntropy > greatestEntropy){
    // bestQuestion = question;
    // }
    // }
    // return bestQuestion.toString();
    // }


    public static void main(String[] args) {
        new Game();
    }

}
