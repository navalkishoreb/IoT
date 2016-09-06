package com.talentica.blescanner.base;

import android.media.MediaCodec;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by NavalB on 21-06-2016.
 */

public class CryptoUtils {
	private final String TRANSFORMATION = "AES";
	private final String encodekey = "1234543444555666";

	public String encrypt(String inputFile) throws MediaCodec.CryptoException, GeneralSecurityException, UnsupportedEncodingException {
		return doEncrypt(encodekey, inputFile);
	}

	public String decrypt(String input) throws MediaCodec.CryptoException {
		// return  doCrypto(Cipher.DECRYPT_MODE, key, inputFile);
		return doDecrypt(encodekey, input);
	}

	private String doEncrypt(String encodekey, String inputStr) throws GeneralSecurityException, UnsupportedEncodingException {
		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			byte[] key = encodekey.getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // use only first 128 bit
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] inputBytes = inputStr.getBytes();
			byte[] outputBytes = cipher.doFinal(inputBytes);
			return Base64.encodeToString(outputBytes, Base64.DEFAULT);
		} catch (GeneralSecurityException | UnsupportedEncodingException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public String doDecrypt(String encodekey, String encrptedStr) {
		try {
			Cipher dcipher = Cipher.getInstance(TRANSFORMATION);
			dcipher = Cipher.getInstance("AES");
			byte[] key = encodekey.getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // use only first 128 bit
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
			dcipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			// decode with base64 to get bytes
			byte[] dec = Base64.decode(encrptedStr.getBytes(), Base64.DEFAULT);
			byte[] utf8 = dcipher.doFinal(dec);
			// create new string based on the specified charset
			return new String(utf8, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
