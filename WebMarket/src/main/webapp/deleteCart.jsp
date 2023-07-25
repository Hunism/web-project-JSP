<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="dto.ProductDTO"%>
<%@ page import="dao.ProductDAO"%>
<%
	String id = request.getParameter("cartId");
	if (id == null || id.trim().equals("")) {
		response.sendRedirect("/CartAction.pr");
		return;
	}

	session.invalidate();
	response.sendRedirect("/CartAction.pr");
%>
