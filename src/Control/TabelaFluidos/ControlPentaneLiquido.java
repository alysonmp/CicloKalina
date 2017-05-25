/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelPentaneLiquido;
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
public class ControlPentaneLiquido {
    
    Session session;
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;
    
    public ControlPentaneLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaPentaneLiquido(){
        String csvFile = "src/Csv/PENTANE_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelPentaneLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] Pentane_l = line.split(cvsSplitBy);
                    
                    session.save(new ModelPentaneLiquido(Double.parseDouble(Pentane_l[0]), Double.parseDouble(Pentane_l[1]), Double.parseDouble(Pentane_l[2]), Double.parseDouble(Pentane_l[3])));
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
    
    public void interpolacaoButano(double pressao, double temperatura){
        Criteria cr = this.session.createCriteria(ModelPentaneLiquido.class);
        //cr = this.session.createCriteria(ModelPentaneLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from Pentane where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPentaneLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelPentaneLiquido> Pentanes = consulta.list(); 
        ModelPentaneLiquido Pentane1 = Pentanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Pentane where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPentaneLiquido.class));//Sem isso aqui impossível de retornar
	Pentanes = consulta.list(); 
        ModelPentaneLiquido Pentane2 = Pentanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Pentane where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPentaneLiquido.class));//Sem isso aqui impossível de retornar
	Pentanes = consulta.list(); 
        ModelPentaneLiquido Pentane3 = Pentanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Pentane where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPentaneLiquido.class));//Sem isso aqui impossível de retornar
	Pentanes = consulta.list(); 
        ModelPentaneLiquido Pentane4 = Pentanes.get(0);
        
        Cpl1 = Pentane1.getCPL()+ (Pentane2.getCPL()- Pentane1.getCPL()) * ((temperatura-Pentane1.getTEMPERATURA())/(Pentane2.getTEMPERATURA()-Pentane1.getTEMPERATURA()));
        Cpl2 = Pentane3.getCPL()+ (Pentane4.getCPL()- Pentane3.getCPL()) * ((temperatura-Pentane3.getTEMPERATURA())/(Pentane4.getTEMPERATURA()-Pentane3.getTEMPERATURA()));
        Cpl = Cpl1 + (Cpl2 - Cpl1) * ((pressao-Pentane1.getPRESSAO())/(Pentane2.getPRESSAO()-Pentane1.getPRESSAO()));
        
        Prl1 = Pentane1.getPRL()+ (Pentane2.getPRL()- Pentane1.getPRL()) * ((temperatura-Pentane1.getTEMPERATURA())/(Pentane2.getTEMPERATURA()-Pentane1.getTEMPERATURA()));
        Prl2 = Pentane3.getPRL()+ (Pentane4.getPRL()- Pentane3.getPRL()) * ((temperatura-Pentane3.getTEMPERATURA())/(Pentane4.getTEMPERATURA()-Pentane3.getTEMPERATURA()));
        Prl = Prl1 + (Prl2 - Prl1) * ((pressao-Pentane3.getPRESSAO())/(Pentane4.getPRESSAO()-Pentane3.getPRESSAO()));
        
        System.out.println(Cpl);
        System.out.println(Prl);
    }    
}
