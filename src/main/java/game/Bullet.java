package game;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Bullet {
    Point2D position;
    Point2D velocity;
    int width;
    int height;

    Image image;

    public Bullet(Point2D position, Point2D velocity, int width, int height){
        this.position = position;
        this.velocity = velocity;
        this.width = width;
        this.height = height;
    }

    
}
