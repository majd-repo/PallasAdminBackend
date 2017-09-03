package util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class TokenUtils {

	// public static final String MAGIC_KEY = "pallas_";

	public static String createToken() {
		Random random = new SecureRandom();
		String token = new BigInteger(130, random).toString(32);
		return token;
	}

}
