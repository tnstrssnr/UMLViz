package graphgeneration.model;

import org.jetbrains.annotations.NotNull;

public class GraphSettings {

	@NotNull
	private String font;
	private int fontSize;

	public GraphSettings(@NotNull String font, int fontSize) {
		this.font = font;
		this.fontSize = fontSize;
	}

	public @NotNull String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
}