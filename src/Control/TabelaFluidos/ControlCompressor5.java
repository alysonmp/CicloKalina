/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.Ciclo2.ModelFluidos;
import Model.TabelasFluidos.ModelCompressor5;
import Model.TabelasFluidos.ModelWaterGas;
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
public class ControlCompressor5 {
    
    Session session;
    private double Cpv, Prv, kv, Muv, Vcv, Dfv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2, Df1, Df2;
    
    private ModelCompressor5 compr1;
    private ModelCompressor5 compr2;
    private ModelCompressor5 compr3;
    private ModelCompressor5 compr4;
    
    public ControlCompressor5(Session session){
        this.session = session;
    }
    
    public void criaTabelaWaterGas(){
        String csvFile = "src/Csv/compre5_gas.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelCompressor5.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] compr_g = line.split(cvsSplitBy);
                    
                    session.save(new ModelCompressor5(Double.parseDouble(compr_g[0]), Double.parseDouble(compr_g[1]), Double.parseDouble(compr_g[2]), Double.parseDouble(compr_g[3]), Double.parseDouble(compr_g[4]), Double.parseDouble(compr_g[5]), Double.parseDouble(compr_g[6]), Double.parseDouble(compr_g[7])));
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
        Criteria cr = this.session.createCriteria(ModelCompressor5.class);
        //cr = this.session.createCriteria(ModelwaterGas.class);
        
        do{
            SQLQuery consulta = this.session.createSQLQuery("select * from compressor5 where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");

            consulta.setResultTransformer(Transformers.aliasToBean(ModelCompressor5.class));//Sem isso aqui impossível de retornar
            List<ModelCompressor5> compress = consulta.list(); 
            compr1 = compress.get(0);

            consulta = this.session.createSQLQuery("select * from compressor5 where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");

            consulta.setResultTransformer(Transformers.aliasToBean(ModelCompressor5.class));//Sem isso aqui impossível de retornar
            compress = consulta.list(); 
            compr2 = compress.get(0);

            consulta = this.session.createSQLQuery("select * from compressor5 where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");

            consulta.setResultTransformer(Transformers.aliasToBean(ModelCompressor5.class));//Sem isso aqui impossível de retornar
            compress = consulta.list(); 
            compr3 = compress.get(0);

            consulta = this.session.createSQLQuery("select * from compressor5 where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");

            consulta.setResultTransformer(Transformers.aliasToBean(ModelCompressor5.class));//Sem isso aqui impossível de retornar
            compress = consulta.list(); 
            compr4 = compress.get(0);

            temperatura += 1;
        }while(compr1 == null || compr2 == null || compr3 == null || compr4 == null);
        
        cpv1 = compr1.getCPV() + (compr2.getCPV() - compr1.getCPV()) * ((temperatura-compr1.getTEMPERATURA())/(compr2.getTEMPERATURA()-compr1.getTEMPERATURA()));
        cpv2 = compr3.getCPV() + (compr4.getCPV() - compr3.getCPV()) * ((temperatura-compr3.getTEMPERATURA())/(compr4.getTEMPERATURA()-compr3.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-compr1.getPRESSAO())/(compr3.getPRESSAO()-compr1.getPRESSAO()));
        
        Prv1 = compr1.getPRV() + (compr2.getPRV() - compr1.getPRV()) * ((temperatura-compr1.getTEMPERATURA())/(compr2.getTEMPERATURA()-compr1.getTEMPERATURA()));
        Prv2 = compr3.getPRV() + (compr4.getPRV() - compr3.getPRV()) * ((temperatura-compr3.getTEMPERATURA())/(compr4.getTEMPERATURA()-compr3.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-compr1.getPRESSAO())/(compr3.getPRESSAO()-compr1.getPRESSAO()));
        
        kv1 = compr1.getKV() + (compr2.getKV() - compr1.getKV()) * ((temperatura-compr1.getTEMPERATURA())/(compr2.getTEMPERATURA()-compr1.getTEMPERATURA()));
        kv2 = compr3.getKV() + (compr4.getKV() - compr3.getKV()) * ((temperatura-compr3.getTEMPERATURA())/(compr4.getTEMPERATURA()-compr3.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-compr1.getPRESSAO())/(compr3.getPRESSAO()-compr1.getPRESSAO()));
        
        Muv1 = compr1.getMUV() + (compr2.getMUV() - compr1.getMUV()) * ((temperatura-compr1.getTEMPERATURA())/(compr2.getTEMPERATURA()-compr1.getTEMPERATURA()));
        Muv2 = compr3.getMUV() + (compr4.getMUV() - compr3.getMUV()) * ((temperatura-compr3.getTEMPERATURA())/(compr4.getTEMPERATURA()-compr3.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-compr1.getPRESSAO())/(compr3.getPRESSAO()-compr1.getPRESSAO()));
        
        Vcv1 = compr1.getVCV() + (compr2.getVCV() - compr1.getVCV()) * ((temperatura-compr1.getTEMPERATURA())/(compr2.getTEMPERATURA()-compr1.getTEMPERATURA()));
        Vcv2 = compr3.getVCV() + (compr4.getVCV() - compr3.getVCV()) * ((temperatura-compr3.getTEMPERATURA())/(compr4.getTEMPERATURA()-compr3.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-compr1.getPRESSAO())/(compr3.getPRESSAO()-compr1.getPRESSAO()));
        
        Df1 = compr1.getDFV() + (compr2.getDFV() - compr1.getDFV()) * ((temperatura-compr1.getTEMPERATURA())/(compr2.getTEMPERATURA()-compr1.getTEMPERATURA()));
        Df2 = compr3.getDFV() + (compr4.getDFV() - compr3.getDFV()) * ((temperatura-compr3.getTEMPERATURA())/(compr4.getTEMPERATURA()-compr3.getTEMPERATURA()));
        Dfv = Df1 + (Df2 - Df1) * ((pressao-compr1.getPRESSAO())/(compr3.getPRESSAO()-compr1.getPRESSAO()));
        
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

    public double getDf() {
        return Dfv;
    }

    public void setDf(double Dfv) {
        this.Dfv = Dfv;
    }

}
