package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "core")
public class ModelCore {
    @Id
    @GeneratedValue
    private int cod;
    
    @Column
    private double Dh;

    @Column
    private double alp;
    
    @Column
    private double del;
    
    @Column
    private double gam;
    
    @Column
    private double b;
    
    @Column
    private double t;
    
    @Column
    private double l;
    
    @Column
    private double s;
    
    @Column
    private double bet;
    
    @Column
    private double por;
    
    public ModelCore() {
    }

    public ModelCore(double Dh, double alp, double del, double gam, double b, double t, double l, double s, double bet, double por) {
        this.Dh = Dh;
        this.alp = alp;
        this.del = del;
        this.gam = gam;
        this.b = b;
        this.t = t;
        this.l = l;
        this.s = s;
        this.bet = bet;
        this.por = por;
    }
    
    public int getNome() {
        return cod;
    }

    public void setNome(int cod) {
        this.cod = cod;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public double getDh() {
        return Dh;
    }

    public void setDh(double Dh) {
        this.Dh = Dh;
    }

    public double getAlp() {
        return alp;
    }

    public void setAlp(double alp) {
        this.alp = alp;
    }

    public double getDel() {
        return del;
    }

    public void setDel(double del) {
        this.del = del;
    }

    public double getGam() {
        return gam;
    }

    public void setGam(double gam) {
        this.gam = gam;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        this.l = l;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public double getPor() {
        return por;
    }

    public void setPor(double por) {
        this.por = por;
    }

    
}
