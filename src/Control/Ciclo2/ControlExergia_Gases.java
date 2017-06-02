/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

import Model.ModelConsExeMatA;
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

        Criteria cr = session.createCriteria(ModelConsExeMatA.class); 
        List results = cr.list();
        
        ModelConsExeMatA consEXE = (ModelConsExeMatA)results.get(0);
        double[] valores = consEXE.getValores();
        
        double To = 298.15;
        double T = Tf;
        XTOTAL=X[1] + X[2] + X[3] + X[4] + X[5] + X[6] + X[7] + X[8];
        
        double DHCO2 = ((((ModelConsExeMatA)results.get(0)).getValores()[0]*T)+((((ModelConsExeMatA)results.get(0)).getValores()[1]/1000)*Math.pow(T,2))+((((ModelConsExeMatA)results.get(0)).getValores()[2]*100000)/T)+(((ModelConsExeMatA)results.get(0)).getValores()[3]))*4.186;
        double DHH2O = ((((ModelConsExeMatA)results.get(1)).getValores()[0]*T)+((((ModelConsExeMatA)results.get(1)).getValores()[1]/1000)*Math.pow(T,2))+((((ModelConsExeMatA)results.get(1)).getValores()[2]*100000)/T)+(((ModelConsExeMatA)results.get(1)).getValores()[3]))*4.186;
        double DHSO2 = ((((ModelConsExeMatA)results.get(2)).getValores()[0]*T)+((((ModelConsExeMatA)results.get(2)).getValores()[1]/1000)*Math.pow(T,2))+((((ModelConsExeMatA)results.get(2)).getValores()[2]*100000)/T)+(((ModelConsExeMatA)results.get(2)).getValores()[3]))*4.186;
        double DHO2 = ((((ModelConsExeMatA)results.get(3)).getValores()[0]*T)+((((ModelConsExeMatA)results.get(3)).getValores()[1]/1000)*Math.pow(T,2))+((((ModelConsExeMatA)results.get(3)).getValores()[2]*100000)/T)+(((ModelConsExeMatA)results.get(3)).getValores()[3]))*4.186;
        double DHN2 = ((((ModelConsExeMatA)results.get(4)).getValores()[0]*T)+((((ModelConsExeMatA)results.get(4)).getValores()[1]/1000)*Math.pow(T,2))+((((ModelConsExeMatA)results.get(4)).getValores()[2]*100000)/T)+(((ModelConsExeMatA)results.get(4)).getValores()[3]))*4.186;

        double DHCO = ((((ModelConsExeMatA)results.get(5)).getValores()[0]*T)+((((ModelConsExeMatA)results.get(5)).getValores()[1]/1000)*Math.pow(T,2))+((((ModelConsExeMatA)results.get(5)).getValores()[3]*100000)/T)+(((ModelConsExeMatA)results.get(5)).getValores()[3]))*4.186;
        double DHH2 = ((((ModelConsExeMatA)results.get(6)).getValores()[0]*T)+((((ModelConsExeMatA)results.get(6)).getValores()[1]/1000)*Math.pow(T,2))+((((ModelConsExeMatA)results.get(6)).getValores()[3]*100000)/T)+(((ModelConsExeMatA)results.get(6)).getValores()[3]))*4.186;
        double DHNO = ((((ModelConsExeMatA)results.get(7)).getValores()[0]*T)+((((ModelConsExeMatA)results.get(7)).getValores()[1]/1000)*Math.pow(T,2))+((((ModelConsExeMatA)results.get(7)).getValores()[3]*100000)/T)+(((ModelConsExeMatA)results.get(7)).getValores()[3]))*4.186;
        
        double DSCO2 = ((((ModelConsExeMatA)results.get(0)).getValores()[0]*Math.log(T/To))+((2*((ModelConsExeMatA)results.get(0)).getValores()[1]/1000)*(T-To))+(((((ModelConsExeMatA)results.get(0)).getValores()[2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        double DSH2O = ((((ModelConsExeMatA)results.get(1)).getValores()[0]*Math.log(T/To))+((2*((ModelConsExeMatA)results.get(1)).getValores()[1]/1000)*(T-To))+(((((ModelConsExeMatA)results.get(1)).getValores()[2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        double DSSO2 = ((((ModelConsExeMatA)results.get(2)).getValores()[0]*Math.log(T/To))+((2*((ModelConsExeMatA)results.get(2)).getValores()[1]/1000)*(T-To))+(((((ModelConsExeMatA)results.get(2)).getValores()[2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        double DSO2 = ((((ModelConsExeMatA)results.get(3)).getValores()[0]*Math.log(T/To))+((2*((ModelConsExeMatA)results.get(3)).getValores()[1]/1000)*(T-To))+(((((ModelConsExeMatA)results.get(3)).getValores()[2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        double DSN2 = ((((ModelConsExeMatA)results.get(4)).getValores()[0]*Math.log(T/To))+((2*((ModelConsExeMatA)results.get(4)).getValores()[1]/1000)*(T-To))+(((((ModelConsExeMatA)results.get(4)).getValores()[2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        
        double DSCO = ((((ModelConsExeMatA)results.get(5)).getValores()[0]*Math.log(T/To))+((2*((ModelConsExeMatA)results.get(5)).getValores()[1]/1000)*(T-To))+(((((ModelConsExeMatA)results.get(5)).getValores()[2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        double DSH2 = ((((ModelConsExeMatA)results.get(6)).getValores()[0]*Math.log(T/To))+((2*((ModelConsExeMatA)results.get(6)).getValores()[1]/1000)*(T-To))+(((((ModelConsExeMatA)results.get(6)).getValores()[2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        double DSNO = ((((ModelConsExeMatA)results.get(7)).getValores()[0]*Math.log(T/To))+((2*((ModelConsExeMatA)results.get(7)).getValores()[1]/1000)*(T-To))+(((((ModelConsExeMatA)results.get(7)).getValores()[2]/2)*100000)*((1/Math.pow(T,2))-(1/Math.pow(To,2)))))*4.1861;
        
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
        
        double[] mfx = new double[y.length];
        double[] logy = new double[y.length];
        for(int i = 0; i < y.length; i++){
            //A resposta aqui não é a soma de tudo, é um vetor do mesmo tamanho do vetor original depois da multiplicação
            //mfx += (mf*y[i]);
            //logy += Math.log(y[i]);
            mfx[i] = mf*y[i];
            logy[i] = Math.log(y[i]);
        }
        double inf = Double.POSITIVE_INFINITY;
        
        for(int qq=1; qq < 8; qq++){
            if(Double.isNaN(logy[qq]) || logy[qq] == inf)
                logy[qq]=0;
        }
        
        double[] yb = new double[y.length];
        double[] ylogy = new double[y.length];
        double b = 1;
        
        for(int i=0; i< y.length;i++){
            ylogy[i] = y[i]*logy[i];
            yb[i] = y[i]*b;
            bgas += yb[i]+(8.31416*ylogy[i]*To);
        }
        
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