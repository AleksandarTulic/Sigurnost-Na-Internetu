package bean;

import java.util.Objects;

public class UserAction {
	private String username;
	private String dateAction;
	private String timeAction;
	private String typeAction;
	private String documentName;
	 
	public UserAction(String username, String dateAction, String timeAction, String typeAction, String documentName) {
		super();
		this.username = username;
		this.timeAction = timeAction;
		this.typeAction = typeAction;
		this.documentName = documentName;
		this.dateAction = dateAction;
	}
	
	public String getDateAction() {
		return dateAction;
	}
	
	public void setDateAction(String dateAction) {
		this.dateAction = dateAction;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTimeAction() {
		return timeAction;
	}

	public void setTimeAction(String timeAction) {
		this.timeAction = timeAction;
	}

	public String getTypeAction() {
		return typeAction;
	}

	public void setTypeAction(String typeAction) {
		this.typeAction = typeAction;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(documentName, timeAction, typeAction, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAction other = (UserAction) obj;
		return Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "UserAction [username=" + username + ", dateAction=" + dateAction + ", timeAction=" + timeAction + ", typeAction=" + typeAction
				+ ", documentName=" + documentName + "]";
	}
}
