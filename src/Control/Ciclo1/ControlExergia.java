/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class ControlExergia {
    
    private double To, Po;
    
    public ControlExergia(double H1, double H2, double H3, double H4, double H5, double H6, double S1, double S2, double S3, double S4, double S5, double S6, double m, double Pref, double Tref, double ii, double Qcon, double Qevp, double mf, double Tf, double Tfout, double[] X, double FON, double Pf, Session session){
        To=25+273.15;
        Po=101.325;
        
        if(FON != 1){
            ControlExergia_Gases exergiaGases = new ControlExergia_Gases(mf, Tf, X, session);
            Ein = exergiaGases.get
                    
            [Ein, Hin, Sin, bgasin, ETin]=Exergia_Gases(mf, Tf, X);
            [Eout, Hout, Sout, bgasout, ETout]=Exergia_Gases(mf, Tfout, X);
    }
    
}

function [E1, E2, E3, E4, E5, E6, EXevp, EXcon]= exergia(H1, H2, H3, H4, H5, H6, S1, S2, S3, S4, S5, S6, m, Pref, Tref, ii, Qcon, Qevp, mf, Tf, Tfout, X, FON, Pf)

%%%%%%%%%%%%%Agua ou g·s no Evaporador  %%%%%%%%%
if FON ~= 1
[Ein, Hin, Sin, bgasin, ETin]=Exergia_Gases(mf, Tf, X);
[Eout, Hout, Sout, bgasout, ETout]=Exergia_Gases(mf, Tfout, X);
%EXevp=(ETin-ETout)*mf;
EXevp=(ETin)*mf; %Teorria josÈ joaquin
else
 Ten=Tf;
 Pen=Pf;
 Ts=Tfout;
 Ps=Pen;
[HLo, HVo] = H_sistema(To, Po, Pref, Tref, 1);
[SLo, SVo] = S_sistema(To, Po, Pref, Tref, 1);
[HLen, HVen] = H_sistema(Ten, Pen, Pref, Tref, 1);
[HLs, HVs] = H_sistema(Ts, Ps, Pref, Tref, 1); 
 [SLen, SVen] = S_sistema(Ten, Pen, Pref, Tref, 1);
 [SLs, SVs] = S_sistema(Ts, Ps, Pref, Tref, 1);
 mH2O=mf;
  EH2Oe= mH2O*((HLen-HLo)-(To*(SLen-SLo)));
 EH2Os= mH2O*((HLs-HLo)-(To*(SLs-SLo)));
 
 %EXevp=EH2Oe-EH2Os;
 EXevp=EH2Oe; %Jose Joaquin
end
%%%%%%agua de arrefecimento%%%%%
 Ten=25+273.15;
 Pen=(101.325)*1.2;
 Ts=35+273.15;
 Ps=Pen;
[HLo, HVo] = H_sistema(To, Po, Pref, Tref, 1);
[SLo, SVo] = S_sistema(To, Po, Pref, Tref, 1);
[HLen, HVen] = H_sistema(Ten, Pen, Pref, Tref, 1);
[HLs, HVs] = H_sistema(Ts, Ps, Pref, Tref, 1); 
 [SLen, SVen] = S_sistema(Ten, Pen, Pref, Tref, 1);
 [SLs, SVs] = S_sistema(Ts, Ps, Pref, Tref, 1);
 mH2O=Qcon/(HLs-HLen);
  EH2Oe= mH2O*((HLen-HLo)-(To*(SLen-SLo)));
 EH2Os= mH2O*((HLs-HLo)-(To*(SLs-SLo)));
 
 EXcon=EH2Os-EH2Oe
 
 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

 %[HL, HV] = H_sistema(To, Po, Pref, Tref, ii); 
% 
% [SL, SV] = S_sistema(To, Po, Pref, Tref, ii);
% 
% [Ps2] = Pdevapor(To, ii);
% if Ps2<Po
%      Ho=H4;
%      So=S4;
% else
%     Ho=HV;
%     So=SV;
% end
    [Ho, HV] = H_sistema(To, Po, Pref, Tref, ii); 
    [So, SV] = S_sistema(To, Po, Pref, Tref, ii);

E1= m*((H1-Ho)-(To*(S1-So)));
E2= m*((H2-Ho)-(To*(S2-So)));
E3= m*((H3-Ho)-(To*(S3-So)));
E4= m*((H4-Ho)-(To*(S4-So)));
E5= m*((H5-Ho)-(To*(S5-So)));
E6= m*((H6-Ho)-(To*(S6-So)));


% Eoutlet=m*((Houlet-Ho)-(To*(Soulet-So)));
% 
% HNH3e=5803.8; %kJ/kmol
% HNH3s=24429; %kJ/kmol
% SNH3e=22.848; %kJ/kmol K
% SNH3s=94.962; %%kJ/kmol K
% 
% mNH3=PF/(HNH3s-HNH3e); %kmol
% DENH3= mNH3*((HNH3e-HNH3s)-(To*(SNH3e-SNH3s)));

