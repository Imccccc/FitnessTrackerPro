package app;


import java.util.ArrayList;
import java.io.*;

public class FileIO {
	public static void main(String args[]){
		A a1=new A();
		A a2=null;
		a1.init();
		
		try {
			FileOutputStream fos = new FileOutputStream("D:\\a.txt");
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(a1);
			oos.close();
			fos.close();
			
			FileInputStream fis = new FileInputStream("D:\\a.txt");
			ObjectInputStream ois=new ObjectInputStream(fis);
			a2=(A)ois.readObject();
			ois.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(a2);
		
	}
}

class A implements Serializable{
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
		return "A [i=" + i + ", s=" + s + ", a=" + a + "]";
	}
	
	
}