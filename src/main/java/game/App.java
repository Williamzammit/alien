package game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    private static int distanceTimer;
    private static int alienCounter;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Alien Game");
        root = new Group();
        scene = new Scene(root);
        stage.setScene(scene);
        int numberOfAliens = 5;
        Alien[] aliens = new Alien[numberOfAliens];
        for(int i = 0; i < aliens.length; i++){
            aliens[i] = new Alien(new Point2D(0, 0), new Point2D(0.1, 0), 16, 16, 1);
        }
        
        

        canvas = new Canvas(400, 400);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        aliens[0].draw(gc);
        stage.show();
        

        alienCounter = 0;
        distanceTimer = 0;
        new AnimationTimer(){
            public void handle(long now){
                gc.setFill(Color.GREEN);
                gc.fillRect(0, 0, 400, 400);

                for(int i = 0; i <= alienCounter; i++){
                    for(int j = 0; j<i; j++){
                        aliens[j].update();
                        aliens[j].draw(gc);
                    }
                }
                distanceTimer++;
                if(distanceTimer>50){
                    alienCounter++;
                    distanceTimer = 0;
                }

            }//End handle()
        }.start();
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