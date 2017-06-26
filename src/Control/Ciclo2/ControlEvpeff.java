/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

import Control.Interpolacao.ControlInterpolacaoCompressor;
import Control.Interpolacao.ControlInterpolacaoGas;
import Control.Interpolacao.ControlInterpolacaoLiquido;
import Model.ModelCriticasKCSMat_PM;
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
        
        ControlInterpolacaoGas intGas = new ControlInterpolacaoGas(ii, T1, P1, session);
        double k1 = intGas.getKv_g();
        double Cp1 = intGas.getCpv_g();
        double MU1 = intGas.getMuv_g();
        double Pr1 = intGas.getPrv_g();
        double Vc1 = intGas.getVcv_g();
        
        intGas = new ControlInterpolacaoGas(ii, T1s, P1, session);
        double k1s = intGas.getKv_g();
        double Cp1s = intGas.getCpv_g();
        double MU1s = intGas.getMuv_g();
        double Pr1s = intGas.getPrv_g();
        double Vc1s = intGas.getVcv_g();
        
        ControlInterpolacaoLiquido intLiquido = new ControlInterpolacaoLiquido(ii, T1s, P1, session);
        double k1sl = intLiquido.getKv_l();
        double Cp1sl = intLiquido.getCpv_l();
        double MU1sl = intLiquido.getMuv_l();
        double Pr1sl = intLiquido.getPrv_l();
        double Vc1sl = intLiquido.getVcv_l();
        
        intLiquido = new ControlInterpolacaoLiquido(ii, T6, P6, session);
        double k6 = intLiquido.getKv_l();
        double Cp6 = intLiquido.getCpv_l();
        double MU6 = intLiquido.getMuv_l();
        double Pr6 = intLiquido.getPrv_l();
        double Vc6 = intLiquido.getVcv_l();
        
        /***************************************
        [kf Cpf MUf Prf Vcf Df] = propVf(FON, Tf, Pf);
        ***************************************/
        
        ControlInterpolacaoCompressor intCompress = new ControlInterpolacaoCompressor(FON, Tf, Pf, session);
        double kf = intCompress.getKv_g();
        double Cpf = intCompress.getCpv_g();
        double MUf = intCompress.getMuv_g();
        double Prf = intCompress.getPrv_g();
        double Vcf = intCompress.getVcv_g();
        double Df = intCompress.getDf_g();
        
        ControlTSaida tSaida = new ControlTSaida(compressor, Tf, Qsup, session);
        double Tf1 = tSaida.getTfout();
        
        intCompress = new ControlInterpolacaoCompressor(FON, Tf1, Pf, session);
        double kf1 = intCompress.getKv_g();
        double Cpf1 = intCompress.getCpv_g();
        double MUf1 = intCompress.getMuv_g();
        double Prf1 = intCompress.getPrv_g();
        double Vcf1 = intCompress.getVcv_g();
        double Df1 = intCompress.getDf_g();
        
        intCompress = new ControlInterpolacaoCompressor(FON, Tf2, Pf, session);
        double kf2 = intCompress.getKv_g();
        double Cpf2 = intCompress.getCpv_g();
        double MUf2 = intCompress.getMuv_g();
        double Prf2 = intCompress.getPrv_g();
        double Vcf2 = intCompress.getVcv_g();
        double Df2 = intCompress.getDf_g();
        
        intCompress = new ControlInterpolacaoCompressor(FON, Tfout, Pf, session);
        double kfout = intCompress.getKv_g();
        double Cpfout = intCompress.getCpv_g();
        double MUfout = intCompress.getMuv_g();
        double Prfout = intCompress.getPrv_g();
        double Vcfout = intCompress.getVcv_g();
        double Dfout = intCompress.getDf_g();
    }
    
}

if SUP==0
    epsup=0;
    NTUsup=0;
    Nhsup=0
    Ncsup=0;
Ghsup=0;
Gcsup=0;
Accsup=0;
Achsup=0; 
    
