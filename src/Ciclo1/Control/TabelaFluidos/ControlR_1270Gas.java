/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelR_1270Gas;
import Ciclo1.Model.TabelasFluidos.ModelR_1270Gas;
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
public class ControlR_1270Gas {
    private Session session;
    private double kv, Cpv, Prv, Muv, Vcv;
    private double kv1, kv2, Cpv1, Cpv2, Prv1, Prv2, Muv1, Muv2, Vcv1, Vcv2;

    public ControlR_1270Gas(Session session) {
        this.session = session;
    }
    
    public void criaTabelaR_1270Gas(){
        String csvFile = "src/Csv/R-1270_gas.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelR_1270Gas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] r_1270_g = line.split(csvSplitBy);
                    
                    this.session.save(new ModelR_1270Gas(Double.parseDouble(r_1270_g[0]),Double.parseDouble(r_1270_g[1]),Double.parseDouble(r_1270_g[2]),Double.parseDouble(r_1270_g[3]),Double.parseDouble(r_1270_g[4]),Double.parseDouble(r_1270_g[5]),Double.parseDouble(r_1270_g[6])));   
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
        Criteria cr = this.session.createCriteria(ModelR_1270Gas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from r_1270_gas where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR_1270Gas.class));
        List<ModelR_1270Gas> r_1270_g = consulta.list();
        ModelR_1270Gas r_1270_g1 = r_1270_g.get(0);
        
        consulta = this.session.createSQLQuery("select * from r_1270_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR_1270Gas.class));
        r_1270_g = consulta.list();
        ModelR_1270Gas r_1270_g2 = r_1270_g.get(0);
        
        consulta = this.session.createSQLQuery("select * from r_1270_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR_1270Gas.class));
        r_1270_g = consulta.list();
        ModelR_1270Gas r_1270_g3 = r_1270_g.get(0);
        
        consulta = this.session.createSQLQuery("select * from r_1270_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR_1270Gas.class));
        r_1270_g = consulta.list();
        ModelR_1270Gas r_1270_g4 = r_1270_g.get(0);
        
        
        
        double p = ((pressao - r_1270_g1.getPRESSAO())/(r_1270_g3.getPRESSAO() - r_1270_g1.getPRESSAO()));
        double t1 = ((temperatura - r_1270_g1.getTEMPERATURA())/(r_1270_g2.getTEMPERATURA() - r_1270_g1.getTEMPERATURA()));
        double t2 = ((temperatura - r_1270_g3.getTEMPERATURA())/(r_1270_g4.getTEMPERATURA() - r_1270_g3.getTEMPERATURA()));
        
        Cpv2 = r_1270_g1.getCPV() + (r_1270_g2.getCPV() - r_1270_g1.getCPV()) * t1;
        Cpv2 = r_1270_g3.getCPV() + (r_1270_g4.getCPV() - r_1270_g3.getCPV()) * t2;
        Cpv = Cpv1 + (Cpv2 - Cpv1) * p;
        
        Prv1 = r_1270_g1.getPRV() + (r_1270_g2.getPRV() - r_1270_g1.getPRV()) * t1;
        Prv2 = r_1270_g3.getPRV() + (r_1270_g4.getPRV() - r_1270_g3.getPRV()) * t2;
        Prv2 = Prv1 + (Prv2 - Prv1) * p;
        
        kv1 = r_1270_g1.getKV() + (r_1270_g2.getKV() - r_1270_g1.getKV()) * t1;
        kv2 = r_1270_g3.getKV() + (r_1270_g4.getKV() - r_1270_g3.getKV()) * t2;
        kv = kv1 + (kv2 - kv1) * p;
        
        Muv1 = r_1270_g1.getMUV() + (r_1270_g2.getMUV() - r_1270_g1.getMUV()) * t1;
        Muv2 = r_1270_g3.getMUV() + (r_1270_g4.getMUV() - r_1270_g3.getMUV()) * t2;
        Muv = Muv1 + (Muv2 - Muv1) * p;
        
        Vcv1 = r_1270_g1.getVCV() + (r_1270_g2.getVCV() - r_1270_g1.getVCV()) * t1;
        Vcv2 = r_1270_g3.getVCV() + (r_1270_g4.getVCV() - r_1270_g3.getVCV()) * t2;
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
