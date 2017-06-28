package Control.Ciclo2;

import Control.Interpolacao.ControlInterpolacaoGas;
import Control.Interpolacao.ControlInterpolacaoLiquido;
import Model.ModelCore;
import Model.ModelCriticasKCSMat_PM;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

public class ControlConeff {

    private double AT, Aho, Aco, Vhx, Lh, Lc, L3, Dph, Dpc;
    
    public ControlConeff(double P4,double P3, double m, double mH2O, int ii, double Ten, double Ten1, double Ts, double T3, double T4, double UACONs, double UACONl, double Pen, double km, Session session) {
       
        double Ghsup,Gcsup,Achlat,Achsup,epCONs,NTUsup,Ncsup,Nhsup,Accsup,
                Cclat,Chlat,Clatmin,Ckatmax,C,Ntulat,epCONl,NcCONl,NhCONl,
                Ghlat,Gclat,Acclat,Clatmax,NTUlat;
        
        double Rehsup, Rehlat, fhsup,fhlat,jhsup,jhlat,Recsup,Reclat,fcsup,fclat,jcsup,
                jclat,hhsup,hhlat,hcsup,hclat,m1sup,m1lat,lh,m2sup,m2lat,lc,e1sup,e1lat,
                e2sup,e2lat,e1gsup,e1glat,e2gsup,e2glat;
        
        double iUsup, Ugsup,iUlat,Uglat,Ahsup,Ahlat,Ahosup,Aholat,Acsup,
                    Aclat,Acosup,Acolat,Lcsup,Lclat,Lhsup,Lhlat,DPhsup,DPhlat,
                    DPh,DPcsup,DPclat,DPc, Afrh,Afrc,Csupmin;
        
        Criteria cr = session.createCriteria(ModelCriticasKCSMat_PM.class);
        List results = cr.list();
        
        ModelCriticasKCSMat_PM PMii = (ModelCriticasKCSMat_PM)results.get(ii);
        ModelCriticasKCSMat_PM PM1 = (ModelCriticasKCSMat_PM)results.get(1);
        double DP4 = P4*1000*4/100;
        double DPCONs = 0.3*DP4;
        double DPCONl = 0.7*DP4;
        
        double DPen = Pen*1000*4/100;
        double DPensup=0.2*DPen;
        double DPenlat=0.8*DPen;
        
        ControlConstantes constantes = new ControlConstantes(T4, m, ii, session);
        ControlZeta zeta = new ControlZeta(constantes.getBeta(), constantes.getEps(), constantes.getDelta());
        double D4 = 1/(((zeta.getZl()*constantes.getR()*T4)/P4)/PMii.getValor());
        double D4v=1/(((zeta.getZv()*constantes.getR()*T4)/P4)/PMii.getValor()); 
        constantes = new ControlConstantes(T3, P3, ii, session);
        zeta = new ControlZeta(constantes.getBeta(), constantes.getEps(), constantes.getDelta());
        double D3=1/(((zeta.getZv()*constantes.getR()*T3)/P3)/PMii.getValor()); 
    
        constantes = new ControlConstantes(Ten, Pen, 1,session);
        zeta = new ControlZeta(constantes.getBeta(), constantes.getEps(), constantes.getDelta());
        double De = 1/(((zeta.getZl()*constantes.getR()*Ten)/Pen)/PM1.getValor()); 
        constantes = new ControlConstantes(Ten1, Pen, 1,session);
        zeta = new ControlZeta(constantes.getBeta(), constantes.getEps(), constantes.getDelta());
        double De1=1/(((zeta.getZl()*constantes.getR()*Ten1)/Pen)/PM1.getValor()); 
        constantes = new ControlConstantes(Ts, Pen, 1,session);
        zeta = new ControlZeta(constantes.getBeta(), constantes.getEps(), constantes.getDelta());
        double Des=1/(((zeta.getZl()*constantes.getR()*Ts)/Pen)/PM1.getValor());
                
        ControlInterpolacaoLiquido propL = new ControlInterpolacaoLiquido(ii, P4, T4, session);
        double k4 = propL.getKv_l();
        double Cp4 = propL.getCpv_l();
        double MU4 = propL.getMuv_l();
        double Pr4 = propL.getPrv_l();
        double Vc4 = propL.getVcv_l();
        
        ControlInterpolacaoGas propV = new ControlInterpolacaoGas(ii,P4,T4,session);
        double k4v = propV.getKv_g();
        double Cp4v = propV.getCpv_g();
        double MU4v = propV.getMuv_g();
        double Pr4v = propV.getPrv_g();
        double Vc4v = propV.getVcv_g();
        
        propV = new ControlInterpolacaoGas(ii,P3,T3,session);
        double k3 = propV.getKv_g();
        double Cp3 = propV.getCpv_g();
        double MU3 = propV.getMuv_g();
        double Pr3 = propV.getPrv_g();
        double Vc3 = propV.getVcv_g();
       
        propL = new ControlInterpolacaoLiquido(ii, Pen, Ten, session);
        double ken = propL.getKv_l();
        double Cpen = propL.getCpv_l();
        double MUen = propL.getMuv_l();
        double Pren = propL.getPrv_l();
        double Vcen = propL.getVcv_l();
        
        propL = new ControlInterpolacaoLiquido(1, Pen, Ten1, session);
        double ken1 = propL.getKv_l();
        double Cpen1 = propL.getCpv_l();
        double MUen1 = propL.getMuv_l();
        double Pren1 = propL.getPrv_l();
        double Vcen1 = propL.getVcv_l();
        
        propL = new ControlInterpolacaoLiquido(ii, Pen, Ts, session);
        double ks = propL.getKv_l();
        double Cps = propL.getCpv_l();
        double MUs = propL.getMuv_l();
        double Prs = propL.getPrv_l();
        double Vcs = propL.getVcv_l();
        
        if(UACONs == 0){ 
            epCONs=0;
            NTUsup=0;
            Ncsup=0;
            Nhsup=0;
            Ghsup=0;
            Gcsup=0;
            Accsup=0;
            Achsup=0; 
            Csupmin = 0;
            
            Cclat=(mH2O*PM1.getValor()*((Cpen1+Cpen)/2))/1000;
            Chlat=(m*PMii.getValor()*((Cp4+Cp4v)/2))/1000;
            Clatmin = Double.min(Chlat,Cclat);
            Clatmax = Double.max(Chlat,Cclat);
            C = Clatmin/Clatmax;
            NTUlat = (UACONl)/Clatmin;
            epCONl=1-Math.exp(-NTUlat);
            NcCONl=2*NTUlat/0.8;
            NhCONl=2*NTUlat/0.8;
            Ghlat=Math.pow((((0.25)/(Math.pow(Pr4,(2/3))*NhCONl))*(2*((D4+D4v)/2)*DPCONl)),0.5);
            Gclat=Math.pow((((0.25)/(Math.pow(Pren,(2/3))*NcCONl))*(2*((De1+De)/2)*DPenlat)),0.5);
            Acclat=(mH2O*PM1.getValor())/Gclat;
            Achlat=m/Ghlat; 
            
        }else{

            double Ccsup=(mH2O*PM1.getValor()*((Cpen1+Cps)/2))/1000;
            double Chsup=(m*PMii.getValor()*((Cp3+Cp4v)/2))/1000;
            Csupmin=Double.min(Chsup,Ccsup);
            double Csupmax=Double.max(Chsup,Ccsup);
            C= Csupmin/Csupmax;
            double epsilonsup1=(Chsup*(T3-T4))/(Csupmin*(T3-Ten1));
            NTUsup=(UACONs)/Csupmin;
            double epsilonsup2=1-Math.exp((Math.exp(Math.pow(-NTUsup,0.78)*C)-1)*(Math.pow(NTUsup,0.22)/C));
            epCONs=Double.min(epsilonsup1,epsilonsup2);
            double NcCONs=2*NTUsup/0.8;
            double NhCONs=2*NTUsup/0.8;
            Ghsup=Math.pow((((0.25)/(Math.pow(Pr3,(2/3))*NhCONs))*(2*((D3+D4v)/2)*DPCONs)),0.5);
            Gcsup=Math.pow((((0.25)/(Math.pow(Pren1,(2/3))*NcCONs))*(2*((De1+Des)/2)*DPensup)),0.5);
            Accsup=(mH2O*PM1.getValor())/Gcsup;
            Achsup=m/Ghsup; 

            Cclat=(mH2O*PM1.getValor()*((Cpen1+Cpen)/2))/1000;
            Chlat=(m*PM1.getValor()*((Cp4+Cp4v)/2))/1000;
            Clatmin= Double.min(Chlat,Cclat);
            Clatmax= Double.max(Chlat,Cclat);
            C = Clatmin/Clatmax;
            NTUlat=(UACONl)/Clatmin;
            epCONl=1-Math.exp(-NTUlat);
            NcCONl=2*NTUlat/0.8;
            NhCONl=2*NTUlat/0.8;
            Ghlat=Math.pow((((0.25)/(Math.pow(Pr4,(2/3))*NhCONl))*(2*((D4+D4v)/2)*DPCONl)),0.5);
            Gclat=Math.pow((((0.25)/(Math.pow(Pren,(2/3))*NcCONl))*(2*((De1+De)/2)*DPenlat)),0.5);
            Acclat=(mH2O*PM1.getValor())/Gclat;
            Achlat=m/Ghlat; 
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
        
        double aa1= (b1*bet1)/(b1+b2+(2*0.0005));
        double aa2=(b2*bet2)/(b1+b2+(2*0.0005));
        double AA=aa1/aa2;
        double cf1=aa1*Dh1/4;
        double cf2=aa2*Dh2/4;

        double erro1=1;
        double erro2=1;
        
        while(erro1>0 || erro2>0){

            Rehsup= (Ghsup*Dh1)/((MU3+MU4v)/2);
            Rehlat= (Ghlat*Dh1)/((MU4v+MU4)/2);
            fhsup= (9.6243*Math.pow(Rehsup,-0.7422)*Math.pow(alp1,-0.1856)*Math.pow(del1,0.3053)*Math.pow(gam1,-0.2659))*Math.pow((1+(7.669e-8*Math.pow(Rehsup,4.429)*Math.pow(alp1,0.920)*Math.pow(del1,3.767)*Math.pow(gam1,0.236))),0.1);
            fhlat= (9.6243*Math.pow(Rehlat,-0.7422)*Math.pow(alp1,-0.1856)*Math.pow(del1,0.3053)*Math.pow(gam1,-0.2659))*Math.pow((1+(7.669e-8*Math.pow(Rehlat,4.429)*Math.pow(alp1,0.920)*Math.pow(del1,3.767)*Math.pow(gam1,0.236))),0.1);
            jhsup= (0.6522*Math.pow(Rehsup,-0.5403)*Math.pow(alp1,-0.1541)*Math.pow(del1,0.1499)*Math.pow(gam1,-0.0678))*Math.pow((1+(5.269e-5*Math.pow(Rehsup,1.340)*Math.pow(alp1,0.504)*Math.pow(del1,0.456)*Math.pow(gam1,-1.055))),0.1);
            jhlat= (0.6522*Math.pow(Rehlat,-0.5403)*Math.pow(alp1,-0.1541)*Math.pow(del1,0.1499)*Math.pow(gam1,-0.0678))*Math.pow((1+(5.269e-5*Math.pow(Rehlat,1.340)*Math.pow(alp1,0.504)*Math.pow(del1,0.456)*Math.pow(gam1,-1.055))),0.1);

            Recsup= (Gcsup*Dh2)/((MUen1+MUs)/2);
            Reclat= (Gclat*Dh2)/((MUen+MUen1)/2);
            fcsup= (9.6243*Math.pow(Recsup,-0.7422)*Math.pow(alp2,-0.1856)*Math.pow(del2,0.3053)*Math.pow(gam2,-0.2659))*Math.pow((1+(7.669e-8*Math.pow(Recsup,4.429)*Math.pow(alp2,0.920)*Math.pow(del2,3.767)*Math.pow(gam2,0.236))),0.1);
            fclat= (9.6243*Math.pow(Reclat,-0.7422)*Math.pow(alp2,-0.1856)*Math.pow(del2,0.3053)*Math.pow(gam2,-0.2659))*Math.pow((1+(7.669e-8*Math.pow(Reclat,4.429)*Math.pow(alp2,0.920)*Math.pow(del2,3.767)*Math.pow(gam2,0.236))),0.1);
            jcsup= (0.6522*Math.pow(Recsup,-0.5403)*Math.pow(alp2,-0.1541)*Math.pow(del2,0.1499)*Math.pow(gam2,-0.0678))*Math.pow((1+(5.269e-5*Math.pow(Recsup,1.340)*Math.pow(alp2,0.504)*Math.pow(del2,0.456)*Math.pow(gam2,-1.055))),0.1);
            jclat= (0.6522*Math.pow(Reclat,-0.5403)*Math.pow(alp2,-0.1541)*Math.pow(del2,0.1499)*Math.pow(gam2,-0.0678))*Math.pow((1+(5.269e-5*Math.pow(Reclat,1.340)*Math.pow(alp2,0.504)*Math.pow(del2,0.456)*Math.pow(gam2,-1.055))),0.1);

            hhsup= jhsup*Ghsup*((Cp3+Cp4v)/2)/Math.pow(Pr3,(2/3)); 
            hhlat= jhlat*Ghlat*((Cp4+Cp4v)/2)/Math.pow(Pr4v,(2/3)); 

            hcsup= jcsup*Gcsup*((Cpen1+Cps)/2)/Math.pow(Prs,(2/3)); 
            hclat= jclat*Gclat*((Cpen+Cpen1)/2)/Math.pow(Pren1,(2/3)); 
            
            m1sup= Math.pow((((2*hhsup)/(km*t1))*(1+(t1/l1))),0.5);
            m1lat= Math.pow((((2*hhlat)/(km*t1))*(1+(t1/l1))),0.5);
            lh=0.5*(b1-t1);

            m2sup=Math.pow((((2*hcsup)/(km*t2))*(1+(t2/l2))),0.5);
            m2lat=Math.pow((((2*hclat)/(km*t2))*(1+(t2/l2))),0.5);
            lc=0.5*(b2-t2);

            e1sup= (Math.tanh(m1sup*lh))/(m1sup*lh);  
            e1lat= (Math.tanh(m1lat*lh))/(m1lat*lh);

            e2sup= (Math.tanh(m2sup*lc))/(m2sup*lc);
            e2lat= (Math.tanh(m2lat*lc))/(m2lat*lc);

            e1gsup= 1-(1-e1sup)*por1;
            e1glat= 1-(1-e1lat)*por1;

            e2gsup= 1-(1-e2sup)*por2;
            e2glat= 1-(1-e2lat)*por2;
            
            iUsup=(1/(e1gsup*hhsup))+(AA/(e2gsup*hcsup));
            Ugsup=1/iUsup;
            iUlat=(1/(e1glat*hhlat))+(AA/(e2glat*hclat));
            Uglat=1/iUlat;

            Ahsup=NTUsup*Csupmin/(Ugsup/1000);
            Ahlat=NTUlat*Clatmin/(Uglat/1000);
            AT=Ahsup+Ahlat;
            Ahosup=(m*PMii.getValor())/Ghsup;
            Aholat=(m*PMii.getValor())/Ghlat;

            Acsup=Ahsup/AA;
            Aclat=Ahlat/AA;
            Acosup=(mH2O*PM1.getValor())/Gcsup;
            Acolat=(mH2O*PM1.getValor())/Gclat;

            Aho= Double.max(Ahosup,Aholat);
            Aco= Double.max(Acosup,Acolat);

            Lcsup=(Dh2*Acsup)/(4*Aco);
            Lclat=(Dh2*Aclat)/(4*Aco);
            Lc=Lcsup+Lclat;
            Lhsup=(Dh1*Ahsup)/(4*Aho);
            Lhlat=(Dh1*Ahlat)/(4*Aho);
            Lh=Lhsup+Lhlat;
            
            Ghsup=(m*PMii.getValor())/Aho;
            Ghlat=(m*PMii.getValor())/Aho;
            Gcsup=(mH2O*PM1.getValor())/Aco;
            Gclat=(mH2O*PM1.getValor())/Aco;

            DPhsup= (2*Math.pow(Ghsup,2)*Lhsup*fhsup)/(Dh1*((D3+D4v)/2));
            DPhlat= (2*Math.pow(Ghlat,2)*Lhlat*fhlat)/(Dh1*((D4v+D4)/2));
            DPh=DPhsup+DPhlat;
            erro1=DPh-DP4;

            DPcsup= (2*Math.pow(Gcsup,2)*Lcsup*fcsup)/(Dh2*((De1+Des)/2));
            DPclat= (2*Math.pow(Gclat,2)*Lclat*fclat)/(Dh2*((De+De1)/2));
            DPc=DPcsup+DPclat;
            erro2=DPc-DPen;

            Afrh=Aho/cf1;
            Afrc=Aco/cf2;

            L3=Afrh/Lc;

            Vhx=L3*Lc*Lh;

            if(UACONs==0){
               Ghsup=0;
               Gcsup=0;

               Ghlat=Math.pow(((DPCONl*(Dh1*((D4v+D4)/2)))/(Lhlat*fhlat*2)),0.5);
               Gclat=Math.pow(((DPenlat*(Dh2*((De+De1)/2)))/(Lclat*fcsup*2)),0.5);
            }else{
               Ghsup=Math.pow(((DPCONs*(Dh1*((D3+D4v)/2)))/(Lhsup*fhsup*2)),0.5);
               Gcsup=Math.pow(((DPensup*(Dh2*((De1+Des)/2)))/(Lcsup*fcsup*2)),0.5);

               Ghlat=Math.pow(((DPCONl*(Dh1*((D4v+D4)/2)))/(Lhlat*fhlat*2)),0.5);
               Gclat=Math.pow(((DPenlat*(Dh2*((De+De1)/2)))/(Lclat*fcsup*2)),0.5);
            }
        }
    }

    public double getAT() {
        return AT;
    }

    public void setAT(double AT) {
        this.AT = AT;
    }

    public double getAho() {
        return Aho;
    }

    public void setAho(double Aho) {
        this.Aho = Aho;
    }

    public double getAco() {
        return Aco;
    }

    public void setAco(double Aco) {
        this.Aco = Aco;
    }

    public double getVhx() {
        return Vhx;
    }

    public void setVhx(double Vhx) {
        this.Vhx = Vhx;
    }

    public double getLh() {
        return Lh;
    }

    public void setLh(double Lh) {
        this.Lh = Lh;
    }

    public double getLc() {
        return Lc;
    }

    public void setLc(double Lc) {
        this.Lc = Lc;
    }

    public double getL3() {
        return L3;
    }

    public void setL3(double L3) {
        this.L3 = L3;
    }

    public double getDph() {
        return Dph;
    }

    public void setDph(double Dph) {
        this.Dph = Dph;
    }

    public double getDpc() {
        return Dpc;
    }

    public void setDpc(double Dpc) {
        this.Dpc = Dpc;
    }
}