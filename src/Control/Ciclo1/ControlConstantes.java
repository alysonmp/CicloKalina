/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlConstantes {
    private double beta, eps, delta, a, b, A, B, K0, Tr, R, Tc, Pc, K1, w;

    public ControlConstantes(double T, double P, double ii,Session session) {
    
        Criteria cr = session.createCriteria(ModelCriticas.class); 
        List results = cr.list();
        
        K0=0.378893+1.489715*w[ii]-0.1713848*(Math.pow(w[ii],2))+0.0196544*(Math.pow(w[ii],3));
        Tr=T/Tc[ii];
        alp=Math.pow((1+K0*(1-(Math.pow(Tr,0.5)))+K1[ii]*(1-Tr)*(0.7-Tr)),2);
        a=(0.457235*(Math.pow(R,2))*(Math.pow(Tc[ii],2))*alp)/Pc[ii];
        b=(0.077796*R*Tc[ii])/Pc[ii];

        A=(a*P)/((Math.pow(R,2))*(Math.pow(T,2)));

        B=(b*P)/(R*T);

        beta=B-1;

        eps=A-3*(Math.pow(B,2))-2*B;

        delta=(Math.pow(B,3))+(Math.pow(B,2))-A*B;

        K1=K1[ii];
        Pc=Pc[ii];
        Tc=Tc[ii];
        w=w[ii];
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

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
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

    public double getA() {
        return a;
    }

    public double getB() {
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
