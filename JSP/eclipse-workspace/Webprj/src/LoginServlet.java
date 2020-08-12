

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action.equals("login")) {
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String url = "";
			if("ehdqkd616".equals(id) && "0000".equals(pw)) {
				HttpSession session = request.getSession();
				session.setAttribute("name", "김연우");
				Cookie c1 = new Cookie("id",id);
				Cookie c2 = new Cookie("pw",pw);
				response.addCookie(c1);
				response.addCookie(c2);
				url = "/MyPage.jsp";
			}else {
				request.setAttribute("message", "아이디 또는 비밀번호가 다릅니다.");
				url = "/Login.jsp";
			}
			request.getRequestDispatcher(url).forward(request, response);
		}else if(action.equals("logout")) {
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("/Login.jsp");
		}
		
	}

}
