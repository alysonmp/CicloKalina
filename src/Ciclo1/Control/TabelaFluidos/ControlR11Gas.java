/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelR11Gas;
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
public class ControlR11Gas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlR11Gas(Session session){
        this.session = session;
    }
    
    public void criaTabelaR11Gas(){
        String csvFile = "src/Csv/R11_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR11Gas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R11_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelR11Gas(Double.parseDouble(R11_g[0]), Double.parseDouble(R11_g[1]), Double.parseDouble(R11_g[2]), Double.parseDouble(R11_g[3]), Double.parseDouble(R11_g[4]), Double.parseDouble(R11_g[5]), Double.parseDouble(R11_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelR11Gas.class);
        //cr = this.session.createCriteria(ModelR11Gas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R11_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR11Gas.class));//Sem isso aqui impossível de retornar
	List<ModelR11Gas> R11s = consulta.list(); 
        ModelR11Gas R111 = R11s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R11_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR11Gas.class));//Sem isso aqui impossível de retornar
	R11s = consulta.list(); 
        ModelR11Gas R112 = R11s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R11_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR11Gas.class));//Sem isso aqui impossível de retornar
	R11s = consulta.list(); 
        ModelR11Gas R113 = R11s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R11_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR11Gas.class));//Sem isso aqui impossível de retornar
	R11s = consulta.list(); 
        ModelR11Gas R114 = R11s.get(0);
        
        cpv1 = R111.getCPV() + (R112.getCPV() - R111.getCPV()) * ((temperatura-R111.getTEMPERATURA())/(R112.getTEMPERATURA()-R111.getTEMPERATURA()));
        cpv2 = R113.getCPV() + (R114.getCPV() - R113.getCPV()) * ((temperatura-R113.getTEMPERATURA())/(R114.getTEMPERATURA()-R113.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-R111.getPRESSAO())/(R113.getPRESSAO()-R111.getPRESSAO()));
        
        Prv1 = R111.getPRV() + (R112.getPRV() - R111.getPRV()) * ((temperatura-R111.getTEMPERATURA())/(R112.getTEMPERATURA()-R111.getTEMPERATURA()));
        Prv2 = R113.getPRV() + (R114.getPRV() - R113.getPRV()) * ((temperatura-R113.getTEMPERATURA())/(R114.getTEMPERATURA()-R113.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-R111.getPRESSAO())/(R113.getPRESSAO()-R111.getPRESSAO()));
        
        kv1 = R111.getKV() + (R112.getKV() - R111.getKV()) * ((temperatura-R111.getTEMPERATURA())/(R112.getTEMPERATURA()-R111.getTEMPERATURA()));
        kv2 = R113.getKV() + (R114.getKV() - R113.getKV()) * ((temperatura-R113.getTEMPERATURA())/(R114.getTEMPERATURA()-R113.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-R111.getPRESSAO())/(R113.getPRESSAO()-R111.getPRESSAO()));
        
        Muv1 = R111.getMUV() + (R112.getMUV() - R111.getMUV()) * ((temperatura-R111.getTEMPERATURA())/(R112.getTEMPERATURA()-R111.getTEMPERATURA()));
        Muv2 = R113.getMUV() + (R114.getMUV() - R113.getMUV()) * ((temperatura-R113.getTEMPERATURA())/(R114.getTEMPERATURA()-R113.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-R111.getPRESSAO())/(R113.getPRESSAO()-R111.getPRESSAO()));
        
        Vcv1 = R111.getVCV() + (R112.getVCV() - R111.getVCV()) * ((temperatura-R111.getTEMPERATURA())/(R112.getTEMPERATURA()-R111.getTEMPERATURA()));
        Vcv2 = R113.getVCV() + (R114.getVCV() - R113.getVCV()) * ((temperatura-R113.getTEMPERATURA())/(R114.getTEMPERATURA()-R113.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-R111.getPRESSAO())/(R113.getPRESSAO()-R111.getPRESSAO()));
        
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
