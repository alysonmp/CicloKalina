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
public class ControlSaturacao {

    private double s3sv,s3sl, s4sl, s4sv, s1sv, s1sl, s6sl, s6sv, h3sv, h3sl, h4sl, h4sv, h1sv, h6sl, h1sl;
    
    public ControlSaturacao(double P4, double P1, double P6, double P3, double Pref, double Tref, int ii, Session session) {
        ControlT_Ref tRef = new ControlT_Ref(P3, ii, session);
        ControlS_Sistema sSistema = new ControlS_Sistema(tRef.getTref(), P3, Pref, Tref, ii, session);
        ControlH_Sistema hSistema = new ControlH_Sistema(tRef.getTref(), P3, Pref, Tref, ii, session);
        
        s3sl = sSistema.getSL();
        s3sv = sSistema.getSV();
        h3sl = hSistema.getHL();
        h3sv = hSistema.getHV();
        
        tRef = new ControlT_Ref(P4, ii, session);
        sSistema = new ControlS_Sistema(tRef.getTref(), P4, Pref, Tref, ii, session);
        hSistema = new ControlH_Sistema(tRef.getTref(), P4, Pref, Tref, ii, session);
        
        s4sl = sSistema.getSL();
        s4sv = sSistema.getSV();
        h4sl = hSistema.getHL();
        h4sv = hSistema.getHV();
        
        tRef = new ControlT_Ref(P1, ii, session);
        sSistema = new ControlS_Sistema(tRef.getTref(), P1, Pref, Tref, ii, session);
        hSistema = new ControlH_Sistema(tRef.getTref(), P1, Pref, Tref, ii, session);
        
        s1sl = sSistema.getSL();
        s1sv = sSistema.getSV();
        h1sl = hSistema.getHL();
        h1sv = hSistema.getHV();
        
        tRef = new ControlT_Ref(P6, ii, session);
        sSistema = new ControlS_Sistema(tRef.getTref(), P6, Pref, Tref, ii, session);
        hSistema = new ControlH_Sistema(tRef.getTref(), P6, Pref, Tref, ii, session);
        
        s6sl = sSistema.getSL();
        s6sv = sSistema.getSV();
        s6sl = hSistema.getHL();
        s6sv = hSistema.getHV();
    }

    public double getS3sv() {
        return s3sv;
    }

    public void setS3sv(double s3sv) {
        this.s3sv = s3sv;
    }

    public double getS3sl() {
        return s3sl;
    }

    public void setS3sl(double s3sl) {
        this.s3sl = s3sl;
    }

    public double getS4sl() {
        return s4sl;
    }

    public void setS4sl(double s4sl) {
        this.s4sl = s4sl;
    }

    public double getS4sv() {
        return s4sv;
    }

    public void setS4sv(double s4sv) {
        this.s4sv = s4sv;
    }

    public double getS1sv() {
        return s1sv;
    }

    public void setS1sv(double s1sv) {
        this.s1sv = s1sv;
    }

    public double getS1sl() {
        return s1sl;
    }

    public void setS1sl(double s1sl) {
        this.s1sl = s1sl;
    }

    public double getS6sl() {
        return s6sl;
    }

    public void setS6sl(double s6sl) {
        this.s6sl = s6sl;
    }

    public double getS6sv() {
        return s6sv;
    }

    public void setS6sv(double s6sv) {
        this.s6sv = s6sv;
    }

    public double getH3sv() {
        return h3sv;
    }

    public void setH3sv(double h3sv) {
        this.h3sv = h3sv;
    }

    public double getH3sl() {
        return h3sl;
    }

    public void setH3sl(double h3sl) {
        this.h3sl = h3sl;
    }

    public double getH4sl() {
        return h4sl;
    }

    public void setH4sl(double h4sl) {
        this.h4sl = h4sl;
    }

    public double getH4sv() {
        return h4sv;
    }

    public void setH4sv(double h4sv) {
        this.h4sv = h4sv;
    }

    public double getH1sv() {
        return h1sv;
    }

    public void setH1sv(double h1sv) {
        this.h1sv = h1sv;
    }

    public double getH6sl() {
        return h6sl;
    }

    public void setH6sl(double h6sl) {
        this.h6sl = h6sl;
    }

    public double getH1sl() {
        return h1sl;
    }

    public void setH1sl(double h1sl) {
        this.h1sl = h1sl;
    }

}
