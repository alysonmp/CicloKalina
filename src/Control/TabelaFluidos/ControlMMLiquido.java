/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelMMLiquido;
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
public class ControlMMLiquido {
    
    Session session;
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;
    
    public ControlMMLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaMMLiquido(){
        String csvFile = "src/Csv/MM_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelMMLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                Transaction tx = session.beginTransaction();
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] MM_l = line.split(cvsSplitBy);
                    
                    session.save(new ModelMMLiquido(Double.parseDouble(MM_l[0]), Double.parseDouble(MM_l[1]), Double.parseDouble(MM_l[2]), Double.parseDouble(MM_l[3])));
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
        Criteria cr = this.session.createCriteria(ModelMMLiquido.class);
        //cr = this.session.createCriteria(ModelMMLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from MM where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMMLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelMMLiquido> MMs = consulta.list(); 
        ModelMMLiquido MM1 = MMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MM where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMMLiquido.class));//Sem isso aqui impossível de retornar
	MMs = consulta.list(); 
        ModelMMLiquido MM2 = MMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MM where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMMLiquido.class));//Sem isso aqui impossível de retornar
	MMs = consulta.list(); 
        ModelMMLiquido MM3 = MMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MM where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMMLiquido.class));//Sem isso aqui impossível de retornar
	MMs = consulta.list(); 
        ModelMMLiquido MM4 = MMs.get(0);
        
        Cpl1 = MM1.getCPL()+ (MM2.getCPL()- MM1.getCPL()) * ((temperatura-MM1.getTEMPERATURA())/(MM2.getTEMPERATURA()-MM1.getTEMPERATURA()));
        Cpl2 = MM3.getCPL()+ (MM4.getCPL()- MM3.getCPL()) * ((temperatura-MM3.getTEMPERATURA())/(MM4.getTEMPERATURA()-MM3.getTEMPERATURA()));
        Cpl = Cpl1 + (Cpl2 - Cpl1) * ((pressao-MM1.getPRESSAO())/(MM2.getPRESSAO()-MM1.getPRESSAO()));
        
        Prl1 = MM1.getPRL()+ (MM2.getPRL()- MM1.getPRL()) * ((temperatura-MM1.getTEMPERATURA())/(MM2.getTEMPERATURA()-MM1.getTEMPERATURA()));
        Prl2 = MM3.getPRL()+ (MM4.getPRL()- MM3.getPRL()) * ((temperatura-MM3.getTEMPERATURA())/(MM4.getTEMPERATURA()-MM3.getTEMPERATURA()));
        Prl = Prl1 + (Prl2 - Prl1) * ((pressao-MM3.getPRESSAO())/(MM4.getPRESSAO()-MM3.getPRESSAO()));
        
        System.out.println(Cpl);
        System.out.println(Prl);
    }    
}
