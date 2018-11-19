<%-- 
    Document   : newjsp
    Created on : 17-nov-2018, 14:06:30
    Author     : jhuidobro
--%>

<%@page import="alu.ubu.es.mydatabasejc.Conexion"%>
<%@ page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <title>Reporte de CATALog</title>
    </head>
    <body>
        <h2>Conexion a Oracle</h2>
        <table>
                     <%
                     Connection con = Conexion.getConnection();
                     Statement sen;
                     ResultSet res;
                     sen=con.createStatement();
                     res=sen.executeQuery(" Select * from catalog");

                     while(res.next()){
                     %>
            <tr>
                <td><%=res.getString(1)%></td>
                <td><%=res.getString(2)%></td>
                <td><%=res.getString(3)%></td>

            </tr>
            <%
                }
                if (con!=null) con.close();
            %>
        </table>
    </body>
</html>