
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Список еды</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h3><a href="${pageContext.request.contextPath}/">Home</a></h3>
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
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr class="${mealTo.excess ? 'excess-row' : 'normal-row'}">
        <td>${mealTo.dateTime.format(FORMATTER)}</td>
        <td>${mealTo.description}</td>
        <td>${mealTo.calories}</td>
        <td><a href="${pageContext.request.contextPath}/meals?action=update&id=${mealTo.id}">Edit</a></td>
        <td><a href="${pageContext.request.contextPath}/meals?action=delete&id=${mealTo.id}">Delete</a></td>
    </tr>
    </c:forEach>
</table>

<a href="${pageContext.request.contextPath}/meals?action=create">Create new meal</a>
</body>
</html>
