/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Model;

import java.io.Serializable;
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
@Table(name = "Linear")
public class ModelLinear implements Serializable{
    
    @Id
    @GeneratedValue
    private int cod;
    
    @Column
    private double[] lin;

    public ModelLinear() {
    }

    public ModelLinear(double[] lin) {
        this.lin = lin;
    }

    public double[] getLin() {
        return lin;
    }

    public void setLin(double[] lin) {
        this.lin = lin;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
}
