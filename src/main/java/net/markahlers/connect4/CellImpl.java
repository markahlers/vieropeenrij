/**
 * 
 */
package net.markahlers.connect4;

/**
 * @author mark
 *
 */
public class CellImpl implements Cell {

	CellValue cellValue;
	int column;
	int row;
	
	/**
	 * Constructor, sets the initial value to EMPTY.
	 */
	public CellImpl(int row, int column) {
		this.row = row;
		this.column = column;
		this.cellValue = CellValue.EMPTY;
	}
	
	
	@Override
	public void setCellValue(CellValue cellValue) {
		this.cellValue = cellValue;		
	}

	@Override
	public CellValue getCellValue() {
		return this.cellValue;
	}

	@Override
	public void clear() {
		this.cellValue = CellValue.EMPTY;
	}

	@Override
	public void setRowLocation(int row) {
		this.row = row;
	}

	@Override
	public void setColumnLocation(int column) {
		this.column = column;
	}

	@Override
	public boolean isEmpty() {
		return (this.cellValue == CellValue.EMPTY);			
	}


}
