package mt.movie_theater;

import com.fasterxml.jackson.databind.ObjectMapper;
import mt.movie_theater.api.controller.movie.MovieController;
import mt.movie_theater.api.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MovieController.class)
public class ControllerTestSupport {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected MovieService movieService;
}
