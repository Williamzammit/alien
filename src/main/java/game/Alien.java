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
    boolean direction;
    int downwardDistance;

    public Alien(Point2D position, Point2D velocity, int width, int height, int health) throws Exception{
        this.position = position;
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        this.health = health;

        InputStream stream = new FileInputStream("Alien.png");
        image = new Image(stream);
        state = 0;
        direction = true;//True = right
        downwardDistance = 1;
    }

    public void update(){
        if(health > 0){
        if(direction && position.getX() > (400-width)){
            updateDown();
        } else if(!direction && position.getX() < 0){
            updateDown();
        } else if(direction){
            updateRight();
        } else if(!direction){
            updateLeft();
        }
        }   
        
    }

    public void draw(GraphicsContext gc){
        if(health > 0){
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

    public void updateRight(){
        position = new Point2D(position.getX() + velocity.getX(), position.getY());
    }

    public void updateLeft(){
        position = new Point2D(position.getX() - velocity.getX(), position.getY());
    }

    public void updateDown(){
        if(position.getY() > 16*downwardDistance){
            direction = !direction;
            downwardDistance++;
        } else {
        position = new Point2D(position.getX(), position.getY() + velocity.getY());
        }
    }

    public void defeatAlien(GraphicsContext gc){
        health = 0;
        position = new Point2D(0, 400);
        gc.drawImage(null, position.getX(), position.getY(), 16, 16);
    }

    public double getAlienX(){
        return position.getX();
    }

    public double getAlienY(){
        return position.getY();
    }
    
}
