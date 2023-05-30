package branch;

import java.util.Scanner;

import administor.AdministorService;
import member.memberService;

public class Login {

	public static void login() {
		memberService mc=null;
		AdministorService ac=null;
		branchService bc=null;
		Scanner sc=new Scanner(System.in);
		boolean run=true;
		while(run) {
			System.out.println("====로그인 메뉴입니다====");
			System.out.println("1.회원계정 로그인//2.관리자,선생님계정 로그인//3.본사계정 로그인//4.나가기");
			System.out.println(">>");
			int menu=Integer.parseInt(sc.nextLine());
			
			
			switch(menu) {
			case 1:
				System.out.println("초기 아이디와 비밀번호는 고지받은 것을 입력하세요");
				mc=new memberService();
				mc.login();
				break;
			case 2:
				ac=new AdministorService();
				ac.login();
				break;
			case 3:
				bc=new branchService();
				bc.login();
				break;
			case 4:
				System.out.println("필라테스ㅇㅇㅇ와 함께해 주셔서 감사합니다. 즐거운 하루 되세요");
				run=false;
				break;
			default:
				System.out.println("번호를 잘못 입력하셨습니다");
				break;
			}
			
		}
		
	}

}
