package dao;

import factory.ConnectionFactory;
import model.Aluno;

import java.sql.*;


public class AlunoDAO {
    private Connection connection;

    public AlunoDAO() {
        try {
            this.connection = ConnectionFactory.getConnection();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao inicializar AlunoDAO: " + e.getMessage(), e);
        }
    }

    public void inserirAluno(Aluno aluno) {
        String sql = "INSERT INTO Aluno (cpf, nome, data_nascimento, peso, altura) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, aluno.getCpf());
            stmt.setString(2, aluno.getNome());
            stmt.setDate(3, Date.valueOf(aluno.getDataNascimento()));
            stmt.setDouble(4, aluno.getPeso());
            stmt.setDouble(5, aluno.getAltura());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir aluno: " + e.getMessage(), e);
        }
    }

    public void excluirAluno(String cpf) {
        String sql = "DELETE FROM Aluno WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir aluno: " + e.getMessage(), e);
        }
    }

    public void atualizarAluno(Aluno aluno) {
        String sql = "UPDATE Aluno SET nome = ?, data_nascimento = ?, peso = ?, altura = ? WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setDate(2, Date.valueOf(aluno.getDataNascimento()));
            stmt.setDouble(3, aluno.getPeso());
            stmt.setDouble(4, aluno.getAltura());
            stmt.setString(5, aluno.getCpf());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aluno: " + e.getMessage(), e);
        }
    }

    public Aluno consultaAluno(String cpf) {
        String sql = "SELECT * FROM Aluno WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Aluno(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getDouble("peso"),
                        rs.getDouble("altura")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar aluno: " + e.getMessage(), e);
        }
        return null;
    }

}
