/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelPropaneGas;
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
public class ControlPropaneGas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlPropaneGas(Session session){
        this.session = session;
    }
    
    public void criaTabelaPropaneGas(){
        String csvFile = "src/Csv/PROPANE_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelPropaneGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] Propane_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelPropaneGas(Double.parseDouble(Propane_g[0]), Double.parseDouble(Propane_g[1]), Double.parseDouble(Propane_g[2]), Double.parseDouble(Propane_g[3]), Double.parseDouble(Propane_g[4]), Double.parseDouble(Propane_g[5]), Double.parseDouble(Propane_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelPropaneGas.class);
        //cr = this.session.createCriteria(ModelPropaneGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from Propane_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropaneGas.class));//Sem isso aqui impossível de retornar
	List<ModelPropaneGas> Propanes = consulta.list(); 
        ModelPropaneGas Propane1 = Propanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Propane_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropaneGas.class));//Sem isso aqui impossível de retornar
	Propanes = consulta.list(); 
        ModelPropaneGas Propane2 = Propanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Propane_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropaneGas.class));//Sem isso aqui impossível de retornar
	Propanes = consulta.list(); 
        ModelPropaneGas Propane3 = Propanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Propane_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropaneGas.class));//Sem isso aqui impossível de retornar
	Propanes = consulta.list(); 
        ModelPropaneGas Propane4 = Propanes.get(0);
        
        cpv1 = Propane1.getCPV() + (Propane2.getCPV() - Propane1.getCPV()) * ((temperatura-Propane1.getTEMPERATURA())/(Propane2.getTEMPERATURA()-Propane1.getTEMPERATURA()));
        cpv2 = Propane3.getCPV() + (Propane4.getCPV() - Propane3.getCPV()) * ((temperatura-Propane3.getTEMPERATURA())/(Propane4.getTEMPERATURA()-Propane3.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-Propane1.getPRESSAO())/(Propane3.getPRESSAO()-Propane1.getPRESSAO()));
        
        Prv1 = Propane1.getPRV() + (Propane2.getPRV() - Propane1.getPRV()) * ((temperatura-Propane1.getTEMPERATURA())/(Propane2.getTEMPERATURA()-Propane1.getTEMPERATURA()));
        Prv2 = Propane3.getPRV() + (Propane4.getPRV() - Propane3.getPRV()) * ((temperatura-Propane3.getTEMPERATURA())/(Propane4.getTEMPERATURA()-Propane3.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-Propane1.getPRESSAO())/(Propane3.getPRESSAO()-Propane1.getPRESSAO()));
        
        kv1 = Propane1.getKV() + (Propane2.getKV() - Propane1.getKV()) * ((temperatura-Propane1.getTEMPERATURA())/(Propane2.getTEMPERATURA()-Propane1.getTEMPERATURA()));
        kv2 = Propane3.getKV() + (Propane4.getKV() - Propane3.getKV()) * ((temperatura-Propane3.getTEMPERATURA())/(Propane4.getTEMPERATURA()-Propane3.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-Propane1.getPRESSAO())/(Propane3.getPRESSAO()-Propane1.getPRESSAO()));
        
        Muv1 = Propane1.getMUV() + (Propane2.getMUV() - Propane1.getMUV()) * ((temperatura-Propane1.getTEMPERATURA())/(Propane2.getTEMPERATURA()-Propane1.getTEMPERATURA()));
        Muv2 = Propane3.getMUV() + (Propane4.getMUV() - Propane3.getMUV()) * ((temperatura-Propane3.getTEMPERATURA())/(Propane4.getTEMPERATURA()-Propane3.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-Propane1.getPRESSAO())/(Propane3.getPRESSAO()-Propane1.getPRESSAO()));
        
        Vcv1 = Propane1.getVCV() + (Propane2.getVCV() - Propane1.getVCV()) * ((temperatura-Propane1.getTEMPERATURA())/(Propane2.getTEMPERATURA()-Propane1.getTEMPERATURA()));
        Vcv2 = Propane3.getVCV() + (Propane4.getVCV() - Propane3.getVCV()) * ((temperatura-Propane3.getTEMPERATURA())/(Propane4.getTEMPERATURA()-Propane3.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-Propane1.getPRESSAO())/(Propane3.getPRESSAO()-Propane1.getPRESSAO()));
        
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
