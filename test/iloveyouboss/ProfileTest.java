package iloveyouboss;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileTest {

    private Profile profile;
    private Criteria criterions;
    private Answer answer;
    private Criterion criterion;

    @Mock
    private Question question;

    @Before
    public void setUp() throws Exception {
        profile = new Profile("profileName");
        criterions = new Criteria();
        when(question.getText()).thenReturn("text");
        answer = new Answer(question, 1);
        criterion = new Criterion(answer, Weight.Important);
    }

    @Test
    public void return_false_when_criteria_is_empty() throws Exception {
        boolean matches = profile.matches(criterions);
        assertThat(matches, is(false));
    }

    @Test
    public void return_false_when_criterian_weight_is_not_must_match() throws Exception {
        criterions.add(criterion);
        profile.add(answer);

        boolean matches = profile.matches(criterions);
        assertThat(matches, is(false));
    }
    
}
