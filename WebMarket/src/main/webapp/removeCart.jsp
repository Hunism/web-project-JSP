<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="dto.ProductDTO"%>
<%@ page import="dao.ProductDAO"%>
<%
String id = request.getParameter("id");
	if (id == null || id.trim().equals("")) {
		response.sendRedirect("/ListAction.pr");
		return;
	}

	ProductDAO dao = ProductDAO.getInstance();
	
	ProductDTO product = dao.getProductById(id);
	if (product == null) {
		response.sendRedirect("/exceptionNoProductIdAction.pr");
	}

	ArrayList<ProductDTO> cartList = (ArrayList<ProductDTO>) session.getAttribute("cartlist");
	ProductDTO goodsQnt = new ProductDTO();
	for (int i = 0; i < cartList.size(); i++) { // 상품리스트 하나씩 출력하기
		goodsQnt = cartList.get(i);
		if (goodsQnt.getProductId().equals(id)) {
	cartList.remove(goodsQnt);
		}
	}

	response.sendRedirect("/CartAction.pr");
%>
