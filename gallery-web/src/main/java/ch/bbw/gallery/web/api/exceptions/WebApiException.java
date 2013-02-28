package ch.bbw.gallery.web.api.exceptions;

import javax.ws.rs.core.Response.Status;

public class WebApiException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String message;
	private Status status;

	public WebApiException(String msg, Object... args) {
		this(Status.INTERNAL_SERVER_ERROR, msg, args);
	}
	
	public WebApiException(Status status, String msg, Object... args) {
		this.message = String.format(msg, args);
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
