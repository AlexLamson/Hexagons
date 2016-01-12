

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

//take a source hexagon and tile it over the screen
public class Tessellation
{
	public static ArrayList<Hexagon> hexList = new ArrayList<Hexagon>();
	public static Hexagon original;
	
	public Tessellation(Hexagon hex)
	{
		original = new Hexagon(hex);
		addHexagons(hex);
	}
	
	//return the hexagon that contains the point pt
	public static Hexagon getHex(Point pt)
	{
		for(int i = 0 ; i < hexList.size(); i++)
			if(hexList.get(i).getBounds().contains(pt))
				return hexList.get(i);
		return hexList.get(0);
	}
	
	//fill the arraylist with hexagons
	public static void addHexagons(Hexagon centeredSource)
	{
		Hexagon saveHex = new Hexagon(centeredSource);
		
//		int xSize = 1, ySize = 1;
		int xSize = (int)(Viewer.pixel.width/(saveHex.radius*2)), ySize = (int)(Viewer.pixel.height/(saveHex.getWidth()));
		
		//move the starting hexagon to the top left corner
		for(int i = 1; i <= xSize; i++)
			saveHex.move(-(int)(Math.sqrt((saveHex.radius*saveHex.radius)-(saveHex.radius/2)*(saveHex.radius/2))*2), 0);
		for(int i = 1; i <= ySize; i++)
			saveHex.move(0, -(int)saveHex.radius*2);
		
		//add the rows of hexagons
		addHex(saveHex);
		for(int y = 0; y <= ySize; y++)
		{
			for(int i = 1; i <= xSize*2; i++)
			{
				saveHex.move((int)(saveHex.getWidth()), 0);
				addHex(saveHex);
			}
			
			saveHex.move(-(int)(saveHex.getWidth()/2), (int)(saveHex.radius*1.5));
			addHex(saveHex);
			
			for(int i = 1; i <= xSize*2; i++)
			{
				saveHex.move(-(int)(saveHex.getWidth()), 0);
				addHex(saveHex);
			}
			
			if(y == ySize)
				break;
			saveHex.move((int)(saveHex.getWidth()/2), (int)(saveHex.radius*1.5));
			addHex(saveHex);
		}
	}
	
	//add a hexagon to the arraylist of hexagons
	public static void addHex(Hexagon hex)
	{
//		System.out.println(new Hexagon(hex));
		hexList.add(new Hexagon(hex));
	}
	
	//reset the colors of all the hexagons to the original
	public static void resetAll()
	{
		for(int i = 0 ; i < hexList.size(); i++)
			hexList.get(i).setColor(original.bgColor,original.edgeColor);
	}
	
	//call the tick method of all the hexagons
	public void tick()
	{
		for(int i = 0 ; i < hexList.size(); i++)
			hexList.get(i).tick();
	}
	
	//call the render method of all the hexagons
	public void render(Graphics g, Hexagon hex)
	{
		for(int i = 0 ; i < hexList.size(); i++)
			hexList.get(i).render(g);
	}
}
