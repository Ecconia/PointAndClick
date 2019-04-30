package de.ecconia.pointandclick.editor;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

class ListLayout implements LayoutManager
{
	@Override
	public void addLayoutComponent(String name, Component comp)
	{
	}
	
	@Override
	public void removeLayoutComponent(Component comp)
	{
	}
	
	@Override
	public Dimension preferredLayoutSize(Container parent)
	{
		int y = 0;
		int x = 0;
		
		for(Component c : parent.getComponents())
		{
			Dimension dim = c.getPreferredSize();
			y += dim.height;
			if(dim.width > x)
			{
				x = dim.width;
			}
			y++;
		}
		
		return new Dimension(x, y);
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent)
	{
		int y = 0;
		int x = 0;
		
		for(Component c : parent.getComponents())
		{
			Dimension dim = c.getMaximumSize();
			y += dim.height;
			if(dim.width > x)
			{
				x = dim.width;
			}
			y++;
		}
		
		return new Dimension(x, y);
	}
	
	@Override
	public void layoutContainer(Container parent)
	{
		int y = 0;
		for(Component c : parent.getComponents())
		{
			c.setBounds(0, y, parent.getWidth(), c.getPreferredSize().height);
			y += c.getPreferredSize().height;
			y++;
		}
	}
}