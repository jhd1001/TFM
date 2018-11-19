<%-- 
    Document   : newjsp1
    Created on : 17-nov-2018, 17:10:52
    Author     : jhuidobro
--%>
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
        <title>conexion resultset</title>
    </head>
    <body>
        <h1>Métodos de conexion.metadata.getTables</h1>
        <table>
            <thead>
                <tr>
                    <%
                        Connection con = Conexion.getConnection();
                        
                        DatabaseMetaData metaData = con.getMetaData();
                        
                        //ResultSet rs = metaData.getTypeInfo();
                        //ResultSet rs = metaData.getSchemas();
                        //ResultSet rs = metaData.getCatalogs();
                        //ResultSet rs = metaData.getTableTypes();
                        //ResultSet rs = metaData.getClientInfoProperties();
                        ResultSet rs = metaData.getTables(null, null, null, null);

                        //Conexion conexion = new Conexion(con);
                        //Method[] metodos = DatabaseMetaData.class.getMethods();
                        ResultSetMetaData rsMetaData = rs.getMetaData();
                        int columnCount = rsMetaData.getColumnCount();
                        for (int j = 0; j < columnCount; j++) {
                    %>
                    <td><%=rsMetaData.getColumnName(j + 1)%></td>
                    <%
                        }
                    %>
                </tr>
            </thead>
            <tbody>
                <%
                    while (rs.next()) {
                %>                        
                <tr>
                    <%
                        for (int i = 0; i < columnCount; i++) {
                    %>
                    <td><%=rs.getObject(i + 1)%></td>
                    <%
                        }
                    %>
                </tr>
                <%
                    }
                %>
            </tbody>
            <%
                con.close();
            %>
        </table>

    </body>
</html>
