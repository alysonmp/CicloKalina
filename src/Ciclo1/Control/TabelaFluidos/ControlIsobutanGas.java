/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelIsobutanGas;
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
public class ControlIsobutanGas {
    private Session session;
    private double kv, Cpv, Prv, Muv, Vcv;
    private double kv1, kv2, Cpv1, Cpv2, Prv1, Prv2, Muv1, Muv2, Vcv1, Vcv2;

    public ControlIsobutanGas(Session session) {
        this.session = session;
    }
    
    public void criaTabelaIsobutanGas(){
        String csvFile = "src/Csv/ISOBUTAN_gas.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelIsobutanGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] isobutan_gas = line.split(csvSplitBy);
                    
                    this.session.save(new ModelIsobutanGas(Double.parseDouble(isobutan_gas[0]),Double.parseDouble(isobutan_gas[1]),Double.parseDouble(isobutan_gas[2]),Double.parseDouble(isobutan_gas[3]),Double.parseDouble(isobutan_gas[4]),Double.parseDouble(isobutan_gas[5]), Double.parseDouble(isobutan_gas[6])));   
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
        Criteria cr = this.session.createCriteria(ModelIsobutanGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from isobutan_gas where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIsobutanGas.class));
        List<ModelIsobutanGas> isobutan_gas = consulta.list();
        ModelIsobutanGas isobutan_gas1 = isobutan_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from isobutan_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIsobutanGas.class));
        isobutan_gas = consulta.list();
        ModelIsobutanGas isobutan_gas2 = isobutan_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from isobutan_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIsobutanGas.class));
        isobutan_gas = consulta.list();
        ModelIsobutanGas isobutan_gas3 = isobutan_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from isobutan_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIsobutanGas.class));
        isobutan_gas = consulta.list();
        ModelIsobutanGas isobutan_gas4 = isobutan_gas.get(0);
        
        double p = ((pressao - isobutan_gas1.getPRESSAO())/(isobutan_gas3.getPRESSAO() - isobutan_gas1.getPRESSAO()));
        double t1 = ((temperatura - isobutan_gas1.getTEMPERATURA())/(isobutan_gas2.getTEMPERATURA() - isobutan_gas1.getTEMPERATURA()));
        double t2 = ((temperatura - isobutan_gas3.getTEMPERATURA())/(isobutan_gas4.getTEMPERATURA() - isobutan_gas3.getTEMPERATURA()));

        Cpv2 = isobutan_gas1.getCPV() + (isobutan_gas2.getCPV() - isobutan_gas1.getCPV()) * t1;
        Cpv2 = isobutan_gas3.getCPV() + (isobutan_gas4.getCPV() - isobutan_gas3.getCPV()) * t2;
        Cpv = Cpv1 + (Cpv2 - Cpv1) * p;
        
        Prv1 = isobutan_gas1.getPRV() + (isobutan_gas2.getPRV() - isobutan_gas1.getPRV()) * t1;
        Prv2 = isobutan_gas3.getPRV() + (isobutan_gas4.getPRV() - isobutan_gas3.getPRV()) * t2;
        Prv2 = Prv1 + (Prv2 - Prv1) * p;
        
        kv1 = isobutan_gas1.getKV() + (isobutan_gas2.getKV() - isobutan_gas1.getKV()) * t1;
        kv2 = isobutan_gas3.getKV() + (isobutan_gas4.getKV() - isobutan_gas3.getKV()) * t2;
        kv = kv1 + (kv2 - kv1) * p;
        
        Muv1 = isobutan_gas1.getMUV() + (isobutan_gas2.getMUV() - isobutan_gas1.getMUV()) * t1;
        Muv2 = isobutan_gas3.getMUV() + (isobutan_gas4.getMUV() - isobutan_gas3.getMUV()) * t2;
        Muv = Muv1 + (Muv2 - Muv1) * p;
        
        Vcv1 = isobutan_gas1.getVCV() + (isobutan_gas2.getVCV() - isobutan_gas1.getVCV()) * t1;
        Vcv2 = isobutan_gas3.getVCV() + (isobutan_gas4.getVCV() - isobutan_gas3.getVCV()) * t2;
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
