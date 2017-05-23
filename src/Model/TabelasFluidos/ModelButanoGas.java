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
@Table(name = "butano_gas")
public class ModelButanoGas implements Serializable{

    @Id
    @GeneratedValue
    private int ID;

    @Column
    private double pressao;
    
    @Column
    private double temperatura;
    
    @Column
    private double variavel1;
    
    @Column
    private double variavel2;
    
    @Column
    private double variavel3;
    
    @Column
    private double variavel4;

    @Column
    private double variavel5;
    
    public ModelButanoGas() {
    }

    public ModelButanoGas(double pressao, double temperatura, double variavel1, double variavel2, double variavel3, double variavel4) {
        this.pressao = pressao;
        this.temperatura = temperatura;
        this.variavel1 = variavel1;
        this.variavel2 = variavel2;
        this.variavel3 = variavel3;
        this.variavel4 = variavel4;
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

    public double getVARIAVEL1() {
        return variavel1;
    }

    public void setVARIAVEL1(double variavel1) {
        this.variavel1 = variavel1;
    }

    public double getVARIAVEL2() {
        return variavel2;
    }

    public void setVARIAVEL2(double variavel1) {
        this.variavel2 = variavel1;
    }
    
    public double getVARIAVEL3() {
        return variavel3;
    }

    public void setVARIAVEL3(double variavel1) {
        this.variavel3 = variavel1;
    }
    
    public double getVARIAVEL4() {
        return variavel4;
    }

    public void setVARIAVEL4(double variavel1) {
        this.variavel4 = variavel1;
    }
    
    public double getVARIAVEL5() {
        return variavel5;
    }

    public void setVARIAVEL5(double variavel1) {
        this.variavel5 = variavel1;
    }
}
