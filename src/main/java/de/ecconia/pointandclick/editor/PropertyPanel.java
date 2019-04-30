package de.ecconia.pointandclick.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.ecconia.pointandclick.data.Clickable;
import de.ecconia.pointandclick.data.Scene;

@SuppressWarnings("serial")
public class PropertyPanel extends JPanel
{
	private final PropertyPanelOwner owner;
	
	public PropertyPanel(PropertyPanelOwner owner)
	{
		this.owner = owner;
		setPreferredSize(new Dimension(400, 0));
		setLayout(new BorderLayout());
	}
	
	public void showScene(Scene scene)
	{
		owner.abortExpectingScene();
		removeAll();
		add(new SceneProperties(scene));
		invalidate();
		validate();
	}
	
	public void showClickable(Clickable clickable)
	{
		owner.abortExpectingScene();
		removeAll();
		add(new ClickableProperties(clickable));
		invalidate();
		validate();
	}
	
	public void selectScene(Scene scene)
	{
		Object o = getComponent(0);
		if(o instanceof ClickableProperties)
		{
			((ClickableProperties) o).acceptScene(scene);
		}
	}
	
	private static class SceneProperties extends JPanel
	{
		public SceneProperties(Scene scene)
		{
			setLayout(new ListLayout());
			add(new JLabel("Properties of " + scene.getName()));
		}
	}
	
	private class ClickableProperties extends JPanel
	{
		public final Clickable clickable;
		public final JLabel currentSceneDirection;
		
		public ClickableProperties(Clickable clickable)
		{
			this.clickable = clickable;
			
			setLayout(new ListLayout());
			add(new JLabel("Properties of " + clickable));
			
			JPanel directedScene = new JPanel(new FlowLayout(FlowLayout.LEFT));
			directedScene.add(new JLabel("Directs to Scene: "));
			Scene toScene = clickable.getToScene();
			currentSceneDirection = new JLabel((toScene == null ? "-none-" : toScene.getName()));
			directedScene.add(currentSceneDirection);
			JButton button = new JButton("Change");
			button.addActionListener((ActionEvent e) -> {
				owner.expectingScene();
			});
			directedScene.add(button);
			
			add(directedScene);
		}
		
		public void acceptScene(Scene scene)
		{
			clickable.setToScene(scene);
			currentSceneDirection.setText(scene.getName());
		}
	}
	
	public interface PropertyPanelOwner
	{
		void expectingScene();
		
		void abortExpectingScene();
	}
}