Cclat=(m*(PM(ii))*((Cp1s)))/1000; %kJ/K*s
Chlat=(mf*((Cpf2+Cpf1)/2))/1000;%kJ/K*s
Clatmin=min(Chlat,Cclat);%kJ/K*s
Clatmax=max(Chlat,Cclat);%kJ/K*s
C=Clatmin/Clatmax;
NTUlat=(UALAT)/Clatmin;
eplat=1-exp(-NTUlat);
Nhlat=2*NTUlat/0.8;
Nclat=2*NTUlat/0.8;
Ghlat=(((0.25)/((Prf1^(2/3))*Nhlat))*(2*((Df1+Df2)/2)*DPflat))^0.5; %kg/m2*s
Gclat=(((0.25)/((Pr1s^(2/3))*Nclat))*(2*((D1sv+D1sl)/2)*DPlat))^0.5; %kg/m2*s
Acclat=(m*(PM(ii)))/Gclat; %m2
Achlat=mf/Ghlat;  %m2


Ccsen=(m*(PM(ii))*((Cp6+Cp1s)/2))/1000;
Chsen=(mf*((Cpfout+Cpf2)/2))/1000;
Csenmin=min(Chsen,Ccsen);
Csenmax=max(Chsen,Ccsen);
C=Csenmin/Csenmax;
epsilonsen1=(Chsen*(Tf2-Tfout))/(Csupmin*(Tf2-T6));
NTUsen=(UASEN)/Csenmin;
epsilonsen2=1-exp((exp((-NTUsen^0.78)*C)-1)*((NTUsen^0.22)/C));
epsen=min(epsilonsen1,epsilonsen2);
Nhsen=1.1*NTUsen/0.8;
Ncsen=10*C*NTUsen/0.8;
Ghsen=(((0.25)/((Prf2^(2/3))*Nhsen))*(2*((Df2+Dfout)/2)*DPfsen))^0.5;
Gcsen=(((0.25)/((Pr1sl^(2/3))*Ncsen))*(2*((D6+D1sl)/2)*DPsen))^0.5;
Accsen=(m*(PM(ii)))/Gcsen;
Achsen=mf/Ghsen; 

else

Ccsup=(m*(PM(ii))*((Cp1+Cp1s)/2))/1000;
Chsup=(mf*((Cpf+Cpf1)/2))/1000;
Csupmin=min(Chsup,Ccsup);
Csupmax=max(Chsup,Ccsup);
C=Csupmin/Csupmax;
epsilonsup1=(Chsup*(Tf-Tf1))/(Csupmin*(Tf-T1s));
NTUsup=(UASUP)/Csupmin;
epsilonsup2=1-exp((exp((-NTUsup^0.78)*C)-1)*((NTUsup^0.22)/C));
epsup=min(epsilonsup1,epsilonsup2);
Nhsup=2*NTUsup/0.8;
Ncsup=2*NTUsup/0.8;
Ghsup=(((0.25)/((Prf^(2/3))*Nhsup))*(2*((Df1+Df)/2)*DPfsup))^0.5;
Gcsup=(((0.25)/((Pr1^(2/3))*Ncsup))*(2*((D1sv+D1)/2)*DPsup))^0.5;
Accsup=(m*(PM(ii)))/Gcsup;
Achsup=mf/Ghsup; 


Cclat=(m*(PM(ii))*((Cp1s)))/1000;
Chlat=(mf*((Cpf2+Cpf1)/2))/1000;
Clatmin=min(Chlat,Cclat);
Clatmax=max(Chlat,Cclat);
C=Clatmin/Clatmax;
NTUlat=(UALAT)/Clatmin;
eplat=1-exp(-NTUlat);
Nhlat=2*NTUlat/0.8;
Nclat=2*NTUlat/0.8;
Ghlat=(((0.25)/((Prf1^(2/3))*Nhlat))*(2*((Df1+Df2)/2)*DPflat))^0.5;
Gclat=(((0.25)/((Pr1s^(2/3))*Nclat))*(2*((D1sv+D1sl)/2)*DPlat))^0.5;
Acclat=(m*(PM(ii)))/Gclat;
Achlat=mf/Ghlat; 


