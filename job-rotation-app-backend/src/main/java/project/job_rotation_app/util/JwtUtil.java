package project.job_rotation_app.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  // 256 bits  for security - generated using https://jwtgenerator.com/tools/jwt-generator
  private static final String SECRET_KEY = "HoZgTdegzYtirr_3pIfW3tKQpfyP-Gm5Mna4zX_YE_gBqGS-MmPRpluz2VSbbf2Lg6Ga0yv7wGPPmOAltR8bVWGHcdl05WwI2gLc4ricqeXcbQ5IhBhWhusyKoR8XFTJSlxHu2goUVcFhqROuqoqicc_sw3IzPlx9TlTJkuiU_YJycVPQz1_ZNtpZb2TvDnRYrNBrTMtJYvYTxDGKaRin--r1dsjIvz_Fvrc7OEr6xfs_gqcB_ZkwJieVWJ7aqtQSzQzG0DBOBxRXwNgIUydzkMb06UNQ6DhK4PIpZGoN30cmKBdnfa6qZ79kbHh6CWUIjcd74mG5eeVI4vDuCqHlg";

  // 15 mins, short-lived tokens limits the amount of time the token can be used
  private static final long EXPIRATION_TIME =
      900000;

  private static final SecretKey KEY =
      Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET_KEY));

  public String generateToken(String username, String userId) {
    Instant now = Instant.now();
    Instant expiry = now.plusMillis(EXPIRATION_TIME);

    return Jwts.builder()
        .subject(username)
        .claims(Map.of(
            "userId", userId
        ))
        .issuedAt(Date.from(now))
        .expiration(Date.from(expiry))
        .signWith(KEY)
        .compact();
  }

  public String extractUsername(String token) {
    return parseClaims(token).getSubject();
  }

  public boolean isTokenValid(String token) {
    try {
      parseClaims(token); // throws if invalid or expired
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  public Claims parseClaims(String token) {
    return Jwts.parser()
        .verifyWith(KEY)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }
}
