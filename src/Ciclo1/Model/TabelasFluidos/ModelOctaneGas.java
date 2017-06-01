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
@Table(name = "Octane_gas")
public class ModelOctaneGas implements Serializable{

    @Id
    @GeneratedValue
    private int ID;

    @Column
    private double pressao;
    
    @Column
    private double temperatura;
    
    @Column
    private double Cpv;
    
    @Column
    private double Prv;
    
    @Column
    private double Kv;
    
    @Column
    private double Muv;
    
    @Column
    private double Vcv;

    public ModelOctaneGas(double pressao, double temperatura) {
        this.pressao = pressao;
        this.temperatura = temperatura;
    }

    public ModelOctaneGas(double pressao, double temperatura, double Cpv, double Prv, double Kv, double Muv, double Vcv) {
        this.pressao = pressao;
        this.temperatura = temperatura;
        this.Cpv = Cpv;
        this.Prv = Prv;
        this.Kv = Kv;
        this.Muv = Muv;
        this.Vcv = Vcv;
    }

    public ModelOctaneGas() {
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

    public double getCPV() {
        return Cpv;
    }

    public void setCPV(double Cpv) {
        this.Cpv = Cpv;
    }

    public double getPRV() {
        return Prv;
    }

    public void setPRV(double Prv) {
        this.Prv = Prv;
    }

    public double getKV() {
        return Kv;
    }

    public void setKV(double Kv) {
        this.Kv = Kv;
    }

    public double getMUV() {
        return Muv;
    }

    public void setMUV(double Muv) {
        this.Muv = Muv;
    }

    public double getVCV() {
        return Vcv;
    }

    public void setVCV(double Vcv) {
        this.Vcv = Vcv;
    }
}
