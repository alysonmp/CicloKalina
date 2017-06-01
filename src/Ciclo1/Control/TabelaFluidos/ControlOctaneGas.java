/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelOctaneGas;
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
public class ControlOctaneGas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlOctaneGas(Session session){
        this.session = session;
    }
    
    public void criaTabelaOctaneGas(){
        String csvFile = "src/Csv/OCTANE_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelOctaneGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] Octane_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelOctaneGas(Double.parseDouble(Octane_g[0]), Double.parseDouble(Octane_g[1]), Double.parseDouble(Octane_g[2]), Double.parseDouble(Octane_g[3]), Double.parseDouble(Octane_g[4]), Double.parseDouble(Octane_g[5]), Double.parseDouble(Octane_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelOctaneGas.class);
        //cr = this.session.createCriteria(ModelOctaneGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from Octane_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelOctaneGas.class));//Sem isso aqui impossível de retornar
	List<ModelOctaneGas> Octanes = consulta.list(); 
        ModelOctaneGas Octane1 = Octanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Octane_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelOctaneGas.class));//Sem isso aqui impossível de retornar
	Octanes = consulta.list(); 
        ModelOctaneGas Octane2 = Octanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Octane_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelOctaneGas.class));//Sem isso aqui impossível de retornar
	Octanes = consulta.list(); 
        ModelOctaneGas Octane3 = Octanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Octane_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelOctaneGas.class));//Sem isso aqui impossível de retornar
	Octanes = consulta.list(); 
        ModelOctaneGas Octane4 = Octanes.get(0);
        
        cpv1 = Octane1.getCPV() + (Octane2.getCPV() - Octane1.getCPV()) * ((temperatura-Octane1.getTEMPERATURA())/(Octane2.getTEMPERATURA()-Octane1.getTEMPERATURA()));
        cpv2 = Octane3.getCPV() + (Octane4.getCPV() - Octane3.getCPV()) * ((temperatura-Octane3.getTEMPERATURA())/(Octane4.getTEMPERATURA()-Octane3.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-Octane1.getPRESSAO())/(Octane3.getPRESSAO()-Octane1.getPRESSAO()));
        
        Prv1 = Octane1.getPRV() + (Octane2.getPRV() - Octane1.getPRV()) * ((temperatura-Octane1.getTEMPERATURA())/(Octane2.getTEMPERATURA()-Octane1.getTEMPERATURA()));
        Prv2 = Octane3.getPRV() + (Octane4.getPRV() - Octane3.getPRV()) * ((temperatura-Octane3.getTEMPERATURA())/(Octane4.getTEMPERATURA()-Octane3.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-Octane1.getPRESSAO())/(Octane3.getPRESSAO()-Octane1.getPRESSAO()));
        
        kv1 = Octane1.getKV() + (Octane2.getKV() - Octane1.getKV()) * ((temperatura-Octane1.getTEMPERATURA())/(Octane2.getTEMPERATURA()-Octane1.getTEMPERATURA()));
        kv2 = Octane3.getKV() + (Octane4.getKV() - Octane3.getKV()) * ((temperatura-Octane3.getTEMPERATURA())/(Octane4.getTEMPERATURA()-Octane3.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-Octane1.getPRESSAO())/(Octane3.getPRESSAO()-Octane1.getPRESSAO()));
        
        Muv1 = Octane1.getMUV() + (Octane2.getMUV() - Octane1.getMUV()) * ((temperatura-Octane1.getTEMPERATURA())/(Octane2.getTEMPERATURA()-Octane1.getTEMPERATURA()));
        Muv2 = Octane3.getMUV() + (Octane4.getMUV() - Octane3.getMUV()) * ((temperatura-Octane3.getTEMPERATURA())/(Octane4.getTEMPERATURA()-Octane3.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-Octane1.getPRESSAO())/(Octane3.getPRESSAO()-Octane1.getPRESSAO()));
        
        Vcv1 = Octane1.getVCV() + (Octane2.getVCV() - Octane1.getVCV()) * ((temperatura-Octane1.getTEMPERATURA())/(Octane2.getTEMPERATURA()-Octane1.getTEMPERATURA()));
        Vcv2 = Octane3.getVCV() + (Octane4.getVCV() - Octane3.getVCV()) * ((temperatura-Octane3.getTEMPERATURA())/(Octane4.getTEMPERATURA()-Octane3.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-Octane1.getPRESSAO())/(Octane3.getPRESSAO()-Octane1.getPRESSAO()));
        
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
