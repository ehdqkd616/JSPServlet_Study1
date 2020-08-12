package lab.web.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.management.RuntimeErrorException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {

	static {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("드라이버 로드 성공");
		}catch(SQLException e) {
			System.out.println("드라이버 로드 실패");
			System.out.println(e.getMessage());
		}
	}
	
	private Connection getConnection() {
		DataSource ds = null;
		Connection con = null;
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle");
			con = ds.getConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	private void closeConnection(Connection con) {
		if(con!=null) {
			try {con.close();}catch(SQLException e) {}
		}
	}
	
	public void insertMember(MemberVO mem) {
		Connection con = null;
		try {
			con = getConnection();
			String sql = "insert into employees values(?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql); 
			stmt.setString(1, mem.getUserId());
			stmt.setString(2, mem.getName());
			stmt.setString(3, mem.getPassword());
			stmt.setString(4, mem.getEmail());
			stmt.setString(5, mem.getAddress());
			stmt.executeUpdate();
		}catch(SQLException e) {
			if(e.getMessage().contains("unique")) {
				throw new RuntimeException("아이디가 중복됩니다.");
			}else {
				e.printStackTrace();
				throw new RuntimeException("insertMember에서 예외발생"+e.getMessage());
			}
		}finally {
			closeConnection(con);
		}
	}
	
	public MemberVO selectMember(String userId){
		Connection con = null;
		MemberVO mem = new MemberVO();
		try {
			con = getConnection();
			String sql = "Select * from Member where userId=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				mem.setName(rs.getString("name"));
				mem.setPassword(rs.getString("password"));
				mem.setEmail(rs.getString("email"));
				mem.setAddress(rs.getString("address"));
			}
		}catch(SQLException e) {
			throw new RuntimeException("selectMember에서 예외 발생 :"+e.getMessage());
		}finally {
			closeConnection(con);
		}
		return mem;
	}
	
	public void updateMember(MemberVO mem) {
		Connection con = null;
		try {
			con = getConnection();
			//update 쿼리문 사용해서 적기
			String sql = "update Member "
					+ "set name= ?,"
					+ "password = ?,"
					+ "email = ?,"
					+ "address= ? "
					+ "where userid=?";
			PreparedStatement stmt = con.prepareStatement(sql); 
			stmt.setString(1, mem.getName());
			stmt.setString(2, mem.getPassword());
			stmt.setString(3, mem.getEmail());
			stmt.setString(4, mem.getAddress());
			stmt.setString(5, mem.getUserId());
//			int result = 
			stmt.executeUpdate();
//			if(result==0) {
//				throw new RuntimeException("아이디 또는 이메일이 중복됩니다.");
//			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("updateMember에서 예외 발생 :"+e.getMessage());
		}finally {
			closeConnection(con);
		}
	}
	
	public String getPassword(String userId) {
		Connection con = null;
		String pw = "";
		try {
			con = getConnection();
			String sql = "Select password from Member where userid=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				pw = rs.getString("password");
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("getPassword에서 예외 발생 :"+e.getMessage());
		}finally {
			closeConnection(con);
		}
		return pw;
	}
	public void deleteMember(String userId, String password) {
		Connection con = null;
		String pw = "";
		try 
		{
			con = getConnection();
			con.setAutoCommit(false);
			String sql = "select password from member where userid=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				pw = rs.getString("password");
			}else {
				throw new RuntimeException("아이디가 잘못 입력되었습니다.");
			}
			if(pw.equals(password)) {
				try {
					String sql2 = "delete from board where masterid in "
							+"(select bbsno from board where userid=?)";
					stmt = con.prepareStatement(sql2);
					stmt.setString(1, userId);
					stmt.executeUpdate();
					
					String sql3 = "delete from member where userId=?";
					stmt = con.prepareStatement(sql3);
					stmt.setString(1, userId);
					stmt.executeUpdate();
					con.commit();
				}catch(SQLException e) {
					con.rollback();
					throw new RuntimeException("삭제가 되지 않았습니다 : "+e.getMessage());
				}
			}else {
				throw new RuntimeException("비밀번호가 다릅니다.");
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("deleteMember에서 예외발생."+e.getMessage());
		}finally {
			closeConnection(con);
		}
		
	}
	
}
