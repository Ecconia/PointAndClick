package de.ecconia.pointandclick;

import java.io.File;

public class FileHelper
{
	//Use the apparently only proper way to get the jar path...
	public static final File jarLoc;
	
	static
	{
		String absolute = FileHelper.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
		if(absolute.endsWith(".jar"))
		{
			absolute = absolute.substring(0, absolute.length() - 1);
			absolute = absolute.substring(0, absolute.lastIndexOf("/") + 1);
			
			String os = System.getProperty("os.name");
			if(os.indexOf("Windows") != -1)
			{
				absolute = absolute.replace("/", "\\\\");
				if(absolute.indexOf("file:\\\\") != -1)
				{
					absolute = absolute.replace("file:\\\\", "");
				}
			}
			else if(absolute.indexOf("file:") != -1)
			{
				absolute = absolute.replace("file:", "");
			}
			
			jarLoc = new File(absolute);
		}
		else
		{
			//Well was not a jar file, use default then.
			jarLoc = new File(".");
		}
	}
}
