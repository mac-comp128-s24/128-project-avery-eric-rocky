import java.awt.Color;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.macalester.graphics.CanvasWindow;

/**
 * The Akinator game instance
 * 
 * @author Avery, Eric, Rocky on Apr 30, 2024
 */
public class Game {

    private CanvasWindow canvas;
    private Color black = Color.BLACK;
    private HomeScreen homeScreen = new HomeScreen();
    private static Future<DecisionTree> tree = Executors.newSingleThreadExecutor()
        .submit(() -> utils.readFromFile("res/caches/DecisionTree:15:500:500"));

    public Game() {
        canvas = new CanvasWindow("Title", 600, 800);
        canvas.setBackground(black);
        homeScreen.addHomescreen(canvas);
    }
    public static void main(String[] args) {
        new Game();
    }

    public static DecisionTree getTree() {
        try {
            return tree.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

}
