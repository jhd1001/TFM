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
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/menu.css">
        <!--script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script-->
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/menu.js"></script>
        <title>menu</title>
    </head>
    <body>
        <div id="cssmenu" style="width: 230px; float: left">
            <ul>
                <li><a href="#"><span>Conexión<br><s:property value="connectionImpl.usuario"/></span></a></li>
                <li class="has-sub"><a href="#"><span>Acerca de</span></a>
                    <ul>
                        <li><a href="#"><span>Empresa</span></a></li>
                        <li class="last"><a href="#"><span>Contacto</span></a></li>
                    </ul>
                </li>
                <li class="last"><a href="#"><span>Contacto</span></a></li>
                <li class="has-sub active"><a href="#"><span>Métodos DatabaseMetaData</span></a>
                    <ul style="display: block;">
                        <s:iterator value="menus">
                            <li>
                                <s:url action="%{action}" var="urlTag" namespace="/DatabaseMetaData">
                                    <s:param name="metodo"><s:property value="metodo"/></s:param>
                                    <s:param name="parametros"><s:property value="parametros"/></s:param>
                                </s:url>
                                <s:a href="%{urlTag}"><s:property value="%{getText(metodo)}"/></s:a>
                            </li>
                        </s:iterator>
                    </ul>
                </li>
            </ul>
        </div>
    </body>
</html>
