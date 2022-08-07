package bean;

import java.io.*;

public class Client extends User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String root;
	private String domen;
	private boolean create;
	private boolean retrieve;
	private boolean update;
	private boolean delete;
	
	public Client() {
		super();
	}
	
	public Client(String username, String password, String root, String domen,
			boolean create, boolean retrieve, boolean update, boolean delete) {
		super(username, password, "client");
		
		this.delete = delete;
		this.create = create;
		this.update = update;
		this.retrieve = retrieve;
		this.domen = domen;
		this.root = root;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getDomen() {
		return domen;
	}

	public void setDomen(String domen) {
		this.domen = domen;
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public boolean isRetrieve() {
		return retrieve;
	}

	public void setRetrieve(boolean retrieve) {
		this.retrieve = retrieve;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	
	
}
