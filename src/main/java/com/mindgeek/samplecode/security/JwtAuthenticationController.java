package com.mindgeek.samplecode.security;

import com.mindgeek.samplecode.datatransferobject.request.JwtRequest;
import com.mindgeek.samplecode.datatransferobject.request.JwtResponse;
import com.mindgeek.samplecode.domainobject.AppUserPrincipal;
import com.mindgeek.samplecode.service.CustomUserDetailService;
import com.mindgeek.samplecode.service.UserService;
import com.mindgeek.samplecode.service.driver.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailService userDetailsService;
    @Autowired
    private UserService userService;

    @Autowired
    DriverService driverService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        driverService.loginDriver(authenticationRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token, null));
    }



    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
