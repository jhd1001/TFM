<%-- 
    Document   : newjsp
    Created on : 17-nov-2018, 14:06:30
    Author     : jhuidobro
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Properties"%>
<%@page import="alu.ubu.es.mydatabasejc.Conexion"%>
<%@ page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <title>Reporte de conexion.typeMap</title>
    </head>
    <body>
        <h2>Conexion a Oracle</h2>
        <table>
                     <%
                     Connection con = Conexion.getConnection();
                     Map<String, Class<?>> typeMap = con.getTypeMap();
                     for (Iterator iterator = typeMap.values().iterator(); iterator.hasNext();) {
                         Class object = (Class)iterator.next();
                     %>
            <tr>
                <td><%=object.getName()%></td>
            </tr>
            <%
                }
                if (con!=null) con.close();
            %>
        </table>
    </body>
</html>