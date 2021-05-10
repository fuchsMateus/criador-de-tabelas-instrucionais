package entities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import services.Utilitarios;

public class Tela extends JDialog {

	private static final long serialVersionUID = 6522229125567045020L;
	
	private JLabel l1 = new JLabel("Caminho para salvar a imagem");
	private JLabel l3 = new JLabel("Instruções do AutoCAD");
	
	private JTextField r1 = new JTextField();
	private JTextArea r3 = new JTextArea();
	
	private JButton btn = new JButton("Criar tabela");
	
	private JPanel painel = new JPanel(new GridBagLayout());
	
	public Tela() {
		setSize(new Dimension(540, 540));
		setTitle("CriadorDeTabelasCAD");
		setLocationRelativeTo(null);
		setResizable(false);
		painel.setBackground(Color.white);
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		
		l1.setPreferredSize(new Dimension(350,25));
		painel.add(l1, gridBagConstraints);
		
		r1.setPreferredSize(new Dimension(350,25));
		gridBagConstraints.gridy++;
		painel.add(r1, gridBagConstraints);
		
		gridBagConstraints.gridy++;
		painel.add(Box.createVerticalStrut(20), gridBagConstraints);
		
		gridBagConstraints.gridy++;
		painel.add(Box.createVerticalStrut(20), gridBagConstraints);
		
		l3.setPreferredSize(new Dimension(350,25));
		gridBagConstraints.gridy++;
		painel.add(l3, gridBagConstraints);
		
		
		r3.setBackground(this.getBackground());
		JScrollPane scroll = new JScrollPane (r3);
		scroll.setPreferredSize(new Dimension(350,250));
		gridBagConstraints.gridy++;
		painel.add(scroll, gridBagConstraints);
		
		gridBagConstraints.gridy++;
		painel.add(Box.createVerticalStrut(20), gridBagConstraints);
		
		btn.setPreferredSize(new Dimension(150,25));
		gridBagConstraints.gridy++;
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String caminho = r1.getText();
				for (int i = 0; i < caminho.length(); i++) {
					if(caminho.charAt(i) == '\\') {
						if(caminho.charAt(i+1) != '\\' && caminho.charAt(i-1) != '\\') {
							caminho = caminho.substring(0,i) +'\\'+caminho.substring(i);
							i = 0;
						}
					}
				}
				caminho+= "\\tableImage.png";
				
				String linhasInstrucoes[] = r3.getText().split("\\r?\\n");
				////////////////////////////////////////////////////////////////////////////
				try {
		            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		        } catch(Exception useDefault) {
		        }

		        Object[][] data = Utilitarios.processarInstrucao(linhasInstrucoes);

		        String[] columns = {"Mensagem na linha de comando ou pela Dynamic Input", "Procedimentos para a execução do comando"};

		        JTable table = new JTable(data, columns);
		        table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		        table.getColumnModel().getColumn(0).setMinWidth(350);
		        table.getColumnModel().getColumn(1).setMinWidth(300);
		        for (int i = 0; i < linhasInstrucoes.length; i++) {
					
				}
		       
		        
		       
		        
		        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		        for(int x=0;x<columns.length;x++){
		            table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
		           }
		        
		        JScrollPane scroll = new JScrollPane(table);

		        JPanel p = new JPanel(new BorderLayout());
		        p.add(scroll,BorderLayout.CENTER);

		        JFrame f = new JFrame("Never shown");
		        f.setContentPane(scroll);
		        f.pack();

		        JTableHeader h = table.getTableHeader();
		        Dimension dH = h.getSize();
		        Dimension dT = table.getSize();
		        int x = (int)dH.getWidth();
		        int y = (int)dH.getHeight() + (int)dT.getHeight();

		        scroll.setDoubleBuffered(false);

		        BufferedImage bi = new BufferedImage(
		            (int)x,
		            (int)y,
		            BufferedImage.TYPE_INT_RGB
		            );

		        Graphics g = bi.createGraphics();
		        h.paint(g);
		        g.translate(0,h.getHeight());
		        table.paint(g);
		        g.dispose();

		        ///////////////////////////////////////////////////////////////////
		        
		        File tableImage = new File(caminho);
		        try {
					ImageIO.write(bi, "png", tableImage);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        JOptionPane.showMessageDialog(null, "Imagem criada no diretório: "+caminho);
		    }
			
		});
		painel.add(btn, gridBagConstraints);
		
		
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.CENTER;	
		
		add(painel, BorderLayout.CENTER);
		setVisible(true);
	}
}
