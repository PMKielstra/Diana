package com.diana.main;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * <p>A singleton helper class to store "preferences".  (Currently, Diana has no user-editable preferences as such, so the PreferenceManager class uses Java Preferences as a way to store small amounts of persistent dat, such as the location of the Artemis install.)
 * @author 13Clocks
 * */
public class PreferenceManager {
	
	private Preferences prefs = Preferences.userRoot().node(this.getClass().getName()); //Get access to Java Preferences.
	
	/**
	 * <p>Set a String key-value pair as a preference.</p>
	 * */
	public void setStringPreference(String key, String value) {
		prefs.put(key, value);
	}
	
	/**
	 * <p>Get the preference value corresponding to a given key.</p>
	 * @return The value, or an empty String (not null) if the value was not found.
	 * */
	public String getStringPreference(String key) {
		return prefs.get(key, "");
	}
	
	/**
	 * <p>Set an int key-value pair as a preference.</p>
	 * */
	public void setIntPreference(String key, int value) {
		prefs.putInt(key, value);
	}
	
	/**
	 * <p>Get the preference value corresponding to a given key.</p>
	 * @return The value, or -1 (not null) if the value was not found.
	 * */
	public int getIntPreference(String key) {
		return prefs.getInt(key, -1);
	}
	
	/**
	 * <p>Delete all stored preferences.  There is no way to recover them after calling this method short of restoring the entire system from a backup.</p>
	 * */
	public void clearPreferences() {
		try {
			prefs.clear();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>The standard key for where Artemis is installed.</p>
	 * */
	public static final String PREF_ARTEMIS_INSTALL = "PREF_ARTEMIS_INSTALL";
	/**
	 * <p>The standard key for the last-used IP address to connect to Artemis.</p>
	 * */
	public static final String PREF_SERVER_IP = "PREF_SERVER_IP";
	/**
	 * <p>The standard key for the last-used ship number.</p>
	 * */
	public static final String PREF_SHIP_NUM = "PREF_SHIP_NUM";
	
	//Singleton code: private constructor, public getInstance().
	private PreferenceManager() {}
	private static PreferenceManager instance;
	/**
	 * <p>Create an instance of PreferenceManager if one doesn't already exist, and return the current instance.</p>
	 * @return The PreferenceManager.
	 * */
	public static PreferenceManager getInstance() {
		if(instance == null) instance = new PreferenceManager();
		return instance;
	}
}
