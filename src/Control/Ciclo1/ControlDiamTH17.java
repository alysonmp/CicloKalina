/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

import java.util.ArrayList;

/**
 *
 * @author leonardo
 */
public class ControlDiamTH17 {
    private double Dr, Teff;
    
    public ControlDiamTH17(double v2, double DHT) {
        
        double NN = 18000, Nsa, Dsa = 0;
        double V3 = v2, Had = DHT;
        int e;
        
        ArrayList<Double> NRi_70 = new ArrayList<>();
        ArrayList<Double> Dii_70 = new ArrayList<>();
        ArrayList<Double> NRs_70 = new ArrayList<>();
        ArrayList<Double> Dis_70 = new ArrayList<>();
        
        ArrayList<Double> NRi_80 = new ArrayList<>();
        ArrayList<Double> Dii_80 = new ArrayList<>();
        ArrayList<Double> NRs_80 = new ArrayList<>();
        ArrayList<Double> Dis_80 = new ArrayList<>();
        
        for(int k=0;k<108;k++){
            Nsa = dr70[k][0];
            if(k>=0 && k < 4){
                e = 0;
                Dsa = eqrs[e][0]+eqrs[e][1]*Math.pow(Nsa,3)+eqrs[e][2]*Math.exp(Nsa);
            }else if(k>=4 && k < 102){
                e = 1;
                Dsa = (Math.pow((eqrs[e][0]+eqrs[e][1]/(Math.pow((Nsa),2))),0.5));
            }else if(k>= 102 && k < 105){
                e = 4;
                Dsa = Math.pow((eqrs[e][0]+eqrs[e][1]*Math.exp(Nsa)),-1);
            }else if(k>=105 && k < 108){
                e = 5;
                Dsa = Math.exp(eqrs[e][0]+eqrs[e][1]*(Nsa));
            }
            
            NRi_70.add(((Math.pow(Had,0.75))*Nsa)/(Math.pow(V3,0.5)));
            Dii_70.add((Dsa*(Math.pow(V3,0.5)))/(Math.pow(Had,0.25)));
            //plot(NRi_70,Dii_70,'rv');
            //hold on;
        }
        
        for(int k=0;k<84;k++){
            Nsa=dr70[k][2];
            if(k>=0 && k< 80){
                e = 2;
                Dsa=Math.pow((eqrs[e][0]+eqrs[e][1]*(Nsa)),-1);
            }else if(k>= 80 && k < 84){
                e = 3;
                Dsa=(eqrs[e][0]+eqrs[e][1]*Math.exp(-Nsa));
            }
            
            NRs_70.add(((Math.pow(Had,0.75))*Nsa)/(Math.pow(V3,0.5)));
            Dis_70.add((Dsa*(Math.pow(V3,0.5)))/(Math.pow(Had,0.25)));
            //plot(NRs_70,Dis_70,'rv');
            //hold on;
        }
        
        for(int k=0; k < 67;k++){//Curva Inferior (Restante)
            Nsa=dr80[k][0];
            if(k >= 0 && k < 63){
                e = 0;
                Dsa=Math.pow((eqro[e][0]+eqro[e][1]*Math.log(Nsa)),-1);
            }else if(k >= 63 && k < 67){
                e=3;
                Dsa = Math.pow((eqro[e][0]+eqro[e][1]*Math.exp(Nsa)),-1);
            }
            NRi_80.add(((Math.pow(Had,0.75))*Nsa)/(Math.pow(V3,0.5)));
            Dii_80.add((Dsa*(Math.pow(V3,0.5)))/(Math.pow(Had,0.25)));
            //plot(NRi_80,Dii_80,'bv')
            //hold on
        }
        
        for(int k=0; k < 70;k++){//Curva Superior (Restante)
            Nsa=dr80[k][2];
            if(k >= 0 && k < 66){
                e = 1;
                Dsa=((Math.log(Nsa))/(eqro[e][0]*Math.log(Nsa)+eqro[e][1]*(Nsa)));
            }else if(k >= 66 && k < 70){
                e = 2;
                Dsa=((eqro[e][0])+(eqro[e][1]/(Nsa))+(eqro[e][2]/Math.pow((Nsa),2)));
            }
            
            NRs_80.add(((Math.pow(Had,0.75))*Nsa)/(Math.pow(V3,0.5)));
            Dis_80.add((Dsa*(Math.pow(V3,0.5)))/(Math.pow(Had,0.25)));
            //plot(NRs_80,Dis_80,'bv')
            //hold on
        }
        
        double MNRb = NRs_70.get(0);
        double MNRa = NRi_70.get(0);
        
        for(int i=1;i< NRi_70.size();i++){
            if(MNRa > NRi_70.get(i)){
                MNRa = NRi_70.get(i);
            }
        }
        
        for(int i=1;i< NRs_70.size();i++){
            if(MNRb < NRs_70.get(i)){
                MNRb = NRs_70.get(i);
            }
        }
        
        if(MNRa >= NN || MNRb <= NN){
        
        }else{
            double NRoa = NN;
            double Nsoa = (Math.pow(V3,0.5)*NRoa)/Math.pow(Had,0.75);
            
            e = 3;
            double Dsoa = Math.pow((eqrs[e][0]+eqrs[e][1]*(Nsoa)),-1); 
            double Ds_70 = (Dsoa*Math.pow(V3,0.5))/math.pow(Had,0.25);
            //plot(NRoa,Ds_70,'gv-');
            //hold on;
            
            e = 2;
            Dsoa = eqrs[e][0]+eqrs[e][1]/Math.pow(Math.pow((Nsoa),2),0.5);
            double Di_70 = (Dsoa*Math.pow(V3,0.5))/math.pow(Had,0.25);
            //plot(NRoa,Di_70,'gv-');
            //hold on
            
            //Dr70=[Ds_70,Di_70]; 
            double Dr70 = (Ds_70+Di_70)/2;
            
            Dr=Dr70;
            Teff = 0.7;
            
            double MNRd = NRs_80.get(0);
            double MNRc = NRi_80.get(0);

            for(int i=1;i< NRi_80.size();i++){
                if(MNRc > NRi_80.get(i)){
                    MNRc = NRi_80.get(i);
                }
            }

            for(int i=1;i< NRs_80.size();i++){
                if(MNRd < NRs_80.get(i)){
                    MNRd = NRs_80.get(i);
                }
            }
            
            if(MNRc>=NN || MNRd<=NN){
            
            }else{
                NRoa=NN;
                Nsoa=(Math.pow(V3,0.5)*NRoa)/Math.pow(Had,0.75);
                
                e=2;
                double Dsos=((Math.log(Nsoa))/(eqro[e][0]*Math.log(Nsoa)+eqro[e][1]*(Nsoa))); 
                double Ds_80=(Dsos*Math.pow(V3,0.5))/Math.pow(Had,0.25);
                //plot(NRoa,Ds_80,'gv-');
                //hold on

                e=1;
                double Dsoi= Math.pow((eqro[e][0]+eqro[e][1]*Math.log(Nsoa)),-1); 
                double Di_80= (Dsoi*Math.pow(V3,0.5))/Math.pow(Had,0.25);
                //plot(NRoa,Di_80,'gv-');
                //hold on;

                //Dr80=[Ds_80 Di_80];
                double Dr80=(Ds_80+Di_80)/2;

                Dr=Dr80
                //%disp ('Eficiencia da Turbina 80%')
                Teff=0.8;
            }
        }
    }

    public double getDr() {
        return Dr;
    }

    public void setDr(double Dr) {
        this.Dr = Dr;
    }

    public double getTeff() {
        return Teff;
    }

    public void setTeff(double Teff) {
        this.Teff = Teff;
    }
    
    
    
}
