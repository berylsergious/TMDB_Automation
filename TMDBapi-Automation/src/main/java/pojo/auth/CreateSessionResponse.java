package pojo.auth;

public class CreateSessionResponse {

	private boolean success;
	private String session_id;
	
	
	
	//getters
    public boolean isSuccess() {
		return success;
	}
	
	public String getSession_id() {
		return session_id;
	}
	
	
	//setters
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	
	
}
