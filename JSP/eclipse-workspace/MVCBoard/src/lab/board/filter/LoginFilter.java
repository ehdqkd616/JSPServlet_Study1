package lab.board.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//필터, 서블릿 등은 모두 주소를 여러개 가지게 할 수 있음 - {"주소1","주소2"}
//BoardServlet으로 가는 요청과 board 폴더 안으로 가는 모든 요청을 하이재킹
@WebFilter({"/Board.do", "/board/*"})
public class LoginFilter implements Filter {

	public LoginFilter() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		HttpServletResponse res = (HttpServletResponse)response;
		String userid = (String)session.getAttribute("userid");
		if(userid == null) {
			//userid가 null인 경우 로그인 안 된 상태. 해당 상태일 때 액션이 contact_do인 경우는
			//메일 전송 요청이어서 돌려보내지 않고 들여보내줌
			if(req.getParameter("action").equals("contact_do")) {

			}else{
			//메일 전송 요청이 아닐 시 전부 돌려보냄.
				res.sendRedirect("/MVC/login.jsp");
				return;
			}
		}
		chain.doFilter(request, response);
	}

}
