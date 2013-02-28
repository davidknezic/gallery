package ch.bbw.gallery.slotmachine;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public interface IGameState {
	
	public void init();
	
	public void destroy();
	
	public void keyPressed(KeyEvent e, IStateContext context);
	
	public void paint(Graphics g);
	
	public void process();

}
