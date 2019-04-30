package de.ecconia.pointandclick.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import de.ecconia.pointandclick.DummyStorage;
import de.ecconia.pointandclick.FileHelper;
import de.ecconia.pointandclick.data.Clickable;
import de.ecconia.pointandclick.data.ImageResource;
import de.ecconia.pointandclick.data.Scene;
import de.ecconia.pointandclick.data.Storage;
import de.ecconia.pointandclick.editor.Projector.ProjectorOwner;
import de.ecconia.pointandclick.editor.PropertyPanel.PropertyPanelOwner;
import de.ecconia.pointandclick.editor.ScenePanel.ScenePanelOwner;

public class StartEditor implements ProjectorOwner, ScenePanelOwner, PropertyPanelOwner
{
	public static void main(String[] args)
	{
		new StartEditor();
	}
	
	private final Projector projector;
	private final ScenePanel sceneList;
	private final PropertyPanel properties;
	
	private Scene currentScene;
	private Storage storage;
	
	private JMenuBar genMenuBar()
	{
		//You wanna real snack'eh? Take this bar:
		JMenuBar bar = new JMenuBar();
		JMenu newMenu = new JMenu("New");
		
		//Add new scene
		JMenuItem item = new JMenuItem("Scene", KeyEvent.VK_S);
		item.addActionListener((ActionEvent e) -> {
			JFileChooser bgImageChooser = new JFileChooser(FileHelper.jarLoc);
			int i = bgImageChooser.showOpenDialog(null);
			if(i == 0)
			{
				File file = bgImageChooser.getSelectedFile();
				String name = JOptionPane.showInputDialog(null, "Insert Scene name.");
				if(name != null && !name.isEmpty())
				{
					Scene newScene = new Scene(name);
					newScene.setBackground(new ImageResource(file));
					storage.addScene(newScene);
					sceneList.refresh();
				}
			}
		});
		newMenu.add(item);
		
		//Add new clickable
		item = new JMenuItem("Clickable", KeyEvent.VK_C);
		item.addActionListener((ActionEvent e) -> {
			projector.newClickable();
		});
		newMenu.add(item);
		
		bar.add(newMenu);
		return bar;
	}
	
	public StartEditor()
	{
		storage = DummyStorage.createTestStorage();
		
		JFrame frame = new JFrame("Point and Click Editor: " + storage.getName());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.setJMenuBar(genMenuBar());
		
		currentScene = storage.getEntryScene();
		
		sceneList = new ScenePanel(this, storage);
		projector = new Projector(new Dimension(storage.getWidth(), storage.getHeight()), currentScene, this);
		properties = new PropertyPanel(this);
		
		frame.add(sceneList, BorderLayout.WEST);
		frame.add(projector, BorderLayout.CENTER);
		frame.add(properties, BorderLayout.EAST);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	//Internal forwarding:
	
	@Override
	public void switchingScene(Scene scene)
	{
		currentScene = scene;
		projector.applyScene(scene);
	}
	
	@Override
	public void newClickable(int x, int y, int width, int height, boolean valid)
	{
		if(valid)
		{
			currentScene.addClickable(new Clickable(x, y, width, height));
		}
		else
		{
			System.out.println("Clickable area occupied.");
		}
	}
	
	@Override
	public void openProperties(Clickable clickable)
	{
		projector.setCurrentClickable(clickable); //Set the clickable area to highlight.
		properties.showClickable(clickable);
		sceneList.setCurrentOpenProperties(null);
	}
	
	@Override
	public void openProperties(Scene scene)
	{
		projector.setCurrentClickable(null); //Reset the clickable area to be highlighted.
		properties.showScene(scene);
		sceneList.setCurrentOpenProperties(scene);
	}
	
	@Override
	public void sceneSelected(Scene scene)
	{
		properties.selectScene(scene);
	}
	
	@Override
	public void switchScene(Scene scene)
	{
		currentScene = scene;
		projector.applyScene(scene);
	}

	@Override
	public void expectingScene()
	{
		sceneList.setWaitForSelection(true);
	}

	@Override
	public void abortExpectingScene()
	{
		sceneList.setWaitForSelection(false);
	}
}
