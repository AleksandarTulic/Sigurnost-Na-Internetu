package help;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.ByteMatrix;

import de.taimos.totp.TOTP;
import logger.MyLogger;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.logging.Level;

public class QR {
	public static final String SAVE_PATH = System.getProperty("catalina.home") + File.separator + "MY_DATA" + File.separator + "qr";
	
	private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

	public String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        char[] hexKey = Hex.encodeHex(bytes);
        return TOTP.getOTP(new String(hexKey));
    }

	private String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
        try {
            return "otpauth://totp/"
                    + URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

	private void createQRCode(String barCodeData, String filePath, int height, int width)
            throws WriterException, IOException {
        ByteMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE,
                width, height);
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            MatrixToImageWriter.writeToStream(matrix, "png", out);
        }
    }
	
	public String generateQR(String username) {
		String secretKey = "AAAAAAAAAAAAAAAAAAAAAAAAAA";
		try {
			secretKey = generateSecretKey();
	        String email = "test@gmail.com";
	        String companyName = "Korisnici";
	        String barCodeUrl = getGoogleAuthenticatorBarCode(secretKey, email, companyName);
	        //System.out.println(barCodeUrl);
	        createQRCode(barCodeUrl, SAVE_PATH + File.separator + username + ".png", 400, 400);
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}
		
		return secretKey;
	}
}
