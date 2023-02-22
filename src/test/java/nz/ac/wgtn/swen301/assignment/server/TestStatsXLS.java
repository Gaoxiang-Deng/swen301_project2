package nz.ac.wgtn.swen301.assignment.server;

import nz.ac.wgtn.swen301.resthome4logs.Server.LevelEnum;
import nz.ac.wgtn.swen301.resthome4logs.Server.LogServlet;
import nz.ac.wgtn.swen301.resthome4logs.Server.StatsXLSServlet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestStatsXLS {
    private StatsXLSServlet statsXLSServlet;
    private LogServlet logsServlet;

    @BeforeEach
    void setupServlet() {
        statsXLSServlet = new StatsXLSServlet();
        logsServlet = new LogServlet();
    }

    @AfterEach
    void clearServlet() {
        statsXLSServlet = null;
        logsServlet = null;
    }

    /**
     * Method under test: default or parameterless constructor of {@link StatsXLSServlet}
     */
    @Test
    void testConstructor() {
        StatsXLSServlet actualStatsXLSServlet = new StatsXLSServlet();
        assertNull(actualStatsXLSServlet.getServletConfig());
        assertEquals("", actualStatsXLSServlet.getServletInfo());
    }

    /**
     * Method under test: {@link StatsXLSServlet#doGet(javax.servlet.http.HttpServletRequest, HttpServletResponse)}
     */
    @Test
    void testDoGet() throws IOException, ServletException {

        StatsXLSServlet statsXLSServlet = new StatsXLSServlet();
        MockHttpServletRequest req = new MockHttpServletRequest();
        statsXLSServlet.doGet(req, new MockHttpServletResponse());
    }

    /**
     * Method under test: {@link StatsXLSServlet#doGet(javax.servlet.http.HttpServletRequest, HttpServletResponse)}
     */
    @Test
    void testDoGet2() throws IOException, ServletException {

        StatsXLSServlet statsXLSServlet = new StatsXLSServlet();
        MockHttpServletRequest req = new MockHttpServletRequest("https://example.org/example",
                "https://example.org/example");

        statsXLSServlet.doGet(req, new MockHttpServletResponse());
    }

    /**
     * Method under test: {@link StatsXLSServlet#doGet(javax.servlet.http.HttpServletRequest, HttpServletResponse)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDoGet3() throws IOException, ServletException {
        StatsXLSServlet statsXLSServlet = new StatsXLSServlet();
        statsXLSServlet.doGet(new MockHttpServletRequest(), null);
    }

    @Test
    void contentTypeTest() throws ServletException, IOException {
        MockHttpServletRequest req1 = new MockHttpServletRequest();
        MockHttpServletResponse res1 = new MockHttpServletResponse();
        statsXLSServlet.doGet(req1, res1);
        assertEquals("application/vnd.ms-excel", res1.getContentType());
    }

    @Test
    void formatTest() throws ServletException, IOException {
        MockHttpServletRequest req1 = new MockHttpServletRequest();
        MockHttpServletResponse res1 = new MockHttpServletResponse();
        statsXLSServlet.doGet(req1, res1);
        Workbook workbook = new HSSFWorkbook(new ByteArrayInputStream(res1.getContentAsByteArray()));
        workbook.getSheetAt(0);
        var resHeader = new ArrayList<String>();
        workbook.getSheetAt(0).getRow(0).forEach(e -> resHeader.add(e.getStringCellValue()));
        ArrayList<String> headerList = new ArrayList<>(List.of("logger"));
        headerList.addAll(Arrays.stream(LevelEnum.values()).map(LevelEnum::toString).collect(Collectors.toUnmodifiableList()));
        assertEquals(headerList, resHeader);
    }

}
