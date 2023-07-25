<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String sessionId = (String) session.getAttribute("sessionId");
%>
<nav class="navbar navbar-expand  navbar-dark bg-dark">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="/welcome.jsp">Home</a>
		</div>
		<div>
			<ul class="navbar-nav mr-auto">
				<c:choose>
					<c:when test="${empty sessionId}">
						<li class="nav-item"><a class="nav-link" href="<c:url value="/LoginView.me"/>">로그인 </a></li>
						<li class="nav-item"><a class="nav-link" href='<c:url value="/AddMemberView.me"/>'>회원 가입</a></li>
					</c:when>
					<c:otherwise>
						<li style="padding-top: 7px; color: white">[<%=sessionId%>님]</li>
						<li class="nav-item"><a class="nav-link" href="<c:url value="/member/logoutMember.jsp"/>">로그아웃 </a></li>
						<li class="nav-item"><a class="nav-link" href="<c:url value="/UpdateView.me"/>">회원 수정</a></li>
					</c:otherwise>
				</c:choose>
				<li class="nav-item"><a class="nav-link" href="<c:url value="/ListAction.pr"/>">상품 목록</a></li>
				<li class="nav-item"><a class="nav-link" href="<c:url value="/AddAction.pr"/>">상품 등록</a></li>
				<li class="nav-item"><a class="nav-link" href="<c:url value="/UpdateActionView.pr"/>">상품 수정</a></li>
				<li class="nav-item"><a class="nav-link" href="<c:url value="/DeleteAction.pr"/>">상품 삭제</a></li>
				<li class="nav-item"><a class="nav-link" href="<c:url value="/BoardListAction.do?pageNum=1"/>">게시판</a></li>
			</ul>
		</div>
	</div>
</nav>
