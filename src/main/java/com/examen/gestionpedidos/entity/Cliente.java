
package com.examen.gestionpedidos.entity;

import jakarta.persistence.*;
        import lombok.*;
        import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, unique = true, length = 15)
    private String dni;

    @Column(nullable = false, unique = true, length = 150)
    private String correo;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    //Este método se ejecuta ANTES de insertar por primera vez
    // estoy asumiendo que todo ingreso será mediante el sistema (No ingreso manual)
    @PrePersist
    protected void onCreate() {

        this.fechaRegistro = LocalDateTime.now();
    }


}