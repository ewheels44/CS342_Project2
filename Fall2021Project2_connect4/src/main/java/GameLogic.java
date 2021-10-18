
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class GameLogic{

  private GameData gamedata; 
  private GameButton pieces;
  

  public GridPane creategameboard(){
    GridPane gameboard = new GridPane();
    gameboard.setAlignment(Pos.CENTER);
    gameboard.setHgap(10);
    gameboard.setVgap(10);
    gameboard.setStyle("-fx-font-size: 100;" + "-fx-boarder-size: 100;");


    for (int x = 0; x < 7; x++) {
      for (int i = 0; i < 6; i++) {

        pieces = new GameButton();
        pieces.setXcord(x);
        pieces.setYcord(i);
        gameboard.add(pieces, x, i);
        pieces.setOnAction(JavaFXTemplate.disable());
      }
    }

    return gameboard;
  }

  public void hasWon(){

  }

}
