
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
        pieces.addXandYcords(pieces, x, i);
        gameboard.add(pieces, x, i);
        pieces.setOnAction(JavaFXTemplate.disable());
      }
    }

    gamedata = new GameData();

    return gameboard;
  }

  // does all the sanity "checks"
  //
  // hasNeighbor
  // isValidMove
  // hasWon
  //
  public void placePiece(GameButton _piece){
    System.out.println(_piece.getXcord());
    System.out.println(_piece.getYcord());

    if(gamedata.getTotalPieces() == 0 && _piece.getYcord() < 5 ){
       
    }
  }

  // checks to see if the players choosen postion
  // is a vaild pos
  //
  public void isValidMove(){
    
    // if the piece cannot be "dropped" any farther
    // then it is a valid move 

  }
  
  public void DropPiece(GameButton _piece, int Y){

    

//     if(_piece.getYcord() < 5){
//       _piece.setYcord(Y);
// 
//       // if it can be moved down
//       if(!_piece.getisValid(_piece)){
//         Y = Y + 1;  
//         return;
//       }
//       else{
//         Y = Y - 1;
//         DropPiece(_piece, Y);
//       }
//     }
  }

  public void hasNeighbor(){
    gamedata.incTotalpices();
    
    if(gamedata.getTotalPieces() < 4){
      return;
    }
    else hasWon();
        
  }

  public void hasWon(){
    
  }

  public String whosTurn(){
    return gamedata.getWhosTurn(); 
  }

  public String nextTurn(){
    return gamedata.getNextTurn();
  }

}
