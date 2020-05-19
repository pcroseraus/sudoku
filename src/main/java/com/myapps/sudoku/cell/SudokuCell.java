//Copyright 2018
package com.myapps.sudoku.cell;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;

/**
 *
 * @author roserp
 */
public class SudokuCell {
    
    int gridNumber;
    int rowNumber;
    int colNumber;
    int value;
    ArrayList<Integer> valuesNotSolved = new ArrayList<>();
    private JTextField cellField = new JTextField("");

    public SudokuCell() {
    }

    public SudokuCell(int gridNumber, int rowNumber, int colNumber, int value, ArrayList<Integer> valuesNotSolved) {
        this.gridNumber = gridNumber;
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.value = value;
        this.valuesNotSolved = valuesNotSolved;
    }

    public int getGridNumber() {
        return gridNumber;
    }

    public void setGridNumber(int gridNumber) {
        this.gridNumber = gridNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColNumber() {
        return colNumber;
    }

    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        cellField.setText(String.valueOf(value));
    }
    
    public String printCell(){
        return ("Grid: " + gridNumber + " Row: " + rowNumber + 
                " Col:" + colNumber + " Value: " + value);
    }

    /** 
     * Returns the JTextField.
     */
    public JTextField getTextField() {
        return cellField;
    }

    public void setNotSolvedValues(ArrayList<Integer> valuesNotSolved) {
        this.valuesNotSolved = valuesNotSolved;
    }
 
    public ArrayList<Integer> getUnsolvedValues() {
        return valuesNotSolved;
    }
    
    public void clearUnsolvedValues(){
        valuesNotSolved.clear();
    }
    
    public String getValueInCell(){
        if( cellField.getText() != null && !cellField.getText().equals("")){
            return cellField.getText();
        }
        else{
            return "";
        }
    }
}
