package org.vasttrafik.wso2.carbon.community.api.exceptions;

import javax.ws.rs.ext.Provider;

import org.vasttrafik.wso2.carbon.common.api.exceptions.GenericExceptionMapper;

/**
 * 
 * @author Lars Andersson
 *
 */
@Provider
public final class CommunityExceptionMapper extends GenericExceptionMapper {
	
	private static final String bundleName = "org.vasttrafik.wso2.carbon.community.api.impl.utils.CommunityErrorListResourceBundle";
	
	public CommunityExceptionMapper() {
		super(bundleName);
	}
}
