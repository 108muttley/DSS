package database;

import Modelo.Prateleira;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class PrateleiraDAO implements Map<String, Prateleira> {
    private static PrateleiraDAO st=null;

    private PrateleiraDAO(){
        try(Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS Prateleira (" +
                            "codPrateleira varchar(5) NOT NULL," +
                            "localizacao varchar(3) NOT NULL,"+
                            "disponibilidade tinyint NOT NULL,"+
                            "PRIMARY KEY (codPrateleira))";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Prateleira get(Object key) {
        return null;
    }

    @Override
    public Prateleira put(String key, Prateleira value) {
        return null;
    }

    @Override
    public Prateleira remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Prateleira> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Prateleira> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Prateleira>> entrySet() {
        return null;
    }
}
