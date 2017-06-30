/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.Ciclo2.ControlAreas;
import Control.Ciclo2.ControlBalanco;
import Control.Ciclo2.ControlBomba;
import Control.Ciclo2.ControlConeff;
import Control.Ciclo2.ControlDiamTH17;
import Control.Ciclo2.ControlEvpeff;
import Control.Ciclo2.ControlMassa;
import Control.Ciclo2.ControlParametros;
import Control.Ciclo2.ControlRegeff;
import Control.Ciclo2.ControlRegenerador;
import Control.Ciclo2.ControlSF;
import Control.Ciclo2.ControlT_Ref;
import Control.Ciclo2.ControlTurbina;
import Model.Ciclo2.ModelBomba;
import Util.DropdownComboBox;
import View.Bomba.ViewBombaPanelRankine;
import java.awt.Component;
import java.util.List;
import java.util.Vector;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author alysonmp
 */
public class Start {
    
    private double mf, P1, P2, T1, P4, T4, P5, P3, P6, Tcontrol, Tcontrol2, H1, H2, S1, S2, T2, H2s, DH2s, sp, v2, DHT, S4, S5, H4, H5, T5, S3, H3, T3, S6, T6, H6, IHR, Q, Tfout, PP, Hlat, Hsen, Hsup, T1s, Wt, Wb, Qevp, Qcon, ec, Qreg, Qreg1, Wn, Acon, Aevp, Areg, AT, ecg, Dr, m, Beff, eff, UASUP, UALAT, UASEN, UAREG, UACONl, UACONs, Ten, Pen, Ten1, Ts, mH2O;
    private double ATreg, Ahoreg, Acoreg, Vreg, Lhreg, Lcreg, L3reg, DPhreg, DPcreg, ATcon, Ahocon, Acocon, Vcon, Lhcon, Lccon, L3con, DPhcon, DPccon, ATevp, Ahoevp, Acoevp, Vevp, Lhevp, Lcevp, L3evp, DPhevp, DPcevp; 
    private int ii;
    private Session session;
    ControlPrincipal ctrlPrincipal;
    String mensagem;
    
    public Start(Session session, ControlPrincipal ctrlPrincipal){
        this.session = session;
        this.ctrlPrincipal = ctrlPrincipal;
    }
    
