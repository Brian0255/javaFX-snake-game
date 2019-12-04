package sample;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PrimaryController {
  private final int PIXEL_SCALE = 10;
  public ChoiceBox<Integer> snakeLength;
  public Button play;
  public Pane menuPane;
  public ColorPicker headColor;
  public ColorPicker bodyColor;
  public TextField name;
  public ChoiceBox<Integer> fruitBox;
  public ChoiceBox<Integer> gridHeight;
  public ChoiceBox<Integer> gridLength;
  public ColorPicker fruitColor;
  private ObservableList<Integer> numbers = FXCollections.observableArrayList();
  private ObservableList<Integer> fruits = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10);
  private ObservableList<Integer> length = FXCollections.observableArrayList(15,20,25,30,35,40,45,50);
  private ObservableList<Integer> height = FXCollections.observableArrayList(15,20,25,30,35,40,45,50);

  @FXML
  void playGame(ActionEvent event) throws IOException {
    //save into game settings so user can play again quickly
    GameSettings.length = snakeLength.getValue();
    GameSettings.bodyColor = bodyColor.getValue();
    GameSettings.fruitColor = fruitColor.getValue();
    GameSettings.fruits = fruitBox.getValue();
    GameSettings.headColor = headColor.getValue();
    GameSettings.name = name.getText();
    GameSettings.height = gridHeight.getValue();
    GameSettings.width = gridLength.getValue();
    GameSettings.played = true;

    FXMLLoader loader = new FXMLLoader((getClass().getResource("gameBoard.fxml")));
    Parent root = loader.load();
    Stage stage = (Stage)menuPane.getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.setWidth(gridLength.getValue()*PIXEL_SCALE+GameSettings.OFF_X);
    stage.setHeight(gridHeight.getValue()*PIXEL_SCALE+GameSettings.OFF_Y);
    stage.show();
    SecondaryController secondary = loader.getController();
    Grid grid = new Grid(gridLength.getValue()*PIXEL_SCALE,gridHeight.getValue()*PIXEL_SCALE);
    Snake snake = new Snake(grid,snakeLength.getValue(),headColor.getValue(),bodyColor.getValue(),secondary.gameBoard,name.getText());
    secondary.playGame(snake,grid,fruitColor.getValue(),fruitBox.getValue(),name.getText());
  }
  @FXML
  private void initialize(){
    for (int i = 3; i <= 10; ++i) {
      numbers.add(i);
    }
    snakeLength.setItems(numbers);
    fruitBox.setItems(fruits);
    gridLength.setItems(length);
    gridHeight.setItems(height);
    if(!GameSettings.played) {
      snakeLength.setValue(5);
      fruitBox.setValue(1);
      gridLength.setValue(15);
      gridHeight.setValue(15);
    }
    else{
      snakeLength.setValue(GameSettings.length);
      fruitBox.setValue(GameSettings.fruits);
      gridLength.setValue(GameSettings.width);
      gridHeight.setValue(GameSettings.height);
      fruitColor.setValue(GameSettings.fruitColor);
      headColor.setValue(GameSettings.headColor);
      bodyColor.setValue(GameSettings.bodyColor);
      name.setText(GameSettings.name);
    }
  }
}

