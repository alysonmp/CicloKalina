/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Util;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 *
 * @author alysonmp
 */
public class DropdownComboBox<E> extends JComboBox<E> {

    public DropdownComboBox() {
        //super(list);
        this.setEditable(true);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        this.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                return new JButton() {
                    @Override
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        this.remove(this.getComponent(0));
    }
}