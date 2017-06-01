/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelR142BGas;
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
public class ControlR142BGas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlR142BGas(Session session){
        this.session = session;
    }
    
    public void criaTabelaR142BGas(){
        String csvFile = "src/Csv/R142B_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR142BGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R142B_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelR142BGas(Double.parseDouble(R142B_g[0]), Double.parseDouble(R142B_g[1]), Double.parseDouble(R142B_g[2]), Double.parseDouble(R142B_g[3]), Double.parseDouble(R142B_g[4]), Double.parseDouble(R142B_g[5]), Double.parseDouble(R142B_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelR142BGas.class);
        //cr = this.session.createCriteria(ModelR142BGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R142B_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR142BGas.class));//Sem isso aqui impossível de retornar
	List<ModelR142BGas> R142Bs = consulta.list(); 
        ModelR142BGas R142B1 = R142Bs.get(0);
        
        consulta = this.session.createSQLQuery("select * from R142B_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR142BGas.class));//Sem isso aqui impossível de retornar
	R142Bs = consulta.list(); 
        ModelR142BGas R142B2 = R142Bs.get(0);
        
        consulta = this.session.createSQLQuery("select * from R142B_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR142BGas.class));//Sem isso aqui impossível de retornar
	R142Bs = consulta.list(); 
        ModelR142BGas R142B3 = R142Bs.get(0);
        
        consulta = this.session.createSQLQuery("select * from R142B_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR142BGas.class));//Sem isso aqui impossível de retornar
	R142Bs = consulta.list(); 
        ModelR142BGas R142B4 = R142Bs.get(0);
        
        cpv1 = R142B1.getCPV() + (R142B2.getCPV() - R142B1.getCPV()) * ((temperatura-R142B1.getTEMPERATURA())/(R142B2.getTEMPERATURA()-R142B1.getTEMPERATURA()));
        cpv2 = R142B3.getCPV() + (R142B4.getCPV() - R142B3.getCPV()) * ((temperatura-R142B3.getTEMPERATURA())/(R142B4.getTEMPERATURA()-R142B3.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-R142B1.getPRESSAO())/(R142B3.getPRESSAO()-R142B1.getPRESSAO()));
        
        Prv1 = R142B1.getPRV() + (R142B2.getPRV() - R142B1.getPRV()) * ((temperatura-R142B1.getTEMPERATURA())/(R142B2.getTEMPERATURA()-R142B1.getTEMPERATURA()));
        Prv2 = R142B3.getPRV() + (R142B4.getPRV() - R142B3.getPRV()) * ((temperatura-R142B3.getTEMPERATURA())/(R142B4.getTEMPERATURA()-R142B3.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-R142B1.getPRESSAO())/(R142B3.getPRESSAO()-R142B1.getPRESSAO()));
        
        kv1 = R142B1.getKV() + (R142B2.getKV() - R142B1.getKV()) * ((temperatura-R142B1.getTEMPERATURA())/(R142B2.getTEMPERATURA()-R142B1.getTEMPERATURA()));
        kv2 = R142B3.getKV() + (R142B4.getKV() - R142B3.getKV()) * ((temperatura-R142B3.getTEMPERATURA())/(R142B4.getTEMPERATURA()-R142B3.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-R142B1.getPRESSAO())/(R142B3.getPRESSAO()-R142B1.getPRESSAO()));
        
        Muv1 = R142B1.getMUV() + (R142B2.getMUV() - R142B1.getMUV()) * ((temperatura-R142B1.getTEMPERATURA())/(R142B2.getTEMPERATURA()-R142B1.getTEMPERATURA()));
        Muv2 = R142B3.getMUV() + (R142B4.getMUV() - R142B3.getMUV()) * ((temperatura-R142B3.getTEMPERATURA())/(R142B4.getTEMPERATURA()-R142B3.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-R142B1.getPRESSAO())/(R142B3.getPRESSAO()-R142B1.getPRESSAO()));
        
        Vcv1 = R142B1.getVCV() + (R142B2.getVCV() - R142B1.getVCV()) * ((temperatura-R142B1.getTEMPERATURA())/(R142B2.getTEMPERATURA()-R142B1.getTEMPERATURA()));
        Vcv2 = R142B3.getVCV() + (R142B4.getVCV() - R142B3.getVCV()) * ((temperatura-R142B3.getTEMPERATURA())/(R142B4.getTEMPERATURA()-R142B3.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-R142B1.getPRESSAO())/(R142B3.getPRESSAO()-R142B1.getPRESSAO()));
        
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
