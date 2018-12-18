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
            <s:include value="/WEB-INF/jspf/menu.jsp"/>
            <div id="contenido-unico">
                <s:actionerror/>
                <s:actionmessage/>
                <h1><s:i18n name="es.ubu.alu.mydatabasejc.package"><s:property value="%{getText(metodo)}"/></s:i18n></h1>
                <h3><s:i18n name="es.ubu.alu.mydatabasejc.help"><s:property value="%{getText(metodo)}"/></s:i18n></h3>
                <table class="tabla">
                    <s:iterator value="listInfo" var="record" status="status">
                        <tr>
                            <s:set var="cuenta" value="1"/>
                            <s:iterator value="#record">
                                <s:set name="propiedad"><s:property /></s:set>
                                
                                <s:if test="#status.first == true">
                                    <th><s:property /></th>
                                </s:if>
                                <s:else>
                                    <td>
                                        <s:if test="#cuenta == 1">
                                            <s:text name="%{propiedad}"/>
                                            <s:set var="cuenta" value="2"/>
                                        </s:if>
                                        <s:else>
                                            <s:property />
                                        </s:else>
                                    </td>
                                </s:else>
                            </s:iterator>
                        </tr>
                    </s:iterator>
                </table>
            </div>
        </div>
    </body>
</html>
