package game;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player {
    Point2D position;
    Point2D velocity;
    int width;
    int height;

    Image image;
    ArrayList<Bullet> bullets;
    static int bulletDelay;

    public Player(Point2D position, Point2D velocity, int width, int height) throws Exception{
        this.position = position;
        this.velocity = velocity;
        this.width = width;
        this.height = height;

        InputStream stream = new FileInputStream("Player.png");
        image = new Image(stream);
        bullets = new ArrayList<Bullet>();
        bulletDelay = 20;

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

    public void periodic(GraphicsContext gc){
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).updateBullet();
            bullets.get(i).draw(gc);
        }
        if(bulletDelay > 0){
            bulletDelay--;
        }
        
    }

    public void shootBullet() throws Exception{
        if(bulletDelay == 0){
            bullets.add(new Bullet(new Point2D(position.getX(), position.getY() - 16), new Point2D(0, 4), 16, 16, true));
            bulletDelay = 20;
        }
        
    }

    public double getBulletX(int x){
        return bullets.get(x).position.getX();
    }

    public double getBulletY(int x){
        return bullets.get(x).position.getY();
    }

    public int getBulletAmount(){
        return bullets.size();
    }
    
}
