package de.ecconia.pointandclick.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JPanel;

import de.ecconia.pointandclick.data.Clickable;
import de.ecconia.pointandclick.data.Scene;

@SuppressWarnings("serial")
public class Projector extends JPanel
{
	private final ProjectorOwner owner;
	private Scene currentScene;
	private Clickable currentClickable;
	
	private static final Color boxColor = new Color(0.0f, 0.0f, 0.2f, 0.5f);
	public static final Color boxColorHighlighted = new Color(0.0f, 0.0f, 0.8f, 0.5f);
	private static final Color boxColorNew = new Color(0.0f, 0.2f, 0.0f, 0.5f);
	private static final Color boxColorInvalid = new Color(0.2f, 0.0f, 0.0f, 0.5f);
	
	private boolean boxMode = false;
	private Point one;
	private DrawBox newBox;
	
	public Projector(Dimension size, Scene scene, ProjectorOwner owner)
	{
		this.owner = owner;
		setPreferredSize(size);
		
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
				int button = e.getButton();
				if(button != MouseEvent.BUTTON1 && button != MouseEvent.BUTTON3)
				{
					return;
				}
				
				if(boxMode)
				{
					//Only allow boxing with left-click.
					if(button == MouseEvent.BUTTON1)
					{
						if(one == null)
						{
							one = e.getPoint();
						}
						else
						{
							boxMode = false;
							one = null;
							owner.newClickable(newBox.x, newBox.y, newBox.width, newBox.height, newBox.valid);
							newBox = null;
							repaint();
						}
					}
				}
				else
				{
					Projector.this.mouseClicked(e.getPoint(), button == MouseEvent.BUTTON1);
				}
			}
		});
	}
	
	private void mousePos(Point p)
	{
		if(boxMode)
		{
			if(one != null)
			{
				newBox = new DrawBox(one, p);
				newBox.checkCollusion(currentScene.getClickables());
				repaint();
			}
		}
	}
	
	private static class DrawBox
	{
		private int x, y, x2, y2, width, height;
		private boolean valid;
		
		public DrawBox(Point a, Point b)
		{
			x = a.x;
			y = a.y;
			x2 = b.x;
			y2 = b.y;
			
			if(x2 < x)
			{
				x = x2;
				x2 = a.x;
			}
			
			if(y2 < y)
			{
				y = y2;
				y2 = a.y;
			}
			
			width = x2 - x;
			height = y2 - y;
		}
		
		public void checkCollusion(List<Clickable> clickables)
		{
			for(Clickable c : clickables)
			{
				if(!(x > (c.getX() + c.getWidth()) || x2 < c.getX() || y > (c.getY() + c.getHeight()) || y2 < c.getY()))
				{
					valid = false;
					return;
				}
			}
			
			valid = true;
		}
		
		public void draw(Graphics g)
		{
			g.setColor(valid ? boxColorNew : boxColorInvalid);
			g.fillRect(x, y, width, height);
		}
	}
	
	private void mouseClicked(Point p, boolean leftclick)
	{
		if(currentScene != null)
		{
			for(Clickable c : currentScene.getClickables())
			{
				if(c.contains(p))
				{
					if(leftclick)
					{
						if(c.getToScene() != null)
						{
							owner.switchingScene(c.getToScene());
						}
					}
					else
					{
						//Open options.
						owner.openProperties(c);
					}
					
					break;
				}
			}
		}
	}
	
	public void applyScene(Scene scene)
	{
		currentScene = scene;
		repaint();
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(currentScene.getBackground().getImage(), 0, 0, null);
		
		for(Clickable clickable : currentScene.getClickables())
		{
			g.setColor(clickable == currentClickable ? boxColorHighlighted : boxColor);
			g.fillRect(clickable.getX(), clickable.getY(), clickable.getWidth(), clickable.getHeight());
		}
		
		if(newBox != null)
		{
			newBox.draw(g);
		}
	}
	
	public interface ProjectorOwner
	{
		void switchingScene(Scene scene);
		
		void newClickable(int x, int y, int width, int height, boolean valid);
		
		void openProperties(Clickable clickable);
	}
	
	public void setCurrentClickable(Clickable clickable)
	{
		this.currentClickable = clickable;
		repaint();
	}
	
	public void newClickable()
	{
		boxMode = true;
	}
}
