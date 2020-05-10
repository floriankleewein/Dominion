package com.floriankleewein.commonclasses.Network;

import java.util.ArrayList;
import java.util.List;

public class UpdatePlayerNamesMsg extends BaseMessage {
    List<String> nameList = new ArrayList<>();

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }
}
