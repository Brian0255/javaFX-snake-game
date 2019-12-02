package sample;

import java.awt.Point;
import java.util.HashMap;
import java.util.Random;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Grid {
  private HashMap<Point,GridObject> points;
  private int x;
  private int y;

  public Grid(int x, int y){
    this.x = x;
    this.y = y;
    points = new HashMap<>();
  }

  public boolean inBounds(Point position, Pane gameBoard){
    Bounds bounds = gameBoard.getLayoutBounds();
    return bounds.contains(position.x,position.y);
  }

  public void generateFruit(int amt, Color color, Pane gameBoard){
    //cannot place fruit where a snake is
    for(int i = 0; i < amt; ++i) {
      boolean occupied = true;
      while (occupied) {
        //attempt to place a fruit in a random spot
        Random r = new Random();
        int randX = r.nextInt((x / 10));
        randX*=10;
        randX += 5;
        int randY = r.nextInt(y / 10);
        randY*=10;
        randY += 5;
        Point spot = new Point(randX, randY);
        if (!contains(spot)) {
          occupied = false;
          Circle circle = Snake.drawCircle(spot,5,gameBoard);
          circle.setFill(color);
          this.addApple(spot,circle);
        }
      }
    }
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void addApple(Point p,Circle c){
    points.put(p,new GridObject("apple",c));
  }

  public void addSnake(Point p, Circle c){
    points.put(p,new GridObject("snake",c));
  }

  public boolean contains(Point p){
    return points.containsKey(p);
  }

  public void removePoint(Point p){
    points.remove(p);
  }

  public GridObject get(Point p){
    return points.get(p);
  }
}
