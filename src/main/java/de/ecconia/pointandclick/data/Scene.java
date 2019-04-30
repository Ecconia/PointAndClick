package de.ecconia.pointandclick.data;

import java.util.ArrayList;
import java.util.List;

public class Scene
{
	private String name;
	private ImageResource background;
	private List<Clickable> clickables = new ArrayList<>();
	
	public Scene(String name)
	{
		this.name = name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setBackground(ImageResource background)
	{
		this.background = background;
	}
	
	public ImageResource getBackground()
	{
		return background;
	}

	public void addClickable(Clickable clickable)
	{
		clickables.add(clickable);
	}

	public List<Clickable> getClickables()
	{
		return clickables;
	}
}
