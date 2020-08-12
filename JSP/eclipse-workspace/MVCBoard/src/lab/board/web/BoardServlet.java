package lab.board.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lab.web.domain.BoardDAO;
import lab.web.domain.BoardVO;
import lab.web.domain.MemberDAO;
import lab.web.domain.MemberVO;
import lab.web.mail.SMTPAuth;


@WebServlet("/Board.do")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	BoardDAO dao;
	MemberDAO mdao;

	public BoardServlet() {
		super();

	}

	//사용할 DAO클래스는 Init에서 생성 - 드라이버로드까지 자동으로 실행
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init();
		dao = new BoardDAO();
		mdao = new MemberDAO();
	}


	public void destroy() {
		dao = null;
		mdao = null;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberVO member = new MemberVO();
		//action을 먼저 받아놓고 action이 무엇인지에 따라 실행 행동 결정
		String action = request.getParameter("action");
		String url = "";
		
		//action이 write = 게시글 쓰기를 누른 경우. 
		if("write".equals(action)) {
			request.setAttribute("message", "새 글 입력");
			url = url + "/board/write.jsp";
			//글 입력을 구분하기 위해 action 파라미터 값을 write_do로 저장 후 write 페이지로 넘김.
			request.setAttribute("action", "write_do");
		//action이 list = 게시글 목록 또는 [1][2] 등의 페이지를 누른 경우
		} else if("list".equals(action)) {
			//먼저 페이지 세팅을 해야 해당하는 페이지의 글 10개를 긁어오기 때문에 페이지 세팅 - 기본값 1.
			String pageStr = request.getParameter("page");
			int page = 1;
			if(pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
			//해당 페이지에 출력할 10개의 글을 lists로 긁어옴
			Collection<BoardVO> lists = dao.selectArticleList(page);
			//페이지로 list 전송.
			request.setAttribute("lists", lists);
			url = url + "/board/list.jsp";
			//글 목록 이외에도 현재 내가 몇 페이지까지 표시해야할지 알아야 함 - 글 전체 갯수가 131개라면 14페이지까지 표시해야함.
			//때문에 글 전체 갯수를 받아옴
			int bbsCount = dao.selectTotalBbsCount();
			double totalPage = 0;
			//totalPage는 전체 페이지 갯수. bbscount는 글 갯수. 글이 131개가 나왔다고 치자. 페이지는 14페이지까지 표시되어야 한다.
			if(bbsCount>0) {
				totalPage = bbsCount/10.0;
			}
			//131개가 나온경우 totalPage에는 13.1이 저장되어 있음 = 1로 나누어 떨어지지 않음. 1로 나누어 떨어지지 않는 다는 건
			//130, 120 등 10으로 나누어 떨어지는 숫자가 아니라는 뜻 = 1페이지 더 출력해야 하기에 +1 계산.
			if((totalPage%1) != 0) {
				totalPage = totalPage+1;
			}
			//총 몇 페이지를 출력해야 하는지, 내가 누른 페이지가 다시 전달 되어야 해당 페이지의 글들을 출력하기에 페이지까지 다시 전달.
			//자바에서 int형 이하끼리의 연산은 int형 - 때문에 위에서 10으로 나눈 경우 10.0이라고 써서 double형으로 바꿔주는 것.
			//여기선 페이지 갯수만 전달되면 되기에 다시 int형으로 변환 후 전달 = 자바에서 소수를 int형으로 바꾸면 무조건 소수자리를 버림.
			request.setAttribute("totalPageCount", (int)totalPage);
			request.setAttribute("page", page);
		//글 제목을 누른 경우 - 제목 링크에 action=view로 달려있음.
		} else if("view".equals(action)) {
			//해당 게시글 번호를 받아서
			String bbsnoStr = request.getParameter("bbsno");
			//숫자로 바꾼 뒤에
			int bbsno = Integer.parseInt(bbsnoStr);
			//글 내용을 가지고 옴.
			BoardVO board = dao.selectArticle(bbsno);
			//가지고 온 뒤에 조회수를 올리면 가지고 오다가 에러가 나는 경우 조회수가 올라가지 않음. 간단한 에러처리.
			dao.updateReadCount(bbsno);
			//내용을 th태그로 표시 - input 태그로 표시하는 경우 자바의 \n을 개행 형태 그대로 표시. th 태그로 표시하는 경우
			//html 태그에서의 개행 표시는 <br> 태그 = 글 내용 사이사이에 \n이 들어가 있는 상태로 출력됨.
			//따라서 String의 replaceAll 메서드를 이용해 내용속 \n을 <br>로 전부 치환 - 글 내용에서 행 바꿈 형태를 유지.
			if(board.getContent() != null) {
				board.setContent(board.getContent().replaceAll("\n", "<br>"));
			}
			//해당 글을 저장
			request.setAttribute("board", board);
			request.setAttribute("message", "글 상세보기");
			url = url + "/board/view.jsp";
		//글 상세보기 화면에서 답글을 누른 경우 실행되는 코드
		} else if("reply".equals(action)) {
			String bbsno = request.getParameter("bbsno");
			//해당 글 내용을 받아와서
			BoardVO board = dao.selectArticle(Integer.parseInt(bbsno));
			//글 제목에 [re] 추가 - 답글인 것 표시.
			board.setSubject("[re]"+board.getSubject());
			//내용에 기존내용
			//     ------
			//    이제 쓸 내용  
			//형태로 기존내용과 답글을 구분해 쓸 수 있게 보여줌. 답글 페이지를 로드해보시면 무슨 말인지 알 수 있습니다.
			board.setContent(board.getContent()+"\n---------\n");
			//제목에 [re] 달고 내용에 ------줄 달아준 상태로 write페이지로 보내면 사용자는 답글 화면을 보고 있는 형태.
			request.setAttribute("board", board);
			request.setAttribute("message", "댓글 입력");
			//action을 reply_do로 보내야 post에서 답글 처리 가능
			request.setAttribute("action", "reply_do");
			url = url + "/board/write.jsp";
		//글 상세보기 화면에서 수정을 누른 경우 실행되는 코드
		} else if("update".equals(action)) {
			//글 번호 가지고 와서
			String bbsnoStr = request.getParameter("bbsno");
			int bbsno = Integer.parseInt(bbsnoStr);
			//내용 가지고 옴.
			BoardVO board = dao.selectArticle(bbsno);
			//내용 페이지로 전송해주고
			request.setAttribute("board", board);
			request.setAttribute("message", "글 수정 화면");
			//action만 update_do로 보내주면 서블릿 post에서 구분 가능
			request.setAttribute("action", "update_do");
			url = url+"/board/write.jsp";
		//글 상세보기 화면에서 삭제를 누른 경우 실행되는 코드
		} else if("delete".equals(action)) {
			//해당 글의 게시글 번호, replynumber(답글인지의 여부)를 받아와서
			String bbsnoStr = request.getParameter("bbsno");
			String replynoStr = request.getParameter("replynumber");
			//그대로 삭제페이지에 전달 - post에서 다시 넘겨 받을 수 있도록.
			request.setAttribute("bbsno", bbsnoStr);
			request.setAttribute("replynumber", replynoStr);
			request.setAttribute("action", "delete_do");
			url = url + "/board/delete.jsp";
		//마이페이지를 누른 경우 실행되는 코드.
		} else if("member".equals(action)) {
			//내가 쓴 총 글 갯수를 가져오고
			int count = dao.selectCount((String)session.getAttribute("userid"));
			//회원 정보를 가지고 와서
			member = mdao.selectMember((String)session.getAttribute("userid"));
			//해당 페이지로 넘겨줌.
			request.setAttribute("member", member);
			request.setAttribute("count", count);
			url= url + "/board/member.jsp";
		//마이페이지에서 목록 확인을 누른 경우 실행되는 코드
		}else if("memberList".equals(action)) {
			//list때와 똑같음 - 바뀌는건 메서드 뿐.
			String pageStr = request.getParameter("page");
			int page = 1;
			if(pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
			//내가 쓴 글만 조회하기 때문에 session에서 userid를 가져와서
			String userid = (String)session.getAttribute("userid");
			//해당 아이디와 페이지로 내가 쓴 글 20개씩 가져옴
			Collection<BoardVO> memberList = dao.memberList(userid, page);
			request.setAttribute("lists", memberList);
			//목록조회와 같게 페이지를 계산해서 보냄 - 20개씩 보내기에 계산 방식이 조금 달라짐.
			int bbsCount = dao.selectCount(userid);
			double totalPage = 0;
			if(bbsCount>0) {
				totalPage = bbsCount/20.0;
			}
			if((totalPage%1) != 0) {
				totalPage = totalPage+1;
			}
			request.setAttribute("totalPageCount", (int)totalPage);
			request.setAttribute("page", page);
			url= url+"/board/memberlist.jsp";
		//action이 위에 있는 것들 중 없는 경우 - 뭔가 코드가 잘못 입력되어 action이 잘못 들어오고 있는 경우
		}else {
			//action이 무엇으로 들어왔나 확인!
			request.setAttribute("message", "잘못된 명령 : doGet-" + action);
			url = url + "/error/error.jsp";
		}
		//위에서 각 if 경우들에 따라 url 변수를 결과를 표시할 페이지로 바꿔놓음 - if문들은 여러개 중에 하나만 실행
		//해당 if문이 실행되면 url은 해당 결과를 표시할 페이지의 주소로 바뀌어 있음 - forward로 보내기만 하면 됨.
		RequestDispatcher disp = request.getRequestDispatcher(url);
		disp.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//마찬가지로 action 받아놓고 시작
		String action = request.getParameter("action");
		String url = "";
		HttpSession session = request.getSession();
		//글 입력을 하는 경우
		//get에서 글 입력시에 write_do, 수정시에 update_do, 댓글입력시에 reply_do로 action 파라미터를 보내놓음
		//같은 페이지에서 같은 post방식의 요청을 같은 Board.do 주소로 보내지면 action 파라미터는 처음부터 달라져 있는 상태
		//post에서 action 파라미터를 통해 구분 가능.
		if("write_do".equals(action)) {
			//파라미터들 넘겨받아서 글 입력 메서드 실행
			String userId = (String)session.getAttribute("userid");
			String password = request.getParameter("password");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");

			BoardVO board = new BoardVO();
			board.setUserId(userId);
			board.setPassword(password);
			board.setContent(content);
			board.setSubject(subject);

			dao.insertArticle(board);
			//글을 입력했으면 목록을 봐야 입력되었는지 확인하기 제일 쉬움 - 목록을 조회하는 주소로 재요청 보냄.
			url = "/MVC/Board.do?action=list";
			response.sendRedirect(url);
			//밑에 forward가 있기에 redirect 실행 후 dopost 메서드 종료.
			return;
		//답글 입력을 하는 경우
		} else if("reply_do".equals(action)) {
			//답글 입력을 할 시에는 넘겨줘야할 파라미터들이 많음 - 답글 메서드, 글 입력 페이지 확인.
			//해당 파라미터들을 받아와 답글 입력.
			String userid = (String)session.getAttribute("userid");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String password = request.getParameter("password");
			int bbsno = Integer.parseInt(request.getParameter("bbsno"));
			int masterid = Integer.parseInt(request.getParameter("masterid"));
			int replynumber = Integer.parseInt(request.getParameter("replynumber"));
			int replystep = Integer.parseInt(request.getParameter("replystep"));
			BoardVO board = new BoardVO();
			board.setBbsno(bbsno);
			board.setUserId(userid);
			board.setSubject(subject);
			board.setContent(content);
			board.setPassword(password);
			board.setMasterId(masterid);
			board.setReplyNumber(replynumber);
			board.setReplyStep(replystep);
			dao.replyArticle(board);
			//답글도 입력 후 목록 조회를 재요청 - 글이 써졌는지 확인 절차.
			response.sendRedirect("/MVC/Board.do?action=list");
			return;
		//글 수정을 하는 경우
		} else if("update_do".equals(action)) {
			//입력한 비밀번호를 가지고 와서
			String password = request.getParameter("password");
			String bbsnoStr = request.getParameter("bbsno");
			int bbsno = Integer.parseInt(bbsnoStr);
			//해당 글의 비밀번호를 가져온 다음 
			String dbpw = dao.getPassword(bbsno);
			//둘이 일치해야 수정 가능. 일치하지 않다면 수정 불가.
			if(dbpw.equals(password)) {
				BoardVO board = new BoardVO();
				board.setBbsno(bbsno);
				//수정은 제목, 내용만 하면 됨.
				board.setSubject(request.getParameter("subject"));
				board.setContent(request.getParameter("content"));
				dao.updateArticle(board);
				//수정은 목록이 아니고 해당 글의 상세내용을 보아야 하기에 상세내용을 보는 주소를 재요청.
				//글의 상세내용을 볼때는 bbsno가 필요함. 이 링크는 글의 제목을 클릭했을 때의 주소와 동일.
				url = "/MVC/Board.do?action=view&bbsno="+bbsno;
				response.sendRedirect(url);
				return;
			}else {
				//비밀번호 다른 경우 error.jsp를 통해 메세지 확인. 팝업창 누르면 다시 수정내용 입력페이지로 돌아감.
				request.setAttribute("message", "비밀번호가 다릅니다. 수정되지 않았습니다.");
				url = url+"/error/error.jsp";
			}
		//삭제를 하는 경우
		} else if("delete_do".equals(action)) {
			//수정과 같이 비밀번호가 맞아야 진행됨
			String password = request.getParameter("password");
			int bbsno = (Integer.parseInt(request.getParameter("bbsno")));
			int replynumber = (Integer.parseInt(request.getParameter("replynumber")));
			String dbpw = dao.getPassword(bbsno);
			//비밀번호가 일치하는 경우 게시글 번호와 replynumber를 넣어주면
			if(dbpw.equals(password)) {
				//알아서 글이면 답글까지, 답글이면 답글만 지워줌.
				dao.deleteArticle(bbsno, replynumber);
				//삭제는 글이 없어졌을 것이므로 목록조회로 확인.
				url = "/MVC/Board.do?action=list";
				response.sendRedirect(url);
				return;
			}else {
				request.setAttribute("message", "비밀번호가 다릅니다. 삭제할 수 없습니다.");
				url = url + "/error/error.jsp";
			}
		//메일 전송 버튼을 누른 경우
		} else if("contact_do".equals(action)) {
			//보낸 사람, 이름, 제목, 내용을 가져와서
			String from = request.getParameter("from");
			String name = request.getParameter("name");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			//메일 전송 메서드에 넣으면 메서드가 메일 전송 - 성공했다면 true, 실패했다면 false이기에 if문 속에 넣어줌
			if(SMTPAuth.sendEmail(from, name, subject, content)) {
				//성공한 경우 성공했다는 알림창 출력 - error.jsp를 통해서 출력할 수 있지만 해당 페이지는 확인을 누르면 무조건 뒤로가기 실행.
				//이 때는 확인을 누르면 index.jsp 페이지로 보낼 것이기에 페이지를 하나 더 만드는건 비효율적이고 서블릿에서 간단하게 출력.
				//출력할 타입(페이지), 인코딩 설정하고
				response.setContentType("text/html;charset=UTF-8");
				//브라우저에 코드를 출력해줄 출력스트림을 만들어서
				PrintWriter out = response.getWriter();
				//해당 코드 작성 - 자바스크립트를 이용한 팝업창 생성. 확인을 누르면 index.jsp로 넘어감. 
				out.println("<script>alert(\"메일이 전송되었습니다!\"); location.href='/MVC/index.jsp';</script>");
				return;
			}
		//get과 마찬가지로 action이 다른 값이 들어온 경우. 어디서 에러가 났는지 확인할 수 있도록 보여줌.
		} else {
			
		}
		//get과 마찬가지로 forward는 맨 마지막에 실행.
	
	}

}
