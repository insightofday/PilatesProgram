package administor;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import branch.branch;
import branch.branchService;
import member.member;
import member.memberDAO;

public class AdministorService {
	private LocalDate now=LocalDate.now();
	Calendar cal=Calendar.getInstance();
	String format="yy/mm/dd";
	SimpleDateFormat sdf=new SimpleDateFormat(format);

	
	
	static Scanner sc= new Scanner(System.in);
	static administor a=new administor();
	 member m= new member();
	public static administor AInfo=null;


	public void login() {
		System.out.println("관리자 아이디는 a로 시작합니다 \t 관리자의 아이디를 입력하세요");
		System.out.println(">>");
		String id=sc.nextLine();
		System.out.println("관리자의 비밀번호를 입력하세요");
		System.out.println(">>");
		String pw=sc.nextLine();
		a=AdministorDAO.getInstance().login(id);
		if(a!=null) {
		if(a.getaPw().equals(pw)) {
			System.out.println("관리자 로그인에 성공했습니다.");
			System.out.println(a.getbName()+"점 "+"관리자 "+a.getaId()+" 님 반갑습니다.");
			AInfo=a;
			//menu호출
			menu();
	}else {
		System.out.println("비밀번호를 잘못 입력했습니다.");
	}
}else {
	System.out.println("아이디를 잘못 입력했습니다.");
}
	}
	
	public void menu() {
		boolean run=true;
		delete(a);//관리자가 로그인할 때 마다 마감일이 다 된 회원을 삭제하기(해당지점의 직원만)
		System.out.println();
		if(a.getBranchError()!=null) {
			if(a.getBranchError().contains(a.getaName())) {//해당 선생님에게만 노출되도록
				//관리자가 로그인하고,본사에서부품수리다했을때알림
					System.out.println();
					System.out.println(a.getBranchError());
					int result=AdministorDAO.getInstance().as(a.getaName());
				}
		}

		
	//해당 선생님에게만 노출되도록
				//관리자가 로그인하고,본사에서지점이동요청수리했을때알림
				administor move=AdministorDAO.getInstance().getMove(a.getaId());
				if(move==null) {
					
				}else if(move!=null) {

							int change=AdministorDAO.getInstance().notNew(move.getuId(), a);
							if(change>0) {
								System.out.println(move.getuId()+" 님이 지점을 변경하여 우리 지점의 회원이 되었습니다");
							}else {
								System.out.println("다시 시도해 주세요");
								}
							}

					
		
	
		

		while(run) {
			System.out.println();
			System.out.println("다음은 ※관리자 메뉴※입니다.");
			System.out.println("1.회원정보조회 2.회원등록 3.회원정보수정(연장, 지점변경포함) 4.부품수리 5.공지사항입력 6.주차등록  7.나가기");
			System.out.println(">>");
			int menu=Integer.parseInt(sc.nextLine());
			if(menu==1) {
				System.out.println();
				view(a);
			}else if(menu==2) {
				System.out.println();
				System.out.println("대면으로 비용을 지불받은 후 진행하여 주세요");
				assign();
			}else if(menu==3) {
				System.out.println();
				updatemenu();
			}else if(menu==4) {System.out.println();
				as();
			}else if(menu==5) {
				System.out.println();
				announce();
			}else if(menu==6) {
				System.out.println();
				parking(AInfo);
			}else if(menu==7) {
				System.out.println();
				run=false;
			}else {
				System.out.println();
				System.out.println("오기입");
			}
			
		}
	}
	

