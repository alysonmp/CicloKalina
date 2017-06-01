/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelR12Gas;
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
public class ControlR12Gas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlR12Gas(Session session){
        this.session = session;
    }
    
    public void criaTabelaR12Gas(){
        String csvFile = "src/Csv/R12_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR12Gas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R12_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelR12Gas(Double.parseDouble(R12_g[0]), Double.parseDouble(R12_g[1]), Double.parseDouble(R12_g[2]), Double.parseDouble(R12_g[3]), Double.parseDouble(R12_g[4]), Double.parseDouble(R12_g[5]), Double.parseDouble(R12_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelR12Gas.class);
        //cr = this.session.createCriteria(ModelR12Gas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R12_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR12Gas.class));//Sem isso aqui impossível de retornar
	List<ModelR12Gas> R12s = consulta.list(); 
        ModelR12Gas R121 = R12s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R12_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR12Gas.class));//Sem isso aqui impossível de retornar
	R12s = consulta.list(); 
        ModelR12Gas R122 = R12s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R12_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR12Gas.class));//Sem isso aqui impossível de retornar
	R12s = consulta.list(); 
        ModelR12Gas R123 = R12s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R12_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR12Gas.class));//Sem isso aqui impossível de retornar
	R12s = consulta.list(); 
        ModelR12Gas R124 = R12s.get(0);
        
        cpv1 = R121.getCPV() + (R122.getCPV() - R121.getCPV()) * ((temperatura-R121.getTEMPERATURA())/(R122.getTEMPERATURA()-R121.getTEMPERATURA()));
        cpv2 = R123.getCPV() + (R124.getCPV() - R123.getCPV()) * ((temperatura-R123.getTEMPERATURA())/(R124.getTEMPERATURA()-R123.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-R121.getPRESSAO())/(R123.getPRESSAO()-R121.getPRESSAO()));
        
        Prv1 = R121.getPRV() + (R122.getPRV() - R121.getPRV()) * ((temperatura-R121.getTEMPERATURA())/(R122.getTEMPERATURA()-R121.getTEMPERATURA()));
        Prv2 = R123.getPRV() + (R124.getPRV() - R123.getPRV()) * ((temperatura-R123.getTEMPERATURA())/(R124.getTEMPERATURA()-R123.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-R121.getPRESSAO())/(R123.getPRESSAO()-R121.getPRESSAO()));
        
        kv1 = R121.getKV() + (R122.getKV() - R121.getKV()) * ((temperatura-R121.getTEMPERATURA())/(R122.getTEMPERATURA()-R121.getTEMPERATURA()));
        kv2 = R123.getKV() + (R124.getKV() - R123.getKV()) * ((temperatura-R123.getTEMPERATURA())/(R124.getTEMPERATURA()-R123.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-R121.getPRESSAO())/(R123.getPRESSAO()-R121.getPRESSAO()));
        
        Muv1 = R121.getMUV() + (R122.getMUV() - R121.getMUV()) * ((temperatura-R121.getTEMPERATURA())/(R122.getTEMPERATURA()-R121.getTEMPERATURA()));
        Muv2 = R123.getMUV() + (R124.getMUV() - R123.getMUV()) * ((temperatura-R123.getTEMPERATURA())/(R124.getTEMPERATURA()-R123.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-R121.getPRESSAO())/(R123.getPRESSAO()-R121.getPRESSAO()));
        
        Vcv1 = R121.getVCV() + (R122.getVCV() - R121.getVCV()) * ((temperatura-R121.getTEMPERATURA())/(R122.getTEMPERATURA()-R121.getTEMPERATURA()));
        Vcv2 = R123.getVCV() + (R124.getVCV() - R123.getVCV()) * ((temperatura-R123.getTEMPERATURA())/(R124.getTEMPERATURA()-R123.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-R121.getPRESSAO())/(R123.getPRESSAO()-R121.getPRESSAO()));
        
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
