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
public class ControlConstantes {
    
    private double R, Pci, Tci, Zci, Tri, Pri, Fi, Omegabi, 
                   Omegaai, Omegaci, ai, alfai, bi, ci, Ai, Bi, Ci,
                   Ni, Mi, Qi, Pcj, Tcj, Zcj, Trj, Prj, Fj, Omegabj, 
                   Omegaaj, Omegacj, aj, alfaj, bj,cj,Aj, Bj, Cj, Nj, Mj, Qj;
    
    public ControlConstantes(double T, double P){
        R = 8.3144621/100000;
        
        //%%%%%%%%%%%%%%%%%%% Amonia %%%%%%%%%%%%%%%%%%%%%
        
        Pci = 112.8; //bar site
        Tci = 405.4; //K
        Zci = 0.28289;
        Tri = T/Tci;
        Pri = P/Pci;
        Fi = 0.627090;
        
        Omegabi = 0.069606085208489;
        //Omegaai=(3*Zci^2)+((3*(1-2*Zci))*Omegabi)+(Omegabi^2)+1-(3*Zci);
        Omegaai = (3*Math.pow(Zci, 2)) + ((3*(1-2*Zci))*Omegabi) + (Math.pow(Omegabi, 2)+1-(3*Zci));
        //Omegaci=1-(3*Zci);
        Omegaci = 1 - (3*Zci);
        
        //ai=Omegaai*(R^2*Tci^2/Pci)*(1+(Fi*(1-(Tri^0.5))))^2;

        ai = Omegaai* ((Math.pow(R, 2)*Math.pow(Tci, 2))/Pci) * Math.pow((1+(Fi*(1-(Math.pow(Tri, 0.5))))), 2);
        //alfai=(1+(Fi*(1-(Tri^0.5))))^2;
        alfai = Math.pow((1+(Fi*(1-(Math.pow(Tri, 0.5))))), 2);
        //bi=Omegabi*(R*Tci/Pci);
        bi = Omegabi * ((R*Tci)/Pci);
        //ci=Omegaci*(R*Tci/Pci);
        ci = Omegaci * ((R*Tci)/Pci);
        
        //Ai=(ai*P)/((R*T)^2);
        Ai = (ai*P)/(Math.pow(R*T, 2));
        //Bi=bi*P/(R*T) ;
        Bi = bi*P/(R*T);
        //Ci=ci*P/(R*T) ;
        Ci = ci*P/(R*T);
        
        //Ni=((bi*ci)+(((bi+ci)/2)^2))^0.5;
        Ni = Math.pow((bi*ci)+Math.pow((bi+ci)/2, 2), 0.5);
        //Mi=(((bi+ci)/2)-Ni)*(P/(R*T)) ;
        Mi = (((bi+ci)/2)-Ni) * P/(R*T);
        //Qi=(((bi+ci)/2)+Ni)*P/(R*T) ;
        Qi = (((bi+ci)/2)+Ni) * P/(R*T);
        
        //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        
        //%%%%%%%%%%%%%%%%%%% Água %%%%%%%%%%%%%%%%%%%%%
        
        Pcj = 221.2; //bar
        Tcj = 647.1; //K
        Zcj = 0.26956;
        Trj = T/Tcj;
        Prj = P/Pcj;
        Fj = 0.689803; 
        
        Omegabj = 0.065284433256334;
        //Omegaaj=(3*Zcj^2)+((3*(1-2*Zcj))*Omegabj)+(Omegabj^2)+1-(3*Zcj);
        Omegaaj = (3*Math.pow(Zcj, 2) + ((3*(1-2*Zcj))*Omegabj) + Math.pow(Omegabj, 2) + 1-(3*Zcj));
        //Omegacj=1-(3*Zcj);
        Omegacj = 1-(3*Zcj);
        
        //aj=Omegaaj*(R^2*Tcj^2/Pcj)*(1+(Fj*(1-(Trj^0.5))))^2;

        aj = Omegaaj * ((Math.pow(R, 2)*Math.pow(Tcj, 2))/Pcj) * Math.pow((1+(Fj*(1-(Math.pow(Trj, 0.5))))), 2);
        //alfaj=(1+(Fj*(1-(Trj^0.5))))^2;
        alfaj = Math.pow((1+(Fj*(1-(Math.pow(Trj, 0.5))))), 2);
        //bj=Omegabj*(R*Tcj/Pcj);
        bj = Omegabj*(R*Tcj/Pcj);
        //cj=Omegacj*(R*Tcj/Pcj);
        cj = Omegacj*(R*Tcj/Pcj);
        
        //Aj=(aj*P)/((R*T)^2);
        Aj = (aj*P)/(Math.pow(R*T, 2)); 
        //Bj=bj*P/(R*T);
        Bj=bj*P/(R*T);
        //Cj=cj*P/(R*T);
        Cj=cj*P/(R*T);
        
        //Nj=((bj*cj)+((bj+cj)/2)^2)^.5 ;
        Nj = Math.pow((bj*cj)+Math.pow((bj+cj)/2, 2), 0.5);
        //Mj=(((bj+cj)/2)-Nj)*(P/(R*T)) ;
        Mj=(((bj+cj)/2)-Nj)*(P/(R*T)) ;
        //Qj=(((bj+cj)/2)+Nj)*P/(R*T) ;
        Qj=(((bj+cj)/2)+Nj)*P/(R*T) ;
        
        //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%  
    }

