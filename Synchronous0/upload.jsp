<%@ page contentType="text/html;charset=gb2312"%>
<html>
	<title>
		<%= application.getServerInfo() %>
	</title>
		<body>
			<form action = "doUpload.jsp" method = "post" enctype = "multipart/form-data">
				<input type = "file" name = "upfile" size = "50">
				<input type = "submit" value = "��ӵ�������">
			</form>
		</body>
</html>
