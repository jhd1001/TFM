<%-- 
    Document   : menu
    Created on : 17-dic-2018, 9:47:59
    Author     : jhuidobro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="menu-lateral">
            Menú
            Conexión: <s:property value="connectionImpl.connection"/>
            <ol>
                <s:iterator value="menus">
                    <li>
                        <s:url action="%{action}" var="urlTag">
                            <s:param name="metodo"><s:property value="metodo"/></s:param>
                            <s:param name="parametros"><s:property value="parametros"/></s:param>
                        </s:url>
                        <s:a href="%{urlTag}"><s:property value="%{getText(metodo)}"/></s:a>
                    </li>
                </s:iterator>
            </ol>
        </div>
    </body>
</html>
