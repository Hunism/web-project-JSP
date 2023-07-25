package mvc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.ProductDAO;
import dto.ProductDTO;




public class ProductController extends HttpServlet {
   private static final long serialVersionUID = 1L;
   static final int LISTCOUNT = 5; 

   List<ProductDTO> productsList = null;
   ProductDTO product = null;



   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request, response);
   }
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      String RequestURI = request.getRequestURI();
      String contextPath = request.getContextPath();
      String command = RequestURI.substring(contextPath.length());

      response.setContentType("text/html; charset=utf-8");
      request.setCharacterEncoding("utf-8");

      if (command.equals("/ListAction.pr")) {//등록된 글 목록 페이지 출력하기
         RequestDispatcher rd = request.getRequestDispatcher("/products.jsp");
         rd.forward(request, response);
      }else if(command.equals("/ViewAction.pr")) {
         RequestDispatcher rd = request.getRequestDispatcher("/product.jsp");
         rd.forward(request, response);
      }else if(command.equals("/AddAction.pr")) {
         RequestDispatcher rd = request.getRequestDispatcher("/addProduct.jsp");
         rd.forward(request, response);
      }else if(command.equals("/WriteAction.pr")) {
         try {
            requestProductsWrite(request);
         } catch (SQLException | IOException e) {
            e.printStackTrace();
         }
         RequestDispatcher rd = request.getRequestDispatcher("/products.jsp");
         rd.forward(request, response);
      }
      else if(command.equals("/UpdateActionView.pr")) {
         RequestDispatcher rd = request.getRequestDispatcher("/editProduct.jsp?edit=update");
         rd.forward(request, response);
      }
      else if(command.equals("/UpdateAction.pr")) {
         try {
            requestProductsUpdate(request);
         } catch (IOException | SQLException e) {
            e.printStackTrace();
         }
         RequestDispatcher rd = request.getRequestDispatcher("/updateProduct.jsp");
         rd.forward(request, response);
      }
      else if(command.equals("/DeleteAction.pr")) {
         RequestDispatcher rd = request.getRequestDispatcher("/editProduct.jsp?edit=delete");
         rd.forward(request, response);
      }
      else if(command.equals("/CartAction.pr")) {
          RequestDispatcher rd = request.getRequestDispatcher("/cart.jsp");
          rd.forward(request, response);
       }
      else if(command.equals("/AddCartAction.pr")) {
          RequestDispatcher rd = request.getRequestDispatcher("/addCart.jsp");
          rd.forward(request, response);
       }
      else if(command.equals("/exceptionNoProductIdAction.pr")) {
          RequestDispatcher rd = request.getRequestDispatcher("/addCart.jsp");
          rd.forward(request, response);
       }
      else if(command.equals("/CancelledAction.pr")) {
          RequestDispatcher rd = request.getRequestDispatcher("/checkOutCancelled.jsp");
          rd.forward(request, response);
       }
      else if(command.equals("/ShippingAction.pr")) {
          RequestDispatcher rd = request.getRequestDispatcher("/shippingInfo.jsp");
          rd.forward(request, response);
       }
      else if(command.equals("/OrderAction.pr")) {
          RequestDispatcher rd = request.getRequestDispatcher("/orderConfirmation.jsp");
          rd.forward(request, response);
       }
      else if(command.equals("/ThankAction.pr")) {
          RequestDispatcher rd = request.getRequestDispatcher("/thankCustomer.jsp");
          rd.forward(request, response);
       }
     
   }
   public void requestProductsUpdate(HttpServletRequest request) throws IOException, SQLException {
      
      String filename = "";
      String realFolder = "E:/kdigital/jsp/jspws/WebMarket/src/main/webapp/upload"; //웹 어플리케이션상의 절대 경로
      String encType = "utf-8"; //인코딩 타입
      int maxSize = 5 * 1024 * 1024; //최대 업로드될 파일의 크기5Mb

      MultipartRequest multi = new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());
      String productId = multi.getParameter("productId");
      String name = multi.getParameter("name");
      Integer unitPrice = Integer.parseInt(multi.getParameter("unitPrice"));
      String description = multi.getParameter("description");
      String manufacturer = multi.getParameter("manufacturer");
      String category = multi.getParameter("category");
      Long unitsInStock = Long.parseLong(multi.getParameter("unitsInStock"));
      String condition = multi.getParameter("condition");

      Integer price;

      if (unitPrice == 0)
         price = 0;
      else
         price = Integer.valueOf(unitPrice);

      long stock;

      if (unitsInStock == 0)
         stock = 0;
      else
         stock = Long.valueOf(unitsInStock);

      Enumeration files = multi.getFileNames();
      String fname = (String) files.nextElement();
      String fileName = multi.getFilesystemName(fname);   
      
      ProductDAO dao = ProductDAO.getInstance();
      ProductDTO product = new ProductDTO();

      product.setProductId(productId);
      product.setPname(name);
      product.setUnitPrice(unitPrice);
      product.setDescription(description);
      product.setManufacturer(manufacturer);
      product.setCategory(category);
      product.setUnitsInStock(stock);
      product.setCondition(condition);
      product.setFilename(fileName);
      try {
    	  dao.productUpadte(product);
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      
      
   }
   public void requestProductsWrite(HttpServletRequest request) throws SQLException, IOException  {
      request.setCharacterEncoding("UTF-8");

      String filename = "";
      String realFolder = "E:/kdigital/jsp/jspws/WebMarket/src/main/webapp/upload"; //웹 어플리케이션상의 절대 경로
      String encType = "utf-8"; //인코딩 타입
      int maxSize = 5 * 1024 * 1024; //최대 업로드될 파일의 크기5Mb

      MultipartRequest multi = new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());

      String productId = multi.getParameter("productId");
      String name = multi.getParameter("name");
      Integer unitPrice = Integer.parseInt(multi.getParameter("unitPrice"));
      String description = multi.getParameter("description");
      String manufacturer = multi.getParameter("manufacturer");
      String category = multi.getParameter("category");
      Long unitsInStock = Long.parseLong(multi.getParameter("unitsInStock"));
      String condition = multi.getParameter("condition");

      Integer price;

      if (unitPrice == 0)
         price = 0;
      else
         price = Integer.valueOf(unitPrice);

      long stock;

      if (unitsInStock == 0)
         stock = 0;
      else
         stock = Long.valueOf(unitsInStock);

      Enumeration files = multi.getFileNames();
      String fname = (String) files.nextElement();
      String fileName = multi.getFilesystemName(fname);

      ProductDAO dao = ProductDAO.getInstance();
      ProductDTO product = new ProductDTO();

      product.setProductId(productId);
      product.setPname(name);
      product.setUnitPrice(unitPrice);
      product.setDescription(description);
      product.setManufacturer(manufacturer);
      product.setCategory(category);
      product.setUnitsInStock(stock);
      product.setCondition(condition);
      product.setFilename(fileName);

      dao.insertProduct(product);



   }
   


}