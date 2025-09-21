package com.app.edhen.pos.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "envios")
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id", nullable = false)
    private Direccion direccion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id")
    private Venta venta;

    @Column(name = "codigo_envio", nullable = false, unique = true)
    private String codigoEnvio;

    private String transportista;

    @Column(name = "costo_envio")
    private Double costoEnvio = 0.0;

    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud;

    @Column(name = "fecha_estimada_entrega")
    private LocalDateTime fechaEstimadaEntrega;

    @Column(name = "fecha_real_entrega")
    private LocalDateTime fechaRealEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_envio", nullable = false)
    private EstadoEnvio estadoEnvio = EstadoEnvio.PENDIENTE;

    @OneToOne(mappedBy = "envio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DatosImpresion datosImpresion;

    // Enumeración para el estado del envío
    public enum EstadoEnvio {
        PENDIENTE,
        PREPARANDO,
        EN_CAMINO,
        ENTREGADO,
        CANCELADO
    }

    // Constructores
    public Envio() {
        this.fechaSolicitud = LocalDateTime.now();
        this.codigoEnvio = generarCodigoEnvio();
    }

    public Envio(Cliente cliente, Direccion direccion, Venta venta) {
        this();
        this.cliente = cliente;
        this.direccion = direccion;
        this.venta = venta;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }

    public Venta getVenta() { return venta; }
    public void setVenta(Venta venta) { this.venta = venta; }

    public String getCodigoEnvio() { return codigoEnvio; }
    public void setCodigoEnvio(String codigoEnvio) { this.codigoEnvio = codigoEnvio; }

    public String getTransportista() { return transportista; }
    public void setTransportista(String transportista) { this.transportista = transportista; }

    public Double getCostoEnvio() { return costoEnvio; }
    public void setCostoEnvio(Double costoEnvio) { this.costoEnvio = costoEnvio; }

    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public LocalDateTime getFechaEstimadaEntrega() { return fechaEstimadaEntrega; }
    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) { this.fechaEstimadaEntrega = fechaEstimadaEntrega; }

    public LocalDateTime getFechaRealEntrega() { return fechaRealEntrega; }
    public void setFechaRealEntrega(LocalDateTime fechaRealEntrega) { this.fechaRealEntrega = fechaRealEntrega; }

    public EstadoEnvio getEstadoEnvio() { return estadoEnvio; }
    public void setEstadoEnvio(EstadoEnvio estadoEnvio) { this.estadoEnvio = estadoEnvio; }

    public DatosImpresion getDatosImpresion() { return datosImpresion; }
    public void setDatosImpresion(DatosImpresion datosImpresion) { this.datosImpresion = datosImpresion; }

    // Métodos de negocio
    private String generarCodigoEnvio() {
        return "ENV-" + System.currentTimeMillis();
    }

    public void prepararEnvio() {
        if (this.estadoEnvio != EstadoEnvio.PENDIENTE) {
            throw new IllegalStateException("Solo los envíos pendientes pueden ser preparados");
        }
        this.estadoEnvio = EstadoEnvio.PREPARANDO;
    }

    public void enviar() {
        if (this.estadoEnvio != EstadoEnvio.PREPARANDO) {
            throw new IllegalStateException("Solo los envíos en preparación pueden ser enviados");
        }
        this.estadoEnvio = EstadoEnvio.EN_CAMINO;
    }

    public void marcarComoEntregado() {
        if (this.estadoEnvio != EstadoEnvio.EN_CAMINO) {
            throw new IllegalStateException("Solo los envíos en camino pueden ser marcados como entregados");
        }
        this.estadoEnvio = EstadoEnvio.ENTREGADO;
        this.fechaRealEntrega = LocalDateTime.now();
    }

    public void cancelarEnvio() {
        if (this.estadoEnvio == EstadoEnvio.ENTREGADO) {
            throw new IllegalStateException("No se puede cancelar un envío ya entregado");
        }
        this.estadoEnvio = EstadoEnvio.CANCELADO;
    }
}