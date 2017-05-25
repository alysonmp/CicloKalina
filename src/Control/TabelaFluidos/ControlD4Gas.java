/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelD4Gas;
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
public class ControlD4Gas {
    private Session session;
    private double kv, Cpv, Prv, Muv, Vcv;
    private double kv1, kv2, Cpv1, Cpv2, Prv1, Prv2, Muv1, Muv2, Vcv1, Vcv2;

    public ControlD4Gas(Session session) {
        this.session = session;
    }
    
    public void criaTabelaD4Gas(){
        String csvFile = "src/Csv/D4_gas.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelD4Gas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                Transaction tx = session.beginTransaction();
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] g4_gas = line.split(csvSplitBy);
                    
                    this.session.save(new ModelD4Gas(Double.parseDouble(g4_gas[0]),Double.parseDouble(g4_gas[1]),Double.parseDouble(g4_gas[2]),Double.parseDouble(g4_gas[3]),Double.parseDouble(g4_gas[4]),Double.parseDouble(g4_gas[5])));   
                }
                tx.commit();
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
    
    public void interpolacaoD4Gas(double pressao,double temperatura){
    
        Criteria cr = this.session.createCriteria(ModelD4Gas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from d4_gas where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD4Gas.class));
        List<ModelD4Gas> d4_gas = consulta.list();
        ModelD4Gas d4_gas1 = d4_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from d4_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD4Gas.class));
        d4_gas = consulta.list();
        ModelD4Gas d4_gas2 = d4_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from d4_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD4Gas.class));
        d4_gas = consulta.list();
        ModelD4Gas d4_gas3 = d4_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from d4_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD4Gas.class));
        d4_gas = consulta.list();
        ModelD4Gas d4_gas4 = d4_gas.get(0);
        
        double p = ((pressao - d4_gas1.getPRESSAO())/(d4_gas3.getPRESSAO() - d4_gas1.getPRESSAO()));
        double t1 = ((temperatura - d4_gas1.getTEMPERATURA())/(d4_gas2.getTEMPERATURA() - d4_gas1.getTEMPERATURA()));
        double t2 = ((temperatura - d4_gas3.getTEMPERATURA())/(d4_gas4.getTEMPERATURA() - d4_gas3.getTEMPERATURA()));

        Cpv2 = d4_gas1.getCPV() + (d4_gas2.getCPV() - d4_gas1.getCPV()) * t1;
        Cpv2 = d4_gas3.getCPV() + (d4_gas4.getCPV() - d4_gas3.getCPV()) * t2;
        Cpv = Cpv2 + (Cpv2 - Cpv2) * p;
        
        Prv1 = d4_gas1.getPRV() + (d4_gas2.getPRV() - d4_gas1.getPRV()) * t1;
        Prv2 = d4_gas3.getPRV() + (d4_gas4.getPRV() - d4_gas3.getPRV()) * t2;
        Prv2 = Prv1 + (Prv2 - Prv1) * p;
        
        kv1 = d4_gas1.getKV() + (d4_gas2.getKV() - d4_gas1.getKV()) * t1;
        kv2 = d4_gas3.getKV() + (d4_gas4.getKV() - d4_gas3.getKV()) * t2;
        kv = kv1 + (kv2 - kv1) * p;
        
        Muv1 = d4_gas1.getMUV() + (d4_gas2.getMUV() - d4_gas1.getMUV()) * t1;
        Muv2 = d4_gas3.getMUV() + (d4_gas4.getMUV() - d4_gas3.getMUV()) * t2;
        Muv = Muv1 + (Muv2 - Muv1) * p;
        
        Vcv1 = d4_gas1.getVCV() + (d4_gas2.getVCV() - d4_gas1.getVCV()) * t1;
        Vcv2 = d4_gas3.getVCV() + (d4_gas4.getVCV() - d4_gas3.getVCV()) * t2;
        Vcv = Vcv1 + (Vcv2 - Vcv1) * p;
    }
}
