package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.*;
class UserInfo extends JFrame implements ActionListener{
	JLabel container = new JLabel();  //头像面板
	JLabel Nametext,QQtext,Sextext;
	JScrollPane scrollpane;
	JPanel Post;
	int pnumber;   //post number
	String UID,Nickname,Usex,Uqq,Uhead;
	String uri;
	String dbpwd;
	String user;
	ResultSet resUinfo,resPost;
	UserInfo(String Uid){
		UID = Uid;
		Datainit();
		Interfaceinit();
		setTitle("不忍直视的界面");
		setBounds(800,100,300,600);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void Interfaceinit() {
		JButton changeHead = new JButton("个人信息");
		scrollpane = new JScrollPane(Post);
		scrollpane.setBackground(Color.blue);
		Box Uinfo = Box.createHorizontalBox();
		Box Name_Sex = Box.createVerticalBox();
		changeHead.addActionListener(this);
		container.setSize(10, 10);
		Name_Sex.add(Nametext);
		Name_Sex.add(Sextext);
		Name_Sex.add(QQtext);
		Uinfo.add(container);
		Uinfo.add(Name_Sex);
		add(changeHead,BorderLayout.SOUTH);
		add(scrollpane,BorderLayout.CENTER);
		add(Uinfo,BorderLayout.NORTH);
	}
	
	private void Datainit() {
		String IP = "129.211.80.114";
		Connection con = null;
		try {
			con = DriverManager.getConnection(DB.uri,DB.user,DB.pwd);
			Statement sql = con.createStatement();
			resUinfo = sql.executeQuery("SELECT * from UserInfo where UID="+UID);
			resUinfo.next();
			Nickname = resUinfo.getString(3);
			Usex = resUinfo.getString(4);
			Uqq = resUinfo.getString(5);
			Uhead = resUinfo.getString(6);
			URL iconurl;
            //初始化用户头像
			iconurl = new URL("http://"+IP+":3000/"+Uhead);
			URLConnection urlConnection = iconurl.openConnection();
			ImageIcon image = new ImageIcon(iconurl);
    		Image newimage = image.getImage();
    		newimage = newimage.getScaledInstance(60, 60, Image.SCALE_DEFAULT);
	    	image.setImage(newimage);
			container.setIcon(image);
			//用户基本信息
			if(Uqq==null) {Uqq = "";}
			if(Usex==null) {Usex="男";}
			Nametext = new JLabel("昵称："+Nickname);
			QQtext = new JLabel("QQ ："+Uqq);
			Sextext = new JLabel("性别："+Usex);
			//滚动框内容
			resPost = sql.executeQuery("SELECT Unickname,Uqq,Ptitle,Pcontent from UserInfo U,JUser_Post UP,JPost P where P.PID=UP.PID and U.UID=UP.UID");
			Post = new JPanel();
			Box BPost = Box.createVerticalBox();
			while(resPost.next()) {				
				JLabel PdetTitle = new JLabel("标题："+resPost.getString(3));
				PdetTitle.setFont(new Font("宋体",1,20));
				JTextArea PdetContent = new JTextArea(resPost.getString(4));
				PdetContent.setColumns(20);
				PdetContent.setEditable(false);
				PdetContent.setBackground(new Color(238,238,238));
				PdetContent.setLineWrap(true);
				JLabel PdetName = new JLabel(resPost.getString(1));
				JLabel PdetQQ = new JLabel("QQ:"+resPost.getString(2));
				Box BPtitle = Box.createHorizontalBox();
				Box BUinfo = Box.createHorizontalBox();
				JPanel BPcontent = new JPanel();
				BPtitle.add(PdetTitle);			
				BUinfo.add(PdetName);
				BUinfo.add(Box.createHorizontalStrut(40));
				BUinfo.add(PdetQQ);
				BPcontent.add(PdetContent);
				BPost.add(BPtitle);
				BPost.add(Box.createVerticalStrut(5));
				BPost.add(BUinfo);
				BPost.add(Box.createVerticalStrut(5));
				BPost.add(BPcontent);
				BPost.add(Box.createVerticalStrut(20));
			}		
			Post.add(BPost);
			con.close();
		}
		catch(SQLException SqlEor) {System.out.println(SqlEor);}
		catch(MalformedURLException e) {System.out.println(e);}
		catch(IOException e) {System.out.println(e);}
	}
	
	public void actionPerformed(ActionEvent e) {
		new ChangeInfo(UID,Nickname,Uqq,Usex,Uhead);
		this.dispose();
	}
}
