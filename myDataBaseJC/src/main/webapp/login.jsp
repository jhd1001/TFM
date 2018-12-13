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
        <title>My DataBase JC</title>
    </head>
    <body>
        <div id="page">
            <div id="cabecera">

            </div>
            <div id="contenido-unico">
                <s:actionerror/>
                <s:actionmessage/>
                <s:form action="login" method="POST" namespace="/">
                    <s:textfield name="url" key="URL"/>
                    <s:textfield name="usuario" key="Usuario"/>
                    <s:password name="password" key="Contrasena"/>
                    <s:submit key="Conectar"/>
                </s:form>
            </div>
        </div>
    </body>
</html>
