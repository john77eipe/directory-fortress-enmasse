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

import org.apache.directory.fortress.core.AdminMgr;
import org.apache.directory.fortress.core.AdminMgrFactory;
import org.apache.directory.fortress.core.DelAdminMgr;
import org.apache.directory.fortress.core.DelAdminMgrFactory;
import org.apache.directory.fortress.core.ReviewMgr;
import org.apache.directory.fortress.core.ReviewMgrFactory;
import org.apache.directory.fortress.core.SecurityException;
import org.apache.directory.fortress.core.rbac.AdminRole;
import org.apache.directory.fortress.core.rbac.PermGrant;
import org.apache.directory.fortress.core.rbac.PermObj;
import org.apache.directory.fortress.core.rbac.Permission;
import org.apache.directory.fortress.core.rbac.Role;
import org.apache.directory.fortress.core.rbac.RoleRelationship;
import org.apache.directory.fortress.core.rbac.SDSet;
import org.apache.directory.fortress.core.rbac.User;
import org.apache.directory.fortress.core.rbac.UserRole;
import org.apache.directory.fortress.core.rest.FortRequest;
import org.apache.directory.fortress.core.rest.FortResponse;
import org.apache.log4j.Logger;

/**
 * Utility for EnMasse Server.  This class is thread safe.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
class AdminMgrImpl
{
    private static final String CLS_NM = AdminMgrImpl.class.getName();
    private static final Logger log = Logger.getLogger(CLS_NM);

    FortResponse addUser(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            User inUser = (User) request.getEntity();
            User outUser = adminMgr.addUser(inUser);
            response.setEntity(outUser);
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

    FortResponse deleteUser(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            User inUser = (User) request.getEntity();
            adminMgr.deleteUser(inUser);
            response.setErrorCode(0);
            response.setEntity(inUser);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse disableUser(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            User inUser = (User) request.getEntity();
            adminMgr.disableUser(inUser);
            response.setErrorCode(0);
            response.setEntity(inUser);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse updateUser(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            User inUser = (User) request.getEntity();
            User outUser = adminMgr.updateUser(inUser);
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

    FortResponse changePassword(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            User inUser = (User) request.getEntity();
            adminMgr.changePassword(inUser, inUser.getNewPassword());
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
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

    FortResponse lockUserAccount(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            User inUser = (User) request.getEntity();
            adminMgr.lockUserAccount(inUser);
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
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

    FortResponse unlockUserAccount(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            User inUser = (User) request.getEntity();
            adminMgr.unlockUserAccount(inUser);
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
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

    FortResponse resetPassword(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            User inUser = (User) request.getEntity();
            adminMgr.resetPassword(inUser, inUser.getNewPassword());
            ReviewMgr reviewMgr = ReviewMgrFactory.createInstance(request.getContextId());
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

    FortResponse addRole(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            Role inRole = (Role) request.getEntity();
            Role outRole = adminMgr.addRole(inRole);
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

    FortResponse deleteRole(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            Role inRole = (Role) request.getEntity();
            adminMgr.deleteRole(inRole);
            response.setEntity(inRole);
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

    FortResponse updateRole(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            Role inRole = (Role) request.getEntity();
            Role outRole = adminMgr.updateRole(inRole);
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

    FortResponse assignUser(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            UserRole inRole = (UserRole) request.getEntity();
            adminMgr.assignUser(inRole);
            response.setEntity(inRole);
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

    FortResponse deassignUser(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            UserRole inRole = (UserRole) request.getEntity();
            adminMgr.deassignUser(inRole);
            response.setEntity(inRole);
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

    FortResponse addPermission(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            Permission inPerm = (Permission) request.getEntity();
            Permission outPerm = adminMgr.addPermission(inPerm);
            response.setEntity(outPerm);
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

    FortResponse updatePermission(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            Permission inPerm = (Permission) request.getEntity();
            Permission outPerm = adminMgr.updatePermission(inPerm);
            response.setEntity(outPerm);
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

    FortResponse deletePermission(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            Permission inPerm = (Permission) request.getEntity();
            adminMgr.deletePermission(inPerm);
            response.setEntity(inPerm);
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

    FortResponse addPermObj(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            PermObj inObj = (PermObj) request.getEntity();
            PermObj outObj = adminMgr.addPermObj(inObj);
            response.setEntity(outObj);
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

    FortResponse updatePermObj(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            PermObj inObj = (PermObj) request.getEntity();
            PermObj outObj = adminMgr.updatePermObj(inObj);
            response.setEntity(outObj);
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

    FortResponse deletePermObj(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            PermObj inObj = (PermObj) request.getEntity();
            adminMgr.deletePermObj(inObj);
            response.setEntity(inObj);
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

    private void grantPerm(FortRequest request) throws SecurityException
    {
        PermGrant permGrant = (PermGrant) request.getEntity();
        AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
        adminMgr.setAdmin(request.getSession());
        Role role = new Role(permGrant.getRoleNm());
        Permission perm = new Permission(permGrant.getObjName(), permGrant.getOpName(), permGrant.getObjId());
        perm.setAdmin(false);
        adminMgr.grantPermission(perm, role);
    }

    private void grantAdminPerm(FortRequest request) throws SecurityException
    {
        PermGrant permGrant = (PermGrant) request.getEntity();
        DelAdminMgr delegatedAdminMgr = DelAdminMgrFactory.createInstance(request.getContextId());
        delegatedAdminMgr.setAdmin(request.getSession());
        AdminRole role = new AdminRole(permGrant.getRoleNm());
        Permission perm = new Permission(permGrant.getObjName(), permGrant.getOpName(), permGrant.getObjId());
        perm.setAdmin(true);
        delegatedAdminMgr.grantPermission(perm, role);
    }

    private void revokePerm(FortRequest request) throws SecurityException
    {
        PermGrant permGrant = (PermGrant) request.getEntity();
        AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
        adminMgr.setAdmin(request.getSession());
        Role role = new Role(permGrant.getRoleNm());
        Permission perm = new Permission(permGrant.getObjName(), permGrant.getOpName(), permGrant.getObjId());
        perm.setAdmin(false);
        adminMgr.revokePermission(perm, role);
    }

    private void revokeAdminPerm(FortRequest request) throws SecurityException
    {
        PermGrant permGrant = (PermGrant) request.getEntity();
        DelAdminMgr delegatedAdminMgr = DelAdminMgrFactory.createInstance(request.getContextId());
        delegatedAdminMgr.setAdmin(request.getSession());
        AdminRole role = new AdminRole(permGrant.getRoleNm());
        Permission perm = new Permission(permGrant.getObjName(), permGrant.getOpName(), permGrant.getObjId());
        perm.setAdmin(true);
        delegatedAdminMgr.revokePermission(perm, role);
    }

    private void grantUserPerm(FortRequest request) throws SecurityException
    {
        PermGrant permGrant = (PermGrant) request.getEntity();
        AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
        adminMgr.setAdmin(request.getSession());
        User user = new User(permGrant.getUserId());
        Permission perm = new Permission(permGrant.getObjName(), permGrant.getOpName(), permGrant.getObjId());
        perm.setAdmin(false);
        adminMgr.grantPermission(perm, user);
    }

    private void grantAdminUserPerm(FortRequest request) throws SecurityException
    {
        PermGrant permGrant = (PermGrant) request.getEntity();
        DelAdminMgr delegatedAdminMgr = DelAdminMgrFactory.createInstance(request.getContextId());
        delegatedAdminMgr.setAdmin(request.getSession());
        User user = new User(permGrant.getUserId());
        Permission perm = new Permission(permGrant.getObjName(), permGrant.getOpName(), permGrant.getObjId());
        perm.setAdmin(true);
        delegatedAdminMgr.grantPermission(perm, user);
    }

    private void revokeUserPerm(FortRequest request) throws SecurityException
    {
        PermGrant permGrant = (PermGrant) request.getEntity();
        AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
        adminMgr.setAdmin(request.getSession());
        User user = new User(permGrant.getUserId());
        Permission perm = new Permission(permGrant.getObjName(), permGrant.getOpName(), permGrant.getObjId());
        perm.setAdmin(false);
        adminMgr.revokePermission(perm, user);
    }

    private void revokeAdminUserPerm(FortRequest request) throws SecurityException
    {
        PermGrant permGrant = (PermGrant) request.getEntity();
        DelAdminMgr delegatedAdminMgr = DelAdminMgrFactory.createInstance(request.getContextId());
        delegatedAdminMgr.setAdmin(request.getSession());
        User user = new User(permGrant.getUserId());
        Permission perm = new Permission(permGrant.getObjName(), permGrant.getOpName(), permGrant.getObjId());
        perm.setAdmin(true);
        delegatedAdminMgr.revokePermission(perm, user);
    }

    FortResponse grant(FortRequest request, FortressServiceImpl fortressService)
    {
        FortResponse response = new FortResponse();
        try
        {
            PermGrant permGrant = (PermGrant) request.getEntity();
            if (permGrant.isAdmin())
            {
                grantAdminPerm(request);
            }
            else
            {
                grantPerm(request);
            }
            response.setEntity(permGrant);
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

    FortResponse revoke(FortRequest request, FortressServiceImpl fortressService)
    {
        FortResponse response = new FortResponse();
        try
        {
            PermGrant permGrant = (PermGrant) request.getEntity();
            if (permGrant.isAdmin())
            {
                revokeAdminPerm(request);
            }
            else
            {
                revokePerm(request);
            }
            response.setEntity(permGrant);
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

    FortResponse grantUser(FortRequest request, FortressServiceImpl fortressService)
    {
        FortResponse response = new FortResponse();
        try
        {
            PermGrant permGrant = (PermGrant) request.getEntity();
            if (permGrant.isAdmin())
            {
                grantAdminUserPerm(request);
            }
            else
            {
                grantUserPerm(request);
            }
            response.setEntity(permGrant);
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

    FortResponse revokeUser(FortRequest request, FortressServiceImpl fortressService)
    {
        FortResponse response = new FortResponse();
        try
        {
            PermGrant permGrant = (PermGrant) request.getEntity();
            if (permGrant.isAdmin())
            {
                revokeAdminUserPerm(request);
            }
            else
            {
                revokeUserPerm(request);
            }
            response.setEntity(permGrant);
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

    FortResponse addDescendant(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            RoleRelationship relationship = (RoleRelationship) request.getEntity();
            adminMgr.addDescendant(relationship.getParent(), relationship.getChild());
            response.setEntity(relationship);
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

    FortResponse addAscendant(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            RoleRelationship relationship = (RoleRelationship) request.getEntity();
            adminMgr.addAscendant(relationship.getChild(), relationship.getParent());
            response.setEntity(relationship);
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

    FortResponse addInheritance(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            RoleRelationship relationship = (RoleRelationship) request.getEntity();
            adminMgr.addInheritance(relationship.getParent(), relationship.getChild());
            response.setEntity(relationship);
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

    FortResponse deleteInheritance(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            RoleRelationship relationship = (RoleRelationship) request.getEntity();
            adminMgr.deleteInheritance(relationship.getParent(), relationship.getChild());
            response.setEntity(relationship);
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

    FortResponse createSsdSet(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            SDSet inSet = (SDSet) request.getEntity();
            SDSet outSet = adminMgr.createSsdSet(inSet);
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

    FortResponse updateSsdSet(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            SDSet inSet = (SDSet) request.getEntity();
            SDSet outSet = adminMgr.updateSsdSet(inSet);
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

    FortResponse addSsdRoleMember(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            SDSet inSet = (SDSet) request.getEntity();
            Role role = new Role(request.getValue());
            SDSet outSet = adminMgr.addSsdRoleMember(inSet, role);
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

    FortResponse deleteSsdRoleMember(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            SDSet inSet = (SDSet) request.getEntity();
            Role role = new Role(request.getValue());
            SDSet outSet = adminMgr.deleteSsdRoleMember(inSet, role);
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

    FortResponse deleteSsdSet(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            SDSet inSet = (SDSet) request.getEntity();
            SDSet outSet = adminMgr.deleteSsdSet(inSet);
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

    FortResponse setSsdSetCardinality(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            SDSet inSet = (SDSet) request.getEntity();
            SDSet outSet = adminMgr.setSsdSetCardinality(inSet, inSet.getCardinality());
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

    FortResponse createDsdSet(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            SDSet inSet = (SDSet) request.getEntity();
            SDSet outSet = adminMgr.createDsdSet(inSet);
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

    FortResponse updateDsdSet(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            SDSet inSet = (SDSet) request.getEntity();
            SDSet outSet = adminMgr.updateDsdSet(inSet);
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

    FortResponse addDsdRoleMember(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            SDSet inSet = (SDSet) request.getEntity();
            Role role = new Role(request.getValue());
            SDSet outSet = adminMgr.addDsdRoleMember(inSet, role);
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

    FortResponse deleteDsdRoleMember(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            SDSet inSet = (SDSet) request.getEntity();
            Role role = new Role(request.getValue());
            SDSet outSet = adminMgr.deleteDsdRoleMember(inSet, role);
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

    FortResponse deleteDsdSet(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            SDSet inSet = (SDSet) request.getEntity();
            SDSet outSet = adminMgr.deleteDsdSet(inSet);
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

    FortResponse setDsdSetCardinality(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            AdminMgr adminMgr = AdminMgrFactory.createInstance(request.getContextId());
            adminMgr.setAdmin(request.getSession());
            SDSet inSet = (SDSet) request.getEntity();
            SDSet outSet = adminMgr.setDsdSetCardinality(inSet, inSet.getCardinality());
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
}