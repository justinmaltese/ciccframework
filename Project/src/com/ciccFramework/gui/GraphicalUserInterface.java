package com.ciccFramework.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JTabbedPane;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.ciccFramework.algorithms.ga.GeneticAlgorithm;
import com.ciccFramework.compatibility.BinaryCompatibilityChecker;
import com.ciccFramework.compatibility.CompatibilityChecker;
import com.ciccFramework.compatibility.QaryCompatibilityChecker;
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



/* This class serves as a graphical user interface for the CICCFramework. A user is able to select
 * algorithms and problems for comparison purposes through a sequence of steps in the interface.
 * Execution statistics are also viewable within the interface itself.
 * 
 * NOTE: Portions of this class were generated using the WindowBuilder WYSIWYG editor for eclipse.
 *  
 * @author Justin Maltese
 * @date 04/10/2016
 * @version 1.0
 */

public class GraphicalUserInterface {

	private ExecutionHandler handler;
	
	// GUI elements accessible globally
	private JFrame frame;
	private DefaultListModel addedAlgorithmsModel;
	private TextArea runTextArea;
	private JButton startButton;
	private JTabbedPane tabbedPane;
	private JCheckBoxMenuItem chckbxmntmShowAlgorithmOutput;
	private ArrayList<JRadioButtonMenuItem> numRunsButtons;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicalUserInterface window = new GraphicalUserInterface();
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
	public GraphicalUserInterface() {
		initialize();
	}
	
	/* Initializes all components of the GUI */
	
