package ru.merenkov.infoSysWeb.group;

public class GroupId {
    Long groupId;

    public GroupId() {
        groupId = 1l;
    }

    public GroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
