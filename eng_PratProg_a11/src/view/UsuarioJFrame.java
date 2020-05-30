package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import handlers.UsuarioSocket;

@SuppressWarnings("serial")
public class UsuarioJFrame extends JFrame implements ActionListener {

    JLabel tituloJLabel = new JLabel("Cliente");
    JLabel enderecoJLabel = new JLabel("Endereço:");
    JTextField enderecoJText = new JTextField("localhost");
    JTextField usuarioJText = new JTextField("");
    JLabel usuarioJLabel = new JLabel("Nome:");
    JLabel portaJLabel = new JLabel("Porta:");
    JTextField portaJText = new JTextField("12345");
    JButton conectarJButton = new JButton("Conectar");
    JLabel mensagensJLabel = new JLabel("Mensagens");
    JTextArea mensagensJTextArea = new JTextArea();
    JTextField mensagemJText = new JTextField();
    JButton enviarJButton = new JButton("Enviar");
    JLabel conectadoJLabel = new JLabel("");
    
    UsuarioSocket clienteSocket = new UsuarioSocket();
    
    public UsuarioJFrame() {
        
        tituloJLabel.setBounds(170, 0, 60, 20);
        enderecoJLabel.setBounds(10, 20, 80, 20);
        enderecoJText.setBounds(85, 20, 100, 20);
        portaJLabel.setBounds(190, 20, 60, 20);
        portaJText.setBounds(240, 20, 60, 20);
        usuarioJText.setBounds(240, 60, 60, 20);
        usuarioJLabel.setBounds(190, 60, 60, 20);
        conectarJButton.setBounds(300, 20, 100, 20);
        mensagensJLabel.setBounds(10, 30, 200, 80);
        mensagensJTextArea.setBounds(10, 90, 350, 150);
        mensagemJText.setBounds(10, 250, 250, 50);
        enviarJButton.setBounds(260, 250, 100, 50);
        conectadoJLabel.setBounds(130, 15, 200, 80);
        conectadoJLabel.setForeground(Color.green);
  
        Container caixa = getContentPane();
        caixa.setLayout(null);
        
        caixa.add(tituloJLabel);
        caixa.add(enderecoJLabel);
        caixa.add(enderecoJText);
        caixa.add(portaJLabel);
        caixa.add(portaJText);
        caixa.add(usuarioJText);
        caixa.add(usuarioJLabel);
        caixa.add(conectarJButton);
        caixa.add(mensagensJLabel);
        caixa.add(mensagensJTextArea);
        caixa.add(mensagemJText);
        caixa.add(enviarJButton);
        caixa.add(conectadoJLabel);
        
        conectarJButton.addActionListener(this);
        enviarJButton.addActionListener(this);
        mensagensJTextArea.setEditable(false);
        
        setVisible(true);
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(conectarJButton)) {
            conectadoJLabel.setText("Conexão estabelecida!");
            enderecoJText.setEnabled(false);
            portaJLabel.setEnabled(false);
            conectarJButton.setEnabled(false);
            
            new Thread(){
                @Override
                public void run() {
                    try {
                        clienteSocket.conectar(enderecoJText.getText(), portaJText.getText());
                        clienteSocket.ativarRecebimentoMensagens(mensagensJTextArea);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        conectadoJLabel.setText("");
                        enderecoJText.setEnabled(true);
                        portaJLabel.setEnabled(true);
                        conectarJButton.setEnabled(true);
                    }
                }
                
            }.start();
        }
        
        if (e.getSource().equals(enviarJButton)) {
            try {
                clienteSocket.enviarMensagem(mensagemJText.getText());
                mensagemJText.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        new UsuarioJFrame();
    }
    
}
