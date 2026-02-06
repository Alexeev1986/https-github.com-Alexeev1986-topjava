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
    <title>Create meal</title>
</head>
<body>
<h1>Edit meal:</h1>

<form method="post" action="/topjava/meals?action=create" enctype="application/x-www-form-urlencoded">
    <label for="dateTime">Enter date: </label>
    <input type="datetime-local" id="dateTime" name="dateTime"
           value="${dateTime}" required>
    <br/>

    <label for="description">Enter description: </label>
    <input type="text" name="description" size="50" value="">
    <br/>

    <label for="calories">Enter calories: </label>
    <input type="text" name="calories" size="50" value="">
    <br/>

    <input type="hidden" name="id" value="0">

    <input type="submit" value="Create!">
</form>
<br/>
<hr/>
<a href="/topjava/meals?action=show">Back</a>
</body>
</html>
