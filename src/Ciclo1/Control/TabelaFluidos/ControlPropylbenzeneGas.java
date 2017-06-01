/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelPropylbenzeneGas;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author leonardo
 */
public class ControlPropylbenzeneGas {
    private Session session;
    private double kv, Cpv, Prv, Muv, Vcv;
    private double kv1, kv2, Cpv1, Cpv2, Prv1, Prv2, Muv1, Muv2, Vcv1, Vcv2;

    public ControlPropylbenzeneGas(Session session) {
        this.session = session;
    }
    
    public void criaTabelaPropylbenzeneGas(){
        String csvFile = "src/Csv/Propylbenzene_gas.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelPropylbenzeneGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] propylbenzene_g = line.split(csvSplitBy);
                    
                    this.session.save(new ModelPropylbenzeneGas(Double.parseDouble(propylbenzene_g[0]),Double.parseDouble(propylbenzene_g[1]),Double.parseDouble(propylbenzene_g[2]),Double.parseDouble(propylbenzene_g[3]),Double.parseDouble(propylbenzene_g[4]),Double.parseDouble(propylbenzene_g[5]),Double.parseDouble(propylbenzene_g[6])));   
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
        Criteria cr = this.session.createCriteria(ModelPropylbenzeneGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from propylbenzene_gas where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropylbenzeneGas.class));
        List<ModelPropylbenzeneGas> propylbenzene_g = consulta.list();
        ModelPropylbenzeneGas propylbenzene_g1 = propylbenzene_g.get(0);
        
        consulta = this.session.createSQLQuery("select * from propylbenzene_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropylbenzeneGas.class));
        propylbenzene_g = consulta.list();
        ModelPropylbenzeneGas propylbenzene_g2 = propylbenzene_g.get(0);
        
        consulta = this.session.createSQLQuery("select * from propylbenzene_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropylbenzeneGas.class));
        propylbenzene_g = consulta.list();
        ModelPropylbenzeneGas propylbenzene_g3 = propylbenzene_g.get(0);
        
        consulta = this.session.createSQLQuery("select * from propylbenzene_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropylbenzeneGas.class));
        propylbenzene_g = consulta.list();
        ModelPropylbenzeneGas propylbenzene_g4 = propylbenzene_g.get(0);
               
        double p = ((pressao - propylbenzene_g1.getPRESSAO())/(propylbenzene_g3.getPRESSAO() - propylbenzene_g1.getPRESSAO()));
        double t1 = ((temperatura - propylbenzene_g1.getTEMPERATURA())/(propylbenzene_g2.getTEMPERATURA() - propylbenzene_g1.getTEMPERATURA()));
        double t2 = ((temperatura - propylbenzene_g3.getTEMPERATURA())/(propylbenzene_g4.getTEMPERATURA() - propylbenzene_g3.getTEMPERATURA()));

        Cpv2 = propylbenzene_g1.getCPV() + (propylbenzene_g2.getCPV() - propylbenzene_g1.getCPV()) * t1;
        Cpv2 = propylbenzene_g3.getCPV() + (propylbenzene_g4.getCPV() - propylbenzene_g3.getCPV()) * t2;
        Cpv = Cpv1 + (Cpv2 - Cpv1) * p;
        
        Prv1 = propylbenzene_g1.getPRV() + (propylbenzene_g2.getPRV() - propylbenzene_g1.getPRV()) * t1;
        Prv2 = propylbenzene_g3.getPRV() + (propylbenzene_g4.getPRV() - propylbenzene_g3.getPRV()) * t2;
        Prv2 = Prv1 + (Prv2 - Prv1) * p;
        
        kv1 = propylbenzene_g1.getKV() + (propylbenzene_g2.getKV() - propylbenzene_g1.getKV()) * t1;
        kv2 = propylbenzene_g3.getKV() + (propylbenzene_g4.getKV() - propylbenzene_g3.getKV()) * t2;
        kv = kv1 + (kv2 - kv1) * p;
        
        Muv1 = propylbenzene_g1.getMUV() + (propylbenzene_g2.getMUV() - propylbenzene_g1.getMUV()) * t1;
        Muv2 = propylbenzene_g3.getMUV() + (propylbenzene_g4.getMUV() - propylbenzene_g3.getMUV()) * t2;
        Muv = Muv1 + (Muv2 - Muv1) * p;
        
        Vcv1 = propylbenzene_g1.getVCV() + (propylbenzene_g2.getVCV() - propylbenzene_g1.getVCV()) * t1;
        Vcv2 = propylbenzene_g3.getVCV() + (propylbenzene_g4.getVCV() - propylbenzene_g3.getVCV()) * t2;
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
