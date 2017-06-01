/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelMDMGas;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author alysonmp
 */
public class ControlMDMGas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlMDMGas(Session session){
        this.session = session;
    }
    
    public void criaTabelaMDMGas(){
        String csvFile = "src/Csv/MDM_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelMDMGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use coMDMa as separator
                    String[] MDM_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelMDMGas(Double.parseDouble(MDM_g[0]), Double.parseDouble(MDM_g[1]), Double.parseDouble(MDM_g[2]), Double.parseDouble(MDM_g[3]), Double.parseDouble(MDM_g[4]), Double.parseDouble(MDM_g[5]), Double.parseDouble(MDM_g[6])));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void interpolacao(double pressao, double temperatura){
        Criteria cr = this.session.createCriteria(ModelMDMGas.class);
        //cr = this.session.createCriteria(ModelMDMGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from MDM_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMDMGas.class));//Sem isso aqui impossível de retornar
	List<ModelMDMGas> MDMs = consulta.list(); 
        ModelMDMGas MDM1 = MDMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MDM_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMDMGas.class));//Sem isso aqui impossível de retornar
	MDMs = consulta.list(); 
        ModelMDMGas MDM2 = MDMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MDM_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMDMGas.class));//Sem isso aqui impossível de retornar
	MDMs = consulta.list(); 
        ModelMDMGas MDM3 = MDMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MDM_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMDMGas.class));//Sem isso aqui impossível de retornar
	MDMs = consulta.list(); 
        ModelMDMGas MDM4 = MDMs.get(0);
        
        cpv1 = MDM1.getCPV() + (MDM2.getCPV() - MDM1.getCPV()) * ((temperatura-MDM1.getTEMPERATURA())/(MDM2.getTEMPERATURA()-MDM1.getTEMPERATURA()));
        cpv2 = MDM3.getCPV() + (MDM4.getCPV() - MDM3.getCPV()) * ((temperatura-MDM3.getTEMPERATURA())/(MDM4.getTEMPERATURA()-MDM3.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-MDM1.getPRESSAO())/(MDM3.getPRESSAO()-MDM1.getPRESSAO()));
        
        Prv1 = MDM1.getPRV() + (MDM2.getPRV() - MDM1.getPRV()) * ((temperatura-MDM1.getTEMPERATURA())/(MDM2.getTEMPERATURA()-MDM1.getTEMPERATURA()));
        Prv2 = MDM3.getPRV() + (MDM4.getPRV() - MDM3.getPRV()) * ((temperatura-MDM3.getTEMPERATURA())/(MDM4.getTEMPERATURA()-MDM3.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-MDM1.getPRESSAO())/(MDM3.getPRESSAO()-MDM1.getPRESSAO()));
        
        kv1 = MDM1.getKV() + (MDM2.getKV() - MDM1.getKV()) * ((temperatura-MDM1.getTEMPERATURA())/(MDM2.getTEMPERATURA()-MDM1.getTEMPERATURA()));
        kv2 = MDM3.getKV() + (MDM4.getKV() - MDM3.getKV()) * ((temperatura-MDM3.getTEMPERATURA())/(MDM4.getTEMPERATURA()-MDM3.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-MDM1.getPRESSAO())/(MDM3.getPRESSAO()-MDM1.getPRESSAO()));
        
        Muv1 = MDM1.getMUV() + (MDM2.getMUV() - MDM1.getMUV()) * ((temperatura-MDM1.getTEMPERATURA())/(MDM2.getTEMPERATURA()-MDM1.getTEMPERATURA()));
        Muv2 = MDM3.getMUV() + (MDM4.getMUV() - MDM3.getMUV()) * ((temperatura-MDM3.getTEMPERATURA())/(MDM4.getTEMPERATURA()-MDM3.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-MDM1.getPRESSAO())/(MDM3.getPRESSAO()-MDM1.getPRESSAO()));
        
        Vcv1 = MDM1.getVCV() + (MDM2.getVCV() - MDM1.getVCV()) * ((temperatura-MDM1.getTEMPERATURA())/(MDM2.getTEMPERATURA()-MDM1.getTEMPERATURA()));
        Vcv2 = MDM3.getVCV() + (MDM4.getVCV() - MDM3.getVCV()) * ((temperatura-MDM3.getTEMPERATURA())/(MDM4.getTEMPERATURA()-MDM3.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-MDM1.getPRESSAO())/(MDM3.getPRESSAO()-MDM1.getPRESSAO()));
        
        System.out.println("cpv = "+Cpv);
        System.out.println("prv = "+Prv);
        System.out.println("kv = "+kv);
        System.out.println("muv = "+Muv);
        System.out.println("vcv = "+Vcv);
    }

    public double getCpv() {
        return Cpv;
    }

    public void setCpv(double Cpv) {
        this.Cpv = Cpv;
    }

    public double getPrv() {
        return Prv;
    }

    public void setPrv(double Prv) {
        this.Prv = Prv;
    }

    public double getKv() {
        return kv;
    }

    public void setKv(double kv) {
        this.kv = kv;
    }

    public double getMuv() {
        return Muv;
    }

    public void setMuv(double Muv) {
        this.Muv = Muv;
    }

    public double getVcv() {
        return Vcv;
    }

    public void setVcv(double Vcv) {
        this.Vcv = Vcv;
    }
}
