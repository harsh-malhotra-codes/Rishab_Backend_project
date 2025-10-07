package in.Project.RestApi.controller;

import in.Project.RestApi.dto.ProfileDTO;
import in.Project.RestApi.io.AuthRequest;
import in.Project.RestApi.io.AuthResponse;
import in.Project.RestApi.io.ProfileRequest;
import in.Project.RestApi.io.ProfileResponse;
import in.Project.RestApi.service.CustomUserDetailsService;
import in.Project.RestApi.service.ProfileService;
import in.Project.RestApi.service.TokenBlackListService;
import in.Project.RestApi.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private  final ModelMapper modelMapper;
    private final ProfileService profileService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;
    private final TokenBlackListService tokenBlackListService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ProfileResponse createProfile(@Valid  @RequestBody ProfileRequest profileRequest) {
        log.info("API /register is called {}",profileRequest);
        ProfileDTO profileDTO= mapToProfileDTO(profileRequest);
        profileDTO=profileService.createProfile(profileDTO);
        log.info("Printing the profile dto details {}",profileDTO);
        return mapToProfileResponse(profileDTO);
    }

    @PostMapping("/login")
    public AuthResponse authenticateProfile(@RequestBody AuthRequest authRequest) throws Exception {
        log.info("API /login is called {}",authRequest);
        authenticate(authRequest);
        final UserDetails userDetails= userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new AuthResponse(token,authRequest.getEmail());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/signout")
    public void  signout (HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromRequest(request);
        if(jwtToken!=null) {
            tokenBlackListService.addTokenToBlackList(jwtToken);
        }
    }

    private String extractJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken!=null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void authenticate(AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        }catch(DisabledException ex) {
            throw new Exception("Profile Disabled");
        }catch (BadCredentialsException ex) {
            throw new Exception("Bad Credentials");
        }
    }

    private ProfileDTO mapToProfileDTO(ProfileRequest profileRequest) {
        return modelMapper.map(profileRequest,ProfileDTO.class);
    }

    private ProfileResponse mapToProfileResponse(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, ProfileResponse.class);
    }


}
