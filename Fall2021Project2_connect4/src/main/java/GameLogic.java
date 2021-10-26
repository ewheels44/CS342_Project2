
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

  private GameData gamedata;
  // private data members of the class used for game logic

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
  public GridPane creategameboard(Stage _primarystage) {
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
  public EventHandler<ActionEvent> disableButton(Stage _primarystage) {
    disableButton = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        // TODO Auto-generated method stub
        GameButton b1 = (GameButton) event.getSource();
        b1 = DropPiece(b1, b1.getXcord(), b1.getYcord());
        b1.setDisable(true); // turn off the last button
        b1.setisValid(false);

        String turn = whosTurn();
        // P1 == B | P2 == R, just set the whole box to a color
        if (turn == "B") {
          b1.setStyle("-fx-background-color: #0000f5;");
        } else {
          b1.setStyle("-fx-background-color: #ff0000;");
        }

        b1.setPieceColor(turn);
        JavaFXTemplate.addturnDisp(turn, b1);

        if (gamedata.getTotalPieces() + 1 == GameBoardROW * GameBoardCOL) {
          // /= "ITS A TIE";
          setPlayerWon("Its a tie, play again to settle this dispute");
          pause.setOnFinished(e -> _primarystage.setScene(JavaFXTemplate.winnerWinnerChickenDinner()));
          pause.play();
        }

        placePiece(b1);
        // checking if the any user won the game
        if (gamedata.isWonGame()) {
          pause.setOnFinished(e -> _primarystage.setScene(JavaFXTemplate.winnerWinnerChickenDinner()));
          pause.play();
        }
      }
    };

    // return
    return disableButton;
  }

  // return the private data member info
  public GameButton getPiece() {
    return this.pieces;
  }

  // return the piece element at x & y coord
  public GameButton getPieceXandY(int _X, int _Y) {
    return piceseslocation[_X][_Y];
  }

  // does all the sanity "checks"
  //
  // hasNeighbor
  // isValidMove
  // hasWon
  //
  public void placePiece(GameButton _piece) {

    hasNeighborSameColor(_piece);
    gamedata.incTotalpices();

    gamedata.addplayerMove(_piece);
  }

  // get/return the total number of elements on the board
  public int getTotalPieces() {
    return gamedata.getTotalPieces();
  }

  // reverse move: going back a move once the user hits the "reverse" button found
  // under "Gameplay menu"
  public void reversemove() {
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
    // newGameButton.setOnAction(disableButton(_primarystage));
    newGameButton.addXandYcords(newGameButton, lastmoveX, lastmoveY);
    gameboard.add(newGameButton, lastmoveY, lastmoveX);

    // setting the old move to the current one
    piceseslocation[lastmoveX][lastmoveY] = newGameButton;

    JavaFXTemplate.removelast();

  }

  // drop piece logic/ gravity so that wherever the user clicks on
  // the gameboard it falls down all the way to the bottom
  public GameButton DropPiece(GameButton _piece, int X, int Y) {

    if (X >= 5)
      return _piece;
    GameButton PieceBelow = getPieceXandY(X + 1, Y);

    // boundary test and dropping the piece
    if (PieceBelow.getisValid() && PieceBelow.getXcord() != 6) {
      _piece = PieceBelow;
      return DropPiece(_piece, X = X + 1, Y);
    } else
      return _piece;
  }

  // horizontal check for same 4 color pieces
  private boolean iswonleftright(GameButton _piece, String _winningcombo) {
    String columpattern = "";
    // ArrayList<GameButton> colorsArray = new ArrayList<>();
    GameButton highlightMe[] = new GameButton[4];

    int j = 0;
    // looping through the column to find the same color pieces
    for (int i = 0; i < GameBoardCOL; i++) {
      if (piceseslocation[_piece.getXcord()][i].getPieceColor() != null) {
        columpattern = columpattern.concat(piceseslocation[_piece.getXcord()][i].getPieceColor());
      } else
        columpattern = columpattern.concat("E");

      if (piceseslocation[_piece.getXcord()][i].getPieceColor() == _piece.getPieceColor()) {
        if (j < 4) {
          highlightMe[j] = piceseslocation[_piece.getXcord()][i];
          j++;
        }
      } else
        j = 0;
    }

    System.out.println("This is left right : " + columpattern);

    if (Pattern.compile(_winningcombo).matcher(columpattern).matches()) {
      for (int i = 0; i < 4; i++) {
        highlightMe[i].setStyle("-fx-background-color: yellow;");
      }
      return true;
    }

    return false;
    // pattern found then return it
    // return Pattern.compile(_winningcombo).matcher(columpattern).matches();
  }

  // vertical checks for same 4 color pieces
  private boolean iswonUPDown(GameButton _piece, String _winningcombo) {
    String columpattern = "";
    GameButton highlightMe[] = new GameButton[4];

    int j = 0;
    // looping through the row to find the same color pieces
    for (int i = 0; i < GameBoardROW; i++) {
      if (piceseslocation[i][_piece.getYcord()].getPieceColor() != null) {
        columpattern = columpattern.concat(piceseslocation[i][_piece.getYcord()].getPieceColor());
      } else
        columpattern = columpattern.concat("E");

      if (piceseslocation[i][_piece.getYcord()].getPieceColor() == _piece.getPieceColor()) {
        if (j < 4) {
          highlightMe[j] = piceseslocation[i][_piece.getYcord()];
          j++;
        }
      } else
        j = 0;
    }

    System.out.println("This is left right : " + columpattern);

    if (Pattern.compile(_winningcombo).matcher(columpattern).matches()) {
      for (int i = 0; i < 4; i++) {
        highlightMe[i].setStyle("-fx-background-color: yellow;");
      }
      return true;
    }

    return false;
  }

  // checking diagonally right and left for pattern match of same 4 color pieces
  private boolean iswonDiagonalRandL(GameButton _piece, String _winningcombo) {

    String diagonalPattern = "";
    //
    // System.out.println("X cord: " + _piece.getXcord());
    // System.out.println("Y cord: " + _piece.getYcord());
    //
    //
    GameButton highlightMe[] = new GameButton[4];

    int i = 0;
    for (int j = 0; j < GameBoardROW; j++) {
      int Ycord = _piece.getXcord() + _piece.getYcord() - j;

      if (Ycord < GameBoardCOL && Ycord >= 0) {
        // System.out.println("this is Xcord(iterator): " + j);
        // System.out.println("This is Ycord(): " + Ycord);

        if (_piece.getXcord() < 6) {
          if (piceseslocation[j][Ycord].getPieceColor() != null) {
            diagonalPattern = diagonalPattern.concat(piceseslocation[j][Ycord].getPieceColor());
          }
          else diagonalPattern = diagonalPattern.concat("E");

          if (piceseslocation[j][Ycord].getPieceColor() == _piece.getPieceColor()) {
            highlightMe[i] = piceseslocation[j][Ycord];
            i++;
          } else
            i = 0;
        }
      }
    }

    // System.out.println("This is diagonalPattern: " + diagonalPattern);

    if (Pattern.compile(_winningcombo).matcher(diagonalPattern).matches()) {
      for (int j = 0; j < 4; j++) {
        highlightMe[j].setStyle("-fx-background-color: yellow;");
      }
      return true;
    }

    return false;

  }

  private boolean iswonDiagonalLandR(GameButton _piece, String _winningcombo) {

    String diagonalPattern = "";
    GameButton highlightMe[] = new GameButton[4];

    // System.out.println("X cord: " + _piece.getXcord());
    // System.out.println("Y cord: " + _piece.getYcord());

    int i = 0;
    for (int j = 0; j < GameBoardROW; j++) {
      int Ycord = _piece.getXcord() - _piece.getYcord() + j;

      if (Ycord < GameBoardCOL && Ycord >= 0) {
        // System.out.println("This is Xcord(): " + Ycord);
        // System.out.println("this is Ycord(iterator): " + j);

        if (Ycord < 6) {
          if (piceseslocation[Ycord][j].getPieceColor() != null) {
            diagonalPattern = diagonalPattern.concat(piceseslocation[Ycord][j].getPieceColor());
          }
          else diagonalPattern = diagonalPattern.concat("E");

          if (piceseslocation[Ycord][j].getPieceColor() == _piece.getPieceColor()) {
            highlightMe[i] = piceseslocation[Ycord][j];
            i++;
          } else
            i = 0;
        }
      }
    }

    // System.out.println("This is diagonalPattern: " + diagonalPattern);

    if (Pattern.compile(_winningcombo).matcher(diagonalPattern).matches()) {
      for (int j = 0; j < 4; j++) {
        highlightMe[j].setStyle("-fx-background-color: yellow;");
      }
      return true;
    }

    return false;

  }

  // helper function to check if the neighboring piece has a matching color uses
  // regex
  public void hasNeighborSameColor(GameButton _piece) {
    // String winningCombo = "(BBBB)|(RRRR)";
    // String winningCombo = "(B{4,}+)||(R{4,}+)";
    final String regex = "(.*BBBB.*)|(.*RRRR.*)";

    // 4 different cases for each piece to check for matching regex
    if (iswonleftright(_piece, regex)) {
      // int leftright = 0;
      // hasWon(_piece, leftright);
      hasWon(_piece);
    }

    if (iswonUPDown(_piece, regex)) {
      // int updwn = 1;
      hasWon(_piece);
    }

    if (iswonDiagonalRandL(_piece, regex)) {
      hasWon(_piece);
    }

    if (iswonDiagonalLandR(_piece, regex)) {
      hasWon(_piece);
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

  // whoever wins the game disabling the board and setting appropriate values
  // according to the color
  public void hasWon(GameButton _piece) {

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

  }

  // public methods to know whose current turn it is and who next will be,
  // these methods are used for better implementation of the game
  public String whosTurn() {
    return gamedata.getWhosTurn();
  }

  public String nextTurn() {
    return gamedata.getNextTurn();
  }

}
