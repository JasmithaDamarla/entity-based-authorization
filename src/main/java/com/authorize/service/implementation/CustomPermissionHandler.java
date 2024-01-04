package com.authorize.service.implementation;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomPermissionHandler implements PermissionEvaluator {

	@Override
	  public boolean hasPermission(
	      Authentication authentication, Object targetDomainObject, Object permission) {
	    if ((authentication == null)
	        || (targetDomainObject == null)
	        || !(permission instanceof String)) {
	      return false;
	    }
	    
	    String targetDomain=null;
	    if (targetDomainObject instanceof String) {
	      targetDomain = (String) targetDomainObject;
	    }

	    return hasPrivilege(authentication, targetDomain, permission.toString().toUpperCase());
	  }

	  @Override
	  public boolean hasPermission(
	      Authentication authentication, Serializable targetId, String targetType, Object permission) {
	    if ((authentication == null) || (targetType == null) || !(permission instanceof String)) {
	      return false;
	    }
	    return hasPrivilege(
	        authentication, targetType.toUpperCase(), permission.toString().toUpperCase());
	  }

	  private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
	    for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
	      if (grantedAuth.getAuthority().startsWith(targetType)
	          && grantedAuth.getAuthority().contains(permission)) {
	        return true;
	      }
	    }
	    return false;
	  }

}
