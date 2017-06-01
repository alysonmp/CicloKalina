/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelPentaneGas;
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
public class ControlPentaneGas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlPentaneGas(Session session){
        this.session = session;
    }
    
    public void criaTabelaPentaneGas(){
        String csvFile = "src/Csv/PENTANE_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelPentaneGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] Pentane_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelPentaneGas(Double.parseDouble(Pentane_g[0]), Double.parseDouble(Pentane_g[1]), Double.parseDouble(Pentane_g[2]), Double.parseDouble(Pentane_g[3]), Double.parseDouble(Pentane_g[4]), Double.parseDouble(Pentane_g[5]), Double.parseDouble(Pentane_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelPentaneGas.class);
        //cr = this.session.createCriteria(ModelPentaneGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from Pentane_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPentaneGas.class));//Sem isso aqui impossível de retornar
	List<ModelPentaneGas> Pentanes = consulta.list(); 
        ModelPentaneGas Pentane1 = Pentanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Pentane_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPentaneGas.class));//Sem isso aqui impossível de retornar
	Pentanes = consulta.list(); 
        ModelPentaneGas Pentane2 = Pentanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Pentane_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPentaneGas.class));//Sem isso aqui impossível de retornar
	Pentanes = consulta.list(); 
        ModelPentaneGas Pentane3 = Pentanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Pentane_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPentaneGas.class));//Sem isso aqui impossível de retornar
	Pentanes = consulta.list(); 
        ModelPentaneGas Pentane4 = Pentanes.get(0);
        
        cpv1 = Pentane1.getCPV() + (Pentane2.getCPV() - Pentane1.getCPV()) * ((temperatura-Pentane1.getTEMPERATURA())/(Pentane2.getTEMPERATURA()-Pentane1.getTEMPERATURA()));
        cpv2 = Pentane3.getCPV() + (Pentane4.getCPV() - Pentane3.getCPV()) * ((temperatura-Pentane3.getTEMPERATURA())/(Pentane4.getTEMPERATURA()-Pentane3.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-Pentane1.getPRESSAO())/(Pentane3.getPRESSAO()-Pentane1.getPRESSAO()));
        
        Prv1 = Pentane1.getPRV() + (Pentane2.getPRV() - Pentane1.getPRV()) * ((temperatura-Pentane1.getTEMPERATURA())/(Pentane2.getTEMPERATURA()-Pentane1.getTEMPERATURA()));
        Prv2 = Pentane3.getPRV() + (Pentane4.getPRV() - Pentane3.getPRV()) * ((temperatura-Pentane3.getTEMPERATURA())/(Pentane4.getTEMPERATURA()-Pentane3.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-Pentane1.getPRESSAO())/(Pentane3.getPRESSAO()-Pentane1.getPRESSAO()));
        
        kv1 = Pentane1.getKV() + (Pentane2.getKV() - Pentane1.getKV()) * ((temperatura-Pentane1.getTEMPERATURA())/(Pentane2.getTEMPERATURA()-Pentane1.getTEMPERATURA()));
        kv2 = Pentane3.getKV() + (Pentane4.getKV() - Pentane3.getKV()) * ((temperatura-Pentane3.getTEMPERATURA())/(Pentane4.getTEMPERATURA()-Pentane3.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-Pentane1.getPRESSAO())/(Pentane3.getPRESSAO()-Pentane1.getPRESSAO()));
        
        Muv1 = Pentane1.getMUV() + (Pentane2.getMUV() - Pentane1.getMUV()) * ((temperatura-Pentane1.getTEMPERATURA())/(Pentane2.getTEMPERATURA()-Pentane1.getTEMPERATURA()));
        Muv2 = Pentane3.getMUV() + (Pentane4.getMUV() - Pentane3.getMUV()) * ((temperatura-Pentane3.getTEMPERATURA())/(Pentane4.getTEMPERATURA()-Pentane3.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-Pentane1.getPRESSAO())/(Pentane3.getPRESSAO()-Pentane1.getPRESSAO()));
        
        Vcv1 = Pentane1.getVCV() + (Pentane2.getVCV() - Pentane1.getVCV()) * ((temperatura-Pentane1.getTEMPERATURA())/(Pentane2.getTEMPERATURA()-Pentane1.getTEMPERATURA()));
        Vcv2 = Pentane3.getVCV() + (Pentane4.getVCV() - Pentane3.getVCV()) * ((temperatura-Pentane3.getTEMPERATURA())/(Pentane4.getTEMPERATURA()-Pentane3.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-Pentane1.getPRESSAO())/(Pentane3.getPRESSAO()-Pentane1.getPRESSAO()));
        
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
