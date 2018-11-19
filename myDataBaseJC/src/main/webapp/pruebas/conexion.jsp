<%-- 
    Document   : newjsp1
    Created on : 17-nov-2018, 17:10:52
    Author     : jhuidobro
--%>
<%@page import="alu.ubu.es.mydatabasejc.ConexionImpl"%>
<%@page import="alu.ubu.es.mydatabasejc.ExcluirTipos"%>
<%@page import="alu.ubu.es.mydatabasejc.Conexion"%>
<%@page import="java.util.Locale"%>
<%@page import="java.lang.reflect.Method"%>
<%@page import="java.lang.reflect.Parameter"%>
<%@page import="java.lang.reflect.Type"%>
<%@ page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Métodos de conexion</h1>
        <table>
            <%
                ConexionImpl conx = new ConexionImpl();
                //Connection con = Conexion.getConnection();
                Method[] metodos = ConexionImpl.class.getMethods();
                for (int i = 0; i < metodos.length; i++) {
                    if (metodos[i].getDeclaringClass().equals(conx.getClass())) {// metodos[i].getReturnType() != void.class && !ExcluirTipos.isExcluido(metodos[i].getReturnType().toString())) {
                        Type[] tipos = metodos[i].getGenericParameterTypes();
                        Parameter[] parameters = metodos[i].getParameters();
            %>
            <tr>
                <td style="border-style: solid"><%=metodos[i].getName()%></td>
                <td style="border-style: solid"><%=metodos[i].getDeclaringClass().getName()%></td>
                <td style="border-style: solid"><%=metodos[i].getReturnType()%></td>
                <td style="border-style: solid"><%for (int j = 0; j < tipos.length; j++) {%>
                    <%=tipos[j].getTypeName()%><br/><%}%>
                </td>
                <td style="border-style: solid">
                    <%
                        for (Parameter param : parameters) {
                    %><%=param.getName()%><br/><%
                                    }
                    %>
                </td>
                <%
                    Object o = null;
                    try {
                        o = metodos[i].invoke(conx, new Object[]{});
                    } catch (Exception e) {
                        o = e.getLocalizedMessage();
                    }
                %>
                <td style="border-style: solid"><%=o%></td>
                <%
                        }
                    }
                    conx.close();
                %>
            </tr>
        </table>

    </body>
</html>
