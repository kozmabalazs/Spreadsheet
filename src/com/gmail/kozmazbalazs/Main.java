package com.gmail.kozmazbalazs;

import java.security.InvalidParameterException;
import java.util.Random;

public class Main {

	public static void main(String[] args) {

		Random random = new Random();
		Spreadsheet sp = Spreadsheet.getInstance();

		Position p1 = new Position(0, 0);
		Position p2 = new Position(0, 1);
		Position p3 = new Position(0, 2);
		Position p4 = new Position(0, 3);
		Position p5 = new Position(1, 0);
		Position p6 = new Position(1, 1);
		Position p7 = new Position(1, 2);
		Position p8 = new Position(1, 3);
		Position p9 = new Position(2, 0);
		Position p10 = new Position(2, 1);
		Position p11 = new Position(2, 2);
		Position p12 = new Position(2, 3);
		Position p13 = new Position(3, 0);
		Position p14 = new Position(3, 1);

		sp.addCell(p1, new ValueCell(random.nextInt(10)));
		sp.addCell(p2, new ValueCell(random.nextInt(10)));
		sp.addCell(p3, new ValueCell(random.nextInt(10)));
		sp.addCell(p4, new ValueCell(random.nextInt(10)));
		sp.addCell(p5, new ValueCell(random.nextInt(10)));
		sp.addCell(p6, new ValueCell(random.nextInt(10)));
		sp.addCell(p7, new ValueCell(random.nextInt(10)));
		sp.addCell(p8, new ValueCell(random.nextInt(10)));

		sp.addCell(p9, new FormulaCell());
		((FormulaCell)sp.getCellByPosition(p9)).setFormula(p1, p2, Operator.ADD);
		sp.addCell(p10, new FormulaCell());
		((FormulaCell)sp.getCellByPosition(p10)).setFormula(p3, p4, Operator.SUBTRACT);
		sp.addCell(p11, new FormulaCell());
		((FormulaCell)sp.getCellByPosition(p11)).setFormula(p5, p6, Operator.MULTIPLY);
		sp.addCell(p12, new FormulaCell());
		try {
			((FormulaCell)sp.getCellByPosition(p12)).setFormula(p12, p8, Operator.DIVIDE);
		} catch (InvalidParameterException e){
			System.out.println(e.getMessage());
		}

		sp.showSpreadsheet();

		sp.addCell(p13, new ReferenceCell());
		((ReferenceCell)sp.getCellByPosition(p13)).setReference(p1);
		sp.addCell(p14, new ReferenceCell());
		((ReferenceCell)sp.getCellByPosition(p14)).setReference(p13);

		sp.showSpreadsheet();

		sp.mergeCells(p1, p2, p3);

		sp.showSpreadsheet();
	}
}
