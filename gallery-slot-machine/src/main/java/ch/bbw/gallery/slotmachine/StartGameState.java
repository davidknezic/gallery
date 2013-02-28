package ch.bbw.gallery.slotmachine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class StartGameState implements IGameState {

	private IGameState runGameState;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
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
		g.setColor(Color.green);
		g.drawLine(0, 0, 100, 100);
	}

	@Override
	public void process() {
		System.out.println("Process");
	}

	public IGameState getRunGameState() {
		return runGameState;
	}

	public void setRunGameState(IGameState runGameState) {
		this.runGameState = runGameState;
	}

}
