package io.sprint0.cli;

import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SemVerTest {

    @Test
    public void testCompare() {
        SemVer.SVComparator semVer = new SemVer.SVComparator();
        assertThat(semVer.compare(new SemVer("0.0.0"), new SemVer("0.0.0")), is(0));
        assertThat(semVer.compare(new SemVer("0.0.0"), new SemVer("0.0.1")), is(lessThan(0)));
        assertThat(semVer.compare(new SemVer("0.0.1"), new SemVer("0.0.0")), is(greaterThan(0)));
        assertThat(semVer.compare(new SemVer("0.2.1"), new SemVer("0.1.5")), is(greaterThan(0)));
    }

    @Test
    public void testCompare_latest() {
        SemVer.SVComparator semVer = new SemVer.SVComparator();
        assertThat(semVer.compare(new SemVer("latest"), new SemVer("latest")), is(0));
        assertThat(semVer.compare(new SemVer("0.0.0"), new SemVer("latest")), is(greaterThan(0)));
        assertThat(semVer.compare(new SemVer("latest"), new SemVer("0.0.1")), is(lessThan(0)));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNonCompliant_strict() {
        new SemVer("greklgea", true);
    }

    @Test
    public void testNonCompliant_nonStrict() {
        new SemVer("dsvndskl");
    }

    @Test
    public void testInstance() {
        checkInstance("1.0.0", 1, 0, 0);
        checkInstance("1.2.0", 1, 2, 0);
        checkInstance("1.2.3", 1, 2, 3);
    }

    private void checkInstance(String value, int major, int minor, int patch) {
        SemVer instance = new SemVer(value);
        assertThat(instance.getMajor(), is(major));
        assertThat(instance.getMinor(), is(minor));
        assertThat(instance.getPatch(), is(patch));
    }

}
