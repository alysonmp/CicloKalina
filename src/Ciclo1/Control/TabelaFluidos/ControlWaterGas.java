/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelWaterGas;
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
public class ControlWaterGas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlWaterGas(Session session){
        this.session = session;
    }
    
    public void criaTabelaWaterGas(){
        String csvFile = "src/Csv/WATER_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelWaterGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] water_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelWaterGas(Double.parseDouble(water_g[0]), Double.parseDouble(water_g[1]), Double.parseDouble(water_g[2]), Double.parseDouble(water_g[3]), Double.parseDouble(water_g[4]), Double.parseDouble(water_g[5]), Double.parseDouble(water_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelWaterGas.class);
        //cr = this.session.createCriteria(ModelwaterGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from water_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelWaterGas.class));//Sem isso aqui impossível de retornar
	List<ModelWaterGas> waters = consulta.list(); 
        ModelWaterGas water1 = waters.get(0);
        
        consulta = this.session.createSQLQuery("select * from water_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelWaterGas.class));//Sem isso aqui impossível de retornar
	waters = consulta.list(); 
        ModelWaterGas water2 = waters.get(0);
        
        consulta = this.session.createSQLQuery("select * from water_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelWaterGas.class));//Sem isso aqui impossível de retornar
	waters = consulta.list(); 
        ModelWaterGas water3 = waters.get(0);
        
        consulta = this.session.createSQLQuery("select * from water_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelWaterGas.class));//Sem isso aqui impossível de retornar
	waters = consulta.list(); 
        ModelWaterGas water4 = waters.get(0);
        
        cpv1 = water1.getCPV() + (water2.getCPV() - water1.getCPV()) * ((temperatura-water1.getTEMPERATURA())/(water2.getTEMPERATURA()-water1.getTEMPERATURA()));
        cpv2 = water3.getCPV() + (water4.getCPV() - water3.getCPV()) * ((temperatura-water3.getTEMPERATURA())/(water4.getTEMPERATURA()-water3.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-water1.getPRESSAO())/(water3.getPRESSAO()-water1.getPRESSAO()));
        
        Prv1 = water1.getPRV() + (water2.getPRV() - water1.getPRV()) * ((temperatura-water1.getTEMPERATURA())/(water2.getTEMPERATURA()-water1.getTEMPERATURA()));
        Prv2 = water3.getPRV() + (water4.getPRV() - water3.getPRV()) * ((temperatura-water3.getTEMPERATURA())/(water4.getTEMPERATURA()-water3.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-water1.getPRESSAO())/(water3.getPRESSAO()-water1.getPRESSAO()));
        
        kv1 = water1.getKV() + (water2.getKV() - water1.getKV()) * ((temperatura-water1.getTEMPERATURA())/(water2.getTEMPERATURA()-water1.getTEMPERATURA()));
        kv2 = water3.getKV() + (water4.getKV() - water3.getKV()) * ((temperatura-water3.getTEMPERATURA())/(water4.getTEMPERATURA()-water3.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-water1.getPRESSAO())/(water3.getPRESSAO()-water1.getPRESSAO()));
        
        Muv1 = water1.getMUV() + (water2.getMUV() - water1.getMUV()) * ((temperatura-water1.getTEMPERATURA())/(water2.getTEMPERATURA()-water1.getTEMPERATURA()));
        Muv2 = water3.getMUV() + (water4.getMUV() - water3.getMUV()) * ((temperatura-water3.getTEMPERATURA())/(water4.getTEMPERATURA()-water3.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-water1.getPRESSAO())/(water3.getPRESSAO()-water1.getPRESSAO()));
        
        Vcv1 = water1.getVCV() + (water2.getVCV() - water1.getVCV()) * ((temperatura-water1.getTEMPERATURA())/(water2.getTEMPERATURA()-water1.getTEMPERATURA()));
        Vcv2 = water3.getVCV() + (water4.getVCV() - water3.getVCV()) * ((temperatura-water3.getTEMPERATURA())/(water4.getTEMPERATURA()-water3.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-water1.getPRESSAO())/(water3.getPRESSAO()-water1.getPRESSAO()));
        
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
