package customer.info;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import policy.info.AutoPolicy;
import policy.info.HousePolicy;

public class CustomerService {

	public Customer getCustomerInfo(int customerNumber, Connection conn) {

		Customer customer = null;
		PreparedStatement customerStatement = null;
		PreparedStatement autoPolicyStatement = null;
		PreparedStatement housePolicyStatement = null;

		try { 

			customerStatement = conn.prepareStatement("SELECT FIRSTNAME, LASTNAME, CUST_STREET, CUST_CITY, CUST_STATE, CUST_ZIPCODE, DATEOFBIRTH FROM INSURPCB.CUSTOMER WHERE CUSTOMERNUMBER = ?");
			autoPolicyStatement = conn.prepareStatement("SELECT POLICYNUMBER, CAR_MAKE, CAR_MODEL, CAR_MANUFACTUREDATE, CAR_REGNUMBER, CAR_DRIVERNAME FROM INSURPCB.POLICY WHERE CUSTOMER_CUSTNO = ? and POLICYTYPE='A'");
			housePolicyStatement = conn.prepareStatement("SELECT POLICYNUMBER, HOMEPROPERTYTYPE, HOMEBEDROOMS, HOMEHOUSEVALUE, HOME_STREET, HOME_ZIPCODE FROM INSURPCB.POLICY WHERE CUSTOMER_CUSTNO = ? and POLICYTYPE='H'");

			customerStatement.setInt(1, customerNumber);
			autoPolicyStatement.setInt(1, customerNumber);
			housePolicyStatement.setInt(1, customerNumber);

			ResultSet customerRS = customerStatement.executeQuery();
			if(customerRS.next()){

				customer = new Customer();
				customer.setFirstName(customerRS.getString("FIRSTNAME").trim());
				customer.setLastName(customerRS.getString("LASTNAME").trim());
				customer.setStreet(customerRS.getString("CUST_STREET").trim());
				customer.setCity(customerRS.getString("CUST_CITY").trim());
				customer.setState(customerRS.getString("CUST_STATE").trim());
				customer.setZipCode(customerRS.getString("CUST_ZIPCODE").trim());
				customer.setDateOfBirth(customerRS.getString("DATEOFBIRTH").trim());

				customerRS.close();
				customerStatement.close();

				//Collect House Policy Information
				ResultSet houseRS = housePolicyStatement.executeQuery();
				while(houseRS.next()){
					HousePolicy housePolicy = new HousePolicy();
					housePolicy.setPolicyNumber(houseRS.getInt("POLICYNUMBER"));
					housePolicy.setPropertyType(houseRS.getString("HOMEPROPERTYTYPE").trim());
					housePolicy.setNumberOfBedrooms(houseRS.getShort("HOMEBEDROOMS"));
					housePolicy.setValue(houseRS.getInt("HOMEHOUSEVALUE"));
					housePolicy.setStreet(houseRS.getString("HOME_STREET").trim());
					housePolicy.setZipCode(houseRS.getString("HOME_ZIPCODE").trim());
					customer.addHousePolicy(housePolicy);
				}
				houseRS.close();
				housePolicyStatement.close();

				//Collect Auto Policy Information
				ResultSet autoRS = autoPolicyStatement.executeQuery();
				while(autoRS.next()){
					AutoPolicy autoPolicy = new AutoPolicy();
					autoPolicy.setPolicyNumber(autoRS.getInt("POLICYNUMBER"));
					autoPolicy.setMake(autoRS.getString("CAR_MAKE").trim());
					autoPolicy.setModel(autoRS.getString("CAR_MODEL").trim());
					autoPolicy.setManufactureDate(autoRS.getString("CAR_MANUFACTUREDATE").trim());
					autoPolicy.setRegistrationNumber(autoRS.getString("CAR_REGNUMBER").trim());
					autoPolicy.setDriverName(autoRS.getString("CAR_DRIVERNAME").trim());
					customer.addAutoPolicy(autoPolicy);
				}
				autoRS.close();
				autoPolicyStatement.close();
			}

		}
		
		catch(Exception e) {

			System.out.println(e.getMessage());
		}
		
		finally {

			try { 
				customerStatement.close();
			}
			catch(Exception e) {

				System.out.println(e.getMessage());
			}
			try { 
				autoPolicyStatement.close();
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
			try { 
				housePolicyStatement.close();
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}

		}

		return customer;
	}
}
