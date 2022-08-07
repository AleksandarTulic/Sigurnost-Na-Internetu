package bean;

import java.io.*;

public class AdminS extends User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public AdminS() {
		super();
	}
	
	public AdminS(String username, String password) {
		super(username, password, "adminS");
	}
}
