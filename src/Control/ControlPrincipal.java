/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Ciclo2.View.ViewLateral;
import Control.Ciclo2.ControlT_Ref;
import Control.TabelaFluidos.ControlButanoGas;
import Control.TabelaFluidos.ControlButanoLiquido;
import Control.TabelaFluidos.ControlD4Gas;
import Control.TabelaFluidos.ControlD4Liquido;
import Control.TabelaFluidos.ControlD5Gas;
import Control.TabelaFluidos.ControlD5Liquido;
import Control.TabelaFluidos.ControlD6Gas;
import Control.TabelaFluidos.ControlD6Liquido;
import Control.TabelaFluidos.ControlHeptaneGas;
import Control.TabelaFluidos.ControlHeptaneLiquido;
import Control.TabelaFluidos.ControlHexaneGas;
import Control.TabelaFluidos.ControlHexaneLiquido;
import Control.TabelaFluidos.ControlIpentaneGas;
import Control.TabelaFluidos.ControlIpentaneLiquido;
import Control.TabelaFluidos.ControlIsobutanGas;
import Control.TabelaFluidos.ControlIsobutanLiquido;
import Control.TabelaFluidos.ControlMD2MGas;
import Control.TabelaFluidos.ControlMD2MLiquido;
import Control.TabelaFluidos.ControlMD3MGas;
import Control.TabelaFluidos.ControlMD3MLiquido;
import Control.TabelaFluidos.ControlMD4MGas;
import Control.TabelaFluidos.ControlMD4MLiquido;
import Control.TabelaFluidos.ControlMDMGas;
import Control.TabelaFluidos.ControlMDMLiquido;
import Control.TabelaFluidos.ControlMMGas;
import Control.TabelaFluidos.ControlMMLiquido;
import Control.TabelaFluidos.ControlOctaneGas;
import Control.TabelaFluidos.ControlOctaneLiquido;
import Control.TabelaFluidos.ControlPentaneGas;
import Control.TabelaFluidos.ControlPentaneLiquido;
import Control.TabelaFluidos.ControlPropaneGas;
import Control.TabelaFluidos.ControlPropaneLiquido;
import Control.TabelaFluidos.ControlR114Gas;
import Control.TabelaFluidos.ControlR114Liquido;
import Control.TabelaFluidos.ControlR11Gas;
import Control.TabelaFluidos.ControlR11Liquido;
import Control.TabelaFluidos.ControlR12Gas;
import Control.TabelaFluidos.ControlR12Liquido;
import Control.TabelaFluidos.ControlR134AGas;
import Control.TabelaFluidos.ControlR134ALiquido;
import Control.TabelaFluidos.ControlR141BGas;
import Control.TabelaFluidos.ControlR141BLiquido;
import Control.TabelaFluidos.ControlR142BGas;
import Control.TabelaFluidos.ControlR142BLiquido;
import Control.TabelaFluidos.ControlR152AGas;
import Control.TabelaFluidos.ControlR152ALiquido;
import Control.TabelaFluidos.ControlR22Gas;
import Control.TabelaFluidos.ControlR22Liquido;
import Control.TabelaFluidos.ControlTolueneGas;
import Control.TabelaFluidos.ControlTolueneLiquido;
import Control.TabelaFluidos.ControlWaterGas;
import Control.TabelaFluidos.ControlWaterLiquido;
import Model.Ciclo2.ModelFluidos;
import Model.Ciclo2.ModelMassa;
import Model.ModelCVA;
import Model.ModelCVB;
import Model.ModelConsExeMatA;
import Model.ModelConsExeMatB;
import Model.ModelConstantesKCSMat_C;
import Model.ModelConstantesKCSMat_CC;
import Model.ModelConstantesRankineMat;
import Model.ModelCriticasKCSMat_K1;
import Model.ModelCriticasKCSMat_PM;
import Model.ModelCriticasKCSMat_Pc;
import Model.ModelCriticasKCSMat_Tc;
import Model.ModelCriticasKCSMat_w;
import Model.ModelDRT70;
import Model.ModelDRT80;
import Model.ModelEqro;
import Model.ModelEqrs;
import Model.ModelLinear;
import Model.ModelQfpsoKCSMat;
import Model.ModelQfpsoRankineMat;
import Model.ModelStart;
import Model.TabelasFluidos.ModelButanoGas;
import Model.TabelasFluidos.ModelHexaneGas;
import Util.HibernateUtil;
import View.Condensador.ViewCondensadorImage;
import View.Evaporador.ViewEvaporadorImage;
import View.Bomba.ViewBombaImage;
import View.Bomba.ViewBombaPanelRankine;
import View.Condensador.ViewCondensadorPanelRankine;
import View.Etapas.ViewEtapaImage;
import View.Evaporador.ViewEvaporadorPanelRankine;
import View.ViewPrincipal;
import View.Turbina.ViewTurbinaImage;
import View.Turbina.ViewTurbinaPanelRankine;
import View.ViewCiclos;
import View.Regenerador.ViewRegeneradorImage;
import View.Regenerador.ViewRegeneradorPanelRankine;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author alysonmp
 */
