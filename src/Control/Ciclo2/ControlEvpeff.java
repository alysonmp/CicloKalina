/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

import Control.Interpolacao.ControlInterpolacaoCompressor;
import Control.Interpolacao.ControlInterpolacaoGas;
import Control.Interpolacao.ControlInterpolacaoLiquido;
import Model.ModelCore;
import Model.ModelCriticasKCSMat_PM;
import java.util.Arrays;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author alysonmp
 */
public class ControlEvpeff {
   
    private double AT, Aho, Aco, Vhx, Lh, Lc, L3, DPh, DPc;
    
    public ControlEvpeff(double P1, double P6, double T1, double T6, int ii, double SUP, double Hlat, double Hsen, double Hsup, int compressor, double m, double mf, double T1s, double Tf, double Tfout, double Pf, int FON, double PINCH, double UASUP, double UALAT, double UASEN, double km, Session session){
    
        double Tf2 = T1s+PINCH;
        double Qsen = m*Hsen;
        double Qlat = m*Hlat;
        double Qsup = m*Hsup;
        
        double DP6 = P6*1000*4/100;
        double DPsup = 0.2*DP6;
        double DPlat = 0.4*DP6;
        double DPsen = 0.4*DP6;
        
        double DPf = Pf*1000*4/100;
        double DPfsup = 0.2*DPf;
        double DPflat = 0.4*DPf;
        double DPfsen = 0.4*DPf;
        
        if(SUP == 0){
            DP6 = P6*1000*4/100;
            DPsup = 0.0*DP6;
            DPlat = 0.5*DP6;
            DPsen = 0.5*DP6;

            DPf = Pf*1000*4/100;
            DPfsup = 0*DPf;
            DPflat = 0.5*DPf;
            DPfsen = 0.0*DPf;
        }
        
        ControlConstantes constantes = new ControlConstantes(Tf, P1, ii, session);
        ControlZeta zeta = new ControlZeta(constantes.getBeta(), constantes.getEps(), constantes.getDelta());
        
        double Zv1 = zeta.getZv();
        
        Criteria cr = session.createCriteria(ModelCriticasKCSMat_PM.class);
        cr.add(Restrictions.eq("cod", ii));
        List results = cr.list();
        ModelCriticasKCSMat_PM pm = (ModelCriticasKCSMat_PM)results.get(0);
        
        double D1 = 1/(((Zv1*constantes.getR()*T1)/P1)/pm.getValor()); //%%kg/m3 
        
        constantes = new ControlConstantes(T6, P6, ii, session);
        zeta = new ControlZeta(constantes.getBeta(), constantes.getEps(), constantes.getDelta());
        double Zl6 = zeta.getZl();
        double D6 = 1/(((Zl6*constantes.getR()*T6)/P6)/pm.getValor()); //%%kg/m3
        
        constantes = new ControlConstantes(T1s, P1, ii, session);
        zeta = new ControlZeta(constantes.getBeta(), constantes.getEps(), constantes.getDelta());
        double Zls = zeta.getZl();
        double Zvs = zeta.getZv();
        
        double D1sl = 1/(((Zls*constantes.getR()*T1s)/P1)/pm.getValor()); //%%kg/m3 
        double D1sv = 1/(((Zvs*constantes.getR()*T1s)/P1)/pm.getValor()); //%%kg/m3 
        
        ControlInterpolacaoGas intGas = new ControlInterpolacaoGas(ii, P1, T1, session);
        double k1 = intGas.getKv_g();
        double Cp1 = intGas.getCpv_g();
        double MU1 = intGas.getMuv_g();
        double Pr1 = intGas.getPrv_g();
        double Vc1 = intGas.getVcv_g();
        
        intGas = new ControlInterpolacaoGas(ii, P1, T1s, session);
        double k1s = intGas.getKv_g();
        double Cp1s = intGas.getCpv_g();
        double MU1s = intGas.getMuv_g();
        double Pr1s = intGas.getPrv_g();
        double Vc1s = intGas.getVcv_g();
        
        ControlInterpolacaoLiquido intLiquido = new ControlInterpolacaoLiquido(ii, P1, T1s, session);
        double k1sl = intLiquido.getKv_l();
        double Cp1sl = intLiquido.getCpv_l();
        double MU1sl = intLiquido.getMuv_l();
        double Pr1sl = intLiquido.getPrv_l();
        double Vc1sl = intLiquido.getVcv_l();
        
        intLiquido = new ControlInterpolacaoLiquido(ii, P6, T6, session);
        double k6 = intLiquido.getKv_l();
        double Cp6 = intLiquido.getCpv_l();
        double MU6 = intLiquido.getMuv_l();
        double Pr6 = intLiquido.getPrv_l();
        double Vc6 = intLiquido.getVcv_l();
        
        ControlInterpolacaoCompressor intCompress = new ControlInterpolacaoCompressor(compressor, Pf, Tf, session);
        double kf = intCompress.getKv_g();
        double Cpf = intCompress.getCpv_g();
        double MUf = intCompress.getMuv_g();
        double Prf = intCompress.getPrv_g();
        double Vcf = intCompress.getVcv_g();
        double Df = intCompress.getDf_g();
        
        ControlTSaida tSaida = new ControlTSaida(compressor, Qsup, Tf, session);
        double Tf1 = tSaida.getTfout();
        
        intCompress = new ControlInterpolacaoCompressor(compressor, Pf, Tf1, session);
        double kf1 = intCompress.getKv_g();
        double Cpf1 = intCompress.getCpv_g();
        double MUf1 = intCompress.getMuv_g();
        double Prf1 = intCompress.getPrv_g();
        double Vcf1 = intCompress.getVcv_g();
        double Df1 = intCompress.getDf_g();
        
        intCompress = new ControlInterpolacaoCompressor(compressor, Pf, Tf2, session);
        double kf2 = intCompress.getKv_g();
        double Cpf2 = intCompress.getCpv_g();
        double MUf2 = intCompress.getMuv_g();
        double Prf2 = intCompress.getPrv_g();
        double Vcf2 = intCompress.getVcv_g();
        double Df2 = intCompress.getDf_g();
        
        intCompress = new ControlInterpolacaoCompressor(compressor, Pf, Tfout, session);
        double kfout = intCompress.getKv_g();
        double Cpfout = intCompress.getCpv_g();
        double MUfout = intCompress.getMuv_g();
        double Prfout = intCompress.getPrv_g();
        double Vcfout = intCompress.getVcv_g();
        double Dfout = intCompress.getDf_g();
        
        double epsup;
        double NTUsup;
        double Nhsup;
        double Ncsup;
        double Ghsup;
        double Gcsup;
        double Accsup;
        double Achsup;
        
        double Cclat;
        double Chlat;
        double Clatmin;
        double Clatmax;
        double C;
        double NTUlat;
        double eplat;
        double Nhlat;
        double Nclat;
        double Ghlat;
        double Gclat;
        double Acclat;
        double Achlat;
        
        double Ccsen;
        double Chsen;
        double Csenmin;
        double Csenmax;
        double epsilonsen1;
        double NTUsen;
        double epsilonsen2;
        double epsen;
        double Nhsen;
        double Ncsen;
        double Ghsen;
        double Gcsen;
        double Accsen;
        double Achsen;
        double Ccsup;
        double Chsup;
        double Csupmin = 0;
        double Csupmax;
        double epsilonsup1;
        double epsilonsup2;
        
        if(SUP == 0){
            epsup = 0;
            NTUsup = 0;
            Nhsup = 0;
            Ncsup = 0;
            Ghsup = 0;
            Gcsup = 0;
            Accsup = 0;
            Achsup = 0; 

            Cclat = (m*(pm.getValor())*((Cp1s)))/1000; //%kJ/K*s
            Chlat = (mf*((Cpf2+Cpf1)/2))/1000; //%kJ/K*s
            Clatmin = Math.min(Chlat,Cclat); //%kJ/K*s
            Clatmax = Math.max(Chlat,Cclat); //%kJ/K*s
            C = Clatmin/Clatmax;
            NTUlat = (UALAT)/Clatmin;
            eplat = 1-Math.exp(-NTUlat);
            Nhlat = 2*NTUlat/0.8;
            Nclat = 2*NTUlat/0.8;
            Ghlat = Math.pow((((0.25)/((Math.pow(Prf1,(2/3)))*Nhlat))*(2*((Df1+Df2)/2)*DPflat)),0.5); //%kg/m2*s
            Gclat=Math.pow((((0.25)/((Math.pow(Pr1s,(2/3)))*Nclat))*(2*((D1sv+D1sl)/2)*DPlat)),0.5); //%kg/m2*s
            Acclat = (m*(pm.getValor()))/Gclat; //%m2
            Achlat = mf/Ghlat;  //%m2

            Ccsen=(m*(pm.getValor())*((Cp6+Cp1s)/2))/1000;
            Chsen=(mf*((Cpfout+Cpf2)/2))/1000;
            Csenmin = Math.min(Chsen,Ccsen);
            Csenmax = Math.max(Chsen,Ccsen);
            C = Csenmin/Csenmax;
            epsilonsen1 = (Chsen*(Tf2-Tfout))/(Csenmin*(Tf2-T6));
            NTUsen = (UASEN)/Csenmin;
            epsilonsen2 = 1-Math.exp((Math.exp((Math.pow(-NTUsen,0.78))*C)-1)*(Math.pow(NTUsen,0.22)/C));
            epsen = Math.min(epsilonsen1,epsilonsen2);
            Nhsen = 1.1*NTUsen/0.8;
            Ncsen = 10*C*NTUsen/0.8;
            Ghsen = Math.pow((((0.25)/(Math.pow(Prf2,(2/3))*Nhsen))*(2*((Df2+Dfout)/2)*DPfsen)),0.5);
            Gcsen = Math.pow((((0.25)/(Math.pow(Pr1sl,(2/3))*Ncsen))*(2*((D6+D1sl)/2)*DPsen)),0.5);
            Accsen = (m*(pm.getValor()))/Gcsen;
            Achsen = mf/Ghsen; 

        }else{
            Ccsup = (m*(pm.getValor())*((Cp1+Cp1s)/2))/1000;
            Chsup = (mf*((Cpf+Cpf1)/2))/1000;
            Csupmin = Math.min(Chsup,Ccsup);
            Csupmax = Math.max(Chsup,Ccsup);
            C = Csupmin/Csupmax;
            epsilonsup1 = (Chsup*(Tf-Tf1))/(Csupmin*(Tf-T1s));
            NTUsup = (UASUP)/Csupmin;
            epsilonsup2 = 1-Math.exp((Math.exp(Math.pow(-NTUsup,0.78)*C)-1)*(Math.pow(NTUsup,0.22)/C));
            epsup = Math.min(epsilonsup1,epsilonsup2);
            Nhsup = 2*NTUsup/0.8;
            Ncsup = 2*NTUsup/0.8;
            Ghsup = Math.pow((((0.25)/(Math.pow(Prf,(2/3))*Nhsup))*(2*((Df1+Df)/2)*DPfsup)),0.5);
            Gcsup = Math.pow((((0.25)/(Math.pow(Pr1,(2/3))*Ncsup))*(2*((D1sv+D1)/2)*DPsup)),0.5);
            Accsup = (m*(pm.getValor()))/Gcsup;
            Achsup = mf/Ghsup; 


            Cclat = (m*(pm.getValor())*((Cp1s)))/1000;
            Chlat = (mf*((Cpf2+Cpf1)/2))/1000;
            Clatmin = Math.min(Chlat,Cclat);
            Clatmax = Math.max(Chlat,Cclat);
            C = Clatmin/Clatmax;
            NTUlat = (UALAT)/Clatmin;
            eplat = 1-Math.exp(-NTUlat);
            Nhlat = 2*NTUlat/0.8;
            Nclat = 2*NTUlat/0.8;
            Ghlat = Math.pow((((0.25)/(Math.pow(Prf1,(2/3))*Nhlat))*(2*((Df1+Df2)/2)*DPflat)),0.5);
            Gclat = Math.pow((((0.25)/(Math.pow(Pr1s,(2/3))*Nclat))*(2*((D1sv+D1sl)/2)*DPlat)),0.5);
            Acclat = (m*(pm.getValor()))/Gclat;
            Achlat = mf/Ghlat;

            Ccsen = (m*(pm.getValor())*((Cp6+Cp1s)/2))/1000;
            Chsen = (mf*((Cpfout+Cpf2)/2))/1000;
            Csenmin = Math.min(Chsen,Ccsen);
            Csenmax = Math.max(Chsen,Ccsen);
            C = Csenmin/Csenmax;
            epsilonsen1 = (Chsen*(Tf2-Tfout))/(Csupmin*(Tf2-T6));
            NTUsen = (UASEN)/Csenmin;
            epsilonsen2 = 1-Math.exp((Math.exp(Math.pow(-NTUsen,0.78)*C)-1)*(Math.pow(NTUsen,0.22)/C));
            epsen = Math.min(epsilonsen1,epsilonsen2);
            Nhsen = 1.1*NTUsen/0.8;
            Ncsen = 10*C*NTUsen/0.8;
            Ghsen = Math.pow((((0.25)/(Math.pow(Prf2,(2/3))*Nhsen))*(2*((Df2+Dfout)/2)*DPfsen)),0.5);
            Gcsen = Math.pow((((0.25)/(Math.pow(Pr1sl,(2/3))*Ncsen))*(2*((D6+D1sl)/2)*DPsen)),0.5);
            Accsen = (m*(pm.getValor()))/Gcsen;
            Achsen = mf/Ghsen;
        }
        
        cr = session.createCriteria(ModelCore.class);
        cr.add(Restrictions.eq("cod", 13));
        results = cr.list();
        ModelCore core = (ModelCore) results.get(0);
        
        //%%Dimensoes do Core 
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
        cr.add(Restrictions.eq("cod", 13));
        results = cr.list();
        core = (ModelCore) results.get(0);
        
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
        
        double aa1 = (b1*bet1)/(b1+b2+(2*0.0005));
        double aa2 = (b2*bet2)/(b1+b2+(2*0.0005));
        double AA = aa1/aa2;
        double cf1 = aa1*Dh1/4;
        double cf2 = aa2*Dh2/4;
        
        double erro1=1;
        double erro2=1;
        
        double Rehsup;
        double Rehlat;
        double Rehsen;
        double fhsup;
        double fhlat;
        double fhsen;
        double jhsup;
        double jhlat;
        double jhsen;
        
        double Recsup;
        double Reclat;
        double Recsen;
        double fcsup;
        double fclat;
        double fcsen;
        double jcsup;
        double jclat;
        double jcsen;
        
        double hhsup;
        double hhlat;
        double hhsen;
        
        double hcsup;
        double hclat;
        double hcsen;
        
        double m1sup;
        double m1lat;
        double m1sen;
        double lh;
        
        double m2sup;
        double m2lat;
        double m2sen;
        double lc;
        
        double e1sup;
        double e1lat;
        double e1sen;
        
        double e2sup;
        double e2lat;
        double e2sen;
        
        double e1gsup;
        double e1glat;
        double e1gsen;
        
        double e2gsup;
        double e2glat;
        double e2gsen;
        
        double iUsup;
        double Ugsup;
        double iUlat;
        double Uglat;
        double iUsen;
        double Ugsen;
        
        double Ahsup;
        double Ahlat;
        double Ahsen;
        double Ahosup;
        double Aholat;
        double Ahosen;
        
        double Acsup;
        double Aclat;
        double Acsen;
        double Acosup;
        double Acolat;
        double Acosen;
        
        double Lcsup;
        double Lclat;
        double Lcsen;
        
        double Lhsup;
        double Lhlat;
        double Lhsen;
        
        double DPhsup;
        double DPhlat;
        double DPhsen;
        
        double DPcsup;
        double DPclat;
        double DPcsen;
        
        double Afrh;
        double Afrc;
        
        while(erro1 > 0 || erro2 > 0){
            Rehsup = (Ghsup*Dh1)/((MUf+MUf1)/2);
            Rehlat = (Ghlat*Dh1)/((MUf2+MUf1)/2);
            Rehsen = (Ghsen*Dh1)/((MUf2+MUfout)/2);
            fhsup = (9.6243*Math.pow(Rehsup,-0.7422)*Math.pow(alp1,-0.1856)*Math.pow(del1,0.3053)*Math.pow(gam1,-0.2659))*Math.pow((1+(7.669e-8*Math.pow(Rehsup,4.429)*Math.pow(alp1,0.920)*Math.pow(del1,3.767)*Math.pow(gam1,0.236))),0.1);
            fhlat = (9.6243*Math.pow(Rehlat,-0.7422)*Math.pow(alp1,-0.1856)*Math.pow(del1,0.3053)*Math.pow(gam1,-0.2659))*Math.pow((1+(7.669e-8*Math.pow(Rehlat,4.429)*Math.pow(alp1,0.920)*Math.pow(del1,3.767)*Math.pow(gam1,0.236))),0.1);
            fhsen = (9.6243*Math.pow(Rehsen,-0.7422)*Math.pow(alp1,-0.1856)*Math.pow(del1,0.3053)*Math.pow(gam1,-0.2659))*Math.pow((1+(7.669e-8*Math.pow(Rehsen,4.429)*Math.pow(alp1,0.920)*Math.pow(del1,3.767)*Math.pow(gam1,0.236))),0.1);
            jhsup = (0.6522*Math.pow(Rehsup,-0.5403)*Math.pow(alp1,-0.1541)*Math.pow(del1,0.1499)*Math.pow(gam1,-0.0678))*Math.pow((1+(5.269e-5*Math.pow(Rehsup,1.340)*Math.pow(alp1,0.504)*Math.pow(del1,0.456)*Math.pow(gam1,-1.055))),0.1);
            jhlat = (0.6522*Math.pow(Rehlat,-0.5403)*Math.pow(alp1,-0.1541)*Math.pow(del1,0.1499)*Math.pow(gam1,-0.0678))*Math.pow((1+(5.269e-5*Math.pow(Rehlat,1.340)*Math.pow(alp1,0.504)*Math.pow(del1,0.456)*Math.pow(gam1,-1.055))),0.1);
            jhsen = (0.6522*Math.pow(Rehsen,-0.5403)*Math.pow(alp1,-0.1541)*Math.pow(del1,0.1499)*Math.pow(gam1,-0.0678))*Math.pow((1+(5.269e-5*Math.pow(Rehsen,1.340)*Math.pow(alp1,0.504)*Math.pow(del1,0.456)*Math.pow(gam1,-1.055))),0.1);
            //% Lhsup= (Dh1*(Prf^(2/3))*Nhsup)/(4*jhsup);
            //% Lhlat= (Dh1*(Prf1^(2/3))*Nhlat)/(4*jhlat);
            //% Lhsen= (Dh1*(Prf2^(2/3))*Nhsen)/(4*jhsen);

            Recsup = (Gcsup*Dh2)/((MU1+MU1s)/2);
            Reclat = (Gclat*Dh2)/((MU1s+MU1sl)/2);
            Recsen = (Gcsen*Dh2)/((MU1sl+MU6)/2);
            fcsup = (9.6243*Math.pow(Recsup,-0.7422)*Math.pow(alp2,-0.1856)*Math.pow(del2,0.3053)*Math.pow(gam2,-0.2659))*Math.pow((1+(7.669e-8*Math.pow(Recsup,4.429)*Math.pow(alp2,0.920)*Math.pow(del2,3.767)*Math.pow(gam2,0.236))),0.1);
            fclat = (9.6243*Math.pow(Reclat,-0.7422)*Math.pow(alp2,-0.1856)*Math.pow(del2,0.3053)*Math.pow(gam2,-0.2659))*Math.pow((1+(7.669e-8*Math.pow(Reclat,4.429)*Math.pow(alp2,0.920)*Math.pow(del2,3.767)*Math.pow(gam2,0.236))),0.1);
            fcsen = (9.6243*Math.pow(Recsen,-0.7422)*Math.pow(alp2,-0.1856)*Math.pow(del2,0.3053)*Math.pow(gam2,-0.2659))*Math.pow((1+(7.669e-8*Math.pow(Recsen,4.429)*Math.pow(alp2,0.920)*Math.pow(del2,3.767)*Math.pow(gam2,0.236))),0.1);
            jcsup = (0.6522*Math.pow(Recsup,-0.5403)*Math.pow(alp2,-0.1541)*Math.pow(del2,0.1499)*Math.pow(gam2,-0.0678))*Math.pow((1+(5.269e-5*Math.pow(Recsup,1.340)*Math.pow(alp2,0.504)*Math.pow(del2,0.456)*Math.pow(gam2,-1.055))),0.1);
            jclat = (0.6522*Math.pow(Reclat,-0.5403)*Math.pow(alp2,-0.1541)*Math.pow(del2,0.1499)*Math.pow(gam2,-0.0678))*Math.pow((1+(5.269e-5*Math.pow(Reclat,1.340)*Math.pow(alp2,0.504)*Math.pow(del2,0.456)*Math.pow(gam2,-1.055))),0.1);
            jcsen = (0.6522*Math.pow(Recsen,-0.5403)*Math.pow(alp2,-0.1541)*Math.pow(del2,0.1499)*Math.pow(gam2,-0.0678))*Math.pow((1+(5.269e-5*Math.pow(Recsen,1.340)*Math.pow(alp2,0.504)*Math.pow(del2,0.456)*Math.pow(gam2,-1.055))),0.1);
            //% Lcsup= (Dh2*(Pr1^(2/3))*Ncsup)/(4*jcsup);
            //% Lclat= (Dh2*(Pr1s^(2/3))*Nclat)/(4*jclat);
            //% Lcsen= (Dh2*(Pr1sl^(2/3))*Ncsen)/(4*jcsen);

            //%%%%%%%%%%%%%   (W/m2*K)   %%%%%%%%%%%%%%%%%%%%
            hhsup = jhsup*Ghsup*((Cpf+Cpf1)/2)/Math.pow(Prf,(2/3)); //% Coeficiente de transferÍncia de calor lado quente
            hhlat = jhlat*Ghlat*((Cpf2+Cpf1)/2)/Math.pow(Prf1,(2/3)); //% Coeficiente de transferÍncia de calor lado quente
            hhsen = jhsen*Ghsen*((Cpf2+Cpfout)/2)/Math.pow(Prf2,(2/3)); //% Coeficiente de transferÍncia de calor lado quente

            hcsup = jcsup*Gcsup*((Cp1+Cp1s)/2)/Math.pow(Pr1,(2/3)); //% Coeficiente de transferÍncia de calor lado frio
            hclat = jclat*Gclat*((Cp1s+Cp1s)/2)/Math.pow(Pr1s,(2/3)); //% Coeficiente de transferÍncia de calor lado frio
            hcsen = jcsen*Gcsen*((Cp6+Cp1s)/2)/Math.pow(Pr1sl,(2/3)); //% Coeficiente de transferÍncia de calor lado frio

            //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

            //% Fatores para c·lclulo da eficiÍncia:
            m1sup = Math.pow((((2*hhsup)/(km*(t1)))*(1+(t1/l1))),0.5);
            m1lat = Math.pow((((2*hhlat)/(km*(t1)))*(1+(t1/l1))),0.5);
            m1sen = Math.pow((((2*hhsen)/(km*(t1)))*(1+(t1/l1))),0.5);
            lh = 0.5*(b1-t1);

            m2sup = Math.pow((((2*hcsup)/(km*(t2)))*(1+(t2/l2))),0.5);
            m2lat = Math.pow((((2*hclat)/(km*(t2)))*(1+(t2/l2))),0.5);
            m2sen = Math.pow((((2*hcsen)/(km*(t2)))*(1+(t2/l2))),0.5);
            lc = 0.5*(b2-t2);

            e1sup = (Math.tanh(m1sup*lh))/(m1sup*lh);  //% EficiÍncia da aleta lado quente
            e1lat = (Math.tanh(m1lat*lh))/(m1lat*lh);  //% EficiÍncia da aleta lado quente
            e1sen = (Math.tanh(m1sen*lh))/(m1sen*lh);  //% EficiÍncia da aleta lado quente

            e2sup = (Math.tanh(m2sup*lc))/(m2sup*lc); //% EficiÍncia da aleta lado frio
            e2lat = (Math.tanh(m2lat*lc))/(m2lat*lc);  //% EficiÍncia da aleta lado frio
            e2sen = (Math.tanh(m2sen*lc))/(m2sen*lc);  //% EficiÍncia da aleta lado frio


            //%The overall surface efficiencies with Af /A values from Fig. E9.1B or input are

            e1gsup = 1-(1-e1sup)*por1;  //% EficiÍncia global da SuperfÌcie lado Quente
            e1glat = 1-(1-e1lat)*por1;  //% EficiÍncia global da SuperfÌcie lado Quente
            e1gsen = 1-(1-e1sen)*por1;  //% EficiÍncia global da SuperfÌcie lado Quente

            e2gsup = 1-(1-e2sup)*por2;  //% EficiÍncia global da SuperfÌcie lado Frio
            e2glat = 1-(1-e2lat)*por2;  //% EficiÍncia global da SuperfÌcie lado frio
            e2gsen = 1-(1-e2sen)*por2;


            //%coeficiente de transferencia de calor (W/m2*K)
            iUsup = (1/(e1gsup*hhsup))+(AA/(e2gsup*hcsup));
            Ugsup = 1/iUsup;
            iUlat = (1/(e1glat*hhlat))+(AA/(e2glat*hclat));
            Uglat = 1/iUlat;
            iUsen = (1/(e1gsen*hhsen))+(AA/(e2gsen*hcsen));
            Ugsen = 1/iUsen;

            //%Area de superficie / %Area de fluxo livre  / Comprimento
            Ahsup = NTUsup*Csupmin/(Ugsup/1000);
            Ahlat = NTUlat*Clatmin/(Uglat/1000);
            Ahsen = NTUsen*Csenmin/(Ugsen/1000);
            Ahosup = mf/Ghsup;
            Aholat = mf/Ghlat;
            Ahosen = mf/Ghsen;
            double[] AhoList = {Ahosup,Aholat,Ahosen};
            double max = Arrays.stream(AhoList).max().getAsDouble();
            AT = Ahsup+Ahsen+Ahlat;

            Acsup = Ahsup/AA;
            Aclat = Ahlat/AA;
            Acsen = Ahsen/AA;
            Acosup = (m*pm.getValor())/Gcsup;
            Acolat = (m*pm.getValor())/Gclat;
            Acosen = (m*pm.getValor())/Gcsen;
            double[] AcoList = {Acosup,Acolat,Acosen};

            Aho = Arrays.stream(AhoList).max().getAsDouble();
            Aco = Arrays.stream(AcoList).max().getAsDouble();

            Lcsup = (Dh2*Acsup)/(4*Aco);
            Lclat = (Dh2*Aclat)/(4*Aco);
            Lcsen = (Dh2*Ahsen)/(4*Aco);
            Lc = Lcsup+Lclat+Lcsen;
            Lhsup = (Dh1*Ahsup)/(4*Aho);
            Lhlat = (Dh1*Ahlat)/(4*Aho);
            Lhsen = (Dh1*Ahsen)/(4*Aho);
            Lh = Lhsup+Lhlat+Lhsen;

            Ghsup = mf/Aho;
            Ghlat = mf/Aho;
            Ghsen = mf/Aho;
            Gcsup = (m*pm.getValor())/Aco;
            Gclat = (m*pm.getValor())/Aco;
            Gcsen = (m*pm.getValor())/Aco;

            DPhsup = (2*Math.pow(Ghsup,2)*Lhsup*fhsup)/(Dh1*((Df1+Df)/2));
            DPhlat = (2*Math.pow(Ghlat,2)*Lhlat*fhlat)/(Dh1*((Df1+Df2)/2));
            DPhsen = (2*Math.pow(Ghsen,2)*Lhsen*fhsen)/(Dh1*((Dfout+Df2)/2));
            DPh = DPhsup+DPhlat+DPhsen;
            erro1 = DPh-DPf;

            DPcsup = (2*Math.pow(Gcsup,2)*Lcsup*fcsup)/(Dh2*((D1+D1sv)/2));
            DPclat = (2*Math.pow(Gclat,2)*Lclat*fclat)/(Dh2*((D1sv+D1sl)/2));
            DPcsen = (2*Math.pow(Gcsen,2)*Lcsen*fcsen)/(Dh2*((D1sl+D6)/2));
            DPc=DPcsup+DPclat+DPcsen;
            erro2=DPc-DP6;

            Afrh = Aho/cf1;
            Afrc = Aco/cf2;

            L3=Afrh/Lc;
            Vhx=L3*Lc*Lh;
            //% L3c=Afrc/Lh;

            if(SUP == 0){
                Ghsup=0;
                Gcsup=0;

                Ghlat = Math.pow(((DPflat*(Dh1*((Df1+Df2)/2)))/(Lhlat*fhlat*2)),0.5);
                Gclat = Math.pow(((DPlat*(Dh2*((D1sv+D1sl)/2)))/(Lclat*fcsup*2)),0.5);

                Ghsen = Math.pow(((DPfsen*(Dh1*((Dfout+Df2)/2)))/(Lhsen*fhsen*2)),0.5);
                Gcsen = Math.pow(((DPsen*(Dh2*((D6+D1sl)/2)))/(Lcsen*fcsen*2)),0.5);
            }else{
                Ghsup = Math.pow(((DPfsup*(Dh1*((Df1+Df)/2)))/(Lhsup*fhsup*2)),0.5);
                Gcsup = Math.pow(((DPsup*(Dh2*((D1sv+D1)/2)))/(Lcsup*fcsup*2)),0.5);

                Ghlat = Math.pow(((DPflat*(Dh1*((Df1+Df2)/2)))/(Lhlat*fhlat*2)),0.5);
                Gclat = Math.pow(((DPlat*(Dh2*((D1sv+D1sl)/2)))/(Lclat*fcsup*2)),0.5);
                
                Ghsen = Math.pow(((DPfsen*(Dh1*((Dfout+Df2)/2)))/(Lhsen*fhsen*2)),0.5);
                Gcsen = Math.pow(((DPsen*(Dh2*((D6+D1sl)/2)))/(Lcsen*fcsen*2)),0.5);
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

    public double getDPh() {
        return DPh;
    }

    public void setDPh(double DPh) {
        this.DPh = DPh;
    }

    public double getDPc() {
        return DPc;
    }

    public void setDPc(double DPc) {
        this.DPc = DPc;
    }
}