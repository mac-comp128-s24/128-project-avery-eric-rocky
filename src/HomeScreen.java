import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.ui.Button;
public class HomeScreen {
    


    public static void addStartButton(CanvasWindow canvas){
        Button startButton = new Button("Start Game");
        startButton.setPosition(200, 200);
        startButton.setScale(5, 5);
        canvas.add(startButton);
    }

    public static void addTitle(CanvasWindow canvas){
        GraphicsText title = new GraphicsText("Akinator");
        title.setFontSize(25);
        title.setPosition(250,250);
        canvas.add(title);
    }

    public static void addImage(CanvasWindow canvas){
        Image image = new Image("images/Akinator.jpg");
        image.setMaxHeight(900);
        image.setMaxWidth(1600);
        canvas.add(image);
    }

}
