package system;

public class Employee {
	private String fName;
	private String lName;
	private String id;
	private int workdays;
	private double ratePerDay;
	private double grossPay;
	private double netPay;
	private int absences;
	
	public void setfName(String fName) {
		this.fName = fName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setWorkdays(int workdays) {
		this.workdays = workdays;
	}

	public void setRatePerDay(double ratePerDay) {
		this.ratePerDay = ratePerDay;
	}

	public void setGrossPay(double grossPay) {
		this.grossPay = grossPay;
	}

	public void setNetPay(double netPay) {
		this.netPay = netPay;
	}

	public void setAbsences(int absences) {
		this.absences = absences;
	}
	
	public Employee(String id, String fName, String lName, int workDays, double ratePerDay, double grossPay,
			double netPay, int absences) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.id = id;
		this.workdays = workDays;
		this.ratePerDay = ratePerDay;
		this.grossPay = grossPay;
		this.netPay = netPay;
		this.absences = absences;
	}
	
	public int getAbsences() {
		return absences;
	}
	
	public String getfName() {
		return fName;
	}

	public String getlName() {
		return lName;
	}

	public String getId() {
		return id;
	}

	public int getWorkdays() {
		return workdays;
	}

	public double getRatePerDay() {
		return ratePerDay;
	}

	public double getGrossPay() {
		return grossPay;
	}

	public double getNetPay() {
		return netPay;
	}
}
