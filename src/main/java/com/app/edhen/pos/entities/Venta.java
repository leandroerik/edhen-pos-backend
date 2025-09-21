package com.app.edhen.pos.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "codigo_venta", nullable = false, unique = true)
    private String codigoVenta;

    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta;

    @Column(nullable = false)
    private Double subtotal = 0.0;

    private Double impuestos = 0.0;

    private Double descuento = 0.0;

    @Column(nullable = false)
    private Double total = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_compra", nullable = false)
    private TipoCompra tipoCompra;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_venta", nullable = false)
    private EstadoVenta estadoVenta = EstadoVenta.PENDIENTE;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VentaDetalle> detalles = new ArrayList<>();

    @OneToOne(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Envio envio;

    // Enumeraciones
    public enum MetodoPago {
        EFECTIVO,
        TARJETA,
        TRANSFERENCIA,
        MERCADO_PAGO
    }

    public enum TipoCompra {
        LOCAL_FISICO,
        WHATSAPP,
        WEB,
        MARKETPLACE,
        TELEFONO
    }

    public enum EstadoVenta {
        PENDIENTE,
        COMPLETADA,
        CANCELADA
    }

    // Constructores
    public Venta() {
        this.fechaVenta = LocalDateTime.now();
        this.codigoVenta = generarCodigoVenta();
    }

    public Venta(Cliente cliente, MetodoPago metodoPago, TipoCompra tipoCompra) {
        this();
        this.cliente = cliente;
        this.metodoPago = metodoPago;
        this.tipoCompra = tipoCompra;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getCodigoVenta() { return codigoVenta; }
    public void setCodigoVenta(String codigoVenta) { this.codigoVenta = codigoVenta; }

    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Double getImpuestos() { return impuestos; }
    public void setImpuestos(Double impuestos) { this.impuestos = impuestos; }

    public Double getDescuento() { return descuento; }
    public void setDescuento(Double descuento) { this.descuento = descuento; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }

    public TipoCompra getTipoCompra() { return tipoCompra; }
    public void setTipoCompra(TipoCompra tipoCompra) { this.tipoCompra = tipoCompra; }

    public EstadoVenta getEstadoVenta() { return estadoVenta; }
    public void setEstadoVenta(EstadoVenta estadoVenta) { this.estadoVenta = estadoVenta; }

    public List<VentaDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<VentaDetalle> detalles) { this.detalles = detalles; }

    public Envio getEnvio() { return envio; }
    public void setEnvio(Envio envio) { this.envio = envio; }

    // Métodos de negocio
    private String generarCodigoVenta() {
        return "V-" + System.currentTimeMillis();
    }

    public void agregarDetalle(VentaDetalle detalle) {
        detalles.add(detalle);
        detalle.setVenta(this);
        calcularTotales();
    }

    public void eliminarDetalle(VentaDetalle detalle) {
        detalles.remove(detalle);
        detalle.setVenta(null);
        calcularTotales();
    }

    public void calcularTotales() {
        this.subtotal = detalles.stream()
                .mapToDouble(VentaDetalle::getSubtotal)
                .sum();

        this.total = this.subtotal + this.impuestos - this.descuento;
    }

    public void completarVenta() {
        if (this.estadoVenta != EstadoVenta.PENDIENTE) {
            throw new IllegalStateException("Solo las ventas pendientes pueden ser completadas");
        }

        // Reducir stock de los productos
        for (VentaDetalle detalle : detalles) {
            detalle.getVariante().reducirStock(detalle.getCantidad());
        }

        this.estadoVenta = EstadoVenta.COMPLETADA;
    }

    public void cancelarVenta() {
        if (this.estadoVenta != EstadoVenta.PENDIENTE) {
            throw new IllegalStateException("Solo las ventas pendientes pueden ser canceladas");
        }

        this.estadoVenta = EstadoVenta.CANCELADA;
    }
}