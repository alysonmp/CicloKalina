/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Interpolacao;

import Ciclo1.Control.TabelaFluidos.ControlButanoGas;
import Ciclo1.Control.TabelaFluidos.ControlD4Gas;
import Ciclo1.Control.TabelaFluidos.ControlD5Gas;
import Ciclo1.Control.TabelaFluidos.ControlD6Gas;
import Ciclo1.Control.TabelaFluidos.ControlDimetylbenzeneGas;
import Ciclo1.Control.TabelaFluidos.ControlEthylbenzeneGas;
import Ciclo1.Control.TabelaFluidos.ControlHeptaneGas;
import Ciclo1.Control.TabelaFluidos.ControlHexaneGas;
import Ciclo1.Control.TabelaFluidos.ControlIpentaneGas;
import Ciclo1.Control.TabelaFluidos.ControlIsobutanGas;
import Ciclo1.Control.TabelaFluidos.ControlMD2MGas;
import Ciclo1.Control.TabelaFluidos.ControlMD3MGas;
import Ciclo1.Control.TabelaFluidos.ControlMD4MGas;
import Ciclo1.Control.TabelaFluidos.ControlMDMGas;
import Ciclo1.Control.TabelaFluidos.ControlMMGas;
import Ciclo1.Control.TabelaFluidos.ControlOctaneGas;
import Ciclo1.Control.TabelaFluidos.ControlPentaneGas;
import Ciclo1.Control.TabelaFluidos.ControlPropaneGas;
import Ciclo1.Control.TabelaFluidos.ControlPropylbenzeneGas;
import Ciclo1.Control.TabelaFluidos.ControlR114Gas;
import Ciclo1.Control.TabelaFluidos.ControlR11Gas;
import Ciclo1.Control.TabelaFluidos.ControlR12Gas;
import Ciclo1.Control.TabelaFluidos.ControlR134AGas;
import Ciclo1.Control.TabelaFluidos.ControlR141BGas;
import Ciclo1.Control.TabelaFluidos.ControlR142BGas;
import Ciclo1.Control.TabelaFluidos.ControlR152AGas;
import Ciclo1.Control.TabelaFluidos.ControlR216_CAGas;
import Ciclo1.Control.TabelaFluidos.ControlR22Gas;
import Ciclo1.Control.TabelaFluidos.ControlR_1270Gas;
import Ciclo1.Control.TabelaFluidos.ControlR_40Gas;
import Ciclo1.Control.TabelaFluidos.ControlTolueneGas;
import Ciclo1.Control.TabelaFluidos.ControlWaterGas;
import javax.swing.JOptionPane;
import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlInterpolacaoGas {
    private Object gas;
    private double Cpv_g, Prv_g, kv_g, Muv_g, Vcv_g, Cpv_l, Prv_l, kv_l, Muv_l, Vcv_l;
    
        public ControlInterpolacaoGas(int fluido, double pressao, double temp, Session session){
            
            switch(fluido){
                case 1:
                    gas = new ControlWaterGas(session);
                    ((ControlWaterGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlWaterGas)gas).getCpv();
                    Prv_g = ((ControlWaterGas)gas).getPrv();
                    kv_g = ((ControlWaterGas)gas).getKv();
                    Muv_g = ((ControlWaterGas)gas).getMuv();
                    Vcv_g = ((ControlWaterGas)gas).getVcv();
                    break;
                    
                case 2:
                    gas = new ControlTolueneGas(session);
                    ((ControlTolueneGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlTolueneGas)gas).getCpv();
                    Prv_g = ((ControlTolueneGas)gas).getPrv();
                    kv_g = ((ControlTolueneGas)gas).getKv();
                    Muv_g = ((ControlTolueneGas)gas).getMuv();
                    Vcv_g = ((ControlTolueneGas)gas).getVcv();       
                    break;
                    
                case 3:
                    gas = new ControlEthylbenzeneGas(session); 
                    ((ControlEthylbenzeneGas)gas).interpolacao(pressao,temp);
                    Cpv_g = ((ControlEthylbenzeneGas)gas).getCpv();
                    Prv_g = ((ControlEthylbenzeneGas)gas).getPrv();
                    kv_g = ((ControlEthylbenzeneGas)gas).getKv();
                    Muv_g = ((ControlEthylbenzeneGas)gas).getMuv();
                    Vcv_g = ((ControlEthylbenzeneGas)gas).getVcv();
                    break;
                            
                case 4:
                    gas = new ControlPropylbenzeneGas(session);
                    ((ControlPropylbenzeneGas)gas).interpolacao(pressao,temp);
                    Cpv_g = ((ControlPropylbenzeneGas)gas).getCpv();
                    Prv_g = ((ControlPropylbenzeneGas)gas).getPrv();
                    kv_g = ((ControlPropylbenzeneGas)gas).getKv();
                    Muv_g = ((ControlPropylbenzeneGas)gas).getMuv();
                    Vcv_g = ((ControlPropylbenzeneGas)gas).getVcv();
                    break;
                    
                case 5:
                    gas = new ControlDimetylbenzeneGas(session);
                    ((ControlDimetylbenzeneGas)gas).interpolacao(pressao,temp);
                    Cpv_g = ((ControlDimetylbenzeneGas)gas).getCpv();
                    Prv_g = ((ControlDimetylbenzeneGas)gas).getPrv();
                    kv_g = ((ControlDimetylbenzeneGas)gas).getKv();
                    Muv_g = ((ControlDimetylbenzeneGas)gas).getMuv();
                    Vcv_g = ((ControlDimetylbenzeneGas)gas).getVcv();
                    break;
                    
                case 6:
                    gas = new ControlMDMGas(session);
                    ((ControlMDMGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlMDMGas)gas).getCpv();
                    Prv_g = ((ControlMDMGas)gas).getPrv();
                    kv_g = ((ControlMDMGas)gas).getKv();
                    Muv_g = ((ControlMDMGas)gas).getMuv();
                    Vcv_g = ((ControlMDMGas)gas).getVcv();
                    break;
                    
                case 7:
                    gas = new ControlMD2MGas(session);
                    ((ControlMD2MGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlMD2MGas)gas).getCpv();
                    Prv_g = ((ControlMD2MGas)gas).getPrv();
                    kv_g = ((ControlMD2MGas)gas).getKv();
                    Muv_g = ((ControlMD2MGas)gas).getMuv();
                    Vcv_g = ((ControlMD2MGas)gas).getVcv();
                    break;
                    
                case 8:
                    gas = new ControlMD3MGas(session);
                    ((ControlMD3MGas)gas).interpolacao(pressao, temp);
                   Cpv_g = ((ControlMD3MGas)gas).getCpv();
                    Prv_g = ((ControlMD3MGas)gas).getPrv();
                    kv_g = ((ControlMD3MGas)gas).getKv();
                    Muv_g = ((ControlMD3MGas)gas).getMuv();
                    Vcv_g = ((ControlMD3MGas)gas).getVcv();
                    break;
            
                case 9:
                    gas = new ControlD6Gas(session);
                    ((ControlD6Gas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlD6Gas)gas).getCpv();
                    Prv_g = ((ControlD6Gas)gas).getPrv();
                    kv_g = ((ControlD6Gas)gas).getKv();
                    Muv_g = ((ControlD6Gas)gas).getMuv();
                    Vcv_g = ((ControlD6Gas)gas).getVcv();
                    break;
                    
                case 10:
                    gas = new ControlIsobutanGas(session);
                    ((ControlIsobutanGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlIsobutanGas)gas).getCpv();
                    Prv_g = ((ControlIsobutanGas)gas).getPrv();
                    kv_g = ((ControlIsobutanGas)gas).getKv();
                    Muv_g = ((ControlIsobutanGas)gas).getMuv();
                    Vcv_g = ((ControlIsobutanGas)gas).getVcv();

                    break;
               
                case 11:
                    gas = new ControlIpentaneGas(session);
                    ((ControlIpentaneGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlIpentaneGas)gas).getCpv();
                    Prv_g = ((ControlIpentaneGas)gas).getPrv();
                    kv_g = ((ControlIpentaneGas)gas).getKv();
                    Muv_g = ((ControlIpentaneGas)gas).getMuv();
                    Vcv_g = ((ControlIpentaneGas)gas).getVcv();
                    break;    

                case 12:
                    gas = new ControlButanoGas(session);
                    ((ControlButanoGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlButanoGas)gas).getCpv();
                    Prv_g = ((ControlButanoGas)gas).getPrv();
                    kv_g = ((ControlButanoGas)gas).getKv();
                    Muv_g = ((ControlButanoGas)gas).getMuv();
                    Vcv_g = ((ControlButanoGas)gas).getVcv();
                    break;

                case 13:
                    gas = new ControlPentaneGas(session);
                    ((ControlPentaneGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlPentaneGas)gas).getCpv();
                    Prv_g = ((ControlPentaneGas)gas).getPrv();
                    kv_g = ((ControlPentaneGas)gas).getKv();
                    Muv_g = ((ControlPentaneGas)gas).getMuv();
                    Vcv_g = ((ControlPentaneGas)gas).getVcv();

                    break;

                case 14:
                    gas = new ControlR134AGas(session);
                    ((ControlR134AGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlR134AGas)gas).getCpv();
                    Prv_g = ((ControlR134AGas)gas).getPrv();
                    kv_g = ((ControlR134AGas)gas).getKv();
                    Muv_g = ((ControlR134AGas)gas).getMuv();
                    Vcv_g = ((ControlR134AGas)gas).getVcv();
                    break;
                
                case 15:
                    gas = new ControlPropaneGas(session);
                    ((ControlPropaneGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlPropaneGas)gas).getCpv();
                    Prv_g = ((ControlPropaneGas)gas).getPrv();
                    kv_g = ((ControlPropaneGas)gas).getKv();
                    Muv_g = ((ControlPropaneGas)gas).getMuv();
                    Vcv_g = ((ControlPropaneGas)gas).getVcv();
                    break;

                case 17:
                    gas = new ControlR_1270Gas(session);
                    ((ControlR_1270Gas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlR_1270Gas)gas).getCpv();
                    Prv_g = ((ControlR_1270Gas)gas).getPrv();
                    kv_g = ((ControlR_1270Gas)gas).getKv();
                    Muv_g = ((ControlR_1270Gas)gas).getMuv();
                    Vcv_g = ((ControlR_1270Gas)gas).getVcv();
                    break;
                    
                case 18:
                    gas = new ControlR_40Gas(session);
                    ((ControlR_40Gas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlR_40Gas)gas).getCpv();
                    Prv_g = ((ControlR_40Gas)gas).getPrv();
                    kv_g = ((ControlR_40Gas)gas).getKv();
                    Muv_g = ((ControlR_40Gas)gas).getMuv();
                    Vcv_g = ((ControlR_40Gas)gas).getVcv();
                    break;
                    
                    
                case 16:
                case 29:
                    gas = new ControlR142BGas(session);
                    ((ControlR142BGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlR142BGas)gas).getCpv();
                    Prv_g = ((ControlR142BGas)gas).getPrv();
                    kv_g = ((ControlR142BGas)gas).getKv();
                    Muv_g = ((ControlR142BGas)gas).getMuv();
                    Vcv_g = ((ControlR142BGas)gas).getVcv();
                    break;

                case 19:
                    gas = new ControlMMGas(session);
                    ((ControlMMGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlMMGas)gas).getCpv();
                    Prv_g = ((ControlMMGas)gas).getPrv();
                    kv_g = ((ControlMMGas)gas).getKv();
                    Muv_g = ((ControlMMGas)gas).getMuv();
                    Vcv_g = ((ControlMMGas)gas).getVcv();
                    break;

                case 20:
                    gas = new ControlMD4MGas(session);
                    ((ControlMD4MGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlMD4MGas)gas).getCpv();
                    Prv_g = ((ControlMD4MGas)gas).getPrv();
                    kv_g = ((ControlMD4MGas)gas).getKv();
                    Muv_g = ((ControlMD4MGas)gas).getMuv();
                    Vcv_g = ((ControlMD4MGas)gas).getVcv();
                    break;

                case 21:
                    gas = new ControlD4Gas(session);
                    ((ControlD4Gas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlD4Gas)gas).getCpv();
                    Prv_g = ((ControlD4Gas)gas).getPrv();
                    kv_g = ((ControlD4Gas)gas).getKv();
                    Muv_g = ((ControlD4Gas)gas).getMuv();
                    Vcv_g = ((ControlD4Gas)gas).getVcv();
                    break;

                case 22:
                    gas = new ControlD5Gas(session);
                    ((ControlD5Gas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlD5Gas)gas).getCpv();
                    Prv_g = ((ControlD5Gas)gas).getPrv();
                    kv_g = ((ControlD5Gas)gas).getKv();
                    Muv_g = ((ControlD5Gas)gas).getMuv();
                    Vcv_g = ((ControlD5Gas)gas).getVcv();
                    break;

                case 23:
                    gas = new ControlR11Gas(session);
                    ((ControlR11Gas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlR11Gas)gas).getCpv();
                    Prv_g = ((ControlR11Gas)gas).getPrv();
                    kv_g = ((ControlR11Gas)gas).getKv();
                    Muv_g = ((ControlR11Gas)gas).getMuv();
                    Vcv_g = ((ControlR11Gas)gas).getVcv();
                    break;

                case 24:
                    gas = new ControlR12Gas(session);
                    ((ControlR12Gas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlR12Gas)gas).getCpv();
                    Prv_g = ((ControlR12Gas)gas).getPrv();
                    kv_g = ((ControlR12Gas)gas).getKv();
                    Muv_g = ((ControlR12Gas)gas).getMuv();
                    Vcv_g = ((ControlR12Gas)gas).getVcv();
                    break;

                case 25:
                    gas = new ControlR216_CAGas(session);
                    ((ControlR216_CAGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlR216_CAGas)gas).getCpv();
                    Prv_g = ((ControlR216_CAGas)gas).getPrv();
                    kv_g = ((ControlR216_CAGas)gas).getKv();
                    Muv_g = ((ControlR216_CAGas)gas).getMuv();
                    Vcv_g = ((ControlR216_CAGas)gas).getVcv();
                    break;
                    
                    
                case 26:
                    gas = new ControlR114Gas(session);
                    ((ControlR114Gas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlR114Gas)gas).getCpv();
                    Prv_g = ((ControlR114Gas)gas).getPrv();
                    kv_g = ((ControlR114Gas)gas).getKv();
                    Muv_g = ((ControlR114Gas)gas).getMuv();
                    Vcv_g = ((ControlR114Gas)gas).getVcv();
                    break;

                case 27:
                    gas = new ControlR141BGas(session);
                    ((ControlR141BGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlR141BGas)gas).getCpv();
                    Prv_g = ((ControlR141BGas)gas).getPrv();
                    kv_g = ((ControlR141BGas)gas).getKv();
                    Muv_g = ((ControlR141BGas)gas).getMuv();
                    Vcv_g = ((ControlR141BGas)gas).getVcv();
                    break;

                case 28:
                    gas = new ControlR152AGas(session);
                    ((ControlR152AGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlR152AGas)gas).getCpv();
                    Prv_g = ((ControlR152AGas)gas).getPrv();
                    kv_g = ((ControlR152AGas)gas).getKv();
                    Muv_g = ((ControlR152AGas)gas).getMuv();
                    Vcv_g = ((ControlR152AGas)gas).getVcv();
                    break;

                case 30:
                    gas = new ControlHexaneGas(session);
                    ((ControlHexaneGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlHexaneGas)gas).getCpv();
                    Prv_g = ((ControlHexaneGas)gas).getPrv();
                    kv_g = ((ControlHexaneGas)gas).getKv();
                    Muv_g = ((ControlHexaneGas)gas).getMuv();
                    Vcv_g = ((ControlHexaneGas)gas).getVcv();
                    break;

                case 31:
                    gas = new ControlHeptaneGas(session);
                    ((ControlHeptaneGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlHeptaneGas)gas).getCpv();
                    Prv_g = ((ControlHeptaneGas)gas).getPrv();
                    kv_g = ((ControlHeptaneGas)gas).getKv();
                    Muv_g = ((ControlHeptaneGas)gas).getMuv();
                    Vcv_g = ((ControlHeptaneGas)gas).getVcv();
                    break;

                case 32:
                    gas = new ControlOctaneGas(session);
                    ((ControlOctaneGas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlOctaneGas)gas).getCpv();
                    Prv_g = ((ControlOctaneGas)gas).getPrv();
                    kv_g = ((ControlOctaneGas)gas).getKv();
                    Muv_g = ((ControlOctaneGas)gas).getMuv();
                    Vcv_g = ((ControlOctaneGas)gas).getVcv();
                    break;

                case 33:
                    gas = new ControlR22Gas(session);
                    ((ControlR22Gas)gas).interpolacao(pressao, temp);
                    Cpv_g = ((ControlR22Gas)gas).getCpv();
                    Prv_g = ((ControlR22Gas)gas).getPrv();
                    kv_g = ((ControlR22Gas)gas).getKv();
                    Muv_g = ((ControlR22Gas)gas).getMuv();
                    Vcv_g = ((ControlR22Gas)gas).getVcv();
                    break;

                default: 
                    JOptionPane.showMessageDialog(null, "Fluido inexistente");
            }
        
        }
}
