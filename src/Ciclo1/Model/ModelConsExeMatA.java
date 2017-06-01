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
 * @author alysonmp
 */
@Entity
@Table(name = "cons_exeA")
public class ModelConsExeMatA implements Serializable{
    
    @Id
    @GeneratedValue
    private int cod;
    
    @Column
    private double[] valores;

    public ModelConsExeMatA() {
    }

    public ModelConsExeMatA(double[] valores) {
        this.valores = valores;
    }

    public int getCod() {
        return cod;
    }

    public void setNome(int cod) {
        this.cod = cod;
    }

    public double[] getValores() {
        return valores;
    }

    public void setValores(double[] valores) {
        this.valores = valores;
    }
    
}
