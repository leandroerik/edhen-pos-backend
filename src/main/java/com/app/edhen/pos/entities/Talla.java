package com.app.edhen.pos.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tallas")
public class Talla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTalla tipo;

    @OneToMany(mappedBy = "talla", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductoVariante> productoVariantes = new ArrayList<>();

    // Enumeración para el tipo de talla
    public enum TipoTalla {
        ROPA_SUPERIOR,
        ROPA_INFERIOR,
        CALZADO,
        UNICO
    }

    // Constructores
    public Talla() {}

    public Talla(String nombre, TipoTalla tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public TipoTalla getTipo() { return tipo; }
    public void setTipo(TipoTalla tipo) { this.tipo = tipo; }

    public List<ProductoVariante> getProductoVariantes() { return productoVariantes; }
    public void setProductoVariantes(List<ProductoVariante> productoVariantes) { this.productoVariantes = productoVariantes; }

    // Métodos de utilidad
    public void agregarProductoVariante(ProductoVariante productoVariante) {
        productoVariantes.add(productoVariante);
        productoVariante.setTalla(this);
    }

    public void eliminarProductoVariante(ProductoVariante productoVariante) {
        productoVariantes.remove(productoVariante);
        productoVariante.setTalla(null);
    }
}