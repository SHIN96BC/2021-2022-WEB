<%@ page contentType="text/html;charset=utf-8" import="javax.sql.DataSource, java.sql.Connection"%>
<jsp:useBean id="dbcp" class="sbc.dbcp.DbcpBean" scope="application"/>

<body style="text-align:center">
<%
	DataSource ds = dbcp.getDs();
	Connection con = ds.getConnection();

%>
	DBCP로부터 얻은 con <%=con%>
</body>