/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

import Model.ModelCriticasKCSMat_Pc;
import Model.ModelCriticasKCSMat_Tc;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author alysonmp
 */
public class ControlParametros {
    
    private double Tevp1, P, Pemax, P1, Pe, Te, Tconop, Pconop, Pref, Tref, parada, Pcri, Tcri;
    private String mensagem;
    
    public ControlParametros(double Tf, int ii, Session session){
        Tevp1 = Tf-10;
        mensagem = "";
        
        Criteria cr = session.createCriteria(ModelCriticasKCSMat_Pc.class);
        cr.add(Restrictions.eq("cod", ii));
        List results = cr.list();
        
        ModelCriticasKCSMat_Pc criticas_pc = (ModelCriticasKCSMat_Pc) results.get(0);
        
        cr = session.createCriteria(ModelCriticasKCSMat_Tc.class);
        cr.add(Restrictions.eq("cod", ii));
        results = cr.list();
        
        ModelCriticasKCSMat_Tc criticas_tc = (ModelCriticasKCSMat_Tc) results.get(0);
        
        P = criticas_pc.getValor()*0.9;
        
        ControlPdeVapor pdevapor = new ControlPdeVapor(Tevp1, ii, session);
        P1 = pdevapor.getPs2();
        
        if(P > P1){
            Pemax = P1;
        }else{
            Pemax = P;
        }
        
        Pe = Pemax;
        
        ControlT_Ref T_Ref = new ControlT_Ref(Pe, ii, session);
        Te = T_Ref.getTref();
        Tconop=(40+273.15);
        
        pdevapor = new ControlPdeVapor(Tconop, ii, session);
        Pconop = pdevapor.getPs2();

        if(Pconop < 10){
            Pconop=10;
        }
        
        T_Ref = new ControlT_Ref(Pconop, ii, session);
        
        Pref = Pconop;
        Tref = Tconop;

        parada = Pe/Pconop;
        
        if(parada <= 1){
            //TTT=rrr*lkjjlkljk
            mensagem = "Não é possível fazer os cálculos";
            return;
        }
        
        Pcri = criticas_pc.getValor();
        Tcri = criticas_tc.getValor();
    }

    public double getPe() {
        return Pe;
    }

    public void setPe(double Pe) {
        this.Pe = Pe;
    }

    public double getTe() {
        return Te;
    }

    public void setTe(double Te) {
        this.Te = Te;
    }

    public double getTconop() {
        return Tconop;
    }

    public void setTconop(double Tconop) {
        this.Tconop = Tconop;
    }

    public double getPconop() {
        return Pconop;
    }

    public void setPconop(double Pconop) {
        this.Pconop = Pconop;
    }

    public double getPref() {
        return Pref;
    }

    public void setPref(double Pref) {
        this.Pref = Pref;
    }

    public double getTref() {
        return Tref;
    }

    public void setTref(double Tref) {
        this.Tref = Tref;
    }

    public double getPcri() {
        return Pcri;
    }

    public void setPcri(double Pcri) {
        this.Pcri = Pcri;
    }

    public double getTcri() {
        return Tcri;
    }

    public void setTcri(double Tcri) {
        this.Tcri = Tcri;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
}