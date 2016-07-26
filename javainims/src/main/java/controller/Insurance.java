package controller;

import java.sql.Connection;
import java.util.List;

import com.ibm.ims.dli.DLIException;
import com.ibm.ims.dli.tm.Application;
import com.ibm.ims.dli.tm.ApplicationFactory;
import com.ibm.ims.dli.tm.IOMessage;
import com.ibm.ims.dli.tm.MessageQueue;
import com.ibm.ims.dli.tm.Transaction;
import com.ibm.ims.jdbc.IMSDataSource;

import customer.info.Customer;
import customer.info.CustomerService;
import policy.info.AutoPolicy;
import policy.info.HousePolicy;


public class Insurance {

	public static void main(String[] args) {

		System.out.println("Controller called");

		CustomerService customerService = new CustomerService();
		Connection conn = null;

		//Application is used to get a Transaction object
		Application app = ApplicationFactory.createApplication();

		//Transaction is primarily used for commit or rollback calls
		Transaction tran = app.getTransaction();

		//Get a handle to the MessageQueue object for sending and receiving 
		//messages to and from the IMS message queue
		MessageQueue messageQueue = app.getMessageQueue();

		IOMessage inputMessage = null;
		IOMessage customerOutputMessage = null;
		IOMessage autoPolicyMessage = null;
		IOMessage housePolicyMessage = null;

		try {

			//initialize the input and output messages to the IOMessage object
			inputMessage = app.getIOMessage("class://message.InputMessage");
			customerOutputMessage = app.getIOMessage("class://message.CustomerOutputMessage");
			autoPolicyMessage = app.getIOMessage("class://message.AutoPolicyMessage");					
			housePolicyMessage = app.getIOMessage("class://message.HousePolicyMessage");

			//get a InputMessage message from the queue, if there is one
			while (messageQueue.getUnique(inputMessage)) {

				//get the customer number from the InputMessage
				int customerNumber = inputMessage.getInt("CUSTNO");

				//get the action from the InputMessage
				String action = inputMessage.getString("ACTION");

				//connect to the database
				IMSDataSource dataSource = new IMSDataSource();
				dataSource.setDriverType(IMSDataSource.DRIVER_TYPE_2);
				dataSource.setDatastoreName("IMDO");
				dataSource.setDatabaseName("INSUR01");
				conn = dataSource.getConnection();
				conn.setAutoCommit(false);

				//update customer information
				if(action.trim().equalsIgnoreCase("put")) {

					customerOutputMessage.setString("ErrorMessage", 
							"Put operation not yet implemented");
					messageQueue.insert(customerOutputMessage, MessageQueue.DEFAULT_DESTINATION);


				}
				
				//get customer information
				if(action.trim().equalsIgnoreCase("get")) {

					//query the database passing in the customer number, and the type-2 connection 
					//if getConnection() gets an exception this code is not reached
					Customer customerInfo = customerService.getCustomerInfo(customerNumber, conn);

					//if a customer is found then return the information
					if(customerInfo != null) {

						int numAutoPolicy = customerInfo.getAutoPolicies().size();
						int numHousePolicy = customerInfo.getHousePolicies().size();

						customerOutputMessage.setString("FIRSTNAME", customerInfo.getFirstName());
						customerOutputMessage.setString("LASTNAME", customerInfo.getLastName());					
						customerOutputMessage.setString("CUST_STREET", customerInfo.getStreet());
						customerOutputMessage.setString("CUST_CITY", customerInfo.getCity());
						customerOutputMessage.setString("CUST_STATE", customerInfo.getState());
						customerOutputMessage.setString("CUST_ZIPCODE", customerInfo.getZipCode());
						customerOutputMessage.setString("DATEOFBIRTH", customerInfo.getDateOfBirth());

						//set the number of auto and house policies
						customerOutputMessage.setInt("NUM_AUTO", numAutoPolicy);
						customerOutputMessage.setInt("NUM_HOUSE", numHousePolicy);

						//insert the populated CustomerOutputMessage into message queue
						messageQueue.insert(customerOutputMessage, MessageQueue.DEFAULT_DESTINATION);

						//loop thru the list of auto policies for the Customer
						//populate the AutoPolicyMessage and insert into message queue
						List<AutoPolicy> autoPolicies = customerInfo.getAutoPolicies();
						for(AutoPolicy autoPolicy : autoPolicies) {

							autoPolicyMessage.setInt("POLICYNUMBER", autoPolicy.getPolicyNumber());
							autoPolicyMessage.setString("CAR_MAKE", autoPolicy.getMake());
							autoPolicyMessage.setString("CAR_MODEL", autoPolicy.getModel());
							autoPolicyMessage.setString("CAR_MANUFACTUREDATE", autoPolicy.getManufactureDate());
							autoPolicyMessage.setString("CAR_REGNUMBER", autoPolicy.getRegistrationNumber());
							autoPolicyMessage.setString("CAR_DRIVERNAME", autoPolicy.getDriverName());

							//insert each AutoPolicyMessage into queue. Each message is a segment.
							messageQueue.insert(autoPolicyMessage, MessageQueue.DEFAULT_DESTINATION);
						}

						//loop thru the list of house policies for the Customer
						//populate the HousePolicyMessage and insert into message queue
						List<HousePolicy> housePolicies = customerInfo.getHousePolicies();
						for(HousePolicy housePolicy : housePolicies) {

							housePolicyMessage.setInt("POLICYNUMBER", housePolicy.getPolicyNumber());
							housePolicyMessage.setShort("HOMEBEDROOMS", housePolicy.getNumberOfBedrooms());
							housePolicyMessage.setInt("HOMEHOUSEVALUE", housePolicy.getValue());
							housePolicyMessage.setString("HOMEPROPERTYTYPE", housePolicy.getPropertyType());
							housePolicyMessage.setString("HOME_STREET", housePolicy.getStreet());
							housePolicyMessage.setString("HOME_ZIPCODE", housePolicy.getZipCode());    

							//insert each HousePolicyMessage into queue. Each message is a segment.
							messageQueue.insert(housePolicyMessage, MessageQueue.DEFAULT_DESTINATION);
						}

						conn.close();
						tran.commit();


					} //end if customer not null

					//if no customer is found
					else {

						customerOutputMessage.setString("ErrorMessage", 
								"No Customer has been found with a customer number of " + customerNumber);
						messageQueue.insert(customerOutputMessage, MessageQueue.DEFAULT_DESTINATION);

					}
				} //end if action=get

			} //end getUnique while loop

		} catch (Exception e) {

			System.out.println(e.getMessage());

			try {

				conn.close();

			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}

			try {

				if(e.getMessage().length()>500){
					customerOutputMessage.setString("ErrorMessage", e.getMessage().substring(0, 500));
				} else {
					customerOutputMessage.setString("ErrorMessage", e.getMessage());
				}	
				messageQueue.insert(customerOutputMessage, MessageQueue.DEFAULT_DESTINATION);

			} catch (DLIException  e1) {
				System.out.println("DLIException encountered");
				System.out.println(e1.getMessage());
			}

		}
		finally {

			app.end();

		}

	}
}