package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelButanoGas;
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
public class ControlButanoGas {
    
    private Session session;
    private double temperatura, pressao, variavel1, variavel2, 
            variavel3, variavel4, variavel5;
    private double v11, v12, v21, v22, v31, v32, v41, v42, v51, v52;

    public ControlButanoGas(Session session) {
        this.session = session;
    }
    
    public void criaTabelaButanoGas(){
        String csvFile = "src/Csv/Butane_gas.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelButanoGas.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                Transaction tx = session.beginTransaction();
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] butano_g = line.split(csvSplitBy);
                    
                    this.session.save(new ModelButanoGas(Double.parseDouble(butano_g[0]),Double.parseDouble(butano_g[1]),Double.parseDouble(butano_g[2]),Double.parseDouble(butano_g[3]),Double.parseDouble(butano_g[4]),Double.parseDouble(butano_g[5])));   
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
    
    public void interpolacaoButano(double pressao,double temperatura){
    
        Criteria cr = this.session.createCriteria(ModelButanoGas.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from butano_gas where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelButanoGas.class));
        List<ModelButanoGas> butano_gas = consulta.list();
        ModelButanoGas butano_gas1 = butano_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from butano_gas where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelButanoGas.class));
        butano_gas = consulta.list();
        ModelButanoGas butano_gas2 = butano_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from butano_gas where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelButanoGas.class));
        butano_gas = consulta.list();
        ModelButanoGas butano_gas3 = butano_gas.get(0);
        
        consulta = this.session.createSQLQuery("select * from butano_gas where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        consulta.setResultTransformer(Transformers.aliasToBean(ModelButanoGas.class));
        butano_gas = consulta.list();
        ModelButanoGas butano_gas4 = butano_gas.get(0);
        
        double p = ((pressao - butano_gas1.getPRESSAO())/(butano_gas3.getPRESSAO() - butano_gas1.getPRESSAO()));
        double t = ((temperatura - butano_gas1.getTEMPERATURA())/(butano_gas2.getTEMPERATURA() - butano_gas1.getTEMPERATURA()));
        
        v11 = butano_gas1.getVARIAVEL1() + (butano_gas2.getVARIAVEL1() - butano_gas1.getVARIAVEL1()) * t;
        v12 = butano_gas3.getVARIAVEL1() + (butano_gas4.getVARIAVEL1() - butano_gas3.getVARIAVEL1()) * t;
        variavel1 = v11 + (v12 - v11) * p;
        
        v21 = butano_gas1.getVARIAVEL2() + (butano_gas2.getVARIAVEL2() - butano_gas1.getVARIAVEL2()) * t;
        v22 = butano_gas3.getVARIAVEL2() + (butano_gas4.getVARIAVEL2() - butano_gas3.getVARIAVEL2()) * t;
        variavel2 = v21 + (v22 - v21) * p;
        
        v31 = butano_gas1.getVARIAVEL3() + (butano_gas2.getVARIAVEL3() - butano_gas1.getVARIAVEL3()) * t;
        v32 = butano_gas3.getVARIAVEL3() + (butano_gas4.getVARIAVEL3() - butano_gas3.getVARIAVEL3()) * t;
        variavel3 = v31 + (v32 - v31) * p;
        
        v41 = butano_gas1.getVARIAVEL4() + (butano_gas2.getVARIAVEL4() - butano_gas1.getVARIAVEL4()) * t;
        v42 = butano_gas3.getVARIAVEL4() + (butano_gas4.getVARIAVEL4() - butano_gas3.getVARIAVEL4()) * t;
        variavel4 = v41 + (v42 - v41) * p;
        
        v51 = butano_gas1.getVARIAVEL5() + (butano_gas2.getVARIAVEL5() - butano_gas1.getVARIAVEL5()) * t;
        v52 = butano_gas3.getVARIAVEL5() + (butano_gas4.getVARIAVEL5() - butano_gas3.getVARIAVEL5()) * t;
        variavel5 = v51 + (v52 - v51) * p;
    }
}