public class ControlPrincipal {
    private double PMax, TMax;
    private ViewPrincipal viewPrincipal;
    private ArrayList<JPanel> panel_usado = new ArrayList();
    private ViewLateral viewLateral;
    Start start;
    String mensagem;
    
    Session session;
    
    @SuppressWarnings("empty-statement")
    public ControlPrincipal(){
        mensagem = "";
        
        SessionFactory sf = HibernateUtil.getSessionFactory();
        this.session = sf.openSession();
        
        Criteria cr = this.session.createCriteria(ModelFluidos.class);
        List results = cr.list();
        if(results.isEmpty()){
            Transaction tx = session.beginTransaction();

            session.save(new ModelFluidos(1, "Agua"));
            session.save(new ModelFluidos(2, "Toluene (C7H8)"));
            session.save(new ModelFluidos(3, "Ethylbenzene (C8H10)"));
            session.save(new ModelFluidos(4, "Propylbenzene (C9H12)"));
            session.save(new ModelFluidos(5, "Dimetylbenzene(C8H10)"));
            session.save(new ModelFluidos(6, "Octamethyltrisiloxane (MDM)"));
            session.save(new ModelFluidos(7, "Decamethyltetrasiloxane (MD2M)"));
            session.save(new ModelFluidos(8, "Dodecamethylpentasiloxane (MD3M)"));
            session.save(new ModelFluidos(9, "Dodecamethylcyclohexasiloxane (D6)"));
            session.save(new ModelFluidos(10, "Isobutano"));
            session.save(new ModelFluidos(11, "Isopentano"));
            session.save(new ModelFluidos(12, "n-Butano"));
            session.save(new ModelFluidos(13, "n-Pentano"));
            session.save(new ModelFluidos(14, "R-134a (1,1,1,2-Tetrafluoroethane)"));
            session.save(new ModelFluidos(15, "R-290 (PROPANE)"));
            session.save(new ModelFluidos(16, "R-142b (1,1-Difluoro-1-chloroethane)"));
            session.save(new ModelFluidos(17, "R-1270 (1-Propene)"));
            session.save(new ModelFluidos(18, "R-40  (Clorometano)"));
            session.save(new ModelFluidos(19, "Hexamethyldisiloxane, (MM)"));
            session.save(new ModelFluidos(20, "Tetradecamethylhexasiloxane (MD4M)"));
            session.save(new ModelFluidos(21, "Octamethylcyclotetrasiloxane (D4)"));
            session.save(new ModelFluidos(22, "Decamethylcyclopentasiloxane (D5)"));
            session.save(new ModelFluidos(23, "Trichlorofluoromethane (R11)"));
            session.save(new ModelFluidos(24, "Dichlorodifluoromethane (R12)"));
            session.save(new ModelFluidos(25, "1,3-Dichloro-1,1,2,2,3,3-hexafluoropropane (R216ca)"));
            session.save(new ModelFluidos(26, "1,2-Dichlorotetrafluoroethane (R114)"));
            session.save(new ModelFluidos(27, "1,1-Dichloro-1-fluoroethane (R141b)"));
            session.save(new ModelFluidos(28, "1,1-Difluoroethane (R152a)"));
            session.save(new ModelFluidos(29, "1-Chloro-1,1-difluoroethane (R142b)"));
            session.save(new ModelFluidos(30, "n-Hexane"));
            session.save(new ModelFluidos(31, "n-Heptane"));
            session.save(new ModelFluidos(32, "n-octane"));
            session.save(new ModelFluidos(33, "Chlorodifluoromethane (R22)"));

            tx.commit();
        }
        
        cr = this.session.createCriteria(ModelConstantesRankineMat.class);
        results = cr.list();
        if(results.isEmpty()){
            Transaction tx = session.beginTransaction();
            
            double[][] valoresC1 = {{90.483, -4669.7, -11.607, 0.017194, 1.0}};
            session.save(new ModelConstantesRankineMat("C1", valoresC1));
            
            double[][] valoresC2 = {{73.649, -7258.2, -7.3037, 4.1653E-6, 2.0}};
            session.save(new ModelConstantesRankineMat("C2", valoresC2));

            double[][] valoresCC = {{33.60134, -0.002145, -3.999E-6, 4.752E-8, -3.533E-11, 0.0, 0.0}, 
                                    {33.573, -0.012581, 8.8906E-5, -7.1783E-8, 1.8569E-11, 0.0, 0.0}};
            session.save(new ModelConstantesRankineMat("CC", valoresCC));
            
            tx.commit();
        }
        
        
        cr = this.session.createCriteria(ModelLinear.class);
        results = cr.list();
        if(results.isEmpty()){
            Transaction tx = session.beginTransaction();

            session.save(new ModelLinear(new double[]{142.2, -0.01509422,-8.3348292E-8,-2.3243455E-13,6.5603658E-17,-3.4487625E-20,2.0808293E-23,-5.8664246E-27,9.1463375E-31,-7.446578E-35,2.4885769E-39}));
            session.save(new ModelLinear(new double[]{143.90002, -0.014493643, -4.6103987E-8, 1.3981357E-12, 2.3993308E-16, -9.8949381E-21, 1.6261678E-24, 0.0, 0.0, 0.0, 0.0}));
            session.save(new ModelLinear(new double[]{145.2, -5.6111011E-4, -0.094364859, 1.0896055E-7,2.3103997E-5, -7.4868329E-12, -2.5130974E-9, 1.9128073E-16, 1.2643961E-13, -7.5492826E-22, -2.4248126E-18}));
            session.save(new ModelLinear(new double[]{135.20007, -0.01007358, 1.2520524E-7, 2.5722111E-12, 4.3171784E-17, -3.9207826E-20, 1.2906424E-24, 0.0, 0.0, 0.0, 0.0}));
            session.save(new ModelLinear(new double[]{159.7099, -0.0061700647, 5.3500968E-8, -3.4218675E-11, 9.9938341E-15, -1.6897954E-18, 1.6983127E-22, -9.9200576E-27, 3.0981733E-31, -3.9847803E-36, 0.0}));
            session.save(new ModelLinear(new double[]{-4501439.8, 384.02683, 3.2531763E10, -0.020762985, -1.3089914E14, 6.9584129E-7, 2.0965934E17, -1.3226177E-11, 9.2327351E19, 1.0920871E-16, 0.0}));
            
            tx.commit();
        }
        
        cr = this.session.createCriteria(ModelQfpsoRankineMat.class);
        results = cr.list();
        if(results.isEmpty()){
            Transaction tx = session.beginTransaction();
            
            session.save(new ModelQfpsoRankineMat(new double[]{-5.0989335E-5, 66.250549, -0.024244439, 1.4359252E-5, -3.1287299E-9, 2.7938016E-10, 3.1938551E-11, -8.6058944E-13, 1.0138024E-14, -5.7854786E-17, 1.3108388E-19, 0.0}));
            session.save(new ModelQfpsoRankineMat(new double[]{-7.6520373E-5, 68.996718, -0.015276822, 4.4519632E-5, 3.2384275E-7, -9.5543181E-9, 3.1876871E-10, -5.3692223E-12, 5.1514164E-14, -2.6095697E-16, 5.4962813E-19, 0.0}));
            session.save(new ModelQfpsoRankineMat(new double[]{7.875352E-5, -0.04698885, 77.569376, 7.5518527E-4, -3.6218838, -4.6157586E-6, 0.05773998, 8.645757E-9, -3.5076036E-4, 3.2457331E-12, 6.9448062E-7, 0.0}));
            session.save(new ModelQfpsoRankineMat(new double[]{-0.0025970942, 99.279743, 0.12007259, 8.0406572E-4, -1.1669656E-5, 4.9266436E-7, -1.169747E-8, 1.6715254E-10, -1.5243562E-12, 7.6811009E-15, -1.5862785E-17, 0.0}));
            session.save(new ModelQfpsoRankineMat(new double[]{1.5353958, 162.37375, 0.11646613, -0.0086520143, 1.3170876E-4, 5.772981E-6, -3.037534E-7, 6.0681698E-9, -6.2711257E-11, 3.3117369E-13, -7.0587574E-16, 0.0}));
  
            tx.commit();
        }
        
        cr = this.session.createCriteria(ModelButanoGas.class);
        results = cr.list();
        if(results.isEmpty()){
            Transaction tx = session.beginTransaction();
            
            new ControlButanoGas(session).criaTabelaButanoGas();
            new ControlButanoLiquido(session).criaTabelaButanoLiquido();
            new ControlD4Gas(session).criaTabelaD4Gas();
            new ControlD4Liquido(session).criaTabelaD4Liquido();
            new ControlD5Gas(session).criaTabelaD5Gas();
            new ControlD5Liquido(session).criaTabelaD5Liquido();
            new ControlD6Gas(session).criaTabelaD6Gas();
            new ControlD6Liquido(session).criaTabelaD6Liquido();
            new ControlHeptaneGas(session).criaTabelaHeptaneGas();
            new ControlHeptaneLiquido(session).criaTabelaHeptaneLiquido();
            new ControlHexaneGas(session).criaTabelaHexaneGas();
            new ControlHexaneLiquido(session).criaTabelaHexaneLiquido();
            new ControlIpentaneGas(session).criaTabelaIpentaneGas();
            new ControlIpentaneLiquido(session).criaTabelaIpentaneLiquido();
            new ControlIsobutanGas(session).criaTabelaIsobutanGas();
            new ControlIsobutanLiquido(session).criaTabelaIsobutanLiquido();
            new ControlMD2MGas(session).criaTabelaMD2MGas();
            new ControlMD2MLiquido(session).criaTabelaMD2MLiquido();
            new ControlMD3MGas(session).criaTabelaMD3MGas();
            new ControlMD3MLiquido(session).criaTabelaMD3MLiquido();
            new ControlMD4MGas(session).criaTabelaMD4MGas();
            new ControlMD4MLiquido(session).criaTabelaMD4MLiquido();
            new ControlMDMGas(session).criaTabelaMDMGas();
            new ControlMDMLiquido(session).criaTabelaMDMLiquido();
            new ControlMMGas(session).criaTabelaMMGas();
            new ControlMMLiquido(session).criaTabelaMMLiquido();
            new ControlOctaneGas(session).criaTabelaOctaneGas();
            new ControlOctaneLiquido(session).criaTabelaOctaneLiquido();
            new ControlPentaneGas(session).criaTabelaPentaneGas();
            new ControlPentaneLiquido(session).criaTabelaPentaneLiquido();
            new ControlPropaneGas(session).criaTabelaPropaneGas();
            new ControlPropaneLiquido(session).criaTabelaPropaneLiquido();
            new ControlR114Gas(session).criaTabelaR114Gas();
            new ControlR114Liquido(session).criaTabelaR114Liquido();
            new ControlR11Gas(session).criaTabelaR11Gas();
            new ControlR11Liquido(session).criaTabelaR11Liquido();
            new ControlR12Gas(session).criaTabelaR12Gas();
            new ControlR12Liquido(session).criaTabelaR12Liquido();
            new ControlR134AGas(session).criaTabelaR134AGas();
            new ControlR134ALiquido(session).criaTabelaR134ALiquido();
            new ControlR141BGas(session).criaTabelaR141BGas();
            new ControlR141BLiquido(session).criaTabelaR141BLiquido();
            new ControlR142BGas(session).criaTabelaR142BGas();
            new ControlR142BLiquido(session).criaTabelaR142BLiquido();
            new ControlR152AGas(session).criaTabelaR152AGas();
            new ControlR152ALiquido(session).criaTabelaR152ALiquido();
            new ControlR22Gas(session).criaTabelaR22Gas();
            new ControlR22Liquido(session).criaTabelaR22Liquido();
            new ControlTolueneGas(session).criaTabelaTolueneGas();
            new ControlTolueneLiquido(session).criaTabelaTolueneLiquido();
            new ControlWaterGas(session).criaTabelaWaterGas();
            new ControlWaterLiquido(session).criaTabelaWaterLiquido();
            
            tx.commit();
        }
        
        cr = this.session.createCriteria(ModelConstantesKCSMat_C.class);
        results = cr.list();
        Transaction tx = session.beginTransaction();
        
        if(results.isEmpty()){
            String csvFile = "src/Csv/C.csv";
            BufferedReader br = null;
            String line = "";
            String csvSplitBy = ";";
            
            try{
                br = new BufferedReader(new FileReader(csvFile));
                while((line = br.readLine()) != null){
                    double[] valoresV = new double[6];
                    String[] table_c = line.split(csvSplitBy);
                    for(int i = 0; i < table_c.length; i++){
                        valoresV[i] = Double.parseDouble(table_c[i]);
                    }
                    this.session.save(new ModelConstantesKCSMat_C(valoresV));
                }
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            csvFile = "src/Csv/CC.csv";
            br = null;
            
            try{
                br = new BufferedReader(new FileReader(csvFile));
                while((line = br.readLine()) != null){
                    double[] valoresV = new double[7];
                    String[] table_c = line.split(csvSplitBy);
                    for(int i = 0; i < table_c.length; i++){
                        valoresV[i] = Double.parseDouble(table_c[i]);
                    }
                    this.session.save(new ModelConstantesKCSMat_CC(valoresV)); 
                }
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        tx.commit();
        
        cr = this.session.createCriteria(ModelCriticasKCSMat_K1.class);
        results = cr.list();
        
        if(results.isEmpty()){
            tx = session.beginTransaction();
            
            double[] valores = {-0.06635, 0.03849, 0.03994, 0.02715, 0.01277, 0.12634,0.03079,0.16431,0.09627,0.03781,0.0284,0.02222,0.03521,-0.0103,0.19724,0.00689,-0.24609,0.0304,-0.0528,0.0346,0.1568,0.0388,0.02574,0.02752,0.10833,0.02818,0.05949,-0.1459,0.00689,0.06998,0.07222,0.07233,0.02623};
            for(int i = 0; i < valores.length; i++){
                this.session.save(new ModelCriticasKCSMat_K1(valores[i]));
            }
            
            double[] valores2 = {18.0, 92.14, 106.17, 120.19, 106.17, 236.53, 310.69, 384.94, 444.92, 58.12, 72.15, 58.12, 72.15, 102.0, 44.1, 100.5, 42.08, 50.49, 162.37752, 458.99328, 296.61576, 370.7697,137.36,120.91,220.92,170.91,116.94,66.05,100.49,86.18,100.2,114.2,86.47};
            for(int i = 0; i < valores2.length; i++){
                this.session.save(new ModelCriticasKCSMat_PM(valores2[i]));
            }
            
            double[] valores3 = {22089.75,4106.0,3606.0,3200.0,3511.0,1415.0,1227.0,945.0,961.0,3648.0,3381.0,4023.0,3529.0,4059.0,4242.0,4120.0,4664.0,6700.0,1939.39,877.47,1332.0,1160.46,4408.0,4124.0,2570.0,3300.0,4340.0,4444.0,4121.0,3032.0,2737.0,2497.0,4975.0};
            for(int i = 0; i < valores3.length; i++){
                this.session.save(new ModelCriticasKCSMat_Pc(valores3[i]));
            }
            
            double[] valores4 = {647.286,591.8,617.2,638.32,616.23,564.09,599.4,628.36,645.78,408.05,460.43,419.53,464.78,374.21,369.82,410.25,365.59,416.25,518.75,653.2,586.5,619.15,471.11,385.12,449.05,418.55,478.85,386.41,410.25,507.85,540.15,568.55,369.2};
            for(int i = 0; i < valores4.length; i++){
                this.session.save(new ModelCriticasKCSMat_Tc(valores4[i]));
            }
            
            double[] valores5 = {0.3438,0.26323,0.3027,0.34513,0.32141,0.5297,0.668,0.7218,0.7361,0.1848,0.22802,0.19409,0.23329,0.3261,0.268,0.2816,0.21974,0.153,0.419,0.785,0.589,0.6658,0.191,0.176,0.3286,0.2533,0.2211,0.2557,0.236,0.3007,0.3498,0.4018,0.215};
            for(int i = 0; i < valores5.length; i++){
                this.session.save(new ModelCriticasKCSMat_w(valores5[i]));
            }
            
            tx.commit();
        }
        
        cr = this.session.createCriteria(ModelQfpsoKCSMat.class);
        results = cr.list();
        
        if(results.isEmpty()){
            tx = session.beginTransaction();
            String csvFile = "src/Csv/Qfpso.csv";
            BufferedReader br = null;
            String line = "";
            String csvSplitBy = ";";
           
            try{
                cr = this.session.createCriteria(ModelQfpsoKCSMat.class);
                results = cr.list();
                br = new BufferedReader(new FileReader(csvFile));
                while((line = br.readLine()) != null){
                    double[] valoresV = new double[12];
                    String[] table_c = line.split(csvSplitBy);
                    for(int i = 0; i < table_c.length; i++){
                        valoresV[i] = Double.parseDouble(table_c[i]);
                    }
                    this.session.save(new ModelQfpsoKCSMat(valoresV));
                }

            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                tx.commit();
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        cr = this.session.createCriteria(ModelConsExeMatA.class);
        results = cr.list();
        
        if(results.isEmpty()){
            tx = session.beginTransaction();
            this.session.save(new ModelConsExeMatA(new double[]{10.57, 1.05, 2.06, -3936.0}));
             
            this.session.save(new ModelConsExeMatA(new double[] {7.3, 1.23, 0.0, -2286.0}));
            
            this.session.save(new ModelConsExeMatA(new double[] {11.4, 0.94, 1.84, -3992.0}));
            
            this.session.save(new ModelConsExeMatA(new double[] {7.16, 0.5, 0.4, -2313.0}));
            
            this.session.save(new ModelConsExeMatA(new double[] {6.83, 0.45, 0.12, -2127.0}));
            
            this.session.save(new ModelConsExeMatA(new double[] {6.79, 0.49, 0.11, -2105.0}));
            
            this.session.save(new ModelConsExeMatA(new double[] {7.3, 1.23, 0.0, -5379.0}));
            
            this.session.save(new ModelConsExeMatA(new double[] {7.03, 0.46, 0.14, -2184.0}));
            
            double[] valoresB = {20140.0, 11710.0, 303500.0, 3970.0, 720.0, 275430.0, 238490.0, 89040.0};
            
            for(int i = 0; i < valoresB.length; i++){
                this.session.save(new ModelConsExeMatB(valoresB[i]));
            }
            
            tx.commit();
        }
        
        
        cr = this.session.createCriteria(ModelCVA.class);
        results = cr.list();
        tx = session.beginTransaction();
        
        if(results.isEmpty()){
            String csvFile = "src/Csv/Cva.csv";
            BufferedReader br = null;
            String line = "";
            String csvSplitBy = ";";
           
            double[] valoresV = new double[2];
            try{
                cr = this.session.createCriteria(ModelCVA.class);
                results = cr.list();
                br = new BufferedReader(new FileReader(csvFile));
                while((line = br.readLine()) != null){
                    String[] table_c = line.split(csvSplitBy);
                    for(int i = 0; i < table_c.length; i++){
                        valoresV[i] = Double.parseDouble(table_c[i]);
                    }
                    this.session.save(new ModelCVA(valoresV));
                }

            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            csvFile = "src/Csv/Cvb.csv";
            br = null;
            line = "";
            csvSplitBy = ";";
           
            valoresV = new double[2];
            try{
                cr = this.session.createCriteria(ModelCVB.class);
                results = cr.list();
                br = new BufferedReader(new FileReader(csvFile));
                while((line = br.readLine()) != null){
                    String[] table_c = line.split(csvSplitBy);
                    for(int i = 0; i < table_c.length; i++){
                        valoresV[i] = Double.parseDouble(table_c[i]);
                    }
                    this.session.save(new ModelCVB(valoresV));
                }

            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        tx.commit();
        
        cr = this.session.createCriteria(ModelDRT70.class);
        results = cr.list();
        tx = session.beginTransaction();
        
        if(results.isEmpty()){
            String csvFile = "src/Csv/drt70.csv";
            BufferedReader br = null;
            String line = "";
            String csvSplitBy = ";";
           
            try{
                cr = this.session.createCriteria(ModelDRT70.class);
                results = cr.list();
                br = new BufferedReader(new FileReader(csvFile));
                while((line = br.readLine()) != null){
                    double[] valoresV = new double[5];
                    String[] table_c = line.split(csvSplitBy);
                    for(int i = 0; i < table_c.length; i++){
                        valoresV[i] = Double.parseDouble(table_c[i]);
                    }
                    this.session.save(new ModelDRT70(valoresV));
                }

            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            csvFile = "src/Csv/drt80.csv";
            br = null;
            line = "";
            csvSplitBy = ";";
           
            try{
                cr = this.session.createCriteria(ModelDRT80.class);
                results = cr.list();
                br = new BufferedReader(new FileReader(csvFile));
                while((line = br.readLine()) != null){
                    double[] valoresV = new double[4];
                    String[] table_c = line.split(csvSplitBy);
                    for(int i = 0; i < table_c.length; i++){
                        valoresV[i] = Double.parseDouble(table_c[i]);
                    }
                    this.session.save(new ModelDRT80(valoresV));
                }

            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            csvFile = "src/Csv/eqro.csv";
            br = null;
            line = "";
            csvSplitBy = ";";
           
            try{
                cr = this.session.createCriteria(ModelEqro.class);
                results = cr.list();
                br = new BufferedReader(new FileReader(csvFile));
                while((line = br.readLine()) != null){
                    double[] valoresV = new double[3];
                    String[] table_c = line.split(csvSplitBy);
                    for(int i = 0; i < table_c.length; i++){
                        valoresV[i] = Double.parseDouble(table_c[i]);
                    }
                    this.session.save(new ModelEqro(valoresV));
                }

            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            csvFile = "src/Csv/eqrs.csv";
            br = null;
            line = "";
            csvSplitBy = ";";
           
            try{
                cr = this.session.createCriteria(ModelEqrs.class);
                results = cr.list();
                br = new BufferedReader(new FileReader(csvFile));
                while((line = br.readLine()) != null){
                    double[] valoresV = new double[3];
                    String[] table_c = line.split(csvSplitBy);
                    for(int i = 0; i < table_c.length; i++){
                        
                        valoresV[i] = Double.parseDouble(table_c[i]);
                    }
                    this.session.save(new ModelEqrs(valoresV));
                }

            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        tx.commit();
        
        cr = this.session.createCriteria(ModelStart.class);
        results = cr.list();
        if(!results.isEmpty()){
            tx = session.beginTransaction();
            ModelStart start = (ModelStart)results.get(0);
            session.delete(start);
            tx.commit();
        }
        
        viewPrincipal = new ViewPrincipal(this);
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(ControlPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        criaCiclo2();
    }
    
    //FUNÇÃO QUE CRIA O DESENHO DO PRIMEIRO CICLO E INDICA OS LOCAIS DOS JPANELS INSERIDOS
    /*public void criaCiclo1() {
        viewPrincipal.getPainelCiclos().removeAll();
        viewPrincipal.getTabbedPanel().removeAll();
        panel_usado.clear();
        
        ViewCiclos ciclo = new ViewCiclos(this, "src/Images/Ciclo1/ciclo1.png", (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.3), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.9));
        ciclo.setLayout(null);
        ciclo.setBounds((int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.1), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.05), 
                          (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.3), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.9));
        viewPrincipal.getPainelCiclos().add(ciclo);
        
        criaPanel(new ViewTurbinaImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.28), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.065), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.04), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.13));
        
        criaPanel(new ViewSeparadorImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.21), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.04), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.1));
        
        criaPanel(new ViewEvaporadorImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.13), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.166), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.075), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.15));
        
        criaPanel(new ViewCondensadorImage(this, 1, session), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.285), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.725), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.07), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.15));
        
        criaPanel(new ViewRecuperadorImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.184), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.35), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.069), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.15));
        
        criaPanel(new ViewRecuperadorImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.268), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.558), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.069), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.15));
        
        criaPanel(new ViewBombaImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.205), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.875), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.035), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.04));
        
        viewPrincipal.getFramePrincipal().repaint();
    }*/
    
    //FUNÇÃO QUE CRIA O DESENHO DO SEGUNDO CICLO E INDICA OS LOCAIS DOS JPANELS INSERIDOS
    public void criaCiclo2(){                
        ViewCiclos ciclo = new ViewCiclos(this, "src/Images/ciclo2.png", (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.3), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.7));

        ciclo.setLayout(null);
        ciclo.setBounds((int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.1), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.05), 
                          (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.3), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.7));
        viewPrincipal.getPainelCiclos().add(ciclo);
        
        criaPanel(new ViewTurbinaImage(this), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.224), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.05), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.13));
        
        criaPanel(new ViewRegeneradorImage(this), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.236), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.288), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.06), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.13));
        
        criaPanel(new ViewEvaporadorImage(this), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.15), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.148), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.055), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.16));
        
        criaPanel(new ViewCondensadorImage(this), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.275), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.61), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.075), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.11));
        
        criaPanel(new ViewBombaImage(this), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.326), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.38), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.065), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.14));
        
        criaPanel(new ViewEtapaImage(this, 2, "1", start), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.195), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.06), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.03), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.065));
        
        criaPanel(new ViewEtapaImage(this, 2, "2", start),
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.25), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.21), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.03), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.065));
        
        criaPanel(new ViewEtapaImage(this, 2, "3", start),
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.25), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.49), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.03), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.065));
        
        criaPanel(new ViewEtapaImage(this, 2, "4", start),
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.345), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.53), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.03), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.065));
        
        criaPanel(new ViewEtapaImage(this, 2, "5", start),
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.314), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.32), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.03), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.065));
        
        criaPanel(new ViewEtapaImage(this, 2, "6", start),
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.192), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.32), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.03), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.065));
        
        viewLateral = new ViewLateral(this);
        viewPrincipal.getPainelEntrada().setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1, Color.lightGray, Color.lightGray), "Dados de Entrada", 1, 2, new Font("Times New Roman", 1, 12), Color.darkGray));
        viewPrincipal.getPainelEntrada().add(viewLateral.getPainelDados());
        viewPrincipal.getFramePrincipal().revalidate();
        viewPrincipal.getFramePrincipal().repaint();
    }
    
    //FUNÇÃO QUE EDITA OS JPANELS ONDE SERÃO MOSTRADOS OS CICLOS E OS INSERE NO PAINEL
    public void criaPanel(JPanel obj, int x, int y, int width, int height){
        obj.setLayout(null);
        //obj.setBackground(Color.black);
        obj.setOpaque(false);
        obj.setBounds(x, y, width, height);
        viewPrincipal.getPainelCiclos().add(obj);
    }  
    
    //FUNÇÃO QUE AJUSTA A MASSA DE TODOS OS COMPONENTES
    public void ajustaMassa(String valor) {
        Component[] components = viewPrincipal.getTabbedPanelComp().getComponents();
        for(Component c: components){
            switch(c.getName()){
                case "Evaporador":
                    ViewEvaporadorPanelRankine painelE = (ViewEvaporadorPanelRankine)c;
                    painelE.getFieldMassa().setSelectedItem(valor);
                    break;
                
                case "Condensador":
                    ViewCondensadorPanelRankine painelC = (ViewCondensadorPanelRankine)c;
                    painelC.getFieldMassa().setSelectedItem(valor);
                    break;
                    
                case "Bomba":
                    ViewBombaPanelRankine painelB = (ViewBombaPanelRankine)c;
                    painelB.getFieldMassa().setSelectedItem(valor);
                    break;
                    
                case "Regenerador":
                    ViewRegeneradorPanelRankine painelR = (ViewRegeneradorPanelRankine)c;
                    painelR.getFieldMassa().setSelectedItem(valor);
                    break;
                    
                case "Turbina":
                    ViewTurbinaPanelRankine painelT = (ViewTurbinaPanelRankine)c;
                    painelT.getFieldMassa().setSelectedItem(valor);
                    break;
            }
            
            Transaction tx = this.session.beginTransaction();
            Criteria cr = this.session.createCriteria(ModelMassa.class);
            List result = cr.list();
            
            ModelMassa massa = (ModelMassa)result.get(0);
            massa.setMassa(Double.parseDouble(valor));
            session.saveOrUpdate(massa);
            
            tx.commit();
        }
    }
    
    public void removeTab(){
        this.getViewPrincipal().getTabbedPanelComp().remove(0);
        this.getPanel_usado().remove(0);
    }
    
    public List getFluidos(){
        SessionFactory sf = HibernateUtil.getSessionFactory();
        this.session = sf.openSession();
        
        Criteria cr = this.session.createCriteria(ModelFluidos.class);
        cr.setProjection(Projections.property("nome"));
        
        List results = cr.list();
        
        return results;
    }
    
    public int getFluidoCod(String fluido){
        SessionFactory sf = HibernateUtil.getSessionFactory();
        this.session = sf.openSession();
        
        Criteria cr = this.session.createCriteria(ModelFluidos.class);
        cr.add(Restrictions.eq("nome", fluido));
        
        List results = cr.list();
        ModelFluidos fluidos = (ModelFluidos) results.get(0);
        
        return fluidos.getCod();
    }
    
    public void iniciaCalculos(){
        double Tf = 0;
        double Pf = 0;
        double sup = 0;
        double pinch = 0;
        double Tconop = 0;
        double eff = 0;
        
        int comp = Integer.parseInt(viewLateral.getComboCompressores().getSelectedItem().toString());
                
        String fluNome = viewPrincipal.getComboFluidos().getSelectedItem().toString();
        int flu = getFluidoCod(fluNome);

        if(viewLateral.getFieldTemp().getEditor().getItem().toString().isEmpty()){
            JOptionPane.showMessageDialog(null, "É necessário inserir a temperatura");
            return;
        }
        Tf = Double.parseDouble(viewLateral.getFieldTemp().getEditor().getItem().toString());
        
        if(viewLateral.getFieldPressao().getEditor().getItem().toString().isEmpty()){
            JOptionPane.showMessageDialog(null, "É necessário inserir a pressão");
            return;
        }
        Pf = Double.parseDouble(viewLateral.getFieldPressao().getEditor().getItem().toString());

        if(viewLateral.getFieldSup().getEditor().getItem().toString().isEmpty()){
            JOptionPane.showMessageDialog(null, "É necessário inserir o valor do superaquecimento");
            return;
        }
        sup = Double.parseDouble(viewLateral.getFieldSup().getEditor().getItem().toString());
        
        if(viewLateral.getFieldPinch().getEditor().getItem().toString().isEmpty()){
            JOptionPane.showMessageDialog(null, "É necessário inserir o valor de pinch");
            return;
        }
        pinch = Double.parseDouble(viewLateral.getFieldPinch().getEditor().getItem().toString());

        if(viewLateral.getFieldTempCond().getEditor().getItem().toString().isEmpty()){
            JOptionPane.showMessageDialog(null, "É necessário inserir a temperatura de condensação");
            return;
        }
        Tconop = Double.parseDouble(viewLateral.getFieldTempCond().getEditor().getItem().toString());
        
        if(viewLateral.getFieldEfetiv().getEditor().getItem().toString().isEmpty()){
            JOptionPane.showMessageDialog(null, "É necessário inserir a efetividade");
            return;
        }
        eff = (Double.parseDouble(viewLateral.getFieldEfetiv().getEditor().getItem().toString()))/100;
        
        //Start start = new Start(1, 14, 415.25, 1144.4, 25, 10, 313.15, 0.3, session);
        start = new Start(comp, flu, Tf, Pf, sup, pinch, Tconop, eff, session, this);
        viewLateral.getFrameEspera().dispose();
        if(!start.getMensagem().equals("")){
            JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        adicionaValoresCaixas();
    }
    
    public void calculaLimites() {
        Criteria cr = this.session.createCriteria(ModelFluidos.class);
        cr.add(Restrictions.eq("nome", viewPrincipal.getComboFluidos().getSelectedItem()));
        
        List results = cr.list();
        
        ModelFluidos fluido = (ModelFluidos) results.get(0);
        int ii = fluido.getCod();
        
        cr = this.session.createCriteria(ModelCriticasKCSMat_Pc.class);
        cr.add(Restrictions.eq("cod", ii));
        results = cr.list();
        
        ModelCriticasKCSMat_Pc pc = (ModelCriticasKCSMat_Pc) results.get(0);
        double p = pc.getValor();
        
        PMax = p * 0.95;
        
        ControlT_Ref TRef = new ControlT_Ref(PMax, ii, session);
        TMax = TRef.getTref();
    }
    
    public void adicionaValoresCaixas(){
        Criteria cr = this.session.createCriteria(ModelStart.class);
        List results = cr.list();
        ModelStart start_l = null;
        
        if(results.isEmpty()){
            start_l = new ModelStart();
        }else{
            start_l = (ModelStart) results.get(0);
        }
        
        start_l.setT1(start.getT1());
        start_l.setT2(start.getT2());
        start_l.setT3(start.getT3());
        start_l.setT4(start.getT4());
        start_l.setT5(start.getT5());
        start_l.setT6(start.getT6());

        start_l.setP1(start.getP1());
        start_l.setP2(start.getP2());
        start_l.setP3(start.getP3());
        start_l.setP4(start.getP4());
        start_l.setP5(start.getP5());
        start_l.setP6(start.getP6());

        start_l.setH1(start.getH1());
        start_l.setH2(start.getH2());
        start_l.setH3(start.getH3());
        start_l.setH4(start.getH4());
        start_l.setH5(start.getH5());
        start_l.setH6(start.getH6());

        start_l.setS1(start.getS1());
        start_l.setS2(start.getS2());
        start_l.setS3(start.getS3());
        start_l.setS4(start.getS4());
        start_l.setS5(start.getS5());
        start_l.setS6(start.getS6());

        start_l.setM(start.getM());
        
        start_l.setBeff(start.getBeff());
        start_l.setQCon(start.getQcon());
        start_l.setEff(start.getEff());

        Transaction tx = this.session.beginTransaction();
        session.saveOrUpdate(start_l);
        tx.commit();
        
        criaPanel(new ViewEtapaImage(this, 2, "1", start), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.195), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.06), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.03), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.065));
        
        criaPanel(new ViewEtapaImage(this, 2, "2", start),
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.25), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.21), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.03), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.065));
        
        criaPanel(new ViewEtapaImage(this, 2, "3", start),
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.25), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.49), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.03), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.065));
        
        criaPanel(new ViewEtapaImage(this, 2, "4", start),
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.345), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.53), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.03), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.065));
        
        criaPanel(new ViewEtapaImage(this, 2, "5", start),
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.314), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.32), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.03), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.065));
        
        criaPanel(new ViewEtapaImage(this, 2, "6", start),
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.192), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.32), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.03), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.065));
    }
    
    public ViewPrincipal getViewPrincipal() {
        return viewPrincipal;
    }

    public void setViewPrincipal(ViewPrincipal viewPrincipal) {
        this.viewPrincipal = viewPrincipal;
    }

    public ArrayList<JPanel> getPanel_usado() {
        return panel_usado;
    }

    public void setPanel_usado(ArrayList<JPanel> panel_usado) {
        this.panel_usado = panel_usado;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public double getPMax() {
        return PMax;
    }

    public void setPMax(double PMax) {
        this.PMax = PMax;
    }

    public double getTMax() {
        return TMax;
    }

    public void setTMax(double TMax) {
        this.TMax = TMax;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}