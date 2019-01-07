<%-- 
    Document   : index
    Created on : 18-nov-2018, 12:06:46
    Author     : jhuidobro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/general.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/ayuda.css">
        <title>My DataBase JC</title>
    </head>
    <body>
        <div id="page">
            <s:i18n name="es.ubu.alu.mydatabasejc.help">
            <s:text name="allProceduresAreCallable2" var="conectar"/>
            <s:text name="URL" var="vURL"/>
            </s:i18n>
            <s:include value="/WEB-INF/jspf/cabecera.jsp"/>
            <div id="contenido-unico">
                <s:actionerror/>
                <s:actionmessage/>
                <div id="filtro-div">
                    <div class="bordeado50">
                        <s:form action="login" method="POST" namespace="/" id="filtro">
                            <s:textfield name="url" key="URL" title='%{ayuda("URL")}' class="ayuda"/>
                            <s:textfield name="usuario" key="Usuario"/>
                            <s:password name="password" key="Contrasena"/>
                            <s:submit key="Conectar" name="conectar" title='%{ayuda("Conectar")}' class="ayuda"/>
                        </s:form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
