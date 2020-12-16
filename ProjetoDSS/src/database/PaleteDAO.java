package database;

import Modelo.Palete;


import java.sql.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class PaleteDAO implements Map<String, Palete> {
    private static PaleteDAO st=null;

    private PaleteDAO(){
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Palete (" +
                    "  QRCode varchar(6) NOT NULL," +
                    "  Prateleira_idPrateleira INT NULL," +
                    "  Robot_codRobot varchar(6) NULL," +
                    "  PRIMARY KEY (QRCode)," +
                    "  INDEX fk_Palete_Prateleira_idx (Prateleira_idPrateleira ASC) VISIBLE," +
                    "  INDEX fk_Palete_Robot1_idx (Robot_codRobot ASC) VISIBLE," +
                    "  CONSTRAINT fk_Palete_Prateleira" +
                    "    FOREIGN KEY (Prateleira_idPrateleira)" +
                    "    REFERENCES Prateleira (codPrateleira)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT fk_Palete_Robot1" +
                    "    FOREIGN KEY (Robot_codRobot)" +
                    "    REFERENCES Robot (codRobot)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION)";
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
    public Palete get(Object key) {
        return null;
    }

    @Override
    public Palete put(String key, Palete value) {
        return null;
    }

    @Override
    public Palete remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Palete> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Palete> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Palete>> entrySet() {
        return null;
    }
}
