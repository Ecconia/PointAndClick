package de.ecconia.pointandclick.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import de.ecconia.pointandclick.data.Clickable;
import de.ecconia.pointandclick.data.Scene;

@SuppressWarnings("serial")
public class Projector extends JPanel
{
	private Scene currentScene;
	
	public Projector(Scene scene)
	{
		applyScene(scene);
		
		addMouseMotionListener(new MouseMotionListener()
		{
			@Override
			public void mouseMoved(MouseEvent e)
			{
				mousePos(e.getPoint());
			}
			
			@Override
			public void mouseDragged(MouseEvent e)
			{
			}
		});
		
		addMouseListener(new MouseListener()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				Projector.this.mouseClicked(e.getPoint());
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
			}
		});
	}
	
	private void mousePos(Point p)
	{
	}
	
	private void mouseClicked(Point p)
	{
		if(currentScene != null)
		{
			for(Clickable c : currentScene.getClickables())
			{
				if(c.contains(p))
				{
					System.out.println("Clicked: " + c);
					
					if(c.getToScene() != null)
					{
						applyScene(c.getToScene());
					}
					
					break;
				}
			}
		}
	}
	
	private void applyScene(Scene scene)
	{
		currentScene = scene;
		repaint();
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(currentScene.getBackground().getImage(), 0, 0, null);
		
		g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
		for(Clickable clickable : currentScene.getClickables())
		{
			g.fillRect(clickable.getX(), clickable.getY(), clickable.getWidth(), clickable.getHeight());
		}
	}
}
