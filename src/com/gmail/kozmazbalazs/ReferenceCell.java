package com.gmail.kozmazbalazs;

import javafx.geometry.Pos;

import java.security.InvalidParameterException;

public class ReferenceCell implements Cell {

    Position position;

    public ReferenceCell(){
    }

    public void setReference(Position position) throws InvalidParameterException{
        this.position = position;
        if (!isValid()){
            throw new InvalidParameterException("Error! Circular reference.");
        }
    }

    public boolean isValid(){
        if (Spreadsheet.getInstance().getPositionForCell(this).equals(position)){
            return false;
        }
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
