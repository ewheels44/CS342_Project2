
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class GameLogic{
  

  public GridPane creategameboard(){
    GridPane gameboard = new GridPane();
    gameboard.setAlignment(Pos.CENTER);
    gameboard.setHgap(10);
    gameboard.setVgap(10);
    gameboard.setStyle("-fx-font-size: 100;" + "-fx-boarder-size: 100;");


    for (int x = 0; x < 7; x++) {
      for (int i = 0; i < 6; i++) {

        GameButton b1 = new GameButton();
        gameboard.add(b1, x, i);
        b1.setOnAction(JavaFXTemplate.disable());
      }
    }

    return gameboard;
  }

}
