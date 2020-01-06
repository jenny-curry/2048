package game2048;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Block2048 
{
	int blockValue;
	int xCoord;
	int yCoord;
	BufferedImage img;
	BufferedImage num;
	boolean lockedIn;
	boolean newCombination = false;
	int blockWidth;
	int blockHeight;
	int spaceBetween;
    int edgeSpace;
    ImageIcon pic = new ImageIcon();
    JLabel blockJLabel;
    JFrame blockJFrame;
    int borderWidth;
    
    

	public Block2048(JFrame frame, int passedValue, boolean locked)
	{
		this.blockValue = passedValue;
		lockedIn = locked;
		//Setting an image to the block depending on its value
		
		blockJFrame = frame;
        blockJLabel = new JLabel();
        blockJLabel.setBounds (10, 10, 10, 10); // arbitrary, will change later
        blockJFrame.getContentPane().add(blockJLabel);
        blockJLabel.setVisible(false);
        blockJLabel.setVisible(true);
        newCombination = false;
        borderWidth = 5;

		try 
		{
		    img = ImageIO.read(new File("BlockImage.png"));
		    blockWidth = 66;
		    blockHeight = 66;
		    spaceBetween = 9;
		    edgeSpace = 5;
		    
		    blockJLabel = new JLabel();
	        blockJLabel.setBounds (10, 10, blockWidth, blockHeight); // arbitrary, will change later
	        
	        Controller2048.gameFrame2048.getContentPane().add(blockJLabel);
	        blockJLabel.setVisible(false);
	        blockJLabel.setVisible(true);
		    
		    switch(blockValue)
		    {
		    case 0:
		    	num = null;
		    	break;
		    case 2:
		    	num = img.getSubimage(edgeSpace + 3*spaceBetween + 3*blockHeight, edgeSpace + 3*spaceBetween + 3*blockWidth, blockWidth, blockHeight);
		    	break;
		    case 4:
		    	num = img.getSubimage(edgeSpace + 2*spaceBetween + 2*blockWidth, edgeSpace + 2*spaceBetween + 2*blockWidth, blockWidth, blockHeight);
		    	break;
		    case 8:
		    	num = img.getSubimage(edgeSpace + spaceBetween + blockWidth, edgeSpace + 3*spaceBetween + 3*blockHeight, blockWidth, blockHeight);
		    	break;
		    case 16:
		    	num = img.getSubimage(edgeSpace, edgeSpace + 3*spaceBetween + 3*blockHeight, blockWidth, blockHeight);
		    	break;
		    case 32:
		    	num = img.getSubimage(edgeSpace , edgeSpace + 2*spaceBetween + 2*blockHeight, blockWidth, blockHeight);
		    	break;
		    case 64:
		    	num = img.getSubimage(edgeSpace + spaceBetween + blockWidth, edgeSpace + 2*spaceBetween + 2*blockHeight, blockWidth, blockHeight);
		    	break;
		    case 128:
		    	num = img.getSubimage(edgeSpace + 2*spaceBetween + 2*blockWidth, edgeSpace + 2*spaceBetween + 2*blockHeight, blockWidth, blockHeight);
		    	break;
		    case 256:
		    	num = img.getSubimage(edgeSpace + 3*spaceBetween + 3*blockWidth, edgeSpace + 2*spaceBetween + 2*blockHeight, blockWidth, blockHeight);
		    	break;
		    case 512:
			    num = img.getSubimage(edgeSpace + 3*spaceBetween + 3*blockWidth, edgeSpace + spaceBetween + blockHeight, blockWidth, blockHeight);
		    	break;
		    case 1024:
		    	num = img.getSubimage(edgeSpace + 2*spaceBetween + 2*blockWidth, edgeSpace + spaceBetween + blockHeight, blockWidth, blockHeight);
		    	break;
		    case 2048:
		    	num = img.getSubimage(edgeSpace + spaceBetween + blockWidth, edgeSpace + spaceBetween + blockHeight, blockWidth, blockHeight);
		    	break;
		    case 4096:
		    	num = img.getSubimage(edgeSpace, edgeSpace + spaceBetween + blockHeight, blockWidth, blockHeight);
		    	break;
		    case 8192:
		    	num = img.getSubimage(edgeSpace, edgeSpace, blockWidth, blockHeight);
		    	break;
		    
		    }
		    if(num != null)
		    {
		    	pic = new ImageIcon (num);
		    }
		  
		    
		} catch (IOException e) 
		{
			System.out.println("Image reading error!");
			e.printStackTrace();
		}
	}
	
	public int getValue() 
	{
		return blockValue;
	}
	
	public void setBlockValue(int passedValue) 
	{
		this.blockValue = passedValue;
		switch(blockValue)
	    {
	    case 0:
	    	num = null;
	    	//if a block is 0 it will not fall.
	    	setLockedIn(true);
	    	break;
	    case 2:
	    	num = img.getSubimage(edgeSpace + 3*spaceBetween + 3*blockHeight-1, edgeSpace + 3*spaceBetween + 3*blockWidth, blockWidth, blockHeight);
	    	break;
	    case 4:
	    	num = img.getSubimage(edgeSpace + 2*spaceBetween + 2*blockWidth, edgeSpace + 3*spaceBetween + 3*blockWidth, blockWidth, blockHeight);
	    	break;
	    case 8:
	    	num = img.getSubimage(edgeSpace + spaceBetween + blockWidth, edgeSpace + 3*spaceBetween + 3*blockHeight, blockWidth, blockHeight);
	    	break;
	    case 16:
	    	num = img.getSubimage(edgeSpace, edgeSpace + 3*spaceBetween + 3*blockHeight, blockWidth, blockHeight);
	    	break;
	    case 32:
	    	num = img.getSubimage(edgeSpace , edgeSpace + 2*spaceBetween + 2*blockHeight, blockWidth, blockHeight);
	    	break;
	    case 64:
	    	num = img.getSubimage(edgeSpace + spaceBetween + blockWidth, edgeSpace + 2*spaceBetween + 2*blockHeight, blockWidth, blockHeight);
	    	break;
	    case 128:
	    	num = img.getSubimage(edgeSpace + 2*spaceBetween + 2*blockWidth, edgeSpace + 2*spaceBetween + 2*blockHeight, blockWidth, blockHeight);
	    	break;
	    case 256:
	    	num = img.getSubimage(edgeSpace + 3*spaceBetween + 3*blockWidth, edgeSpace + 2*spaceBetween + 2*blockHeight, blockWidth, blockHeight);
	    	break;
	    case 512:
		    num = img.getSubimage(edgeSpace + 3*spaceBetween + 3*blockWidth, edgeSpace + spaceBetween + blockHeight, blockWidth, blockHeight);
	    	break;
	    case 1024:
	    	num = img.getSubimage(edgeSpace + 2*spaceBetween + 2*blockWidth, edgeSpace + spaceBetween + blockHeight, blockWidth, blockHeight);
	    	break;
	    case 2048:
	    	num = img.getSubimage(edgeSpace + spaceBetween + blockWidth, edgeSpace + spaceBetween + blockHeight, blockWidth, blockHeight);
	    	break;
	    case 4096:
	    	num = img.getSubimage(edgeSpace, edgeSpace + spaceBetween + blockHeight, blockWidth, blockHeight);
	    	break;
	    case 8192:
	    	num = img.getSubimage(edgeSpace, edgeSpace, blockWidth, blockHeight);
	    	break;
	    
	    }
		if(num != null)
	    {
	    	pic = new ImageIcon (num);
	    }
		
	}
	
	public int getX()
	{
		return xCoord;
	}
	public int getY()
	{
		return yCoord;
	}
	
	public void setX(int x)
	{
		xCoord = x;
	}
	public void setY(int y)
	{
		yCoord = y;
	}
	
	public int getArrayX()
	{
		return xCoord/67;
	}
	
	public int getArrayY()
	{
		return yCoord/67;
	}
	
	public boolean getLockedIn()
	{
		return lockedIn;
	}
	
	public void setLockedIn(boolean isLocked)
	{
		lockedIn = isLocked;
	}
	
	public boolean isNewCombination(){
		return newCombination;
	}
	
	public void setNewCombination(boolean passedNewCombination) {
		//System.out.println("SET BLOCK " + this.yCoord/67 +", "+ this.xCoord/67 + " NEW COMBO TO: " + passedNewCombination);
		newCombination = passedNewCombination;
	}
	
	public void drawBlock() 
	{
		
		if(blockValue != 0)
		{
			blockJLabel.setIcon(pic);
	        blockJLabel.setBounds(xCoord,yCoord,pic.getIconWidth(),pic.getIconHeight());  
	        blockJLabel.setVisible(true);
		}
		else
		{
			blockJLabel.setVisible(false);
		}
		 
	}
	
	public void addBorder()
	{
		javax.swing.border.Border border = BorderFactory.createLineBorder(Color.YELLOW, borderWidth);
		blockJLabel.setBorder(border);
		if(blockValue == 2)
		{
			num = img.getSubimage(edgeSpace + 3*spaceBetween + 3*blockHeight  + 4, edgeSpace + 3*spaceBetween + 3*blockWidth , blockWidth , blockHeight );
	    }
		else if(blockValue == 4)
		{
			num = img.getSubimage(edgeSpace + 2*spaceBetween + 2*blockWidth  + 4, edgeSpace + 3*spaceBetween + 3*blockWidth , blockWidth , blockHeight );
		}
		pic = new ImageIcon (num);
		
	}
	
	public void removeBorder()
	{
		blockJLabel.setBorder(null);
		setBlockValue(blockValue);
		drawBlock();
	}
	
}
