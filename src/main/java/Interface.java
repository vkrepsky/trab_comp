import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interface {

	private JFrame frame;
	File openFile;
	JTextArea textArea;
	JTextArea textMsg;
	String textoCopiado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // TELA FICA IGUAL DO S.O
					Interface window = new Interface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// CRIA O APP
	public Interface() {
		initialize();
	}

	// INICIALIZA OS CONTEUDOS DO FRAME
	private void initialize() {

		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\gpzan\\Pictures\\icon\\compilar.png"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLUE);
		frame.setTitle("COMPILADOR EQUIPE 4");
		frame.setMinimumSize(new Dimension(900, 600));
		frame.pack();
		frame.getContentPane().setLayout(new BorderLayout());

		// PANEL MENU LATERAL
		JPanel panelLateral = new JPanel();
		panelLateral.setMinimumSize(new Dimension(150, 150));
		panelLateral.setPreferredSize(new Dimension(150, 600));
		panelLateral.setBackground(SystemColor.controlHighlight);
		frame.getContentPane().add(panelLateral, BorderLayout.WEST);

		// PANEL DA BARRA DE ESTATUS
		JPanel barraStatus = new JPanel();
		// barraStatus.setSize(886, -31);
		barraStatus.setLocation(0, 568);
		barraStatus.setPreferredSize(new Dimension(25, 25));
		barraStatus.setBackground(Color.WHITE);
		frame.getContentPane().add(barraStatus, BorderLayout.SOUTH);

		// LABEL QUE FICA EMBAIXO
		JLabel lblPath = new JLabel();
		lblPath.setHorizontalAlignment(SwingConstants.CENTER);
		lblPath.setBackground(Color.BLACK);
		lblPath.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblPath.setBounds(10, -12, 0, 0);
		barraStatus.add(lblPath);

		// SPLIT PANEL ONDE ESTAO EDITOR DE TEXTO E A AREA DE MENSAGENS, ASSIM COMO OS
		// SCROLLS
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.8);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setBounds(150, 0, 736, 541);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		// EDITOR DE TEXTO
		textArea = new JTextArea();
		textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
		splitPane.setLeftComponent(textArea);

		// AREA DE MSG DE TEXTO COMPILADOR
		textMsg = new JTextArea();
		textMsg.setEnabled(false);
		textMsg.setFont(new Font("Consolas", Font.PLAIN, 14));
		splitPane.setRightComponent(textMsg);

		// ADD SCROLL
		JScrollPane scrollTextArea = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JScrollPane scrollTextMsg = new JScrollPane(textMsg, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		splitPane.add(scrollTextArea);
		splitPane.add(scrollTextMsg);

		// NOVO BOTAO
		JButton btnNovo = new JButton("Novo [ctrl-n]");
		btnNovo.setIcon(new ImageIcon("C:\\Users\\gpzan\\Pictures\\icon\\novo.png"));
		btnNovo.setPreferredSize(new Dimension(150, 48));
		panelLateral.add(btnNovo);
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textMsg.setText(null);
				novo();
				lblPath.setText("");
			}
		});
		btnNovo.setForeground(Color.DARK_GRAY);
		btnNovo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnNovo.setBackground(SystemColor.control);
		btnNovo.setHorizontalAlignment(SwingConstants.LEADING);

		// ABRIR BOTAO
		JButton btnAbrir = new JButton("Abrir [ctrl-o]");
		btnAbrir.setIcon(new ImageIcon("C:\\Users\\gpzan\\Pictures\\icon\\abrir.png"));
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrir();
				lblPath.setText(pastaNome());
			}
		});
		btnAbrir.setForeground(Color.DARK_GRAY);
		btnAbrir.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnAbrir.setBackground(SystemColor.control);
		btnAbrir.setHorizontalAlignment(SwingConstants.LEADING);
		btnAbrir.setPreferredSize(new Dimension(150, 48));
		panelLateral.add(btnAbrir);

		// SALVAR BOTAO
		JButton btnSalvar = new JButton("Salvar [ctrl-s]");
		btnSalvar.setIcon(new ImageIcon("C:\\Users\\gpzan\\Pictures\\icon\\salvar.png"));
		btnSalvar.setSelectedIcon(null);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
				lblPath.setText(pastaNome());
			}
		});
		btnSalvar.setForeground(Color.DARK_GRAY);
		btnSalvar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnSalvar.setBackground(SystemColor.control);
		btnSalvar.setHorizontalAlignment(SwingConstants.LEADING);
		btnSalvar.setPreferredSize(new Dimension(150, 48));
		panelLateral.add(btnSalvar);

		JButton btnCopiar = new JButton("Copiar [ctrl-c]");
		btnCopiar.setIcon(new ImageIcon("C:\\Users\\gpzan\\Pictures\\icon\\copiar.png"));
		btnCopiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copiar();
			}
		});
		btnCopiar.setForeground(Color.DARK_GRAY);
		btnCopiar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnCopiar.setBackground(SystemColor.control);
		btnCopiar.setHorizontalAlignment(SwingConstants.LEADING);
		btnCopiar.setPreferredSize(new Dimension(150, 48));
		panelLateral.add(btnCopiar);

		JButton btnColar = new JButton("Colar [ctrl-v]");
		btnColar.setIcon(new ImageIcon("C:\\Users\\gpzan\\Pictures\\icon\\colar.png"));
		btnColar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colar();
			}
		});
		btnColar.setForeground(Color.DARK_GRAY);
		btnColar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnColar.setBackground(SystemColor.control);
		btnColar.setHorizontalAlignment(SwingConstants.LEADING);
		btnColar.setPreferredSize(new Dimension(150, 48));
		panelLateral.add(btnColar);

		JButton btnRecortar = new JButton("Recortar [ctrl-x]");
		btnRecortar.setIcon(new ImageIcon("C:\\Users\\gpzan\\Pictures\\icon\\recortar.png"));
		btnRecortar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recortar();
			}
		});
		btnRecortar.setForeground(Color.DARK_GRAY);
		btnRecortar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnRecortar.setBackground(SystemColor.control);
		btnRecortar.setHorizontalAlignment(SwingConstants.LEADING);
		btnRecortar.setPreferredSize(new Dimension(150, 48));
		panelLateral.add(btnRecortar);

		JButton btnComp = new JButton("Compilar [F7]");
		btnComp.setIcon(new ImageIcon("C:\\Users\\gpzan\\Pictures\\icon\\compilar.png"));
		btnComp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compilar();
			}
		});
		btnComp.setForeground(Color.DARK_GRAY);
		btnComp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnComp.setBackground(SystemColor.control);
		btnComp.setHorizontalAlignment(SwingConstants.LEADING);
		btnComp.setPreferredSize(new Dimension(150, 48));
		panelLateral.add(btnComp);

		JButton btnEquipe = new JButton("Equipe [F1]");
		btnEquipe.setIcon(new ImageIcon("C:\\Users\\gpzan\\Pictures\\icon\\equipe.png"));
		btnEquipe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textMsg.setText("Gabriel Pietro Zandon�, Gabriel Theindl Dallarosa, Victor Weiers Krepsky");
			}
		});

		btnEquipe.setForeground(Color.DARK_GRAY);
		btnEquipe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnEquipe.setBackground(SystemColor.control);
		btnEquipe.setHorizontalAlignment(SwingConstants.LEADING);
		btnEquipe.setPreferredSize(new Dimension(150, 48));
		panelLateral.add(btnEquipe);

		// KEY LISTENER
		textArea.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

				// CTRL + n
				if (e.isControlDown() && e.getKeyCode() == 78) {
					novo();
					textMsg.setText(null);
					lblPath.setText("");
				}

				// CTRL + o
				if (e.isControlDown() && e.getKeyCode() == 79) {
					abrir();
					lblPath.setText(pastaNome());
					textMsg.setText("");
				}

				// CTRL + s
				if (e.isControlDown() && e.getKeyCode() == 83) {
					salvar();
					lblPath.setText(pastaNome());
					textMsg.setText("");
				}

				switch (e.getKeyCode()) {
				// f7
				case 118:
					compilar();
					break;
				// f1
				case 112:
					textMsg.setText("Gabriel Pietro Zandon�, Gabriel Theindl Dallarosa, Victor Weiers Krepsky");
					break;
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

		});
	}

	//
	// copiar CTRL + X
	//
	private void copiar() {
		textoCopiado = textArea.getText();
	}

	//
	// colar CTRL + V
	//
	private void colar() {
		textArea.setText(textoCopiado);
	}

	//
	// recortar CTRL + X
	//
	private void recortar() {
		textoCopiado = textArea.getText();
		textArea.setText("");
	}

	// RETORNA SOMENTE A PASTA\NOME DO CAMINHO DO ARQUIVO
	private String pastaNome() {

		String pastaNome = "";
		byte contaBarra = 0;

		if (openFile.exists()) {
			for (int n = openFile.toString().length() - 1; contaBarra < 2; n--) {

				if (openFile.toString().charAt(n - 1) == (char) 92) {
					contaBarra++;
				}

				pastaNome += openFile.toString().charAt(n);
			}

			pastaNome = (new StringBuilder(pastaNome).reverse().toString());
		}

		return pastaNome;
	}

	private void novo() {

		textArea.setText("");
		openFile = null;

	}

	private void abrir() {

		try {

			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Seleciona um arquivo de texto para abrir");
			chooser.showOpenDialog(null);

			openFile = chooser.getSelectedFile();
			if (!openFile.exists()) {
				// adicionar mensagem de erro
				openFile = null;
				return;
			}

			Scanner reader = new Scanner(openFile);
			String contents = "";
			while (reader.hasNextLine()) {
				contents += reader.nextLine() + "\n";
			}
			reader.close();
			textArea.setText(contents);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void salvar() {

		if (openFile == null) {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Salvar novo arquivo");
			chooser.showSaveDialog(null);

			File selectedFile = chooser.getSelectedFile();
			String contents = textArea.getText();

			Formatter form;
			try {
				form = new Formatter(selectedFile);
				form.format("%s", contents);
				form.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			openFile = selectedFile;

		} else {
			String contents = textArea.getText();
			Formatter form;
			try {
				form = new Formatter(openFile);
				form.format("%s", contents);
				form.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	private void compilar()  {
		Lexico lexico = new Lexico();
		String msg = "LINHA		    CLASSE			LEXEMA" + "\n";
		int linha = 1;

		lexico.setInput(textArea.getText());

		String s = textArea.getText();
		String[] ss = s.split(" ");

		Pattern p = Pattern.compile("\n");

		Matcher m = p.matcher(s);

		int count = 0;
		while (m.find()) {

			count++;
		}
		System.out.println(count);

		try
		{
			Token t = null;
			while((t = lexico.nextToken()) != null ){

				for ( String s1: ss) {

					if (s1.contains("\n")){
						String[] arr = s1.split("\n");

						for (String s2: arr) {
							msg += linha +    "        " + (tokenId(t.getId())) + "        " + (t.getLexeme()) + "\n";
							linha++;
							t = lexico.nextToken();
						}
						linha--;
					}else {
						msg += linha +    "        " + (tokenId(t.getId())) + "        " + (t.getLexeme()) + "\n";
						t = lexico.nextToken();
					}
				}

			}

		}
		catch ( Exception e )
		{
		    textMsg.setText(e.getMessage() + " " + e.getLocalizedMessage());
		}
		
		textMsg.setText(msg);
	}


	
	private String tokenId(int id) {
		
		switch (id) {
		
		case 2:
			return "identificador    ";
		
		case 3:
			return "constante int    ";
			
		case 4:
			return "constante float  ";
			
		case 5:
			return "constante char   ";
			
		case 6:
			return "constante string ";
		}
		
		if(id > 16) {
			return "s�mbolo especial ";
		}
		
		if(id <= 17 && id >= 7) {
			return "palavra reservada";
		}
		return "";
	}
	
}
