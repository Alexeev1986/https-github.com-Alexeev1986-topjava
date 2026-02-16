<%@ taglib prefix="th" uri="http://jakarta.apache.org/taglibs/standard/permittedTaglibs" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>User: ${userName}</h2>
<form method="post" action="users">
    <input type="hidden" name="action" value="select"/>

    <select name="userId">
        <option value="1" ${selectedUserId == 1 ? 'selected' : ''}>User</option>
        <option value="2" ${selectedUserId == 2 ? 'selected' : ''}>Admin</option>
    </select>

    <input type="submit" value="Select User"/>
</form>
</body>
</html>