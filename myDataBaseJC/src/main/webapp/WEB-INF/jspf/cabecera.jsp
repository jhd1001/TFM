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
        <div id="cabecera">
            <table>
                <tr>
                    <td>
                        <a href="https://www.ubu.es/" title="Inicio" rel="home" class="header__logo" id="logo" target="blank">
                            <img src="${pageContext.request.contextPath}/resources/images/logoubu_0_0.png" alt="Inicio" class="header__logo-image">
                        </a>
                    </td>
                    <td class="right">
                        <a href="https://www.ubu.es/master-universitario-online-en-ingenieria-informatica" class="title-grandson" target="blank">
                            <s:property value="@es.ubu.alu.mydatabasejc.ValoresPorDefecto@estudio"/>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div class="centrado titulo">
                            <s:property value="@es.ubu.alu.mydatabasejc.ValoresPorDefecto@titulo"/><br><br>
                            <s:property value="@es.ubu.alu.mydatabasejc.ValoresPorDefecto@subtitulo"/>
                        </div>
                        <div class="centrado">
                            <s:property value="@es.ubu.alu.mydatabasejc.ValoresPorDefecto@descripcionTrabajo"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><s:property value="connectionImpl.url"/></td>
                    <td class="right"><s:property value="@es.ubu.alu.mydatabasejc.ValoresPorDefecto@autor"/></td>
                </tr>
                <tr>
                    <td><s:property value="connectionImpl.driver"/></td>
                    <td class="right"><s:property value="@es.ubu.alu.mydatabasejc.ValoresPorDefecto@email"/></td>
                </tr>
            </table>
        </div>
    </body>
</html>
