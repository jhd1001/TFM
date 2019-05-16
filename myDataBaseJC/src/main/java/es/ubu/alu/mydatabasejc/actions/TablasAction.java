package es.ubu.alu.mydatabasejc.actions;

import com.opensymphony.xwork2.Preparable;
import es.ubu.alu.mydatabasejc.exceptions.SQLCommandException;
import es.ubu.alu.mydatabasejc.jdbc.ConnectionImpl;
import es.ubu.alu.mydatabasejc.jdbc.SQLCommand;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author jhuidobro
 */
public class TablasAction extends MenuAction implements Preparable, SessionAware {

    private ConnectionImpl connectionImpl;
    private Map<String, Object> sesion;
    private String TABLE_SCHEM;
    private String TABLE_NAME;
    private String TABLE_TYPE;
    private List<List> listInfo;
    private Set<String> arrayParametros;
    private String[] pkArgumentos;
    private String[] pkValores;
    private boolean metodoLink;
    private String[] formCampos;
    private String[] formValores;
    private Map<String, Integer> mapaEditables;
    private Map<String, Integer[]> mapaCompleto;

    public boolean isEditDisabled(String campo) {
        for (String campoWritable : mapaEditables.keySet()) {
            if (campoWritable.equalsIgnoreCase(campo)) {
                return false;
            }
        }
        return true;
    }

    public String insertarGuardar() {
        if (TABLE_NAME == null || "".equals(TABLE_NAME)) {
            sesion.put(ACTION_ERROR, "Faltan.esquema.o.nombre.de.tabla");
            return "tablas";
        }
        if (formCampos == null || formCampos.length == 0 || formValores == null || formValores.length == 0) {
            sesion.put(ACTION_ERROR, "Faltan.datos.para.hacer.la.operacion");
            return "consulta";
        }

        try {
            SQLCommand sqlComand = new SQLCommand(connectionImpl);
            if (sqlComand.executeUpdate(TABLE_SCHEM, TABLE_NAME, mapaCompleto, formCampos, formValores) == 0) {
                sesion.put(ACTION_MESSAGE, "No.se.ha.insertado.ningún.registro");
            } else {
                sesion.put(ACTION_MESSAGE, "El.registro.ha.sido.insertado");
            }
        } catch (SQLCommandException ex) {
            addActionError(ex.getLocalizedMessage());
            return insertar();
        }
        return "consulta";
    }

    public String insertar() {
        if (TABLE_NAME == null || "".equals(TABLE_NAME)) {
            sesion.put(ACTION_ERROR, "Faltan.esquema.o.nombre.de.tabla");
            return "tablas";
        }

        SQLCommand sqlCommand = new SQLCommand(connectionImpl);
        // define el array de los campos para presentarlos en la jsp
        arrayParametros = sqlCommand.getMap(mapaCompleto, SQLCommand.ISAUTOINCREMENT + SQLCommand.ISDEFINITELYWRITABLE + SQLCommand.ISWRITABLE).keySet();

        return SUCCESS;
    }

    public Object getPkValores(int i) {
        return pkValores == null ? null : (i >= pkValores.length ? null : pkValores[i]);
    }

    public String editarGuardar() {
        if (TABLE_NAME == null || "".equals(TABLE_NAME)) {
            sesion.put(ACTION_ERROR, "Faltan.esquema.o.nombre.de.tabla");
            return "tablas";
        }
        if (pkArgumentos == null || pkArgumentos.length == 0) {
            sesion.put(ACTION_ERROR, "Falta.primary.key");
            return "consulta";
        }
        if (formCampos == null || formCampos.length == 0 || formValores == null || formValores.length == 0) {
            sesion.put(ACTION_ERROR, "Faltan.datos.para.hacer.la.operacion");
            return "consulta";
        }

        try {
            SQLCommand sqlComand = new SQLCommand(connectionImpl);
            if (sqlComand.executeUpdate(TABLE_SCHEM, TABLE_NAME, mapaCompleto, SQLCommand.OPERACION_UPDATE, pkArgumentos, pkValores, formCampos, formValores) == 0) {
                sesion.put(ACTION_MESSAGE, "No.se.ha.actualizado.ningún.registro");
            } else {
                sesion.put(ACTION_MESSAGE, "El.registro.ha.sido.actualizado");
            }
        } catch (SQLCommandException ex) {
            addActionError(ex.getLocalizedMessage());
            return editar();
        }
        return "consulta";
    }

