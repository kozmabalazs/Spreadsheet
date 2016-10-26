package com.gmail.kozmazbalazs;

import javafx.geometry.Pos;

import java.security.InvalidParameterException;
import java.util.*;

public class Spreadsheet {

	private final static int SpreadsheetSize = 50;

	private HashMap<Position, Cell> cells;
	private HashMap<Position, Cell> cellsTemp;
	private static Spreadsheet INSTANCE;
	private int matrix[][];

	private Spreadsheet() {
		cells = new HashMap<Position, Cell>();
		cellsTemp = new HashMap<Position,Cell>();
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
		if (x>=1){
			matrix[x-1][y] = 1;
		}
		matrix[x+1][y] = 1;
		if (y>=1){
			matrix[x][y-1] = 1;
		}
		matrix[x][y+1] = 1;
	}

	public void mergeCells(Position ... positions)throws InvalidParameterException{
		Arrays.sort(positions, new Comparator<Position>() {
			@Override
			public int compare(Position o1, Position o2) {
				int result = Integer.compare(o1.getX(), o2.getX());
				if(result == 0){
					result = Integer.compare(o1.getY(), o2.getY());
				}
				return result;
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

	public void showMatrix(){
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 50; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
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

	public boolean containsCell(Cell cell) {
		for (Map.Entry<Position, Cell> entry : cells.entrySet()) {
			if (entry.getValue().equals(cell)) {
				return true;
			}
		}
		return false;
	}

	public boolean containsPosition(Position position){
		if (cells.containsKey(position)){
			return true;
		} else {
			return false;
		}
	}

	public void lockCell(Cell cell){
		Position p = getPositionForCell(cell);
		cellsTemp.put(p, cells.get(p));
		cells.remove(p);
	}

	public void unlockCell(Cell cell){
		Position p = getPositionForCell(cell);
		cells.put(p, cells.get(p));
		cellsTemp.remove(p);
	}
}
