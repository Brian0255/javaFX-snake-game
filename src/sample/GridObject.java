package sample;

import javafx.scene.shape.Circle;

public class GridObject {
  private String type;
  private Circle circle;

  public String getType() {
    return type;
  }

  public Circle getCircle() {
    return circle;
  }

  public GridObject(String type, Circle circle){
    this.type = type;
    this.circle = circle;
  }

}
