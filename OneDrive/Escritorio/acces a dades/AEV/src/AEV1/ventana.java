package AEV1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Button;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.TextArea;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.Font;

public class ventana extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textInp;
	private JTextField textCoin;
	private JTextField textSust;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventana frame = new ventana();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ventana() {
		TextArea textArea = new TextArea();
		JCheckBox chckbxMay = new JCheckBox("Mayúsculas");
		chckbxMay.setFont(new Font("Tahoma", Font.PLAIN, 13));
		JCheckBox chckbxAc = new JCheckBox("Acentos");
		chckbxAc.setFont(new Font("Tahoma", Font.PLAIN, 13));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 642, 556);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		Button InfoBut = new Button("Info");
		InfoBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File dir = new File(textInp.getText());
				textArea.setText(Info(dir, 0, ""));
			}
		});
		InfoBut.setBounds(194, 57, 119, 27);
		contentPane.add(InfoBut);

		textInp = new JTextField();
		textInp.setBounds(35, 57, 132, 27);
		contentPane.add(textInp);
		textInp.setColumns(10);

		textArea.setEditable(false);
		textArea.setBounds(35, 236, 563, 226);
		contentPane.add(textArea);

		textCoin = new JTextField();
		textCoin.setBounds(341, 57, 132, 27);
		contentPane.add(textCoin);
		textCoin.setColumns(10);

		Button btnCoin = new Button("Coincidencias");
		btnCoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File dir = new File(textInp.getText());
				textArea.setText(Coin(dir, "", 0, textCoin.getText(), chckbxMay.isSelected(), chckbxAc.isSelected()));
			}
		});
		btnCoin.setBounds(479, 57, 119, 27);
		contentPane.add(btnCoin);

		chckbxMay.setBounds(341, 174, 93, 21);
		contentPane.add(chckbxMay);

		chckbxAc.setBounds(470, 171, 93, 27);
		contentPane.add(chckbxAc);

		textSust = new JTextField();
		textSust.setColumns(10);
		textSust.setBounds(35, 153, 132, 27);
		contentPane.add(textSust);

		Button btnSust = new Button("Sustituir");
		btnSust.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File dir = new File(textInp.getText());
				textArea.setText(Reescritura(dir, "", 0, textCoin.getText(), textSust.getText(), chckbxMay.isSelected(),
						chckbxAc.isSelected()));
			}
		});
		btnSust.setBounds(194, 153, 119, 27);
		contentPane.add(btnSust);

		JLabel lblDir = new JLabel("Directorio principal");
		lblDir.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDir.setBounds(35, 20, 132, 27);
		contentPane.add(lblDir);

		JLabel lblPalabra = new JLabel("Palabra a buscar");
		lblPalabra.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPalabra.setBounds(341, 20, 132, 27);
		contentPane.add(lblPalabra);

		JLabel lblPalabraParaSustituir = new JLabel("Palabra para sustituir");
		lblPalabraParaSustituir.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPalabraParaSustituir.setBounds(35, 106, 155, 27);
		contentPane.add(lblPalabraParaSustituir);

		JLabel lblCar = new JLabel("Características a tener en cuenta");
		lblCar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCar.setBounds(341, 121, 222, 27);
		contentPane.add(lblCar);
	}

	// funció encarregada de buscar les coincidències dins dels documents

	public String Busc(String arch, String text, boolean may, boolean ac) {
		/*
		 * num equival a la quantitat de coincidències trobades linea és el String on es
		 * guardaran les línies de text dels documents
		 */
		int num = 0;
		String linea;
		// Creem lect, un BufferedReader,la qual cosa ens permetrà llegir els documents
		try (BufferedReader lect = new BufferedReader(new FileReader(arch))) {
			// Este if i les seues variants, s´encarreguen de comprovar quin checkbox està
			// marcat
			// per a tindre en compte unes condicions o altres
			if (!may && !ac) {
				// Adaptem el text del document en text depenent de les condicions marcades
				text = Normalizer.normalize(text.toLowerCase(), Normalizer.Form.NFD).replaceAll("\\p{M}", "");
				/*
				 * posa la línia del document en linea fins que la próxima línia és equivalent a
				 * null i ind s´encarrega de dir la posició de la paraula que està buscant i per
				 * cada una aumenta el valor de num
				 */
				while ((linea = lect.readLine()) != null) {
					int ind = Normalizer.normalize(linea.toLowerCase(), Normalizer.Form.NFD).replaceAll("\\p{M}", "")
							.indexOf(text);
					while (ind != -1) {
						num++;
						ind = linea.indexOf(text, ind + 1);
					}
				}
			} else if (!may && ac) {
				text = text.toLowerCase();
				while ((linea = lect.readLine()) != null) {
					int ind = linea.toLowerCase().indexOf(text);
					while (ind != -1) {
						num++;
						ind = linea.indexOf(text, ind + 1);
					}
				}
			} else if (may && !ac) {
				text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
				while ((linea = lect.readLine()) != null) {
					int ind = Normalizer.normalize(linea, Normalizer.Form.NFD).replaceAll("\\p{M}", "").indexOf(text);
					while (ind != -1) {
						num++;
						ind = linea.indexOf(text, ind + 1);
					}
				}
			} else {
				while ((linea = lect.readLine()) != null) {
					int ind = linea.indexOf(text);
					while (ind != -1) {
						num++;
						ind = linea.indexOf(text, ind + 1);
					}
				}
			}
			// retorna num
			return String.valueOf(num);
		} catch (IOException e) {
			return String.valueOf(num);
		}

	}

	// funció que s´encarrega d´escriure tota la informació del directori a un
	// String incloent les coincidéncies

	public String Coin(File dir, String res, int num, String coin, boolean may, boolean ac) {
		File[] cont = dir.listFiles();
		if (cont.length > 0) {

			for (File c : cont) {
				for (int i = 0; i < num; i++) {
					res += "|-- ";
				}
				if (c.isDirectory()) {
					res += "/" + c.getName() + "\n";
					res = Coin(c, res, num + 1, coin, may, ac);
				} else {
					res += c.getName() + "(" + Busc(c.getAbsolutePath(), coin, may, ac) + "coincidéncias)\n";
				}
			}
		}
		return res;
	}

	// funció similar a Coin però que retorna l´última modificació dels documents
	public String Info(File dir, int num, String res) {
		File[] cont = dir.listFiles();
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if (cont.length > 0) {

			for (File c : cont) {
				for (int i = 0; i < num; i++) {
					res += "|-- ";
				}
				if (c.isDirectory()) {
					res += "/" + c.getName() + "\n";
					res = Info(c, num + 1, res);
				} else {
					res += c.getName() + "(" + c.length() / 1000 + "KB - " + fecha.format(c.lastModified()) + ")\n";
				}
			}
		}
		return res;
	}

	// funció similar a Busc però que retorna el num de canvis dels documents
	public String Sust(File arch, String text, String nuevo, boolean may, boolean ac) {
		int num = 0;
		String linea = "";
		//ací a demés de crear lect, creem write, que ens permetrà crear un nou document
		try (BufferedReader lect = new BufferedReader(new FileReader(arch.getAbsolutePath()));
				BufferedWriter write = new BufferedWriter(new FileWriter(arch.getParent() + "/1" + arch.getName()))) {
			if (!may && !ac) {
				text = Normalizer.normalize(text.toLowerCase(), Normalizer.Form.NFD).replaceAll("\\p{M}", "");
				while ((linea = lect.readLine()) != null) {
					int ind = Normalizer.normalize(linea.toLowerCase(), Normalizer.Form.NFD).replaceAll("\\p{M}", "")
							.indexOf(text);
					while (ind != -1) {
						num++;
						ind = linea.indexOf(text, ind + 1);
					}
					// a nlin escriurem el text del document principal canviant la palabra coincident amb la nova
					String nlin = Normalizer.normalize(linea.toLowerCase(), Normalizer.Form.NFD)
							.replaceAll("\\p{M}", "").replaceAll(text, nuevo);
					// escrivim el nou document 
					write.write(nlin);
					write.newLine();
				}
			} else if (!may && ac) {
				text = text.toLowerCase();
				while ((linea = lect.readLine()) != null) {
					int ind = linea.toLowerCase().indexOf(text);
					while (ind != -1) {
						num++;
						ind = linea.indexOf(text, ind + 1);

					}
					String nlin = linea.toLowerCase().replace(text, nuevo);
					write.write(nlin);
					write.newLine();
				}
			} else if (may && !ac) {
				text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
				while ((linea = lect.readLine()) != null) {
					int ind = Normalizer.normalize(linea, Normalizer.Form.NFD).replaceAll("\\p{M}", "").indexOf(text);
					while (ind != -1) {
						num++;
						ind = linea.indexOf(text, ind + 1);
					}
					String nlin = Normalizer.normalize(linea, Normalizer.Form.NFD).replaceAll("\\p{M}", "")
							.replace(text, nuevo);
					write.write(nlin);
					write.newLine();
				}
			} else {
				while ((linea = lect.readLine()) != null) {
					int ind = linea.indexOf(text);
					while (ind != -1) {
						num++;
						ind = linea.indexOf(text, ind + 1);
					}
					String nlin = linea.replace(text, nuevo);
					write.write(nlin);
					write.newLine();
				}
			}
			write.close();
		} catch (IOException e) {

		}
		return String.valueOf(num);
	}

	// Similar a la funció Coin però que retorna la quantitat de canvis en els
	// documents
	public String Reescritura(File dir, String res, int num, String coin, String nuevo, boolean may, boolean ac) {
		File[] cont = dir.listFiles();
		if (cont.length > 0) {

			for (File c : cont) {
				for (int i = 0; i < num; i++) {
					res += "|-- ";
				}
				if (c.isDirectory()) {
					res += "/" + c.getName() + "\n";
					res = Reescritura(c, res, num + 1, coin, nuevo, may, ac);
				} else {
					res += c.getName() + "(" + Sust(c, coin, nuevo, may, ac) + "sustituciones)\n";
				}
			}
		}
		return res;
	}
}