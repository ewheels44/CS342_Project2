
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class GameLogic {

  private GameData gamedata; 
  private GameButton pieces;
  private GridPane gameboard;

  private int GameBoardROW = 6;
  private int GameBoardCOL = 7;

  private GameButton piceseslocation[][];
  
  private EventHandler<ActionEvent> disableButton;

  public GridPane creategameboard(Stage _pirmarystage){
    gameboard = new GridPane();
    gameboard.setAlignment(Pos.CENTER);
    gameboard.setHgap(10);
    gameboard.setVgap(10);
    gameboard.setStyle("-fx-font-size: 100;" + "-fx-boarder-size: 100;");

    piceseslocation = new GameButton[GameBoardROW][GameBoardCOL]; 

    for (int i = 0; i < GameBoardROW; i++) {
      for (int j = 0; j < GameBoardCOL; j++) {

        pieces = new GameButton();
        pieces.addXandYcords(pieces, i, j);
        pieces.setOnAction(disableButton(_pirmarystage));

        // gameboard doesnt abide by the rules of rows and colums apparently
        //
        gameboard.add(pieces, j, i);        


        piceseslocation[i][j] = pieces;
      }
    }

    gamedata = new GameData();

    return gameboard;
  }

  public EventHandler<ActionEvent> disableButton(Stage _pirmarystage){
    disableButton = new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event) {
        // TODO Auto-generated method stub
        GameButton b1 = (GameButton) event.getSource();
        b1 = DropPiece(b1, b1.getXcord(), b1.getYcord());
        b1.setDisable(true);
        b1.setisValid(false);

        String turn = whosTurn();
        b1.setText(turn);
        b1.setPieceColor(turn);

        JavaFXTemplate.addturnDisp(turn);

        placePiece(b1);
        if(gamedata.isWonGame()){
          _pirmarystage.setScene(JavaFXTemplate.winnerWinnerChickenDinner()); 
        }
      }
    };
    
    return disableButton;
  }

    //
    // this is a test
    // 
//     public EventHandler<ActionEvent> disableButton(){
//       @Override
//       public void handle(ActionEvent event) {
//         // TODO Auto-generated method stub
//         GameButton b1 = (GameButton) event.getSource();
//         b1 = gameLogic.DropPiece(b1, b1.getXcord(), b1.getYcord());
//         b1.setDisable(true);
//         b1.setisValid(false);
// 
//         String turn = gameLogic.whosTurn();
//         b1.setText(turn);
//         b1.setPieceColor(turn);
//         whosmove.getItems().add(turn);
//         gameLogic.placePiece(b1);
//         if(gameData.isWonGame()){
//         }
//         turndisplay.setText(gameLogic.nextTurn());
//       }
//     };

//   public Node getPiece(int _X, int _Y){
//     Node result = null;
// 
//     ObservableList<Node> pieces = gameboard.getChildren(); 
// 
//     for(Node node : pieces){
//       if(gameboard.getRowIndex(node) == _X && gameboard.getColumnIndex(node) == _Y){
//         result = node;
//         return result;
//       }
//     }
// 
//     return null;
//   }

  public GameButton getPiece(){
    return this.pieces;
  }

  public GameButton getPieceXandY(int _X, int _Y){
    return piceseslocation[_X][_Y];
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
    
    hasNeighborSameColor(_piece);
  }

  
  public GameButton DropPiece(GameButton _piece, int X, int Y){

    System.out.println("X cord: " + _piece.getXcord());
    System.out.println("Y cord: " + _piece.getYcord());

    if(X >= 5) return _piece;

    GameButton PieceBelow = getPieceXandY(X+1, Y);

    if(PieceBelow.getisValid() && PieceBelow.getXcord() != 6){
      _piece = PieceBelow;
      return DropPiece(_piece, X = X + 1, Y);
    }
    else return _piece;

  }

  public void hasNeighborSameColor(GameButton _piece){
    gamedata.incTotalpices();

    int X = _piece.getXcord();
    int Y = _piece.getYcord();

    GameButton PieceBelow = null;
    GameButton PieceLeft = null;
    GameButton PieceRight = null;

    GameButton PieceDiagonalRandL = null;
    GameButton PieceDiagonalLandR = null;

//     // left most
//     if(Y == 0){
//       PieceRight = getPiece(X, Y+1);
//       PieceDiagonalRandL = getPiece(X-1, Y+1);
//     }
// 
//     // bottom
//     if(X == 5){
//       PieceRight = getPiece(X, Y+1);
//       PieceLeft =  getPiece(X, Y-1);
//       PieceDiagonalRandL = getPiece(X-1, Y+1);
//       PieceDiagonalLandR = getPiece(X-1, Y-1);
//     }
// 
//     // rigth most
//     if(Y == 6){
//       PieceLeft =  getPiece(X, Y-1);
//       PieceDiagonalLandR = getPiece(X-1, Y-1);
//     }
    
    PieceRight = getPieceXandY(X, Y+1);
    PieceDiagonalRandL = getPieceXandY(X-1, Y+1);
    
    if(PieceRight.getPieceColor() == _piece.getPieceColor()){
      int leftandrightcount = PieceRight.getLeftRigth();
      _piece.setLeftRigth(leftandrightcount+=1); 
      hasWon(_piece);
    }

        
  }

  public void hasWon(GameButton _piece){
    System.out.println("This is getLeftRigth: " + _piece.getLeftRigth());
    if(_piece.getLeftRigth() + 1 == 4){
      System.out.println("HAS WON!!!");
      gamedata.setWonGame(true);
    }
    
  }

  public String whosTurn(){
    return gamedata.getWhosTurn(); 
  }

  public String nextTurn(){
    return gamedata.getNextTurn();
  }

}
