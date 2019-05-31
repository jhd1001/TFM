<%-- 
    Document   : Form
    Created on : 18-dic-2018, 10:47:02
    Author     : jhuidobro
--%>

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
                <div>
                    <s:actionerror/>
                    <s:actionmessage/>
                </div>
                <div>
                    <h1><s:text name="%{TABLE_TYPE}"/></h1>
                    <h2><s:property value="TABLE_NAME"/></h2>
                    <h3><s:text name="Esquema"/>: <s:property value="TABLE_CAT"/> <s:property value="TABLE_SCHEM"/></h3>
                </div>
                <div id="resultset">
                    <div class="centrado" id="filtro-div">
                        <div class="bordeado">
                            <span>Editar</span>
                            <s:form name="filtro" method="POST" theme="simple" id="filtro" action="editarGuardar">
                                <table class="filtro">
                                    <s:iterator value="listInfo" var="record" status="status">
                                        <tr>
                                            <s:iterator value="#record" status="stat">
                                                <s:set var="campo"><s:property escapeHtml="false"/></s:set>
                                                <s:if test="#stat.first == true">
                                                    <td>
                                                        <s:text name="%{campo}"/>:
                                                        <s:hidden name="formCampos" value="%{campo}"/>
                                                        <s:set var="nombreCampo" value="%{campo}"/>
                                                    </td>
                                                </s:if>
                                                <s:else>
                                                    <td><s:textfield name="formValores" value="%{campo}" disabled="%{isEditDisabled(#nombreCampo)}"/></td>
                                                </s:else>
                                            </s:iterator>
                                        </tr>
                                    </s:iterator>
                                </table>
                                <s:hidden name="TABLE_CAT"/>
                                <s:hidden name="TABLE_SCHEM"/>
                                <s:hidden name="TABLE_NAME"/>
                                <s:hidden name="TABLE_TYPE"/>
                                <s:iterator value="pkArgumentos" status="stat">
                                    <s:set var="pk"><s:property/></s:set>
                                    <s:hidden name="pkArgumentos" value="%{pk}"/>
                                    <s:hidden name="pkValores" value="%{getPkValores(#stat.count-1)}"/>
                                </s:iterator>
                                <s:submit name="guardar" value="Guardar"/>
                            </s:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
