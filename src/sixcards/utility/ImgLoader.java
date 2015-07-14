package sixcards.utility;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImgLoader {
	
	public static void main(String[] args) {
		String cc[] ={"h","s","d","e"};
		ArrayList<BufferedImage> list = loadImage("52PokerCards.png", 4, 13);
		
		for(int i=0;i<52;i++){
			int rank = i%13 + 1;
			String suit = cc[i/13];
			String fileName = rank+suit+".png";
//			System.out.println(fileName);
			File f = new File("C:\\Users\\Steven\\Desktop\\SixCards web- contents\\img\\"+fileName);
			try {
				ImageIO.write(list.get(i), "PNG", f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	}
	
	
	
	private static ArrayList<BufferedImage> loadImage(String file,int row,int col){
		ArrayList<BufferedImage> res = new ArrayList<BufferedImage>();
		int hei = 0, wid = 0, x = 0, y = 0;
		// LOAD THE IMAGE
//		Toolkit tk = Toolkit.getDefaultToolkit();
//		Image img = tk.createImage(file);
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// AND WAIT FOR IT TO BE FULLY IN MEMORY BEFORE RETURNING IT
//		MediaTracker tracker = new MediaTracker(null);
//		tracker.addImage(img, 0);
//		try {
//			tracker.waitForID(0);
//		} catch (InterruptedException ie) {
//			System.out.println("MT INTERRUPTED");
//		}
		
		wid = img.getWidth()/col;
		hei = img.getHeight()/row;
		System.out.println(wid+"     "+hei);
		
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				x = j*wid;
				y = i*hei;
				BufferedImage imageToLoad = img.getSubimage(x, y, wid, hei);
				res.add(imageToLoad);
			}
		}
		
		return res;	
	}
	
	
	
	
	private static BufferedImage loadImage(String fileName){
		// LOAD THE IMAGE
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.createImage(fileName);

		// AND WAIT FOR IT TO BE FULLY IN MEMORY BEFORE RETURNING IT
		MediaTracker tracker = new MediaTracker(null);
		tracker.addImage(img, 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException ie) {
			System.out.println("MT INTERRUPTED");
		}

		// WE'LL USE BUFFERED IMAGES
		BufferedImage imageToLoad = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = imageToLoad.getGraphics();
		g.drawImage(img, 0, 0, null);
		
		return imageToLoad;
	}
}
