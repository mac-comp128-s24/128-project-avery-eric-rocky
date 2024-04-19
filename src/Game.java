import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.ui.Button;

public class Game {

    private CanvasWindow canvas;

    public Game() {
        canvas = new CanvasWindow("Title", 600, 800);
        HomeScreen.addImage(canvas);
        HomeScreen.addStartButton(canvas);
        HomeScreen.addTitle(canvas);
    }


    




    public static void main(String[] args) {
        new Game();
    }

}
