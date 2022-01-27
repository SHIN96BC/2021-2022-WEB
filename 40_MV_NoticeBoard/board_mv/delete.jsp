<%@ page contentType="text/html;charset=utf-8" import="java.util.ArrayList, sbc.mv.model.BoardDTO"%>
<jsp:useBean id="boardDAO" class="sbc.mv.model.BoardDAO" scope="application"/>

<%! 
	private int getSeq(HttpServletRequest request){
		int seq = -1;
		String seqStr = request.getParameter("seq");
		if(seqStr != null){
			seqStr = seqStr.trim();
			if(seqStr.length() != 0){
				try{
					seq = Integer.parseInt(seqStr);
					return seq;
				}catch(NumberFormatException nfe){
				}
			}
		}
		return seq;
	}
%>

<%
	int seq = getSeq(request);
	if(seq == -1){
		response.sendRedirect("list.jsp");
	}else{
		boardDAO.delete(seq);
	}
	response.sendRedirect("list.jsp");
%>