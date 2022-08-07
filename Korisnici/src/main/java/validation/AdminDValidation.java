package validation;

import bean.AdminD;

public class AdminDValidation extends UserValidation{
	private static final String REGEX_CHECK_ROOT = "[a-zA-Z0-9_\\\\]{1,}[a-zA-Z0-9_]$";
	
	public boolean check(AdminD adminD) {
		return super.check(adminD) && checkRoot(adminD.getRoot());
	}
	
	private boolean checkRoot(String value) {
		return super.checkRegex(value, REGEX_CHECK_ROOT);
	}
}
