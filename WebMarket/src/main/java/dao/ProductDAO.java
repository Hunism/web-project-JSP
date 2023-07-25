package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import dto.ProductDTO;
import mvc.database.DBConnection;
import mvc.model.BoardDTO;

public class ProductDAO extends DBConnection{

   private ArrayList<ProductDTO> listOfProducts = new ArrayList<ProductDTO>();
   private static ProductDAO instance = new ProductDAO();

   public static ProductDAO getInstance(){
      return instance;
   } 
   public ArrayList<ProductDTO> selectList(Connection conn) throws SQLException{
      listOfProducts = new ArrayList<ProductDTO>();
      //디비에서 select해서 리스트에 담고 그걸 반환
      String sql = "select * from product";
      try(PreparedStatement pst = conn.prepareStatement(sql)){
         try(ResultSet rs = pst.executeQuery()){
            while(rs.next()) {
               ProductDTO product = new ProductDTO(); //product 객체선언 
               product.setProductId(rs.getString("p_id")); //각각 컬럼값 디비에서 가져와서 객체에 저장
               product.setPname(rs.getString("p_name"));    
               product.setUnitPrice(rs.getInt("p_unitPrice"));  
               product.setDescription(rs.getString("p_description"));  
               product.setManufacturer(rs.getString("p_manufacturer"));            
               product.setCategory(rs.getString("p_category"));  
               product.setUnitsInStock(rs.getLong("p_unitsInStock"));  
               product.setCondition(rs.getString("p_condition"));  
               product.setFilename(rs.getString("p_filename"));
               listOfProducts.add(product);// 결과 목록에 저장
            }
         }
      }
      return listOfProducts;
   }

   public ArrayList<ProductDTO> getAllProducts() {
      return listOfProducts;
   }

   public ProductDTO getProductById(String productId) {
      ProductDTO productById = null;

      for (int i = 0; i < listOfProducts.size(); i++) {
         ProductDTO product = listOfProducts.get(i);
         if (product != null && product.getProductId() != null && product.getProductId().equals(productId)) {
            productById = product;
            break;
         }
      }
      return productById;
   }

   public void addProduct(ProductDTO product) {
      listOfProducts.add(product);

   }
   public void insertProduct(ProductDTO product)  {

      Connection conn = null;
      PreparedStatement pstmt = null;
      try {
         conn = DBConnection.getConnection();      

         String sql = "insert into product values(?,?,?,?,?,?,?,?,?)";

         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, product.getProductId());
         pstmt.setString(2, product.getPname());
         pstmt.setInt(3, product.getUnitPrice());
         pstmt.setString(4, product.getDescription());
         pstmt.setString(5, product.getManufacturer());
         pstmt.setString(6, product.getCategory());
         pstmt.setLong(7, product.getUnitsInStock());
         pstmt.setString(8, product.getCondition());
         pstmt.setString(9, product.getFilename());
         pstmt.executeUpdate();

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
   } 

   public void productUpadte(ProductDTO dto) throws SQLException, ClassNotFoundException {

      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      conn = DBConnection.getConnection();      
      String sql = "select * from product where p_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, dto.getProductId());
      rs = pstmt.executeQuery();   

      if (rs.next()) {      
         if (dto.getFilename() != null) {
            sql = "UPDATE product SET p_name=?, p_unitPrice=?, p_description=?, p_manufacturer=?, p_category=?, p_unitsInStock=?, p_condition=?, p_fileName=? WHERE p_id=?";   
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getPname());
            pstmt.setInt(2, dto.getUnitPrice());
            pstmt.setString(3, dto.getDescription());
            pstmt.setString(4, dto.getManufacturer());
            pstmt.setString(5, dto.getCategory());
            pstmt.setLong(6, dto.getUnitsInStock());
            pstmt.setString(7, dto.getCondition());
            pstmt.setString(8, dto.getFilename());
            pstmt.setString(9, dto.getProductId());   
            pstmt.executeUpdate();
         } else {
            sql = "UPDATE product SET p_name=?, p_unitPrice=?, p_description=?, p_manufacturer=?, p_category=?, p_unitsInStock=?, p_condition=? WHERE p_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getPname());
            pstmt.setInt(2, dto.getUnitPrice());
            pstmt.setString(3, dto.getDescription());
            pstmt.setString(4, dto.getManufacturer());
            pstmt.setString(5, dto.getCategory());
            pstmt.setLong(6, dto.getUnitsInStock());
            pstmt.setString(7, dto.getCondition());
            pstmt.setString(8, dto.getProductId());   ;
            pstmt.executeUpdate();         
         }      
      }
      if (rs != null)
         rs.close();
      if (pstmt != null)
         pstmt.close();
      if (conn != null)
         conn.close();

   }
}