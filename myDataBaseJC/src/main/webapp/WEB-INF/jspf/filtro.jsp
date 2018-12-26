<%-- 
    Document   : filtro
    Created on : 18-dic-2018, 10:47:02
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
        <div id="filtro">
            <s:form name="filtro" method="POST" theme="simple">
                <s:set var="counter" value="0"/>
                <ul>
                    <s:iterator value="arrayParametros">
                        <li>
                            <s:set var="filtro"><s:property/></s:set>
                            <s:text name="%{filtro}" />: 
                            <s:hidden name="filtroArgumentos" value="%{filtro}"/>
                            <s:textfield name="filtroValores" value="%{getParameter(#counter)}"/>
                        </li>
                        <s:set var="counter" value="%{#counter+1}"/>
                    </s:iterator>
                </ul>
                <s:if test="#counter != 0">
                    <s:hidden name="metodo"/>
                    <s:hidden name="parametros"/>
                    <s:hidden name="TABLE_SCHEM"/>
                    <s:hidden name="TABLE_NAME"/>
                    <s:submit name="filtrar"/>
                </s:if>
            </s:form>
        </div>
    </body>
</html>
