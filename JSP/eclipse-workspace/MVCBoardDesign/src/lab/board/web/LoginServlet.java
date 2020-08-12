package lab.board.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lab.web.domain.MemberDAO;
import lab.web.domain.MemberVO;

@WebServlet("/Login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MemberDAO dao;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//action 여기서만 빠지면 섭섭해서 전부 만들어 놓음 - 없어도 상관 없지만(loginServlet의 get메서드는 로그아웃만 처리)
		//보통 시스템을 만들면 없어도 되는 곳이라도 규칙 유지를 위해 같은 형태를 유지 - action 파라미터 부여.
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		//로그아웃 처리. index로 다시 넘어감.
		if(action.equals("logout")) {
			session.invalidate();
			response.sendRedirect("/M3/index.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get에서 안 쓰기 때문에 post에서 생성.
		dao = new MemberDAO();
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		String url = "";
		try {
			//입력한 아이디로 비밀번호를 받아옴
			String dbpw = dao.getPassword(userid);
			//데이터베이스에 저장된 비밀번호와 내가 입력한 비밀번호가 같다면
			if(dbpw.equals(password)) {
				//세션에 이름, 아이디 저장 - 로그인 처리
				MemberVO mem = dao.selectMember(userid);
				session.setAttribute("name", mem.getName());
				session.setAttribute("userid", userid);
				//게시글 목록으로 바로 전송
				url = "/M3/Board.do?action=list";
				//redirect를 forward와 같이 쓰는 경우 한 번의 요청에 응답이 두 번 나가는 경우가 발생할 수 있기에
				response.sendRedirect(url);
				//redirect후 메서드 자체 종료.
				return;
			}else {
				//비밀번호가 다르다면 바로 로그인 페이지로 전송
				session.invalidate();
				url ="/login.jsp";
				//비밀번호가 맞지 않는 경우는 두 가지 - 아이디가 없거나 비밀번호가 다르거나
				if(dbpw.equals("")) {
					//비밀번호를 가져오는 메서드에서 ""를 리턴한다는 것은 쿼리는 정상실행 됐지만 아무 값도 없다는 것 - 아이디가 없음
					//MemberDAO.getPassword()주석 확인
					request.setAttribute("message", "아이디가 없습니다.");
				}else {
					//뭔가 다른 값이 들어왔다면 입력한 비밀번호가 데이터베이스의 그것과 다름
					request.setAttribute("message", "비밀번호가 다릅니다.");
				}
			}
		}catch(RuntimeException e) {
			session.invalidate();
			//sql자체에서 에러가 난 경우 바로 확인할 수 있게 에러 메세지 전송
			request.setAttribute("message", e.getMessage());
			url="/login.jsp";
		}
		RequestDispatcher disp = request.getRequestDispatcher(url);
		disp.forward(request, response);
	}
}

