package ServerAndClient;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class RecordTabelModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int RecordTime_Col=0;
	private static final int PlantId_Col=1;
	private static final int Temp_Col=2;
	
	private String[] columnNames ={"Record Time", "Plant No", "Temperature"};
	
	private ArrayList<Record> RecordList;
	public RecordTabelModel(ArrayList<Record> records){
		RecordList=records;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return RecordList.size();
	}
	
	@Override
	public String getColumnName(int col){
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		Record temporary = RecordList.get(row);
		switch(col){
			case RecordTime_Col:
				return temporary.getRecordTime();
			case PlantId_Col:
				return temporary.getPlantId();
			case Temp_Col:
				return temporary.getTemperature();
			default:
				return "something is wrong";
		}
	}

}
