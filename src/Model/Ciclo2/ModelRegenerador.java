/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Ciclo2;

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
    private double temperaturaEntr2;
    
    @Column
    private double temperaturaSai2;
    
    @Column
    private double pressaoEntr;
    
    @Column
    private double pressaoSai;
    
    @Column
    private double pressaoEntr2;
    
    @Column
    private double pressaoSai2;
    
    @Column
    private double delaPressao;
    
    @Column
    private double efetividade;

    public ModelRegenerador() {
    }

    public ModelRegenerador(double temperaturaEntr, double temperaturaSai, double temperaturaEntr2, double temperaturaSai2, double pressaoEntr, double pressaoSai, double pressaoEntr2, double pressaoSai2, double delaPressao, double efetividade) {
        this.temperaturaEntr = temperaturaEntr;
        this.temperaturaSai = temperaturaSai;
        this.temperaturaEntr2 = temperaturaEntr2;
        this.temperaturaSai2 = temperaturaSai2;
        this.pressaoEntr = pressaoEntr;
        this.pressaoSai = pressaoSai;
        this.pressaoEntr2 = pressaoEntr2;
        this.pressaoSai2 = pressaoSai2;
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

    public double getTemperaturaEntr2() {
        return temperaturaEntr2;
    }

    public void setTemperaturaEntr2(double temperaturaEntr2) {
        this.temperaturaEntr2 = temperaturaEntr2;
    }

    public double getTemperaturaSai2() {
        return temperaturaSai2;
    }

    public void setTemperaturaSai2(double temperaturaSai2) {
        this.temperaturaSai2 = temperaturaSai2;
    }

    public double getPressaoEntr2() {
        return pressaoEntr2;
    }

    public void setPressaoEntr2(double pressaoEntr2) {
        this.pressaoEntr2 = pressaoEntr2;
    }

    public double getPressaoSai2() {
        return pressaoSai2;
    }

    public void setPressaoSai2(double pressaoSai2) {
        this.pressaoSai2 = pressaoSai2;
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
