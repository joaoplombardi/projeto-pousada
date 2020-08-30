package br.com.connectall.pousada.bd;

import br.com.connectall.pousada.models.Categoria;
import br.com.connectall.pousada.models.Quarto;
import br.com.connectall.pousada.models.Reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConexaoQuarto {
    private Connection conn;

    public List<Quarto> consultarTodosQuartos() throws SQLException {

        Statement stmnt = this.conn.createStatement();
        ResultSet result = stmnt.executeQuery("select * from t_pousada_reservas;");
        List<Quarto> quarto = new ArrayList<>();
        do {

            Integer id = result.getInt("id_quarto");
            Categoria enumCategoria = null;
            if (id > 20 && id <= 25) enumCategoria = Categoria.VIP;
            if (id > 10 && id <= 20) enumCategoria = Categoria.APARTAMENTO;
            Integer maxPessoas = result.getInt("nr_maxpessoas");
            Double valor = result.getDouble("nr_valor");

            quarto.add(new Quarto(id, enumCategoria, maxPessoas, valor));

        } while (result.next());
        return quarto;
    }

    public Quarto retornaQuarto(int idQuarto) {
        Quarto quarto = new Quarto();
        try {
            Statement stmnt = this.conn.createStatement();
            ResultSet result = stmnt.executeQuery("select * from t_pousada_quartos where id_quarto = " + idQuarto + ";");
            do {
                Integer id = result.getInt("id_quarto");
                String categoria = result.getString("ds_categoria");
                Integer qtdMaxima = result.getInt("qt_max_pessoas ");
                Double valorDiaria = result.getDouble("ds_valor_diaria");
                quarto.setNumero(id);
                quarto.setCategoria(categoria.equals("VIP") ? Categoria.VIP : Categoria.APARTAMENTO);
                quarto.setMaxPessoas(qtdMaxima);
                quarto.setValorDiaria(valorDiaria);
            } while (result.next());

        } catch (SQLException e) {
            System.err.println("Erro na busca do quarto!");
            e.printStackTrace();
        }
        return quarto;
    }

    public boolean verificaDisponibilidadeQuarto(int numero) {
        Boolean disponivel = false;
        try {
            Statement stmnt = this.conn.createStatement();
            ResultSet result = stmnt.executeQuery("select * from t_pousada_reservas where id_quarto = " + numero + ";");
            disponivel = result != null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disponivel;
    }

}
