package second.junit;

import org.example.HTTPRequests;
import org.example.ProcessQuery;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

public class HTTPMockTest {
    HTTPRequests httpQueryClass;

    @Before
    public void setUp() {
        httpQueryClass = Mockito.mock(HTTPRequests.class);
    }

    @Test
    public void mockTestExample() {
        Mockito.when(httpQueryClass.getRequest(any())).thenReturn("test");

        ProcessQuery processQuery = new ProcessQuery(httpQueryClass);

        String result = processQuery.process("test param");
        assertThat(result).isEqualTo("TEST");
    }
}
