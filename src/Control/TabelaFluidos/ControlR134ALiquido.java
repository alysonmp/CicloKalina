/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelR134ALiquido;
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
public class ControlR134ALiquido {
    
    Session session;
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;
    
    public ControlR134ALiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR134ALiquido(){
        String csvFile = "src/Csv/R134A_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR134ALiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                Transaction tx = session.beginTransaction();
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R134A_l = line.split(cvsSplitBy);
                    
                    session.save(new ModelR134ALiquido(Double.parseDouble(R134A_l[0]), Double.parseDouble(R134A_l[1]), Double.parseDouble(R134A_l[2]), Double.parseDouble(R134A_l[3])));
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
        Criteria cr = this.session.createCriteria(ModelR134ALiquido.class);
        //cr = this.session.createCriteria(ModelR134ALiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R134A where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR134ALiquido.class));//Sem isso aqui impossível de retornar
	List<ModelR134ALiquido> R134As = consulta.list(); 
        ModelR134ALiquido R134A1 = R134As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R134A where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR134ALiquido.class));//Sem isso aqui impossível de retornar
	R134As = consulta.list(); 
        ModelR134ALiquido R134A2 = R134As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R134A where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR134ALiquido.class));//Sem isso aqui impossível de retornar
	R134As = consulta.list(); 
        ModelR134ALiquido R134A3 = R134As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R134A where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR134ALiquido.class));//Sem isso aqui impossível de retornar
	R134As = consulta.list(); 
        ModelR134ALiquido R134A4 = R134As.get(0);
        
        Cpl1 = R134A1.getCPL()+ (R134A2.getCPL()- R134A1.getCPL()) * ((temperatura-R134A1.getTEMPERATURA())/(R134A2.getTEMPERATURA()-R134A1.getTEMPERATURA()));
        Cpl2 = R134A3.getCPL()+ (R134A4.getCPL()- R134A3.getCPL()) * ((temperatura-R134A3.getTEMPERATURA())/(R134A4.getTEMPERATURA()-R134A3.getTEMPERATURA()));
        Cpl = Cpl1 + (Cpl2 - Cpl1) * ((pressao-R134A1.getPRESSAO())/(R134A2.getPRESSAO()-R134A1.getPRESSAO()));
        
        Prl1 = R134A1.getPRL()+ (R134A2.getPRL()- R134A1.getPRL()) * ((temperatura-R134A1.getTEMPERATURA())/(R134A2.getTEMPERATURA()-R134A1.getTEMPERATURA()));
        Prl2 = R134A3.getPRL()+ (R134A4.getPRL()- R134A3.getPRL()) * ((temperatura-R134A3.getTEMPERATURA())/(R134A4.getTEMPERATURA()-R134A3.getTEMPERATURA()));
        Prl = Prl1 + (Prl2 - Prl1) * ((pressao-R134A3.getPRESSAO())/(R134A4.getPRESSAO()-R134A3.getPRESSAO()));
        
        System.out.println(Cpl);
        System.out.println(Prl);
    }    
}
