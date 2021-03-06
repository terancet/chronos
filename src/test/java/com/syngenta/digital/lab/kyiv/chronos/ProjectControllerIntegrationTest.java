package com.syngenta.digital.lab.kyiv.chronos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.syngenta.digital.lab.kyiv.chronos.utils.db.utils.ExpectedGeneratedQueryNumber;
import com.syngenta.digital.lab.kyiv.chronos.utils.db.utils.ExpectedGeneratedQueryNumbers;
import com.syngenta.digital.lab.kyiv.chronos.utils.db.utils.QueryType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.ProjectDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.ErrorResponsePayload;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.utils.JsonUtils;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT_UNORDERED;

@DatabaseTearDown("/dbTearDown.xml")
public class ProjectControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldFailToUpdateDeletedProject/dbSetup.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldFailToUpdateDeletedProject/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 3)
    public void shouldFailToUpdateDeletedProject() {

        Response response = this.getRestAssured()
                .body(JsonUtils.readFromJson("/ProjectControllerIntegrationTest/shouldFailToUpdateDeletedProject/failRequest.json", ProjectDto.class))
                .contentType(ContentType.JSON)
                .put("/api/v0/project")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(), new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
        });

        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/ProjectControllerIntegrationTest/shouldFailToUpdateDeletedProject/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldFailToUpdateProjectIfProjectWithIdDoesntExist/dbSetup.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldFailToUpdateProjectIfProjectWithIdDoesntExist/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 3)
    public void shouldFailToUpdateProjectIfProjectWithIdDoesntExist() {
        Response response = this.getRestAssured()
                .body(JsonUtils.readFromJson("/ProjectControllerIntegrationTest/shouldFailToUpdateProjectIfProjectWithIdDoesntExist/failRequest.json", ProjectDto.class))
                .contentType(ContentType.JSON)
                .put("/api/v0/project")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(), new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
        });

        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/ProjectControllerIntegrationTest/shouldFailToUpdateProjectIfProjectWithIdDoesntExist/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldFailToUpdateProjectIfDtoHasNullId/dbSetup.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldFailToUpdateProjectIfDtoHasNullId/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 1)
    public void shouldFailToUpdateProjectIfDtoHasNullId() {
        Response response = this.getRestAssured()
                .body(JsonUtils.readFromJson("/ProjectControllerIntegrationTest/shouldFailToUpdateProjectIfDtoHasNullId/failRequest.json", ProjectDto.class))
                .contentType(ContentType.JSON)
                .put("/api/v0/project")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(), new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
        });

        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/ProjectControllerIntegrationTest/shouldFailToUpdateProjectIfDtoHasNullId/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldNotAddNewProjectWithFlagDeletedSetToTrue/dbDataBase.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldNotAddNewProjectWithFlagDeletedSetToTrue/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 1)
    public void shouldNotAddNewProjectWithFlagDeletedSetToTrue() {
        Response response = this.getRestAssured()
                .body(JsonUtils.readFromJson("/ProjectControllerIntegrationTest/shouldNotAddNewProjectWithFlagDeletedSetToTrue/failToAddNewProjectRequest.json", ProjectDto.class))
                .contentType(ContentType.JSON)
                .post("/api/v0/project")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(), new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
        });

        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/ProjectControllerIntegrationTest/shouldNotAddNewProjectWithFlagDeletedSetToTrue/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyAddNewProject/dbSetup.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyAddNewProject/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumbers({
            @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2),
            @ExpectedGeneratedQueryNumber(queryType = QueryType.INSERT, expectedNumber = 1)
    })
    public void shouldSuccessfullyAddNewProject() {
        Response response = this.getRestAssured()
                .body(JsonUtils.readFromJson("/ProjectControllerIntegrationTest/shouldSuccessfullyAddNewProject/addNewProjectRequest.json", ProjectDto.class))
                .contentType(ContentType.JSON)
                .post("/api/v0/project")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<ProjectDto> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<ProjectDto>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectTypeId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectName()).isEqualTo("Project name");
        Assertions.assertThat(actualResponse.getData().getColor()).isEqualTo("color");
        Assertions.assertThat(actualResponse.getData().getProjectDescription()).isEqualTo("Some useful description");
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldFailToAddNewProjectIfNoExistingProjectTypeIdIsSpecified/dbSetup.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldFailToAddNewProjectIfNoExistingProjectTypeIdIsSpecified/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldFailToAddNewProjectIfNoExistingProjectTypeIdIsSpecified() {
        Response response = this.getRestAssured()
                .body(JsonUtils.readFromJson("/ProjectControllerIntegrationTest/shouldFailToAddNewProjectIfNoExistingProjectTypeIdIsSpecified/failToAddNewProjectRequest.json", ProjectDto.class))
                .contentType(ContentType.JSON)
                .post("/api/v0/project")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(), new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
        });

        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/ProjectControllerIntegrationTest/shouldFailToAddNewProjectIfNoExistingProjectTypeIdIsSpecified/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyFindProjectByItsId/dbSetup.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyFindProjectByItsId/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldSuccessfullyFindProjectByItsId() {
        Response response = this.getRestAssured()
                .get("/api/v0/project/999")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<ProjectDto> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<ProjectDto>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(302);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectTypeId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectName()).isEqualTo("Project name");
        Assertions.assertThat(actualResponse.getData().getProjectDescription()).isEqualTo("Some useful description");
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyFindAllProjects/dbSetup.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyFindAllProjects/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldSuccessfullyFindAllProjects() {
        Response response = this.getRestAssured()
                .get("/api/v0/project")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<List<ProjectDto>> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<List<ProjectDto>>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(302);

        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotEmpty();
        Assertions.assertThat(actualResponse.getData().size()).isEqualTo(2);

        Assertions.assertThat(actualResponse.getData().get(0).getId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().get(0).getProjectTypeId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().get(0).getProjectName()).isEqualTo("Project name");
        Assertions.assertThat(actualResponse.getData().get(0).getProjectDescription()).isEqualTo("Some useful description");
        Assertions.assertThat(actualResponse.getData().get(1).getId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().get(1).getProjectTypeId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().get(1).getProjectName()).isEqualTo("Project name");
        Assertions.assertThat(actualResponse.getData().get(1).getProjectDescription()).isEqualTo("Some useful description");
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyUpdateTheExistingProject/dbSetup.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyUpdateTheExistingProject/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumbers({
            @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 3),
            @ExpectedGeneratedQueryNumber(queryType = QueryType.UPDATE, expectedNumber = 1)
    })
    public void shouldSuccessfullyUpdateTheExistingProject() {
        Response response = this.getRestAssured()
                .body(JsonUtils.readFromJson("/ProjectControllerIntegrationTest/shouldSuccessfullyUpdateTheExistingProject/updateTheExistingProjectRequest.json", ProjectDto.class))
                .contentType(ContentType.JSON)
                .put("/api/v0/project")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<ProjectDto> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<ProjectDto>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectTypeId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectName()).isEqualTo("Updated project name");
        Assertions.assertThat(actualResponse.getData().getProjectDescription()).isEqualTo("Some useful description");
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyDeleteProjectByItsId/dbSetup.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyDeleteProjectByItsId/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    public void shouldSuccessfullyDeleteProjectByItsId() {
        Response response = this.getRestAssured()
                .delete("/api/v0/project/1")
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
    }
}
