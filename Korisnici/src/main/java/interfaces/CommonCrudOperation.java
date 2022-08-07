package interfaces;

import bean.User;

public interface CommonCrudOperation {
	public boolean checkIfExists(String username);
	public boolean deleteUser(String username);
	public User getUser(String username);
}
