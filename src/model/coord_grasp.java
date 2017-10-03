/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author GZAVALA
 */
//clase que será usada para tener una copia de las coordenadas leidas del archivo de texto,
//y a la vez para preservar las posiciones iniciales ya que con ésta estructura se irán eliminando los 
//valores o coordenadas que vayan siendo agregadas a la solución grasp
public class coord_grasp {
    
 int x;   
 int y; 
 int pos; 
 int ind_lrc=0; //se usará para indicar si el objeto=coordenada esta en la lista reducida de cantidatos, por cada pasada 
// 1 : en caso esté y 0 en caso contrario
    public coord_grasp(int x, int y, int pos, int ind_lrc) {
        this.x = x;
        this.y = y;
        this.pos = pos;
        this.ind_lrc = ind_lrc;

    }
 
    public coord_grasp() {
        this.x = 0;
        this.y = 0;
        this.pos = 0;
        this.ind_lrc=0;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPos() {
        return pos;
    }

    public void setInd_lrc(int ind_lrc) {
        this.ind_lrc = ind_lrc;
    }

    public int getInd_lrc() {
        return ind_lrc;
    }
 
    
    
 
 
}
