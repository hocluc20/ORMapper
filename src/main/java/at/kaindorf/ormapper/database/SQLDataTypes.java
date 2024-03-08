package at.kaindorf.ormapper.database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class SQLDataTypes {
    public static final Map<Class<?>, String> SQL_TYPES = Map.ofEntries(
            Map.entry(Integer.class,"INT"),
            Map.entry(int.class,"INT"),
            Map.entry(Long.class,"BIGINT"),
            Map.entry(long.class,"BIGINT"),
            Map.entry(Double.class,"FLOAT8"),
            Map.entry(double.class,"FLOAT8"),
            Map.entry(Boolean.class,"BOOL"),
            Map.entry(boolean.class,"BOOL"),
            Map.entry(LocalDate.class,"DATE"),
            Map.entry(LocalTime.class,"TIME"),
            Map.entry(LocalDateTime.class,"TIMESTAMP"),
            Map.entry(String.class,"VARCHAR(255)")
    );

}
