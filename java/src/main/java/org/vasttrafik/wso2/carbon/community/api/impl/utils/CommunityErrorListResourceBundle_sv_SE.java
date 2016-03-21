package org.vasttrafik.wso2.carbon.community.api.impl.utils;

import org.vasttrafik.wso2.carbon.common.api.utils.AbstractErrorListResourceBundle;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class CommunityErrorListResourceBundle_sv_SE extends AbstractErrorListResourceBundle {
	
	private static Object[][] content = {
		// Custom error codes
		{"1000","Valideringsfel","Fel vid validering av parameter och/eller entitet"},
		{"1001","Ogiltigt parametervärde","Det angivna värdet {0} för parametern {1} är ogiltigt."},
		{"1002","Resursen saknas","Den efterfrågade resursen hittades inte."},
		{"1003","Alla poster kunde inte tas bort","Av de {0} posterna kunde bara {1} tas bort."},
		{"1004","Databasfel","Oväntat fel i databasen: {0}"},
		{"1101","Ogiltig token","Angiven token är inte längre giltig. Var vänlig logga in på nytt."},
		{"1102","Ogiltig token","Angiven token är ogiltig. Var vänlig ange en korrekt token."},
		{"1103","Felaktig token","Angiven token tillhör en annan användare. Var vänlig ange en korrekt token."},
		{"1104","Ej behörig","Användaren saknar behörighet att utföra begärd operation."},
		{"1105","Token saknas","Obligatorisk JWT token saknas."},
		{"1201","Topic stängd","Angiven topic är stängd för vidare svar och kommentarer."},
		{"1202","Topic borttagen","Angiven topic är borttagen och kan inte längre uppdateras."},
		{"1203","Post saknas","Kan inte skapa topic utan tillhörande post."},
		{"1204","Skapad av saknas","Kan inte uppdatera topic utan att veta vem som skapade den."},
		{"1205","Otillåtet format","Endast administratörer får skapa inlägg i formatet html."}
	};

	@Override
	protected Object[][] getContents() {
		return content;
	}
}
