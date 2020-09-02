package br.com.connectall.pousada.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reserva {
    private int id;
    private Quarto quarto;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private Integer qtdePessoas;

    public Reserva() {

    }

    public Reserva(Integer id, Quarto quarto, LocalDate dataEntrada, LocalDate dataSaida, Integer qtdePessoas) {
        this.id = id;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.qtdePessoas = qtdePessoas;
    }

    public Reserva(Integer id, Quarto quarto, LocalDate dataEntrada, Integer qtdePessoas) {
        this.id = id;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        this.qtdePessoas = qtdePessoas;
    }

    public int getId() {
        return id;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public Integer getQtdePessoas() {
        return qtdePessoas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public void setQtdePessoas(Integer qtdePessoas) {
        this.qtdePessoas = qtdePessoas;
    }

    @Override
    public String toString() {
        return String.format("Reserva Numero: %s\n" +
                "Quarto: %s\n" +
                "Data de Check-in: %s\n" +
                "Data de Check-out: %s\n" +
                "Quantidade de pessoas: %s\n" +
                "______________________________", id, quarto.getNumero(), dataEntrada, dataSaida, qtdePessoas);
    }

    public void finalizarReserva() {
        this.dataSaida = LocalDate.now();
    }

    public double calcularValorFinal() {

        this.finalizarReserva();

        long estadiaDias = ChronoUnit.DAYS.between(this.dataEntrada, this.dataSaida);

        double valorFinal = (this.quarto.getValorDiaria() * estadiaDias);

        return valorFinal;

    }

}
