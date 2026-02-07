
<%--
  Created by IntelliJ IDEA.
  User: relotech
  Date: 07.02.2026
  Time: 22:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>${editMode eq 'true' ? 'Edit meal' : 'Create meal'}</title>
</head>
<body>
<h1>${editMode eq 'true' ? 'Edit meal' : 'Create meal'}:</h1>

<form method="post" action="${pageContext.request.contextPath}/meals?action=${editMode eq 'true' ? 'update' : 'create'}"
      enctype="application/x-www-form-urlencoded">
    <label for="dateTime">Enter date: </label>
    <input type="datetime-local" id="dateTime" name="dateTime"
           value="${dateTime}" required>
    <br/>

    <label for="description">Enter description: </label>
    <input type="text" name="description" size="50" value="${editMode eq 'true' ? meal.description : ''}">
    <br/>

    <label for="calories">Enter calories: </label>
    <input type="text" name="calories" size="50" value="${editMode eq 'true' ? meal.calories : ''}">
    <br/>

    <c:if test="${editMode eq 'true'}">
        <input type="hidden" name="id" value="${meal.id}">
    </c:if>

    <input type="submit" value="${editMode eq 'true' ? 'Update!' : 'Create!'}">
</form>
<br/>
<hr/>
<a href="${pageContext.request.contextPath}/meals?action=show">Back</a>
</body>
</html>