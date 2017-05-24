/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelR22Liquido;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

/**
 *
 * @author alysonmp
 */
public class ControlR22Liquido {
    
    Session session;
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;
    
    public ControlR22Liquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR22Liquido(){
        String csvFile = "src/Csv/R22_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR22Liquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                Transaction tx = session.beginTransaction();
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R22_l = line.split(cvsSplitBy);
                    
                    session.save(new ModelR22Liquido(Double.parseDouble(R22_l[0]), Double.parseDouble(R22_l[1]), Double.parseDouble(R22_l[2]), Double.parseDouble(R22_l[3])));
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
    
    public void interpolacaoButano(double pressao, double temperatura){
        Criteria cr = this.session.createCriteria(ModelR22Liquido.class);
        //cr = this.session.createCriteria(ModelR22Liquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R22 where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR22Liquido.class));//Sem isso aqui impossível de retornar
	List<ModelR22Liquido> R22s = consulta.list(); 
        ModelR22Liquido R221 = R22s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R22 where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR22Liquido.class));//Sem isso aqui impossível de retornar
	R22s = consulta.list(); 
        ModelR22Liquido R222 = R22s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R22 where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR22Liquido.class));//Sem isso aqui impossível de retornar
	R22s = consulta.list(); 
        ModelR22Liquido R223 = R22s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R22 where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR22Liquido.class));//Sem isso aqui impossível de retornar
	R22s = consulta.list(); 
        ModelR22Liquido R224 = R22s.get(0);
        
        Cpl1 = R221.getCPL()+ (R222.getCPL()- R221.getCPL()) * ((temperatura-R221.getTEMPERATURA())/(R222.getTEMPERATURA()-R221.getTEMPERATURA()));
        Cpl2 = R223.getCPL()+ (R224.getCPL()- R223.getCPL()) * ((temperatura-R223.getTEMPERATURA())/(R224.getTEMPERATURA()-R223.getTEMPERATURA()));
        Cpl = Cpl1 + (Cpl2 - Cpl1) * ((pressao-R221.getPRESSAO())/(R222.getPRESSAO()-R221.getPRESSAO()));
        
        Prl1 = R221.getPRL()+ (R222.getPRL()- R221.getPRL()) * ((temperatura-R221.getTEMPERATURA())/(R222.getTEMPERATURA()-R221.getTEMPERATURA()));
        Prl2 = R223.getPRL()+ (R224.getPRL()- R223.getPRL()) * ((temperatura-R223.getTEMPERATURA())/(R224.getTEMPERATURA()-R223.getTEMPERATURA()));
        Prl = Prl1 + (Prl2 - Prl1) * ((pressao-R223.getPRESSAO())/(R224.getPRESSAO()-R223.getPRESSAO()));
        
        System.out.println(Cpl);
        System.out.println(Prl);
    }    
}
