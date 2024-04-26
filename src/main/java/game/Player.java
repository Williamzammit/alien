package game;

import java.io.FileInputStream;
import java.io.InputStream;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player {
    Point2D position;
    Point2D velocity;
    int width;
    int height;

    Image image;

    public Player(Point2D position, Point2D velocity, int width, int height) throws Exception{
        this.position = position;
        this.velocity = velocity;
        this.width = width;
        this.height = height;

        InputStream stream = new FileInputStream("Player.png");
        image = new Image(stream);

    }

    public void draw(GraphicsContext gc){
        gc.drawImage(image, position.getX(), position.getY(), width, height);
    }

    public void updateRight(){
        position = new Point2D(position.getX() + velocity.getX(), position.getY());
    }

    public void updateLeft(){
        position = new Point2D(position.getX() - velocity.getX(), position.getY());
    }
    
}
