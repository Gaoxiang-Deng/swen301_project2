package nz.ac.wgtn.swen301.assignment.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen301.resthome4logs.Server.LevelEnum;
import nz.ac.wgtn.swen301.resthome4logs.Server.LogEvent;
import nz.ac.wgtn.swen301.resthome4logs.Server.LogServlet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestPostLogs {
    private LogServlet servlet;

    @BeforeEach
    void setupServlet() {
        servlet = new LogServlet();
    }

    @AfterEach
    void clearServlet() {
        servlet = null;
    }

    /**
     * Test invalid 1. Post an empty request.
     *
     * @throws IOException      the io exception
     * @throws ServletException the servlet exception
     */
    @Test
    void test_empty() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        servlet.doPost(request, response);
        assertEquals(400, response.getStatus());
    }

    /**
     * Test invalid 2. Adding duplicated LogEvent. Expect 409.
     *
     * @throws IOException      the io exception
     * @throws ServletException the servlet exception
     */
    @Test
    void test_duplicatedID() throws IOException, ServletException {
        var om = new ObjectMapper();
        var log = new LogEvent("message", "thread", "logger", LevelEnum.INFO, "info");
        String body = "  {\n" +
            "    \"id\": \"d290f1ee-6c54-4b01-90e6-d701748f0851\",\n" +
            "    \"message\": \"application started\",\n" +
            "    \"timestamp\": \"04-05-2021 10:12:00\",\n" +
            "    \"thread\": \"main\",\n" +
            "    \"logger\": \"com.example.Foo\",\n" +
            "    \"level\": \"DEBUG\",\n" +
            "    \"errorDetails\": \"string\"\n" +
            "  }";
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        MockHttpServletResponse response1 = new MockHttpServletResponse();
        request1.setContent(body.getBytes());
        MockHttpServletRequest request2 = new MockHttpServletRequest();
        MockHttpServletResponse response2 = new MockHttpServletResponse();
        request2.setContent(body.getBytes());
        servlet.doPost(request1, response1);
        servlet.doPost(request2, response2);
        assertEquals(409, response2.getStatus());
    }

    /**
     * Test invalid 1. Expect 400 for invalid input, object invalid.
     *
     * @throws IOException      the io exception
     * @throws ServletException the servlet exception
     */
    @Test
    void test_invalid1() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        String body = " \n" +
            "    \"timestamp\": \"04-05-2021 10:12:00\",\n" +
            "    \"thread\": \"main\",\n" +
            "    \"logger\": \"com.example.Foo\",\n" +
            "    \"level\": \"DEBUG\",\n" +
            "    \"errorDetails\": \"string\"\n" +
            "  }";
        request.setContent(body.getBytes());
        servlet.doPost(request, response);
        assertEquals(400, response.getStatus());
    }
    @Test
    void test_valid1() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        String body = "  {\n" +
            "    \"id\": \"d290f1ee-6c54-4b01-90e6-d701748f0851\",\n" +
            "    \"message\": \"application started\",\n" +
            "    \"timestamp\": \"04-05-2021 10:12:00\",\n" +
            "    \"thread\": \"main\",\n" +
            "    \"logger\": \"com.example.Foo\",\n" +
            "    \"level\": \"DEBUG\",\n" +
            "    \"errorDetails\": \"string\"\n" +
            "  }";
        request.setContent(body.getBytes());
        servlet.doPost(request, response);
        assertEquals(201, response.getStatus());
    }
}
