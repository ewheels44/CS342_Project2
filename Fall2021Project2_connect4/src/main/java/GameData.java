import java.util.Stack;

public class GameData extends GameLogic {
  // private data members which contains info about the player characteristics
  private int totalpices = 0;
  // number of players
  private Player player1;
  private Player player2;
  // use of stack for tracking
  private Stack<GameButton> playerMoveStack;
  // who won info
  private boolean wonGame;

  // setting up the constructor with players and stack
  GameData() {
    player1 = new Player();
    player1.setColor("B");
    player1.setPlayerNuber(1);


    player2 = new Player();
    player2.setColor("R");
    player2.setPlayerNuber(2);

    playerMoveStack = new Stack<>();
  }

  // adding player to the stack (push)
  public void addplayerMove(GameButton _piece){
    playerMoveStack.push(_piece);
  }

  // popping the player from the stack LIFO used for reverse
  public GameButton popPlayerMove(){
    return playerMoveStack.pop();
  }

  // method for who won the game
  public boolean isWonGame() {
    return wonGame;
  }

  // setting won who the game to the private variable
  public void setWonGame(boolean wonGame) {
    this.wonGame = wonGame;
  }

  // returns total number of pieces on the board used for tie
  public int getTotalPieces() {
    return this.totalpices;
  }

  // Increment counter for total number of pieces on the board
  public void incTotalpices() {
    this.totalpices++;
  }

  // Decrement counter for total number of pieces on the board
  public void decTotalpieces(){
    this.totalpices--;
  }

  // get info about whose current turn is to play on the board -> used for one of the JAVAFX box
  public String getWhosTurn() {
    // if player1 has not gone
    // setting player1 to play first
    if (!player1.getHasGone()) {
      player1.setTurn(true);
      player2.setTurn(false);
      return player1.getColor();
    }
    // else second player goes
    else {
      player2.setTurn(true);
      player1.setTurn(false);
      return player2.getColor();
    }
  }

  // returns info abt next turn of the user
  public String getNextTurn() {
    // if player 1 has recently played return info accordingly
    if (!player1.getHasGone()) {
      return player1.getColor();
    } else {
      return player2.getColor();
    }
  }

}
