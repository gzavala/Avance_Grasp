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
public class coordenadas {
    int x; 
    int y; 

 
    public coordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public coordenadas() {
        this.x = 0;
        this.y = 0;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    
    
}
