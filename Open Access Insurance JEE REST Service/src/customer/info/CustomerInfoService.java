package customer.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import policy.info.AutoPolicy;
import policy.info.HousePolicy;

@Stateless
@Path("/customer")
public class CustomerInfoService {
	@Resource(name="INSURANCEDB")
	DataSource insuranceDB;
	
	@Path("/info")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public CustomerInfo getCustomerInfo(@QueryParam("customerNumber") int customerNumber) {
		CustomerInfo customerInfo = new CustomerInfo();
		try {
			Connection conn = insuranceDB.getConnection();
			PreparedStatement customerStatement = conn.prepareStatement("SELECT FIRSTNAME, LASTNAME, CUST_STREET, CUST_CITY, CUST_STATE, CUST_ZIPCODE, DATEOFBIRTH FROM INSURPCB.CUSTOMER WHERE CUSTOMERNUMBER = ?");
			PreparedStatement autoPolicyStatement = conn.prepareStatement("SELECT POLICYNUMBER, CAR_MAKE, CAR_MODEL, CAR_MANUFACTUREDATE, CAR_REGNUMBER, CAR_DRIVERNAME FROM INSURPCB.POLICY WHERE CUSTOMER_CUSTNO = ? and POLICYTYPE='A'");
			PreparedStatement housePolicyStatement = conn.prepareStatement("SELECT POLICYNUMBER, HOMEPROPERTYTYPE, HOMEBEDROOMS, HOMEHOUSEVALUE, HOME_STREET, HOME_ZIPCODE FROM INSURPCB.POLICY WHERE CUSTOMER_CUSTNO = ? and POLICYTYPE='H'");
			
			customerStatement.setInt(1, customerNumber);
			autoPolicyStatement.setInt(1, customerNumber);
			housePolicyStatement.setInt(1, customerNumber);
			
			ResultSet customerRS = customerStatement.executeQuery();
			if(customerRS.next()){
				customerInfo.setFirstName(customerRS.getString(1).trim());
				customerInfo.setLastName(customerRS.getString(2).trim());
				customerInfo.setStreet(customerRS.getString(3).trim());
				customerInfo.setCity(customerRS.getString(4).trim());
				customerInfo.setState(customerRS.getString(5).trim());
				customerInfo.setZipCode(customerRS.getString(6).trim());
				customerInfo.setDateOfBirth(customerRS.getString(7).trim());
				customerRS.close();
				customerStatement.close();
				
				//Collect House Policy Information
				ResultSet houseRS = housePolicyStatement.executeQuery();
				while(houseRS.next()){
					HousePolicy housePolicy = new HousePolicy();
					housePolicy.setPolicyNumber(houseRS.getInt(1));
					housePolicy.setPropertyType(houseRS.getString(2).trim());
					housePolicy.setNumberOfBedrooms(houseRS.getShort(3));
					housePolicy.setValue(houseRS.getInt(4));
					housePolicy.setStreet(houseRS.getString(5).trim());
					housePolicy.setZipCode(houseRS.getString(6).trim());
					customerInfo.addHousePolicy(housePolicy);
				}
				houseRS.close();
				housePolicyStatement.close();
				
				//Collect Auto Policy Information
				ResultSet autoRS = autoPolicyStatement.executeQuery();
				while(autoRS.next()){
					AutoPolicy autoPolicy = new AutoPolicy();
					autoPolicy.setPolicyNumber(autoRS.getInt(1));
					autoPolicy.setMake(autoRS.getString(2).trim());
					autoPolicy.setModel(autoRS.getString(3).trim());
					autoPolicy.setManufactureDate(autoRS.getString(4).trim());
					autoPolicy.setRegistrationNumber(autoRS.getString(5).trim());
					autoPolicy.setDriverName(autoRS.getString(6).trim());
					customerInfo.addAutoPolicy(autoPolicy);
				}
				autoRS.close();
				autoPolicyStatement.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return customerInfo;
	}
}
