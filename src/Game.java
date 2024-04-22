import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.ui.Button;


import java.awt.Color;

public class Game {

    private CanvasWindow canvas;
    private Color black = Color.BLACK;

    public Game() {
        canvas = new CanvasWindow("Title", 600, 800);
        canvas.setBackground(black);
        HomeScreen.addHomescreen(canvas);
    }


    




    public static void main(String[] args) {
        new Game();
    }

}
