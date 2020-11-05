package lab.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.Request;

import lab.web.model.MemberDAO;
import lab.web.model.MemberVO;

@WebServlet("/Login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    MemberDAO dao;
    
    public void init(ServletConfig config) {
    	dao = new MemberDAO();
    }
  
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		if(action.equals("logout")) {
			session.invalidate();
			response.sendRedirect("/index.jsp");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");
		String pw = request.getParameter("pw");
		HttpSession session = request.getSession();
		String url ="";
		try {
			String dbpw = dao.getPassword(userId);
			if(dbpw.equals(pw)) {
				MemberVO mem = dao.selectMember(userId);
				session.setAttribute("name", mem.getName());
				session.setAttribute("userId", userId);
				url = "/JDBC/Board.do?action=list";
			}else {
				session.invalidate();
				url="/Login.jsp";
				if(dbpw.equals("")) {
					request.setAttribute("message", "아이디가 없습니다.");
				}else {
					request.setAttribute("message", "비밀번호가 다릅니다.");
				}
			}
		}
		catch(RuntimeException e) {
			session.invalidate();
			request.setAttribute("message", e.getMessage());
			url="/Login.jsp";
		}
		RequestDispatcher disp = request.getRequestDispatcher(url);
		disp.forward(request, response);
	}
}


