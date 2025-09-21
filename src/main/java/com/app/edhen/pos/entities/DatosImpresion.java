package com.app.edhen.pos.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "datos_impresion")
public class DatosImpresion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "envio_id", nullable = false)
    private Envio envio;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "fecha_impresion")
    private LocalDateTime fechaImpresion;

    @Column(nullable = false)
    private Boolean impreso = false;

    // Constructores
    public DatosImpresion() {}

    public DatosImpresion(Envio envio, String contenido) {
        this.envio = envio;
        this.contenido = contenido;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Envio getEnvio() { return envio; }
    public void setEnvio(Envio envio) { this.envio = envio; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDateTime getFechaImpresion() { return fechaImpresion; }
    public void setFechaImpresion(LocalDateTime fechaImpresion) { this.fechaImpresion = fechaImpresion; }

    public Boolean getImpreso() { return impreso; }
    public void setImpreso(Boolean impreso) { this.impreso = impreso; }

    // Métodos de negocio
    public void marcarComoImpreso() {
        this.impreso = true;
        this.fechaImpresion = LocalDateTime.now();
    }
}