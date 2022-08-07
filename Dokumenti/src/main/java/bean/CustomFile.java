package bean;

import java.util.Objects;

public class CustomFile {
	private String path = "";
	private String file = "";
	private String type = "";
	
	public CustomFile(String path, String file, String type) {
		super();
		this.path = path;
		this.file = file;
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public int hashCode() {
		return Objects.hash(file, path, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomFile other = (CustomFile) obj;
		return Objects.equals(file, other.file) && Objects.equals(path, other.path) && Objects.equals(type, other.type);
	}
	@Override
	public String toString() {
		return "CustomFile [path=" + path + ", file=" + file + ", type=" + type + "]";
	}
}
