import java.util.Stack;

public class GameData extends GameLogic {

  private int totalpices = 0;

  private Player player1;
  private Player player2;

  private Stack<GameButton> playerMoveStack;

  private boolean wonGame;


  GameData() {
    player1 = new Player();
    player1.setColor("B");
    player1.setPlayerNuber(1);


    player2 = new Player();
    player2.setColor("R");
    player2.setPlayerNuber(2);

    playerMoveStack = new Stack<>();
  }


  public void addplayerMove(GameButton _piece){
    playerMoveStack.push(_piece);
  }

  public GameButton popPlayerMove(){
    return playerMoveStack.pop();
  }

  public void TESTprintplayermovestack(){
    // playerMoveStack.forEach(e -> System.out.println("this is the stack " + e.getPieceColor()));
  }


public boolean isWonGame() {
    return wonGame;
  }

  public void setWonGame(boolean wonGame) {
    this.wonGame = wonGame;
  }

  public int getTotalPieces() {
    return this.totalpices;
  }

  public void incTotalpices() {
    this.totalpices++;
  }

  public void decTotalpieces(){
    this.totalpices--;
  }

  public String getWhosTurn() {

    // if player1 has not gone
    // it is there turn
    //
    if (!player1.getHasGone()) {
      player1.setTurn(true);
      player2.setTurn(false);
      return player1.getColor();
    } else {
      player2.setTurn(true);
      player1.setTurn(false);
      return player2.getColor();
    }
  }

  public String getNextTurn() {

    if (!player1.getHasGone()) {
      return player1.getColor();
    } else {
      return player2.getColor();
    }
  }

}
