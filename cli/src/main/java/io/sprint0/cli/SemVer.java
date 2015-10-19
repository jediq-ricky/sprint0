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
    private String classifier;

    private boolean latest;


    public SemVer(String value) {
        this.value = value;
        if (value.equalsIgnoreCase("latest")) {
            latest = true;
        } else {

            Matcher matcher = pattern.matcher(value);
            boolean foundMatch = matcher.find();
            if (!foundMatch) {
                throw new IllegalArgumentException("Value '" + value + "' is not sem-ver compliant");
            }

            major = Integer.parseInt(matcher.group(1));
            minor = Integer.parseInt(matcher.group(2));
            patch = Integer.parseInt(matcher.group(3));
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

    public String getClassifier() {
        return classifier;
    }

    public static class SVComparator implements Comparator<SemVer> {

        @Override
        public int compare(SemVer left, SemVer right) {
            int majorCompare = Integer.compare(left.getMajor(), right.getMajor());
            int minorCompare = Integer.compare(left.getMinor(), right.getMinor());
            int patchCompare = Integer.compare(left.getPatch(), right.getPatch());
            int classifierCompare = left.getClassifier() != null ? left.getClassifier().compareTo(right.getClassifier()) : 0;

            if (majorCompare != 0) {
                return majorCompare;
            }
            if (minorCompare != 0) {
                return minorCompare;
            }
            if (patchCompare != 0) {
                return patchCompare;
            }
            return classifierCompare;
        }
    }
}
