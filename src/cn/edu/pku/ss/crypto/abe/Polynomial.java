package cn.edu.pku.ss.crypto.abe;

import it.unisa.dia.gas.jpbc.Element;

import java.util.List;


public class Polynomial {
	int deg;
	List<Element> coef; /* coefficients from [0] x^0 to [deg] x^deg */
						/* G_T (of length deg + 1) */
	
	public static Element eval_poly(Polynomial q, Element x){
		int i;
		Element s, t, r;

		r = PairingManager.defaultPairing.getZr().newElement().setToZero();
		t = PairingManager.defaultPairing.getZr().newElement().setToOne();
		
		for(i=0; i<q.deg+1; i++){
			s = q.coef.get(i).duplicate().mul(t);
			r.add(s);
			t.mul(x);
		}
		return r;
	}
}
