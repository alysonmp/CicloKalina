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
@Table(name = "criticas_k1")
public class ModelCriticasKCSMat_K1 implements Serializable{
    
    @Id
    @GeneratedValue
    private int cod;
    
    @Column
    private double valor;

    public ModelCriticasKCSMat_K1() {
    }

    public ModelCriticasKCSMat_K1(double valor) {
        this.valor = valor;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int name) {
        this.cod = name;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }    
}
