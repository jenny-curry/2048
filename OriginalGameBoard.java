package game2048;


import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;

public class OriginalGameBoard implements GameBoard 
{
	protected Block2048[][] gameArray;
	int length = 0;
	int height = 0;
	public int gameScore = 0;
	Block2048 newestBlock;

	public OriginalGameBoard(JFrame frame, int passedLength, int passedHeight)
	{
		gameArray = new Block2048[passedHeight][passedLength];
		length = passedLength;
		height = passedHeight;
		fillGameBoard(frame);
		printArray();
	}
	
	private void fillGameBoard(JFrame frame) 
	{
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < length; j++)
			{
				gameArray[i][j] = new Block2048(frame, 0,true);
				gameArray[i][j].setX(j*67);
				gameArray[i][j].setY(i*67 + Controller2048.barHeight);
			}
			
		}
		
		populate();
		populate();
		//populate();populate();populate();populate();populate();populate();populate();populate();populate();populate();
	}
	
	public void populate() 
	{
		//System.out.println("I'm populating");
		//this double will be used to determine if a 2 or a 4 is printed
		double whatNumber = Math.random();
		boolean stillPicking = true;
		Random randomInt = new Random();
		while(stillPicking) {
			int firstRandom = randomInt.nextInt(height);
			int secondRandom = randomInt.nextInt(length);
			if(gameArray[firstRandom][secondRandom].getValue() == 0) 
			{
				if(whatNumber > 0.5) 
				{
					gameArray[firstRandom][secondRandom].setBlockValue(2);
					changeNewestBlock(firstRandom,secondRandom);

				}
				else
				{
					gameArray[firstRandom][secondRandom].setBlockValue(4);
					changeNewestBlock(firstRandom,secondRandom);
				}
				stillPicking = false;
			}
		}
	}

	public boolean isFull()
	{
		boolean isArrayFull = true;
	
		for(int i = 0; i < length; i++) 
		{
			for(int j = 0; j < length; j++)
			{
				if(gameArray[i][j].getValue()== 0)
				{
					isArrayFull = false;
					break;
				}
			}
		}
		
		return isArrayFull;
	}
	
	@Override
	public boolean isGameOver() 
	{
		boolean gameOver = false;



		if(isFull())
		{
			gameOver = true;
			for(int i = 0; i < length; i++) 
			{
				for(int j = 0; j < length; j++)
				{
					if(isThereValidMove(i,j)) 
					{
						gameOver = false;
						break;
					}
				}
				if(gameOver == false)
				{
					break;
				}
			}
		}

		return gameOver;
	}

	@Override
	public void moveRight() 
	{
		length = gameArray.length;
		int height = gameArray[0].length;
		for(int i = 0; i < length; i++) 
		{
			int holder = -1;
			for(int j = length-1; j > 0; j--) 
			{
				if(gameArray[i][j].getValue() == 0) 
				{
					holder = j;
					break;
				}
			}
			if(holder !=-1){
				for(int k = holder -1; k>=0; k--) 
				{
					if(gameArray[i][k].getValue() != 0) 
					{
						gameArray[i][holder].setBlockValue(gameArray[i][k].getValue());
						gameArray[i][k].setBlockValue(0);
						holder --;
					}
				}
			}
		}
	}

	@Override
	public void moveLeft() 
	{
		System.out.println("Move Left");
		length = gameArray.length;
		int height = gameArray[0].length;
		for(int i = 0; i < length; i++) 
		{
			int holder = -1;
			for(int j = 0; j < length; j++) 
			{
				if(gameArray[i][j].getValue() == 0) 
				{
					holder = j;
					break;
				}
			}
			if(holder !=-1){
				for(int k = holder +1; k<length; k++) 
				{
					if(gameArray[i][k].getValue() != 0) 
					{
						gameArray[i][holder].setBlockValue(gameArray[i][k].getValue());
						gameArray[i][k].setBlockValue(0);
						holder ++;
					}
				}
			}
		}
	}

	@Override
	public void moveUp() 
	{
		length = gameArray.length;
		int height = gameArray[0].length;
		for(int i = 0; i < length; i++) 
		{
			int holder = -1;
			for(int j = 0; j < length-1; j++) 
			{
				if(gameArray[j][i].getValue() == 0) {
					holder = j;
					break;
				}
			}
			if(holder !=-1){
				for(int k = holder +1; k < length; k++) 
				{
					if(gameArray[k][i].getValue() != 0) 
					{
						gameArray[holder][i].setBlockValue(gameArray[k][i].getValue());
						gameArray[k][i].setBlockValue(0);
						holder ++;
					}
				}
			}
		}
			
	}

	@Override
	public void moveDown() 
	{
		length = gameArray.length;
		int height = gameArray[0].length;
		for(int i = 0; i < length; i++) 
		{
			int holder = -1;
			for(int j = length-1; j > 0; j--) 
			{
				if(gameArray[j][i].getValue() == 0) 
				{
					holder = j;
					break;
				}
			}
			if(holder != -1)
			{
				for(int k = holder - 1; k >= 0; k--) 
				{
					if(gameArray[k][i].getValue() != 0) 
					{
						gameArray[holder][i].setBlockValue(gameArray[k][i].getValue());
						gameArray[k][i].setBlockValue(0);
						holder --;
					}
				}
			}
		}
		
		
	}
	
	public boolean isThereValidMove(int i, int j)
	{
		
		
		boolean isValidMove = false;
		  if(i==0) 
			{
				//If block is in the top row, left column
				if(j==0) 
				{
					if(lookRight(i,j).getValue() == gameArray[i][j].getValue())
					{
						isValidMove = true;
					}
					if(lookDown(i,j).getValue()==gameArray[i][j].getValue())
					{
						isValidMove = true;
					}
				}
				//If block is in the top row, right column.
				else if(j== length-1)
				{
					if(lookLeft(i,j).getValue()==gameArray[i][j].getValue())
					{
						isValidMove = true;
					}
					if(lookDown(i,j).getValue()==gameArray[i][j].getValue())
					{
						isValidMove = true;
					}

				}
				//If block is in the top row but not in the left or right corner
				else
				{
					if(lookRight(i,j).getValue() == gameArray[i][j].getValue())
					{
						isValidMove = true;
					}
					if(lookLeft(i,j).getValue()==gameArray[i][j].getValue())
					{
						isValidMove = true;
					}
					
					if(lookDown(i,j).getValue()==gameArray[i][j].getValue())
					{
						isValidMove = true;
					}

				}
			}
			// If block is in the bottom row
			else if(i == height-1)
			{
				//If block is in the bottom left corner
				if(j == 0)
				{
					if(lookRight(i,j).getValue() == gameArray[i][j].getValue())
					{
						isValidMove = true;
					}

					if(lookUp(i,j).getValue()==gameArray[i][j].getValue())
					{
						isValidMove = true;
					}

				}
				//If block is in the bottom row, right column
				else if(j == length -1)
				{
					if(lookLeft(i,j).getValue()== gameArray[i][j].getValue())
					{
						isValidMove = true;
					}
					if(lookUp(i,j).getValue()==gameArray[i][j].getValue())
					{
						isValidMove = true;
					}

				}
				//If block is in the bottom row, but not in the bottom corners
				else
				{
		
					if(lookRight(i,j).getValue() == gameArray[i][j].getValue())
					{
						isValidMove = true;
					}
					if(lookLeft(i,j).getValue()==gameArray[i][j].getValue())
					{
						isValidMove = true;
					}
					if(lookUp(i,j).getValue()== gameArray[i][j].getValue())
					{
						isValidMove = true;
					}
					
				}
			}
			//If block is in the left column, but not in the top or bottom left corners
			else if(j == 0)
			{
				if(lookRight(i,j).getValue() == gameArray[i][j].getValue())
				{
					isValidMove = true;
				}
				if(lookUp(i,j).getValue()==gameArray[i][j].getValue())
				{
					isValidMove = true;
				}
				if(lookDown(i,j).getValue()==gameArray[i][j].getValue())
				{
					isValidMove = true;
				}
				
			}
			//If block is in the right column, but not in the top or bottom right corners
			else if(j== length -1)
			{
				if(lookLeft(i,j).getValue()==gameArray[i][j].getValue())
				{
					isValidMove = true;
				}
				if(lookUp(i,j).getValue()==gameArray[i][j].getValue())
				{
					isValidMove = true;
				}
				if(lookDown(i,j).getValue()==gameArray[i][j].getValue())
				{
					isValidMove = true;
				}
			}	
			else
			{
				if(lookRight(i,j).getValue() == gameArray[i][j].getValue())
				{
					isValidMove = true;
				}
				if(lookLeft(i,j).getValue()==gameArray[i][j].getValue())
				{
					isValidMove = true;
				
				if(lookDown(i,j).getValue()==gameArray[i][j].getValue())
				{
					isValidMove = true;
				}
				if(lookUp(i,j).getValue()==gameArray[i][j].getValue())
				{
					isValidMove = true;
				}
				
			}
		}
		return isValidMove;
	 }  
	

	
	public void printArray() 
	{
		
		for(int i = 0; i < length; i++)
		{
			for(int j = 0; j < length; j++)
			{
				//System.out.print(gameArray[i][j].getValue() + " ");
			}
			//System.out.println("");
		}
	}

	@Override
	public Block2048 lookUp(int i, int j) {
		
		return gameArray[i-1][j];
	}

	@Override
	public Block2048 lookRight(int i, int j) {

		return gameArray[i][j+1];
	}

	@Override
	public Block2048 lookDown(int i, int j) {
		
		return gameArray[i+1][j];
	}

	@Override
	public Block2048 lookLeft(int i, int j) {
		
		return gameArray[i][j-1];
	}

	@Override
	public void draw() 
	{
		for(int i = 0; i < length; i++)
		{
			for(int j = 0; j < length; j++)
			{
				gameArray[i][j].drawBlock();
			}
		}
	}

	@Override
	public void combineRight() {
		int length = gameArray.length;
		for(int i = 0; i < length; i++) {
			for(int j = length-1; j >0; j--) {
				if(gameArray[i][j].getValue()==gameArray[i][j-1].getValue()) {
					//sum the two block values and assign to the block on the right
					gameArray[i][j].setBlockValue(gameArray[i][j].getValue()+gameArray[i][j-1].getValue());
					addToScore(gameScore,gameArray[i][j].getValue());
					gameArray[i][j-1].setBlockValue(0);
				}
			}
		}
		moveRight();
		if(!isFull())
		{
			populate();
		}
		printArray();
		System.out.println("Score is: " + gameScore);
	}

	@Override
	public void combineLeft() {
		int length = gameArray.length;
		for(int i = 0; i < length; i++) {
			for(int j = 0; j <length-1; j++) {
				if(gameArray[i][j].getValue()==gameArray[i][j+1].getValue()) {
					//sum the two block values and assign to the block on the right
					gameArray[i][j].setBlockValue(gameArray[i][j].getValue()+gameArray[i][j+1].getValue());
					addToScore(gameScore,gameArray[i][j].getValue());
					gameArray[i][j+1].setBlockValue(0);
				}
			}
		}
		moveLeft();
		if(!isFull())
		{
			populate();
		}
		printArray();
		System.out.println("Score is: " + gameScore);
	}

	@Override
	public void combineUp() {
		int length = gameArray.length;
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < length-1; j++) {
				if(gameArray[j][i].getValue()==gameArray[j+1][i].getValue()) {
					//sum the two block values and assign to the block on the right
					gameArray[j][i].setBlockValue(gameArray[j][i].getValue()+gameArray[j+1][i].getValue());
					addToScore(gameScore,gameArray[j][i].getValue());
					gameArray[j+1][i].setBlockValue(0);
				}
			}
		}
		moveUp();
		if(!isFull())
		{
			populate();
		}
		printArray();
		System.out.println("Score is: " + gameScore);
	}

	@Override
	public void combineDown() {
		int length = gameArray.length;
		for(int i = 0; i < length; i++) {
			for(int j = length -1; j > 0; j--) {
				if(gameArray[j][i].getValue()==gameArray[j-1][i].getValue()) {
					//sum the two block values and assign to the block on the right
					gameArray[j][i].setBlockValue(gameArray[j][i].getValue()+gameArray[j-1][i].getValue());
					addToScore(gameScore,gameArray[j][i].getValue());
					gameArray[j-1][i].setBlockValue(0);
				}
			}
		}
		moveDown();
		if(!isFull())
		{
			populate();
		}
		printArray();
		System.out.println("Score is: " + gameScore);
	}
	// This function takes in the current score and the value to add to the score as integers and adds them togehter.  
	public void addToScore(int currentScore, int addToScore) 
	{
		gameScore = gameScore + addToScore; 
	}
	
	public int getScore()
	{
		return gameScore;
	}
	
	public void reduceScore(int reduceScoreBy)
	{
		gameScore = (int) (gameScore - (gameScore* 0.10));
	}


	@Override
	public void fall() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean getNeedToPopulate() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setNeedToPopulate(boolean b) {
		// TODO Auto-generated method stub
		
	}
	public void changeNewestBlock(int col, int row)
	{
		if(newestBlock != null)
		{ //take off border
			newestBlock.removeBorder();
		}
		
		//Assign next block as newest block
		newestBlock = gameArray[col][row];
		gameArray[col][row].addBorder();
	}
	
}
