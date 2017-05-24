/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelR12Liquido;
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
public class ControlR12Liquido {
    
    Session session;
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;
    
    public ControlR12Liquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR12Liquido(){
        String csvFile = "src/Csv/R12_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR12Liquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                Transaction tx = session.beginTransaction();
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R12_l = line.split(cvsSplitBy);
                    
                    session.save(new ModelR12Liquido(Double.parseDouble(R12_l[0]), Double.parseDouble(R12_l[1]), Double.parseDouble(R12_l[2]), Double.parseDouble(R12_l[3])));
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
        Criteria cr = this.session.createCriteria(ModelR12Liquido.class);
        //cr = this.session.createCriteria(ModelR12Liquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R12 where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR12Liquido.class));//Sem isso aqui impossível de retornar
	List<ModelR12Liquido> R12s = consulta.list(); 
        ModelR12Liquido R121 = R12s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R12 where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR12Liquido.class));//Sem isso aqui impossível de retornar
	R12s = consulta.list(); 
        ModelR12Liquido R122 = R12s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R12 where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR12Liquido.class));//Sem isso aqui impossível de retornar
	R12s = consulta.list(); 
        ModelR12Liquido R123 = R12s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R12 where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR12Liquido.class));//Sem isso aqui impossível de retornar
	R12s = consulta.list(); 
        ModelR12Liquido R124 = R12s.get(0);
        
        Cpl1 = R121.getCPL()+ (R122.getCPL()- R121.getCPL()) * ((temperatura-R121.getTEMPERATURA())/(R122.getTEMPERATURA()-R121.getTEMPERATURA()));
        Cpl2 = R123.getCPL()+ (R124.getCPL()- R123.getCPL()) * ((temperatura-R123.getTEMPERATURA())/(R124.getTEMPERATURA()-R123.getTEMPERATURA()));
        Cpl = Cpl1 + (Cpl2 - Cpl1) * ((pressao-R121.getPRESSAO())/(R122.getPRESSAO()-R121.getPRESSAO()));
        
        Prl1 = R121.getPRL()+ (R122.getPRL()- R121.getPRL()) * ((temperatura-R121.getTEMPERATURA())/(R122.getTEMPERATURA()-R121.getTEMPERATURA()));
        Prl2 = R123.getPRL()+ (R124.getPRL()- R123.getPRL()) * ((temperatura-R123.getTEMPERATURA())/(R124.getTEMPERATURA()-R123.getTEMPERATURA()));
        Prl = Prl1 + (Prl2 - Prl1) * ((pressao-R123.getPRESSAO())/(R124.getPRESSAO()-R123.getPRESSAO()));
        
        System.out.println(Cpl);
        System.out.println(Prl);
    }    
}
