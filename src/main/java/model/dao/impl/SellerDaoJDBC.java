package model.dao.impl;

import db.DB;
import db.DBException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.*;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn; //singleton

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "insert into seller\n" +
                           "(Name, Email, BirthDate, BaseSalary, DepartmentId)" +
                           "values (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            int rowsAffected = st.executeUpdate();
            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DBException("Unexpected error! Object not inserted!!!");
            }
//            rs = st.executeQuery();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "update seller\n" +
                            "set Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?\n" +
                            "where Id = ?"
            );
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());
            st.executeUpdate();
//            rs = st.executeQuery();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("delete from seller where Id = ?");
           st.setInt(1, id);
           st.executeUpdate();
        }catch(SQLException e) {
            throw new DBException(e.getMessage());
        }finally{
            DB.closeStatement(st);
        }

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "select seller.*, department.Name as DepName " +
                            "from seller inner join department " +
                            "on seller.DepartmentId = department.Id " +
                            "Where seller.Id = ?"
            );
            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()){
                Department dep = instantiateDepartment(rs);
                Seller obj = instantiateSeller(rs, dep);
                return obj;
            }
            return null;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }finally{
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department dep) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                   "select seller.*, department.Name as DepName\n" +
                           "from seller inner join department\n" +
                           "on seller.DepartmentId = department.Id\n" +
                           "where DepartmentId = ?\n" +
                           "order by Name"
            );
            st.setInt(1, dep.getId());
            rs = st.executeQuery();

            var list = new ArrayList<Seller>();
            var mp = new HashMap<Integer, Department>();

            while(rs.next()){
                Department dep2 = mp.get(rs.getInt("DepartmentId"));
                if(dep2 == null) {
                    dep2 = instantiateDepartment(rs);
                    mp.put(dep.getId(), dep);
                }
                Seller obj = instantiateSeller(rs, dep2);
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }finally{
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "select seller.*, department.Name as DepName\n" +
                            "from seller inner join department\n" +
                            "on seller.DepartmentId = department.Id\n" +
                            "order by Name"
            );
            rs = st.executeQuery();
            var list = new ArrayList<Seller>();
            var mp = new HashMap<Integer, Department>();
            while(rs.next()){
                Department dep = mp.get(rs.getInt("DepartmentId"));
                if(dep == null) {
                    dep = instantiateDepartment(rs);
                    mp.put(dep.getId(), dep);
                }
                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }finally{
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException{
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartment(dep);
        return obj;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

}
