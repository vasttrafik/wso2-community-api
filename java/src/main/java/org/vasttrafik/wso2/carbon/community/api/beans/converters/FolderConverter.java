package org.vasttrafik.wso2.carbon.community.api.beans.converters;

import org.vasttrafik.wso2.carbon.community.api.beans.Folder;
import org.vasttrafik.wso2.carbon.community.api.model.FolderDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class FolderConverter {
	
	public Folder convert(FolderDTO folderDTO) {
    	Folder folder = new Folder();
    	folder.setId(folderDTO.getId());
    	folder.setImageURL(folderDTO.getImageUrl());
    	folder.setMemberId(folderDTO.getMemberId());
    	folder.setName(folderDTO.getName());
    	folder.setNumberOfMessages(folderDTO.getNumMsgs());
    	folder.setNumberOfUnreadMessages(folderDTO.getNumUnreadMsgs());
    	folder.setParentId(folderDTO.getParentId());
    	folder.setSize(folderDTO.getSize());
    	Folder.TypeEnum type = Folder.TypeEnum.valueOf(folderDTO.getType());
    	folder.setType(type);
    	return folder;
    }
}
