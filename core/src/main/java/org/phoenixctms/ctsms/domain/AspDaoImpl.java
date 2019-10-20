// Generated by: hibernate/SpringHibernateDaoImpl.vsl in andromda-spring-cartridge.
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.phoenixctms.ctsms.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.phoenixctms.ctsms.compare.VOIDComparator;
import org.phoenixctms.ctsms.query.CategoryCriterion;
import org.phoenixctms.ctsms.query.CriteriaUtil;
import org.phoenixctms.ctsms.util.DefaultSettings;
import org.phoenixctms.ctsms.util.SettingCodes;
import org.phoenixctms.ctsms.util.Settings;
import org.phoenixctms.ctsms.util.Settings.Bundle;
import org.phoenixctms.ctsms.vo.AspAtcCodeVO;
import org.phoenixctms.ctsms.vo.AspSubstanceVO;
import org.phoenixctms.ctsms.vo.AspVO;

/**
 * @see Asp
 */
public class AspDaoImpl
		extends AspDaoBase {

	private final static String ATC_CODES_SEPARATOR = "; ";
	private final static boolean MATCH_SUBSTANCE_NAME = true;
	private final static boolean MATCH_ATC_CODE_CODE = true;
	private final static boolean MATCH_REGISTRATION_NUMBER = true;
	private final static VOIDComparator SUBSTANCE_ID_COMPARATOR = new VOIDComparator<AspSubstanceVO>(false);
	private final static VOIDComparator ATC_CODE_ID_COMPARATOR = new VOIDComparator<AspAtcCodeVO>(false);

	private static void applyAspNameCriterions(org.hibernate.Criteria aspCriteria, String nameInfix) {
		String revision = Settings.getString(SettingCodes.ASP_REVISION, Bundle.SETTINGS, DefaultSettings.ASP_REVISION);
		ArrayList<CategoryCriterion> criterions = new ArrayList<CategoryCriterion>();
		criterions.add(new CategoryCriterion(nameInfix, "name", MatchMode.ANYWHERE));
		if (MATCH_REGISTRATION_NUMBER) {
			criterions.add(new CategoryCriterion(nameInfix, "registrationNumber", MatchMode.EXACT));
		}
		if (MATCH_SUBSTANCE_NAME) {
			org.hibernate.Criteria substancesCriteria = aspCriteria.createCriteria("substances", "substances0", CriteriaSpecification.LEFT_JOIN);
			substancesCriteria.add(Restrictions.eq("revision", revision));
			criterions.add(new CategoryCriterion(nameInfix, "substances0.name", MatchMode.ANYWHERE));
		}
		if (MATCH_ATC_CODE_CODE) {
			org.hibernate.Criteria atcCodesCriteria = aspCriteria.createCriteria("atcCodes", "atcCodes0", CriteriaSpecification.LEFT_JOIN);
			atcCodesCriteria.add(Restrictions.eq("revision", revision));
			criterions.add(new CategoryCriterion(nameInfix, "atcCodes0.code", MatchMode.EXACT));
		}
		CategoryCriterion.applyOr(aspCriteria, criterions);
		aspCriteria.add(Restrictions.eq("revision", revision));
	}

	private static final String getAtcCodesLabel(Collection<AspAtcCodeVO> aspAtcCodeVOs) {
		StringBuilder sb = new StringBuilder();
		Iterator<AspAtcCodeVO> aspAtcCodeVOsIt = aspAtcCodeVOs.iterator();
		while (aspAtcCodeVOsIt.hasNext()) {
			sb.append(aspAtcCodeVOsIt.next().getCode());
			if (aspAtcCodeVOsIt.hasNext()) {
				sb.append(ATC_CODES_SEPARATOR);
			}
		}
		return sb.toString();
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public Asp aspVOToEntity(AspVO aspVO) {
		Asp entity = this.loadAspFromAspVO(aspVO);
		this.aspVOToEntity(aspVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void aspVOToEntity(
			AspVO source,
			Asp target,
			boolean copyIfNull) {
		super.aspVOToEntity(source, target, copyIfNull);
		Collection substances = source.getSubstances();
		if (substances.size() > 0) {
			substances = new ArrayList(substances); //prevent changing VO
			this.getAspSubstanceDao().aspSubstanceVOToEntityCollection(substances); // copy if null cannot be passed; copy if null is always true
			target.setSubstances(substances);
		} else if (copyIfNull) {
			target.getSubstances().clear();
		}
		Collection atcCodes = source.getAtcCodes();
		if (atcCodes.size() > 0) {
			atcCodes = new ArrayList(atcCodes); //prevent changing VO
			this.getAspAtcCodeDao().aspAtcCodeVOToEntityCollection(atcCodes); // copy if null cannot be passed; copy if null is always true
			target.setAtcCodes(atcCodes);
		} else if (copyIfNull) {
			target.getAtcCodes().clear();
		}
	}

	private org.hibernate.Criteria createAspCriteria(boolean cacheable) {
		org.hibernate.Criteria aspCriteria = this.getSession().createCriteria(Asp.class);
		if (cacheable) {
			aspCriteria.setCacheable(true);
		}
		return aspCriteria;
	}

	@Override
	protected Collection<String> handleFindAspNames(String nameInfix, Integer limit) {
		org.hibernate.Criteria aspCriteria = createAspCriteria(true);
		applyAspNameCriterions(aspCriteria, nameInfix);
		aspCriteria.add(Restrictions.not(Restrictions.or(Restrictions.eq("name", ""), Restrictions.isNull("name"))));
		aspCriteria.addOrder(Order.asc("name"));
		aspCriteria.setProjection(Projections.distinct(Projections.property("name")));
		CriteriaUtil.applyLimit(limit, Settings.getIntNullable(SettingCodes.ASP_NAME_AUTOCOMPLETE_DEFAULT_RESULT_LIMIT, Bundle.SETTINGS,
				DefaultSettings.ASP_NAME_AUTOCOMPLETE_DEFAULT_RESULT_LIMIT), aspCriteria);
		return aspCriteria.list();
	}

	@Override
	protected Collection<Asp> handleFindAsps(String nameInfix, Integer limit) throws Exception {
		org.hibernate.Criteria aspCriteria = createAspCriteria(true);
		applyAspNameCriterions(aspCriteria, nameInfix);
		aspCriteria.addOrder(Order.asc("name"));
		CriteriaUtil.applyLimit(limit,
				Settings.getIntNullable(SettingCodes.ASP_AUTOCOMPLETE_DEFAULT_RESULT_LIMIT, Bundle.SETTINGS, DefaultSettings.ASP_AUTOCOMPLETE_DEFAULT_RESULT_LIMIT),
				aspCriteria);
		if (MATCH_SUBSTANCE_NAME || MATCH_ATC_CODE_CODE) {
			// ProjectionList projectionList = Projections.projectionList().add(Projections.id());
			// projectionList.add(Projections.property("name"));
			// List asps = aspCriteria.setProjection(Projections.distinct(projectionList)).list();
			// Iterator it = asps.iterator();
			// ArrayList result = new ArrayList(asps.size());
			// while (it.hasNext()) {
			// result.add(this.load((Long) ((Object[]) it.next())[0]));
			// }
			// return result;
			return CriteriaUtil.listDistinctRoot(aspCriteria, this, "name");
		} else {
			return aspCriteria.list();
		}
	}

	@Override
	protected long handleGetMedicationCount(String revision) throws Exception {
		org.hibernate.Criteria aspCriteria = createAspCriteria(false);
		aspCriteria.add(Restrictions.eq("revision", revision));
		aspCriteria.createCriteria("medications", CriteriaSpecification.INNER_JOIN);
		return (Long) aspCriteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	public void handleRemoveAllTxn(Set<Asp> asps) throws Exception {
		Transaction transaction = this.getSession(true).beginTransaction();
		try {
			Iterator<Asp> it = asps.iterator();
			while (it.hasNext()) {
				removeAsp(it.next().getId());
			}
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void handleRemoveTxn(Asp asp) throws Exception {
		Transaction transaction = this.getSession(true).beginTransaction();
		try {
			removeAsp(asp.getId());
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void handleRemoveTxn(Long aspId) throws Exception {
		Transaction transaction = this.getSession(true).beginTransaction();
		try {
			removeAsp(aspId);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private Asp loadAspFromAspVO(AspVO aspVO) {
		Asp asp = null;
		Long id = aspVO.getId();
		if (id != null) {
			asp = this.load(id);
		}
		if (asp == null) {
			asp = Asp.Factory.newInstance();
		}
		return asp;
	}

	private void removeAsp(Long aspId) {
		Asp asp = get(aspId);
		this.getHibernateTemplate().deleteAll(asp.getSubstances()); // constraint error if used by diagnosis
		asp.getSubstances().clear();
		this.getHibernateTemplate().deleteAll(asp.getAtcCodes());
		asp.getAtcCodes().clear();
		this.getHibernateTemplate().delete(asp);
	}

	private ArrayList<AspAtcCodeVO> toAspAtcCodeVOCollection(Collection<AspAtcCode> atcCodes) { // lazyload persistentset prevention
		// related to http://forum.andromda.org/viewtopic.php?t=4288
		AspAtcCodeDao aspAtcCodeDao = this.getAspAtcCodeDao();
		ArrayList<AspAtcCodeVO> result = new ArrayList<AspAtcCodeVO>(atcCodes.size());
		Iterator<AspAtcCode> it = atcCodes.iterator();
		while (it.hasNext()) {
			result.add(aspAtcCodeDao.toAspAtcCodeVO(it.next()));
		}
		Collections.sort(result, ATC_CODE_ID_COMPARATOR);
		return result;
	}

	private ArrayList<AspSubstanceVO> toAspSubstanceVOCollection(Collection<AspSubstance> substances) { // lazyload persistentset prevention
		// related to http://forum.andromda.org/viewtopic.php?t=4288
		AspSubstanceDao aspSubstanceDao = this.getAspSubstanceDao();
		ArrayList<AspSubstanceVO> result = new ArrayList<AspSubstanceVO>(substances.size());
		Iterator<AspSubstance> it = substances.iterator();
		while (it.hasNext()) {
			result.add(aspSubstanceDao.toAspSubstanceVO(it.next()));
		}
		Collections.sort(result, SUBSTANCE_ID_COMPARATOR);
		return result;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public AspVO toAspVO(final Asp entity) {
		return super.toAspVO(entity);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void toAspVO(
			Asp source,
			AspVO target) {
		super.toAspVO(source, target);
		target.setSubstances(toAspSubstanceVOCollection(source.getSubstances()));
		target.setAtcCodes(toAspAtcCodeVOCollection(source.getAtcCodes()));
		target.setAtcCodesLabel(getAtcCodesLabel(target.getAtcCodes()));
	}
}