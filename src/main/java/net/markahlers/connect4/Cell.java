/**
 * Interface for the Cell object.
 */
package net.markahlers.connect4;

/**
 * @author mark
 *
 */
public interface Cell {

	
	/**
	 * Sets the value of the cell
	 * 
	 * @param cellvalue
	 *            as int
	 */
	void setCellValue(CellValue cellValue);

	/**
	 * Returns the value of the cell
	 * 
	 * @return int value of the cell.
	 */
	CellValue getCellValue();

	/**
	 *  Clear the cell of any value 
	 */
	void clear();
	
	/**
	 * Sets the row this cell is on
	 * @param row
	 */
	void setRowLocation(int row);
	
	/**
	 * Sets the column this cell is on
	 * @param column
	 */
	void setColumnLocation(int column);

	/**
	 * Checks if the cell is empty or has a value other than empty.
	 * @return boolean true or false
	 */
	boolean isEmpty();

}
