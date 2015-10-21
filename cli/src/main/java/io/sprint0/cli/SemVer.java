package io.sprint0.cli;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SemVer {

    private String patternString = "^(?:(\\d+)\\.)?(?:(\\d+)\\.)?(\\*|\\d+)$";
    private Pattern pattern = Pattern.compile(patternString);

    private String value;
    private int major;
    private int minor;
    private int patch;

    private boolean latest;


    public SemVer(String value) {
        this(value, false);
    }

    public SemVer(String value, boolean strict) {
        this.value = value;

        if ("latest".equalsIgnoreCase(value)) {
            latest = true;
        } else {

            Matcher matcher = pattern.matcher(value);
            boolean foundMatch = matcher.find();
            if (foundMatch) {
                major = Integer.parseInt(matcher.group(1));
                minor = Integer.parseInt(matcher.group(2));
                patch = Integer.parseInt(matcher.group(3));
            } else {
                if (strict) {
                    throw new IllegalArgumentException("Value '" + value + "' is not sem-ver compliant");
                }
            }

        }
    }

    public String getValue() {
        return value;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    public boolean isLatest() {
        return latest;
    }

    public static class SVComparator implements Comparator<SemVer> {

        @Override
        public int compare(SemVer left, SemVer right) {
            if (left.isLatest() && right.isLatest()) {
                return 0;
            }

            if (left.isLatest()) {
                return -1;
            }

            if (right.isLatest()) {
                return 1;
            }

            int majorCompare = Integer.compare(left.getMajor(), right.getMajor()) * 1000000;
            int minorCompare = Integer.compare(left.getMinor(), right.getMinor()) * 1000;
            int patchCompare = Integer.compare(left.getPatch(), right.getPatch()) * 1;

            return majorCompare + minorCompare + patchCompare;
        }
    }
}
