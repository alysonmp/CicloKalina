/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.Control.TabelaFluidos;

import Ciclo2.Model.Ciclo2.ModelFluidos;
import Ciclo2.Model.TabelasFluidos.ModelR141BGas;
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
public class ControlR141BGas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    private ModelR141BGas R141B1;
    private ModelR141BGas R141B2;
    private ModelR141BGas R141B3;
    private ModelR141BGas R141B4;
    
    public ControlR141BGas(Session session){
        this.session = session;
    }
    
    public void criaTabelaR141BGas(){
        String csvFile = "src/Csv/R141B_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR141BGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R141B_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelR141BGas(Double.parseDouble(R141B_g[0]), Double.parseDouble(R141B_g[1]), Double.parseDouble(R141B_g[2]), Double.parseDouble(R141B_g[3]), Double.parseDouble(R141B_g[4]), Double.parseDouble(R141B_g[5]), Double.parseDouble(R141B_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelR141BGas.class);
        //cr = this.session.createCriteria(ModelR141BGas.class);
        
        do{
            SQLQuery consulta = this.session.createSQLQuery("select * from R141B_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");

            consulta.setResultTransformer(Transformers.aliasToBean(ModelR141BGas.class));//Sem isso aqui impossível de retornar
            List<ModelR141BGas> R141Bs = consulta.list(); 
            R141B1 = R141Bs.get(0);

            consulta = this.session.createSQLQuery("select * from R141B_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");

            consulta.setResultTransformer(Transformers.aliasToBean(ModelR141BGas.class));//Sem isso aqui impossível de retornar
            R141Bs = consulta.list(); 
            R141B2 = R141Bs.get(0);

            consulta = this.session.createSQLQuery("select * from R141B_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");

            consulta.setResultTransformer(Transformers.aliasToBean(ModelR141BGas.class));//Sem isso aqui impossível de retornar
            R141Bs = consulta.list(); 
            R141B3 = R141Bs.get(0);

            consulta = this.session.createSQLQuery("select * from R141B_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");

            consulta.setResultTransformer(Transformers.aliasToBean(ModelR141BGas.class));//Sem isso aqui impossível de retornar
            R141Bs = consulta.list(); 
            R141B4 = R141Bs.get(0);

            temperatura += 1;
        }while(R141B1 != null || R141B2 != null || R141B3 != null || R141B4 != null);
        
        cpv1 = R141B1.getCPV() + (R141B2.getCPV() - R141B1.getCPV()) * ((temperatura-R141B1.getTEMPERATURA())/(R141B2.getTEMPERATURA()-R141B1.getTEMPERATURA()));
        cpv2 = R141B3.getCPV() + (R141B4.getCPV() - R141B3.getCPV()) * ((temperatura-R141B3.getTEMPERATURA())/(R141B4.getTEMPERATURA()-R141B3.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-R141B1.getPRESSAO())/(R141B3.getPRESSAO()-R141B1.getPRESSAO()));
        
        Prv1 = R141B1.getPRV() + (R141B2.getPRV() - R141B1.getPRV()) * ((temperatura-R141B1.getTEMPERATURA())/(R141B2.getTEMPERATURA()-R141B1.getTEMPERATURA()));
        Prv2 = R141B3.getPRV() + (R141B4.getPRV() - R141B3.getPRV()) * ((temperatura-R141B3.getTEMPERATURA())/(R141B4.getTEMPERATURA()-R141B3.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-R141B1.getPRESSAO())/(R141B3.getPRESSAO()-R141B1.getPRESSAO()));
        
        kv1 = R141B1.getKV() + (R141B2.getKV() - R141B1.getKV()) * ((temperatura-R141B1.getTEMPERATURA())/(R141B2.getTEMPERATURA()-R141B1.getTEMPERATURA()));
        kv2 = R141B3.getKV() + (R141B4.getKV() - R141B3.getKV()) * ((temperatura-R141B3.getTEMPERATURA())/(R141B4.getTEMPERATURA()-R141B3.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-R141B1.getPRESSAO())/(R141B3.getPRESSAO()-R141B1.getPRESSAO()));
        
        Muv1 = R141B1.getMUV() + (R141B2.getMUV() - R141B1.getMUV()) * ((temperatura-R141B1.getTEMPERATURA())/(R141B2.getTEMPERATURA()-R141B1.getTEMPERATURA()));
        Muv2 = R141B3.getMUV() + (R141B4.getMUV() - R141B3.getMUV()) * ((temperatura-R141B3.getTEMPERATURA())/(R141B4.getTEMPERATURA()-R141B3.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-R141B1.getPRESSAO())/(R141B3.getPRESSAO()-R141B1.getPRESSAO()));
        
        Vcv1 = R141B1.getVCV() + (R141B2.getVCV() - R141B1.getVCV()) * ((temperatura-R141B1.getTEMPERATURA())/(R141B2.getTEMPERATURA()-R141B1.getTEMPERATURA()));
        Vcv2 = R141B3.getVCV() + (R141B4.getVCV() - R141B3.getVCV()) * ((temperatura-R141B3.getTEMPERATURA())/(R141B4.getTEMPERATURA()-R141B3.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-R141B1.getPRESSAO())/(R141B3.getPRESSAO()-R141B1.getPRESSAO()));
        
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
