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
		textId = new JTextField("10000003",15);      //输入账号
		textPwdL = new JPasswordField("151234",15);     //登录输入密码
		textPwdR = new JPasswordField(15);    //注册设置密码
		textPwdC = new JPasswordField(15);	 //确认密码
		
		Login = new JButton("登录");    //登录按钮
		Register = new JButton("注册"); //注册按钮
		JPanel L= new JPanel();           //登录选项卡
		JPanel R= new JPanel();           //注册选项卡
		
		Login.addActionListener(this);  //监听登录按钮
		Register.addActionListener(this);  //监听注册按钮
		JTabbedPane LorR = new JTabbedPane();  //选项卡
		LorR.addTab("登录", L);               
		LorR.addTab("注册", R);
		
		Box LID = Box.createHorizontalBox();   //登录水平盒子
		Box Lpwd = Box.createHorizontalBox(); //登录水平盒子
		Box Lbutton = Box.createHorizontalBox();   //登录水平盒子
		Box LV = Box.createVerticalBox();         //垂直盒子
		
		Box Rpwd = Box.createHorizontalBox();  //注册水平盒子
		Box Rrepwd = Box.createHorizontalBox();  //注册水平盒子
		Box Rbutton = Box.createHorizontalBox();  //注册水平盒子
		Box RV = Box.createVerticalBox();  //注册垂直盒子
;		
		LID.add(new JLabel("用户："));
		LID.add(textId);
		Lpwd.add(new JLabel("密码："));
		Lpwd.add(textPwdL);
		Lbutton.add(Login);
		
		Rpwd.add(new JLabel("设置密码："));
		Rpwd.add(textPwdR);
		Rrepwd.add(new JLabel("重复密码："));
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
		R.add(new JLabel("注册成功后自动分配账号。"),BorderLayout.NORTH);
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
			if(e.getActionCommand() == "登录") {
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
					JOptionPane.showMessageDialog(this,"用户名或密码错误！","错误 ",0);
				}
			}
			else if(e.getActionCommand() == "注册"){
				String FirstPwd = textPwdR.getText();
				String RepeatPwd = textPwdC.getText();
				if(!FirstPwd.equals(RepeatPwd)) {
					JOptionPane.showMessageDialog(this, "两次密码不一致，请重试","错误",0);
				}
				else if(FirstPwd.isEmpty()){
					JOptionPane.showMessageDialog(this, "密码不能为空","错误",0);
				}
				else {
					String SQLgetMaxID = "select max(UID) from UserInfo";
					try {
						ResultSet MaxID = sql.executeQuery(SQLgetMaxID);
						MaxID.next();
						int newID = MaxID.getInt(1)+1;
						String SQLregister = "insert into UserInfo (UID,Upassword,Usex,Uhead) value ("+DB.DataToSQLchar(newID)+","+DB.DataToSQLchar(FirstPwd)+",'男','JavaPic/test.jpg')";
						int ok = sql.executeUpdate(SQLregister);
						int goLogin = JOptionPane.showConfirmDialog(this,"你的用户名为："+newID+",点击确定登录","提示",2,1);
						if(goLogin==0) {
							textId.setText(newID+"");
							textPwdL.setText(FirstPwd);
							Login.doClick();
						}
					}
					catch(SQLException excep) {
						JOptionPane.showMessageDialog(this,"服务不可用","错误 ",0);
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
