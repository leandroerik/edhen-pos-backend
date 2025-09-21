package com.app.edhen.pos.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(name = "codigo_barras", unique = true)
    private String codigoBarras;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(name = "precio_base", nullable = false)
    private Double precioBase;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @Column(nullable = false)
    private Boolean activo = true;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductoVariante> variantes = new ArrayList<>();

    // Constructores
    public Producto() {}

    public Producto(Categoria categoria, String nombre, Double precioBase) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.precioBase = precioBase;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecioBase() { return precioBase; }
    public void setPrecioBase(Double precioBase) { this.precioBase = precioBase; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public List<ProductoVariante> getVariantes() { return variantes; }
    public void setVariantes(List<ProductoVariante> variantes) { this.variantes = variantes; }

    // Métodos de utilidad
    public void agregarVariante(ProductoVariante variante) {
        variantes.add(variante);
        variante.setProducto(this);
    }

    public void eliminarVariante(ProductoVariante variante) {
        variantes.remove(variante);
        variante.setProducto(null);
    }
}