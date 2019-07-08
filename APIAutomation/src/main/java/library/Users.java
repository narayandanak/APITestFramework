package library;

public class Users implements Cloneable{

	private String identifier;
	private String id;
	private String token;

	public Users(String identifier, String id, String token) {
		this.identifier =  identifier;
		this.id = id;
		this.token = token;
	}
	
	public static String getIdfromIdentifier(String identifier) {
		return MyBaseTest.hashMapOfUsers.get(identifier.toUpperCase()).getId();
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public Users clone() {
		return this;
	}

}
