
import java.lang.String;
// player class that holds multiple characteristics of the player
public class Player{
  // private data members of a player  like color of it, current player number (1 or 2) and if they made any move yet
  private String color;
  private int playerNumber;
  private boolean hasGone = false;

  // return the turn of the player
  public int getTurn(){
    return 0;
  }
  // setting the color of the individual player
  public void setColor(String _color){
    this.color = _color;
  }
  // return the color of the player
  public String getColor(){ return this.color;  }
  // setting the current number of the player
  public void setPlayerNuber(int _playerNumber){
    this.playerNumber = _playerNumber;
  }
  // return the current player number
  public int getPlayerNumber(){
    return this.playerNumber;
  }
  // return if the player has moved ( using a boolean )
  public boolean getHasGone(){
    return this.hasGone;
  }
  // setting the turn of the player
  public void setTurn(boolean _state){
    this.hasGone = _state;
  }

}
