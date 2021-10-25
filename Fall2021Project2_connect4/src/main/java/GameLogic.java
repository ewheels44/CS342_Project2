
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.lang.String;
import java.util.regex.Pattern;
import javafx.animation.PauseTransition;

public class GameLogic {

  // private data members of the class used for game logic
  private GameData gamedata; 
  private GameButton pieces;
  private GridPane gameboard;
  // initial game board rows and cols
  private int GameBoardROW = 6;
  private int GameBoardCOL = 7;
  // 2d array for the board
  private GameButton piceseslocation[][];
  // disabling the button after its pressed
  private EventHandler<ActionEvent> disableButton;
  // using pause when one of the players wins the game
  private PauseTransition pause = new PauseTransition(Duration.seconds(1));
  // storing the info for who won?
  private String playerWon;

  // initializing the game board
  public GridPane creategameboard(Stage _primarystage){
    gameboard = new GridPane();
    gameboard.setAlignment(Pos.CENTER);
    gameboard.setHgap(5); // setting the gaps between the gridpane horizontal
    gameboard.setVgap(5); // setting the gaps between the gridpane vertical
    gameboard.setStyle("-fx-font-size: 50;" + "-fx-boarder-size: 50;");

    // init the 2d array
    piceseslocation = new GameButton[GameBoardROW][GameBoardCOL]; 

    // filling the 2d array
    for (int i = 0; i < GameBoardROW; i++) {
      for (int j = 0; j < GameBoardCOL; j++) {

        pieces = new GameButton();
        pieces.addXandYcords(pieces, i, j);
        pieces.setOnAction(disableButton(_primarystage)); // once the button is pressed disabling it
        // adding the pieces to the board accordingly
        gameboard.add(pieces, j, i);
        // piece locations
        piceseslocation[i][j] = pieces;
      }
    }

    gamedata = new GameData();
    // returning the board
    return gameboard;
  }
  // event handler for listening the buttons in the bckgrnd until they are pressed
  public EventHandler<ActionEvent> disableButton(Stage _primarystage ){
    disableButton = new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event) {
        // TODO Auto-generated method stub
        GameButton b1 = (GameButton) event.getSource();
        b1 = DropPiece(b1, b1.getXcord(), b1.getYcord());
        b1.setDisable(true); // turn off the last button
        b1.setisValid(false);

        String turn = whosTurn();
        // P1 == B | P2 == R, just set the whole box to a color for distinguishing turns
        if (turn == "B") {
          b1.setStyle ("-fx-background-color: #0000f5;");
        }
        else {
          b1.setStyle ("-fx-background-color: #ff0000;");
        }

        b1.setPieceColor(turn);
        JavaFXTemplate.addturnDisp(turn, b1);
        placePiece(b1);
        // checking if the any user won the game
        if(gamedata.isWonGame()){
          pause.setOnFinished(e -> _primarystage.setScene(JavaFXTemplate.winnerWinnerChickenDinner()));
          pause.play();
        }
      }
    };
    // return
    return disableButton;
  }

  // return the private data member info
  public GameButton getPiece(){
    return this.pieces;
  }
  // return the piece element at x & y coord
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
    // TODO print statements for testing?
    System.out.println(_piece.getXcord());
    System.out.println(_piece.getYcord());
    
    hasNeighborSameColor(_piece);
    gamedata.incTotalpices();
    
    gamedata.addplayerMove(_piece);
  }

  // get/return the total number of elements on the board
  public int getTotalPieces(){
    return gamedata.getTotalPieces();
  }

  // reverse move: going back a move once the user hits the "reverse" button found under "Gameplay menu"
  public void reversemove(){
    // popping the very last move so that we can go back a move
    GameButton lastmove = gamedata.popPlayerMove();
    // decrementing the total number of pieces counted
    gamedata.decTotalpieces();
    // going back a move and storing a lcl variable
    int lastmoveX = lastmove.getXcord();
    int lastmoveY = lastmove.getYcord();
    // element that should be removed
    GameButton removeME = piceseslocation[lastmoveX][lastmoveY];
    gameboard.getChildren().remove(removeME);

    // going back to the old co ordinates
    GameButton newGameButton = new GameButton();
    newGameButton.addXandYcords(newGameButton, lastmoveX, lastmoveY);
    gameboard.add(newGameButton, lastmoveY, lastmoveX);

    // setting the old move to the current one
    piceseslocation[lastmoveX][lastmoveY] = newGameButton;

    JavaFXTemplate.removelast();

  }

  // drop piece logic/ gravity so that wherever the user clicks on
  // the gameboard it falls down all the way to the bottom
  public GameButton DropPiece(GameButton _piece, int X, int Y){
    // TODO print statements for testing?
    System.out.println("X cord: " + _piece.getXcord());
    System.out.println("Y cord: " + _piece.getYcord());

    if(X >= 5) return _piece;
    GameButton PieceBelow = getPieceXandY(X+1, Y);

    // boundary test and dropping the piece
    if(PieceBelow.getisValid() && PieceBelow.getXcord() != 6){
      _piece = PieceBelow;
      return DropPiece(_piece, X = X + 1, Y);
    }
    else return _piece;
  }

  // horizontal check for same 4 color pieces
  private boolean iswonleftright(GameButton _piece, String _winningcombo){
    String columpattern = "";
    // looping through the column to find the same color pieces
    for(int i = 0; i < GameBoardCOL; i++){
      if(piceseslocation[_piece.getXcord()][i].getPieceColor() != null){
        columpattern = columpattern.concat(piceseslocation[_piece.getXcord()][i].getPieceColor());
      }
    }
    // pattern found then return it
    return Pattern.compile(_winningcombo).matcher(columpattern).matches();
  }
  // vertical checks for same 4 color pieces
  private boolean iswonUPDown(GameButton _piece, String _winningcombo){
    String columpattern = "";
    // looping through the row to find the same color pieces
    for(int i = 0; i < GameBoardROW; i++){
      if(piceseslocation[i][_piece.getYcord()].getPieceColor() != null){
        columpattern = columpattern.concat(piceseslocation[i][_piece.getYcord()].getPieceColor());
      }
    }
    //pattern found then returning it
    return Pattern.compile(_winningcombo).matcher(columpattern).matches();
  }

  // checking diagonally right and left for pattern match of same 4 color pieces
  private boolean iswonDiagonalRandL(GameButton _piece, String _winningcombo){
    
    String columpattern = "";

    int startX = GameBoardROW - 1;

    int startY = 0;


    for (int j = startY; j < GameBoardCOL; j++) {
      if(piceseslocation[startX][j].getPieceColor() != null){
        columpattern = columpattern.concat(piceseslocation[startX--][j].getPieceColor());
      }

    }

    return Pattern.compile(_winningcombo).matcher(columpattern).matches();
  }

  private boolean iswonDiagonalLandR(GameButton _piece, String _winningcombo){

    String columpattern = "";

    int startX = 0;
    int startY = GameBoardCOL - 1;

    System.out.println("this is starty: " + startY);

    for (int j = startY; j > 0 ; j--) {
      if(piceseslocation[startX][j].getPieceColor() != null){
        columpattern = columpattern.concat(piceseslocation[startX--][j].getPieceColor());
      }

    }

    return Pattern.compile(_winningcombo).matcher(columpattern).matches();
      
  }

  // helper function to check if the neighboring piece has a matching color uses regex
  public void hasNeighborSameColor(GameButton _piece){
    // String winningCombo = "(BBBB)|(RRRR)";
    // String winningCombo = "(B{4,}+)||(R{4,}+)";
    final String regex = "(.*BBBB.*)|(.*RRRR*.)";

    // 4 different cases for each piece to check for matching regex
    if(iswonleftright(_piece, regex)){
      int leftright = 0;
      hasWon(_piece, leftright);
    }

    if(iswonUPDown(_piece, regex)){
      int updwn = 1;
      hasWon(_piece, updwn);
    }

    if(iswonDiagonalRandL(_piece, regex)){
      int dRandL = 2;
      hasWon(_piece, dRandL);
    }

    if(iswonDiagonalLandR(_piece, regex)){
      int dLandR = 3;
      hasWon(_piece, dLandR);
    }

  }

  // returning player name who won
  public String getPlayerWon() {
	return playerWon;
  }

  // setting the player who won the connect 4 pattern
  public void setPlayerWon(String playerWon) {
      this.playerWon = playerWon;
  }

  // whoever wins the game disabling the board and setting appropriate values according to the color
  public void hasWon(GameButton _piece, int howplayerwon){
    //TODO print statements for testing?
    System.out.println("HAS WON!!!");
    gamedata.setWonGame(true);

    // disabling the board so that the game cannot be played as a user won the game
    for (int i = 0; i < GameBoardROW; i++) {
      for (int j = 0; j < GameBoardCOL; j++) {
        piceseslocation[i][j].setDisable(true);
      }
    }

    String colorWon = _piece.getPieceColor();
    gamedata.setPlayerWon(colorWon);
    // setting the player who won to a public method for better implementation
    setPlayerWon(colorWon);

    // TODO print statements for testing?
    System.out.println("X cord has won!: " + _piece.getXcord());
    System.out.println("Y cord has won!: " + _piece.getYcord());
    GameButton winningPiece = _piece;
    int winningpieceX = winningPiece.getXcord();
    int winningpieceY = winningPiece.getYcord();

    // left Right
    if(howplayerwon == 0){
      if(piceseslocation[winningpieceX][winningpieceY - 1].getPieceColor() == _piece.getPieceColor()){
        for(int i = 0; i < 4; i++){
          piceseslocation[winningpieceX][winningpieceY - i].setStyle("-fx-background-color: yellow;"); 
        }
      }
      else{
        for(int i = 0; i < 4; i++){
          piceseslocation[winningpieceX][winningpieceY + i].setStyle("-fx-background-color: yellow;"); 
        }
      }
    }

    // Up Down
    if(howplayerwon == 1){
      if(piceseslocation[winningpieceX - 1][winningpieceY].getPieceColor() == _piece.getPieceColor()){
        for(int i = 0; i < 4; i++){
          piceseslocation[winningpieceX - i][winningpieceY].setStyle("-fx-background-color: yellow;"); 
        }
      }
      else{
        for(int i = 0; i < 4; i++){
          piceseslocation[winningpieceX + i][winningpieceY].setStyle("-fx-background-color: yellow;"); 
        }
      }

    }

    // Diagonal Right Left
    if(howplayerwon == 2){
      if(piceseslocation[winningpieceX + 1][winningpieceY - 1].getPieceColor() == _piece.getPieceColor()){
        for(int i = 0; i < 4; i++){
          piceseslocation[winningpieceX++][winningpieceY - i].setStyle("-fx-background-color: yellow;"); 
        }
      }
      else{
        for(int i = 0; i < 4; i++){
          piceseslocation[winningpieceX--][winningpieceY + i].setStyle("-fx-background-color: yellow;"); 
        }
      }

    }

    // Diagonal Left Right 
    if(howplayerwon == 3){
      if(piceseslocation[winningpieceX + 1][winningpieceY + 1].getPieceColor() == _piece.getPieceColor()){
        for(int i = 0; i < 4; i++){
          piceseslocation[winningpieceX++][winningpieceY + i].setStyle("-fx-background-color: yellow;"); 
        }
      }
      else{
        for(int i = 0; i < 4; i++){
          piceseslocation[winningpieceX--][winningpieceY - i].setStyle("-fx-background-color: yellow;"); 
        }
      }

    }


  }

  // public methods to know whose current turn it is and who next will be,
  // these methods are used for better implementation of the game
  public String whosTurn(){
    return gamedata.getWhosTurn(); 
  }

  public String nextTurn(){
    return gamedata.getNextTurn();
  }


}
