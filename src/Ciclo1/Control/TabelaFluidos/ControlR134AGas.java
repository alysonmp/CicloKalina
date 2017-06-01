/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelR134AGas;
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
public class ControlR134AGas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlR134AGas(Session session){
        this.session = session;
    }
    
    public void criaTabelaR134AGas(){
        String csvFile = "src/Csv/R134A_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR134AGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R134A_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelR134AGas(Double.parseDouble(R134A_g[0]), Double.parseDouble(R134A_g[1]), Double.parseDouble(R134A_g[2]), Double.parseDouble(R134A_g[3]), Double.parseDouble(R134A_g[4]), Double.parseDouble(R134A_g[5]), Double.parseDouble(R134A_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelR134AGas.class);
        //cr = this.session.createCriteria(ModelR134AGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R134A_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR134AGas.class));//Sem isso aqui impossível de retornar
	List<ModelR134AGas> R134As = consulta.list(); 
        ModelR134AGas R134A1 = R134As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R134A_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR134AGas.class));//Sem isso aqui impossível de retornar
	R134As = consulta.list(); 
        ModelR134AGas R134A2 = R134As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R134A_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR134AGas.class));//Sem isso aqui impossível de retornar
	R134As = consulta.list(); 
        ModelR134AGas R134A3 = R134As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R134A_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR134AGas.class));//Sem isso aqui impossível de retornar
	R134As = consulta.list(); 
        ModelR134AGas R134A4 = R134As.get(0);
        
        cpv1 = R134A1.getCPV() + (R134A2.getCPV() - R134A1.getCPV()) * ((temperatura-R134A1.getTEMPERATURA())/(R134A2.getTEMPERATURA()-R134A1.getTEMPERATURA()));
        cpv2 = R134A3.getCPV() + (R134A4.getCPV() - R134A3.getCPV()) * ((temperatura-R134A3.getTEMPERATURA())/(R134A4.getTEMPERATURA()-R134A3.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-R134A1.getPRESSAO())/(R134A3.getPRESSAO()-R134A1.getPRESSAO()));
        
        Prv1 = R134A1.getPRV() + (R134A2.getPRV() - R134A1.getPRV()) * ((temperatura-R134A1.getTEMPERATURA())/(R134A2.getTEMPERATURA()-R134A1.getTEMPERATURA()));
        Prv2 = R134A3.getPRV() + (R134A4.getPRV() - R134A3.getPRV()) * ((temperatura-R134A3.getTEMPERATURA())/(R134A4.getTEMPERATURA()-R134A3.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-R134A1.getPRESSAO())/(R134A3.getPRESSAO()-R134A1.getPRESSAO()));
        
        kv1 = R134A1.getKV() + (R134A2.getKV() - R134A1.getKV()) * ((temperatura-R134A1.getTEMPERATURA())/(R134A2.getTEMPERATURA()-R134A1.getTEMPERATURA()));
        kv2 = R134A3.getKV() + (R134A4.getKV() - R134A3.getKV()) * ((temperatura-R134A3.getTEMPERATURA())/(R134A4.getTEMPERATURA()-R134A3.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-R134A1.getPRESSAO())/(R134A3.getPRESSAO()-R134A1.getPRESSAO()));
        
        Muv1 = R134A1.getMUV() + (R134A2.getMUV() - R134A1.getMUV()) * ((temperatura-R134A1.getTEMPERATURA())/(R134A2.getTEMPERATURA()-R134A1.getTEMPERATURA()));
        Muv2 = R134A3.getMUV() + (R134A4.getMUV() - R134A3.getMUV()) * ((temperatura-R134A3.getTEMPERATURA())/(R134A4.getTEMPERATURA()-R134A3.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-R134A1.getPRESSAO())/(R134A3.getPRESSAO()-R134A1.getPRESSAO()));
        
        Vcv1 = R134A1.getVCV() + (R134A2.getVCV() - R134A1.getVCV()) * ((temperatura-R134A1.getTEMPERATURA())/(R134A2.getTEMPERATURA()-R134A1.getTEMPERATURA()));
        Vcv2 = R134A3.getVCV() + (R134A4.getVCV() - R134A3.getVCV()) * ((temperatura-R134A3.getTEMPERATURA())/(R134A4.getTEMPERATURA()-R134A3.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-R134A1.getPRESSAO())/(R134A3.getPRESSAO()-R134A1.getPRESSAO()));
        
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
