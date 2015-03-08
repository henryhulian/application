package com.rest.server.util;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.codec.binary.Base64;


public class RSAUtils {
	
	private static  final  String privateKey="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCB6KNhSiFr4NGXtTY/2Hz5jJ9+" +
			"te2IC2tRRHamVF2pZan7FYI7w2tzMf51WRUQsDHPzRvrQvVAXAObV8lr0prrqmUAifyP6L2Re1Ek" +
			"ZLb2CEJWyyEHAewUejOqyn8ZBUXumM2dmjTtYPt+WS2Qpyt2StEgt0THsMGqEYCVIRs6Dkw5d8OI" +
			"dsYncdCBq2JJZy7kRYZaZ+gE+brjGWhjjZHGnRsv6M73OHG849rgF9de3he7YLgiT6syYhQrKblu" +
			"9BSvST2lrdAxrXckm54j06mff9jeoKLJMMBSpHHbl9oLg5GhCgWHsCBhnBf1ZiQEzZ5REIJdg0bF" +
			"BEfPylWVYcQfAgMBAAECggEABqyUEECFvQwc2IV31wnhSdTesNzZ6j5j8hXpXpxA/534gKzXNwjQ" +
			"MWflIdREz8mtlXZ9M7YJ8ZeIm+eDsqVsTiU/8SolrqMPcjT6szr1Z2h/OARRmxGPNnIcy26B3yA1" +
			"Oo/hKHKuu29bHF+qhp8ADaDEY8DmqLe9C2bAy2ibBH16pimSiOTWaNFQb5ZLxie2u6R7sQfFYS7Q" +
			"u42X+7t/2se6VB1cCw850szp4keALe9iV36YCgepvu8lOZ7RZ1PaZn+03s+F96V3OkiGwtDEwt9E" +
			"7xAyVbh0kGHeUn7NjVLLhoErI0KYAZwzzExgHOmkOfJa7/712Jcd7Gly09ibIQKBgQDQv5TdJswK" +
			"Nw53W+FLWBo9mBqLd6J0CtWkfH2YfyCaxseGkrC4sE6rXDbI19Uspnz/CIVEkn+Ry9so+dUcnCAX" +
			"CoDkHjnQZW5hRa9hOzDqo+n6UXSKNgNsLSMGn4vDjBLmUZ9C5mAi3rWMcT03galpD9Nm1ph8SH7p" +
			"RkEreE7KrwKBgQCfUIIO3wP5BNyZoVym6bx9eT0M3ctGvdUsMapelGc3weZgFoOTKnTsKeB9+6+W" +
			"AaGgbNYqJLTSHO+txs5qBebxhx857708J4csZTbScBWppSJ3p58aLYnDFvNWX8XUpPk7+frxvMfL" +
			"7EPhn128IkuVArI0qrxsZJLdKp6WXGA5kQKBgQC1TS+lxASC+r59vDc4kRR5Wpnzd7yIvMBBVD5h" +
			"LEjNJMg2gUErpzUsb7LFTW/Aj/kQJ/5XSEW0AzIdrJ8DcjYMzPqVJgtgjDgKE9P2xsn9gM++sPBe" +
			"+fqPAcTiA8AH3qirkUiZei0VHAjiKZfZlavlq/EX6FoX138ia/o+HweP0QKBgEFbf9+l8jVIn0+P" +
			"eTkojqFTCY6rtucQndGBlokyjJ29OEuXMOdA9bKlA2nrxP7fMHVl8P5dDKleIZ3C/49TWIkhi9fy" +
			"2ElDVhsKctEmFl0AWAGKjKTqpaeZVL7Mj5ZLD8xhI2fksHqiNL7s6EF0OFmrP3yHxsEZCX8eKu04" +
			"fGlhAoGBAI1+KgKLEYqh7OfClzJ0wYhuWdU3GELexrrr3PmwGyxnW9OHQ7/NS2QK9GKwdujoML40" +
			"+TMsAX14H6qDOYuESuI+3fzJRz0FCGRTfNRmHrKWPwPwcFWJPsbT9XFEDRkPOv8DFCQXOIlFLtgT" +
			"rUdZUZsdQWHnzogfjieAh9z6uqFQ"; 

	
	public static String decrypt(String encryptedData){
		String decryptedData=null;
		try {
			// get the private key
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			KeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
	  	    PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
		
			Cipher rsaCipher = Cipher.getInstance("RSA");
			rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
			
			byte[] utf8=rsaCipher.doFinal(Base64.decodeBase64(encryptedData));
			
			decryptedData = (new String(utf8,"UTF-8"));
			
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} 
		
		return decryptedData;
	}
	
	public static String checkAndDecrypt( String encryptedData ){
		
		if(encryptedData==null){
			return encryptedData;
		}
		
		if(encryptedData.length()>50){
			return decrypt(encryptedData);
		}
		 
		return encryptedData;
	}
	
	
/*	public static void main(String[] args) {
		//绉侀挜杞琩er宸ュ叿openssl pkcs8 -topk8 -nocrypt -inform pem -in private.pem  -outform der  -out  private.der
		String password="E40/GLvfNAW+CaiSBvGULi8O96SmVTHNx9hdsCkO8pC0cWsAiRGPZAbuCk+ijn+pbL+Y0PTTmo+W5MEe2jo1lGSe2DvVfB4uPEJqdU4pszwz4S2S1y9CabA3gy45W+OXawq4jV5RKbRnV5xda98kubQ7z34PnREhFy7P+CJHELrCezNVi+ngbGW4/9giehKLGlCS+Ymm9c5ILya5ZD7x4YEV1P0licNNi3aKF9mEJv6JL/rd0gfvqPhUrzx784/bcW84Yu/nIXl+cDZWl4JE37FeSyS/9HBITFjrZW0lOlndhS4C5awAb0In65dynoJflkyw2/egjlAUSBGG0j3j7A==";
		System.out.println(decrypt(password));
	}*/
}
