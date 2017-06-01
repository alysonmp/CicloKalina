/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelMMGas;
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
public class ControlMMGas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlMMGas(Session session){
        this.session = session;
    }
    
    public void criaTabelaMMGas(){
        String csvFile = "src/Csv/MM_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelMMGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] MM_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelMMGas(Double.parseDouble(MM_g[0]), Double.parseDouble(MM_g[1]), Double.parseDouble(MM_g[2]), Double.parseDouble(MM_g[3]), Double.parseDouble(MM_g[4]), Double.parseDouble(MM_g[5]), Double.parseDouble(MM_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelMMGas.class);
        //cr = this.session.createCriteria(ModelMMGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from MM_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMMGas.class));//Sem isso aqui impossível de retornar
	List<ModelMMGas> MMs = consulta.list(); 
        ModelMMGas MM1 = MMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MM_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMMGas.class));//Sem isso aqui impossível de retornar
	MMs = consulta.list(); 
        ModelMMGas MM2 = MMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MM_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMMGas.class));//Sem isso aqui impossível de retornar
	MMs = consulta.list(); 
        ModelMMGas MM3 = MMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MM_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMMGas.class));//Sem isso aqui impossível de retornar
	MMs = consulta.list(); 
        ModelMMGas MM4 = MMs.get(0);
        
        cpv1 = MM1.getCPV() + (MM2.getCPV() - MM1.getCPV()) * ((temperatura-MM1.getTEMPERATURA())/(MM2.getTEMPERATURA()-MM1.getTEMPERATURA()));
        cpv2 = MM3.getCPV() + (MM4.getCPV() - MM3.getCPV()) * ((temperatura-MM3.getTEMPERATURA())/(MM4.getTEMPERATURA()-MM3.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-MM1.getPRESSAO())/(MM3.getPRESSAO()-MM1.getPRESSAO()));
        
        Prv1 = MM1.getPRV() + (MM2.getPRV() - MM1.getPRV()) * ((temperatura-MM1.getTEMPERATURA())/(MM2.getTEMPERATURA()-MM1.getTEMPERATURA()));
        Prv2 = MM3.getPRV() + (MM4.getPRV() - MM3.getPRV()) * ((temperatura-MM3.getTEMPERATURA())/(MM4.getTEMPERATURA()-MM3.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-MM1.getPRESSAO())/(MM3.getPRESSAO()-MM1.getPRESSAO()));
        
        kv1 = MM1.getKV() + (MM2.getKV() - MM1.getKV()) * ((temperatura-MM1.getTEMPERATURA())/(MM2.getTEMPERATURA()-MM1.getTEMPERATURA()));
        kv2 = MM3.getKV() + (MM4.getKV() - MM3.getKV()) * ((temperatura-MM3.getTEMPERATURA())/(MM4.getTEMPERATURA()-MM3.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-MM1.getPRESSAO())/(MM3.getPRESSAO()-MM1.getPRESSAO()));
        
        Muv1 = MM1.getMUV() + (MM2.getMUV() - MM1.getMUV()) * ((temperatura-MM1.getTEMPERATURA())/(MM2.getTEMPERATURA()-MM1.getTEMPERATURA()));
        Muv2 = MM3.getMUV() + (MM4.getMUV() - MM3.getMUV()) * ((temperatura-MM3.getTEMPERATURA())/(MM4.getTEMPERATURA()-MM3.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-MM1.getPRESSAO())/(MM3.getPRESSAO()-MM1.getPRESSAO()));
        
        Vcv1 = MM1.getVCV() + (MM2.getVCV() - MM1.getVCV()) * ((temperatura-MM1.getTEMPERATURA())/(MM2.getTEMPERATURA()-MM1.getTEMPERATURA()));
        Vcv2 = MM3.getVCV() + (MM4.getVCV() - MM3.getVCV()) * ((temperatura-MM3.getTEMPERATURA())/(MM4.getTEMPERATURA()-MM3.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-MM1.getPRESSAO())/(MM3.getPRESSAO()-MM1.getPRESSAO()));
        
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
