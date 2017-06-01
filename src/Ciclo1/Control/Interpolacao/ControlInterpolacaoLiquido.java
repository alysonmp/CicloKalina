/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Interpolacao;

import Ciclo1.Control.TabelaFluidos.ControlButanoLiquido;
import Ciclo1.Control.TabelaFluidos.ControlD4Liquido;
import Ciclo1.Control.TabelaFluidos.ControlD5Liquido;
import Ciclo1.Control.TabelaFluidos.ControlD6Liquido;
import Ciclo1.Control.TabelaFluidos.ControlDimetylbenzeneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlEthylbenzeneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlHeptaneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlHexaneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlIpentaneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlIsobutanLiquido;
import Ciclo1.Control.TabelaFluidos.ControlMD2MLiquido;
import Ciclo1.Control.TabelaFluidos.ControlMD3MLiquido;
import Ciclo1.Control.TabelaFluidos.ControlMD4MLiquido;
import Ciclo1.Control.TabelaFluidos.ControlMDMLiquido;
import Ciclo1.Control.TabelaFluidos.ControlMMLiquido;
import Ciclo1.Control.TabelaFluidos.ControlOctaneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlPentaneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlPropaneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlPropylbenzeneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlR114Liquido;
import Ciclo1.Control.TabelaFluidos.ControlR11Liquido;
import Ciclo1.Control.TabelaFluidos.ControlR12Liquido;
import Ciclo1.Control.TabelaFluidos.ControlR134ALiquido;
import Ciclo1.Control.TabelaFluidos.ControlR141BLiquido;
import Ciclo1.Control.TabelaFluidos.ControlR142BLiquido;
import Ciclo1.Control.TabelaFluidos.ControlR152ALiquido;
import Ciclo1.Control.TabelaFluidos.ControlR216_CALiquido;
import Ciclo1.Control.TabelaFluidos.ControlR22Liquido;
import Ciclo1.Control.TabelaFluidos.ControlR_1270Liquido;
import Ciclo1.Control.TabelaFluidos.ControlR_40Liquido;
import Ciclo1.Control.TabelaFluidos.ControlTolueneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlWaterLiquido;
import javax.swing.JOptionPane;
import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlInterpolacaoLiquido {
    private Object liquido;
    private double Cpv_g, Prv_g, kv_g, Muv_g, Vcv_g, Cpv_l, Prv_l, kv_l, Muv_l, Vcv_l;
    
    public ControlInterpolacaoLiquido(int fluido, double pressao, double temp, Session session) {
   
        switch(fluido){
            case 1:
                liquido = new ControlWaterLiquido(session);
                ((ControlWaterLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlWaterLiquido)liquido).getCpl();
                Prv_l = ((ControlWaterLiquido)liquido).getPrl();
                kv_l = ((ControlWaterLiquido)liquido).getKl();
                Muv_l = ((ControlWaterLiquido)liquido).getMul();
                Vcv_l = ((ControlWaterLiquido)liquido).getVcl();
                break;
                
            case 2:
                liquido = new ControlTolueneLiquido(session);
                ((ControlTolueneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlTolueneLiquido)liquido).getCpl();
                Prv_l = ((ControlTolueneLiquido)liquido).getPrl();
                kv_l = ((ControlTolueneLiquido)liquido).getKl();
                Muv_l = ((ControlTolueneLiquido)liquido).getMul();
                Vcv_l = ((ControlTolueneLiquido)liquido).getVcl();
                break;
                
            case 3:
                liquido = new ControlEthylbenzeneLiquido(session);
                ((ControlEthylbenzeneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlEthylbenzeneLiquido)liquido).getCpl();
                Prv_l = ((ControlEthylbenzeneLiquido)liquido).getPrl();
                kv_l = ((ControlEthylbenzeneLiquido)liquido).getKl();
                Muv_l = ((ControlEthylbenzeneLiquido)liquido).getMul();
                Vcv_l = ((ControlEthylbenzeneLiquido)liquido).getVcl();
                break;
                            
            case 4:
                liquido = new ControlPropylbenzeneLiquido(session);
                ((ControlPropylbenzeneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlPropylbenzeneLiquido)liquido).getCpl();
                Prv_l = ((ControlPropylbenzeneLiquido)liquido).getPrl();
                kv_l = ((ControlPropylbenzeneLiquido)liquido).getKl();
                Muv_l = ((ControlPropylbenzeneLiquido)liquido).getMul();
                Vcv_l = ((ControlPropylbenzeneLiquido)liquido).getVcl();
                    break;
                    
            case 5:
                liquido = new ControlDimetylbenzeneLiquido(session);
                ((ControlDimetylbenzeneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlDimetylbenzeneLiquido)liquido).getCpl();
                Prv_l = ((ControlDimetylbenzeneLiquido)liquido).getPrl();
                kv_l = ((ControlDimetylbenzeneLiquido)liquido).getKl();
                Muv_l = ((ControlDimetylbenzeneLiquido)liquido).getMul();
                Vcv_l = ((ControlDimetylbenzeneLiquido)liquido).getVcl();
                break;
                
            case 6:
                liquido = new ControlMDMLiquido(session);
                ((ControlMDMLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlMDMLiquido)liquido).getCpl();
                Prv_l = ((ControlMDMLiquido)liquido).getPrl();
                kv_l = ((ControlMDMLiquido)liquido).getKl();
                Muv_l = ((ControlMDMLiquido)liquido).getMul();
                Vcv_l = ((ControlMDMLiquido)liquido).getVcl();
                break;
              
            case 7:
                liquido = new ControlMD2MLiquido(session);
                ((ControlMD2MLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlMD2MLiquido)liquido).getCpl();
                Prv_l = ((ControlMD2MLiquido)liquido).getPrl();
                kv_l = ((ControlMD2MLiquido)liquido).getKl();
                Muv_l = ((ControlMD2MLiquido)liquido).getMul();
                Vcv_l = ((ControlMD2MLiquido)liquido).getVcl();
                break;
                
            case 8:
                liquido = new ControlMD3MLiquido(session);
                ((ControlMD3MLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlMD3MLiquido)liquido).getCpl();
                Prv_l = ((ControlMD3MLiquido)liquido).getPrl();
                kv_l = ((ControlMD3MLiquido)liquido).getKl();
                Muv_l = ((ControlMD3MLiquido)liquido).getMul();
                Vcv_l = ((ControlMD3MLiquido)liquido).getVcl();
                break;
                
            case 9:
                liquido = new ControlD6Liquido(session);
                ((ControlD6Liquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlD6Liquido)liquido).getCpl();
                Prv_l = ((ControlD6Liquido)liquido).getPrl();
                kv_l = ((ControlD6Liquido)liquido).getKl();
                Muv_l = ((ControlD6Liquido)liquido).getMul();
                Vcv_l = ((ControlD6Liquido)liquido).getVcl();
                break;
                
            case 10:
                liquido = new ControlIsobutanLiquido(session);
                ((ControlIsobutanLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlIsobutanLiquido)liquido).getCpl();
                Prv_l = ((ControlIsobutanLiquido)liquido).getPrl();
                kv_l = ((ControlIsobutanLiquido)liquido).getKl();
                Muv_l = ((ControlIsobutanLiquido)liquido).getMul();
                Vcv_l = ((ControlIsobutanLiquido)liquido).getVcl();
                break;
                
            case 11:
                liquido = new ControlIpentaneLiquido(session);
                ((ControlIpentaneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlIpentaneLiquido)liquido).getCpl();
                Prv_l = ((ControlIpentaneLiquido)liquido).getPrl();
                kv_l = ((ControlIpentaneLiquido)liquido).getKl();
                Muv_l = ((ControlIpentaneLiquido)liquido).getMul();
                Vcv_l = ((ControlIpentaneLiquido)liquido).getVcl();
                break;    
                
            case 12:
                liquido = new ControlButanoLiquido(session);
                ((ControlButanoLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlButanoLiquido)liquido).getCpl();
                Prv_l = ((ControlButanoLiquido)liquido).getPrl();
                kv_l = ((ControlButanoLiquido)liquido).getKl();
                Muv_l = ((ControlButanoLiquido)liquido).getMul();
                Vcv_l = ((ControlButanoLiquido)liquido).getVcl();
                break;
                
            case 13:
                liquido = new ControlPentaneLiquido(session);
                ((ControlPentaneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlPentaneLiquido)liquido).getCpl();
                Prv_l = ((ControlPentaneLiquido)liquido).getPrl();
                kv_l = ((ControlPentaneLiquido)liquido).getKl();
                Muv_l = ((ControlPentaneLiquido)liquido).getMul();
                Vcv_l = ((ControlPentaneLiquido)liquido).getVcl();
                break;
                
            case 14:
                liquido = new ControlR134ALiquido(session);
                ((ControlR134ALiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlR134ALiquido)liquido).getCpl();
                Prv_l = ((ControlR134ALiquido)liquido).getPrl();
                kv_l = ((ControlR134ALiquido)liquido).getKl();
                Muv_l = ((ControlR134ALiquido)liquido).getMul();
                Vcv_l = ((ControlR134ALiquido)liquido).getVcl();
                break;
                
            case 15:
                liquido = new ControlPropaneLiquido(session);
                ((ControlPropaneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlPropaneLiquido)liquido).getCpl();
                Prv_l = ((ControlPropaneLiquido)liquido).getPrl();
                kv_l = ((ControlPropaneLiquido)liquido).getKl();
                Muv_l = ((ControlPropaneLiquido)liquido).getMul();
                Vcv_l = ((ControlPropaneLiquido)liquido).getVcl();
                break;
                
            case 17:
                liquido = new ControlR_1270Liquido(session);
                ((ControlR_1270Liquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlR_1270Liquido)liquido).getCpl();
                Prv_l = ((ControlR_1270Liquido)liquido).getPrl();
                kv_l = ((ControlR_1270Liquido)liquido).getKl();
                Muv_l = ((ControlR_1270Liquido)liquido).getMul();
                Vcv_l = ((ControlR_1270Liquido)liquido).getVcl();
                break;
   
                    
            case 18:
                liquido = new ControlR_40Liquido(session);
                ((ControlR_40Liquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlR_40Liquido)liquido).getCpl();
                Prv_l = ((ControlR_40Liquido)liquido).getPrl();
                kv_l = ((ControlR_40Liquido)liquido).getKl();
                Muv_l = ((ControlR_40Liquido)liquido).getMul();
                Vcv_l = ((ControlR_40Liquido)liquido).getVcl();
                break;
      
                
            case 16:
            case 29:
                liquido = new ControlR142BLiquido(session);
                ((ControlR142BLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlR142BLiquido)liquido).getCpl();
                Prv_l = ((ControlR142BLiquido)liquido).getPrl();
                kv_l = ((ControlR142BLiquido)liquido).getKl();
                Muv_l = ((ControlR142BLiquido)liquido).getMul();
                Vcv_l = ((ControlR142BLiquido)liquido).getVcl();
                break;
                
            case 19:
                liquido = new ControlMMLiquido(session);
                ((ControlMMLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlMMLiquido)liquido).getCpl();
                Prv_l = ((ControlMMLiquido)liquido).getPrl();
                kv_l = ((ControlMMLiquido)liquido).getKl();
                Muv_l = ((ControlMMLiquido)liquido).getMul();
                Vcv_l = ((ControlMMLiquido)liquido).getVcl();
                break;
                
            case 20:
                liquido = new ControlMD4MLiquido(session);
                ((ControlMD4MLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlMD4MLiquido)liquido).getCpl();
                Prv_l = ((ControlMD4MLiquido)liquido).getPrl();
                kv_l = ((ControlMD4MLiquido)liquido).getKl();
                Muv_l = ((ControlMD4MLiquido)liquido).getMul();
                Vcv_l = ((ControlMD4MLiquido)liquido).getVcl();
                break;
                
            case 21:
                liquido = new ControlD4Liquido(session);
                ((ControlD4Liquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlD4Liquido)liquido).getCpl();
                Prv_l = ((ControlD4Liquido)liquido).getPrl();
                kv_l = ((ControlD4Liquido)liquido).getKl();
                Muv_l = ((ControlD4Liquido)liquido).getMul();
                Vcv_l = ((ControlD4Liquido)liquido).getVcl();
                break;
                
            case 22:
                liquido = new ControlD5Liquido(session);
                ((ControlD5Liquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlD5Liquido)liquido).getCpl();
                Prv_l = ((ControlD5Liquido)liquido).getPrl();
                kv_l = ((ControlD5Liquido)liquido).getKl();
                Muv_l = ((ControlD5Liquido)liquido).getMul();
                Vcv_l = ((ControlD5Liquido)liquido).getVcl();
                break;
                
            case 23:
                liquido = new ControlR11Liquido(session);
                ((ControlR11Liquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlR11Liquido)liquido).getCpl();
                Prv_l = ((ControlR11Liquido)liquido).getPrl();
                kv_l = ((ControlR11Liquido)liquido).getKl();
                Muv_l = ((ControlR11Liquido)liquido).getMul();
                Vcv_l = ((ControlR11Liquido)liquido).getVcl();
                break;
                
            case 24:
                liquido = new ControlR12Liquido(session);
                ((ControlR12Liquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlR12Liquido)liquido).getCpl();
                Prv_l = ((ControlR12Liquido)liquido).getPrl();
                kv_l = ((ControlR12Liquido)liquido).getKl();
                Muv_l = ((ControlR12Liquido)liquido).getMul();
                Vcv_l = ((ControlR12Liquido)liquido).getVcl();
                break;
                
            case 25:
                liquido = new ControlR216_CALiquido(session);
                ((ControlR216_CALiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlR216_CALiquido)liquido).getCpl();
                Prv_l = ((ControlR216_CALiquido)liquido).getPrl();
                kv_l = ((ControlR216_CALiquido)liquido).getKl();
                Muv_l = ((ControlR216_CALiquido)liquido).getMul();
                Vcv_l = ((ControlR216_CALiquido)liquido).getVcl();
                break;
                
            case 26:
                liquido = new ControlR114Liquido(session);
                ((ControlR114Liquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlR114Liquido)liquido).getCpl();
                Prv_l = ((ControlR114Liquido)liquido).getPrl();
                kv_l = ((ControlR114Liquido)liquido).getKl();
                Muv_l = ((ControlR114Liquido)liquido).getMul();
                Vcv_l = ((ControlR114Liquido)liquido).getVcl();
                break;
                
            case 27:
                liquido = new ControlR141BLiquido(session);
                ((ControlR141BLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlR141BLiquido)liquido).getCpl();
                Prv_l = ((ControlR141BLiquido)liquido).getPrl();
                kv_l = ((ControlR141BLiquido)liquido).getKl();
                Muv_l = ((ControlR141BLiquido)liquido).getMul();
                Vcv_l = ((ControlR141BLiquido)liquido).getVcl();
                break;
                
            case 28:
                liquido = new ControlR152ALiquido(session);
                ((ControlR152ALiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlR152ALiquido)liquido).getCpl();
                Prv_l = ((ControlR152ALiquido)liquido).getPrl();
                kv_l = ((ControlR152ALiquido)liquido).getKl();
                Muv_l = ((ControlR152ALiquido)liquido).getMul();
                Vcv_l = ((ControlR152ALiquido)liquido).getVcl();
                break;
                
            case 30:
                liquido = new ControlHexaneLiquido(session);
                ((ControlHexaneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlHexaneLiquido)liquido).getCpl();
                Prv_l = ((ControlHexaneLiquido)liquido).getPrl();
                kv_l = ((ControlHexaneLiquido)liquido).getKl();
                Muv_l = ((ControlHexaneLiquido)liquido).getMul();
                Vcv_l = ((ControlHexaneLiquido)liquido).getVcl();
                break;
                
            case 31:
                liquido = new ControlHeptaneLiquido(session);
                ((ControlHeptaneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlHeptaneLiquido)liquido).getCpl();
                Prv_l = ((ControlHeptaneLiquido)liquido).getPrl();
                kv_l = ((ControlHeptaneLiquido)liquido).getKl();
                Muv_l = ((ControlHeptaneLiquido)liquido).getMul();
                Vcv_l = ((ControlHeptaneLiquido)liquido).getVcl();
                break;
                
            case 32:
                liquido = new ControlOctaneLiquido(session);
                ((ControlOctaneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlOctaneLiquido)liquido).getCpl();
                Prv_l = ((ControlOctaneLiquido)liquido).getPrl();
                kv_l = ((ControlOctaneLiquido)liquido).getKl();
                Muv_l = ((ControlOctaneLiquido)liquido).getMul();
                Vcv_l = ((ControlOctaneLiquido)liquido).getVcl();
                break;
                
            case 33:
                liquido = new ControlR22Liquido(session);
                ((ControlR22Liquido)liquido).interpolacao(pressao, temp);
                Cpv_l = ((ControlR22Liquido)liquido).getCpl();
                Prv_l = ((ControlR22Liquido)liquido).getPrl();
                kv_l = ((ControlR22Liquido)liquido).getKl();
                Muv_l = ((ControlR22Liquido)liquido).getMul();
                Vcv_l = ((ControlR22Liquido)liquido).getVcl();
                break;
            
            default: 
                JOptionPane.showMessageDialog(null, "Fluido inexistente");
        }
    }
}
