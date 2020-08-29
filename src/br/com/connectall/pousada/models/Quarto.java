package br.com.connectall.pousada.models;

public class Quarto {

    private int numero;
    private Categoria categoria;
    private int maxPessoas;
    private double valorDiaria;

    public Quarto() {
    }


    public Quarto(int numero, Categoria categoria, int maxPessoas, double valorDiaria) {
        this.numero = numero;
        this.categoria = categoria;
        this.maxPessoas = maxPessoas;
        this.valorDiaria = valorDiaria;
    }


    public int getNumero() {
        return numero;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public int getMaxPessoas() {
        return maxPessoas;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setMaxPessoas(int maxPessoas) {
        this.maxPessoas = maxPessoas;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

}
