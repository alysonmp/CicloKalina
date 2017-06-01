package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelButanoGas;
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
public class ControlButanoGas {
    
    private Session session;
    private double kv, Cpv, Prv, Muv, Vcv;
    private double kv1, kv2, Cpv1, Cpv2, Prv1, Prv2, Muv1, Muv2, Vcv1, Vcv2;

    public ControlButanoGas(Session session) {
        this.session = session;
    }
    
    public void criaTabelaButanoGas(){
        String csvFile = "src/Csv/Butane_gas.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelButanoGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] butano_g = line.split(csvSplitBy);
                    
                    this.session.save(new ModelButanoGas(Double.parseDouble(butano_g[0]),Double.parseDouble(butano_g[1]),Double.parseDouble(butano_g[2]),Double.parseDouble(butano_g[3]),Double.parseDouble(butano_g[4]),Double.parseDouble(butano_g[5]),Double.parseDouble(butano_g[6])));   
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
        Criteria cr = this.session.createCriteria(ModelButanoGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from butano_gas where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelButanoGas.class));
        List<ModelButanoGas> butano_gas = consulta.list();
        ModelButanoGas butano_gas1 = butano_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from butano_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelButanoGas.class));
        butano_gas = consulta.list();
        ModelButanoGas butano_gas2 = butano_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from butano_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelButanoGas.class));
        butano_gas = consulta.list();
        ModelButanoGas butano_gas3 = butano_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from butano_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelButanoGas.class));
        butano_gas = consulta.list();
        ModelButanoGas butano_gas4 = butano_gas.get(0);
        
        double p = ((pressao - butano_gas1.getPRESSAO())/(butano_gas3.getPRESSAO() - butano_gas1.getPRESSAO()));
        double t1 = ((temperatura - butano_gas1.getTEMPERATURA())/(butano_gas2.getTEMPERATURA() - butano_gas1.getTEMPERATURA()));
        double t2 = ((temperatura - butano_gas3.getTEMPERATURA())/(butano_gas4.getTEMPERATURA() - butano_gas3.getTEMPERATURA()));

        Cpv2 = butano_gas1.getCPV() + (butano_gas2.getCPV() - butano_gas1.getCPV()) * t1;
        Cpv2 = butano_gas3.getCPV() + (butano_gas4.getCPV() - butano_gas3.getCPV()) * t2;
        Cpv = Cpv1 + (Cpv2 - Cpv1) * p;
        
        Prv1 = butano_gas1.getPRV() + (butano_gas2.getPRV() - butano_gas1.getPRV()) * t1;
        Prv2 = butano_gas3.getPRV() + (butano_gas4.getPRV() - butano_gas3.getPRV()) * t2;
        Prv2 = Prv1 + (Prv2 - Prv1) * p;
        
        kv1 = butano_gas1.getKV() + (butano_gas2.getKV() - butano_gas1.getKV()) * t1;
        kv2 = butano_gas3.getKV() + (butano_gas4.getKV() - butano_gas3.getKV()) * t2;
        kv = kv1 + (kv2 - kv1) * p;
        
        Muv1 = butano_gas1.getMUV() + (butano_gas2.getMUV() - butano_gas1.getMUV()) * t1;
        Muv2 = butano_gas3.getMUV() + (butano_gas4.getMUV() - butano_gas3.getMUV()) * t2;
        Muv = Muv1 + (Muv2 - Muv1) * p;
        
        Vcv1 = butano_gas1.getVCV() + (butano_gas2.getVCV() - butano_gas1.getVCV()) * t1;
        Vcv2 = butano_gas3.getVCV() + (butano_gas4.getVCV() - butano_gas3.getVCV()) * t2;
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
