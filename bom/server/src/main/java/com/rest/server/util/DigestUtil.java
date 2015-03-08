package com.rest.server.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;


public class DigestUtil {

	public static String sha256_base64(String base) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] value;
			value = digest.digest(base.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(value));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static String sha256_hex(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static String md5(String orgin)
	  {
	    try
	    {
	      MessageDigest md = MessageDigest.getInstance("MD5");
	      return byte2hex(md.digest(orgin.toString().getBytes("utf-8")));
	    } catch (Exception e) {
	    }
	    throw new RuntimeException("sign error !");
	  }

	  public static String md5toLowerCase(String orgin)
	  {
	    try
	    {
	      return md5(orgin).toLowerCase();
	    } catch (Exception e) {
	    }
	    throw new RuntimeException("sign error !");
	  }

	  private static String byte2hex(byte[] b)
	  {
	    String hs = "";
	    String stmp = "";
	    for (int n = 0; n < b.length; n++) {
	      stmp = Integer.toHexString(b[n] & 0xFF);
	      if (stmp.length() == 1)
	        hs = hs + "0" + stmp;
	      else
	        hs = hs + stmp;
	    }
	    return hs.toUpperCase();
	  }
}
