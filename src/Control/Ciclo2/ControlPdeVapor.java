/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

import Model.ModelConstantesKCSMat_C;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author leonardo
 */
public class ControlPdeVapor {
    
    private double Ps2;
    
    public ControlPdeVapor(double T, int ii, Session session) {
        double P = 1000;
        
        //RECUPERA TODOS OS DADOS DA TABELA DE CONSTANTES
        Criteria cr = session.createCriteria(ModelConstantesKCSMat_C.class); 
        cr.add(Restrictions.eq("cod", ii));
        List results = cr.list();
        
        ModelConstantesKCSMat_C constantesKCS = (ModelConstantesKCSMat_C)results.get(0); 
        double[] valores = constantesKCS.getValores();
        
        if(ii >= 1 && ii <= 5){
            //Ps2=(exp(C(ii,1)+(C(ii,2)/T)+(C(ii,3)*log(T))+(C(ii,4)*(T^C(ii,5)))))/1000;
            Ps2 = (Math.exp(valores[0]+(valores[1]/T)+(valores[2]*Math.log(T))+(valores[3]*(Math.pow(T,valores[4])))))/1000;
        }else if(ii >= 10 && ii <= 18){
            //Ps2=(exp(C(ii,1)+(C(ii,2)/T)+(C(ii,3)*log(T))+(C(ii,4)*(T^C(ii,5)))));
            Ps2 = (Math.exp(valores[0]+(valores[1]/T)+(valores[2]*Math.log(T))+(valores[3]*(Math.pow(T,valores[4])))));
        }else{
            ControlConstantes constantes = new ControlConstantes(T, P, ii, session);
            //Ps2=Pc*exp(((C(ii,1)*(1-(T/Tc)))+ (C(ii,2)*((1-(T/Tc))^1.5))+(C(ii,3)*((1-(T/Tc))^2.5))+(C(ii,4)*((1-(T/Tc))^5)))/(T/Tc));
            Ps2=constantes.getPc()*Math.exp(((valores[0])*(1-(T/constantes.getTc()))+(valores[1])*Math.pow((1-(T/constantes.getTc())),1.5)+(valores[2]*Math.pow((1-(T/constantes.getTc())),2.5))+(valores[3])*(Math.pow(1-(T/constantes.getTc()),5)))/(T/constantes.getTc()));
        }
   
        if(ii == 10){
            //Ps2=(exp(C(ii,1)+(C(ii,2)/T)+(C(ii,3)*log(T))+(C(ii,4)*(T^C(ii,5)))))/1000;
            Ps2=(Math.exp(valores[0]+(valores[1]/T)+(valores[2]*Math.log(T))+(valores[3]*(Math.pow(T,valores[4])))))/1000;
        }
        if(ii >= 23){
            //Ps2=exp(C(ii,1)+(C(ii,2)/(T+C(ii,3)))+(C(ii,4)*log(T))+(C(ii,5)*T^C(ii,6)));
            Ps2 = Math.exp(valores[0]+(valores[1]/(T+valores[2]))+(valores[3]*Math.log(T))+(valores[4]*Math.pow(T,valores[5])));
        }    
    }

    public double getPs2() {
        return Ps2;
    }

    public void setPs2(double Ps2) {
        this.Ps2 = Ps2;
    }
    
    
}
