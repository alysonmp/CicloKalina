/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

/**
 *
 * @author alysonmp
 */
public class ControlFugmix {
    
    double bpi, bpj, cpi, cpj, vml, vmv, ap_1;
    
    public ControlFugmix(double T, double P, double zi){
        
        ControlConstantesMix constantesMix = new ControlConstantesMix(T, P, zi);
        ControlConstantes constantes = constantesMix.getConstantes();
        ControlZetamix zetamix = new ControlZetamix(constantesMix.getAm(), constantesMix.getBm(), constantesMix.getCm());
        
        bpi = constantes.getbi()-constantesMix.getbm();
        bpj = constantes.getbj()-constantesMix.getbm();
        cpi = constantes.getci()-constantesMix.getcm();
        cpj = constantes.getcj()-constantesMix.getcm();
        
        vml = zetamix.getZlm()*constantes.getR()*T/P;
        vmv = zetamix.getZvm()*constantes.getR()*T/P;
        
        ap_1 = ((-2)*((Math.pow(zi,2)*constantes.getai())+(Math.pow(constantesMix.getzj(), 2)*constantes.getaj())+(zi*constantesMix.getzj()*(Math.pow(constantes.getai()*constantes.getaj(), 0.5))*(1-constantesMix.getKij()))+(constantesMix.getzj()*zi*(Math.pow(constantes.getaj()*constantes.getai(), 0.5))*(1-constantesMix.getKji()))));
    }
}


function [phil_i, phiv_i, phil_j, phiv_j] = fugmix(T, P, zi)

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
[Zlm, Zvm, Zm] =Zetamix(Am, Bm, Cm);
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
ap_2=((-3)*((zi^2*zj*((ai*aj)^0.5)*(kij-kji))+(zj^2*zi*((aj*ai)^0.5)*(kji-kij))));
ap_3i=(2*(zi*zj*((ai*aj)^0.5)*(kij-kji)));
ap_3j=(2*(zj*zi*((ai*aj)^0.5)*(kji-kij)));
ap_4i=(2*zi*ai)+(zj*((ai*aj)^0.5)*(1-kij+1-kji+((kji-kij)*zj)));
ap_4j=(2*zj*aj)+(zi*((ai*aj)^0.5)*(1-kji+1-kij+((kij-kji)*zi)));

api=ap_1+ap_2+ap_3i+ap_4i;
apj=ap_1+ap_2+ap_3j+ap_4j;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
QQ=(bm^2+(6*bm*cm)+cm^2)*(-1);
DD_l=vml^2+(vml*(bm+cm))-(bm*cm);
DD_v=vmv^2+(vmv*(bm+cm))-(bm*cm);
D1_l=(1/((-QQ)^0.5))*log(((2*vml)+bm+cm-((-QQ)^0.5))/((2*vml)+bm+cm+((-QQ)^0.5)));
D1_v=(1/((-QQ)^0.5))*log(((2*vmv)+bm+cm-((-QQ)^0.5))/((2*vmv)+bm+cm+((-QQ)^0.5)));
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

F1v_i=((vmv/(QQ*DD_v))*(bm^2+cm^2+(6*bm*cm)+(bpi*bm)+(3*bpi*cm)+(3*bm*cpi)+(cm*cpi)))+((1/(QQ*DD_v))*((bpi*cm^2)+(bm^2*cpi)-(bm*bpi*cm)-(bm*cm*cpi)))+((D1_v/QQ)* ((bm*bpi)+(3*bm*cpi)-(bm^2)+(3*bpi*cm)+(cm*cpi)-(cm^2)-(6*bm*cm)));
F1l_i=((vml/(QQ*DD_l))*(bm^2+cm^2+(6*bm*cm)+(bpi*bm)+(3*bpi*cm)+(3*bm*cpi)+(cm*cpi)))+((1/(QQ*DD_l))*((bpi*cm^2)+(bm^2*cpi)-(bm*bpi*cm)-(bm*cm*cpi)))+((D1_l/QQ)* ((bm*bpi)+(3*bm*cpi)-(bm^2)+(3*bpi*cm)+(cm*cpi)-(cm^2)-(6*bm*cm)));

F1v_j=((vmv/(QQ*DD_v))*(bm^2+cm^2+(6*bm*cm)+(bpj*bm)+(3*bpj*cm)+(3*bm*cpj)+(cm*cpj)))+((1/(QQ*DD_v))*((bpj*cm^2)+(bm^2*cpj)-(bm*bpj*cm)-(bm*cm*cpj)))+((D1_v/QQ)* ((bm*bpj)+(3*bm*cpj)-(bm^2)+(3*bpj*cm)+(cm*cpj)-(cm^2)-(6*bm*cm)));
F1l_j=((vml/(QQ*DD_l))*(bm^2+cm^2+(6*bm*cm)+(bpj*bm)+(3*bpj*cm)+(3*bm*cpj)+(cm*cpj)))+((1/(QQ*DD_l))*((bpj*cm^2)+(bm^2*cpj)-(bm*bpj*cm)-(bm*cm*cpj)))+((D1_l/QQ)* ((bm*bpj)+(3*bm*cpj)-(bm^2)+(3*bpj*cm)+(cm*cpj)-(cm^2)-(6*bm*cm)));


phil_i=(exp((log(vml/(vml-bm)))+((bm+bpi)/(vml-bm))+((api*D1_l)/(R*T))+((am/(R*T))*F1l_i)-log(Zlm)))*zi*P;
phiv_i=(exp((log(vmv/(vmv-bm)))+((bm+bpi)/(vmv-bm))+((api*D1_v)/(R*T))+((am/(R*T))*F1v_i)-log(Zvm)))*zi*P;

phil_j=(exp((log(vml/(vml-bm)))+((bm+bpj)/(vml-bm))+((apj*D1_l)/(R*T))+((am/(R*T))*F1l_j)-log(Zlm)))*(1-zi)*P;
phiv_j=(exp((log(vmv/(vmv-bm)))+((bm+bpj)/(vmv-bm))+((apj*D1_v)/(R*T))+((am/(R*T))*F1v_j)-log(Zvm)))*(1-zi)*P;

end
