<%-- 
    Document   : inicial
    Created on : 11-dic-2018, 12:06:46
    Author     : jhuidobro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/general.css">
        <title>My DataBase JC</title>
    </head>
    <body>
        <div id="page">
            <s:include value="/WEB-INF/jspf/cabecera.jsp"/>
            <s:include value="/WEB-INF/jspf/menu.jsp"/>
            <div id="contenido-unico">
                <s:actionerror/>
                <s:actionmessage/>
                <h1>INICIAL</h1>
                <div class="centrado">
                    <table class="tabla">
                        <tr>
                            <th>Propiedad</th>
                            <th>Valor</th>
                        </tr>
                        <s:iterator value="listInicial">
                            <tr>
                                <td>
                                    <%--<s:i18n name="es.ubu.alu.mydatabasejc.connectionInfo">--%>
                                    <s:property value="%{getText(propiedad)}"/>
                                    <%--</s:i18n>--%>
                                </td>
                                <td><s:property value="valor"/></td>
                            </tr>
                        </s:iterator>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
