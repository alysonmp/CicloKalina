/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelD6Gas;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

/**
 *
 * @author leonardo
 */
public class ControlD6Gas {
    private Session session;
    private double kv, Cpv, Prv, Muv, Vcv;
    private double kv1, kv2, Cpv1, Cpv2, Prv1, Prv2, Muv1, Muv2, Vcv1, Vcv2;

    public ControlD6Gas(Session session) {
        this.session = session;
    }
    
    public void criaTabelaD6Gas(){
        String csvFile = "src/Csv/D6_gas.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelD6Gas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] d6_gas = line.split(csvSplitBy);
                    
                    this.session.save(new ModelD6Gas(Double.parseDouble(d6_gas[0]),Double.parseDouble(d6_gas[1]),Double.parseDouble(d6_gas[2]),Double.parseDouble(d6_gas[3]),Double.parseDouble(d6_gas[4]),Double.parseDouble(d6_gas[5]), Double.parseDouble(d6_gas[6])));   
                }
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
    
    public void interpolacao(double pressao,double temperatura){
        Criteria cr = this.session.createCriteria(ModelD6Gas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from d6_gas where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD6Gas.class));
        List<ModelD6Gas> d6_gas = consulta.list();
        ModelD6Gas d6_gas1 = d6_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from d6_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD6Gas.class));
        d6_gas = consulta.list();
        ModelD6Gas d6_gas2 = d6_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from d6_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD6Gas.class));
        d6_gas = consulta.list();
        ModelD6Gas d6_gas3 = d6_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from d6_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD6Gas.class));
        d6_gas = consulta.list();
        ModelD6Gas d6_gas4 = d6_gas.get(0);
        
        double p = ((pressao - d6_gas1.getPRESSAO())/(d6_gas3.getPRESSAO() - d6_gas1.getPRESSAO()));
        double t1 = ((temperatura - d6_gas1.getTEMPERATURA())/(d6_gas2.getTEMPERATURA() - d6_gas1.getTEMPERATURA()));
        double t2 = ((temperatura - d6_gas3.getTEMPERATURA())/(d6_gas4.getTEMPERATURA() - d6_gas3.getTEMPERATURA()));

        Cpv2 = d6_gas1.getCPV() + (d6_gas2.getCPV() - d6_gas1.getCPV()) * t1;
        Cpv2 = d6_gas3.getCPV() + (d6_gas4.getCPV() - d6_gas3.getCPV()) * t2;
        Cpv = Cpv1 + (Cpv2 - Cpv1) * p;
        
        Prv1 = d6_gas1.getPRV() + (d6_gas2.getPRV() - d6_gas1.getPRV()) * t1;
        Prv2 = d6_gas3.getPRV() + (d6_gas4.getPRV() - d6_gas3.getPRV()) * t2;
        Prv2 = Prv1 + (Prv2 - Prv1) * p;
        
        kv1 = d6_gas1.getKV() + (d6_gas2.getKV() - d6_gas1.getKV()) * t1;
        kv2 = d6_gas3.getKV() + (d6_gas4.getKV() - d6_gas3.getKV()) * t2;
        kv = kv1 + (kv2 - kv1) * p;
        
        Muv1 = d6_gas1.getMUV() + (d6_gas2.getMUV() - d6_gas1.getMUV()) * t1;
        Muv2 = d6_gas3.getMUV() + (d6_gas4.getMUV() - d6_gas3.getMUV()) * t2;
        Muv = Muv1 + (Muv2 - Muv1) * p;
        
        Vcv1 = d6_gas1.getVCV() + (d6_gas2.getVCV() - d6_gas1.getVCV()) * t1;
        Vcv2 = d6_gas3.getVCV() + (d6_gas4.getVCV() - d6_gas3.getVCV()) * t2;
        Vcv = Vcv1 + (Vcv2 - Vcv1) * p;
    }

    public double getKv() {
        return kv;
    }

    public void setKv(double kv) {
        this.kv = kv;
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
