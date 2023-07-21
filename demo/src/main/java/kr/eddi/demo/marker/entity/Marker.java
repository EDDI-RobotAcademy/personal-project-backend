package kr.eddi.demo.marker.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Marker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String coordLat;
    private String coordLng;

    public Marker(String coordLat, String coordLng) {
        this.coordLat = coordLat;
        this.coordLng = coordLng;
    }

    // Getter와 Setter (생략 가능)

    public Long getId() {
        return id;
    }

    public String getCoordLat() {
        return coordLat;
    }

    public void setCoordLat(String coordLat) {
        this.coordLat = coordLat;
    }

    public String getCoordLng() {
        return coordLng;
    }

    public void setCoordLng(String coordLng) {
        this.coordLng = coordLng;
    }
}
