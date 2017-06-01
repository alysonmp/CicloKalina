/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class ControlExergia {
    
    private double To, Po;
    private double E1, E2, E3, E4, E5, E6, EXevp, EXcon;
    
    public ControlExergia(double H1, double H2, double H3, double H4, double H5, double H6, double S1, double S2, double S3, double S4, double S5, double S6, double m, double Pref, double Tref, double ii, double Qcon, double Qevp, double mf, double Tf, double Tfout, double[] X, double FON, double Pf, Session session){
        To=25+273.15;
        Po=101.325;
        double Ten, Pen, Ts, Ps, mH2O, EH2Oe, EH2Os;
        double HLo, HVo, SLo, SVo, HLen, HVen, SLen, SVen, HLs, HVs, SLs, SVs;
        
        ControlH_Sistema h_Sistema = new ControlH_Sistema(To, Po, Pref, Tref, 0, session);
        HLo  = h_Sistema.getHL();
        HVo  = h_Sistema.getHV();
        ControlS_Sistema s_Sistema = new ControlS_Sistema(To, Po, Pref, Tref, 0, session);
        SLo = s_Sistema.getSL();
        SVo = s_Sistema.getSV();
        
        if(FON != 1){
            ControlExergia_Gases exergiaGasesIn = new ControlExergia_Gases(mf, Tf, X, session);
            //ControlExergia_Gases exergiaGasesOut = new ControlExergia_Gases(mf, Tfout, X, session);

            EXevp = exergiaGasesIn.getET()*mf;
        }else{
            Ten = Tf;
            Pen = Pf;
            Ts = Tfout;
            Ps = Pen;
            
            h_Sistema = new ControlH_Sistema(Ten, Pen, Pref, Tref, 0, session);
            HLen  = h_Sistema.getHL();
            HVen  = h_Sistema.getHV();
            s_Sistema = new ControlS_Sistema(Ten, Pen, Pref, Tref, 0, session);
            SLen = s_Sistema.getSL();
            SVen = s_Sistema.getSV();
            
            h_Sistema = new ControlH_Sistema(Ts, Ps, Pref, Tref, 0, session);
            HLs  = h_Sistema.getHL();
            HVs  = h_Sistema.getHV();
            s_Sistema = new ControlS_Sistema(Ts, Ps, Pref, Tref, 0, session);
            SLs = s_Sistema.getSL();
            SVs = s_Sistema.getSV();
            
            mH2O = mf;
            EH2Oe= mH2O*((HLen-HLo)-(To*(SLen-SLo)));
            EH2Os= mH2O*((HLs-HLo)-(To*(SLs-SLo)));
            
            EXevp=EH2Oe;
        }
        
        Ten = 25+273.15;
        Pen=(101.325)*1.2;
        Ts=35+273.15;
        Ps=Pen;
            
        h_Sistema = new ControlH_Sistema(Ten, Pen, Pref, Tref, 0, session);
        HLen  = h_Sistema.getHL();
        HVen  = h_Sistema.getHV();
        s_Sistema = new ControlS_Sistema(Ten, Pen, Pref, Tref, 0, session);
        SLen = s_Sistema.getSL();
        SVen = s_Sistema.getSV();
            
        h_Sistema = new ControlH_Sistema(Ts, Ps, Pref, Tref, 0, session);
        HLs  = h_Sistema.getHL();
        HVs  = h_Sistema.getHV();
        s_Sistema = new ControlS_Sistema(Ts, Ps, Pref, Tref, 0, session);
        SLs = s_Sistema.getSL();
        SVs = s_Sistema.getSV();
        
        mH2O = Qcon/(HLs-HLen);
        EH2Oe = mH2O*((HLen-HLo)-(To*(SLen-SLo)));
        EH2Os = mH2O*((HLs-HLo)-(To*(SLs-SLo)));

        EXcon=EH2Os-EH2Oe;
        
        E1= m*((H1-HLo)-(To*(S1-SLo)));
        E2= m*((H2-HLo)-(To*(S2-SLo)));
        E3= m*((H3-HLo)-(To*(S3-SLo)));
        E4= m*((H4-HLo)-(To*(S4-SLo)));
        E5= m*((H5-HLo)-(To*(S5-SLo)));
        E6= m*((H6-HLo)-(To*(S6-SLo)));

    }

    public double getTo() {
        return To;
    }

    public void setTo(double To) {
        this.To = To;
    }

    public double getPo() {
        return Po;
    }

    public void setPo(double Po) {
        this.Po = Po;
    }

    public double getE1() {
        return E1;
    }

    public void setE1(double E1) {
        this.E1 = E1;
    }

    public double getE2() {
        return E2;
    }

    public void setE2(double E2) {
        this.E2 = E2;
    }

    public double getE3() {
        return E3;
    }

    public void setE3(double E3) {
        this.E3 = E3;
    }

    public double getE4() {
        return E4;
    }

    public void setE4(double E4) {
        this.E4 = E4;
    }

    public double getE5() {
        return E5;
    }

    public void setE5(double E5) {
        this.E5 = E5;
    }

    public double getE6() {
        return E6;
    }

    public void setE6(double E6) {
        this.E6 = E6;
    }

    public double getEXevp() {
        return EXevp;
    }

    public void setEXevp(double EXevp) {
        this.EXevp = EXevp;
    }

    public double getEXcon() {
        return EXcon;
    }

    public void setEXcon(double EXcon) {
        this.EXcon = EXcon;
    }
    
    
    
}