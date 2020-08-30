package br.com.connectall.pousada.bd;
import br.com.connectall.pousada.models.Quarto;
import br.com.connectall.pousada.models.Reserva;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;
import java.lang.Class;

public class ConexaoBD {

    private Connection conn;
    public ConexaoBD(){

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.conn = DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl", "",
                    "");
        } catch (ClassNotFoundException e) {
            System.err.println("O driver não foi encontrado!: " + e.getMessage());
            e.printStackTrace();
        }catch (SQLException e){
            System.err.println("Ocorreu um erro na conexão com o Banco de Dados: " + e.getMessage());
        }
    }

    public List<Reserva> consultar() throws SQLException{

        Statement stmnt = this.conn.createStatement();
        ResultSet resultReserva = stmnt.executeQuery("select * from t_pousada_reservas");
        List<Reserva> reservas = new ArrayList<>();
        do {

            Integer id = resultReserva.getInt("id_reserva");
            Integer quarto = resultReserva.getInt("id_quarto");
            Date dataEntrada = resultReserva.getDate("dt_entrada");
            Date dataSaida = resultReserva.getDate("dt_saida");
            Integer qtdePessoas = resultReserva.getInt("qt_pessoas");
            
            LocalDate dtEntrada = dataEntrada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dtSaida = dataSaida.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            ConexaoQuarto cq = new ConexaoQuarto();
            List<Quarto> quartoFiltrado = cq.consultarTodosQuartos().stream()
                  .filter(quartoFiltro -> quarto == quartoFiltro.getNumero())
                  .collect(Collectors.toList());
            Quarto quartoFinal = quartoFiltrado.get(0);

            reservas.add(new Reserva(id, quartoFinal, dtEntrada, dtSaida, qtdePessoas));

        }while(resultReserva.next());

        return reservas;

    }

    public void salvar(Reserva reserva) throws SQLException{
        Statement stmnt = this.conn.createStatement();
        String sqlCommand = String.format("insert into t_pousada_reservas(id_reserva, id_quarto, dt_entrada, qt_pessoas)" +
                "values(sq_reserva.nextval, %s, %s, %s);", reserva.getQuarto().getNumero(), reserva.getDataEntrada(), reserva.getQtdePessoas());
        stmnt.execute(sqlCommand);
        this.desconectaBanco(this.conn);
    }

    public void desconectaBanco(Connection conn) throws SQLException{
        if(!conn.isClosed()) conn.close();
    }

}
