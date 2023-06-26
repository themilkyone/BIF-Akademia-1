package com.ttpsc.listener.template;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import wt.services.ServiceEventListenerAdapter;
import wt.vc.wip.WorkInProgressServiceEvent;


public class EventListenerAdapter extends ServiceEventListenerAdapter {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(EventListenerAdapter.class);

    public EventListenerAdapter(String className) {
        super(className);

    }

    @Override
    public void notifyVetoableEvent(Object event) throws Exception {
        LOGGER.debug("EventListenerAdapter.notifyVetoableEvent ::Entry with " + event);
        // To check event type :
        // if (event instanceof WorkInProgressServiceEvent
        // && StringUtils.equals(WorkInProgressServiceEvent.POST_CHECKIN, ((WorkInProgressServiceEvent) event).getEventType())) {
        // To check event object
        // Object object = ((WorkInProgressServiceEvent) event).getEventTarget();
        // if (object instanceof WTDocument) {
        // In this example: If it is POST_CHECKIN event from WorkInProgressServiceEvent and if object is of type WTDocument
        // }
        // }

        // Example
        if (event instanceof WorkInProgressServiceEvent) {
            WorkInProgressServiceEvent wipse = (WorkInProgressServiceEvent) event;
            if (StringUtils.equals(WorkInProgressServiceEvent.PRE_CHECKIN, wipse.getEventType())) {
                // do something before checkin
            }
            if (StringUtils.equals(WorkInProgressServiceEvent.POST_CHECKOUT, wipse.getEventType())) {
                //
            }
        }
    }
}

