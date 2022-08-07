package validation;

import org.apache.commons.validator.routines.InetAddressValidator;

import bean.Client;

public class ClientValidation extends UserValidation{
	private static final String REGEX_CHECK_ROOT = "[a-zA-Z0-9_\\\\]{1,}[a-zA-Z0-9_]$";
	
	public boolean check(Client client) {
		return super.check(client) && checkRoot(client.getRoot()) && checkDomen(client.getDomen());
	}
	
	private boolean checkRoot(String value){
		return super.checkRegex(value, REGEX_CHECK_ROOT);
	}
	
	private boolean checkDomen(String value) {
		return InetAddressValidator.getInstance().isValidInet6Address(value);
	}
}
