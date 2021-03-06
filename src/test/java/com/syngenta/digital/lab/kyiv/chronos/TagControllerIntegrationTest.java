package com.syngenta.digital.lab.kyiv.chronos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.syngenta.digital.lab.kyiv.chronos.utils.db.utils.ExpectedGeneratedQueryNumber;
import com.syngenta.digital.lab.kyiv.chronos.utils.db.utils.ExpectedGeneratedQueryNumbers;
import com.syngenta.digital.lab.kyiv.chronos.utils.db.utils.QueryType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TagDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.utils.JsonUtils;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT_UNORDERED;

@DatabaseTearDown("/dbTearDown.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class TagControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/TagControllerIntegrationTest/shouldAddNewTag/dbSetup.xml")
    @ExpectedDatabase(value = "/TagControllerIntegrationTest/shouldAddNewTag/expectedDataBase.xml", assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumbers({
            @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 1),
            @ExpectedGeneratedQueryNumber(queryType = QueryType.INSERT, expectedNumber = 1)
    })
    public void shouldAddNewTag() {
        Response response = this.getRestAssured()
                .body(JsonUtils.readFromJson("/TagControllerIntegrationTest/shouldAddNewTag/addNewTagRequest.json", TagDto.class))
                .contentType(ContentType.JSON)
                .post("/api/v0/tag")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<TagDto> actualResponse = objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<TagDto>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getTagId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getTag()).isNotBlank();
        Assertions.assertThat(actualResponse.getData().getTag()).isEqualTo("awesome_tag");
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/TagControllerIntegrationTest/shouldFindAllTagsMatchingRegex/dbSetup.xml")
    @ExpectedDatabase(value = "/TagControllerIntegrationTest/shouldFindAllTagsMatchingRegex/expectedDataBase.xml", assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldFindAllTagsMatchingRegex() {
        Response response = RestAssured
                .given()
                .param("prefix", "tag")
                .get("/api/v0/tag/matching")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<List<TagDto>> actualResponse = objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<List<TagDto>>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(302);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotEmpty();
        Assertions.assertThat(actualResponse.getData().stream().map(TagDto::getTag).collect(Collectors.toList()))
                .isEqualTo(List.of("tag_123", "tag", "tag   "));
    }
}
