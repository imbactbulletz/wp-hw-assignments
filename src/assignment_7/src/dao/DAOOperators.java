package assignment_7.src.dao;

import dao.DAOAbstractDatabase;
import dao.IDAOOperators;
import entities.Operators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOOperators extends DAOAbstractDatabase<Operators> implements IDAOOperators {

    public DAOOperators(){
        super(Operators.class);
    }

    @Override
    public boolean login(Operators operator) {
        Connection connection = createConnection();

        //System.out.println("USERNAME: " + operator.getUsername() + " PASSWORD: " + operator.getPassword());

        if (connection == null) {
            return false;
        }
        String query = String.format("select * from %s where %s = \"%s\" and %s = \"%s\"", "operators", "username", operator.getUsername(), "password", operator.getPassword());

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean registration(Operators operator) {
        Connection connection = createConnection();

        if (connection == null) {
            return false;
        }
        //System.out.println("USERNAME: " + operator.getUsername() + " PASSWORD: " + operator.getPassword());
        String query = String.format("select * from %s where %s = \"%s\"", "operators", "username", operator.getUsername());
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) {
                preparedStatement = connection.prepareStatement(String.format("insert into %s (%s,%s) VALUES (\"%s\",\"%s\")", "operators", "username", "password", operator.getUsername(), operator.getPassword()));
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    return true;
                }
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
