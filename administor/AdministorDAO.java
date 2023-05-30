package administor;

import java.util.ArrayList;
import java.util.List;

import common.DAO;
import member.member;

public class AdministorDAO extends DAO {
	static AdministorDAO aDAO=null;
	private AdministorDAO() {
		
	}
	public static AdministorDAO getInstance() {
		if(aDAO==null) {
			aDAO=new AdministorDAO();
		}return aDAO;
	}
	//로그인(아이디중복체크를겸함)
	public administor login(String id) {
		administor a=null;
		try {
			conn();
			String sql="select * from administor where a_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				a=new administor();
				a.setaId(rs.getString("a_id"));
				a.setaAnnouncement(rs.getString("a_announcement"));
				a.setaAs(rs.getString("a_as"));
				a.setaPw(rs.getString("a_pw"));
				a.setaParking(rs.getString("a_parking"));
				a.setaName(rs.getString("a_name"));
				a.setbName(rs.getString("b_name"));
				a.setaCarNumber(rs.getString("a_car_number"));
				a.setBranchError(rs.getString("branch_error"));
				a.setMove(rs.getString("move"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			disConn();
		}
		return a;
	}
	//부품수리요청 전달받기
	public int as(String teacher, String branchError) {
		int result=0;
		try {
			conn();
			String sql="update administor set branch_error=? where a_name=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, branchError);
			pstmt.setString(2, teacher);
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		return result;
	}
	//부품수리요청 reset
	public int as(String teacher) {
		int result=0;
		try {
			conn();
			String sql="update administor set branch_error=null where a_name=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, teacher);
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		return result;
	}
	
	//지점변경 전달받기+전달하기
	public int move(String id, String name) {
		int result=0;
		try {
			conn();
			String sql="update member set b_name=? where u_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		return result;
	}
	//지점 바뀐 회원 들고오기
	public administor getMove(String a_id) {
		administor a=null;
		try {
			conn();
			String sql="select m.b_name,m.u_id from administor a, member m where m.b_name like'%신규%' and a.a_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, a_id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				a=new administor();
				a.setbName(rs.getString("b_name"));
				a.setuId(rs.getString("u_id"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		return a;
	}
	//신규회원아닌걸로수정하기
	public int notNew(String u_id, administor a) {
		int result=0;
		try {
			conn();
			String sql="update member set b_name=? where u_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, a.getbName());
			pstmt.setString(2, u_id);
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		return result;
	}
	

	
	
	//회원가입
	public int assign(member member) {
		int result=0;
		try {
			conn();
			String sql="insert into member(u_id, u_pw, u_registdate, u_startdate, u_enddate, u_total, u_phone, u_name, b_name, m_car_number)"
					+ "values(?,?,sysdate,?,?,?,?,?,?,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, member.getuId());
			pstmt.setString(2, member.getuPw());
			pstmt.setString(3, member.getuStartdate());
			pstmt.setString(4, member.getuEnddate());
			pstmt.setInt(5, member.getuTotal());
			pstmt.setString(6, member.getuPhone());
			pstmt.setString(7, member.getuName());
			pstmt.setString(8, member.getbName());
			pstmt.setString(9, member.getmCarNumber());
			member.setuRegistdate();
			result=pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		
		return result;
	}
	//공지사항수정
	public int announce(administor a) {
		int result=0;
		try {
			conn();
			String sql="update administor set a_announcement=? where a_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, a.getaAnnouncement());
			pstmt.setString(2, a.getaId());
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		
		return result;
	}
	//회원정보수정
	public int updatemem(member m) {
		int result=0;
		try {
			conn();
			String sql="update member set u_name=?, u_phone=? where u_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, m.getuName());
			pstmt.setString(2,m.getuPhone());
			pstmt.setString(3,m.getuId());
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		
		return result;
	}
	
	//등록정보 수정
	public int update(member m) {
		int result=0;
		try {
			conn();
			String sql="update member set u_startdate=?,u_enddate=?,u_total=? where u_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, m.getuStartdate());
			pstmt.setString(2, m.getuEnddate());
			pstmt.setInt(3, m.getuTotal());
			pstmt.setString(4, m.getuId());
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
				
		return result;
	}
	//전체회원정보 조회(해당지점만)
	public List<member> viewAll(administor a){
		List list=new ArrayList();
		member mem=null;
		try {
			conn();
			String sql="select * from member where b_name=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, a.getbName());
			rs=pstmt.executeQuery();
			while(rs.next()) {
				mem= new member();
				mem.setuName(rs.getString("u_name"));
				mem.setuPhone(rs.getString("u_phone"));
				mem.setuId(rs.getString("u_id"));
				mem.setuParking(rs.getString("u_parking"));
				mem.setuStartdate(rs.getString("u_startdate"));
				mem.setuEnddate(rs.getString("u_enddate"));
				mem.setuTotal(rs.getInt("u_total"));
				mem.getuRegistdate();
				mem.setmCarNumber(rs.getString("m_car_number"));
				mem.setbName(rs.getString("b_name"));
				list.add(mem);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		
		return list;
	}
	//회원정보 삭제
	public int dropMember() {
		int result=0;
		try {
			conn();
			String sql="delete from member where to_char(sysdate-1,'yy/mm/dd')>= to_char(u_enddate)";
			
	///////////날짜형식바꿔야 해
			
			
				
			pstmt=conn.prepareStatement(sql);
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		return result;
		
	}
	//부품수리 요청
	public int as(administor a, String afterservice) {
		int result=0;
		try {
			conn();
			String sql="update administor set a_as=? where a_id=? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, afterservice);
			pstmt.setString(2, a.getaId());
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		return result;
		
	}
	public int parking() {
		int result=0;
		try {
			conn();
			String sql="update administor set a_parking =concat(to_char(sysdate,'mm-dd'),' ')";
			pstmt=conn.prepareStatement(sql);

			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		
		return result;
	}
//	public administor asRequest(administor a){
//		administor ad=null;
//		try {
//			conn();
//			String sql="select b_name from member where a_id=? and a_as like '%요청%'";
//			pstmt=conn.prepareStatement(sql);
//			pstmt.setString(1, a.getaId());
//			rs=pstmt.executeQuery();
//			if(rs.next()) {
//				ad=new administor();
//				ad.setaAs(rs.getString("a_as"));
//			}
//		}catch(Exception e) {
//			e.printStackTrace();
//		}finally {
//			disConn();
//		}
//		return a;
//	}

}
