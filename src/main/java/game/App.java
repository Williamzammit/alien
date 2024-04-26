package game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Group root;
    private static Canvas canvas;
    public static GraphicsContext gc;

    private static int distanceTimer;
    private static int alienCounter;

    

    static HashSet<String> currentlyActiveKeys;
    static Player player;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Alien Game");
        root = new Group();
        scene = new Scene(root);
        stage.setScene(scene);
        int numberOfAliens = 100;
        player = new Player(new Point2D(200, 350), new Point2D(5, 5), 16, 16);
        Alien[] aliens = new Alien[numberOfAliens];
        for(int i = 0; i < aliens.length; i++){
            aliens[i] = new Alien(new Point2D(0, 0), new Point2D(2, 2), 16, 16, 1);
        }
        
        

        canvas = new Canvas(400, 400);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        
        aliens[0].draw(gc);
        prepareActionHandlers();
        stage.show();
        

        alienCounter = 0;
        distanceTimer = 0;
        
        new AnimationTimer(){
            public void handle(long now){
                gc.setFill(Color.PINK);
                gc.fillRect(0, 0, 400, 400);
                tickAndRender();
                player.draw(gc);

                for(int i = 0; i < alienCounter; i++){
                    
                        aliens[i].update();
                        aliens[i].draw(gc);
                    
                }
                distanceTimer++;
                if(distanceTimer>40 && alienCounter < numberOfAliens){
                    alienCounter++;
                    distanceTimer = 0;
                }

            }//End handle()
        }.start();
    }

    private static void prepareActionHandlers()
    {
      currentlyActiveKeys = new HashSet<String>();
      scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
        @Override
        public void handle(KeyEvent event){
          currentlyActiveKeys.add(event.getCode().toString());
          System.out.println(event.getCode().toString());
        }//end handle
      });//end setOnKeyPressed

      scene.setOnKeyReleased(new EventHandler<KeyEvent>()
      {
         @Override
        public void handle(KeyEvent event)
        {
          currentlyActiveKeys.remove(event.getCode().toString());
        }
        
      });//end setOnKeyReleased
    }//end prepareActionHandlers()

    private static void tickAndRender(){

        if(currentlyActiveKeys.contains("LEFT")){
            player.updateLeft();
            //player.draw(gc);
        } else if(currentlyActiveKeys.contains("RIGHT")){
            player.updateRight();
            //player.draw(gc);
        }

    }

    /*static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }*/

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}