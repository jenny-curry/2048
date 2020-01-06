package game2048;

import java.awt.Graphics;

import javax.swing.JFrame;

public interface GameBoard {

public void combineRight();
public void combineLeft();
public void combineUp();
public void combineDown();
public boolean isGameOver();
public void moveRight();
public void moveLeft();
public void moveUp();
public void moveDown();
public void fall();
public Block2048 lookUp(int i, int j);
public Block2048 lookRight(int i, int j);
public Block2048 lookDown(int i, int j);
public Block2048 lookLeft(int i, int j);
public void populate();
public boolean isFull();
public void draw();
public boolean getNeedToPopulate();
public void setNeedToPopulate(boolean b);
public int getScore();


}
