package com.cvs.hackathon.face;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

public class DrawRectangle {

	public static void main(String[] args) {
		
		String image_file = "C:\\Users\\csm\\eclipse-workspace-4.7\\AzureVisionWeb\\src\\com\\cvs\\hackathon\\test07192020.jpg";
		URL url = null;
		//https://storagecvteam01.blob.core.windows.net/faces/facetoid01.jpg
		//https://storagecvteam01.blob.core.windows.net/faces/Joe pic2.jpg
		try {
			
			url =  new URL("https://storagecvteam01.blob.core.windows.net/faces/Joepic2.jpg");
			BufferedImage img = ImageIO.read(url);
			Graphics2D g2d = img.createGraphics();
			g2d.setColor(Color.GREEN);
			g2d.setStroke(new BasicStroke(15));
			g2d.drawRect(723, 462, 1248, 1248);//g2d.drawRect(1404, 1314, 507, 507);
			
			g2d.dispose();
			ImageIO.write(img, "jpg", new File(image_file));
		} catch (Exception e) {
		    System.out.println("[ERROR] Could not save image.");
		}
	}

}
