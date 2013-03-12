package ch.bbw.gallery.slotmachine;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StartGameState implements IGameState {

	private BufferedImage slotMachine;
	
	private BufferedImage hatch;
	
	private IGameState runGameState;
	
	public StartGameState() {
		Class<?> loader = getClass();
		
		try {
			this.slotMachine = ImageIO.read(loader.getResourceAsStream("/slot.png"));
			this.hatch = ImageIO.read(loader.getResourceAsStream("/hatch.png"));
		} catch (IOException e) {
		}
	}
	
	@Override
	public void init() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void keyPressed(KeyEvent e, IStateContext context) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println("Enter");
			context.setState(this.runGameState);
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(this.hatch, 51, 85, null);
		g.drawImage(this.slotMachine, 0, 0, null);
	}

	@Override
	public void process() {
	}

	public IGameState getRunGameState() {
		return runGameState;
	}

	public void setRunGameState(IGameState runGameState) {
		this.runGameState = runGameState;
	}

}
