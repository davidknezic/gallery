package ch.bbw.gallery.core.models;

public enum ImageType {
	jpeg ("image/jpeg"),
	jpg ("image/jpeg"),
	png ("image/png");
	
	private final String mimeType;
	
	private ImageType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public String mimeType() {
		return this.mimeType;
	}
	
	public static ImageType fromMimeType(String mimeType) {
		for (ImageType type : values()) {
			if (type.mimeType.equalsIgnoreCase(mimeType)) {
				return type;
			}
		}
		return null;
	}
}
