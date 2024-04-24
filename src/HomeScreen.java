import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.ui.Button;




public class HomeScreen {
    



    public CanvasWindow rulesScreen;
    public CanvasWindow startScreen;
    public Color yellow = Color.YELLOW;
    public Color black = Color.BLACK;
    


    public HomeScreen(){
    
    }


    public void addStartButton(CanvasWindow canvas){
        Button startButton = new Button("Start Game");
        startButton.setPosition(250, 400);
        startButton.setScale(5, 5);
        canvas.add(startButton);
        startButton.onClick(() -> {startScreen = new CanvasWindow("Akinator", 600, 800);
        });
        startButton.onClick(() -> {canvas.closeWindow();
        });
    }



    public void addRulesButton(CanvasWindow canvas){
        Button rulesButton = new Button("How to Play");
        rulesButton.setPosition(245, 370);
        rulesButton.setScale(5, 5);
        canvas.add(rulesButton);
        rulesButton.onClick(() -> {rulesScreen = new CanvasWindow("Rules", 600, 800); editRulesScreen(rulesScreen);;
        });
    }



    public void addTitle(CanvasWindow canvas){
        GraphicsText title = new GraphicsText("Akinator Game");
        title.setFontSize(50);
        title.setFillColor(yellow);
        title.setPosition(130,170);
        canvas.add(title);
    }

    public void addImage(CanvasWindow canvas){
        Image image = new Image("images/Akinator.jpg");
        image.setMaxHeight(2500);
        image.setMaxWidth(2500);
        image.setPosition(200,450);
        canvas.add(image);
    }

    



    public void addHomescreen(CanvasWindow canvas){
        addImage(canvas);
        addTitle(canvas);
        addRulesButton(canvas);
        addStartButton(canvas);

    }


    public void addRules(CanvasWindow canvas){
        GraphicsText rule1 = new GraphicsText("The player must think of a fictional or real character, object, or animal");
        rule1.setFontSize(20);
        rule1.setFillColor(black);
        rule1.setPosition(130,170);
        canvas.add(rule1);
        GraphicsText rule2 = new GraphicsText("Akinator, the Genie will then interogate the play");
        rule2.setFontSize(20);
        rule2.setFillColor(black);
        rule2.setPosition(130,180);
        canvas.add(rule2);
    }



    public void editRulesScreen(CanvasWindow canvas){
        addImage(canvas);
        addRules(canvas);
        
    }



}
