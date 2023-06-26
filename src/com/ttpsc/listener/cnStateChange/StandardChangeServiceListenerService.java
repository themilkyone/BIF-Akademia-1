package com.ttpsc.listener.cnStateChange;

import java.io.Serializable;

import com.ttpsc.listener.StandardCheckInService;

import wt.change2.ChangeService2Event;
import wt.fc.PersistenceManagerEvent;
import wt.lifecycle.LifeCycleServiceEvent;
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.vc.wip.WorkInProgressServiceEvent;

public class StandardChangeServiceListenerService extends StandardManager
		implements ChangeStateListenerService, Serializable {

	@Override
	protected synchronized void performStartupProcess() throws ManagerException {
		ChangeStateListenerAdapter adapter = new ChangeStateListenerAdapter(getName());
		getManagerService().addEventListener(adapter,
				LifeCycleServiceEvent.generateEventKey(LifeCycleServiceEvent.SET_STATE));
	}

	public static StandardChangeServiceListenerService newStandardChangeServiceListenerService() throws WTException {
		StandardChangeServiceListenerService instance = new StandardChangeServiceListenerService();
		instance.initialize();
		return instance;

	}

}
