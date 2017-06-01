/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Model.TabelasFluidos;

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
@Table(name = "R22_liquido")
public class ModelR22Liquido implements Serializable{
    
    @Id
    @GeneratedValue
    private int ID;
    
    @Column
    private double pressao;
    
    @Column
    private double temperatura;
    
    @Column
    private double kl;
    
    @Column
    private double Cpl;
    
    @Column
    private double Prl;
    
    @Column
    private double Mul;

    @Column
    private double Vcl;

    public ModelR22Liquido(double pressao, double temperatura, double kl, double Cpl, double Prl, double Mul, double Vcl) {
        this.pressao = pressao;
        this.temperatura = temperatura;
        this.kl = kl;
        this.Cpl = Cpl;
        this.Prl = Prl;
        this.Mul = Mul;
        this.Vcl = Vcl;
    }
    
    public ModelR22Liquido(double pressao, double temperatura) {
        this.pressao = pressao;
        this.temperatura = temperatura;
    }

    public ModelR22Liquido() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getPRESSAO() {
        return pressao;
    }

    public void setPRESSAO(double pressao) {
        this.pressao = pressao;
    }

    public double getTEMPERATURA() {
        return temperatura;
    }

    public void setTEMPERATURA(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getCPL() {
        return Cpl;
    }

    public void setCPL(double Cpl) {
        this.Cpl = Cpl;
    }

    public double getPRL() {
        return Prl;
    }

    public void setPRL(double Prl) {
        this.Prl = Prl;
    }
    
    public double getKL() {
        return kl;
    }

    public void setKL(double kl) {
        this.kl = kl;
    }

    public double getMUL() {
        return Mul;
    }

    public void setMUL(double Mul) {
        this.Mul = Mul;
    }

    public double getVCL() {
        return Vcl;
    }

    public void setVCL(double Vcl) {
        this.Vcl = Vcl;
    }
}
