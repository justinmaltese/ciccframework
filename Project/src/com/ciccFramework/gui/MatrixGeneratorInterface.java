package com.ciccFramework.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JTabbedPane;

import com.ciccFramework.algorithms.ga.GeneticAlgorithm;
import com.ciccFramework.compatibility.BinaryCompatibilityChecker;
import com.ciccFramework.compatibility.CompatibilityChecker;
import com.ciccFramework.compatibility.QaryCompatibilityChecker;
import com.ciccFramework.compatibility.matrix.CompatibilityMatrixGenerator;
import com.ciccFramework.core.ExecutionHandler;
import com.ciccFramework.core.ParameterSet;
import com.ciccFramework.core.CoveringProblem;
import com.ciccFramework.io.CodeWriter;
import com.ciccFramework.schema.AlgorithmSchema;
import com.ciccFramework.schema.XMLSchemaLoader;

import java.awt.Component;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.SwingUtilities;

import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

/* This class serves as a graphical user interface for compatibility matrix generation
 * purposes. A user first selects a maximum value for q,n and r. Then, compatibility matrices
 * are generated sequentially as .matrix files until the maximum parameter values are reached.
 * 
 * 
 * NOTE: Portions of this class were generated using the WindowBuilder WYSIWYG editor for eclipse.
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class MatrixGeneratorInterface {
	// GUI elements accessible globally
	private JFrame frame;
	private TextArea runTextArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MatrixGeneratorInterface window = new MatrixGeneratorInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MatrixGeneratorInterface() {
		
		initialize();
	}
	
	/* This method is responsible for initializating all components of the GUI */
	
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 786, 430);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JLabel lblCodeParameters = new JLabel("Compatibility Matrix Generator");
		lblCodeParameters.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCodeParameters.setBounds(235, 9, 340, 26);
		panel.add(lblCodeParameters);
		
		
		JLabel lblQ = new JLabel("Q:");
		lblQ.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQ.setBounds(65, 105, 40, 25);
		panel.add(lblQ);
		
		JLabel lblProblemParameters = new JLabel("Select Maximum Parameters");
		lblProblemParameters.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProblemParameters.setBounds(10, 74, 200, 26);
		panel.add(lblProblemParameters);
		
		JLabel lblN = new JLabel("N:");
		lblN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblN.setBounds(65, 150, 40, 25);
		panel.add(lblN);
		
		JLabel lblR = new JLabel("R:");
		lblR.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblR.setBounds(65, 195, 40, 25);
		panel.add(lblR);
		
		final JTextField qText = new JTextField();
		qText.setText("4");
		qText.setBounds(95, 110, 35, 20);
		panel.add(qText);
		qText.setColumns(10);
		
		final JTextField nText = new JTextField();
		nText.setText("8");
		nText.setColumns(10);
		nText.setBounds(95, 155, 35, 20);
		panel.add(nText);
		
		final JTextField rText = new JTextField();
		rText.setText("8");
		rText.setColumns(10);
		rText.setBounds(95, 200, 35, 20);
		panel.add(rText);
		
		runTextArea = new TextArea();
		runTextArea.setBounds(210, 55, 520, 205);
		panel.add(runTextArea);
		
		final JButton startBtn = new JButton("Start");
		startBtn.addActionListener(new ActionListener() {
			 /* Called when the start button is pressed, this method parses all required
			  * values from the current form. If all values are valid, a CompatibilityMatrixGenerator
			  * is used to batch create compatibility matrices.
			  * 
			  * (non-Javadoc)
			  * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			  */
            public void actionPerformed(ActionEvent e)
            {
            	int q;
            	int n;
            	int r;
            	try {
            		q = Integer.parseInt(qText.getText());
            		n = Integer.parseInt(nText.getText());
            		r = Integer.parseInt(rText.getText());
            		redirectOutputToGUI();
            		startBtn.setEnabled(false);
            		new Thread(new CompatibilityMatrixGenerator(q,n,r)).start();
            	} catch (NumberFormatException E) {
            		String errorText = "Error: Must have valid integer values for all textboxs!";
            		JOptionPane.showMessageDialog(null, errorText);
            	}
            }
        });
		startBtn.setBounds(436, 277, 89, 23);
		panel.add(startBtn);
	}
	
	/* Appends a string to the text area which is designated for
	 * all program output.
	 */
	
	private void appendToTextArea(final String str) {
		  SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		      runTextArea.append(str);
		    }
		  });
		}
	
	/* This method redirects standard output and error streams to a 
	 * textarea within the GUI.
	 */
		 
		private void redirectOutputToGUI() {
		  OutputStream outStream = new OutputStream() {
			  
			@Override
			public void write(byte[] b, int off, int len) throws IOException {
			  appendToTextArea(new String(b, off, len));
			}
			  
		    @Override
		    public void write(int b) throws IOException {
		      appendToTextArea(String.valueOf((char) b));
		    }
		 
		    
		 
		    @Override
		    public void write(byte[] b) throws IOException {
		      write(b, 0, b.length);
		    }
		  };
		 
		  System.setOut(new PrintStream(outStream, true));
		  System.setErr(new PrintStream(outStream, true));
		}
}
