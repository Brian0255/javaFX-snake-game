package sample;

import java.awt.Point;
import java.io.IOException;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SecondaryController {
  private final Point UP = new Point(0,-10);
  private final Point DOWN = new Point(0,10);
  private final Point LEFT = new Point(-10,0);
  private final Point RIGHT = new Point(10,0);


  public Pane gameBoard;
  public Button playAgain;
  public Label gameOver;

  private boolean started = false;

  @FXML
  void pressed(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader((getClass().getResource("menu.fxml")));
    Parent root = loader.load();
    Stage stage = (Stage)gameBoard.getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.setWidth(429+GameSettings.OFF_X);
    stage.setHeight(499+GameSettings.OFF_Y);
    stage.show();
  }

  private void animate(Snake snake, Grid grid, Color fruitColor, String name){
    snake.move(grid,gameBoard,fruitColor,this);
  }

  void gameOver(Snake snake){
    Stage stage = (Stage) gameBoard.getScene().getWindow();
    stage.setWidth(gameOver.getWidth()+GameSettings.OFF_X);
    stage.setHeight(gameOver.getHeight()+playAgain.getHeight()+GameSettings.OFF_Y);
    gameOver.setText("Game over, " + snake.getName() + "! You got a score of " + snake.getLength() + ".");
    gameOver.setLayoutX(gameBoard.getLayoutX());
    gameOver.setLayoutY(gameBoard.getLayoutY());
    playAgain.setLayoutX(gameBoard.getLayoutX());
    playAgain.setLayoutY(gameBoard.getLayoutY()+gameOver.getHeight());
    playAgain.toFront();
    playAgain.setVisible(true);
    gameOver.toFront();
    gameOver.setVisible(true);
  }

  void playGame(Snake snake, Grid grid, Color fruitColor, int fruitAmt, String name){
    snake.generate(grid,gameBoard);
    Scene scene = gameBoard.getScene();
    scene.addEventHandler(KeyEvent.KEY_PRESSED,key ->{
      switch(key.getCode()){
        case UP:
          if(snake.getDirection() != DOWN) {
            snake.setDirection(this.UP);
            if (!started) {
              animate(snake, grid, fruitColor, name);
              started = true;
            }
          }
          break;
        case DOWN:
          if(snake.getDirection() != UP) {
            snake.setDirection(this.DOWN);
            if (!started) {
              animate(snake, grid, fruitColor, name);
              started = true;
            }
          }
          break;
        case LEFT:
          if(snake.getDirection() != RIGHT) {
            snake.setDirection(this.LEFT);
            if (!started) {
              animate(snake, grid, fruitColor, name);
              started = true;
            }
          }
          break;
        case RIGHT:
          if(snake.getDirection() != LEFT) {
            snake.setDirection(this.RIGHT);
            if (!started) {
              animate(snake, grid, fruitColor, name);
              started = true;
            }
          }
          break;
        default:
          break;
      }
    });

    grid.generateFruit(fruitAmt,fruitColor,gameBoard);
  }
}
