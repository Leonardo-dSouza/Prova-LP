package dao;

import model.Aluno;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    private Connection connection;

    public AlunoDAO() {
        try {
            // Conexão com o banco de dados
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/LP", "root", "kali");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage());
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
            throw new RuntimeException("Erro ao inserir aluno: " + e.getMessage());
        }
    }

    public void excluirAluno(String cpf) {
        String sql = "DELETE FROM Aluno WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir aluno: " + e.getMessage());
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
            throw new RuntimeException("Erro ao atualizar aluno: " + e.getMessage());
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
            throw new RuntimeException("Erro ao consultar aluno: " + e.getMessage());
        }
        return null;
    }

    public List<Aluno> listarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM Aluno";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getDouble("peso"),
                        rs.getDouble("altura")
                );
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos: " + e.getMessage());
        }
        return alunos;
    }

    public void gravaIMC(Aluno aluno) {
        double imc = aluno.calculaIMC();
        String interpretacao = interpretaIMC(imc);
        String filename = "imc_" + aluno.getCpf() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("Data do cálculo: " + LocalDate.now());
            writer.newLine();
            writer.write("CPF: " + aluno.getCpf());
            writer.newLine();
            writer.write("Nome: " + aluno.getNome());
            writer.newLine();
            writer.write("IMC: " + imc);
            writer.newLine();
            writer.write("Interpretação: " + interpretacao);
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar IMC no arquivo: " + e.getMessage());
        }
    }

    private String interpretaIMC(double imc) {
        if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc >= 18.5 && imc < 24.9) {
            return "Peso normal";
        } else if (imc >= 25 && imc < 29.9) {
            return "Sobrepeso";
        } else {
            return "Obesidade";
        }
    }
}
