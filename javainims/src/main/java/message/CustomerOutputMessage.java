package message;

import com.ibm.ims.application.IMSFieldMessage;
import com.ibm.ims.base.DLITypeInfo;

public class CustomerOutputMessage extends IMSFieldMessage {
	private static final long serialVersionUID = 23432884;

	static DLITypeInfo[] fieldInfo = {

			new DLITypeInfo("CUSTNO", DLITypeInfo.INTEGER, 1, 4),
			new DLITypeInfo("FIRSTNAME", DLITypeInfo.CHAR, 5, 10),
			new DLITypeInfo("LASTNAME", DLITypeInfo.CHAR, 15, 20),
			new DLITypeInfo("DATEOFBIRTH", DLITypeInfo.CHAR, 35, 10),
			new DLITypeInfo("CUST_STREET", DLITypeInfo.CHAR, 45, 25),
			new DLITypeInfo("CUST_CITY", DLITypeInfo.CHAR, 70, 13),
			new DLITypeInfo("CUST_STATE", DLITypeInfo.CHAR, 83, 2),
			new DLITypeInfo("CUST_ZIPCODE", DLITypeInfo.CHAR, 85, 5),
			new DLITypeInfo("NUM_HOUSE", DLITypeInfo.INTEGER, 90, 4),
			new DLITypeInfo("NUM_AUTO", DLITypeInfo.INTEGER, 94, 4),
			new DLITypeInfo("ErrorMessage", DLITypeInfo.CHAR, 98, 500)	        

	};

	public CustomerOutputMessage() {  
		super(fieldInfo, 597, false);                   
	}

}
