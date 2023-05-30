package branch;

import java.util.ArrayList;
import java.util.List;

import administor.administor;
import common.DAO;
import member.member;

public class branchDAO extends DAO{
	static branchDAO bDAO=null;
	private branchDAO() {	
	}
	public static branchDAO getInstance() {
		if(bDAO==null) {
			bDAO=new branchDAO();
		}return bDAO;
	}
	
	public branch login(String id) {
		branch b=null;
		try {
			conn();
			String sql="select * from branch where b_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				b=new branch();
				b.setb_id(rs.getString("b_id"));
				b.setB_pw(rs.getString("b_pw"));
				b.setB_name(rs.getString("b_name"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		return b;
	}
	
	//as목록보기
	public List<administor> asList() {
		administor a = null;
		List<administor> list= new ArrayList<>();
		try {
			conn();
			String sql="select a_as, a_name, a_id, b_name from administor where a_as is not null and not (a_as='수리중')"
					+ "and not (a_as='수리완료')";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.equals(null)) {
				System.out.println("없는 이름입니다.");
			}
			while(rs.next()) {
				a=new administor();
				a.setbName(rs.getString("b_name"));
				a.setaAs(rs.getString("a_as"));
				a.setaName(rs.getString("a_name"));
				a.setaId(rs.getString("a_id"));
				list.add(a);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		
		return list;
	}
	
	//수리요청받기, 수리사항 알리기
	public int confirmAs(String aId, String asmessage) {
		int result=0;
		try {
			conn();
			String sql="update administor set a_as=? where a_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, asmessage);
			pstmt.setString(2, aId);
			result=pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			disConn();
		}
		
		return result;
	}
	public List<administor> asNow() {
		administor a=null;
		List<administor> list=new ArrayList<>();
		try {
			conn();
			String sql="select b_name, a_name, a_as from administor where a_as='수리중'";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				a= new administor();
				a.setaName(rs.getString("a_name"));
				a.setaAs(rs.getString("a_as"));
				a.setbName(rs.getString("b_name"));
				list.add(a);
			}
					
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		
		return list;
	}
	
	//회원 지점 변경
	public int change(String memId) {
		int result=0;
		try {
			conn();
			String sql="update member set b_name=replace(b_name,'요청','신규') where u_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		return result;
	}
	
	//선생님가입
	public int assign(administor ad) {
		int result=0;
		try {
			conn();
			String sql="insert into administor(a_id, a_pw, a_name, b_name, a_role,a_license)"
					+ "values(?,?,?,?,?,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, ad.getaId());
			pstmt.setString(2, ad.getaPw());
			pstmt.setString(3, ad.getaName());
			pstmt.setString(4, ad.getbName());
			pstmt.setString(5, ad.getaRole());
			pstmt.setString(6, ad.getaLicense());
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		return result;
	}
	//as완료 알림
	public List<administor> asEnd() {
		administor a = null;
		List<administor> list= new ArrayList<>();
		try {
			conn();
			String sql="select a_as, a_name, a_id from administor where a_as ='수리중'";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.equals(null)) {
				System.out.println("없는 이름입니다.");
			}
			while(rs.next()) {
				a=new administor();
				a.setaAs(rs.getString("a_as"));
				a.setaName(rs.getString("a_name"));
				a.setaId(rs.getString("a_id"));
				list.add(a);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}
		
		return list;
	}
	
	public member moveRequest() {
		member mem=null;
		try {
			conn();
			String sql="select * from member where b_name like '%요청%'";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				mem= new member();
				branch.inform="!!지점변경 요청이 있습니다!!";
				mem.setuId(rs.getString("u_id"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disConn();
		}return mem;
	}
}
