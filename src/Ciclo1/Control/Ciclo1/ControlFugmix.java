/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Ciclo1;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class ControlFugmix {
    
    private double bpi, bpj, cpi, cpj, vml, vmv, 
            ap_1, ap_2, ap_3i, ap_3j, ap_4i, ap_4j, api, apj,
            QQ, DD_l,DD_v, D1_v, D1_l,
            F1v_i, F1l_i, F1v_j, F1l_j,
            phil_i, phiv_i, phil_j, phiv_j;
    
    private ControlConstantesMix constantesMix;
    private ControlConstantes constantes;
    private ControlZetamix zetamix;
    
    public ControlFugmix(double T, double P, double zi, Session session){
        
        constantesMix = new ControlConstantesMix(T, P, zi);
        constantes = constantesMix.getConstantes();
        zetamix = new ControlZetamix(constantesMix.getAm(), constantesMix.getBm(), constantesMix.getCm());
        
        bpi = constantes.getbi()-constantesMix.getbm();
        bpj = constantes.getbj()-constantesMix.getbm();
        cpi = constantes.getci()-constantesMix.getcm();
        cpj = constantes.getcj()-constantesMix.getcm();
        
        vml = zetamix.getZl()*constantes.getR()*T/P;
        vmv = zetamix.getZv()*constantes.getR()*T/P;
        
        //ap_1=((-2)*((zi^2*ai)+(zj^2*aj)+(zi*zj*((ai*aj)^0.5)*(1-kij))+(zj*zi*((aj*ai)^0.5)*(1-kji))));
        ap_1 = ((-2)*((Math.pow(zi,2)*constantes.getai())+(Math.pow(constantesMix.getzj(), 2)*constantes.getaj())+(zi*constantesMix.getzj()*(Math.pow(constantes.getai()*constantes.getaj(), 0.5))*(1-constantesMix.getKij()))+(constantesMix.getzj()*zi*(Math.pow(constantes.getaj()*constantes.getai(), 0.5))*(1-constantesMix.getKji()))));
        //ap_2=((-3)*((zi^2*zj*((ai*aj)^0.5)*(kij-kji))+(zj^2*zi*((aj*ai)^0.5)*(kji-kij))));
        ap_2 = ((-3)*((Math.pow(zi,2)*constantesMix.getzj()*((Math.pow((constantes.getai()*constantes.getaj()),0.5))*(constantesMix.getKij()-constantesMix.getKji()))+(Math.pow(constantesMix.getzj(), 2)*zi*(Math.pow(constantes.getaj()*constantes.getai(),0.5))*(constantesMix.getKji()-constantesMix.getKij())))));
        //ap_3i=(2*(zi*zj*((ai*aj)^0.5)*(kij-kji)));
        ap_3i = (2*(zi*constantesMix.getzj()*(Math.pow((constantes.getai()*constantes.getaj()),0.5))*(constantesMix.getKij()-constantesMix.getKji())));
        //ap_3j=(2*(zj*zi*((ai*aj)^0.5)*(kji-kij)));
        ap_3j = (2*(constantesMix.getzj()*zi*(Math.pow((constantes.getai()*constantes.getaj()),0.5))*(constantesMix.getKji()-constantesMix.getKij())));
        //ap_4i=(2*zi*ai)+(zj*((ai*aj)^0.5)*(1-kij+1-kji+((kji-kij)*zj)));
        ap_4i = ((2*zi*constantes.getai())+(constantesMix.getzj()*(Math.pow((constantes.getai()*constantes.getaj()),0.5))) * (1-constantesMix.getKij()+1-constantesMix.getKji()+((constantesMix.getKji() - constantesMix.getKij())*constantesMix.getzj())));
        //ap_4j=(2*zj*aj)+(zi*((ai*aj)^0.5)*(1-kji+1-kij+((kij-kji)*zi)));
        ap_4j = ((2*constantesMix.getzj()*constantes.getaj())+(zi*(Math.pow((constantes.getai()*constantes.getaj()),0.5))) * (1-constantesMix.getKji()+1-constantesMix.getKij()+((constantesMix.getKij() - constantesMix.getKji())*zi)));

        //api=ap_1+ap_2+ap_3i+ap_4i;
        api = ap_1 + ap_2 + ap_3i + ap_4i;
        //apj=ap_1+ap_2+ap_3j+ap_4j;
        apj = ap_1 + ap_2 + ap_3j + ap_4j;
        
        //QQ=(bm^2+(6*bm*cm)+cm^2)*(-1);
        QQ = (Math.pow(constantesMix.getbm(),2)+((6*constantesMix.getbm()*constantesMix.getcm())+Math.pow(constantesMix.getcm(),2)))*(-1);
        //DD_l=vml^2+(vml*(bm+cm))-(bm*cm);
        DD_l = (Math.pow(vml, 2) + (vml * (constantesMix.getbm()+constantesMix.getcm()))-(constantesMix.getbm()*constantesMix.getcm()));
        //DD_v=vmv^2+(vmv*(bm+cm))-(bm*cm);
        DD_v = (Math.pow(vmv, 2) + (vmv * (constantesMix.getbm()+constantesMix.getcm()))-(constantesMix.getbm()*constantesMix.getcm()));
        //D1_l=(1/((-QQ)^0.5))*log(((2*vml)+bm+cm-((-QQ)^0.5))/((2*vml)+bm+cm+((-QQ)^0.5)));
        D1_l = ((1/(Math.pow(-QQ,0.5)))*Math.log(((2*vml)+constantesMix.getbm()+constantesMix.getcm()-(Math.pow(-QQ,0.5)))/((2*vml)+constantesMix.getbm()+constantesMix.getcm()+(Math.pow(-QQ,0.5)))));
        //D1_v=(1/((-QQ)^0.5))*log(((2*vmv)+bm+cm-((-QQ)^0.5))/((2*vmv)+bm+cm+((-QQ)^0.5)));
        D1_v = ((1/(Math.pow(-QQ,0.5)))*Math.log(((2*vmv)+constantesMix.getbm()+constantesMix.getcm()-(Math.pow(-QQ,0.5)))/((2*vmv)+constantesMix.getbm()+constantesMix.getcm()+(Math.pow(-QQ,0.5)))));
    
        
        //F1v_i=((vmv/(QQ*DD_v))*(bm^2+cm^2+(6*bm*cm)+(bpi*bm)+(3*bpi*cm)+(3*bm*cpi)+(cm*cpi)))+((1/(QQ*DD_v))*((bpi*cm^2)+(bm^2*cpi)-(bm*bpi*cm)-(bm*cm*cpi)))+((D1_v/QQ)* ((bm*bpi)+(3*bm*cpi)-(bm^2)+(3*bpi*cm)+(cm*cpi)-(cm^2)-(6*bm*cm)));
        F1v_i = ((vmv/(QQ*DD_v))*(Math.pow(constantesMix.getbm(), 2)+Math.pow(constantesMix.getcm(),2)+(6*constantesMix.getbm()*constantesMix.getcm())+(bpi*constantesMix.getbm())+(3*bpi*constantesMix.getcm())+(3*constantesMix.getbm()*cpi)+(constantesMix.getcm()*cpi)))
                + ((1/(QQ*DD_v))*((bpi*Math.pow(constantesMix.getcm(), 2))+(Math.pow(constantesMix.getbm(),2)*cpi)-(constantesMix.getbm()*bpi*constantesMix.getcm())-(constantesMix.getbm()*constantesMix.getcm()*cpi)))
                + ((D1_v/QQ)* ((constantesMix.getbm()*bpi)+(3*constantesMix.getbm()*cpi)-(Math.pow(constantesMix.getbm(),2))+(3*bpi*constantesMix.getcm())+(constantesMix.getcm()*cpi)-(Math.pow(constantesMix.getcm(),2))-(6*constantesMix.getbm()*constantesMix.getcm())));
        
        //F1l_i=((vml/(QQ*DD_l))*(bm^2+cm^2+(6*bm*cm)+(bpi*bm)+(3*bpi*cm)+(3*bm*cpi)+(cm*cpi)))+((1/(QQ*DD_l))*((bpi*cm^2)+(bm^2*cpi)-(bm*bpi*cm)-(bm*cm*cpi)))+((D1_l/QQ)* ((bm*bpi)+(3*bm*cpi)-(bm^2)+(3*bpi*cm)+(cm*cpi)-(cm^2)-(6*bm*cm)));
        F1l_i = ((vml/(QQ*DD_l))*(Math.pow(constantesMix.getbm(), 2)+Math.pow(constantesMix.getcm(),2)+(6*constantesMix.getbm()*constantesMix.getcm())+(bpi*constantesMix.getbm())+(3*bpi*constantesMix.getcm())+(3*constantesMix.getbm()*cpi)+(constantesMix.getcm()*cpi)))
                + ((1/(QQ*DD_l))*((bpi*Math.pow(constantesMix.getcm(), 2))+(Math.pow(constantesMix.getbm(),2)*cpi)-(constantesMix.getbm()*bpi*constantesMix.getcm())-(constantesMix.getbm()*constantesMix.getcm()*cpi)))
                + ((D1_l/QQ)* ((constantesMix.getbm()*bpi)+(3*constantesMix.getbm()*cpi)-(Math.pow(constantesMix.getbm(),2))+(3*bpi*constantesMix.getcm())+(constantesMix.getcm()*cpi)-(Math.pow(constantesMix.getcm(),2))-(6*constantesMix.getbm()*constantesMix.getcm())));
    
        //F1v_j=((vmv/(QQ*DD_v))*(bm^2+cm^2+(6*bm*cm)+(bpj*bm)+(3*bpj*cm)+(3*bm*cpj)+(cm*cpj)))+((1/(QQ*DD_v))*((bpj*cm^2)+(bm^2*cpj)-(bm*bpj*cm)-(bm*cm*cpj)))+((D1_v/QQ)* ((bm*bpj)+(3*bm*cpj)-(bm^2)+(3*bpj*cm)+(cm*cpj)-(cm^2)-(6*bm*cm)));
        F1v_j = ((vmv/(QQ*DD_v))*(Math.pow(constantesMix.getbm(), 2)+Math.pow(constantesMix.getcm(),2)+(6*constantesMix.getbm()*constantesMix.getcm())+(bpj*constantesMix.getbm())+(3*bpj*constantesMix.getcm())+(3*constantesMix.getbm()*cpj)+(constantesMix.getcm()*cpj)))
                + ((1/(QQ*DD_v))*((bpj*Math.pow(constantesMix.getcm(), 2))+(Math.pow(constantesMix.getbm(),2)*cpj)-(constantesMix.getbm()*bpj*constantesMix.getcm())-(constantesMix.getbm()*constantesMix.getcm()*cpj)))
                + ((D1_v/QQ)* ((constantesMix.getbm()*bpj)+(3*constantesMix.getbm()*cpj)-(Math.pow(constantesMix.getbm(),2))+(3*bpj*constantesMix.getcm())+(constantesMix.getcm()*cpj)-(Math.pow(constantesMix.getcm(),2))-(6*constantesMix.getbm()*constantesMix.getcm())));
        
        //F1l_j=((vml/(QQ*DD_l))*(bm^2+cm^2+(6*bm*cm)+(bpj*bm)+(3*bpj*cm)+(3*bm*cpj)+(cm*cpj)))+((1/(QQ*DD_l))*((bpj*cm^2)+(bm^2*cpj)-(bm*bpj*cm)-(bm*cm*cpj)))+((D1_l/QQ)* ((bm*bpj)+(3*bm*cpj)-(bm^2)+(3*bpj*cm)+(cm*cpj)-(cm^2)-(6*bm*cm)));
        F1l_j = ((vml/(QQ*DD_l))*(Math.pow(constantesMix.getbm(), 2)+Math.pow(constantesMix.getcm(),2)+(6*constantesMix.getbm()*constantesMix.getcm())+(bpj*constantesMix.getbm())+(3*bpj*constantesMix.getcm())+(3*constantesMix.getbm()*cpj)+(constantesMix.getcm()*cpj)))
                + ((1/(QQ*DD_l))*((bpj*Math.pow(constantesMix.getcm(), 2))+(Math.pow(constantesMix.getbm(),2)*cpj)-(constantesMix.getbm()*bpj*constantesMix.getcm())-(constantesMix.getbm()*constantesMix.getcm()*cpj)))
                + ((D1_l/QQ)* ((constantesMix.getbm()*bpj)+(3*constantesMix.getbm()*cpj)-(Math.pow(constantesMix.getbm(),2))+(3*bpj*constantesMix.getcm())+(constantesMix.getcm()*cpj)-(Math.pow(constantesMix.getcm(),2))-(6*constantesMix.getbm()*constantesMix.getcm())));
        
        
        //phil_i=(exp((log(vml/(vml-bm)))+((bm+bpi)/(vml-bm))+((api*D1_l)/(R*T))+((am/(R*T))*F1l_i)-log(Zlm)))*zi*P;
        phil_i = (Math.exp((Math.log(vml/(vml-constantesMix.getbm())))+((constantesMix.getbm()+bpi)/(vml-constantesMix.getbm()))+((api*D1_l)/(constantes.getR()*T))+((constantesMix.getam()/(constantes.getR()*T))*F1l_i)-Math.log(zetamix.getZl())))*zi*P;
        
        //phiv_i=(exp((log(vmv/(vmv-bm)))+((bm+bpi)/(vmv-bm))+((api*D1_v)/(R*T))+((am/(R*T))*F1v_i)-log(Zvm)))*zi*P;
        phiv_i = (Math.exp((Math.log(vmv/(vmv-constantesMix.getbm())))+((constantesMix.getbm()+bpi)/(vmv-constantesMix.getbm()))+((api*D1_v)/(constantes.getR()*T))+((constantesMix.getam()/(constantes.getR()*T))*F1v_i)-Math.log(zetamix.getZv())))*zi*P;

        //phil_j=(exp((log(vml/(vml-bm)))+((bm+bpj)/(vml-bm))+((apj*D1_l)/(R*T))+((am/(R*T))*F1l_j)-log(Zlm)))*(1-zi)*P;
        phil_j = (Math.exp((Math.log(vml/(vml-constantesMix.getbm())))+((constantesMix.getbm()+bpj)/(vml-constantesMix.getbm()))+((apj*D1_l)/(constantes.getR()*T))+((constantesMix.getam()/(constantes.getR()*T))*F1l_j)-Math.log(zetamix.getZl())))*(1-zi)*P;
        
        //phiv_j=(exp((log(vmv/(vmv-bm)))+((bm+bpj)/(vmv-bm))+((apj*D1_v)/(R*T))+((am/(R*T))*F1v_j)-log(Zvm)))*(1-zi)*P;
        phiv_j = (Math.exp((Math.log(vmv/(vmv-constantesMix.getbm())))+((constantesMix.getbm()+bpj)/(vmv-constantesMix.getbm()))+((apj*D1_v)/(constantes.getR()*T))+((constantesMix.getam()/(constantes.getR()*T))*F1v_j)-Math.log(zetamix.getZv())))*(1-zi)*P;

        
        //function [phil_i, phiv_i, phil_j, phiv_j] = fugmix(T, P, zi)

        //[Zlm, Zvm, Zm] = Zetamix(Am, Bm, Cm);
        
    }

    public double getPhil_i() {
        return phil_i;
    }

    public void setPhil_i(double phil_i) {
        this.phil_i = phil_i;
    }

    public double getPhiv_i() {
        return phiv_i;
    }

    public void setPhiv_i(double phiv_i) {
        this.phiv_i = phiv_i;
    }

    public double getPhil_j() {
        return phil_j;
    }

    public void setPhil_j(double phil_j) {
        this.phil_j = phil_j;
    }

    public double getPhiv_j() {
        return phiv_j;
    }

    public void setPhiv_j(double phiv_j) {
        this.phiv_j = phiv_j;
    }

    public ControlConstantesMix getConstantesMix() {
        return constantesMix;
    }

    public void setConstantesMix(ControlConstantesMix constantesMix) {
        this.constantesMix = constantesMix;
    }

    public ControlConstantes getConstantes() {
        return constantes;
    }

    public void setConstantes(ControlConstantes constantes) {
        this.constantes = constantes;
    }

    public ControlZetamix getZetamix() {
        return zetamix;
    }

    public void setZetamix(ControlZetamix zetamix) {
        this.zetamix = zetamix;
    }

}
