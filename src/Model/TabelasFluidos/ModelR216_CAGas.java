/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.TabelasFluidos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author leonardo ModelR216_CAGas
 */
@Entity
@Table(name = "r216_ca_gas")
public class ModelR216_CAGas implements Serializable{
    @Id
    @GeneratedValue
    private int cod;
    
    @Column
    private double pressao;
    
    @Column
    private double temperatura;
    
    @Column
    private double Titulov;
    
    @Column
    private double Cpv;
    
    @Column 
    private double Vav; 
    
    @Column
    private double Vcv;

    public ModelR216_CAGas(int cod, double pressao, double temperatura, double Titulov, double Cpv, double Vav, double Vcv) {
        this.cod = cod;
        this.pressao = pressao;
        this.temperatura = temperatura;
        this.Titulov = Titulov;
        this.Cpv = Cpv;
        this.Vav = Vav;
        this.Vcv = Vcv;
    }

    public ModelR216_CAGas() {
    }

    public int getCOD() {
        return cod;
    }

    public void setCOD(int cod) {
        this.cod = cod;
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

    public double getTITULOV() {
        return Titulov;
    }

    public void setTITULOV(double Titulov) {
        this.Titulov = Titulov;
    }

    public double getCPV() {
        return Cpv;
    }

    public void setCPV(double Cpv) {
        this.Cpv = Cpv;
    }

    public double getVAV() {
        return Vav;
    }

    public void setVAV(double Vav) {
        this.Vav = Vav;
    }

    public double getVCV() {
        return Vcv;
    }

    public void setVCV(double Vcv) {
        this.Vcv = Vcv;
    }
}


