package member;

import java.time.LocalDate;
import java.util.Date;

public class member {
	static LocalDate now = LocalDate.now();//현재시간구하기
	public member() {
		
	}
	private String uId;
	private String uPw;
	private  LocalDate uRegistdate;
	private String uStartdate;
	private  String uEnddate;
	private int uTotal;
	private  String uPhone;
	private  String uParking;
	private  String uName;
	private String bName;
	private String mCarNumber;
	private String uReservation1;
	private String uReservation2;
	private String ureservation3;
	
	
	
//	private int mon9am;
//	private int tue9am;
//	private int wed9am;
//	private int thu9am;
//	private int fri9am;
//	
//	private int mon6pm;
//	private int tue6pm;
//	private int wed6pm;
//	private int thu6pm;
//	private int fri6pm;
//	
//	private int mon10pm;
//	private int tue10pm;
	
//	private String monInfo;
//	private String tueInfo;
//	private String wedInfo;
//	private String thuInfo;
//	private String friInfo;
	
	public String getuReservation2() {
		return uReservation2;
	}

	public void setuReservation2(String uReservation2) {
		this.uReservation2 = uReservation2;
	}

	public String getUreservation3() {
		return ureservation3;
	}

	public void setUreservation3(String ureservation3) {
		this.ureservation3 = ureservation3;
	}

	public String getuReservation1() {
		return uReservation1;
	}

	public void setuReservation1(String uReservation1) {
		this.uReservation1 = uReservation1;
	}
	private String monDate;
	private String tueDate;
	private String wedDate;
	private String thuDate;
	private String friDate;
	
	private String dateInfo;

	private String reserpermit;


	
	
	
	
	
	


	public String getDateInfo() {
		return dateInfo;
	}

	public void setDateInfo(String dateInfo) {
		this.dateInfo = dateInfo;
	}
	
	

	

//	public String getMonInfo() {
//		return monInfo;
//	}
//
//	public void setMonInfo(String monInfo) {
//		this.monInfo = monInfo;
//	}
//
//	public String getTueInfo() {
//		return tueInfo;
//	}
//
//	public void setTueInfo(String tueInfo) {
//		this.tueInfo = tueInfo;
//	}
//
//	public String getWedInfo() {
//		return wedInfo;
//	}
//
//	public void setWedInfo(String wedInfo) {
//		this.wedInfo = wedInfo;
//	}
//
//	public String getThuInfo() {
//		return thuInfo;
//	}
//
//	public void setThuInfo(String thuInfo) {
//		this.thuInfo = thuInfo;
//	}
//
//	public String getFriInfo() {
//		return friInfo;
//	}
//
//	public void setFriInfo(String friInfo) {
//		this.friInfo = friInfo;
//	}

	public String getMonDate() {
		return monDate;
	}

	public void setMonDate(String monDate) {
		this.monDate = monDate;
	}

	public String getTueDate() {
		return tueDate;
	}

	public void setTueDate(String tueDate) {
		this.tueDate = tueDate;
	}

	public String getWedDate() {
		return wedDate;
	}

	public void setWedDate(String wedDate) {
		this.wedDate = wedDate;
	}

	public String getThuDate() {
		return thuDate;
	}

	public void setThuDate(String thuDate) {
		this.thuDate = thuDate;
	}

	public String getFriDate() {
		return friDate;
	}

	public void setFriDate(String friDate) {
		this.friDate = friDate;
	}

	
	
	
	
	
	public String getReserpermit() {
		return reserpermit;
	}
	
	public void setReserpermit(String reserpermit) {
		this.reserpermit = reserpermit;
	}
	
	
	
	public String getmCarNumber() {
		return mCarNumber;
	}
	public void setmCarNumber(String aCarNumber) {
		this.mCarNumber = aCarNumber;
	}
	
	
	
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getuPw() {
		return uPw;
	}
	public void setuPw(String uPw) {
		this.uPw = uPw;
	}
	public LocalDate getuRegistdate() {
		return uRegistdate;
	}
	public void setuRegistdate() {
		this.uRegistdate = now;
	}
	public String getuStartdate() {
		return uStartdate;
	}
	public void setuStartdate(String uStartdate) {
		this.uStartdate = uStartdate;
	}
	public String getuEnddate() {
		return uEnddate;
	}
	public void setuEnddate(String uEnddate) {
		this.uEnddate = uEnddate;
	}
	public int getuTotal() {
		return uTotal;
	}
	public void setuTotal(int uTotal) {
		this.uTotal = uTotal;
	}
	public String getuPhone() {
		return uPhone;
	}
	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}
	public String getuParking() {
		return uParking;
	}
	public void setuParking(String uParking) {
		this.uParking = uParking;
	}
	
	
}