	public void assign() {
		boolean run=true;
		while(run) {
			System.out.println();
		System.out.println("--회원등록 탭입니다--");
		String id="";
			System.out.println("회원의 이름을 입력하세요");
			System.out.println(">>");
			String name=sc.nextLine();
			
			boolean running=true;
			String phonen="";
			String pw="";
			while(running) {
				System.out.println("회원의 휴대폰번호를 입력하세요.(특수문자 없이 번호만 입력할 것)");
				System.out.println(">>");
				phonen=sc.nextLine();
				pw=phonen.substring(7);//아이디와 비밀번호는 휴대폰번호 뒷 네자리
				//휴대폰번호==회원id==회원비번.
				if(phonen.length()!=11) {
					System.out.println("11자리 번호가 아닙니다. 잘못 입력하셨습니다.");
				}else {
					running=false;
				}
				
			}
			//id중복을 체크
			member mem=memberDAO.getInstance().login(pw);		
			while(mem!=null) {
				pw=pw+"1";
				member chk=memberDAO.getInstance().login(pw);
				if(chk==null) {
					System.out.println("회원의 id는 "+pw+" 입니다");
					break;}
				}
			
			System.out.println("회원의 시작일자를 입력하세요.(YYYYMMDD)");
			System.out.println(">>");
			String start=sc.nextLine();
			
			
			System.out.println("회원의 종료일자를 입력하세요.(YYYYMMDD)");
			System.out.println(">>");
			String end=sc.nextLine();
			
			System.out.println("회원의 총 횟수를 입력하세요.(숫자만 기입)");
			System.out.println(">>");
			int total=Integer.parseInt(sc.nextLine());
			
			System.out.println("회원의 차량번호를 입력하세요.등록할 차량이 없다면 엔터키를 입력하세요");
			System.out.println(">>");
			String car=sc.nextLine();
			
			member member=new member();
			member.setuRegistdate();
			member.setuEnddate(end);
			member.setuId(pw);
			member.setuPw(pw);
			member.setuName(name);
			member.setuPhone(phonen);
			member.setuStartdate(start);
			member.setuTotal(total);
			member.setbName(a.getbName());
			member.setmCarNumber(car);
			int result=AdministorDAO.getInstance().assign(member);
			if(result>0) {
				System.out.println("회원등록을 성공하였습니다.");
				run=false;
			}else {
				System.out.println("죄송합니다.다시 실행해 주세요");
			}
		}
			
		}
	
	
	private void announce() {System.out.println();
		System.out.println("--공지사항 탭입니다--");
		System.out.println("예약시에 노출되는 공지사항을 기입해 주세요");
		System.out.println(">>");
		String ann=sc.nextLine();
		a.setaAnnouncement(ann);
		int result=AdministorDAO.getInstance().announce(a);
		if(result>0) {
			System.out.println("공지사항이 업데이트되었습니다.");
		}else {
			System.out.println("실패ㅠㅠ 다시 시도해 주세요");
		}
		
	}
	private void updatemenu() {
		System.out.println("--회원 정보 수정탭입니다--");
		String id="";
		while(true) {
			System.out.println("변경을 원하는 회원의 id를 입력하세요(id는 바꿀 수 없습니다)");
			System.out.println(">>");
			id=sc.nextLine();
			m.setuId(id);
			if(memberDAO.getInstance().login(id)==null) {
				System.out.println("존재하지 않는 id입니다");
			}else {
				break;
			}
		}
		System.out.println("1.개인정보수정  2.등록정보수정(연장,등록일 변경)  3.지점변경");
		System.out.println(">>");
		int menu=Integer.parseInt(sc.nextLine());
		switch(menu) {
		case 1:
			updatemem(id);
			break;
		case 2:
			update(id);
			break;
		case 3:
			move(id);
		}
	}
	
	private void updatemem(String id) {
		System.out.println("전화번호와 이름을 수정할 수 있습니다");
		boolean running=true;
		String phonen="";
		String pw="";
		while(running) {
			System.out.println();
			System.out.println("변경할 회원의 <휴대폰번호>를 입력하세요.(특수문자 없이 번호만 입력할 것)");
			System.out.println("만약 변경을 원하지 않는 경우, 기존의 번호와 동일하게 입력하세요.");
			System.out.println(">>");
			phonen=sc.nextLine();
			m.setuPhone(phonen);
			if(phonen.length()!=11) {
				System.out.println("11자리 번호가 아닙니다. 잘못 입력하셨습니다.");
			}else {
				running=false;
			}
		}
		System.out.println("변경할 회원의 <이름>을 입력하세요.");
		System.out.println("만약 변경을 원하지 않는 경우, 기존의 이름과 동일하게 입력하세요.");
		System.out.println(">>");
		m.setuName(sc.nextLine());
		int result=AdministorDAO.getInstance().updatemem(m);
		if(result>0) {
			System.out.println("회원정보가 수정되었습니다.");
		}else{
			System.out.println("회원정보수정에 실패하였습니다.");
		}
		
	}
	
	
	private void update(String id) {
		System.out.println();
		System.out.println("변경할 회원의 <시작일>을 입력하세요");
		System.out.println("만약 변경을 원하지 않는 경우, 기존의 시작일과 동일하게 입력하세요.");
		System.out.println(">>");
		m.setuStartdate(sc.nextLine());
		
		System.out.println("변경할 회원의 <종료일>을 입력하세요");
		System.out.println("만약 변경을 원하지 않는 경우, 기존의 종료일과 동일하게 입력하세요.");
		System.out.println(">>");
		m.setuEnddate(sc.nextLine());
		System.out.println("변경할 회원의 <총 횟수>를 입력하세요");
		System.out.println("만약 변경을 원하지 않는 경우, 기존의 횟수와 동일하게 입력하세요.");
		System.out.println(">>");
		m.setuTotal(Integer.parseInt(sc.nextLine()));
		int result=AdministorDAO.getInstance().update(m);
		if(result>0) {
			System.out.println("회원정보가 수정되었습니다.");
		}else {
			System.out.println("등록정보없데이트에 실패하였습니다.");
		}
	}
	private void view(administor a) {
		boolean run=true;
		while(run) {
			System.out.println();
			System.out.println("--회원정보 탭입니다--");
			System.out.println("1.전체회원 조회 2.1명만 조회 3.나가기");
			System.out.println(">>");
			String menu=sc.nextLine();
			switch(menu) {
			case "1":
				viewAll(a);
				break;
			case "2":
				view1();
				break;
			case "3":
				run=false;
				break;
			}
		}
	}
	
