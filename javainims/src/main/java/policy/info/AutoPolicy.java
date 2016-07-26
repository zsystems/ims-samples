package policy.info;

public class AutoPolicy {
	
	private String make;
	private String model;
	private String manufactureDate;
	private String registrationNumber;
	private String driverName;
	private int policyNumber;
	
	public int getPolicyNumber() {
		return policyNumber;
	}
	
	public void setPolicyNumber(int policyNumber) {
		this.policyNumber = policyNumber;
	}
	
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getManufactureDate() {
		return manufactureDate;
	}
	public void setManufactureDate(String manufactureDate) {
		this.manufactureDate = manufactureDate;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
}
