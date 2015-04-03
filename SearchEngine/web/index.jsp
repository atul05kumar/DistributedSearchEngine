<%-- 
    Document   : index
    Created on : 29 Nov, 2014, 3:04:07 AM
    Author     : ankur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Engine</title>
    </head>
    <body>
        <div align="center" style="margin-top: 210px;">
            <font color="blue" style="font-size: 32px;"><h2>Search Engine</h2></font>
            <form name="search" action="SearchEngine" method="post">
                <input type="text" name="searchquery" size="32" maxlength="32"/>

                <input type="submit" value="Search"/>
            </form>
        </div>

    </body>
</html>
