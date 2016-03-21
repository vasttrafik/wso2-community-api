package org.vasttrafik.wso2.carbon.community.api.beans.converters;

import java.util.ArrayList;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.beans.MemberRanking;
import org.vasttrafik.wso2.carbon.community.api.model.MemberRankingDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class MemberRankingConverter {
	
	public List<MemberRanking> convert(List<MemberRankingDTO> rankingDTOs) {
    	List<MemberRanking> rankings = new ArrayList<MemberRanking>(rankingDTOs.size());
    	for (MemberRankingDTO rankingDTO : rankingDTOs) {
    		MemberRanking ranking = convert(rankingDTO);
    		rankings.add(ranking);
    	}
    	return rankings;
    }
    
    public MemberRanking convert(MemberRankingDTO rankingDTO) {
    	MemberRanking ranking = new MemberRanking();
    	ranking.setCurrentPoints(rankingDTO.getCurrentScore());
    	ranking.setId(rankingDTO.getId());
    	ranking.setImageURL(rankingDTO.getImageUrl());
    	ranking.setMemberId(rankingDTO.getMemberId());
    	ranking.setMinPoints(rankingDTO.getMinPoints());
    	ranking.setTitle(rankingDTO.getTitle());
    	MemberRanking.TypeEnum rankingType = MemberRanking.TypeEnum.valueOf(rankingDTO.getType());
    	ranking.setType(rankingType);
    	return ranking;
    }

}
