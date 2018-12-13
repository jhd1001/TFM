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
    </head>
    <body>
        <div id="page">
            <div id="cabecera">
                <table>
                    <tr>
                        <td><s:property value="connectionImpl.url"/></td>
                        <td><s:property value="connectionImpl.driver"/></td>
                    </tr>
                    <tr>
                        <td><s:property value="connectionImpl.usuario"/></td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
            </div>
            <div id="menu-lateral">
                <h1>Menú</h1>
                Conexión: <s:property value="connectionImpl.connection"/>
                <ol>
                    <s:iterator value="listMenu">
                        <li>
                            <s:url action="resultset" var="urlTag">
                                <s:param name="metodo"><s:property value="propiedad"/></s:param>
                                <s:param name="parametros"><s:property value="valor"/></s:param>
                            </s:url>
                            <s:a href="%{urlTag}"><s:property value="%{getText(propiedad)}"/></s:a>
                            </li>
                    </s:iterator>
                </ol>
            </div>
            <div id="contenido-unico">
                <s:actionerror/>
                <s:actionmessage/>
                <h1>INICIAL</h1>
                <table class="tabla">
                    <s:iterator value="listResultSet" var="record" status="status">
                        <tr>
                            <s:iterator value="#record">
                                <s:if test="#status.first == true">
                                    <th><s:property /></th>
                                </s:if>
                                <s:else>
                                    <td><s:property /></td>
                                </s:else>
                            </s:iterator>
                        </s:iterator>
                </table>
            </div>
        </div>
    </body>
</html>
