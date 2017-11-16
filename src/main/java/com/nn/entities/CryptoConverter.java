package com.nn.entities;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

	private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
	private static final byte[] KEY = "t0pCkret".getBytes();

	@Override
	public String convertToDatabaseColumn(String password) {
		// do some encryption
		Key key = new SecretKeySpec(KEY, "AES");
		try {
			Cipher c = Cipher.getInstance(ALGORITHM);
			c.init(Cipher.ENCRYPT_MODE, key);
			return Base64.getEncoder().encodeToString(c.doFinal(password.getBytes()));
		} catch (Exception e) {
			// TODO log exception pls
			throw new RuntimeException(e);
		}
	}

	@Override
	public String convertToEntityAttribute(String encryptedPassword) {
		// do some decryption
		Key key = new SecretKeySpec(KEY, "AES");
		try {
			Cipher c = Cipher.getInstance(ALGORITHM);
			c.init(Cipher.DECRYPT_MODE, key);
			return new String(c.doFinal(Base64.getDecoder().decode((encryptedPassword))));
		} catch (Exception e) {
			// TODO log exception pls
			throw new RuntimeException(e);
		}
	}
}