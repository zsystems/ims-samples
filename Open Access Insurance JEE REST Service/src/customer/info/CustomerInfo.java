package customer.info;

import java.util.ArrayList;
import java.util.List;

import policy.info.AutoPolicy;
import policy.info.HousePolicy;

public class CustomerInfo {
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String street;
	private String city;
	private String state;
	private String zipCode;
	private List<HousePolicy> housePolicies;
	private List<AutoPolicy> autoPolicies;
	
	public List<AutoPolicy> getAutoPolicies() {
		return autoPolicies;
	}
	public void setAutoPolicies(List<AutoPolicy> autoPolicies) {
		this.autoPolicies = autoPolicies;
	}
	public void addAutoPolicy(AutoPolicy autoPolicy) {
		if(autoPolicies == null) {
			autoPolicies = new ArrayList<>();
		}
		
		autoPolicies.add(autoPolicy);
	}
	public List<HousePolicy> getHousePolicies() {
		return housePolicies;
	}
	public void setHousePolicies(List<HousePolicy> housePolicies) {
		this.housePolicies = housePolicies;
	}
	public void addHousePolicy(HousePolicy housePolicy) {
		if(housePolicies == null) {
			housePolicies = new ArrayList<>();
		}
		
		housePolicies.add(housePolicy);
	}
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
}
