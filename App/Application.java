package App;

import branch.Login;
import member.member;
import member.memberService;

public class Application {
	public Application() {
		run(memberService.memberInfo);
	}

	private void run(member memberInfo) {
		if(memberInfo==null) {
			Login.login();
		}
		
	}
}
