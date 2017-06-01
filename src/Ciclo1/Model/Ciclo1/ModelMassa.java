/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Model.Ciclo1;

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
@Table(name = "Massa")
public class ModelMassa implements Serializable{
    @Id
    @GeneratedValue
    private int cod;
    
    @Column
    private double massa;

    public ModelMassa() {
    }

    public ModelMassa(double massa) {
        this.massa = massa;
    }
    
    public double getMassa() {
        return massa;
    }

    public void setMassa(double massa) {
        this.massa = massa;
    }
}
