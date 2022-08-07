package validation;

import java.util.regex.Pattern;

import bean.User;

public abstract class UserValidation implements IValidation{
	private static final String REGEX_USERNAME = "^[a-zA-Z0-9_]{2,45}$";
	private static final String REGEX_PASSWORD = ".{6,45}";
	private static final String REGEX_ROLE = "^(adminS|adminD|client)$";
	
	public boolean check(User user) {
		return checkRegex(user.getUsername(), REGEX_USERNAME)
				&& checkRegex(user.getPassword(), REGEX_PASSWORD)
				&& checkRegex(user.getRole(), REGEX_ROLE);
	}
	
	public boolean checkRegex(String value, String pattern){
		if (value == null)
			return false;
		
		Pattern REGEX_PATTERN = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		return REGEX_PATTERN.matcher(value).find();
	}
}
