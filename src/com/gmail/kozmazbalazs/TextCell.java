package com.gmail.kozmazbalazs;

public class TextCell implements Cell {

	private String textContent;

	public TextCell(String text) {
		this.textContent = text;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	@Override
	public String getValue() {
		return textContent;
	}
}
