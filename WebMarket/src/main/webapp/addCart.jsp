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

	ArrayList<ProductDTO> goodsList = dao.getAllProducts();
	ProductDTO goods = new ProductDTO();
	for (int i = 0; i < goodsList.size(); i++) {
		goods = goodsList.get(i);
		if (goods.getProductId().equals(id)) { 			
	break;
		}
	}
	
	ArrayList<ProductDTO> list = (ArrayList<ProductDTO>) session.getAttribute("cartlist");
	if (list == null) { 
		list = new ArrayList<ProductDTO>();
		session.setAttribute("cartlist", list);
	}

	int cnt = 0;
	ProductDTO goodsQnt = new ProductDTO();
	for (int i = 0; i < list.size(); i++) {
		goodsQnt = list.get(i);
		if (goodsQnt.getProductId().equals(id)) {
	cnt++;
	int orderQuantity = goodsQnt.getQuantity() + 1;
	goodsQnt.setQuantity(orderQuantity);
		}
	}

	if (cnt == 0) { 
		goods.setQuantity(1);
		list.add(goods);
	}

	response.sendRedirect("/ViewAction.pr?id=" + id);
%>