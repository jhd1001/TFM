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
                <s:actionerror/>
                <s:actionmessage/>
                <h1><s:property value="%{getText(metodo)}"/></h1>
                <h3><s:property value="metodo"/></h3>
                <div class="descripcion"><s:property value="%{ayuda(metodo)}"/></div>
                <s:include value="/WEB-INF/jspf/filtro.jsp"/>
                <div id="resultset">
                    <table class="tabla">
                        <s:iterator value="listInfo" var="record" status="status">
                            <s:if test="#status.first == true"><thead></s:if>
                            <tr>
                                <s:iterator value="#record" status="stat">
                                    <s:set name="propiedad"><s:property /></s:set>

                                    <s:if test="#status.first == true">
                                        <s:if test="!metodoLink && #stat.count != 1">
                                            <th><s:property /></th>
                                        </s:if>
                                        <s:elseif test="#stat.count != 1">
                                            <th><s:property /></th>
                                        </s:elseif>
                                        <s:else>
                                            <th class="ayuda"><s:property/></th>
                                        </s:else>
                                    </s:if>
                                    <s:else>
                                        <s:if test="metodoLink">
                                            <s:if test="#stat.count == 1">
                                                    <s:url action="%{linkAction}" namespace="%{linkNamespace}" var="urlTag"/>
                                                    <s:set var="url"><s:property value="urlTag"/>?<s:property/></s:set>
                                            </s:if>
                                            <s:elseif test="#stat.count == 2">
                                                <td title='<s:property value="%{ayuda(propiedad)}"/>'>
                                                    <s:if test="#stat.count-1 == linkColumnNumber">
                                                        <s:a href="%{url}" id="ir" ><s:text name="%{propiedad}"/></s:a>
                                                    </s:if>
                                                    <s:else>
                                                        <s:text name="%{propiedad}"/>
                                                    </s:else>
                                                </td>
                                            </s:elseif>
                                            <s:else>
                                                <td>
                                                    <s:if test="#stat.count-1 == linkColumnNumber">
                                                        <s:a href="%{url}" id="ir" ><s:property/></s:a>
                                                    </s:if>
                                                    <s:else>
                                                        <s:property/>
                                                    </s:else>
                                                </td>
                                            </s:else>
                                        </s:if>
                                        <s:elseif test="#stat.count == 1">
                                            <td title='<s:property value="%{ayuda(#propiedad)}"/>'>
                                                <s:text name="%{propiedad}"/>
                                            </td>
                                        </s:elseif>
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
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/resources/ordenacion.js"></script>
    </body>
</html>
