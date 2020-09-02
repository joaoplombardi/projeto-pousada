package br.com.connectall.pousada;

import br.com.connectall.pousada.bd.ConexaoBD;
import br.com.connectall.pousada.bd.ConexaoQuarto;
import br.com.connectall.pousada.models.Pousada;
import br.com.connectall.pousada.models.Quarto;
import br.com.connectall.pousada.models.Reserva;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    public static void criandoQuartos() {
        ConexaoQuarto cq = new ConexaoQuarto();

        try {
            for (int i = 11; i < 26; i++) {
                if (i <= 20) {
                    Quarto quarto = new Quarto(i, 5, 50.0);
                    cq.salvarQuarto(quarto);
                } else {
                    Quarto quarto = new Quarto(i, 10, 100.0);
                    cq.salvarQuarto(quarto);
                }
            }
            cq.desconectaBanco();
        } catch (SQLException e) {
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

            switch (opcao) {
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
        } while (opcao != 0);
        scan.close();
    }

    private static void realizarReserva(Scanner scan) {
        Reserva reserva = new Reserva();

        ConexaoBD cbd = new ConexaoBD();
        ConexaoQuarto cq = new ConexaoQuarto();

        int numeroQuarto = 0;
        System.out.println(" ________________________________________________ ");
        System.out.println("|                                                |");
        System.out.println("| Apartamentos de 11 a 20 | VIP de 21 a 25       |");
        System.out.print("| Digite o quarto que deseja: ");
        numeroQuarto = scan.nextInt();
        scan.nextLine();
        Quarto quarto = cq.retornaQuarto(numeroQuarto);

        int qtdePessoas = 0;
        System.out.print("| Quantas pessoas ficarão hospedadas:");
        qtdePessoas = scan.nextInt();
        scan.nextLine();
        if (quarto.getMaxPessoas() < qtdePessoas) {
            System.err.println("O número de hospedes excede o limite do quarto.");
            System.exit(0);
        } else {
            try {
                if (cq.verificaDisponibilidadeQuarto(numeroQuarto)) {
                    reserva.setQuarto(cq.retornaQuarto(numeroQuarto));
                    reserva.setDataEntrada(LocalDate.now());
                    reserva.setQtdePessoas(qtdePessoas);
                    cbd.salvar(reserva);
                    System.out.println("| Sua reserva foi realizada!                     |");
                    System.out.println(" ------------------------------------------------ ");
                    menu();
                } else {
                    System.err.print("O quarto solicitado está reservado!");
                    menu();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            cbd.desconectaBanco();
            cq.desconectaBanco();
        } catch (SQLException e) {
            System.err.println("A conexão com o Banco de Dados não finalizou");
        }


    }

    private static void consultarReservas() {
        Pousada pousada = new Pousada();
        pousada.retornarPousada();
        menu();
    }

    private static void finalizarReserva() {
        ConexaoBD cbd = new ConexaoBD();
        Scanner scan = new Scanner(System.in);
        Reserva reserva = new Reserva();

        Integer numeroQuarto;

        System.out.println("Digite o numero do quarto que estava hospedado: ");
        numeroQuarto = scan.nextInt();
        scan.nextLine();
        try {
            reserva.setId(cbd.retornaUmaReserva(numeroQuarto).getId());
            reserva.setQuarto(cbd.retornaUmaReserva(numeroQuarto).getQuarto());
            reserva.setDataEntrada(cbd.retornaUmaReserva(numeroQuarto).getDataEntrada());
            reserva.setQtdePessoas(cbd.retornaUmaReserva(numeroQuarto).getQtdePessoas());

            long diferancaDeDias = ChronoUnit.DAYS.between(reserva.getDataEntrada(), LocalDate.now());

            if (diferancaDeDias >= 2){
                System.out.printf("Reserva finalizada!\nO valor a ser pago é R$%s", reserva.calcularValorFinal());
                System.out.println(" ------------------------------------------------ ");

                cbd.uptadeReserva(reserva);
            }else {
                System.err.println("Você precisa ficar hospedado no minimo dois dias na pousada.");
            }

            menu();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
