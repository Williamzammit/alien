package game;

import java.io.FileInputStream;
import java.io.InputStream;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet {
    Point2D position;
    Point2D velocity;
    int width;
    int height;
    boolean active;

    Image image;
    InputStream stream;

    public Bullet(Point2D position, Point2D velocity, int width, int height, boolean active) throws Exception{
        this.position = position;
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        this.active = active;

        stream = new FileInputStream("Bullet.png");
        image = new Image(stream);
    }

    public void updateBullet(){
        if(active){
        position = new Point2D(position.getX(), position.getY() - velocity.getY());
        }
    }

    public void draw(GraphicsContext gc){
        if(active){
        gc.drawImage(image, position.getX(), position.getY(), width, height);
        }
    }

    public void deleteBullet(GraphicsContext gc){
        active = false;
        gc.drawImage(null, 0, 400, 16, 16);
        position = new Point2D(400, 400);
    }

    
}
