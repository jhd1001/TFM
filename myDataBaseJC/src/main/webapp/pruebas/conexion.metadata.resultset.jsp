<%-- 
    Document   : newjsp1
    Created on : 17-nov-2018, 17:10:52
    Author     : jhuidobro
--%>
<%@page import="alu.ubu.es.mydatabasejc.MetaDataResultSetImpl"%>
<%@page import="alu.ubu.es.mydatabasejc.MetaDataInfoImpl"%>
<%@page import="java.lang.annotation.Annotation"%>
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
        <title>conexion metadata</title>
    </head>
    <body>
        <h1>Métodos de conexion.metadata</h1>
        <table>
            <%
                MetaDataResultSetImpl metaData = new MetaDataResultSetImpl();
                //Conexion conexion = new Conexion(con);
                Method[] metodos = MetaDataResultSetImpl.class.getMethods();
                for (int i = 0; i < metodos.length; i++) {
                    if (metodos[i].getDeclaringClass().equals(metaData.getClass())) {
                        Type[] tipos = metodos[i].getGenericParameterTypes();
                        Parameter[] parameters = metodos[i].getParameters();
            %>
            <tr>
                <td style="border-style: solid"><%=metodos[i].getName()%></td>
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
                    /* Permite invocar a los métodos del metadata
                    try {
                        o = metodos[i].invoke(metaData, new Object[]{});
                    } catch (Exception e) {
                        o = e.getLocalizedMessage();
                    }
*/
                %>
                <td style="border-style: solid"><%=o%></td>
            </tr>
            <%
                    }
                }
                metaData.getConnection().close();
            %>
        </table>

    </body>
</html>
