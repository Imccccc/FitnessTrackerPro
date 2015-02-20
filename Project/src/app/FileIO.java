package app;

import java.util.ArrayList;
import java.io.*;

public class FileIO {
	public static void write(Object obj, String path){
		try {
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object read(String path){
		Object ret;
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois=new ObjectInputStream(fis);
			ret=ois.readObject();
			ois.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ret;
	}
	
	
	
	public static void main(String args[]){
		TestClass a1=new TestClass();
		TestClass a2=null;
		a1.init();
		write(a1, "d:\\a.txt");
		
		System.out.println((TestClass)read("d:\\a.txt"));
		
	}
}

class TestClass implements Serializable{
	private static final long serialVersionUID = 1L;
	int i;
	String s;
	ArrayList<String> a;
	
	public void init(){
		i=123;
		s="zxc";
		a=new ArrayList<String>(3);
		a.add("qwe1");
		a.add("qwe2");
		a.add("qwe3");
	}

	@Override
	public String toString() {
		return "TestClass [i=" + i + ", s=" + s + ", a=" + a + "]";
	}
	
	
}