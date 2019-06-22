package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

class MakePost extends JFrame implements ActionListener{
	String UID;
	ChangeInfo last;
	JTextField Ptitle;
	JTextArea Pcontent;
	public MakePost(String UID,ChangeInfo last) {
		this.UID = UID;
		this.last = last;
		init();
		setBounds(300,100,600,400);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void init() {
		JPanel BlankL = new JPanel();
		JPanel BlankR = new JPanel();
		Box Btitle = Box.createHorizontalBox();
		Box Bcontent = Box.createVerticalBox();
		Ptitle = new JTextField(10);
		Pcontent = new JTextArea();
		JButton confirm = new JButton("发布");
		confirm.addActionListener(this);
		Pcontent.setColumns(6);
		Pcontent.setLineWrap(true);
		Btitle.add(Box.createHorizontalStrut(10));
		Btitle.add(new JLabel("标题："));
		Btitle.add(Ptitle);
		Btitle.add(Box.createHorizontalStrut(20));
		Bcontent.add(new JLabel("内容"));
		Bcontent.add(Box.createVerticalStrut(5));
		Bcontent.add(Pcontent);
		BlankL.setSize(400,0);
		BlankL.setSize(400,0);
		add(Btitle,BorderLayout.NORTH);
		add(Bcontent,BorderLayout.CENTER);
		add(BlankL,BorderLayout.EAST);
		add(BlankR,BorderLayout.WEST);
		add(confirm,BorderLayout.SOUTH);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO 自动生成的方法存根
		Connection con = null;
		try {
    		con = DriverManager.getConnection(DB.uri,DB.user,DB.pwd);
			Statement sql = con.createStatement();
			String queryMaxPID = "select max(PID) from JUser_Post";
			ResultSet MaxPID = sql.executeQuery(queryMaxPID);
			MaxPID.next();
			int NewPID = MaxPID.getInt(1)+1;
			System.out.println();
			System.out.println(Ptitle.getText());
			System.out.println();
			String insertPost = "insert into JPost values("+DB.DataToSQLchar(NewPID)+","+DB.DataToSQLchar(Ptitle.getText())+","+DB.DataToSQLchar(Pcontent.getText())+")";
			String insertUP = "insert into JUser_Post values("+DB.DataToSQLchar(NewPID)+","+DB.DataToSQLchar(UID)+")";
			System.out.println(insertPost+"\n"+insertUP);
			sql.executeUpdate(insertPost);
			sql.executeUpdate(insertUP);
			con.close();
			new ChangeInfo(last.UID,last.Nickname,last.Uqq,last.Usex,last.Uhead);
			this.dispose();
    	}
    	catch(SQLException E) {System.out.println(E);}
	}
}
