package com.app.edhen.pos.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "colores")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(name = "codigo_hex", length = 7)
    private String codigoHex;

    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductoVariante> productoVariantes = new ArrayList<>();

    // Constructores
    public Color() {}

    public Color(String nombre, String codigoHex) {
        this.nombre = nombre;
        this.codigoHex = codigoHex;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCodigoHex() { return codigoHex; }
    public void setCodigoHex(String codigoHex) { this.codigoHex = codigoHex; }

    public List<ProductoVariante> getProductoVariantes() { return productoVariantes; }
    public void setProductoVariantes(List<ProductoVariante> productoVariantes) { this.productoVariantes = productoVariantes; }

    // Métodos de utilidad
    public void agregarProductoVariante(ProductoVariante productoVariante) {
        productoVariantes.add(productoVariante);
        productoVariante.setColor(this);
    }

    public void eliminarProductoVariante(ProductoVariante productoVariante) {
        productoVariantes.remove(productoVariante);
        productoVariante.setColor(null);
    }
}