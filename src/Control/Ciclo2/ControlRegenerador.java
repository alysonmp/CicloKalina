/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class ControlRegenerador {
    
    private double P6, P3, H3, T3, S3, H6, T6, S6, IHR, HL2, HV2, PP, HL3, HV3, Test, erro, DT, it, HL6, HV6, SL3, SV3, H, burbuja, SL6, SV6, DT11, DT12, DT13;
    
    public ControlRegenerador(double G, double H2, double H5, double S2, double S5, double P2, double T2, double P5, double T5, double P1, double Pconop, double Tconop, double Pref, double Tref, int ii, double eff, Session session){
    
        P6 = P1;
        P3 = Pconop;
        
        if(eff == 0){    
            H3=H2;
            T3=T2;
            S3=S2;
            H6=H5;
            T6=T5;
            S6=S5;
            IHR=0;
        }else{
            ControlH_Sistema HSistema = new ControlH_Sistema(T2, P2, Pref, Tref, ii, session);
            HL2 = HSistema.getHL();
            HV2 = HSistema.getHV();
            PP = T5+5;

            if(T2 > PP && G == 1 && HV2 == H2){
                T3 = T2-eff*(T2-T5);
                HSistema = new ControlH_Sistema(T3, P3, Pref, Tref, ii, session);
                HL3 = HSistema.getHL();
                HV3 = HSistema.getHV();
                
                H3 = HV3;
                
                ControlS_Sistema SSistema = new ControlS_Sistema(T3, P3, Pref, Tref, ii, session);
                SL3 = SSistema.getSL();
                SV3 = SSistema.getSV();
                
                S3 = SV3;
                H6 = H2-H3+H5;
                Test = T2;
                erro = 1;
                DT = 200;
                it = 0;
                while(erro > 0.0001){
                    it = it+1;
                    if(it > 10000){
                        //kkkkk=kkkkk*PPPPPP
                        break;
                    }
                    HSistema = new ControlH_Sistema(Test, P6, Pref, Tref, ii, session);
                    HL6 = HSistema.getHL();
                    HV6 = HSistema.getHV();
                    
                    H = HL6;
                    erro = Math.abs((H6-H)/H6);
                    burbuja = H6-H;
                    if(erro > 0.0001 && burbuja < 0){
                        Test = Test-DT;
                        DT = DT/2;
                        if(DT < 0.0005){
                            DT = 0.00035397;
                        }
                    }else{
                        if(erro > 0.0001 && burbuja > 0){
                            Test = Test+DT;
                            DT = DT/2;
                            if(DT < 0.0005){
                                DT = 0.000311139;
                            }
                        }
                    }
                }
                T6 = Test;
                SSistema = new ControlS_Sistema(T6, P6, Pref, Tref, ii, session);
                SL6 = SSistema.getSL();
                SV6 = SSistema.getSV();
                S6 = SL6;
                IHR = eff;
            }else{
                if(G == 1){
                    System.out.println("Não é possivél fazer Regeneração");
                    System.out.println("T2 é menor o igual que T5");
                }
                H3 = H2;
                T3 = T2;
                S3 = S2;
                H6 = H5;
                T6 = T5;
                S6 = S5;
                IHR = 0;
                //kkk*pppp*ttrfgd*iogffdgfjhj
            }
            
            /*
            
            COLOCAR MENSAGEM PARA USUÁRIO
            
            DT11 = T6+10;
            DT12 = T5+10;
            if(T2 <= DT11 && IHR > 0){ 
                H3=H2;
                T3=T2;
                S3=S2;
                H6=H5;
                T6=T5;
                S6=S5;
                IHR=0;
                //kkk*pppp*ttrfgd*iogffdgfjhj
            }
            
            if(IHR > 0 && T3 <= DT12){
                H3=H2;
                T3=T2;
                S3=S2;
                H6=H5;
                T6=T5;
                S6=S5;
                IHR=0;
                //kkk*pppp*ttrfgd*iogffdgfjhj
            }
            
            DT13 = T6-T5;
            if(DT13 <= 10){ 
                //COLOCAR MENSAGEM PARA USUÁRIO
                H3 = H2;
                T3 = T2;
                S3 = S2;
                H6 = H5;
                T6 = T5;
                S6 = S5;
                IHR = 0;
                //kkk*pppp*ttrfgd*iogffdgfjhj
            }*/
        }
    }

    public double getIHR() {
        return IHR;
    }

    public void setIHR(double IHR) {
        this.IHR = IHR;
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

    public double getS3() {
        return S3;
    }

    public void setS3(double S3) {
        this.S3 = S3;
    }

    public double getH6() {
        return H6;
    }

    public void setH6(double H6) {
        this.H6 = H6;
    }

    public double getT6() {
        return T6;
    }

    public void setT6(double T6) {
        this.T6 = T6;
    }

    public double getS6() {
        return S6;
    }

    public void setS6(double S6) {
        this.S6 = S6;
    }
}