package game2048;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class MainMenu 
{
	JLabel welcomeLabel;
    JFrame menuJFrame;
    Dimension screenSize;
	public MainMenu()
	{
		menuJFrame = new JFrame("Menu");
		menuJFrame.setSize(795, 820);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		menuJFrame.setLocation(screenSize.width/2-795/2, screenSize.height/2-820/2);
		menuJFrame.getContentPane().setLayout(null);
		menuJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			menuJFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("Background.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
        welcomeLabel = new JLabel();
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(64.0f));
        welcomeLabel.setText(" Welcome to 2048! ");
        welcomeLabel.setSize(615,100);
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(Color.white);
        javax.swing.border.Border border = BorderFactory.createLineBorder(Color.BLUE, 5);
        welcomeLabel.setBorder(border);
        
        
        
        welcomeLabel.setLocation(91, 175); // arbitrary, will change later
        menuJFrame.getContentPane().add(welcomeLabel);
        welcomeLabel.setVisible(false);
        welcomeLabel.setVisible(true);
		welcomeLabel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				System.out.println("hello");
			}
		});
		JLabel Original = new JLabel();
		Original.setFont(welcomeLabel.getFont().deriveFont(26.0f));
		border = BorderFactory.createLineBorder(Color.black, 5);
		
		Original.setBorder(border);
		Original.setText(" Play Original Style ->");
		Original.setOpaque(false);
		Original.setForeground(Color.blue);
		
		Original.setSize(300,55);
		Original.setBackground(Color.black);
		Original.setLocation(50,350);
		menuJFrame.getContentPane().add(Original);
		
		JLabel Russian = new JLabel();
		Russian.setFont(welcomeLabel.getFont().deriveFont(26.0f));
		Russian.setBorder(border);
		Russian.setText(" Play Russian Style ->");
		Russian.setOpaque(false);
		Russian.setForeground(Color.blue);
		
		Russian.setSize(300,55);
		Russian.setBackground(Color.black);
		Russian.setLocation(50,440);
		menuJFrame.getContentPane().add(Russian);
		
		JButton OrigOpt1 = new JButton();
		JButton OrigOpt2 = new JButton();
		JButton RussOpt1 = new JButton();
		JButton RussOpt2 = new JButton();
		
		
		border = BorderFactory.createLineBorder(Color.black, 2);
		OrigOpt1.setBorder(border);
		OrigOpt1.setText("4 X 4");
		OrigOpt1.setOpaque(true);
		OrigOpt1.setForeground(Color.white);
		OrigOpt1.setFont(welcomeLabel.getFont().deriveFont(36.0f));
		OrigOpt1.setSize(140,60);
		OrigOpt1.setBackground(Color.black);
		OrigOpt1.setLocation(400,345);
		menuJFrame.getContentPane().add(OrigOpt1);
		OrigOpt1.addActionListener(new ActionListener()
		{
			@Override
			  public void actionPerformed(ActionEvent e)
			  {
			    //call the Controller function start game
				Controller2048.gameType = 0;
				Controller2048.ARRAY_WIDTH = 4;
				Controller2048.ARRAY_HEIGHT = 4;
			    Controller2048.newGame();
			  }

			
			});
		
		OrigOpt2.setBorder(border);
		OrigOpt2.setText("8 X 8");
		OrigOpt2.setOpaque(true);
		OrigOpt2.setForeground(Color.white);
		OrigOpt2.setFont(welcomeLabel.getFont().deriveFont(36.0f));
		OrigOpt2.setSize(140,60);
		OrigOpt2.setBackground(Color.black);
		OrigOpt2.setLocation(570,345);
		menuJFrame.getContentPane().add(OrigOpt2);
		OrigOpt2.addActionListener(new ActionListener()
		{
			@Override
			  public void actionPerformed(ActionEvent e)
			  {
			    //call the Controller function start game
				Controller2048.gameType = 0;
				Controller2048.ARRAY_WIDTH = 8;
				Controller2048.ARRAY_HEIGHT = 8;
			    Controller2048.newGame();
			  }

			
			});
		
		RussOpt1.setBorder(border);
		RussOpt1.setText("5 X 7");
		RussOpt1.setOpaque(true);
		RussOpt1.setForeground(Color.white);
		RussOpt1.setFont(welcomeLabel.getFont().deriveFont(36.0f));
		RussOpt1.setSize(140,60);
		RussOpt1.setBackground(Color.black);
		RussOpt1.setLocation(400,435);
		menuJFrame.getContentPane().add(RussOpt1);
		RussOpt1.addActionListener(new ActionListener()
		{
			@Override
			  public void actionPerformed(ActionEvent e)
			  {
			    //call the Controller function start game
				Controller2048.gameType = 1;
				Controller2048.ARRAY_WIDTH = 5;
				Controller2048.ARRAY_HEIGHT = 7;
			    Controller2048.newGame();
			  }

			
			});
		
		RussOpt2.setBorder(border);
		RussOpt2.setText("7 X 9");
		RussOpt2.setOpaque(true);
		RussOpt2.setForeground(Color.white);
		RussOpt2.setFont(welcomeLabel.getFont().deriveFont(36.0f));
		RussOpt2.setSize(140,60);
		RussOpt2.setBackground(Color.black);
		RussOpt2.setLocation(570,435);
		menuJFrame.getContentPane().add(RussOpt2);
		RussOpt2.addActionListener(new ActionListener()
		{
			@Override
			  public void actionPerformed(ActionEvent e)
			  {
			    //call the Controller function start game
				Controller2048.gameType = 1;
				Controller2048.ARRAY_WIDTH = 7;
				Controller2048.ARRAY_HEIGHT = 9;
			    Controller2048.newGame();
			  }

			
			});
        
		
	}
	
	public JFrame getFrame()
	{
		return menuJFrame;
	}
	
	
}
	