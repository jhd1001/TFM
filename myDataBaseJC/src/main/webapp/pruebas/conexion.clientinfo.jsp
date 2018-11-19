<%-- 
    Document   : newjsp
    Created on : 17-nov-2018, 14:06:30
    Author     : jhuidobro
--%>

<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Properties"%>
<%@page import="alu.ubu.es.mydatabasejc.Conexion"%>
<%@ page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <title>Reporte de conexion.clientInfo</title>
    </head>
    <body>
        <h2>Conexion a Oracle</h2>
        <table>
                     <%
                     Connection con = Conexion.getConnection();
                     Properties properties = con.getClientInfo();
                     for (Enumeration e = properties.keys(); e.hasMoreElements();) {
                         Object object = e.nextElement();
                     %>
            <tr>
                <td><%=object%></td>
                <td><%=properties.getProperty(object.toString())%></td>
            </tr>
            <%
                }
                if (con!=null) con.close();
            %>
        </table>
    </body>
</html>