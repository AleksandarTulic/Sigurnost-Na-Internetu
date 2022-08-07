package validation;

import bean.User;

public interface IValidation {
	public boolean check(User user);
	
	public boolean checkRegex(String value, String pattern);
}
