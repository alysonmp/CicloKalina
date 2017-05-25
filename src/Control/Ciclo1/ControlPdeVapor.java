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
public class ControlPdeVapor {
    
    private double Ps2;
    
    private Session session;

    public ControlPdeVapor(double T, int ii, Session session) {
        this.session = session;
        double P = 1000;
        
        //RECUPERA TODOS OS DADOS DA TABELA DE CONSTANTES
        Criteria cr = this.session.createCriteria(ModelContantes.class); 
        List results = cr.list();
        
        ModelConstantes constantesMat = (ModelConstantes)results.get(0); 
        double[][] valores = constantesMat.getValores();
        
        if(ii >= 1 && ii <= 5){
            //Ps2=(exp(C(ii,1)+(C(ii,2)/T)+(C(ii,3)*log(T))+(C(ii,4)*(T^C(ii,5)))))/1000;
            Ps2 = (Math.exp(valores[ii][0]+(valores[ii][1]/T)+(valores[ii][2]*Math.log(T))+(valores[ii][3]*(Math.pow(T,valores[ii][4])))))/1000;
        }else if(ii >= 10 && ii <= 18){
            //Ps2=(exp(C(ii,1)+(C(ii,2)/T)+(C(ii,3)*log(T))+(C(ii,4)*(T^C(ii,5)))));
            Ps2 = (Math.exp(valores[ii][0]+(valores[ii][1]/T)+(valores[ii][2]*Math.log(T))+(valores[ii][3]*(Math.pow(T,valores[ii][4])))));
        }else{
            ControlConstantes constantes = new ControlConstantes(T, P, ii, session);
            //Ps2=Pc*exp(((C(ii,1)*(1-(T/Tc)))+ (C(ii,2)*((1-(T/Tc))^1.5))+(C(ii,3)*((1-(T/Tc))^2.5))+(C(ii,4)*((1-(T/Tc))^5)))/(T/Tc));
            Ps2=constantes.getPc()*Math.exp(((valores[ii][0])*(1-(T/constantes.getTc()))+(valores[ii][1])*Math.pow((1-(T/constantes.getTc())),1.5)+(valores[ii][2]*Math.pow((1-(T/constantes.getTc())),2.5))+(valores[ii][3])*(Math.pow(1-(T/constantes.getTc()),5)))/(T/constantes.getTc()));
        }
   
        if(ii == 10){
            //Ps2=(exp(C(ii,1)+(C(ii,2)/T)+(C(ii,3)*log(T))+(C(ii,4)*(T^C(ii,5)))))/1000;
            Ps2=(Math.exp(valores[ii][0]+(valores[ii][1])/T)+(valores[ii][2]*Math.log(T))+(valores[ii][3]*(Math.pow(T,valores[ii][4]))))/1000;
        }
        if(ii >= 23){
            //Ps2=exp(C(ii,1)+(C(ii,2)/(T+C(ii,3)))+(C(ii,4)*log(T))+(C(ii,5)*T^C(ii,6)));
            Ps2 = Math.exp(valores[ii][0]+(valores[ii][1]/(T+valores[ii][2]))+(valores[ii][3]*Math.log(T))+(valores[ii][4]*Math.pow(T,valores[ii][5])));
        }    
    }
}