Ccsen=(m*(PM(ii))*((Cp6+Cp1s)/2))/1000;
Chsen=(mf*((Cpfout+Cpf2)/2))/1000;
Csenmin=min(Chsen,Ccsen);
Csenmax=max(Chsen,Ccsen);
C=Csenmin/Csenmax;
epsilonsen1=(Chsen*(Tf2-Tfout))/(Csupmin*(Tf2-T6));
NTUsen=(UASEN)/Csenmin;
epsilonsen2=1-exp((exp((-NTUsen^0.78)*C)-1)*((NTUsen^0.22)/C));
epsen=min(epsilonsen1,epsilonsen2);
Nhsen=1.1*NTUsen/0.8;
Ncsen=10*C*NTUsen/0.8;
Ghsen=(((0.25)/((Prf2^(2/3))*Nhsen))*(2*((Df2+Dfout)/2)*DPfsen))^0.5;
Gcsen=(((0.25)/((Pr1sl^(2/3))*Ncsen))*(2*((D6+D1sl)/2)*DPsen))^0.5;
Accsen=(m*(PM(ii)))/Gcsen;
Achsen=mf/Ghsen; 
end
%%Dimensoes do Core 
[Dh1, alp1, del1, gam1, b1, t1, l1, s1, bet1, por1]=Core(13);
[Dh2, alp2, del2, gam2, b2, t2, l2, s2, bet2, por2]=Core(13);
aa1=(b1*bet1)/(b1+b2+(2*0.0005));
aa2=(b2*bet2)/(b1+b2+(2*0.0005));
AA=aa1/aa2;
cf1=aa1*Dh1/4;
cf2=aa2*Dh2/4;

erro1=1;
erro2=1;

while erro1>0 | erro2>0;
Rehsup= (Ghsup*Dh1)/((MUf+MUf1)/2);
Rehlat= (Ghlat*Dh1)/((MUf2+MUf1)/2);
Rehsen= (Ghsen*Dh1)/((MUf2+MUfout)/2);
fhsup= (9.6243*(Rehsup^-0.7422)*(alp1^-0.1856)*(del1^0.3053)*(gam1^-0.2659))*((1+(7.669e-8*(Rehsup^4.429)*(alp1^0.920)*(del1^3.767)*(gam1^0.236)))^0.1);
fhlat= (9.6243*(Rehlat^-0.7422)*(alp1^-0.1856)*(del1^0.3053)*(gam1^-0.2659))*((1+(7.669e-8*(Rehlat^4.429)*(alp1^0.920)*(del1^3.767)*(gam1^0.236)))^0.1);
fhsen= (9.6243*(Rehsen^-0.7422)*(alp1^-0.1856)*(del1^0.3053)*(gam1^-0.2659))*((1+(7.669e-8*(Rehsen^4.429)*(alp1^0.920)*(del1^3.767)*(gam1^0.236)))^0.1);
jhsup= (0.6522*(Rehsup^-0.5403)*(alp1^-0.1541)*(del1^0.1499)*(gam1^-0.0678))*((1+(5.269e-5*(Rehsup^1.340)*(alp1^0.504)*(del1^0.456)*(gam1^-1.055)))^0.1);
jhlat= (0.6522*(Rehlat^-0.5403)*(alp1^-0.1541)*(del1^0.1499)*(gam1^-0.0678))*((1+(5.269e-5*(Rehlat^1.340)*(alp1^0.504)*(del1^0.456)*(gam1^-1.055)))^0.1);
jhsen= (0.6522*(Rehsen^-0.5403)*(alp1^-0.1541)*(del1^0.1499)*(gam1^-0.0678))*((1+(5.269e-5*(Rehsen^1.340)*(alp1^0.504)*(del1^0.456)*(gam1^-1.055)))^0.1);
% Lhsup= (Dh1*(Prf^(2/3))*Nhsup)/(4*jhsup);
% Lhlat= (Dh1*(Prf1^(2/3))*Nhlat)/(4*jhlat);
% Lhsen= (Dh1*(Prf2^(2/3))*Nhsen)/(4*jhsen);

