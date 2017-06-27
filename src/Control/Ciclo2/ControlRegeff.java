package Control.Ciclo2;

import Control.Interpolacao.ControlInterpolacaoGas;
import Control.Interpolacao.ControlInterpolacaoLiquido;
import Model.ModelCore;
import Model.ModelCriticasKCSMat_PM;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

public class ControlRegeff {

    private double AT, Aho, Aco, Vhx, Lh, Lc, L3, DPh, DPc;
    
    public ControlRegeff(int ii, double UAREG, double T2, double T3, double T5, double T6, double m, double eff, double P5, double P6, double P2, double P3, double km, Session session) {
        
        double Rehsup, fhsup, jhsup, Recsup, fcsup,jcsup,hhsup,hcsup,m1sup,lh,m2sup,
                lc,e1sup,e2sup,e1gsup,e2gsup,iUsup,Ugsup,Ahsup,Ahosup,Acsup,Acosup,
                Lcsup,Lhsup,DPhsup,DPcsup,Afrh,Afrc,Gh,Gc,NTU,ep,Nc, Nh,Acc,Ach;
        double Cregmin,Ghsup,Gcsup;
        
        double DPregh=P2*1000*4/100;
        double DPregc=P5*1000*4/100;
        
        ControlInterpolacaoGas propV = new ControlInterpolacaoGas(ii,P2,T2,session);
        double k2 = propV.getKv_g();
        double Cp2 = propV.getCpv_g();
        double MU2 = propV.getMuv_g();
        double Pr2 = propV.getPrv_g();
        double Vc2 = propV.getVcv_g();
        
        propV = new ControlInterpolacaoGas(ii,P3,T3,session);
        double k3 = propV.getKv_g();
        double Cp3 = propV.getCpv_g();
        double MU3 = propV.getMuv_g();
        double Pr3 = propV.getPrv_g();
        double Vc3 = propV.getVcv_g();
        
        ControlInterpolacaoLiquido propL = new ControlInterpolacaoLiquido(ii, P5, T5, session);
        double k5 = propL.getKv_l();
        double Cp5 = propL.getCpv_l();
        double MU5 = propL.getMuv_l();
        double Pr5 = propL.getPrv_l();
        double Vc5 = propL.getVcv_l();
        
        propL = new ControlInterpolacaoLiquido(ii, P6, T6, session);
        double k6 = propL.getKv_l();
        double Cp6 = propL.getCpv_l();
        double MU6 = propL.getMuv_l();
        double Pr6 = propL.getPrv_l();
        double Vc6 = propL.getVcv_l();
        
        Criteria cr = session.createCriteria(ModelCriticasKCSMat_PM.class);
        List results = cr.list();
        ModelCriticasKCSMat_PM PMii = (ModelCriticasKCSMat_PM)results.get(ii);
        ModelCriticasKCSMat_PM PM1 = (ModelCriticasKCSMat_PM)results.get(1);

        ControlConstantes constantes = new ControlConstantes(T6, P6, ii, session);
        ControlZeta zeta = new ControlZeta(constantes.getBeta(),constantes.getEps(),constantes.getDelta());
        double D6=1/(((zeta.getZl()*constantes.getR()*T6)/P6)/PMii.getValor());  
        
        constantes = new ControlConstantes(T5, P5, ii, session);
        zeta = new ControlZeta(constantes.getBeta(),constantes.getEps(),constantes.getDelta());
        double D5=1/(((zeta.getZl()*constantes.getR()*T5)/P5)/PMii.getValor());
        
        constantes = new ControlConstantes(T2, P2, ii, session);
        zeta = new ControlZeta(constantes.getBeta(),constantes.getEps(),constantes.getDelta());
        double D2=1/(((zeta.getZv()*constantes.getR()*T2)/P2)/PMii.getValor());
        
        constantes = new ControlConstantes(T3, P3, ii, session);
        zeta = new ControlZeta(constantes.getBeta(),constantes.getEps(),constantes.getDelta());
        double D3=1/(((zeta.getZv()*constantes.getR()*T3)/P3)/PMii.getValor()); 
        
        
        
        if(eff==0){
            NTU=0;
            ep=0;
            Nc=0;
            Nh=0;
            Gh=0;
            Gc=0;
            Acc=0;
            Ach=0; 
            Cregmin = 0;
            Ghsup = 0;
            Gcsup = 0;
        }else{
            double Ccreg=(m*(PMii.getValor())*((Cp5+Cp6)/2))/1000;
            double Chreg=(m*(PMii.getValor())*((Cp2+Cp3)/2))/1000;
            Cregmin = Double.min(Chreg,Ccreg); //???
            double Cregmax = Double.max(Chreg,Ccreg);
            double C=Cregmin/Cregmax;
            double epsilonreg1 = (Chreg*(T2-T3))/(Cregmin*(T2-T5));
            NTU=(UAREG)/Cregmin;
            double epsilonreg2 = 1-Math.exp((Math.exp(Math.pow(-NTU,0.78)*C)-1)*(Math.pow(NTU,0.22)/C));
            ep = Double.min(epsilonreg1,epsilonreg2);
            double Ncreg=10*C*NTU/0.8;
            double Nhreg=1.1*NTU/0.8;
            Ghsup=Math.pow((((0.25)/(Math.pow(Pr2,(2/3))*Nhreg))*(2*((D2+D3)/2)*DPregh)),0.5);//??
            Gcsup=Math.pow((((0.25)/(Math.pow(Pr5,(2/3))*Ncreg))*(2*((D5+D6)/2)*DPregc)),0.5);//??
            Acc=(m*(PMii.getValor()))/Gcsup;
            Ach=(m*(PMii.getValor()))/Ghsup; 
        }
 
        cr = session.createCriteria(ModelCore.class);
        results = cr.list();
        ModelCore core = (ModelCore)results.get(9);
        
        double Dh1 = core.getDh();
        double alp1 = core.getAlp();
        double del1 = core.getDel();
        double gam1 = core.getGam();
        double b1 = core.getB();
        double t1 = core.getT();
        double l1 = core.getL();
        double s1 = core.getS();
        double bet1 = core.getBet();
        double por1 = core.getPor();
        
        cr = session.createCriteria(ModelCore.class);
        results = cr.list();
        core = (ModelCore)results.get(9);
        
        double Dh2 = core.getDh();
        double alp2 = core.getAlp();
        double del2 = core.getDel();
        double gam2 = core.getGam();
        double b2 = core.getB();
        double t2 = core.getT();
        double l2 = core.getL();
        double s2 = core.getS();
        double bet2 = core.getBet();
        double por2 = core.getPor();
        
        double aa1=(b1*bet1)/(b1+b2+(2*0.0005));
        double aa2=(b2*bet2)/(b1+b2+(2*0.0005));
        double AA=aa1/aa2;
        double cf1=aa1*Dh1/4;
        double cf2=aa2*Dh2/4;
        double erro1=1;
        double erro2=1;
        
        while(erro1>0 || erro2>0){

            Rehsup= (Ghsup*Dh1)/((MU3+MU2)/2);
            fhsup= (9.6243*Math.pow(Rehsup,-0.7422)*Math.pow(alp1,-0.1856)*Math.pow(del1,0.3053)*Math.pow(gam1,-0.2659))*Math.pow((1+(7.669e-8*Math.pow(Rehsup,4.429)*Math.pow(alp1,0.920)*Math.pow(del1,3.767)*Math.pow(gam1,0.236))),0.1);
            jhsup= (0.6522*Math.pow(Rehsup,-0.5403)*Math.pow(alp1,-0.1541)*Math.pow(del1,0.1499)*Math.pow(gam1,-0.0678))*Math.pow((1+(5.269e-5*Math.pow(Rehsup,1.340)*Math.pow(alp1,0.504)*Math.pow(del1,0.456)*Math.pow(gam1,-1.055))),0.1);

            Recsup= (Gcsup*Dh2)/((MU5+MU6)/2);
            fcsup= (9.6243*Math.pow(Recsup,-0.7422)*Math.pow(alp2,-0.1856)*Math.pow(del2,0.3053)*Math.pow(gam2,-0.2659))*Math.pow((1+(7.669e-8*Math.pow(Recsup,4.429)*Math.pow(alp2,0.920)*Math.pow(del2,3.767)*Math.pow(gam2,0.236))),0.1);
            jcsup= (0.6522*Math.pow(Recsup,-0.5403)*Math.pow(alp2,-0.1541)*Math.pow(del2,0.1499)*Math.pow(gam2,-0.0678))*Math.pow((1+(5.269e-5*Math.pow(Recsup,1.340)*Math.pow(alp2,0.504)*Math.pow(del2,0.456)*Math.pow(gam2,-1.055))),0.1);

            hhsup= jhsup*Ghsup*((Cp3+Cp2)/2)/Math.pow(Pr2,(2/3));
            hcsup= jcsup*Gcsup*((Cp5+Cp6)/2)/Math.pow(Pr5,(2/3));
            
            m1sup=Math.pow((((2*hhsup)/(km*t1))*(1+(t1/l1))),0.5);
            lh=0.5*(b1-t1);

            m2sup= Math.pow((((2*hcsup)/(km*t2))*(1+(t2/l2))),0.5);
            lc=0.5*(b2-t2);

            e1sup= (Math.tanh(m1sup*lh))/(m1sup*lh);
            e2sup= (Math.tanh(m2sup*lc))/(m2sup*lc);
            
            e1gsup= 1-(1-e1sup)*por1;
            e2gsup= 1-(1-e2sup)*por2;  
            
            iUsup=(1/(e1gsup*hhsup))+(AA/(e2gsup*hcsup));
            Ugsup=1/iUsup;

            Ahsup=NTU*Cregmin/(Ugsup/1000);
            AT=Ahsup;
            Ahosup=(m*PMii.getValor())/Ghsup;
            Aho=Ahosup;

            Acsup=Ahsup/AA;
            Acosup=(m*PMii.getValor())/Gcsup;
            Aco=Acosup;

            Lcsup=(Dh2*Acsup)/(4*Aco);
            Lc=Lcsup;
            Lhsup=(Dh1*Ahsup)/(4*Aho);
            Lh=Lhsup;


            Ghsup=(m*PMii.getValor())/Aho;
            Gcsup=(m*PMii.getValor())/Aco;

            DPhsup= (2*Math.pow(Ghsup,2)*Lhsup*fhsup)/(Dh1*((D3+D2)/2));
            DPh=DPhsup;
            erro1=DPh-DPregh;

            DPcsup= (2*Math.pow(Gcsup,2)*Lcsup*fcsup)/(Dh2*((D6+D5)/2));
            DPc=DPcsup;
            erro2=DPc-DPregc;

            Afrh=Aho/cf1;
            Afrc=Aco/cf2;

            L3=Afrh/Lc;

            Vhx=L3*Lc*Lh;

            if(eff==0){
                Gh=0;
                Gc=0;
            }else{
                Ghsup = Math.pow(((DPregh*(Dh1*((D3+D2)/2)))/(Lhsup*fhsup*2)),0.5);
                Gcsup = Math.pow(((DPregc*(Dh2*((D5+D6)/2)))/(Lcsup*fcsup*2)),0.5);
            }
        }
        
    }
}
