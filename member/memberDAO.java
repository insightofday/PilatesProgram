package member;


import java.util.ArrayList;
import java.util.List;

import administor.administor;
import common.DAO;

public class memberDAO extends DAO {
	static memberDAO memDAO=null;
	
	private memberDAO() {	
}
	public static memberDAO getInstance() {
		if(memDAO==null) {
			memDAO=new memberDAO();
		}return memDAO;
	}
	
	//로그인(아이디중복체크를겸함)
	public member login(String pw) {
		member mem=null;
		try {
			conn();
			String sql="select * from member where u_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, pw);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				mem=new member();
				mem.setuId(rs.getString("u_id"));
				mem.setuPw(rs.getString("u_pw"));
				mem.setuPhone(rs.getString("u_phone"));
				mem.setuName(rs.getString("u_name"));
				mem.setuParking(rs.getString("u_parking"));
				mem.getuRegistdate();
				mem.setuEnddate(rs.getString("u_enddate"));
				mem.setuStartdate(rs.getString("u_startdate"));
				mem.setuTotal(rs.getInt("u_total"));
				mem.setmCarNumber(rs.getString("m_car_number"));
				mem.setbName(rs.getString("b_name"));
				mem.setuReservation1(rs.getString("reservation1"));
				mem.setuReservation2(rs.getString("reservation2"));
				mem.setUreservation3(rs.getString("reservation3"));
				}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		
		
		return mem;
	}

	
	public int pwchange(String newpw, String id) {
		int result=0;
		try {
			conn();
			String sql="update member set u_pw=? where u_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, newpw);
			pstmt.setString(2, id);
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}

		return result;
	}
	

	
//주차
	public void now() {
		try {
			conn();
			String sql="select to_char(sysdate, 'hh12:mi') now ,to_char(sysdate+2/24,'hh12:mi') from dual ";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				String time=rs.getString("now");
				System.out.println("현재시각은 "+time +" 이고, ");
				String later=rs.getString("to_char(sysdate+2/24,'hh12:mi')");
				System.out.println(later +" 까지 주차장을 이용할 수 있습니다");
				System.out.println("오늘도 즐겁게 운동합시다~");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
	}
	
	

	//지점명으로 선생님 조회
	public List<administor> searchT(String bname){
		List list=new ArrayList();
		administor ad=null;
		try {
			conn();
			String sql="select * from administor where b_name=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,bname);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				ad=new administor();
				ad.setaAnnouncement(rs.getString("a_announcement"));
				ad.setaName(rs.getString("a_name"));
				list.add(ad);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		
		return list;
	}
	//횟수차감 
	public int total(member memberInfo) {
		int result=0;
		try {
				conn();
				String sql="update member set u_total=u_total-1 where u_id=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, memberInfo.getuId());
				result=pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		
		return result;
	}
	//예약하기(회원테이블에 정보입력)
	public int reservation(String reservation,String date,member memberInfo) {
		int result=0;
		try {
			if(reservation.equals("reservation1")) 
			{
				conn();
				String sql="update member set reservation1=? where u_id=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, date);
				pstmt.setString(2, memberInfo.getuId());
				result=pstmt.executeUpdate();
			}
			else if(reservation.equals("reservation2")) 
			{
				conn();
				String sql="update member set reservation2=? where u_id=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, date);
				pstmt.setString(2, memberInfo.getuId());
				result=pstmt.executeUpdate();
			}
			else if(reservation.equals("reservation3")) 
			{
				conn();
				String sql="update member set reservation3=? where u_id=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, date);
				pstmt.setString(2, memberInfo.getuId());
				result=pstmt.executeUpdate();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		return result;
	}
//	//예약취소
	public int rollback(member memberInfo) {
		int result=0;
		try {
			conn();
			String sql="update member set u_total=u_total+1 where u_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, memberInfo.getuId());
			result=pstmt.executeUpdate();
			

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		
		return result;
	}
	//회원 예약정보 리셋
	public int reset(String info, member memberInfo) {
		int result=0;
		try {
			conn();
			String sql="update member set  "+info+" = null where u_id=?";
			pstmt=conn.prepareStatement(sql);
//			pstmt.setString(1, info);
			pstmt.setString(1, memberInfo.getuId());
			result=pstmt.executeUpdate();
			

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		return result;
	}
	
	//공지사항띄우기
	public administor ann(String teacherName) {
		administor a=null;
		try {
			conn();
			String sql="select a_name , a_announcement  from administor where a_name=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, teacherName);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				a=new administor();
				a.setaName(rs.getString("a_name"));
				if(rs.getString("a_announcement")==null) {
					a.setaAnnouncement("건강하세요~");
				}else {
					a.setaAnnouncement(rs.getString("a_announcement"));			
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}return a;
	}
	

//	public void remove(String dateInfo, member member) {
//		String delete="";
//		try {
//			conn();
//			String sql="select reservation1, reservation2, reservation3 from member where u_id=?";
//			pstmt=conn.prepareStatement(sql);
//			pstmt.setString(1, member.getuId());
//			rs=pstmt.executeQuery();
//			if(rs.next()) {
//				if(rs.getString("reservation1").equals(dateInfo)) {
//					delete="reservation1";
//				}else if(rs.getString("reservation2").equals(dateInfo)) {
//					delete="reservation2";
//				}else if(rs.getString("reservation3").equals(dateInfo)) {
//					delete="reservation3";
//				}
//			}
//			sql="update member set "+delete+" =null where u_id=?";
//			pstmt=conn.prepareStatement(sql);
//			pstmt.setString(1, member.getuId());
//			rs=pstmt.executeQuery();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}finally {
//			disConn();
//		}
//	}

}
