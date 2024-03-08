package at.kaindorf.ormapper.database;

import at.kaindorf.ormapper.io.IO_Access;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EntityManager {
    private DB_Access dbAccess = DB_Access.getInstance();
    private Statement statement;

    public EntityManager(){
        try {
            List<Class<?>> entityClasss = IO_Access.scanEntityClassesFromProject();
            statement = dbAccess.getStatement();
            if(dbAccess.getDbddlSchema().equals("drop-and-create")){
                entityClasss.forEach(this::deleteTable);
                entityClasss.forEach(this::createTable);
            }
        } catch (IOException e) {
            System.out.println("Failed scanning entity classes");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("Statement creation failed");
            throw new RuntimeException(e);
        }
    }

    /*
    * Create new Table in postgres database from entity
    * CREATE TABLE [IF NOT EXISTS] table_name (
        column1 datatype(length) column_constraint,
        column2 datatype(length) column_constraint,
        ...
         table_constraints
        );
    *  */

    public void deleteTable (Class<?> entityClass){
        String sqlString = "DROP TABLE IF EXISTS "+entityClass.getSimpleName().toLowerCase();
        try {
            statement.execute(sqlString);
            System.out.println("Table "+entityClass.getSimpleName()+" dropped");
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public void createTable (Class<?> entityClass){
        String tableName = entityClass.getSimpleName().toLowerCase();
        StringBuilder sqlString = new StringBuilder("CREATE TABLE ")
                .append(tableName)
                .append(" (");

        for (Field field: entityClass.getDeclaredFields()){
            System.out.println(field.getName());
            String columnName = field.getName().toLowerCase();
            String datatype = "??";
            String contraint = "??";
        }

        System.out.println(sqlString);
    }

    public static void main(String[] args) {
        EntityManager entityManager = new EntityManager();
    }


}
