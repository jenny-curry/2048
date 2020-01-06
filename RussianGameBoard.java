package game2048;

import javax.swing.JFrame;

public class RussianGameBoard implements GameBoard {

	protected Block2048[][] gameArray;
	int length = 0;
	int centerColumn = 0;
	int height = 0;
	private int capacity = 0;
	private int numberOfBlocks = 0;
	public int gameScore = 0;
	public int combineTotal = 0;
	public boolean needToPopulate = false;
	public int iPosition = 0;;
	public int jPosition = 0;
	
	public RussianGameBoard(JFrame frame,int passedLength, int passedHeight) {
		gameArray = new Block2048[passedHeight][passedLength];
		length = passedLength;
		height = passedHeight;
		centerColumn = (passedLength/2);
		fillGameBoard(frame);
	}

	//This function fills the gameboard with blocks which have a value of zero.
	private void fillGameBoard(JFrame frame)
	{
		for(int row = 0; row < height; row++)
		{
			for(int col = 0; col < length; col++)
			{
				gameArray[row][col] = new Block2048(frame, 0, true);
				gameArray[row][col].setX(col*67);
				gameArray[row][col].setY(row*67 + Controller2048.barHeight);
			}
		}
		populate();
	}
	
	@Override
	//This function moves the falling block one space to the right each time it is called
	public void moveRight() {
		for(int row = height-1; row >= 0; row--)
		{
			for(int col = length-1; col >=0; col--)
			{
				//Identifies the falling block
				if(gameArray[row][col].getLockedIn() == false)
				{
					//prevents out of bounds exception or falling block moving into already occupied space. 
					if(col != length-1 &&(gameArray[row][col+1].getValue()==0))
					{
						gameArray[row][col+1].setBlockValue(gameArray[row][col].getValue());
						gameArray[row][col+1].setLockedIn(false);
						gameArray[row][col].setBlockValue(0);
					}
				}
				
			}
		}
		
	}

	@Override
	//This moves the falling block one space to the left each time it is called
	public void moveLeft() {
		for(int row = height-1; row >= 0; row--)
		{
			for(int col = 0; col < length; col++)
			{
				//Identifies the falling block
				if(gameArray[row][col].getLockedIn() == false)
				{
					//prevents out of bounds exception or falling block moving into already occupied space. 
					if(col != 0 &&(gameArray[row][col-1].getValue()==0))
					{
						gameArray[row][col-1].setBlockValue(gameArray[row][col].getValue());
						gameArray[row][col-1].setLockedIn(false);
						gameArray[row][col].setBlockValue(0);
					}
				}
				
			}
		}
	}

