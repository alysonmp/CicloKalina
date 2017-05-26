/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

/**
 *
 * @author leonardo
 */
public class ControlBalancoExergia {
    
    private double FB, FT, FR, FC, FP, PB, PT, PR, PC, PP,
            EFFB, EFFT, EFFR, EFFC, EFFP, EFFee, IB, IT, IR, IC, IP;
    
    public ControlBalancoExergia(double E1, double E2, double E3, double E4, double E5, double E6, double Wb, double Wt, double Wn, double EXevp, double EXcon) {
        FB=EXevp;
        FT=E1-E2;
        FR=E2-E3;
        FC=E3-E4;
        FP=Wb;

        PB=E1-E6;
        PT=Wt*.98;
        PR=E6-E5;
        PC=EXcon;
        PP=E5-E4;

        EFFB=100*PB/FB;
        EFFT=100*PT/FT;

        if(FR==0)
            EFFR=0;
        else
            EFFR=100*PR/(FR);
        
        EFFC=100*PC/FC;
        EFFP=100*PP/FP;

        EFFee=100*(Wn)/FB;

        IB=-(PB-FB);
        IT=-(PT-FT);
        IR=-(PR-FR);
        IC=FC-PC;
        IP=-(PP-FP);
    }

    public double getFB() {
        return FB;
    }

    public void setFB(double FB) {
        this.FB = FB;
    }

    public double getFT() {
        return FT;
    }

    public void setFT(double FT) {
        this.FT = FT;
    }

    public double getFR() {
        return FR;
    }

    public void setFR(double FR) {
        this.FR = FR;
    }

    public double getFC() {
        return FC;
    }

    public void setFC(double FC) {
        this.FC = FC;
    }

    public double getFP() {
        return FP;
    }

    public void setFP(double FP) {
        this.FP = FP;
    }

    public double getPB() {
        return PB;
    }

    public void setPB(double PB) {
        this.PB = PB;
    }

    public double getPT() {
        return PT;
    }

    public void setPT(double PT) {
        this.PT = PT;
    }

    public double getPR() {
        return PR;
    }

    public void setPR(double PR) {
        this.PR = PR;
    }

    public double getPC() {
        return PC;
    }

    public void setPC(double PC) {
        this.PC = PC;
    }

    public double getPP() {
        return PP;
    }

    public void setPP(double PP) {
        this.PP = PP;
    }

    public double getEFFB() {
        return EFFB;
    }

    public void setEFFB(double EFFB) {
        this.EFFB = EFFB;
    }

    public double getEFFT() {
        return EFFT;
    }

    public void setEFFT(double EFFT) {
        this.EFFT = EFFT;
    }

    public double getEFFR() {
        return EFFR;
    }

    public void setEFFR(double EFFR) {
        this.EFFR = EFFR;
    }

    public double getEFFC() {
        return EFFC;
    }

    public void setEFFC(double EFFC) {
        this.EFFC = EFFC;
    }

    public double getEFFP() {
        return EFFP;
    }

    public void setEFFP(double EFFP) {
        this.EFFP = EFFP;
    }

    public double getEFFee() {
        return EFFee;
    }

    public void setEFFee(double EFFee) {
        this.EFFee = EFFee;
    }

    public double getIB() {
        return IB;
    }

    public void setIB(double IB) {
        this.IB = IB;
    }

    public double getIT() {
        return IT;
    }

    public void setIT(double IT) {
        this.IT = IT;
    }

    public double getIR() {
        return IR;
    }

    public void setIR(double IR) {
        this.IR = IR;
    }

    public double getIC() {
        return IC;
    }

    public void setIC(double IC) {
        this.IC = IC;
    }

    public double getIP() {
        return IP;
    }

    public void setIP(double IP) {
        this.IP = IP;
    }

    
}
