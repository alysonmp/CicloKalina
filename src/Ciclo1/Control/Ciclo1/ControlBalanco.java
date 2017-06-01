/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Ciclo1;

/**
 *
 * @author alysonmp
 */
public class ControlBalanco {
    
    private double Qcon, QLHR, QHHR, Wt, Wb, Wn;
    
    public ControlBalanco(double VF1, double m, double H2, double H4, double H7, double H8, double H9, double H10, double H12, double H11){
        Qcon = m*(H8-H9);
        QLHR = m*(H7-H8);
        QHHR = m*(H12-H11);
        Wt = m*VF1*(H2-H4);
        Wb = m*(H10-H9)*0.8;
        Wn = Wt*0.98-Wb;
    }

    public double getQcon() {
        return Qcon;
    }

    public void setQcon(double Qcon) {
        this.Qcon = Qcon;
    }

    public double getQLHR() {
        return QLHR;
    }

    public void setQLHR(double QLHR) {
        this.QLHR = QLHR;
    }

    public double getQHHR() {
        return QHHR;
    }

    public void setQHHR(double QHHR) {
        this.QHHR = QHHR;
    }

    public double getWt() {
        return Wt;
    }

    public void setWt(double Wt) {
        this.Wt = Wt;
    }

    public double getWb() {
        return Wb;
    }

    public void setWb(double Wb) {
        this.Wb = Wb;
    }

    public double getWn() {
        return Wn;
    }

    public void setWn(double Wn) {
        this.Wn = Wn;
    }   
}