package core;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Screen extends JFrame{

	private static int DISPLAY_X, DISPLAY_Y, DISPLAY_WIDTH, DISPLAY_HEIGHT;
	private BufferStrategy bf;

	public Screen(String header, int width, int height){
		super(header);
		DISPLAY_WIDTH = width;
		DISPLAY_HEIGHT = height;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);

		setVisible(true);
		this.createBufferStrategy(2);
		bf = this.getBufferStrategy();

		Insets insets = getInsets();
		DISPLAY_X = insets.left;
		DISPLAY_Y = insets.right;
		resizeToInternalSize(DISPLAY_WIDTH,DISPLAY_HEIGHT);
	}

	public Graphics2D getGraphics(){
		if(bf != null){
		return (Graphics2D)bf.getDrawGraphics();
		}
		return (Graphics2D)super.getGraphics();
	}

	public void update(){
		bf.show();
		Toolkit.getDefaultToolkit().sync();
	}

	public void resizeToInternalSize(int internalWidth, int
			internalHeight)
	{
		Insets insets = getInsets();
		final int newWidth = internalWidth + insets.left +
		insets.right;
		final int newHeight = internalHeight + insets.top +
		insets.bottom;

		Runnable resize = new Runnable()
		{
			public void run()
			{
				setSize(newWidth, newHeight);
			}
		};

		if(!SwingUtilities.isEventDispatchThread())
		{
			try
			{
				SwingUtilities.invokeAndWait(resize);
			}
			catch(Exception e) {}
		}
		else
			resize.run();

		validate();
	}
}
