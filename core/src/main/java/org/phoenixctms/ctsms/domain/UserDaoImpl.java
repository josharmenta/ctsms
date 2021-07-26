// Generated by: hibernate/SpringHibernateDaoImpl.vsl in andromda-spring-cartridge.
// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.phoenixctms.ctsms.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.phoenixctms.ctsms.enumeration.DBModule;
import org.phoenixctms.ctsms.query.CriteriaUtil;
import org.phoenixctms.ctsms.query.QueryUtil;
import org.phoenixctms.ctsms.query.SubCriteriaMap;
import org.phoenixctms.ctsms.util.CommonUtil;
import org.phoenixctms.ctsms.util.L10nUtil;
import org.phoenixctms.ctsms.util.L10nUtil.Locales;
import org.phoenixctms.ctsms.vo.AuthenticationTypeVO;
import org.phoenixctms.ctsms.vo.CriteriaInstantVO;
import org.phoenixctms.ctsms.vo.PSFVO;
import org.phoenixctms.ctsms.vo.StaffOutVO;
import org.phoenixctms.ctsms.vo.UserInVO;
import org.phoenixctms.ctsms.vo.UserInheritedVO;
import org.phoenixctms.ctsms.vo.UserOutVO;
import org.phoenixctms.ctsms.vo.UserSettingsInVO;
import org.phoenixctms.ctsms.vocycle.DeferredVO;
import org.phoenixctms.ctsms.vocycle.UserReflexionGraph;
import org.springframework.dao.DataAccessResourceFailureException;

/**
 * @see User
 */
