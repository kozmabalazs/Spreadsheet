package com.gmail.kozmazbalazs;

import java.security.InvalidParameterException;

public class ReferenceCell implements Cell {

	Position position;

	public ReferenceCell(Position position) throws InvalidParameterException{
		this.position = position;
		if (!isValid()) {
			throw new InvalidParameterException("Error! Circular reference.");
		}
	}

	public void setReference(Position position) throws InvalidParameterException{
		this.position = position;
		if(!isValid()) {
			throw new InvalidParameterException("Error! Circular reference.");
		}
		if(checkForCircularRef()){
			throw new InvalidParameterException("Error! Circular reference.");
		}
	}

	public boolean isValid() {
		if (Spreadsheet.getInstance().getPositionForCell(this) == null){
			return true;
		}
		if (Spreadsheet.getInstance().getPositionForCell(this).equals(position)) {
			return false;
		}
		return false;
	}

	public boolean checkForCircularRef(){
		Spreadsheet.getInstance().lockCell(this);
		try{
			Spreadsheet.getInstance().getCellByPosition(position).getValue();
		} catch (Exception e){
			Spreadsheet.getInstance().unlockCell(this);
			return false;
		}
		Spreadsheet.getInstance().unlockCell(this);
		return true;
	}

	@Override
	public Object getValue() {
		return Spreadsheet.getInstance().getCellByPosition(position).getValue();
	}

	@Override
	public String toString() {
		return "Reference cell = " + getValue();
	}
}
