package edu.upc.dsa.models;

// ELIMINAR ESTE IMPORT
// import java.lang.annotation.ElementType;

public class Ubication {
    private int x;
    private int y;
    private ElementType type;  // Este es el ElementType de nuestro paquete

    // Constructor vacío necesario para JSON
    public Ubication() {
    }

    // Constructor principal
    public Ubication(int x, int y, ElementType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ElementType getType() {
        return type;
    }

    // Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Posicion del Usuario [Coordenada X=" + x + ", Coordenada Y=" + y + ", Tipo=" + type + "]";
    }
}