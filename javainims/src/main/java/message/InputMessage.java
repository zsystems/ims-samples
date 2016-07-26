package message;

import com.ibm.ims.application.IMSFieldMessage;
import com.ibm.ims.base.DLITypeInfo;

public class InputMessage extends IMSFieldMessage {

	private static final long serialVersionUID = 1L;
	static DLITypeInfo[] fieldInfo = 
		{
				//ACTION for our example can be 'get ' or 'put '
				new DLITypeInfo("ACTION", DLITypeInfo.CHAR, 1, 4),
				new DLITypeInfo("CUSTNO", DLITypeInfo.INTEGER, 5, 4),
				new DLITypeInfo("CUST_STREET", DLITypeInfo.CHAR, 9, 25),
				new DLITypeInfo("CUST_CITY", DLITypeInfo.CHAR, 34, 13),
				new DLITypeInfo("CUST_STATE", DLITypeInfo.CHAR, 47, 2),
				new DLITypeInfo("CUST_ZIPCODE", DLITypeInfo.CHAR, 49, 5)

		};  

	/**
	 * Required no arguments constructor
	 */
	public InputMessage()
	{ 
		super(fieldInfo, 53, false); 
	}

}
