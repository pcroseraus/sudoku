/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapps.sudoku.main;

import com.myapps.sudoku.cell.GridHolder;
import com.myapps.sudoku.cell.SudokuCell;

import com.myapps.sudoku.utils.Triple;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author roserp
 */
public class SudokuMain implements ActionListener{

    GridHolder gridHolder = new GridHolder();
    static boolean solved = false;
    
    //Button to close the dialog and exit, 
    JButton closeButton = new JButton("Close");
    // Clicking the solve Button will begin the process of solving
    JButton solveButton = new JButton("Solve");
    // This is the frame that represents a 9 x 9 grid of JTextFields. 
    private static JFrame frame;
    
    public static void main(String[] args) {

        
        SudokuMain mainProg = new SudokuMain();
        //Contains the close button
        JPanel buttonPanel = mainProg.buildButtonPanel();
        //Contains an 9 x 9 grid of Suduko Cells JTextFields. 
        // This is all UI JPanel work. 
        JPanel sudukoPanel = mainProg.buildSudukoPanel();
        // Listener for the close button. 
        mainProg.addActionListener();
        frame = new JFrame("Suduko Solver");
        frame.getContentPane().setLayout(new BorderLayout());
        
        // Creates the Map of size 9 with key Grid Number value 3 x 3 cells. 
        // This is how we did this of old, Now the method is moved to the Grid
        // Holder 
        //mainProg.createCells();
        // Now we call the gridHolder to create the cells. This will also create
        // unsolved values. 
        mainProg.createCells();
        
        // The suduko Panel is a GridLayout of 9 x 9. This call fills the layout
        // with JTextFields from the Grid Holder. 
        mainProg.addCellsToGridHolder(sudukoPanel);
        // Now add Panels to the UI
        frame.getContentPane().add(sudukoPanel, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(400, 400);
        frame.setVisible(true);

        System.out.println("\n\n SOLVING..............\n\n");
        
    }
    /**
     * Creates a JPanel, with the GridLayout manager designed to hold 9 rows, 
     * and 9 columns of values that are described by a SudukoCell
     * @return JPanel only has layout manager , fields will be added later. 
     */
    private JPanel buildSudukoPanel() {
        JPanel sudukoPanel = new JPanel();
        sudukoPanel.setLayout(new GridLayout(9,9));
        return sudukoPanel;
    }
    
    /**
     * Accepts the sudukoPanel constructed earlier with a GridLayout of 9 rows,
     * and 9 columns and fills it with TextFields contained the GridHolder Map.
     * No values are filled in at this time.
     * MAKING THIS CHANGE TO TEST THE GIT SUPPORT IN NETBEANS. 
     * @param sudukoPanel 
     */
    public void addCellsToGridHolder(JPanel sudukoPanel){
        for( int row = 1; row <10; row++ ){
            for( int col = 1; col < 10; col++){
                //This is the new way
                //Build the key
                String key = gridHolder.buildKey(row, col);
                // Now get the Map of SudokuCells
                Map<String,SudokuCell> cells = gridHolder.getNewGrids();
                SudokuCell cell = cells.get(key);
                // Now add the Text Field to the sudukoPanel
                sudukoPanel.add(cell.getTextField());
            }
        }
    }
    /** 
     * Creates 81 empty cells. 
     */
    private void createCells(){
        gridHolder.createCells();
    }
    
    private void addActionListener() {
        solveButton.addActionListener(this);
        closeButton.addActionListener(this);
    }
    
    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(solveButton);
        panel.add(closeButton);
        return panel;
    }
    
    /**
     * will get every cell and if the value is > 0 this has been solved. 
     * @return 
     */
    public boolean checkSolution() {
        for(int row = 1; row < 10; row++ ){
            for (int cols = 1; cols < 10; cols++) {
                String key = gridHolder.buildKey(row, cols);
                SudokuCell cell = gridHolder.getNewGrids().get(key);
                if(cell.getValue() == 0){
                   return false;
                }
            }
        }
        return true;
    }

    /**
     * This will iterate over all the cells in the table, and attempt to solve
     * for each row.  By looking at the values in each row, and remove the 
     * value of unsolved for each row. 
     */
    public void solveForRows() {
        for (int row = 1; row < 10; row++) {
            List<SudokuCell> cells = getCellsForSingleRow(row);
            forEachValueFoundRemoveValue(cells);
            solveForCellsWhereOnlyOneCellInSetOfCellsCanHaveOnlyOneValue(cells);
        }
        
    }
    
