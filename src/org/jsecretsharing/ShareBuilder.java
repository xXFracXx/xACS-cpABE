package org.jsecretsharing;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class ShareBuilder {
	private final SecureRandom random;
	private final Polynomial polynomial;
	
	public ShareBuilder(byte[] secret, int recoveryThreshold, int shareSize, SecureRandom random) {
		this.random = random;
		this.polynomial = new Polynomial(buildCoeffients(new BigInteger(secret), recoveryThreshold, shareSize));
	}

	public List<Share> build(int count) {
		List<Share> shares = new ArrayList<Share>();
		for (int i = 1; i <= count; i++) {
			BigInteger x = BigInteger.valueOf(i);
			BigInteger y = polynomial.evaluate(x);
			shares.add(new Share(x, y));
		}
		return shares;
	}

	private BigInteger[] buildCoeffients(BigInteger secret, int count, int size) {
		BigInteger[] coefficients = new BigInteger[count];
		coefficients[0] = secret;
		for (int i = 1; i < coefficients.length; i++) {
			coefficients[i] = buildCoefficient(size);
		}
		return coefficients;
	}

	private BigInteger buildCoefficient(int size) {
		byte[] buffer = new byte[size];
		random.nextBytes(buffer);
		return new BigInteger(buffer);
	}
	
}
