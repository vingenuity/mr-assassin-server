package us.mrassassin.c2dm;

import java.io.IOException;

import us.mrassassin.c2dm.AuthenticationUtil;

public class GetAuthenticationToken {

	public static void main(String[] args) throws IOException {
		String token = AuthenticationUtil.getToken(SecureStorage.USER,
				SecureStorage.PASSWORD);
		System.out.println(token);
	}
}
