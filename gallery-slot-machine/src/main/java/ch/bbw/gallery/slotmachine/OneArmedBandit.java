package ch.bbw.gallery.slotmachine;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.Timer;

public class OneArmedBandit extends Applet implements ActionListener, KeyListener, IStateContext {

	private static final long serialVersionUID = 1L;
	
	private URL apiURL;
	private Timer timer;
	
	private IGameState currentGameState;
	
	@Override
	public void start() {
		// Initialize all available game states
		StartGameState startGameState = new StartGameState();
		RunGameState runGameState = new RunGameState("http://localhost:8080/global-gallery/api");
		startGameState.setRunGameState(runGameState);
		runGameState.setStartGameState(startGameState);
		
		// set default current State
		this.currentGameState = startGameState;
		
//		try {
//			this.apiURL = new URL(getParameter("url"));
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
		
		this.addKeyListener(this);
		
		this.timer = new Timer(40, this);
		this.timer.start();
	}
	
	@Override
	public void paint(Graphics g) {
		System.out.println("paint");
		this.currentGameState.paint(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.currentGameState.process();
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Press");
		this.currentGameState.keyPressed(e, this);
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void setState(IGameState gameState) {
		this.currentGameState.destroy();
		gameState.init();
		this.currentGameState = gameState;
	}
}
