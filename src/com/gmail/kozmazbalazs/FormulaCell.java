package com.gmail.kozmazbalazs;

import java.security.InvalidParameterException;

public class FormulaCell implements Cell {

    private Operator operator;
	private Position firstCell;
	private Position secondCell;

	public FormulaCell(){
	}

	public void setFormula(Position firstCell, Position secondCell, Operator operator) throws InvalidParameterException {
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

	public boolean isValid(Position firstCell, Position secondCell){
		if (Spreadsheet.getInstance().getPositionForCell(this).equals(firstCell) ||
				Spreadsheet.getInstance().getPositionForCell(this).equals(secondCell)){
			return false;
		}
		return true;
	}

	public Integer getValue() throws NullPointerException {
		Cell cell1 = Spreadsheet.getInstance().getCellByPosition(firstCell);
		Cell cell2 = Spreadsheet.getInstance().getCellByPosition(secondCell);
		try{
			return operator.calculate((int) cell1.getValue(), (int) cell2.getValue());
		} catch (NullPointerException e){
			return 0;
		}
	}

	@Override
	public String toString() {
		return "FormulaCell = " + getValue();
	}

}
