package assignment_7.src.entities;

import utils.SafeConverter;

public class Operators extends BasicEntity{
    private String username;
    private String password;

    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";

    public Operators(){
        super();

        this.columnNames.add(USERNAME);
        this.columnNames.add(PASSWORD);
    }


    @Override
    public Object getValueForColumnName(String columnName) {
        if(USERNAME.equals(columnName)){
            return this.username;
        }

        if(PASSWORD.equals(columnName)){
            return this.password;
        }

        return super.getValueForColumnName(columnName);
    }

    @Override
    public void setValueForColumnName(String columnName, Object value) {
        if(USERNAME.equals(columnName)){
            this.setUsername(SafeConverter.toSafeString(value));
        }

        if(PASSWORD.equals(columnName)){
            this.setPassword(SafeConverter.toSafeString(value));
        }

        super.setValueForColumnName(columnName, value);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
