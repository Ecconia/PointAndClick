package de.ecconia.pointandclick.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Storage
{
	private String name;
	private int width, height;
	private Map<String, Scene> scenes;
	private Scene entryScene;
	
	public Storage(int width, int height, String name)
	{
		this.name = name;
		this.width = width;
		this.height = height;
		scenes = new HashMap<>();
	}
	
	public Scene getEntryScene()
	{
		return entryScene;
	}
	
	public void addStartScene(Scene scene)
	{
		entryScene = scene;
		addScene(scene);
	}
	
	public void addScene(Scene scene)
	{
		scenes.put(scene.getName(), scene);
	}
	
	public Scene getScene(String name)
	{
		return scenes.get(name);
	}

	public String getName()
	{
		return name;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	//Listeners:
	
	public Collection<Scene> getScenes()
	{
		return scenes.values();
	}
}
