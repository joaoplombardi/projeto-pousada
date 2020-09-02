package br.com.connectall.pousada.models;

public class Quarto {

    private int numero;
    private Categoria categoria;
    private int maxPessoas;
    private double valorDiaria;

    public Quarto() {
    }


    public Quarto(int numero, int maxPessoas, double valorDiaria) {
        this.numero = numero;
        if (numero > 10 && numero <= 20) {
            this.categoria = Categoria.APARTAMENTO;
        } else if (numero > 20 && numero <= 25) {
            this.categoria = Categoria.VIP;
        } else System.err.println("Erro ao cadastrar quarto!");
        this.maxPessoas = maxPessoas;
        this.valorDiaria = valorDiaria;
    }

    public Quarto(int id, Categoria enumCategoria, int maxPessoas, double valor) {
        this.numero = id;
        this.categoria = enumCategoria;
        this.maxPessoas = maxPessoas;
        this.valorDiaria = valor;
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
