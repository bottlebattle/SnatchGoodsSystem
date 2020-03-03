package com.snatch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.snatch.domain.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserUtil {

	private static List<User> createUserList(int count){
		List<User> users = new ArrayList<User>(count);
		//生成用户
		for(int i=0;i<count;i++) {
			User user = new User();
			user.setId(13000000000L+i);
			user.setUserName("user"+i);
			user.setSalt("1a2b3c");
			user.setPassword(MD5Util.inputPasstoDBPass("miaoshamima", user.getSalt()));
			users.add(user);
		}
		return users;
	}

	public static boolean insertUsersToDB(List<User> users) throws Exception {
		Connection conn = DBUtil.getConn();
		String sql = "insert into user(user_name, salt, password, id)values(?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		for(int i=0;i<users.size();i++) {
			User user = users.get(i);
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getSalt());
			pstmt.setString(3, user.getPassword());
			pstmt.setLong(4, user.getId());
			pstmt.addBatch();
		}
		pstmt.executeBatch();
		pstmt.close();
		conn.close();
		return true;
	}

	private static void getTokenIntoText(List<User> users) throws IOException {
		String urlString = "http://localhost:8081/test/generateData";
		File file = new File("D:/tokens.txt");
		if(file.exists()) {
			file.delete();
		}
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		file.createNewFile();
		raf.seek(0);
		for(int i=0;i<users.size();i++) {
			User user = users.get(i);
			URL url = new URL(urlString);
			HttpURLConnection co = (HttpURLConnection)url.openConnection();
			co.setRequestMethod("POST");
			co.setDoOutput(true);
			OutputStream out = co.getOutputStream();
			String params = "userId="+user.getId()+"&password="+MD5Util.inputPassFormPass("123456");
			out.write(params.getBytes());
			out.flush();
			InputStream inputStream = co.getInputStream();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte buff[] = new byte[1024];
			int len = 0;
			while((len = inputStream.read(buff)) >= 0) {
				bout.write(buff, 0 ,len);
			}
			inputStream.close();
			bout.close();
			String token = new String(bout.toByteArray());

			System.out.println("create token : " + user.getId());

			String row = user.getId()+","+token;
			raf.seek(raf.length());
			raf.write(row.getBytes());
			raf.write("\r\n".getBytes());
			System.out.println("write to file : " + user.getId());
		}
		raf.close();
	}

	private static void createUser(int count) throws Exception{
		List<User> users = createUserList(500);
		System.out.println("create user");
		//插入数据库
		//insertUsersToDB(users);
		System.out.println("insert to db");
		//登录，生成token
		getTokenIntoText(users);
		
		System.out.println("over");
	}

	public static void main(String[] args)throws Exception {
		createUser(5000);
	}
}
