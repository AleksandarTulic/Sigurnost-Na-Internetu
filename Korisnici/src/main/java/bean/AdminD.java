package bean;

import java.io.*;

public class AdminD extends User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String root;
	
	public AdminD() {
		super();
	}
	
	public AdminD(String username, String password, String root) {
		super(username, password, "adminD");
		this.root = root;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}
}
