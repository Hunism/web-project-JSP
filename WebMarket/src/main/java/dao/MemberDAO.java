package dao;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.MemberDTO;
import mvc.database.DBConnection;

public class MemberDAO extends DBConnection{
	
	private static MemberDAO instance = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return instance;
	}
	
	public int insertMember(MemberDTO member)  {
	      Connection conn = null;
	      int rs = 0;
	      PreparedStatement pstmt = null;
	      
	      try {
	         conn = DBConnection.getConnection();      
	         String sql = "insert into member values(?,?,?,?,?,?,?,?,?)";

	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, member.getId());
	         pstmt.setString(2, member.getPassword());
	         pstmt.setString(3, member.getName());
	         pstmt.setString(4, member.getGender());
	         pstmt.setString(5, member.getBirth());
	         pstmt.setString(6, member.getMail());
	         pstmt.setString(7, member.getPhone());
	         pstmt.setString(8, member.getAdress());
	         pstmt.setNString(9, member.getRegist_day());
	         rs = pstmt.executeUpdate();
	         
	      } catch (Exception ex) {
	         System.out.println("insertProduct() 에러 : " + ex);
	      } finally {
	         try {                           
	            if (pstmt != null) 
	               pstmt.close();            
	            if (conn != null) 
	               conn.close();
	         } catch (Exception ex) {
	            throw new RuntimeException(ex.getMessage());
	         }      
	   } 
	      return rs;
	}

	public int LoginMember(MemberDTO member) {
		Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      try {
	         conn = DBConnection.getConnection();      

	         String sql = "SELECT * FROM MEMBER WHERE ID=? and password=?";

	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, member.getId());
	         pstmt.setString(2, member.getPassword());
	         rs = pstmt.executeQuery();
	         if(rs.next()) {
	        	 return 1;
	         }
	      } catch (Exception ex) {
	         System.out.println("insertProduct() 에러 : " + ex);
	      } finally {
	         try {                           
	            if (pstmt != null) 
	               pstmt.close();            
	            if (conn != null) 
	               conn.close();
	            if (rs != null) 
	            	rs.close();
	         } catch (Exception ex) {
	            throw new RuntimeException(ex.getMessage());
	         }      
	      }
	      return 0;
	   } 

	 public int updateMember(MemberDTO member) {

	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      int rs = 0;
	      try {
	         String sql = "UPDATE MEMBER SET PASSWORD=?, NAME=?, GENDER=?, BIRTH=?, MAIL=?, PHONE=?, ADDRESS=? WHERE ID=?";

	         conn = DBConnection.getConnection();
	         pstmt = conn.prepareStatement(sql);
	         
	         
	         pstmt.setString(1, member.getPassword());
	         pstmt.setString(2, member.getName());
	         pstmt.setString(3, member.getGender());
	         pstmt.setString(4, member.getBirth());
	         pstmt.setString(5, member.getMail());
	         pstmt.setString(6, member.getPhone());
	         pstmt.setString(7, member.getAdress());
	         pstmt.setString(8, member.getId());
	         rs = pstmt.executeUpdate();         
	      } catch (Exception ex) {
	         System.out.println("updateMember() 에러 : " + ex);
	      } finally {
	         try {                              
	            if (pstmt != null) 
	               pstmt.close();            
	            if (conn != null) 
	               conn.close();
	         } catch (Exception ex) {
	            throw new RuntimeException(ex.getMessage());
	         }      
	      }
	      return rs;
	   } 
	 public MemberDTO getMemberById(String id) {
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      MemberDTO member = null;

	      String sql = "select * from member where id = ? ";

	      try {
	         conn = DBConnection.getConnection();
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, id);
	         rs = pstmt.executeQuery();

	         if (rs.next()) {
	            member = new MemberDTO();
	            member.setId(rs.getString("id"));
	            member.setPassword(rs.getString("password"));
	            member.setName(rs.getString("name"));
	            member.setGender(rs.getString("gender"));
	            member.setBirth(rs.getString("birth"));
	            member.setMail(rs.getString("mail"));
	            member.setPhone(rs.getString("phone"));
	            member.setAdress(rs.getString("address"));
	            member.setRegist_day(rs.getString("regist_day"));
	         }
	         
	         return member;
	      } catch (Exception ex) {
	         System.out.println("getBoardByNum() 에러 : " + ex);
	      } finally {
	         try {
	            if (rs != null) 
	               rs.close();                     
	            if (pstmt != null) 
	               pstmt.close();            
	            if (conn != null) 
	               conn.close();
	         } catch (Exception ex) {
	            throw new RuntimeException(ex.getMessage());
	         }      
	      }
	      return null;
	   }
	 public void deleteMember(String id ) {
	      Connection conn = null;
	      PreparedStatement pstmt = null;      
	      String sql = "DELETE FROM member WHERE id = ?";   

	      try {
	         conn = DBConnection.getConnection();
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, id);
	         pstmt.executeUpdate();
	      } catch (Exception ex) {
	         System.out.println("deleteMember() 에러 : " + ex);
	      } finally {
	         try {                              
	            if (pstmt != null) 
	               pstmt.close();            
	            if (conn != null) 
	               conn.close();
	         } catch (Exception ex) {
	            throw new RuntimeException(ex.getMessage());
	         }      
	      }
	   }   

	 
}
