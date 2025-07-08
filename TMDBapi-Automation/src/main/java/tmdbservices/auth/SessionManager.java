package tmdbservices.auth;

public class SessionManager {

	private static String sessionId;

    public static void setSessionId(String id) {
        sessionId = id;
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static boolean isSessionActive() {
        return sessionId != null && !sessionId.isEmpty();
    }

    public static void clearSession() {
        sessionId = null;
    }
	
}
