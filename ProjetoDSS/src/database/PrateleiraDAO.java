package database;

import Modelo.GPS;
import Modelo.Prateleira;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PrateleiraDAO implements Map<String, Prateleira> {
    private static PrateleiraDAO st = null;

    private PrateleiraDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Prateleira (" +
                    "codPrateleira varchar(5) NOT NULL," +
                    "localizacao_x INT NOT NULL," +
                    "localizacao_y INT NOT NULL," +
                    "disponibilidade tinyint NOT NULL," +
                    "codPalete varchar(6) NULL," +
                    "PRIMARY KEY (codPrateleira))";
            stm.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static PrateleiraDAO getInstance() {
        if (PrateleiraDAO.st == null) PrateleiraDAO.st = new PrateleiraDAO();
        return PrateleiraDAO.st;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Prateleira")) {
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
             ResultSet rs = stm.executeQuery("SELECT codPrateleira FROM Prateleira WHERE codPrateleira='" + key.toString() + "'")) {
            r = rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Prateleira p = (Prateleira) value;
        return this.containsKey(p.getCodPrateleira());
    }

    @Override
    public Prateleira get(Object key) {
        Prateleira p = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Prateleira WHERE codPrateleira='" + key.toString() + "'");) {
            if (rs.next()) {
                //Verificar como fazer por causa do construtor da prateleira levar um boolean disponibilidade e um GPS localização
                p = new Prateleira(rs.getString("codPrateleira"), rs.getInt("disponibilidade") == 1,
                        rs.getString("codPalete"), new GPS(rs.getInt("localizacao_x"), rs.getInt("localizacao_y")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return p;
    }

    @Override
    public Prateleira put(String key, Prateleira p1) {
        Prateleira p2 = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            int d;
            if (p1.isAvailable()) d = 1;
            else d = 0;
            if (p1.getCodPalete() != null) {
                stm.executeUpdate("INSERT INTO Prateleira " +
                        "VALUES ('" + p1.getCodPrateleira() + "', '" +
                        p1.getLocalizacao().getX() + "', '" +
                        p1.getLocalizacao().getY() + "', '" +
                        d + "', " +
                        "'" + p1.getCodPalete() + "')" +
                        "ON DUPLICATE KEY UPDATE disponibilidade=VALUES(disponibilidade),codPalete=VALUES(codPalete)");
            } else {
                stm.executeUpdate("INSERT INTO Prateleira " +
                        "VALUES ('" + p1.getCodPrateleira() + "', '" +
                        p1.getLocalizacao().getX() + "', '" +
                        p1.getLocalizacao().getY() + "', '" +
                        d + "', NULL )" +
                        "ON DUPLICATE KEY UPDATE disponibilidade=VALUES(disponibilidade),codPalete=VALUES(codPalete)");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return p2;
    }

    @Override
    public Prateleira remove(Object key) {
        Prateleira p = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();) {
            stm.executeUpdate("DELETE FROM Prateleira WHERE codPrateleira = '" + key.toString() + "' ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return p;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Prateleira> prateleiras) {
        for (Prateleira p : prateleiras.values()) this.put(p.getCodPrateleira(), p);
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();) {
            stm.execute("TRUNCATE Prateleira");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Set<String> keySet() {
        Set<String> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT codPrateleira FROM Prateleira");) {
            while (rs.next()) {
                String idt = rs.getString("codPrateleira");
                res.add(idt);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public Collection<Prateleira> values() {
        Collection<Prateleira> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT codPrateleira FROM Prateleira");) {
            while (rs.next()) {
                String idt = rs.getString("codPrateleira");
                Prateleira p = this.get(idt);
                res.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public Set<Entry<String, Prateleira>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Prateleira>> entrySet() not implemented!");
    }

    public static void povoa() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "INSERT INTO Prateleira (codPrateleira, " +
                    "localizacao_x, " +
                    "localizacao_y," +
                    "disponibilidade," +
                    "codPalete)" +
                    "VALUES ('0-0',0,0,1,NULL)," +
                    "('P01',5,0,1,NULL)," +
                    "('P02',10,0,1,NULL)," +
                    "('P06',5,5,1,NULL)," +
                    "('P03',15,0,1,NULL)," +
                    "('P07',10,5,1,NULL)," +
                    "('P04',20,0,1,NULL)," +
                    "('P08',15,5,1,NULL)," +
                    "('P05',25,0,1,NULL)," +
                    "('P09',20,5,1,NULL)," +
                    "('P10',25,5,1,NULL)," +
                    "('e-e',28,3,1,NULL)" +
                    "ON DUPLICATE KEY UPDATE codPrateleira=VALUES(codPrateleira)";
            stm.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
