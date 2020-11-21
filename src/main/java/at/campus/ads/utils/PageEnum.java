package at.campus.ads.utils;

import javassist.NotFoundException;

public enum PageEnum {
    START_PAGE(0),
    HOME(1),
    LOGIN(2),
    REGISTER(3);

    private int value;

    PageEnum(int value) {
        this.value = value;
    }

    public PageEnum get(int value) throws NotFoundException {
        for (PageEnum actionEnum : values()) {
            if (actionEnum.value == value) {
                return actionEnum;
            }
        }
        throw new NotFoundException("Could not found this page!");
    }
}
