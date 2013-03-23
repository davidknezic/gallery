package ch.bbw.gallery.web.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import ch.bbw.gallery.core.models.Image;
import ch.bbw.gallery.core.models.ImageSize;
import ch.bbw.gallery.core.models.ImageType;

/**
 * Exposes methods for loading and manipulating images
 */
public interface IImageService {
	/**
	 * Uploads an image without the meta data
	 * 
	 * @param stream Stream of the image
	 * @param type Type of the image
	 * @return Newly created image object
	 */
	public Image uploadImage(InputStream stream, ImageType type) throws Exception;
	
	/**
	 * Saves the provided image
	 * 
	 * @param image Image object to save
	 */
	public void saveImage(Image image);
	
	/**
	 * Publishes the specified image
	 * 
	 * @param image Image object to publish
	 */
	public void publishImage(Image image);
	
	/**
	 * Writes an image to the provided output stream
	 * 
	 * @param id Image identifier
	 * @param output Output stream
	 * @throws IOException When the output can't be written
	 */
	public void downloadImage(String id, OutputStream output) throws IOException;
	
	/**
	 * Writes an image with the desired size to the provided output stream
	 * 
	 * @param id Image identifier
	 * @param size Size of the requested image
	 * @param output Output stream
	 * @throws IOException When the output can't be written
	 */
	public void downloadImage(String id, ImageSize size, OutputStream output) throws IOException;
	
	/**
	 * Gets the image object
	 * 
	 * @param id Image identifier
	 * @return Image object
	 */
	public Image retrieveImage(String id);
	
	/**
	 * Returns a list of random images
	 * 
	 * @param count Number of images to return
	 * @return Random images
	 */
	public List<Image> randomImages(int count);
	
	/**
	 * Returns a list of images in the creation order, beginning from the
	 * provided image.
	 * 
	 * @param count Number of images to return
	 * @param startId Optional identifier of the starting image
	 * @return List of images
	 */
	public List<Image> getImages(int count, String startId);
}
