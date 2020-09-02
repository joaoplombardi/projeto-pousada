package br.com.connectall.pousada.models;

import br.com.connectall.pousada.bd.ConexaoBD;

import java.sql.SQLException;
import java.util.List;

public class Pousada {

    private List<Reserva> reservas;

    public Pousada() {
        try {
            ConexaoBD cbd = new ConexaoBD();
            this.reservas = cbd.consultar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void retornarPousada() {
        this.reservas.forEach(System.out::println);
    }

}