    //public Start(int compressor, int flu, double Tf, double Pf, double SUP, double PINCH, double Tconop, double eff, double Ve, Session session, ControlPrincipal ctrlPrincipal){
    public void iniciaCalculos(int compressor, int flu, double Tf, double Pf, double mf, double SUP, double PINCH, double Tconop, double eff, double km, int FON){
        double Ve = 0.6;
        this.ctrlPrincipal = ctrlPrincipal;
        this.eff = eff;
        mensagem = "";
        
        //flu=14;
        //Tf=142.1+273.15;
        //compressor=1;
        
        ControlParametros parametros = new ControlParametros(Tf, flu, session);
        if(!parametros.getMensagem().equals("")){
            mensagem = parametros.getMensagem();
            return;
        }
        
        double Pe = parametros.getPe();
        double Te = parametros.getTe();
        Tconop = parametros.getTconop();
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
        
        P1 = Pe*Ve;
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
        
        Tcontrol = Tf-T1;
        if(Tcontrol < 5){
            mensagem = "Pressão ou temperatura de vaporização elevadas \nou temperatura de superaquecimento elevada.";
            return;
        }

        /*Tcontrol2 = Tcri-T1;
        if(Tcontrol2 <= 2){ 
           //jkljkljkljkljkl*jkljkljkjkl
        }*/
        
        if(P1 < 200){
            mensagem = "Baixa pressão de vaporização.";
            return;
        }
        
        ControlTurbina turbina = new ControlTurbina(Teff, P1, T1, P2, Pref, Tref, ii, session);
        if(!turbina.getMensagem().equals("")){
            mensagem = turbina.getMensagem();
            return;
        }
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
        if(!regenerador.getMensagem().equals("")){
            mensagem = regenerador.getMensagem();
            return;
        }
        S3 = regenerador.getS3();
        H3 = regenerador.getH3();
        S6 = regenerador.getS6();
        H6 = regenerador.getH6();
        T3 = regenerador.getT3();
        T6 = regenerador.getT6();
        IHR = regenerador.getIHR();
        this.eff = IHR;

        ControlMassa massa = new ControlMassa(H4, H1, H6, P1, ii, Pref, Tref, T1, T6, SUP, PINCH, mf, Tf, Pf, compressor, session);
        if(!massa.getMensagem().equals("")){
            mensagem = massa.getMensagem();
            return;
        }
        m = massa.getM();
        Q = massa.getQ();
        Tfout = massa.getTfout();
        Hlat = massa.getHlat();
        Hsen = massa.getHsen();
        Hsup = massa.getHsup();
        T1s = massa.getT1s();
        PP = massa.getPINCH();
        
        ControlSF sf = new ControlSF(T2, P2, ii, m, H2s, session);
        sp = sf.getSp();
        v2 = sf.getV2();
        DHT = sf.getDHT();

        //ControlDiamTH17 diamTH17 = new ControlDiamTH17(v2, DHT, session);
        //Dr = diamTH17.getDr();
        //Teff = diamTH17.getTeff();
        
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
        if(!areas.getMensagem().equals("")){
            mensagem = areas.getMensagem();
            return;
        }
        Acon = areas.getAcon();
        Aevp = areas.getAevp();
        Areg = areas.getAreg();
        
        AT = Acon+Aevp+Areg;
        ecg = ec*100;
        
        ControlEvpeff evpeff = new ControlEvpeff(P1, P6, T1, T6, ii, SUP, Hlat, Hsen, Hsup, compressor, m, mf, T1s, Tf, Tfout, Pf, FON, PINCH, UASUP, UALAT, UASEN, km, session);
        ATevp = evpeff.getAT();
        Ahoevp = evpeff.getAho();
        Acoevp = evpeff.getAco();
        Vevp = evpeff.getVhx();
        Lhevp = evpeff.getLh();
        Lcevp = evpeff.getLc();
        L3evp = evpeff.getL3();
        DPhevp = evpeff.getDPh();
        DPcevp = evpeff.getDPc();

        ControlConeff coneff = new ControlConeff(P4, P3, m, mH2O, ii, Ten, Ten1, Ts, T3, T4, UACONs, UACONl, Pen, km, session);
        ATcon = coneff.getAT();
        Ahocon = coneff.getAho();
        Acocon = coneff.getAco();
        Vcon = coneff.getVhx();
        Lhcon = coneff.getLh();
        Lccon = coneff.getLc();
        L3con = coneff.getL3();
        DPhcon = coneff.getDph();
        DPccon = coneff.getDpc();
                
        ControlRegeff regeff = new ControlRegeff(ii, UAREG, T2, T3, T5, T6, m, eff, P5, P6, P2, P3, km, session);
        ATreg = regeff.getAT();
        Ahoreg = regeff.getAho();
        Acoreg = regeff.getAco();
        Vreg = regeff.getVhx();
        Lhreg = regeff.getLh();
        Lcreg = regeff.getLc();
        L3reg = regeff.getL3();
        DPhreg = regeff.getDPh();
        DPcreg = regeff.getDPc();
    }   
    
