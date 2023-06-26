package com.ttpsc.link;

import com.ttpsc.service.AcademyServiceHelper;
import com.ttpsc.service.StandardAcademyService;
import com.ttpsc.workable.WorkableServiceHelper;

import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.part.WTPart;
import wt.part.WTPartDescribeLink;
import wt.part.WTPartUsageLink;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.vc.wip.Workable;

public class StandardLinkService extends StandardManager implements LinkService {

	public static StandardLinkService newStandardLinkService() throws WTException {
		StandardLinkService standardLinkService = new StandardLinkService();
		standardLinkService.initialize();

		return standardLinkService;
	}

	@Override
	public void createChildForPart(WTPart parent) throws Exception {

		WTPart childPart = AcademyServiceHelper.service.createWtPart("Child of " + parent.getName(),parent.getContainerReference());

		Workable parentWorkingCopy = WorkableServiceHelper.service.checkOut(parent, "Checking out parent");

		WTPartUsageLink childParentLink = WTPartUsageLink.newWTPartUsageLink((WTPart)parentWorkingCopy, childPart.getMaster());
		
		PersistenceHelper.manager.save(childParentLink);
		
		WorkableServiceHelper.service.checkIn(parentWorkingCopy, "Checking in parent");
		

	}

	@Override
	public WTPartDescribeLink createDescribeLink(WTPart part, WTDocument document) throws Exception {

		
		Workable partWorkingCopy = WorkableServiceHelper.service.checkOut(part, "Checking out part");
		//Przy tworzeniu nalezy dac wychekcoutowany workingcopy bo inaczej bylby bylad, ze nie jest wycheckoutowany glowny plik
		WTPartDescribeLink wtPartDescribeLink = WTPartDescribeLink.newWTPartDescribeLink((WTPart)partWorkingCopy, document);
		PersistenceHelper.manager.save(wtPartDescribeLink);
		WorkableServiceHelper.service.checkIn(part, "Checking in part");


		return wtPartDescribeLink;
	}

}
