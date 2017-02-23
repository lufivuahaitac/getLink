<%-- 
    Document   : redirect
    Created on : Oct 11, 2016, 8:38:32 AM
    Author     : Nguyen Van Nhan
--%>

<%--
Views should be stored under the WEB-INF folder so that
they are not accessible except through controller process.

This JSP is here to provide a redirect to the dispatcher
servlet but should be the only JSP outside of WEB-INF.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% response.sendRedirect("getlink"); %>