    public void autalizaFieldsTemp(DropdownComboBox field){
        if(field.getName().equals("tempSaiEvaporador")){
            T1 = Double.parseDouble(field.getSelectedItem().toString());
            ControlAtualizaFieldsTemp atualiza = new ControlAtualizaFieldsTemp(session, ctrlPrincipal, T1, 1);
        }
        
        if(field.getName().equals("tempEntrTurbina")){
            T1 = Double.parseDouble(field.getSelectedItem().toString());
            ControlAtualizaFieldsTemp atualiza = new ControlAtualizaFieldsTemp(session, ctrlPrincipal, T1, 2);
        }
        
        if(field.getName().equals("tempSaiTurbina")){
            T2 = Double.parseDouble(field.getSelectedItem().toString());
            ControlAtualizaFieldsTemp atualiza = new ControlAtualizaFieldsTemp(session, ctrlPrincipal, T2, 3);
        }
        
        if(field.getName().equals("tempEntrRegenerador")){
            T2 = Double.parseDouble(field.getSelectedItem().toString());
            ControlAtualizaFieldsTemp atualiza = new ControlAtualizaFieldsTemp(session, ctrlPrincipal, T2, 4);
        }
        
        if(field.getName().equals("tempSaiRegenerador")){
            T3 = Double.parseDouble(field.getSelectedItem().toString());
            ControlAtualizaFieldsTemp atualiza = new ControlAtualizaFieldsTemp(session, ctrlPrincipal, T3, 5);
        }
        
        if(field.getName().equals("tempEntrCondensador")){
            T3 = Double.parseDouble(field.getSelectedItem().toString());
            ControlAtualizaFieldsTemp atualiza = new ControlAtualizaFieldsTemp(session, ctrlPrincipal, T3, 6);
        }
        
        if(field.getName().equals("tempSaiCondensador")){
            T4 = Double.parseDouble(field.getSelectedItem().toString());
            ControlAtualizaFieldsTemp atualiza = new ControlAtualizaFieldsTemp(session, ctrlPrincipal, T4, 7);
        }
        
        if(field.getName().equals("tempEntrBomba")){
            T4 = Double.parseDouble(field.getSelectedItem().toString());
            ControlAtualizaFieldsTemp atualiza = new ControlAtualizaFieldsTemp(session, ctrlPrincipal, T4, 8);
        }
        
        if(field.getName().equals("tempSaiBomba")){
            T5 = Double.parseDouble(field.getSelectedItem().toString());
            ControlAtualizaFieldsTemp atualiza = new ControlAtualizaFieldsTemp(session, ctrlPrincipal, T5, 9);
        }
        
        if(field.getName().equals("tempEntrRegenerador")){
            T5 = Double.parseDouble(field.getSelectedItem().toString());
            ControlAtualizaFieldsTemp atualiza = new ControlAtualizaFieldsTemp(session, ctrlPrincipal, T5, 10);
        }
        
        if(field.getName().equals("tempSaiRegenerador")){
            T6 = Double.parseDouble(field.getSelectedItem().toString());
            ControlAtualizaFieldsTemp atualiza = new ControlAtualizaFieldsTemp(session, ctrlPrincipal, T6, 11);
        }
        
        if(field.getName().equals("tempEntrEvaporador")){
            T6 = Double.parseDouble(field.getSelectedItem().toString());
            ControlAtualizaFieldsTemp atualiza = new ControlAtualizaFieldsTemp(session, ctrlPrincipal, T6, 12);
        }
    }
    
