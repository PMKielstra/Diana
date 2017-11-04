package com.diana.main;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.diana.main.ArtemisConnector.ArtemisConnectorListener;

import jsyntaxpane.DefaultSyntaxKit;


/**
 * <p>The main window UI.  Handles communication between the user and the backend.</p>
 * @author 13Clocks
 * */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int MAX_SHIPS = 8;
	
	private JPanel contentPane;
	private JEditorPane textArea;
	private JMenuItem mntmDisconnect;
	private JMenuItem mntmConnect;
	private JRadioButtonMenuItem[] shipNumbers = new JRadioButtonMenuItem[MAX_SHIPS];
	private JMenu mnSetShipNumber;
	
	private RulesetParser parser = new RulesetParser();
		
	private ArtemisConnector server;

	/**
	 * <p>Create, but don't show, a MainWindow JFrame.</p>
	 * */
	public MainWindow() throws IOException {
		setIconImage(ImageIO.read(ClassLoader.getSystemResource("Diana icon.png"))); //Set the icon and title for the titlebar.
		setTitle("Diana for Artemis 2.3");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //This is the main window, so Diana should exit when it is closed.
		
		setBounds(100, 100, 800, 600); //Set the window size and positions.
		
		contentPane = new JPanel(); //Create a panel.
		contentPane.setBorder(new EmptyBorder(1, 2, 2, 2)); //Give it a border (the top is one smaller than the rest because the menu bar will have a bottom margin).
		contentPane.setLayout(new BorderLayout(0, 0)); //Set the layout to respect borders.
		setContentPane(contentPane); //Set this panel as the contentPane.
		
		textArea = new JEditorPane(); //Create a JEditorPane for user text entry.
		contentPane.add(new JScrollPane(textArea), BorderLayout.CENTER); //Add it to the content pane.
		textArea.setEditorKit(new DefaultSyntaxKit(new RulesetRegexLexer())); //Set the EditorKit.  This is where JSyntaxPane is used.
		
		JMenuBar menuBar = new JMenuBar(); //Create and add a menu bar.
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File"); //Create and add a file menu.
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenRuleset = new JMenuItem("Open ruleset"); //File>Open ruleset
		mntmOpenRuleset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser chooser = new JFileChooser(); //Create a file chooser dialog.
				chooser.setFileFilter(new FileNameExtensionFilter("Text files (.txt)", "txt")); //Suggest it only open text files.
				chooser.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) { //When a file has been selected
						File file = chooser.getSelectedFile();
						if(file == null || !file.exists()) return; //If the file exists,
						try(BufferedReader reader = new BufferedReader(new FileReader(file))){ //Open it (try-with-resources requires Java 7 or up),
							StringBuilder sb = new StringBuilder();
							String line;
							while((line = reader.readLine()) != null) sb.append(line).append("\n"); //Read it line-by-line,
							textArea.setText(sb.toString()); //And load it into the text area.
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}});
				chooser.showOpenDialog(MainWindow.this); //Show the dialog.
			}});
		mnFile.add(mntmOpenRuleset);
		
		JMenuItem mntmSaveRuleset = new JMenuItem("Save ruleset"); //File>Save ruleset
		mntmSaveRuleset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser chooser = new JFileChooser(); //Create a save file dialog.
				chooser.setFileFilter(new FileNameExtensionFilter("Text files (.txt)", "txt")); //Suggest text as a file type.
				chooser.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) { //When a filename has been selected,
						File file = chooser.getSelectedFile();
						if(file == null) return; //Quit if nothing was selected.
						if(!file.getPath().endsWith(".txt")) file = new File(file.getPath() + ".txt"); //Add .txt onto the end of the path if it isn't already there.
						try (PrintWriter printWriter = new PrintWriter(file)){ //Open the file for writing (try-with-resources requires Java 7 or up).  This will create it if it doesn't exist.
							printWriter.print(textArea.getText()); //Write the ruleset to the file.
							printWriter.flush(); //Flush the stream to the file.
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
					}});
				chooser.showSaveDialog(MainWindow.this); //Show the chooser.
			}});
		mnFile.add(mntmSaveRuleset);
		
		JMenuItem mntmAbout = new JMenuItem("Show licenses"); //File>Show licenses.
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LicensesWindow(MainWindow.this, true); //Create (and automatically show) a new LicensesWindow.
			}});
		mnFile.add(mntmAbout);
		
		JMenuItem mntmQuit = new JMenuItem("Quit"); //File>Quit
		mntmQuit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0); //Quit with exit code 0 (no error).
			}});
		mnFile.add(mntmQuit);
		
		JMenu mnArtemis = new JMenu("Artemis"); //Create and add an Artemis menu.
		menuBar.add(mnArtemis);
		
		mnSetShipNumber = new JMenu("Ship number"); //Artemis>Set ship number
		mnArtemis.add(mnSetShipNumber);
		ButtonGroup shipNumberButtonGroup = new ButtonGroup(); //Create a ButtonGroup to manage the radio button menu items.
		for(int i = 0; i < MAX_SHIPS; i++) { //For each ship index,
			shipNumbers[i] = new JRadioButtonMenuItem(Integer.toString(i+1)); //Generate a button with that number.  (Index 0 should point to ship number 1.)
			shipNumberButtonGroup.add(shipNumbers[i]); //Add it to the ButtonGroup,
			mnSetShipNumber.add(shipNumbers[i]); //And add it to the menu.
			final int j = i; //We're going to use i in a listener now, so save it as final.
			shipNumbers[i].addItemListener(new ItemListener() { //When this radio button is selected,
				@Override
				public void itemStateChanged(ItemEvent event) {
					if(event.getStateChange() == ItemEvent.SELECTED) PreferenceManager.getInstance().setIntPreference(PreferenceManager.PREF_SHIP_NUM, j); //Save the selection.
				}});
		}
		int selectedShip = PreferenceManager.getInstance().getIntPreference(PreferenceManager.PREF_SHIP_NUM); //Which ship was selected last time?
		if(selectedShip > -1) { //If we have previously saved a ship number,
			shipNumbers[selectedShip].setSelected(true); //Select that ship number.
		}else { //Otherwise,
			shipNumbers[0].setSelected(true); //Select the first one.
		}
		
		mntmConnect = new JMenuItem("Connect..."); //Artemis>Connect...
		mntmConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Ruleset ruleset = parser.parseRuleset(textArea.getText()); //Try to parse the user's text into a Ruleset.
				if(ruleset.rules.size() == 0) { //If it doesn't parse into a valid set of rules,
					JOptionPane.showMessageDialog(MainWindow.this, "No valid rules!"); //Alert the user,
					return; //And cancel the connection attempt.
				}
				String host = JOptionPane.showInputDialog(MainWindow.this, "Please enter server IP address.", PreferenceManager.getInstance().getStringPreference(PreferenceManager.PREF_SERVER_IP)); //Ask for the Artemis IP address, and suggest the one that worked last time.
				try {
					if(host != null && host.length() > 0) connectToArtemis(host, ruleset); //If the user entered something, try to connect.
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}});
		mnArtemis.add(mntmConnect);
		
		mntmDisconnect = new JMenuItem("Disconnect"); //Artemis>Disconnect.
		mntmDisconnect.setEnabled(false); //This isn't enabled at first -- it's only turned on when Diana is connected to Artemis.
		mntmDisconnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				server.disconnect(); //Disconnect the ArtemisConnector.
			}});
		mnArtemis.add(mntmDisconnect);

	}
	
	private void connectToArtemis(String host, Ruleset ruleset) throws IOException {
		int shipNumber = 1;
		for(int i = 0; i < MAX_SHIPS; i++) { //Iterate over all ship-number radio button menu items to find the selected one..
			if(shipNumbers[i].isSelected()) {
				shipNumber = i + 1; //Set the ship number (1 through 8) to the index of the selected item (0 through 7) plus one.
				break;
			}
		}
		server = new ArtemisConnector(host, PreferenceManager.getInstance().getStringPreference(PreferenceManager.PREF_ARTEMIS_INSTALL), shipNumber, ruleset); //Create 
		server.setArtemisConnectorListener(new ArtemisConnectorListener() { //Set the listener to manage the text area and the connect and disconnect menus.
			@Override
			public void onConnect() { //When connected,
				textArea.setEnabled(false); //Disable editing, the Connect menu button, and the ship number selection menu.
				mntmConnect.setEnabled(false);
				mnSetShipNumber.setEnabled(false);
				mntmDisconnect.setEnabled(true); //Enable the Disconnect menu button.
				PreferenceManager.getInstance().setStringPreference(PreferenceManager.PREF_SERVER_IP, host); //We've successfully connected, so save the IP address for next time.
			}
			@Override
			public void onDisconnect() { //When disconnected,
				textArea.setEnabled(true); //Enable editing, the Connect menu button, and the ship number selection menu.
				mntmConnect.setEnabled(true);
				mnSetShipNumber.setEnabled(true);
				mntmDisconnect.setEnabled(false); //Disable the Disconnect menu button.
			}});
		server.connect(); //Start the connector.
	}

}
