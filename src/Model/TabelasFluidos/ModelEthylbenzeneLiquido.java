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
 * @author leonardo
 */
@Entity
@Table(name = "ethylbenzene_liquido") 
public class ModelEthylbenzeneLiquido implements Serializable{
    @Id
    @GeneratedValue
    private int cod;
    
    @Column
    private double pressao;
    
    @Column
    private double temperatura;
    
    @Column
    private double Titulol;
    
    @Column
    private double Cpl;
    
    @Column 
    private double Val; 
    
    @Column
    private double Vcl;

    public ModelEthylbenzeneLiquido(int cod, double pressao, double temperatura, double Titulol, double Cpl, double Val, double Vcl) {
        this.cod = cod;
        this.pressao = pressao;
        this.temperatura = temperatura;
        this.Titulol = Titulol;
        this.Cpl = Cpl;
        this.Val = Val;
        this.Vcl = Vcl;
    }

    public ModelEthylbenzeneLiquido() {
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

    public double getTITULOL() {
        return Titulol;
    }

    public void setTITULOL(double Titulol) {
        this.Titulol = Titulol;
    }

    public double getCPL() {
        return Cpl;
    }

    public void setCPL(double Cpl) {
        this.Cpl = Cpl;
    }

    public double getVAL() {
        return Val;
    }

    public void setVAL(double Val) {
        this.Val = Val;
    }

    public double getVCL() {
        return Vcl;
    }

    public void setVCL(double Vcl) {
        this.Vcl = Vcl;
    }
}
