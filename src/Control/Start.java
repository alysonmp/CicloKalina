/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.Ciclo1.ControlAreas;
import Control.Ciclo1.ControlBalanco;
import Control.Ciclo1.ControlBomba;
import Control.Ciclo1.ControlDiamTH17;
import Control.Ciclo1.ControlInterpolacao;
import Control.Ciclo1.ControlMassa;
import Control.Ciclo1.ControlRegenerador;
import Control.Ciclo1.ControlSF;
import Control.Ciclo1.ControlT_Ref;
import Control.Ciclo1.ControlTurbina;
import Control.TabelaFluidos.ControlWaterGas;
import Model.TabelasFluidos.ModelWaterGas;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class Start {
    
    private double mf, Pf, P1, P2, SUP, eff, PINCH, T1, P4, T4, P5, P3, P6, Tcontrol, Tcontrol2, H1, H2, S1, S2, T2, H2s, DH2s, sp, v2, DHT, S4, S5, H4, H5, T5, S3, H3, T3, S6, T6, IHR, Q, Tfout, PP, Hlat, Hsen, Hsup, T1s, Wt, Wb, Qevp, Qcon, ec, Qreg, Qreg1, Wn, Acon, Aevp, Areg, AT, ecg, Dr;
    private int ii;
    private Session session;
    
    public Start(int compressor, int flu, double Pe, double Pconop, double Tconop, double Tee, double Tf, double Tcri, double Teff, double Tref, double Pref, double m, double Beff, double G, double H6, Session session){
        this.session = session;
        if(compressor == 5){
            mf = 2.8400; //%kmol/s
        }else{
            mf = 1.5375; //%kmol/s
        }
        Pf = 101.325;
        P1 = Pe*0.8;
        P2 = Pconop;
        SUP = 5;
        eff = 0.0;
        PINCH = 10;
        ii = flu;
        
        //ControlT_Ref TRef = new ControlT_Ref(Pe, ii, session);
        T1 = Tee+SUP;
        P2 = Pconop; //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%Pcon È variavel
        P4 = P2;
        T4 = Tconop;
        P5 = P1;
        P3 = P2;
        P6 = P1;
        Tcontrol=Tf-T1;
        
        if(Tcontrol < 9.5){
            //jkljkljkljkljkl*jkljkljkjkl
        }

        Tcontrol2 = Tcri-T1;
        if(Tcontrol2 <= 2){ 
           //jkljkljkljkljkl*jkljkljkjkl
        }
        
        if(P1 < 303.1){
            //jkljkljkljkljkl*jkljkljkjkl
        }
        
        /*ControlTurbina turbina = new ControlTurbina(Teff, P1, T1, P2, Pref, Tref, ii, session);
        H1 = turbina.getH1();
        H2 = turbina.getIsoTurbina().getH2();
        S1 = turbina.getS1();
        S2 = turbina.getIsoTurbina().getS2();
        T2 = turbina.getIsoTurbina().getT2();
        H2s = turbina.getIsoTurbina().getH2s();*/

        ControlInterpolacao interpolacao = new ControlInterpolacao(ii, Pref, Tref, session);
        Object gas = interpolacao.getGas();
        Object liquido = interpolacao.getLiquido();
        
        Field[] gases = gas.getClass().getFields();
        System.out.println(gases[0]);
        Class<?> classe = gas.getClass();
        Field[] atributos = classe.getDeclaredFields();
        
        
        System.out.println(gas.getClass().getName());
        
        DH2s = H1-H2s;
        
        ControlSF sf = new ControlSF(T2, P2, ii, m, DH2s, session);
        sp = sf.getSp();
        v2 = sf.getV2();
        DHT = sf.getDHT();
        
        ControlDiamTH17 diamTH17 = new ControlDiamTH17(v2, DHT, session);
        Dr = diamTH17.getDr();
        Teff = diamTH17.getTeff();
                
        if(Teff < 0.8){
            ControlTurbina turbina = new ControlTurbina(Teff, P1, T1, P2, Pref, Tref, ii, session);
            H1 = turbina.getH1();
            H2 = turbina.getIsoTurbina().getH2();
            S1 = turbina.getS1();
            S2 = turbina.getIsoTurbina().getS2();
            T2 = turbina.getIsoTurbina().getT2();
            H2s = turbina.getIsoTurbina().getH2s();
            
            DH2s=H1-H2s;
            
            sf = new ControlSF(T2, P2, ii, m, DH2s, session);
            sp = sf.getSp();
            v2 = sf.getV2();
            DHT = sf.getDHT();
            
            diamTH17 = new ControlDiamTH17(v2, DHT, session);
            Dr = diamTH17.getDr();
            Teff = diamTH17.getTeff();
                    
            ControlBomba bomba = new ControlBomba(Beff, P1, T1, P4, T4, Pref, Tref, ii, session);
            S4 = bomba.getS4();
            H4 = bomba.getH4();
            S5 = bomba.getS5();
            H5 = bomba.getS5();
            T5 = bomba.getT5();
            
            ControlRegenerador regenerador = new ControlRegenerador(G, H2, H5, S2, S5, P2, T2, P5, T5, P1, Pconop, Tconop, Pref, Tref, ii, eff, session);
            S3 = regenerador.getS3();
            H3 = regenerador.getH3();
            S6 = regenerador.getS6();
            T3 = regenerador.getT3();
            T6 = regenerador.getT6();
            IHR = regenerador.getIHR();
            eff = IHR;
            
            ControlMassa massa = new ControlMassa(H4, H1, H6, P1, ii, Pref, Tref, T1, T6, SUP, PINCH, mf, Tf, Pf, compressor, session);
            m = massa.getM();
            Q = massa.getQ();
            Tfout = massa.getTfout();
            PP = massa.getPINCH();
            Hlat = massa.getHlat();
            Hsen = massa.getHsen();
            Hsup = massa.getHsup();
            T1s = massa.getT1s();
            
            ControlBalanco balanco = new ControlBalanco(T1, H1, H2, H3, H4, H5, H6, S1, S2, S3, S4, S5, S6, m, Pref, Tref);
            Wt = balanco.getWt();
            Wb = balanco.getWb();
            Qevp = balanco.getQevp();
            Qcon = balanco.getQcon();
            ec = balanco.getEc();
            Qreg = balanco.getQreg();
            Qreg1 = balanco.getQreg1();
            Wn = balanco.getWn();
            
            ControlAreas areas = new ControlAreas(T1, T2, T3, T4, T5, T6, Tf, Tfout, Qevp, Qcon, Qreg, eff, Hlat, Hsen, Hsup, T1s, PP, SUP, m, Pref, Tref, P4, ii, H3, compressor, session);
            Acon = areas.getAcon();
            Aevp = areas.getAevp();
            Areg = areas.getAreg();
            
            AT = Acon+Aevp+Areg;
            ecg = ec*100;
        }
    }   
}