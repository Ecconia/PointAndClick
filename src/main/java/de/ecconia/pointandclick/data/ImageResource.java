package de.ecconia.pointandclick.data;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ImageResource
{
	private Image image;
	private final File path;
	
	public ImageResource(File path)
	{
		this.path = path;
		
		try
		{
			image = ImageIO.read(path);
		}
		catch(IOException e)
		{
			String error = "Could not load image '" + path.getAbsolutePath() + "', " + e.getClass().getSimpleName() + " exception happend" + (e.getMessage() != null ? e.getMessage() : '.');
			System.out.println("Could not load image '" + path.getAbsolutePath() + "', " + e.getClass().getSimpleName() + " exception happend" + (e.getMessage() != null ? e.getMessage() : '.'));
			JOptionPane.showMessageDialog(null, error, "Error loading resource.", JOptionPane.ERROR_MESSAGE);
		}
	}

	public Image getImage()
	{
		return image;
	}
	
	public File getPath()
	{
		return path;
	}
	
	
}
