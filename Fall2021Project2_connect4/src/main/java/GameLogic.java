
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.lang.String;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ListIterator;

public class GameLogic {

  private GameData gamedata; 
  private GameButton pieces;
  private GridPane gameboard;

  private int GameBoardROW = 6;
  private int GameBoardCOL = 7;

  private GameButton piceseslocation[][];
  
  private EventHandler<ActionEvent> disableButton;

  private String playerWon;

  public GridPane creategameboard(Stage _primarystage){
    gameboard = new GridPane();
    gameboard.setAlignment(Pos.CENTER);
    gameboard.setHgap(5);
    gameboard.setVgap(5);
    gameboard.setStyle("-fx-font-size: 50;" + "-fx-boarder-size: 50;");

    piceseslocation = new GameButton[GameBoardROW][GameBoardCOL]; 

    for (int i = 0; i < GameBoardROW; i++) {
      for (int j = 0; j < GameBoardCOL; j++) {

        pieces = new GameButton();
        pieces.addXandYcords(pieces, i, j);
        pieces.setOnAction(disableButton(_primarystage));

        // gameboard doesnt abide by the rules of rows and colums apparently
        // ^ .add takes col first then row
        gameboard.add(pieces, j, i);


        piceseslocation[i][j] = pieces;
      }
    }

    gamedata = new GameData();

    return gameboard;
  }

  public EventHandler<ActionEvent> disableButton(Stage _primarystage){
    disableButton = new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event) {
        // TODO Auto-generated method stub
        GameButton b1 = (GameButton) event.getSource();
        b1 = DropPiece(b1, b1.getXcord(), b1.getYcord());
        b1.setDisable(true);
        b1.setisValid(false);

        String turn = whosTurn();
        // P1 == B | P2 == R, just set the whole box to a color
        if ( turn == "B") {
          b1.setStyle ("-fx-background-color: #0000f5;");
        }
        else {
          b1.setStyle ("-fx-background-color: #ff0000;");
        }

//        b1.setText(turn);
        b1.setPieceColor(turn);
        JavaFXTemplate.addturnDisp(turn);
        placePiece(b1);
        if(gamedata.isWonGame()){
          _primarystage.setScene(JavaFXTemplate.winnerWinnerChickenDinner()); 
        }
      }
    };
    
    return disableButton;
  }


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
    gamedata.incTotalpices();
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


  private boolean iswonleftright(GameButton _piece, String _winningcombo){

    String columpattern = "";

    for(int i = 0; i < GameBoardCOL; i++){
      if(piceseslocation[_piece.getXcord()][i].getPieceColor() != null){
        columpattern = columpattern.concat(piceseslocation[_piece.getXcord()][i].getPieceColor());
      }
    }

    return Pattern.compile(_winningcombo).matcher(columpattern).matches();
  }

  private boolean iswonUPDown(GameButton _piece, String _winningcombo){
    
    String columpattern = "";

    for(int i = 0; i < GameBoardROW; i++){
      if(piceseslocation[i][_piece.getYcord()].getPieceColor() != null){
        columpattern = columpattern.concat(piceseslocation[i][_piece.getYcord()].getPieceColor());
      }
    }

    return Pattern.compile(_winningcombo).matcher(columpattern).matches();
  }

  private boolean iswonDiagonalRandL(GameButton _piece, String _winningcombo){
    
    String columpattern = "";

    int startX = GameBoardROW - 1;
    // int startY = _piece.getYcord() - (_piece.getYcord() - 1);
    // if(startY <= 1) startY = 0;
    int startY = 0;

    // System.out.println("this is starty: " + startY);

    for (int j = startY; j < GameBoardCOL; j++) {
      if(piceseslocation[startX][j].getPieceColor() != null){
        columpattern = columpattern.concat(piceseslocation[startX--][j].getPieceColor());
      }

      // System.out.println("Start should decriment: " + startX);
      // System.out.println("j should decriment: " + j);
    }

    // System.out.println("This is columpattern: " + columpattern);

    return Pattern.compile(_winningcombo).matcher(columpattern).matches();
  }

  private boolean iswonDiagonalLandR(GameButton _piece, String _winningcombo){

    String columpattern = "";

    int startX = GameBoardROW - 1;
    int startY = GameBoardCOL - 1;

    System.out.println("this is starty: " + startY);

    for (int j = startY; j > 0 ; j--) {
      if(piceseslocation[startX][j].getPieceColor() != null){
        columpattern = columpattern.concat(piceseslocation[startX--][j].getPieceColor());
      }

      // System.out.println("Start should decriment: " + startX);
      // System.out.println("j should decriment: " + j);
    }

    // System.out.println("This is columpattern: " + columpattern);

    return Pattern.compile(_winningcombo).matcher(columpattern).matches();
      
  }

  public void hasNeighborSameColor(GameButton _piece){

//     Pattern winningCombo = Pattern.compile(".s");
// 
    // boolean b1 = Pattern.compile("(.*BBBB.*)|(.*RRRR*.)").matcher("BBRBBBB").matches();
    // System.out.println(b1);

    // String winningCombo = "(BBBB)|(RRRR)";
    // String winningCombo = "(B{4,}+)||(R{4,}+)";
    final String regex = "(.*BBBB.*)|(.*RRRR*.)";
    
    if(iswonleftright(_piece, regex)){
      hasWon(_piece);
    }

    if(iswonUPDown(_piece, regex)){
      hasWon(_piece);
    }

    if(iswonDiagonalRandL(_piece, regex)){
      hasWon(_piece);
    }

    if(iswonDiagonalLandR(_piece, regex)){
      hasWon(_piece);
    }

  }

public String getPlayerWon() {
	return playerWon;
}

public void setPlayerWon(String playerWon) {
	this.playerWon = playerWon;
}

  public void hasWon(GameButton _piece){

    System.out.println("HAS WON!!!");
    gamedata.setWonGame(true);

    String colorWon = _piece.getPieceColor();
    gamedata.setPlayerWon(colorWon);

    setPlayerWon(colorWon);
    
  }

  public String whosTurn(){
    return gamedata.getWhosTurn(); 
  }

  public String nextTurn(){
    return gamedata.getNextTurn();
  }

}
