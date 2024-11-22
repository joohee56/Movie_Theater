package mt.movie_theater.api.movie.controller;

import mt.movie_theater.ControllerTestSupport;

class MovieControllerTest extends ControllerTestSupport {

//    @DisplayName("신규 영화를 등록한다.")
//    @Test
//    void createMovie() throws Exception {
//        //given
//        MovieCreateRequest request = MovieCreateRequest.builder()
//                .title("청설")
//                .subTitle("Hear Me: Our Summer")
//                .description("사랑이야기")
//                .releaseDate(LocalDate.of(2024, 10, 27))
//                .durationMinutes(108)
//                .posterUrl("test@test.com")
//                .ageRating(AgeRating.ALL)
//                .director("조선호")
//                .standardPrice(10000)
//                .screeningType(ScreeningType.TWO_D)
//                .genreTypes(List.of(GenreType.DRAMA, GenreType.COMEDY))
//                .actors(List.of("홍경", "노윤서", "김민주"))
//                .build();
//
//        //when, then
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movies/new")
//                        .content(objectMapper.writeValueAsString(request))
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.status").value("OK"))
//                .andExpect(jsonPath("$.message").value("OK"));
//    }
//
//    @DisplayName("신규 영화를 등록할 때, 영화제목은 필수이다.")
//    @Test
//    void createMovieWithReleaseDateAfterThanCurrent() throws Exception {
//        //given
//        LocalDate now = LocalDate.now();
//
//        MovieCreateRequest request = MovieCreateRequest.builder()
//                .subTitle("Hear Me: Our Summer")
//                .description("사랑이야기")
//                .releaseDate(now.plusDays(1))
//                .durationMinutes(108)
//                .posterUrl("test@test.com")
//                .ageRating(AgeRating.ALL)
//                .director("조선호")
//                .screeningType(ScreeningType.TWO_D)
//                .standardPrice(10000)
//                .genreTypes(List.of(GenreType.DRAMA, GenreType.COMEDY))
//                .actors(List.of("홍경", "노윤서", "김민주"))
//                .build();
//
//        //when, then
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movies/new")
//                        .content(objectMapper.writeValueAsString(request))
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(jsonPath("$.code").value(400))
//                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
//                .andExpect(jsonPath("$.message").value("영화제목은 필수 입력값입니다."))
//                .andExpect(jsonPath("$.data").isEmpty());
//    }

}