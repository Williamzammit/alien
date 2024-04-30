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

    static Alien[] aliens;
    int numberOfAliens;

    

    static HashSet<String> currentlyActiveKeys;
    static Player player;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Alien Game");
        root = new Group();
        scene = new Scene(root);
        stage.setScene(scene);
        numberOfAliens = 10;
        player = new Player(new Point2D(200, 350), new Point2D(5, 5), 16, 16);
        aliens = new Alien[numberOfAliens];
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
                try{
                tickAndRender();
                } catch(Exception e){

                }
                player.draw(gc);
                player.periodic(gc);
                checkCollisions();

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

    private static void tickAndRender() throws Exception{

        if(currentlyActiveKeys.contains("LEFT")){
            player.updateLeft();
            //player.draw(gc);
        } else if(currentlyActiveKeys.contains("RIGHT")){
            player.updateRight();
            //player.draw(gc);
        }
        if(currentlyActiveKeys.contains("SPACE")){
            player.shootBullet();
        }

    }

    public void removeAliens(int x){
        aliens[x].defeatAlien(gc);
    }

    public void checkCollisions(){
        for(int i = 0; i < player.getBulletAmount(); i++){
            if(player.bullets.get(i).active){
            for(int j = 0; j < numberOfAliens; j++){
                if(
                    (player.getBulletX(i) > aliens[j].getAlienX() && player.getBulletX(i) < (aliens[j].getAlienX()+16)) 
                    &&
                    ((player.getBulletY(i) > aliens[j].getAlienY() || player.getBulletY(i)+16 > aliens[j].getAlienY())
                     && player.getBulletY(i) < (aliens[j].getAlienY()+16))
                ){
                    System.out.println("DBUISALKBFKHOALFBHAIOBFSAHKFAS");
                    aliens[j].defeatAlien(gc);
                    player.bullets.get(i).deleteBullet(gc);
                }
            }
        }
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