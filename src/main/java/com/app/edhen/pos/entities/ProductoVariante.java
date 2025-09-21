package com.app.edhen.pos.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "producto_variantes")
public class ProductoVariante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talla_id", nullable = false)
    private Talla talla;

    @Column(unique = true)
    private String sku;

    private Double precio;

    @Column(nullable = false)
    private Integer stock = 0;

    @Column(name = "stock_minimo")
    private Integer stockMinimo = 5;

    @Column(nullable = false)
    private Boolean activo = true;

    @OneToMany(mappedBy = "variante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VentaDetalle> ventaDetalles = new ArrayList<>();

    // Constructores
    public ProductoVariante() {}

    public ProductoVariante(Producto producto, Color color, Talla talla, String sku, Double precio, Integer stock) {
        this.producto = producto;
        this.color = color;
        this.talla = talla;
        this.sku = sku;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public Talla getTalla() { return talla; }
    public void setTalla(Talla talla) { this.talla = talla; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public List<VentaDetalle> getVentaDetalles() { return ventaDetalles; }
    public void setVentaDetalles(List<VentaDetalle> ventaDetalles) { this.ventaDetalles = ventaDetalles; }

    // Métodos de negocio
    public void reducirStock(Integer cantidad) {
        if (this.stock >= cantidad) {
            this.stock -= cantidad;
        } else {
            throw new IllegalStateException("Stock insuficiente para la variante: " + this.sku);
        }
    }

    public void aumentarStock(Integer cantidad) {
        this.stock += cantidad;
    }

    public Boolean necesitaReposicion() {
        return this.stock <= this.stockMinimo;
    }
}