	@Override
	public void moveUp() {
		//empty stub function
	}
	
	
	//This function causes the falling block to fall one space each time it is called.
	public void fall() {
		boolean wasThereACombination = false;
		boolean combo = false;
		int loopCount = 1;
		//printArray();
	 for(int row = height-1; row >= 0; row--)
		{
			for(int col = 0; col < length; col++)
			{
				//If falling block is not on the bottom row
				if(gameArray[row][col].getLockedIn() == false && row != height-1)
				{
					//if the space below the falling block is open, then block falls.
					if(lookDown(row,col).getValue()==0)
					{
						gameArray[row+1][col].setBlockValue(gameArray[row][col].getValue());
						gameArray[row+1][col].setLockedIn(false);
						gameArray[row][col].setBlockValue(0);
					}
					//if there is a block beneath falling block then lock the falling block in place and look for combinations around it. 
					else
					{
						gameArray[row][col].setLockedIn(true);
						//look for combinations around the block 
						wasThereACombination = combineAround(row,col);
						
						//while loop continues to make combinations until there are no more on the board.
						while(wasThereACombination)
						{
							wasThereACombination = false;
							loopCount ++;
							
							for(int combineRow = height-1; combineRow >= 0; combineRow--)
							{
								for(int combineCol = 0; combineCol < length; combineCol++)
								{
										wasThereACombination = combineAround(combineRow,combineCol);
								}
							}
						}
						needToPopulate = true;
					}
				
				}
				//looks for a block which was falling but is now on the bottom row. 
				if(gameArray[row][col].getLockedIn() == false && row == height-1)
				{
					//locks the block in place so it won't continue to fall
					gameArray[row][col].setLockedIn(true);
					
					//initiates the combination sequence
					wasThereACombination = combineAround(row,col);
					
					//continues to combine throughout the gameboard until there are no more combinations
					while(wasThereACombination)
					{
						wasThereACombination = false;
						
						for(int combineRow = height-1; combineRow >= 0; combineRow--)
						{
							for(int combineCol = 0; combineCol < length; combineCol++)
							{
									wasThereACombination = combineAround(combineRow,combineCol);
									//if there was a combination it sets the sentinel value to true to keep the combination loop going else sentinel value = false and exit loop
							}
						}
					}
					needToPopulate = true;
				}
				
			}
		}
	 printArray();
	}
	
	
	@Override
	//This moves all blocks which have open space beneath them down.
	public void moveDown() {
	
		for(int col = 0; col < length; col++) 
		{
			int moveTo = -1;
			for(int row = height-1; row > 0; row--) 
			{
				if(gameArray[row][col].getValue() == 0) 
				{
					moveTo = row;
					break;
				}
			}
			if(moveTo != -1)
			{
				for(int moveFrom = moveTo - 1; moveFrom >= 0; moveFrom--) 
				{
					
					if(gameArray[moveFrom][col].getValue() != 0) 
					{
						if(gameArray[moveFrom][col].isNewCombination())
						{
							gameArray[moveTo][col].setNewCombination(true);
							gameArray[moveFrom][col].setNewCombination(false);
							
						}
						gameArray[moveTo][col].setBlockValue(gameArray[moveFrom][col].getValue());
						gameArray[moveFrom][col].setBlockValue(0);
						moveTo --;
					}
				}
			}
		}
		printArray();
	}
	
	//This function takes the falling block and drops it to the bottom empty space in the column.
	public boolean dropBlock()
	{
		boolean didWeDropABlock = false;
		int column = lookForColumn();
		if(column != -1)
		{
			int bottom = lookForBottom(column);
		
	
		for(int row = 0; row < height; row++) 
		{
			for(int col = 0; col < length; col++)
			{
				if(gameArray[row][col].getLockedIn()==false)
				{
					gameArray[bottom][col].setBlockValue(gameArray[row][col].getValue());
					gameArray[bottom][col].setLockedIn(false);
					gameArray[row][col].setBlockValue(0);
					fall();
					didWeDropABlock = true;
					return didWeDropABlock;
				}
			}
		}
			needToPopulate = true;
		}
		System.out.println("Finished Drop Block");
		return didWeDropABlock;
	}
	//This function looks for the column which has the falling block in it
	public int lookForColumn()
	{
		int columnValue = -1;
		 for(int row = height-1; row >= 0; row--)
			{
				for(int col = 0; col < length; col++)
				{
					if(gameArray[row][col].getLockedIn()==false)
					{
						 columnValue =  gameArray[row][col].getArrayX();
					}
					
				}
				
			}
		 return columnValue;
	}
	
	//this function looks for the lowest open space in the column the falling block is in. This open space will be the space the falling block will fall to.  
	public int lookForBottom(int column)
	{
		int bottom = 0;
		for(int row = height-1; row > 0; row--)
		{
			if(gameArray[row][column].getValue() == 0)
			{
				bottom = row;
				break;
			}
		}
		return bottom;
	}
	
	@Override
	//determines if game is over by looking at the center column and determining if it is full.
	public boolean isGameOver() {
		boolean gameOver = false;
		if(isFull())
		{
			gameOver = true;
		}
		return gameOver;
	}

	//returns the block above the block which is looking.
public Block2048 lookUp(int row, int col) {
	
		return gameArray[row-1][col];
	}

