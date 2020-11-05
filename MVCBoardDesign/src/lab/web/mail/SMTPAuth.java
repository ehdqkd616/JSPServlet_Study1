package lab.web.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//Authenticator의 PasswordAuthentication 메서드가 계정 로그인에 필요.
//때문에 상속해서 재정의해서 만들어야 함.
public class SMTPAuth extends Authenticator{

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		//계정 정보를 담은 메서드를 재정의 해야 함. 해당 메서드를 통해 계정과 비밀번호를 SMTP 서버에 입력.
		//아이디와 비밀번호 위치에 여러분의 계정 아이디/비밀번호를 적어주세요.
		return new PasswordAuthentication("ehdqkd61616","a132451!");
	}

	public static boolean sendEmail(String from, String name, String subject, String content) {
		//SMTP 서버에 접속할 내용을 담은 Properties 객체 생성, Map과 비슷하다고 생각하시면 됩니다.
		//데이터를 저장할 용도로 사용.
		Properties pro = new Properties();
		//gmail을 이용하기에 host는 gmail. naver를 이용하고 싶다면 값을 stmp.naver.com으로 변경!
		pro.put("mail.smtp.host", "smtp.naver.com");
		//gmail SMTP 서버의 port. 네이버는 587.
		pro.put("mail.smtp.port", "465");
		//권한, ssl 보안접속등의 허용인데 gmail 같은 경우 무조건 다 true.
		pro.put("mail.smtp.auth", "true");
		pro.put("mail.smtp.ssl.enable", "true");
		pro.put("mail.smtp.starttls.enable", "true");
		try {
			//계정 정보를 가지고 있는 객체 생성 - PasswordAuthentication 메서드를 통해 호출
			SMTPAuth auth = new SMTPAuth();
			//계정 정보와 서버 정보를 담은 세션객체 생성 - 해당 계정으로 해당 SMTP 서버에 접속함.
			Session session = Session.getInstance(pro, auth);
			//해당 SMTP 서버에 접속 후 메일 기능을 이용하기 위해 만들어놓은 MimeMessage 사용.
			MimeMessage msg = new MimeMessage(session);
			//메일 제목 설정
			msg.setSubject(subject);
			//보낸 사람을 주소 형태로 만들어야 함, 때문에 보낸사람, 이름 순으로 받음
			Address froms = new InternetAddress(from, name);
			//보낸 사람 설정. gmail에서 spam 방지 차원으로 인증된 계정만 사용하기 때문에 무조건 여러분의
			//아이디로 메일을 보냅니다. 인증되지 않은 계정으로 메일을 마구 보내는 경우 스팸메일이기 때문입니다.
			//여기서 설정해도 내가 나에게 메일을 보내는 형태로 메일이 보내지겠지만, 여러분이 SMTP 서버를 여러분걸로
			//하나 구축하는 경우는 해당 설정을 해제할 수 있기에 보낸사람을 바로 적을 수 있게 될 겁니다. 
			//이 상황은 코드의 잘못이 아니고 구글의 정책 문제 때문입니다.
			msg.setFrom(froms);
			//받을 사람. 여러분의 메일 주소를 적어주세요.
			Address tos = new InternetAddress("gctserf@gmail.com");
			//메일에 받을 사람 설정.
			msg.addRecipient(Message.RecipientType.TO, tos);
			//메일의 내용 설정. 보내는 사람이 무조건 나로 보이기에 내용에 보내는 사람의 메일 주소, 이름, 내용 전부 출력.
			msg.setContent("보내는 분:"+from
					+"<br>성함 :"+name+
					"<br>"+content, "text/html;charset=UTF-8");
			//메일을 전송하는 메서드.
			Transport.send(msg);
		//메일을 보내다가 에러가 나는 경우 확인할 수 있도록 만든 예외처리.
		}catch(AddressException e) {
			e.printStackTrace();
			throw new RuntimeException("주소가 잘못되었습니다. 콘솔 확인");
		}catch(MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("메일을 보내는 과정에서 문제가 생겼습니다. 콘솔 확인");
		}
		//정상적으로 전송 되었다면 True 리턴 - 비정상적이라면 false를 리턴할 일 없이
		//무조건 위에서 RuntimeException 발생시킴 - errorRun.jsp로 가게 됨.
		return true;
	}

}
