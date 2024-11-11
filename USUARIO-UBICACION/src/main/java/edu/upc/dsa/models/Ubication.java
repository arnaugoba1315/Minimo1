package edu.upc.dsa.models;

import java.lang.annotation.ElementType;

public class Ubication {
    private int x;
    private int y;
    private ElementType type;

    public Ubication(int x, int y, edu.upc.dsa.models.ElementType type) {
    }

    public Ubication(int x, int y, ElementType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ElementType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Posicion del Usuario [Coordenada X=" + x+ ", Coordenada Y=" + y + ", Tipo=" + type +  "]";
    }

}