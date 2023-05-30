package member;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import administor.administor;

public class memberService {
	String[]dateAndT=new String[2];
	
	Scanner sc=new Scanner(System.in);
	public static member memberInfo=null;
	public static member reservationInfo=null;
	LocalDate now = LocalDate.now();
	
	public void login() {
		System.out.println("회원의 아이디를 입력하세요");
		System.out.println(">>");
		String id=sc.nextLine();
		System.out.println("회원의 비밀번호를 입력하세요");
		System.out.println(">>");
		String pw=sc.nextLine();
		memberInfo=memberDAO.getInstance().login(id);
		if(memberInfo!=null) {
		if(memberInfo.getuPw().equals(pw)) {
			System.out.println("회원 로그인에 성공했습니다.");
			System.out.println("회원 "+memberInfo.getuName()+"님 반갑습니다.");
			System.out.println();
			

			
			//최초 회원가입시 비밀번호 바꾸기
			if(memberInfo.getuPw().equals(memberInfo.getuId())) {
				System.out.println("최초의 비밀번호는 아이디와 같습니다.");
				System.out.println("비밀번호를 바꾸시겠습니까?");
				System.out.println("1.바꾸기 2.바꾸지 않기");
				System.out.println(">>");
				int menu=Integer.parseInt(sc.nextLine());
				if(menu==1) {
					System.out.println("바꾸고 싶은 비밀번호를 입력하세요");
					System.out.println(">>");
					String newpw=sc.nextLine();
					int result=memberDAO.getInstance().pwchange(newpw, id);
					if(result>0) {
						System.out.println("비밀번호가 정상 업데이트되었습니다");
					}else {
						System.out.println("비밀번호가 업데이트되지 않았습니다.");
					}
				}else if(menu==2) {
				}else {
					System.out.println("1과 2중에 선택하세요");
				}
			}menu();
		}else {
			System.out.println("비밀번호 오류입니다.");
		}
		}else {
			System.out.println("회원 id를 다시 입력하세요");
		}
		
	}
	
	private void menu() {
		boolean run=true;
//		LocalDate now=LocalDate.now();
//		String date[]=now.toString().split("-");
//		String dateInfo="";
//		for(int i=0;i<date.length;i++) {
//			dateInfo+=date[i];
//		}
//		System.out.println(Integer.parseInt(dateInfo));
//		
		
		while(run) {	
			System.out.println();
			System.out.println("다음은 ※회원 메뉴※입니다.");
			System.out.println("1.예약하기 2.내정보보기 3.예약취소 4.주차등록하기 5.나가기");

			System.out.println(">>");
			int memselect=Integer.parseInt(sc.nextLine());
			switch(memselect) {
			case 1:
				chknum();
				break;
			case 2:
				inform();
				break;
			case 3:
				cancel(memberInfo);
				break;

			case 4:
				parking();
				break;
			case 5:
				run=false;
				break;
			}
			
		}
		
	}
	public void cancel(member memberInfo) {
		System.out.println();
		System.out.println("--예약취소 탭입니다--");
		System.out.println("<예약 목록>");
		int info=1;
		memberInfo=memberDAO.getInstance().login(memberInfo.getuPw());
		String reservationGroup[]= {memberInfo.getuReservation1(),memberInfo.getuReservation2(),
				memberInfo.getUreservation3()};

		
		for(String a: reservationGroup) {
			if(a!=null) {
				String[] dateAndT=a.split("/");
				System.out.println(dateAndT[0]+"선생님의 "+dateAndT[1] +" 수업");
			}
		}
		int var=0;
		String time="";
		System.out.println();
			System.out.println("몇 일의 예약을 취소할까요? 메뉴로 돌아가기를 원하면 숫자2를 눌러주세요");
			System.out.println(">>");
			time=sc.nextLine();
			 if (time.equals("2")) {
					System.out.println("메뉴로 돌아갑니다");
					menu();
				}
			 if(time.endsWith(now.toString().substring(8))) {
					System.out.println("당일에는 예약취소가 불가능합니다.");
					menu();
				}

			for(String a: reservationGroup) {//취소가능한 날짜인지 확인
				if(a!=null) {
					dateAndT=a.split("/");
					if(time.endsWith(dateAndT[1].substring(6))) {
						var++;
					}
				}
			}
			if(var==0) {//취소가능한 날짜인지 확인2
				System.out.println("조회되지 않는 날짜입니다");
				menu();
			}
	
		String reservationInfo="";
		for(int i=0;i<reservationGroup.length;i++) {
			if(reservationGroup[i]!=null) {
				if(reservationGroup[i].contains(time)) {//시간정보배열의 시간을 활용해서
					reservationInfo=String.valueOf(i+1);//reservation1,2,3에 넣기
					break;
				}
			}
		}
		
		reservationInfo="reservation"+reservationInfo;
		int result1=memberDAO.getInstance().rollback(memberInfo);
		int result2=memberDAO.getInstance().reset(reservationInfo, memberInfo);
		if(result1>0&&result2>0) {
			System.out.println(time+" 의 예약이 취소되었습니다.");
			System.out.println();
		}else {
			System.out.println("다시 시도해 주세요");
		}



	}


