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
        <div class="centrado" id="filtro-div">
            <s:if test="arrayParametros.size()!=0">
                <div class="bordeado">
                    <span>Filtro</span>
                    <s:form name="filtro" method="POST" theme="simple" id="filtro">
                        <table class="filtro">
                            <s:iterator value="arrayParametros" status="stat">
                                <s:if test="#stat.odd == true">
                                    <tr id="odd">
                                </s:if>
                                <s:set var="filtro"><s:property escapeHtml="false"/></s:set>
                                <td>
                                    <s:text name="%{filtro}" />: 
                                    <s:hidden name="filtroArgumentos" value="%{filtro}"/>
                                </td>
                                <td><s:textfield name="filtroValores" value="%{getParameter(#stat.count-1)}"/></td>
                                <s:if test="#stat.even == true">
                                    </tr>
                                </s:if>
                            </s:iterator>
                        </table>
                        <s:hidden name="metodo"/>
                        <s:hidden name="parametros"/>
                        <s:hidden name="TABLE_SCHEM"/>
                        <s:hidden name="TABLE_NAME"/>
                        <s:submit name="filtrar" value="filtrar"/>
                    </s:form>
                </div>
            </s:if>
        </div>
    </body>
</html>
