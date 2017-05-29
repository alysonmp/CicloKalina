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
 * @author alysonmp
 */
@Entity
@Table(name = "Pentane_liquido")
public class ModelPentaneLiquido implements Serializable{
    
    @Id
    @GeneratedValue
    private int ID;
    
    @Column
    private double pressao;
    
    @Column
    private double temperatura;
    
    @Column
    private double Cpl;
    
    @Column
    private double Prl;

    public ModelPentaneLiquido(double pressao, double temperatura) {
        this.pressao = pressao;
        this.temperatura = temperatura;
    }

    public ModelPentaneLiquido(double pressao, double temperatura, double Cpl, double Prl) {
        this.pressao = pressao;
        this.temperatura = temperatura;
        this.Cpl = Cpl;
        this.Prl = Prl;
    }

    public ModelPentaneLiquido() {
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
}