Recsup= (Gcsup*Dh2)/((MU1+MU1s)/2);
Reclat= (Gclat*Dh2)/((MU1s+MU1sl)/2);
Recsen= (Gcsen*Dh2)/((MU1sl+MU6)/2);
fcsup= (9.6243*(Recsup^-0.7422)*(alp2^-0.1856)*(del2^0.3053)*(gam2^-0.2659))*((1+(7.669e-8*(Recsup^4.429)*(alp2^0.920)*(del2^3.767)*(gam2^0.236)))^0.1);
fclat= (9.6243*(Reclat^-0.7422)*(alp2^-0.1856)*(del2^0.3053)*(gam2^-0.2659))*((1+(7.669e-8*(Reclat^4.429)*(alp2^0.920)*(del2^3.767)*(gam2^0.236)))^0.1);
fcsen= (9.6243*(Recsen^-0.7422)*(alp2^-0.1856)*(del2^0.3053)*(gam2^-0.2659))*((1+(7.669e-8*(Recsen^4.429)*(alp2^0.920)*(del2^3.767)*(gam2^0.236)))^0.1);
jcsup= (0.6522*(Recsup^-0.5403)*(alp2^-0.1541)*(del2^0.1499)*(gam2^-0.0678))*((1+(5.269e-5*(Recsup^1.340)*(alp2^0.504)*(del2^0.456)*(gam2^-1.055)))^0.1);
jclat= (0.6522*(Reclat^-0.5403)*(alp2^-0.1541)*(del2^0.1499)*(gam2^-0.0678))*((1+(5.269e-5*(Reclat^1.340)*(alp2^0.504)*(del2^0.456)*(gam2^-1.055)))^0.1);
jcsen= (0.6522*(Recsen^-0.5403)*(alp2^-0.1541)*(del2^0.1499)*(gam2^-0.0678))*((1+(5.269e-5*(Recsen^1.340)*(alp2^0.504)*(del2^0.456)*(gam2^-1.055)))^0.1);
% Lcsup= (Dh2*(Pr1^(2/3))*Ncsup)/(4*jcsup);
% Lclat= (Dh2*(Pr1s^(2/3))*Nclat)/(4*jclat);
% Lcsen= (Dh2*(Pr1sl^(2/3))*Ncsen)/(4*jcsen);

%%%%%%%%%%%%%   (W/m2*K)   %%%%%%%%%%%%%%%%%%%%
hhsup= jhsup*Ghsup*((Cpf+Cpf1)/2)/(Prf^(2/3)); % Coeficiente de transferÍncia de calor lado quente
hhlat= jhlat*Ghlat*((Cpf2+Cpf1)/2)/(Prf1^(2/3)); % Coeficiente de transferÍncia de calor lado quente
hhsen= jhsen*Ghsen*((Cpf2+Cpfout)/2)/(Prf2^(2/3)); % Coeficiente de transferÍncia de calor lado quente

hcsup= jcsup*Gcsup*((Cp1+Cp1s)/2)/(Pr1^(2/3)); % Coeficiente de transferÍncia de calor lado frio
hclat= jclat*Gclat*((Cp1s+Cp1s)/2)/(Pr1s^(2/3)); % Coeficiente de transferÍncia de calor lado frio
hcsen= jcsen*Gcsen*((Cp6+Cp1s)/2)/(Pr1sl^(2/3)); % Coeficiente de transferÍncia de calor lado frio

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Fatores para c·lclulo da eficiÍncia:
m1sup=(((2*hhsup)/(km*(t1)))*(1+(t1/l1)))^0.5;
m1lat=(((2*hhlat)/(km*(t1)))*(1+(t1/l1)))^0.5;
m1sen=(((2*hhsen)/(km*(t1)))*(1+(t1/l1)))^0.5;
lh=0.5*(b1-t1);

m2sup=(((2*hcsup)/(km*(t2)))*(1+(t2/l2)))^0.5;
m2lat=(((2*hclat)/(km*(t2)))*(1+(t2/l2)))^0.5;
m2sen=(((2*hcsen)/(km*(t2)))*(1+(t2/l2)))^0.5;
lc=0.5*(b2-t2);

