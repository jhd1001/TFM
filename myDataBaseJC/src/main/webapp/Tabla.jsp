<%-- 
    Document   : inicial
    Created on : 11-dic-2018, 12:06:46
    Author     : jhuidobro
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My DataBase JC</title>
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/general.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/ordenacion.css">
    </head>
    <body>
        <div id="page">
            <s:include value="/WEB-INF/jspf/cabecera.jsp"/>
            <s:include value="/WEB-INF/jspf/menu.jsp"/>
            <div id="contenido-unico">
                <s:actionerror/>
                <s:actionmessage/>
                <h1><s:text name="%{TABLE_TYPE}"/></h1>
                <h2><s:property value="TABLE_NAME"/></h2>
                <h3><s:text name="Esquema"/>: <s:property value="TABLE_SCHEM"/></h3>
                <s:include value="/WEB-INF/jspf/filtro.jsp"/>
                <div id="resultset">
                    <table class="tabla">
                        <s:iterator value="listInfo" var="record" status="status">
                            <s:if test="#status.first == true"><thead></s:if>
                                    <tr>
                                    <s:iterator value="#record" status="stat">
                                        <s:if test="#status.first == true">
                                                <th><s:property /></th>
                                        </s:if>
                                        <s:else>
                                                <td><s:property/></td>
                                        </s:else>
                                    </s:iterator>
                                </tr>
                                <s:if test="#status.first == true"></thead></s:if>
                            </s:iterator>
                    </table>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/resources/ordenacion.js"></script>
    </body>
</html>
