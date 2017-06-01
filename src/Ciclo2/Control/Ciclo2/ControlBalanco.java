/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.Control.Ciclo2;

/**
 *
 * @author leonardo
 */
public class ControlBalanco {
    private double Wt, Wb, Qevp, Qcon, ec, Qreg, Qreg1, Wn;

    public ControlBalanco(double T1, double H1, double H2, double H3, double H4, double H5, double H6, double S1, double S2, double S3, double S4, double S5, double S6, double m, double Pref, double Tref ) {
        Wt = m*(H1-H2);
        Wb = m*(H5-H4);
        Wn = (Wt*0.98)-Wb;
        Qevp=m*(H1-H6);
        Qreg=m*(H2-H3);
        Qreg1=m*(H6-H5);
        Qcon=m*(H3-H4);
        ec=(((H1-H2)*.98)-(H5-H4))/((H1-H6)/0.8); 
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

    public double getQevp() {
        return Qevp;
    }

    public void setQevp(double Qevp) {
        this.Qevp = Qevp;
    }

    public double getQcon() {
        return Qcon;
    }

    public void setQcon(double Qcon) {
        this.Qcon = Qcon;
    }

    public double getEc() {
        return ec;
    }

    public void setEc(double ec) {
        this.ec = ec;
    }

    public double getQreg() {
        return Qreg;
    }

    public void setQreg(double Qreg) {
        this.Qreg = Qreg;
    }

    public double getQreg1() {
        return Qreg1;
    }

    public void setQreg1(double Qreg1) {
        this.Qreg1 = Qreg1;
    }

    public double getWn() {
        return Wn;
    }

    public void setWn(double Wn) {
        this.Wn = Wn;
    }
    
    
}