    /**
     * accepts a row coordinate, then iterates over all the columns in this row
     * and gets the list of cells that comprise the row. 
     * @param row
     * @return List of cells in the the row. 
     */
    public List<SudokuCell> getCellsForSingleRow(int row){
        List<SudokuCell> rowCells = new ArrayList<>();
        for( int col = 1; col < 10; col++){
            String key = gridHolder.buildKey(row, col);
            SudokuCell cell = gridHolder.getNewGrids().get(key);
            rowCells.add(cell);
        }
        return rowCells;
    }
    
    /**
     * This method will work for columns or rows or grids. It is will accept the
     * cells and remove the value passed from those cells unsolved values. 
     * @param cells 
     */
    private void forEachValueFoundRemoveValue(final List<SudokuCell> cells ){
        for(SudokuCell cell : cells ){
            setAnyValueWhereOnlyOneResultIsPossible(cells);
            String value = cell.getTextField().getText();
            if( value != null && !value.equals("")){
                try{
                    int numericValue = Integer.parseInt(value);
                    //iterate over the each cell in the array and remove the value
                    // from unsolved values. 
                    removeValueFromNotSolvedList(cells, numericValue);
                    // now that the unsolved values list has been reduced check
                    // the size is 1 , then the value to be added to the value 
                    //ui component have been identified. 
                    setAnyValueWhereOnlyOneResultIsPossible(cells);
                }
                catch(NumberFormatException nfe){
                    // do nothing cell does not have a valid value. 
                }
            }
        }
    }
    
    private void setAnyValueWhereOnlyOneResultIsPossible(final List<SudokuCell> cells){
        for(SudokuCell cell: cells){
            if(cell.getValue() == 0 && cell.getUnsolvedValues().size() == 1){
                int value = cell.getUnsolvedValues().get(0);
                cell.setValue(value);
                cell.getTextField().setText(String.valueOf(value));
                cell.getTextField().repaint();
                // an added step is to clear the list of unsolved values
                cell.clearUnsolvedValues();
                System.out.println("cell text field is "  + cell.getTextField().getText());
            }
        }
    }
    
    private void removeValueFromNotSolvedList(final List<SudokuCell> cells, final int value){
        for(SudokuCell cell : cells ){
            cell.getUnsolvedValues().remove(new Integer(value));
        }
    }
    
    /**
     * This will iterate over all the columns in a sudoku grid. First get the
     * cells in the column. Pass the columns into a method that will check each
     * cell for a value greater than 0.  If a value greater than 0 is found, 
     * remove it from the list of unsolved for all cells in the column. 
     */
    public void solveForCols() {
        for (int col = 1; col < 10; col++) {
            List<SudokuCell> cells = getCellsForSingleCol(col);
            forEachValueFoundRemoveValue(cells);
            solveForCellsWhereOnlyOneCellInSetOfCellsCanHaveOnlyOneValue(cells);
        }
    }
    
    /**
     * accepts a col coordinate, then iterates over all the rows in this column
     * and gets the list of cells that comprise the column. 
     * @param row
     * @return List of cells in the the col. 
     */
    public List<SudokuCell> getCellsForSingleCol(int col){
        List<SudokuCell> colCells = new ArrayList<>();
        for( int row = 1; row < 10; row++){
            String key = gridHolder.buildKey(row, col);
            SudokuCell cell = gridHolder.getNewGrids().get(key);
            colCells.add(cell);
        }
        return colCells;
    }
    
    /**
     * This will iterate over all the cells in the table, and attempt to solve
     * for each row.  By looking at the values in each row, and remove the 
     * value of unsolved for each row. 
     */
    public void solveForGrids() {
            for( int grid = 1; grid < 10; grid++ )
            {
                List<SudokuCell> cells = getCellsForGrid(grid);
                forEachValueFoundRemoveValue(cells);
                solveForCellsWhereOnlyOneCellInSetOfCellsCanHaveOnlyOneValue(cells);
            }  
        }
        

    /** 
     * Each grid has cells in it 3 rows x 3 columns. return this. 
     * @param gridNumber
     * @return 
     */
    private List<SudokuCell> getCellsForGrid(int gridNumber){
        List<SudokuCell> cells = new ArrayList<>();
        
        switch(gridNumber){
            case 1:
                return getGridCells(GridHolder.GRID_ONE_START);
            case 2:
                return getGridCells(GridHolder.GRID_TWO_START);
            case 3:
                return getGridCells(GridHolder.GRID_THREE_START);
            case 4:
                return getGridCells(GridHolder.GRID_FOUR_START);
            case 5:
                return getGridCells(GridHolder.GRID_FIVE_START);
            case 6:
                return getGridCells(GridHolder.GRID_SIX_START);
            case 7:
                return getGridCells(GridHolder.GRID_SEVEN_START);
            case 8:
                return getGridCells(GridHolder.GRID_EIGHT_START);
            case 9:
                return getGridCells(GridHolder.GRID_NINE_START);
            default:
                break;
        }
        return cells;
    }
    
