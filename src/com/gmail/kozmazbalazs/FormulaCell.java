package com.gmail.kozmazbalazs;

import java.security.InvalidParameterException;

public class FormulaCell implements Cell {

    private Operator operator;
	private Position firstCell;
	private Position secondCell;

	public FormulaCell(Position firstCell, Position secondCell, Operator operator) throws InvalidParameterException{
		if(!Spreadsheet.getInstance().containsPosition(firstCell) ||
				!Spreadsheet.getInstance().containsPosition(secondCell)){
			throw new InvalidParameterException("Error! The position is empty!");
		}
		if (Spreadsheet.getInstance().getCellByPosition(firstCell) instanceof TextCell ||
				Spreadsheet.getInstance().getCellByPosition(secondCell) instanceof TextCell){
			throw new InvalidParameterException("Error! Cannot calculate with text!");
		}
		if(!isValid(firstCell, secondCell)){
			throw new InvalidParameterException("Error! Circular reference.");
		}
		this.firstCell=firstCell;
		this.secondCell=secondCell;
		this.operator = operator;
	}

	public void setNewFormula(Position firstCell, Position secondCell, Operator operator) throws InvalidParameterException {
		if(!Spreadsheet.getInstance().containsPosition(firstCell) ||
				!Spreadsheet.getInstance().containsPosition(secondCell)){
			throw new InvalidParameterException("Error! The position is empty!");
		}
		if (Spreadsheet.getInstance().getCellByPosition(firstCell) instanceof TextCell ||
				Spreadsheet.getInstance().getCellByPosition(secondCell) instanceof TextCell){
			throw new InvalidParameterException("Error! Cannot calculate with text!");
		}
		if(!isValid(firstCell, secondCell)){
			throw new InvalidParameterException("Error! Circular reference.");
		}
		if(!checkForCircularRef(firstCell, secondCell)){
			throw new InvalidParameterException("Error! Circular reference.");
		}
		this.firstCell=firstCell;
		this.secondCell=secondCell;
		this.operator = operator;
	}

	public boolean isValid(Position firstCell, Position secondCell){
		if (Spreadsheet.getInstance().getPositionForCell(this) == null){
			return true;
		}
		if (Spreadsheet.getInstance().getPositionForCell(this).equals(firstCell) ||
				Spreadsheet.getInstance().getPositionForCell(this).equals(secondCell)){
			return false;
		}
		return true;
	}

	public Integer getValue() {
		Cell cell1 = Spreadsheet.getInstance().getCellByPosition(firstCell);
		Cell cell2 = Spreadsheet.getInstance().getCellByPosition(secondCell);

		int result = operator.calculate((int) cell1.getValue(), (int) cell2.getValue());



		return result;
	}

	public boolean checkForCircularRef(Position p1, Position p2){
		Spreadsheet.getInstance().lockCell(this);
		try{
			Spreadsheet.getInstance().getCellByPosition(p1).getValue();
			Spreadsheet.getInstance().getCellByPosition(p2).getValue();
		} catch (Exception e){
			Spreadsheet.getInstance().unlockCell(this);
			return false;
		}
		Spreadsheet.getInstance().unlockCell(this);
		return true;
	}

	@Override
	public String toString() {
		return "FormulaCell = " + getValue();
	}

}
