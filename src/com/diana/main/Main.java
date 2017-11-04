package com.diana.main;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * <p>The point of entry to the program.  Do not instantiate this class.</p>
 * @author 13Clocks.
 * */
public class Main {
	
	private static String[] possibleInstallLocations = new String[] { //A list of places to look for the Artemis installation.
				"C:/Program Files (x86)/Artemis",
				"C:/Program Files/Artemis"
			};
	
	/**
	 * <p>Called on startup.  This has two roles.  First, it looks for the Artemis install location and, if it doesn't find it, asks the user where it is.  Then, if it knows where Artemis is installed, it launches the main window, which contains the rest of the program logic.</p>
	 * @see MainWindow
	 * @param args Command-line arguments.  These are currently ignored.
	 * */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //Use system look and feel
		MainWindow mw = new MainWindow(); //Create the user interface (so that we can use it as a parent for the file chooser further down) but don't show it yet.
	
		if(PreferenceManager.getInstance().getStringPreference(PreferenceManager.PREF_ARTEMIS_INSTALL).equals("") || !new File(PreferenceManager.getInstance().getStringPreference(PreferenceManager.PREF_ARTEMIS_INSTALL) + "/Artemis.exe").exists()) { //If we haven't already got the Artemis install path, or if Artemis has moved,
			boolean foundLocation = false;
			for(String location : possibleInstallLocations) { //Search through all default locations.
				File file = new File(location + "/Artemis.exe"); //Check to see if any of them actually contain Artemis.exe.
				if(file.exists()) {
					PreferenceManager.getInstance().setStringPreference(PreferenceManager.PREF_ARTEMIS_INSTALL, location);  //If they do, save this location as the Artemis install location.
					foundLocation = true;
					break;
				}
			}
			if(!foundLocation) { //If we haven't found the location,
				JFileChooser chooser = new JFileChooser(); //Prepare a directory select dialog.
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setDialogTitle("Please select Artemis install directory");
				chooser.setAcceptAllFileFilterUsed(false);
				if(chooser.showOpenDialog(mw) == JFileChooser.APPROVE_OPTION && new File(chooser.getSelectedFile(), "Artemis.exe").exists()) { //If the user has selected a directory containing Artemis.exe,
					PreferenceManager.getInstance().setStringPreference(PreferenceManager.PREF_ARTEMIS_INSTALL, chooser.getSelectedFile().getCanonicalPath()); //Save the directory.
				}else{ //Otherwise,
					System.exit(1); //Clearly, the user isn't ready to launch this program yet.
				}
			}
		}
		
		mw.setVisible(true); //Show the main window.
	}

}

