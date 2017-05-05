package com.ericsson.appiot.gateway.examples.twitter;

import com.ericsson.appiot.gateway.AppIoTGateway;
import com.ericsson.appiot.gateway.AppIoTListener;
import com.ericsson.appiot.gateway.device.DeviceAppIoTListener;
import com.ericsson.appiot.gateway.device.DeviceManager;
import com.ericsson.appiot.gateway.examples.smartobjects.TweetingAddressableTextDisplay;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TwitterGateway {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private AppIoTListener appIoTListener;
	private AppIoTGateway appIoTGateway;

	public static void main(String[] args) {
		TwitterGateway application = new TwitterGateway();
		application.start();
	}
	
	private void start() {
		logger.log(Level.INFO, "Http Rest Gateway starting up.");
		DeviceManager deviceManager = new DeviceManager();
        deviceManager.registerSmartObject("/3341", TweetingAddressableTextDisplay.class);
        
		appIoTListener = new DeviceAppIoTListener(deviceManager);
        
		appIoTGateway = new AppIoTGateway(appIoTListener);
		appIoTGateway.start();
			
		logger.log(Level.INFO, "Http Rest Gateway started. Type quit to shut down.");

		Scanner scanner = new Scanner(System.in);
		while(!scanner.nextLine().equalsIgnoreCase("quit")) {
		}
		scanner.close();
		logger.log(Level.INFO, "Simple Gateway shut down.");
	}
}
