package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity(name = "domicilio")
public class DomicilioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String calle;
    private Integer numero;
    private String codigoPostal;
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "localidad_id", referencedColumnName = "id", nullable = false)
    private LocalidadEntity localidad;

    @Override
    public String toString() {
        return "id=" + id +",calle='" + calle +",numero=" + numero + ",codigoPostal=" + codigoPostal + ",localidad=" + localidad.getNombre();
    }

    public static DomicilioEntity fromSnapshotString(String snapshot) {
        DomicilioEntity domicilio = new DomicilioEntity();
        String[] parts = snapshot.split(",");

        for (String part : parts) {
            String[] keyValue = part.split("=");
            String key = keyValue[0].trim();
            String value = keyValue.length > 1 ? keyValue[1].trim() : "";

            switch (key) {
                case "id":
                    domicilio.setId(Long.parseLong(value));
                    break;
                case "calle":
                    domicilio.setCalle(value.replace("'", ""));
                    break;
                case "numero":
                    domicilio.setNumero(Integer.parseInt(value));
                    break;
                case "codigoPostal":
                    domicilio.setCodigoPostal(value);
                    break;
                case "localidad":
                    LocalidadEntity localidad = new LocalidadEntity();
                    localidad.setNombre(value); // solo seteamos el nombre
                    domicilio.setLocalidad(localidad);
                    break;
            }
        }

        return domicilio;
    }
}
