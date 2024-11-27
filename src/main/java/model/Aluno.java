package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Aluno {
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private double peso;
    private double altura;

    public Aluno(String cpf, String nome, LocalDate dataNascimento, double peso, double altura) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.peso = peso;
        this.altura = altura;
    }

    // Getters e setters
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }



    // Metodo para calcular o IMC
    public double calculaIMC() {
        return peso / (altura * altura);
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
            throw new RuntimeException("Erro ao gravar IMC no arquivo: " + e.getMessage(), e);
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
