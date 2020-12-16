package database;

import Modelo.Robot;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RobotDAO implements Map<String, Robot> {
    private static RobotDAO st = null;

    private RobotDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()){
                String sql = "CREATE TABLE IF NOT EXISTS Robot ("+
                                "codRobot varchar(6) NOT NULL,"+
                                "localizacao varchar(3) NOT NULL,"+
                                "disponibilidade TINYINT NOT NULL,"+
                                "PRIMARY KEY (codRobot))";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static RobotDAO getInstance(){
        if(RobotDAO.st == null) RobotDAO.st = new RobotDAO();
        return RobotDAO.st;
    }

    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Robot")) {
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    public boolean isEmpty(){
        return (this.size() == 0);
    }

    public boolean containsKey(Object key){
        boolean r = false;
        try(Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT codRobot FROM Robot WHERE codRobot='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        };
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Robot r = (Robot) value;
        return this.containsKey(r.getCod());
    }

    @Override
    public Robot get(Object key) {
        return null;
    }

    @Override
    public Robot put(String key, Robot value) {
        return null;
    }

    @Override
    public Robot remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Robot> robots) {
        for(Robot r : robots.values()) this.put(r.getCod(),r);
    }

    @Override
    public void clear() {
        try(Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()){
                    stm.execute("UPDATE Robot SET Robot=NULL");
                    stm.execute("TRUNCATE Robot");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Robot> values() {
        Collection<Robot> res = new HashSet<>();
        try(Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT codRobot FROM Robot")){
            while(rs.next()){
                String idt = rs.getString("codRobot");
                Robot r = this.get(idt);
                res.add(r);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public Set<Entry<String, Robot>> entrySet() {
        return null;
    }

}
