package br.com.connectall.pousada.models;

import java.util.*;

public class Pousada {

    private List<Reserva> reservas;

    public Pousada(List<Reserva> reservas) {
        this.reservas = new ArrayList<>();
    }

    public List<Reserva> getReservas() {
        return reservas;
    }


}
