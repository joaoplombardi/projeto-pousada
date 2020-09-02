package br.com.connectall.pousada.bd;

import br.com.connectall.pousada.models.Categoria;
import br.com.connectall.pousada.models.Quarto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConexaoQuarto {
    private Connection conn;

    public ConexaoQuarto() {
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

    public List<Quarto> consultarTodosQuartos() throws SQLException {

        Statement stmnt = this.conn.createStatement();
        ResultSet result = stmnt.executeQuery("select * from t_pousada_quartos");
        List<Quarto> quarto = new ArrayList<>();
        do {
            result.next();

            Integer id = result.getInt("id_quarto");
            Categoria enumCategoria = null;
            if (id > 20 && id <= 25) enumCategoria = Categoria.VIP;
            if (id > 10 && id <= 20) enumCategoria = Categoria.APARTAMENTO;
            Integer maxPessoas = result.getInt("qt_max_pessoas");
            Double valor = result.getDouble("ds_valor_diaria");

            quarto.add(new Quarto(id, enumCategoria, maxPessoas, valor));

        } while (result.next());
        return quarto;
    }

    public Quarto retornaQuarto(int idQuarto) {
        Quarto quarto = new Quarto();

        try {
            Statement stmnt = conn.createStatement();
            ResultSet result = stmnt.executeQuery("select * from t_pousada_quartos where id_quarto = " + idQuarto + "");
            do {
                result.next();
                Integer id = result.getInt("id_quarto");
                String categoria = result.getString("ds_categoria");
                Integer qtdMaxima = result.getInt("qt_max_pessoas");
                Double valorDiaria = result.getDouble("ds_valor_diaria");
                quarto.setNumero(id);
                quarto.setCategoria("VIP".equalsIgnoreCase(categoria) ? Categoria.VIP : Categoria.APARTAMENTO);
                quarto.setMaxPessoas(qtdMaxima);
                quarto.setValorDiaria(valorDiaria);

                return quarto;
            } while (result.next());

        } catch (SQLException e) {
            System.err.println("Erro na busca do quarto!");
            e.printStackTrace();
            return null;
        }


    }

    public boolean verificaDisponibilidadeQuarto(int numero) throws SQLException {
        Statement stmnt = this.conn.createStatement();
        ResultSet result = stmnt.executeQuery("select * from t_pousada_reserva where id_quarto = " + numero + "");
        return !result.next();
    }

    public void salvarQuarto(Quarto quarto) {
        try {
            Statement stmnt = this.conn.createStatement();
            String sql = String.format("insert into t_pousada_quartos(id_quarto, ds_categoria, qt_max_pessoas, ds_valor_diaria) values " +
                            "(%s, '%s', %s, %s)",
                    quarto.getNumero(),
                    quarto.getCategoria().toString(),
                    quarto.getMaxPessoas(),
                    quarto.getValorDiaria());
            stmnt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Não foi possível cadastrar o quarto");
            e.printStackTrace();
        }

    }

    public void desconectaBanco() throws SQLException {
        if (!this.conn.isClosed()) {
            this.conn.close();
        }
    }

    public void desconectaBanco(Connection conn) throws SQLException {
        if (!conn.isClosed()) {
            conn.close();
        }
    }

}
