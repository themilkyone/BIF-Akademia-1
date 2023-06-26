package com.ttpsc.listener;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.ptc.windchill.bac.specfile.jaxb.LifeCycleTemplate;
import com.ttpsc.link.LinkServiceHelper;
import com.ttpsc.service.AcademyServiceHelper;
import com.ttpsc.sql.SQLQueryServiceHelper;

import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.inf.container.WTContainer;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.services.ServiceEventListenerAdapter;
import wt.vc.wip.WorkInProgressServiceEvent;

public class CheckInListenerAdapter extends ServiceEventListenerAdapter {

	private static final Logger LOGGER = Logger.getLogger(CheckInListenerAdapter.class);

	static {
		LOGGER.setLevel(Level.DEBUG);
	}

	public CheckInListenerAdapter(String className) {
		super(className);

	}

	@Override
	public void notifyVetoableEvent(Object event) throws Exception {
		if (event instanceof WorkInProgressServiceEvent && StringUtils.equals(WorkInProgressServiceEvent.POST_CHECKIN,
				((WorkInProgressServiceEvent) event).getEventType())) {

			Object object = ((WorkInProgressServiceEvent) event).getEventTarget();

			WTContainer container = SQLQueryServiceHelper.service.findContainerByName("Archived products");
			SQLQueryServiceHelper.service.deleteParts(container);
//			if (object instanceof WTDocument) {
//				WTDocument document = (WTDocument) object;
//				Optional<WTPart> partByName = SQLQueryServiceHelper.service.findPart(document.getName(),document.getContainer());
//
//				if (LOGGER.isDebugEnabled()) {
//					LOGGER.debug("Part by name" + partByName);
//				}
//				if (partByName.isPresent()) {
//
//					LinkServiceHelper.service.createDescribeLink(partByName.get(), document);
//
//				}

//				if (!StringUtils.equals(document.getState().toString(), "RELEASED")) {
//					LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) document, State.toState("RELEASED"));
//				}

		}

	}
}
