package mtdo.learn.javaee.ticketmonster.rest;

import java.util.Comparator;

import mtdo.learn.javaee.ticketmonster.model.Section;

public class SectionComparator implements Comparator<Section> {

    private static final SectionComparator INSTANCE = new SectionComparator();

    private SectionComparator(){
    }

    public static SectionComparator instance() {
        return INSTANCE;
    }

    @Override
    public int compare(Section section, Section otherSection) {
        return section.getId().compareTo(otherSection.getId());
    }

}
