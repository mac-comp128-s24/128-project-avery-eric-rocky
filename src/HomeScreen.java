import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.TextAlignment;
import edu.macalester.graphics.ui.Button;



/**
 * Helper class for drawing UI
 * 
 * @author Avery, Eric, Rocky on Apr 30, 2024
 */
public class HomeScreen {
    


    private DecisionTreeNode currentNode;
    public CanvasWindow rulesScreen;
    private CanvasWindow startScreen;
    private Color yellow = Color.YELLOW;
    private Color black = Color.BLACK;
    


    public HomeScreen(){
    
    }


    public void addStartButton(CanvasWindow canvas){
        Button startButton = new Button("Start Game");
        startButton.setPosition(250, 400);
        startButton.setScale(5, 5);
        canvas.add(startButton);
        startButton.onClick(() -> {
            startScreen = new CanvasWindow("Akinator", 600, 800);
            editScreen(startScreen);
        });

    }

    public void addStartButton2(CanvasWindow canvas) {
        Button startButton = new Button("Tree Viewer");
        startButton.setPosition(250, 430);
        startButton.setScale(5, 5);
        canvas.add(startButton);
        startButton.onClick(() -> {
            CanvasWindow treeScreen = new CanvasWindow("TreeViewer", 800, 800);
            treeScreen.setBackground(black);
            GraphicsText loadingText = new GraphicsText("Loading...", 400, 400);
            loadingText.setAlignment(TextAlignment.CENTER);
            loadingText.setFillColor(Color.YELLOW);
            treeScreen.add(loadingText);
            treeScreen.draw();
            DecisionTreeViewer viewer = new DecisionTreeViewer(Game.getTree(), treeScreen, 400, 10);
            treeScreen.remove(loadingText);
        });
    }


    public void editScreen(CanvasWindow canvas) {
        canvas.setBackground(black);
        GraphicsText loadingText = new GraphicsText("Loading...", 300, 400);
        loadingText.setAlignment(TextAlignment.CENTER);
        loadingText.setFillColor(Color.YELLOW);
        canvas.add(loadingText);
        canvas.draw();

        Image quote = new Image("images/quote.png");
        quote.setMaxHeight(400);
        quote.setMaxWidth(600);
        quote.setCenter(300, 370);
        canvas.add(quote);
        addImage(canvas);
        Button yes = new Button("Yes");
        canvas.add(yes);
        yes.setPosition(200, 700);
        Button no = new Button("No");
        canvas.add(no);
        no.setPosition(270, 700);
        Button idk = new Button("I don't know");
        canvas.add(idk);
        idk.setPosition(340, 700);
        Button back = new Button("Back to Home Screen");
        canvas.add(back);
        back.onClick(() -> {
            canvas.closeWindow();
        });
        Button restart = new Button("Restart");
        canvas.add(restart);
        restart.setPosition(270, 730);


        GraphicsText speechBubble = new GraphicsText();
        speechBubble.setWrappingWidth(150);
        speechBubble.setCenter(quote.getCenter());
        speechBubble.setAlignment(TextAlignment.CENTER);
        speechBubble.setFillColor(Color.BLACK);
        canvas.add(speechBubble);

        currentNode = Game.getTree().getRoot();
        speechBubble.setText(talk(currentNode));
        yes.onClick(() -> {
            if (!currentNode.isLeaf()) {
                DecisionTreeNode.QuestionNode questionNode = (DecisionTreeNode.QuestionNode) currentNode;
                currentNode = questionNode.getYes();
                speechBubble.setText(talk(currentNode));
                speechBubble.setCenter(quote.getCenter());

            } else {
                speechBubble.setText("I never fail!!!");
                speechBubble.setCenter(quote.getCenter());

            }
        });
        no.onClick(() -> {
            if (!currentNode.isLeaf()) {
                DecisionTreeNode.QuestionNode questionNode = (DecisionTreeNode.QuestionNode) currentNode;
                currentNode = questionNode.getNo();
                speechBubble.setText(talk(currentNode));
                speechBubble.setCenter(quote.getCenter());

            } else {
                speechBubble.setText("You're lying!!!");
                speechBubble.setCenter(quote.getCenter());

            }
        });
        restart.onClick(() -> { currentNode = Game.getTree().getRoot(); speechBubble.setText(talk(currentNode)); });
        canvas.remove(loadingText);
    }

    private String talk(DecisionTreeNode node) {
        String text;
        if (node.isLeaf()) {
            DecisionTreeNode.AnswerNode answerNodeNode = (DecisionTreeNode.AnswerNode) node;
            text = "Is it any of these? "
                + answerNodeNode.getAnswers().subList(0, Math.min(answerNodeNode.getAnswers().size(), 10))
                    .toString();
        } else {
            DecisionTreeNode.QuestionNode questionNode = (DecisionTreeNode.QuestionNode) node;
            text = questionNode.getQuestion();
        }
        return text;
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
        rule1.setPosition(90, 155);
        rule1.setFillColor(yellow);
        canvas.add(rule1);
        GraphicsText rule2 = new GraphicsText("Akinator, the Genie will then interogate the player");
        rule2.setFontSize(20);
        rule2.setFillColor(black);
        rule2.setPosition(90, 180);
        rule2.setFillColor(yellow);
        canvas.add(rule2);
        GraphicsText rule3 = new GraphicsText("by a series of questions and will try to guess");
        rule3.setFontSize(20);
        rule3.setFillColor(black);
        rule3.setPosition(90, 205);
        rule3.setFillColor(yellow);
        canvas.add(rule3);
        GraphicsText rule4 = new GraphicsText("who/what the player is thinking");
        rule4.setFontSize(20);
        rule4.setFillColor(black);
        rule4.setPosition(90, 230);
        rule4.setFillColor(yellow);
        canvas.add(rule4);
        GraphicsText rule5 = new GraphicsText("The player can answer \"Yes\", \"No\", \"Don't know\"");
        rule5.setFontSize(20);
        rule5.setFillColor(black);
        rule5.setPosition(90, 255);
        rule5.setFillColor(yellow);
        canvas.add(rule5);
        Button close = new Button("Go back");
        canvas.add(close);
        close.onClick(() -> {
            canvas.closeWindow();
        });

    }



    public void editRulesScreen(CanvasWindow canvas){
        addImage(canvas);
        addRules(canvas);
        
    }



}
