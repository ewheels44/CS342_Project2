import javafx.scene.control.Button;
// game button class used for holding additional info abt the button and its characteristics
public class GameButton extends Button{
  // private data members: x & y coordinates, current piececolor
  private int Xcord = 0;
  private int Ycord = 0;

  private String pieceColor;

  private boolean isValid = true;

  // adding x and x & y coordinates
  public void addXandYcords(GameButton _piece, int _X, int _Y){
    _piece.setXcord(_X);
    _piece.setYcord(_Y);
  }

  // setting the x coordinate to the private member
  public void setXcord(int _Xcrd){
    this.Xcord = _Xcrd;
  }
  // returns the x coordinate
  public int getXcord(){
    return this.Xcord;
  }
  // setting the y coordinate to the private member
  public void setYcord(int _Ycord){
    this.Ycord = _Ycord;
  }
  // returns the y coordinate
  public int getYcord(){
    return this.Ycord;
  }
  // return the bool if the spot is valid or not
  public boolean getisValid(){
    return this.isValid;
  }
  // used for checking if the current spot is valid or not
  public void setisValid(boolean _x){
    this.isValid = _x; 
  }
  // setting the color of the piece
  public void setPieceColor(String _color){
    this.pieceColor = _color;
  }
  // return the color of the piece
  public String getPieceColor(){ return this.pieceColor;  }

}
