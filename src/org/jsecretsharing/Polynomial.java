package org.jsecretsharing;

import java.math.BigInteger;
import java.util.Arrays;

public class Polynomial {
	private final BigInteger[] coefficients;
	
	public Polynomial(BigInteger... coefficients) {
		this.coefficients = Arrays.copyOf(coefficients, coefficients.length);
	}

	public BigInteger evaluate(BigInteger x) {
		BigInteger y = BigInteger.ZERO;
		for (int i = 0; i < coefficients.length; i++) {
			if (coefficients[i].signum() != 0) {
				y = y.add(coefficients[i].multiply(x.pow(i)));
			}
		}
		return y;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = coefficients.length - 1; i >= 0; i--) {
			if (coefficients[i].signum() != 0) {
				builder.append(coefficients[i].toString());
				if (i == 1) {
					builder.append("x");
				} else if (i > 1) {
					builder.append("x^").append(i);
				}
				
				if (i != 0) {
					builder.append(" + ");
				}
			}
		}
		return builder.toString();
	}
}
