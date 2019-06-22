package main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

class FTP {
	public static void uploadFile(String LocalUrl,String FileName) {
        
		String ip = "129.211.80.114";            //服务器IP地址	
		String userName = "ftpuser";        //用于登陆服务器的用户名				
		String passWord = "qwe..123";            //登陆密码
		String remoteDirectoryPath = "/usr/DBdesign/JavaPic";    //远程文件夹的绝对路径   	
		String localFilePath = LocalUrl;    //要上传到服务器的本地文件的绝对路径
		String remoteFileName = FileName;    //将本地文件上传到服务器后文件的名字			
		
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(ip);
			boolean isLogin = ftpClient.login(userName, passWord);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			System.out.println("uploadFile 登陆成功？ " + isLogin);
			ftpClient.changeWorkingDirectory(remoteDirectoryPath);
			InputStream is = new FileInputStream(new File(localFilePath));
			boolean isStore = ftpClient.storeFile(remoteFileName, is);
			System.out.println("上传成功？ " + isStore);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
