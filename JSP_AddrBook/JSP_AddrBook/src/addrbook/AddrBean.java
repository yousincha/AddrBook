package addrbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import addrbook.AddrBook;

public class AddrBean<Addrook> {
	//1.�����ͺ��̽� Ŀ�ؼ�
	Connection conn=null;
	PreparedStatement pstmt=null;
	
	String jdbc_driver="com.mysql.cj.jdbc.Driver";
	String jdbc_url="jdbc:mysql://localhost:3306/jspdb?characterEncoding=UTF-8&serverTimezone=UTC";
	
	void connect() {
		try {
		Class.forName(jdbc_driver);
		conn=DriverManager.getConnection(jdbc_url,"root","1234");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}//connection end
	
	//2.�ּҷ� �Է�
	public boolean insertDB(AddrBook addrbook) {
		connect();
		String sql="insert into addrbook(ab_name,ab_email,ab_birth,ab_tel,ab_comdept,ab_memo) values(?,?,?,?,?,?)";
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,addrbook.getAb_name());
			pstmt.setString(2,addrbook.getAb_email());
			pstmt.setString(3,addrbook.getAb_birth());
			pstmt.setString(4,addrbook.getAb_tel());
			pstmt.setString(5,addrbook.getAb_comdept());
			pstmt.setString(6,addrbook.getAb_memo());
			pstmt.executeUpdate();
		
	}catch(Exception e) {
			e.printStackTrace();
			return false;
	}
		return true;
	}
	
	//3.�ּҷ� ����
	public boolean updateDB(AddrBook addrbook) throws Exception{
		connect();
		
		String sql="update addrbook set ab_name=?, ab_email=?, ab_birth=? , ab_tel=?, ab_comdept=?, ab_memo=? where ab_id=?";
		
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,addrbook.getAb_name());
			pstmt.setString(2,addrbook.getAb_email());
			pstmt.setString(3,addrbook.getAb_birth());
			pstmt.setString(4,addrbook.getAb_tel());
			pstmt.setString(5,addrbook.getAb_comdept());
			pstmt.setString(6,addrbook.getAb_memo());
			pstmt.setInt(7,addrbook.getAb_id());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//4.�ּҷ� ����
	public boolean deleteDB(int gb_id) throws SQLException {
		connect();
		String sql="delete from addrbook where ab_id=?";
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, gb_id);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			conn.close();
			pstmt.close();
		}
		return true;
		}
	
	//5.�ּҷ� ���
	public ArrayList<AddrBook> getDBList()	
	{
		connect();
		ArrayList<AddrBook> datas=new ArrayList<AddrBook>();
		String sql="select * from addrbook order by ab_id desc";
		
		try {
			pstmt=conn.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				AddrBook addrbook=new AddrBook();
				addrbook.setAb_id(rs.getInt("ab_id"));
				addrbook.setAb_email(rs.getString("ab_email"));
				addrbook.setAb_name(rs.getString("ab_name"));
				addrbook.setAb_comdept(rs.getString("ab_comdept"));
				addrbook.setAb_birth(rs.getString("ab_birth"));
				addrbook.setAb_tel(rs.getString("ab_tel"));
				addrbook.setAb_memo(rs.getString("ab_memo"));
				datas.add(addrbook);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datas;
	}	
	
	//6.������ ����� ���븸�� �������� �κ�(����/���� �� �ʿ�)
	public AddrBook getDB(int gb_id) throws Exception{
		connect();
		
		String sql="select * from addrbook where ab_id=?";
		AddrBook addrbook = new AddrBook();
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,gb_id);
			ResultSet rs = pstmt.executeQuery();
			//�����Ͱ� �ϳ��� �����Ƿ� rs.next()�� �ѹ��� ���� �Ѵ�.
			rs.next();
			addrbook.setAb_id(rs.getInt("ab_id"));
			addrbook.setAb_email(rs.getString("ab_email"));
			addrbook.setAb_name(rs.getString("ab_name"));
			addrbook.setAb_comdept(rs.getString("ab_comdept"));
			addrbook.setAb_birth(rs.getString("ab_birth"));
			addrbook.setAb_tel(rs.getString("ab_tel"));
			addrbook.setAb_memo(rs.getString("ab_memo"));
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return addrbook;
	}
	
}