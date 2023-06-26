package com.ttpsc.change;

import wt.change2.AffectedActivityData;
import wt.change2.ChangeActivity2;
import wt.change2.ChangeException2;
import wt.change2.ChangeOrder2;
import wt.change2.ChangeRecord2;
import wt.change2.Changeable2;
import wt.change2.WTChangeActivity2;
import wt.change2.WTChangeOrder2;
import wt.inf.container.WTContainerRef;
import wt.method.RemoteInterface;
import wt.team.Team;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

@RemoteInterface
public interface ChangeService {
	
  WTChangeOrder2 createChangeNotice(String name, WTContainerRef ref)
      throws WTException, WTPropertyVetoException;
 
  WTChangeActivity2 createChangeTask(String name, WTChangeOrder2 changeNotice) throws WTException;
  
  
  WTChangeActivity2 createChangeTask(String name, WTChangeOrder2 changeNotice, Team team) throws WTException;
  
  Team createTeam(WTChangeOrder2 changeNotice) throws WTException;
  ChangeRecord2 addToResultingObject(Changeable2 changeAble, ChangeActivity2 changeActivity) throws WTException;

  ChangeRecord2 addToResultingObjectByHelper(Changeable2 changeAble, ChangeActivity2 changeActivity) throws WTException;
  
  AffectedActivityData addObjectToAffectedObject(Changeable2 changeAble, ChangeActivity2 changeActivity) throws ChangeException2, WTException;

}
