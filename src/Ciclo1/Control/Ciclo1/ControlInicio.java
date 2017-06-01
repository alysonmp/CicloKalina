/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Ciclo1;

import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlInicio {
        
    private double P1 = 35,PP = 4, SUBT = 5, Tf = 408.35, Tres = 298.15, 
            effLt = 0.32, zi = 0.75, M = 1,sup=3,VAR = 0.5;
    
    private int compressor = 4;
    
    private double TbolA, TbolB, TorvA, TorvB, Teff, T1max, Tcon, Pcon, Beff, Pref, 
            Tref, DT1, P2, Pf;
        
    public ControlInicio(Session session) {
        
        P2=P1;
        Pf=4000/100 ;   

        ControlParametros p = new ControlParametros(P1, Tf, Pf, zi, Tres, session);

        TbolA = p.getTbolA();
        TbolB = p.getTbolB();
        TorvA = p.getTorvA();
        TorvB = p.getTorvB();
        Teff = p.getTeff();
        T1max = p.getT1max();
        Tcon = p.getTcon();
        Pcon = p.getPcon();
        Beff = p.getBeff();
        Pref = p.getPref();
        Tref = p.getTref();


        DT1 = (TorvA - TbolA);
    }

    public double getP1() {
        return P1;
    }

    public double getTorvB() {
        return TorvB;
    }

    public void setTorvB(double TorvB) {
        this.TorvB = TorvB;
    }

    public double getP2() {
        return P2;
    }

    public void setP2(double P2) {
        this.P2 = P2;
    }

    public double getPf() {
        return Pf;
    }

    public void setPf(double Pf) {
        this.Pf = Pf;
    }

    public void setP1(double P1) {
        this.P1 = P1;
    }

    public double getPP() {
        return PP;
    }

    public void setPP(double PP) {
        this.PP = PP;
    }

    public double getSUBT() {
        return SUBT;
    }

    public void setSUBT(double SUBT) {
        this.SUBT = SUBT;
    }

    public double getTf() {
        return Tf;
    }

    public void setTf(double Tf) {
        this.Tf = Tf;
    }

    public double getTres() {
        return Tres;
    }

    public void setTres(double Tres) {
        this.Tres = Tres;
    }

    public double getEffLt() {
        return effLt;
    }

    public void setEffLt(double effLt) {
        this.effLt = effLt;
    }

    public double getZi() {
        return zi;
    }

    public void setZi(double zi) {
        this.zi = zi;
    }

    public double getVAR() {
        return VAR;
    }

    public void setVAR(double VAR) {
        this.VAR = VAR;
    }

    public int getCompressor() {
        return compressor;
    }

    public void setCompressor(int compressor) {
        this.compressor = compressor;
    }

    public double getDT1() {
        return DT1;
    }

    public void setDT1(double DT1) {
        this.DT1 = DT1;
    }

    public double getTbolA() {
        return TbolA;
    }

    public void setTbolA(double TbolA) {
        this.TbolA = TbolA;
    }

    public double getTbolB() {
        return TbolB;
    }

    public void setTbolB(double TbolB) {
        this.TbolB = TbolB;
    }

    public double getTorvA() {
        return TorvA;
    }

    public void setTorvA(double TorvA) {
        this.TorvA = TorvA;
    }

    public double getTeff() {
        return Teff;
    }

    public void setTeff(double Teff) {
        this.Teff = Teff;
    }

    public double getT1max() {
        return T1max;
    }

    public void setT1max(double T1max) {
        this.T1max = T1max;
    }

    public double getTcon() {
        return Tcon;
    }

    public void setTcon(double Tcon) {
        this.Tcon = Tcon;
    }

    public double getPcon() {
        return Pcon;
    }

    public void setPcon(double Pcon) {
        this.Pcon = Pcon;
    }

    public double getBeff() {
        return Beff;
    }

    public void setBeff(double Beff) {
        this.Beff = Beff;
    }

    public double getPref() {
        return Pref;
    }

    public void setPref(double Pref) {
        this.Pref = Pref;
    }

    public double getTref() {
        return Tref;
    }

    public void setTref(double Tref) {
        this.Tref = Tref;
    }
    
}
