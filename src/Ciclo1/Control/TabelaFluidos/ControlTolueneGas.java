/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelTolueneGas;
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
public class ControlTolueneGas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlTolueneGas(Session session){
        this.session = session;
    }
    
    public void criaTabelaTolueneGas(){
        String csvFile = "src/Csv/TOLUENE_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelTolueneGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] Toluene_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelTolueneGas(Double.parseDouble(Toluene_g[0]), Double.parseDouble(Toluene_g[1]), Double.parseDouble(Toluene_g[2]), Double.parseDouble(Toluene_g[3]), Double.parseDouble(Toluene_g[4]), Double.parseDouble(Toluene_g[5]), Double.parseDouble(Toluene_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelTolueneGas.class);
        //cr = this.session.createCriteria(ModelTolueneGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from Toluene_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelTolueneGas.class));//Sem isso aqui impossível de retornar
	List<ModelTolueneGas> Toluenes = consulta.list(); 
        ModelTolueneGas Toluene1 = Toluenes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Toluene_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelTolueneGas.class));//Sem isso aqui impossível de retornar
	Toluenes = consulta.list(); 
        ModelTolueneGas Toluene2 = Toluenes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Toluene_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelTolueneGas.class));//Sem isso aqui impossível de retornar
	Toluenes = consulta.list(); 
        ModelTolueneGas Toluene3 = Toluenes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Toluene_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelTolueneGas.class));//Sem isso aqui impossível de retornar
	Toluenes = consulta.list(); 
        ModelTolueneGas Toluene4 = Toluenes.get(0);
        
        cpv1 = Toluene1.getCPV() + (Toluene2.getCPV() - Toluene1.getCPV()) * ((temperatura-Toluene1.getTEMPERATURA())/(Toluene2.getTEMPERATURA()-Toluene1.getTEMPERATURA()));
        cpv2 = Toluene3.getCPV() + (Toluene4.getCPV() - Toluene3.getCPV()) * ((temperatura-Toluene3.getTEMPERATURA())/(Toluene4.getTEMPERATURA()-Toluene3.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-Toluene1.getPRESSAO())/(Toluene3.getPRESSAO()-Toluene1.getPRESSAO()));
        
        Prv1 = Toluene1.getPRV() + (Toluene2.getPRV() - Toluene1.getPRV()) * ((temperatura-Toluene1.getTEMPERATURA())/(Toluene2.getTEMPERATURA()-Toluene1.getTEMPERATURA()));
        Prv2 = Toluene3.getPRV() + (Toluene4.getPRV() - Toluene3.getPRV()) * ((temperatura-Toluene3.getTEMPERATURA())/(Toluene4.getTEMPERATURA()-Toluene3.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-Toluene1.getPRESSAO())/(Toluene3.getPRESSAO()-Toluene1.getPRESSAO()));
        
        kv1 = Toluene1.getKV() + (Toluene2.getKV() - Toluene1.getKV()) * ((temperatura-Toluene1.getTEMPERATURA())/(Toluene2.getTEMPERATURA()-Toluene1.getTEMPERATURA()));
        kv2 = Toluene3.getKV() + (Toluene4.getKV() - Toluene3.getKV()) * ((temperatura-Toluene3.getTEMPERATURA())/(Toluene4.getTEMPERATURA()-Toluene3.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-Toluene1.getPRESSAO())/(Toluene3.getPRESSAO()-Toluene1.getPRESSAO()));
        
        Muv1 = Toluene1.getMUV() + (Toluene2.getMUV() - Toluene1.getMUV()) * ((temperatura-Toluene1.getTEMPERATURA())/(Toluene2.getTEMPERATURA()-Toluene1.getTEMPERATURA()));
        Muv2 = Toluene3.getMUV() + (Toluene4.getMUV() - Toluene3.getMUV()) * ((temperatura-Toluene3.getTEMPERATURA())/(Toluene4.getTEMPERATURA()-Toluene3.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-Toluene1.getPRESSAO())/(Toluene3.getPRESSAO()-Toluene1.getPRESSAO()));
        
        Vcv1 = Toluene1.getVCV() + (Toluene2.getVCV() - Toluene1.getVCV()) * ((temperatura-Toluene1.getTEMPERATURA())/(Toluene2.getTEMPERATURA()-Toluene1.getTEMPERATURA()));
        Vcv2 = Toluene3.getVCV() + (Toluene4.getVCV() - Toluene3.getVCV()) * ((temperatura-Toluene3.getTEMPERATURA())/(Toluene4.getTEMPERATURA()-Toluene3.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-Toluene1.getPRESSAO())/(Toluene3.getPRESSAO()-Toluene1.getPRESSAO()));
        
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
