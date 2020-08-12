package lab.board.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lab.web.domain.MemberDAO;
import lab.web.domain.MemberVO;

@WebServlet("/Member.do")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MemberDAO dao;
	public MemberServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		dao = new MemberDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//무슨 요청인지 알기 위한 파라미터. 항상 받아놓고 시작.
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		//로그인 된 상태라면 아이디를 세션에서 받아오면 됨
		String userid = (String)session.getAttribute("userid");
		MemberVO member = new MemberVO();
		String url="";
		//회원 가입 요청이 들어온 경우 - 회원 가입과 회원 수정은 같은 페이지에서 처리할 것. 서블릿에서 구분해야 함
		//때문에 get방식에서 action 파라미터를 가입시엔 insert, 수정시에는 update로 실어보냄 - post로 갔을때 구분 가능
		//회원 가입 요청 클릭 - 서블릿 - 회원페이지(action=insert) - post에서 insert로 인식 가능 - 가입처리
		//회원 정보 수정 요청 클릭 - 서블릿 - 회원페이지(기존 회원정보+action=update) - post에서 update로 인식 가능 - 수정처리
		if(action.equals("insert")) {
			//가입요청일때는 action만 insert로 전달하면 됨, 메세지도 같이 전달
			request.setAttribute("action", action);
			request.setAttribute("message", "회원 가입");
			//회원 정보를 입력하는 페이지
			url="/memberform.jsp";
		}else if(action.equals("update")) {
			//수정 요청인 경우 id를 받아 해당 회원정보를 페이지에 띄워줌 - 사용자가 보면서 입력!
			try {
				//회원정보 얻어옴
				member = dao.selectMember(userid);
				//회원정보도 같이 보냄
				request.setAttribute("member", member);
				//action을 update로 해야 입력/수정 처리는 하는 post 메서드에서 어떤 요청을 통해 왔는지 구분 가능
				request.setAttribute("action", action);
				request.setAttribute("message", "회원 정보 수정");
			}catch(RuntimeException e) {
				request.setAttribute("message", e.getMessage());
			}
			//같은 페이지로 보냄 - !다른 요청을 수행할 수 있는 이유는 action을 다르게 보내니까!
			url = "/memberform.jsp";
		}else if(action.equals("delete")) {
			//삭제시에는 비밀번호만 입력할 페이지로 action만 전달 - post에서 구분 가능할 수 있게.
			try {
				request.setAttribute("action", action);
			}catch(RuntimeException e) {
				request.setAttribute("message", e.getMessage());
			}
			//삭제 페이지는 정보 입력창과 다르게 로그인 시에만 들어갈 수 있음 - board폴더 내에 존재.
			url="/board/memberDelete.jsp";
		}
		RequestDispatcher disp = request.getRequestDispatcher(url);
		disp.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//입력 및 수정 둘 다 아이디, 비밀번호, 이름, 이메일, 주소 등을 받아 처리해야 함.
		//때문에 그냥 처음부터 받아서 MemberVO로 만들어 놓고 시작.
		//setCharacterEncoding이 빠진 이유 - filter에서 전부 처리중! 원래는 항상 적혀야 함.
		String action = request.getParameter("action");
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String url="";
		MemberVO member = new MemberVO();
		member.setUserId(userid);
		member.setPassword(password);
		member.setName(name);
		member.setEmail(email);
		member.setAddress(address);
		//가입 요청
		if(action.equals("insert")) {
			//가입 메서드 실행 후 로그인 페이지로 돌려보냄.
			dao.insert(member);
			request.setAttribute("message", "회원가입성공");
			url="/login.jsp";
		//수정 요청
		}else if(action.equals("update")) {
			//수정 메서드 실행 후 회원정보조회 페이지로 돌려보냄.
			dao.updateMember(member);
			response.sendRedirect("/MVC/Board.do?action=member");
			return;
		//삭제 요청
		}else if(action.equals("delete")) {
			//아이디와 비밀번호를 입력받아 삭제 
			dao.deleteMember(userid, password);
			request.setAttribute("message", "회원 정보 삭제 완료");
			HttpSession session = request.getSession();
			session.invalidate();
			//삭제 후 로그아웃처리 후에 로그인 페이지로 돌려보냄.
			url="/login.jsp";
		}
		RequestDispatcher disp = request.getRequestDispatcher(url);
		disp.forward(request, response);
	}

}