    public String editar() {
        if (TABLE_NAME == null || "".equals(TABLE_NAME)) {
            sesion.put(ACTION_ERROR, "Faltan.esquema.o.nombre.de.tabla");
            return "tablas";
        }
        if (pkArgumentos == null || pkArgumentos.length == 0) {
            sesion.put(ACTION_ERROR, "Falta.primary.key");
            return "consulta";
        }

        // obtiene el resultset del registro a editar
        ResultSet rs = null;
        try {
            SQLCommand sqlComand = new SQLCommand(connectionImpl);
            // y se resuelve el resultset
            rs = sqlComand.executeQuery(TABLE_SCHEM, TABLE_NAME, mapaCompleto, pkArgumentos, pkValores, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // se transforma el resultset en una lista para su visualización
            listInfo = getListInfoReverse(rs);
            // se consigue el mapa de campos editables.
            mapaEditables = sqlComand.getMap(mapaCompleto, SQLCommand.ISDEFINITELYWRITABLE + SQLCommand.ISWRITABLE);

        } catch (SQLCommandException ex) {
            addActionError(ex.getMessage());
            return ERROR;
        } finally {
            cierraRS(rs, null);
        }
        return SUCCESS;
    }

    public String borrar() {
        if (TABLE_NAME == null || "".equals(TABLE_NAME)) {
            sesion.put(ACTION_ERROR, "Faltan.esquema.o.nombre.de.tabla");
            return "tablas";
        }
        if (pkArgumentos == null || pkArgumentos.length == 0) {
            sesion.put(ACTION_ERROR, "Falta.primary.key");
            return "consulta";
        }

        try {
            SQLCommand sqlComand = new SQLCommand(connectionImpl);
            if (sqlComand.executeUpdate(TABLE_SCHEM, TABLE_NAME, mapaCompleto, SQLCommand.OPERACION_DELETE, pkArgumentos, pkValores, null, null) == 0) {
                sesion.put(ACTION_ERROR, "No.se.ha.eliminado.ningún.registro");
            } else {
                sesion.put(ACTION_MESSAGE, "El.registro.ha.sido.eliminado");
            }
        } catch (SQLCommandException ex) {
            sesion.put(ACTION_ERROR, ex.getLocalizedMessage());
        } finally {
            return "consulta";
        }
    }

    public Object getParameter(int i) {
        String[] a = {};
        return sesion.get(TABLE_SCHEM + "." + TABLE_NAME + "." + arrayParametros.toArray(a)[i]);
    }

    /**
     * Obtenidos los tipos de los parámetros, se convierten los valores
     * recibidos a los tipos correspondientes y se ponen en la sesión para
     * sucesivas llamadas a este método Convierte cada valor de filtroValores al
     * tipo necesario según se indica en el mapa obtenido de la sesion y grabado
     * previamente, en la primera ejecución y lo añade a la sesión del usuario
     * con el nombre correspondiente en filtroArgumentos
     *
     * @param tabla nombre del esquema y la tabla. Se buscará en la sesión un
     * objeto map con este nombre. Contendrá un mapa de los campos del resultset
     * y su tipo
     * @return mapa del resultset de la tabla con
     * @throws SQLException
     */
    private Map<String, Integer> setParametrosSesion(String tabla) { 
        // obtiene el mapa de campos de búsqueda
        SQLCommand sqlCommand = new SQLCommand(connectionImpl);
        Map<String, Integer> mapa = sqlCommand.getMap(mapaCompleto, SQLCommand.ISSEARCHABLE);

        // En la segunda fase de la función, se analizan los parámetros de la request
        // para añadir a la sesión los datos de filtrado enviados por el usuario
        if (filtroArgumentos == null || filtroValores == null
                || filtroArgumentos.length == 0 || filtroValores.length == 0) {
            return mapa;
        }
        // obtiene el conjunto de parámetros
        Set<String> set = mapa.keySet();
        int i = 0;
        // para cada columna del resultset
        for (String columna : set) {
            try {
                String atributo = tabla + "." + filtroArgumentos[i]; //TABLE_SCHEM + "." + TABLE_NAME + "." + filtroArgumentos[i];
                Object valor = null;
                // si el valor recibido no está en blanco
                if (!"".equals(filtroValores[i])) 
                    // se asigna a la variable valor
                    valor = SQLCommand.getValor(mapa.get(columna), filtroValores[i]);
                // y se añade a la sesión del usuario con el nombre de esquema,
                // tabla y columna
                sesion.put(atributo, valor);
            } catch (Exception ex) {
                addActionError(filtroArgumentos[i] + ": " + ex.toString());
            } finally {
                i++;
            }
        }
        // se devuelve el mapa del resultset para su uso posterior.
        return mapa;
    }

    /**
     * Consulta la tabla recibida en TABLE_NAME y en TABLE_SCHEM y la presenta
     * en jsp en forma de lista
     *
     * @return
     */
    public String consulta() {
        // Recoge los mensajes de error previos y los establece en esta acción
        if (sesion.get(ACTION_ERROR) != null) {
            addActionError(getText((String) sesion.get(ACTION_ERROR)));
            sesion.remove(ACTION_ERROR);
        }

        // Recoge los mensajes informativos previos y los establece en esta acción
        if (sesion.get(ACTION_MESSAGE) != null) {
            addActionMessage(getText((String) sesion.get(ACTION_MESSAGE)));
            sesion.remove(ACTION_MESSAGE);
        }

        // comprueba que los parámetros se reciban correctamente
        if (TABLE_NAME == null || "".equals(TABLE_NAME)) {
            sesion.put(ACTION_ERROR, "Faltan.esquema.o.nombre.de.tabla");
            return "back";
        }
        metodoLink = false; // Será true cuando exista una primary key en la tabla consultada

        // establece y recoge los parámetros de búsqueda en sesión del usuario
        Map<String, Integer> mapa = setParametrosSesion(
                    TABLE_SCHEM == null ? TABLE_NAME
                            : (TABLE_SCHEM.equals("") ? TABLE_NAME
                                    : (TABLE_SCHEM.equals("null") ? TABLE_NAME
                                            : TABLE_SCHEM + "." + TABLE_NAME)));

        // define el array de posibles parámetros para presentarlos en la jsp
        arrayParametros = mapa.keySet();

        //PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        try {
            SQLCommand sqlCommand = new SQLCommand(connectionImpl);
            rs = sqlCommand.executeQuery(TABLE_SCHEM, TABLE_NAME, arrayParametros, sesion);
            // se obtiene una lista con los campos primary key
            List<String> pkList = new ArrayList<>();
            String[] linkParametros = {};
            rs2 = connectionImpl.getConnection().getMetaData().getPrimaryKeys(null, TABLE_SCHEM == null ? "" : TABLE_SCHEM, TABLE_NAME);
            while (rs2.next()) {
                pkList.add(rs2.getString("COLUMN_NAME"));
            }
            if (pkList.size() != 0) {
                metodoLink = true;
            }
            // se transforma el resultset en una lista para su visualización
            listInfo = getListInfo(rs, metodoLink, pkList.toArray(linkParametros), "pkArgumentos", "pkValores");
        } catch (SQLException | SQLCommandException ex) { //ResultSetException | 
            addActionError(ex.getMessage());
            return ERROR;
        } finally {
            cierraRS(rs, null);
            cierraRS(rs2, null);
        }
        return SUCCESS;
    }

