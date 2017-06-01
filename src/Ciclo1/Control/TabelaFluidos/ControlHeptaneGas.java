/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelHeptaneGas;
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
public class ControlHeptaneGas {
    private Session session;
    private double kv, Cpv, Prv, Muv, Vcv;
    private double Cpv1, Cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlHeptaneGas(Session session) {
        this.session = session;
    }
    
    public void criaTabelaHeptaneGas(){
        String csvFile = "src/Csv/HEPTANE_gas.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelHeptaneGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] heptane_g = line.split(csvSplitBy);
                    
                    this.session.save(new ModelHeptaneGas(Double.parseDouble(heptane_g[0]),Double.parseDouble(heptane_g[1]),Double.parseDouble(heptane_g[2]),Double.parseDouble(heptane_g[3]),Double.parseDouble(heptane_g[4]),Double.parseDouble(heptane_g[5]), Double.parseDouble(heptane_g[6])));   
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
        Criteria cr = this.session.createCriteria(ModelHeptaneGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from heptane_gas where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHeptaneGas.class));
        List<ModelHeptaneGas> heptane_gas = consulta.list();
        ModelHeptaneGas heptane_gas1 = heptane_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from heptane_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHeptaneGas.class));
        heptane_gas = consulta.list();
        ModelHeptaneGas heptane_gas2 = heptane_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from heptane_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHeptaneGas.class));
        heptane_gas = consulta.list();
        ModelHeptaneGas heptane_gas3 = heptane_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from heptane_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHeptaneGas.class));
        heptane_gas = consulta.list();
        ModelHeptaneGas heptane_gas4 = heptane_gas.get(0);
        
        double p = ((pressao - heptane_gas1.getPRESSAO())/(heptane_gas3.getPRESSAO() - heptane_gas1.getPRESSAO()));
        double t1 = ((temperatura - heptane_gas1.getTEMPERATURA())/(heptane_gas2.getTEMPERATURA() - heptane_gas1.getTEMPERATURA()));
        double t2 = ((temperatura - heptane_gas3.getTEMPERATURA())/(heptane_gas4.getTEMPERATURA() - heptane_gas3.getTEMPERATURA()));

        Cpv1 = heptane_gas1.getCPV() + (heptane_gas2.getCPV() - heptane_gas1.getCPV()) * t1;
        Cpv2 = heptane_gas3.getCPV() + (heptane_gas4.getCPV() - heptane_gas3.getCPV()) * t2;
        Cpv = Cpv1 + (Cpv2 - Cpv1) * p;
        
        Prv1 = heptane_gas1.getPRV() + (heptane_gas2.getPRV() - heptane_gas1.getPRV()) * t1;
        Prv2 = heptane_gas3.getPRV() + (heptane_gas4.getPRV() - heptane_gas3.getPRV()) * t2;
        Prv2 = Prv1 + (Prv2 - Prv1) * p;
        
        kv1 = heptane_gas1.getKV() + (heptane_gas2.getKV() - heptane_gas1.getKV()) * t1;
        kv2 = heptane_gas3.getKV() + (heptane_gas4.getKV() - heptane_gas3.getKV()) * t2;
        kv = kv1 + (kv2 - kv1) * p;
        
        Muv1 = heptane_gas1.getMUV() + (heptane_gas2.getMUV() - heptane_gas1.getMUV()) * t1;
        Muv2 = heptane_gas3.getMUV() + (heptane_gas4.getMUV() - heptane_gas3.getMUV()) * t2;
        Muv = Muv1 + (Muv2 - Muv1) * p;
        
        Vcv1 = heptane_gas1.getVCV() + (heptane_gas2.getVCV() - heptane_gas1.getVCV()) * t1;
        Vcv2 = heptane_gas3.getVCV() + (heptane_gas4.getVCV() - heptane_gas3.getVCV()) * t2;
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
