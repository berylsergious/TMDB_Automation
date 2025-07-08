package pojo.auth;

public class RequestTokenResponse {
	
	private boolean success;
	private String expires_at;
    private String request_token;
    
    
    
    
   //getters
	public boolean isSuccess() {
		return success;
	}
	
	public String getExpires_at() {
		return expires_at;
	}
	
	public String getRequest_token() {
		return request_token;
	}

	
	
	
	//Setters
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setExpires_at(String expires_at) {
		this.expires_at = expires_at;
	}

	public void setRequest_token(String request_token) {
		this.request_token = request_token;
	}
	
	

}
