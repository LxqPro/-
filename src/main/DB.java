package main;

class DB {
	static String uri = "jdbc:mysql://129.211.80.114:3306/Forum? useSSL=true&characterEncoding=utf-8";
	static String user = "root";
	static String pwd = "qwe..123";
	public static String DataToSQLchar(String str) {
		str = "'"+str+"'";
		return str;
	}
	public static String DataToSQLchar(int number) {
		String str = number+"";
		return DataToSQLchar(str);
	}
}
