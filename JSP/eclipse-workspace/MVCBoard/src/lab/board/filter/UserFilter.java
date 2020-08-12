package lab.board.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//글의 수정 및 삭제 조건을 볼 때 작동하기 때문에 주소는 BoardServlet의 주소.
@WebFilter("/Board.do")
public class UserFilter implements Filter {

	public UserFilter() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest)request;
		String action = hreq.getParameter("action");
		//action이 update 또는 delete인 경우 수정이나 삭제 요청이 들어온 것.
		if(action.equals("update")||action.equals("delete")) {
			HttpSession session = hreq.getSession();
			String userid = (String)session.getAttribute("userid");
			if(hreq.getParameter("userid").equals(userid)) {
				//수정이나 삭제 요청 시에 session에 저장된 userid와 글에 저장된 userid 값을 비교
				//같으면 다음 컴포넌트로 넘김 - 수정/삭제 작업 실행
				chain.doFilter(request, response);
			}else {
				//다르면 메세지를 저장해 error.jsp로 넘김 - 메시지 내용을 담은 팝업창 출력
				//사용자는 확인 클릭 후 다시 글 내용 화면으로 돌아감.
				request.setAttribute("message", "본인 글이 아니면 수정 또는 삭제할 수 없습니다.");
				hreq.getRequestDispatcher("/error/error.jsp").forward(request, response);
			}
		}else {
			chain.doFilter(request, response);
		}
		
	}

}
