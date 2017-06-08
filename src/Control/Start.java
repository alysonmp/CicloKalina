/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.Ciclo2.ControlAreas;
import Control.Ciclo2.ControlBalanco;
import Control.Ciclo2.ControlBomba;
import Control.Ciclo2.ControlDiamTH17;
import Control.Ciclo2.ControlMassa;
import Control.Ciclo2.ControlParametros;
import Control.Ciclo2.ControlRegenerador;
import Control.Ciclo2.ControlSF;
import Control.Ciclo2.ControlT_Ref;
import Control.Ciclo2.ControlTurbina;
import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class Start {
    
    private double mf, P1, P2, T1, P4, T4, P5, P3, P6, Tcontrol, Tcontrol2, H1, H2, S1, S2, T2, H2s, DH2s, sp, v2, DHT, S4, S5, H4, H5, T5, S3, H3, T3, S6, T6, H6, IHR, Q, Tfout, PP, Hlat, Hsen, Hsup, T1s, Wt, Wb, Qevp, Qcon, ec, Qreg, Qreg1, Wn, Acon, Aevp, Areg, AT, ecg, Dr, m, Beff, eff;
    private int ii;
    private Session session;
    ControlPrincipal ctrlPrincipal;
    
    public Start(int compressor, int flu, double Tf, double Pf, double SUP, double PINCH, double Tconop, double eff, Session session, ControlPrincipal ctrlPrincipal){
        
        this.ctrlPrincipal = ctrlPrincipal;
        this.eff = eff;
        
        //flu=14;
        //Tf=142.1+273.15;
        //compressor=1;
        
        ControlParametros parametros = new ControlParametros(Tf, flu, session);
        double Pe = parametros.getPe();
        double Te = parametros.getTe();
        double Pconop = parametros.getPconop();
        double Pref = parametros.getPref();
        double Tref = parametros.getTref();
        double Tcri = parametros.getTcri();
        double Pcri = parametros.getPcri();

        double G = 1;
        Beff = 0.8;
        double Teff = 0.8;
        
        this.session = session;
        if(compressor == 5){
            mf = 2.8400; //%kmol/s
        }else{
            mf = 1.5375; //%kmol/s
        }
        
        P1 = Pe*0.8;
        P2 = Pconop;
        ii = flu;
        
        ControlT_Ref TRef = new ControlT_Ref(P1, ii, session);
        double Tee = TRef.getTref();
        T1 = Tee+SUP;
        P2 = Pconop; //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%Pcon È variavel
        P4 = P2;
        T4 = Tconop;
        P5 = P1;
        P3 = P2;
        P6 = P1;
        Tcontrol=Tf-T1;
        
        if(Tcontrol < 9.5){
            //jkljkljkljkljkl*jkljkljkjkl
        }

        Tcontrol2 = Tcri-T1;
        if(Tcontrol2 <= 2){ 
           //jkljkljkljkljkl*jkljkljkjkl
        }
        
        if(P1 < 303.1){
            //jkljkljkljkljkl*jkljkljkjkl
        }
        
        ControlTurbina turbina = new ControlTurbina(Teff, P1, T1, P2, Pref, Tref, ii, session);
        H1 = turbina.getH1();
        H2 = turbina.getIsoTurbina().getH2();
        S1 = turbina.getS1();
        S2 = turbina.getIsoTurbina().getS2();
        T2 = turbina.getIsoTurbina().getT2();
        H2s = turbina.getIsoTurbina().getH2s();

        
        //QUANDO FOR AJUSTAR INTERFACE GRÁFICA, CHAMAR DIRETAMENTE A INTERPOLAÇÃO DE VAPOR OU LIQUIDO, TRECHOS 1, 2 E 3, SEMPRE VAPOR, TRECHOS 4, 5 E 6, SEMPRE LIQUIDO
        /*ControlInterpolacao interpolacao = new ControlInterpolacao(ii, Pref, Tref, session);
        double Cpv_g = interpolacao.getCpv_g();
        double Cpv_l = interpolacao.getCpv_l();
        double kv_g = interpolacao.getKv_g();
        double kv_l = interpolacao.getKv_l();
        double Muv_g = interpolacao.getMuv_g();
        double Muv_l = interpolacao.getMuv_l();
        double Prv_g = interpolacao.getPrv_g();
        double Prv_l = interpolacao.getPrv_l();
        double Vcv_g = interpolacao.getVcv_g();
        double Vcv_l = interpolacao.getVcv_l();*/
        
        DH2s = H1-H2s;
        
        /*ControlSF sf = new ControlSF(T2, P2, ii, m, DH2s, session);
        sp = sf.getSp();
        v2 = sf.getV2();
        DHT = sf.getDHT();
        
        ControlDiamTH17 diamTH17 = new ControlDiamTH17(v2, DHT, session);
        Dr = diamTH17.getDr();
        Teff = diamTH17.getTeff();
                
        if(Teff < 0.8){
            turbina = new ControlTurbina(Teff, P1, T1, P2, Pref, Tref, ii, session);
            H1 = turbina.getH1();
            H2 = turbina.getIsoTurbina().getH2();
            S1 = turbina.getS1();
            S2 = turbina.getIsoTurbina().getS2();
            T2 = turbina.getIsoTurbina().getT2();
            H2s = turbina.getIsoTurbina().getH2s();
            
            DH2s=H1-H2s;
            
            sf = new ControlSF(T2, P2, ii, m, DH2s, session);
            sp = sf.getSp();
            v2 = sf.getV2();
            DHT = sf.getDHT();
            
            diamTH17 = new ControlDiamTH17(v2, DHT, session);
            Dr = diamTH17.getDr();
            Teff = diamTH17.getTeff();
        }*/
                    
        ControlBomba bomba = new ControlBomba(Beff, P1, T1, P4, T4, Pref, Tref, ii, session);
        S4 = bomba.getS4();
        H4 = bomba.getH4();
        S5 = bomba.getS5();
        H5 = bomba.getH5();
        T5 = bomba.getT5();

        ControlRegenerador regenerador = new ControlRegenerador(G, H2, H5, S2, S5, P2, T2, P5, T5, P1, Pconop, Tconop, Pref, Tref, ii, this.eff, session);
        S3 = regenerador.getS3();
        H3 = regenerador.getH3();
        S6 = regenerador.getS6();
        H6 = regenerador.getH6();
        T3 = regenerador.getT3();
        T6 = regenerador.getT6();
        IHR = regenerador.getIHR();
        this.eff = IHR;

        ControlMassa massa = new ControlMassa(H4, H1, H6, P1, ii, Pref, Tref, T1, T6, SUP, PINCH, mf, Tf, Pf, compressor, session);
        m = massa.getM();
        Q = massa.getQ();
        Tfout = massa.getTfout();
        Hlat = massa.getHlat();
        Hsen = massa.getHsen();
        Hsup = massa.getHsup();
        T1s = massa.getT1s();

        ControlSF sf = new ControlSF(T2, P2, ii, m, DH2s, session);
        sp = sf.getSp();
        v2 = sf.getV2();
        DHT = sf.getDHT();

        ControlDiamTH17 diamTH17 = new ControlDiamTH17(v2, DHT, session);
        Dr = diamTH17.getDr();
        Teff = diamTH17.getTeff();
        
        ControlBalanco balanco = new ControlBalanco(T1, H1, H2, H3, H4, H5, H6, S1, S2, S3, S4, S5, S6, m, Pref, Tref);
        Wt = balanco.getWt();
        Wb = balanco.getWb();
        Qevp = balanco.getQevp();
        Qcon = balanco.getQcon();
        ec = balanco.getEc();
        Qreg = balanco.getQreg();
        Qreg1 = balanco.getQreg1();
        Wn = balanco.getWn();

        ControlAreas areas = new ControlAreas(T1, T2, T3, T4, T5, T6, Tf, Tfout, Qevp, Qcon, Qreg, this.eff, Hlat, Hsen, Hsup, T1s, PP, SUP, m, Pref, Tref, P4, ii, H3, compressor, session);
        Acon = areas.getAcon();
        Aevp = areas.getAevp();
        Areg = areas.getAreg();

        AT = Acon+Aevp+Areg;
        ecg = ec*100;
    }   

    public double getP1() {
        return P1;
    }

    public void setP1(double P1) {
        this.P1 = P1;
    }

    public double getP2() {
        return P2;
    }

    public void setP2(double P2) {
        this.P2 = P2;
    }

    public double getT1() {
        return T1;
    }

    public void setT1(double T1) {
        this.T1 = T1;
    }

    public double getP4() {
        return P4;
    }

    public void setP4(double P4) {
        this.P4 = P4;
    }

    public double getT4() {
        return T4;
    }

    public void setT4(double T4) {
        this.T4 = T4;
    }

    public double getP5() {
        return P5;
    }

    public void setP5(double P5) {
        this.P5 = P5;
    }

    public double getP3() {
        return P3;
    }

    public void setP3(double P3) {
        this.P3 = P3;
    }

    public double getP6() {
        return P6;
    }

    public void setP6(double P6) {
        this.P6 = P6;
    }

    public double getH1() {
        return H1;
    }

    public void setH1(double H1) {
        this.H1 = H1;
    }

    public double getH2() {
        return H2;
    }

    public void setH2(double H2) {
        this.H2 = H2;
    }

    public double getS1() {
        return S1;
    }

    public void setS1(double S1) {
        this.S1 = S1;
    }

    public double getS2() {
        return S2;
    }

    public void setS2(double S2) {
        this.S2 = S2;
    }

    public double getT2() {
        return T2;
    }

    public void setT2(double T2) {
        this.T2 = T2;
    }

    public double getS4() {
        return S4;
    }

    public void setS4(double S4) {
        this.S4 = S4;
    }

    public double getS5() {
        return S5;
    }

    public void setS5(double S5) {
        this.S5 = S5;
    }

    public double getH4() {
        return H4;
    }

    public void setH4(double H4) {
        this.H4 = H4;
    }

    public double getH5() {
        return H5;
    }

    public void setH5(double H5) {
        this.H5 = H5;
    }

    public double getT5() {
        return T5;
    }

    public void setT5(double T5) {
        this.T5 = T5;
    }

    public double getS3() {
        return S3;
    }

    public void setS3(double S3) {
        this.S3 = S3;
    }

    public double getH3() {
        return H3;
    }

    public void setH3(double H3) {
        this.H3 = H3;
    }

    public double getT3() {
        return T3;
    }

    public void setT3(double T3) {
        this.T3 = T3;
    }

    public double getS6() {
        return S6;
    }

    public void setS6(double S6) {
        this.S6 = S6;
    }

    public double getT6() {
        return T6;
    }

    public void setT6(double T6) {
        this.T6 = T6;
    }

    public double getH6() {
        return H6;
    }

    public void setH6(double H6) {
        this.H6 = H6;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getBeff() {
        return Beff;
    }

    public void setBeff(double Beff) {
        this.Beff = Beff;
    }

    public double getQcon() {
        return Qcon;
    }

    public void setQcon(double Qcon) {
        this.Qcon = Qcon;
    }

    public double getEff() {
        return eff;
    }

    public void setEff(double eff) {
        this.eff = eff;
    }
}