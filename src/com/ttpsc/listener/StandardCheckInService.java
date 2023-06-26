package com.ttpsc.listener;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.ttpsc.listener.template.StandardEventService;

import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.vc.wip.WorkInProgressServiceEvent;

public class StandardCheckInService extends StandardManager implements CheckInService, Serializable {

	private static final Logger LOGGER = Logger.getLogger(StandardCheckInService.class);

	private static final long serialVersionUID = 3619719519750330029L;

	@Override
	protected synchronized void performStartupProcess() throws ManagerException {

		LOGGER.info("StandardCheckInService.performStartUpProcess(): " + getName());
		CheckInListenerAdapter listener = new CheckInListenerAdapter(StandardCheckInService.class.getName());
		getManagerService().addEventListener(listener,
				WorkInProgressServiceEvent.generateEventKey(WorkInProgressServiceEvent.POST_CHECKIN));
	}

	public static StandardCheckInService newStandardCheckInService() throws WTException {
		StandardCheckInService instance = new StandardCheckInService();
		instance.initialize();
		return instance;
	}
}
