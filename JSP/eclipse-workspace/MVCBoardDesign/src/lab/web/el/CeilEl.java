package lab.web.el;

public class CeilEl {

	//EL커스텀 함수는 static 메서드만 호출 가능 - static 메서드로 Math.ceil과 같은 기능을 하는 메서드 선언.
	//매개변수로 들어온 소수를 올림하여 리턴하는 형태.
	public static double pageCeil(double num) {
		return Math.ceil(num);
	}
	
}
