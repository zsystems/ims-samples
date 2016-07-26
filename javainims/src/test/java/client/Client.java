package client;

import java.io.ByteArrayOutputStream;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import com.ibm.ims.connect.ApiProperties;
import com.ibm.ims.connect.Connection;
import com.ibm.ims.connect.ConnectionFactory;
import com.ibm.ims.connect.ImsConnectApiException;
import com.ibm.ims.connect.InputMessage;
import com.ibm.ims.connect.OutputMessage;
import com.ibm.ims.connect.TmInteraction;
import com.ibm.ims.dli.conversion.util.ConversionUtils;

public class Client {

	String host, datastore, tranName, racfId, racfGroupName, clientId;
	int port;
	private static final String CODEPAGE = "cp037";
	static Vector<byte[]> byteArray = new Vector<byte[]>();

	public static void main(String[] args) throws Exception {


		Client client = new Client("INSUR01 ", "9.nn.nnn.nnn", "IMDO", 8890, "PAUL", "SYS1", "CLIENX1");

		//create input message		
		//ACTION
		client.appendString("get", 4);

		//CUSTNO
		client.appendInt(3);

		//CUST_STREET
		client.appendString("Rustic Place", 25);

		//CUST_CITY
		client.appendString("Palo Alto", 13);

		//CUST_STATE
		client.appendString("CA", 2);

		//CUST_ZIPCODE
		client.appendString("95002", 5);

		client.invoke();

	}



	/**
	 * Pad the string to reach the desired length
	 * @param value
	 * @param length
	 * @return
	 */
	public String padRight(String value, int length) {

		if(value.length()>length) {

			String shorthenedString = value.substring(0, length);
			return shorthenedString;

		}
		return String.format("%1$-" + length + "s", value);  
	}


	/**
	 * 
	 * @param tranName 8 character transaction name
	 * @param host the IP address of the z/OS system
	 * @param datastore the IMS datastore name
	 * @param port the IMS Connect port
	 * @param racfId 8 character RACFID
	 * @param racfGroupName 8 character RACF GROUP ID
	 * @param clientId 8 character IMS Connect client ID
	 */

	public Client(String tranName, String host, String datastore, int port, String racfId, String racfGroupName, String clientId) {

		this.tranName=tranName;
		this.host=host;
		this.port=port;
		this.datastore=datastore;
		this.racfId=racfId;
		this.racfGroupName=racfGroupName;
		this.clientId=clientId;

	}

	/**
	 * Invoke the transaction
	 * @throws ImsConnectApiException
	 * @throws Exception
	 */
	public void invoke() throws ImsConnectApiException, Exception {

		Connection myConnection = null;
		TmInteraction myTmInteraction = null; 
		ConnectionFactory myConnectionFactory = new ConnectionFactory();
		myConnectionFactory.setHostName(host);
		myConnectionFactory.setPortNumber(port); 
		myConnectionFactory.setUseSslConnection(false);
		myConnection = myConnectionFactory.getConnection();
		myConnection.setClientId(clientId); 
		myTmInteraction = myConnection.createInteraction(); 

		//trancode with length 8
		myTmInteraction.setTrancode(tranName);
		myTmInteraction.setImsDatastoreName(datastore);
		myTmInteraction.setRacfUserId(racfId);
		myTmInteraction.setRacfGroupName(racfGroupName);

		myTmInteraction.setInputMessageDataSegmentsIncludeLlzzAndTrancode(ApiProperties.INPUT_MESSAGE_DATA_SEGMENTS_DO_NOT_INCLUDE_LLZZ_AND_TRANCODE);
		myTmInteraction.setImsConnectTimeout(ApiProperties.TIMEOUT_2_SECONDS);
		myTmInteraction.setInteractionTimeout(ApiProperties.TIMEOUT_2_SECONDS);
		myTmInteraction.setInteractionTypeDescription(ApiProperties.INTERACTION_TYPE_DESC_SENDRECV);
		myTmInteraction.setImsConnectCodepage(CODEPAGE); 

		// get InputMessage instance from myTMInteraction
		InputMessage imsConnectInputMessage = myTmInteraction.getInputMessage();

		// Populate InputMessage object with input byte array
		imsConnectInputMessage.setInputMessageData(getByteMessage());

		// execute the transaction 
		myTmInteraction.execute(); 

		// get output from myTMInteraction
		OutputMessage outputMessage = myTmInteraction.getOutputMessage();

		// get data from outMsg as a string
		displayData(outputMessage.getDataAsString().replace((char)0x00, ' '), outputMessage);

		myConnection.disconnect();
	}

	/**
	 * Convert an int to a byte array and add to Vector of byte arrays
	 * @param value
	 */
	public void appendInt(int value) {

		byte[] intBytes = ConversionUtils.convertToByte(value);
		byteArray.addElement(intBytes);

	}

	public void appendShort(short value) {

		byte[] shortBytes = ConversionUtils.convertToByte(value);
		byteArray.addElement(shortBytes);

	}


	/**
	 * Convert String to byte array and add to Vector of byte arrays.  String will be padded to the length specified
	 * @param value the String to be converted
	 * @param length the length of the padded String
	 */
	public void appendString(String value, int length) {

		byte stringBytes[];

		//pad the data so it meets the length of the field
		String paddedString = padRight(value, length);

		try {
			stringBytes = paddedString.getBytes(CODEPAGE);
			byteArray.addElement(stringBytes);

		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		}

	}

	public void appendString(String value) {

		byte stringBytes[];

		try {
			stringBytes = value.getBytes(CODEPAGE);
			byteArray.addElement(stringBytes);

		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Creates a byte array from a Vector of byte array elements.
	 * The byte array will be sent to the specified IMS transaction. 
	 * @return byte array
	 * 
	 */
	public byte[] getByteMessage() {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		for(byte[] arrayElement : byteArray) {

			try {
				outputStream.write( arrayElement );

			} catch (IOException e) {

				System.out.println(e.getMessage());
			}

		}

		return outputStream.toByteArray();

	}


	private void displayData(String outputData, OutputMessage outputMessage) {

		byte [][] arrayOfData = outputMessage.getDataAsArrayOfByteArrays();

		for (int i = 0; i < arrayOfData.length; i++) {

			String s=null;
			try {
				s = new String(arrayOfData[i], CODEPAGE);
			} catch (UnsupportedEncodingException e) {


				System.out.println(e.getMessage());
			}

			System.out.println(s);

		}

		System.out.println("Done"); 
	}

}
