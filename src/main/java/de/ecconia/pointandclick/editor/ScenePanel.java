package de.ecconia.pointandclick.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import de.ecconia.pointandclick.data.Scene;
import de.ecconia.pointandclick.data.Storage;

@SuppressWarnings("serial")
public class ScenePanel extends JPanel
{
	private final Storage storage;
	private final ScenePanelOwner owner;
	
	private boolean expectSelection;
	
	public ScenePanel(ScenePanelOwner owner, Storage storage)
	{
		this.owner = owner;
		this.storage = storage;
		setLayout(new ListLayout());
		
		for(Scene s : storage.getScenes())
		{
			add(new SceneEntry(this, s));
		}
	}
	
	public void refresh()
	{
		removeAll();
		for(Scene s : storage.getScenes())
		{
			add(new SceneEntry(this, s));
		}
		invalidate();
		validate();
	}
	
	private void clicked(SceneEntry sceneEntry, boolean leftclick)
	{
		Scene scene = sceneEntry.getScene();
		if(expectSelection)
		{
			if(leftclick)
			{
				owner.sceneSelected(scene);
				expectSelection = false;
			}
		}
		else
		{
			if(leftclick)
			{
				owner.switchScene(scene);
			}
			else
			{
				owner.openProperties(scene);
			}
		}
	}
	
	private static class SceneEntry extends JPanel
	{
		private final Scene scene;
		private Image scaledImage;
		private boolean openProps;
		
		public SceneEntry(ScenePanel panel, Scene scene)
		{
			setPreferredSize(new Dimension(150, 70));
			
			this.scene = scene;
			if(scene.getBackground() != null)
			{
				scaledImage = scene.getBackground().getImage();
			}
			
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
					
					panel.clicked(SceneEntry.this, button == MouseEvent.BUTTON1);
				}
			});
		}
		
		public Scene getScene()
		{
			return scene;
		}
		
		@Override
		public void paint(Graphics g)
		{
			g.setColor(new Color(220, 220, 220));
			g.fillRect(0, 0, getWidth(), getHeight());
			
			if(scaledImage != null)
			{
				g.drawImage(scaledImage, 5, 5, 60, 60, null);
			}
			
			g.setColor(Color.BLACK);
			String title = scene.getName();
			int height = g.getFontMetrics().getAscent();
			g.drawString(title, 70, getHeight() / 2 + height / 2);
			
			if(openProps)
			{
				g.setColor(Projector.boxColorHighlighted);
				g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
				g.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
			}
		}
		
		public void updatePropertiesOpen(Scene scene)
		{
			boolean before = openProps;
			openProps = scene == this.scene;
			if(before != openProps)
			{
				repaint();
			}
		}
	}
	
	public interface ScenePanelOwner
	{
		void openProperties(Scene scene);
		
		void sceneSelected(Scene scene);
		
		void switchScene(Scene scene);
	}
	
	public void setWaitForSelection(boolean b)
	{
		expectSelection = b;
	}
	
	public void setCurrentOpenProperties(Scene scene)
	{
		for(Object o : getComponents())
		{
			SceneEntry entry = (SceneEntry) o;
			entry.updatePropertiesOpen(scene);
		}
	}
}