e1sup= (tanh(m1sup*lh))/(m1sup*lh);  % EficiÍncia da aleta lado quente
e1lat= (tanh(m1lat*lh))/(m1lat*lh);  % EficiÍncia da aleta lado quente
e1sen= (tanh(m1sen*lh))/(m1sen*lh);  % EficiÍncia da aleta lado quente

e2sup= (tanh(m2sup*lc))/(m2sup*lc); % EficiÍncia da aleta lado frio
e2lat= (tanh(m2lat*lc))/(m2lat*lc);  % EficiÍncia da aleta lado frio
e2sen= (tanh(m2sen*lc))/(m2sen*lc);  % EficiÍncia da aleta lado frio


%The overall surface efficiencies with Af /A values from Fig. E9.1B or input are

e1gsup= 1-(1-e1sup)*por1;  % EficiÍncia global da SuperfÌcie lado Quente
e1glat= 1-(1-e1lat)*por1;  % EficiÍncia global da SuperfÌcie lado Quente
e1gsen= 1-(1-e1sen)*por1;  % EficiÍncia global da SuperfÌcie lado Quente

e2gsup= 1-(1-e2sup)*por2;  % EficiÍncia global da SuperfÌcie lado Frio
e2glat= 1-(1-e2lat)*por2;  % EficiÍncia global da SuperfÌcie lado frio
e2gsen= 1-(1-e2sen)*por2;


%coeficiente de transferencia de calor (W/m2*K)
iUsup=(1/(e1gsup*hhsup))+(AA/(e2gsup*hcsup))
Ugsup=1/iUsup;
iUlat=(1/(e1glat*hhlat))+(AA/(e2glat*hclat))
Uglat=1/iUlat;
iUsen=(1/(e1gsen*hhsen))+(AA/(e2gsen*hcsen))
Ugsen=1/iUsen;

%Area de superficie / %Area de fluxo livre  / Comprimento
Ahsup=NTUsup*Csupmin/(Ugsup/1000);
Ahlat=NTUlat*Clatmin/(Uglat/1000);
Ahsen=NTUsen*Csenmin/(Ugsen/1000);
Ahosup=mf/Ghsup;
Aholat=mf/Ghlat;
Ahosen=mf/Ghsen;
Aho=[Ahosup;Aholat;Ahosen];
AT=Ahsup+Ahsen+Ahlat;

Acsup=Ahsup/AA;
Aclat=Ahlat/AA;
Acsen=Ahsen/AA;
Acosup=(m*PM(ii))/Gcsup;
Acolat=(m*PM(ii))/Gclat;
Acosen=(m*PM(ii))/Gcsen;
Aco=[Acosup;Acolat;Acosen];

Aho=max(Aho);
Aco=max(Aco);

Lcsup=(Dh2*Acsup)/(4*Aco);
Lclat=(Dh2*Aclat)/(4*Aco);
Lcsen=(Dh2*Ahsen)/(4*Aco);
Lc=Lcsup+Lclat+Lcsen;
Lhsup=(Dh1*Ahsup)/(4*Aho);
Lhlat=(Dh1*Ahlat)/(4*Aho);
Lhsen=(Dh1*Ahsen)/(4*Aho);
Lh=Lhsup+Lhlat+Lhsen;


Ghsup=mf/Aho;
Ghlat=mf/Aho;
Ghsen=mf/Aho;
Gcsup=(m*PM(ii))/Aco;
Gclat=(m*PM(ii))/Aco;
Gcsen=(m*PM(ii))/Aco;

DPhsup= (2*(Ghsup^2)*Lhsup*fhsup)/(Dh1*((Df1+Df)/2));
DPhlat= (2*(Ghlat^2)*Lhlat*fhlat)/(Dh1*((Df1+Df2)/2));
DPhsen= (2*(Ghsen^2)*Lhsen*fhsen)/(Dh1*((Dfout+Df2)/2));
DPh=DPhsup+DPhlat+DPhsen;
erro1=DPh-DPf;

