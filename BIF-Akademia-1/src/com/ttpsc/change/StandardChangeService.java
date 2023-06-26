package com.ttpsc.change;

import java.util.Date;
import java.util.Vector;

import wt.change2.AffectedActivityData;
import wt.change2.ChangeActivity2;
import wt.change2.ChangeActivityIfc;
import wt.change2.ChangeException2;
import wt.change2.ChangeHelper2;
import wt.change2.ChangeNoticeComplexity;
import wt.change2.ChangeOrder2;
import wt.change2.ChangeRecord2;
import wt.change2.Changeable2;
import wt.change2.WTChangeActivity2;
import wt.change2.WTChangeOrder2;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.inf.container.WTContainerRef;
import wt.project.Role;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.team.Team;
import wt.team.TeamReference;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

public class StandardChangeService extends StandardManager implements ChangeService {

	public static StandardChangeService newStandardChangeService() throws WTException {
		StandardChangeService service = new StandardChangeService();
		service.initialize();

		return service;
	}

	@Override
	public WTChangeOrder2 createChangeNotice(String name, WTContainerRef ref)
			throws WTException, WTPropertyVetoException {
		WTChangeOrder2 changeOrder = WTChangeOrder2.newWTChangeOrder2(name);
		changeOrder.setContainerReference(ref);
		changeOrder.setChangeNoticeComplexity(ChangeNoticeComplexity.BASIC);

		return (WTChangeOrder2) PersistenceHelper.manager.save(changeOrder);
	}

	@Override
	public WTChangeActivity2 createChangeTask(String name, WTChangeOrder2 changeNotice) throws WTException {

		try {
			WTChangeActivity2 changeTask = WTChangeActivity2.newWTChangeActivity2(name);
			changeTask.setContainerReference(changeNotice.getContainerReference());
			ChangeActivityIfc changeActivity = ChangeHelper2.service.saveChangeActivity(changeNotice, changeTask,
					false);

			return (WTChangeActivity2) changeActivity;

		} catch (WTPropertyVetoException e) {

			throw new WTException(e);
		}

	}

	@Override
	public ChangeRecord2 addToResultingObject(Changeable2 changeAble, ChangeActivity2 changeActivity)
			throws WTException {

		ChangeRecord2 changeRecord = ChangeRecord2.newChangeRecord2(changeAble, changeActivity);
		Persistable save = PersistenceHelper.manager.save(changeRecord);
		return (ChangeRecord2) save;
	}

	@Override
	public ChangeRecord2 addToResultingObjectByHelper(Changeable2 changeAble, ChangeActivity2 changeActivity)
			throws WTException {
		Vector partVec = new Vector();
		partVec.addElement(changeAble);
		Vector storeAssociations = ChangeHelper2.service.storeAssociations(ChangeRecord2.class, changeActivity,
				partVec);

		return (ChangeRecord2) storeAssociations.get(0);
	}

	@Override
	public AffectedActivityData addObjectToAffectedObject(Changeable2 changeAble, ChangeActivity2 changeActivity)
			throws ChangeException2, WTException {
		Vector partVec = new Vector();
		partVec.addElement(changeAble);
		Vector storeAssociations = ChangeHelper2.service.storeAssociations(AffectedActivityData.class, changeActivity,
				partVec);

		return (AffectedActivityData) storeAssociations.get(0);
	}

	@Override
	public WTChangeActivity2 createChangeTask(String name, WTChangeOrder2 changeNotice, Team team) throws WTException {
		try {
			WTChangeActivity2 changeTask = WTChangeActivity2.newWTChangeActivity2(name);
			changeTask.setContainerReference(changeNotice.getContainerReference());

			TeamReference teamReference = TeamReference.newTeamReference(team);
			changeTask.setTeamId(teamReference);
			ChangeActivityIfc save = ChangeHelper2.service.saveChangeActivity(changeNotice, changeTask, false);

			return (WTChangeActivity2) save;
		} catch (WTPropertyVetoException e) {

			throw new WTException(e);
		}
	}

	@Override
	public Team createTeam(WTChangeOrder2 changeNotice) throws WTException {
		Team newTeam = Team.newTeam();
		try {
			newTeam.setName("CustomTeam" + new Date());
			newTeam.setDomainRef(changeNotice.getDomainRef());
			Team save = (Team) PersistenceHelper.manager.save(newTeam);
			save.addPrincipal(Role.REVIEWER, SessionHelper.getPrincipal());
			return save;
		} catch (WTPropertyVetoException e) {
			// TODO Auto-generated catch block
			throw new WTException(e);
		}

	}
}