	private void viewAll(administor a) {
		System.out.println();
		System.out.println("다음은 전체회원 조회 탭입니다");
		List<member> list=AdministorDAO.getInstance().viewAll(a);
		for(int i=0;i<list.size();i++) {

				
			String sb= new String("회원의 <이름>은 "+list.get(i).getuName()+"\n회원의 <휴대폰번호>는 "+list.get(i).getuPhone()+
					"\n회원의 <시작일>은 "+list.get(i).getuStartdate().substring(0,10)+"\n회원의 <종료일>은 "+list.get(i).getuEnddate().substring(0,10)+"\n회원의 <남은 횟수>는 "+
					list.get(i).getuTotal()+"\n회원의 <차량정보>는 "+list.get(i).getmCarNumber()+"\n회원의 <아이디>는 "+list.get(i).getuId());
			String s=sb.toString().replace("null"," <미등록된 자료-기입요망> " );
			
			
			

			System.out.println(s);
			System.out.println("===================================");
			}

	}
	
	private void delete(administor a) {
		List<member> list=AdministorDAO.getInstance().viewAll(a);
		for(int i=0;i<list.size();i++) {
			int result=0;
			if(list.get(i).getuEnddate()!=null) {
				//String end=list.get(i).getuEnddate().toString().substring(0,10).replace("-","");
				//System.out.println(list.get(i).getuEnddate().toString());
				//System.out.println(list.get(i).getuEnddate().toString().substring(0,10).replace("-",""));
					result=AdministorDAO.getInstance().dropMember();
				}
			if(result>0) {
				System.out.println("!!!만료기간이 된 회원 "+result+"명의 데이터는 파기되었습니다.!!!");
			}
		}
	}
	
	private void view1() {
		System.out.println("다음은 1명만 조회 탭입니다.");	
		String id="";
		while(true) {
			System.out.println("조회를 원하는 회원의 id를 입력하세요");
			System.out.println(">>");
			id=sc.nextLine();
			if(memberDAO.getInstance().login(id)==null) {
				System.out.println("존재하지 않는 id입니다");
			}else {
				member mem=memberDAO.getInstance().login(id);
				String sb= new String("회원의 <이름>은 "+mem.getuName()+"\n회원의 <휴대폰번호>는 "+mem.getuPhone()+"\n회원의 <시작일>은 "+mem.getuStartdate().substring(0,10)+"\n회원의 <종료일>은 "+mem.getuEnddate().substring(0,10)+"\n회원의 <남은 횟수>는 "+
						mem.getuTotal()+"\n회원의 <주차정보>는 "+mem.getmCarNumber()+"\n회원의 <비밀번호>는 "+mem.getuPw());
				String s=sb.toString().replace("null"," <미등록된 자료-기입요망> " );
				System.out.println(s);
				
				
	/////////////길이학원에서 바꿔야 함!!!
				break;
			}
	}
	
	}
	public void as() {
			System.out.println("--부품수리 탭입니다--");
				System.out.println("수리요청 내용을 입력하세요 만약 뒤로가기를 원하면 숫자1을 입력하세요");
				System.out.println(">>");
				String afterservice=sc.nextLine();
				if(!afterservice.equals("1")) {
					int result=AdministorDAO.getInstance().as(a,afterservice);
					if(result>0) {
						System.out.println("수리내역을 지점에 전달하겠습니다.");
					}else {
						System.out.println("죄송합니다. 다시 입력해 주세요");
					}
				}
				if(afterservice.equals("1")) {}
	}
	public void parking(administor AInfo) {
		LocalDate now = LocalDate.now();
		System.out.println("--주차관리 탭입니다--");
		int result=AdministorDAO.getInstance().parking();
		if(result>0) {
			System.out.println("주차등록이 완료되었습니다.");
			System.out.println("오늘 날짜는 "+now+ " 이고, 하루동안 주차가 가능합니다.");
			System.out.println(AInfo.getaName()+" 님, 좋은 하루 되세요~");
		}else {
			System.out.println("죄송하지만, 주차등록을 다시 진행해 주세요");
		}
	}
	
	public void move(String id) {
		branchService bs=new branchService();
		System.out.println();
		System.out.println("지점변경을 본사에 의뢰합니다.");
		boolean run=true;
		String name="";
		int var=0;
		while(run) {
			System.out.println("변경을 원하는 지점명을 입력하세요");
			System.out.println(">>");
			name=sc.nextLine();

			for(int i=0;i<branchService.branches.length;i++) {
			if(branchService.branches[i].equals(name)) {
				var++;
				run=false;
				}
			}if(var==0) {
			System.out.println("없는 지점입니다. 지점명을 다시 확인후 진행해 주세요");
			run=false;
			menu();
		}
		}
//	if(AdministorDAO.getInstance().asRequest(AInfo)!=null) {
//		System.out.println("이미 수리된 요청입니다. 본사의 응답을 기다려 주세요");
//	}else {
		int result=AdministorDAO.getInstance().move(id, name+"요청");
		if(result>0) {
			System.out.println("변경요청을 전달하겠습니다");
		}else {
			System.out.println("죄송합니다 다시 시도해 주세요");
		}
		
		
//	}
	}
}


