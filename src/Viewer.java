

/*
 * Alex's awesome hexagon program!
 * 
 * Controls:
 * left click	- brighter
 * middle click	- random color
 * right click	- darker
 * space		- toggle random colors mode
 */

import java.applet.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.*;

public class Viewer extends Applet implements Runnable
{
	private static final long serialVersionUID = 8864158495101925325L;				//because stupid warnings
	
	public static int radius = 30, edgeWidth = 4;									//radius is from center to any vertex, edgeWidth is the length of each side
	public static Color bgColor = new Color(0,50,140), edgeColor = Color.black;
	public static boolean randomColorsMode = false, cubeMode = false;
	public static int pixelSize = 1;												//change the scale the pixels are multiplied by when drawn to
	
	public static int computerSpeed = 10;		//higher number for slower computers
	public static int tickTime = 5;
	public static boolean isRunning = false;
	
	public static String windowName = "Hexagons are awesome";

	public static boolean debugMode = false;
	
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int screenWidth = (int)screenSize.getWidth();
	public static int screenHeight = (int)screenSize.getHeight();
	public static Dimension realSize;															//size of whole window
	public static Dimension size = new Dimension(screenWidth*2/3,screenHeight*2/3);				//drawable area
	public static Dimension pixel = new Dimension(size.width/pixelSize, size.height/pixelSize);	//"pixels" in drawable area

	public static Point mse = new Point(0, 0);

	public static boolean isMouseLeft = false;
	public static boolean isMouseMiddle = false;
	public static boolean isMouseRight = false;

	private Image screen;
	public static JFrame frame;
	public static Tessellation tess;
	public Hexagon testHex;

	public Viewer()
	{
		setPreferredSize(size);
		requestFocus();
	}

	public static void restart()
	{
		Viewer viewer = new Viewer();
		viewer.start();
	}

	public void start()
	{
		//defining objects
		double centerX = (Viewer.pixel.width/2);
		double centerY = (Viewer.pixel.height/2);
		testHex = new Hexagon(centerX, centerY, radius, edgeWidth, bgColor, edgeColor);
		tess = new Tessellation(testHex);
		
		addKeyListener(new Listening());
		addMouseListener(new Listening());
		addMouseMotionListener(new Listening());
		addMouseWheelListener(new Listening());

		//start the main loop
		isRunning = true;
		new Thread(this).start();
	}

	public void stop()
	{
		isRunning = false;
	}

	public void tick()
	{
//		if(frame.getWidth() != realSize.width || frame.getHeight() != realSize.height)
//			frame.pack();
		
//		testHex.tick();
		tess.tick();
		
	}

	public void render()
	{
		Graphics g = screen.getGraphics();

//		g.setColor(new Color(0, 150, 255));
//		g.fillRect(0, 0, pixel.width, pixel.height);
		
		tess.render(g, testHex);			//render the tesselation
//		testHex.render(g);
		
//		g.setColor(Color.red);
//		g.drawLine(0, 0, pixel.width, pixel.height);
//		g.setColor(Color.green);
//		g.drawLine(0, pixel.height, pixel.width, 0);
		
		g = getGraphics();

		g.drawImage(screen, 0, 0, size.width, size.height, 0, 0, pixel.width, pixel.height, null);
		g.dispose();		//throw it away to avoid lag from too many graphics objects
	}

	public void run()
	{
		screen = createVolatileImage(pixel.width, pixel.height);	//actually use the graphics card (less lag)
		
		render();
		if(!debugMode)
			JOptionPane.showMessageDialog(null, "Hexagon thing\n\nControls:\nleft click - brighter\nmiddle click - random color\nright click - darker\nspace - toggle random colors mode (seizure warning!)");
		
		while(isRunning)
		{
			tick();			//do math and any calculations
			render();		//draw the objects (in this case, hexagons)

			try
			{
				Thread.sleep(tickTime*(int)computerSpeed);
			}catch(Exception e){ }
		}
	}

	public static void main(String[] args) {
		Viewer viewer = new Viewer();

		frame = new JFrame();
		frame.add(viewer);
		frame.pack();

		realSize = new Dimension(frame.getWidth(), frame.getHeight());

		frame.setTitle(windowName);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);		//null makes it go to the center
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		viewer.start();
	}
}
