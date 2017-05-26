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
    }
    
    
}
