# odyssey-backend
Backend part of booking application Odyssey developed in Java using Spring Boot framework.
Security implemented in combination with Keycloak server. All communication is through TLS (https). Users are stored in embedded ldap database. More about security in the following chapter.

### Owasp top 10 list

1. **A01:2021-Broken Access Control**
    - **Description:** Mitigation implemented using RBAC control with 3 levels of access using keycloak.

2. **A02:2021-Cryptographic Failures**
    - **Description:** Application is using keycloak for all user management so the passwords are automatically hashed using PBKDF2 algorithm. Additionally, TLS is used for all communication (keycloak server, frontend and backend).

3. **A03:2021-Injection**
    - **Description:** Injection is prevented by using JPA Repository and annotations for `@Query` and `@Param`.

4. **A05:2021-Security Misconfiguration**
    - **Description:** Security filter chain uses XSS protection specified in the Web Config. 

5. **A07:2021-Identification and Authentication Failures**
    - **Description:** In the keycloak is implemented password policy that prevents using common passwords, as well as using special characters and upper letters. Additionally, login is secured with 2FA using any mobile Authenticator. 