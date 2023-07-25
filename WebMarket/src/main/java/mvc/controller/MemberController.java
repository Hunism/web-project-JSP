package mvc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDAO;
import dto.MemberDTO;

public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final int LISTCOUNT = 5; 

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());

		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");

		if (command.equals("/AddMember.me")) {
			int rs = requestMemberAdd(request);
			if(rs == 1) {
				RequestDispatcher rd = request.getRequestDispatcher("/member/resultMember.jsp?msg=1");
				rd.forward(request, response);
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/member/addMember.jsp");
				rd.forward(request, response);
			}
		} else if(command.equals("/AddMemberView.me")) {
			RequestDispatcher rd = request.getRequestDispatcher("/member/addMember.jsp");
			rd.forward(request, response);
		}
		else if(command.equals("/LoginView.me")) {
			RequestDispatcher rd = request.getRequestDispatcher("/member/loginMember.jsp");
			rd.forward(request, response);
		}else if(command.equals("/Login.me")) {
			int rs = requestLogin(request);
			if(rs==1) {
				RequestDispatcher rd = request.getRequestDispatcher("/member/resultMember.jsp?msg=2");
				rd.forward(request, response);
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/member/loginMember.jsp?error=1");
				rd.forward(request, response);
			}
		}
		else if(command.equals("/UpdateView.me")) {
			requestMemberView(request);
			RequestDispatcher rd = request.getRequestDispatcher("/member/updateMember.jsp");
			rd.forward(request, response);
		}
		else if(command.equals("/Update.me")) {
			int rs = requestMemberUpdate(request);
			if(rs==1) {
				RequestDispatcher rd = request.getRequestDispatcher("/member/resultMember.jsp?msg=0");
				rd.forward(request, response);
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/member/updateMember.jsp");
				rd.forward(request, response);
			}
		}
		else if(command.equals("/Delete.me")) {
			requestMemberDelete(request);
			RequestDispatcher rd = request.getRequestDispatcher("/member/resultMember.jsp");
			rd.forward(request, response);
		}
		
	}

	public int requestMemberAdd(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String year = request.getParameter("birthyy");
		String month = request.getParameter("birthmm");
		String day = request.getParameter("birthdd");
		String birth = year + "/" + month + "/" + day;
		String mail1 = request.getParameter("mail1");
		String mail2 = request.getParameter("mail2");
		String mail = mail1 + "@" + mail2;
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");

		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		String currentTimestampToString = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(currentTimestamp);

		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO member = new MemberDTO();
		member.setId(id);
		member.setPassword(password);
		member.setName(name);
		member.setGender(gender);
		member.setBirth(birth);
		member.setMail(mail);
		member.setPhone(phone);
		member.setAdress(address);
		member.setRegist_day(currentTimestampToString);
		int rs = dao.insertMember(member);
		return rs;
	}

	public int requestLogin(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");
		String password = request.getParameter("password");

		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO member = new MemberDTO();
		member.setId(id);
		member.setPassword(password);
		int rs = dao.LoginMember(member);
		if(rs == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("sessionId", id);
		}
		return rs;
	}
	public void requestMemberView(HttpServletRequest request){

		MemberDAO dao = MemberDAO.getInstance();
		String id = request.getParameter("id");

		MemberDTO member = new MemberDTO();
		member = dao.getMemberById(id);      

		request.setAttribute("member", member); 
		//System.out.println(member);
	}

	//선택된 글 내용 수정하기
	public int requestMemberUpdate(HttpServletRequest request){
		String year = request.getParameter("birthyy");
		String month = request.getParameter("birthmm");
		String day = request.getParameter("birthdd");
		String birth = year + "/" + month + "/" + day;
		String mail1 = request.getParameter("mail1");
		String mail2 = request.getParameter("mail2");
		String mail = mail1 + "@" + mail2;

		MemberDAO dao = MemberDAO.getInstance();      

		MemberDTO member = new MemberDTO();
		member.setId(request.getParameter("id"));
		member.setPassword(request.getParameter("password"));
		member.setName(request.getParameter("name"));
		member.setGender(request.getParameter("gender"));
		member.setBirth(birth);
		member.setMail(mail);
		member.setPhone(request.getParameter("phone"));
		member.setAdress(request.getParameter("address"));
		int rs = dao.updateMember(member);   
		return rs;
	}
	//선택된 멤버 삭제하기
		public void requestMemberDelete(HttpServletRequest request){

			String id = request.getParameter("id");

			MemberDAO dao = MemberDAO.getInstance();
			dao.deleteMember(id);							
		}
}   

