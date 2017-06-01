/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelDimetylbenzeneGas;
import Ciclo1.Model.TabelasFluidos.ModelDimetylbenzeneGas;
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
public class ControlDimetylbenzeneGas {
    private Session session;
    private double kv, Cpv, Prv, Muv, Vcv;
    private double kv1, kv2, Cpv1, Cpv2, Prv1, Prv2, Muv1, Muv2, Vcv1, Vcv2;

    public ControlDimetylbenzeneGas(Session session) {
        this.session = session;
    }
    
    public void criaTabelaDimetylbenzeneGas(){
        String csvFile = "src/Csv/Dimetylbenzene_gas.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelDimetylbenzeneGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] dimetylbenzene_g = line.split(csvSplitBy);
                    
                    this.session.save(new ModelDimetylbenzeneGas(Double.parseDouble(dimetylbenzene_g[0]),Double.parseDouble(dimetylbenzene_g[1]),Double.parseDouble(dimetylbenzene_g[2]),Double.parseDouble(dimetylbenzene_g[3]),Double.parseDouble(dimetylbenzene_g[4]),Double.parseDouble(dimetylbenzene_g[5]),Double.parseDouble(dimetylbenzene_g[6])));   
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
        Criteria cr = this.session.createCriteria(ModelDimetylbenzeneGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from dimetylbenzene_gas where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelDimetylbenzeneGas.class));
        List<ModelDimetylbenzeneGas> dimetylbenzene_g = consulta.list();
        ModelDimetylbenzeneGas dimetylbenzene_g1 = dimetylbenzene_g.get(0);
        
        consulta = this.session.createSQLQuery("select * from dimetylbenzene_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelDimetylbenzeneGas.class));
        dimetylbenzene_g = consulta.list();
        ModelDimetylbenzeneGas dimetylbenzene_g2 = dimetylbenzene_g.get(0);
        
        consulta = this.session.createSQLQuery("select * from dimetylbenzene_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelDimetylbenzeneGas.class));
        dimetylbenzene_g = consulta.list();
        ModelDimetylbenzeneGas dimetylbenzene_g3 = dimetylbenzene_g.get(0);
        
        consulta = this.session.createSQLQuery("select * from dimetylbenzene_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelDimetylbenzeneGas.class));
        dimetylbenzene_g = consulta.list();
        ModelDimetylbenzeneGas dimetylbenzene_g4 = dimetylbenzene_g.get(0);
        
        double p = ((pressao - dimetylbenzene_g1.getPRESSAO())/(dimetylbenzene_g3.getPRESSAO() - dimetylbenzene_g1.getPRESSAO()));
        double t1 = ((temperatura - dimetylbenzene_g1.getTEMPERATURA())/(dimetylbenzene_g2.getTEMPERATURA() - dimetylbenzene_g1.getTEMPERATURA()));
        double t2 = ((temperatura - dimetylbenzene_g3.getTEMPERATURA())/(dimetylbenzene_g4.getTEMPERATURA() - dimetylbenzene_g3.getTEMPERATURA()));

        Cpv2 = dimetylbenzene_g1.getCPV() + (dimetylbenzene_g2.getCPV() - dimetylbenzene_g1.getCPV()) * t1;
        Cpv2 = dimetylbenzene_g3.getCPV() + (dimetylbenzene_g4.getCPV() - dimetylbenzene_g3.getCPV()) * t2;
        Cpv = Cpv1 + (Cpv2 - Cpv1) * p;
        
        Prv1 = dimetylbenzene_g1.getPRV() + (dimetylbenzene_g2.getPRV() - dimetylbenzene_g1.getPRV()) * t1;
        Prv2 = dimetylbenzene_g3.getPRV() + (dimetylbenzene_g4.getPRV() - dimetylbenzene_g3.getPRV()) * t2;
        Prv2 = Prv1 + (Prv2 - Prv1) * p;
        
        kv1 = dimetylbenzene_g1.getKV() + (dimetylbenzene_g2.getKV() - dimetylbenzene_g1.getKV()) * t1;
        kv2 = dimetylbenzene_g3.getKV() + (dimetylbenzene_g4.getKV() - dimetylbenzene_g3.getKV()) * t2;
        kv = kv1 + (kv2 - kv1) * p;
        
        Muv1 = dimetylbenzene_g1.getMUV() + (dimetylbenzene_g2.getMUV() - dimetylbenzene_g1.getMUV()) * t1;
        Muv2 = dimetylbenzene_g3.getMUV() + (dimetylbenzene_g4.getMUV() - dimetylbenzene_g3.getMUV()) * t2;
        Muv = Muv1 + (Muv2 - Muv1) * p;
        
        Vcv1 = dimetylbenzene_g1.getVCV() + (dimetylbenzene_g2.getVCV() - dimetylbenzene_g1.getVCV()) * t1;
        Vcv2 = dimetylbenzene_g3.getVCV() + (dimetylbenzene_g4.getVCV() - dimetylbenzene_g3.getVCV()) * t2;
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
