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
 * @author leonardo
 */

@Entity
@Table (name = "etapa1")
public class ModelEtapa1 implements Serializable{

    @Id
    @GeneratedValue
    private int cod;
    
    @Column
    private double temperatura;
       
    @Column
    private double pressao;

    public ModelEtapa1(double temperatura, double pressao) {
        this.temperatura = temperatura;
        this.pressao = pressao;
    }

    public ModelEtapa1() {
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
    
    
    
    
    
}
