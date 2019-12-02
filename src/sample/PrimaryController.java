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
  @FXML
  private ChoiceBox<Integer> snakeLength;
  @FXML
  private Button play;
  @FXML
  private Pane menuPane;
  @FXML
  private ColorPicker headColor;
  @FXML
  private ColorPicker bodyColor;
  @FXML
  private TextField name;
  @FXML
  private ChoiceBox<Integer> fruitBox;
  @FXML
  private ChoiceBox<Integer> gridHeight;
  @FXML
  private ChoiceBox<Integer> gridLength;
  @FXML
  private ColorPicker fruitColor;
  @FXML
  private Pane gameBoard;
  private ObservableList<Integer> numbers = FXCollections.observableArrayList();
  private ObservableList<Integer> fruits = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10);
  private ObservableList<Integer> length = FXCollections.observableArrayList(15,20,25,30,35,40,45,50);
  private ObservableList<Integer> height = FXCollections.observableArrayList(15,20,25,30,35,40,45,50);
  @FXML
  void playGame(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader((getClass().getResource("gameBoard.fxml")));
    Parent root = loader.load();
    Stage stage = (Stage)menuPane.getScene().getWindow();
    stage.setScene(new Scene(root,gridLength.getValue()*PIXEL_SCALE,gridHeight.getValue()*PIXEL_SCALE));
    stage.show();
    stage.setResizable(false);
    SecondaryController secondary = loader.getController();
    Grid grid = new Grid(gridLength.getValue()*PIXEL_SCALE,gridHeight.getValue()*PIXEL_SCALE);
    Snake snake = new Snake(grid,snakeLength.getValue(),headColor.getValue(),bodyColor.getValue(),secondary.gameBoard,name.getText());
    secondary.playGame(snake,grid,fruitColor.getValue(),fruitBox.getValue(),name.getText());
  }
  @FXML
  private void initialize(){
    snakeLength.setValue(5);
    fruitBox.setValue(1);
    gridLength.setValue(15);
    gridHeight.setValue(15);
    for(int i = 3; i <= 10; ++i){
      numbers.add(i);
    }
    snakeLength.setItems(numbers);
    fruitBox.setItems(fruits);
    gridLength.setItems(length);
    gridHeight.setItems(height);
  }
}

