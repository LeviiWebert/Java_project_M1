package service;

public class DateBDD {
	private String dateBDDString;
	public DateBDD(int year,int month,int day) {
		dateBDDString = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
	}
	
	
	public String getDateBDD() {
		return dateBDDString;
	}
	
	
	
}
