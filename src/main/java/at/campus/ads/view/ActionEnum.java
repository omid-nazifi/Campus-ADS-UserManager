package at.campus.ads.view;

import javassist.NotFoundException;

public enum ActionEnum {
    EXIT(0),
    LOGIN(1),
    REGISTER(2),
    CHANGE_PASSWORD(3),
    DELETE_ACCOUNT(4),
    LOGOUT(5),
    GO_HOME(6);

    private int value;

    ActionEnum(int value) {
        this.value = value;
    }

    public static ActionEnum get(int value) throws NotFoundException {
        for (ActionEnum actionEnum : values()) {
            if (actionEnum.value == value) {
                return actionEnum;
            }
        }
        throw new NotFoundException("Could not found entered action!");
    }
}
