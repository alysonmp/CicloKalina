/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.Ciclo2.ModelFluidos;
import Model.TabelasFluidos.ModelHexane;
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
public class ControlHexane {
    
    Session session;
    private double kv, Cpv, Prv, Muv, Vcv;
    private double cpv1, cpv2, Prv1, Prv2, kv1, kv2, Muv1, Muv2, Vcv1, Vcv2;
    
    public ControlHexane(Session session){
        this.session = session;
    }
    
    public void criaTabelaHexane(){
        String csvFile = "src/Csv/Hexane Teste.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {
            Criteria cr = this.session.createCriteria(ModelHexane.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                Transaction tx = session.beginTransaction();
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] hexane = line.split(cvsSplitBy);

                    session.save(new ModelHexane(Double.parseDouble(hexane[0]), Double.parseDouble(hexane[1]), Double.parseDouble(hexane[2]), Double.parseDouble(hexane[3]), Double.parseDouble(hexane[4]), Double.parseDouble(hexane[5]), Double.parseDouble(hexane[6])));
                }
                
                tx.commit();
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
    
    public void interpolacaoHexane(double pressao, double temperatura){
        Criteria cr = this.session.createCriteria(ModelHexane.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from Hexane where pressao <= " +pressao+ " and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHexane.class));//Sem isso aqui impossível de retornar
	List<ModelHexane> hexanes = consulta.list(); 
        ModelHexane hexane1 = hexanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Hexane where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHexane.class));//Sem isso aqui impossível de retornar
	hexanes = consulta.list(); 
        ModelHexane hexane2 = hexanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Hexane where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHexane.class));//Sem isso aqui impossível de retornar
	hexanes = consulta.list(); 
        ModelHexane hexane3 = hexanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Hexane where pressao >= " +pressao+ " and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHexane.class));//Sem isso aqui impossível de retornar
	hexanes = consulta.list(); 
        ModelHexane hexane4 = hexanes.get(0);
        
        cpv1 = hexane1.getCPV() + (hexane2.getCPV() - hexane1.getCPV()) * ((temperatura-hexane1.getTEMPERATURA())/(hexane2.getTEMPERATURA()-hexane1.getTEMPERATURA()));
        cpv2 = hexane3.getCPV() + (hexane4.getCPV() - hexane3.getCPV()) * ((temperatura-hexane3.getTEMPERATURA())/(hexane4.getTEMPERATURA()-hexane3.getTEMPERATURA()));
        Cpv = cpv1 + (cpv2 - cpv1) * ((pressao-hexane1.getPRESSAO())/(hexane3.getPRESSAO()-hexane1.getPRESSAO()));
        
        Prv1 = hexane1.getPRV() + (hexane2.getPRV() - hexane1.getPRV()) * ((temperatura-hexane1.getTEMPERATURA())/(hexane2.getTEMPERATURA()-hexane1.getTEMPERATURA()));
        Prv2 = hexane3.getPRV() + (hexane4.getPRV() - hexane3.getPRV()) * ((temperatura-hexane3.getTEMPERATURA())/(hexane4.getTEMPERATURA()-hexane3.getTEMPERATURA()));
        Prv = Prv1 + (Prv2 - Prv1) * ((pressao-hexane1.getPRESSAO())/(hexane3.getPRESSAO()-hexane1.getPRESSAO()));
        
        kv1 = hexane1.getKV() + (hexane2.getKV() - hexane1.getKV()) * ((temperatura-hexane1.getTEMPERATURA())/(hexane2.getTEMPERATURA()-hexane1.getTEMPERATURA()));
        kv2 = hexane3.getKV() + (hexane4.getKV() - hexane3.getKV()) * ((temperatura-hexane3.getTEMPERATURA())/(hexane4.getTEMPERATURA()-hexane3.getTEMPERATURA()));
        kv = kv1 + (kv2 - kv1) * ((pressao-hexane1.getPRESSAO())/(hexane3.getPRESSAO()-hexane1.getPRESSAO()));
        
        Muv1 = hexane1.getMUV() + (hexane2.getMUV() - hexane1.getMUV()) * ((temperatura-hexane1.getTEMPERATURA())/(hexane2.getTEMPERATURA()-hexane1.getTEMPERATURA()));
        Muv2 = hexane3.getMUV() + (hexane4.getMUV() - hexane3.getMUV()) * ((temperatura-hexane3.getTEMPERATURA())/(hexane4.getTEMPERATURA()-hexane3.getTEMPERATURA()));
        Muv = Muv1 + (Muv2 - Muv1) * ((pressao-hexane1.getPRESSAO())/(hexane3.getPRESSAO()-hexane1.getPRESSAO()));
        
        Vcv1 = hexane1.getVCV() + (hexane2.getVCV() - hexane1.getVCV()) * ((temperatura-hexane1.getTEMPERATURA())/(hexane2.getTEMPERATURA()-hexane1.getTEMPERATURA()));
        Vcv2 = hexane3.getVCV() + (hexane4.getVCV() - hexane3.getVCV()) * ((temperatura-hexane3.getTEMPERATURA())/(hexane4.getTEMPERATURA()-hexane3.getTEMPERATURA()));
        Vcv = Vcv1 + (Vcv2 - Vcv1) * ((pressao-hexane1.getPRESSAO())/(hexane3.getPRESSAO()-hexane1.getPRESSAO()));
    }

    public double getKv() {
        return kv;
    }

    public void setKv(double kv) {
        this.kv = kv;
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
