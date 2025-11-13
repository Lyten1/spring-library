package mate.academy.springlibrary.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mate.academy.springlibrary.dto.book.BookDto;
import mate.academy.springlibrary.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.springlibrary.dto.category.CategoryDto;
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

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerIntegration {

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
    @DisplayName("Save new category")
    void save_ValidCategory_ShouldSaveCategory() throws Exception {
        // Given
        CategoryDto expected = new CategoryDto()
                .setName("investigation");
        String content = objectMapper.writeValueAsString(expected);

        // When
        MvcResult result = mockMvc.perform(
                        post("/categories")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // Then
        CategoryDto actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), CategoryDto.class);
        assertNotNull(actual);
        assertNotNull(actual.getId());

        assertTrue(reflectionEquals(expected, actual, "id"));
    }


    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get all categories")
    void getAll_GivenCategoriesInCatalog_ShouldReturnAllCategories() throws Exception {
        // Given
        List<CategoryDto> expected = new ArrayList<>();
        expected.add(new CategoryDto()
                .setName("fantasy"));
        expected.add(new CategoryDto()
                .setName("horror"));

        // When
        MvcResult result = mockMvc.perform(
                        get("/categories/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String json = result.getResponse().getContentAsString();
        JsonNode content = objectMapper.readTree(json).get("content");
        CategoryDto[] actual = objectMapper.readValue(content.toString(), CategoryDto[].class);
        assertEquals(2, actual.length);
        assertTrue(reflectionEquals(expected.get(0), actual[0]));
        assertTrue(reflectionEquals(expected.get(1), actual[1]));
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get category by id")
    void getCategoryById_GivenCategoriesInCatalogAndCorrectId_ShouldReturnCaWithCorrectId() throws Exception {
        // Given
        CategoryDto expected = new CategoryDto()
                .setId(1L)
                .setName("fantasy");

        // When
        MvcResult result = mockMvc.perform(
                        get("/categories/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), BookDto.class);
        assertNotNull(actual);
        assertTrue(reflectionEquals(expected, actual));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update category")
    void updateCategory_GivenCategoryToUpdate_ShouldUpdateCategory() throws Exception {
        // Given
        CategoryDto expected = new CategoryDto()
                .setId(1L)
                .setName("investigation");
        String content = objectMapper.writeValueAsString(expected);

        // When
        MvcResult result = mockMvc.perform(
                        put("/categories/1")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        CategoryDto actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), CategoryDto.class);
        assertNotNull(actual);
        assertTrue(reflectionEquals(expected, actual));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    @Test
    @DisplayName("Delete category")
    void deleteCategory_GivenCategoryToDelete_ShouldDeleteCategory() throws Exception {
        // Given

        // When
        mockMvc.perform(
                        delete("/categories/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        // Then
        mockMvc.perform(
                        get("/categories/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get books by category")
    void getBooksByCategoryId_GivenBooksInCatalog_ShouldReturnBookWithCriteria() throws Exception {
        // Given
        List<BookDtoWithoutCategoryIds> expected = new ArrayList<>();
        expected.add(new BookDtoWithoutCategoryIds()
                .setId(1L)
                .setPrice(BigDecimal.valueOf(12.99))
                .setTitle("hobit")
                .setAuthor("D. Lois")
                .setIsbn("123532421-1234"));
        expected.add(new BookDtoWithoutCategoryIds()
                .setId(2L)
                .setPrice(BigDecimal.valueOf(16.89))
                .setTitle("man")
                .setAuthor("D. Peter")
                .setIsbn("54325654-1235"));

        // When
        MvcResult result = mockMvc.perform(
                        get("/categories/1/books")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        BookDtoWithoutCategoryIds[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), BookDtoWithoutCategoryIds[].class);
        assertEquals(2, actual.length);
        assertTrue(reflectionEquals(expected.get(0), actual[0]));
        assertTrue(reflectionEquals(expected.get(1), actual[1]));
    }
}
