package com.ttpsc.listener;

import wt.fc.PersistenceManagerEvent;
import wt.part.WTPartMaster;
import wt.services.ServiceEventListenerAdapter;
import wt.util.WTException;

public class FirstListenerAdapter extends ServiceEventListenerAdapter {

  public FirstListenerAdapter(String s) {
    super(s);
  }

  @Override
  public void notifyVetoableEvent(Object o) throws Exception {
    super.notifyVetoableEvent(o);

    if (o instanceof PersistenceManagerEvent) {
      PersistenceManagerEvent managerEvent = (PersistenceManagerEvent) o;
      Object eventTarget = managerEvent.getEventTarget();

      if (eventTarget instanceof WTPartMaster) {
        WTPartMaster event = (WTPartMaster) eventTarget;

        if (event.getName().startsWith("A")) {
          throw new WTException("Wrong name!");
        }
      }
    }
  }
}
