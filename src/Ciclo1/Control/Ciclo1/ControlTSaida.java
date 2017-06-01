/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Ciclo1;

import Ciclo2.Model.ModelLinear;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlTSaida {

    private double Tfout;
        
    public ControlTSaida(int compressor, double Tf, double QTfcor, Session session) {        
        int x = compressor-1;
        
        //Recuperando dados da tabela Linear
        Criteria cr = session.createCriteria(ModelLinear.class); 
        List results = cr.list();
        
        ModelLinear l = (ModelLinear) results.get(x);
        double[] lin = l.getLin();
        
        
        switch (compressor) {
            case 1:
                //Tfout=(lin(x,1)+(lin(x,2).*QTfcor)+(lin(x,3).*QTfcor.^2)+(lin(x,4).*QTfcor.^3)+(lin(x,5).*QTfcor.^4)+(lin(x,6).*QTfcor.^5)+(lin(x,7).*QTfcor.^6)+(lin(x,8).*QTfcor.^7))+273.15
                Tfout = lin[0]+(lin[1]*QTfcor)+(lin[2]*Math.pow(QTfcor,2))+(lin[3]*Math.pow(QTfcor,3))+(lin[4]*Math.pow(QTfcor,4))+(lin[5]*Math.pow(QTfcor,5))+(lin[6]*Math.pow(QTfcor,6))+(lin[7]*Math.pow(QTfcor,7))+273.15;
                break;
            case 2:
                //Tfout=(lin(x,1)+(lin(x,2).*QTfcor)+(lin(x,3).*QTfcor.^2)+(lin(x,4).*QTfcor.^3)+(lin(x,5).*QTfcor.^4)+(lin(x,6).*QTfcor.^5)+(lin(x,7).*QTfcor.^6)+(lin(x,8).*QTfcor.^7)+(lin(x,9).*QTfcor.^8)+(lin(x,10).*QTfcor.^9))+273.15
                Tfout = lin[0]+(lin[1]*QTfcor)+(lin[2]*Math.pow(QTfcor,2))+(lin[3]*Math.pow(QTfcor,3))+(lin[4]*Math.pow(QTfcor,4))+(lin[5]*Math.pow(QTfcor,5))+(lin[6]*Math.pow(QTfcor,6))+(lin[7]*Math.pow(QTfcor,7))+(lin[8]*Math.pow(QTfcor,8))+(lin[9]*Math.pow(QTfcor, 9))+273.15;
                break;
            case 3:
                //Tfout=((lin(x,1)+(lin(x,3).*QTfcor)+(lin(x,5).*QTfcor.^2)+(lin(x,7).*QTfcor.^3)+(lin(x,9).*QTfcor.^4)+(lin(x,11).*QTfcor.^5))./(1+(lin(x,2).*QTfcor)+(lin(x,4).*QTfcor.^2)+(lin(x,6).*QTfcor.^3)+(lin(x,8).*QTfcor.^4)+(lin(x,10).*QTfcor.^5)))+273.15
                Tfout = lin[0]+(lin[2]*QTfcor)+(lin[4]*Math.pow(QTfcor,2))+(lin[6]*Math.pow(QTfcor,3))+(lin[8]*Math.pow(QTfcor,4))+(lin[10]*Math.pow(QTfcor,5))/(1+(lin[1]*QTfcor)+(lin[3]*Math.pow(QTfcor,2))+(lin[5]*Math.pow(QTfcor,3))+(lin[7]*Math.pow(QTfcor,4))+(lin[9]*Math.pow(QTfcor,5)))+273.15;
                break;
            case 4:
                //Tfout=(lin(x,1)+(lin(x,2).*QTfcor)+(lin(x,3).*QTfcor.^2)+(lin(x,4).*QTfcor.^3)+(lin(x,5).*QTfcor.^4)+(lin(x,6).*QTfcor.^5)+(lin(x,7).*QTfcor.^6)+(lin(x,8).*QTfcor.^7)+(lin(x,9).*QTfcor.^8)+(lin(x,10).*QTfcor.^9))+273.15
                Tfout=(lin[0]+(lin[1]*QTfcor)+(lin[2]*Math.pow(QTfcor,2))+(lin[3]*Math.pow(QTfcor,3))+(lin[4]*Math.pow(QTfcor,4))+(lin[5]*Math.pow(QTfcor,5))+(lin[6]*Math.pow(QTfcor,6))+(lin[7]*Math.pow(QTfcor,7))+(lin[8]*Math.pow(QTfcor,8))+(lin[9]*Math.pow(QTfcor,9)))+273.15;
                break;
            case 5:
                if(QTfcor < 11372.01){
                    //Tfout=(lin(x,1)+(lin(x,2).*QTfcor)+(lin(x,3).*QTfcor.^2)+(lin(x,4).*QTfcor.^3)+(lin(x,5).*QTfcor.^4)+(lin(x,6).*QTfcor.^5)+(lin(x,7).*QTfcor.^6)+(lin(x,8).*QTfcor.^7)+(lin(x,9).*QTfcor.^8)+(lin(x,10).*QTfcor.^9))+273.15
                    Tfout= lin[0]+(lin[1]*QTfcor)+(lin[2]*Math.pow(QTfcor,2))+(lin[3]*Math.pow(QTfcor,3))+(lin[4]*Math.pow(QTfcor,4))+(lin[5]*Math.pow(QTfcor,5))+(lin[6]*Math.pow(QTfcor,6))+(lin[7]*Math.pow(QTfcor,7))+(lin[8]*Math.pow(QTfcor,8))+(lin[9]*Math.pow(QTfcor,9))+273.15;
                }else{
                    //Tfout=(lin(x,1)+(lin(x,2).*QTfcor)+(lin(x,3)./QTfcor.^1)+(lin(x,4).*QTfcor.^2)+(lin(x,5)./QTfcor.^2)+(lin(x,6).*QTfcor.^3)+(lin(x,7)./QTfcor.^3)+(lin(x,8).*QTfcor.^4)+(lin(x,9)./QTfcor.^4)+(lin(x,10).*QTfcor.^5))+273.15
                    Tfout= lin[0]+(lin[1]*QTfcor)+(lin[2]/Math.pow(QTfcor,1))+(lin[3]*Math.pow(QTfcor,2))+(lin[4]/Math.pow(QTfcor,2))+(lin[5]*Math.pow(QTfcor,3))+(lin[6]/Math.pow(QTfcor,3))+(lin[7]*Math.pow(QTfcor,4))+(lin[8]/Math.pow(QTfcor,4))+(lin[9]*Math.pow(QTfcor,5))+273.15;
                }   break;
            default:
                break;
        }
    }

    public double getTfout() {
        return Tfout;
    }

    public void setTfout(double Tfout) {
        this.Tfout = Tfout;
    }
    
    
}
