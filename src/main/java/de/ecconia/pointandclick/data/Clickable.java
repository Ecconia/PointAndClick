package de.ecconia.pointandclick.data;

import java.awt.Point;

public class Clickable
{
	private Scene toScene;
	private int x, y, width, height;
	
	public Clickable(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Clickable(int x, int y, int width, int height, Scene scene)
	{
		this(x, y, width, height);
		toScene = scene;
	}

	public void setToScene(Scene scene)
	{
		this.toScene = scene;
	}
	
	public Scene getToScene()
	{
		return toScene;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public boolean contains(Point p)
	{
		return !(p.x < x || p.y < y || p.x > (x + width) || p.y > (y + height));
//		return p.x >= x && p.y >= y && p.x < (x + width) && p.y < (y + height);
	}
	
	@Override
	public String toString()
	{
		String debug = x + ", " + y + ", " + width + ", " + height;
		if(toScene != null)
		{
			debug += " -> " + toScene.getName();
		}
		return debug;
	}
}
