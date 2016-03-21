package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;


/**
 * Java bean for 'MemberRanking' entity. 
 * 
 * @author Lars Andersson
 *
 */
public final class MemberRankingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Integer id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    // DB : com_user_id int 
    private Integer memberId;
    // DB : com_ranking_id int 
    private Integer rankingId;
    // DB : com_title varchar 
    private String title;
    // DB : com_type varchar 
    private String type;
    // DB : com_min_points int 
    private Integer minPoints;
    // DB : com_image_url varchar 
    private String imageUrl;
    // DB : com_current_score int 
    private Integer currentScore;

    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId( Integer id ) {
        this.id = id ;
    }

    public Integer getId() {
        return this.id;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    /**
     * Sets the memberId value
     * @param memberId The value to set.
     */
    public void setMemberId( Integer memberId ) {
        this.memberId = memberId;
    }

    /**
     * Retrieves the memberId value
     * @return The memberId value.
     */
    public Integer getMemberId() {
        return this.memberId;
    }
    /**
     * Sets the rankingId value
     * @param rankingId The value to set.
     */
    public void setRankingId( Integer rankingId ) {
        this.rankingId = rankingId;
    }

    /**
     * Retrieves the rankingId value
     * @return The rankingId value.
     */
    public Integer getRankingId() {
        return this.rankingId;
    }
    
    /**
     * Retrieves the title value
     * @return The title value.
     */
    public String getTitle() {
		return title;
	}
    /**
     * Sets the title value
     * @param title The value to set.
     */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getMinPoints() {
		return minPoints;
	}

	public void setMinPoints(Integer minPoints) {
		this.minPoints = minPoints;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
     * Sets the currentScore value
     * @param currentScore The value to set.
     */
    public void setCurrentScore( Integer currentScore ) {
        this.currentScore = currentScore;
    }

    /**
     * Retrieves the currentScore value
     * @return The currentScore value.
     */
    public Integer getCurrentScore() {
        return this.currentScore;
    }

    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(memberId);
        sb.append("|");
        sb.append(rankingId);
        sb.append("|");
        sb.append(title);
        sb.append("|");
        sb.append(type);
        sb.append("|");
        sb.append(minPoints);
        sb.append("|");
        sb.append(imageUrl);
        sb.append("|");
        sb.append(currentScore);
        return sb.toString(); 
    } 

}
