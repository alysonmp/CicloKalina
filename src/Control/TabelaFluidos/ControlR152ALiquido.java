/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelR152ALiquido;
import Model.TabelasFluidos.ModelR152ALiquido;
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
public class ControlR152ALiquido {
    
    Session session;
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;
    
    public ControlR152ALiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR152ALiquido(){
        String csvFile = "src/Csv/R152A_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR152ALiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                Transaction tx = session.beginTransaction();
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R152A_l = line.split(cvsSplitBy);
                    
                    session.save(new ModelR152ALiquido(Double.parseDouble(R152A_l[0]), Double.parseDouble(R152A_l[1]), Double.parseDouble(R152A_l[2]), Double.parseDouble(R152A_l[3])));
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
        Criteria cr = this.session.createCriteria(ModelR152ALiquido.class);
        //cr = this.session.createCriteria(ModelR152ALiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R152A where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR152ALiquido.class));//Sem isso aqui impossível de retornar
	List<ModelR152ALiquido> R152As = consulta.list(); 
        ModelR152ALiquido R152A1 = R152As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R152A where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR152ALiquido.class));//Sem isso aqui impossível de retornar
	R152As = consulta.list(); 
        ModelR152ALiquido R152A2 = R152As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R152A where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR152ALiquido.class));//Sem isso aqui impossível de retornar
	R152As = consulta.list(); 
        ModelR152ALiquido R152A3 = R152As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R152A where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR152ALiquido.class));//Sem isso aqui impossível de retornar
	R152As = consulta.list(); 
        ModelR152ALiquido R152A4 = R152As.get(0);
        
        Cpl1 = R152A1.getCPL()+ (R152A2.getCPL()- R152A1.getCPL()) * ((temperatura-R152A1.getTEMPERATURA())/(R152A2.getTEMPERATURA()-R152A1.getTEMPERATURA()));
        Cpl2 = R152A3.getCPL()+ (R152A4.getCPL()- R152A3.getCPL()) * ((temperatura-R152A3.getTEMPERATURA())/(R152A4.getTEMPERATURA()-R152A3.getTEMPERATURA()));
        Cpl = Cpl1 + (Cpl2 - Cpl1) * ((pressao-R152A1.getPRESSAO())/(R152A2.getPRESSAO()-R152A1.getPRESSAO()));
        
        Prl1 = R152A1.getPRL()+ (R152A2.getPRL()- R152A1.getPRL()) * ((temperatura-R152A1.getTEMPERATURA())/(R152A2.getTEMPERATURA()-R152A1.getTEMPERATURA()));
        Prl2 = R152A3.getPRL()+ (R152A4.getPRL()- R152A3.getPRL()) * ((temperatura-R152A3.getTEMPERATURA())/(R152A4.getTEMPERATURA()-R152A3.getTEMPERATURA()));
        Prl = Prl1 + (Prl2 - Prl1) * ((pressao-R152A3.getPRESSAO())/(R152A4.getPRESSAO()-R152A3.getPRESSAO()));
        
        System.out.println(Cpl);
        System.out.println(Prl);
    }    
}
