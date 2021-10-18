
import javafx.scene.control.ListView;

public class GameData extends GameLogic{
  
  private ListView<String> whosmove;
  private int totalpices;

  private Player player1;
  private Player player2;

  GameData(){
    player1.setColor("B");
    player1.setPlayerNuber(1);

    player2.setColor("R");
    player2.setPlayerNuber(2);
  }

  public Player getPlayerInfo(int playerNumber){
    if(playerNumber == 1) return player1;
    else return player2;
  }

  public void incTotalpices(){
    this.totalpices++;
  }

}
