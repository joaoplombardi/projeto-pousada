package br.com.connectall.pousada;
import br.com.connectall.pousada.bd.ConexaoBD;
import br.com.connectall.pousada.bd.ConexaoQuarto;
import br.com.connectall.pousada.models.Reserva;

import java.sql.SQLException;
import java.time.LocalDate;
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


        LocalDate dataEntrada = LocalDate.now();

        int qtdePessoas = 0;
        System.out.println("Quantas pessoas ficarão hospedadas: ");
        qtdePessoas = scan.nextInt();
        scan.nextLine();


        try {
            if (cq.verificaDisponibilidadeQuarto(numeroQuarto)){
                reserva.setQuarto(cq.retornaQuarto(numeroQuarto));
                reserva.setDataEntrada(dataEntrada);
                reserva.setQtdePessoas(qtdePessoas);
                cbd.salvar(reserva);
            }else {
                System.err.println("O quarto solicitado está reservado!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void consultarReservas() {


    }

    private static void finalizarReserva() {


    }


}
