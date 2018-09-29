package assignment_7.src.dao;


import entities.BasicEntity;


import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DAOAbstractDatabase <T extends BasicEntity> implements IDAOAbstract<T>{
    private Class<T> clazz;

    private String USERNAME = "root";
    private String PASSWORD = "";
    private String DATABASE_NAME = "rno";


    public DAOAbstractDatabase(Class<T> clazz){
        this.clazz = clazz;
    }

    @Override
    public boolean add(T object) {
        if( object == null ){
            return false;
        }
        Connection conn = createConnection();
        if (conn == null) {
            return false;
        }

        String columnsName = "";
        String questionMarks = "";
        for(String columnName : object.columnNames()){
            if(object.primaryKeyColumnName().equals(columnName))
                continue;
            columnsName = columnsName == "" ? columnName : String.format("%s, %s", columnsName, columnName);
            questionMarks = questionMarks == "" ? "?" : String.format("%s, ?", questionMarks);
        }

        String strQuery = String.format("INSERT INTO %s (%s) VALUES (%s)", this.clazz.getSimpleName(), columnsName, questionMarks);

        try {
            PreparedStatement st = conn.prepareStatement(strQuery);
            int parameterIndex = 1;
            for(String columnName : object.columnNames()){
                if(object.primaryKeyColumnName().equals(columnName))
                    continue;
                st.setObject(parameterIndex++, object.getValueForColumnName(columnName));
            }
            int rows_affected = st.executeUpdate();


            return rows_affected > 0;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<T> getAll() {

        Connection conn = createConnection();
        if (conn == null) {
            return null;
        }
        try {
            PreparedStatement st = conn.prepareStatement(String.format("select * from %s", this.clazz.getSimpleName()));
            ResultSet rs = st.executeQuery();
            List<T> list = new ArrayList<T>();
            while (rs.next()) {
                list.add(readFromResultSet(rs));
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

    protected T readFromResultSet(ResultSet resultSet){
        if( resultSet == null){
            return null;
        }

        try {
            T object = this.clazz.getDeclaredConstructor().newInstance();

            for(String columnName : object.columnNames()){

                object.setValueForColumnName(columnName, resultSet.getObject(columnName));
            }

            return object;

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    protected void closeResultSet(ResultSet resultSet){
        if(resultSet == null){
            return;
        }

        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected Connection createConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/" + DATABASE_NAME, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void closeConnection(Connection connection){
        if( connection == null){
            return;
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void closeStatement(PreparedStatement statement){
        if(statement == null){
            return;
        }

        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
