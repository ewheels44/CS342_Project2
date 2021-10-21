import javafx.scene.control.Button;

public class GameButton extends Button{

  private int UpDown;
  private int diagonalRandL;
  private int leftRight;
  private int diagonalLandR;

  private int Xcord = 0;
  private int Ycord = 0;

  private String pieceColor;

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

  public boolean getisValid(){
    return this.isValid;
  }

  public void setisValid(boolean _x){
    this.isValid = _x; 
  }

  public void setPieceColor(String _color){
    this.pieceColor = _color;
  }

  public String getPieceColor(){
    return this.pieceColor;
  }

public int getUpDown() {
	return UpDown;
}

public void setUpDown(int upDown) {
	UpDown = upDown;
}

public int getDiagonalRandL() {
	return diagonalRandL;
}

public void setDiagonalRandL(int diagonalRandL) {
	this.diagonalRandL = diagonalRandL;
}

public int getLeftRigth() {
	return leftRight;
}

public void setLeftRigth(int _leftRight) {
	this.leftRight = _leftRight;
}

public int getDiagonalLandR() {
	return diagonalLandR;
}

public void setDiagonalLandR(int diagonalLandR) {
	this.diagonalLandR = diagonalLandR;
}

public boolean isValid() {
	return isValid;
}

public void setValid(boolean isValid) {
	this.isValid = isValid;
}



}
