package lab.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter("/Board.do")
public class UserFilter implements Filter {

    public UserFilter() {

    }

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest)request;
		String action = hreq.getParameter("action");
		if(action.contentEquals("update")||action.equals("delete")) {
			HttpSession session = hreq.getSession();
			String userId = (String)session.getAttribute("userId");
			if(hreq.getParameter("userId").equals("userId")) {
				chain.doFilter(request, response);
			}else {
				request.setAttribute("message", "본인 글이 아니면 수정 또는 삭제할 수 없습니다.");
				hreq.getRequestDispatcher("/error/error.jsp").forward(request, response);
			}
		}else {
			chain.doFilter(request, response);
		}
	}

}
