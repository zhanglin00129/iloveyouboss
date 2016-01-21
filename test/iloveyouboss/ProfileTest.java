package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ProfileTest {

    private Profile profile;
    private Criteria criterions;

    private Answer positiveAnswer1;
    private Answer positiveAnswer2;

    private Answer negativeAnswer1;
    private Answer negativeAnswer2;

    private Criterion criterion1;
    private Criterion criterion2;

    private Question question1;
    private Question question2;


    @Before
    public void setUp() throws Exception {
        profile = new Profile("profileName");
        criterions = new Criteria();

        question1 = new BooleanQuestion(1, "text1");
        question2 = new BooleanQuestion(2, "text2");

        positiveAnswer1 = new Answer(question1, 1);
        negativeAnswer1 = new Answer(question1, -1);

        positiveAnswer2 = new Answer(question2, 1);
        negativeAnswer2 = new Answer(question2, -1);
    }

    @Test
    public void return_false_when_criteria_is_empty() throws Exception {
        boolean matches = profile.matches(criterions);
        assertThat(matches, is(false));
    }


    @Test
    public void return_true_when_criterian_weight_is_dont_care() throws Exception {
        profile.add(negativeAnswer1);

        criterion1 = new Criterion(positiveAnswer1, Weight.DontCare);
        criterions.add(criterion1);

        boolean matches = profile.matches(criterions);
        assertThat(matches, is(true));
    }


    @Test
    public void return_true_when_profile_answers_contains_matched_answer() {
        profile.add(positiveAnswer1);
        profile.add(negativeAnswer2);

        criterion1 = new Criterion(positiveAnswer1, Weight.Important);
        criterions.add(criterion1);

        boolean matches = profile.matches(criterions);
        assertThat(matches, is(true));
    }

    @Test
    public void return_true_when_profile_answers_contain_at_least_one_matched_answer() {
        profile.add(positiveAnswer1);
        profile.add(negativeAnswer2);

        criterion1 = new Criterion(positiveAnswer1, Weight.Important);
        criterion2 = new Criterion(positiveAnswer2, Weight.Important);
        criterions.add(criterion1);
        criterions.add(criterion2);

        boolean matches = profile.matches(criterions);
        assertThat(matches, is(true));
    }

    @Test
    public void return_false_when_profile_answers_does_not_contain_matched_answer() {
        profile.add(negativeAnswer1);
        profile.add(positiveAnswer2);

        criterion1 = new Criterion(positiveAnswer1, Weight.Important);
        criterions.add(criterion1);

        boolean matches = profile.matches(criterions);
        assertThat(matches, is(false));
    }

    @Test
    public void return_false_when_profile_answers_contains_at_least_one_not_matched_answer_with_must_match_weight() {
        profile.add(positiveAnswer1);
        profile.add(positiveAnswer2);

        criterion1 = new Criterion(positiveAnswer1, Weight.Important);
        criterion2 = new Criterion(negativeAnswer2, Weight.MustMatch);
        criterions.add(criterion1);
        criterions.add(criterion2);

        boolean matches = profile.matches(criterions);
        assertThat(matches, is(false));
    }
    
}
