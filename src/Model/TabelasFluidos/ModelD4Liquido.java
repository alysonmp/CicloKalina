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
@Table(name = "D4")
public class ModelD4Liquido implements Serializable{
    
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

    public ModelD4Liquido() {
    }
    
    public ModelD4Liquido(double pressao, double temperatura, double variavel1, double variavel2) {
        this.pressao = pressao;
        this.temperatura = temperatura;
        this.variavel1 = variavel1;
        this.variavel2 = variavel2;
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
}
