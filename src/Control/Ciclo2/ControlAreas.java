/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlAreas {

    private double Acon, Areg, Aevp, UASUP, UALAT, UASEN, UACONl, UACONs, UAREG, Ten, Pen, Ten1, mH2O;
    private String mensagem = "";
    
    public ControlAreas(double T1, double T2, double T3, double T4, double T5, double T6, double Tf, double Tfout, double Qevp, double Qcon, double Qreg, double eff, double Hlat, double Hsen, double Hsup, double T1s, double PP, double SUP, double m, double Pref, double Tref, double P4, int ii, double H3,int compressor,Session session) {
        double Ucon = 0.75;
        double Uevp = 0.75;
        double Ureg = 0.75;
        double Toile=Tf;
        double Toils=Tfout;
        double Toil1=T1s+PP;
        double Toil2=Tf;
        
        double Qsen=m*Hsen;
        double Qlat=m*Hlat;
        double Qsup=m*Hsup;
        
        double Tmevp3 = 1;
        if(SUP > 0){
            ControlTSaida tSaida = new ControlTSaida(compressor, Tf, Qsup, session);
            Toil2 = tSaida.getTfout();
            //Tmevp3= ((Toile-T1)-(Toil2-T1s))/(log((Toile-T1)/(Toil2-T1s)));
            Tmevp3= ((Toile-T1)-(Toil2-T1s))/(Math.log((Toile-T1)/(Toil2-T1s)));
        }
        
        Ten = 25+273.15;
        Pen = 101.325;
        Ten1 = T4-7;
        double Ps = 110;
        
        ControlH_Sistema h_Sistema = new ControlH_Sistema(Ten, Pen, 10, 273, 1, session);
        double HLen = h_Sistema.getHL();
        //double HVen = h_Sistema.getHV();
        h_Sistema = new ControlH_Sistema(Ten1, Ps, 10, 273, 1, session);
        double HLs = h_Sistema.getHL();
        //double HVs = h_Sistema.getHV();
        h_Sistema = new ControlH_Sistema(T4, P4,  Pref, Tref, ii, session);
        double HLsat = h_Sistema.getHL();
        double HVsat = h_Sistema.getHV();
        
        double HCONlat=HVsat-HLsat;
        double HCONsen=H3-HVsat;

        double Qconlat=m*HCONlat;
        double Qconsen=m*HCONsen;
        mH2O=Qconlat/(HLs-HLen);
        
        double Tcon3=T3-T4;
        double Ts;
        if(Tcon3 < 1){
            Qconsen=0;
            Ts=Ten1;
        }else{
            h_Sistema = new ControlH_Sistema(Ten, Pen, 10, 273, 1, session);
            double Hin = h_Sistema.getHL();
            
            double Test = Ten1;
            double erro = 1;
            double DT = 4.7;
            int it = 0;
            double Burbuja;
            while(erro > 0.0005){
                it +=1;
                if(it > 10000){
                    mensagem = "Não houve convergência no cálculo da entropia";
                    return;
                }
                
                h_Sistema = new ControlH_Sistema(Test, Pen, 10, 273, 1, session);
                double Hout = h_Sistema.getHL();
                double Q = (Hout - Hin)*mH2O;
                erro = Math.abs((Qcon-Q)/Qcon);
                Burbuja = Q - Qcon;
                if(erro > 0.0005 && Burbuja < 0){
                    Test += DT;
                    DT /= 2;
                    if(DT < 0.0005)
                        DT=0.0005456321;
                }else if(erro>0.0005 && Burbuja>0){
                    Test=Test-DT;
                    DT=DT/2;
                    if (DT<0.0005)
                        DT=0.0003975313;
                }
            }
            
            Ts = Test;
            if(Ts < Ten1){
                mensagem = "Não foi possível realizar os cálculos";
                return;
            }
        }
        
        if(eff == 0){
            //Tmevp1= ((Toil1-T1s)-(Toils-T6))/(log((Toil1-T1s)/(Toils-T6)));
            double Tmevp1= ((Toil1-T1s)-(Toils-T6))/(Math.log((Toil1-T1s)/(Toils-T6)));
            
            //Tmevp2= ((Toil2-T1s)-(Toil1-T1s))/(log((Toil2-T1s)/(Toil1-T1s)));
            double Tmevp2= ((Toil2-T1s)-(Toil1-T1s))/(Math.log((Toil2-T1s)/(Toil1-T1s)));
            
            //Aevp= (Qsen/(Tmevp1*Uevp))+(Qlat/(Tmevp2*Uevp))+(Qsup/(Tmevp3*Uevp));
            Aevp = (Qsen/(Tmevp1*Uevp))+(Qlat/(Tmevp2*Uevp))+(Qsup/(Tmevp3*Uevp));
        
            UASUP=(Qsup/(Tmevp3));
            UALAT=(Qlat/(Tmevp2));
            UASEN=(Qsen/(Tmevp1));
            
            //Tmcon1= ((T4-Ten1)-(T4-Ten))/(log((T4-Ten1)/(T4-Ten)));
            double Tmcon1= ((T4-Ten1)-(T4-Ten))/(Math.log((T4-Ten1)/(T4-Ten)));
            
            //Tmcon2= ((T3-Ts)-(T4-Ten1))/(log((T3-Ts)/(T4-Ten1)));
            double Tmcon2= ((T3-Ts)-(T4-Ten1))/(Math.log((T3-Ts)/(T4-Ten1)));

            //Acon= Qconlat/(Tmcon1*Ucon)+Qconsen/(Tmcon2*Ucon);
            Acon= Qconlat/(Tmcon1*Ucon)+Qconsen/(Tmcon2*Ucon);
            
            UACONl=Qconlat/(Tmcon1);
            UACONs=Qconsen/(Tmcon2);
            UAREG=0;
            Areg = 0;
            
            if(T3 == T4 & Acon < 10){
                Acon = 0;
            }
        }else{
            //Tmcon1= ((T4-Ten1)-(T4-Ten))/(log((T4-Ten1)/(T4-Ten)));
            double Tmcon1= ((T4-Ten1)-(T4-Ten))/(Math.log((T4-Ten1)/(T4-Ten)));
            
            //Tmcon2= ((T3-Ts)-(T4-Ten1))/(log((T3-Ts)/(T4-Ten1)));
            double Tmcon2= ((T3-Ts)-(T4-Ten1))/(Math.log((T3-Ts)/(T4-Ten1)));
            
            //Acon= Qconlat/(Tmcon1*Ucon)+Qconsen/(Tmcon2*Ucon);
            Acon= Qconlat/(Tmcon1*Ucon)+Qconsen/(Tmcon2*Ucon);

            //Tmreg= ((T2-T6)-(T3-T5))/(log((T2-T6)/(T3-T5)));
            double Tmreg= ((T2-T6)-(T3-T5))/(Math.log((T2-T6)/(T3-T5)));
            
            //Areg= Qreg/(Tmreg*Ureg);
            Areg= Qreg/(Tmreg*Ureg);

            //Tmevp1= ((Toil1-T1s)-(Toils-T6))/(log((Toil1-T1s)/(Toils-T6)));
            double Tmevp1= ((Toil1-T1s)-(Toils-T6))/(Math.log((Toil1-T1s)/(Toils-T6)));
            
            //Tmevp2= ((Toil2-T1s)-(Toil1-T1s))/(log((Toil2-T1s)/(Toil1-T1s)));
            double Tmevp2= ((Toil2-T1s)-(Toil1-T1s))/(Math.log((Toil2-T1s)/(Toil1-T1s)));
            
            //Aevp= (Qsen/(Tmevp1*Uevp))+(Qlat/(Tmevp2*Uevp))+(Qsup/(Tmevp3*Uevp));
            Aevp= (Qsen/(Tmevp1*Uevp))+(Qlat/(Tmevp2*Uevp))+(Qsup/(Tmevp3*Uevp));

            UACONl=Qconlat/(Tmcon1);
            UACONs=Qconsen/(Tmcon2);
            UAREG=Qreg/(Tmreg);
            UASUP=(Qsup/(Tmevp3));
            UALAT=(Qlat/(Tmevp2));
            UASEN=(Qsen/(Tmevp1));
            
            if(T3==T4 && Acon<10)
                Acon = 0;
        }
    }

    public double getAcon() {
        return Acon;
    }

    public void setAcon(double Acon) {
        this.Acon = Acon;
    }

    public double getAreg() {
        return Areg;
    }

    public void setAreg(double Areg) {
        this.Areg = Areg;
    }

    public double getAevp() {
        return Aevp;
    }

    public void setAevp(double Aevp) {
        this.Aevp = Aevp;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
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

    public double getUAREG() {
        return UAREG;
    }

    public void setUAREG(double UAREG) {
        this.UAREG = UAREG;
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

    public double getmH2O() {
        return mH2O;
    }

    public void setmH2O(double mH2O) {
        this.mH2O = mH2O;
    }
}
