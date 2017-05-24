/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelPropaneLiquido;
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
public class ControlPropaneLiquido {
    
    Session session;
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;
    
    public ControlPropaneLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaPropaneLiquido(){
        String csvFile = "src/Csv/PROPANE_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelPropaneLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                Transaction tx = session.beginTransaction();
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] Propane_l = line.split(cvsSplitBy);
                    
                    session.save(new ModelPropaneLiquido(Double.parseDouble(Propane_l[0]), Double.parseDouble(Propane_l[1]), Double.parseDouble(Propane_l[2]), Double.parseDouble(Propane_l[3])));
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
        Criteria cr = this.session.createCriteria(ModelPropaneLiquido.class);
        //cr = this.session.createCriteria(ModelPropaneLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from Propane where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropaneLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelPropaneLiquido> Propanes = consulta.list(); 
        ModelPropaneLiquido Propane1 = Propanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Propane where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropaneLiquido.class));//Sem isso aqui impossível de retornar
	Propanes = consulta.list(); 
        ModelPropaneLiquido Propane2 = Propanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Propane where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropaneLiquido.class));//Sem isso aqui impossível de retornar
	Propanes = consulta.list(); 
        ModelPropaneLiquido Propane3 = Propanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Propane where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropaneLiquido.class));//Sem isso aqui impossível de retornar
	Propanes = consulta.list(); 
        ModelPropaneLiquido Propane4 = Propanes.get(0);
        
        Cpl1 = Propane1.getCPL()+ (Propane2.getCPL()- Propane1.getCPL()) * ((temperatura-Propane1.getTEMPERATURA())/(Propane2.getTEMPERATURA()-Propane1.getTEMPERATURA()));
        Cpl2 = Propane3.getCPL()+ (Propane4.getCPL()- Propane3.getCPL()) * ((temperatura-Propane3.getTEMPERATURA())/(Propane4.getTEMPERATURA()-Propane3.getTEMPERATURA()));
        Cpl = Cpl1 + (Cpl2 - Cpl1) * ((pressao-Propane1.getPRESSAO())/(Propane2.getPRESSAO()-Propane1.getPRESSAO()));
        
        Prl1 = Propane1.getPRL()+ (Propane2.getPRL()- Propane1.getPRL()) * ((temperatura-Propane1.getTEMPERATURA())/(Propane2.getTEMPERATURA()-Propane1.getTEMPERATURA()));
        Prl2 = Propane3.getPRL()+ (Propane4.getPRL()- Propane3.getPRL()) * ((temperatura-Propane3.getTEMPERATURA())/(Propane4.getTEMPERATURA()-Propane3.getTEMPERATURA()));
        Prl = Prl1 + (Prl2 - Prl1) * ((pressao-Propane3.getPRESSAO())/(Propane4.getPRESSAO()-Propane3.getPRESSAO()));
        
        System.out.println(Cpl);
        System.out.println(Prl);
    }    
}
