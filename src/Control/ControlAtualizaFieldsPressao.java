/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Ciclo2.ModelBomba;
import Model.Ciclo2.ModelCondensador;
import Model.Ciclo2.ModelEvaporador;
import Model.Ciclo2.ModelRegenerador;
import Model.Ciclo2.ModelTurbina;
import View.Bomba.ViewBombaPanelRankine;
import View.Condensador.ViewCondensadorPanelRankine;
import View.Evaporador.ViewEvaporadorPanelRankine;
import View.Regenerador.ViewRegeneradorPanelRankine;
import View.Turbina.ViewTurbinaPanelRankine;
import java.awt.Component;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author leonardo
 */
public class ControlAtualizaFieldsPressao {
    Session session;
    ControlPrincipal ctrlPrincipal;

    public ControlAtualizaFieldsPressao(Session session, ControlPrincipal ctrlPrincipal, double valor, int field) {
        this.session = session;
        this.ctrlPrincipal = ctrlPrincipal;
        Component[] components;
        
        switch(field){
            case 1:
                components = ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().getComponents();
                for(Component c: components){
                    if(c.getName().equals("Turbina")){
                        ViewTurbinaPanelRankine panel = (ViewTurbinaPanelRankine)c;
                        panel.getFieldPressaoEntr().setSelectedItem(valor);
                        break;
                    }
                }
                atualizaCaso1(valor);
                break;
            
            case 2:
                components = ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().getComponents();
                for(Component c: components){
                    if(c.getName().equals("Evaporador")){
                        ViewEvaporadorPanelRankine panel = (ViewEvaporadorPanelRankine)c;
                        panel.getFieldPressaoSai().setSelectedItem(valor);
                        break;
                    }
                }
                atualizaCaso2(valor);
                break;
                
            case 3:
                components = ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().getComponents();
                for(Component c: components){
                    if(c.getName().equals("Regenerador")){
                        ViewRegeneradorPanelRankine panel = (ViewRegeneradorPanelRankine)c;
                        panel.getFieldPressaoEntr().setSelectedItem(valor);
                        break;
                    }
                }
                atualizaCaso3(valor);
                break;
                
            case 4:
                components = ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().getComponents();
                for(Component c: components){
                    if(c.getName().equals("Turbina")){
                        ViewTurbinaPanelRankine panel = (ViewTurbinaPanelRankine)c;
                        panel.getFieldPressaoSai().setSelectedItem(valor);
                        break;
                    }
                }
                atualizaCaso4(valor);
                break;
            case 5:
                components = ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().getComponents();
                for(Component c: components){
                    if(c.getName().equals("Condensador")){
                        ViewCondensadorPanelRankine panel = (ViewCondensadorPanelRankine)c;
                        panel.getFieldPressaoEntr().setSelectedItem(valor);
                        break;
                    }
                }
                atualizaCaso5(valor);
                break;
            case 6:
                components = ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().getComponents();
                for(Component c: components){
                    if(c.getName().equals("Regenerador")){
                        ViewRegeneradorPanelRankine panel = (ViewRegeneradorPanelRankine)c;
                        panel.getFieldPressaoSai().setSelectedItem(valor);
                        break;
                    }
                }
                atualizaCaso6(valor);
                break;
            case 7:
                components = ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().getComponents();
                for(Component c: components){
                    if(c.getName().equals("Bomba")){
                        ViewBombaPanelRankine panel = (ViewBombaPanelRankine)c;
                        panel.getFieldPressaoEntr().setSelectedItem(valor);
                        break;
                    }
                }
                atualizaCaso7(valor);
                break;
            case 8:
                components = ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().getComponents();
                for(Component c: components){
                    if(c.getName().equals("Condensador")){
                        ViewCondensadorPanelRankine panel = (ViewCondensadorPanelRankine)c;
                        panel.getFieldPressaoSai().setSelectedItem(valor);
                        break;
                    }
                }
                atualizaCaso8(valor);
                break;
            case 9:
                components = ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().getComponents();
                for(Component c: components){
                    if(c.getName().equals("Regenerador")){
                        ViewRegeneradorPanelRankine panel = (ViewRegeneradorPanelRankine)c;
                        panel.getFieldPressaoEntr().setSelectedItem(valor);
                        break;
                    }
                }
                atualizaCaso9(valor);
                break;
            case 10:
                components = ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().getComponents();
                for(Component c: components){
                    if(c.getName().equals("Bomba")){
                        ViewBombaPanelRankine panel = (ViewBombaPanelRankine)c;
                        panel.getFieldPressaoSai().setSelectedItem(valor);
                        break;
                    }
                }
                atualizaCaso10(valor);
                break;
            case 11:
                components = ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().getComponents();
                for(Component c: components){
                    if(c.getName().equals("Evaporador")){
                        ViewEvaporadorPanelRankine panel = (ViewEvaporadorPanelRankine)c;
                        panel.getFieldPressaoEntr().setSelectedItem(valor);
                        break;
                    }
                }
                atualizaCaso11(valor);
                break;
            case 12:
                components = ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().getComponents();
                for(Component c: components){
                    if(c.getName().equals("Regenerador")){
                        ViewRegeneradorPanelRankine panel = (ViewRegeneradorPanelRankine)c;
                        panel.getFieldPressaoSai().setSelectedItem(valor);
                        break;
                    }
                }
                atualizaCaso12(valor);
                break;
        }
    }
    
