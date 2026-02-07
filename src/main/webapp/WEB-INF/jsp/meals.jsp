
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список еды</title>
</head>
<body>
<h3><a href="/topjava">Home</a></h3>
<table border="2">
    <caption>Meals</caption>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${mealsTo}" var="mealTo">
    <tr>
        <td style="${mealTo.excess ? 'color: red;' : 'green'}">${mealTo.formattedDateTime}</td>
        <td style="${mealTo.excess ? 'color: red;' : 'green'}">${mealTo.description}</td>
        <td style="${mealTo.excess ? 'color: red;' : 'green'}">${mealTo.calories}</td>
        <td><a href="${pageContext.request.contextPath}/meals?action=update&id=${mealTo.id}">Edit</a></td>
        <td><a href="${pageContext.request.contextPath}/meals?action=delete&id=${mealTo.id}">Delete</a></td>
    </tr>
    </c:forEach>
</table>

<a href="/topjava/meals?action=create">Create new meal</a>
</body>
</html>