	public void parking() {
		System.out.println();
		System.out.println("--주차등록 탭입니다--");
		System.out.println(memberInfo.getuName()+"님. 주차등록을 진행하시겠습니다");
	
			memberDAO.getInstance().now();

		//	int result=memberDAO.getInstance().parking(memberInfo);

	}
	public void inform() {
		
		System.out.println();
		System.out.println("---내정보보기 탭입니다--");
		memberInfo=memberDAO.getInstance().login(memberInfo.getuPw());
		String reservationGroup[]= {memberInfo.getuReservation1(),memberInfo.getuReservation2(),
				memberInfo.getUreservation3()};
	
		
		for(int i=0;i<reservationGroup.length;i++) {
			if(reservationGroup[i]!=null) {
				String[] dateAndT =reservationGroup[i].split("/");
				System.out.println("나의 "+ (i+1)+" 번 예약은  "+dateAndT[0]+"선생님의 "+dateAndT[1] +" 수업");
			}
		}
		
		
		System.out.println("나의 전화번호는 "+memberInfo.getuPhone());
		System.out.println("나의 지점은 "+memberInfo.getbName());
		System.out.println("나의 시작일은 "+memberInfo.getuStartdate().toString().substring(0,10));/////////////길이학원에서 바꿔야 함!!!
		System.out.println("나의 종료일은 "+memberInfo.getuEnddate().toString().substring(0,10));/////////////길이학원에서 바꿔야 함!!!
		System.out.println("나의 남은 횟수는"+memberInfo.getuTotal()+" 회");
		System.out.println("나의 비밀번호는 "+memberInfo.getuPw());

	
	}
	
	public void chknum() {
		memberInfo=memberDAO.getInstance().login(memberInfo.getuPw());
		System.out.println();
		System.out.println("---예약하기 탭입니다--");
		System.out.println();
		System.out.println("~~이번 주의 예약정보는 다음과 같습니다~~");
		
		String reservationGroup[]= {memberInfo.getuReservation1(),memberInfo.getuReservation2(),
				memberInfo.getUreservation3()};
		
		int var=0;
		for(int i=0;i<reservationGroup.length;i++) {
			if(reservationGroup[i]!=null) {
				var++;
				String []date=reservationGroup[i].split("/");
				System.out.println();
				System.out.println((i+1)+"번째 운동:: \n \t"+date[0]+" 선생님 "+date[1]+"일");
			}
		}
		System.out.println("~~이번 주의 예약정보는 다음과 같습니다~~");
		if(var==reservationGroup.length) {
			System.out.println();
				System.out.println("일주일에 최대 3번까지 운동을 할 수 있습니다");
				System.out.println("메뉴로 돌아갑니다");
		}else if(memberInfo.getuTotal()==0){
			System.out.println("횟수가 만료되었습니다.");
		}else {
			reservation(reservationGroup);
		}
	}
		

		public void reservation(String reservationGroup[]) {
		List<administor> list=memberDAO.getInstance().searchT(memberInfo.getbName());
		System.out.println();
		System.out.println("선생님 목록은 다음과 같습니다");
		System.out.println("============================");
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i).getaName());
		}
		System.out.println("============================");
		String t="";
		String date="";
		boolean run=true;
		int reserved=reservationGroup.length;
		

		int count=list.size();
		while(run) {
			System.out.println("어떤 선생님께 수업을 들을 건가요?(이름만 기입하세요)");
			System.out.println(">>");
			t=sc.nextLine();
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getaName().contains(t)) {
					count--;
					run=false;
					break;
				}
			}if(count==list.size()) {
				System.out.println("없는 이름입니다");
			}
		}
		administor a=memberDAO.getInstance().ann(t);
		//a.getaAnnouncement().replace("", "건강하세요~");
		System.out.println(a.getaName()+"선생님의 공지사항: "+a.getaAnnouncement());
		
			while(true) {
				System.out.println("언제 운동을 할 것인가요?(YYYYMMDD)");
				System.out.println(">>");
				date=sc.nextLine();
				if(date.length()==8) {
					break;
				}
			}
			for(String b: reservationGroup) {
				if(b!=null) {
					if(b.contains(date)) {
						System.out.println("!이미 예약된 날짜입니다. 다시 입력해 주세요");
						chknum();
					}
				}
			
		date=t+'/'+date;
	


		int result1=0;
		int result2=0;



				if((memberInfo.getuReservation1()==memberInfo.getuReservation2())&&
						(memberInfo.getUreservation3()==memberInfo.getuReservation2())) 
				{
					result1=memberDAO.getInstance().reservation("reservation1",date, memberInfo);
					result2=memberDAO.getInstance().total(memberInfo);
					memberInfo.setuReservation1(date);
				}
				
				else if(!(memberInfo.getuReservation1()==null)&&
						(memberInfo.getuReservation2()==memberInfo.getUreservation3()))
				{
					result1=memberDAO.getInstance().reservation("reservation2",date, memberInfo);
					result2=memberDAO.getInstance().total(memberInfo);
					memberInfo.setuReservation2(date);
				}
				
				else
				{
					result1=memberDAO.getInstance().reservation("reservation3",date, memberInfo);
					result2=memberDAO.getInstance().total(memberInfo);
					memberInfo.setUreservation3(date);
				}
				
				
				
				if(result1>0&&result2>0) {
					memberInfo=memberDAO.getInstance().login(memberInfo.getuId());//새로운횟수로 갱신
					
					if(memberInfo==null) {
						System.out.println("죄송합니다 다시 시도해 주세요");
					}else {
						System.out.println(memberInfo.getuName()+" 님의 남은 횟수는 "+memberInfo.getuTotal()+" 회 입니다");
						System.out.println("하루 전까지 예약취소를 했을 때에만 횟수에서 차감되지 않습니다");
						System.out.println("이 점 유의해 주세요");
						menu();
					}
				}else {
					System.out.println("죄송합니다 다시 시도해 주세요");
				}
			}
		}
		
}
	
	
	
	

		
	

	


