package database;

import Modelo.Palete;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PaleteDAO implements Map<String, Palete> {
    private static PaleteDAO st = null;

    private PaleteDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `projetoDSS`.`Palete` (\n" +
                    "        `QRCode` VARCHAR(6) NOT NULL,\n" +
                    "        `Material` VARCHAR(10) NOT NULL,\n" +
                    "        `Prateleira_codPrateleira` VARCHAR(6) NULL,\n" +
                    "        `Robot_codRobot` VARCHAR(6) NULL,\n" +
                    "        PRIMARY KEY (QRCode),\n" +
                    "        FOREIGN KEY (Prateleira_codPrateleira) REFERENCES Prateleira(codPrateleira),\n" +
                    "        FOREIGN KEY (Robot_codRobot) REFERENCES Robot(codRobot))";
            stm.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static PaleteDAO getInstance() {
        if (PaleteDAO.st == null) PaleteDAO.st = new PaleteDAO();
        return PaleteDAO.st;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Palete")) {
            if (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return (this.size() == 0);
    }

    @Override
    public boolean containsKey(Object key) {
        boolean r = false;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT QRCode FROM Palete WHERE QRCode = '" + key.toString() + "' ")) {
            r = rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Palete p = (Palete) value;
        return this.containsKey(p.getCodPalete());
    }

    @Override
    public Palete get(Object key) {
        Palete p = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Palete WHERE QRCode = '" + key.toString() + "' ")) {
            if (rs.next()) {
                if (rs.getString("Prateleira_codPrateleira") == null)
                    p = new Palete(rs.getString("QRCode"), rs.getString("Robot_codRobot"), rs.getString("Material"));
                else
                    p = new Palete(rs.getString("QRCode"), rs.getString("Prateleira_codPrateleira"), rs.getString("Material"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return p;
    }

    @Override
    public Palete put(String key, Palete value) {
        Palete res = null;

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            if (value.getLoc().startsWith("R")) {
                stm.executeUpdate("INSERT INTO Palete VALUES ('" + value.getCodPalete() + "', '" + value.getM() + "', NULL , '" + value.getLoc() + "')" +
                        "ON DUPLICATE KEY UPDATE Prateleira_codPrateleira=VALUES(Prateleira_codPrateleira), Robot_codRobot=VALUES(Robot_codRobot)");
            } else {
                stm.executeUpdate("INSERT INTO Palete VALUES ('" + value.getCodPalete() + "', '" + value.getM() + "', '" + value.getLoc() + "' , NULL)" +
                        "ON DUPLICATE KEY UPDATE Prateleira_codPrateleira=VALUES(Prateleira_codPrateleira), Robot_codRobot=VALUES(Robot_codRobot)");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return res;
    }

    @Override
    public Palete remove(Object key) {
        Palete p = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM Palete WHERE QRCode = '" + key.toString() + "' ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return p;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Palete> paletes) {
        for (Palete p : paletes.values())
            this.put(p.getCodPalete(), p);
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.execute("TRUNCATE Palete");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Set<String> keySet() {
        Set<String> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT QRCode FROM Palete")) {
            while (rs.next()) {
                String idt = rs.getString("QRCode");
                res.add(idt);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public Collection<Palete> values() {
        Collection<Palete> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT QRCode FROM Palete")) {
            while (rs.next()) {
                String idt = rs.getString("QRCode");
                Palete p = this.get(idt);
                res.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public Set<Entry<String, Palete>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Palete>> entrySet() not implemented!");
    }
}
