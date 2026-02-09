<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>${meal.id != null ? 'Edit meal' : 'Create meal'}</title>
</head>
<body>
<h1>${meal.id != null ? 'Edit meal' : 'Create meal'}:</h1>

<form method="post" action="${pageContext.request.contextPath}/meals?action=${meal.id != null ? 'update' : 'create'}">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <label for="dateTime">Enter date: </label>
    <jsp:useBean id="FORMATTER" scope="request" type="java.time.format.DateTimeFormatter"/>
    <input type="datetime-local" name="dateTime"
           value="${meal.dateTime.format(FORMATTER)}" required>
    <br/>
    <label for="description">Enter description: </label>
    <input type="text" name="description" size="50"
           value="${meal.description}">
    <br/>
    <label for="calories">Enter calories: </label>
    <input type="text" name="calories" size="50"
           value="${meal.calories}">
    <br/>

    <c:if test="${meal.id != null}">
        <input type="hidden" name="id" value="${meal.id}">
    </c:if>

    <input type="submit" value="${meal.id != null ? 'Update!' : 'Create!'}">
</form>
<br/>
<hr/>
<a href="${pageContext.request.contextPath}/meals?action=show">Back</a>
</body>
</html>