package org.vasttrafik.wso2.carbon.community.api.beans.converters;

import java.util.ArrayList;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.beans.Member.StatusEnum;
import org.vasttrafik.wso2.carbon.community.api.model.MemberDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class MemberConverter {
	
	public List<Member> convert(List<MemberDTO> memberDTOs) {
    	List<Member> members = new ArrayList<Member>(memberDTOs.size());
    	for (MemberDTO memberDTO : memberDTOs) {
    		Member member = convert(memberDTO);
    		members.add(member);
    	}
    	return members;
    }
    
	public Member convert(MemberDTO memberDTO) {
    	Member member = new Member();
    	member.setAcceptAllMessages(memberDTO.getAcceptAllMsg());
    	member.setEmail(memberDTO.getEmail());
    	member.setGravatarEmail(memberDTO.getGravatarEmail());
    	member.setId(memberDTO.getId());
    	member.setNotifyEmail(memberDTO.getNotifyEmail());
    	member.setNotifyMessage(memberDTO.getNotifyMessage());
    	member.setNotifyText(memberDTO.getNotifyText());
    	member.setShowEmail(memberDTO.getShowEmail());
    	member.setShowRankings(memberDTO.getShowRankings()); 
    	member.setSignature(memberDTO.getSignature());
    	member.setUseGravatar(memberDTO.getUseGravatar());
    	member.setUserName(memberDTO.getUserName());
    	Member.StatusEnum status = StatusEnum.valueOf(memberDTO.getStatus());
    	member.setStatus(status);
    	return member;
    }
    
	public MemberDTO convert(Member member) {
    	MemberDTO memberDTO = new MemberDTO();
    	memberDTO.setAcceptAllMsg(member.getAcceptAllMessages());
    	memberDTO.setEmail(member.getEmail());
    	memberDTO.setGravatarEmail(member.getGravatarEmail());
    	memberDTO.setId(member.getId());
    	memberDTO.setNotifyEmail(member.getNotifyEmail());
    	memberDTO.setNotifyMessage(member.getNotifyMessage());
    	memberDTO.setNotifyText(member.getNotifyText());
    	memberDTO.setShowEmail(member.getShowEmail());
    	memberDTO.setShowRankings(member.getShowRankings()); 
    	memberDTO.setSignature(member.getSignature());
    	memberDTO.setUseGravatar(member.getUseGravatar());
    	memberDTO.setUserName(member.getUserName());
    	memberDTO.setStatus(member.getStatus().toString());
    	return memberDTO;
    }
}
