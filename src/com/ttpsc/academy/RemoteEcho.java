package com.ttpsc.academy;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.ttpsc.remote.bundle.TestBundleResource;
import com.ttpsc.service.AcademyServiceHelper;
import com.ttpsc.service.StandardAcademyService;
import com.ttpsc.workable.WorkableServiceHelper;

import wt.doc.WTDocument;
import wt.fc.ObjectIdentifier;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.inf.container.WTContainerRef;
import wt.method.RemoteAccess;
import wt.part.WTPart;
import wt.preference.PreferenceHelper;
import wt.util.WTException;
import wt.util.WTMessage;
import wt.util.WTProperties;
import wt.vc.wip.Workable;

public class RemoteEcho implements RemoteAccess {

	public static final String parameter = "academy";

	public static void echo() {
		System.out.println("Hello world");
		System.out.println("Hello world");

	}

	public static void echo(String message) {
		System.out.println(message);
	}

	public static void readProperty(String param) throws IOException {

		WTProperties wt = WTProperties.getLocalProperties();
		System.out.println(wt.getProperty(param));
	}

	public static void instructionBundle() {

		String localizedMessage = WTMessage.getLocalizedMessage(TestBundleResource.class.getName(),
				TestBundleResource.FIRST_INSTRUCTION);
		String localizedMessageDE = WTMessage.getLocalizedMessage(TestBundleResource.class.getName(),
				TestBundleResource.FIRST_INSTRUCTION, null, Locale.GERMANY);

		System.out.println(localizedMessage);
		System.out.println("DE Message:" + localizedMessageDE);

		String localizedMessage2 = WTMessage.getLocalizedMessage(TestBundleResource.class.getName(),
				TestBundleResource.SECOND_INSTRUCTION, new Object[] { "Mateusz" });
		String localizedMessageDE2 = WTMessage.getLocalizedMessage(TestBundleResource.class.getName(),
				TestBundleResource.SECOND_INSTRUCTION, new Object[] { "Potempa" }, Locale.GERMANY);

		System.out.println(localizedMessage2);
		System.out.println("DE Message:" + localizedMessageDE2);
	}

	public static void readPreference() throws WTException {
		Object value = PreferenceHelper.service.getValue("/customization/enable", null);
		System.out.println(value);
	}

	public static Persistable getObjectByOID(String oid) throws WTException {

		ObjectIdentifier objectID = ObjectIdentifier.newObjectIdentifier(oid);
		Persistable result = PersistenceHelper.manager.refresh(objectID);

		return result;

	}

	public static void readAttributes() throws WTException {
		WTPart newPart = AcademyServiceHelper.service.createWtPart("Part attributes");
		Map<String, Object> attributes = AcademyServiceHelper.service.getAttributes(newPart,
				Collections.singleton("name"));
		Object result = attributes.get("name");

	}

	public static void setAttribute() throws Exception {
		WTPart newPart = AcademyServiceHelper.service.createWtPart("BuyPartAttribute");
		Workable checkOut = WorkableServiceHelper.service.checkOut(newPart, "Checking out");
		Map<String, Object> attr = new HashMap<>();
		attr.put("source", "buy");
		Persistable persistable = AcademyServiceHelper.service.setAttributes(checkOut, attr);

		WorkableServiceHelper.service.checkIn((WTPart) persistable, "Checking in");

	}

	public static void setTestDocumentAttribute() throws Exception {

		WTContainerRef container = AcademyServiceHelper.service.getContainer("Demo Organization", "GOLF_CART");
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("description", "My Desc");
		map.put("test", "My test value");

		WTDocument wtDocument = AcademyServiceHelper.service.createWtDocument("DocumentSet", container, map);
		WorkableServiceHelper.service.checkIn(wtDocument, "Checking in");

		
		

	}

}
