package com.sfb805.application;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.apache.commons.lang3.ArrayUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import com.sfb805.controller.Comands;
import com.sfb805.controller.GraphController;
import com.sfb805.gui2owl2gui.ClassManipulator;
import com.sfb805.gui2owl2gui.IndividualManipulator;
import com.sfb805.gui2owl2gui.ObjectPropertyManipulator;
import com.sfb805.main.Main;
import com.sfb805.nx2owl2nx.OWLpointWriter;
import com.sfb805.sparql.SPARQLudo;
import com.sfb805.testing.DLquery;
import com.sfb805.udo.NXObjectSelector;
import com.sfb805.udo.UDOCollection;

import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import nxopen.Edge;
import nxopen.NXException;
import nxopen.Point3d;

/**
 *
 * @author Max
 */
public class GUISwing extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private GUISwingVariables guiVariables;
	private PrintStream printStream;
	public BufferedReader reader;
	public UDOCollection udoCollection;
	public Main pelops;
	static Main staticpelops;
	static IndividualManipulator staticIndividualManipulator;
	public ClassManipulator classManipulator;
	public IndividualManipulator individualManipulator;
	public ObjectPropertyManipulator objectPropertyManipulator;
	public static String home = System.getProperty("user.home").replace("\\", "/");
	static String matlabCurrentFolderString = home + "/Desktop/PELOPS/";
	static String timeDataFilePath;
	static OWLOntology timeDataOntology = null;
	static String timeDataIRIString = "http://www.hozo.jp/owl/EXPOApr19";
	static MatlabProxy proxy;
	static MatlabProxyFactory factory;

	private ImageIcon forwardNormal;
	private ImageIcon backwardNormal;
	private ImageIcon forwardHover;
	private ImageIcon backwardHover;

	public OWLOntologyManager manager;
	// public OWLOntology ontologyT = null;
	public NGBrowser ngBrowser;
	public GraphController graphController;
	// String filePath1 = "file:///c:/users/max/desktop/PELOPS/10303_108.owl";

	/**
	 * Creates new form GUISwing
	 */
	public GUISwing() {

		this.udoCollection = new UDOCollection();
		this.pelops = new Main();
		staticpelops = this.pelops;
		this.ngBrowser = new NGBrowser();
		this.ngBrowser.setDefaultCloseOperation(NGBrowser.HIDE_ON_CLOSE);

		this.graphController = new GraphController();
		// this.initPelops();

		this.classManipulator = new ClassManipulator();
		this.objectPropertyManipulator = new ObjectPropertyManipulator();
		this.individualManipulator = new IndividualManipulator();
		staticIndividualManipulator = this.individualManipulator;
		initComponents();
		try {
			forwardNormal = new ImageIcon(getClass().getResource("/forwardOne.png"));
			backwardNormal = new ImageIcon(getClass().getResource("/backwardOne.png"));
			backwardHover = new ImageIcon(getClass().getResource("/backwardOneSmall.png"));
			forwardHover = new ImageIcon(getClass().getResource("/forwardOneSmall.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.guiVariables = new GUISwingVariables();
		this.guiVariables.setIsDisabledLabelOne(false);
		this.reader = new BufferedReader(new InputStreamReader(System.in));
		this.printStream = new PrintStream(new CustomOutputStream(jTextArea1));
		System.setOut(printStream);
		System.setErr(printStream);
		System.out.println("\t\t:::system started:::");

		this.setResizable(false);
		this.resetPanelOne();
		this.resetPanelTwo();
		this.resetPanelThree();
		this.two.setEnabled(false);
		this.three.setEnabled(false);
		this.four.setEnabled(false);
		this.five.setEnabled(false);

	}

	// public void initPelops() {
	// try {
	// System.out.print("Trying to create OWLManager ");
	// manager = OWLManager.createOWLOntologyManager();
	// System.out.print("OWLManager created sucessfully.");
	//
	// System.out.print("Trying to load " + filePath1 + "...");
	// ontologyT =
	// manager.loadOntologyFromOntologyDocument(IRI.create(filePath1));
	// System.out.print("File " + filePath1 + "loaded sucessfully.");
	//
	// } catch (OWLOntologyCreationException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// }

	public void resetPanelThree() {
		this.restoreThree.setVisible(false);
		this.panelThreeInOne.setVisible(false);
		this.matllabLabel.setVisible(true);
	}

	public void resetPanelTwo() {
		try {
			this.matlabLogo.setVisible(true);
			this.resetPanelThree();
			this.pnaelTwoInOne.setBorder(null);
			this.three.setEnabled(false);
			this.four.setEnabled(false);
			this.restoreTwo.setVisible(false);
			this.restoreTwo.setVisible(false);
			this.pnaelTwoInOne.setVisible(false);
			this.pnaelTwoButtonOne.setVisible(false);
			this.panelTwoInTwo.setVisible(false);
			this.pnaelTwoButtonTwo.setVisible(false);
			this.pnaelTwoInThree.setVisible(false);
			this.pnaelTwoButtonThree.setVisible(false);
		} catch (Exception e) {

		}

	}

	public void resetPanelOne() {
		try {
			this.nxlogo.setVisible(true);
			this.matlabLogo.setVisible(true);
			this.resetPanelTwo();
			this.resetPanelThree();
			this.restoreOne.setVisible(false);
			this.two.setEnabled(false);
			this.three.setEnabled(false);
			this.four.setEnabled(false);
			this.five.setEnabled(false);
			this.guiVariables.setIsDisabledLabelTwo(true);
			this.panelOneInOne.setVisible(false);
			this.paneOneInsideOne.setVisible(false);
			this.paneOneInsideTwo.setVisible(false);
			this.paneOneInsideThree.setVisible(false);
		} catch (Exception e) {

		}

	}

	public void setPanelOne() {
		try {

			if (this.panelOneInOne.isVisible() == false) {
				this.nxlogo.setVisible(false);
				this.two.setEnabled(true);
				this.restoreOne.setVisible(true);
				this.panelOneInOne.setVisible(true);
				this.guiVariables.setIsDisabledLabelFour(false);
			}
		} catch (Exception e) {

		}

	}

	public void setPanelTwo() {
		try {
			if (this.panelTwoInTwo.isVisible() == false) {
				if (this.pnaelTwoInOne.isVisible() == false) {
					this.matlabLogo.setVisible(false);
					this.three.setEnabled(true);
					this.restoreTwo.setVisible(true);
					this.guiVariables.setIsDisabledLabelThree(false);
					this.pnaelTwoInOne.setVisible(true);
					this.panelTwoInTwo.setVisible(true);
					this.pnaelTwoInThree.setVisible(true);
					this.pnaelTwoButtonOne.setVisible(true);
					this.pnaelTwoButtonTwo.setVisible(true);
					this.guiVariables.setIsDisabledLabelThree(false);
					this.guiVariables.setIsDisabledLabelFive(true);
				}
				if (this.guiVariables.getIsDisabledLabelFour() == false) {
					if (pnaelTwoButtonThree.isVisible() == false) {
						pnaelTwoButtonThree.setVisible(true);
					}
				}
			}
		} catch (Exception e) {

		}

	}

	public void setPanelThree() {
		try {
			if (this.panelThreeInOne.isVisible() == false) {
				this.four.setEnabled(true);
				this.matllabLabel.setVisible(false);
				this.panelThreeInOne.setVisible(true);
				this.panelThreeInOneOne.setVisible(true);
				this.restoreThree.setVisible(true);
				this.guiVariables.setIsDisabledLabelFour(false);

				if (this.guiVariables.getIsDisabledLabelFive() == false) {
					if (pnaelTwoButtonThree.isVisible() == false) {
						pnaelTwoButtonThree.setVisible(true);
					}
				}
			}
		} catch (Exception e) {

		}

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jPanel6 = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		three = new javax.swing.JLabel();
		four = new javax.swing.JLabel();
		panelOne = new javax.swing.JPanel();
		panelOne.setOpaque(false);
		panelOneInOne = new javax.swing.JPanel();
		panelOneInOne.setOpaque(false);
		jButton4 = new javax.swing.JButton();
		paneOneInsideOne = new javax.swing.JPanel();
		paneOneInsideOne.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jButton5 = new javax.swing.JButton();
		jButton6 = new javax.swing.JButton();
		jButton7 = new javax.swing.JButton();
		paneOneInsideTwo = new javax.swing.JPanel();
		jTabbedPane2 = new javax.swing.JTabbedPane();
		jTabbedPane2.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		jTabbedPane2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jPanel14 = new javax.swing.JPanel();
		jButton9 = new javax.swing.JButton();
		jPanel15 = new javax.swing.JPanel();
		jComboBox2 = new javax.swing.JComboBox<>();
		jButton8 = new javax.swing.JButton();
		paneOneInsideThree = new javax.swing.JPanel();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jTabbedPane1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jTabbedPane1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		nxlogo = new javax.swing.JLabel();
		panelTwo = new javax.swing.JPanel();
		panelTwo.setOpaque(false);
		pnaelTwoInOne = new javax.swing.JPanel();
		panelTwoInTwo = new javax.swing.JPanel();
		pnaelTwoInThree = new javax.swing.JPanel();
		pnaelTwoButtonTwo = new javax.swing.JButton();

		pnaelTwoButtonTwo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					try {
						graphController.setComand(Comands.TYPE_ONE);
						graphController.proccessGraph();
					} catch (Exception e) {
						e.printStackTrace();
					}

					ngBrowser.loadURL(Comands.VIEW_ONE);
					ngBrowser.setVisible(true);
					// new
					// NGView("file:///C:/Users/Abiel/Desktop/PELOPS/index.html#foaf").setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		pnaelTwoButtonOne = new javax.swing.JButton();
		pnaelTwoButtonThree = new javax.swing.JButton();
		matlabLogo = new javax.swing.JLabel();
		panelThree = new javax.swing.JPanel();
		panelThree.setOpaque(false);
		panelThreeInOne = new javax.swing.JPanel();
		panelThreeInOne.setVisible(false);
		panelThreeInOne.setOpaque(false);
		panelThreeInOneOne = new javax.swing.JTabbedPane();
		panelThreeInOneOne.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelThreeInOneOne.setVisible(false);
		jPanel4 = new javax.swing.JPanel();
		jPanel7 = new javax.swing.JPanel();
		jPanel7.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jPanel8 = new javax.swing.JPanel();
		jPanel8.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jPanel5 = new javax.swing.JPanel();
		jPanel9 = new javax.swing.JPanel();
		jPanel9.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jPanel10 = new javax.swing.JPanel();
		jPanel10.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		matllabLabel = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanel6.setBackground(new java.awt.Color(185, 15, 34));
		jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

		javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE));
		jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 71, Short.MAX_VALUE));

		jPanel2.setBackground(javax.swing.UIManager.getDefaults().getColor("nb.versioning.textannotation.color"));
		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
		jPanel2.setLayout(null);
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		two = new javax.swing.JLabel();
		two.setFocusCycleRoot(true);

		two.setIcon(new ImageIcon(GUISwing.class.getResource("/forwardOne.png"))); // NOI18N
		two.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					if (two.isEnabled() == true) {
						setPanelTwo();
						pelops.nx2owl();
					}
				} catch (OWLOntologyStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

				if (two.isEnabled() == true) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					two.setIcon(forwardHover);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (two.isEnabled() == true) {
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					two.setIcon(forwardNormal);
				}
			}
		});
		jPanel2.add(two);
		two.setBounds(423, 55, 108, 70);

		three.setIcon(new ImageIcon(GUISwing.class.getResource("/forwardOne.png"))); // NOI18N
		three.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					if (three.isEnabled() == true) {
						setPanelThree();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (three.isEnabled() == true) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					three.setIcon(forwardHover);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (three.isEnabled() == true) {

					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					three.setIcon(forwardNormal);
				}
			}
		});
		jPanel2.add(three);
		three.setBounds(820, 55, 108, 70);

		four.setIcon(new ImageIcon(GUISwing.class.getResource("/backwardOne.png"))); // NOI18N
		four.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					if (four.isEnabled() == true) {
						five.setEnabled(true);
						pnaelTwoInOne
								.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
						pelops.matlab2owl();
					}
				} catch (OWLOntologyStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (three.isEnabled() == true) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					four.setIcon(backwardHover);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (three.isEnabled() == true) {
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					four.setIcon(backwardNormal);
				}
			}
		});
		jPanel2.add(four);
		four.setBounds(808, 449, 120, 68);

		panelOne.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		panelOne.setLayout(null);

		jButton4.setText("Uncertainity B");
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});

		jButton5.setText("Edge");
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});

		jButton6.setText("Point");
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton6ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout paneOneInsideOneLayout = new javax.swing.GroupLayout(paneOneInsideOne);
		paneOneInsideOneLayout
				.setHorizontalGroup(paneOneInsideOneLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, paneOneInsideOneLayout.createSequentialGroup().addGap(42)
								.addComponent(jButton6, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE).addGap(18)
								.addComponent(jButton5, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE).addGap(39)));
		paneOneInsideOneLayout.setVerticalGroup(paneOneInsideOneLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, paneOneInsideOneLayout.createSequentialGroup()
						.addGroup(paneOneInsideOneLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jButton6, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
								.addComponent(jButton5, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
						.addGap(13)));
		paneOneInsideOne.setLayout(paneOneInsideOneLayout);

		jButton7.setText("Class B");
		jButton7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton7ActionPerformed(evt);
			}
		});

		jButton9.setText("Individual");
		jButton9.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton9ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
		jPanel14Layout.setHorizontalGroup(jPanel14Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel14Layout.createSequentialGroup().addGap(24)
						.addComponent(jButton9, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(124, Short.MAX_VALUE)));
		jPanel14Layout.setVerticalGroup(jPanel14Layout.createParallelGroup(Alignment.LEADING).addGroup(
				jPanel14Layout.createSequentialGroup().addComponent(jButton9).addContainerGap(22, Short.MAX_VALUE)));
		jPanel14.setLayout(jPanel14Layout);

		jTabbedPane2.addTab("Individual", jPanel14);

		jComboBox2.setModel(
				new javax.swing.DefaultComboBoxModel<>(this.classManipulator.makeShortForm(this.pelops.ontologyT,
						this.classManipulator.getAllOWLClasses(this.pelops.ontologyT, this.pelops.manager))));

		javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
		jPanel15Layout.setHorizontalGroup(jPanel15Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, jPanel15Layout.createSequentialGroup().addContainerGap()
						.addComponent(jComboBox2, 0, 245, Short.MAX_VALUE).addContainerGap()));
		jPanel15Layout.setVerticalGroup(jPanel15Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel15Layout
						.createSequentialGroup().addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(15, Short.MAX_VALUE)));
		jPanel15.setLayout(jPanel15Layout);

		jTabbedPane2.addTab("Class", jPanel15);

		javax.swing.GroupLayout paneOneInsideTwoLayout = new javax.swing.GroupLayout(paneOneInsideTwo);
		paneOneInsideTwoLayout.setHorizontalGroup(paneOneInsideTwoLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(paneOneInsideTwoLayout.createSequentialGroup().addContainerGap(85, Short.MAX_VALUE)
						.addComponent(jTabbedPane2, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
						.addGap(21)));
		paneOneInsideTwoLayout.setVerticalGroup(paneOneInsideTwoLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(paneOneInsideTwoLayout
						.createSequentialGroup().addComponent(jTabbedPane2, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(12, Short.MAX_VALUE)));
		paneOneInsideTwo.setLayout(paneOneInsideTwoLayout);

		panel = new JPanel();
		jTabbedPane2.addTab("Assert", null, panel, null);

		btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					udoCollection.createClassAssertionUDOs(udoCollection.classAssertion_selectedObjects,
							udoCollection.classAssertion_selectedPoints, (String) jComboBox2.getSelectedItem(),
							udoCollection.myBasePart);
				} catch (RemoteException | NXException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		panel.add(btnNewButton);

		jButton8.setText("Direction B");
		jButton8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton8ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout paneOneInsideThreeLayout = new javax.swing.GroupLayout(paneOneInsideThree);
		paneOneInsideThreeLayout.setHorizontalGroup(paneOneInsideThreeLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(paneOneInsideThreeLayout
						.createSequentialGroup().addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(24, Short.MAX_VALUE)));
		paneOneInsideThreeLayout
				.setVerticalGroup(paneOneInsideThreeLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(paneOneInsideThreeLayout.createSequentialGroup().addContainerGap()
								.addComponent(jTabbedPane1, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
								.addContainerGap()));
		paneOneInsideThree.setLayout(paneOneInsideThreeLayout);
		one = new javax.swing.JLabel();
		one.setBounds(0, 208, 100, 60);
		jPanel2.add(one);

		one.setIcon(new ImageIcon(GUISwing.class.getResource("/forwardOne.png"))); // NOI18N
		one.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jLabel2MouseClicked(evt);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				one.setIcon(forwardHover);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				one.setIcon(forwardNormal);
			}
		});
		jPanel11 = new javax.swing.JPanel();
		jPanel11.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jButton13 = new javax.swing.JButton();

		jButton13.setText("Individual");
		jButton13.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton13ActionPerformed(evt);
			}
		});
		five = new javax.swing.JLabel();
		five.setBounds(406, 449, 120, 68);
		jPanel2.add(five);
		five.setIcon(new ImageIcon(GUISwing.class.getResource("/backwardOne.png"))); // NOI18N
		five.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (five.isEnabled() == true) {
					try {
						pelops.owl2nx();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {

				if (five.isEnabled() == true) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					five.setIcon(backwardHover);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (five.isEnabled() == true) {
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					five.setIcon(backwardNormal);
				}
			}
		});

		javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
		jPanel11Layout.setHorizontalGroup(
				jPanel11Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel11Layout.createSequentialGroup()
						.addContainerGap().addComponent(jButton13).addContainerGap(154, Short.MAX_VALUE)));
		jPanel11Layout.setVerticalGroup(jPanel11Layout.createParallelGroup(Alignment.LEADING).addGroup(
				jPanel11Layout.createSequentialGroup().addComponent(jButton13).addContainerGap(40, Short.MAX_VALUE)));
		jPanel11.setLayout(jPanel11Layout);

		jTabbedPane1.addTab("Subject", jPanel11);
		jPanel13 = new javax.swing.JPanel();

		jButton11 = new JButton("ObjectButton");
		jButton11.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					udoCollection.selectObject4PropertyAssertion();
				} catch (OWLOntologyCreationException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});

		jPanel12 = new javax.swing.JPanel();
		jComboBox1 = new javax.swing.JComboBox<>();
		// jComboBox1.setRenderer(new MyComboBoxRenderer());

		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(this.objectPropertyManipulator.makeShortForm(
				this.pelops.ontologyT,
				this.objectPropertyManipulator.getAllOWLObjectProperties(this.pelops.ontologyT, this.pelops.manager))));

		javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
		jPanel12Layout.setHorizontalGroup(jPanel12Layout.createParallelGroup(Alignment.LEADING).addComponent(jComboBox1,
				0, 351, Short.MAX_VALUE));
		jPanel12Layout.setVerticalGroup(jPanel12Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel12Layout
						.createSequentialGroup().addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(20, Short.MAX_VALUE)));
		jPanel12.setLayout(jPanel12Layout);

		jTabbedPane1.addTab("Predicat", jPanel12);

		javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
		jPanel13Layout.setHorizontalGroup(jPanel13Layout.createParallelGroup(Alignment.LEADING).addGroup(
				jPanel13Layout.createSequentialGroup().addComponent(jButton11).addContainerGap(235, Short.MAX_VALUE)));
		jPanel13Layout.setVerticalGroup(jPanel13Layout.createParallelGroup(Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				jPanel13Layout.createSequentialGroup().addComponent(jButton11).addContainerGap(69, Short.MAX_VALUE)));
		jPanel13.setLayout(jPanel13Layout);

		jTabbedPane1.addTab("Object", jPanel13);
		jPanel16 = new javax.swing.JPanel();

		jTabbedPane1.addTab("Assert", jPanel16);

		btnNewButton2 = new JButton("OK    ");
		btnNewButton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					udoCollection.createObjectPropertyAssertion(
							ArrayUtils.addAll(udoCollection.selectedSubjectNXObjects,
									udoCollection.selectedObjectNXObjects),
							ArrayUtils.addAll(udoCollection.selectedSubjectPoints, udoCollection.selectedObjectPoints),
							"#" + (String) jComboBox1.getSelectedItem(), udoCollection.myBasePart);
					// udoCollection.createClassAssertionUDOs(udoCollection.classAssertion_selectedObjects,
					// udoCollection.classAssertion_selectedPoints, (String)
					// jComboBox2.getSelectedItem(),
					// udoCollection.myBasePart);
				} catch (RemoteException | NXException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		jPanel16.add(btnNewButton2);

		paneOneInsideFour = new JPanel();
		paneOneInsideFour.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JButton btnTimeData = new JButton("time data");

		javax.swing.GroupLayout gl_panelOneInOne = new javax.swing.GroupLayout(panelOneInOne);
		gl_panelOneInOne
				.setHorizontalGroup(gl_panelOneInOne.createParallelGroup(Alignment.TRAILING).addGroup(gl_panelOneInOne
						.createSequentialGroup().addContainerGap().addGroup(gl_panelOneInOne
								.createParallelGroup(Alignment.LEADING).addComponent(paneOneInsideTwo,
										GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
								.addGroup(gl_panelOneInOne.createSequentialGroup()
										.addGroup(gl_panelOneInOne.createParallelGroup(Alignment.LEADING, false)
												.addComponent(jButton7, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
												.addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 360,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(paneOneInsideOne, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE))
								.addComponent(jButton8, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE)
								.addComponent(paneOneInsideThree, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(
										gl_panelOneInOne.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(paneOneInsideFour, Alignment.LEADING, 0, 0,
														Short.MAX_VALUE)
												.addComponent(btnTimeData, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														366, Short.MAX_VALUE)))
						.addContainerGap()));
		gl_panelOneInOne
				.setVerticalGroup(
						gl_panelOneInOne.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelOneInOne.createSequentialGroup().addContainerGap()
										.addComponent(jButton4).addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(paneOneInsideOne, GroupLayout.PREFERRED_SIZE, 54,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(jButton7)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(paneOneInsideTwo, GroupLayout.PREFERRED_SIZE, 85,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(jButton8)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(paneOneInsideThree, GroupLayout.PREFERRED_SIZE, 95,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnTimeData, GroupLayout.PREFERRED_SIZE, 24,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(paneOneInsideFour, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
										.addContainerGap()));

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JPanel panel_1 = new JPanel();
		tabbedPane_1.addTab("Subject", null, panel_1, null);

		btnIndividual = new JButton("Individual");
		btnIndividual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					udoCollection.selectSubject4PropertyAssertion();
				} catch (OWLOntologyCreationException event) {
					// TODO Auto-generated catch block
					event.printStackTrace();
				}

			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panel_1.createSequentialGroup().addComponent(btnIndividual).addContainerGap(258, Short.MAX_VALUE)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panel_1.createSequentialGroup().addComponent(btnIndividual).addContainerGap(35, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);

		JPanel panel_2 = new JPanel();
		tabbedPane_1.addTab("Predicat", null, panel_2, null);

		comboBox = new JComboBox();
		comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(this.objectPropertyManipulator.makeShortForm(
				this.pelops.ontologyT,
				this.objectPropertyManipulator.getAllOWLObjectProperties(this.pelops.ontologyT, this.pelops.manager))));

		btnSelectTimeData_2 = new JButton("Select time data ontology");
		btnSelectTimeData_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				jButton20ActionPerformed(e);
			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2
				.setHorizontalGroup(
						gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(
										gl_panel_2.createSequentialGroup().addContainerGap()
												.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
														.addComponent(comboBox, Alignment.TRAILING, 0, 352,
																Short.MAX_VALUE)
														.addComponent(btnSelectTimeData_2))
												.addContainerGap()));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup().addComponent(btnSelectTimeData_2).addGap(5)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(29, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);
		GroupLayout gl_paneOneInsideFour = new GroupLayout(paneOneInsideFour);
		gl_paneOneInsideFour.setHorizontalGroup(gl_paneOneInsideFour.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_paneOneInsideFour.createSequentialGroup()
						.addComponent(tabbedPane_1, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(19, Short.MAX_VALUE)));
		gl_paneOneInsideFour
				.setVerticalGroup(gl_paneOneInsideFour.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_paneOneInsideFour.createSequentialGroup().addGap(5)
								.addComponent(tabbedPane_1, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
								.addContainerGap()));

		panel_3 = new JPanel();
		tabbedPane_1.addTab("Object", null, panel_3, null);

		comboBox_2 = new JComboBox();
		btnSelectTimeData_1 = new JButton("select time data content");
		btnSelectTimeData_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// JFileChooser-Objekt erstellen
				JFileChooser chooser = new JFileChooser();
				// Dialog zum Oeffnen von Dateien anzeigen
				int rueckgabeWert = chooser.showOpenDialog(null);

				if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
					// Ausgabe der ausgewaehlten Datei
					System.out.println("Die zu öffnende Datei ist: " + chooser.getSelectedFile().getName());

					timeDataFilePath = chooser.getSelectedFile().getAbsolutePath();

					// create ontology from file

					try {
						timeDataOntology = staticpelops.manager.loadOntologyFromOntologyDocument(
								IRI.create("file:///" + GUISwing.timeDataFilePath.replace('\\', '/')));
					} catch (OWLOntologyCreationException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					comboBox_2.setModel(new javax.swing.DefaultComboBoxModel<>(
							staticIndividualManipulator.makeShortForm(timeDataOntology, staticIndividualManipulator
									.getAllOWLNamedIndividuals(timeDataOntology, staticpelops.manager))));

					jButton21ActionPerformed(e);

				}
			}
		});

		comboBox_2.setName("");

		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
						.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(btnSelectTimeData_1, GroupLayout.PREFERRED_SIZE, 151,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(19, Short.MAX_VALUE)));
		gl_panel_3
				.setVerticalGroup(
						gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_3.createSequentialGroup().addComponent(btnSelectTimeData_1)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(comboBox_2,
												GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(14, Short.MAX_VALUE)));
		panel_3.setLayout(gl_panel_3);

		panel_4 = new JPanel();
		tabbedPane_1.addTab("Assert", null, panel_4, null);

		btnSelectTimeData = new JButton("OK");
		btnSelectTimeData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				jButton19ActionPerformed(e);

				// //add all axioms of the selected ontology in the populated
				// ontology
				// staticpelops.manager.addAxioms(staticpelops.ontologyT,
				// timeDataOntology.axioms());//
				// manager.//timeDataOntology.getAxioms
				// try {
				// staticpelops.manager.saveOntology(staticpelops.ontologyT, new
				// RDFXMLDocumentFormat(),
				// IRI.create(staticpelops.file2.toURI()));
				// } catch (OWLOntologyStorageException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }

				// add the selected time data individual to the selected point
				// individual

				// udoCollection.createObjectPropertyAssertion(
				// ArrayUtils.addAll(udoCollection.selectedSubjectNXObjects,
				// udoCollection.selectedObjectNXObjects),
				// ArrayUtils.addAll(udoCollection.selectedSubjectPoints,
				// udoCollection.selectedObjectPoints),
				// (String) jComboBox1.getSelectedItem(),
				// udoCollection.myBasePart);

			}
		});
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(gl_panel_4.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_4
				.createSequentialGroup().addComponent(btnSelectTimeData).addContainerGap(258, Short.MAX_VALUE)));
		gl_panel_4.setVerticalGroup(gl_panel_4.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_4
				.createSequentialGroup().addComponent(btnSelectTimeData).addContainerGap(43, Short.MAX_VALUE)));
		panel_4.setLayout(gl_panel_4);
		paneOneInsideFour.setLayout(gl_paneOneInsideFour);
		panelOneInOne.setLayout(gl_panelOneInOne);

		panelOne.add(panelOneInOne);
		panelOneInOne.setBounds(10, 43, 387, 525);

		nxlogo.setBackground(java.awt.Color.white);
		nxlogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Siemens-logo.png"))); // NOI18N
		nxlogo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		panelOne.add(nxlogo);
		nxlogo.setBounds(54, 140, 250, 150);

		jPanel2.add(panelOne);
		panelOne.setBounds(41, 29, 404, 579);
		restoreOne = new javax.swing.JButton();
		restoreOne.setIcon(new ImageIcon(GUISwing.class.getResource("/one.png")));
		restoreOne.setBounds(0, 0, 40, 33);
		panelOne.add(restoreOne);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(392, 140, 5, 7);
		panelOne.add(tabbedPane);
		restoreOne.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		panelTwo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		panelTwo.setLayout(null);

		pnaelTwoInOne.setBorder(new javax.swing.border.MatteBorder(null));

		panelTwoInTwo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		pnaelTwoInThree.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		pnaelTwoButtonTwo.setText("T-BOX");

		javax.swing.GroupLayout pnaelTwoInThreeLayout = new javax.swing.GroupLayout(pnaelTwoInThree);
		pnaelTwoInThree.setLayout(pnaelTwoInThreeLayout);
		pnaelTwoInThreeLayout
				.setHorizontalGroup(pnaelTwoInThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(pnaelTwoInThreeLayout.createSequentialGroup().addGap(30, 30, 30)
								.addComponent(pnaelTwoButtonTwo).addGap(42, 42, 42)));
		pnaelTwoInThreeLayout.setVerticalGroup(
				pnaelTwoInThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING, pnaelTwoInThreeLayout.createSequentialGroup()
								.addGap(153, 153, 153).addComponent(pnaelTwoButtonTwo).addContainerGap()));

		pnaelTwoButtonOne.setText("T-BOX 1");
		pnaelTwoButtonOne.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				pnaelTwoButtonOneActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout gl_panelTwoInTwo = new javax.swing.GroupLayout(panelTwoInTwo);
		panelTwoInTwo.setLayout(gl_panelTwoInTwo);
		gl_panelTwoInTwo.setHorizontalGroup(gl_panelTwoInTwo
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(gl_panelTwoInTwo.createSequentialGroup()
						.addGroup(gl_panelTwoInTwo.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(gl_panelTwoInTwo.createSequentialGroup().addGap(27, 27, 27).addComponent(
										pnaelTwoInThree, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelTwoInTwo.createSequentialGroup().addGap(57, 57, 57)
										.addComponent(pnaelTwoButtonOne)))
						.addGap(27, 27, 27)));
		gl_panelTwoInTwo
				.setVerticalGroup(gl_panelTwoInTwo.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gl_panelTwoInTwo.createSequentialGroup()
								.addContainerGap().addComponent(pnaelTwoButtonOne).addGap(12, 12, 12)
								.addComponent(pnaelTwoInThree, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(20, 20, 20)));

		pnaelTwoButtonThree.setText("A BOX 2");

		javax.swing.GroupLayout pnaelTwoInOneLayout = new javax.swing.GroupLayout(pnaelTwoInOne);
		pnaelTwoInOneLayout.setHorizontalGroup(pnaelTwoInOneLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pnaelTwoInOneLayout.createSequentialGroup().addGroup(pnaelTwoInOneLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(pnaelTwoInOneLayout.createSequentialGroup().addGap(42).addComponent(panelTwoInTwo,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(pnaelTwoInOneLayout.createSequentialGroup().addGap(100)
								.addComponent(pnaelTwoButtonThree)))
						.addContainerGap(62, Short.MAX_VALUE)));
		pnaelTwoInOneLayout.setVerticalGroup(pnaelTwoInOneLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pnaelTwoInOneLayout
						.createSequentialGroup().addGap(23).addComponent(panelTwoInTwo, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(pnaelTwoButtonThree).addGap(29)));
		pnaelTwoInOne.setLayout(pnaelTwoInOneLayout);

		panelTwo.add(pnaelTwoInOne);
		pnaelTwoInOne.setBounds(54, 85, 279, 342);

		matlabLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/owl2.png"))); // NOI18N
		matlabLogo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		panelTwo.add(matlabLogo);
		matlabLogo.setBounds(44, 130, 260, 150);

		jPanel2.add(panelTwo);
		panelTwo.setBounds(457, 29, 393, 579);
		restoreTwo = new javax.swing.JButton();
		restoreTwo.setIcon(new ImageIcon(GUISwing.class.getResource("/two.png")));
		restoreTwo.setBounds(0, 0, 42, 33);
		panelTwo.add(restoreTwo);
		restoreTwo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});

		panelThree.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		panelThree.setLayout(null);

		javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
		jPanel7Layout.setHorizontalGroup(
				jPanel7Layout.createParallelGroup(Alignment.LEADING).addGap(0, 241, Short.MAX_VALUE));
		jPanel7Layout
				.setVerticalGroup(jPanel7Layout.createParallelGroup(Alignment.LEADING).addGap(0, 82, Short.MAX_VALUE));
		jPanel7.setLayout(jPanel7Layout);

		JButton btnQuery = new JButton("Query");
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton22ActionPerformed(e);
			}
		});

		comboBox_3 = new JComboBox();

		javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
		jPanel8Layout.setHorizontalGroup(jPanel8Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING,
						jPanel8Layout.createSequentialGroup().addContainerGap(252, Short.MAX_VALUE)
								.addComponent(btnQuery).addContainerGap())
				.addGroup(jPanel8Layout.createSequentialGroup().addContainerGap()
						.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(14, Short.MAX_VALUE)));
		jPanel8Layout
				.setVerticalGroup(
						jPanel8Layout.createParallelGroup(Alignment.TRAILING)
								.addGroup(jPanel8Layout.createSequentialGroup().addGap(24)
										.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, 24,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 175, Short.MAX_VALUE)
										.addComponent(btnQuery).addContainerGap()));
		jPanel8.setLayout(jPanel8Layout);
		jButton14 = new javax.swing.JButton();

		jButton14.setText("Simple SPARQL: Process tagged Points");
		jButton14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton14ActionPerformed(e);
			}
		});
		jButton15 = new javax.swing.JButton();
		jButton15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				jButton15ActionPerformed(evt);
			}
		});

		jButton15.setText("Time Data SPARQL Query");

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel4Layout
				.createSequentialGroup().addGroup(jPanel4Layout
						.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, jPanel4Layout
								.createSequentialGroup().addContainerGap()
								.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
										.addComponent(jPanel7, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
										.addComponent(jButton15, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)))
						.addGroup(Alignment.TRAILING,
								jPanel4Layout.createSequentialGroup().addGap(3).addComponent(jButton14,
										GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE))
						.addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(jPanel8,
								GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)))
				.addContainerGap()));
		jPanel4Layout
				.setVerticalGroup(
						jPanel4Layout.createParallelGroup(Alignment.LEADING)
								.addGroup(jPanel4Layout.createSequentialGroup().addGap(11).addComponent(jButton14)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(jPanel7, GroupLayout.PREFERRED_SIZE, 97,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18).addComponent(jButton15).addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(jPanel8, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
										.addContainerGap()));
		jPanel4.setLayout(jPanel4Layout);

		panelThreeInOneOne.addTab("SPARQL QUERY", jPanel4);
		jButton16 = new javax.swing.JButton();
		jButton16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton16ActionPerformed(e);
			}
		});

		jButton16.setText("Select Point for calculate translation of uncertainty");

		jButton16.setText("Select Point for calculate translation of uncertainty");

		jButton18 = new javax.swing.JButton();
		jButton18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton18ActionPerformed(e);
			}
		});

		jButton18.setText("Query Class of Points for translation of uncertainty");

		comboBox_objectProperties = new JComboBox();

		comboBox_objectProperties.setModel(new javax.swing.DefaultComboBoxModel<>(
				this.objectPropertyManipulator.makeShortForm(this.pelops.ontologyT, this.objectPropertyManipulator
						.getAllOWLObjectProperties(this.pelops.ontologyT, this.pelops.manager))));
		comboBox_class1 = new JComboBox();
		comboBox_class1.setModel(
				new javax.swing.DefaultComboBoxModel<>(this.classManipulator.makeShortForm(this.pelops.ontologyT,
						this.classManipulator.getAllOWLClasses(this.pelops.ontologyT, this.pelops.manager))));

		javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
		jPanel9Layout.setHorizontalGroup(jPanel9Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel9Layout
				.createSequentialGroup().addContainerGap()
				.addGroup(jPanel9Layout.createParallelGroup(Alignment.TRAILING)
						.addComponent(jButton18, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addGroup(jPanel9Layout.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(jPanel9Layout.createSequentialGroup()
										.addComponent(jButton16, GroupLayout.PREFERRED_SIZE, 159,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(comboBox_class1, GroupLayout.PREFERRED_SIZE, 152,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(comboBox_objectProperties, GroupLayout.PREFERRED_SIZE, 315,
										GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		jPanel9Layout.setVerticalGroup(jPanel9Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel9Layout.createSequentialGroup().addGap(25)
						.addComponent(comboBox_objectProperties, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(jPanel9Layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBox_class1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(jButton16))
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(jButton18)
						.addContainerGap(50, Short.MAX_VALUE)));

		jPanel9.setLayout(jPanel9Layout);
		jButton17 = new javax.swing.JButton();
		jButton17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton17ActionPerformed(e);
			}
		});

		jButton17.setText("Berechnung der Unsicherheit aller Punkte in ausgewählter Klasse");

		comboBox_1 = new JComboBox();
		comboBox_1.setModel(
				new javax.swing.DefaultComboBoxModel<>(this.classManipulator.makeShortForm(this.pelops.ontologyT,
						this.classManipulator.getAllOWLClasses(this.pelops.ontologyT, this.pelops.manager))));

		javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
		jPanel10Layout
				.setHorizontalGroup(jPanel10Layout.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
						jPanel10Layout.createSequentialGroup().addContainerGap()
								.addGroup(jPanel10Layout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(jButton17, Alignment.LEADING, 0, 0, Short.MAX_VALUE).addComponent(
												comboBox_1, Alignment.LEADING, 0, 298, Short.MAX_VALUE))
								.addContainerGap(89, Short.MAX_VALUE)));
		jPanel10Layout
				.setVerticalGroup(jPanel10Layout.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
						jPanel10Layout.createSequentialGroup().addGap(23)
								.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(jButton17).addContainerGap(25, Short.MAX_VALUE)));
		jPanel10.setLayout(jPanel10Layout);

		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel5Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel5Layout.createParallelGroup(Alignment.TRAILING)
								.addComponent(jPanel9, Alignment.LEADING, 0, 0, Short.MAX_VALUE).addComponent(jPanel10,
										Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(4, Short.MAX_VALUE)));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel5Layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel9, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE).addGap(47)
						.addComponent(jPanel10, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
						.addGap(27)));
		jPanel5.setLayout(jPanel5Layout);

		panelThreeInOneOne.addTab("DL-QUERY ", jPanel5);

		javax.swing.GroupLayout gl_panelThreeInOne = new javax.swing.GroupLayout(panelThreeInOne);
		gl_panelThreeInOne.setHorizontalGroup(gl_panelThreeInOne.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelThreeInOne.createSequentialGroup().addContainerGap().addComponent(panelThreeInOneOne,
						GroupLayout.PREFERRED_SIZE, 358, Short.MAX_VALUE)));
		gl_panelThreeInOne.setVerticalGroup(gl_panelThreeInOne.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelThreeInOne.createSequentialGroup().addContainerGap()
						.addComponent(panelThreeInOneOne, GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
						.addContainerGap()));
		panelThreeInOne.setLayout(gl_panelThreeInOne);

		panelThree.add(panelThreeInOne);
		panelThreeInOne.setBounds(26, 56, 368, 512);

		matllabLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Matlab_Logo.png"))); // NOI18N
		matllabLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		panelThree.add(matllabLabel);
		matllabLabel.setBounds(54, 136, 260, 154);

		jPanel2.add(panelThree);
		panelThree.setBounds(862, 29, 406, 579);
		restoreThree = new javax.swing.JButton();
		restoreThree.setIcon(new ImageIcon(GUISwing.class.getResource("/three.png")));
		restoreThree.setBounds(0, 0, 34, 27);
		panelThree.add(restoreThree);
		restoreThree.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
		jTextArea1.setColumns(20);
		jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
		jTextArea1.setRows(11);
		jScrollPane1.setViewportView(jTextArea1);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
				.addComponent(jPanel2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1274, Short.MAX_VALUE)
				.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 1274, Short.MAX_VALUE)
				.addComponent(jPanel6, GroupLayout.DEFAULT_SIZE, 1274, Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				jPanel1Layout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 619, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
						.addGap(185)));
		jPanel1.setLayout(jPanel1Layout);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(jPanel1,
				GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(jPanel1,
				GroupLayout.PREFERRED_SIZE, 840, Short.MAX_VALUE));
		getContentPane().setLayout(layout);

		setSize(new Dimension(1280, 868));
		setLocationRelativeTo(null);
	}// </editor-fold>//GEN-END:initComponents

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		this.resetPanelOne();
	}// GEN-LAST:event_jButton1ActionPerformed

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		this.resetPanelThree();
	}// GEN-LAST:event_jButton2ActionPerformed

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
		this.resetPanelTwo();
	}// GEN-LAST:event_jButton3ActionPerformed

	private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton7ActionPerformed
		if (this.paneOneInsideTwo.isVisible() == false) {
			this.paneOneInsideTwo.setSize(40, 40);
			this.paneOneInsideTwo.setVisible(true);
		}
	}// GEN-LAST:event_jButton7ActionPerformed

	private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton8ActionPerformed
		if (this.paneOneInsideThree.isVisible() == false) {
			this.paneOneInsideThree.setSize(40, 40);
			this.paneOneInsideThree.setVisible(true);
		}
	}// GEN-LAST:event_jButton8ActionPerformed

	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
		if (this.paneOneInsideOne.isVisible() == false) {
			this.paneOneInsideOne.setSize(30, 30);
			this.paneOneInsideOne.setVisible(true);
		}
	}// GEN-LAST:event_jButton4ActionPerformed

	private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel2MouseClicked
		// JOptionPane.showMessageDialog(null,"Working");

		this.setPanelOne();

	}// GEN-LAST:event_jLabel2MouseClicked

	private void pnaelTwoButtonOneActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_pnaelTwoButtonOneActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_pnaelTwoButtonOneActionPerformed

	private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel3MouseClicked
		try {
			if (two.isDisplayable() == true) {
				this.setPanelTwo();
				this.pelops.nx2owl();
			}
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}// GEN-LAST:event_jLabel3MouseClicked

	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			// connect one or more points to a normal distribution
			udoCollection.normalDistributionUDO();
		} catch (RemoteException | NXException ex) {
			System.out.println(ex);
		}
	}

	private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton6ActionPerformed
		// get and set all necessary class attributes

		try {
			// connect one or more points to a normal distribution
			udoCollection.normalDistributionUDO();
		} catch (RemoteException | NXException ex) {
			System.out.println(ex);
		}
	}// GEN-LAST:event_jButton6ActionPerformed

	private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton9ActionPerformed
		try {
			udoCollection.selectPoint4classAssertion();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO add your handling code here:
	}// GEN-LAST:event_jButton9ActionPerformed

	private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton13ActionPerformed
		try {
			udoCollection.selectSubject4PropertyAssertion();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO add your handling code here:

	}// GEN-LAST:event_jButton13ActionPerformed

	private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {
		SPARQLudo sparqludo = new SPARQLudo();
		this.pelops.owl2matlab_matrix = sparqludo.run(new String("file:" + this.pelops.filePath2));

		if (this.pelops.owl2matlab_matrix.length == 0) {
			System.out.println("Query returns no result.");
		} else {

			try {
				this.pelops.owl2matlab();
			} catch (OWLOntologyCreationException | RemoteException | OWLOntologyStorageException | NXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.println("try to set time points...");
		DLquery myQuery = new DLquery();
		String selectedClassShortForm = timeDataIRIString + "#TimePoint";
		Set setOfIndividuals = myQuery.getIndividualsOfClassByLongName(selectedClassShortForm, this.pelops.ontologyT);
		OWLNamedIndividual[] arrayOfIndividuals = (OWLNamedIndividual[]) setOfIndividuals
				.toArray(new OWLNamedIndividual[setOfIndividuals.size()]);
		for (int i = 0; i < arrayOfIndividuals.length; i++) {
			System.out.println(arrayOfIndividuals[i]);
		}

		String[] namedIndividualsShortForm = new String[arrayOfIndividuals.length];
		DefaultPrefixManager pm = new DefaultPrefixManager(null, null, timeDataIRIString + "#");

		for (int i = 0; i < arrayOfIndividuals.length; i++) {
			namedIndividualsShortForm[i] = pm.getShortForm(arrayOfIndividuals[i]).substring(1);
			System.out.println(namedIndividualsShortForm[i]);
		}

		if (namedIndividualsShortForm.length == 0) {
			System.out.println("No uncertainty time data available.");
		}

		else {
			for (int i = 0; i < namedIndividualsShortForm.length; i++) {
				System.out.println(namedIndividualsShortForm[i]);
			}

			// String[] dummynames = { "Hello", "Dummy" };

			comboBox_3.setModel(new javax.swing.DefaultComboBoxModel<>(namedIndividualsShortForm));
			System.out.println("time points set successfully.");
		}
	}

	private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {
		// select a point for which the uncertainty shall be calculated by
		// adding expectation and variance of all other points that are
		// connected to the queried point by a certain transitive object
		// property.

		// select a point
		int NumberOfPointsToBeSelected = 1;
		NXObjectSelector nxObjectSelector = null;
		nxObjectSelector = new NXObjectSelector(udoCollection.myBasePart, udoCollection.theUI, udoCollection.theSession,
				NumberOfPointsToBeSelected);
		System.out.println("Query selected Point.");
		// try {
		// UDOCollection.theUI.nxmessageBox().show("Info",
		// NXMessageBox.DialogType.INFORMATION,
		// "Query Point Selected.");
		// } catch (RemoteException | NXException e2) {
		// // TODO Auto-generated catch block
		// e2.printStackTrace();
		// }
		OWLOntology ontology = null;
		DLquery myQuery = new DLquery();
		try {
			ontology = myQuery.manager.loadOntologyFromOntologyDocument(IRI.create("file:///" + this.pelops.filePath2));
		} catch (OWLOntologyCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// change the point into a class and add it to the ontology
		// get the equivalent class of points that influence the individuals in
		// the class of the selected point
		ClassManipulator classManipulator = new ClassManipulator();
		String selectedClassShortForm = classManipulator.convertPointIndividuumToClass(ontology, myQuery.manager,
				new String(nxObjectSelector.getLinkableParentObjects()[0].hashCode() + "_"
						+ nxObjectSelector.getSelectedPoints()[0]));
		// add the point to its own class
		classManipulator.setClassAxiomToPoint(ontology, myQuery.manager, selectedClassShortForm,
				new String(nxObjectSelector.getLinkableParentObjects()[0].hashCode() + "_"
						+ nxObjectSelector.getSelectedPoints()[0]));
		System.out.println("selectedClassShortForm: " + selectedClassShortForm);
		// try {
		// UDOCollection.theUI.nxmessageBox().show("Info",
		// NXMessageBox.DialogType.INFORMATION,
		// "selectedClassShortForm: " + selectedClassShortForm);
		// } catch (RemoteException | NXException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		String selectedObjectPropertyShortForm = "#" + (String) comboBox_objectProperties.getSelectedItem();

		String nameOfClassToBeQueried = classManipulator.createEquivalentClassOfInfluencingIndividuals(ontology,
				myQuery.manager, selectedClassShortForm, selectedObjectPropertyShortForm);

		try {
			myQuery.manager.saveOntology(ontology, new RDFXMLDocumentFormat());
		} catch (OWLOntologyStorageException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// query the ontology
		Object[][] influenced_point = null;
		Object[][] influencing_points = null;
		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println("influenced point:");
			influenced_point = myQuery.queryPointsInClassOfInterest(ontology, myQuery.manager, selectedClassShortForm);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println("influencing points:");
			influencing_points = myQuery.queryPointsInClassOfInterest(ontology, myQuery.manager,
					nameOfClassToBeQueried);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (influencing_points.length == 0 && influenced_point.length == 0) {
			System.out.println("Query returns no result.");
		} else {
			Point3d selectedPoint = null;
			Edge selectedEdge = (Edge) nxObjectSelector.getLinkableParentObjects()[0];

			if (nxObjectSelector.getSelectedPoints()[0].equals("V1")) {

				try {
					selectedPoint = selectedEdge.getVertices().vertex1;
				} catch (RemoteException | NXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (nxObjectSelector.getSelectedPoints()[0].equals("V2")) {

				try {
					selectedPoint = selectedEdge.getVertices().vertex2;
				} catch (RemoteException | NXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("Selected Point is neigther V1 nor V2.");
			}

			// initialize the expectation and variance by adding all influencing
			// expectations and variances
			Object[][] point = new Object[1][9];
			// iri_string
			point[0][0] = new String(ontology.getOntologyID().getOntologyIRI().get().toString() + "#Point3d_"
					+ nxObjectSelector.getLinkableParentObjects()[0].hashCode() + "_"
					+ nxObjectSelector.getSelectedPoints()[0]);

			// try {
			// UDOCollection.theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "point[0][0]: " + point[0][0]);
			// } catch (RemoteException | NXException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			// hash_string
			point[0][1] = new String(nxObjectSelector.getLinkableParentObjects()[0].hashCode() + "_"
					+ nxObjectSelector.getSelectedPoints()[0]);
			// if the selected point has no attached expectation or variance
			// information
			if (influenced_point.length == 0) {
				System.out.println("influenced point has no uncertainty data attached.");
				// expectation
				point[0][2] = selectedPoint.x;
				point[0][3] = selectedPoint.y;
				point[0][4] = selectedPoint.z;
				// variance
				point[0][5] = 0.0;
				point[0][6] = 0.0;
				point[0][7] = 0.0;
			}
			// or use the attache expectation and variance information
			else {
				System.out.println("influenced point has uncertainty data attached.");
				// expectation
				point[0][2] = (double) influenced_point[0][5];
				point[0][3] = (double) influenced_point[0][6];
				point[0][4] = (double) influenced_point[0][7];
				// variance
				point[0][5] = (double) influenced_point[0][8];
				point[0][6] = (double) influenced_point[0][9];
				point[0][7] = (double) influenced_point[0][10];

			}
			// sample number
			point[0][8] = 0;

			System.out.println("influencing_points.length: " + influencing_points.length);
			for (int i = 0; i < influencing_points.length; i++) {
				System.out.println("influencing_points: " + influencing_points[i][0] + " / " + influencing_points[i][1]
						+ " / " + influencing_points[i][2] + " / " + influencing_points[i][3] + " / "
						+ influencing_points[i][4] + " / " + influencing_points[i][5] + " / " + influencing_points[i][6]
						+ " / " + influencing_points[i][7] + " / " + influencing_points[i][8] + " / "
						+ influencing_points[i][9] + " / " + influencing_points[i][10] + " / "
						+ influencing_points[i][11]);
			}

			for (int i = 0; i < influencing_points.length; i++) {

				// expectation
				point[0][2] = (double) point[0][2] + (double) influencing_points[i][5]
						- (double) influencing_points[i][2];
				point[0][3] = (double) point[0][3] + (double) influencing_points[i][6]
						- (double) influencing_points[i][3];
				point[0][4] = (double) point[0][4] + (double) influencing_points[i][7]
						- (double) influencing_points[i][4];
				// point[0][2]+(double)influencing_points[i][2];
				// point[0][3] = (double)
				// point[0][3]+(double)influencing_points[i][3];
				// point[0][4] = (double)
				// point[0][4]+(double)influencing_points[i][4];
				// variance
				double newValue = (double) influencing_points[i][8];
				point[0][5] = (double) point[0][5] + newValue;
				point[0][6] = (double) point[0][6] + (double) influencing_points[i][9];
				point[0][7] = (double) point[0][7] + (double) influencing_points[i][10];

				// sample number
				point[0][8] = Math.max((int) point[0][8], (int) influencing_points[i][11]);
			}

			this.pelops.owl2matlab_matrix = point;

			try {
				this.pelops.owl2matlab();
			} catch (OWLOntologyCreationException | RemoteException | OWLOntologyStorageException | NXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {
		Object[][] pointsInClassOfInterest = null;
		String selectedClassShortForm = "#" + (String) comboBox_1.getSelectedItem();

		OWLOntology ontology = null;
		DLquery myQuery = new DLquery();
		try {
			ontology = myQuery.manager.loadOntologyFromOntologyDocument(IRI.create("file:///" + this.pelops.filePath2));
		} catch (OWLOntologyCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			pointsInClassOfInterest = myQuery.queryPointsInClassOfInterest(ontology, myQuery.manager,
					selectedClassShortForm);
		} catch (OWLOntologyCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (pointsInClassOfInterest.length == 0) {
			System.out.println("Query returns no result.");
		} else {

			Object[][] point = new Object[pointsInClassOfInterest.length][9];
			for (int i = 0; i < pointsInClassOfInterest.length; i++) {

				point[i][0] = (String) pointsInClassOfInterest[i][0];

				// hash_string
				point[i][1] = (String) pointsInClassOfInterest[i][1];

				// expectation
				point[i][2] = (double) pointsInClassOfInterest[i][5];
				point[i][3] = (double) pointsInClassOfInterest[i][6];
				point[i][4] = (double) pointsInClassOfInterest[i][7];

				// variance
				point[i][5] = (double) pointsInClassOfInterest[i][8];
				point[i][6] = (double) pointsInClassOfInterest[i][9];
				point[i][7] = (double) pointsInClassOfInterest[i][10];

				// sample number
				point[i][8] = (int) pointsInClassOfInterest[i][11];
			}

			this.pelops.owl2matlab_matrix = point;

			try {
				this.pelops.owl2matlab();
			} catch (OWLOntologyCreationException | RemoteException | OWLOntologyStorageException | NXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {

		String selectedObjectPropertyShortForm = "#" + (String) comboBox_objectProperties.getSelectedItem();
		String selectedClassShortForm = "#" + (String) comboBox_class1.getSelectedItem();
		// select points from class of interest
		Object[][] pointsInClassOfInterest = null;

		OWLOntology ontology = null;
		manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory factory = manager.getOWLDataFactory();

		DLquery myQuery = new DLquery();
		try {
			ontology = myQuery.manager.loadOntologyFromOntologyDocument(IRI.create("file:///" + this.pelops.filePath2));
		} catch (OWLOntologyCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IRI ontology_iri = (IRI) ontology.getOntologyID().getOntologyIRI().get();
		PrefixManager pm = new DefaultPrefixManager(null, null, ontology_iri.toString());
		OWLNamedIndividual individual = null;
		Set setOfIndividuals = myQuery.getIndividualsOfClassByName(selectedClassShortForm, ontology);

		List<Object[]> listOfPoints = new ArrayList<Object[]>(1);

		if (setOfIndividuals.isEmpty()) {
			System.out.println("Query returns no result.");
		} else {

			// query result of the ontology
			Object[][] influenced_point = null;
			Object[][] influencing_points = null;
			// processed and stored for next steps
			Object[] point = null;
			int index = 9;
			int individualIndex = 0;
			System.out.println("Answer: " + setOfIndividuals.size() + " individuals belong to the class "
					+ selectedClassShortForm);
			for (Iterator iterator = setOfIndividuals.iterator(); iterator.hasNext();) {
				individual = (OWLNamedIndividual) iterator.next();
				System.out.println("indivdiual:" + individual.getIRI());
			}
			for (Iterator iterator = setOfIndividuals.iterator(); iterator.hasNext();) {

				individual = (OWLNamedIndividual) iterator.next();
				System.out.println("" + individual.getIRI());

				// change the point into a class and add it to the ontology
				// get the equivalent class of points that influence the
				// individuals in
				// the class of the selected point

				OWLDataPropertyExpression hasNXhashCode = factory.getOWLDataProperty("#hasNXhashCode", pm);
				OWLLiteral nxHash = myQuery.getDataPropertyValue(individual, hasNXhashCode, ontology);

				ClassManipulator classManipulator = new ClassManipulator();
				String PunningClassName = classManipulator.convertPointIndividuumToClass(ontology, myQuery.manager,
						nxHash.getLiteral());
				// add the point to its own class
				classManipulator.setClassAxiomToPoint(ontology, myQuery.manager, PunningClassName, nxHash.getLiteral());
				System.out.println("selectedClassShortForm: " + PunningClassName);
				// create the class of individuals that have a relation to the
				// individuals in the selectedClass via the selected
				// ObjectProperty
				String nameOfClassToBeQueried = classManipulator.createEquivalentClassOfInfluencingIndividuals(ontology,
						myQuery.manager, PunningClassName, selectedObjectPropertyShortForm);

				try {
					myQuery.manager.saveOntology(ontology, new RDFXMLDocumentFormat());
				} catch (OWLOntologyStorageException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				try {
					influenced_point = myQuery.queryPointsInClassOfInterest(ontology, myQuery.manager,
							PunningClassName);
					influencing_points = myQuery.queryPointsInClassOfInterest(ontology, myQuery.manager,
							nameOfClassToBeQueried);
				} catch (OWLOntologyCreationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// if both influencing and influenced points have no attached
				// uncertainty information
				if (influencing_points.length == 0 && influenced_point.length == 0) {
					System.out.println("Query returns no result.");
				} else {

					point = new Object[index];
					// Point3d selectedPoint = null;

					// iri_string
					point[0] = individual.getIRI().getIRIString();

					// hash_string
					point[1] = nxHash.getLiteral();

					// if the selected point has no attached expectation or
					// variance
					// information
					if (influenced_point.length == 0) {
						OWLObjectProperty objectProperty = factory.getOWLObjectProperty("#ordered_list_has_Slot", pm);

						// expectation
						Set xyz = myQuery.getIndividualsByObjectProperty(individual, objectProperty, ontology);
						//
						for (Iterator iterator2 = xyz.iterator(); iterator2.hasNext();) {
							OWLNamedIndividual indi = (OWLNamedIndividual) iterator2.next();

							// ToDo: check if the point has uncertainty...create
							// a better
							// indicator: Class assertion: if VarianceX,Y or Z
							// -> Uncertain
							// Point.

							// ...check to which class the individual belongs
							// and get
							// its
							// dataProperty
							OWLDataPropertyExpression hasValue = factory.getOWLDataProperty("#hasValue", pm);
							OWLLiteral literal = myQuery.getDataPropertyValue(indi, hasValue, ontology);

							OWLClass check = factory.getOWLClass("#Cartesian_X_Coordinate", pm);
							if (myQuery.checkIndividualClassAffiliation(indi, check, ontology)) {
								point[2] = Double.parseDouble(literal.getLiteral());
								// System.out.println("Check: " + check.getIRI()
								// + " has literal " + literal.getLiteral());

							}
							check = factory.getOWLClass("#Cartesian_Y_Coordinate", pm);
							if (myQuery.checkIndividualClassAffiliation(indi, check, ontology)) {
								double y_coordinate = Double.parseDouble(literal.getLiteral());
								point[3] = y_coordinate;
								// System.out.println("Check: " + check.getIRI()
								// + " has literal " + literal.getLiteral());
							}
							//
							check = factory.getOWLClass("#Cartesian_Z_Coordinate", pm);
							if (myQuery.checkIndividualClassAffiliation(indi, check, ontology)) {
								double z_coordinate = Double.parseDouble(literal.getLiteral());
								point[4] = z_coordinate;
								// System.out.println("Check: " + check.getIRI()
								// + " has literal " + literal.getLiteral());
							}

							// variance
							point[5] = 0.0;
							point[6] = 0.0;
							point[7] = 0.0;
						}
					}
					// or use the attache expectation and variance information
					else {
						// expectation
						point[2] = (double) influenced_point[0][5];
						point[3] = (double) influenced_point[0][6];
						point[4] = (double) influenced_point[0][7];
						// variance
						point[5] = (double) influenced_point[0][8];
						point[6] = (double) influenced_point[0][9];
						point[7] = (double) influenced_point[0][10];

					}
					// sample number
					point[8] = 0;

					System.out.println("influencing_points.length: " + influencing_points.length);
					for (int i = 0; i < influencing_points.length; i++) {
						System.out.println("influencing_points on individual " + PunningClassName + ": "
								+ influencing_points[i][0] + " / " + influencing_points[i][1] + " / "
								+ influencing_points[i][2] + " / " + influencing_points[i][3] + " / "
								+ influencing_points[i][4] + " / " + influencing_points[i][5] + " / "
								+ influencing_points[i][6] + " / " + influencing_points[i][7] + " / "
								+ influencing_points[i][8] + " / " + influencing_points[i][9] + " / "
								+ influencing_points[i][10] + " / " + influencing_points[i][11]);
					}

					for (int i = 0; i < influencing_points.length; i++) {

						// expectation
						point[2] = (double) point[2] + (double) influencing_points[i][5]
								- (double) influencing_points[i][2];
						point[3] = (double) point[3] + (double) influencing_points[i][6]
								- (double) influencing_points[i][3];
						point[4] = (double) point[4] + (double) influencing_points[i][7]
								- (double) influencing_points[i][4];
						// point[0][2]+(double)influencing_points[i][2];
						// point[0][3] = (double)
						// point[0][3]+(double)influencing_points[i][3];
						// point[0][4] = (double)
						// point[0][4]+(double)influencing_points[i][4];
						// variance
						double newValue = (double) influencing_points[i][8];
						point[5] = (double) point[5] + newValue;
						point[6] = (double) point[6] + (double) influencing_points[i][9];
						point[7] = (double) point[7] + (double) influencing_points[i][10];

						// sample number
						point[8] = Math.max((int) point[8], (int) influencing_points[i][11]);
					}
					listOfPoints.add(point);
					point = new Object[index];
				}

				individualIndex = individualIndex + 1;
			}
			for (int i = 0; i < point.length; i++) {

				System.out.println("point[" + i + "]: " + point[i]);

			}
			Object[][] points = new Object[listOfPoints.size()][];
			points = (Object[][]) listOfPoints.toArray(new Object[listOfPoints.size()][]);

			System.out.println("points.length: " + points.length);
			for (int i = 0; i < points.length; i++) {
				for (int j = 0; j < points[i].length; j++) {
					System.out.println("points[" + i + "][" + j + "]: " + points[i][j]);
				}
			}

			this.pelops.owl2matlab_matrix = points;

			try {
				this.pelops.owl2matlab();
			} catch (OWLOntologyCreationException | RemoteException | OWLOntologyStorageException | NXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}

	}

	private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {

		// add all axioms of the selected ontology in the populated ontology
		this.pelops.manager.addAxioms(this.pelops.ontologyT, timeDataOntology.axioms());// manager.//timeDataOntology.getAxioms
		try {
			this.pelops.manager.saveOntology(this.pelops.ontologyT, new RDFXMLDocumentFormat(),
					IRI.create(this.pelops.file2.toURI()));
		} catch (OWLOntologyStorageException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// add the selected time data individual to the selected point
		// individual
		// selected point
		String point1_hash = new String(
				udoCollection.selectedSubjectNXObjects[0].hashCode() + "_" + udoCollection.selectedSubjectPoints[0]);

		// predicat
		// comboBox
		String objectPropertyShortForm = (String) comboBox.getSelectedItem();

		// object
		// comboBox_2
		String owlNamedIndividualShortForm = (String) comboBox_2.getSelectedItem();

		// add point coordinates to ontology
		OWLpointWriter pointWriter = new OWLpointWriter();
		try {
			pointWriter.writeCurrentVertecesOfEdge((Edge) udoCollection.selectedSubjectNXObjects[0],
					udoCollection.selectedSubjectPoints[0], this.pelops.ontologyT, this.pelops.manager);
		} catch (RemoteException | NXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// add punning to ontology
		ClassManipulator classManipulator = new ClassManipulator();
		String selectedClassShortForm = classManipulator.convertPointIndividuumToClass(this.pelops.ontologyT,
				this.pelops.manager, new String(udoCollection.selectedSubjectNXObjects[0].hashCode() + "_"
						+ udoCollection.selectedSubjectPoints[0]));
		// add the point to its own class
		classManipulator.setClassAxiomToPoint(this.pelops.ontologyT, this.pelops.manager, selectedClassShortForm,
				new String(udoCollection.selectedSubjectNXObjects[0].hashCode() + "_"
						+ udoCollection.selectedSubjectPoints[0]));
		// ObjectPropertyAssertion
		objectPropertyManipulator.setOWLObjectPropertyAxiomBetween1PointAnd1OWLNamedIndividualFromDifferentOntology(
				this.pelops.ontologyT, timeDataOntology, this.pelops.manager, objectPropertyShortForm, point1_hash,
				"#" + owlNamedIndividualShortForm);

	}

	private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {

		// JFileChooser-Objekt erstellen
		JFileChooser chooser = new JFileChooser();
		// Dialog zum Oeffnen von Dateien anzeigen
		int rueckgabeWert = chooser.showOpenDialog(null);

		if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
			// Ausgabe der ausgewaehlten Datei
			System.out.println("Die zu öffnende Datei ist: " + chooser.getSelectedFile().getName());

			timeDataFilePath = chooser.getSelectedFile().getAbsolutePath();

			// create ontology from file

			try {
				timeDataOntology = staticpelops.manager.loadOntologyFromOntologyDocument(
						IRI.create("file:///" + GUISwing.timeDataFilePath.replace('\\', '/')));
			} catch (OWLOntologyCreationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(objectPropertyManipulator.makeShortForm(
					timeDataOntology,
					objectPropertyManipulator.getAllOWLObjectProperties(timeDataOntology, staticpelops.manager))));

		}

	}

	public void jButton21ActionPerformed(ActionEvent evt) {

	}

	public void jButton22ActionPerformed(ActionEvent evt) {

		Object[][] point = new Object[0][0];
		Object[] point_ephemere = null;
		Integer sample_number = 50;
		String owlNamedIndividualShortForm = (String) comboBox_3.getSelectedItem();
		PrefixManager pm = new DefaultPrefixManager(null, null, timeDataIRIString);

		System.out.println("Selected: " + owlNamedIndividualShortForm);
		OWLDataFactory dataFactory = this.pelops.manager.getOWLDataFactory();
		DLquery myQuery = new DLquery();
		OWLNamedIndividual timepoint = dataFactory.getOWLNamedIndividual("#" + owlNamedIndividualShortForm, pm);
		OWLDataPropertyExpression hasNXhashCode = dataFactory.getOWLDataProperty("#hasNXhashCode", pm);
		OWLDataPropertyExpression hasExpectationValueX = dataFactory.getOWLDataProperty("#hasExpectationValueX", pm);
		OWLDataPropertyExpression hasVarianceValueX = dataFactory.getOWLDataProperty("#hasVarianceValueX", pm);

		OWLDataPropertyExpression hasExpectationValueY = dataFactory.getOWLDataProperty("#hasExpectationValueY", pm);
		OWLDataPropertyExpression hasVarianceValueY = dataFactory.getOWLDataProperty("#hasVarianceValueY", pm);

		OWLDataPropertyExpression hasExpectationValueZ = dataFactory.getOWLDataProperty("#hasExpectationValueZ", pm);
		OWLDataPropertyExpression hasVarianceValueZ = dataFactory.getOWLDataProperty("#hasVarianceValueZ", pm);

		OWLClass[] superclass = myQuery.getDirectClassOfIndividual(timepoint, this.pelops.ontologyT);

		if (superclass.length == 0) {
			System.out.println("no Punning possible for selected point in time.");
		}

		else {
			System.out.println("Punning Class: " + superclass[0]);

			OWLNamedIndividual[] individuals = myQuery.query1(superclass[0], this.pelops.ontologyT);

			if (individuals.length == 0) {
				System.out.println("No time variant uncertainty data available for the selected point in time.");
			} else {
				Object[][] influenced_point = null;
				point = new Object[individuals.length][9];

				for (int k = 0; k < individuals.length; k++) {
					OWLNamedIndividual normaldistribution = myQuery.query2(individuals[k], this.pelops.ontologyT,
							superclass[0]);

					// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
					ClassManipulator classManipulator = new ClassManipulator();
					PrefixManager pm1 = new DefaultPrefixManager(null, null,
							this.pelops.ontologyT.getOntologyID().getOntologyIRI().get().toString());

					OWLDataPropertyExpression hasValue = dataFactory.getOWLDataProperty("#hasValue", pm1);
					OWLDataPropertyExpression hasNXhashCode1 = dataFactory.getOWLDataProperty("#hasNXhashCode", pm1);

					OWLLiteral literal = myQuery.getDataPropertyValue(individuals[k], hasValue, this.pelops.ontologyT);
					OWLLiteral nxHashCode = myQuery.getDataPropertyValue(individuals[k], hasNXhashCode1,
							this.pelops.ontologyT);

					String PunningClassName = classManipulator.convertPointIndividuumToClass(this.pelops.ontologyT,
							this.pelops.manager, nxHashCode.getLiteral());
					try {
						influenced_point = myQuery.queryPointsInClassOfInterest(this.pelops.ontologyT,
								this.pelops.manager, PunningClassName);

					} catch (OWLOntologyCreationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// if both influencing and influenced points have no
					// attached
					// uncertainty information

					point_ephemere = new Object[9];
					// Point3d selectedPoint = null;

					// iri_string
					point_ephemere[0] = individuals[k].getIRI().getIRIString();

					// hash_string
					point_ephemere[1] = nxHashCode.getLiteral();

					// if the selected point has no attached expectation or
					// variance
					// information
					if (influenced_point.length == 0) {
						OWLObjectProperty objectProperty = dataFactory.getOWLObjectProperty("#ordered_list_has_Slot",
								pm1);

						// expectation
						Set xyz = myQuery.getIndividualsByObjectProperty(individuals[k], objectProperty,
								this.pelops.ontologyT);
						//
						for (Iterator iterator2 = xyz.iterator(); iterator2.hasNext();) {
							OWLNamedIndividual indi = (OWLNamedIndividual) iterator2.next();

							// ToDo: check if the point has uncertainty...create
							// a better
							// indicator: Class assertion: if VarianceX,Y or Z
							// -> Uncertain
							// Point.

							// ...check to which class the individual belongs
							// and get
							// its
							// dataProperty

							OWLLiteral literal1 = myQuery.getDataPropertyValue(indi, hasValue, this.pelops.ontologyT);

							OWLClass check = dataFactory.getOWLClass("#Cartesian_X_Coordinate", pm1);
							if (myQuery.checkIndividualClassAffiliation(indi, check, this.pelops.ontologyT)) {
								point_ephemere[2] = Double.parseDouble(literal1.getLiteral());
								// System.out.println("Check: " + check.getIRI()
								// + " has literal " + literal.getLiteral());

							}
							check = dataFactory.getOWLClass("#Cartesian_Y_Coordinate", pm1);
							if (myQuery.checkIndividualClassAffiliation(indi, check, this.pelops.ontologyT)) {
								double y_coordinate = Double.parseDouble(literal1.getLiteral());
								point_ephemere[3] = y_coordinate;
								// System.out.println("Check: " + check.getIRI()
								// + " has literal " + literal.getLiteral());
							}
							//
							check = dataFactory.getOWLClass("#Cartesian_Z_Coordinate", pm1);
							if (myQuery.checkIndividualClassAffiliation(indi, check, this.pelops.ontologyT)) {
								double z_coordinate = Double.parseDouble(literal1.getLiteral());
								point_ephemere[4] = z_coordinate;
								// System.out.println("Check: " + check.getIRI()
								// + " has literal " + literal.getLiteral());
							}

							// variance
							point_ephemere[5] = 0.0;
							point_ephemere[6] = 0.0;
							point_ephemere[7] = 0.0;
						}
					}
					// or use the attache expectation and variance information
					else {
						// expectation
						point_ephemere[2] = (double) influenced_point[0][5];
						point_ephemere[3] = (double) influenced_point[0][6];
						point_ephemere[4] = (double) influenced_point[0][7];
						// variance
						point_ephemere[5] = (double) influenced_point[0][8];
						point_ephemere[6] = (double) influenced_point[0][9];
						point_ephemere[7] = (double) influenced_point[0][10];

					}

					// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

					point[k][2] = Double.parseDouble(myQuery
							.getDataPropertyValue(normaldistribution, hasExpectationValueX, this.pelops.ontologyT)
							.getLiteral()) + (double) point_ephemere[2];
					point[k][3] = Double.parseDouble(myQuery
							.getDataPropertyValue(normaldistribution, hasExpectationValueY, this.pelops.ontologyT)
							.getLiteral()) + (double) point_ephemere[3];
					point[k][4] = Double.parseDouble(myQuery
							.getDataPropertyValue(normaldistribution, hasExpectationValueZ, this.pelops.ontologyT)
							.getLiteral()) + (double) point_ephemere[4];
					point[k][5] = Double.parseDouble(
							myQuery.getDataPropertyValue(normaldistribution, hasVarianceValueX, this.pelops.ontologyT)
									.getLiteral())
							+ (double) point_ephemere[5];
					point[k][6] = Double.parseDouble(
							myQuery.getDataPropertyValue(normaldistribution, hasVarianceValueY, this.pelops.ontologyT)
									.getLiteral())
							+ (double) point_ephemere[6];
					point[k][7] = Double.parseDouble(
							myQuery.getDataPropertyValue(normaldistribution, hasVarianceValueZ, this.pelops.ontologyT)
									.getLiteral())
							+ (double) point_ephemere[7];
					point[k][8] = sample_number;

					System.out.println(this.getClass().getName() + ".point: " + point[k][0] + "/" + point[k][1] + "/"
							+ point[k][2] + "/" + point[k][3] + "/" + point[k][4] + "/" + point[k][5] + "/"
							+ point[k][6] + "/" + point[k][6] + "/" + point[k][8]);

				}

			}

			// point[i][0] = iri_string;
			// point[i][1] = hash_string;
			// point[i][2] = expectation_x;
			// point[i][3] = expectation_y;
			// point[i][4] = expectation_z;
			// point[i][5] = variance_x;
			// point[i][6] = varinace_y;
			// point[i][7] = variance_z;
			// point[i][8] = sample_number; // number of samples for Matlab

			this.pelops.owl2matlab_matrix = point;

			if (this.pelops.owl2matlab_matrix.length == 0) {
				System.out.println("Query returns no result.");
			} else {

				try {
					this.pelops.owl2matlab();
				} catch (OWLOntologyCreationException | RemoteException | OWLOntologyStorageException | NXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
		// code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
		 * html
		 */

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(GUISwing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(GUISwing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(GUISwing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(GUISwing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>
		/**
		 * // You should execute this part on the Event Dispatch Thread //
		 * because it modifies a Swing component JFXPanel jfxPanel = new
		 * JFXPanel(); jFrame.add(jfxPanel);
		 *
		 * // Creation of scene and future interactions with JFXPanel // should
		 * take place on the JavaFX Application Thread Platform.runLater(() -> {
		 * WebView webView = new WebView(); jfxPanel.setScene(new
		 * Scene(webView));
		 * webView.getEngine().load("http://www.stackoverflow.com/"); });
		 */

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					GUISwing guiSwing = new GUISwing();
					guiSwing.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					// JOptionPane.showMessageDialog(null, "myMesage"+e);
				}
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton restoreOne;
	private javax.swing.JButton jButton13;
	private javax.swing.JButton jButton14;
	private javax.swing.JButton jButton15;
	private javax.swing.JButton jButton16;
	private javax.swing.JButton jButton17;
	private javax.swing.JButton jButton18;
	private javax.swing.JButton restoreThree;
	private javax.swing.JButton restoreTwo;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton jButton6;
	private javax.swing.JButton jButton7;
	private javax.swing.JButton jButton8;
	private javax.swing.JButton jButton9;
	private javax.swing.JComboBox<String> jComboBox1;
	private javax.swing.JComboBox<String> jComboBox2;
	private javax.swing.JLabel three;
	private javax.swing.JLabel one;
	private javax.swing.JLabel two;
	private javax.swing.JLabel five;
	private javax.swing.JLabel four;
	private javax.swing.JLabel nxlogo;
	private javax.swing.JLabel matlabLogo;
	private javax.swing.JLabel matllabLabel;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel10;
	private javax.swing.JPanel jPanel11;
	private javax.swing.JPanel jPanel12;
	private javax.swing.JPanel jPanel13;
	private javax.swing.JPanel jPanel14;
	private javax.swing.JPanel jPanel15;
	private javax.swing.JPanel jPanel16;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel panelThreeInOne;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTabbedPane jTabbedPane2;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JPanel paneOneInsideOne;
	private javax.swing.JPanel paneOneInsideThree;
	private javax.swing.JPanel paneOneInsideTwo;
	private javax.swing.JPanel panelOne;
	private javax.swing.JPanel panelThree;
	private javax.swing.JPanel panelTwo;
	private javax.swing.JPanel panelOneInOne;
	private javax.swing.JButton pnaelTwoButtonOne;
	private javax.swing.JButton pnaelTwoButtonThree;
	private javax.swing.JButton pnaelTwoButtonTwo;
	private javax.swing.JPanel pnaelTwoInOne;
	private javax.swing.JPanel pnaelTwoInThree;
	private javax.swing.JPanel panelTwoInTwo;
	private javax.swing.JTabbedPane panelThreeInOneOne;
	private JPanel panel;
	private JButton btnNewButton;
	private JComboBox<String> comboBox_objectProperties;
	private JComboBox<String> comboBox_1;
	private JComboBox<String> comboBox_class1;
	private JButton jButton11;
	private JButton btnNewButton2;
	private JPanel paneOneInsideFour;
	private JPanel panel_3;
	private JPanel panel_4;
	private JButton btnIndividual;
	private JButton btnSelectTimeData;
	private JButton btnSelectTimeData_1;
	private JComboBox comboBox;
	private JComboBox comboBox_2;
	private JComboBox<String> comboBox_3;
	private JButton btnSelectTimeData_2;
	// End of variables declaration//GEN-END:variables

	class CustomOutputStream extends OutputStream {

		private JTextArea textArea;

		public CustomOutputStream(JTextArea textArea) {
			this.textArea = textArea;
		}

		@Override
		public void write(int b) throws IOException {
			// redirects data to the text area
			textArea.append(String.valueOf((char) b));
			// scrolls the text area to the end of data
			textArea.setCaretPosition(jTextArea1.getDocument().getLength());

		}
	}

	class MyComboBoxRenderer extends BasicComboBoxRenderer {
		public Component getListCellRendererComponent(javax.swing.JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			if (isSelected) {
				// setBackground(jComboBox1.getBackground());
				// setForeground(jComboBox1.getForeground());
				if (-1 < index) {
					list.setToolTipText(jComboBox1.getItemAt(index));
				}
			}
			setFont(list.getFont());
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}
}
