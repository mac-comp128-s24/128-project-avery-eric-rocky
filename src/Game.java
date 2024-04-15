import edu.macalester.graphics.CanvasWindow;

public class Game {

    private CanvasWindow canvas;

    public Game() {
        canvas = new CanvasWindow("Title", 600, 800);
    }


    public static void main(String[] args) {
        new Game();
    }

}
