package br.com.connectall.pousada;
import br.com.connectall.pousada.bd.ConexaoBD;
import br.com.connectall.pousada.bd.ConexaoQuarto;
import br.com.connectall.pousada.models.Quarto;
import br.com.connectall.pousada.models.Reserva;

import java.sql.SQLException;
import java.text.spi.DateFormatProvider;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class PousadaApplication {
    public static void menu() {
        System.out.println(" ______________________________________");
        System.out.println("|                                      |");
        System.out.println("|          Pousada ConnectALL          |");
        System.out.println("|                                      |");
        System.out.println("|                                      |");
        System.out.println("| 1.) Reservar um quarto               |");
        System.out.println("| 2.) Consultar reservas da pousada    |");
        System.out.println("| 3.) Finalizar reserva                |");
        System.out.println("| 0.) Sair                             |");
        System.out.println("|______________________________________|");
    }
    public static void criandoQuartos(){
        ConexaoQuarto cq = new ConexaoQuarto();

//        Quarto quarto1 = new Quarto(10, 5 , 50.0);
//        Quarto quarto2 = new Quarto(11, 5 , 50.0);
//        Quarto quarto3 = new Quarto(12, 5 , 50.0);
//        Quarto quarto4 = new Quarto(13, 5 , 50.0);
//        Quarto quarto5 = new Quarto(14, 5 , 50.0);
//        Quarto quarto6 = new Quarto(15, 5 , 50.0);
//        Quarto quarto7 = new Quarto(16, 5 , 50.0);
//        Quarto quarto8 = new Quarto(17, 5 , 50.0);
//        Quarto quarto9 = new Quarto(18, 5 , 50.0);
//        Quarto quarto10 = new Quarto(19, 5 , 50.0);
//        Quarto quarto11 = new Quarto(20, 10 , 100.0);
//        Quarto quarto12 = new Quarto(21, 10 , 100.0);
//        Quarto quarto13 = new Quarto(22, 10 , 100.0);
//        Quarto quarto14 = new Quarto(23, 10 , 100.0);
//        Quarto quarto15 = new Quarto(24, 10 , 100.0);
    try {
        for (int i = 11; i < 26; i++){
            if (i <= 20){
                Quarto quarto = new Quarto(i, 5 , 50.0);
                cq.salvarQuarto(quarto);
            }else {
                Quarto quarto = new Quarto(i, 10 , 100.0);
                cq.salvarQuarto(quarto);
            }
        }
        cq.desconectaBanco();
    }catch (SQLException e){
        e.printStackTrace();
    }


    }

    public static void main(String[] args) {
        menu();
        int opcao;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println(">> ");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao){
                case 1:
                    realizarReserva(scan);
                    break;
                case 2:
                    consultarReservas();
                    break;
                case 3:
                    finalizarReserva();
                    break;

            }
        }while (opcao != 0);
        scan.close();
    }
    private static void realizarReserva(Scanner scan) {
        Reserva reserva = new Reserva();

        ConexaoBD cbd = new ConexaoBD();
        ConexaoQuarto cq = new ConexaoQuarto();

        int numeroQuarto = 0;
        System.out.println("Digite o quarto que deseja: ");
        numeroQuarto = scan.nextInt();
        scan.nextLine();
        Quarto quarto = cq.retornaQuarto(numeroQuarto);

        int qtdePessoas = 0;
        System.out.println("Quantas pessoas ficarão hospedadas: ");
        qtdePessoas = scan.nextInt();
        scan.nextLine();
        if (quarto.getMaxPessoas() < qtdePessoas){
            System.err.println("O número de hospedes excede o limite do quarto.");
        }else {
            try {
                if (cq.verificaDisponibilidadeQuarto(numeroQuarto)){
                    reserva.setQuarto(cq.retornaQuarto(numeroQuarto));
                    reserva.setDataEntrada(LocalDate.now());
                    reserva.setQtdePessoas(qtdePessoas);
                    cbd.salvar(reserva);
                }else {
                    System.err.println("O quarto solicitado está reservado!");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        System.out.println("Sua reserva foi realizada!");
        try{
            cbd.desconectaBanco();
            cq.desconectaBanco();
        }catch (SQLException e){
            System.err.println("A conexão com o Banco de Dados não finalizou");
        }




    }

    private static void consultarReservas() {
        ConexaoBD cbd = new ConexaoBD();
        try {
            List<Reserva> reservas = cbd.consultar();
            reservas.forEach(System.out::println);
        }catch (SQLException e){
            System.err.println("Erro ao consultar as reservas.");
            e.printStackTrace();
        }
    }

    private static void finalizarReserva() {


    }


}