    /**
     * This will accept the startingIndex and get the 3 x 3 cells that make up
     * a grid. 
     * @param startingIndex
     * @param cells 
     */
    private List<SudokuCell> getGridCells(final Triple startingIndex){
        List<SudokuCell> cells = new ArrayList();
        int col = startingIndex.getCol();
        int row = startingIndex.getRow();
        for( int i = col ; i < col + 3;  i++){
            String key = gridHolder.buildKey(row, i);
            cells.add(gridHolder.getNewGrids().get(key));
            
        }
        row++;
        for( int i = col ; i < col + 3;  i++){
            String key = gridHolder.buildKey(row, i);
            cells.add(gridHolder.getNewGrids().get(key));
        }
        row++;
        for( int i = col ; i < col + 3;  i++){
            String key = gridHolder.buildKey(row, i);
            cells.add(gridHolder.getNewGrids().get(key));
        }
        return cells;      
    }
    
          
    /**
     * The panel has been constructed with a grid layout 9 x 9. Add each 
     * JTextField to a respective row in order. 
     * @param sudukoPanel 
     */
    public void addCellsToSudokuPanel(final JPanel sudukoPanel){
        for( int row = 1; row <10; row++ ){
            for( int col = 1; col < 10; col++){
                String key = gridHolder.buildKey(row, col);
                SudokuCell cell = gridHolder.getNewGrids().get(key);
                // Use the accessor method for the TextField in this cell and 
                // add it to the UI.
                sudukoPanel.add(cell.getTextField());
            }
        }
    }

    /**
     * Will print all of the values in the sudoku cells. 
     */
    public void printCells() {
        for( int row = 1; row < 10; row++){
            for( int col = 1; col < 10; col++){
                String key = gridHolder.buildKey(row, col);
                SudokuCell cell = gridHolder.getNewGrids().get(key);
                System.out.println("Value at row " + cell.getRowNumber() + " and col = " + 
                        cell.getColNumber() + " is " + cell.getValueInCell());
            }
        }
    }

    

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == closeButton)
        {
            // since there is only one listener
            System.out.println("SHUTTING DOWN.................");
            System.exit(0);
        }
        if(e.getSource() == solveButton){
            // First get the cells from the grid holder.  
            getDataFromUI();
            
        
        while (!checkSolution()) {
        //This should walk the cells and and insert into evaluate each rows 
        // values and remove from the unsolved list of each cell, any value 
        // found in these cells. 
        solveForRows();
        solveForCols();
        solveForGrids();
       }
            
        }
    }

    /** 
     * This method will inspect each JText Field and gets its value if there is
     * one. 
     */
    
    private void getDataFromUI() {
         for( int row = 1; row < 10; row++){
            for( int col = 1; col < 10; col++){
                String key = gridHolder.buildKey(row, col);
                SudokuCell cell = gridHolder.getNewGrids().get(key);
                String value = cell.getTextField().getText();
                if((value != null ) && (!value.equals(""))){
                    try{
                        int cellValue = Integer.parseInt(value);
                        cell.setValue(cellValue);
                        // since we have a value clear the unvolved values. 
                        cell.clearUnsolvedValues();
                    }catch( NumberFormatException nfe){
                        // DO nothing. 
                    }
                } 
            }
        }
        printCells();
    }

    /**
     * To solve the more complex sudukos , it is necessary to inspect each cell
     * in a set of cells 
     */
    private void solveForCellsWhereOnlyOneCellInSetOfCellsCanHaveOnlyOneValue(final List<SudokuCell> cells) {
        int occuranceCount = 0;
            // for each possible 1..9
            for( int i = 1; i < 10; i++){
                int valueToCheck = i;
                for( SudokuCell cell : cells){
                    if(cell.getUnsolvedValues().contains(valueToCheck)){
                    occuranceCount++; 
                    }
                    if(occuranceCount > 1){
                    // nothing to do here, except return occurance count to 0
                    occuranceCount = 0;
                    // break out of loop no sense evaluating past 2 entries. 
                    break;
                    }  
                }
                //visited each cell in the list if count == 1 , we have found 
                //a value to place in a cell.
                if( occuranceCount == 1){
                    // visit each cell and 
                    for( SudokuCell cell : cells){
                        if(cell.getUnsolvedValues().contains(valueToCheck)){
                            cell.setValue(valueToCheck);
                            cell.clearUnsolvedValues();       
                        }
                    } // end of cells looking for the existence of a valueToCheck
                } // end of if occuranceCont == 1
            }// end of checking each value
        
       
    }
    
}
