package com.ttpsc.service;

import java.util.Map;
import java.util.Set;

import wt.doc.WTDocument;
import wt.fc.ObjectIdentifier;
import wt.fc.Persistable;
import wt.fc.WTReference;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.method.RemoteInterface;
import wt.part.WTPart;
import wt.type.TypeDefinitionReference;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

@RemoteInterface
public interface AcademyService {

	public void helloFromService(String message);

	public WTPart createWtPart(String name) throws WTException;

	public WTPart createWtPart(String name, WTContainerRef containerReference) throws WTException;

	public WTPart createWtPart(String name, WTContainerRef containerReference, TypeDefinitionReference typeDefinition)
			throws WTException;

	public WTDocument createWtDocument(String name) throws WTException;

	public WTDocument createWtDocument(String name, WTContainerRef containerReference) throws WTException;

	public WTDocument createWtDocument(String name, WTContainerRef containerReference,Map<String,Object>attributes) throws WTException, Exception;

	public Persistable getObjectByOID(String oid) throws WTException;

	public Persistable getObjectByReference(String reference) throws WTException;

	public ObjectIdentifier getObjectIdentifier(Class<?> clazz, String OID) throws WTException;

	public WTReference getReference(Class<?> clazz, String OID, boolean isVersionReference) throws WTException;

	public WTContainerRef getContainer(String organization, String containerName) throws WTException;

	Map<String, Object> getAttributes(Persistable persistable, Set<String> attributes) throws WTException;

	Persistable setAttributes(Persistable persistable, Map<String, Object> attr) throws WTException;

}
