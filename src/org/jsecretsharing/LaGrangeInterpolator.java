package org.jsecretsharing;

import java.math.BigDecimal;
import java.math.BigInteger;

public class LaGrangeInterpolator {
	private final BigDecimal[] independentValues, dependentValues;
	private final int degree;
	
	public LaGrangeInterpolator(BigInteger[] independentValues, BigInteger[] dependentValues) {
		if (independentValues.length != dependentValues.length) {
			throw new IllegalArgumentException("must be the same number of X values as Y values");
		}
		
		this.degree = independentValues.length;
		this.independentValues = new BigDecimal[degree];
		this.dependentValues = new BigDecimal[degree];
		for (int i = 0; i < degree; i++) {
			this.independentValues[i] = new BigDecimal(independentValues[i]);
			this.dependentValues[i] = new BigDecimal(dependentValues[i]);
		}
	}

	public BigInteger interpolate(BigInteger x) {
		BigDecimal value = BigDecimal.ZERO;
		BigDecimal desiredPos = new BigDecimal(x);
		for (int i = 0; i < degree; i++) {
			BigDecimal weight = BigDecimal.ONE;
			for (int j = 0; j < degree; j++) {
				if (j != i) {
					BigDecimal top = desiredPos.subtract(independentValues[j]);
					BigDecimal bottom = independentValues[i].subtract(independentValues[j]);
					BigDecimal factor = top.divide(bottom);
					weight = weight.multiply(factor);
				}
			}
			value = value.add(weight.multiply(dependentValues[i]));
		}
		return value.toBigInteger();
	}
}
