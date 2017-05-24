package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelD4Liquido;
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
 * @author leonardo
 */
public class ControlD4Liquido {
    private Session session;
    
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;

    public ControlD4Liquido(Session session) {
        this.session = this.session;
    }
    
    public void criaTabelaD4Liquido(){
        String csvFile = "src/Csv/D4_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelD4Liquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                Transaction tx = session.beginTransaction();
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] d4_liquido = line.split(csvSplitBy);
                    
                    this.session.save(new ModelD4Liquido(Double.parseDouble(d4_liquido[0]),Double.parseDouble(d4_liquido[1]),Double.parseDouble(d4_liquido[2]),Double.parseDouble(d4_liquido[3])));   
                }
                tx.commit();
            }
            
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void interpolacaoD4Liquido(double pressao, double temperatura){
        Criteria cr = this.session.createCriteria(ModelD4Liquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from d4_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD4Liquido.class));//Sem isso aqui impossível de retornar
	List<ModelD4Liquido> d4_liquido = consulta.list(); 
        ModelD4Liquido d4_liquido1 = d4_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from d4_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD4Liquido.class));//Sem isso aqui impossível de retornar
	d4_liquido = consulta.list(); 
        ModelD4Liquido d4_liquido2 = d4_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from d4_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD4Liquido.class));//Sem isso aqui impossível de retornar
	d4_liquido = consulta.list(); 
        ModelD4Liquido d4_liquido3 = d4_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from d4_liquido where pressao >= " +pressao+ " and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD4Liquido.class));//Sem isso aqui impossível de retornar
	d4_liquido = consulta.list(); 
        ModelD4Liquido d4_liquido4 = d4_liquido.get(0);
     
        double p1 = ((pressao - d4_liquido1.getPRESSAO())/(d4_liquido2.getPRESSAO() - d4_liquido1.getPRESSAO()));
        double p2 = ((pressao - d4_liquido3.getPRESSAO())/(d4_liquido4.getPRESSAO() - d4_liquido3.getPRESSAO()));
        double t1 = ((temperatura - d4_liquido1.getTEMPERATURA())/(d4_liquido2.getTEMPERATURA() - d4_liquido1.getTEMPERATURA()));
        double t2 = ((temperatura - d4_liquido3.getTEMPERATURA())/(d4_liquido4.getTEMPERATURA() - d4_liquido3.getTEMPERATURA()));
        
        Cpl1 = d4_liquido1.getCPL()+ (d4_liquido2.getCPL()- d4_liquido1.getCPL()) * t1;
        Cpl2 = d4_liquido3.getCPL()+ (d4_liquido4.getCPL()- d4_liquido3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p1;
        
        Prl1 = d4_liquido1.getPRL()+ (d4_liquido2.getPRL()- d4_liquido1.getPRL()) * t1;
        Prl2 = d4_liquido3.getPRL()+ (d4_liquido4.getPRL()- d4_liquido3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p2;
    }
    
}
