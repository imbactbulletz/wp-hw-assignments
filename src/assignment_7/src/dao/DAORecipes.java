package assignment_7.src.dao;

import dao.DAOAbstractDatabase;
import dao.IDAORecipes;
import entities.Recipes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAORecipes extends DAOAbstractDatabase<Recipes> implements IDAORecipes {

    public DAORecipes(){
        super(Recipes.class);
    }

    @Override
    public List<Recipes> searchByName(String name) {
        Connection conn = createConnection();
        if (conn == null) {
            return null;
        }
        try {
            PreparedStatement st = conn.prepareStatement(String.format("select * from %s where %s LIKE \"%%%s%%\"", Recipes.class.getSimpleName(), "name",name));
            ResultSet rs = st.executeQuery();
            List<Recipes> list = new ArrayList<Recipes>();
            while (rs.next()) {
                Recipes rsRecipe = readFromResultSet(rs);
               // System.out.println(rsRecipe.getName());
                list.add(rsRecipe);
            }
            closeStatement(st);
            closeResultSet(rs);
            return list;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    @Override
    public List<Recipes> getPage(int page, int pageSize, String searchCriteria) {
        Connection conn = createConnection();
        if (conn == null) {
            return null;
        }
        try {
           // System.out.println("PAGE:" + page +" MUL: " + ((page-1)*pageSize));
            PreparedStatement st = conn.prepareStatement(String.format("select * from %s limit %s offset %s", Recipes.class.getSimpleName(), pageSize, (page-1)*pageSize));
            ResultSet rs = st.executeQuery();
            List<Recipes> list = new ArrayList<Recipes>();
            while (rs.next()) {
                Recipes rsRecipe = readFromResultSet(rs);
                //.out.println(rsRecipe.getName());
                if(rsRecipe.getName().contains(searchCriteria)){
                    list.add(rsRecipe);
                }
                else if(searchCriteria.equals("*")){
                    list.add(rsRecipe);
                }
            }
            closeStatement(st);
            closeResultSet(rs);
            return list;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return null;
    }
}
