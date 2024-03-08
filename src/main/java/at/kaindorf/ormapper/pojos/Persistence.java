package at.kaindorf.ormapper.pojos;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@XmlRootElement(name = "persistence-unit")
//@XmlAccessorType(XmlAccessType.FIELD) //not required!

public class Persistence {
    private Map<String, String> entryMap = new HashMap<>();

    @XmlElementWrapper(name = "properties")
    @XmlElement(name = "property")
    public MapEntry[] getEntryMap(){
//        return entryMap.entrySet().toArray(MapEntry[]::new);
        return entryMap.entrySet()
                .stream()
                .map(entry -> new MapEntry(entry.getKey(),entry.getValue()))
                .toArray(MapEntry[]::new);
    }
    public void setEntryMap(MapEntry[] entries){
        this.entryMap = Arrays.stream(entries)
                .collect(Collectors.toMap(MapEntry::getName,MapEntry::getValue));
    }

    public Map<String, String> getDatabaseProperties (){
        return entryMap;
    }


    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MapEntry{
        @XmlAttribute
        private String name;
        @XmlAttribute
        private String value;
    }


}
