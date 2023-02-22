package nz.ac.wgtn.swen301.assignment.server;

import nz.ac.wgtn.swen301.resthome4logs.Server.LogServlet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGetLog {
    private LogServlet servlet;

    @BeforeEach
    void setupServlet() {
        servlet = new LogServlet();
    }

    @AfterEach
    void clearServlet() {
        servlet = null;
    }

    @Test
    void test_contentType() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addParameters(Map.of("limit", "5", "level", "ALL"));
        servlet.doGet(request, response);
        assertEquals("application/json", response.getContentType());
    }

    @Test
    void test_noParameters() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        servlet.doGet(request, response);
        assertEquals(400, response.getStatus());
    }

    @Test
    void test_withInvalidParameters() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addParameters(Map.of("limit", "5", "randomKey", "randomValue"));
        servlet.doGet(request, response);
        assertEquals(400, response.getStatus());
    }

    @Test
    void test_withValidParameters1() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addParameters(Map.of("limit", "5", "level", "ALL"));
        servlet.doGet(request, response);
        assertEquals(MockHttpServletResponse.SC_OK, response.getStatus());
        assertEquals("[]\n", response.getContentAsString());
        //expect empty list
    }
}
