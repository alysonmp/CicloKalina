/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author alysonmp
 */
@Entity
@Table(name = "Constantes")
public class ModelConstantesMat implements Serializable{
    
    @Id
    private String nome;
    
    @Column
    private double[][] valores;

    public ModelConstantesMat() {
    }

    public ModelConstantesMat(String nome, double[][] valores) {
        this.nome = nome;
        this.valores = valores;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double[][] getValores() {
        return valores;
    }

    public void setValores(double[][] valores) {
        this.valores = valores;
    }    
}
