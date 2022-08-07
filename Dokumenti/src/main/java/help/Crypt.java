package help;

import org.apache.commons.codec.digest.DigestUtils;

public class Crypt {
	public String sha256(String password) {
		return DigestUtils.sha256Hex(password);
	}
}
