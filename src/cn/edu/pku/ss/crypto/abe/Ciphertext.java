package cn.edu.pku.ss.crypto.abe;

import it.unisa.dia.gas.jpbc.Element;
import cn.edu.pku.ss.crypto.abe.serialize.Serializable;
import cn.edu.pku.ss.crypto.abe.serialize.SimpleSerializable;

public class Ciphertext implements SimpleSerializable{
	@Serializable
	Policy p;
	@Serializable(group="GT")
	Element Cs; //GT
	@Serializable(group="G1")
	Element C;  //G1
}
