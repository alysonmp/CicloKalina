/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.Ciclo2.ModelFluidos;
import Model.TabelasFluidos.ModelButanoLiquido;
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
public class ControlButanoLiquido {
    
    Session session;
    private double variavel1, variavel2;
    private double variavel11, variavel12, variavel21, variavel22;
    
    public ControlButanoLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaButano(){
        String csvFile = "src/Csv/Butano_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "\t";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelButanoLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                Transaction tx = session.beginTransaction();
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] butano_l = line.split(cvsSplitBy);
                    
                    session.save(new ModelButanoLiquido(Double.parseDouble(butano_l[0]), Double.parseDouble(butano_l[1]), Double.parseDouble(butano_l[2]), Double.parseDouble(butano_l[3])));
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
        Criteria cr = this.session.createCriteria(ModelHexane.class);
        //cr = this.session.createCriteria(ModelHexane.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from Hexane where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHexane.class));//Sem isso aqui impossível de retornar
	List<ModelButanoLiquido> hexanes = consulta.list(); 
        ModelButanoLiquido hexane1 = hexanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Hexane where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHexane.class));//Sem isso aqui impossível de retornar
	hexanes = consulta.list(); 
        ModelButanoLiquido hexane2 = hexanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Hexane where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHexane.class));//Sem isso aqui impossível de retornar
	hexanes = consulta.list(); 
        ModelButanoLiquido hexane3 = hexanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Hexane where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHexane.class));//Sem isso aqui impossível de retornar
	hexanes = consulta.list(); 
        ModelButanoLiquido hexane4 = hexanes.get(0);
        
        variavel11 = hexane1.getVARIAVEL1()+ (hexane2.getVARIAVEL1()- hexane1.getVARIAVEL1()) * ((temperatura-hexane1.getTEMPERATURA())/(hexane2.getTEMPERATURA()-hexane1.getTEMPERATURA()));
        variavel12 = hexane3.getVARIAVEL1()+ (hexane4.getVARIAVEL1()- hexane3.getVARIAVEL1()) * ((temperatura-hexane3.getTEMPERATURA())/(hexane4.getTEMPERATURA()-hexane3.getTEMPERATURA()));
        variavel1 = variavel11 + (variavel12 - variavel11) * ((pressao-hexane1.getPRESSAO())/(hexane3.getPRESSAO()-hexane1.getPRESSAO()));
        
        variavel21 = hexane1.getVARIAVEL1()+ (hexane2.getVARIAVEL1()- hexane1.getVARIAVEL1()) * ((temperatura-hexane1.getTEMPERATURA())/(hexane2.getTEMPERATURA()-hexane1.getTEMPERATURA()));
        variavel22 = hexane3.getVARIAVEL1()+ (hexane4.getVARIAVEL1()- hexane3.getVARIAVEL1()) * ((temperatura-hexane3.getTEMPERATURA())/(hexane4.getTEMPERATURA()-hexane3.getTEMPERATURA()));
        variavel2 = variavel21 + (variavel22 - variavel21) * ((pressao-hexane1.getPRESSAO())/(hexane3.getPRESSAO()-hexane1.getPRESSAO()));
        
        System.out.println(variavel1);
        System.out.println(variavel2);
    }
    
}
