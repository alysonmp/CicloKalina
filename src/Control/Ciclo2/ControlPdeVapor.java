/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

import Model.Ciclo2.ModelFluidos;
import Model.ModelConstantesMat;
import Util.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author alysonmp
 */
public class ControlPdeVapor {
    
    double Psi, Psj, P, Pmax, Pmin, erro, burbuja, Pest, DP;
    private Session session;
    
    public ControlPdeVapor(double T, double x, Session session){
        this.session = session;
        
        //RECUPERA TODOS OS DADOS DA TABELA DE CONSTANTES
        Criteria cr = this.session.createCriteria(ModelConstantesMat.class); 
        List results = cr.list();
        
        //UTILIZA A PRIMEIRA LINHA BUSCADA, VARIÁVEL C1
        ModelConstantesMat constantesMat = (ModelConstantesMat)results.get(0); 
        double[][] valores = constantesMat.getValores();
        
        Psi = (Math.exp(valores[0][0]) + (valores[0][1]/T) + (valores[0][2]*Math.log(T)) + (valores[0][3]*(Math.pow(T, valores[0][4]))))/100000;
        
        //UTILIZA A SEGUNDA LINHA BUSCADA, VARIÁVEL C2
        results = cr.list();
        constantesMat = (ModelConstantesMat)results.get(1); 
        valores = constantesMat.getValores();
        
        Psj = (Math.exp(valores[0][0]) + (valores[0][1]/T) + (valores[0][2]*Math.log(T)) + (valores[0][3]*(Math.pow(T, valores[0][4]))))/100000;
        
        if(x==0){
            P = Psj;
            Pmax = 210.23; //%bar
        }else{
            P=Psi;
            Pmax=109.34; //%bar
        }
        
        if(P < Pmax){
            ControlFug fug = new ControlFug(T, P, x);
            
            erro = Math.abs(fug.getFil()-fug.getFiv());
            Pest = P;
            DP = P*1/100;
            
            while(erro >= 0.00001){
                erro = Math.abs(fug.getFil()-fug.getFiv());
                burbuja = fug.getFil()-fug.getFiv();
                if(erro > 0.00001 && burbuja < 0){
                    Pest = Pest-DP;
                    DP = DP/2;
                    if(DP<0.0025){
                        DP=0.00214987569731;
                    }
                }else if(erro > 0.00001 && burbuja > 0){
                    Pest = Pest+DP;
                    DP = DP/2;
                    if(DP < 0.0025){
                        DP=0.00219332541;
                    }
                }
            }
            
            if(x==0){
                Psi=0;
                Psj=Pest;
            }else{
                Psi=Pest;
                Psj=0;
            }
        }else{
            if(x == 0){
                Psi=0;
                Psj=Pmax;
            }
            else{
                Psi=Pmax;
                Psj=0;
            }
        }
    }

    public double getPsi() {
        return Psi;
    }

    public void setPsi(double Psi) {
        this.Psi = Psi;
    }

    public double getPsj() {
        return Psj;
    }

    public void setPsj(double Psj) {
        this.Psj = Psj;
    }

    
}
