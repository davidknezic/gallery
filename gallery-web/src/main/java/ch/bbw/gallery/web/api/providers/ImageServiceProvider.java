package ch.bbw.gallery.web.api.providers;

import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import ch.bbw.gallery.web.services.IImageService;
import ch.bbw.gallery.web.services.ImageService;

import com.sun.jersey.spi.inject.SingletonTypeInjectableProvider;

@Provider
public class ImageServiceProvider extends SingletonTypeInjectableProvider<Context, IImageService> {
	public ImageServiceProvider() throws Exception {
		super(IImageService.class, new ImageService());
	}
}
