/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.Model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
/**
 *
 * @author leonardo
 */
@Entity
@Table(name = "lateral")
public class ModelLateral implements Serializable{

    @Id
    @GeneratedValue
    private int ID;

    @Column
    private double massa;
    
    @Column
    private double temperatura;
    
    @Column
    private double pressao;
    
    @Column
    private double tempCond;
    
    @Column
    private double efetividade;
    
    @Column
    private double Superaquecimento;
    
    @Column
    private double pinch;
    
    @Column
    private String tipoMassa;
    
    @Column 
    private String tipoTemp;
    
    @Column
    private String tipoPressao;
    
    @Column
    private String tipoTempCond;

    public ModelLateral(double massa, double temperatura, double pressao, double tempCond, double efetividade, double Superaquecimento, double pinch, String tipoMassa, String tipoTemp, String tipoPressao, String tipoTempCond) {
        this.massa = massa;
        this.temperatura = temperatura;
        this.pressao = pressao;
        this.tempCond = tempCond;
        this.efetividade = efetividade;
        this.Superaquecimento = Superaquecimento;
        this.pinch = pinch;
        this.tipoMassa = tipoMassa;
        this.tipoTemp = tipoTemp;
        this.tipoPressao = tipoPressao;
        this.tipoTempCond = tipoTempCond;
    }

    public ModelLateral() {
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getMassa() {
        return massa;
    }

    public void setMassa(double massa) {
        this.massa = massa;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getPressao() {
        return pressao;
    }

    public void setPressao(double pressao) {
        this.pressao = pressao;
    }

    public double getTempCond() {
        return tempCond;
    }

    public void setTempCond(double tempCond) {
        this.tempCond = tempCond;
    }

    public double getEfetividade() {
        return efetividade;
    }

    public void setEfetividade(double efetividade) {
        this.efetividade = efetividade;
    }

    public double getSuperaquecimento() {
        return Superaquecimento;
    }

    public void setSuperaquecimento(double Superaquecimento) {
        this.Superaquecimento = Superaquecimento;
    }

    public double getPinch() {
        return pinch;
    }

    public void setPinch(double pinch) {
        this.pinch = pinch;
    }

    public String getTipoMassa() {
        return tipoMassa;
    }

    public void setTipoMassa(String tipoMassa) {
        this.tipoMassa = tipoMassa;
    }

    public String getTipoTemp() {
        return tipoTemp;
    }

    public void setTipoTemp(String tipoTemp) {
        this.tipoTemp = tipoTemp;
    }

    public String getTipoPressao() {
        return tipoPressao;
    }

    public void setTipoPressao(String tipoPressao) {
        this.tipoPressao = tipoPressao;
    }

    public String getTipoTempCond() {
        return tipoTempCond;
    }

    public void setTipoTempCond(String tipoTempCond) {
        this.tipoTempCond = tipoTempCond;
    }
}
