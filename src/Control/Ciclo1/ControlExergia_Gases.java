/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

import Model.ModelConsExeMat;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author leonardo
 */
public class ControlExergia_Gases {
    private double Ein, Hin, Sin, bgas, ET, XTOTAL;

    public ControlExergia_Gases(double mf, double Tf, double[] X, Session session) {

        Criteria cr = session.createCriteria(ModelConsExeMat.class); 
        cr.add(Restrictions.eq("name", "A"));
        List results = cr.list();
        
        ModelConsExeMat consEXE = (ModelConsExeMat)results.get(0);
        double[][] valores = consEXE.getValores();
        
        double To = 298.15;
        double T = Tf;
        XTOTAL=X[1] + X[2] + X[3] + X[4] + X[5] + X[6] + X[7] + X[8];
        
        double DHCO2 =((valores[0][0]*T)+((valores[0][1]/1000)*Math.pow(T,2))+((valores[0][2]*100000)/T)+(valores[0][3]))*4.186;
        double DHH2O =((valores[1][0]*T)+((valores[1][1]/1000)*Math.pow(T,2))+((valores[1][2]*100000)/T)+(valores[1][3]))*4.186;
        double DHSO2 =((valores[2][0]*T)+((valores[2][1]/1000)*Math.pow(T,2))+((valores[2][2]*100000)/T)+(valores[2][4]))*4.186;
        double DHO2 = ((valores[3][0]*T)+((valores[3][1]/1000)*Math.pow(T,2))+((valores[3][2]*100000)/T)+(valores[3][4]))*4.186;
        double DHN2 = ((valores[4][0]*T)+((valores[4][1]/1000)*Math.pow(T,2))+((valores[4][2]*100000)/T)+(valores[4][4]))*4.186;

        double DHCO = ((valores[5][0]*T)+((valores[5][1]/1000)*Math.pow(T,2))+((valores[5][2]*100000)/T)+(valores[5][4]))*4.186;
        double DHH2 = ((valores[6][0]*T)+((valores[6][1]/1000)*Math.pow(T,2))+((valores[6][2]*100000)/T)+(valores[6][4]))*4.186;
        double DHNO = ((valores[7][0]*T)+((valores[7][1]/1000)*Math.pow(T,2))+((valores[7][2]*100000)/T)+(valores[7][4]))*4.186;
        
        double DSCO2 = ((valores[0][0]*Math.log(T/To))+((2*valores[0][1]/1000)*(T-To))+(((valores[0][2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        double DSH2O = ((valores[1][0]*Math.log(T/To))+((2*valores[1][1]/1000)*(T-To))+(((valores[1][2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        double DSSO2 = ((valores[2][0]*Math.log(T/To))+((2*valores[2][1]/1000)*(T-To))+(((valores[2][2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        double DSO2 = ((valores[3][0]*Math.log(T/To))+((2*valores[3][1]/1000)*(T-To))+(((valores[3][2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        double DSN2 = ((valores[4][0]*Math.log(T/To))+((2*valores[4][1]/1000)*(T-To))+(((valores[4][2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        
        double DSCO = ((valores[5][0]*Math.log(T/To))+((2*valores[5][1]/1000)*(T-To))+(((valores[5][2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        double DSH2 = ((valores[6][0]*Math.log(T/To))+((2*valores[6][1]/1000)*(T-To))+(((valores[6][2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        double DSNO = ((valores[7][0]*Math.log(T/To))+((2*valores[7][1]/1000)*(T-To))+(((valores[7][2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        
        double[] y = {X[1]/XTOTAL, X[2]/XTOTAL, X[3]/XTOTAL,  X[4]/XTOTAL, X[5]/XTOTAL,  X[6]/XTOTAL,  X[7]/XTOTAL,  X[8]/XTOTAL};
        
        double DHECO2 = (DHCO2)*y[0];
        double DHEH2O = (DHH2O)*y[1];
        double DHESO2 = (DHSO2)*y[2];
        double DHEO2 = (DHO2)*y[3];
        double DHEN2 = (DHN2)*y[4];

        double DHECO = DHCO*y[5];
        double DHEH2 = DHH2*y[6];
        double DHENO = DHNO*y[7];
        
        Hin = (DHECO2+DHEH2O+DHESO2+DHEO2+DHEN2+DHECO+DHEH2+DHENO);
        
        double DSECO2 = (DSCO2)*y[0];
        double DSEH2O = (DSH2O)*y[1];
        double DSESO2 = (DSSO2)*y[2];
        double DSEO2 = (DSO2)*y[3];
        double DSEN2 = (DSN2)*y[4];

        double DSECO = DSCO*y[5];
        double DSEH2 = DSH2*y[6];
        double DSENO = DSNO*y[7];
        
        Sin=(DSECO2+DSEH2O+DSESO2+DSEO2+DSEN2+DSECO+DSEH2+DSENO);
        
        double ECO2 = (DHCO2-(To*DSCO2))*y[0];
        double EH2O = (DHH2O-(To*DSH2O))*y[1];
        double ESO2 = (DHSO2-(To*DSSO2))*y[2];
        double EO2 = (DHO2-(To*DSO2))*y[3];
        double EN2 = (DHN2-(To*DSN2))*y[4];

        double ECO = (DHCO-(To*DSCO))*y[5];
        double EH2 = (DHH2-(To*DSH2))*y[6];
        double ENO = (DHNO-(To*DSNO))*y[7];
        
        Ein=(ECO2+EH2O+ESO2+EO2+EN2+ECO+EH2+ENO);
        
        double mfx = 0;
        double logy = 0;
        for(int i = 0; i < y.length; i++){
            //A resposta aqui não é a soma de tudo, é um vetor do mesmo tamanho do vetor original depois da multiplicação
            mfx += (mf*y[i]);
            logy += Math.log(y[i]);
        }
        
        for(int qq=1; qq < 8; qq++){
            double real = isinf(logy(qq,1));
            if real>0
                logy(qq,1)=0;
            else
                logy(qq,1)=logy(qq,1);
            end
            end

        ylogy=y*logy;
        yb=y*b;
        bgas=yb+(8.31416*ylogy*To); %%kJ/kmol

        ET=bgas+Ein;

    }
    
    public double getEin() {
        return Ein;
    }

    public void setEin(double Ein) {
        this.Ein = Ein;
    }

    public double getHin() {
        return Hin;
    }

    public void setHin(double Hin) {
        this.Hin = Hin;
    }

    public double getSin() {
        return Sin;
    }

    public void setSin(double Sin) {
        this.Sin = Sin;
    }

    public double getBgas() {
        return bgas;
    }

    public void setBgas(double bgas) {
        this.bgas = bgas;
    }

    public double getET() {
        return ET;
    }

    public void setET(double ET) {
        this.ET = ET;
    }
}