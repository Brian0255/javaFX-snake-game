package sample;

import java.awt.Point;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SecondaryController {
  public final Point UP = new Point(0,-10);
  public final Point DOWN = new Point(0,10);
  public final Point LEFT = new Point(-10,0);
  public final Point RIGHT = new Point(10,0);

  public Pane gameBoard;
  public Label gameOver;

  public boolean started = false;

  public void animate(Snake snake, Grid grid, Color fruitColor, String name){
    snake.move(grid,gameBoard,fruitColor,this);
  }

  public void gameOver(Snake snake){
    Stage stage = (Stage) gameBoard.getScene().getWindow();
    stage.setWidth(220);
    stage.setHeight(100);
    gameOver.setText("Game over, " + snake.getName() + "! You got a score of " + snake.getLength() + ".");
    gameOver.setLayoutX(gameBoard.getLayoutX());
    gameOver.setLayoutY(gameBoard.getLayoutY());
    gameOver.toFront();
    gameOver.setVisible(true);
  }

  public void playGame(Snake snake, Grid grid, Color fruitColor, int fruitAmt, String name){
    snake.generate(grid,gameBoard);
    Scene scene = gameBoard.getScene();

    scene.addEventHandler(KeyEvent.KEY_PRESSED,key ->{
      switch(key.getCode()){
        case UP:
          snake.setDirection(this.UP);
          if(!started){
            animate(snake,grid,fruitColor,name);
            started = true;
          }
          break;
        case DOWN:
          snake.setDirection(this.DOWN);
          if(!started) {
            animate(snake, grid, fruitColor,name);
            started = true;
          }
          break;
        case LEFT:
          snake.setDirection(this.LEFT);
          if(!started) {
            animate(snake, grid, fruitColor,name);
            started = true;
          }
          break;
        case RIGHT:
          snake.setDirection(this.RIGHT);
          if(!started) {
            animate(snake, grid, fruitColor,name);
            started = true;
          }
          break;
        default:
          break;
      }
    });

    grid.generateFruit(fruitAmt,fruitColor,gameBoard);
  }
}
