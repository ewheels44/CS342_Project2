// (OO)
import java.lang.String;

public class Player{

  private String color;
  private int playerNumber;
  private boolean hasGone = false;

  public int getTurn(){
    return 0;
  }

  public void setColor(String _color){
    this.color = _color;
  }

  public String getColor(){
    
    return this.color;
  }

  public void setPlayerNuber(int _playerNumber){
    this.playerNumber = _playerNumber;
  }

  public int getPlayerNumber(){
    return this.playerNumber;
  }

  public boolean getHasGone(){
    return this.hasGone;
  }

  public void setTurn(boolean _state){
    this.hasGone = _state;
  }

}
