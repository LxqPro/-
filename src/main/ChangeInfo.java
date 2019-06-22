package main;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javafx.scene.shape.Box;

class ChangeInfo extends JFrame implements ActionListener{
	String UID,Nickname,Uqq,Usex,Uhead;
	JButton ChooseHead;
	JButton Save,Return,Delete,Publish;
	JTextField CNickname,CUqq,DelPID;
	JComboBox CUsex;
	JScrollPane scrollPane;
	UserInfo Last;
	public ChangeInfo(String UID,String Unickname,String Uqq,String Usex,String Uhead){
		this.UID = UID;
		this.Nickname = Unickname;
		this.Uqq = Uqq;
		this.Usex = Usex;
		this.Uhead = Uhead;
		DataInit();
		InterfaceInit();
		setTitle("������Ϣ");
		setBounds(500,100,800,600);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void DataInit() {
		ChooseHead = new JButton("ѡ��ͷ��");
		Save = new JButton("����");
		Return = new JButton("����");
		Delete = new JButton("ɾ������");
		Publish = new JButton("��������");
		ChooseHead.addActionListener(this);
		Save.addActionListener(this);
		Return.addActionListener(this);
		Delete.addActionListener(this);
		Publish.addActionListener(this);
		CNickname = new JTextField(Nickname,15);
		CUqq = new JTextField(Uqq,10);
		CUsex = new JComboBox();
		CUsex.addItem("��");
		CUsex.addItem("Ů");
		Object[] columns={"����ID","���ӱ���"};//�ֶ�
		Object[][] data=null;//��Ҫչʾ�����ݣ�һ���Ƕ�ά����
		Connection con = null;
		String mysql = "select P.PID,P.Ptitle from JPost P,JUser_Post UP where UP.UID="+UID+" and P.PID=UP.PID";
		try {
    		con = DriverManager.getConnection(DB.uri,DB.user,DB.pwd);
			Statement sql = con.createStatement();   
			ResultSet res = sql.executeQuery(mysql);
			int index=0;
			res.next();
			res.last();
			int maxCol = res.getRow();
			res.beforeFirst();
			data = new String[maxCol][2];
			while(res.next()) {
				data[index][0] = res.getString(1);
				data[index][1] = res.getString(2);
				index++;
			}
			con.close();
    	}
    	catch(SQLException E) {System.out.println(E);}
		DefaultTableModel model=new DefaultTableModel(data, columns);
		JTable Post = new JTable(model);
		scrollPane = new JScrollPane(Post);
	}
	private void InterfaceInit() {
		JPanel BasicInfo = new JPanel();
		JPanel Bottom = new JPanel();
		DelPID = new JTextField(6);
		BasicInfo.add(ChooseHead);
		BasicInfo.add(new JLabel("�ǳ�:"));
		BasicInfo.add(CNickname);
		BasicInfo.add(new JLabel("QQ:"));
		BasicInfo.add(CUqq);
		BasicInfo.add(new JLabel("�Ա�:"));
		BasicInfo.add(CUsex);
		BasicInfo.add(Save);
		BasicInfo.add(Return);
		Bottom.add(new JLabel("��������ɾ�������ӵ�ID:"));
		Bottom.add(DelPID);
		Bottom.add(Delete);
		Bottom.add(Publish);
		this.add(BasicInfo,BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(Bottom, BorderLayout.SOUTH);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getActionCommand()=="����") {
			Nickname = CNickname.getText();
			Uqq = CUqq.getText();
			Usex = CUsex.getSelectedItem().toString();
			String mysql = "update UserInfo set Unickname="+"'"+Nickname+"',"+"Usex="+"'"+Usex+"',"+"Uqq="+"'"+Uqq+"' where UID="+UID;
			Connection con = null;
			try {
        		con = DriverManager.getConnection(DB.uri,DB.user,DB.pwd);
    			Statement sql = con.createStatement();    			
    			sql.executeUpdate(mysql);
    			JOptionPane.showMessageDialog(this,"�޸ĳɹ�!","��ʾ ",JOptionPane.PLAIN_MESSAGE);
    			con.close();
        	}
        	catch(SQLException E) {System.out.println(E);}
		}
		else if(e.getActionCommand()=="����") {
			this.dispose();
			new UserInfo(UID);
		}
		else if(e.getActionCommand()=="ѡ��ͷ��") {
			//�޸�ͷ��
			JFileChooser file = new JFileChooser();
			int val=file.showOpenDialog(null);    //�ļ��򿪶Ի���
	        if(val==JFileChooser.APPROVE_OPTION)
	        {
	            //����ѡ���ļ�
	        	String imgPath = file.getSelectedFile().toString();
	        	int suffix = imgPath.lastIndexOf(".");
	        	Uhead = UID+imgPath.substring(suffix);
	        	FTP.uploadFile(imgPath, Uhead);
	        	Connection con = null;
	        	try {
	        		con = DriverManager.getConnection(DB.uri,DB.user,DB.pwd);
	    			Statement sql = con.createStatement();
	    			String mysql = "update UserInfo set Uhead="+"'JavaPic/"+Uhead+"'"+" where UID="+UID;
	    			int OK = sql.executeUpdate(mysql);
	    			JOptionPane.showMessageDialog(this,"�޸ĳɹ�!","��ʾ ",JOptionPane.PLAIN_MESSAGE);
	    			con.close();
	        	}
	        	catch(SQLException E) {System.out.println(E);}
	        }
	        else
	        {
	            //δ����ѡ���ļ�����ѡ��ȡ����ť
	            System.out.println("δѡ���ļ�");
	        }
		}
		else if(e.getActionCommand()=="ɾ������") {
			String PostToDel = DelPID.getText();
			System.out.println(PostToDel);
			Connection con = null;
			boolean isPost = false;
        	try {
        		con = DriverManager.getConnection(DB.uri,DB.user,DB.pwd);
    			Statement sql = con.createStatement();
    			String mysql0 = "select PID from JPost";
    			ResultSet PIDset = sql.executeQuery(mysql0);
    			while(PIDset.next()) {
    				if(PIDset.getString(1).equals(PostToDel)) {
    					isPost = true;
    					break;
    				}
    			}
    			if(isPost == false) {
    				JOptionPane.showMessageDialog(this,"�����ڵ�����","��ʾ ",1);
    				return;
    			}
    			String mysql1 = "delete from JUser_Post where PID="+PostToDel;
    			String mysql2 = "delete from JPost where PID="+PostToDel;
    			sql.executeUpdate(mysql1);
    			sql.executeUpdate(mysql2);
    			JOptionPane.showMessageDialog(this,"ɾ���ɹ�!","��ʾ ",JOptionPane.PLAIN_MESSAGE);
    			new ChangeInfo(UID,Nickname,Uqq,Usex,Uhead);
    			this.dispose();
    			con.close();
        	}
        	catch(SQLException E) {System.out.println(E);}
		}
		else if(e.getActionCommand()=="��������") {
			new MakePost(UID,this);
			this.dispose();
		}
	}
}
