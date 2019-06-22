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
        
		String ip = "129.211.80.114";            //������IP��ַ	
		String userName = "ftpuser";        //���ڵ�½���������û���				
		String passWord = "qwe..123";            //��½����
		String remoteDirectoryPath = "/usr/DBdesign/JavaPic";    //Զ���ļ��еľ���·��   	
		String localFilePath = LocalUrl;    //Ҫ�ϴ����������ı����ļ��ľ���·��
		String remoteFileName = FileName;    //�������ļ��ϴ������������ļ�������			
		
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(ip);
			boolean isLogin = ftpClient.login(userName, passWord);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			System.out.println("uploadFile ��½�ɹ��� " + isLogin);
			ftpClient.changeWorkingDirectory(remoteDirectoryPath);
			InputStream is = new FileInputStream(new File(localFilePath));
			boolean isStore = ftpClient.storeFile(remoteFileName, is);
			System.out.println("�ϴ��ɹ��� " + isStore);
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