    /**
     * Se valida la conexión obtenida. Si error, se pasará a login.jsp
     */
    @Override
    public void validate() {
        super.validarLogin(connectionImpl);
        // si no hay errores, se carga el mapa de la tabla consultada
        if (!hasActionErrors()) {
        // busca el mapa del resultset en la sesión del usuario
            mapaCompleto = (Map<String, Integer[]>)sesion.get(
                    TABLE_SCHEM==null ? TABLE_NAME :
                        (TABLE_SCHEM.equals("") ? TABLE_NAME : 
                                TABLE_SCHEM + "." + TABLE_NAME));
            // si el mapa aún no se ha creado
            if (mapaCompleto == null) {
                // se calcula y se guarda en la sesión
                SQLCommand sqlCommand = new SQLCommand(connectionImpl);
                mapaCompleto = sqlCommand.getMap(TABLE_SCHEM, TABLE_NAME);
                sesion.put(
                        TABLE_SCHEM==null ? TABLE_NAME :
                            (TABLE_SCHEM.equals("") ? TABLE_NAME : 
                                    TABLE_SCHEM + "." + TABLE_NAME), 
                        mapaCompleto);
            }
        }

    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.sesion = map;
    }

    /**
     * Se obtiene la conexión de la sesión del usuario
     *
     * @throws Exception
     */
    @Override
    public void prepare() throws Exception {
        connectionImpl = (ConnectionImpl) sesion.get(CONEXION);
    }

    public String getTABLE_SCHEM() {return TABLE_SCHEM;}

    public void setTABLE_SCHEM(String TABLE_SCHEM) {this.TABLE_SCHEM = TABLE_SCHEM;}

    public String getTABLE_NAME() {return TABLE_NAME;}

    public void setTABLE_NAME(String TABLE_NAME) {this.TABLE_NAME = TABLE_NAME;}

    public String getTABLE_TYPE() {return TABLE_TYPE;}

    public void setTABLE_TYPE(String TABLE_TYPE) {this.TABLE_TYPE = TABLE_TYPE;}

    public List<List> getListInfo() {return listInfo;}

    public void setListInfo(List<List> listInfo) {this.listInfo = listInfo;}

    public Set<String> getArrayParametros() {return arrayParametros;}

    public void setArrayParametros(Set<String> arrayParametros) {this.arrayParametros = arrayParametros;}

    public ConnectionImpl getConnectionImpl() {return connectionImpl;}

    public void setConnectionImpl(ConnectionImpl connectionImpl) {this.connectionImpl = connectionImpl;}

    public String[] getPkArgumentos() {return pkArgumentos;}

    public void setPkArgumentos(String[] pkArgumentos) {this.pkArgumentos = pkArgumentos;}

    public String[] getPkValores() {return pkValores;}

    public void setPkValores(String[] pkValores) {this.pkValores = pkValores;}

    public boolean isMetodoLink() {return metodoLink;}

    public void setMetodoLink(boolean metodoLink) {this.metodoLink = metodoLink;}

    public String[] getFormCampos() {return formCampos;}

    public void setFormCampos(String[] formCampos) {this.formCampos = formCampos;}

    public String[] getFormValores() {return formValores;}

    public void setFormValores(String[] formValores) {this.formValores = formValores;}

}
