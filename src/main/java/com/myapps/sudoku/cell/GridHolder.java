/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapps.sudoku.cell;

import com.google.common.base.Preconditions;
import com.myapps.sudoku.utils.Triple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This file contains a Grid that describes the cells in a sudoku grid. 
 * A 9 x 9 matrix, that contains 9 3 x 3 grids. 
 * @author roserp
 */
public class GridHolder {
    
    public static Triple GRID_ONE_START = new Triple(1, 1);
    public static Triple GRID_TWO_START = new Triple(1, 4);
    public static Triple GRID_THREE_START = new Triple(3, 7);
    public static Triple GRID_FOUR_START = new Triple(4, 1);
    public static Triple GRID_FIVE_START = new Triple(4, 4);
    public static Triple GRID_SIX_START = new Triple(4, 7);
    public static Triple GRID_SEVEN_START = new Triple(7, 1);
    public static Triple GRID_EIGHT_START = new Triple(7, 4);
    public static Triple GRID_NINE_START = new Triple(7, 7);
    
   Map<String, SudokuCell> newGrids = new HashMap<>();
   
     public GridHolder() {
    }
    
    /**
     * Creates all the cells in the Sudoku puzzle. 81 cells each having a key
     * of row + col in string form like "11" for row1 col1 etc. The grid number
     * is from left to right top to bottom 1..9. The value is set to 0 by 
     * default which is invalid. Each cell has a list of unsolved values . 1..9
     * Each cell has a JTextField which will be used to display the grid of 
     * cells. 
     */
    public void createCells(){
        for( int row = 1; row < 10; row++){
            for( int col = 1; col < 10; col++){
                String key = buildKey(row, col);
                int grid = getGridForRowCol(key);
                SudokuCell cell = new SudokuCell(grid, row, col, 0, buildNotSolvedList());
                newGrids.put(key, cell);
            }
        }
        
    }
    
    /**
     * Given and row and column in int form this method builds a key in String
     * form. and uses this key for the key in the map. 
     * @param row
     * @param col
     * @return A String that is the key for a cell. 
     */
    public String buildKey( final int row , final int col){
        return( String.valueOf(row) +  String.valueOf(col));
    }

    /**
     * Gets the newGrids 
     * @return 
     */
    public Map<String, SudokuCell> getNewGrids() {
        return newGrids;
    }
    
    
    
    
        
    
     private int getGridForRowCol(final String key) {
        if( key.equals("11") || key.equals("12") || key.equals("13") ||
            key.equals("21") || key.equals("22") || key.equals("24") ||
            key.equals("31") || key.equals("32") || key.equals("33"))
            return 1;
        if( key.equals("14") || key.equals("15") || key.equals("16") ||
            key.equals("24") || key.equals("25") || key.equals("26") ||
            key.equals("34") || key.equals("35") || key.equals("36"))
            return 2;    
        if( key.equals("17") || key.equals("18") || key.equals("16") ||
            key.equals("27") || key.equals("28") || key.equals("29") ||
            key.equals("37") || key.equals("38") || key.equals("39"))
            return 3;    
        if( key.equals("41") || key.equals("42") || key.equals("43") ||
            key.equals("51") || key.equals("52") || key.equals("53") ||
            key.equals("61") || key.equals("62") || key.equals("63"))
            return 4;    
        if( key.equals("44") || key.equals("45") || key.equals("46") ||
            key.equals("54") || key.equals("55") || key.equals("56") ||
            key.equals("64") || key.equals("65") || key.equals("66"))
            return 5;
        if( key.equals("47") || key.equals("48") || key.equals("49") ||
            key.equals("57") || key.equals("58") || key.equals("59") ||
            key.equals("67") || key.equals("68") || key.equals("66"))
            return 6;
        if( key.equals("71") || key.equals("72") || key.equals("73") ||
            key.equals("81") || key.equals("82") || key.equals("83") ||
            key.equals("91") || key.equals("92") || key.equals("93"))
            return 7;    
        if( key.equals("74") || key.equals("75") || key.equals("76") ||
            key.equals("84") || key.equals("85") || key.equals("86") ||
            key.equals("84") || key.equals("85") || key.equals("86"))
            return 8;
        if( key.equals("77") || key.equals("78") || key.equals("79") ||
            key.equals("87") || key.equals("88") || key.equals("89") ||
            key.equals("97") || key.equals("98") || key.equals("99"))
            return 9;
        //ll never get here. 
        return 0;
    }

     /**
      * Builds a list of all 9 possible values for each cell. This will be 
      * reduced each time a value is set. 
      * @return 
      */
    private ArrayList<Integer> buildNotSolvedList() {
        ArrayList<Integer> notSolvedValues = new ArrayList<>();
        for( int i = 1; i < 10; i++){
            notSolvedValues.add(i);
        }
        return notSolvedValues;
    }
    
} //  end of class Grid Holder.
