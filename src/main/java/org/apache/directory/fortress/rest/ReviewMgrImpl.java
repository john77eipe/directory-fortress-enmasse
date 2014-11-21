/*
 *   Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */
package org.apache.directory.fortress.rest;

import org.apache.directory.fortress.core.ReviewMgr;
import org.apache.directory.fortress.core.ReviewMgrFactory;
import org.apache.directory.fortress.core.SecurityException;
import org.apache.directory.fortress.core.rbac.OrgUnit;
import org.apache.directory.fortress.core.rbac.PermObj;
import org.apache.directory.fortress.core.rbac.Permission;
import org.apache.directory.fortress.core.rbac.Role;
import org.apache.directory.fortress.core.rbac.SDSet;
import org.apache.directory.fortress.core.rbac.User;
import org.apache.directory.fortress.core.rbac.UserRole;
import org.apache.directory.fortress.core.rest.FortRequest;
import org.apache.directory.fortress.core.rest.FortResponse;
import org.apache.directory.fortress.core.util.attr.VUtil;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;

/**
 * Utility for EnMasse Server.  This class is thread safe.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
class ReviewMgrImpl
{
    private static final String CLS_NM = ReviewMgrImpl.class.getName();
    private static final Logger log = Logger.getLogger(CLS_NM);

    FortResponse readPermission(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            Permission inPerm = (Permission) request.getEntity();
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            Permission retPerm = reviewMgr.readPermission(inPerm);
            response.setEntity(retPerm);
            response.setErrorCode(0);
        }
        catch (org.apache.directory.fortress.core.SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse readPermObj(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            PermObj inObj = (PermObj) request.getEntity();
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            PermObj retObj = reviewMgr.readPermObj(inObj);
            response.setEntity(retObj);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse findPermissions(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            Permission inPerm = (Permission) request.getEntity();
            List<Permission> perms = reviewMgr.findPermissions(inPerm);
            response.setEntities(perms);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse findPermObjs(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            PermObj inObj = (PermObj) request.getEntity();
            List<PermObj> objs = null;
            if (VUtil.isNotNullOrEmpty(inObj.getOu()))
            {
                objs = reviewMgr.findPermObjs(new OrgUnit(inObj.getOu(), OrgUnit.Type.PERM));
            }
            else
            {
                objs = reviewMgr.findPermObjs(inObj);
            }
            response.setEntities(objs);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse readRole(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            Role inRole = (Role) request.getEntity();
            Role outRole = reviewMgr.readRole(inRole);
            response.setEntity(outRole);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse findRoles(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            String searchValue = request.getValue();
            if (request.getLimit() != null)
            {
                List<String> retRoles = reviewMgr.findRoles(searchValue, request.getLimit());
                response.setValues(retRoles);
            }
            else
            {
                List<Role> roles = reviewMgr.findRoles(searchValue);
                response.setEntities(roles);
            }
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse readUserM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            User inUser = (User) request.getEntity();
            User outUser = reviewMgr.readUser(inUser);
            response.setEntity(outUser);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse findUsersM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            User inUser = (User) request.getEntity();
            if (request.getLimit() != null)
            {
                List<String> retUsers = reviewMgr.findUsers(inUser, request.getLimit());
                response.setValues(retUsers);
            }
            else
            {
                List<User> retUsers;
                if (VUtil.isNotNullOrEmpty(inUser.getOu()))
                {
                    retUsers = reviewMgr.findUsers(new OrgUnit(inUser.getOu(), OrgUnit.Type.USER));
                }
                else
                {
                    retUsers = reviewMgr.findUsers(inUser);
                }
                response.setEntities(retUsers);
            }
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse assignedUsersM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            Role inRole = (Role) request.getEntity();
            if (request.getLimit() != null)
            {
                List<String> retUsers = reviewMgr.assignedUsers(inRole, request.getLimit());
                response.setValues(retUsers);
            }
            else
            {
                List<User> users = reviewMgr.assignedUsers(inRole);
                response.setEntities(users);
                response.setEntities(users);
            }
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse assignedRolesM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            if (VUtil.isNotNullOrEmpty(request.getValue()))
            {
                String userId = request.getValue();
                List<String> retRoles = reviewMgr.assignedRoles(userId);
                response.setValues(retRoles);
            }
            else
            {
                User inUser = (User) request.getEntity();
                List<UserRole> uRoles = reviewMgr.assignedRoles(inUser);
                response.setEntities(uRoles);
            }
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse authorizedUsersM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            Role inRole = (Role) request.getEntity();
            List<User> users = reviewMgr.authorizedUsers(inRole);
            response.setEntities(users);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse authorizedRoleM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            User inUser = (User) request.getEntity();
            Set<String> outSet = reviewMgr.authorizedRoles(inUser);
            response.setValueSet(outSet);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse permissionRolesM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            Permission inPerm = (Permission) request.getEntity();
            List<String> outList = reviewMgr.permissionRoles(inPerm);
            response.setValues(outList);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse authorizedPermissionRolesM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            Permission inPerm = (Permission) request.getEntity();
            Set<String> outSet = reviewMgr.authorizedPermissionRoles(inPerm);
            response.setValueSet(outSet);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse permissionUsersM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            Permission inPerm = (Permission) request.getEntity();
            List<String> outList = reviewMgr.permissionUsers(inPerm);
            response.setValues(outList);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse authorizedPermissionUsersM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            Permission inPerm = (Permission) request.getEntity();
            Set<String> outSet = reviewMgr.authorizedPermissionUsers(inPerm);
            response.setValueSet(outSet);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse userPermissionsM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            User inUser = (User) request.getEntity();
            List<Permission> perms = reviewMgr.userPermissions(inUser);
            response.setEntities(perms);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse rolePermissionsM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            Role inRole = (Role) request.getEntity();
            List<Permission> perms = reviewMgr.rolePermissions(inRole);
            response.setEntities(perms);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse ssdRoleSetsM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            Role inRole = (Role) request.getEntity();
            List<SDSet> outSets = reviewMgr.ssdRoleSets(inRole);
            response.setEntities(outSets);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse ssdRoleSetM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            SDSet inSet = (SDSet) request.getEntity();
            SDSet outSet = reviewMgr.ssdRoleSet(inSet);
            response.setEntity(outSet);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse ssdRoleSetRolesM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            SDSet inSet = (SDSet) request.getEntity();
            Set<String> outSet = reviewMgr.ssdRoleSetRoles(inSet);
            response.setValueSet(outSet);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse ssdRoleSetCardinalityM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            SDSet inSet = (SDSet) request.getEntity();
            int cardinality = reviewMgr.ssdRoleSetCardinality(inSet);
            inSet.setCardinality(cardinality);
            response.setEntity(inSet);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
        }
        return response;
    }

    FortResponse ssdSetsM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            SDSet inSdSet = (SDSet) request.getEntity();
            List<SDSet> outSets = reviewMgr.ssdSets(inSdSet);
            response.setEntities(outSets);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse dsdRoleSetsM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            Role inRole = (Role) request.getEntity();
            List<SDSet> outSets = reviewMgr.dsdRoleSets(inRole);
            response.setEntities(outSets);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse dsdRoleSetM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            SDSet inSet = (SDSet) request.getEntity();
            SDSet outSet = reviewMgr.dsdRoleSet(inSet);
            response.setEntity(outSet);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse dsdRoleSetRolesM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            SDSet inSet = (SDSet) request.getEntity();
            Set<String> outSet = reviewMgr.dsdRoleSetRoles(inSet);
            response.setValueSet(outSet);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse dsdRoleSetCardinalityM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            SDSet inSet = (SDSet) request.getEntity();
            int cardinality = reviewMgr.dsdRoleSetCardinality(inSet);
            inSet.setCardinality(cardinality);
            response.setEntity(inSet);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
        }
        return response;
    }

    FortResponse dsdSetsM(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
            reviewMgr.setAdmin(request.getSession());
            SDSet inSdSet = (SDSet) request.getEntity();
            List<SDSet> outSets = reviewMgr.dsdSets(inSdSet);
            response.setEntities(outSets);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }
}