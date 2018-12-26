<%-- 
    Document   : inicial
    Created on : 11-dic-2018, 12:06:46
    Author     : jhuidobro
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My DataBase JC</title>
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1.min.js"></script>
        <style>
            table tr th {
                cursor: pointer;
                -webkit-user-select: none;
                -moz-user-select: none;
                -ms-user-select: none;
                user-select: none;
            }

            .sorting {
                background-color: #D4D4D4;
            }

            .asc:after {
                content: ' ↑';
            }

            .desc:after {
                content: " ↓";
            }      
        </style>
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
                <s:include value="/WEB-INF/jspf/filtro.jsp"/>
                <table class="tabla">
                    <s:iterator value="listInfo" var="record" status="status">
                        <s:if test="#status.first == true"><thead></s:if>
                                <tr>
                                <s:iterator value="#record" status="stat">
                                    <s:set name="propiedad"><s:property /></s:set>

                                    <s:if test="#status.first == true">
                                        <s:if test="!metodoLink">
                                            <th><s:property /></th>
                                        </s:if>
                                        <s:elseif test="#stat.count != 1">
                                            <th><s:property /></th>
                                        </s:elseif>
                                    </s:if>
                                    <s:else>
                                        <s:if test="metodoLink">
                                            <s:if test="#stat.count == 1">
                                                    <s:url action="%{linkAction}" namespace="%{linkNamespace}" var="urlTag"/>
                                                    <s:set var="url"><s:property value="urlTag"/>?<s:property/></s:set>
                                            </s:if>
                                            <s:elseif test="#stat.count == 2">
                                                <td>
                                                    <s:if test="#stat.count-1 == linkColumnNumber">
                                                        <s:a href="%{url}" id="ir" ><s:text name="%{propiedad}"/></s:a>
                                                    </s:if>
                                                    <s:else>
                                                        <s:text name="%{propiedad}"/>
                                                    </s:else>
                                                </td>
                                            </s:elseif>
                                            <s:else>
                                                <td>
                                                    <s:if test="#stat.count-1 == linkColumnNumber">
                                                        <s:a href="%{url}" id="ir" ><s:property/></s:a>
                                                    </s:if>
                                                    <s:else>
                                                        <s:property/>
                                                    </s:else>
                                                </td>
                                            </s:else>
                                        </s:if>
                                        <s:elseif test="#stat.count == 1">
                                            <td>
                                                <s:text name="%{propiedad}"/>
                                            </td>
                                        </s:elseif>
                                        <s:else>
                                            <td>
                                                <s:property/>
                                            </td>
                                        </s:else>
                                    </s:else>
                                </s:iterator>
                            </tr>
                            <s:if test="#status.first == true"></thead></s:if>
                        </s:iterator>
                </table>
            </div>
        </div>
        <script>
            $('th').click(function () {
                var table = $(this).parents('table').eq(0);
                var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()));
                this.asc = !this.asc;
                if (!this.asc) {
                    rows = rows.reverse();
                }
                for (var i = 0; i < rows.length; i++) {
                    table.append(rows[i]);
                }
                setIcon($(this), this.asc);
            });

// Para comparar los valores de la tabla entre sí
            function comparer(index) {
                return function (a, b) {
                    var valA = getCellValue(a, index),
                            valB = getCellValue(b, index);
                    return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.localeCompare(valB);
                };
            }

// Obtiene los valores de cada celda
            function getCellValue(row, index) {
                return $(row).children('td').eq(index).html();
            }

// Muestra gráficamente qué ordenamiento se está aplicando
            function setIcon(element, asc) {
                $("th").each(function (index) {
                    $(this).removeClass("sorting");
                    $(this).removeClass("asc");
                    $(this).removeClass("desc");
                });
                element.addClass("sorting");
                if (asc)
                    element.addClass("asc");
                else
                    element.addClass("desc");
            }
        </script>
    </body>
</html>
