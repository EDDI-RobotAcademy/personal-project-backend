package kr.eddi.demo.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Marker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String coordLat;
    private String coordLng;

    public Marker(String coordLat, String coordLng) {
        this.coordLat = coordLat;
        this.coordLng = coordLng;
    }
}