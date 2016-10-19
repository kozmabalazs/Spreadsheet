package com.gmail.kozmazbalazs;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Spreadsheet {

	private final static int SpreadsheetSize = 50;

	private LinkedHashMap<Position, Cell> cells;
	private static Spreadsheet INSTANCE;
	private int matrix[][];

	private Spreadsheet() {
		cells = new LinkedHashMap<Position, Cell>();
		matrix = new int[SpreadsheetSize][SpreadsheetSize];
		for(int i=0; i<SpreadsheetSize; i++)
			for (int j = 0; j < SpreadsheetSize; j++) {
				matrix[i][j] = 0;
			}
	}

	public static Spreadsheet getInstance(){
		if (INSTANCE == null){
			INSTANCE = new Spreadsheet();
		}
		return INSTANCE;
	}

	public Cell getCellByPosition(Position position) {
		return cells.get(position);
	}

	public void addCell(Position position, Cell cell) {
		cells.put(position, cell);
		int x = position.getX();
		int y = position.getY();
		if (y>1){
			matrix[x][y-1] = 1;
		}
		matrix[x][y+1] = 1;
		if (x>1){
			matrix[x-1][y] = 1;
		}
		matrix[x+1][y] = 1;
	}

	public void mergeCells(Position ... positions)throws InvalidParameterException{
		for(int i=0; i<positions.length - 1; i++){
			if(positions[i].getX() != positions[i+1].getX()){
				throw new InvalidParameterException("Not a row!");
			}
		}
		Arrays.sort(positions, new Comparator<Position>() {
			@Override
			public int compare(Position o1, Position o2) {
				return Integer.compare(o1.getY(), o2.getY());
			}
		});

		for(int i=0; i<positions.length -1; i++) {
			Position position1 = positions[i];
			Position position2 = positions[i+1];
			if(matrix[position1.getY()][position2.getY()] != 1) {
				throw new InvalidParameterException("Not a row!");
			}
		}

		Cell cell = cells.get(positions[0]);
		for(int i=1; i<positions.length; i++){
			cells.put(positions[i],cell);
		}
	}

	public void showSpreadsheet() {
		for (Cell cell : cells.values()) {
			System.out.println(cell.toString());
		}
	}

	public Position getPositionForCell(Cell cell) {
		for (Map.Entry<Position, Cell> entry : cells.entrySet()) {
			if (entry.getValue().equals(cell)) {
				return entry.getKey();
			}
		}
		return null;
	}
}
