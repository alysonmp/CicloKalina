/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelMD4MGas;
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
public class ControlMD4MGas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlMD4MGas(Session session){
        this.session = session;
    }
    
    public void criaTabelaMD4MGas(){
        String csvFile = "src/Csv/MD4M_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelMD4MGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use coMD4Ma as separator
                    String[] MD4M_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelMD4MGas(Double.parseDouble(MD4M_g[0]), Double.parseDouble(MD4M_g[1]), Double.parseDouble(MD4M_g[2]), Double.parseDouble(MD4M_g[3]), Double.parseDouble(MD4M_g[4]), Double.parseDouble(MD4M_g[5]), Double.parseDouble(MD4M_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelMD4MGas.class);
        //cr = this.session.createCriteria(ModelMD4MGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from MD4M_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD4MGas.class));//Sem isso aqui impossível de retornar
	List<ModelMD4MGas> MD4Ms = consulta.list(); 
        ModelMD4MGas MD4M1 = MD4Ms.get(0);
        
        consulta = this.session.createSQLQuery("select * from MD4M_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD4MGas.class));//Sem isso aqui impossível de retornar
	MD4Ms = consulta.list(); 
        ModelMD4MGas MD4M2 = MD4Ms.get(0);
        
        consulta = this.session.createSQLQuery("select * from MD4M_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD4MGas.class));//Sem isso aqui impossível de retornar
	MD4Ms = consulta.list(); 
        ModelMD4MGas MD4M3 = MD4Ms.get(0);
        
        consulta = this.session.createSQLQuery("select * from MD4M_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD4MGas.class));//Sem isso aqui impossível de retornar
	MD4Ms = consulta.list(); 
        ModelMD4MGas MD4M4 = MD4Ms.get(0);
        
        cpv1 = MD4M1.getCPV() + (MD4M2.getCPV() - MD4M1.getCPV()) * ((temperatura-MD4M1.getTEMPERATURA())/(MD4M2.getTEMPERATURA()-MD4M1.getTEMPERATURA()));
        cpv2 = MD4M3.getCPV() + (MD4M4.getCPV() - MD4M3.getCPV()) * ((temperatura-MD4M3.getTEMPERATURA())/(MD4M4.getTEMPERATURA()-MD4M3.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-MD4M1.getPRESSAO())/(MD4M3.getPRESSAO()-MD4M1.getPRESSAO()));
        
        Prv1 = MD4M1.getPRV() + (MD4M2.getPRV() - MD4M1.getPRV()) * ((temperatura-MD4M1.getTEMPERATURA())/(MD4M2.getTEMPERATURA()-MD4M1.getTEMPERATURA()));
        Prv2 = MD4M3.getPRV() + (MD4M4.getPRV() - MD4M3.getPRV()) * ((temperatura-MD4M3.getTEMPERATURA())/(MD4M4.getTEMPERATURA()-MD4M3.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-MD4M1.getPRESSAO())/(MD4M3.getPRESSAO()-MD4M1.getPRESSAO()));
        
        kv1 = MD4M1.getKV() + (MD4M2.getKV() - MD4M1.getKV()) * ((temperatura-MD4M1.getTEMPERATURA())/(MD4M2.getTEMPERATURA()-MD4M1.getTEMPERATURA()));
        kv2 = MD4M3.getKV() + (MD4M4.getKV() - MD4M3.getKV()) * ((temperatura-MD4M3.getTEMPERATURA())/(MD4M4.getTEMPERATURA()-MD4M3.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-MD4M1.getPRESSAO())/(MD4M3.getPRESSAO()-MD4M1.getPRESSAO()));
        
        Muv1 = MD4M1.getMUV() + (MD4M2.getMUV() - MD4M1.getMUV()) * ((temperatura-MD4M1.getTEMPERATURA())/(MD4M2.getTEMPERATURA()-MD4M1.getTEMPERATURA()));
        Muv2 = MD4M3.getMUV() + (MD4M4.getMUV() - MD4M3.getMUV()) * ((temperatura-MD4M3.getTEMPERATURA())/(MD4M4.getTEMPERATURA()-MD4M3.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-MD4M1.getPRESSAO())/(MD4M3.getPRESSAO()-MD4M1.getPRESSAO()));
        
        Vcv1 = MD4M1.getVCV() + (MD4M2.getVCV() - MD4M1.getVCV()) * ((temperatura-MD4M1.getTEMPERATURA())/(MD4M2.getTEMPERATURA()-MD4M1.getTEMPERATURA()));
        Vcv2 = MD4M3.getVCV() + (MD4M4.getVCV() - MD4M3.getVCV()) * ((temperatura-MD4M3.getTEMPERATURA())/(MD4M4.getTEMPERATURA()-MD4M3.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-MD4M1.getPRESSAO())/(MD4M3.getPRESSAO()-MD4M1.getPRESSAO()));
        
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
