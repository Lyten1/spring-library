package mate.academy.springlibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mate.academy.springlibrary.dto.book.BookDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIntegrationTest {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(@Autowired DataSource dataSource) throws SQLException {
        teardown(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books-and-categories/insert-two-books-to-db-with-category.sql")
            );
        }
    }

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @AfterAll
    static void afterAll( @Autowired DataSource dataSource) throws SQLException {
        teardown(dataSource);
    }

    static void teardown(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books-and-categories/remove-all-books-and-category.sql")
            );
        }
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Save new book")
    void save_ValidBook_ShouldSaveBook() throws Exception {
        // Given
        BookDto expected = new BookDto()
                .setPrice(BigDecimal.valueOf(12.99))
                .setTitle("hobit")
                .setAuthor("D. Lois")
                .setIsbn("12353249871-1234")
                .setCategoryIds(List.of(1L));
        String content = objectMapper.writeValueAsString(expected);
        // When
        MvcResult result = mockMvc.perform(
                        post("/books")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // Then
        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), BookDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }


    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get all books")
    void getAll_GivenBooksInCatalog_ShouldReturnAllBooks() throws Exception {
        // Given
        List<BookDto> expected = new ArrayList<>();
        expected.add(new BookDto()
                .setId(1L)
                .setPrice(BigDecimal.valueOf(12.99))
                .setTitle("hobit")
                .setAuthor("D. Lois")
                .setIsbn("123532421-1234")
                .setCategoryIds(List.of(1L)));
        expected.add(new BookDto()
                .setId(2L)
                .setPrice(BigDecimal.valueOf(16.89))
                .setTitle("man")
                .setAuthor("D. Peter")
                .setIsbn("54325654-1235")
                .setCategoryIds(List.of(1L)));

        // When
        MvcResult result = mockMvc.perform(
                get("/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        BookDto[] actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), BookDto[].class);
        Assertions.assertEquals(2, actual.length);
        EqualsBuilder.reflectionEquals(expected.get(0), actual[0]);
        EqualsBuilder.reflectionEquals(expected.get(1), actual[1]);
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get book by id")
    void getBookById_GivenBooksInCatalogAndCorrectId_ShouldReturnBookWithCorrectId() throws Exception {
        // Given
        BookDto expected = new BookDto()
                .setId(1L)
                .setPrice(BigDecimal.valueOf(12.99))
                .setTitle("hobit")
                .setAuthor("D. Lois")
                .setIsbn("123532421-1234")
                .setCategoryIds(List.of(1L));

        // When
        MvcResult result = mockMvc.perform(
                        get("/books/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), BookDto.class);
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update book")
    void updateBook_GivenBookToUpdate_ShouldUpdateBook() throws Exception {
        // Given
        BookDto expected = new BookDto()
                .setId(1L)
                .setPrice(BigDecimal.valueOf(12.99))
                .setTitle("hobitovan")
                .setAuthor("D. Lois")
                .setIsbn("123532421-1234")
                .setCategoryIds(List.of(1L));
        String content = objectMapper.writeValueAsString(expected);

        // When
        MvcResult result = mockMvc.perform(
                        put("/books/1")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), BookDto.class);
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    @Test
    @DisplayName("Delete book")
    void deleteBook_GivenBookToDelete_ShouldDeleteBook() throws Exception {
        // Given

        // When
        mockMvc.perform(
                        delete("/books/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        // Then
        mockMvc.perform(
                        get("/books/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Search book")
    void searchBook_GivenBooksInCatalog_ShouldReturnBookWithCriteria() throws Exception {
        // Given
        List<BookDto> expected = new ArrayList<>();
        expected.add(new BookDto()
                .setId(1L)
                .setPrice(BigDecimal.valueOf(12.99))
                .setTitle("hobit")
                .setAuthor("D. Lois")
                .setIsbn("123532421-1234")
                .setCategoryIds(List.of(1L)));

        // When
        MvcResult result = mockMvc.perform(
                        get("/books/search")
                                .param("partTitle", "hob")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        BookDto[] actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), BookDto[].class);
        Assertions.assertNotNull(actual[0]);
        EqualsBuilder.reflectionEquals(expected, actual[0]);
    }
}
