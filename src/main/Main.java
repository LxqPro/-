package main;
import javax.swing.*;
import java.awt.*;
public class Main {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		try {Class.forName("com.mysql.cj.jdbc.Driver");}
		catch(Exception e) {System.out.println(e);}
		Login login = new Login();
		login.setTitle("欢迎来到本论坛");
		login.setBounds(520,250,400,240);
	}
}
