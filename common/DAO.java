package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DAO {
	//Data(DB)AccessObject
	//연결까지만 책임지는 것 : DAO(from...CRUD는 책임x)
	
	//java와DB를 연결할 때 쓰는 객체
	protected Connection conn=null;
	//쿼리를 갖고 실행하는 객체
	protected PreparedStatement pstmt=null;
	//쿼리를 갖고 실행하는 객체
	protected Statement stmt=null;
	//조회결과값을 반환받는 객체
	protected ResultSet rs=null;
	
	String driver="oracle.jdbc.driver.OracleDriver";//driver에 대한 정보는 referenced libraries에 있음
	String url="jdbc:oracle:thin:@localhost:1521:xe";
	String id="c##gym";
	String pw="1234";
	
	//DB연결메소드
	public void conn() {
		try {
			//driver loading
			Class.forName(driver);
			//connect DB
			conn=DriverManager.getConnection(url,id,pw);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//DB종료(연결의 역순으로 진행됨)
	//disconnect가 필요한 이유:
	//DB에 접속할 수 있는 사람(connection pool)은 한정되어 있기 때문에 다 쓰면 disconnect해줘야 함
	public void disConn() {
		try {
			if(rs!=null) {
				rs.close();
			}
			if(stmt!=null) {
				stmt.close();
			}
			if(pstmt!=null) {
				pstmt.close();
			}
			if(conn!=null) {
				conn.close();
			}//if-elseif쓰면 안 되는 이유: 첫 번 째 if가 만족되면 후의 문장은 실행되지 않기 때문에 모두 if로 지정해주어
			//모두 종료되도록 한다.
			//default가 null이기 때문에 null이면 실행한 적이 없는 객체가 되기 때문에 null이 아닌지 확인하면 된다.
		}catch(Exception e){
			
		}
	}
	
	
}
