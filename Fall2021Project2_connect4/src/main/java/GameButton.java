import javafx.scene.control.Button;

public class GameButton extends Button{

  private int UpDown;
  private int diagonalRandL;
  private int leftRigth;
  private int diagonalLandR;

  private int Xcord;
  private int Ycord;

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

}
