/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.Control.Ciclo2;

import Ciclo1.Control.TabelaFluidos.ControlButanoGas;
import Ciclo1.Control.TabelaFluidos.ControlButanoLiquido;
import Ciclo1.Control.TabelaFluidos.ControlD4Gas;
import Ciclo1.Control.TabelaFluidos.ControlD4Liquido;
import Ciclo1.Control.TabelaFluidos.ControlD5Gas;
import Ciclo1.Control.TabelaFluidos.ControlD5Liquido;
import Ciclo1.Control.TabelaFluidos.ControlD6Gas;
import Ciclo1.Control.TabelaFluidos.ControlD6Liquido;
import Ciclo1.Control.TabelaFluidos.ControlHeptaneGas;
import Ciclo1.Control.TabelaFluidos.ControlHeptaneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlHexaneGas;
import Ciclo1.Control.TabelaFluidos.ControlHexaneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlIpentaneGas;
import Ciclo1.Control.TabelaFluidos.ControlIpentaneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlIsobutanGas;
import Ciclo1.Control.TabelaFluidos.ControlIsobutanLiquido;
import Ciclo1.Control.TabelaFluidos.ControlMD2MGas;
import Ciclo1.Control.TabelaFluidos.ControlMD2MLiquido;
import Ciclo1.Control.TabelaFluidos.ControlMD3MGas;
import Ciclo1.Control.TabelaFluidos.ControlMD3MLiquido;
import Ciclo1.Control.TabelaFluidos.ControlMD4MGas;
import Ciclo1.Control.TabelaFluidos.ControlMD4MLiquido;
import Ciclo1.Control.TabelaFluidos.ControlMDMGas;
import Ciclo1.Control.TabelaFluidos.ControlMDMLiquido;
import Ciclo1.Control.TabelaFluidos.ControlMMGas;
import Ciclo1.Control.TabelaFluidos.ControlMMLiquido;
import Ciclo1.Control.TabelaFluidos.ControlOctaneGas;
import Ciclo1.Control.TabelaFluidos.ControlOctaneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlPentaneGas;
import Ciclo1.Control.TabelaFluidos.ControlPentaneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlPropaneGas;
import Ciclo1.Control.TabelaFluidos.ControlPropaneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlR114Gas;
import Ciclo1.Control.TabelaFluidos.ControlR114Liquido;
import Ciclo1.Control.TabelaFluidos.ControlR11Gas;
import Ciclo1.Control.TabelaFluidos.ControlR11Liquido;
import Ciclo1.Control.TabelaFluidos.ControlR12Gas;
import Ciclo1.Control.TabelaFluidos.ControlR12Liquido;
import Ciclo1.Control.TabelaFluidos.ControlR134AGas;
import Ciclo1.Control.TabelaFluidos.ControlR134ALiquido;
import Ciclo1.Control.TabelaFluidos.ControlR141BGas;
import Ciclo1.Control.TabelaFluidos.ControlR141BLiquido;
import Ciclo1.Control.TabelaFluidos.ControlR142BGas;
import Ciclo1.Control.TabelaFluidos.ControlR142BLiquido;
import Ciclo1.Control.TabelaFluidos.ControlR152AGas;
import Ciclo1.Control.TabelaFluidos.ControlR152ALiquido;
import Ciclo1.Control.TabelaFluidos.ControlR22Gas;
import Ciclo1.Control.TabelaFluidos.ControlR22Liquido;
import Ciclo1.Control.TabelaFluidos.ControlTolueneGas;
import Ciclo1.Control.TabelaFluidos.ControlTolueneLiquido;
import Ciclo1.Control.TabelaFluidos.ControlWaterGas;
import Ciclo1.Control.TabelaFluidos.ControlWaterLiquido;
import javax.swing.JOptionPane;
import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class ControlInterpolacao {
    
    private Object gas, liquido;
    private double Cpv_g, Prv_g, kv_g, Muv_g, Vcv_g, Cpv_l, Prv_l, kv_l, Muv_l, Vcv_l;
    
    public ControlInterpolacao(int fluido, double pressao, double temp, Session session){
        switch(fluido){
            case 1:
                gas = new ControlWaterGas(session);
                liquido = new ControlWaterLiquido(session);
                ((ControlWaterGas)gas).interpolacao(pressao, temp);
                ((ControlWaterLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlWaterGas)gas).getCpv();
                Prv_g = ((ControlWaterGas)gas).getPrv();
                kv_g = ((ControlWaterGas)gas).getKv();
                Muv_g = ((ControlWaterGas)gas).getMuv();
                Vcv_g = ((ControlWaterGas)gas).getVcv();
                
                Cpv_l = ((ControlWaterLiquido)liquido).getCpl();
                Prv_l = ((ControlWaterLiquido)liquido).getPrl();
                kv_l = ((ControlWaterLiquido)liquido).getKl();
                Muv_l = ((ControlWaterLiquido)liquido).getMul();
                Vcv_l = ((ControlWaterLiquido)liquido).getVcl();
                break;
                
            case 2:
                gas = new ControlTolueneGas(session);
                liquido = new ControlTolueneLiquido(session);
                ((ControlTolueneGas)gas).interpolacao(pressao, temp);
                ((ControlTolueneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlTolueneGas)gas).getCpv();
                Prv_g = ((ControlTolueneGas)gas).getPrv();
                kv_g = ((ControlTolueneGas)gas).getKv();
                Muv_g = ((ControlTolueneGas)gas).getMuv();
                Vcv_g = ((ControlTolueneGas)gas).getVcv();
                
                Cpv_l = ((ControlTolueneLiquido)liquido).getCpl();
                Prv_l = ((ControlTolueneLiquido)liquido).getPrl();
                kv_l = ((ControlTolueneLiquido)liquido).getKl();
                Muv_l = ((ControlTolueneLiquido)liquido).getMul();
                Vcv_l = ((ControlTolueneLiquido)liquido).getVcl();
                break;
                
            case 6:
                gas = new ControlMDMGas(session);
                liquido = new ControlMDMLiquido(session);
                ((ControlMDMGas)gas).interpolacao(pressao, temp);
                ((ControlMDMLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlMDMGas)gas).getCpv();
                Prv_g = ((ControlMDMGas)gas).getPrv();
                kv_g = ((ControlMDMGas)gas).getKv();
                Muv_g = ((ControlMDMGas)gas).getMuv();
                Vcv_g = ((ControlMDMGas)gas).getVcv();
                
                Cpv_l = ((ControlMDMLiquido)liquido).getCpl();
                Prv_l = ((ControlMDMLiquido)liquido).getPrl();
                kv_l = ((ControlMDMLiquido)liquido).getKl();
                Muv_l = ((ControlMDMLiquido)liquido).getMul();
                Vcv_l = ((ControlMDMLiquido)liquido).getVcl();
                break;
              
            case 7:
                gas = new ControlMD2MGas(session);
                liquido = new ControlMD2MLiquido(session);
                ((ControlMD2MGas)gas).interpolacao(pressao, temp);
                ((ControlMD2MLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlMD2MGas)gas).getCpv();
                Prv_g = ((ControlMD2MGas)gas).getPrv();
                kv_g = ((ControlMD2MGas)gas).getKv();
                Muv_g = ((ControlMD2MGas)gas).getMuv();
                Vcv_g = ((ControlMD2MGas)gas).getVcv();
                
                Cpv_l = ((ControlMD2MLiquido)liquido).getCpl();
                Prv_l = ((ControlMD2MLiquido)liquido).getPrl();
                kv_l = ((ControlMD2MLiquido)liquido).getKl();
                Muv_l = ((ControlMD2MLiquido)liquido).getMul();
                Vcv_l = ((ControlMD2MLiquido)liquido).getVcl();
                break;
                
            case 8:
                gas = new ControlMD3MGas(session);
                liquido = new ControlMD3MLiquido(session);
                ((ControlMD3MGas)gas).interpolacao(pressao, temp);
                ((ControlMD3MLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlMD3MGas)gas).getCpv();
                Prv_g = ((ControlMD3MGas)gas).getPrv();
                kv_g = ((ControlMD3MGas)gas).getKv();
                Muv_g = ((ControlMD3MGas)gas).getMuv();
                Vcv_g = ((ControlMD3MGas)gas).getVcv();
                
                Cpv_l = ((ControlMD3MLiquido)liquido).getCpl();
                Prv_l = ((ControlMD3MLiquido)liquido).getPrl();
                kv_l = ((ControlMD3MLiquido)liquido).getKl();
                Muv_l = ((ControlMD3MLiquido)liquido).getMul();
                Vcv_l = ((ControlMD3MLiquido)liquido).getVcl();
                break;
                
            case 9:
                gas = new ControlD6Gas(session);
                liquido = new ControlD6Liquido(session);
                ((ControlD6Gas)gas).interpolacao(pressao, temp);
                ((ControlD6Liquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlD6Gas)gas).getCpv();
                Prv_g = ((ControlD6Gas)gas).getPrv();
                kv_g = ((ControlD6Gas)gas).getKv();
                Muv_g = ((ControlD6Gas)gas).getMuv();
                Vcv_g = ((ControlD6Gas)gas).getVcv();
                
                Cpv_l = ((ControlD6Liquido)liquido).getCpl();
                Prv_l = ((ControlD6Liquido)liquido).getPrl();
                kv_l = ((ControlD6Liquido)liquido).getKl();
                Muv_l = ((ControlD6Liquido)liquido).getMul();
                Vcv_l = ((ControlD6Liquido)liquido).getVcl();
                break;
                
            case 10:
                gas = new ControlIsobutanGas(session);
                liquido = new ControlIsobutanLiquido(session);
                ((ControlIsobutanGas)gas).interpolacao(pressao, temp);
                ((ControlIsobutanLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlIsobutanGas)gas).getCpv();
                Prv_g = ((ControlIsobutanGas)gas).getPrv();
                kv_g = ((ControlIsobutanGas)gas).getKv();
                Muv_g = ((ControlIsobutanGas)gas).getMuv();
                Vcv_g = ((ControlIsobutanGas)gas).getVcv();
                
                Cpv_l = ((ControlIsobutanLiquido)liquido).getCpl();
                Prv_l = ((ControlIsobutanLiquido)liquido).getPrl();
                kv_l = ((ControlIsobutanLiquido)liquido).getKl();
                Muv_l = ((ControlIsobutanLiquido)liquido).getMul();
                Vcv_l = ((ControlIsobutanLiquido)liquido).getVcl();
                break;
                
            case 11:
                gas = new ControlIpentaneGas(session);
                liquido = new ControlIpentaneLiquido(session);
                ((ControlIpentaneGas)gas).interpolacao(pressao, temp);
                ((ControlIpentaneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlIpentaneGas)gas).getCpv();
                Prv_g = ((ControlIpentaneGas)gas).getPrv();
                kv_g = ((ControlIpentaneGas)gas).getKv();
                Muv_g = ((ControlIpentaneGas)gas).getMuv();
                Vcv_g = ((ControlIpentaneGas)gas).getVcv();
                
                Cpv_l = ((ControlIpentaneLiquido)liquido).getCpl();
                Prv_l = ((ControlIpentaneLiquido)liquido).getPrl();
                kv_l = ((ControlIpentaneLiquido)liquido).getKl();
                Muv_l = ((ControlIpentaneLiquido)liquido).getMul();
                Vcv_l = ((ControlIpentaneLiquido)liquido).getVcl();
                break;    
                
            case 12:
                gas = new ControlButanoGas(session);
                liquido = new ControlButanoLiquido(session);
                ((ControlButanoGas)gas).interpolacao(pressao, temp);
                ((ControlButanoLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlButanoGas)gas).getCpv();
                Prv_g = ((ControlButanoGas)gas).getPrv();
                kv_g = ((ControlButanoGas)gas).getKv();
                Muv_g = ((ControlButanoGas)gas).getMuv();
                Vcv_g = ((ControlButanoGas)gas).getVcv();
                
                Cpv_l = ((ControlButanoLiquido)liquido).getCpl();
                Prv_l = ((ControlButanoLiquido)liquido).getPrl();
                kv_l = ((ControlButanoLiquido)liquido).getKl();
                Muv_l = ((ControlButanoLiquido)liquido).getMul();
                Vcv_l = ((ControlButanoLiquido)liquido).getVcl();
                break;
                
            case 13:
                gas = new ControlPentaneGas(session);
                liquido = new ControlPentaneLiquido(session);
                ((ControlPentaneGas)gas).interpolacao(pressao, temp);
                ((ControlPentaneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlPentaneGas)gas).getCpv();
                Prv_g = ((ControlPentaneGas)gas).getPrv();
                kv_g = ((ControlPentaneGas)gas).getKv();
                Muv_g = ((ControlPentaneGas)gas).getMuv();
                Vcv_g = ((ControlPentaneGas)gas).getVcv();
                
                Cpv_l = ((ControlPentaneLiquido)liquido).getCpl();
                Prv_l = ((ControlPentaneLiquido)liquido).getPrl();
                kv_l = ((ControlPentaneLiquido)liquido).getKl();
                Muv_l = ((ControlPentaneLiquido)liquido).getMul();
                Vcv_l = ((ControlPentaneLiquido)liquido).getVcl();
                break;
                
            case 14:
                gas = new ControlR134AGas(session);
                liquido = new ControlR134ALiquido(session);
                ((ControlR134AGas)gas).interpolacao(pressao, temp);
                ((ControlR134ALiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlR134AGas)gas).getCpv();
                Prv_g = ((ControlR134AGas)gas).getPrv();
                kv_g = ((ControlR134AGas)gas).getKv();
                Muv_g = ((ControlR134AGas)gas).getMuv();
                Vcv_g = ((ControlR134AGas)gas).getVcv();
                
                Cpv_l = ((ControlR134ALiquido)liquido).getCpl();
                Prv_l = ((ControlR134ALiquido)liquido).getPrl();
                kv_l = ((ControlR134ALiquido)liquido).getKl();
                Muv_l = ((ControlR134ALiquido)liquido).getMul();
                Vcv_l = ((ControlR134ALiquido)liquido).getVcl();
                break;
                
            case 15:
                gas = new ControlPropaneGas(session);
                liquido = new ControlPropaneLiquido(session);
                ((ControlPropaneGas)gas).interpolacao(pressao, temp);
                ((ControlPropaneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlPropaneGas)gas).getCpv();
                Prv_g = ((ControlPropaneGas)gas).getPrv();
                kv_g = ((ControlPropaneGas)gas).getKv();
                Muv_g = ((ControlPropaneGas)gas).getMuv();
                Vcv_g = ((ControlPropaneGas)gas).getVcv();
                
                Cpv_l = ((ControlPropaneLiquido)liquido).getCpl();
                Prv_l = ((ControlPropaneLiquido)liquido).getPrl();
                kv_l = ((ControlPropaneLiquido)liquido).getKl();
                Muv_l = ((ControlPropaneLiquido)liquido).getMul();
                Vcv_l = ((ControlPropaneLiquido)liquido).getVcl();
                break;
                
            case 16:
            case 29:
                gas = new ControlR142BGas(session);
                liquido = new ControlR142BLiquido(session);
                ((ControlR142BGas)gas).interpolacao(pressao, temp);
                ((ControlR142BLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlR142BGas)gas).getCpv();
                Prv_g = ((ControlR142BGas)gas).getPrv();
                kv_g = ((ControlR142BGas)gas).getKv();
                Muv_g = ((ControlR142BGas)gas).getMuv();
                Vcv_g = ((ControlR142BGas)gas).getVcv();
                
                Cpv_l = ((ControlR142BLiquido)liquido).getCpl();
                Prv_l = ((ControlR142BLiquido)liquido).getPrl();
                kv_l = ((ControlR142BLiquido)liquido).getKl();
                Muv_l = ((ControlR142BLiquido)liquido).getMul();
                Vcv_l = ((ControlR142BLiquido)liquido).getVcl();
                break;
                
            case 19:
                gas = new ControlMMGas(session);
                liquido = new ControlMMLiquido(session);
                ((ControlMMGas)gas).interpolacao(pressao, temp);
                ((ControlMMLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlMMGas)gas).getCpv();
                Prv_g = ((ControlMMGas)gas).getPrv();
                kv_g = ((ControlMMGas)gas).getKv();
                Muv_g = ((ControlMMGas)gas).getMuv();
                Vcv_g = ((ControlMMGas)gas).getVcv();
                
                Cpv_l = ((ControlMMLiquido)liquido).getCpl();
                Prv_l = ((ControlMMLiquido)liquido).getPrl();
                kv_l = ((ControlMMLiquido)liquido).getKl();
                Muv_l = ((ControlMMLiquido)liquido).getMul();
                Vcv_l = ((ControlMMLiquido)liquido).getVcl();
                break;
                
            case 20:
                gas = new ControlMD4MGas(session);
                liquido = new ControlMD4MLiquido(session);
                ((ControlMD4MGas)gas).interpolacao(pressao, temp);
                ((ControlMD4MLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlMD4MGas)gas).getCpv();
                Prv_g = ((ControlMD4MGas)gas).getPrv();
                kv_g = ((ControlMD4MGas)gas).getKv();
                Muv_g = ((ControlMD4MGas)gas).getMuv();
                Vcv_g = ((ControlMD4MGas)gas).getVcv();
                
                Cpv_l = ((ControlMD4MLiquido)liquido).getCpl();
                Prv_l = ((ControlMD4MLiquido)liquido).getPrl();
                kv_l = ((ControlMD4MLiquido)liquido).getKl();
                Muv_l = ((ControlMD4MLiquido)liquido).getMul();
                Vcv_l = ((ControlMD4MLiquido)liquido).getVcl();
                break;
                
            case 21:
                gas = new ControlD4Gas(session);
                liquido = new ControlD4Liquido(session);
                ((ControlD4Gas)gas).interpolacao(pressao, temp);
                ((ControlD4Liquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlD4Gas)gas).getCpv();
                Prv_g = ((ControlD4Gas)gas).getPrv();
                kv_g = ((ControlD4Gas)gas).getKv();
                Muv_g = ((ControlD4Gas)gas).getMuv();
                Vcv_g = ((ControlD4Gas)gas).getVcv();
                
                Cpv_l = ((ControlD4Liquido)liquido).getCpl();
                Prv_l = ((ControlD4Liquido)liquido).getPrl();
                kv_l = ((ControlD4Liquido)liquido).getKl();
                Muv_l = ((ControlD4Liquido)liquido).getMul();
                Vcv_l = ((ControlD4Liquido)liquido).getVcl();
                break;
                
            case 22:
                gas = new ControlD5Gas(session);
                liquido = new ControlD5Liquido(session);
                ((ControlD5Gas)gas).interpolacao(pressao, temp);
                ((ControlD5Liquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlD5Gas)gas).getCpv();
                Prv_g = ((ControlD5Gas)gas).getPrv();
                kv_g = ((ControlD5Gas)gas).getKv();
                Muv_g = ((ControlD5Gas)gas).getMuv();
                Vcv_g = ((ControlD5Gas)gas).getVcv();
                
                Cpv_l = ((ControlD5Liquido)liquido).getCpl();
                Prv_l = ((ControlD5Liquido)liquido).getPrl();
                kv_l = ((ControlD5Liquido)liquido).getKl();
                Muv_l = ((ControlD5Liquido)liquido).getMul();
                Vcv_l = ((ControlD5Liquido)liquido).getVcl();
                break;
                
            case 23:
                gas = new ControlR11Gas(session);
                liquido = new ControlR11Liquido(session);
                ((ControlR11Gas)gas).interpolacao(pressao, temp);
                ((ControlR11Liquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlR11Gas)gas).getCpv();
                Prv_g = ((ControlR11Gas)gas).getPrv();
                kv_g = ((ControlR11Gas)gas).getKv();
                Muv_g = ((ControlR11Gas)gas).getMuv();
                Vcv_g = ((ControlR11Gas)gas).getVcv();
                
                Cpv_l = ((ControlR11Liquido)liquido).getCpl();
                Prv_l = ((ControlR11Liquido)liquido).getPrl();
                kv_l = ((ControlR11Liquido)liquido).getKl();
                Muv_l = ((ControlR11Liquido)liquido).getMul();
                Vcv_l = ((ControlR11Liquido)liquido).getVcl();
                break;
                
            case 24:
                gas = new ControlR12Gas(session);
                liquido = new ControlR12Liquido(session);
                ((ControlR12Gas)gas).interpolacao(pressao, temp);
                ((ControlR12Liquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlR12Gas)gas).getCpv();
                Prv_g = ((ControlR12Gas)gas).getPrv();
                kv_g = ((ControlR12Gas)gas).getKv();
                Muv_g = ((ControlR12Gas)gas).getMuv();
                Vcv_g = ((ControlR12Gas)gas).getVcv();
                
                Cpv_l = ((ControlR12Liquido)liquido).getCpl();
                Prv_l = ((ControlR12Liquido)liquido).getPrl();
                kv_l = ((ControlR12Liquido)liquido).getKl();
                Muv_l = ((ControlR12Liquido)liquido).getMul();
                Vcv_l = ((ControlR12Liquido)liquido).getVcl();
                break;
                
            case 26:
                gas = new ControlR114Gas(session);
                liquido = new ControlR114Liquido(session);
                ((ControlR114Gas)gas).interpolacao(pressao, temp);
                ((ControlR114Liquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlR114Gas)gas).getCpv();
                Prv_g = ((ControlR114Gas)gas).getPrv();
                kv_g = ((ControlR114Gas)gas).getKv();
                Muv_g = ((ControlR114Gas)gas).getMuv();
                Vcv_g = ((ControlR114Gas)gas).getVcv();
                
                Cpv_l = ((ControlR114Liquido)liquido).getCpl();
                Prv_l = ((ControlR114Liquido)liquido).getPrl();
                kv_l = ((ControlR114Liquido)liquido).getKl();
                Muv_l = ((ControlR114Liquido)liquido).getMul();
                Vcv_l = ((ControlR114Liquido)liquido).getVcl();
                break;
                
            case 27:
                gas = new ControlR141BGas(session);
                liquido = new ControlR141BLiquido(session);
                ((ControlR141BGas)gas).interpolacao(pressao, temp);
                ((ControlR141BLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlR141BGas)gas).getCpv();
                Prv_g = ((ControlR141BGas)gas).getPrv();
                kv_g = ((ControlR141BGas)gas).getKv();
                Muv_g = ((ControlR141BGas)gas).getMuv();
                Vcv_g = ((ControlR141BGas)gas).getVcv();
                
                Cpv_l = ((ControlR141BLiquido)liquido).getCpl();
                Prv_l = ((ControlR141BLiquido)liquido).getPrl();
                kv_l = ((ControlR141BLiquido)liquido).getKl();
                Muv_l = ((ControlR141BLiquido)liquido).getMul();
                Vcv_l = ((ControlR141BLiquido)liquido).getVcl();
                break;
                
            case 28:
                gas = new ControlR152AGas(session);
                liquido = new ControlR152ALiquido(session);
                ((ControlR152AGas)gas).interpolacao(pressao, temp);
                ((ControlR152ALiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlR152AGas)gas).getCpv();
                Prv_g = ((ControlR152AGas)gas).getPrv();
                kv_g = ((ControlR152AGas)gas).getKv();
                Muv_g = ((ControlR152AGas)gas).getMuv();
                Vcv_g = ((ControlR152AGas)gas).getVcv();
                
                Cpv_l = ((ControlR152ALiquido)liquido).getCpl();
                Prv_l = ((ControlR152ALiquido)liquido).getPrl();
                kv_l = ((ControlR152ALiquido)liquido).getKl();
                Muv_l = ((ControlR152ALiquido)liquido).getMul();
                Vcv_l = ((ControlR152ALiquido)liquido).getVcl();
                break;
                
            case 30:
                gas = new ControlHexaneGas(session);
                liquido = new ControlHexaneLiquido(session);
                ((ControlHexaneGas)gas).interpolacao(pressao, temp);
                ((ControlHexaneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlHexaneGas)gas).getCpv();
                Prv_g = ((ControlHexaneGas)gas).getPrv();
                kv_g = ((ControlHexaneGas)gas).getKv();
                Muv_g = ((ControlHexaneGas)gas).getMuv();
                Vcv_g = ((ControlHexaneGas)gas).getVcv();
                
                Cpv_l = ((ControlHexaneLiquido)liquido).getCpl();
                Prv_l = ((ControlHexaneLiquido)liquido).getPrl();
                kv_l = ((ControlHexaneLiquido)liquido).getKl();
                Muv_l = ((ControlHexaneLiquido)liquido).getMul();
                Vcv_l = ((ControlHexaneLiquido)liquido).getVcl();
                break;
                
            case 31:
                gas = new ControlHeptaneGas(session);
                liquido = new ControlHeptaneLiquido(session);
                ((ControlHeptaneGas)gas).interpolacao(pressao, temp);
                ((ControlHeptaneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlHeptaneGas)gas).getCpv();
                Prv_g = ((ControlHeptaneGas)gas).getPrv();
                kv_g = ((ControlHeptaneGas)gas).getKv();
                Muv_g = ((ControlHeptaneGas)gas).getMuv();
                Vcv_g = ((ControlHeptaneGas)gas).getVcv();
                
                Cpv_l = ((ControlHeptaneLiquido)liquido).getCpl();
                Prv_l = ((ControlHeptaneLiquido)liquido).getPrl();
                kv_l = ((ControlHeptaneLiquido)liquido).getKl();
                Muv_l = ((ControlHeptaneLiquido)liquido).getMul();
                Vcv_l = ((ControlHeptaneLiquido)liquido).getVcl();
                break;
                
            case 32:
                gas = new ControlOctaneGas(session);
                liquido = new ControlOctaneLiquido(session);
                ((ControlOctaneGas)gas).interpolacao(pressao, temp);
                ((ControlOctaneLiquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlOctaneGas)gas).getCpv();
                Prv_g = ((ControlOctaneGas)gas).getPrv();
                kv_g = ((ControlOctaneGas)gas).getKv();
                Muv_g = ((ControlOctaneGas)gas).getMuv();
                Vcv_g = ((ControlOctaneGas)gas).getVcv();
                
                Cpv_l = ((ControlOctaneLiquido)liquido).getCpl();
                Prv_l = ((ControlOctaneLiquido)liquido).getPrl();
                kv_l = ((ControlOctaneLiquido)liquido).getKl();
                Muv_l = ((ControlOctaneLiquido)liquido).getMul();
                Vcv_l = ((ControlOctaneLiquido)liquido).getVcl();
                break;
                
            case 33:
                gas = new ControlR22Gas(session);
                liquido = new ControlR22Liquido(session);
                ((ControlR22Gas)gas).interpolacao(pressao, temp);
                ((ControlR22Liquido)liquido).interpolacao(pressao, temp);
                Cpv_g = ((ControlR22Gas)gas).getCpv();
                Prv_g = ((ControlR22Gas)gas).getPrv();
                kv_g = ((ControlR22Gas)gas).getKv();
                Muv_g = ((ControlR22Gas)gas).getMuv();
                Vcv_g = ((ControlR22Gas)gas).getVcv();
                
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

    public double getCpv_g() {
        return Cpv_g;
    }

    public void setCpv_g(double Cpv_g) {
        this.Cpv_g = Cpv_g;
    }

    public double getPrv_g() {
        return Prv_g;
    }

    public void setPrv_g(double Prv_g) {
        this.Prv_g = Prv_g;
    }

    public double getKv_g() {
        return kv_g;
    }

    public void setKv_g(double kv_g) {
        this.kv_g = kv_g;
    }

    public double getMuv_g() {
        return Muv_g;
    }

    public void setMuv_g(double Muv_g) {
        this.Muv_g = Muv_g;
    }

    public double getVcv_g() {
        return Vcv_g;
    }

    public void setVcv_g(double Vcv_g) {
        this.Vcv_g = Vcv_g;
    }

    public double getCpv_l() {
        return Cpv_l;
    }

    public void setCpv_l(double Cpv_l) {
        this.Cpv_l = Cpv_l;
    }

    public double getPrv_l() {
        return Prv_l;
    }

    public void setPrv_l(double Prv_l) {
        this.Prv_l = Prv_l;
    }

    public double getKv_l() {
        return kv_l;
    }

    public void setKv_l(double kv_l) {
        this.kv_l = kv_l;
    }

    public double getMuv_l() {
        return Muv_l;
    }

    public void setMuv_l(double Muv_l) {
        this.Muv_l = Muv_l;
    }

    public double getVcv_l() {
        return Vcv_l;
    }

    public void setVcv_l(double Vcv_l) {
        this.Vcv_l = Vcv_l;
    }
}
