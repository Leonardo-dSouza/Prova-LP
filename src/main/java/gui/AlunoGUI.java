package gui;

import dao.AlunoDAO;
import model.Aluno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class AlunoGUI extends JFrame {
    private JTextField tfCpf, tfNome, tfDataNascimento, tfPeso, tfAltura;
    private JButton btnInserir, btnConsultar, btnAtualizar, btnExcluir, btnCalcularIMC;
    private AlunoDAO alunoDAO;

    public AlunoGUI() {
        alunoDAO = new AlunoDAO();

        setTitle("Gerenciamento de Alunos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new GridLayout(8, 2));

        // Labels e campos de texto
        add(new JLabel("CPF:"));
        tfCpf = new JTextField();
        add(tfCpf);

        add(new JLabel("Nome:"));
        tfNome = new JTextField();
        add(tfNome);

        add(new JLabel("Data de Nascimento (YYYY-MM-DD):"));
        tfDataNascimento = new JTextField();
        add(tfDataNascimento);

        add(new JLabel("Peso (kg):"));
        tfPeso = new JTextField();
        add(tfPeso);

        add(new JLabel("Altura (m):"));
        tfAltura = new JTextField();
        add(tfAltura);

        // Botões
        btnInserir = new JButton("Inserir");
        btnConsultar = new JButton("Consultar");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");
        btnCalcularIMC = new JButton("Calcular IMC");

        add(btnInserir);
        add(btnConsultar);
        add(btnAtualizar);
        add(btnExcluir);
        add(btnCalcularIMC);

        // Ações dos botões
        btnInserir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Aluno aluno = new Aluno(
                            tfCpf.getText(),
                            tfNome.getText(),
                            LocalDate.parse(tfDataNascimento.getText()),
                            Double.parseDouble(tfPeso.getText()),
                            Double.parseDouble(tfAltura.getText())
                    );
                    alunoDAO.inserirAluno(aluno);
                    JOptionPane.showMessageDialog(null, "Aluno inserido com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao inserir aluno: " + ex.getMessage());
                }
            }
        });

        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Aluno aluno = alunoDAO.consultaAluno(tfCpf.getText());
                    if (aluno != null) {
                        tfNome.setText(aluno.getNome());
                        tfDataNascimento.setText(aluno.getDataNascimento().toString());
                        tfPeso.setText(String.valueOf(aluno.getPeso()));
                        tfAltura.setText(String.valueOf(aluno.getAltura()));
                        JOptionPane.showMessageDialog(null, "Aluno encontrado!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Aluno não encontrado!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao consultar aluno: " + ex.getMessage());
                }
            }
        });

        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Aluno aluno = new Aluno(
                            tfCpf.getText(),
                            tfNome.getText(),
                            LocalDate.parse(tfDataNascimento.getText()),
                            Double.parseDouble(tfPeso.getText()),
                            Double.parseDouble(tfAltura.getText())
                    );
                    alunoDAO.atualizarAluno(aluno);
                    JOptionPane.showMessageDialog(null, "Aluno atualizado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar aluno: " + ex.getMessage());
                }
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    alunoDAO.excluirAluno(tfCpf.getText());
                    JOptionPane.showMessageDialog(null, "Aluno excluído com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir aluno: " + ex.getMessage());
                }
            }
        });

        btnCalcularIMC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Aluno aluno = alunoDAO.consultaAluno(tfCpf.getText());
                    if (aluno != null) {
                        alunoDAO.gravaIMC(aluno);
                        double imc = aluno.calculaIMC();
                        String interpretacao = imc < 18.5 ? "Abaixo do peso" : (imc < 24.9 ? "Peso normal" : (imc < 29.9 ? "Sobrepeso" : "Obesidade"));
                        JOptionPane.showMessageDialog(null, "IMC calculado: " + imc + " (" + interpretacao + ")");
                    } else {
                        JOptionPane.showMessageDialog(null, "Aluno não encontrado!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao calcular IMC: " + ex.getMessage());
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new AlunoGUI();
    }
}
