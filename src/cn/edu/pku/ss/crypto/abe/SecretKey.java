package cn.edu.pku.ss.crypto.abe;

import cn.edu.pku.ss.crypto.abe.serialize.Serializable;
import cn.edu.pku.ss.crypto.abe.serialize.SimpleSerializable;
import it.unisa.dia.gas.jpbc.Element;

public class SecretKey implements SimpleSerializable{
	@Serializable(group="G2")
	Element D;
	
	@Serializable
	SKComponent[] comps;
	
	 public static class SKComponent implements SimpleSerializable{
		@Serializable
		String attr;
		@Serializable(group="G2")
		Element Dj;
		@Serializable(group="G2")
		Element _Dj;
	}
}
