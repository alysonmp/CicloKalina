/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author leonardo
 */
@Entity
@Table(name = "Qfpso")
public class ModelQfpso {
    
    @Id
    @GeneratedValue
    private int cod;
    
    @Column
    private double[] Qfpso;

    public ModelQfpso() {
    }        
    
    public ModelQfpso(double[] Qfpso){
        this.Qfpso = Qfpso;
    }

    public double[] getQfpso() {
        return Qfpso;
    }

    public void setQfpso(double[] Qfpso) {
        this.Qfpso = Qfpso;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
}
