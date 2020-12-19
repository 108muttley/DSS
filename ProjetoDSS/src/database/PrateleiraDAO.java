package database;

import Modelo.Palete;
import Modelo.Prateleira;

import java.sql.*;
import java.util.*;

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
                stm.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public PrateleiraDAO getInstance(){
        if(PrateleiraDAO.st==null) PrateleiraDAO.st = new PrateleiraDAO();
        return PrateleiraDAO.st;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Prateleira") ) {
            if (rs.next()){
                i = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return (this.size()==0);
    }

    @Override
    public boolean containsKey(Object key) {
        boolean r = false;
        try(Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT codPrateleira FROM Prateleira WHERE codPrateleira='"+key.toString()+"'")) {
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
            ResultSet rs = stm.executeQuery("SELECT * FROM Prateleira WHERE codPrateleira='"+key.toString()+"'");) {
                if(rs.next()){
                    //Verificar como fazer por causa do construtor da prateleira levar um boolean disponibilidade e um GPS localização

                    //p = new Prateleira(rs.getString("codPrateleira"),rs.getInt("disponibilidade")==1,new Palete(),rs.getString("localizacao"));
                }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return p;
    }

    @Override
    // Não está feita por causa dos construtores ainda, temos que avaliar melhor toda a stiuação das localizações e assim
    public Prateleira put(String key, Prateleira value) {
        return null;
    }

    @Override
    public Prateleira remove(Object key) {
        Prateleira p = this.get(key);
        try(Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement();) {
                stm.executeUpdate("DELETE FROM Prateleira WHERE codPrateleira = '"+key.toString()+"' ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return p;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Prateleira> prateleiras) {
        for(Prateleira p : prateleiras.values()) this.put(p.getCodPrateleira(),p);
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
                while(rs.next()){
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
        try(Connection conn = DriverManager.getConnection(DAOconfig.URL,DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT codPrateleira FROM Prateleira");) {
                while(rs.next()){
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
        return null;
    }
}
