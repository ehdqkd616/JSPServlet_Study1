

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


@WebFilter("/*")
public class FirstFilter implements Filter {

  
    public FirstFilter() {
        // TODO Auto-generated constructor stub
    }

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("전처리 실행");
		System.out.println(request.getAttribute("message"));
		chain.doFilter(request, response);
		System.out.println("후처리 실행");
		System.out.println(request.getAttribute("message"));
	}


	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
