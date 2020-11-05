package lab.web.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {

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
		}catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	private void closeConnection(Connection con) {
		if(con!=null) {
			try {
				con.close();
			}catch(SQLException e) {}
		}
	}

	//글을 입력할 메서드
	public void insertArticle(BoardVO board) {
		Connection con = null;
		//게시글 번호는 우리가 입력하지 않음 - 알아서 자동으로 입력(최대값+1)
		//첫 글이 없는 경우는 null이 나오므로 해당 경우에 0부터 출발하기 위해 nvl 사용
		String sql1 = "select nvl(max(bbsno),0) from board";
		int bbsno = 0;
		String sql2 = "insert into board" + "(bbsno, userid, password, subject, content,"+
				"writedate, masterid, readcount, replynumber, replystep)"+
				"values(?,?,?,?,?,SYSDATE,?,0,0,0)";
		try {
			con = getConnection();
			//첫 번째 sql 실행
			PreparedStatement pstmt = con.prepareStatement(sql1);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			bbsno = rs.getInt(1)+1;
			//결과(게시글 번호)를 얻은 후 두번째 쿼리 사용을 위해 sql2를 담은 새로운 preparedStatement 객체 생성
			pstmt = con.prepareStatement(sql2);
			pstmt.setInt(1, bbsno);
			pstmt.setString(2, board.getUserId());
			pstmt.setString(3, board.getPassword());
			pstmt.setString(4, board.getSubject());
			pstmt.setString(5, board.getContent());
			pstmt.setInt(6, bbsno);
			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("BoardDAO.insertArticle()예외발생-콘솔확인");
		}finally {
			closeConnection(con);
		}
	}

	//목록 출력 메서드
	public Collection<BoardVO> selectArticleList(int page){
		Connection con = null;
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
					//해당 rownum을 1번부터가 아닌 11번부터 20번, 21번부터 30번 등 자유롭게 활용하기 위해 한 번 더 서브쿼리로 추출
		String sql = "select bbsno, name, subject, writedate, readcount, rnum from (" +
				//정렬한 쿼리에 rownum 부여 - 페이지별 10개씩의 글들을 출력하기 위함
				"select bbsno, name, subject, writedate, readcount, rownum as rnum from ("+
				//목록 출력을 위해 먼저 정렬하는 쿼리
				"select bbsno, name, subject, writedate, readcount from board b "+
				//이름은 아이디로 조인해서 가지고 옴
				"join member m on b.userid=m.userid "+
				"order by masterid desc, replynumber, replystep)) "+
				//정렬 하는 쿼리
				"where rnum between ? and ?" ;
		//1페이지일때 start=1, end=10. 2페이지일때 start=11, end=20. 
		int start = (page-1) * 10 +1;
		int end = start+9;
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardVO board = new BoardVO();
				//목록에서 보여줄 건 이름, 제목, 작성일, 조회수. 게시글번호는 글 제목 클릭시 상세내용을 보여주기 위해 필요.
				board.setBbsno(rs.getInt("bbsno"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setWriteDate(rs.getDate("writedate"));
				board.setReadCount(rs.getInt("readcount"));
				list.add(board);
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("BoardDAO.selectArticleList()예외발생-콘솔확인");
		}finally {
			closeConnection(con);
		}
		return list;
	}

	//게시글 내용을 가져오는 메서드.
	public BoardVO selectArticle(int bbsno) {
		Connection con = null;
		BoardVO board = null;
			//우리가 출력할 건 제목, 작성자 이름, 제목, 내용, 조회수, 작성일 뿐이지만
			//해당 화면에서 답글 작성 및 수정, 삭제로 이동하게 됨 - userid, masterid, replynumber, replystep 필요
			//때문에 같이 받아와서 페이지에 숨겨놓은 상태로 출력은 제목, 이름 등만 출력함.
		String sql = "select bbsno, name, b.userid, subject, content, readcount, "
				+ "writedate, masterid, replynumber, replystep"
				+ " from board b join member m on b.userid=m.userid where bbsno=?";
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bbsno);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				board = new BoardVO();
				board.setBbsno(rs.getInt("bbsno"));
				board.setName(rs.getString("name"));
				board.setUserId(rs.getString("userid"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setReadCount(rs.getInt("readcount"));
				board.setWriteDate(rs.getDate("writedate"));
				board.setMasterId(rs.getInt("masterid"));
				board.setReplyNumber(rs.getInt("replynumber"));
				board.setReplyStep(rs.getInt("replystep"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("BoardDAO.selectArticle()예외발생-콘솔확인");
		}finally {
			closeConnection(con);
		}
		return board;
	}
	
	//글 제목 누르면 바로 조회수 부터 올려주는 메서드. 조회수 값을 +1로 수정해주면 됨.
	public void updateReadCount(int bbsno) {
		Connection con = null;
		String sql = "update board set readcount=readcount+1 where bbsno=?";
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bbsno);
			pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("BoardDAO.updateReadCount()예외발생-콘솔확인");
		}finally {
			closeConnection(con);
		}
	}

	//글의 비밀번호를 얻어오는 메서드.
	public String getPassword(int bbsno) {
		Connection con = null;
		String password = "";
		String sql = "select password from board where bbsno=?";
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,bbsno);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				password = rs.getString("password");
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("BoardDAO.getPassword()예외발생-콘솔확인");
		}finally {
			closeConnection(con);
		}
		return password;
	}

	//답글을 달아주는 메서드. 그냥 글과 답글은 여러 컬럼값들이 달라지기 때문에 메서드를 따로 생성.
	public void replyArticle(BoardVO board) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			//여러개의 트랜잭션 쿼리를 동시에 실행하기 위해 autoCommit을 풀고 시작.
			con.setAutoCommit(false);
			//먼저 기존에 작성되어 있는 답글들에게 니가 먼저 작성되었다를 표시 - replynumber 1 증가.
			String sql1 = "update board set replynumber=replynumber+1 where masterid=? and replynumber >?";
			pstmt = con.prepareStatement(sql1);
			pstmt.setInt(1, board.getMasterId());
			pstmt.setInt(2, board.getReplyNumber());
			pstmt.executeUpdate();
			//게시글 번호 입력을 위해 최대값 얻어옴.
			String sql2 = "select max(bbsno) from board";
			pstmt = con.prepareStatement(sql2);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board.setBbsno(rs.getInt(1)+1);
			}
			//해당 답글 입력.
			String sql3 = "insert into board values(?,?,?,?,?,SYSDATE,?,0,?,?)";
			pstmt = con.prepareStatement(sql3);
			pstmt.setInt(1, board.getBbsno());
			pstmt.setString(2, board.getUserId());
			pstmt.setString(3, board.getPassword());
			pstmt.setString(4, board.getSubject());
			pstmt.setString(5, board.getContent());
			pstmt.setInt(6, board.getMasterId());
			//기존 글(답글) 보다 replynumber와 replystep이 1씩 증가해야 해당 글(답글)의 답글인 형태를 표현 가능
			pstmt.setInt(7, board.getReplyNumber()+1);
			pstmt.setInt(8, board.getReplyStep()+1);
			pstmt.executeUpdate();
			//모든 쿼리 정상 실행 완료 시 commit.
			con.commit();
		}catch(Exception e) {
			try {
				con.rollback();
			}catch (SQLException e1) {}
			e.printStackTrace();
			throw new RuntimeException("BoardDAO.replyArticle()예외발생-콘솔확인");
		}finally {
			closeConnection(con);
		}
	}

	//글 삭제. 답글인지를 구분해야 하기에 replynumber를 같이 넘겨받음.
	public void deleteArticle(int bbsno, int replynumber) {
		String sql="";
		Connection con = null;
		try {
			con = getConnection();
			if(replynumber>0) {
				//replynumber가 0보다 크면 이 글은 무조건 답글, 따라서 이 글만 지우면 됨.
				sql = "delete from board where bbsno=?";
			}else {
				//replynumber가 0이라면 이 글은 답글일 수 없음. 그렇다면 해당 글에 달려있는 답글들까지 모두 지워야 하기에
				//masterid를 기준으로 삭제 = 글과 모든 답글까지 전부 삭제.
				sql = "delete from board where masterid=?";
			}
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bbsno);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("BoardDAO.deleteArticle()예외발생-콘솔확인");
		}finally {
			closeConnection(con);
		}
	}
	
	//글을 수정하는 메서드.
	public void updateArticle(BoardVO board) {
		Connection con = null;
		String sql = "update board set "+
				"subject=?, content=?, writedate=SYSDATE "+
				"where bbsno=?";
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getSubject());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getBbsno());
			pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("BoardDAO.updateArticle()예외발생-콘솔확인");
		}finally {
			closeConnection(con);
		}
	}

	//페이지 처리를 위해 글의 총 개수를 가져오는 메서드. ex: 총 글 갯수 131개 = 페이지수 14개.
	public int selectTotalBbsCount() {
		Connection con = null;
		String sql = "select count(bbsno) from board";
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int bbsCount = rs.getInt(1);
			return bbsCount;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("BoardDAO.selectTotalBbsCount()예외발생-콘솔확인");
		}finally {
			closeConnection(con);
		}
	}

	//마이페이지에서 내가 쓴 글이 총 몇 개인지(답글포함) 보여주기 위해 사용하는 메서드.
	public int selectCount(String userid) {
		Connection con = null;
		//해당 userid로 작성된 게시글을 count 하면 됨.
		String sql = "select count(bbsno) from board where userid=?";
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			return count;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("BoardDAO.selectCount()예외발생-콘솔확인");
		}finally {
			closeConnection(con);
		}
	}

	//내가 쓴 글만 가져오는 메서드. 목록과 똑같은 쿼리. 다른점은 userid를 받아 내가 쓴 글들만 가져온다는 where절만 추가하면 됨.
	//where b.userid=? 가 조건을 지정하는 쿼리. 조건 지정한 글만 가져와서 게시글 번호 역순으로 정렬 후 rownum 부여
	public Collection<BoardVO> memberList(String userid, int page){
		Connection con = null;
		String sql = "select rnum, bbsno, name, subject, readcount, writedate from " + 
				"(select rownum rnum, bbsno, name, subject, readcount, writedate from " + 
				"(select bbsno, name, subject, readcount, writedate from board b " +
				"join member m on b.userid = m.userid where b.userid=? order by bbsno desc))" + 
				"where rnum between ? and ?";
		ArrayList<BoardVO> list = new ArrayList<>();
		//해당 글들은 한 페이지에 20개씩 보여줄 것. 때문에 계산식 변경.
		int start = (page-1) * 20 +1;
		int end = start+19;
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setBbsno(rs.getInt("bbsno"));
				board.setName(rs.getString("name"));
				board.setWriteDate(rs.getDate("writedate"));
				board.setSubject(rs.getString("subject"));
				board.setReadCount(rs.getInt("readcount"));
				list.add(board);
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("BoardDAO.memberList()예외발생-콘솔확인");
		}finally {
			closeConnection(con);
		}
		return list;
	}
}
