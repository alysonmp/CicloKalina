/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlMassa {
    private double m, Q, Tfout, PINCH, Hlat, Hsen, Hsup, T1s;

    public ControlMassa(double H4, double H1, double H6, double P1, int ii, double Pref, double Tref, double T1, double T6, double SUP, double PINCH, double mf, double Tf, double Pf, int compressor, Session session) {
        T1s = T1 - SUP;
        
        int FON = 0,titulo=0;
        double Hsat,Hin, tit, Qfon1 = 0, Burbuja=0;
        double[] X = null;

        double Tf2 = T1s + PINCH;
        
        ControlH_Sistema hSistema = new ControlH_Sistema(T1s, P1, Pref, Tref, ii, session);
        ControlCalor calor = new ControlCalor(compressor, Tf, Tf2, session);

        double EntEVP = H1 - hSistema.getHL();
        
        if(Tf2 > Tf){
            //erro
        }
        
        m = calor.getQfon1()/EntEVP;
        double QTf = m * (H1 - H6);
        double QTfcor = QTf* 1.02;
        ControlTSaida tSaidaF = new ControlTSaida(compressor, Tf, QTfcor,session);
        
        //Correcao da massa
        
        int it = 0;
        /*while((T6+5) > tSaidaF.getTfout()){
            PINCH+=0.1;
            it++;
            if(it > 10000)
                break;
            T1s = T1 - SUP;
            
        }*/
        Tf2=T1s+PINCH;
        
        
        if(FON == 1){
            hSistema = new ControlH_Sistema(Tf, Pf, Pref, Tref, 0, session);
            Hin = hSistema.getHL() + (titulo*(hSistema.getHV()-hSistema.getHL()));
            hSistema = new ControlH_Sistema(Tf2, Pf, Pref, Tref, 0, session);
            ControlT_Ref tRef = new ControlT_Ref(Pf, 0, session);
            if(tRef.getTref() < Tf2){
                Hsat =  hSistema.getHV();
                tit = 0;
            }else{
                Hsat = hSistema.getHL();
                tit = 0;
            }
            Qfon1 = (Hin - Hsat);
        }else{
            ControlExergia_Gases exergia_Gases1 = new ControlExergia_Gases(mf, Tf, X, session);
            ControlExergia_Gases exergia_Gases2 = new ControlExergia_Gases(mf, Tf2, X, session);
            Qfon1=(exergia_Gases1.getHin() - exergia_Gases2.getHin())*mf;
        }
        
        m = (Qfon1/EntEVP);
        QTf=m*(H1-H6);
        
        double Test=T6;
        double erro=1;
        double DT=5;
        it=0;
        while(erro>0.0005){
            it=it+1;
                if(it > 100000)
                    break;

            if(FON==1){
//               [HL, HV] = H_sistema(Test, Pf, Pref, Tref, 1);  
//                Hout=HL + (tit*(HV-HL)) ;
//                Q=(Hin-Hout)*mf;
//                erro=abs((QTf-Q)/QTf);
//                Burbuja=Q-QTf;
//                if erro>0.0005 & Burbuja<0
//                   Test=Test-DT;
//                    DT=DT/2;
//                  if DT<0.0005;
//                    DT=0.0005456321;
//                  end
            }else if(erro>0.0005 && Burbuja>0){
                    Test=Test+DT;
                    DT=DT/2;
                    if(DT<0.0005)
                        DT=0.0003975313;
            }else{ 
                ControlExergia_Gases exergia_Gases1 = new ControlExergia_Gases(mf, Tf, X, session);
                ControlExergia_Gases exergia_Gases = new ControlExergia_Gases(mf, Test, X, session);
                Q = exergia_Gases1.getHin() - exergia_Gases.getHin()*mf;
                erro=Math.abs((QTf-Q)/QTf);
                Burbuja=Q-QTf;
                if(erro>0.0005 && Burbuja<0){
                    Test=Test-DT;
                    DT=DT/2;
                    
                }else if(erro>0.0005 & Burbuja>0){
                    Test=Test+DT;
                    DT=DT/2;
                    if(DT<0.0005)
                        DT=0.0003975313;
                }
                if(erro < 0.0005)
                    DT=0.0005456321; 
                if(Test<=T6)
                    erro=0;
            } 
        Tfout=Test;
        }
    }
}