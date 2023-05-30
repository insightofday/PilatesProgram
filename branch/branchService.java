package branch;

import java.util.List;
import java.util.Scanner;

import administor.AdministorDAO;
import administor.administor;
import member.member;

public class branchService {
	Scanner sc = new Scanner(System.in);
	administor administor;
	

	
	public static String[] branches= {"대구중앙로","대구대실","대구대현","대구이곡","제주남부"};
	char[] id= new char[6];
	





	
	public void login() {
		System.out.println("본사 아이디는 b로 시작합니다 \t 본사 아이디를 입력하세요");
		System.out.println(">>");
		String id = sc.nextLine();
		System.out.println("본사 비밀번호를 입력하세요");
		System.out.println(">>");
		String pw = sc.nextLine();
		branch b = branchDAO.getInstance().login(id);
		if (b != null) {
			if (b.getB_pw().equals(pw)) {
				System.out.println("본사 로그인에 성공했습니다");
				menu();
			} else {
				System.out.println("비밀번호 오류입니다");
			}
		}
		else{
			System.out.println("아이디 오류입니다");
		}
	}

	public void menu() {
		System.out.println();
		boolean run = true;
		//지점변경이 있다면 띄우기
		member mem=branchDAO.getInstance().moveRequest();
		if(branch.inform!=null) {
			System.out.println(branch.inform);
			System.out.println();	
			System.out.println("회원지점변경 탭으로 이동할까요?");
			System.out.println("1.네 2.아니오");
			System.out.println(">>");
			int move=Integer.parseInt(sc.nextLine());
			if(move==1) {
				move();
			}else {
				
			}
		}

		while (run) {
			System.out.println();
			System.out.println("다음은 ※본사 메뉴※입니다.");
			System.out.println("1.선생님등록하기  2.수리요청받기 3.수리완료알림  4.회원지점변경  5.나가기");
			System.out.println(">>");
			int menu = Integer.parseInt(sc.nextLine());
			switch (menu) {
			case 1:
				assign();
				break;
			case 2:
				asconfirm();
				break;
			case 3:
				asSuccess();
				break;
			case 4:
				move();
				break;
			case 5:
				run = false;
				break;
			default:
				System.out.println("오기입입니다");
				break;
			}
		}
	}

	private void assign() {
			administor ad=new administor();
			System.out.println();
			System.out.println("--선생님등록하기 탭입니다--");
			System.out.println("지점과 대면/유선 상으로 상의한 후에 진행해 주세요");
			System.out.println();
			System.out.println("현재 필라테스 ㅇㅇㅇ의 지점은 다음과 같습니다.");
			System.out.println("- - - - - - - - - - - - - - - ");
				for(int i=0;i<branches.length;i++) {
					System.out.print(branches[i]);
					System.out.print(" ");
				}
				
			System.out.println();
			System.out.println("- - - - - - - - - - - - - - - ");
			int count=0;
			while(true) {
				System.out.println("어떤 지점의 선생님인가요?");
				System.out.println(">>");
				ad.setbName(sc.nextLine());
				for(int i=0;i<branches.length;i++) {
					if(branches[i].equals(ad.getbName())) {
						count++;
					}
				}
				if(count!=0) {
					break;
				}else {
					System.out.println("등록되지 않은 지점입니다");
				}
			}
			System.out.println("선생님이 갖고 있는 자격증은 무엇인가요? 없다면 엔터를 입력하세요");
			System.out.println(">>");
			ad.setaLicense(sc.nextLine());
			System.out.println("선생님의 성함은 어떻게 되나요?");
			System.out.println(">>");
			ad.setaName(sc.nextLine());
			ad.setaId(idset());
			//선생님의 id를 랜덤값으로 지정(a로시작하는6글자)
			System.out.println("선생님은 어떤 비밀번호를 원하시나요?(8자리 이상)");
			System.out.println(">>");
			ad.setaPw(sc.nextLine());
			while(ad.getaPw().length()<7) {
				System.out.println("8자리 이상을 입력해 주세요");
				System.out.println(">>");
				ad.setaPw(sc.nextLine());
			}
			ad.setaRole("선생님");
			int result=branchDAO.getInstance().assign(ad);
			if(result>0) {
				System.out.println("앞으로 "+ad.getbName()+"지점과 함께 할 "+ad.getaName()+" 선생님의 소중한 정보가 등록되었습니다.");
			}else {
				System.out.println("다시 시도해 주세요");
			}
			
	}

