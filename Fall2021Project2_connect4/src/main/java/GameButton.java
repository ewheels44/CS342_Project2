import javafx.scene.control.Button;

public class GameButton extends Button{

  private int UpDown;
  private int diagonalRandL;
  private int leftRigth;
  private int diagonalLandR;

  private int Xcord = 0;
  private int Ycord = 0;

  private boolean isValid = true;

  public void addXandYcords(GameButton _piece, int _X, int _Y){
    _piece.setXcord(_X);
    _piece.setYcord(_Y);
  }

  public void setXcord(int _Xcrd){
    this.Xcord = _Xcrd;
  }

  public int getXcord(){
    return this.Xcord;
  }

  public void setYcord(int _Ycord){
    this.Ycord = _Ycord;
  }

  public int getYcord(){
    return this.Ycord;
  }

  public boolean getisValid(GameButton _piece){
    return _piece.isValid;
  }

  public void setisValid(GameButton _piece, boolean _x){
    _piece.isValid = _x; 
  }

}
