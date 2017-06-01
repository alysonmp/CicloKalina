/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Ciclo1;

import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class ControlParametros {
    
    private double Tcon, TbolA, yii, TorvA, xii, Pcon, TbolB, TorvB, Beff, Pref, Tref, T1max, Teff;
    
    public ControlParametros(double P1, double Tf, double Pf, double zi, double Tres, Session session){
        Tcon = Tres+15;
        ControlPBolha pBolha = new ControlPBolha(Tcon, zi, session);
        Pcon = pBolha.getPbol();
        
        ControlTBolha tBolha = new ControlTBolha(P1, zi, session);
        TbolA = tBolha.getTbol();
        yii = tBolha.getYi();
        
        ControlTorvalho torvalho = new ControlTorvalho(P1, zi, session);
        TorvA = torvalho.getTorv();
        xii = torvalho.getXi();
        
        tBolha = new ControlTBolha(Pcon, zi, session);
        TbolB = tBolha.getTbol();
        yii = tBolha.getYi();
        
        torvalho = new ControlTorvalho(Pcon, zi, session);
        TorvB = torvalho.getTorv();
        xii = torvalho.getXi();
        
        Beff = 0.8;
        Pref = 5000/100;
        Tref = 273.15;
        T1max = Tf-5;
        Teff = 0.8;
    }

    public double getTcon() {
        return Tcon;
    }

    public void setTcon(double Tcon) {
        this.Tcon = Tcon;
    }

    public double getTbolA() {
        return TbolA;
    }

    public void setTbolA(double TbolA) {
        this.TbolA = TbolA;
    }

    public double getTorvA() {
        return TorvA;
    }

    public void setTorvA(double TorvA) {
        this.TorvA = TorvA;
    }

    public double getPcon() {
        return Pcon;
    }

    public void setPcon(double Pcon) {
        this.Pcon = Pcon;
    }

    public double getTbolB() {
        return TbolB;
    }

    public void setTbolB(double TbolB) {
        this.TbolB = TbolB;
    }

    public double getTorvB() {
        return TorvB;
    }

    public void setTorvB(double TorvB) {
        this.TorvB = TorvB;
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

    public double getT1max() {
        return T1max;
    }

    public void setT1max(double T1max) {
        this.T1max = T1max;
    }

    public double getTeff() {
        return Teff;
    }

    public void setTeff(double Teff) {
        this.Teff = Teff;
    }
}