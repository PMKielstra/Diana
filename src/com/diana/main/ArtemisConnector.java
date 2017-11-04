package com.diana.main;
import java.io.IOException;

import com.walkertribe.ian.Context;
import com.walkertribe.ian.enums.Console;
import com.walkertribe.ian.iface.ArtemisNetworkInterface;
import com.walkertribe.ian.iface.ConnectionSuccessEvent;
import com.walkertribe.ian.iface.DisconnectEvent;
import com.walkertribe.ian.iface.Listener;
import com.walkertribe.ian.iface.ThreadedArtemisNetworkInterface;
import com.walkertribe.ian.protocol.core.setup.ReadyPacket;
import com.walkertribe.ian.protocol.core.setup.SetConsolePacket;
import com.walkertribe.ian.protocol.core.setup.SetShipPacket;
import com.walkertribe.ian.util.PlayerShipUpdateListener;
import com.walkertribe.ian.vesseldata.FilePathResolver;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * <p>A class to keep track of a single connection with an Artemis server, handling communication between the server and the rulesets.  A new instance of this class is created for each connection to Artemis.</p>
 * @author 13Clocks.
 * */
public class ArtemisConnector extends PlayerShipUpdateListener {
	
	/**
	 * <p>Used by UI classes to know when we have connected to or disconnected from Artemis.  They can then update the UI accordingly.</p>
	 * */
	public interface ArtemisConnectorListener {
		void onConnect();
		void onDisconnect();
	}
	
    private static final int DEFAULT_ARTEMIS_PORT = 2010;
    
    //Passed in or created in the constructor.
    private ArtemisNetworkInterface server;
    private Ruleset ruleset;
    private Context ctx;
    private ArtemisConnectorListener listener;
    
    /**
     * <p>Create a new ArtemisConnector, which can then connect to Artemis at the given host.</p>
     * @param host The IP address of the Artemis host.  Optionally, this may include a colon and then a port number: "192.168.1.1:8080".
     * @param artemisInstallPath The path to the folder in which Artemis is installed: "C:\Program Files (x86)\Artemis".  This folder must contain Artemis.exe.
     * @param shipNumber The number of the ship to connect to.  Generally, this will be 1.
     * @param ruleset The rules to follow for this connection, as input by the user and parsed by the RulesetParser.
     * @see Ruleset
     * @see RulesetParser
     * */
    public ArtemisConnector(String host, String artemisInstallPath, int shipNumber, Ruleset ruleset) throws IOException {
        super(shipNumber);
        this.ruleset = ruleset; //Store the ruleset.
        int port = DEFAULT_ARTEMIS_PORT;
        int colonPos = host.indexOf(':'); //Try to find a colon (which precedes a port number) within the IP address.
        if (colonPos != -1) { //If there is one ("192.168.1.1:8080"),
            port = Integer.parseInt(host.substring(colonPos + 1)); //Take everything that comes after it as the port,
            host = host.substring(0, colonPos); //And everything that came before it as the IP address.
        }
        ctx = new Context(new FilePathResolver(artemisInstallPath)); //Create a Context for the Artemis interface.
        server = new ThreadedArtemisNetworkInterface(host, port, ctx); //Create the interface (connects automatically).
        server.addListener(this); //This class is a PlayerShipUpdateListener.
    }
    
    /**
     * <p>Set the ArtemisConnectorListener that will be used to notify the UI when Diana has connected to or disconnected from the server.</p>
     * <p>Only one will usually ever be needed, so this sets a single listener instead of keeping a list.</p>
     * @param l The listener to set.
     * @see ArtemisConnectorListener
     * */
    public void setArtemisConnectorListener(ArtemisConnectorListener l) {
    	listener = l;
    }

    /**
     * <p>Actually connect to Artemis.</p>
     * */
    public void connect() {
    	server.start();
    }
    
    /**
     * <p>Disconnect from Artemis and call the onDisconnect() method for the current listener.</p>
     * @see ArtemisConnectorListener
     * */
    public void disconnect() {
    	server.stop();
    	if(listener != null) listener.onDisconnect();
    }
   
    /**
     * <p>Called by the server when Diana has successfully connected to Artemis.  Sends three packets: select the ship, select Observer console, and set ready to play.  Calls the onConnect() method for the current listener.</p>
     * @see PlayerShipUpdateListener
     * @see ArtemisConnectorListener
     * */
    @Listener
    public void onConnectSuccess(ConnectionSuccessEvent event) {
        server.send(new SetShipPacket(getNumber()));
        server.send(new SetConsolePacket(Console.OBSERVER, true));
        server.send(new ReadyPacket());
        if(listener != null) listener.onConnect();
    }
    
    
    private ArtemisPlayer persistentPlayer = new ArtemisPlayer(getNumber()); //Keep a persistent record as the ArtemisPlayer objects passed in to onShipUpdate have all properties null or undefined except for whatever has changed since the last onShipUpdate.
    
    /**
     * <p>Called by the server whenever a packet pertaining to the current player ship is sent out.  Calls the carryOut() method on the ruleset passed in to the constructor.</p>
     * @param player The player data received from the server.
     * @see Ruleset
     * @see ArtemisPlayer
     * */
    @Override
    public void onShipUpdate(ArtemisPlayer player) {
    	persistentPlayer.updateFrom(player, ctx);
    	if(player != null) ruleset.carryOut(server, persistentPlayer);
    }

    /**
     * <p>Called by the server when disconnected from Artemis.  Calls the onDisconnect() method in the current listener.</p>
     * @see ArtemisConnectorListener
     * */
	@Override
	public void onDisconnect(DisconnectEvent event) {
		listener.onDisconnect();
	}  
    
}