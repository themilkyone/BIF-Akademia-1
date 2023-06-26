package com.ttpsc.listener.template;

import java.io.Serializable;

import org.apache.log4j.Logger;

import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.vc.wip.WorkInProgressServiceEvent;

public class StandardEventService extends StandardManager implements EventService, Serializable {

    private static final Logger LOGGER = Logger.getLogger(StandardEventService.class);

    private static final long serialVersionUID = 3619719519750330029L;

    @Override
    protected synchronized void performStartupProcess() throws ManagerException {
        // Create an event listener be creation of Listener Adapter
        EventListenerAdapter listener = new EventListenerAdapter(StandardEventService.class.getName());
        // register specific event with specific event type
        // list of events under https://connect.tt.com.pl/space/display/TC/Windchill+Listener+Events
        getManagerService().addEventListener(listener, WorkInProgressServiceEvent.generateEventKey(WorkInProgressServiceEvent.POST_CHECKIN));
        LOGGER.debug("Initialized EventListenerAdapter");
    }

    public static StandardEventService newStandardPostCheckinService() throws WTException {
        StandardEventService instance = new StandardEventService();
        instance.initialize();
        return instance;
    }
}
