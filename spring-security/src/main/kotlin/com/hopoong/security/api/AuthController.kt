package com.hopoong.security.api

import com.hopoong.security.domain.member.MemberDto
import com.hopoong.security.response.CommonResponse
import com.hopoong.security.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class AuthController(
    private val authService: AuthService,
) {

    @GetMapping("/all")
    fun redisDataAll() : ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.OK).body(authService.redisDataAll())
    }

    @GetMapping("/clear")
    fun redisDataClear() : ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.OK).body(authService.redisDataClear())
    }

    @GetMapping("/expire/{key}")
    fun redisDataKeyExpireTime(@PathVariable key: String) : ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.OK).body(authService.redisDataKeyExpireTime(key))
    }


    /*
     * 로그인
     */
    @PostMapping("/login")
    fun saveMemberInfo(@RequestBody params: MemberDto) : ResponseEntity<Any> {
        println(params.toString())
        var token = authService.loadUserByUsername(params)

        return ResponseEntity.status(HttpStatus.OK)
            .headers(authService.createRefreshToken(token.accessToken)).body(token)
    }

    /*
     * 회원가입
     */
    @PostMapping("/signup")
    fun signup(@RequestBody params: MemberDto) : ResponseEntity<CommonResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(authService.signup(params))
    }

    /*
     * refresh token 재발행
     */
    @GetMapping("/refresh-token")
    fun reissueAccessToken(@CookieValue(value = "refresh-token", required = true) refreshToken: String) : ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.OK).body(authService.reissueAccessToken(refreshToken))
    }

    /*
     * logout
     */
    @GetMapping("/logout")
    fun logout(@CookieValue(value = "refresh-token", required = true) refreshToken: String) : ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.OK)
            .headers(authService.logout(refreshToken)).body("successfully")
    }
}