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
public class ControlConstantesMix {
    
    private double kii, kjj, kij, kji, zj, aij, aji, am, bm, cm, Am, Bm, Cm, Nm, Mm, Qm;
    ControlConstantes constantes;
    
    public ControlConstantesMix(double T, double P, double zi){
        kii = 0;
        kjj = 0;
        kij = -0.25054;  //%%artigo
        kji = -0.26238;  //%%artigo
        
        zj = 1 - zi;
        
        constantes = new ControlConstantes(T, P);
        
        //aij= (1-kij+((kij-kji)*zi))*((ai*aj)^0.5);
        aij = (1-kij+((kij-kji)*zi))*Math.pow((constantes.getai()*constantes.getaj()), 0.5);
        //aji= (1-kji+((kji-kij)*zj))*((ai*aj)^0.5);
        aji = (1-kji+((kji-kij)*zj))*Math.pow((constantes.getai()*constantes.getaj()), 0.5);
        
        //am=(zi^2*ai)+(zi*zj*aij)+(zj*zi*aji)+(zj^2*aj);
        am = (Math.pow(zi, 2)*constantes.getai() + (zi*zj*aij) + (zj*zi*aji) + (Math.pow(zj, 2)*constantes.getaj()));
        //bm=(zi*bi)+(zj*bj);
        bm = (zi*constantes.getbi())+(zj*constantes.getbj());
        //cm=(zi*ci)+(zj*cj);
        cm = (zi*constantes.getci())+(zj*constantes.getcj());
        
        //Am=(am*P)/((R*T)^2);
        Am = (am*P)/Math.pow(constantes.getR()*T, 2);
        //Bm=bm*P/(R*T);
        Bm = bm*P/(constantes.getR()*T);
        //Cm=cm*P/(R*T);
        Cm = cm*P/(constantes.getR()*T);
        
        //Nm=((bm*cm)+(((bm+cm)/2)^2))^0.5;
        Nm = (Math.pow((bm*cm)+Math.pow((bm+cm)/2, 2), 0.5));
        //Mm=(((bm+cm)/2)-Nm)*(P/(R*T));
        Mm = (((bm+cm)/2)-Nm)*(P/(constantes.getR()*T));
        //Qm=(((bm+cm)/2)+Nm)*P/(R*T);
        Qm = (((bm+cm)/2)+Nm)*P/(constantes.getR()*T);
    } 

    public double getKij() {
        return kij;
    }

    public void setKij(double kij) {
        this.kij = kij;
    }

    public double getKji() {
        return kji;
    }

    public void setKji(double kji) {
        this.kji = kji;
    }

    public double getzj() {
        return zj;
    }

    public void setzj(double zj) {
        this.zj = zj;
    }

    public double getam() {
        return am;
    }

    public void setam(double am) {
        this.am = am;
    }

    public double getbm() {
        return bm;
    }

    public void setbm(double bm) {
        this.bm = bm;
    }

    public double getcm() {
        return cm;
    }

    public void setcm(double cm) {
        this.cm = cm;
    }

    public double getAm() {
        return Am;
    }

    public void setAm(double Am) {
        this.Am = Am;
    }

    public double getBm() {
        return Bm;
    }

    public void setBm(double Bm) {
        this.Bm = Bm;
    }

    public double getCm() {
        return Cm;
    }

    public void setCm(double Cm) {
        this.Cm = Cm;
    }

    public double getNm() {
        return Nm;
    }

    public void setNm(double Nm) {
        this.Nm = Nm;
    }

    public double getMm() {
        return Mm;
    }

    public void setMm(double Mm) {
        this.Mm = Mm;
    }

    public double getQm() {
        return Qm;
    }

    public void setQm(double Qm) {
        this.Qm = Qm;
    }   

    public ControlConstantes getConstantes() {
        return constantes;
    }

    public void setConstantes(ControlConstantes constantes) {
        this.constantes = constantes;
    }
}



