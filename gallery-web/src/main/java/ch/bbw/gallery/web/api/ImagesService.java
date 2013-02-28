package ch.bbw.gallery.web.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import ch.bbw.gallery.core.models.Image;
import ch.bbw.gallery.core.models.ImageSize;
import ch.bbw.gallery.core.models.ImageType;
import ch.bbw.gallery.web.api.exceptions.WebApiException;
import ch.bbw.gallery.web.services.IImageService;
import ch.bbw.gallery.web.services.ImageService;

@Path("/images")
public class ImagesService {
	/**
	 * ImageService
	 */
	private IImageService imageService;

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	public ImagesService() throws Exception {
		this.imageService = new ImageService();
	}
	
	/**
	 * Return the specified number of random images
	 * 
	 * @param count Number of images to return
	 * @return Random images
	 * @throws WebApiException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/random/{count}")
	public List<Image> random(@PathParam("count") int count) throws WebApiException {
		return this.imageService.randomImages(count);
	}
	
	/**
	 * Edit the attributes of an image
	 * 
	 * @param image Image object with modified attributes
	 * @return New image as saved in the database
	 * @throws WebApiException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/edit")
	public Image edit(Image image) throws WebApiException {
		Image editImage = this.imageService.retrieveImage(image.getId());
		
		editImage.setComment(image.getComment());
		
		String extOrig = editImage.getType().toString();
		String extEdit = FilenameUtils.getExtension(image.getFilename());
		
		if (extOrig.equalsIgnoreCase(extEdit)) {
			editImage.setFilename(image.getFilename());
		} else {
			throw new WebApiException(Status.NOT_ACCEPTABLE, "Filename must have extension '%s'", extOrig);
		}
		
		this.imageService.saveImage(editImage);
		
		return editImage;
	}
	
	/**
	 * Upload one or multiple files
	 * 
	 * @param request Request context
	 * @return List of newly created images
	 * @throws WebApiException
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/upload")
	public List<Image> upload(@Context HttpServletRequest request) throws WebApiException {
		List<FileItem> files;
		List<Image> images = new ArrayList<Image>();
		
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		try {
			files = upload.parseRequest(request);
		} catch (FileUploadException e) {
			throw new WebApiException("Error uploading images");
		}
		
		// One part corresponds to one file
		for (FileItem file : files) {
			InputStream stream;
			
			try {
				stream = file.getInputStream();
			} catch (IOException e) {
				continue;
			}
			
			ImageType type = ImageType.fromMimeType(file.getContentType());
			
			if (type == null) {
				throw new WebApiException("Unsupported file type");
			}
			
			String baseName = FilenameUtils.getBaseName(file.getName());
			String filename = baseName
					+ FilenameUtils.EXTENSION_SEPARATOR_STR
					+ type.toString();
			
			try {
				Image image = this.imageService.uploadImage(stream, type);
				image.setFilename(filename);
				
				this.imageService.saveImage(image);
				
				images.add(image);
			} catch (Exception e) {
				throw new WebApiException("Error uploading file '%s'", file.getName());
			}
		}
		
		return images;
	}
	
	/**
	 * Retrieve a thumbnail of the specified image
	 *  
	 * @param id Image whose thumbnail is returned
	 * @param size Desired thumbnail size
	 * @return Thumbnail of the specified image in the desired size
	 * @throws WebApiException
	 */
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("/thumbnail/{id}/{width}/{height}")
	public Response thumbnail(
			@PathParam("id") String id,
			@DefaultValue("0") @PathParam("width") int width,
			@DefaultValue("0") @PathParam("height") int height) throws WebApiException {
		final Image image = this.imageService.retrieveImage(id);
		final ImageSize size = new ImageSize(width, height);
		
		StreamingOutput output = new StreamingOutput() {
			public void write(OutputStream output)
					throws IOException, WebApplicationException {
				imageService.downloadImage(image.getId(), size, output);
			}
		};
		
		String disp = String.format("attachment: filename=%s", image.getFilename());
		
		return Response.ok()
				.header("Content-Disposition", disp)
				.type(image.getType().mimeType())
				.entity(output).build();
	}

	/**
	 * Retrieves the full image data
	 * 
	 * @param id Image to retrieve
	 * @return Specified image
	 * @throws WebApiException
	 */
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("/download/{id}")
	public Response download(@PathParam("id") String id) throws WebApiException {
		final Image image = this.imageService.retrieveImage(id);
		
		StreamingOutput output = new StreamingOutput() {
			public void write(OutputStream output)
					throws IOException, WebApplicationException {
				imageService.downloadImage(image.getId(), output);
			}
		};
		
		String disp = String.format("attachment: filename=%s", image.getFilename());
		return Response.ok()
				.header("Content-Disposition", disp)
				.type(image.getType().mimeType())
				.entity(output).build();
	}
}
