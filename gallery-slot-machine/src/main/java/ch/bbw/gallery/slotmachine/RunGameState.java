package ch.bbw.gallery.slotmachine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import ch.bbw.gallery.core.models.Image;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class RunGameState implements IGameState {

	private StartGameState startGameState;
	
	private WebResource webResource;
	
	private Image[] imageList;
	
	private BufferedImage currentImage;
	
	private int frames = 0;
	
	private int currentImageNumber = 0;
	
	public RunGameState(String path) {
		ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client c = Client.create(clientConfig);

		webResource = c.resource(path); // "http://localhost:8080/global-gallery/api"
	}
	
	@Override
	public void init() {
		imageList = webResource.path("/random/10").accept(MediaType.APPLICATION_JSON).get(Image[].class);
		
		MultivaluedMap<String, String> map = new MultivaluedMapImpl();
		map.add("id", imageList[currentImageNumber].getId());
		map.add("width", "300");
		map.add("height", "300");
		currentImage = webResource.path("/thumbnail").queryParams(map).accept(MediaType.APPLICATION_JSON).get(BufferedImage.class);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e, IStateContext context) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			context.setState(this.startGameState);
		}
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.drawLine(50, 50, 100, 100);
	}

	@Override
	public void process() {
		
		frames++;
		
		if (frames > 10) {
			
			
			
			frames = 0;
		}
		
		System.out.println("Process2");
	}

	public StartGameState getStartGameState() {
		return startGameState;
	}

	public void setStartGameState(StartGameState startGameState) {
		this.startGameState = startGameState;
	}

}
