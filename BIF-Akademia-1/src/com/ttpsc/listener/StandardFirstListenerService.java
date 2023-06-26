package com.ttpsc.listener;


import java.io.Serializable;
import wt.fc.PersistenceManagerEvent;
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.vc.wip.WorkInProgressServiceEvent;

public class StandardFirstListenerService extends StandardManager implements Serializable, FirstListenerService {
  public static StandardFirstListenerService newStandardFirstListenerService() throws WTException {
    StandardFirstListenerService service = new StandardFirstListenerService();
    service.initialize();

    return service;
  }

  @Override
  protected synchronized void performStartupProcess() throws ManagerException {
    FirstListenerAdapter adapter = new FirstListenerAdapter(getName());

    getManagerService().addEventListener(adapter,
        PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.INSERT));
    getManagerService().addEventListener(adapter,
        WorkInProgressServiceEvent.generateEventKey(WorkInProgressServiceEvent.POST_CHECKOUT));

  }
}
