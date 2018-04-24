package cn.edu.pku.ss.crypto.abe;

import cn.edu.pku.ss.crypto.abe.serialize.Serializable;
import cn.edu.pku.ss.crypto.abe.serialize.SimpleSerializable;
import it.unisa.dia.gas.jpbc.Element;

public class MasterKey implements SimpleSerializable{
	@Serializable(group="Zr")
	Element beta;
	
	@Serializable(group="G2")
	Element g_alpha;
}
