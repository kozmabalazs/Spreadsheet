package com.gmail.kozmazbalazs;

import java.util.ArrayList;
import java.util.List;

public class ValueCell implements Cell {

	private int value;

	public ValueCell(int value) {
		this.value = value;
	}

	public Integer getValue() {

		return value;
	}

	public void setValue(int value) {

		this.value = value;
	}

	@Override
	public String toString() {

		return "ValueCell = " + value;
	}

}
