/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelR22Gas;
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
public class ControlR22Gas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlR22Gas(Session session){
        this.session = session;
    }
    
    public void criaTabelaR22Gas(){
        String csvFile = "src/Csv/R22_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR22Gas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R22_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelR22Gas(Double.parseDouble(R22_g[0]), Double.parseDouble(R22_g[1]), Double.parseDouble(R22_g[2]), Double.parseDouble(R22_g[3]), Double.parseDouble(R22_g[4]), Double.parseDouble(R22_g[5]), Double.parseDouble(R22_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelR22Gas.class);
        //cr = this.session.createCriteria(ModelR22Gas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R22_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR22Gas.class));//Sem isso aqui impossível de retornar
	List<ModelR22Gas> R22s = consulta.list(); 
        ModelR22Gas R221 = R22s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R22_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR22Gas.class));//Sem isso aqui impossível de retornar
	R22s = consulta.list(); 
        ModelR22Gas R222 = R22s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R22_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR22Gas.class));//Sem isso aqui impossível de retornar
	R22s = consulta.list(); 
        ModelR22Gas R223 = R22s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R22_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR22Gas.class));//Sem isso aqui impossível de retornar
	R22s = consulta.list(); 
        ModelR22Gas R224 = R22s.get(0);
        
        cpv1 = R221.getCPV() + (R222.getCPV() - R221.getCPV()) * ((temperatura-R221.getTEMPERATURA())/(R222.getTEMPERATURA()-R221.getTEMPERATURA()));
        cpv2 = R223.getCPV() + (R224.getCPV() - R223.getCPV()) * ((temperatura-R223.getTEMPERATURA())/(R224.getTEMPERATURA()-R223.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-R221.getPRESSAO())/(R223.getPRESSAO()-R221.getPRESSAO()));
        
        Prv1 = R221.getPRV() + (R222.getPRV() - R221.getPRV()) * ((temperatura-R221.getTEMPERATURA())/(R222.getTEMPERATURA()-R221.getTEMPERATURA()));
        Prv2 = R223.getPRV() + (R224.getPRV() - R223.getPRV()) * ((temperatura-R223.getTEMPERATURA())/(R224.getTEMPERATURA()-R223.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-R221.getPRESSAO())/(R223.getPRESSAO()-R221.getPRESSAO()));
        
        kv1 = R221.getKV() + (R222.getKV() - R221.getKV()) * ((temperatura-R221.getTEMPERATURA())/(R222.getTEMPERATURA()-R221.getTEMPERATURA()));
        kv2 = R223.getKV() + (R224.getKV() - R223.getKV()) * ((temperatura-R223.getTEMPERATURA())/(R224.getTEMPERATURA()-R223.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-R221.getPRESSAO())/(R223.getPRESSAO()-R221.getPRESSAO()));
        
        Muv1 = R221.getMUV() + (R222.getMUV() - R221.getMUV()) * ((temperatura-R221.getTEMPERATURA())/(R222.getTEMPERATURA()-R221.getTEMPERATURA()));
        Muv2 = R223.getMUV() + (R224.getMUV() - R223.getMUV()) * ((temperatura-R223.getTEMPERATURA())/(R224.getTEMPERATURA()-R223.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-R221.getPRESSAO())/(R223.getPRESSAO()-R221.getPRESSAO()));
        
        Vcv1 = R221.getVCV() + (R222.getVCV() - R221.getVCV()) * ((temperatura-R221.getTEMPERATURA())/(R222.getTEMPERATURA()-R221.getTEMPERATURA()));
        Vcv2 = R223.getVCV() + (R224.getVCV() - R223.getVCV()) * ((temperatura-R223.getTEMPERATURA())/(R224.getTEMPERATURA()-R223.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-R221.getPRESSAO())/(R223.getPRESSAO()-R221.getPRESSAO()));
        
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
