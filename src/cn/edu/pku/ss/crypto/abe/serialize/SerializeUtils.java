package cn.edu.pku.ss.crypto.abe.serialize;

import it.unisa.dia.gas.jpbc.Element;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import cn.edu.pku.ss.crypto.abe.PairingManager;
import cn.edu.pku.ss.crypto.abe.Policy;
import cn.edu.pku.ss.crypto.abe.SecretKey.SKComponent;

public class SerializeUtils {
	public static <T extends SimpleSerializable> T _unserialize(
			Class<T> clazz, DataInputStream dis) {
		T t = null;
		Field[] fields = clazz.getDeclaredFields();
		try {
			t = clazz.newInstance();
			for (Field field : fields) {
				field.setAccessible(true);
				if (Modifier.isTransient(field.getModifiers())) {
					continue;
				}
				byte mark = dis.readByte();
				// unserialize Element
				if (field.getType() == Element.class) {
					if (mark != SimpleSerializable.ElementMark) {
						System.err.println("serialize Element error!");
						return null;
					}
					Element e = null;
					int len = dis.readInt();
					if (len == 0) {
						field.set(t, null);
						continue;
					}
					byte[] buffer = new byte[len];
//					String name = field.getName();
					Serializable annotation = field.getAnnotation(Serializable.class);
					String group = annotation.group();
					dis.read(buffer);
					if(group.equals("Zr")){
						e = PairingManager.defaultPairing.getZr().newElementFromBytes(buffer);
					}
					else if(group.equals("G1")){
						e = PairingManager.defaultPairing.getG1().newElementFromBytes(buffer);
					}
					else if(group.equals("G2")){
						e = PairingManager.defaultPairing.getG2().newElementFromBytes(buffer);
					}
					else if(group.equals("GT")){
						e = PairingManager.defaultPairing.getGT().newElementFromBytes(buffer);
					}
//					if (name.equals("g") || name.equals("h") || name.equals("C")
//							|| name.equals("Cy") || name.equals("_Cy")) {
//						e = PairingManager.defaultPairing.getG1()
//								.newElementFromBytes(buffer);
//					} else if (name.equals("gp") || name.equals("g_alpha")
//							|| name.equals("D") || name.equals("Dj")
//							|| name.equals("_Dj")) {
//						e = PairingManager.defaultPairing.getG2()
//								.newElementFromBytes(buffer);
//					} else if (name.equals("g_hat_alpha") || name.equals("Cs")) {
//						e = PairingManager.defaultPairing.getGT()
//								.newElementFromBytes(buffer);
//					} else if (name.equals("beta")) {
//						e = PairingManager.defaultPairing.getZr()
//								.newElementFromBytes(buffer);
//					}
					field.set(t, e);
				} else if (field.getType() == Policy.class) {
					if (mark != SimpleSerializable.PolicyMark) {
						System.err.println("serialize Policy error!");
						return null;
					}
					Policy p = _unserialize(Policy.class, dis);
					field.set(t, p);
				}
				// unserialize String
				else if (field.getType() == String.class) {
					if (mark != SimpleSerializable.StringMark) {
						System.err.println("serialize String error!");
						return null;
					}
					String s = dis.readUTF();
					field.set(t, s);
				}
				else if (field.getType() == int.class){
					if (mark != SimpleSerializable.IntMark) {
						System.err.println("serialize Int error!");
						return null;
					}
					int i = dis.readInt();
					field.set(t,i);
				}
				// unserialize Array
				else if (field.getType().isArray()) {
					if (mark != SimpleSerializable.ArrayMark) {
						System.err.println("serialize Array error!");
						return null;
					}
					Class<?> c = field.getType().getComponentType();
					int arrlen = dis.readInt();
					if(arrlen == 0){
						field.set(t, null);
						continue;
					}
					if (c == SKComponent.class) {
						SKComponent[] comps = new SKComponent[arrlen];
						for (int i = 0; i < arrlen; i++) {
							comps[i] = _unserialize(SKComponent.class, dis);
						}
						field.set(t, comps);
					} else if (c == Policy.class) {
						Policy[] ps = new Policy[arrlen];
						for (int i = 0; i < arrlen; i++) {
							mark = dis.readByte();
							if(mark != SimpleSerializable.PolicyMark){
								System.err.println("serialize Policy error!");
								return null;
							}
							ps[i] = _unserialize(Policy.class, dis);
						}
						field.set(t, ps);
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}

	public static <T extends SimpleSerializable> T unserialize(Class<T> clazz,
			File file) {
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(new FileInputStream(file));
			return _unserialize(clazz, dis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

	private static <T extends SimpleSerializable> void _serialize(T obj,
			DataOutputStream dos) {
		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				if (Modifier.isTransient(field.getModifiers())) {
					continue;
				}
				Class<?> type = field.getType();
				if (type == Element.class) {
					Element e = (Element) field.get(obj);
					dos.writeByte(SimpleSerializable.ElementMark);
					int len = e == null ? 0 : e.toBytes().length;
					dos.writeInt(len);
					if (e != null) {
						dos.write(e.toBytes());
					}
				} else if (type == String.class) {
					String s = (String) field.get(obj);
					s = ((s == null) ? "" : s);
					dos.writeByte(SimpleSerializable.StringMark);
					dos.writeUTF(s);
				} else if (type == int.class) {
					int i = field.getInt(obj);
					dos.writeByte(SimpleSerializable.IntMark);
					dos.writeInt(i);
				} else if (type == Policy.class) {
					Policy p = (Policy) field.get(obj);
					dos.writeByte(SimpleSerializable.PolicyMark);
					_serialize(p, dos);
				} else if (type.isArray()) {
					Class<?> clazz = type.getComponentType();
					if (clazz == SKComponent.class) {
						SKComponent[] array = (SKComponent[]) field.get(obj);
						int len = array.length;
						dos.writeByte(SimpleSerializable.ArrayMark);
						dos.writeInt(len);
						for (int i = 0; i < len; i++) {
							SKComponent comp = array[i];
							_serialize(comp, dos);
						}
					} else if (clazz == Policy.class) {
						Policy[] array = (Policy[]) field.get(obj);
						int len = array == null ? 0 : array.length;
						dos.writeByte(SimpleSerializable.ArrayMark);
						dos.writeInt(len);
						if(len == 0){
							continue;
						}
						for (int i = 0; i < len; i++) {
							Policy p = array[i];
							dos.writeByte(SimpleSerializable.PolicyMark);
							_serialize(p, dos);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static <T extends SimpleSerializable> void serialize(T obj, File file) {
		DataOutputStream dos = null;
		try {
			dos = new DataOutputStream(new FileOutputStream(file, true));
			_serialize(obj, dos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static <T extends SimpleSerializable> T constructFromByteArray(Class<T> clazz, byte[] b){
		File tmp = null;
		try {
			tmp = File.createTempFile("random", "bytearray");
			OutputStream os = new FileOutputStream(tmp);
			os.write(b);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unserialize(clazz, tmp);
	}
	
	public static <T extends SimpleSerializable> byte[] convertToByteArray(T obj){
		File tmp = null;
		try {
			tmp = File.createTempFile("random", "bytearray");
			SerializeUtils.serialize(obj, tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] buf = new byte[(int) tmp.length()];
		try {
			InputStream is = new FileInputStream(tmp);
			is.read(buf);
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buf;
	}
}