DPcsup= (2*(Gcsup^2)*Lcsup*fcsup)/(Dh2*((D1+D1sv)/2));
DPclat= (2*(Gclat^2)*Lclat*fclat)/(Dh2*((D1sv+D1sl)/2));
DPcsen= (2*(Gcsen^2)*Lcsen*fcsen)/(Dh2*((D1sl+D6)/2));
DPc=DPcsup+DPclat+DPcsen;
erro2=DPc-DP6;



 Afrh=Aho/cf1;
 Afrc=Aco/cf2;
 
 L3=Afrh/Lc;
 Vhx=L3*Lc*Lh;
% L3c=Afrc/Lh;
 


if SUP==0
Ghsup=0;
Gcsup=0;

Ghlat=((DPflat*(Dh1*((Df1+Df2)/2)))/(Lhlat*fhlat*2))^0.5;
Gclat=((DPlat*(Dh2*((D1sv+D1sl)/2)))/(Lclat*fcsup*2))^0.5;

Ghsen=((DPfsen*(Dh1*((Dfout+Df2)/2)))/(Lhsen*fhsen*2))^0.5;
Gcsen=((DPsen*(Dh2*((D6+D1sl)/2)))/(Lcsen*fcsen*2))^0.5;

else

Ghsup=((DPfsup*(Dh1*((Df1+Df)/2)))/(Lhsup*fhsup*2))^0.5;
Gcsup=((DPsup*(Dh2*((D1sv+D1)/2)))/(Lcsup*fcsup*2))^0.5;

Ghlat=((DPflat*(Dh1*((Df1+Df2)/2)))/(Lhlat*fhlat*2))^0.5;
Gclat=((DPlat*(Dh2*((D1sv+D1sl)/2)))/(Lclat*fcsup*2))^0.5;

Ghsen=((DPfsen*(Dh1*((Dfout+Df2)/2)))/(Lhsen*fhsen*2))^0.5;
Gcsen=((DPsen*(Dh2*((D6+D1sl)/2)))/(Lcsen*fcsen*2))^0.5;

end
end

end



% Rhsup=1/(e1gsup*hhsup*Ahsup);
% Rhlat=1/(e1glat*hhlat*Ahlat);
% Rhsen=1/(e1gsen*hhsen*Ahsen);
% 
% Rcsup=1/(e2gsup*hcsup*Acsup);
% Rclat=1/(e2glat*hclat*Aclat);
% Rcsen=1/(e2gsen*hcsen*Acsen);
% 
% RRsup=Rhsup/Rcsup;
% RRlat=Rhlat/Rclat;
% RRsen=Rhsen/Rcsen;
% 
% Twsup=((Tf-Tf1)/2)+(RRsup*((T1-T1s)/2));
% Twlat=((Tf1-Tf2)/2)+(RRlat*((T1s-T1s)/2));
% Twsen=((Tf2-Tfout)/2)+(RRsen*((T1s-T6)/2));
% 
% 
% Ghsup=(((jhsup/fhsup)/((Prf^(2/3))*Nhsup))*(2*((Df1+Df)/2)*DPhsup*e1gsup))^0.5;
% Gcsup=(((jcsup/fcsup)/((Pr1^(2/3))*Ncsup))*(2*((D1sv+D1)/2)*DPcsup*e2gsup))^0.5;
% 
% Ghlat=(((jhlat/fhlat)/((Prf1^(2/3))*Nhlat))*(2*((Df1+Df2)/2)*DPhlat*e1glat))^0.5;
% Gclat=(((jclat/fclat)/((Pr1s^(2/3))*Nclat))*(2*((D1sv+D1sl)/2)*DPclat*e2glat))^0.5;
% 
% Ghsen=(((jhsen/fhsen)/((Prf2^(2/3))*Nhsen))*(2*((Df2+Dfout)/2)*DPhsen*e1gsen))^0.5;
% Gcsen=(((jcsen/fcsen)/((Pr1sl^(2/3))*Ncsen))*(2*((D6+D1sl)/2)*DPcsen*e2gsen))^0.5;