    private void atualizaCaso1(double valor){
        Criteria cr = session.createCriteria(ModelTurbina.class);
        List result = cr.list();
        Transaction tx = session.beginTransaction();

        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelTurbina)result.get(i)).getPressaoEntr(), valor)){
                return;
            }
        }
        
        if(result.size() < 5){
            ModelTurbina m1 = new ModelTurbina();
            m1.setPressaoEntr(valor);
            session.saveOrUpdate(m1);
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelTurbina m1 = (ModelTurbina) result.get(i);
                ModelTurbina m2 = (ModelTurbina) result.get(i-1);

                m1.setPressaoEntr(m2.getPressaoEntr());

                session.saveOrUpdate(m1);
            }

            ModelTurbina m1 = (ModelTurbina) result.get(0);
            m1.setPressaoEntr(valor);

            session.saveOrUpdate(m1);
        }
        tx.commit();
    }
    
    private void atualizaCaso2(double valor){
        Criteria cr = session.createCriteria(ModelEvaporador.class);
        List result = cr.list();
        Transaction tx = session.beginTransaction();

        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelEvaporador)result.get(i)).getPressaoSai(), valor)){
                return;
            }
        }
        
        if(result.size() < 5){
            ModelEvaporador m1 = new ModelEvaporador();
            m1.setPressaoSai(valor);
            session.saveOrUpdate(m1);
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelEvaporador m1 = (ModelEvaporador) result.get(i);
                ModelEvaporador m2 = (ModelEvaporador) result.get(i-1);

                m1.setPressaoSai(m2.getPressaoSai());

                session.saveOrUpdate(m1);
            }

            ModelEvaporador m1 = (ModelEvaporador) result.get(0);
            m1.setPressaoSai(valor);

            session.saveOrUpdate(m1);
        }
        tx.commit();
    }
    
    private void atualizaCaso3(double valor){
        Criteria cr = session.createCriteria(ModelRegenerador.class);
        List result = cr.list();
        Transaction tx = session.beginTransaction();

        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelRegenerador)result.get(i)).getPressaoEntr(), valor)){
                return;
            }
        }
        
        if(result.size() < 5){
            ModelRegenerador m1 = new ModelRegenerador();
            m1.setPressaoEntr(valor);
            session.saveOrUpdate(m1);
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelRegenerador m1 = (ModelRegenerador) result.get(i);
                ModelRegenerador m2 = (ModelRegenerador) result.get(i-1);

                m1.setPressaoEntr(m2.getPressaoEntr());

                session.saveOrUpdate(m1);
            }

            ModelRegenerador m1 = (ModelRegenerador) result.get(0);
            m1.setPressaoEntr(valor);

            session.saveOrUpdate(m1);
        }
        tx.commit();
    }
    
    private void atualizaCaso4(double valor){
        Criteria cr = session.createCriteria(ModelTurbina.class);
        List result = cr.list();
        Transaction tx = session.beginTransaction();

        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelTurbina)result.get(i)).getPressaoSai(), valor)){
                return;
            }
        }
        
        if(result.size() < 5){
            ModelTurbina m1 = new ModelTurbina();
            m1.setPressaoSai(valor);
            session.saveOrUpdate(m1);
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelTurbina m1 = (ModelTurbina) result.get(i);
                ModelTurbina m2 = (ModelTurbina) result.get(i-1);

                m1.setPressaoSai(m2.getPressaoSai());

                session.saveOrUpdate(m1);
            }

            ModelTurbina m1 = (ModelTurbina) result.get(0);
            m1.setPressaoSai(valor);

            session.saveOrUpdate(m1);
        }
        tx.commit();
    }
    
    private void atualizaCaso5(double valor){
        Criteria cr = session.createCriteria(ModelCondensador.class);
        List result = cr.list();
        Transaction tx = session.beginTransaction();

        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelCondensador)result.get(i)).getPressaoEntr(), valor)){
                return;
            }
        }
        
        if(result.size() < 5){
            ModelCondensador m1 = new ModelCondensador();
            m1.setPressaoEntr(valor);
            session.saveOrUpdate(m1);
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelCondensador m1 = (ModelCondensador) result.get(i);
                ModelCondensador m2 = (ModelCondensador) result.get(i-1);

                m1.setPressaoEntr(m2.getPressaoEntr());

                session.saveOrUpdate(m1);
            }

            ModelCondensador m1 = (ModelCondensador) result.get(0);
            m1.setPressaoEntr(valor);

            session.saveOrUpdate(m1);
        }
        tx.commit();
    }
    
    private void atualizaCaso6(double valor){
        Criteria cr = session.createCriteria(ModelRegenerador.class);
        List result = cr.list();
        Transaction tx = session.beginTransaction();

        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelRegenerador)result.get(i)).getPressaoSai(), valor)){
                return;
            }
        }
        
        if(result.size() < 5){
            ModelRegenerador m1 = new ModelRegenerador();
            m1.setPressaoSai(valor);
            session.saveOrUpdate(m1);
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelRegenerador m1 = (ModelRegenerador) result.get(i);
                ModelRegenerador m2 = (ModelRegenerador) result.get(i-1);

                m1.setPressaoSai(m2.getPressaoSai());

                session.saveOrUpdate(m1);
            }

            ModelRegenerador m1 = (ModelRegenerador) result.get(0);
            m1.setPressaoSai(valor);

            session.saveOrUpdate(m1);
        }
        tx.commit();
    }
    
    private void atualizaCaso7(double valor) {
        Criteria cr = session.createCriteria(ModelBomba.class);
        List result = cr.list();
        Transaction tx = session.beginTransaction();

        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelBomba)result.get(i)).getPressaoEntr(), valor)){
                return;
            }
        }
        
        if(result.size() < 5){
            ModelBomba m1 = new ModelBomba();
            m1.setPressaoEntr(valor);
            session.saveOrUpdate(m1);
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelBomba m1 = (ModelBomba) result.get(i);
                ModelBomba m2 = (ModelBomba) result.get(i-1);

                m1.setPressaoEntr(m2.getPressaoEntr());

                session.saveOrUpdate(m1);
            }

            ModelBomba m1 = (ModelBomba) result.get(0);
            m1.setPressaoEntr(valor);

            session.saveOrUpdate(m1);
        }
        tx.commit();
    }
    
    private void atualizaCaso8(double valor){
        Criteria cr = session.createCriteria(ModelCondensador.class);
        List result = cr.list();
        Transaction tx = session.beginTransaction();

        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelCondensador)result.get(i)).getPressaoSai(), valor)){
                return;
            }
        }
        
        if(result.size() < 5){
            ModelCondensador m1 = new ModelCondensador();
            m1.setPressaoSai(valor);
            session.saveOrUpdate(m1);
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelCondensador m1 = (ModelCondensador) result.get(i);
                ModelCondensador m2 = (ModelCondensador) result.get(i-1);

                m1.setPressaoSai(m2.getPressaoSai());

                session.saveOrUpdate(m1);
            }

            ModelCondensador m1 = (ModelCondensador) result.get(0);
            m1.setPressaoSai(valor);
            session.saveOrUpdate(m1);
        }
        tx.commit();
        
    }
    
    private void atualizaCaso9(double valor) {
        Criteria cr = session.createCriteria(ModelRegenerador.class);
        List result = cr.list();
        Transaction tx = session.beginTransaction();

        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelRegenerador)result.get(i)).getPressaoEntr(), valor)){
                return;
            }
        }
        
        if(result.size() < 5){
            ModelRegenerador m1 = new ModelRegenerador();
            m1.setPressaoEntr(valor);
            session.saveOrUpdate(m1);
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelRegenerador m1 = (ModelRegenerador) result.get(i);
                ModelRegenerador m2 = (ModelRegenerador) result.get(i-1);

                m1.setPressaoEntr(m2.getPressaoEntr());

                session.saveOrUpdate(m1);
            }
            
            ModelRegenerador m1 = (ModelRegenerador) result.get(0);
            m1.setPressaoEntr(valor);

            session.saveOrUpdate(m1);
        }
        tx.commit();
    }
    
    private void atualizaCaso10(double valor){
        Criteria cr = session.createCriteria(ModelBomba.class);
        List result = cr.list();
        Transaction tx = session.beginTransaction();

        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelBomba)result.get(i)).getPressaoSai(), valor)){
                return;
            }
        }
        
        if(result.size() < 5){
            ModelBomba m1 = new ModelBomba();
            m1.setPressaoSai(valor);
            session.saveOrUpdate(m1);
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelBomba m1 = (ModelBomba) result.get(i);
                ModelBomba m2 = (ModelBomba) result.get(i-1);

                m1.setPressaoSai(m2.getPressaoSai());

                session.saveOrUpdate(m1);
            }

            ModelBomba m1 = (ModelBomba) result.get(0);
            m1.setPressaoSai(valor);
            session.saveOrUpdate(m1);
        }
        tx.commit();   
    }
    
    private void atualizaCaso11(double valor) {
        Criteria cr = session.createCriteria(ModelEvaporador.class);
        List result = cr.list();
        Transaction tx = session.beginTransaction();

        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelEvaporador)result.get(i)).getPressaoEntr(), valor)){
                return;
            }
        }
        
        if(result.size() < 5){
            ModelEvaporador m1 = new ModelEvaporador();
            m1.setPressaoEntr(valor);
            session.saveOrUpdate(m1);
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelEvaporador m1 = (ModelEvaporador) result.get(i);
                ModelEvaporador m2 = (ModelEvaporador) result.get(i-1);

                m1.setPressaoEntr(m2.getPressaoEntr());

                session.saveOrUpdate(m1);
            }
            
            ModelEvaporador m1 = (ModelEvaporador) result.get(0);
            m1.setPressaoEntr(valor);

            session.saveOrUpdate(m1);
        }
        tx.commit();
    }
    
    private void atualizaCaso12(double valor){
        Criteria cr = session.createCriteria(ModelRegenerador.class);
        List result = cr.list();
        Transaction tx = session.beginTransaction();

        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelRegenerador)result.get(i)).getPressaoSai(), valor)){
                return;
            }
        }
        
        if(result.size() < 5){
            ModelRegenerador m1 = new ModelRegenerador();
            m1.setPressaoSai(valor);
            session.saveOrUpdate(m1);
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelRegenerador m1 = (ModelRegenerador) result.get(i);
                ModelRegenerador m2 = (ModelRegenerador) result.get(i-1);

                m1.setPressaoSai(m2.getPressaoSai());

                session.saveOrUpdate(m1);
            }

            ModelRegenerador m1 = (ModelRegenerador) result.get(0);
            m1.setPressaoSai(valor);
            session.saveOrUpdate(m1);
        }
        tx.commit();   
    }
    
}


    