package lab.web.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import lab.web.domain.MemberVO;

public class MemberDAO {
	
	static {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("드라이버 로드 성공");
		}catch (SQLException e) {
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

	private void closeConnection (Connection con) {
		if(con!=null) {
			try {
				con.close();
			}catch(SQLException e) {}
		}
	}
	//회원 가입을 위한 메서드. 멤버 객체를 받아 그대로 insert 쿼리 실행
	public void insert (MemberVO member) {
		Connection con = null;
		try {
			con = getConnection();
			String sql = "insert into member values(?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPassword());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getAddress());
			//insert, update, delete 등의 트랜잭션용 쿼리는 executeUpdate()로 처리.
			pstmt.executeUpdate();
		}catch(SQLException e) {
			//unique constraint .... 형태의 에러 메세지는 아이디의 중복 여부때 발생하기 때문에
			//메세지를 확실히 하기 위해서 if 조건문으로 처리
			if(e.getMessage().contains("unique")) {
				throw new RuntimeException("아이디가 중복됩니다.");
			}else {
				//해당 컬럼의 길이보다 긴 값(ex : userid는 20byte 10글자. 10글자보다 긴 아이디를 넣은 경우)이나
				//기타 다른 오류는 콘솔로 메세지 확인 - 팝업창엔 어느 메서드인지만 출력. ppt에서 봤듯이 메세지가 길어지는경우
				//또는 메세지에 ORA-00... 형식의 데이터베이스 에러코드가 포함되는 경우 팝업창에서 출력하지 않는 경우가 발생
				e.printStackTrace();
				throw new RuntimeException("MemberDAO.insert()예외발생-콘솔확인");
			}
		}finally {
			closeConnection(con);
		}
	}

	//회원정보조회를 위한 메서드
	public MemberVO selectMember(String userid) {
		Connection con = null;
		MemberVO member = new MemberVO();
		try {
			con = getConnection();
			String sql = "select * from member where userid=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				member.setUserId(userid);
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setAddress(rs.getString("address"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("MemberDAO.selectMember()예외발생-콘솔확인");
		}finally {
			closeConnection(con);
		}
		return member;
	}

	//회원정보 수정을 위한 메서드
	public void updateMember(MemberVO member) {
		Connection con = null;
		try {
			con = getConnection();
			String sql = "update member set email=?, address=?, name=?,"
					+ " password=? where userid=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getAddress());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getPassword());
			pstmt.setString(5, member.getUserId());
			//update 쿼리도 executeUpdate 이용.
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("MemberDAO.update()예외발생-콘솔확인");
		}finally {
			closeConnection(con);
		}
	}

	//삭제시에 호출될 비밀번호 체크 메서드. 쿼리의 테이블명만 다르지 글을 수정 및 삭제할때도 똑같은 코드 작성
	public String getPassword(String userid) {
		//""값이 리턴되었다는건 쿼리는 정상실행, 아이디가 없는 경우
		//밖에서 해당 값인지 아닌지로 구별.
		String pw = "";
		Connection con = null;
		try {
			con = getConnection();
			String sql = "select password from member where userid=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				pw = rs.getString("password");
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("MemberDAO.getPassword()예외발생-콘솔확인");
		}finally {
			closeConnection(con);
		}
		return pw;
	}

	//삭제 메서드, 아이디와 비밀번호를 모두 받아 체크
	public void deleteMember(String userid, String password) {
		Connection con=null;
		String pw = "";
		try {
			con = getConnection();
			//글도 지우고 회원정보도 지우는 등 여러 트랜잭션 쿼리를 실행할 것이기 때문에 도중에 한 쿼리에서 에러가 난 경우
			//나머지 쿼리들도 전부 rollback 해줘야 데이터 보존. JDBC는 기본적으로 AutoCommit 상태이기 때문에
			//AutoCommit을 풀고 시작.
			con.setAutoCommit(false);
			String sql = "select password from member where userid=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				pw = rs.getString("password");
			}else {
				throw new RuntimeException("아이디가 잘못 입력되었습니다.");
			}
			//먼저 비밀번호 확인 후 맞으면 삭제.
			if(pw.equals(password)) {
				try {
					//내가 쓴 글에 누군가가 댓글을 달았을 수 있음. 내가 탈퇴하면 내 글이 지워짐 - 내 글에 달린 다른사람의 답글들도 전부 지워야 함.
					//때문에 게시글 번호가 아닌 masterid를 기준삼아 삭제 - 내 글과 내 글의 댓글들까지 전부 삭제 가능.
					String sql2 = "delete from board where masterid in (select bbsno from board where userid=?)";	
					pstmt = con.prepareStatement(sql2);
					pstmt.setString(1, userid);
					pstmt.executeUpdate();
					//글 삭제 후에는 내 회원정보를 삭제
					String sql3 = "delete from member where userid=?";
					//preparedStatement 객체를 sql용, sql2용, sql3용 으로 계속 바꿔주는 모습을 볼 수 있음. 
					//preparedStatement 객체는 집어넣은 쿼리만 실행 - 새로운 sql문을 넣어야 해당 sql문을 실행
					//때문에 계속 새로운 쿼리문을 넣은 preparedStatement 객체를 다시 만듦. 여러쿼리를 한 메서드에서 실행하는 방법.
					pstmt = con.prepareStatement(sql3);
					pstmt.setString(1, userid);
					pstmt.executeUpdate();
					//전부 정상적으로 실행됐다면 Commit
					con.commit();
				}catch(SQLException e) {
					//하나라도 틀려서 에러가 발생했다면 rollback.
					con.rollback();
					throw new RuntimeException("삭제가 되지 않았습니다 : "+e.getMessage());
				}
			}else {
				throw new RuntimeException("비밀번호가 다릅니다.");
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("MemberDAO.deleteMember()예외발생-콘솔확인");
		}finally {
			closeConnection(con);
		}
	}
}