    public double getR() {
        return R;
    }

    public void setR(double R) {
        this.R = R;
    }

    public double getPci() {
        return Pci;
    }

    public void setPci(double Pci) {
        this.Pci = Pci;
    }

    public double getTci() {
        return Tci;
    }

    public void setTci(double Tci) {
        this.Tci = Tci;
    }

    public double getZci() {
        return Zci;
    }

    public void setZci(double Zci) {
        this.Zci = Zci;
    }

    public double getTri() {
        return Tri;
    }

    public void setTri(double Tri) {
        this.Tri = Tri;
    }

    public double getPri() {
        return Pri;
    }

    public void setPri(double Pri) {
        this.Pri = Pri;
    }

    public double getFi() {
        return Fi;
    }

    public void setFi(double Fi) {
        this.Fi = Fi;
    }

    public double getOmegabi() {
        return Omegabi;
    }

    public void setOmegabi(double Omegabi) {
        this.Omegabi = Omegabi;
    }

    public double getOmegaai() {
        return Omegaai;
    }

    public void setOmegaai(double Omegaai) {
        this.Omegaai = Omegaai;
    }

    public double getOmegaci() {
        return Omegaci;
    }

    public void setOmegaci(double Omegaci) {
        this.Omegaci = Omegaci;
    }

    public double getai() {
        return ai;
    }

    public void setai(double ai) {
        this.ai = ai;
    }

    public double getAlfai() {
        return alfai;
    }

    public void setAlfai(double alfai) {
        this.alfai = alfai;
    }

    public double getbi() {
        return bi;
    }

    public void setbi(double bi) {
        this.bi = bi;
    }

    public double getci() {
        return ci;
    }

    public void setci(double ci) {
        this.ci = ci;
    }

    public double getAi() {
        return Ai;
    }

    public void setAi(double Ai) {
        this.Ai = Ai;
    }

    public double getBi() {
        return Bi;
    }

    public void setBi(double Bi) {
        this.Bi = Bi;
    }

    public double getCi() {
        return Ci;
    }

    public void setCi(double Ci) {
        this.Ci = Ci;
    }

    public double getNi() {
        return Ni;
    }

    public void setNi(double Ni) {
        this.Ni = Ni;
    }

    public double getMi() {
        return Mi;
    }

    public void setMi(double Mi) {
        this.Mi = Mi;
    }

    public double getQi() {
        return Qi;
    }

    public void setQi(double Qi) {
        this.Qi = Qi;
    }

    public double getPcj() {
        return Pcj;
    }

    public void setPcj(double Pcj) {
        this.Pcj = Pcj;
    }

    public double getTcj() {
        return Tcj;
    }

    public void setTcj(double Tcj) {
        this.Tcj = Tcj;
    }

    public double getZcj() {
        return Zcj;
    }

    public void setZcj(double Zcj) {
        this.Zcj = Zcj;
    }

    public double getTrj() {
        return Trj;
    }

    public void setTrj(double Trj) {
        this.Trj = Trj;
    }

    public double getPrj() {
        return Prj;
    }

    public void setPrj(double Prj) {
        this.Prj = Prj;
    }

    public double getFj() {
        return Fj;
    }

    public void setFj(double Fj) {
        this.Fj = Fj;
    }

    public double getOmegabj() {
        return Omegabj;
    }

    public void setOmegabj(double Omegabj) {
        this.Omegabj = Omegabj;
    }

    public double getOmegaaj() {
        return Omegaaj;
    }

    public void setOmegaaj(double Omegaaj) {
        this.Omegaaj = Omegaaj;
    }

    public double getOmegacj() {
        return Omegacj;
    }

    public void setOmegacj(double Omegacj) {
        this.Omegacj = Omegacj;
    }

    public double getaj() {
        return aj;
    }

    public void setaj(double aj) {
        this.aj = aj;
    }

    public double getAlfaj() {
        return alfaj;
    }

    public void setAlfaj(double alfaj) {
        this.alfaj = alfaj;
    }

    public double getbj() {
        return bj;
    }

    public void setbj(double bj) {
        this.bj = bj;
    }

    public double getcj() {
        return cj;
    }

    public void setcj(double cj) {
        this.cj = cj;
    }

    public double getAj() {
        return Aj;
    }

    public void setAj(double Aj) {
        this.Aj = Aj;
    }

    public double getBj() {
        return Bj;
    }

    public void setBj(double Bj) {
        this.Bj = Bj;
    }

    public double getCj() {
        return Cj;
    }

    public void setCj(double Cj) {
        this.Cj = Cj;
    }

    public double getNj() {
        return Nj;
    }

    public void setNj(double Nj) {
        this.Nj = Nj;
    }

    public double getMj() {
        return Mj;
    }

    public void setMj(double Mj) {
        this.Mj = Mj;
    }

    public double getQj() {
        return Qj;
    }

    public void setQj(double Qj) {
        this.Qj = Qj;
    }
}
   
   
