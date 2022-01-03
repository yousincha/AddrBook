<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ page import="java.util.ArrayList"%>
<%@ page import="addrbook.AddrBook"%>

<!DOCTYPE html>
<%request.setCharacterEncoding("utf-8"); %>
<jsp:useBean id="addrbook" class="addrbook.AddrBook"/>
<jsp:setProperty property="*" name="addrbook"/>
<jsp:useBean id="ab" class="addrbook.AddrBean"/>

<html>
<head>
<meta charset="UTF-8">
<title>addrbook_control</title>
</head>
<body>
<%
	String action=request.getParameter("action");
	
	if(action.equals("insert")){
		//데이터베이스에 삽입 될 수 있도록 해주는 문장으로 분기
		 if(ab.insertDB(addrbook)){
			 response.sendRedirect("addrbook_control.jsp?action=list");
		 };
		
	
	//주소록 수정 페이지 요청한  경우
	}else if(action.equals("edit")){
		  AddrBook abook = ab.getDB(addrbook.getAb_id());
		  if(!request.getParameter("upasswd").equals("1234")){
			  out.println("<script>alert('비밀번호가 틀렸습니다.!!');history.go(-1);</script>");
		  }
	      else {
			request.setAttribute("ab",abook);
			pageContext.forward("addrbook_edit_form.jsp");
		 }	
	}else if(action.equals("update")){
			if(ab.updateDB(addrbook)){
			response.sendRedirect("addrbook_control.jsp?action=list");
		}
		else
			throw new Exception("DB 갱신오류");
	}
	else if(action.equals("delete")){
		if(ab.deleteDB(addrbook.getAb_id())){
			response.sendRedirect("addrbook_control.jsp?action=list");
		}
		else
			throw new Exception("삭제오류");
	}
		else if(action.equals("list")){
			ArrayList<AddrBook> datas=ab.getDBList();
			request.setAttribute("datas",datas);
			pageContext.forward("addrbook_list.jsp");
	}
	
%>
</body>
</html>