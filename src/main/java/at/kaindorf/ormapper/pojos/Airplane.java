package at.kaindorf.ormapper.pojos;

import at.kaindorf.ormapper.annotations.Column;
import at.kaindorf.ormapper.annotations.Entity;
import at.kaindorf.ormapper.annotations.Id;

import java.time.LocalDate;

@Entity(name = "airbus")
public class Airplane {

    @Id
    private Long airplaneId;
    @Column(lenght = 100, unique = true, nullable = false)
    private String name;

    private Double wingspan;

    @Column(nullable = false)
    private int maxNumberofPassengers;

    @Column(name = "build_at")
    private LocalDate buildDate;
}
