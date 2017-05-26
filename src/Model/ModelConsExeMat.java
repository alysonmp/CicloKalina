/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author alysonmp
 */
public class ModelConsExeMat {
    
    private String nome;
    private double[][] valores;

    public ModelConsExeMat() {
    }

    public ModelConsExeMat(String nome, double[][] valores) {
        this.nome = nome;
        this.valores = valores;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double[][] getValores() {
        return valores;
    }

    public void setValores(double[][] valores) {
        this.valores = valores;
    }
    
}
