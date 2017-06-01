/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelR152AGas;
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
public class ControlR152AGas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlR152AGas(Session session){
        this.session = session;
    }
    
    public void criaTabelaR152AGas(){
        String csvFile = "src/Csv/R152A_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR152AGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R152A_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelR152AGas(Double.parseDouble(R152A_g[0]), Double.parseDouble(R152A_g[1]), Double.parseDouble(R152A_g[2]), Double.parseDouble(R152A_g[3]), Double.parseDouble(R152A_g[4]), Double.parseDouble(R152A_g[5]), Double.parseDouble(R152A_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelR152AGas.class);
        //cr = this.session.createCriteria(ModelR152AGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R152A_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR152AGas.class));//Sem isso aqui impossível de retornar
	List<ModelR152AGas> R152As = consulta.list(); 
        ModelR152AGas R152A1 = R152As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R152A_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR152AGas.class));//Sem isso aqui impossível de retornar
	R152As = consulta.list(); 
        ModelR152AGas R152A2 = R152As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R152A_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR152AGas.class));//Sem isso aqui impossível de retornar
	R152As = consulta.list(); 
        ModelR152AGas R152A3 = R152As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R152A_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR152AGas.class));//Sem isso aqui impossível de retornar
	R152As = consulta.list(); 
        ModelR152AGas R152A4 = R152As.get(0);
        
        cpv1 = R152A1.getCPV() + (R152A2.getCPV() - R152A1.getCPV()) * ((temperatura-R152A1.getTEMPERATURA())/(R152A2.getTEMPERATURA()-R152A1.getTEMPERATURA()));
        cpv2 = R152A3.getCPV() + (R152A4.getCPV() - R152A3.getCPV()) * ((temperatura-R152A3.getTEMPERATURA())/(R152A4.getTEMPERATURA()-R152A3.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-R152A1.getPRESSAO())/(R152A3.getPRESSAO()-R152A1.getPRESSAO()));
        
        Prv1 = R152A1.getPRV() + (R152A2.getPRV() - R152A1.getPRV()) * ((temperatura-R152A1.getTEMPERATURA())/(R152A2.getTEMPERATURA()-R152A1.getTEMPERATURA()));
        Prv2 = R152A3.getPRV() + (R152A4.getPRV() - R152A3.getPRV()) * ((temperatura-R152A3.getTEMPERATURA())/(R152A4.getTEMPERATURA()-R152A3.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-R152A1.getPRESSAO())/(R152A3.getPRESSAO()-R152A1.getPRESSAO()));
        
        kv1 = R152A1.getKV() + (R152A2.getKV() - R152A1.getKV()) * ((temperatura-R152A1.getTEMPERATURA())/(R152A2.getTEMPERATURA()-R152A1.getTEMPERATURA()));
        kv2 = R152A3.getKV() + (R152A4.getKV() - R152A3.getKV()) * ((temperatura-R152A3.getTEMPERATURA())/(R152A4.getTEMPERATURA()-R152A3.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-R152A1.getPRESSAO())/(R152A3.getPRESSAO()-R152A1.getPRESSAO()));
        
        Muv1 = R152A1.getMUV() + (R152A2.getMUV() - R152A1.getMUV()) * ((temperatura-R152A1.getTEMPERATURA())/(R152A2.getTEMPERATURA()-R152A1.getTEMPERATURA()));
        Muv2 = R152A3.getMUV() + (R152A4.getMUV() - R152A3.getMUV()) * ((temperatura-R152A3.getTEMPERATURA())/(R152A4.getTEMPERATURA()-R152A3.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-R152A1.getPRESSAO())/(R152A3.getPRESSAO()-R152A1.getPRESSAO()));
        
        Vcv1 = R152A1.getVCV() + (R152A2.getVCV() - R152A1.getVCV()) * ((temperatura-R152A1.getTEMPERATURA())/(R152A2.getTEMPERATURA()-R152A1.getTEMPERATURA()));
        Vcv2 = R152A3.getVCV() + (R152A4.getVCV() - R152A3.getVCV()) * ((temperatura-R152A3.getTEMPERATURA())/(R152A4.getTEMPERATURA()-R152A3.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-R152A1.getPRESSAO())/(R152A3.getPRESSAO()-R152A1.getPRESSAO()));
        
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
