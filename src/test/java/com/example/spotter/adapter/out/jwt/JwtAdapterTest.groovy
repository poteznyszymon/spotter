package com.example.spotter.adapter.out.jwt

import com.example.spotter.domain.Role
import com.example.spotter.domain.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import spock.lang.Specification
import spock.lang.Subject

class JwtAdapterTest extends Specification {

	static final String SECRET = Base64.encoder.encodeToString(Jwts.SIG.HS256.key().build().encoded)
	static final long EXPIRATION_MS = 3600000 // 1 hours

	@Subject
	JwtAdapter jwtAdapter = new JwtAdapter(SECRET, EXPIRATION_MS)

	def "should generate a non null token"() {
		when:
			def user = createUser("testuser")
			def token = jwtAdapter.generateToken(user)
		then:
			token != null
			!token.isEmpty()
	}

	def "should extract username from token"() {
		given:
			def user = createUser("testuser")
			def token = jwtAdapter.generateToken(user)
		when:
			def username = jwtAdapter.extractClaim(token, Claims::getSubject)
		then:
			username == "testuser"
	}

	def "should validate token for correct username"() {
		given:
			def user = createUser("testuser")
			def token = jwtAdapter.generateToken(user)
		expect:
			jwtAdapter.isTokenValid(token, "testuser")
	}

	def "should reject token for wrong username"() {
		given:
			def user = createUser("testuser")
			def token = jwtAdapter.generateToken(user)
		expect:
			!jwtAdapter.isTokenValid(token, "otheruser")
	}

	def "should generate different tokens for different usernames"() {
		given:
			def user1 = createUser("user1")
			def user2 = createUser("user2")
			def token1 = jwtAdapter.generateToken(user1)
			def token2 = jwtAdapter.generateToken(user2)
		expect:
			token1 != token2
	}

	def "should throw exception for invalid token"() {
		when:
			jwtAdapter.extractClaim("invalid.token.here", Claims::getSubject)
		then:
			thrown(Exception)
	}

	def "should throw exception for token signed with different key"() {
		given:
			def otherSecret = Base64.encoder.encodeToString(Jwts.SIG.HS256.key().build().encoded)
			def otherAdapter = new JwtAdapter(otherSecret, EXPIRATION_MS)
			def user = createUser("testuser")
			def token = otherAdapter.generateToken(user)
		when:
			jwtAdapter.extractClaim(token, Claims::getSubject)
		then:
			thrown(Exception)
	}

	def "should reject expired token"() {
		given:
			def expiredAdapter = new JwtAdapter(SECRET, -1000)
			def user = createUser("testuser")
			def token = expiredAdapter.generateToken(user)
		when:
			jwtAdapter.extractClaim(token, Claims::getSubject)
		then:
			thrown(Exception)
	}

	private static User createUser(String username) {
		def user = new User()
		user.setUsername(username)
		user.setUuid(UUID.randomUUID())
		user.setFirstName("John")
		user.setLastName("Doe")
		user.setRole(Role.USER)
		user.setEmail("johndoe@example.com")
		return user
	}
}
