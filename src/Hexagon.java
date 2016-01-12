

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class Hexagon extends Polygon
{
	private static final long serialVersionUID = -3242508602551258245L;
	public Color bgColor, edgeColor;
	public double x, y, radius, edgeWidth;
	public Polygon inner;
	
	//constructor with default colors
	public Hexagon(double x, double y, double radius, double edgeWidth)
	{
		npoints = 6;
		this.edgeWidth = edgeWidth;
		makeHex(x, y, radius);
		bgColor = new Color(10,155,255);
		edgeColor = new Color(41,66,110);
	}
	
	//constructor with chosen colors
	public Hexagon(double x, double y, double radius, double edgeWidth, Color bgColor, Color edgeColor)
	{
		npoints = 6;
		this.edgeWidth = edgeWidth; 
		makeHex(x, y, radius);
		this.bgColor = bgColor;
		this.edgeColor = edgeColor;
	}
	
	//copy constructor
	public Hexagon(Hexagon hex)
	{
		super(hex.xpoints, hex.ypoints, hex.npoints);
		this.inner = new Polygon(hex.inner.xpoints, hex.inner.ypoints, hex.inner.npoints);

		this.x = hex.x;
		this.y = hex.y;
		this.radius = hex.radius;
		this.edgeWidth = hex.edgeWidth;
		this.bgColor = hex.bgColor;
		this.edgeColor = hex.edgeColor;
	}
	
	//set the xpoints and ypoints of this and the inner Polygon
	public void makeHex(double x, double y, double radius)
	{
		edgeWidth -= 1;			//this is to cancel out the +1 below
		
		this.x = x;
		this.y = y;
		this.radius = radius;
		
		double angle = (npoints-2)*Math.PI/npoints/2;
		int[] tempXPoints1 = new int[6];
		int[] tempYPoints1 = new int[6];
		int[] tempXPoints2 = new int[6];
		int[] tempYPoints2 = new int[6];
		
		for(int i = 0; i < npoints; i++)
		{
			double[] pt1 = {x, y+radius+1};		//+1 because fillPolygon is stupid (removes the annoying white lines)
			
			AffineTransform.getRotateInstance(angle*i, x, y).transform(pt1, 0, pt1, 0, 1);
			tempXPoints1[i] = (int)pt1[0];
			tempYPoints1[i] = (int)pt1[1];
			
			double[] pt2 = {x, y-radius+edgeWidth};

			AffineTransform.getRotateInstance(angle*i, x, y).transform(pt2, 0, pt2, 0, 1);
			tempXPoints2[i] = (int)pt2[0];
			tempYPoints2[i] = (int)pt2[1];
		}
		
		xpoints = tempXPoints1;
		ypoints = tempYPoints1;
		
		inner = new Polygon(tempXPoints2, tempYPoints2, 6);
	}
	
	//translate the hexagon by deltaX, deltaY
	public void move(int deltaX, int deltaY)
	{
//		translate((int)x + deltaX, (int)y + deltaY);
//		inner.translate((int)x + deltaX, (int)y + deltaY);
		translate(deltaX, deltaY);
		inner.translate(deltaX, deltaY);
		x += deltaX;
		y += deltaY;
	}
	
	//translate the hexagon to point deltaX, deltaY
	public void moveTo(int deltaX, int deltaY)
	{
		translate(deltaX-(int)x, deltaY-(int)y);
		inner.translate(deltaX-(int)x, deltaY-(int)y);
		x = deltaX;
		y = deltaY;
	}
	
	//calculate and return the width of this Hexagon
	public double getWidth()
	{
		return Math.sqrt((radius*radius) - (radius/2)*(radius/2))*2;
	}
	
	//this method is called every couple milliseconds, right before its rendered
	public void tick()
	{
		//change the bgColor to a random color if randomColorsMode is true
		if(Viewer.randomColorsMode)
			randomColor();
	}
	
	//brighten the hexagon
	public void brighten()
	{
		setColor(bgColor.brighter(), edgeColor);
	}

	//darken the hexagon
	public void darken()
	{
		setColor(bgColor.darker(), edgeColor);
	}

	//change the bgColor to a random color
	public void randomColor()
	{
		Random r = new Random();
		Color  c = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
		setColor(c, edgeColor);
	}
	
	//set the bgColor and edgeColor as specified
	public void setColor(Color bgColor, Color edgeColor)
	{
		this.bgColor = bgColor;
		this.edgeColor = edgeColor;
	}
	
	//draw the hexagon
	public void render(Graphics g)
	{
		g.setColor(edgeColor);
		g.fillPolygon(this);
		g.setColor(bgColor);
		g.fillPolygon(inner);
		
		if(Viewer.cubeMode)
		{
			g.setColor(edgeColor);
			for(int i = 0; i < npoints/2; i++)
				g.drawLine(xpoints[i], ypoints[i], xpoints[i+npoints/2], ypoints[i+npoints/2]);
		}
	}
	
	//return a string of the hexagon's properties
	public String toString()
	{
		return "Pos: {"+(int)x+","+(int)y+"}  Radius: "+radius+"  Edge Width: "+edgeWidth+"  Fill: "+bgColor+"  Border: "+edgeColor;
	}
}
