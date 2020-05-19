//Copyright 2018
package com.myapps.sudoku.utils;

import com.myapps.sudoku.cell.GridHolder;

/**
 *
 * @author roserp
 */
public class Triple {
    GridHolder gridHolder = new GridHolder();
    private int row;
    private int col;
    private String key;

    public Triple(int row, int col) {
        this.row = row;
        this.col = col;
        this.key = gridHolder.buildKey(row,col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getKey() {
        return key;
    }
    
    
    
}
