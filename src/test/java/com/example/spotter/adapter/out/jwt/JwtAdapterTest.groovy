package com.example.spotter.adapter.out.jwt

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
			def token = jwtAdapter.generateToken("testuser")

		then:
			token != null
			!token.isEmpty()
	}

	def "should extract username from token"() {
		given:
			def token = jwtAdapter.generateToken("testuser")

		when:
			def username = jwtAdapter.extractUsername(token)

		then:
			username == "testuser"
	}

	def "should validate token for correct username"() {
		given:
			def token = jwtAdapter.generateToken("testuser")

		expect:
			jwtAdapter.isTokenValid(token, "testuser")
	}

	def "should reject token for wrong username"() {
		given:
			def token = jwtAdapter.generateToken("testuser")

		expect:
			!jwtAdapter.isTokenValid(token, "otheruser")
	}

	def "should generate different tokens for different usernames"() {
		given:
			def token1 = jwtAdapter.generateToken("user1")
			def token2 = jwtAdapter.generateToken("user2")

		expect:
			token1 != token2
	}

	def "should throw exception for invalid token"() {
		when:
			jwtAdapter.extractUsername("invalid.token.here")

		then:
			thrown(Exception)
	}

	def "should throw exception for token signed with different key"() {
		given:
			def otherSecret = Base64.encoder.encodeToString(Jwts.SIG.HS256.key().build().encoded)
			def otherAdapter = new JwtAdapter(otherSecret, EXPIRATION_MS)
			def token = otherAdapter.generateToken("testuser")

		when:
			jwtAdapter.extractUsername(token)

		then:
			thrown(Exception)
	}

	def "should reject expired token"() {
		given:
			def expiredAdapter = new JwtAdapter(SECRET, -1000)
			def token = expiredAdapter.generateToken("testuser")

		when:
			jwtAdapter.extractUsername(token)

		then:
			thrown(Exception)
	}
}
