package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
class Login extends JFrame implements ActionListener{
	String Uid,Upwd;
	JPasswordField textPwdL,textPwdR,textPwdC;
	JTextField textId;
	JButton Login,Register;
	Login(){
		init();
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	void init() {
		textId = new JTextField("10000003",15);      //�����˺�
		textPwdL = new JPasswordField("151234",15);     //��¼��������
		textPwdR = new JPasswordField(15);    //ע����������
		textPwdC = new JPasswordField(15);	 //ȷ������
		
		Login = new JButton("��¼");    //��¼��ť
		Register = new JButton("ע��"); //ע�ᰴť
		JPanel L= new JPanel();           //��¼ѡ�
		JPanel R= new JPanel();           //ע��ѡ�
		
		Login.addActionListener(this);  //������¼��ť
		Register.addActionListener(this);  //����ע�ᰴť
		JTabbedPane LorR = new JTabbedPane();  //ѡ�
		LorR.addTab("��¼", L);               
		LorR.addTab("ע��", R);
		
		Box LID = Box.createHorizontalBox();   //��¼ˮƽ����
		Box Lpwd = Box.createHorizontalBox(); //��¼ˮƽ����
		Box Lbutton = Box.createHorizontalBox();   //��¼ˮƽ����
		Box LV = Box.createVerticalBox();         //��ֱ����
		
		Box Rpwd = Box.createHorizontalBox();  //ע��ˮƽ����
		Box Rrepwd = Box.createHorizontalBox();  //ע��ˮƽ����
		Box Rbutton = Box.createHorizontalBox();  //ע��ˮƽ����
		Box RV = Box.createVerticalBox();  //ע�ᴹֱ����
;		
		LID.add(new JLabel("�û���"));
		LID.add(textId);
		Lpwd.add(new JLabel("���룺"));
		Lpwd.add(textPwdL);
		Lbutton.add(Login);
		
		Rpwd.add(new JLabel("�������룺"));
		Rpwd.add(textPwdR);
		Rrepwd.add(new JLabel("�ظ����룺"));
		Rrepwd.add(textPwdC);
		Rbutton.add(Register);
		
		LV.add(Box.createVerticalStrut(30));
		LV.add(LID);
		LV.add(Lpwd);
		LV.add(Box.createVerticalStrut(10));
		LV.add(Lbutton);
		LV.add(Box.createVerticalStrut(15));
		L.add(LV,BorderLayout.CENTER);
		
		RV.add(Box.createVerticalStrut(30));
		RV.add(Rpwd);
		RV.add(Rrepwd);
		RV.add(Box.createVerticalStrut(10));
		RV.add(Rbutton);
		RV.add(Box.createVerticalStrut(15));
		R.add(new JLabel("ע��ɹ����Զ������˺š�"),BorderLayout.NORTH);
		R.add(RV,BorderLayout.CENTER);
		
		add(LorR,BorderLayout.CENTER);
	}
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		String uri = "jdbc:mysql://129.211.80.114:3306/Forum? useSSL=true&characterEncoding=utf-8";
		String dbpwd = "qwe..123";
		String user = "root";
		Connection con = null;
		try {
			con = DriverManager.getConnection(uri,user,dbpwd);
			Statement sql = con.createStatement();
			if(e.getActionCommand() == "��¼") {
				Uid = textId.getText();
				Upwd = textPwdL.getText();
				try {
					ResultSet res = sql.executeQuery("SELECT UID,Upassword from UserInfo where UID="+Uid);
					res.next();
					String tempPwd = res.getString(2);
					if(!tempPwd.equals(Upwd)) {
						throw new SQLException();
					}				
					new UserInfo(Uid);
					this.dispose();
				}
				catch(SQLException excep) {
					JOptionPane.showMessageDialog(this,"�û������������","���� ",0);
				}
			}
			else if(e.getActionCommand() == "ע��"){
				String FirstPwd = textPwdR.getText();
				String RepeatPwd = textPwdC.getText();
				if(!FirstPwd.equals(RepeatPwd)) {
					JOptionPane.showMessageDialog(this, "�������벻һ�£�������","����",0);
				}
				else if(FirstPwd.isEmpty()){
					JOptionPane.showMessageDialog(this, "���벻��Ϊ��","����",0);
				}
				else {
					String SQLgetMaxID = "select max(UID) from UserInfo";
					try {
						ResultSet MaxID = sql.executeQuery(SQLgetMaxID);
						MaxID.next();
						int newID = MaxID.getInt(1)+1;
						String SQLregister = "insert into UserInfo (UID,Upassword,Usex,Uhead) value ("+DB.DataToSQLchar(newID)+","+DB.DataToSQLchar(FirstPwd)+",'��','JavaPic/test.jpg')";
						int ok = sql.executeUpdate(SQLregister);
						int goLogin = JOptionPane.showConfirmDialog(this,"����û���Ϊ��"+newID+",���ȷ����¼","��ʾ",2,1);
						if(goLogin==0) {
							textId.setText(newID+"");
							textPwdL.setText(FirstPwd);
							Login.doClick();
						}
					}
					catch(SQLException excep) {
						JOptionPane.showMessageDialog(this,"���񲻿���","���� ",0);
					}
				}
			}
			else {
				System.out.println("Unexpected error!");
			}
		}
		catch(SQLException excep) {
			System.out.println(excep);
		}
	}
}
