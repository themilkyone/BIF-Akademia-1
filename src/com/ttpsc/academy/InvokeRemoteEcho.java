package com.ttpsc.academy;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.ttpsc.change.ChangeServiceHelper;
import com.ttpsc.link.LinkServiceHelper;
import com.ttpsc.link.StandardLinkService;
import com.ttpsc.listener.cnStateChange.ChangeStateListenerAdapter;
import com.ttpsc.service.AcademyServiceHelper;
import com.ttpsc.service.StandardAcademyService;
import com.ttpsc.sql.SQLQueryServiceHelper;
import com.ttpsc.workable.WorkableServiceHelper;

import wt.change2.WTChangeActivity2;
import wt.change2.WTChangeOrder2;
import wt.doc.WTDocument;
import wt.fc.ObjectIdentifier;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.WTReference;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTList;
import wt.inf.container.WTContainerRef;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.part.WTPartUsageLink;
import wt.pdmlink.PDMLinkProduct;
import wt.team.Team;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtilityServiceHelper;
import wt.util.WTException;

public class InvokeRemoteEcho implements RemoteAccess {

	private static final Logger LOGGER = Logger.getLogger(InvokeRemoteEcho.class);

	static {

		LOGGER.setLevel(Level.DEBUG);
	}
	public static void main(String[] args) throws Exception {
		RemoteMethodServer rms = RemoteMethodServer.getDefault();
		rms.setUserName("wcadmin");
		rms.setPassword("wcadmin");

//		rms.invoke("setTestDocumentAttribute", "com.ttpsc.academy.RemoteEcho", null, new Class[] {}, new Object[] {});

//		rms.invoke("test", "com.ttpsc.academy.InvokeRemoteEcho", null,  new Class[] {}, new Object[] {});
		
		rms.invoke("test3", "com.ttpsc.academy.InvokeRemoteEcho", null,  new Class[] {}, new Object[] {});

	}

	public static void test() throws WTException {

		List<WTPart> allPartsBySoftType = SQLQueryServiceHelper.service.findAllPartsBySoftType("com.ptc.ElectricalPart");
		
		for(WTPart part:allPartsBySoftType) {
			LOGGER.debug(part.getName()+" : "+part.getNumber()); 
		}
	}
	
	public static void test2() throws WTException {

		List<WTDocument> allDocumentsBySoftType = SQLQueryServiceHelper.service.findDocumentsBySoftType("com.ptc.InterCommData");
		
		for(WTDocument doc:allDocumentsBySoftType) {
			LOGGER.debug(doc.getName()+" : "+doc.getNumber()); 
		}
	}
	
	public static void test3() throws Exception {
		
		Optional<WTPart> partByNumber = SQLQueryServiceHelper.service.findPartByNumber("0000000123");
		List<WTPartUsageLink> usageLinksByWTPart = SQLQueryServiceHelper.service
				.getUsageLinksByWTPart(partByNumber.get());
		WTPart checkedOutPart = WorkableServiceHelper.service.checkOut(partByNumber.get(), "Checking out partByNumer");
		PersistenceHelper.manager.delete((Persistable) usageLinksByWTPart);
		WorkableServiceHelper.service.checkIn(checkedOutPart, "Checking in partByNumber");
		
	
	}

}
