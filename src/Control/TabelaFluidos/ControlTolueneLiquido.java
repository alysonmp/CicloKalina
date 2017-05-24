/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelTolueneLiquido;
import Model.TabelasFluidos.ModelTolueneLiquido;
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
public class ControlTolueneLiquido {
    
    Session session;
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;
    
    public ControlTolueneLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaTolueneLiquido(){
        String csvFile = "src/Csv/TOLUENE_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelTolueneLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                Transaction tx = session.beginTransaction();
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] Toluene_l = line.split(cvsSplitBy);
                    
                    session.save(new ModelTolueneLiquido(Double.parseDouble(Toluene_l[0]), Double.parseDouble(Toluene_l[1]), Double.parseDouble(Toluene_l[2]), Double.parseDouble(Toluene_l[3])));
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
        Criteria cr = this.session.createCriteria(ModelTolueneLiquido.class);
        //cr = this.session.createCriteria(ModelTolueneLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from toluene where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelTolueneLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelTolueneLiquido> Toluenes = consulta.list(); 
        ModelTolueneLiquido toluene1 = Toluenes.get(0);
        
        consulta = this.session.createSQLQuery("select * from toluene where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelTolueneLiquido.class));//Sem isso aqui impossível de retornar
	Toluenes = consulta.list(); 
        ModelTolueneLiquido toluene2 = Toluenes.get(0);
        
        consulta = this.session.createSQLQuery("select * from toluene where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelTolueneLiquido.class));//Sem isso aqui impossível de retornar
	Toluenes = consulta.list(); 
        ModelTolueneLiquido toluene3 = Toluenes.get(0);
        
        consulta = this.session.createSQLQuery("select * from toluene where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelTolueneLiquido.class));//Sem isso aqui impossível de retornar
	Toluenes = consulta.list(); 
        ModelTolueneLiquido toluene4 = Toluenes.get(0);
        
        Cpl1 = toluene1.getCPL()+ (toluene2.getCPL()- toluene1.getCPL()) * ((temperatura-toluene1.getTEMPERATURA())/(toluene2.getTEMPERATURA()-toluene1.getTEMPERATURA()));
        Cpl2 = toluene3.getCPL()+ (toluene4.getCPL()- toluene3.getCPL()) * ((temperatura-toluene3.getTEMPERATURA())/(toluene4.getTEMPERATURA()-toluene3.getTEMPERATURA()));
        Cpl = Cpl1 + (Cpl2 - Cpl1) * ((pressao-toluene1.getPRESSAO())/(toluene2.getPRESSAO()-toluene1.getPRESSAO()));
        
        Prl1 = toluene1.getPRL()+ (toluene2.getPRL()- toluene1.getPRL()) * ((temperatura-toluene1.getTEMPERATURA())/(toluene2.getTEMPERATURA()-toluene1.getTEMPERATURA()));
        Prl2 = toluene3.getPRL()+ (toluene4.getPRL()- toluene3.getPRL()) * ((temperatura-toluene3.getTEMPERATURA())/(toluene4.getTEMPERATURA()-toluene3.getTEMPERATURA()));
        Prl = Prl1 + (Prl2 - Prl1) * ((pressao-toluene3.getPRESSAO())/(toluene4.getPRESSAO()-toluene3.getPRESSAO()));
        
        System.out.println(Cpl);
        System.out.println(Prl);
    }
    
}
