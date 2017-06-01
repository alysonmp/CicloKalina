/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.Control.Ciclo2;

/**
 *
 * @author leonardo
 */
public class ControlCusto {

    private double CBom, CT, CAevp, CAreg, CAcon, OM, FCI, PEC;
    
    public ControlCusto(double Qevp, double Wb, double Wt, double Aevp, double Areg, double Acon) {
        CBom = 1*3500*(Math.pow(Wb,0.7));
        CT = 1*6000*(Math.pow(Wt,0.7));
        CAevp= 1*(2300*(Math.pow(Aevp,0.8)));
        CAreg= 1*(2300*(Math.pow(Areg,0.8)));
        CAcon= 1*(2300*(Math.pow(Acon,0.8)));
    
        PEC=CBom+CT+CAevp+ CAreg +CAcon;
        
        double TDC=0.6*PEC;
        double TIC=0.20*TDC;
        FCI=PEC+TDC+TIC;
        OM=PEC*0.05;

    }

    public double getCBom() {
        return CBom;
    }

    public void setCBom(double CBom) {
        this.CBom = CBom;
    }

    public double getCT() {
        return CT;
    }

    public void setCT(double CT) {
        this.CT = CT;
    }

    public double getCAevp() {
        return CAevp;
    }

    public void setCAevp(double CAevp) {
        this.CAevp = CAevp;
    }

    public double getCAreg() {
        return CAreg;
    }

    public void setCAreg(double CAreg) {
        this.CAreg = CAreg;
    }

    public double getCAcon() {
        return CAcon;
    }

    public void setCAcon(double CAcon) {
        this.CAcon = CAcon;
    }

    public double getOM() {
        return OM;
    }

    public void setOM(double OM) {
        this.OM = OM;
    }

    public double getFCI() {
        return FCI;
    }

    public void setFCI(double FCI) {
        this.FCI = FCI;
    }

    public double getPEC() {
        return PEC;
    }

    public void setPEC(double PEC) {
        this.PEC = PEC;
    }
    
    
}
