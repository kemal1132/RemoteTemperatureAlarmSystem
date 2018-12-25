package ServerAndClient;

import java.io.Serializable;

public class Record implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String recordTime;
	private int plantId;
	private int temperature;
	public Record(String recordTime, int plantid, int temperature){
		this.recordTime=recordTime;
		this.plantId=plantid;
		this.temperature=temperature;
	}
	public String toString(){
		return recordTime+" - "+plantId+" - "+temperature;
	}
	public int getTemperature() {
		return temperature;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public int getPlantId() {
		return plantId;
	}
}
