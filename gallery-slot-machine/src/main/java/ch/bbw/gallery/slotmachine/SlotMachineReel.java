package ch.bbw.gallery.slotmachine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Random;

import ch.bbw.gallery.core.models.Image;

public class SlotMachineReel {
	private BufferedImage reelTexture;
	
	private float totalHeight;
	
	private float velocity = 0.02f;
	
	private float rotation = 0.0f;
	
	public SlotMachineReel(RunGameStateReelItem[] items, int width, int height) {
		
		Random r = new Random(new Date().getTime());
		velocity += r.nextFloat() * 0.06f;
		
		int padding = (height - 160) / 2;
		int cut = 160 - padding;
		
		this.totalHeight = items.length * 160.0f;
		
		int h = (items.length + 1) * 160 + 2 * padding;
		
		
		reelTexture = new BufferedImage(160, h, BufferedImage.TYPE_INT_RGB);
		
		Graphics g = reelTexture.getGraphics();
		g.drawImage(items[items.length - 1].getImage(), 0, -cut, null);
		
		for (int i = 0; i < items.length; i++) {
			g.drawImage(items[i].getImage(), 0, i * 160 + padding, null);
		}

		g.drawImage(items[0].getImage(), 0, items.length * 160 + padding, null);
		g.drawImage(items[1].getImage(), 0, (items.length + 1) * 160 + padding, null);
		
		System.out.println(this.totalHeight);
		
	}
	
	public void paint(Graphics g) {
		int verticalPosition = (int) (totalHeight * rotation);
		
		g.drawImage(reelTexture, 0, -verticalPosition, null);
	}
	
	public void process() {
		rotation += velocity;
		
		if (rotation >= 1.0f) {
			rotation -= 1.0f;
		}
	}
	
	public void stop() {
		this.velocity = 0.0f;
	}
	
	public Image current() {
		return null;
	}
}
