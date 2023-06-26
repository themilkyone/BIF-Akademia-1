package com.ttpsc.listener.cnStateChange;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.ttpsc.listener.template.EventListenerAdapter;
import com.ttpsc.sql.SQLQueryServiceHelper;

import javassist.expr.Instanceof;
import wt.change2.ChangeService2Event;
import wt.change2.WTChangeActivity2;
import wt.change2.WTChangeOrder2;
import wt.fc.PersistenceHelper;
import wt.fc.collections.WTHashSet;
import wt.fc.collections.WTSet;
import wt.inf.container.WTContainer;
import wt.lifecycle.LifeCycleServiceEvent;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.services.ServiceEventListenerAdapter;
import wt.vc.wip.WorkInProgressServiceEvent;

public class ChangeStateListenerAdapter extends ServiceEventListenerAdapter {

	private static final String DELETE_OLD_PRODUCTS = "Delete old products";
	private static final String ARCHIVE_PRODUCTS = "Archived products";
	private static final Logger LOGGER = Logger.getLogger(ChangeStateListenerAdapter.class);

	static {

		LOGGER.setLevel(Level.DEBUG);
	}

	public ChangeStateListenerAdapter(String var1) {
		super(var1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void notifyVetoableEvent(Object event) throws Exception {

		if (event instanceof LifeCycleServiceEvent) {

			LifeCycleServiceEvent lifeCycleServiceEvent = (LifeCycleServiceEvent) event;

			if (StringUtils.equals(LifeCycleServiceEvent.SET_STATE, lifeCycleServiceEvent.getEventType())) {

				Object eventTarget = lifeCycleServiceEvent.getEventTarget();

				if (eventTarget instanceof WTChangeOrder2) {

					WTChangeOrder2 changeNotice = (WTChangeOrder2) eventTarget;
					if (changeNotice.getName().contains(DELETE_OLD_PRODUCTS)
							&& changeNotice.getLifeCycleState().equals(State.RELEASED)) {

						LOGGER.debug("I am in");
						//1
						
//						WTContainer containerByName = SQLQueryServiceHelper.service.findContainerByName(ARCHIVE_PRODUCTS);
//						SQLQueryServiceHelper.service.deleteParts(containerByName);
						
						//2
//						List<WTPart> partsInContainer = SQLQueryServiceHelper.service.findPartsInContainer(ARCHIVE_PRODUCTS);
//						
//						WTSet containerPartsSet=new WTHashSet(partsInContainer);
//						
//						PersistenceHelper.manager.delete(containerPartsSet);
						
						//3
						List<WTPart> demoOrganizationParts = SQLQueryServiceHelper.service.findPartsInOrganization("Demo Organization");
						
						for(WTPart part:demoOrganizationParts) {
							LOGGER.debug(part.getNumber());
						}

					}

				}
			}
		}

	}
}
