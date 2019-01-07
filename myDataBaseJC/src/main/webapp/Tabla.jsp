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
        <title>My DataBase JC</title>
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/general.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/ordenacion.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/ayuda.css">
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
                    <h3><s:text name="Esquema"/>: <s:property value="TABLE_SCHEM"/></h3>
                </div>
                <s:include value="/WEB-INF/jspf/filtro.jsp"/>
                <div id="resultset">
                    <table class="tabla">
                        <s:iterator value="listInfo" var="record" status="status">
                            <s:if test="#status.first == true"><thead></s:if>
                                <tr onmouseover='$(this).children("td").children("ul.bqY").removeClass("ocultar")' 
                                    onmouseout='$(this).children("td").children("ul.bqY").addClass("ocultar")'>
                                    <s:iterator value="#record" status="stat">
                                        <!-- primera columna -->
                                        <s:if test="#stat.first == true && metodoLink">
                                            <!-- primera fila -->
                                            <s:if test="#status.first == true">
                                                <th><s:text name=""/></th>
                                            </s:if>
                                            <!-- resto de filas -->
                                            <s:else>
                                                <td>
                                                    <ul class="bqY ocultar">
                                                        <s:url action="borrar" var="urlBru" includeParams="get" escapeAmp="false"/>
                                                        <s:set var="url"><s:property value="urlBru"/>&<s:property/></s:set>
                                                        <li class="bqX" title="<s:property value='%{ayuda("borrar.registro")}'/>">
                                                            <s:a  href="%{url}" class="acciones bru">&nbsp;&nbsp;</s:a>
                                                        </li>
                                                        <s:url action="editar" var="urlEdt" includeParams="get" escapeAmp="false"/>
                                                        <s:set var="urle"><s:property value="urlEdt"/>&<s:property/></s:set>
                                                        <li class="bqX" title="<s:property value='%{ayuda("update.registro")}'/>">
                                                            <s:a  href="%{urle}" class="acciones edt">&nbsp;&nbsp;</s:a>
                                                        </li>
                                                        <s:url action="insertar" var="urlIns" includeParams="get" escapeAmp="false"/>
                                                        <li class="bqX" title="<s:property value='%{ayuda("insert.registro")}'/>">
                                                            <s:a  href="%{urlIns}" class="acciones ins">&nbsp;&nbsp;</s:a>
                                                        </li>
                                                    </ul>
                                                </td>
                                            </s:else>
                                        </s:if>
                                        <!-- resto de columnas-->
                                        <s:else>
                                            <!-- primera fila -->
                                            <s:if test="#status.first == true">
                                                <th><s:property /></th>
                                            </s:if>
                                            <!-- resto de filas -->
                                            <s:else>
                                                <td>
                                                    <s:property/>
                                                </td>
                                            </s:else>
                                        </s:else>
                                    </s:iterator>
                                </tr>
                                <s:if test="#status.first == true"></thead></s:if>
                            </s:iterator>
                    </table>
                </div>
                <!--div id="derecha">derecha</div>-->
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/resources/ordenacion.js"></script>
    </body>
</html>
