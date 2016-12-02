package org.vasttrafik.wso2.carbon.community.api.beans.converters;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.beans.Member.StatusEnum;
import org.vasttrafik.wso2.carbon.community.api.model.MemberDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class MemberConverter {
	
	private static final Log log = LogFactory.getLog(MemberConverter.class);
	
	public List<Member> convert(List<MemberDTO> memberDTOs) {
    	List<Member> members = new ArrayList<Member>(memberDTOs.size());
    	for (MemberDTO memberDTO : memberDTOs) {
    		Member member = convert(memberDTO);
    		members.add(member);
    	}
    	return members;
    }
	
	public List<Member> convertPublic(List<MemberDTO> memberDTOs) {
    	List<Member> members = new ArrayList<Member>(memberDTOs.size());
    	for (MemberDTO memberDTO : memberDTOs) {
    		Member member = convertPublic(memberDTO);
    		members.add(member);
    	}
    	return members;
    }
    
	public Member convert(MemberDTO memberDTO) {
    	Member member = new Member();
    	member.setAcceptAllMessages(memberDTO.getAcceptAllMsg());
    	member.setEmail(memberDTO.getEmail());
    	member.setGravatarEmail(memberDTO.getGravatarEmail());
    	
		// Hash gravatar email to use for looking up gravatar info
		if (memberDTO.getUseGravatar() != null && memberDTO.getUseGravatar() && memberDTO.getGravatarEmail() != null
				&& memberDTO.getGravatarEmail().length() > 0) {

			member.setGravatarEmailHash(getMD5Hash(memberDTO.getGravatarEmail()));
		}
    	
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
	
	public Member convertPublic(MemberDTO memberDTO) {
    	Member member = new Member();
    	member.setAcceptAllMessages(memberDTO.getAcceptAllMsg());
    	
    	if(memberDTO.getShowEmail() != null && memberDTO.getShowEmail())
    		member.setEmail(memberDTO.getEmail());

		// Hash gravatar email to use for looking up gravatar info
		if (memberDTO.getUseGravatar() != null && memberDTO.getUseGravatar() && memberDTO.getGravatarEmail() != null
				&& memberDTO.getGravatarEmail().length() > 0) {

			member.setGravatarEmailHash(getMD5Hash(memberDTO.getGravatarEmail()));
		}

    	member.setId(memberDTO.getId());
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
    	memberDTO.setStatus(member.getStatus() != null ? member.getStatus().toString() : null);
    	return memberDTO;
    }
	
	public String getMD5Hash(String input) {

		try {
			byte[] bytesOfMessage = input.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(bytesOfMessage);

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < digest.length; i++) {
				sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();

		} catch (Exception e) {
			log.error("Problem hashing user gravatar email", e);
		}

		return null;

	}
}
