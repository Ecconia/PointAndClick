package de.ecconia.pointandclick.engine;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import de.ecconia.pointandclick.DummyStorage;
import de.ecconia.pointandclick.data.Storage;

public class StartEngine
{
	public static void main(String[] args)
	{
		Storage storage = DummyStorage.createTestStorage();
		
		JFrame frame = new JFrame("Point and Click: " + storage.getName());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(new Projector(storage.getEntryScene()), BorderLayout.CENTER);
		
		frame.setPreferredSize(new Dimension(storage.getWidth(), storage.getHeight()));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
