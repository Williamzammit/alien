package game;

import java.io.FileInputStream;
import java.io.InputStream;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Alien {
    Point2D position;
    Point2D velocity;
    int width;
    int height;
    int health;

    Image image;
    int state;

    public Alien(Point2D position, Point2D velocity, int width, int height, int health) throws Exception{
        this.position = position;
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        this.health = health;

        InputStream stream = new FileInputStream("Alien.png");
        image = new Image(stream);
        state = 0;
    }

    public void update(){
        position = new Point2D(position.getX() + velocity.getX(), 0);
    }

    public void draw(GraphicsContext gc){
        if(state < 10){
            gc.drawImage(image, 16, 0, width, height, position.getX(), position.getY(), width, height);
            state++;
        } else {
            gc.drawImage(image, 0, 0, width, height, position.getX(), position.getY(), width, height);
            state++;
        }
        if (state > 20){
            state = 0;
        }
    }
    
}
