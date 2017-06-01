/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.Control.Ciclo2;

import Ciclo1.Model.ModelCriticasKCSMat_K1;
import Ciclo1.Model.ModelCriticasKCSMat_Pc;
import Ciclo1.Model.ModelCriticasKCSMat_Tc;
import Ciclo1.Model.ModelCriticasKCSMat_w;
import java.util.List;
import org.apache.derby.vti.Restriction;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author leonardo
 */
public class ControlConstantes {
    private double beta, eps, delta, a, b, A, B, K0, Tr, R, Tc, Pc, K1, w;

    public ControlConstantes(double T, double P, int ii,Session session) {
        this.R = 8.314472;
        
        Criteria cr = session.createCriteria(ModelCriticasKCSMat_K1.class); 
        cr.add(Restrictions.eq("cod", ii));
        List results = cr.list();
        K1 = ((ModelCriticasKCSMat_K1)results.get(0)).getValor();
        
        cr = session.createCriteria(ModelCriticasKCSMat_w.class); 
        cr.add(Restrictions.eq("cod", ii));
        results = cr.list();
        w = ((ModelCriticasKCSMat_w)results.get(0)).getValor();
        
        cr = session.createCriteria(ModelCriticasKCSMat_Tc.class); 
        cr.add(Restrictions.eq("cod", ii));
        results = cr.list();
        Tc = ((ModelCriticasKCSMat_Tc)results.get(0)).getValor();
        
        cr = session.createCriteria(ModelCriticasKCSMat_Pc.class); 
        cr.add(Restrictions.eq("cod", ii));
        results = cr.list();
        Pc = ((ModelCriticasKCSMat_Pc)results.get(0)).getValor();
        
        K0 = 0.378893+1.489715*w-0.1713848*(Math.pow(w,2))+0.0196544*(Math.pow(w,3));
        Tr = T/Tc;
        double alp = Math.pow((1+K0*(1-(Math.pow(Tr,0.5)))+K1*(1-Tr)*(0.7-Tr)),2);
        a = (0.457235*(Math.pow(R,2))*(Math.pow(Tc,2))*alp)/Pc;
        b = (0.077796*R*Tc)/Pc;

        A=(a*P)/((Math.pow(R,2))*(Math.pow(T,2)));

        B=(b*P)/(R*T);

        beta=B-1;

        eps=A-3*(Math.pow(B,2))-2*B;

        delta=(Math.pow(B,3))+(Math.pow(B,2))-A*B;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public void seta(double a) {
        this.a = a;
    }

    public void setb(double b) {
        this.b = b;
    }

    public void setA(double A) {
        this.A = A;
    }

    public void setB(double B) {
        this.B = B;
    }

    public void setK0(double K0) {
        this.K0 = K0;
    }

    public void setTr(double Tr) {
        this.Tr = Tr;
    }

    public void setR(double R) {
        this.R = R;
    }

    public void setTc(double Tc) {
        this.Tc = Tc;
    }

    public void setPc(double Pc) {
        this.Pc = Pc;
    }

    public void setK1(double K1) {
        this.K1 = K1;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getBeta() {
        return beta;
    }

    public double getEps() {
        return eps;
    }

    public double getDelta() {
        return delta;
    }

    public double geta() {
        return a;
    }

    public double getb() {
        return b;
    }

    public double getA() {
        return A;
    }

    public double getB() {
        return B;
    }

    public double getK0() {
        return K0;
    }

    public double getTr() {
        return Tr;
    }

    public double getR() {
        return R;
    }

    public double getTc() {
        return Tc;
    }

    public double getPc() {
        return Pc;
    }

    public double getK1() {
        return K1;
    }

    public double getW() {
        return w;
    }

    
    
}
