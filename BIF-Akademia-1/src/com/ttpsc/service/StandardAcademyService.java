package com.ttpsc.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.meta.common.DisplayOperationIdentifier;
import com.ptc.core.meta.common.OperationIdentifier;
import com.ptc.core.meta.common.UpdateOperationIdentifier;
import com.ttpsc.workable.WorkableServiceHelper;

import wt.doc.WTDocument;
import wt.fc.ObjectIdentifier;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.type.TypeDefinitionReference;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.wip.Workable;

public class StandardAcademyService extends StandardManager implements AcademyService {

	private static final String SEPARATOR = ":";
	private static final String ORGANIZATION = "Demo Organization";
	private static final String PRODUCT = "GOLF_CART";

	@Override
	public void helloFromService(String message) {
		System.out.println(message);

	}

	public static StandardAcademyService newStandardAcademyService() throws WTException {
		StandardAcademyService standardAcademyService = new StandardAcademyService();
		standardAcademyService.initialize();

		return standardAcademyService;
	}

	@Override
	public WTPart createWtPart(String name) throws WTException {

		return createWtPart(name, getContainer(ORGANIZATION, PRODUCT));

	}

	@Override
	public WTPart createWtPart(String name, WTContainerRef containerReference) throws WTException {

		return createWtPart(name, containerReference, null);
	}

	@Override
	public WTPart createWtPart(String name, WTContainerRef containerReference, TypeDefinitionReference typeDefinition)
			throws WTException {

		try {
			WTPart newWTPart = WTPart.newWTPart();
			newWTPart.setName(name);
			newWTPart.setContainerReference(containerReference);

			if (typeDefinition != null) {
				newWTPart.setTypeDefinitionReference(typeDefinition);
			}
			Persistable savePart = PersistenceHelper.manager.save(newWTPart);
			return (WTPart) savePart;
		} catch (WTPropertyVetoException e) {
			throw new WTException(e);
		}

	}

	@Override
	public WTDocument createWtDocument(String name) throws WTException {

		return createWtDocument(name, getContainer(ORGANIZATION, PRODUCT));
	}

	@Override
	public WTDocument createWtDocument(String name, WTContainerRef containerReference) throws WTException {
		try {
			WTDocument newWTDocument = WTDocument.newWTDocument();
			newWTDocument.setName(name);
			newWTDocument.setContainerReference(containerReference);
			Persistable saveDocument = PersistenceHelper.manager.save(newWTDocument);
			return (WTDocument) saveDocument;
		} catch (WTPropertyVetoException e) {

			throw new WTException(e);
		}
	}

	@Override
	public WTDocument createWtDocument(String name, WTContainerRef containerReference, Map<String, Object> attributes)
			throws Exception {

		WTDocument document = createWtDocument(name, containerReference);
		Workable checkOut = WorkableServiceHelper.service.checkOut(document, "Checking out");

		return (WTDocument) setAttributes(checkOut, attributes);

	}

	@Override
	public Persistable getObjectByOID(String oid) throws WTException {
		ObjectIdentifier objectID = ObjectIdentifier.newObjectIdentifier(oid);
		Persistable result = PersistenceHelper.manager.refresh(objectID);

		return result;
	}

	@Override
	public Persistable getObjectByReference(String reference) throws WTException {

		ReferenceFactory rf = new ReferenceFactory();
		WTReference wtRef = rf.getReference(reference);
		Persistable object = wtRef.getObject();

		return object;
	}

	@Override
	public ObjectIdentifier getObjectIdentifier(Class<?> clazz, String OID) throws WTException {

		ObjectIdentifier objectID = ObjectIdentifier.newObjectIdentifier(clazz.getName() + SEPARATOR + OID);
		return objectID;
	}

	@Override
	public WTReference getReference(Class<?> clazz, String OID, boolean isVersionReference) throws WTException {

		ReferenceFactory rf = new ReferenceFactory();
		String referenceType = isVersionReference ? "VR" : "OR";
		WTReference wtRef = rf.getReference(referenceType + SEPARATOR + clazz.getName() + SEPARATOR + OID);

		return wtRef;
	}

	@Override
	public WTContainerRef getContainer(String organization, String containerName) throws WTException {

		String containerPath = "/wt.inf.container.OrgContainer=" + organization + "/wt.pdmlink.PDMLinkProduct="
				+ containerName;

		WTContainerRef container = WTContainerHelper.service.getByPath(containerPath);

		return container;
	}

	@Override
	public Map<String, Object> getAttributes(Persistable persistable, Set<String> attributes) throws WTException {

		PersistableAdapter persistableAdapter = new PersistableAdapter(persistable, null, SessionHelper.getLocale(),
				new DisplayOperationIdentifier());
		persistableAdapter.load(attributes);

		Map<String, Object> map = new HashMap<>();
		for (String attribute : attributes) {
			Object o = persistableAdapter.get(attribute);
			map.put(attribute, o);
		}
		return map;
	}

	@Override
	public Persistable setAttributes(Persistable persistable, Map<String, Object> attr) throws WTException {
		PersistableAdapter persistableAdapter = new PersistableAdapter(persistable, null, SessionHelper.getLocale(),
				new UpdateOperationIdentifier());
		persistableAdapter.load(attr.keySet());

		for (Map.Entry<String, Object> index : attr.entrySet()) {
			persistableAdapter.set(index.getKey(), index.getValue());
		}

		Persistable apply = persistableAdapter.apply();

		return PersistenceHelper.manager.modify(apply);
	}

}
