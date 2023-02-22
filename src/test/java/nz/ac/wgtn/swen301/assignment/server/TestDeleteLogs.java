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

class TestDeleteLogs {
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
    void test_Valid1() throws IOException, ServletException {
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
        servlet.doDelete(request, response);
        request.addParameters(Map.of("limit", "5", "level", "ALL"));
        servlet.doGet(request, response);
        assertEquals("[]\n", response.getContentAsString());
    }

}
