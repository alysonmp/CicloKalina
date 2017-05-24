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
@Table(name = "d4_liquido")
public class ModelD4Liquido implements Serializable{
    
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

    public ModelD4Liquido() {
    }
    
    public ModelD4Liquido(double pressao, double temperatura, double Cpl, double Prl) {
        this.pressao = pressao;
        this.temperatura = temperatura;
        this.Cpl = Cpl;
        this.Prl = Prl;
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
