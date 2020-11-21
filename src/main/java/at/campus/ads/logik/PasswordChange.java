package at.campus.ads.logik;

import at.campus.ads.domain.User;
import at.campus.ads.utils.CurrentUserSingleton;

public class PasswordChange {
	private User currentUser;
	
	public PasswordChange() {
		this.currentUser = CurrentUserSingleton.INSTANCE.getUser();
	}
	
}
