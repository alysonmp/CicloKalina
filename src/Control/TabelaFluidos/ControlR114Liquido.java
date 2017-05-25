/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelR114Liquido;
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
public class ControlR114Liquido {
    
    Session session;
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;
    
    public ControlR114Liquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR114Liquido(){
        String csvFile = "src/Csv/R114_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR114Liquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R114_l = line.split(cvsSplitBy);
                    
                    session.save(new ModelR114Liquido(Double.parseDouble(R114_l[0]), Double.parseDouble(R114_l[1]), Double.parseDouble(R114_l[2]), Double.parseDouble(R114_l[3])));
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
        Criteria cr = this.session.createCriteria(ModelR114Liquido.class);
        //cr = this.session.createCriteria(ModelR114Liquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R114 where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR114Liquido.class));//Sem isso aqui impossível de retornar
	List<ModelR114Liquido> R114s = consulta.list(); 
        ModelR114Liquido R1141 = R114s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R114 where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR114Liquido.class));//Sem isso aqui impossível de retornar
	R114s = consulta.list(); 
        ModelR114Liquido R1142 = R114s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R114 where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR114Liquido.class));//Sem isso aqui impossível de retornar
	R114s = consulta.list(); 
        ModelR114Liquido R1143 = R114s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R114 where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR114Liquido.class));//Sem isso aqui impossível de retornar
	R114s = consulta.list(); 
        ModelR114Liquido R1144 = R114s.get(0);
        
        Cpl1 = R1141.getCPL()+ (R1142.getCPL()- R1141.getCPL()) * ((temperatura-R1141.getTEMPERATURA())/(R1142.getTEMPERATURA()-R1141.getTEMPERATURA()));
        Cpl2 = R1143.getCPL()+ (R1144.getCPL()- R1143.getCPL()) * ((temperatura-R1143.getTEMPERATURA())/(R1144.getTEMPERATURA()-R1143.getTEMPERATURA()));
        Cpl = Cpl1 + (Cpl2 - Cpl1) * ((pressao-R1141.getPRESSAO())/(R1142.getPRESSAO()-R1141.getPRESSAO()));
        
        Prl1 = R1141.getPRL()+ (R1142.getPRL()- R1141.getPRL()) * ((temperatura-R1141.getTEMPERATURA())/(R1142.getTEMPERATURA()-R1141.getTEMPERATURA()));
        Prl2 = R1143.getPRL()+ (R1144.getPRL()- R1143.getPRL()) * ((temperatura-R1143.getTEMPERATURA())/(R1144.getTEMPERATURA()-R1143.getTEMPERATURA()));
        Prl = Prl1 + (Prl2 - Prl1) * ((pressao-R1143.getPRESSAO())/(R1144.getPRESSAO()-R1143.getPRESSAO()));
        
        System.out.println(Cpl);
        System.out.println(Prl);
    }    
}
