package org.jsecretsharing;

import java.math.BigInteger;

public class Share {
	private final BigInteger x, y;
	
	public Share(BigInteger x, BigInteger y) {
		this.x = x;
		this.y = y;
	}
	
	public BigInteger getX() {
		return x;
	}
	
	public BigInteger getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return pad(x.toString(16)) + ":" + pad(y.toString(16));
	}

	public static Share load(String data) {
		String[] values = data.split(":", 2);
		return new Share(new BigInteger(values[0], 16), new BigInteger(values[1], 16));
	}
	
	private String pad(String hex) {
		if (hex.length() % 2 == 1) {
			return "0" + hex;
		}
		return hex;
	}
}
