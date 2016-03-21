package org.vasttrafik.wso2.carbon.community.api.beans.converters;

import java.util.ArrayList;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.beans.Vote;
import org.vasttrafik.wso2.carbon.community.api.model.VoteDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class VoteConverter {

	public List<Vote> convert(List<VoteDTO> voteDTOs) {
		List<Vote> votes = new ArrayList<Vote>(voteDTOs.size());
		for (VoteDTO voteDTO : voteDTOs) {
			Vote vote = convert(voteDTO);
			votes.add(vote);
		}
		return votes;
	}
	
	public Vote convert(VoteDTO voteDTO) {
		Vote vote = new Vote();
		vote.setId(voteDTO.getId());
		vote.setMemberId(voteDTO.getMemberId());
		vote.setPoints(voteDTO.getPoints());
		vote.setPostId(voteDTO.getPostId());
		String type = voteDTO.getType();
		Vote.TypeEnum voteType = Vote.TypeEnum.valueOf(type);
		vote.setType(voteType);
		return vote;
	}
}
