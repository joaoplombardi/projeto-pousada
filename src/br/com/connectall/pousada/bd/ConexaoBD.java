package br.com.connectall.pousada.bd;

import br.com.connectall.pousada.models.Quarto;
import br.com.connectall.pousada.models.Reserva;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConexaoBD {

    private Connection conn;

    public ConexaoBD() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.conn = DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl", "RM86433",
                    "110701");
        } catch (ClassNotFoundException e) {
            System.err.println("O driver não foi encontrado!: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Ocorreu um erro na conexão com o Banco de Dados: " + e.getMessage());
        }
    }

    public List<Reserva> consultar() throws SQLException {

        Statement stmnt = this.conn.createStatement();
        ResultSet resultReserva = stmnt.executeQuery("select * from t_pousada_reserva");
        List<Reserva> reservas = new ArrayList<>();
        while (resultReserva.next()) {

            Integer id = resultReserva.getInt("id_reserva");
            Integer quarto = resultReserva.getInt("id_quarto");
            Date dataEntrada = resultReserva.getDate("dt_entrada");
            Date dataSaida = resultReserva.getDate("dt_saida");
            Integer qtdePessoas = resultReserva.getInt("qt_pessoas");

            LocalDate dtEntrada = dataEntrada.toLocalDate();


            ConexaoQuarto cq = new ConexaoQuarto();
//            List<Quarto> quartoFiltrado = cq.consultarTodosQuartos().stream()
//                  .filter(quartoFiltro -> quarto == quartoFiltro.getNumero())
//                  .collect(Collectors.toList());
//            Quarto quartoFinal = quartoFiltrado.get(0);
            Quarto quartoFinal = cq.retornaQuarto(quarto);
            if (dataSaida != null) {
                LocalDate dtSaida = dataSaida.toLocalDate();
                reservas.add(new Reserva(id, quartoFinal, dtEntrada, dtSaida, qtdePessoas));

            } else {
                reservas.add(new Reserva(id, quartoFinal, dtEntrada, qtdePessoas));
            }
        }

        return reservas;

    }

    public Reserva retornaUmaReserva(int numeroQuarto) throws SQLException {
        ConexaoQuarto cq = new ConexaoQuarto();

        Statement stmnt = this.conn.createStatement();
        String sql = String.format("Select * from t_pousada_reserva where id_quarto = " + numeroQuarto + " and dt_saida is null");
        ResultSet result = stmnt.executeQuery(sql);

        if (result.next()) {
            Integer id = result.getInt("id_reserva");
            Integer idQuarto = result.getInt("id_quarto");
            java.sql.Date dt_entrada = result.getDate("dt_entrada");
            Integer qtPessoas = result.getInt("id_reserva");

            LocalDate dtEntrada = dt_entrada.toLocalDate();

            Reserva reserva = new Reserva(id, cq.retornaQuarto(idQuarto), dtEntrada, qtPessoas);

            return reserva;
        } else {
            return null;
        }

    }

    public void salvar(Reserva reserva) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Statement stmnt = this.conn.createStatement();
        String dtEntrada = reserva.getDataEntrada().format(formatter);
        String sqlCommand = String.format("insert into t_pousada_reserva(id_reserva, id_quarto, dt_entrada, qt_pessoas)" +
                        "values(sq_reserva.nextval, %s, to_date('%s', 'dd/mm/yyyy'), %s)",
                reserva.getQuarto().getNumero(),
                dtEntrada,
                reserva.getQtdePessoas());
        stmnt.executeUpdate(sqlCommand);
        this.desconectaBanco(this.conn);
    }

    public void uptadeReserva(Reserva reserva) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Statement stmnt = this.conn.createStatement();
        String sql = String.format("update t_pousada_reserva set dt_saida = to_date('%s' , 'dd/mm/yyyy') where id_reserva = %s",
                reserva.getDataSaida().format(formatter),
                reserva.getId());
        stmnt.executeUpdate(sql);
        this.desconectaBanco(this.conn);
    }

    public void desconectaBanco(Connection conn) throws SQLException {
        if (!conn.isClosed()) {
            conn.close();
        }
    }

    public void desconectaBanco() throws SQLException {
        if (!this.conn.isClosed()) {
            this.conn.close();
        }
    }

}
