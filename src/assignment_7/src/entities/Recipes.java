package assignment_7.src.entities;

import utils.SafeConverter;

public class Recipes extends BasicEntity{
    private String name;
    private String content;

    private static final String NAME = "NAME";
    private static final String CONTENT = "CONTENT";

    public Recipes(){
        super();

        this.columnNames.add(NAME);
        this.columnNames.add(CONTENT);

    }

    @Override
    public Object getValueForColumnName(String columnName) {

        if(NAME.equals(columnName)){
            return this.name;
        }

        if(CONTENT.equals(columnName)){
            return this.content;
        }

        return super.getValueForColumnName(columnName);
    }

    @Override
    public void setValueForColumnName(String columnName, Object value) {

        if(NAME.equals(columnName)){
            this.setName(SafeConverter.toSafeString(value));
        }

        if(CONTENT.equals(columnName)){
            this.setContent(SafeConverter.toSafeString(value));
        }

        super.setValueForColumnName(columnName, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
