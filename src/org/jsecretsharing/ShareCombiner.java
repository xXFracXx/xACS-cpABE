package org.jsecretsharing;

import java.math.BigInteger;
import java.util.List;

public class ShareCombiner {
	private final BigInteger[] independentValues, dependentValues;
	
	public ShareCombiner(List<Share> shares) {
		this.independentValues = new BigInteger[shares.size()];
		this.dependentValues = new BigInteger[shares.size()];
		for (int i = 0; i < shares.size(); i++) {
			independentValues[i] = shares.get(i).getX();
			dependentValues[i] = shares.get(i).getY();
		}
	}

	public byte[] combine() {
		LaGrangeInterpolator interpolator = new LaGrangeInterpolator(independentValues, dependentValues);
		return interpolator.interpolate(BigInteger.ZERO).toByteArray();
	}
}
