/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelR114Gas;
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
public class ControlR114Gas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlR114Gas(Session session){
        this.session = session;
    }
    
    public void criaTabelaR114Gas(){
        String csvFile = "src/Csv/R114_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR114Gas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R114_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelR114Gas(Double.parseDouble(R114_g[0]), Double.parseDouble(R114_g[1]), Double.parseDouble(R114_g[2]), Double.parseDouble(R114_g[3]), Double.parseDouble(R114_g[4]), Double.parseDouble(R114_g[5]), Double.parseDouble(R114_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelR114Gas.class);
        //cr = this.session.createCriteria(ModelR114Gas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R114_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR114Gas.class));//Sem isso aqui impossível de retornar
	List<ModelR114Gas> R114s = consulta.list(); 
        ModelR114Gas R1141 = R114s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R114_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR114Gas.class));//Sem isso aqui impossível de retornar
	R114s = consulta.list(); 
        ModelR114Gas R1142 = R114s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R114_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR114Gas.class));//Sem isso aqui impossível de retornar
	R114s = consulta.list(); 
        ModelR114Gas R1143 = R114s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R114_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR114Gas.class));//Sem isso aqui impossível de retornar
	R114s = consulta.list(); 
        ModelR114Gas R1144 = R114s.get(0);
        
        cpv1 = R1141.getCPV() + (R1142.getCPV() - R1141.getCPV()) * ((temperatura-R1141.getTEMPERATURA())/(R1142.getTEMPERATURA()-R1141.getTEMPERATURA()));
        cpv2 = R1143.getCPV() + (R1144.getCPV() - R1143.getCPV()) * ((temperatura-R1143.getTEMPERATURA())/(R1144.getTEMPERATURA()-R1143.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-R1141.getPRESSAO())/(R1143.getPRESSAO()-R1141.getPRESSAO()));
        
        Prv1 = R1141.getPRV() + (R1142.getPRV() - R1141.getPRV()) * ((temperatura-R1141.getTEMPERATURA())/(R1142.getTEMPERATURA()-R1141.getTEMPERATURA()));
        Prv2 = R1143.getPRV() + (R1144.getPRV() - R1143.getPRV()) * ((temperatura-R1143.getTEMPERATURA())/(R1144.getTEMPERATURA()-R1143.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-R1141.getPRESSAO())/(R1143.getPRESSAO()-R1141.getPRESSAO()));
        
        kv1 = R1141.getKV() + (R1142.getKV() - R1141.getKV()) * ((temperatura-R1141.getTEMPERATURA())/(R1142.getTEMPERATURA()-R1141.getTEMPERATURA()));
        kv2 = R1143.getKV() + (R1144.getKV() - R1143.getKV()) * ((temperatura-R1143.getTEMPERATURA())/(R1144.getTEMPERATURA()-R1143.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-R1141.getPRESSAO())/(R1143.getPRESSAO()-R1141.getPRESSAO()));
        
        Muv1 = R1141.getMUV() + (R1142.getMUV() - R1141.getMUV()) * ((temperatura-R1141.getTEMPERATURA())/(R1142.getTEMPERATURA()-R1141.getTEMPERATURA()));
        Muv2 = R1143.getMUV() + (R1144.getMUV() - R1143.getMUV()) * ((temperatura-R1143.getTEMPERATURA())/(R1144.getTEMPERATURA()-R1143.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-R1141.getPRESSAO())/(R1143.getPRESSAO()-R1141.getPRESSAO()));
        
        Vcv1 = R1141.getVCV() + (R1142.getVCV() - R1141.getVCV()) * ((temperatura-R1141.getTEMPERATURA())/(R1142.getTEMPERATURA()-R1141.getTEMPERATURA()));
        Vcv2 = R1143.getVCV() + (R1144.getVCV() - R1143.getVCV()) * ((temperatura-R1143.getTEMPERATURA())/(R1144.getTEMPERATURA()-R1143.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-R1141.getPRESSAO())/(R1143.getPRESSAO()-R1141.getPRESSAO()));
        
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
