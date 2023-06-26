package com.ttpsc.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ibm.icu.util.BytesTrie.Result;
import com.ptc.core.meta.type.mgmt.server.impl.WTTypeDefinition;
import com.ttpsc.service.StandardAcademyService;

import wt.doc.WTDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.org.WTOrganization;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartUsageLink;
import wt.pdmlink.PDMLinkProduct;
import wt.pds.StatementSpec;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.util.WTException;

public class StandardSQLQueryService extends StandardManager implements SQLQueryService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1404764598383843735L;

	public static StandardSQLQueryService newStandardSQLQueryService() throws WTException {
		StandardSQLQueryService standardSQLQueryService = new StandardSQLQueryService();
		standardSQLQueryService.initialize();
		return standardSQLQueryService;
	}

	@Override
	public QuerySpec getObjectInfromation(Persistable persistable) throws QueryException {

		Class qc = persistable.getClass();
		QuerySpec qs = new QuerySpec(qc);
		int idx = qs.addClassList(qc, true);

		return null;

	}

	@Override
	public void queryWTPart() throws QueryException {

		Class qc = wt.part.WTPart.class;
		QuerySpec qs = new QuerySpec(qc);
		int idx = qs.addClassList(qc, true);
		qs.appendWhere(new SearchCondition(qc, WTPart.NAME, SearchCondition.LIKE, "P%"), new int[] { idx });
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(qc, "iterationInfo.latest", SearchCondition.IS_TRUE), new int[] { idx });
		System.out.println(qs);

		;

	}

	@Override
	public Optional<WTPart> findPartByName(String name) throws WTException {
		Class qc = wt.part.WTPart.class;
		QuerySpec qs = new QuerySpec(qc);
		int idx = qs.addClassList(qc, true);
		qs.appendWhere(new SearchCondition(qc, WTPart.NAME, SearchCondition.EQUAL, name), new int[] { idx });
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(qc, "iterationInfo.latest", SearchCondition.IS_TRUE), new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) qs);
		WTPart object = null;
		if (result.hasMoreElements()) {
			object = (WTPart) result.nextElement();

		}

		Optional<WTPart> part = Optional.ofNullable(object);
		return part;

	}

	@Override
	public void deleteParts(WTContainer container) throws WTException {
		Class qc = WTPart.class;
		QuerySpec qs = null;
		int idx;
		try {
			qs = new QuerySpec(qc);
			idx = qs.addClassList(qc, true);
			qs.appendWhere(new SearchCondition(qc, "containerReference.key.id", SearchCondition.EQUAL,
					container.getPersistInfo().getObjectIdentifier().getId()), new int[] { idx });
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);
		while (qr.hasMoreElements()) {
			Object object = qr.nextElement();
			PersistenceHelper.manager.delete((Persistable) object);
		}

	}

	@Override
	public Optional<WTPart> findPart(String name, WTContainer container, boolean isLatest) throws WTException {

		Class qc = WTPart.class;
		QuerySpec qs = new QuerySpec(qc);
		int idx = qs.addClassList(qc, true);
		qs.appendWhere(new SearchCondition(qc, WTPart.NAME, SearchCondition.EQUAL, name), new int[] { idx });

		if (container != null) {
			SearchCondition containerCondition = new SearchCondition(qc, "containerReference.key.id",
					SearchCondition.EQUAL, container.getPersistInfo().getObjectIdentifier().getId());
			qs.appendAnd();
			qs.appendWhere(containerCondition, new int[] { idx });
		}
		if (isLatest) {

			SearchCondition latestCondition = new SearchCondition(qc, "iterationInfo.latest", SearchCondition.IS_TRUE);
			qs.appendAnd();
			qs.appendWhere(latestCondition, new int[] { idx });
		}

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) qs);
		WTPart object = null;
		if (result.hasMoreElements()) {
			object = (WTPart) result.nextElement();

		}

		Optional<WTPart> part = Optional.ofNullable(object);
		return part;
	}

	@Override
	public Optional<WTPart> findPart(String name) throws WTException {
		return findPart(name, null);
	}

	@Override
	public Optional<WTPart> findPart(String name, WTContainer container) throws WTException {
		return findPart(name, container, true);
	}

	@Override
	public WTContainer findContainerByName(String name) throws WTException {

		Class qc = PDMLinkProduct.class;
		QuerySpec qs = null;
		int idx;

		try {
			qs = new QuerySpec(qc);
			idx = qs.addClassList(qc, true);
			qs.appendWhere(new SearchCondition(qc, PDMLinkProduct.NAME, SearchCondition.EQUAL, name),
					new int[] { idx });
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);
		WTContainer container = null;
		if (qr.hasMoreElements()) {
			container = (WTContainer) qr.nextElement();
		}

		return container;
	}

	@Override
	public List<WTPart> findPartsInContainer(String containerName) throws WTException {

		QuerySpec querySpec = new QuerySpec(WTPart.class);
		int partAlias = querySpec.appendClassList(WTPart.class, true); // tutaj jest na true, jezeli wynik queryspec MA
																		// BYC typu WTPart
		int wtContainerAlias = querySpec.appendClassList(WTContainer.class, false);

		SearchCondition joinTableSearchCondition = new SearchCondition(WTPart.class, WTPart.CONTAINER_ID,
				WTContainer.class, "thePersistInfo.theObjectIdentifier.id");
		SearchCondition findContainerByName = new SearchCondition(WTContainer.class, WTContainer.NAME,
				SearchCondition.EQUAL, containerName);

		querySpec.appendWhere(findContainerByName, new int[] { wtContainerAlias });
		querySpec.appendAnd();
		querySpec.appendWhere(joinTableSearchCondition, new int[] { partAlias, wtContainerAlias });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) querySpec);
		List<WTPart> partsInContainer = new ArrayList<>();
		while (result.hasMoreElements()) {
			Object object = result.nextElement();
			Persistable[] persistableObjectTable = (Persistable[]) object;
			WTPart persistable = (WTPart) persistableObjectTable[0];
			partsInContainer.add(persistable);

		}

		return partsInContainer;
	}

	@Override
	public List<WTPart> findPartsInOrganization(String organizationName) throws WTException {

		QuerySpec querySpec = new QuerySpec(WTPart.class);
		int partAlias = querySpec.appendClassList(WTPart.class, true);
		int partMaseterAlias = querySpec.appendClassList(WTPartMaster.class, false);
		int organizationAlias = querySpec.appendClassList(WTOrganization.class, false);

		SearchCondition partReferenceCondition = new SearchCondition(WTPartMaster.class,
				"thePersistInfo.theObjectIdentifier.id", WTPart.class, "masterReference.key.id");

		SearchCondition partMasterCondition = new SearchCondition(WTPartMaster.class, "organizationReference.key.id",
				WTOrganization.class, "thePersistInfo.theObjectIdentifier.id");

		SearchCondition organizationNameCondition = new SearchCondition(WTOrganization.class, WTOrganization.NAME,
				SearchCondition.EQUAL, organizationName);

		SearchCondition latestPartIterationCondition = new SearchCondition(WTPart.class, WTPart.LATEST_ITERATION,
				SearchCondition.IS_TRUE);

		querySpec.appendWhere(organizationNameCondition, new int[] { organizationAlias });
		querySpec.appendAnd();
		querySpec.appendWhere(partMasterCondition, new int[] { partMaseterAlias, organizationAlias });
		querySpec.appendAnd();
		querySpec.appendWhere(partReferenceCondition, new int[] { partMaseterAlias, partAlias });
		querySpec.appendAnd();
		querySpec.appendWhere(latestPartIterationCondition, new int[] { partAlias });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) querySpec);
		List<WTPart> partsInOrganization = new ArrayList<>();
		while (result.hasMoreElements()) {
			Object object = result.nextElement();
			Persistable[] persistableObjectTable = (Persistable[]) object;
			WTPart persistable = (WTPart) persistableObjectTable[0];
			partsInOrganization.add(persistable);

		}

		return partsInOrganization;

	}

	@Override
	public List<WTPart> findAllPartsBySoftType(String softTypeName) throws WTException {

		QuerySpec querySpec = new QuerySpec();
		int partAlias = querySpec.appendClassList(WTPart.class, true);
		int softTypeAlias = querySpec.appendClassList(WTTypeDefinition.class, false);

		String softTypePartReference = "typeDefinitionReference.key.id";

		SearchCondition typeDefCondition = new SearchCondition(WTPart.class, softTypePartReference,
				WTTypeDefinition.class, "thePersistInfo.theObjectIdentifier.id");

		SearchCondition typeDefByNameCondition = new SearchCondition(WTTypeDefinition.class, WTTypeDefinition.NAME,
				SearchCondition.EQUAL, softTypeName);

		querySpec.appendWhere(typeDefCondition, new int[] { partAlias, softTypeAlias });
		querySpec.appendAnd();
		querySpec.appendWhere(typeDefByNameCondition, new int[] { softTypeAlias });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) querySpec);
		List<WTPart> partsBySoftType = new ArrayList<>();
		while (result.hasMoreElements()) {
			Object object = result.nextElement();
			Persistable[] persistableObjectTable = (Persistable[]) object;
			WTPart persistable = (WTPart) persistableObjectTable[0];
			partsBySoftType.add(persistable);

		}

		return partsBySoftType;
	}

	@Override
	public List<WTDocument> findDocumentsBySoftType(String softTypeName) throws WTException {

		QuerySpec querySpec = new QuerySpec();
		int documentAlias = querySpec.appendClassList(WTDocument.class, true);
		int softTypeAlias = querySpec.appendClassList(WTTypeDefinition.class, false);

		String softTypeDocumentReference = "typeDefinitionReference.key.id";

		SearchCondition typeDefCondition = new SearchCondition(WTDocument.class, softTypeDocumentReference,
				WTTypeDefinition.class, "thePersistInfo.theObjectIdentifier.id");

		SearchCondition typeDefByNameCondition = new SearchCondition(WTTypeDefinition.class, WTTypeDefinition.NAME,
				SearchCondition.EQUAL, softTypeName);

		SearchCondition latestIterationCondition = new SearchCondition(WTDocument.class, "iterationInfo.latest",
				SearchCondition.IS_TRUE);

		querySpec.appendWhere(typeDefCondition, new int[] { documentAlias, softTypeAlias });
		querySpec.appendAnd();
		querySpec.appendWhere(typeDefByNameCondition, new int[] { softTypeAlias });
		querySpec.appendAnd();
		querySpec.appendWhere(latestIterationCondition, new int[] { documentAlias });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) querySpec);
		List<WTDocument> documentsBySoftType = new ArrayList<>();
		while (result.hasMoreElements()) {
			Object object = result.nextElement();
			Persistable[] persistableObjectTable = (Persistable[]) object;
			WTDocument persistable = (WTDocument) persistableObjectTable[0];
			documentsBySoftType.add(persistable);

		}

		return documentsBySoftType;
	}

	@Override
	public List<WTPartUsageLink> getUsageLinksByWTPart(WTPart wtPart) throws WTException {

		QuerySpec querySpec = new QuerySpec();
		int usageLinkAlias = querySpec.appendClassList(WTPartUsageLink.class, true);

		SearchCondition usageLinkPartJoin = new SearchCondition(WTPartUsageLink.class, "roleAObjectRef.key.id",
				SearchCondition.EQUAL, wtPart.getPersistInfo().getObjectIdentifier().getId());

		querySpec.appendWhere(usageLinkPartJoin, new int[] { usageLinkAlias });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) querySpec);
		List<WTPartUsageLink> usageLinks = new ArrayList<>();

		while (result.hasMoreElements()) {

			Persistable[] persistableObjectTable = (Persistable[]) result.nextElement();
			WTPartUsageLink persistable = (WTPartUsageLink) persistableObjectTable[0];
			usageLinks.add(persistable);

		}
		return usageLinks;

	}

	@Override
	public Optional<WTPart> findPartByNumber(String number) throws WTException {

		QuerySpec querySpec = new QuerySpec();
		int partAlias = querySpec.appendClassList(WTPart.class, true);

		SearchCondition partByNumberCondition = new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL,
				number);

		QueryResult result = PersistenceHelper.manager.find(querySpec);
		WTPart object = null;
		if (result.hasMoreElements()) {

			Persistable[] persistableObjectTable = (Persistable[]) result.nextElement();
			WTPart persistable = (WTPart) persistableObjectTable[0];
			object = persistable;

		}

		Optional<WTPart> part = Optional.ofNullable(object);
		return part;
	}

	@Override
	public WTPartUsageLink findUsageLink(WTPart part, WTPartMaster child) throws WTException {
		QuerySpec querySpec = new QuerySpec();

		int wtPartUsageLinkAlias = querySpec.addClassList(WTPartUsageLink.class, true);

		SearchCondition usageLinkPartJoin = new SearchCondition(WTPartUsageLink.class, "roleAObjectRef.key.id",
				SearchCondition.EQUAL, part.getPersistInfo().getObjectIdentifier().getId());

		SearchCondition usageLinkPartMasterJoin = new SearchCondition(WTPartUsageLink.class, "roleBObjectRef.key.id",
				SearchCondition.EQUAL, child.getPersistInfo().getObjectIdentifier().getId());

		querySpec.appendWhere(usageLinkPartJoin, new int[] { wtPartUsageLinkAlias });
		querySpec.appendAnd();
		querySpec.appendWhere(usageLinkPartMasterJoin, new int[] { wtPartUsageLinkAlias });

		QueryResult result = PersistenceHelper.manager.find(querySpec);

		WTPartUsageLink childParentUsageLink = null;
		if (result.hasMoreElements()) {
			Persistable[] persistableObjectTable = (Persistable[]) result.nextElement();
			WTPartUsageLink persistable = (WTPartUsageLink) persistableObjectTable[0];
			childParentUsageLink = (WTPartUsageLink) result.nextElement();

		}

		return childParentUsageLink;

	}

}
