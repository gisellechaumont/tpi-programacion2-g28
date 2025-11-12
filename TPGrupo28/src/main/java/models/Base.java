/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Daniela Bonetti
 */
public abstract class Base {
 private long id;    
 private boolean eliminado;

    protected Base(long id, boolean eliminado) {
        this.id = id;
        this.eliminado = eliminado;
    }

    public Base() {
        eliminado = false;
    }

    
    
    public long getId() {
        return id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
 
 
 
 
 
 
 
}
