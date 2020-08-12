package lab.board.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

//해당 프로젝트의 모든 요청에 대해 적용
@WebFilter("/*")
public class EncoFilter implements Filter {

  
    public EncoFilter() {
      
    }
    
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//필터는 따로 순서를 지정하지 않는 이상 서버가 인스턴스를 만들어 실행하기 때문에 알파벳 순대로 실행
		//이 필터가 가장 먼저 실행됨. System.out.println으로 확인해 보세요.
		//모든 요청에 대해 UTF-8 인코딩 적용
		request.setCharacterEncoding("UTF-8");
		//다음 필터 또는 JSP or Servlet으로 요청, 응답을 넘겨줌.
		chain.doFilter(request, response);
	}

}
