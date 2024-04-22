import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.ui.Button;




public class HomeScreen {
    



    private static CanvasWindow rulesScreen;
    static Color yellow = Color.YELLOW;
    


    public HomeScreen(){
        
    }


    public static void addStartButton(CanvasWindow canvas){
        Button startButton = new Button("Start Game");
        startButton.setPosition(250, 400);
        startButton.setScale(5, 5);
        canvas.add(startButton);
    }


    public static void addRulesButton(CanvasWindow canvas){
        Button rulesButton = new Button("How to Play");
        rulesButton.setPosition(245, 370);
        rulesButton.setScale(5, 5);
        canvas.add(rulesButton);
        rulesButton.onClick(() -> {rulesScreen = new CanvasWindow("Rules", 600, 800);
        });
    }

    public static void addTitle(CanvasWindow canvas){
        GraphicsText title = new GraphicsText("Akinator Game");
        title.setFontSize(50);
        title.setFillColor(yellow);
        title.setPosition(130,170);
        canvas.add(title);
    }

    public static void addImage(CanvasWindow canvas){
        Image image = new Image("images/Akinator.jpg");
        image.setMaxHeight(2500);
        image.setMaxWidth(2500);
        image.setPosition(200,450);
        canvas.add(image);
    }



    public static void addHomescreen(CanvasWindow canvas){
        addImage(canvas);
        addTitle(canvas);
        addRulesButton(canvas);
        addStartButton(canvas);

    }

}
