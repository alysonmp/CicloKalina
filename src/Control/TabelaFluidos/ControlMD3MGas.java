/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.Ciclo2.ModelFluidos;
import Model.TabelasFluidos.ModelMD3MGas;
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
public class ControlMD3MGas {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    private ModelMD3MGas MD3M1;
    private ModelMD3MGas MD3M2;
    private ModelMD3MGas MD3M3;
    private ModelMD3MGas MD3M4;
    
    public ControlMD3MGas(Session session){
        this.session = session;
    }
    
    public void criaTabelaMD3MGas(){
        String csvFile = "src/Csv/MD3M_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelMD3MGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use coMD3Ma as separator
                    String[] MD3M_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelMD3MGas(Double.parseDouble(MD3M_g[0]), Double.parseDouble(MD3M_g[1]), Double.parseDouble(MD3M_g[2]), Double.parseDouble(MD3M_g[3]), Double.parseDouble(MD3M_g[4]), Double.parseDouble(MD3M_g[5]), Double.parseDouble(MD3M_g[6])));
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
        Criteria cr = this.session.createCriteria(ModelMD3MGas.class);
        //cr = this.session.createCriteria(ModelMD3MGas.class);
        
        do{
            SQLQuery consulta = this.session.createSQLQuery("select * from MD3M_gas where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");

            consulta.setResultTransformer(Transformers.aliasToBean(ModelMD3MGas.class));//Sem isso aqui impossível de retornar
            List<ModelMD3MGas> MD3Ms = consulta.list(); 
            MD3M1 = MD3Ms.get(0);

            consulta = this.session.createSQLQuery("select * from MD3M_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");

            consulta.setResultTransformer(Transformers.aliasToBean(ModelMD3MGas.class));//Sem isso aqui impossível de retornar
            MD3Ms = consulta.list(); 
            MD3M2 = MD3Ms.get(0);

            consulta = this.session.createSQLQuery("select * from MD3M_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");

            consulta.setResultTransformer(Transformers.aliasToBean(ModelMD3MGas.class));//Sem isso aqui impossível de retornar
            MD3Ms = consulta.list(); 
            MD3M3 = MD3Ms.get(0);

            consulta = this.session.createSQLQuery("select * from MD3M_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");

            consulta.setResultTransformer(Transformers.aliasToBean(ModelMD3MGas.class));//Sem isso aqui impossível de retornar
            MD3Ms = consulta.list(); 
            MD3M4 = MD3Ms.get(0);

            temperatura += 1;
        }while(MD3M1 != null || MD3M2 != null || MD3M3 != null || MD3M4 != null);
        
        cpv1 = MD3M1.getCPV() + (MD3M2.getCPV() - MD3M1.getCPV()) * ((temperatura-MD3M1.getTEMPERATURA())/(MD3M2.getTEMPERATURA()-MD3M1.getTEMPERATURA()));
        cpv2 = MD3M3.getCPV() + (MD3M4.getCPV() - MD3M3.getCPV()) * ((temperatura-MD3M3.getTEMPERATURA())/(MD3M4.getTEMPERATURA()-MD3M3.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-MD3M1.getPRESSAO())/(MD3M3.getPRESSAO()-MD3M1.getPRESSAO()));
        
        Prv1 = MD3M1.getPRV() + (MD3M2.getPRV() - MD3M1.getPRV()) * ((temperatura-MD3M1.getTEMPERATURA())/(MD3M2.getTEMPERATURA()-MD3M1.getTEMPERATURA()));
        Prv2 = MD3M3.getPRV() + (MD3M4.getPRV() - MD3M3.getPRV()) * ((temperatura-MD3M3.getTEMPERATURA())/(MD3M4.getTEMPERATURA()-MD3M3.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-MD3M1.getPRESSAO())/(MD3M3.getPRESSAO()-MD3M1.getPRESSAO()));
        
        kv1 = MD3M1.getKV() + (MD3M2.getKV() - MD3M1.getKV()) * ((temperatura-MD3M1.getTEMPERATURA())/(MD3M2.getTEMPERATURA()-MD3M1.getTEMPERATURA()));
        kv2 = MD3M3.getKV() + (MD3M4.getKV() - MD3M3.getKV()) * ((temperatura-MD3M3.getTEMPERATURA())/(MD3M4.getTEMPERATURA()-MD3M3.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-MD3M1.getPRESSAO())/(MD3M3.getPRESSAO()-MD3M1.getPRESSAO()));
        
        Muv1 = MD3M1.getMUV() + (MD3M2.getMUV() - MD3M1.getMUV()) * ((temperatura-MD3M1.getTEMPERATURA())/(MD3M2.getTEMPERATURA()-MD3M1.getTEMPERATURA()));
        Muv2 = MD3M3.getMUV() + (MD3M4.getMUV() - MD3M3.getMUV()) * ((temperatura-MD3M3.getTEMPERATURA())/(MD3M4.getTEMPERATURA()-MD3M3.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-MD3M1.getPRESSAO())/(MD3M3.getPRESSAO()-MD3M1.getPRESSAO()));
        
        Vcv1 = MD3M1.getVCV() + (MD3M2.getVCV() - MD3M1.getVCV()) * ((temperatura-MD3M1.getTEMPERATURA())/(MD3M2.getTEMPERATURA()-MD3M1.getTEMPERATURA()));
        Vcv2 = MD3M3.getVCV() + (MD3M4.getVCV() - MD3M3.getVCV()) * ((temperatura-MD3M3.getTEMPERATURA())/(MD3M4.getTEMPERATURA()-MD3M3.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-MD3M1.getPRESSAO())/(MD3M3.getPRESSAO()-MD3M1.getPRESSAO()));
        
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
