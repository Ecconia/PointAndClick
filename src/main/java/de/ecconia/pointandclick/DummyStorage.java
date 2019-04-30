package de.ecconia.pointandclick;

import java.io.File;

import de.ecconia.pointandclick.data.Clickable;
import de.ecconia.pointandclick.data.ImageResource;
import de.ecconia.pointandclick.data.Scene;
import de.ecconia.pointandclick.data.Storage;

public class DummyStorage
{
	public static Storage createTestStorage()
	{
		Storage storage = new Storage(1024, 1024, "Dummy project");
		
		Scene scene = new Scene("startScene");
		storage.addStartScene(scene);
		scene.setBackground(new ImageResource(new File(FileHelper.jarLoc, "Start.png")));
		
		scene = new Scene("forest");
		scene.setBackground(new ImageResource(new File(FileHelper.jarLoc, "Forest.png")));
		storage.addScene(scene);
		
		storage.getScene("startScene").addClickable(new Clickable(600, 400, 200, 300, storage.getScene("forest")));
		storage.getScene("startScene").addClickable(new Clickable(50, 600, 200, 250));
		storage.getScene("forest").addClickable(new Clickable(300, 800, 350, 200, storage.getScene("startScene")));
		
		return storage;
	}
}