	private String idset() {
		boolean run=true;
		String verifiedId="";
		administor ad= null;

		while(run) {
				for(int i=1;i<id.length;i++) {
					if(i%3==0||i%4==0) {
						id[0]='a';
						char c=(char)((Math.random()*10)+48);
						id[i]=c;
					}else {
						char c=(char)((Math.random()*26)+97);
						id[i]=c;
					}
				}
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<id.length;i++) {
				sb.append(id[i]);
			}
			verifiedId=sb.toString();
			if(AdministorDAO.getInstance().login(verifiedId)!=null) {
			
			}else {
				System.out.println("선생님의 id는 <"+verifiedId+"> 로 배정되었습니다");
				run=false;
			}
			
		}
		return verifiedId;
	}


	private void asSuccess() {
		System.out.println();
		System.out.println("--수리완료알림 탭입니다--");
		List <administor>list1=branchDAO.getInstance().asNow();
		if(list1.size()>0) {
			for (int i = 0; i < list1.size(); i++) {
				System.out.println("현재 수리중으로 등록된 목록");
				System.out.println("================================");
				System.out.println(list1.get(i).getbName()+" 지점 "+list1.get(i).getaName()+" 선생님의 요청 ");
				System.out.println("================================");
			}
		}
		System.out.println("어떤 선생님의 요청을 완료했나요?");
		System.out.println(">>");
		String t = sc.nextLine();
		List<administor> list2 = branchDAO.getInstance().asEnd();
		int var=0;
		
		for (int i = 0; i < list2.size(); i++) {
			if (list2.get(i).getaName().equals(t)) {
				var++;
				String s = "수리완료";
				
				int result1 = branchDAO.getInstance().confirmAs(list2.get(i).getaId(), s);
				administor.setBranchError(t+" 선생님의 부품수리요청을 완료하였습니다");
				
				int result2=AdministorDAO.getInstance().as(t,administor.getBranchError());
				
				if (result1 > 0&&result2>0) {
					System.out.println(t + "선생님의 요청을 '수리완료'로 바꾸었습니다.");
					System.out.println("해당사항을 전달하겠습니다");
					
				} else {
					System.out.println("죄송하지만, 업데이트에 실패했습니다.");
				}
			}
		}if(var==0) {
			System.out.println("이름을 잘못 입력하셨습니다");
		}
	}

	private void asconfirm() {
		System.out.println();
		System.out.println("--수리요청받기 탭입니다--");
		List<administor> list = branchDAO.getInstance().asList();
		if (list.size() > 0) {
			System.out.println("수리요청내역은 다음과 같습니다.");
			for (int i = 0; i < list.size(); i++) {
				System.out.println("================================");
				System.out.println(list.get(i).getbName()+" 점 "+list.get(i).getaName() + " 님의 요청 \n" + list.get(i).getaAs());
				System.out.println("================================");
			}
			System.out.println("수리요청을 수락하시겠습니까?");
			System.out.println("1.네 2.아니오");
			System.out.println(">>");
			int menu = Integer.parseInt(sc.nextLine());
			if (menu == 1) {
				System.out.println("어떤 선생님의 요청을 수리할까요?");
				System.out.println(">>");
				String t = sc.nextLine();

				int var=0;
				for (int j = 0; j < list.size(); j++) {
					if (list.get(j).getaName().equals(t)) {
						var++;
						int result = branchDAO.getInstance().confirmAs(list.get(j).getaId(), "수리중");
						if (result > 0) {
							System.out.println(t + "선생님의 요청을 '수리중'으로 바꾸었습니다.");
						} else {
							System.out.println("죄송하지만, 업데이트에 실패했습니다.");
						}
					}
				}if(var==0) {
					System.out.println("이름을 잘못 입력하셨습니다.");
				}
			} else if (menu == 2) {
				System.out.println("※지점장 메뉴※로 돌아갑니다");
			}

		} else {
			System.out.println("수리요청내역이 없습니다.");
		}

	}
	public void move() {
		System.out.println();
		System.out.println("--회원지점변경 탭입니다--");
		
		member mem=branchDAO.getInstance().moveRequest();
		if(branch.inform!=null) {
			System.out.println();
			int result1=branchDAO.getInstance().change(mem.getuId());
			if(result1>0) {
				System.out.println(mem.getuId()+" 님의 지점을 변경하였습니다");
				//administor.setMove(branchService.memId+" 님의 지점을 "+ branchService.memName +"(으)로 변경하였습니다");	
			}else {
				System.out.println("지점명 변경에 실패하였습니다.");
			}

					}
			}

		
		}



