import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;

public class Game {

    private CanvasWindow canvas;
    private Color black = Color.BLACK;

    public Game() {
        canvas = new CanvasWindow("Title", 600, 800);
        canvas.setBackground(black);
        HomeScreen.addHomescreen(canvas);


        DecisionTree tree = new DecisionTree(null, this::entropyAlg);
    }

    private String entropyAlg(Database data) {
        return "hi";
    }


    public static void main(String[] args) {
        new Game();
    }

}
