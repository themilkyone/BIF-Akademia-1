package com.ttpsc.workable;

import com.ttpsc.link.StandardLinkService;

import wt.pom.PersistenceException;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.wip.CheckoutLink;
import wt.vc.wip.NonLatestCheckoutException;
import wt.vc.wip.WorkInProgressException;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;

public class StandardWorkableService extends StandardManager implements WorkableService {

	public static StandardWorkableService newStandardWorkableService() throws WTException {
		StandardWorkableService standardWorkableService = new StandardWorkableService();
		standardWorkableService.initialize();

		return standardWorkableService;
	}

	@Override
	public <T extends Workable> T checkOut(T workable, String checkOutComment) throws Exception {

		Workable workingCopy;
		if (!WorkInProgressHelper.isCheckedOut(workable)) {
			workingCopy = WorkInProgressHelper.service
					.checkout(workable, WorkInProgressHelper.service.getCheckoutFolder(), checkOutComment)
					.getWorkingCopy();

		} else {
			workingCopy=WorkInProgressHelper.service.workingCopyOf(workable);
		}
		return (T) workingCopy;

	}

	@Override
	public <T extends Workable> T checkIn(T workable, String checkInComment) throws Exception {

		Workable checkIn = WorkInProgressHelper.service.checkin(workable, checkInComment);

		return (T)checkIn;
	}

}
