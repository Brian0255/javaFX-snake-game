package sample;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.awt.Point;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Snake {
  private Point direction;
  private Node head;
  private int fruitsEaten;
  private double animSpeed = 125;
  private int length;
  private Color bodyColor;
  private Color headColor;
  private final int PIXEL_SCALE = 10;
  private String name;

  private static class Node{
    private Point position;
    private Node next;
    Circle circle;
   private Node(Point position, Node next, Circle circle){
      this.position = position;
      this.next = next;
      this.circle = circle;
    }
  }

  public Point getDirection() {
    return direction;
  }

  public String getName() {
    return name;
  }

  public Snake(Grid grid,int length, Color headColor, Color bodyColor, Pane gameBoard, String name){
    this.length = length;
    this.name = name;
    this.fruitsEaten = 0;
    this.headColor = headColor;
    this.bodyColor = bodyColor;
    int offX = (((grid.getX()-10)/20))*10+5; //rounds to the nearest grid space in the middle even if grid sizes are even numbers by using integer division
    int offY = (((grid.getY()-10)/20))*10+5;
    int x = (int) (offX + gameBoard.getLayoutX());
    int y = (int) (offY + gameBoard.getLayoutY());
    Circle c = drawCircle(new Point(x,y),PIXEL_SCALE/2,gameBoard);
    c.setFill(headColor);
    Node head = new Node(new Point(x,y),null,c);
    grid.addSnake(head.position,head.circle);
    this.head = head;
  }

  private void eatFruit(){
    ++this.fruitsEaten;
    //if snake eats 5 fruit game speeds up by current anim speed + 10%
    if(this.fruitsEaten == 5){
      this.fruitsEaten = 0;
      animSpeed = animSpeed - animSpeed/10;
    }
  }

  public void generate(Grid grid, Pane gameBoard){
    Node prev = head;
    for(int i = 1; i <= length-1; ++i){
      Point position = new Point(head.position.x+(i*PIXEL_SCALE),head.position.y);
      Node node = new Node(position,null,null);
      Circle c = drawCircle(node.position,PIXEL_SCALE/2,gameBoard);
      node.circle = c;
      c.setFill(bodyColor);
      grid.addSnake(node.position,node.circle);
      prev.next = node;
      prev = prev.next;
    }
  }

  public void setDirection(Point direction) {
    this.direction = direction;
  }

  public void extend(Node newHead){
    newHead.next = head;
    head = newHead;
  }

  public static Circle drawCircle(Point position, int radius, Pane parent){
    Circle circle = new Circle();
    circle.setCenterX(position.x);
    circle.setCenterY(position.y);
    circle.setRadius(radius);
    parent.getChildren().add(circle);
    return circle;
  }

  private void moveHelper(Node node, Point newPosition,Grid grid, Pane gameBoard, Color fruitColor, SecondaryController controller){
    Point temp = node.position;
    grid.removePoint(temp);
    TranslateTransition anim = new TranslateTransition(Duration.millis(this.animSpeed),node.circle);
    anim.setByX(newPosition.x-temp.x);
    anim.setByY(newPosition.y-temp.y);
    anim.play();
    node.position = newPosition;
    grid.addSnake(newPosition,node.circle);
    if(node.next!=null) {
      moveHelper(node.next, temp,grid,gameBoard,fruitColor,controller);
    }
    else {
      anim.setOnFinished(event -> {
        this.move(grid,gameBoard,fruitColor,controller);
      });
    }
  }

  public int getLength() {
    return length;
  }

  public void move(Grid grid, Pane gameBoard, Color fruitColor, SecondaryController controller) {
    Point temp = head.position;
    grid.removePoint(temp);
    int newX = head.position.x + direction.x;
    int newY = head.position.y + direction.y;
    Point next = new Point(newX, newY);
    if (!grid.inBounds(next,gameBoard)) {
      controller.gameOver(this);
    } else {
      if (grid.get(next) != null) {
        if (grid.get(next).getType().equals("apple")) {
          this.eatFruit();
          Circle add = grid.get(next).getCircle();
          add.setFill(headColor);
          head.circle.setFill(bodyColor);
          grid.removePoint(next);
          Node toAdd = new Node(next, null, add);
          extend(toAdd);
          grid.addSnake(toAdd.position, add);
          grid.addSnake(temp, head.circle);
          ++this.length;
          grid.generateFruit(1, fruitColor, gameBoard);
          moveHelper(head.next, temp, grid, gameBoard, fruitColor,controller);
        } else if (grid.get(next).getType().equals("snake")) {
          controller.gameOver(this);
        }
      } else {
        TranslateTransition anim = new TranslateTransition(Duration.millis(this.animSpeed), head.circle);
        anim.setByX(direction.x);
        anim.setByY(direction.y);
        anim.play();
        head.position = next;
        grid.addSnake(head.position, head.circle);
        moveHelper(head.next, temp, grid, gameBoard, fruitColor,controller);
      }
    }
  }
}
