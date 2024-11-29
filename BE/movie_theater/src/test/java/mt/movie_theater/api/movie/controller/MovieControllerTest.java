//package mt.movie_theater.api.movie.controller;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import mt.movie_theater.api.movie.request.MovieCreateRequest;
//import mt.movie_theater.api.movie.service.MovieService;
//import mt.movie_theater.domain.genre.GenreType;
//import mt.movie_theater.domain.movie.AgeRating;
//import mt.movie_theater.domain.movie.ScreeningType;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//@WebMvcTest(controllers = MovieController.class)
//class MovieControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private MovieService movieService;
//
//    @DisplayName("신규 영화를 등록한다.")
//    @Test
//    void createMovie() throws Exception {
//        //given
//        MovieCreateRequest request = MovieCreateRequest.builder()
//                .title("청설")
//                .subTitle("Hear Me: Our Summer")
//                .description("가슴으로 사랑을 느끼는, 청량한 설렘의 순간\"")
//                .releaseDate(LocalDate.of(2024, 10, 27))
//                .durationMinutes(108)
//                .posterImage()
//                .ageRating(AgeRating.ALL)
//                .director("조선호")
//                .screeningType(ScreeningType.TWO_D)
//                .standardPrice(10000)
//                .genreTypes(List.of(GenreType.ROMANCE, GenreType.DRAMA))
//                .actors(List.of("홍경", "노윤서"))
//                .build();
//
//        //when, then
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movies/new")
//                .content(objectMapper.writeValueAsString(request))
//                .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.status").value("OK"))
//                .andExpect(jsonPath("$.message").value("OK"))
//                .andExpect(jsonPath("$.data").isNotEmpty());
//    }
//}