	private void initialize() {
		handler = new ExecutionHandler();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 786, 430);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		
		initMenu();
		initStep1();
		initStep2();
		initExecutionTab();
	}
	
	/* Initializes the options menu */
	
	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		

		// initialize buttons to determine desired # of runs
		ButtonGroup numRunsGroup = new ButtonGroup();
		numRunsButtons = new ArrayList<JRadioButtonMenuItem>();
		
		JRadioButtonMenuItem rdbtnmntmRuns = new JRadioButtonMenuItem("5 Runs");
		numRunsGroup.add(rdbtnmntmRuns);
		numRunsButtons.add(rdbtnmntmRuns);
		mnOptions.add(rdbtnmntmRuns);
		
		JRadioButtonMenuItem rdbtnmntmRuns_1 = new JRadioButtonMenuItem("10 Runs");
		rdbtnmntmRuns_1.setSelected(true);
		mnOptions.add(rdbtnmntmRuns_1);
		numRunsButtons.add(rdbtnmntmRuns_1);
		numRunsGroup.add(rdbtnmntmRuns_1);
		
		
		JRadioButtonMenuItem rdbtnmntmRuns_2 = new JRadioButtonMenuItem("15 Runs");
		numRunsGroup.add(rdbtnmntmRuns_2);
		numRunsButtons.add(rdbtnmntmRuns_2);
		mnOptions.add(rdbtnmntmRuns_2);
		
		JRadioButtonMenuItem rdbtnmntmRuns_3 = new JRadioButtonMenuItem("20 Runs");
		mnOptions.add(rdbtnmntmRuns_3);
		numRunsButtons.add(rdbtnmntmRuns_3);
		numRunsGroup.add(rdbtnmntmRuns_3);
		
		JRadioButtonMenuItem rdbtnmntmRuns_4 = new JRadioButtonMenuItem("25 Runs");
		numRunsGroup.add(rdbtnmntmRuns_4);
		numRunsButtons.add(rdbtnmntmRuns_4);
		mnOptions.add(rdbtnmntmRuns_4);
		
		JRadioButtonMenuItem rdbtnmntmRuns_5 = new JRadioButtonMenuItem("30 Runs");
		mnOptions.add(rdbtnmntmRuns_5);
		numRunsButtons.add(rdbtnmntmRuns_5);
		numRunsGroup.add(rdbtnmntmRuns_5);
		
		mnOptions.addSeparator();
		
		// initialize checkbox to determine if algorithm output is written
		
		chckbxmntmShowAlgorithmOutput = new JCheckBoxMenuItem("Show Algorithm Output");
		mnOptions.add(chckbxmntmShowAlgorithmOutput);
	}

	/* Initializes the step 1 tab */
	
	private void initStep1() {	
		final HashMap<String,AlgorithmSchema> algorithmSchemas = XMLSchemaLoader.loadAlgorithmSchemas();
		
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Step 1", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblCodeParameters = new JLabel("Step 1: Select Algorithms to Compare");
		lblCodeParameters.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCodeParameters.setBounds(195, 24, 340, 26);
		panel.add(lblCodeParameters);
		
		final DefaultListModel<String> model = new DefaultListModel();
		final JList candidateList = new JList(model);
		candidateList.setBounds(65, 80, 200, 250);
		panel.add(candidateList);
		Set<Entry<String, AlgorithmSchema>> schemaSet = algorithmSchemas.entrySet();
		int index = 0;
		
		final String[] parallelSchemaIDs = new String[schemaSet.size()];
		
		// add all schema names to candidate list and also
		// record schema IDs in parallel array for future use
		for (Entry<String, AlgorithmSchema> schemaEntry: schemaSet) {
			String schemaName = schemaEntry.getValue().NAME;
			model.add(index,schemaName);
			parallelSchemaIDs[index] = schemaEntry.getValue().ID;
			index++;
		}
		addedAlgorithmsModel = new DefaultListModel();
		final JList addedList = new JList(addedAlgorithmsModel);
		addedList.setBounds(485, 79, 200, 215);
		panel.add(addedList);
		
		JButton addButton = new JButton("Add >>");
		addButton.setBounds(325, 197, 89, 23);
		final GraphicalUserInterface gui = this;
		addButton.addActionListener(new ActionListener() {
			 
	            public void actionPerformed(ActionEvent e)
	            {
	            	int selectedIndex = candidateList.getSelectedIndex();
	       
	            	if (selectedIndex != -1) {
	            		//model.remove(selectedIndex);
	            		String schemaID = parallelSchemaIDs[selectedIndex];
	            		AlgorithmSchema schema = algorithmSchemas.get(schemaID);
	            		ParameterSelectionDialog createdWindow = new ParameterSelectionDialog(schema,gui);
	            		//setVisible(false);
	            	}
	            }
	        });
		panel.add(addButton);
		
		JButton removeBtn = new JButton("Remove All");
		removeBtn.setBounds(485, 299, 205, 23);
		removeBtn.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	handler.removeAllAlgorithms();
            	addedAlgorithmsModel.clear();
            }
        });
		panel.add(removeBtn);
		
		JLabel lblAvailableAlgorithms = new JLabel("Available Algorithms");
		lblAvailableAlgorithms.setBounds(110, 61, 120, 14);
		panel.add(lblAvailableAlgorithms);
		
		JLabel lblSelectedAlgorithms = new JLabel("Selected Algorithms");
		lblSelectedAlgorithms.setBounds(535, 60, 120, 14);
		panel.add(lblSelectedAlgorithms);
		
		JButton nextBtn = new JButton("Next -->");
		nextBtn.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	
            	// if at least one algorithm go to next step
            	if (addedAlgorithmsModel.size() == 0) {
            		String errorText = "Error: Must have selected at least one algorithm!";
            		JOptionPane.showMessageDialog(null, errorText);
            	} else {
            		nextTab();
            	}
            }
        });
		nextBtn.setBounds(666, 7, 89, 23);
		panel.add(nextBtn);
		
		JPanel panel3 = new JPanel();
		frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tabbedPane}));
	}
	
	/* Initializes the step 2 tab */
	
	private void initStep2() {
		JPanel panel = new JPanel();
		tabbedPane.addTab("Step 2", null, panel, null);
		tabbedPane.setEnabledAt(1, false);
		panel.setLayout(null);
		
		JLabel lblCodeParameters = new JLabel("Step 2: Select Benchmark Problems");
		lblCodeParameters.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCodeParameters.setBounds(195, 24, 340, 26);
		panel.add(lblCodeParameters);
		
		
		
		final DefaultListModel addedProblemsModel = new DefaultListModel();
		final JList addedList = new JList(addedProblemsModel);
		addedList.setBounds(485, 79, 200, 215);
		panel.add(addedList);
		
		JButton removeBtn = new JButton("Remove All");
		removeBtn.setBounds(485, 299, 205, 23);
		removeBtn.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	handler.removeAllProblems();
            	addedProblemsModel.clear();
            }
        });
		panel.add(removeBtn);
		
		// create labels to input problem parameters
		JLabel lblQ = new JLabel("Q:");
		lblQ.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQ.setBounds(125, 125, 40, 25);
		panel.add(lblQ);
		
		JLabel lblProblemParameters = new JLabel("Problem Parameters");
		lblProblemParameters.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProblemParameters.setBounds(100, 85, 165, 26);
		panel.add(lblProblemParameters);
		
		JLabel lblN = new JLabel("N:");
		lblN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblN.setBounds(125, 170, 40, 25);
		panel.add(lblN);
		
		JLabel lblR = new JLabel("R:");
		lblR.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblR.setBounds(125, 215, 40, 25);
		panel.add(lblR);
		
		JLabel lblSeedSize = new JLabel("Seed Size:");
		lblSeedSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSeedSize.setBounds(75, 265, 80, 25);
		panel.add(lblSeedSize);
		
		final JTextField qText = new JTextField();
		qText.setBounds(155, 130, 35, 20);
		panel.add(qText);
		qText.setColumns(10);
		
		final JTextField nText = new JTextField();
		nText.setColumns(10);
		nText.setBounds(155, 175, 35, 20);
		panel.add(nText);
		
		final JTextField rText = new JTextField();
		rText.setColumns(10);
		rText.setBounds(155, 220, 35, 20);
		panel.add(rText);
		
		final JTextField seedSizeText = new JTextField();
		seedSizeText.setColumns(10);
		seedSizeText.setBounds(155, 270, 35, 20);
		panel.add(seedSizeText);
		
		JButton addButton = new JButton("Add >>");
		addButton.setBounds(325, 197, 89, 23);
		
		addButton.addActionListener(new ActionListener() {
			 
			// if valid values for all textboxs, create new problem and
			// add to both problem JList and handler
	            public void actionPerformed(ActionEvent e)
	            {
	            	int q;
	            	int n;
	            	int r;
	            	int seedSize;
	            	try {
	            		q = Integer.parseInt(qText.getText());
	            		n = Integer.parseInt(nText.getText());
	            		r = Integer.parseInt(rText.getText());
	            		seedSize = Integer.parseInt(seedSizeText.getText());
	            		CoveringProblem createdProblem = new CoveringProblem(q,n,r,seedSize);
	            		handler.attachProblemInstance(createdProblem);
	            		addedProblemsModel.add(addedProblemsModel.size(), createdProblem.toString());
	            	} catch (NumberFormatException E) {
	            		String errorText = "Error: Must have valid integer values for all textboxs!";
	            		JOptionPane.showMessageDialog(null, errorText);
	            	}
	            }
	        });
		panel.add(addButton);
		
		JButton nextBtn = new JButton("Next -->");
		nextBtn.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	
            	// if at least one problem added, select next tab and begin execution
            	if (addedProblemsModel.size() == 0) {
            		String errorText = "Error: Must have selected at least one problem!";
            		JOptionPane.showMessageDialog(null, errorText);
            	} else {
            		nextTab();
            		redirectOutputToGUI();
            		handler.writeAlgorithmOutput(chckbxmntmShowAlgorithmOutput.getState());
            		handler.setNumRuns(getNumRuns());
            		new Thread(handler).start();
            	}
            }
        });
		nextBtn.setBounds(666, 7, 89, 23);
		panel.add(nextBtn);
	}
	
	/* Returns the number of runs selected via the buttons in the Options menu */
	
	private int getNumRuns() {
		for (int i=1;i<=numRunsButtons.size();i++) {
			if (numRunsButtons.get(i-1).isSelected()) {
				return (i*5);
			}
		}
		throw new RuntimeException("None of the number of run buttons are selected within the Options menu!");
	}
	
	/* Initializes the execution tab */
	
	private void initExecutionTab() {
		JPanel panel2 = new JPanel();
		tabbedPane.addTab("Execution", null, panel2, null);
		tabbedPane.setEnabledAt(2, false);
		panel2.setLayout(null);
		
		runTextArea = new TextArea();
		runTextArea.setBounds(85, 45, 565, 205);
		panel2.add(runTextArea);
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
	
	/* This method redirects the standard output and error streams  
	 * to a textarea within the GUI.
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
		
	// Enables and switches to the next tab, while also disabling the current selected tab

	
	private void nextTab() {
		int nextTabIndex = tabbedPane.getSelectedIndex() + 1;
		tabbedPane.setEnabledAt(nextTabIndex, true);
		tabbedPane.setSelectedIndex(nextTabIndex);
		tabbedPane.setEnabledAt(nextTabIndex-1, false);
	}
	
	// attaches a given algorithm schema and parameter set to the
	// execution handler for later use
	
	public void addAlgorithm(AlgorithmSchema schema, ParameterSet params) {
		handler.attachAlgorithmInstance(schema, params);
		addedAlgorithmsModel.add(addedAlgorithmsModel.getSize(), schema.NAME);
	}
	
	// hides or shows the GUI
	
	public void setVisible(boolean val) {
		frame.setVisible(val);
	}
}
