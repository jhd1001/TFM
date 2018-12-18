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
                <s:hidden name="metodo"/>
                <s:hidden name="parametros"/>
                <ul>
                    <s:set var="counter" value="0"/>
                    <s:iterator value="camposFiltro">
                        <li>
                            <s:set var="filtro"><s:property /></s:set>
                            <s:text name="%{filtro}" />: 
                            <s:i18n name="es.ubu.alu.mydatabasejc.filtro">
                                <s:text name="%{filtro}">
                                    <s:param><s:property value="%{getParameter(#counter)}"/></s:param>
                                </s:text>
                            </s:i18n>
                        </li>
                        <s:set var="counter" value="%{#counter+1}"/>
                    </s:iterator>
                </ul>
                <s:submit name="filtrar"/>
            </s:form>
        </div>
    </body>
</html>
