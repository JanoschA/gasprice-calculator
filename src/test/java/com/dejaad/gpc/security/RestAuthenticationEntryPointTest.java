package com.dejaad.gpc.security;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class RestAuthenticationEntryPointTest {

    private final RestAuthenticationEntryPoint entryPoint = new RestAuthenticationEntryPoint();

    @Test
    void commence() throws IOException {
        HttpServletResponse httpServletResponse = mock();

        entryPoint.commence(mock(), httpServletResponse, mock());

        verify(httpServletResponse, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, null);
    }
}
