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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter({"/hr/*", "/Emp.do"})
public class LoginFilter implements Filter {

  
    public LoginFilter() {

    }


	public void destroy() {

	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest)request;
		HttpServletResponse hres = (HttpServletResponse)response;
		HttpSession session = hreq.getSession();
		if(session.getAttribute("userId")==null) {
			hreq.setAttribute("message", "로그인이 필요합니다.");
			hreq.getRequestDispatcher("/Login.jsp").forward(hreq, response);
			return;
		}
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
