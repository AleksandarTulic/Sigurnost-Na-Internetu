package help;

import java.util.logging.Level;

import org.apache.commons.codec.digest.DigestUtils;

import logger.MyLogger;

public class Crypt {
	public String sha256(String password) {
		String result = password;
		
		try {
			result = DigestUtils.sha256Hex(password);
		}
		catch (Exception e) {
			result = password;
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}
		
		return result;
	}
}
