package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;


/**
 * Java bean for 'User' entity
 * 
 * @author Lars Andersson
 *
 */
public final class MemberDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Integer id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //---------------------------------------------------------------------- 
 // DB : com_user_name varchar 
    private String userName;
    // DB : com_email varchar 
    private String email;
    // DB : com_status varchar 
    private String status;
    // DB : com_show_email bit 
    private Boolean showEmail;
    // DB : com_show_rankings bit 
    private Boolean showRankings;
    // DB : com_signature varchar 
    private String signature;
    // DB : com_use_gravatar bit 
    private Boolean useGravatar;
    // DB : com_gravatar_email varchar 
    private String gravatarEmail;
    // DB : com_accept_all_msg bit 
    private Boolean acceptAllMsg;
    // DB : com_notify_email bit 
    private Boolean notifyEmail;
    // DB : com_notify_message bit 
    private Boolean notifyMessage;
    // DB : com_notify_text bit 
    private Boolean notifyText;

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
     * Sets the userName value
     * @param userName The value to set.
     */
    public void setUserName( String userName ) {
        this.userName = userName;
    }

    /**
     * Retrieves the userName value
     * @return The userName value.
     */
    public String getUserName() {
        return this.userName;
    }
    
    /**
     * Sets the email value
     * @param email The value to set.
     */
    public void setEmail( String email ) {
        this.email = email;
    }

    /**
     * Retrieves the email value
     * @return The email value.
     */
    public String getEmail() {
        return this.email;
    }

	/**
     * Sets the status value
     * @param status The value to set.
     */
    public void setStatus( String status ) {
        this.status = status;
    }

    /**
     * Retrieves the status value
     * @return The status value.
     */
    public String getStatus() {
        return this.status;
    }
 
    /**
     * Sets the showEmail value
     * @param showEmail The value to set.
     */
    public void setShowEmail(Boolean showEmail) {
        this.showEmail = showEmail;
    }

    /**
     * Retrieves the showEmail value
     * @return The showEmail value.
     */
    public Boolean getShowEmail() {
        return this.showEmail;
    }
    
    public Boolean getShowRankings() {
		return showRankings;
	}

	public void setShowRankings(Boolean showRankings) {
		this.showRankings = showRankings;
	}

	/**
     * Sets the signature value
     * @param signature The value to set.
     */
    public void setSignature( String signature ) {
        this.signature = signature;
    }

    /**
     * Retrieves the signature value
     * @return The signature value.
     */
    public String getSignature() {
        return this.signature;
    }
    /**
     * Sets the useGravatar value
     * @param useGravatar The value to set.
     */
    public void setUseGravatar(Boolean useGravatar) {
        this.useGravatar = useGravatar;
    }

    /**
     * Retrieves the useGravatar value
     * @return The useGravatar value.
     */
    public Boolean getUseGravatar() {
        return this.useGravatar;
    }
    /**
     * Sets the gravatarEmail value
     * @param gravatarEmail The value to set.
     */
    public void setGravatarEmail( String gravatarEmail ) {
        this.gravatarEmail = gravatarEmail;
    }

    /**
     * Retrieves the gravatarEmail value
     * @return The gravatarEmail value.
     */
    public String getGravatarEmail() {
        return this.gravatarEmail;
    }
    /**
     * Sets the acceptAllMsg value
     * @param acceptAllMsg The value to set.
     */
    public void setAcceptAllMsg(Boolean acceptAllMsg) {
        this.acceptAllMsg = acceptAllMsg;
    }

    /**
     * Retrieves the acceptAllMsg value
     * @return The acceptAllMsg value.
     */
    public Boolean getAcceptAllMsg() {
        return this.acceptAllMsg;
    }
    /**
     * Sets the notifyEmail value
     * @param notifyEmail The value to set.
     */
    public void setNotifyEmail(Boolean notifyEmail) {
        this.notifyEmail = notifyEmail;
    }

    /**
     * Retrieves the notifyEmail value
     * @return The notifyEmail value.
     */
    public Boolean getNotifyEmail() {
        return this.notifyEmail;
    }
    /**
     * Sets the notifyMessage value
     * @param notifyMessage The value to set.
     */
    public void setNotifyMessage(Boolean notifyMessage) {
        this.notifyMessage = notifyMessage;
    }

    /**
     * Retrieves the notifyMessage value
     * @return The notifyMessage value.
     */
    public Boolean getNotifyMessage() {
        return this.notifyMessage;
    }
    /**
     * Sets the notifyText value
     * @param notifyText The value to set.
     */
    public void setNotifyText(Boolean notifyText) {
        this.notifyText = notifyText;
    }

    /**
     * Retrieves the notifyText value
     * @return The notifyText value.
     */
    public Boolean getNotifyText() {
        return this.notifyText;
    }

    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(userName);
        sb.append("|");
        sb.append(email);
        sb.append("|");
        sb.append(status);
        sb.append("|");
        sb.append(showEmail);
        sb.append("|");
        sb.append(signature);
        sb.append("|");
        sb.append(useGravatar);
        sb.append("|");
        sb.append(gravatarEmail);
        sb.append("|");
        sb.append(acceptAllMsg);
        sb.append("|");
        sb.append(notifyEmail);
        sb.append("|");
        sb.append(notifyMessage);
        sb.append("|");
        sb.append(notifyText);
        return sb.toString(); 
    } 

}
