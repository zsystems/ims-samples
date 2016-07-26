package message;

import com.ibm.ims.application.IMSFieldMessage;
import com.ibm.ims.base.DLITypeInfo;

public class HousePolicyMessage extends IMSFieldMessage {
	private static final long serialVersionUID = 23432884;

	static DLITypeInfo[] fieldInfo = {

			new DLITypeInfo("POLICYNUMBER", DLITypeInfo.INTEGER, 1, 4),	        
			new DLITypeInfo("HOMEPROPERTYTYPE", DLITypeInfo.CHAR, 5, 15),	        
			new DLITypeInfo("HOMEBEDROOMS", DLITypeInfo.SMALLINT, 20, 2),
			new DLITypeInfo("HOMEHOUSEVALUE", DLITypeInfo.INTEGER, 22, 4),
			new DLITypeInfo("HOME_STREET", DLITypeInfo.CHAR, 26, 25),
			new DLITypeInfo("HOME_ZIPCODE", DLITypeInfo.CHAR, 51, 5)

	};

	public HousePolicyMessage() {  
		super(fieldInfo, 55, false);                   
	}

}