    public void atualizaFieldsPressao(DropdownComboBox field){
        ControlAtualizaFieldsPressao atualiza;
        switch(field.getName()){
            case "pressaoSaiEvaporador":
                P3 = Double.parseDouble(field.getSelectedItem().toString());
                atualiza = new ControlAtualizaFieldsPressao(session, ctrlPrincipal, P3, 1);
                break;
            case "pressaoEntrTurbuna":
                P4 = Double.parseDouble(field.getSelectedItem().toString());
                atualiza = new ControlAtualizaFieldsPressao(session, ctrlPrincipal, P4, 2);
                break;
            case "pressaoSaiTurbina":
                P3 = Double.parseDouble(field.getSelectedItem().toString());
                atualiza = new ControlAtualizaFieldsPressao(session, ctrlPrincipal, P3, 3);
                break;
            case "pressaoEntrRegenerador1":
                P4 = Double.parseDouble(field.getSelectedItem().toString());
                atualiza = new ControlAtualizaFieldsPressao(session, ctrlPrincipal, P4, 4);
                break;
            case "pressaoSaiRegenerador1":
                P3 = Double.parseDouble(field.getSelectedItem().toString());
                atualiza = new ControlAtualizaFieldsPressao(session, ctrlPrincipal, P3, 5);
                break;
            case "pressaoEntrCondensador":
                P4 = Double.parseDouble(field.getSelectedItem().toString());
                atualiza = new ControlAtualizaFieldsPressao(session, ctrlPrincipal, P4, 6);
                break;
            case "pressaoSaiCondensador":
                P3 = Double.parseDouble(field.getSelectedItem().toString());
                atualiza = new ControlAtualizaFieldsPressao(session, ctrlPrincipal, P3, 7);
                break;
            case "pressaoEntrBomba":
                P4 = Double.parseDouble(field.getSelectedItem().toString());
                atualiza = new ControlAtualizaFieldsPressao(session, ctrlPrincipal, P4, 8);
                break;
            case "pressaoSaiBomba":
                P3 = Double.parseDouble(field.getSelectedItem().toString());
                atualiza = new ControlAtualizaFieldsPressao(session, ctrlPrincipal, P3, 9);
                break;
            case "pressaoEntrRegenerador2":
                P4 = Double.parseDouble(field.getSelectedItem().toString());
                atualiza = new ControlAtualizaFieldsPressao(session, ctrlPrincipal, P4, 10);
                break;
            case "pressaoSaiRegenerador2":
                P3 = Double.parseDouble(field.getSelectedItem().toString());
                atualiza = new ControlAtualizaFieldsPressao(session, ctrlPrincipal, P3, 11);
                break;
            case "pressaoEntrEvaporador":
                P4 = Double.parseDouble(field.getSelectedItem().toString());
                atualiza = new ControlAtualizaFieldsPressao(session, ctrlPrincipal, P4, 12);
                break;
        }
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

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
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

    public double getAcon() {
        return Acon;
    }

    public void setAcon(double Acon) {
        this.Acon = Acon;
    }

    public double getAevp() {
        return Aevp;
    }

    public void setAevp(double Aevp) {
        this.Aevp = Aevp;
    }

    public double getAreg() {
        return Areg;
    }

    public void setAreg(double Areg) {
        this.Areg = Areg;
    }

    public double getAT() {
        return AT;
    }

    public void setAT(double AT) {
        this.AT = AT;
    }

    public double getUASUP() {
        return UASUP;
    }

    public void setUASUP(double UASUP) {
        this.UASUP = UASUP;
    }

    public double getUALAT() {
        return UALAT;
    }

    public void setUALAT(double UALAT) {
        this.UALAT = UALAT;
    }

    public double getUASEN() {
        return UASEN;
    }

    public void setUASEN(double UASEN) {
        this.UASEN = UASEN;
    }

    public double getUAREG() {
        return UAREG;
    }

    public void setUAREG(double UAREG) {
        this.UAREG = UAREG;
    }

    public double getUACONl() {
        return UACONl;
    }

    public void setUACONl(double UACONl) {
        this.UACONl = UACONl;
    }

    public double getUACONs() {
        return UACONs;
    }

    public void setUACONs(double UACONs) {
        this.UACONs = UACONs;
    }

    public double getTen() {
        return Ten;
    }

    public void setTen(double Ten) {
        this.Ten = Ten;
    }

    public double getPen() {
        return Pen;
    }

    public void setPen(double Pen) {
        this.Pen = Pen;
    }

    public double getTen1() {
        return Ten1;
    }

    public void setTen1(double Ten1) {
        this.Ten1 = Ten1;
    }

    public double getTs() {
        return Ts;
    }

    public void setTs(double Ts) {
        this.Ts = Ts;
    }

    public double getmH2O() {
        return mH2O;
    }

    public void setmH2O(double mH2O) {
        this.mH2O = mH2O;
    }
}