	@Override
	//returns the block to the right the block which is looking.
	public Block2048 lookRight(int row, int col) {
		
		
		return gameArray[row][col+1];
	}

	@Override
	//returns the block beneath the block which is looking.
	public Block2048 lookDown(int row, int col) {
		
		return gameArray[row+1][col];
	}

	@Override
	//returns the block to the left the block which is looking.
	public Block2048 lookLeft(int row, int col) {
		
		return gameArray[row][col-1];
	}

	//Populate generates the block that will fall from the top center. A random number between 0 and 1 is generated and depending on that number, a block with a certain value is populated.
	public void populate() {
		
		System.out.println("populated new block");
		double whatNumber = Math.random();
		if(whatNumber < 0.167) 
		{
			gameArray[0][centerColumn].setBlockValue(2);
		}
		else if(whatNumber > 0.167 && whatNumber <0.333)
		{
			gameArray[0][centerColumn].setBlockValue(4);
		}
		else if(whatNumber >= 0.334 && whatNumber < 0.499)
		{
			gameArray[0][centerColumn].setBlockValue(8);
		}
		else if(whatNumber >= 0.499 && whatNumber < 0.664)
		{
			gameArray[0][centerColumn].setBlockValue(16);
		}
		else if(whatNumber >= 0.664 && whatNumber < 0.83)
		{
			gameArray[0][centerColumn].setBlockValue(32);
		}
		else 
		{
			gameArray[0][centerColumn].setBlockValue(64);
		}
		
		gameArray[0][centerColumn].setLockedIn(false);
	}

	@Override
	//determines if the gameArray is full
	public boolean isFull() {
		
		boolean full = false;
		if(gameArray[0][centerColumn].getValue()!=0 && gameArray[0][centerColumn].getLockedIn()==true)
		{
			full = true;
		}
		return full;
	}
	
	//returns a boolean value which indicates if a block needs to be populated. 
	public boolean getNeedToPopulate()
	{
		return needToPopulate;
	}
	
	//sets the boolean value which indicates if a block needs to be populated
	public void setNeedToPopulate(boolean setValue)
	{
		needToPopulate = setValue;
	}

	@Override
	//tells the block at row/col to draw itself.
	public void draw() 
	{
		for(int row = 0; row < height; row++)
		{
			for(int col = 0; col < length; col++)
			{
				gameArray[row][col].drawBlock();
			}
		}
	}
	