public class UserDaoImpl
		extends UserDaoBase {

	private org.hibernate.Criteria createUserCriteria() {
		org.hibernate.Criteria userCriteria = this.getSession().createCriteria(User.class);
		return userCriteria;
	}

	@Override
	protected long handleGetChildrenCount(Long userId) throws Exception {
		org.hibernate.Criteria userCriteria = createUserCriteria();
		userCriteria.add(Restrictions.eq("parent.id", userId.longValue()));
		return (Long) userCriteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	/**
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws DataAccessResourceFailureException
	 * @inheritDoc
	 */
	@Override
	protected Collection<User> handleFindByCriteria(CriteriaInstantVO criteria, PSFVO psf) throws Exception {
		Query query = QueryUtil.createSearchQuery(
				criteria,
				DBModule.USER_DB,
				psf,
				this.getSessionFactory(),
				this.getCriterionTieDao(),
				this.getCriterionPropertyDao(),
				this.getCriterionRestrictionDao());
		return query.list();
	}

	@Override
	protected Collection<User> handleFindByIdDepartment(Long userId,
			Long departmentId, PSFVO psf) throws Exception {
		org.hibernate.Criteria userCriteria = createUserCriteria();
		SubCriteriaMap criteriaMap = new SubCriteriaMap(User.class, userCriteria);
		CriteriaUtil.applyIdDepartmentCriterion(userCriteria, userId, departmentId);
		CriteriaUtil.applyPSFVO(criteriaMap, psf);
		return userCriteria.list();
	}

	/**
	 * @throws Exception
	 * @inheritDoc
	 */
	@Override
	protected Collection<User> handleFindByIdentity(Long identityId, PSFVO psf) throws Exception {
		org.hibernate.Criteria userCriteria = createUserCriteria();
		SubCriteriaMap criteriaMap = new SubCriteriaMap(User.class, userCriteria);
		if (identityId != null) {
			userCriteria.add(Restrictions.eq("identity.id", identityId.longValue()));
		}
		CriteriaUtil.applyPSFVO(criteriaMap, psf);
		return userCriteria.list();
	}

	@Override
	protected long handleGetCountByCriteria(CriteriaInstantVO criteria, PSFVO psf) throws Exception {
		return QueryUtil.getSearchQueryResultCount(
				criteria,
				DBModule.USER_DB,
				psf,
				this.getSessionFactory(),
				this.getCriterionTieDao(),
				this.getCriterionPropertyDao(),
				this.getCriterionRestrictionDao());
	}

	private void loadDeferredStaffOutVOs(HashMap<Class, HashMap<Long, Object>> voMap) {
		HashMap<Long, Object> staffVOMap = voMap.get(StaffOutVO.class);
		StaffDao staffDao = this.getStaffDao();
		if (staffVOMap != null) {
			Iterator<Entry<Long, Object>> staffVOMapIt = (new HashSet<Entry<Long, Object>>(staffVOMap.entrySet())).iterator();
			while (staffVOMapIt.hasNext()) {
				Entry<Long, Object> staffVO = staffVOMapIt.next();
				DeferredVO deferredVO = (DeferredVO) staffVO.getValue();
				if (deferredVO.isDeferred()) {
					deferredVO.setDeferred(false);
					staffDao.toStaffOutVO(staffDao.load(staffVO.getKey()), (StaffOutVO) deferredVO.getVo(), voMap, staffVOMap.size(), 0, 0);
				}
			}
		}
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private User loadUserFromUserInVO(UserInVO userInVO) {
		User user = null;
		Long id = userInVO.getId();
		if (id != null) {
			user = this.load(id);
		}
		if (user == null) {
			user = User.Factory.newInstance();
		}
		return user;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private User loadUserFromUserOutVO(UserOutVO userOutVO) {
		throw new UnsupportedOperationException("out value object to recursive entity not supported");
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public UserInVO toUserInVO(final User entity) {
		return super.toUserInVO(entity);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void toUserInVO(
			User source,
			UserInVO target) {
		super.toUserInVO(source, target);
		Department department = source.getDepartment();
		Staff identity = source.getIdentity();
		User parent = source.getParent();
		if (department != null) {
			target.setDepartmentId(department.getId());
		}
		if (identity != null) {
			target.setIdentityId(identity.getId());
		}
		if (parent != null) {
			target.setParentId(parent.getId());
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public UserOutVO toUserOutVO(final User entity) {
		return super.toUserOutVO(entity);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void toUserOutVO(
			User source,
			UserOutVO target) {
		HashMap<Class, HashMap<Long, Object>> voMap = new HashMap<Class, HashMap<Long, Object>>();
		(new UserReflexionGraph(this, this.getDepartmentDao())).toVOHelper(source, target, voMap);
		loadDeferredStaffOutVOs(voMap);
	}

	@Override
	public void toUserOutVO(
			User source,
			UserOutVO target, HashMap<Class, HashMap<Long, Object>> voMap) {
		(new UserReflexionGraph(this, this.getDepartmentDao())).toVOHelper(source, target, voMap);
		loadDeferredStaffOutVOs(voMap);
	}

	@Override
	public void toUserOutVO(
			User source,
			UserOutVO target, HashMap<Class, HashMap<Long, Object>> voMap, Integer... maxInstances) {
		(new UserReflexionGraph(this, this.getDepartmentDao(), maxInstances)).toVOHelper(source, target, voMap);
		loadDeferredStaffOutVOs(voMap);
	}

	@Override
	public void toUserOutVO(
			User source,
			UserOutVO target, Integer... maxInstances) {
		HashMap<Class, HashMap<Long, Object>> voMap = new HashMap<Class, HashMap<Long, Object>>();
		(new UserReflexionGraph(this, this.getDepartmentDao(), maxInstances)).toVOHelper(source, target, voMap);
		loadDeferredStaffOutVOs(voMap);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public User userInVOToEntity(UserInVO userInVO) {
		User entity = this.loadUserFromUserInVO(userInVO);
		this.userInVOToEntity(userInVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void userInVOToEntity(
			UserInVO source,
			User target,
			boolean copyIfNull) {
		super.userInVOToEntity(source, target, copyIfNull);
		Long departmentId = source.getDepartmentId();
		Long identityId = source.getIdentityId();
		Long parentId = source.getParentId();
		if (departmentId != null) {
			Department department = this.getDepartmentDao().load(departmentId);
			target.setDepartment(department);
			department.addUsers(target);
		} else if (copyIfNull) {
			Department department = target.getDepartment();
			target.setDepartment(null);
			if (department != null) {
				department.removeUsers(target);
			}
		}
		if (identityId != null) {
			Staff identity = this.getStaffDao().load(identityId);
			target.setIdentity(identity);
			identity.addAccounts(target);
		} else if (copyIfNull) {
			Staff identity = target.getIdentity();
			target.setIdentity(null);
			if (identity != null) {
				identity.removeAccounts(target);
			}
		}
		if (parentId != null) {
			if (target.getParent() != null) {
				target.getParent().removeChildren(target);
			}
			User parent = this.load(parentId);
			target.setParent(parent);
			parent.addChildren(target);
		} else if (copyIfNull) {
			User parent = target.getParent();
			target.setParent(null);
			if (parent != null) {
				parent.removeChildren(target);
			}
		}
		Collection inheritedProperties;
		if ((inheritedProperties = source.getInheritedProperties()).size() > 0 || copyIfNull) {
			target.setInheritedPropertyList(UserReflexionGraph.toInheritedPropertyList(inheritedProperties));
		}
		Collection inheritedPermissionProfileGroups;
		if ((inheritedPermissionProfileGroups = source.getInheritedPermissionProfileGroups()).size() > 0 || copyIfNull) {
			target.setInheritedPermissionProfileGroupList(UserReflexionGraph.toInheritedPermissionProfileGroupList(inheritedPermissionProfileGroups));
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public User userOutVOToEntity(UserOutVO userOutVO) {
		User entity = this.loadUserFromUserOutVO(userOutVO);
		this.userOutVOToEntity(userOutVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void userOutVOToEntity(
			UserOutVO source,
			User target,
			boolean copyIfNull) {
		super.userOutVOToEntity(source, target, copyIfNull);
		StaffOutVO identityVO = source.getIdentity();
		UserOutVO parentVO = source.getParent();
		AuthenticationTypeVO authMethodVO = source.getAuthMethod();
		if (identityVO != null) {
			Staff identity = this.getStaffDao().staffOutVOToEntity(identityVO);
			target.setIdentity(identity);
			identity.addAccounts(target);
		} else if (copyIfNull) {
			Staff identity = target.getIdentity();
			target.setIdentity(null);
			if (identity != null) {
				identity.removeAccounts(target);
			}
		}
		if (authMethodVO != null) {
			target.setAuthMethod(authMethodVO.getMethod());
		} else if (copyIfNull) {
			target.setAuthMethod(null);
		}
		if (parentVO != null) {
			if (target.getParent() != null) {
				target.getParent().removeChildren(target);
			}
			User parent = this.userOutVOToEntity(parentVO);
			target.setParent(parent);
			parent.addChildren(target);
		} else if (copyIfNull) {
			User parent = target.getParent();
			target.setParent(null);
			if (parent != null) {
				parent.removeChildren(target);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void toUserSettingsInVO(
			User source,
			UserSettingsInVO target) {
		super.toUserSettingsInVO(source, target);
	}

	/**
	 * {@inheritDoc}
	 */
	public UserSettingsInVO toUserSettingsInVO(final User entity) {
		return super.toUserSettingsInVO(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	public User userSettingsInVOToEntity(UserSettingsInVO userSettingsInVO) {
		User entity = this.loadUserFromUserSettingsInVO(userSettingsInVO);
		this.userSettingsInVOToEntity(userSettingsInVO, entity, true);
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void userSettingsInVOToEntity(
			UserSettingsInVO source,
			User target,
			boolean copyIfNull) {
		super.userSettingsInVOToEntity(source, target, copyIfNull);
		if (source.getInheritedProperties().size() > 0 || copyIfNull) {
			Collection inheritedProperties = UserReflexionGraph.getInheritedProperties(target.getInheritedPropertyList());
			inheritedProperties.removeAll(CommonUtil.USER_SETTINGS_INHERITABLE_PROPERTIES);
			inheritedProperties.addAll(source.getInheritedProperties());
			target.setInheritedPropertyList(UserReflexionGraph.toInheritedPropertyList(inheritedProperties));
		}
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private User loadUserFromUserSettingsInVO(UserSettingsInVO userSettingsInVO) {
		User user = null;
		Long id = userSettingsInVO.getId();
		if (id != null) {
			user = this.load(id);
		}
		if (user == null) {
			user = User.Factory.newInstance();
		}
		return user;
	}

	private User loadUserFromUserInheritedVO(UserInheritedVO userInheritedVO) {
		throw new UnsupportedOperationException("org.phoenixctms.ctsms.domain.loadUserFromUserInheritedVO(UserInheritedVO) not yet implemented.");
	}

	@Override
	public User userInheritedVOToEntity(UserInheritedVO userInheritedVO) {
		User entity = this.loadUserFromUserInheritedVO(userInheritedVO);
		this.userInheritedVOToEntity(userInheritedVO, entity, true);
		return entity;
	}

	@Override
	public void userInheritedVOToEntity(
			UserInheritedVO source,
			User target,
			boolean copyIfNull) {
		super.userInheritedVOToEntity(source, target, copyIfNull);
	}

	@Override
	public UserInheritedVO toUserInheritedVO(final User entity) {
		return super.toUserInheritedVO(entity);
	}

	@Override
	public void toUserInheritedVO(
			User source,
			UserInheritedVO target) {
		super.toUserInheritedVO(source, target);
		Staff identity = source.getIdentity();
		if (identity != null) {
			target.setIdentity(this.getStaffDao().toStaffOutVO(identity));
		}
		Department department = source.getDepartment();
		if (department != null) {
			target.setDepartment(this.getDepartmentDao().toDepartmentVO(department));
		}
		target.setAuthMethod(L10nUtil.createAuthenticationTypeVO(Locales.USER, source.getAuthMethod()));
		HashMap<Long, HashSet<String>> inheritPropertyMap = new HashMap<Long, HashSet<String>>();
		setInheritedProperty(source, target, "enableInventoryModule", Boolean.TYPE, inheritPropertyMap);
		setInheritedProperty(source, target, "enableStaffModule", Boolean.TYPE, inheritPropertyMap);
		setInheritedProperty(source, target, "enableCourseModule", Boolean.TYPE, inheritPropertyMap);
		setInheritedProperty(source, target, "enableTrialModule", Boolean.TYPE, inheritPropertyMap);
		setInheritedProperty(source, target, "enableInputFieldModule", Boolean.TYPE, inheritPropertyMap);
		setInheritedProperty(source, target, "enableProbandModule", Boolean.TYPE, inheritPropertyMap);
		setInheritedProperty(source, target, "enableMassMailModule", Boolean.TYPE, inheritPropertyMap);
		setInheritedProperty(source, target, "enableUserModule", Boolean.TYPE, inheritPropertyMap);
		setInheritedProperty(source, target, "visibleInventoryTabList", String.class, inheritPropertyMap);
		setInheritedProperty(source, target, "visibleStaffTabList", String.class, inheritPropertyMap);
		setInheritedProperty(source, target, "visibleCourseTabList", String.class, inheritPropertyMap);
		setInheritedProperty(source, target, "visibleTrialTabList", String.class, inheritPropertyMap);
		setInheritedProperty(source, target, "visibleProbandTabList", String.class, inheritPropertyMap);
		setInheritedProperty(source, target, "visibleInputFieldTabList", String.class, inheritPropertyMap);
		setInheritedProperty(source, target, "visibleUserTabList", String.class, inheritPropertyMap);
		setInheritedProperty(source, target, "visibleMassMailTabList", String.class, inheritPropertyMap);
		setInheritedProperty(source, target, "decrypt", Boolean.TYPE, inheritPropertyMap);
		setInheritedProperty(source, target, "decryptUntrusted", Boolean.TYPE, inheritPropertyMap);
		setInheritedProperty(source, target, "locked", Boolean.TYPE, inheritPropertyMap);
		setInheritedProperty(source, target, "timeZone", String.class, inheritPropertyMap);
		setInheritedProperty(source, target, "locale", String.class, inheritPropertyMap);
		setInheritedProperty(source, target, "showTooltips", Boolean.TYPE, inheritPropertyMap);
		setInheritedProperty(source, target, "theme", String.class, inheritPropertyMap);
		setInheritedProperty(source, target, "decimalSeparator", String.class, inheritPropertyMap);
		setInheritedProperty(source, target, "dateFormat", String.class, inheritPropertyMap);
	}

	private static boolean isInherited(User user, String propertyName, HashMap<Long, HashSet<String>> inheritPropertyMap) {
		HashSet<String> inheritedPropertyList;
		if (inheritPropertyMap.containsKey(user.getId())) {
			inheritedPropertyList = inheritPropertyMap.get(user.getId());
		} else {
			inheritedPropertyList = UserReflexionGraph.getInheritedProperties(user.getInheritedPropertyList());
			inheritPropertyMap.put(user.getId(), inheritedPropertyList);
		}
		return inheritedPropertyList.contains(propertyName);
	}

	private static void setInheritedProperty(User source, UserInheritedVO target, String propertyName, Class type, HashMap<Long, HashSet<String>> inheritPropertyMap) {
		try {
			if (isInherited(source, propertyName, inheritPropertyMap)) {
				User parent = source.getParent();
				if (parent != null) {
					CommonUtil.getPropertySetter(UserInheritedVO.class, propertyName, type).invoke(target,
							getInheritedProperty(parent, propertyName, inheritPropertyMap));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Object getInheritedProperty(User user, String propertyName, HashMap<Long, HashSet<String>> inheritPropertyMap) {
		try {
			if (isInherited(user, propertyName, inheritPropertyMap)) {
				User parent = user.getParent();
				if (parent != null) {
					return getInheritedProperty(parent, propertyName, inheritPropertyMap);
				}
			}
			return CommonUtil.getPropertyGetter(User.class, propertyName).invoke(user);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}