<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form:form action="send" modelAttribute="register">
	name:<form:input path="name"/>
	mail:<form:input type="email" path="email"/>
	<input type="submit"/>

</form:form>

</body>
</html>