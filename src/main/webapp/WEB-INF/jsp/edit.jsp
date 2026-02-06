<%--
  Created by IntelliJ IDEA.
  User: relotech
  Date: 06.02.2026
  Time: 20:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit meal</title>
</head>
<body>
<h1>Edit meal:</h1>

<form method="post" action="/topjava/meals?action=update" enctype="application/x-www-form-urlencoded">
    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
    <label for="dateTime">Enter date: </label>
    <input type="text" name="dateTime" size="50" value="${meal.dateTime}">
    <br/>

    <label for="description">Enter description: </label>
    <input type="text" name="description" size="50" value="${meal.description}">
    <br/>

    <label for="calories">Enter calories: </label>
    <input type="text" name="calories" size="50" value="${meal.calories}">
    <br/>

    <input type="hidden" name="id" value="${meal.id}">

    <input type="submit" value="Update!">
</form>
<br/>
<hr/>
<a href="/topjava/meals?action=show">Back</a>
</body>
</html>
