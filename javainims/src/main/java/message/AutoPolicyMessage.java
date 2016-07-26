package message;

import com.ibm.ims.application.IMSFieldMessage;
import com.ibm.ims.base.DLITypeInfo;

public class AutoPolicyMessage extends IMSFieldMessage {
	private static final long serialVersionUID = 23432884;

	static DLITypeInfo[] fieldInfo = {

			new DLITypeInfo("POLICYNUMBER", DLITypeInfo.INTEGER, 1, 4),			
			new DLITypeInfo("CAR_MAKE", DLITypeInfo.CHAR, 5, 15),
			new DLITypeInfo("CAR_MODEL", DLITypeInfo.CHAR, 20, 15),
			new DLITypeInfo("CAR_MANUFACTUREDATE", DLITypeInfo.CHAR, 35, 10),	        
			new DLITypeInfo("CAR_REGNUMBER", DLITypeInfo.CHAR, 45, 7),
			new DLITypeInfo("CAR_DRIVERNAME", DLITypeInfo.CHAR, 52, 30)


	};

	public AutoPolicyMessage() {  
		super(fieldInfo, 81, false);                   
	}



}
