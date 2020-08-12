package lab.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lab.web.model.MemberVO;
import lab.web.model.MemberDAO;

@WebServlet("/Member.do")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberDAO dao;
    public MemberServlet() {
        super();
        dao = new MemberDAO();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		MemberVO mem = new MemberVO();
		String url = "";
		if("insert".equals(action)) {
			request.setAttribute("action", action);
			request.setAttribute("message", "회원 가입");
			url = "/InsertMember.jsp";
		}else if("update".equals(action)) {
			try {
				mem = dao.selectMember(userId);
				request.setAttribute("mem", mem);
				request.setAttribute("action", action);
				request.setAttribute("mesage", "회원 정보 수정");
			}catch(RuntimeException e) {
				request.setAttribute("message", e.getMessage());
			}
			url = "/InsertMember.jsp";
		}else if(action.equals("delete")) {
			try {
				request.setAttribute("action", action);
			}catch(RuntimeException e) {
				request.setAttribute("message",e.getMessage());
			}
			url="/memberDelete.jsp";
		}
		RequestDispatcher disp = request.getRequestDispatcher(url);
		disp.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String userId = request.getParameter("userId");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String url="";
		MemberVO mem = new MemberVO();
		mem.setUserId(userId);
		mem.setName(name);
		mem.setPassword(password);
		mem.setEmail(email);
		mem.setAddress(address);
		if(action.equals("insert")) {
			dao.insertMember(mem);
			request.setAttribute("message", "회원가입성공");
			url="/Login.jsp";
		}else if(action.equals("update")) {
			dao.updateMember(mem);
			response.sendRedirect("/Board.do?action=member");
			return;
		}else if(action.equals("delete")) {
			dao.deleteMember(userId, password);
			request.setAttribute("message", "회원 정보 삭제 완료");
			HttpSession session = request.getSession();
			session.invalidate();
			url="/Login.jsp";
		}
		RequestDispatcher disp = request.getRequestDispatcher(url);
		disp.forward(request, response);
	}
}

