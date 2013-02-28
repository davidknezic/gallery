package ch.bbw.gallery.core.models;

public class Image {
	private String id;
	private String filename;
	private String comment;
	private ImageType type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ImageType getType() {
		return type;
	}

	public void setType(ImageType type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		Image image = (Image)obj;
		return (this.id.equalsIgnoreCase(image.getId()));
	}
}
