package main;
import javax.swing.*;
import java.awt.*;
public class Main {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		try {Class.forName("com.mysql.cj.jdbc.Driver");}
		catch(Exception e) {System.out.println(e);}
		Login login = new Login();
		login.setTitle("��ӭ��������̳");
		login.setBounds(520,250,400,240);
	}
}
