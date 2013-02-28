package ch.bbw.gallery.web.api.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ch.bbw.gallery.web.api.exceptions.WebApiException;

@Provider
public class WebApiExceptionMapper implements ExceptionMapper<WebApiException> {
	public Response toResponse(WebApiException e) {
		WebApiError error = new WebApiError();
		error.error = e.getMessage();
		error.status = e.getStatus().getStatusCode();
		
		return Response.status(e.getStatus()).entity(error).build();
	}
	
	private class WebApiError {
		@SuppressWarnings("unused")
		public String error;
		@SuppressWarnings("unused")
		public int status;
	}
}
