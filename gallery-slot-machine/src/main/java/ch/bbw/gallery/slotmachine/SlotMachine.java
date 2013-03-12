package ch.bbw.gallery.slotmachine;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

public class SlotMachine extends Applet implements ActionListener, KeyListener, IStateContext {
	/**
	 * For serialization
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Global application timer
	 */
	private Timer timer;
	
	/**
	 * Current applet state
	 */
	private IGameState currentGameState;
	
	/**
	 * Off screen image for double buffered drawing
	 */
	private Image offImage;
	
	/**
	 * Graphics instance of the off screen image for double buffering
	 */
	private Graphics offGraphics;
	
	/**
	 * Initialize the applet
	 */
	@Override
	public void init() {
		// Initialize all available game states
		StartGameState startGameState = new StartGameState();
		RunGameState runGameState = new RunGameState("http://localhost:8080/gallery-web/api");
		startGameState.setRunGameState(runGameState);
		runGameState.setStartGameState(startGameState);
		
		// Set default current state
		this.currentGameState = startGameState;
		this.currentGameState.init();
		
		// Some general settings
		this.setSize(640, 480);
		
		// Create an off screen graphics and image object for double buffering
		this.offImage = createImage(640, 480);
		this.offGraphics = this.offImage.getGraphics();
		
		// Create the main application timer
		this.timer = new Timer(40, this);
	}
	
	/**
	 * Start the applet
	 */
	@Override
	public void start() {
		// Add key listener for catching key events
		this.addKeyListener(this);
		
		// Start the timer
		this.timer.start();
	}
	
	/**
	 * Stop the applet
	 */
	@Override
	public void stop() {
		// Stop the game by stopping the timer
		this.timer.stop();
		
		// Detach the key listener
		this.removeKeyListener(this);
	}
	
	@Override
	public void update(Graphics g) {
		this.currentGameState.paint(this.offGraphics);
		
		g.drawImage(this.offImage, 0, 0, null);
	}
	
	@Override
	public void paint(Graphics g) {
		update(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.currentGameState.process();
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.currentGameState.keyPressed(e, this);
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void setState(IGameState gameState) {
		this.currentGameState.destroy();
		gameState.init();
		this.currentGameState = gameState;
	}
}
