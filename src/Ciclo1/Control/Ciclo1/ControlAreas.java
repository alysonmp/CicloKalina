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
public class ControlAreas {
    
    private double Ucon, Uevp, Ureg, Toile, Toils, Toil1, Toil2, Tmevp1, Tmevp2, Aevp, TmRLT1, ALHR,
                   Ten, Pen, Ten1, Ps, Tmcon1, Acon, TmRHT1, AHHR, AT;
    
    public ControlAreas(double QLHR, double QHHR, double Qcon, double Qsen, double Qlat, double PP, double Tf, double Tfout, double T1max, double T12, double TbolA, double T9, double T8, double T11, double T7, double T10, double T3, double T5){
        Ucon=0.75; //%%0.4
        Uevp=0.75; //%%0.4
        Ureg=0.75; //%%0.3

        Toile = Tf;
        Toils = Tfout;
        Toil1 = T1max+PP;
        Toil2 = Tf;
        
        /*%%%% Evaporador %%%%%%%%%%%%%%%%%%%%%
        %1 Calor sensible
        %2 Calor Latente*/
        
        //Tmevp1= ((Toil1-TbolA)-(Toils-T12))/(log((Toil1-TbolA)/(Toils-T12)));
        Tmevp1= ((Toil1-TbolA)-(Toils-T12))/(Math.log((Toil1-TbolA)/(Toils-T12)));

        //Tmevp2= ((Toil2-T1max)-(Toil1-TbolA))/(log((Toil2-T1max)/(Toil1-TbolA)));
        Tmevp2= ((Toil2-T1max)-(Toil1-TbolA))/(Math.log((Toil2-T1max)/(Toil1-TbolA)));

        //Aevp= (Qsen/(Tmevp1*Uevp))+(Qlat/(Tmevp2*Uevp));
        Aevp= (Qsen/(Tmevp1*Uevp))+(Qlat/(Tmevp2*Uevp));
        
        /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        %%%%%%%%Calculo da agua do condensador%%%%*/
        Ten = 25+273.15;
        Pen = 101.325;
        Ten1 = 35+273.15;
        Ps = 101.325;

        //Tmcon1 = ((T8-Ten1)-(T9-Ten))/(log((T8-Ten1)/(T9-Ten)));
        Tmcon1 = ((T8-Ten1)-(T9-Ten))/(Math.log((T8-Ten1)/(T9-Ten)));

        Acon = Qcon/(Tmcon1*Ucon);
        
        /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        %%%%%%%%Calculo Recuperador LT%%%%*/

        //TmRLT1= ((T7-T11)-(T8-T10))/(log((T7-T11)/(T8-T10)));
        TmRLT1= ((T7-T11)-(T8-T10))/(Math.log((T7-T11)/(T8-T10)));

        ALHR= QLHR/(TmRLT1*Ureg);

        /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        %%%%%%%%Calculo Recuperador HT%%%%*/
        
        //TmRHT1=((T3-T12)-(T5-T11))/(log((T3-T12)/(T5-T11)));
        TmRHT1=((T3-T12)-(T5-T11))/(Math.log((T3-T12)/(T5-T11)));

        AHHR = QHHR/(TmRHT1*Ureg);
        AT = Acon+Aevp+ALHR+AHHR;

    }

    public double getAevp() {
        return Aevp;
    }

    public void setAevp(double Aevp) {
        this.Aevp = Aevp;
    }

    public double getALHR() {
        return ALHR;
    }

    public void setALHR(double ALHR) {
        this.ALHR = ALHR;
    }

    public double getAcon() {
        return Acon;
    }

    public void setAcon(double Acon) {
        this.Acon = Acon;
    }

    public double getAHHR() {
        return AHHR;
    }

    public void setAHHR(double AHHR) {
        this.AHHR = AHHR;
    }

    public double getAT() {
        return AT;
    }

    public void setAT(double AT) {
        this.AT = AT;
    }
}