package app;

import java.util.ArrayList;
import java.io.*;

public class FileIO {
	public static void write(ArrayList al, String path){
		try {
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(al);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList read(String path){
		FileInputStream fis=null;
		ObjectInputStream ois=null;
	
		try {
			fis = new FileInputStream(path);
			ois=new ObjectInputStream(fis);
			return (ArrayList)ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				fis.close();
			} catch (IOException e) {}
		}
		return null;
	}
	
	
	
	public static void main(String args[]){
		TestClass a1=new TestClass();
		TestClass a2=new TestClass();
		a1.init();
		a2.init2();
		ArrayList<TestClass> t=new ArrayList<TestClass>();
		t.add(a1);
		t.add(a2);
		write(t, "d:\\a.txt");
		System.out.println((ArrayList)read("d:\\a.txt"));
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
	public void init2(){
		i=1232;
		s="zxc2";
		a=new ArrayList<String>(3);
		a.add("qwe12");
		a.add("qwe22");
		a.add("qwe32");
	}

	@Override
	public String toString() {
		return "TestClass [i=" + i + ", s=" + s + ", a=" + a + "]";
	}
	
	
}