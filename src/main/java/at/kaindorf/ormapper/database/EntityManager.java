package at.kaindorf.ormapper.database;

import at.kaindorf.ormapper.annotations.Column;
import at.kaindorf.ormapper.annotations.Entity;
import at.kaindorf.ormapper.annotations.Id;
import at.kaindorf.ormapper.io.IO_Access;
import at.kaindorf.ormapper.pojos.Airplane;

import java.io.IOException;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static at.kaindorf.ormapper.database.SQLDataTypes.SQL_TYPES;

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
        //entry point for reflections??????

        //Class<?> clazz1 = Airplane.class;
        Class<?> clazz2 = entityClass.getClass();

        String annotationname = entityClass.getAnnotation(Entity.class).name();
        String tableName = annotationname.isBlank() ?  entityClass.getSimpleName().toLowerCase() : annotationname ;
        String createTableString = String.format("CREATE TABLE %s (", tableName);


        // getDeclaredFields = sind die private auch dabei
        // getFields = public oder inherited sind

        for (Field field: entityClass.getDeclaredFields()){
            String columnName = field.getName().toLowerCase();
            String datatype = SQL_TYPES.get(field.getType());
            String constraint = field.isAnnotationPresent(Id.class) ? " PRIMARY KEY" : "";

            if(field.isAnnotationPresent(Column.class)){
                Column column = field.getDeclaredAnnotation(Column.class);
                //change columname if required
                String name = column.name();
                columnName = name.isBlank() ? columnName : name;
                //Insert NOT NULL if required
                constraint += column.nullable() ? "" : " NOT NULL";
                constraint += column.unique() ? " UNIQUE" : "";

                //change Varchar-length if required
                if(field.getType().equals(String.class) && column.lenght() != 255){
                    datatype = datatype.replace("255", column.lenght()+"");
                }
            }
            createTableString += String.format("%s %s%s,\n", columnName,datatype,constraint);
        }
        createTableString = createTableString.substring(0,createTableString.lastIndexOf(","))+");";
        try {
            statement.execute(createTableString);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        System.out.println(createTableString);
    }

    public static void main(String[] args) {
        EntityManager entityManager = new EntityManager();
    }


}
