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
@Table(name = "Regenerador")
public class ModelRegenerador implements Serializable{
    
    @Id
    @GeneratedValue
    private int cod;
    
    @Column
    private double temperaturaEntr;
    
    @Column
    private double temperaturaSai;
    
    @Column
    private double pressaoEntr;
    
    @Column
    private double pressaoSai;
    
    @Column
    private double delaPressao;
    
    @Column
    private double efetividade;

    public ModelRegenerador() {
    }

    public ModelRegenerador(double temperaturaEntr, double temperaturaSai, double pressaoEntr, double pressaoSai, double delaPressao, double efetividade) {
        this.temperaturaEntr = temperaturaEntr;
        this.temperaturaSai = temperaturaSai;
        this.pressaoEntr = pressaoEntr;
        this.pressaoSai = pressaoSai;
        this.delaPressao = delaPressao;
        this.efetividade = efetividade;
    }
    
    public double getTemperaturaEntr() {
        return temperaturaEntr;
    }

    public void setTemperaturaEntr(double temperaturaEntr) {
        this.temperaturaEntr = temperaturaEntr;
    }

    public double getTemperaturaSai() {
        return temperaturaSai;
    }

    public void setTemperaturaSai(double temperaturaSai) {
        this.temperaturaSai = temperaturaSai;
    }

    public double getPressaoEntr() {
        return pressaoEntr;
    }

    public void setPressaoEntr(double pressaoEntr) {
        this.pressaoEntr = pressaoEntr;
    }

    public double getPressaoSai() {
        return pressaoSai;
    }

    public void setPressaoSai(double pressaoSai) {
        this.pressaoSai = pressaoSai;
    }

    public double getDelaPressao() {
        return delaPressao;
    }

    public void setDelaPressao(double delaPressao) {
        this.delaPressao = delaPressao;
    }

    public double getEfetividade() {
        return efetividade;
    }

    public void setEfetividade(double efetividade) {
        this.efetividade = efetividade;
    }
}
