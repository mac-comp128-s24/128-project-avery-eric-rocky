import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
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
        startButton.onClick(() -> {startScreen = new CanvasWindow("Akinator", 600, 800); editScreen(startScreen);
        });
        
    }

    public void addStartButton2(CanvasWindow canvas) {
        Button startButton = new Button("Tree Viewer");
        startButton.setPosition(250, 430);
        startButton.setScale(5, 5);
        canvas.add(startButton);
        startButton.onClick(() -> {
            startScreen = new CanvasWindow("TreeViewer", 800, 800);
            GraphicsText loadingText = new GraphicsText("Loading...", 400, 400);
            startScreen.add(loadingText);
            startScreen.draw();
            DecisionTreeViewer viewer = new DecisionTreeViewer(Game.getTree(), startScreen, 400, 10);
            startScreen.remove(loadingText);
        });
        startButton.onClick(() -> {
            // canvas.closeWindow();
        });
    }


    public void editScreen(CanvasWindow canvas){
        Image quote = new Image("images/quote.png");
        quote.setMaxHeight(400);
        quote.setMaxWidth(600);
        canvas.add(quote);
        quote.setCenter(300, 370);
        addImage(canvas);
        canvas.setBackground(black);
        Button yes = new Button("yes");
        canvas.add(yes);
        yes.setPosition(200, 700);
        Button no = new Button("no");
        canvas.add(no);
        no.setPosition(270, 700);
        Button idk = new Button("don't know");
        canvas.add(idk);
        idk.setPosition(340, 700);
        Button back = new Button("Back to Home Screen");
        canvas.add(back);
        back.onClick(() -> {canvas.closeWindow();
        });






    }

    public void addRulesButton(CanvasWindow canvas){
        Button rulesButton = new Button("How to Play");
        rulesButton.setPosition(250, 370);
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
        addStartButton2(canvas);

    }


    public void addRules(CanvasWindow canvas){
        canvas.setBackground(black);
        GraphicsText rule1 = new GraphicsText("The player must think of a fictional or real character, object, or animal");
        rule1.setFontSize(20);
        rule1.setFillColor(black);
        rule1.setPosition(90,155);
        rule1.setFillColor(yellow);
        canvas.add(rule1);
        GraphicsText rule2 = new GraphicsText("Akinator, the Genie will then interogate the player");
        rule2.setFontSize(20);
        rule2.setFillColor(black);
        rule2.setPosition(90,180);
        rule2.setFillColor(yellow);
        canvas.add(rule2);
        GraphicsText rule3 = new GraphicsText("by a series of questions and will try to guess");
        rule3.setFontSize(20);
        rule3.setFillColor(black);
        rule3.setPosition(90,205);
        rule3.setFillColor(yellow);
        canvas.add(rule3);
        GraphicsText rule4 = new GraphicsText("who/what the player is thinking");
        rule4.setFontSize(20);
        rule4.setFillColor(black);
        rule4.setPosition(90,230);
        rule4.setFillColor(yellow);
        canvas.add(rule4);
        GraphicsText rule5 = new GraphicsText("The player can answer \"Yes\", \"No\", \"Don't know\"");
        rule5.setFontSize(20);
        rule5.setFillColor(black);
        rule5.setPosition(90,255);
        rule5.setFillColor(yellow);
        canvas.add(rule5);
        Button close = new Button("Go back");
        canvas.add(close);
        close.onClick(() -> {canvas.closeWindow();
        });

    }



    public void editRulesScreen(CanvasWindow canvas){
        addImage(canvas);
        addRules(canvas);
        
    }



}
