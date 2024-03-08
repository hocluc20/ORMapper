package at.kaindorf.ormapper.io;

import at.kaindorf.ormapper.annotations.Entity;
import at.kaindorf.ormapper.pojos.Persistence;
import jakarta.xml.bind.JAXB;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class IO_Access {

    public static Persistence loadPersistenceUnit(){
        InputStream inputStream = IO_Access.class.getResourceAsStream("/persistence.xml");
        return JAXB.unmarshal(inputStream, Persistence.class);
    }

    public static List<Class<?>> scanEntityClassesFromProject() throws IOException {
        Path sourcePath = Path.of(System.getProperty("user.dir"), "src", "main","java");
        return Files.walk(sourcePath)
                .filter(path -> path.toString().endsWith(".java"))
                .map(sourcePath::relativize)
                .map(p -> p.toString().replace(File.separator,".").replace(".java",""))
                .map(IO_Access::getClassInfo)
                .filter(c -> c.isAnnotationPresent(Entity.class))
                .collect(Collectors.toList());
    }

    private static Class<?> getClassInfo(String s){
        try {
            return Class.forName(s);
        } catch (ClassNotFoundException e) {
            return Object.class;
        }
    }

    /**
     * C:\Hochfellner\HTBLA Kaindorf\4CHIF\POS\Exa_OR-Mapper\src\main\java\at\kaindorf\ormapper\annotations\Entity.java
     * at.kaindorf.ormapper.annotations.Entity.java
     * @param args
     */


    public static void main(String[] args) {
        try{
            System.out.println(scanEntityClassesFromProject());
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }


}
