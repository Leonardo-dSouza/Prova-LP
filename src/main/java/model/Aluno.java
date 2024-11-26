package model;

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
}
