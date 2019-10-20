// Generated by: hibernate/SpringHibernateDaoImpl.vsl in andromda-spring-cartridge.
// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.phoenixctms.ctsms.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.phoenixctms.ctsms.compare.EntityIDComparator;
import org.phoenixctms.ctsms.compare.VOIDComparator;
import org.phoenixctms.ctsms.util.L10nUtil;
import org.phoenixctms.ctsms.util.L10nUtil.Locales;
import org.phoenixctms.ctsms.vo.ProbandListStatusLogLevelVO;
import org.phoenixctms.ctsms.vo.ProbandListStatusTypeVO;

/**
 * @see ProbandListStatusType
 */
public class ProbandListStatusTypeDaoImpl
		extends ProbandListStatusTypeDaoBase {

	private final static EntityIDComparator ID_COMPARATOR = new EntityIDComparator<ProbandListStatusType>(false);
	private final static VOIDComparator LOG_LEVEL_ID_COMPARATOR = new VOIDComparator<ProbandListStatusLogLevelVO>(false);

	private org.hibernate.Criteria createProbandListStatusTypeCriteria() {
		org.hibernate.Criteria probandListStatusTypeCriteria = this.getSession().createCriteria(ProbandListStatusType.class);
		probandListStatusTypeCriteria.setCacheable(true);
		return probandListStatusTypeCriteria;
	}

	@Override
	protected Collection<ProbandListStatusType> handleFindByPerson(Boolean person) throws Exception {
		org.hibernate.Criteria probandListStatusTypeCriteria = createProbandListStatusTypeCriteria();
		if (person != null) {
			probandListStatusTypeCriteria.add(Restrictions.eq("person", person.booleanValue()));
		}
		probandListStatusTypeCriteria.addOrder(Order.asc("id"));
		return probandListStatusTypeCriteria.list();
	}

	/**
	 * @inheritDoc
	 */
	@Override
	protected Collection<ProbandListStatusType> handleFindInitialStates(Boolean signup, Boolean person) {
		org.hibernate.Criteria probandListStatusTypeCriteria = createProbandListStatusTypeCriteria();
		probandListStatusTypeCriteria.add(Restrictions.eq("initial", true));
		if (signup != null) {
			probandListStatusTypeCriteria.add(Restrictions.eq("signup", signup.booleanValue()));
		}
		if (person != null) {
			probandListStatusTypeCriteria.add(Restrictions.eq("person", person.booleanValue()));
		}
		probandListStatusTypeCriteria.addOrder(Order.asc("id"));
		return probandListStatusTypeCriteria.list();
	}

	/**
	 * @inheritDoc
	 */
	@Override
	protected Collection<ProbandListStatusType> handleFindTransitions(Long statusTypeId) {
		ProbandListStatusType statusType = this.load(statusTypeId);
		Iterator<ProbandListStatusType> it = null;
		if (statusType != null) {
			it = statusType.getTransitions().iterator();
		}
		ArrayList<ProbandListStatusType> result = new ArrayList<ProbandListStatusType>();
		if (it != null) { // force load:
			while (it.hasNext()) {
				result.add(this.load(it.next().getId()));
			}
		}
		Collections.sort(result, ID_COMPARATOR);
		return result;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private ProbandListStatusType loadProbandListStatusTypeFromProbandListStatusTypeVO(ProbandListStatusTypeVO probandListStatusTypeVO) {
		// TODO implement loadProbandListStatusTypeFromProbandListStatusTypeVO
		// throw new UnsupportedOperationException("org.phoenixctms.ctsms.domain.loadProbandListStatusTypeFromProbandListStatusTypeVO(ProbandListStatusTypeVO) not yet implemented.");
		ProbandListStatusType probandListStatusType = null;
		Long id = probandListStatusTypeVO.getId();
		if (id != null) {
			probandListStatusType = this.load(id);
		}
		if (probandListStatusType == null) {
			probandListStatusType = ProbandListStatusType.Factory.newInstance();
		}
		return probandListStatusType;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public ProbandListStatusType probandListStatusTypeVOToEntity(ProbandListStatusTypeVO probandListStatusTypeVO) {
		ProbandListStatusType entity = this.loadProbandListStatusTypeFromProbandListStatusTypeVO(probandListStatusTypeVO);
		this.probandListStatusTypeVOToEntity(probandListStatusTypeVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void probandListStatusTypeVOToEntity(
			ProbandListStatusTypeVO source,
			ProbandListStatusType target,
			boolean copyIfNull) {
		super.probandListStatusTypeVOToEntity(source, target, copyIfNull);
		Collection logLevels = source.getLogLevels();
		if (logLevels.size() > 0) {
			logLevels = new ArrayList(logLevels); //prevent changing VO
			this.getProbandListStatusLogLevelDao().probandListStatusLogLevelVOToEntityCollection(logLevels);
			target.setLogLevels(logLevels);
		} else if (copyIfNull) {
			target.getLogLevels().clear();
		}
	}

	private ArrayList<ProbandListStatusLogLevelVO> toProbandListStatusLogLevelVOCollection(Collection<ProbandListStatusLogLevel> loglevels) {
		// related to http://forum.andromda.org/viewtopic.php?t=4288
		ProbandListStatusLogLevelDao probandListStatusLogLevelDao = this.getProbandListStatusLogLevelDao();
		ArrayList<ProbandListStatusLogLevelVO> result = new ArrayList<ProbandListStatusLogLevelVO>(loglevels.size());
		Iterator<ProbandListStatusLogLevel> it = loglevels.iterator();
		while (it.hasNext()) {
			result.add(probandListStatusLogLevelDao.toProbandListStatusLogLevelVO(it.next()));
		}
		Collections.sort(result, LOG_LEVEL_ID_COMPARATOR);
		return result;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public ProbandListStatusTypeVO toProbandListStatusTypeVO(final ProbandListStatusType entity) {
		return super.toProbandListStatusTypeVO(entity);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void toProbandListStatusTypeVO(
			ProbandListStatusType source,
			ProbandListStatusTypeVO target) {
		super.toProbandListStatusTypeVO(source, target);
		// WARNING! No conversion for target.logLevels (can't convert source.getLogLevels():org.phoenixctms.ctsms.domain.ProbandListStatusLogLevel to
		// org.phoenixctms.ctsms.vo.ProbandListStatusLogLevelVO
		target.setName(L10nUtil.getProbandListStatusTypeName(Locales.USER, source.getNameL10nKey()));
		target.setLogLevels(toProbandListStatusLogLevelVOCollection(source.getLogLevels()));
	}
}