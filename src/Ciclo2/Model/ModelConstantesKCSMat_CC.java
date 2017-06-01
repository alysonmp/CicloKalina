/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author alysonmp
 */
@Entity
@Table(name = "Constantes_Kcs_CC")
public class ModelConstantesKCSMat_CC implements Serializable{
    
    @Id
    @GeneratedValue
    private int cod;
    
    @Column
    private double[] valores;

    public ModelConstantesKCSMat_CC() {
    }

    public ModelConstantesKCSMat_CC(double[] valores) {
        this.valores = valores;
    }
    
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public double[] getValores() {
        return valores;
    }

    public void setValores(double[] valores) {
        this.valores = valores;
    }
    
    
}
