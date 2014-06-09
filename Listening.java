

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Listening implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{

	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		switch(key)
		{
		case KeyEvent.VK_SPACE:				//toggle random colors mode when space is pressed
			if(Viewer.randomColorsMode)
			{
				Tessellation.resetAll();
				Viewer.randomColorsMode = false;
			}
			else
				Viewer.randomColorsMode = true;
			break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		switch(key)
		{
		}
	}
	
	public void keyTyped(KeyEvent e)
	{

	}
	
	public void mouseClicked(MouseEvent e)
	{
		Viewer.mse.setLocation(e.getX()/Viewer.pixelSize, e.getY()/Viewer.pixelSize);
		Hexagon save = Tessellation.getHex(Viewer.mse);			//get the hexagon at the current cursor location
//		System.out.println(save);
		mouseToggle(e, true);
		click(save);
		mouseToggle(e, false);
	}
	
	public void mouseDragged(MouseEvent e)
	{
		Viewer.mse.setLocation(e.getX()/Viewer.pixelSize, e.getY()/Viewer.pixelSize);
		Hexagon save = Tessellation.getHex(Viewer.mse);			//get the hexagon at the current cursor location
//		System.out.println(save);
		click(save);
	}
	
	public static void click(Hexagon save)
	{
		if(Viewer.isMouseLeft)			//left click
			save.brighten();
		else if(Viewer.isMouseMiddle)	//middle click
			save.randomColor();
		else if(Viewer.isMouseRight)	//right click
			save.darken();
	}
	
	public void mousePressed(MouseEvent e)
	{
		mouseToggle(e, true);
	}

	public void mouseReleased(MouseEvent e)
	{
		mouseToggle(e, false);
	}
	
	public static void mouseToggle(MouseEvent e, boolean toggle)
	{
		if(e.getButton() == MouseEvent.BUTTON1)			//left click
			Viewer.isMouseLeft = toggle;
		else if(e.getButton() == MouseEvent.BUTTON2)	//middle click
			Viewer.isMouseMiddle = toggle;
		else if(e.getButton() == MouseEvent.BUTTON3)	//right click
			Viewer.isMouseRight = toggle;
	}
	
	public void mouseMoved(MouseEvent e)
	{
		Viewer.mse.setLocation(e.getX(), e.getY());
	}

	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if(e.getWheelRotation() < 0)			//scrolled up
		{
			Hexagon save = Tessellation.getHex(Viewer.mse);
			save.brighten();			//brighten the hexagon at the cursor location
		}
		else if(e.getWheelRotation() > 0)		//scrolled down
		{
			Hexagon save = Tessellation.getHex(Viewer.mse);
			save.darken();				//darken the hexagon at the cursor location
		}
	}
	
	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}
}