	//This function looks for combinations and combines around the block which calls the function.
	public boolean combineAround(int row, int col)
	{
		boolean didWeCombine = false;
		gameArray[row][col].setNewCombination(false);
		combineTotal= gameArray[row][col].getValue();
		Block2048 myBlock = gameArray[row][col];
		
		//If the block is in the top row
		if(row==0) 
		{
			//If block is in the top row, left column
			if(col==0) 
			{
				if(lookRight(row,col).getValue() == gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
				{
					combineTotal = combineTotal*2;
					lookRight(row,col).setBlockValue(0);
					gameArray[row][col].setNewCombination(true);
					setScore(combineTotal);
				}
				if(lookDown(row,col).getValue()==gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
				{
					combineTotal = combineTotal*2;
					lookDown(row,col).setBlockValue(0);
					gameArray[row][col].setNewCombination(true);
					setScore(combineTotal);
				}
				
				gameArray[row][col].setBlockValue(combineTotal);
				

			}
			//If block is in the top row, right column.
			else if(col== length-1)
			{
				if(lookLeft(row,col).getValue()==gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
				{
					combineTotal = combineTotal*2;
					lookLeft(row,col).setBlockValue(0);
					gameArray[row][col].setNewCombination(true);
					setScore(combineTotal);
				}
				if(lookDown(row,col).getValue()==gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
				{
					combineTotal = combineTotal*2;
					lookDown(row,col).setBlockValue(0);
					gameArray[row][col].setNewCombination(true);
					setScore(combineTotal);
				}
				gameArray[row][col].setBlockValue(combineTotal);

				
			}
			//If block is in the top row but not in the left or right corner
			else
			{
				if(lookRight(row,col).getValue() == gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
				{
					combineTotal = combineTotal*2;
					lookRight(row,col).setBlockValue(0);
					gameArray[row][col].setNewCombination(true);
					setScore(combineTotal);
				}
				if(lookLeft(row,col).getValue()==gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
				{
					combineTotal = combineTotal*2;
					lookLeft(row,col).setBlockValue(0);
					gameArray[row][col].setNewCombination(true);
					setScore(combineTotal);
				}
				
				if(lookDown(row,col).getValue()==gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
				{
					combineTotal = combineTotal*2;
					lookDown(row,col).setBlockValue(0);
					gameArray[row][col].setNewCombination(true);
					setScore(combineTotal);
				}
				
				gameArray[row][col].setBlockValue(combineTotal);
				
			}
		}
		// If block is in the bottom row
		else if(row == height-1)
		{
			//If block is in the bottom left corner
			if(col == 0)
			{
				if(lookRight(row,col).getValue() == gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
				{
					combineTotal = combineTotal*2;
					lookRight(row,col).setBlockValue(0);
					gameArray[row][col].setNewCombination(true);
					setScore(combineTotal);
				}

				if(lookUp(row,col).getValue()==gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
				{
					combineTotal = combineTotal*2;
					lookUp(row,col).setBlockValue(0);
					gameArray[row][col].setNewCombination(true);
					setScore(combineTotal);
				}
				
				gameArray[row][col].setBlockValue(combineTotal);
				
			}
			//If block is in the bottom row, right column
			else if(col == length -1)
			{
				if(lookLeft(row,col).getValue()== gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
				{
					combineTotal = combineTotal*2;
					lookLeft(row,col).setBlockValue(0);
					gameArray[row][col].setNewCombination(true);
					setScore(combineTotal);
				}
				if(lookUp(row,col).getValue()==gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
				{
					combineTotal = combineTotal*2;
					lookUp(row,col).setBlockValue(0);
					gameArray[row][col].setNewCombination(true);
					setScore(combineTotal);
				}
				
				gameArray[row][col].setBlockValue(combineTotal);
				
			}
			//If block is in the bottom row, but not in the bottom corners
			else
			{
	
				if(lookRight(row,col).getValue() == gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
				{
					combineTotal = combineTotal*2;
					lookRight(row,col).setBlockValue(0);
					gameArray[row][col].setNewCombination(true);
					setScore(combineTotal);
				}
				if(lookLeft(row,col).getValue()==gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
				{
					combineTotal = combineTotal*2;
					lookLeft(row,col).setBlockValue(0);
					gameArray[row][col].setNewCombination(true);
					setScore(combineTotal);
				}
				if(lookUp(row,col).getValue()== gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
				{
					combineTotal = combineTotal*2;
					lookUp(row,col).setBlockValue(0);
					gameArray[row][col].setNewCombination(true);
					setScore(combineTotal);
				}
				
				gameArray[row][col].setBlockValue(combineTotal);
				
			}
		}
		//If block is in the left column, but not in the top or bottom left corners
		else if(col == 0)
		{
			//System.out.println("LeftColumn combine start: " + combineTotal);
			if(lookRight(row,col).getValue() == gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
			{
				combineTotal = combineTotal*2;
				//System.out.println("Left Column combine: " +combineTotal);
				lookRight(row,col).setBlockValue(0);
				gameArray[row][col].setNewCombination(true);
				setScore(combineTotal);
			}
			if(lookUp(row,col).getValue()==gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
			{
				combineTotal = combineTotal*2;
				//System.out.println("Left Column combine: " +combineTotal);
				lookUp(row,col).setBlockValue(0);
				gameArray[row][col].setNewCombination(true);
				setScore(combineTotal);
			}
			if(lookDown(row,col).getValue()==gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
			{
				combineTotal = combineTotal*2;
				//System.out.println("Left Column combine: " +combineTotal);
				lookDown(row,col).setBlockValue(0);
				gameArray[row][col].setNewCombination(true);
				setScore(combineTotal);
			}
			
			gameArray[row][col].setBlockValue(combineTotal);
		}
		//If block is in the right column, but not in the top or bottom right corners
		else if(col== length -1)
		{
			if(lookLeft(row,col).getValue()==gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
			{
				combineTotal = combineTotal*2;
				lookLeft(row,col).setBlockValue(0);
				gameArray[row][col].setNewCombination(true);
				setScore(combineTotal);
			}
			if(lookUp(row,col).getValue()==gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
			{
				combineTotal = combineTotal*2;
				lookUp(row,col).setBlockValue(0);
				gameArray[row][col].setNewCombination(true);
				setScore(combineTotal);
			}
			if(lookDown(row,col).getValue()==gameArray[row][col].getValue() && gameArray[row][col].getValue() != 0)
			{
				combineTotal = combineTotal*2;
				lookDown(row,col).setBlockValue(0);
				gameArray[row][col].setNewCombination(true);
				setScore(combineTotal);
			}
			
			gameArray[row][col].setBlockValue(combineTotal);
			
		}
		
		//If block is away from all edges
		else
		{
			//System.out.println("Middle Array combine start: " + combineTotal);
			if(gameArray[row][col].getValue() != 0 && (lookRight(row,col).getValue() == gameArray[row][col].getValue()))
			{
				combineTotal = combineTotal*2;
				//System.out.println("Middle Array combine: " + combineTotal);
				lookRight(row,col).setBlockValue(0);
				gameArray[row][col].setNewCombination(true);
				setScore(combineTotal);
			}
			if( gameArray[row][col].getValue() != 0 && (lookLeft(row,col).getValue()==gameArray[row][col].getValue()))
			{
				combineTotal = combineTotal*2;
				//System.out.println("Middle Array combine: " + combineTotal);
				lookLeft(row,col).setBlockValue(0);
				gameArray[row][col].setNewCombination(true);
				setScore(combineTotal);
			}
			if(gameArray[row][col].getValue() != 0 && (lookUp(row,col).getValue()==gameArray[row][col].getValue()))
			{
				combineTotal = combineTotal*2;
				//System.out.println("Middle Array combine: " + combineTotal);
				lookUp(row,col).setBlockValue(0);
				gameArray[row][col].setNewCombination(true);
				setScore(combineTotal);
			}
			if(gameArray[row][col].getValue() != 0 && (lookDown(row,col).getValue()==gameArray[row][col].getValue()))
			{
				combineTotal = combineTotal*2;
				//System.out.println("Middle Array combine: " + combineTotal);
				lookDown(row,col).setBlockValue(0);
				gameArray[row][col].setNewCombination(true);
				setScore(combineTotal);
			}
			
			gameArray[row][col].setBlockValue(combineTotal);

		}
		
		didWeCombine = gameArray[row][col].isNewCombination();
		moveDown();
		combineTotal = 0;
		return didWeCombine;		
	}
	
	@Override
	public void combineRight() {
		
	}

	@Override
	public void combineLeft() {
		
	}

	@Override
	public void combineUp() {
		
	}

	@Override
	public void combineDown() {
		
		
	}
	
	public boolean combineAll() {
		boolean combine = true;
		
		for(int col = 0; col < length; col++) 
		{
			for(int row = height-1; row > 0; row--) 
			{
				combine = combineAround(row,col);
			}
		}
		
		return combine;
	}
	
	public void printArray() 
	{
		for(int row = 0; row < height; row++)
		{
			for(int col = 0; col < length; col++)
			{
				System.out.print(gameArray[row][col].getLockedIn() + " ");
			}
			System.out.println(" ");
		}
		System.out.println();
		System.out.println();
	}

    public int getScore()
    {
    	return gameScore;
    }
    
    public void setScore(int addToScore)
    {
    	gameScore += addToScore;
    }
}
